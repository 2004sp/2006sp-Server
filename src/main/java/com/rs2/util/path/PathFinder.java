package com.rs2.util.path;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.player.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Server-side route finder backed by the 377 walking collision map.
 *
 * Local routes use a reusable primitive BFS workspace. Long/global routes use
 * a bounded A* search so they do not allocate multi-million-cell matrices.
 */
public final class PathFinder {
    private static final PathFinder instance = new PathFinder();

    private static final int SEARCH_SIZE = 128;
    private static final int SEARCH_HALF = SEARCH_SIZE / 2;
    private static final int SEARCH_AREA = SEARCH_SIZE * SEARCH_SIZE;
    private static final int GLOBAL_NODE_LIMIT = 80000;
    private static final int ALTERNATIVE_RADIUS = 10;
    private static final int ALTERNATIVE_MAX_STEPS = 100;

    private static final int[] DELTA_X = {0, -1, 0, 1, -1, -1, 1, 1};
    private static final int[] DELTA_Y = {-1, 0, 1, 0, -1, 1, -1, 1};
    private static final byte[] DIRECTION = {1, 2, 4, 8, 3, 6, 9, 12};

    private static final ThreadLocal<SearchWorkspace> LOCAL_WORKSPACE =
            new ThreadLocal<SearchWorkspace>() {
                @Override
                protected SearchWorkspace initialValue() {
                    return new SearchWorkspace();
                }
            };

    private static final Comparator<GlobalNode> GLOBAL_NODE_COMPARATOR =
            new Comparator<GlobalNode>() {
                @Override
                public int compare(GlobalNode first, GlobalNode second) {
                    if (first.f != second.f) {
                        return first.f < second.f ? -1 : 1;
                    }
                    if (first.h != second.h) {
                        return first.h < second.h ? -1 : 1;
                    }
                    return first.g < second.g ? -1 : (first.g == second.g ? 0 : 1);
                }
            };

    private PathFinder() {
    }

    public static PathFinder getInstance() {
        return instance;
    }

    /**
     * Compatibility entry point for the old large-grid route finder.
     */
    public static boolean findGlobalPath(Player player, int targetX, int targetY,
                                         boolean allowAlternative,
                                         int targetWidth, int targetHeight) {
        if (player == null) {
            return false;
        }

        int width = Math.max(1, targetWidth);
        int height = Math.max(1, targetHeight);
        Route route = searchGlobal(player, targetX, targetY, width, height, true);
        if (route == null) {
            return false;
        }

        queueRoute(player, route);
        return true;
    }

    public static boolean findPath(Player player, int targetX, int targetY,
                                   boolean allowAlternative,
                                   int targetWidth, int targetHeight) {
        return findPath((Entity) player, targetX, targetY,
                allowAlternative, targetWidth, targetHeight);
    }

    /**
     * Finds a route to an exact tile. targetWidth/targetHeight are used only
     * when selecting a nearest reachable alternative, matching the old API.
     */
    public static boolean findPath(Entity entity, int targetX, int targetY,
                                   boolean allowAlternative,
                                   int targetWidth, int targetHeight) {
        if (entity == null) {
            return false;
        }

        Route route = searchLocal(entity, targetX, targetY,
                Math.max(1, targetWidth), Math.max(1, targetHeight),
                allowAlternative, RouteMode.EXACT_TILE);
        if (route == null) {
            return false;
        }

        queueRoute(entity, route);
        return true;
    }

    /**
     * Routes to an orthogonally adjacent tile around a target rectangle.
     * This is the correct destination model for scenery and entity interaction.
     */
    public static boolean findPathToAdjacent(Entity entity,
                                             int targetX, int targetY,
                                             int targetWidth, int targetHeight,
                                             boolean allowAlternative) {
        if (entity == null) {
            return false;
        }

        Route route = searchLocal(entity, targetX, targetY,
                Math.max(1, targetWidth), Math.max(1, targetHeight),
                allowAlternative, RouteMode.ADJACENT_RECTANGLE);
        if (route == null) {
            return false;
        }

        queueRoute(entity, route);
        return true;
    }

