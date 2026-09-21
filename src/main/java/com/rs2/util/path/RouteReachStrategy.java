package com.rs2.util.path;

/**
 * RuneScape-style destination reach checks.
 *
 * Object shape and rotation are part of the route destination: walls,
 * wall decorations and rectangular scenery have different valid approach
 * tiles. The low collision bits used here are the same wall flags populated
 * by WalkingCollisionMap.
 */
final class RouteReachStrategy {
    static final int EXACT_TILE = -1;
    static final int EXCLUSIVE_RECTANGLE = -2;

    private static final int WALL_NORTH_WEST = 0x1;
    private static final int WALL_NORTH = 0x2;
    private static final int WALL_NORTH_EAST = 0x4;
    private static final int WALL_EAST = 0x8;
    private static final int WALL_SOUTH_EAST = 0x10;
    private static final int WALL_SOUTH = 0x20;
    private static final int WALL_SOUTH_WEST = 0x40;
    private static final int WALL_WEST = 0x80;

    private static final int BLOCK_WEST = 0x1280108;
    private static final int BLOCK_EAST = 0x1280180;
    private static final int BLOCK_SOUTH = 0x1280102;
    private static final int BLOCK_NORTH = 0x1280120;

    private static final int ACCESS_NORTH = 0x1;
    private static final int ACCESS_EAST = 0x2;
    private static final int ACCESS_SOUTH = 0x4;
    private static final int ACCESS_WEST = 0x8;

    private static final int WALL_STRATEGY = 0;
    private static final int WALL_DECORATION_STRATEGY = 1;
    private static final int RECTANGLE_STRATEGY = 2;
    private static final int NO_STRATEGY = 3;
    private static final int EXCLUSIVE_RECTANGLE_STRATEGY = 4;

    private RouteReachStrategy() {
    }

    static boolean reached(int plane,
                           int x, int y,
                           int destinationX, int destinationY,
                           int destinationWidth, int destinationHeight,
                           int sourceSize,
                           int rotation,
                           int shape,
                           int accessMask) {
        int strategy = exitStrategy(shape);

        if (strategy != EXCLUSIVE_RECTANGLE_STRATEGY
                && x == destinationX && y == destinationY) {
            return true;
        }

        switch (strategy) {
            case WALL_STRATEGY:
                return reachWall(plane, x, y, destinationX, destinationY,
                        sourceSize, shape, rotation);
            case WALL_DECORATION_STRATEGY:
                return reachWallDecoration(plane, x, y, destinationX, destinationY,
                        sourceSize, shape, rotation);
            case RECTANGLE_STRATEGY:
                return reachRectangle(plane, x, y, destinationX, destinationY,
                        sourceSize, destinationWidth, destinationHeight,
                        rotation, accessMask, false);
            case EXCLUSIVE_RECTANGLE_STRATEGY:
                return reachRectangle(plane, x, y, destinationX, destinationY,
                        sourceSize, destinationWidth, destinationHeight,
                        rotation, accessMask, true);
            default:
                return false;
        }
    }

    static int destinationWidth(int shape, int rotation, int width, int height) {
        if (usesRectangleStrategy(shape) && (rotation & 1) != 0) {
            return Math.max(1, height);
        }
        return Math.max(1, width);
    }

    static int destinationHeight(int shape, int rotation, int width, int height) {
        if (usesRectangleStrategy(shape) && (rotation & 1) != 0) {
            return Math.max(1, width);
        }
        return Math.max(1, height);
    }

    private static boolean usesRectangleStrategy(int shape) {
        return shape == EXCLUSIVE_RECTANGLE
                || shape == 10 || shape == 11 || shape == 22;
    }

    private static int exitStrategy(int shape) {
        if (shape == EXCLUSIVE_RECTANGLE) {
            return EXCLUSIVE_RECTANGLE_STRATEGY;
        }
        if (shape == EXACT_TILE) {
            return NO_STRATEGY;
        }
        if ((shape >= 0 && shape <= 3) || shape == 9) {
            return WALL_STRATEGY;
        }
        if (shape >= 4 && shape < 9) {
            return WALL_DECORATION_STRATEGY;
        }
        if (shape == 10 || shape == 11 || shape == 22) {
            return RECTANGLE_STRATEGY;
        }
        return NO_STRATEGY;
    }

