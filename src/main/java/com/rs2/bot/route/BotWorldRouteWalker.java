package com.rs2.bot.route;

import com.rs2.ServerSettings;
import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.route.BotWorldRoute;
import com.rs2.bot.route.BotWorldRouteChoice;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Iterator;

public final class BotWorldRouteWalker {
    private static int routeDistanceThreshold = 25;
    private static void findConnectingWorldRoute(Player player, ArrayList arrayList) {
        BotTaskDefinition botTaskDefinition = player.currentBotTask;
        Position targetPosition = botTaskDefinition.getStartPosition();
        BotWorldRouteChoice bestStartingChoice = null;
        BotWorldRouteChoice bestConnectingChoice = null;
        double bestScore = 0.0;
        int currentDistance = GameUtil.getDistance(player.getPosition(), targetPosition);
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            BotWorldRouteChoice currentChoice = (BotWorldRouteChoice)iterator.next();
            ArrayList<BotWorldRouteChoice> candidateChoices = new ArrayList<BotWorldRouteChoice>();
            ArrayList<BotWorldRouteChoice> improvingChoices = new ArrayList<BotWorldRouteChoice>();
            Position routeEndPosition = currentChoice.reversed ? currentChoice.route.getStartPosition() : currentChoice.route.getEndPosition();
            BotWorldRoute[] botWorldRouteArray = BotWorldRoute.values();
            int length = botWorldRouteArray.length;
            int index = 0;
            while (index < length) {
                BotWorldRoute route = botWorldRouteArray[index];
                if (route != currentChoice.route) {
                    int startDistance = GameUtil.getDistance(route.getStartPosition(), routeEndPosition);
                    int endDistance = GameUtil.getDistance(route.getEndPosition(), routeEndPosition);
                    if (startDistance < endDistance) {
                        if (startDistance <= routeDistanceThreshold) {
                            candidateChoices.add(new BotWorldRouteChoice(route, false));
                        }
                    } else if (endDistance <= routeDistanceThreshold) {
                        candidateChoices.add(new BotWorldRouteChoice(route, true));
                    }
                }
                ++index;
            }
            if (candidateChoices.size() > 1) {
                int currentRouteIndex = -1;
                index = 0;
                for (BotWorldRouteChoice botWorldRouteChoice : candidateChoices) {
                    if (player.currentWorldRouteChoice != null && player.currentWorldRouteChoice.route == botWorldRouteChoice.route) {
                        currentRouteIndex = index;
                    }
                    ++index;
                }
                if (currentRouteIndex != -1) {
                    candidateChoices.remove(currentRouteIndex);
                }
            }
            if (candidateChoices.size() == 1) {
                BotWorldRouteChoice candidateChoice = (BotWorldRouteChoice)candidateChoices.get(0);
                Position candidateEndPosition = candidateChoice.reversed ? candidateChoice.route.getStartPosition() : candidateChoice.route.getEndPosition();
                if (GameUtil.getDistance(candidateEndPosition, targetPosition) < currentDistance) {
                    improvingChoices.add(candidateChoice);
                }
            } else {
                for (BotWorldRouteChoice botWorldRouteChoice : candidateChoices) {
                    if (player.currentWorldRouteChoice != null && player.currentWorldRouteChoice.route == botWorldRouteChoice.route) continue;
                    Position candidateEndPosition = botWorldRouteChoice.reversed ? botWorldRouteChoice.route.getStartPosition() : botWorldRouteChoice.route.getEndPosition();
                    int candidateDistance = GameUtil.getDistance(candidateEndPosition, targetPosition);
                    if (candidateDistance >= currentDistance) continue;
                    improvingChoices.add(botWorldRouteChoice);
                }
            }
            for (BotWorldRouteChoice botWorldRouteChoice : improvingChoices) {
                Position candidateEndPosition = botWorldRouteChoice.reversed ? botWorldRouteChoice.route.getStartPosition() : botWorldRouteChoice.route.getEndPosition();
                int candidateDistance = GameUtil.getDistance(candidateEndPosition, targetPosition);
                double improvement = currentDistance - candidateDistance;
                double currentRouteDistance = currentChoice.route.getDistance();
                double candidateRouteDistance = botWorldRouteChoice.route.getDistance();
                double score = improvement / (candidateRouteDistance + currentRouteDistance);
                if (score < bestScore) continue;
                bestScore = score;
                bestStartingChoice = currentChoice;
                bestConnectingChoice = botWorldRouteChoice;
            }
        }
        if (bestStartingChoice != null && bestConnectingChoice != null) {
            if (!player.getMovementQueue().isRunning() && player.getRunEnergyPercent() >= 80) {
                player.getMovementQueue().setRunning(true);
            }
            BotWorldRouteWalker.startWorldRoute(player, bestStartingChoice);
            return;
        }
        if (ServerSettings.debugModeEnabled) {
            System.out.println("Error! Could not find suitable overworld walk path for player: " + player.getUsername() + " at: " + player.getPosition() + " " + player.currentBotTask);
        }
    }

    public static boolean findWorldRoute(Player player) {
        if (player.currentBotTask == null) {
            return true;
        }
        BotTaskDefinition botTaskDefinition = player.currentBotTask;
        Position targetPosition = botTaskDefinition.getStartPosition();
        int currentDistance = GameUtil.getDistance(player.getPosition(), targetPosition);
        if (currentDistance <= routeDistanceThreshold) {
            player.currentWorldRouteChoice = null;
            GameplayHelper.startBotTaskRoute(player);
            return true;
        }
        ArrayList<BotWorldRouteChoice> nearbyChoices = new ArrayList<BotWorldRouteChoice>();
        BotWorldRouteChoice bestChoice = null;
        BotWorldRoute[] botWorldRouteArray = BotWorldRoute.values();
        int length = botWorldRouteArray.length;
        int index = 0;
        while (index < length) {
            BotWorldRoute botWorldRoute = botWorldRouteArray[index];
            int startDistance = GameUtil.getDistance(botWorldRoute.getStartPosition(), player.getPosition());
            int endDistance = GameUtil.getDistance(botWorldRoute.getEndPosition(), player.getPosition());
            if (startDistance < endDistance) {
                if (startDistance <= routeDistanceThreshold) {
                    nearbyChoices.add(new BotWorldRouteChoice(botWorldRoute, false));
                }
            } else if (endDistance <= routeDistanceThreshold) {
                nearbyChoices.add(new BotWorldRouteChoice(botWorldRoute, true));
            }
            ++index;
        }
        ArrayList<BotWorldRouteChoice> improvingChoices = new ArrayList<BotWorldRouteChoice>();
        index = 0;
        if (nearbyChoices.size() > 1) {
            length = -1;
            int index2 = 0;
            for (BotWorldRouteChoice botWorldRouteChoice : nearbyChoices) {
                if (player.currentWorldRouteChoice != null && player.currentWorldRouteChoice.route == botWorldRouteChoice.route) {
                    length = index2;
                }
                ++index2;
            }
            if (length != -1) {
                nearbyChoices.remove(length);
            }
        }
        if (nearbyChoices.size() == 1) {
            bestChoice = (BotWorldRouteChoice)nearbyChoices.get(0);
            Position position = bestChoice.reversed ? bestChoice.route.getStartPosition() : bestChoice.route.getEndPosition();
            index = GameUtil.getDistance(position, targetPosition);
        } else {
            for (BotWorldRouteChoice botWorldRouteChoice : nearbyChoices) {
                if (player.currentWorldRouteChoice != null && player.currentWorldRouteChoice.route == botWorldRouteChoice.route) continue;
                Position position = botWorldRouteChoice.reversed ? botWorldRouteChoice.route.getStartPosition() : botWorldRouteChoice.route.getEndPosition();
                int candidateDistance = GameUtil.getDistance(position, targetPosition);
                if (candidateDistance >= currentDistance) continue;
                improvingChoices.add(botWorldRouteChoice);
            }
            double bestScore = 0.0;
            Iterator<BotWorldRouteChoice> iterator = improvingChoices.iterator();
            while (iterator.hasNext()) {
                BotWorldRouteChoice candidateChoice = iterator.next();
                Position position = candidateChoice.reversed ? candidateChoice.route.getStartPosition() : candidateChoice.route.getEndPosition();
                int candidateDistance = GameUtil.getDistance(position, targetPosition);
                double improvement = currentDistance - candidateDistance;
                double routeDistance = candidateChoice.route.getDistance();
                double score = improvement / routeDistance;
                if (score < bestScore) continue;
                bestScore = score;
                bestChoice = candidateChoice;
                index = candidateDistance;
            }
        }
        if (bestChoice != null) {
            if (index <= routeDistanceThreshold) {
                player.botTaskState = "world walk finish";
                player.getMovementQueue().setRunning(false);
            }
            if (!player.getMovementQueue().isRunning() && player.getRunEnergyPercent() >= 80) {
                player.getMovementQueue().setRunning(true);
            }
            BotWorldRouteWalker.startWorldRoute(player, bestChoice);
        } else if (nearbyChoices.size() > 0) {
            BotWorldRouteWalker.findConnectingWorldRoute(player, nearbyChoices);
        } else {
            if (ServerSettings.debugModeEnabled) {
            System.out.println("Error! Could not find suitable overworld walk path for player: " + player.getUsername() + " at: " + player.getPosition() + " " + player.currentBotTask);
        }
            return false;
        }
        return true;
    }

    public static int getRouteIndex(BotWorldRouteChoice botWorldRouteChoice) {
        int index = 0;
        while (index < BotWorldRoute.values().length) {
            BotWorldRoute botWorldRoute = BotWorldRoute.values()[index];
            Object value = botWorldRoute;
            value = botWorldRouteChoice;
            if (botWorldRoute == ((BotWorldRouteChoice)value).route) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public static void continueWorldRoute(Player player, BotWorldRouteChoice botWorldRouteChoice2) {
        BotWorldRouteChoice botWorldRouteChoice = botWorldRouteChoice2;
        BotWorldRoute botWorldRoute = botWorldRouteChoice.route;
        player.currentWorldRouteChoice = botWorldRouteChoice2;
        if (botWorldRoute.getRoute() != null) {
            botWorldRouteChoice = botWorldRouteChoice2;
            player.currentBotRoute = botWorldRouteChoice.reversed ? botWorldRoute.getRoute().reversed() : botWorldRoute.getRoute();
        } else {
            botWorldRouteChoice = botWorldRouteChoice2;
            BotRoute botRoute = player.currentBotRoute = botWorldRouteChoice.reversed ? botWorldRoute.getSegments()[player.botPathSegmentIndex].reversed() : botWorldRoute.getSegments()[player.botPathSegmentIndex];
        }
        if (botWorldRoute.getRouteNpcId() != -1) {
            player.botTargetNpcId = botWorldRoute.getRouteNpcId();
        }
        Position position = player.currentBotRoute.waypoints[player.botPathWaypointIndex];
        int distance = GameUtil.getDistance(player.getPosition(), position);
        if (distance > routeDistanceThreshold) {
            if (ServerSettings.debugModeEnabled) {
                System.out.println("Detected possibly frozen bot: " + player.getUsername() + " at: " + player.getPosition() + ", trying to apply fix.");
            }
            BotWorldRouteWalker.findWorldRoute(player);
            return;
        }
        player.continueBotRoute();
    }

    private static void startWorldRoute(Player player, BotWorldRouteChoice botWorldRouteChoice) {
        player.botEnabled = true;
        player.botPathWaypointIndex = 0;
        BotWorldRouteChoice botWorldRouteChoice2 = botWorldRouteChoice;
        BotWorldRoute botWorldRoute = botWorldRouteChoice2.route;
        player.currentWorldRouteChoice = botWorldRouteChoice;
        player.botPathSegmentIndex = 0;
        if (botWorldRoute.getRoute() != null) {
            botWorldRouteChoice2 = botWorldRouteChoice;
            player.currentBotRoute = botWorldRouteChoice2.reversed ? botWorldRoute.getRoute().reversed() : botWorldRoute.getRoute();
            player.botTaskState = "world walk find";
        } else {
            botWorldRouteChoice2 = botWorldRouteChoice;
            player.botPathSegmentIndex = botWorldRouteChoice2.reversed ? botWorldRoute.getSegments().length - 1 : 0;
            botWorldRouteChoice2 = botWorldRouteChoice;
            player.currentBotRoute = botWorldRouteChoice2.reversed ? botWorldRoute.getSegments()[player.botPathSegmentIndex].reversed() : botWorldRoute.getSegments()[player.botPathSegmentIndex];
            player.botTaskState = "world walk towards";
        }
        if (botWorldRoute.getRouteNpcId() != -1) {
            player.botTargetNpcId = botWorldRoute.getRouteNpcId();
        }
        player.continueBotRoute();
    }

    public static void advanceRouteSegment(Player player) {
        BotWorldRouteChoice botWorldRouteChoice = player.currentWorldRouteChoice;
        BotWorldRoute botWorldRoute = botWorldRouteChoice.route;
        botWorldRouteChoice = player.currentWorldRouteChoice;
        if (!botWorldRouteChoice.reversed) {
            ++player.botPathSegmentIndex;
            player.currentBotRoute = botWorldRoute.getSegments()[player.botPathSegmentIndex];
            player.botPathWaypointIndex = 0;
            if (player.botPathSegmentIndex == botWorldRoute.getSegments().length - 1) {
                player.botTaskState = "world walk find";
                return;
            }
        } else {
            --player.botPathSegmentIndex;
            player.currentBotRoute = botWorldRoute.getSegments()[player.botPathSegmentIndex].reversed();
            player.botPathWaypointIndex = 0;
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "world walk find";
            }
        }
    }
}