    /**
     * Reachability check without mutating the movement queue.
     */
    public static boolean isReachable(Entity entity, int targetX, int targetY) {
        if (entity == null) {
            return false;
        }

        return searchLocal(entity, targetX, targetY,
                1, 1, false, RouteMode.EXACT_TILE) != null;
    }

    private static Route searchLocal(Entity entity,
                                     int targetX, int targetY,
                                     int targetWidth, int targetHeight,
                                     boolean allowAlternative,
                                     RouteMode mode) {
        SearchWorkspace workspace = LOCAL_WORKSPACE.get();
        int generation = workspace.nextGeneration();

        int startX = entity.getPosition().getX();
        int startY = entity.getPosition().getY();
        int plane = entity.getPosition().getPlane();
        int moverSize = Math.max(1, entity.getSize());

        int baseX = startX - SEARCH_HALF;
        int baseY = startY - SEARCH_HALF;
        int startLocalX = SEARCH_HALF;
        int startLocalY = SEARCH_HALF;
        int startIndex = index(startLocalX, startLocalY);

        int read = 0;
        int write = 0;
        workspace.queue[write++] = pack(startLocalX, startLocalY);
        workspace.visited[startIndex] = generation;
        workspace.directions[startIndex] = 99;
        workspace.distances[startIndex] = 0;

        int endLocalX = -1;
        int endLocalY = -1;
        boolean reached = false;

        while (read < write) {
            int packed = workspace.queue[read++];
            int localX = packed >>> 8;
            int localY = packed & 0xff;
            int worldX = baseX + localX;
            int worldY = baseY + localY;

            if (hasReached(mode, worldX, worldY, moverSize,
                    targetX, targetY, targetWidth, targetHeight)) {
                endLocalX = localX;
                endLocalY = localY;
                reached = true;
                break;
            }

            int currentIndex = index(localX, localY);
            int nextDistance = (workspace.distances[currentIndex] & 0xffff) + 1;

            for (int directionIndex = 0; directionIndex < DELTA_X.length; directionIndex++) {
                int nextLocalX = localX + DELTA_X[directionIndex];
                int nextLocalY = localY + DELTA_Y[directionIndex];

                if (nextLocalX < 0 || nextLocalY < 0
                        || nextLocalX >= SEARCH_SIZE || nextLocalY >= SEARCH_SIZE) {
                    continue;
                }

                int nextIndex = index(nextLocalX, nextLocalY);
                if (workspace.visited[nextIndex] == generation) {
                    continue;
                }

                int nextWorldX = worldX + DELTA_X[directionIndex];
                int nextWorldY = worldY + DELTA_Y[directionIndex];
                if (!WalkingCollisionMap.canTravelBetween(
                        worldX, worldY, nextWorldX, nextWorldY,
                        plane, moverSize, moverSize)) {
                    continue;
                }

                workspace.visited[nextIndex] = generation;
                workspace.directions[nextIndex] = DIRECTION[directionIndex];
                workspace.distances[nextIndex] =
                        (short) Math.min(Short.MAX_VALUE, nextDistance);
                workspace.queue[write++] = pack(nextLocalX, nextLocalY);
            }
        }

        if (!reached && allowAlternative) {
            int bestDistance = Integer.MAX_VALUE;
            int bestSteps = Integer.MAX_VALUE;

            for (int localY = 0; localY < SEARCH_SIZE; localY++) {
                for (int localX = 0; localX < SEARCH_SIZE; localX++) {
                    int tileIndex = index(localX, localY);
                    if (workspace.visited[tileIndex] != generation) {
                        continue;
                    }

                    int steps = workspace.distances[tileIndex] & 0xffff;
                    if (steps >= ALTERNATIVE_MAX_STEPS) {
                        continue;
                    }

                    int worldX = baseX + localX;
                    int worldY = baseY + localY;
                    int gapX = rectangleGap(worldX, worldX + moverSize - 1,
                            targetX, targetX + targetWidth - 1);
                    int gapY = rectangleGap(worldY, worldY + moverSize - 1,
                            targetY, targetY + targetHeight - 1);

                    if (gapX > ALTERNATIVE_RADIUS || gapY > ALTERNATIVE_RADIUS) {
                        continue;
                    }

                    int distance = gapX * gapX + gapY * gapY;
                    if (distance < bestDistance
                            || (distance == bestDistance && steps < bestSteps)) {
                        bestDistance = distance;
                        bestSteps = steps;
                        endLocalX = localX;
                        endLocalY = localY;
                    }
                }
            }
        }

        if (endLocalX < 0 || endLocalY < 0) {
            return null;
        }

        return reconstructLocal(workspace, baseX, baseY,
                startLocalX, startLocalY, endLocalX, endLocalY);
    }