    private static boolean reachRectangle(int plane,
                                          int x, int y,
                                          int destinationX, int destinationY,
                                          int sourceSize,
                                          int destinationWidth,
                                          int destinationHeight,
                                          int rotation,
                                          int accessMask,
                                          boolean exclusive) {
        int width = destinationWidth(RECTANGLE_STRATEGY, rotation,
                destinationWidth, destinationHeight);
        int height = destinationHeight(RECTANGLE_STRATEGY, rotation,
                destinationWidth, destinationHeight);
        int rotatedAccessMask = rotateAccessMask(rotation, accessMask);

        boolean overlaps = rectanglesOverlap(
                x, y, sourceSize, sourceSize,
                destinationX, destinationY, width, height);

        if (exclusive && overlaps) {
            return false;
        }
        if (!exclusive && overlaps) {
            return true;
        }

        if (sourceSize == 1) {
            return reachRectangleOne(plane, x, y,
                    destinationX, destinationY, width, height,
                    rotatedAccessMask);
        }

        return reachRectangleMany(plane, x, y, sourceSize, sourceSize,
                destinationX, destinationY, width, height,
                rotatedAccessMask);
    }

    private static boolean reachRectangleOne(int plane,
                                             int x, int y,
                                             int destinationX, int destinationY,
                                             int width, int height,
                                             int accessMask) {
        int east = destinationX + width - 1;
        int north = destinationY + height - 1;

        if (x == destinationX - 1 && y >= destinationY && y <= north
                && (flags(x, y, plane) & WALL_EAST) == 0
                && (accessMask & ACCESS_WEST) == 0) {
            return true;
        }

        if (x == east + 1 && y >= destinationY && y <= north
                && (flags(x, y, plane) & WALL_WEST) == 0
                && (accessMask & ACCESS_EAST) == 0) {
            return true;
        }

        if (y + 1 == destinationY && x >= destinationX && x <= east
                && (flags(x, y, plane) & WALL_NORTH) == 0
                && (accessMask & ACCESS_SOUTH) == 0) {
            return true;
        }

        return y == north + 1 && x >= destinationX && x <= east
                && (flags(x, y, plane) & WALL_SOUTH) == 0
                && (accessMask & ACCESS_NORTH) == 0;
    }