    private static Route searchGlobal(Entity entity,
                                      int targetX, int targetY,
                                      int targetWidth, int targetHeight,
                                      boolean allowAlternative) {
        int startX = entity.getPosition().getX();
        int startY = entity.getPosition().getY();
        int plane = entity.getPosition().getPlane();
        int moverSize = Math.max(1, entity.getSize());

        PriorityQueue<GlobalNode> open =
                new PriorityQueue<GlobalNode>(256, GLOBAL_NODE_COMPARATOR);
        Map<Long, GlobalNode> nodes = new HashMap<Long, GlobalNode>(4096);

        GlobalNode start = new GlobalNode(startX, startY, 0,
                heuristic(startX, startY, targetX, targetY), null);
        open.add(start);
        nodes.put(key(startX, startY), start);

        GlobalNode result = null;
        GlobalNode bestAlternative = null;
        int bestDistance = Integer.MAX_VALUE;
        int bestSteps = Integer.MAX_VALUE;
        int expanded = 0;

        while (!open.isEmpty() && expanded < GLOBAL_NODE_LIMIT) {
            GlobalNode current = open.poll();
            if (nodes.get(key(current.x, current.y)) != current || current.closed) {
                continue;
            }

            current.closed = true;
            expanded++;

            if (current.x == targetX && current.y == targetY) {
                result = current;
                break;
            }

            if (allowAlternative && current.g < ALTERNATIVE_MAX_STEPS) {
                int gapX = rectangleGap(current.x, current.x + moverSize - 1,
                        targetX, targetX + targetWidth - 1);
                int gapY = rectangleGap(current.y, current.y + moverSize - 1,
                        targetY, targetY + targetHeight - 1);

                if (gapX <= ALTERNATIVE_RADIUS && gapY <= ALTERNATIVE_RADIUS) {
                    int distance = gapX * gapX + gapY * gapY;
                    if (distance < bestDistance
                            || (distance == bestDistance && current.g < bestSteps)) {
                        bestDistance = distance;
                        bestSteps = current.g;
                        bestAlternative = current;
                    }
                }
            }

            for (int directionIndex = 0; directionIndex < DELTA_X.length; directionIndex++) {
                int nextX = current.x + DELTA_X[directionIndex];
                int nextY = current.y + DELTA_Y[directionIndex];

                if (!WalkingCollisionMap.canTravelBetween(
                        current.x, current.y, nextX, nextY,
                        plane, moverSize, moverSize)) {
                    continue;
                }

                int nextG = current.g + 1;
                long nextKey = key(nextX, nextY);
                GlobalNode previous = nodes.get(nextKey);
                if (previous != null && previous.g <= nextG) {
                    continue;
                }

                int h = heuristic(nextX, nextY, targetX, targetY);
                GlobalNode next = new GlobalNode(nextX, nextY, nextG, h, current);
                nodes.put(nextKey, next);
                open.add(next);
            }
        }

        if (result == null) {
            result = bestAlternative;
        }
        if (result == null) {
            return null;
        }

        return reconstructGlobal(result);
    }

    private static boolean hasReached(RouteMode mode,
                                      int x, int y, int moverSize,
                                      int targetX, int targetY,
                                      int targetWidth, int targetHeight) {
        if (mode == RouteMode.EXACT_TILE) {
            return x == targetX && y == targetY;
        }

        int sourceMinX = x;
        int sourceMaxX = x + moverSize - 1;
        int sourceMinY = y;
        int sourceMaxY = y + moverSize - 1;
        int targetMinX = targetX;
        int targetMaxX = targetX + targetWidth - 1;
        int targetMinY = targetY;
        int targetMaxY = targetY + targetHeight - 1;

        boolean xOverlap = sourceMaxX >= targetMinX && sourceMinX <= targetMaxX;
        boolean yOverlap = sourceMaxY >= targetMinY && sourceMinY <= targetMaxY;

        boolean besideX = sourceMaxX + 1 == targetMinX
                || targetMaxX + 1 == sourceMinX;
        boolean besideY = sourceMaxY + 1 == targetMinY
                || targetMaxY + 1 == sourceMinY;

        return (besideX && yOverlap) || (besideY && xOverlap);
    }