    private static boolean reachRectangleMany(int plane,
                                              int x, int y,
                                              int sourceWidth, int sourceHeight,
                                              int destinationX, int destinationY,
                                              int destinationWidth,
                                              int destinationHeight,
                                              int accessMask) {
        int sourceEast = x + sourceWidth;
        int sourceNorth = y + sourceHeight;
        int destinationEast = destinationX + destinationWidth;
        int destinationNorth = destinationY + destinationHeight;

        if (destinationEast == x && (accessMask & ACCESS_EAST) == 0) {
            int fromY = Math.max(y, destinationY);
            int toY = Math.min(sourceNorth, destinationNorth);
            for (int sideY = fromY; sideY < toY; sideY++) {
                if ((flags(destinationEast - 1, sideY, plane) & WALL_EAST) == 0) {
                    return true;
                }
            }
        } else if (sourceEast == destinationX && (accessMask & ACCESS_WEST) == 0) {
            int fromY = Math.max(y, destinationY);
            int toY = Math.min(sourceNorth, destinationNorth);
            for (int sideY = fromY; sideY < toY; sideY++) {
                if ((flags(destinationX, sideY, plane) & WALL_WEST) == 0) {
                    return true;
                }
            }
        } else if (y == destinationNorth && (accessMask & ACCESS_NORTH) == 0) {
            int fromX = Math.max(x, destinationX);
            int toX = Math.min(sourceEast, destinationEast);
            for (int sideX = fromX; sideX < toX; sideX++) {
                if ((flags(sideX, destinationNorth - 1, plane) & WALL_NORTH) == 0) {
                    return true;
                }
            }
        } else if (destinationY == sourceNorth && (accessMask & ACCESS_SOUTH) == 0) {
            int fromX = Math.max(x, destinationX);
            int toX = Math.min(sourceEast, destinationEast);
            for (int sideX = fromX; sideX < toX; sideX++) {
                if ((flags(sideX, destinationY, plane) & WALL_SOUTH) == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean reachWall(int plane,
                                     int x, int y,
                                     int destinationX, int destinationY,
                                     int sourceSize,
                                     int shape,
                                     int rotation) {
        if (sourceSize == 1) {
            return reachWallOne(plane, x, y, destinationX, destinationY,
                    shape, rotation);
        }
        return reachWallMany(plane, x, y, destinationX, destinationY,
                sourceSize, shape, rotation);
    }

    private static boolean reachWallOne(int plane,
                                        int x, int y,
                                        int destinationX, int destinationY,
                                        int shape,
                                        int rotation) {
        if (shape == 0) {
            switch (rotation & 3) {
                case 0:
                    if (x == destinationX - 1 && y == destinationY) return true;
                    if (x == destinationX && y == destinationY + 1
                            && (flags(x, y, plane) & BLOCK_NORTH) == 0) return true;
                    return x == destinationX && y == destinationY - 1
                            && (flags(x, y, plane) & BLOCK_SOUTH) == 0;
                case 1:
                    if (x == destinationX && y == destinationY + 1) return true;
                    if (x == destinationX - 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_WEST) == 0) return true;
                    return x == destinationX + 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_EAST) == 0;
                case 2:
                    if (x == destinationX + 1 && y == destinationY) return true;
                    if (x == destinationX && y == destinationY + 1
                            && (flags(x, y, plane) & BLOCK_NORTH) == 0) return true;
                    return x == destinationX && y == destinationY - 1
                            && (flags(x, y, plane) & BLOCK_SOUTH) == 0;
                default:
                    if (x == destinationX && y == destinationY - 1) return true;
                    if (x == destinationX - 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_WEST) == 0) return true;
                    return x == destinationX + 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_EAST) == 0;
            }
        }

        if (shape == 2) {
            switch (rotation & 3) {
                case 0:
                    if (x == destinationX - 1 && y == destinationY) return true;
                    if (x == destinationX && y == destinationY + 1) return true;
                    if (x == destinationX + 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_EAST) == 0) return true;
                    return x == destinationX && y == destinationY - 1
                            && (flags(x, y, plane) & BLOCK_SOUTH) == 0;
                case 1:
                    if (x == destinationX - 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_WEST) == 0) return true;
                    if (x == destinationX && y == destinationY + 1) return true;
                    if (x == destinationX + 1 && y == destinationY) return true;
                    return x == destinationX && y == destinationY - 1
                            && (flags(x, y, plane) & BLOCK_SOUTH) == 0;
                case 2:
                    if (x == destinationX - 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_WEST) == 0) return true;
                    if (x == destinationX && y == destinationY + 1
                            && (flags(x, y, plane) & BLOCK_NORTH) == 0) return true;
                    if (x == destinationX + 1 && y == destinationY) return true;
                    return x == destinationX && y == destinationY - 1;
                default:
                    if (x == destinationX - 1 && y == destinationY) return true;
                    if (x == destinationX && y == destinationY + 1
                            && (flags(x, y, plane) & BLOCK_NORTH) == 0) return true;
                    if (x == destinationX + 1 && y == destinationY
                            && (flags(x, y, plane) & BLOCK_EAST) == 0) return true;
                    return x == destinationX && y == destinationY - 1;
            }
        }

        if (shape == 9) {
            if (x == destinationX && y == destinationY + 1
                    && (flags(x, y, plane) & WALL_SOUTH) == 0) return true;
            if (x == destinationX && y == destinationY - 1
                    && (flags(x, y, plane) & WALL_NORTH) == 0) return true;
            if (x == destinationX - 1 && y == destinationY
                    && (flags(x, y, plane) & WALL_EAST) == 0) return true;
            return x == destinationX + 1 && y == destinationY
                    && (flags(x, y, plane) & WALL_WEST) == 0;
        }

        return false;
    }

    private static boolean reachWallMany(int plane,
                                         int x, int y,
                                         int destinationX, int destinationY,
                                         int sourceSize,
                                         int shape,
                                         int rotation) {
        int east = x + sourceSize - 1;
        int north = y + sourceSize - 1;
        boolean overlapsY = y <= destinationY && north >= destinationY;
        boolean overlapsX = x <= destinationX && east >= destinationX;

        if (shape == 0) {
            switch (rotation & 3) {
                case 0:
                    if (x == destinationX - sourceSize && overlapsY) return true;
                    if (overlapsX && y == destinationY + 1
                            && (flags(destinationX, y, plane) & BLOCK_NORTH) == 0) return true;
                    return overlapsX && y == destinationY - sourceSize
                            && (flags(destinationX, north, plane) & BLOCK_SOUTH) == 0;
                case 1:
                    if (overlapsX && y == destinationY + 1) return true;
                    if (x == destinationX - sourceSize && overlapsY
                            && (flags(east, destinationY, plane) & BLOCK_WEST) == 0) return true;
                    return x == destinationX + 1 && overlapsY
                            && (flags(x, destinationY, plane) & BLOCK_EAST) == 0;
                case 2:
                    if (x == destinationX + 1 && overlapsY) return true;
                    if (overlapsX && y == destinationY + 1
                            && (flags(destinationX, y, plane) & BLOCK_NORTH) == 0) return true;
                    return overlapsX && y == destinationY - sourceSize
                            && (flags(destinationX, north, plane) & BLOCK_SOUTH) == 0;
                default:
                    if (overlapsX && y == destinationY - sourceSize) return true;
                    if (x == destinationX - sourceSize && overlapsY
                            && (flags(east, destinationY, plane) & BLOCK_WEST) == 0) return true;
                    return x == destinationX + 1 && overlapsY
                            && (flags(x, destinationY, plane) & BLOCK_EAST) == 0;
            }
        }

        if (shape == 2) {
            switch (rotation & 3) {
                case 0:
                    if (x == destinationX - sourceSize && overlapsY) return true;
                    if (overlapsX && y == destinationY + 1) return true;
                    if (x == destinationX + 1 && overlapsY
                            && (flags(x, destinationY, plane) & BLOCK_EAST) == 0) return true;
                    return overlapsX && y == destinationY - sourceSize
                            && (flags(destinationX, north, plane) & BLOCK_SOUTH) == 0;
                case 1:
                    if (x == destinationX - sourceSize && overlapsY
                            && (flags(east, destinationY, plane) & BLOCK_WEST) == 0) return true;
                    if (overlapsX && y == destinationY + 1) return true;
                    if (x == destinationX + 1 && overlapsY) return true;
                    return overlapsX && y == destinationY - sourceSize
                            && (flags(destinationX, north, plane) & BLOCK_SOUTH) == 0;
                case 2:
                    if (x == destinationX - sourceSize && overlapsY
                            && (flags(east, destinationY, plane) & BLOCK_WEST) == 0) return true;
                    if (overlapsX && y == destinationY + 1
                            && (flags(destinationX, y, plane) & BLOCK_NORTH) == 0) return true;
                    if (x == destinationX + 1 && overlapsY) return true;
                    return overlapsX && y == destinationY - sourceSize;
                default:
                    if (x == destinationX - sourceSize && overlapsY) return true;
                    if (overlapsX && y == destinationY + 1
                            && (flags(destinationX, y, plane) & BLOCK_NORTH) == 0) return true;
                    if (x == destinationX + 1 && overlapsY
                            && (flags(x, destinationY, plane) & BLOCK_EAST) == 0) return true;
                    return overlapsX && y == destinationY - sourceSize;
            }
        }

        if (shape == 9) {
            if (overlapsX && y == destinationY + 1
                    && (flags(destinationX, y, plane) & BLOCK_NORTH) == 0) return true;
            if (overlapsX && y == destinationY - sourceSize
                    && (flags(destinationX, north, plane) & BLOCK_SOUTH) == 0) return true;
            if (x == destinationX - sourceSize && overlapsY
                    && (flags(east, destinationY, plane) & BLOCK_WEST) == 0) return true;
            return x == destinationX + 1 && overlapsY
                    && (flags(x, destinationY, plane) & BLOCK_EAST) == 0;
        }

        return false;
    }

    private static boolean reachWallDecoration(int plane,
                                               int x, int y,
                                               int destinationX, int destinationY,
                                               int sourceSize,
                                               int shape,
                                               int rotation) {
        int alteredRotation = shape == 7 ? (rotation + 2) & 3 : rotation & 3;
        if (sourceSize == 1) {
            if (shape == 6 || shape == 7) {
                switch (alteredRotation) {
                    case 0:
                        if (x == destinationX + 1 && y == destinationY
                                && (flags(x, y, plane) & WALL_WEST) == 0) return true;
                        return x == destinationX && y == destinationY - 1
                                && (flags(x, y, plane) & WALL_NORTH) == 0;
                    case 1:
                        if (x == destinationX - 1 && y == destinationY
                                && (flags(x, y, plane) & WALL_EAST) == 0) return true;
                        return x == destinationX && y == destinationY - 1
                                && (flags(x, y, plane) & WALL_NORTH) == 0;
                    case 2:
                        if (x == destinationX - 1 && y == destinationY
                                && (flags(x, y, plane) & WALL_EAST) == 0) return true;
                        return x == destinationX && y == destinationY + 1
                                && (flags(x, y, plane) & WALL_SOUTH) == 0;
                    default:
                        if (x == destinationX + 1 && y == destinationY
                                && (flags(x, y, plane) & WALL_WEST) == 0) return true;
                        return x == destinationX && y == destinationY + 1
                                && (flags(x, y, plane) & WALL_SOUTH) == 0;
                }
            }

            if (shape == 8) {
                if (x == destinationX && y == destinationY + 1
                        && (flags(x, y, plane) & WALL_SOUTH) == 0) return true;
                if (x == destinationX && y == destinationY - 1
                        && (flags(x, y, plane) & WALL_NORTH) == 0) return true;
                if (x == destinationX - 1 && y == destinationY
                        && (flags(x, y, plane) & WALL_EAST) == 0) return true;
                return x == destinationX + 1 && y == destinationY
                        && (flags(x, y, plane) & WALL_WEST) == 0;
            }

            return false;
        }

        int east = x + sourceSize - 1;
        int north = y + sourceSize - 1;
        boolean overlapsY = y <= destinationY && north >= destinationY;
        boolean overlapsX = x <= destinationX && east >= destinationX;

        if (shape == 6 || shape == 7) {
            switch (alteredRotation) {
                case 0:
                    if (x == destinationX + 1 && overlapsY
                            && (flags(x, destinationY, plane) & WALL_WEST) == 0) return true;
                    return overlapsX && y == destinationY - sourceSize
                            && (flags(destinationX, north, plane) & WALL_NORTH) == 0;
                case 1:
                    if (x == destinationX - sourceSize && overlapsY
                            && (flags(east, destinationY, plane) & WALL_EAST) == 0) return true;
                    return overlapsX && y == destinationY - sourceSize
                            && (flags(destinationX, north, plane) & WALL_NORTH) == 0;
                case 2:
                    if (x == destinationX - sourceSize && overlapsY
                            && (flags(east, destinationY, plane) & WALL_EAST) == 0) return true;
                    return overlapsX && y == destinationY + 1
                            && (flags(destinationX, y, plane) & WALL_SOUTH) == 0;
                default:
                    if (x == destinationX + 1 && overlapsY
                            && (flags(x, destinationY, plane) & WALL_WEST) == 0) return true;
                    return overlapsX && y == destinationY + 1
                            && (flags(destinationX, y, plane) & WALL_SOUTH) == 0;
            }
        }

        if (shape == 8) {
            if (overlapsX && y == destinationY + 1
                    && (flags(destinationX, y, plane) & WALL_SOUTH) == 0) return true;
            if (overlapsX && y == destinationY - sourceSize
                    && (flags(destinationX, north, plane) & WALL_NORTH) == 0) return true;
            if (x == destinationX - sourceSize && overlapsY
                    && (flags(east, destinationY, plane) & WALL_EAST) == 0) return true;
            return x == destinationX + 1 && overlapsY
                    && (flags(x, destinationY, plane) & WALL_WEST) == 0;
        }

        return false;
    }

    private static int rotateAccessMask(int rotation, int accessMask) {
        int angle = rotation & 3;
        int mask = accessMask & 0xf;
        if (angle == 0 || mask == 0) {
            return mask;
        }
        return ((mask << angle) & 0xf) | (mask >>> (4 - angle));
    }

    private static boolean rectanglesOverlap(int sourceX, int sourceY,
                                             int destinationX, int destinationY,
                                             int sourceWidth, int sourceHeight,
                                             int destinationWidth,
                                             int destinationHeight) {
        return sourceX < destinationX + destinationWidth
                && sourceX + sourceWidth > destinationX
                && sourceY < destinationY + destinationHeight
                && sourceY + sourceHeight > destinationY;
    }

    private static int flags(int x, int y, int plane) {
        return WalkingCollisionMap.getTileFlags(x, y, plane);
    }
}