    private static Route reconstructLocal(SearchWorkspace workspace,
                                          int baseX, int baseY,
                                          int startX, int startY,
                                          int endX, int endY) {
        int count = 0;
        int x = endX;
        int y = endY;

        while (x != startX || y != startY) {
            count++;
            int direction = workspace.directions[index(x, y)] & 0xff;
            if (direction == 0 || direction == 99) {
                return null;
            }

            if ((direction & 2) != 0) {
                x++;
            } else if ((direction & 8) != 0) {
                x--;
            }

            if ((direction & 1) != 0) {
                y++;
            } else if ((direction & 4) != 0) {
                y--;
            }
        }

        int[] routeX = new int[count];
        int[] routeY = new int[count];
        x = endX;
        y = endY;

        for (int index = count - 1; index >= 0; index--) {
            routeX[index] = baseX + x;
            routeY[index] = baseY + y;

            int direction = workspace.directions[PathFinder.index(x, y)] & 0xff;
            if ((direction & 2) != 0) {
                x++;
            } else if ((direction & 8) != 0) {
                x--;
            }

            if ((direction & 1) != 0) {
                y++;
            } else if ((direction & 4) != 0) {
                y--;
            }
        }

        return new Route(routeX, routeY);
    }

    private static Route reconstructGlobal(GlobalNode end) {
        int count = 0;
        GlobalNode cursor = end;
        while (cursor.parent != null) {
            count++;
            cursor = cursor.parent;
        }

        int[] routeX = new int[count];
        int[] routeY = new int[count];
        cursor = end;

        for (int index = count - 1; index >= 0; index--) {
            routeX[index] = cursor.x;
            routeY[index] = cursor.y;
            cursor = cursor.parent;
        }

        return new Route(routeX, routeY);
    }

    private static void queueRoute(Entity entity, Route route) {
        entity.getMovementQueue().clear();
        int plane = entity.getPosition().getPlane();

        for (int index = 0; index < route.x.length; index++) {
            entity.getMovementQueue().addStep(
                    new Position(route.x[index], route.y[index], plane));
        }

        entity.getMovementQueue().removeFirstStep();
    }

    private static int heuristic(int x, int y, int targetX, int targetY) {
        return Math.max(Math.abs(targetX - x), Math.abs(targetY - y));
    }

    private static int rectangleGap(int minA, int maxA, int minB, int maxB) {
        if (maxA < minB) {
            return minB - maxA;
        }
        if (minA > maxB) {
            return minA - maxB;
        }
        return 0;
    }

    private static int index(int x, int y) {
        return y * SEARCH_SIZE + x;
    }

    private static int pack(int x, int y) {
        return (x << 8) | y;
    }

    private static long key(int x, int y) {
        return ((long) x << 32) ^ (y & 0xffffffffL);
    }

    private enum RouteMode {
        EXACT_TILE,
        ADJACENT_RECTANGLE
    }

    private static final class SearchWorkspace {
        private final int[] visited = new int[SEARCH_AREA];
        private final byte[] directions = new byte[SEARCH_AREA];
        private final short[] distances = new short[SEARCH_AREA];
        private final int[] queue = new int[SEARCH_AREA];
        private int generation = 1;

        private int nextGeneration() {
            generation++;
            if (generation == Integer.MAX_VALUE) {
                java.util.Arrays.fill(visited, 0);
                generation = 1;
            }
            return generation;
        }
    }

    private static final class Route {
        private final int[] x;
        private final int[] y;

        private Route(int[] x, int[] y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final class GlobalNode {
        private final int x;
        private final int y;
        private final int g;
        private final int h;
        private final int f;
        private final GlobalNode parent;
        private boolean closed;

        private GlobalNode(int x, int y, int g, int h, GlobalNode parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.parent = parent;
        }
    }
}
