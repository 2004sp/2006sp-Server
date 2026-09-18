/*
 * Source recovered from CFR output plus javap bytecode for handleGnomeGliderButton.
 */
package com.rs2.model.travel;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.task.TickTask;
import com.rs2.model.travel.GnomeGliderDestination;
import com.rs2.model.travel.GnomeGliderLandingTask;
import com.rs2.model.travel.GnomeGliderTravelTask;
import com.rs2.model.travel.HajedyCartRoute;
import com.rs2.model.travel.ShipRoute;
import com.rs2.model.travel.ShipTravelArrivalTask;
import com.rs2.model.travel.TravelBootstrap;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import java.util.ArrayList;

public class TravelManager {
    public static boolean handleGnomeGliderButton(Player player, int buttonId) {
        for (GnomeGliderDestination destination : GnomeGliderDestination.values()) {
            if (buttonId != destination.buttonId) {
                continue;
            }
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("travel gnome-glider button player=" + GameplayTrace.describe(player) + " buttonId=" + buttonId + " destination=" + destination);
            }
            if (!player.getInteractionTarget().isNpc()) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("travel gnome-glider blocked non-npc-target player=" + GameplayTrace.describe(player) + " buttonId=" + buttonId);
                }
                return false;
            }
            Npc gliderNpc = (Npc)player.getInteractionTarget();
            if (player.getInteractionTarget() != null) {
                player.packetSender.showInterface(802);
                int targetRouteIndex = destination.routeIndex;
                int currentRouteIndex = getCurrentGnomeGliderRouteIndex(gliderNpc);
                int routeConfig = getGnomeGliderRouteConfig(currentRouteIndex, targetRouteIndex);
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("travel gnome-glider route player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(gliderNpc) + " currentRoute=" + currentRouteIndex + " targetRoute=" + targetRouteIndex + " routeConfig=" + routeConfig + " destination=" + destination);
                }
                if (routeConfig <= 0) {
                    player.packetSender.sendGameMessage("You can't fly there from here.");
                } else if (routeConfig == 20) {
                    player.packetSender.sendGameMessage("You can't fly to the same place you are at.");
                } else if (routeConfig == 25) {
                    player.packetSender.sendGameMessage("You can't fly to that place yet.");
                } else {
                    player.packetSender.sendConfig(153, routeConfig);
                    player.setActionLocked(true);
                    CycleEventHandler.getInstance().schedule(player, new GnomeGliderTravelTask(player, destination), 3);
                    CycleEventHandler.getInstance().schedule(player, new GnomeGliderLandingTask(player), 4);
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("travel gnome-glider scheduled player=" + GameplayTrace.describe(player) + " destination=" + destination + " routeConfig=" + routeConfig);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static int getCurrentGnomeGliderRouteIndex(Npc gliderNpc) {
        int npcId = gliderNpc.getNpcId();
        int regionId = GameUtil.getRegionId(gliderNpc.getPosition().getX(), gliderNpc.getPosition().getY());
        if (npcId == 3810 || regionId == 11318) {
            return 0;
        }
        if (npcId == 3811 || regionId == 9782) {
            return 1;
        }
        if (npcId == 3809 || regionId == 13106) {
            return 3;
        }
        if (npcId == 3812 || regionId == 11822) {
            return 4;
        }
        if (npcId == 1800 || regionId == 10030) {
            return 5;
        }
        return 0;
    }

    private static int getGnomeGliderRouteConfig(int currentRouteIndex, int targetRouteIndex) {
        if (currentRouteIndex == targetRouteIndex) {
            return 20;
        }
        switch (currentRouteIndex) {
            case 0:
                return targetRouteIndex == 1 ? 2 : 0;
            case 1:
                if (targetRouteIndex == 0) {
                    return 1;
                }
                if (targetRouteIndex == 2) {
                    return 3;
                }
                if (targetRouteIndex == 3) {
                    return 4;
                }
                if (targetRouteIndex == 4) {
                    return 7;
                }
                return targetRouteIndex == 5 ? 25 : 0;
            case 3:
                return targetRouteIndex == 1 ? 5 : 0;
            case 4:
                return targetRouteIndex == 1 ? 6 : 0;
            case 5:
                return targetRouteIndex == 1 ? 11 : 0;
            default:
                return 0;
        }
    }

    public TravelManager(TravelBootstrap travelBootstrap) {
        new ArrayList();
    }

    public static boolean handleShipRoute(Player player, ShipRoute shipRoute) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel ship request player=" + GameplayTrace.describe((Player)player) + " route=" + shipRoute + " fare=" + shipRoute.fareCoins + " destination=" + shipRoute.destinationPosition);
        }
        int value = shipRoute.fareCoins;
        if (shipRoute == ShipRoute.RELLEKKA_TO_WATERBIRTH && ((Player)player).getQuestState(40) == 1) {
            value = 0;
        }
        if (value > 0) {
            ItemStack itemStack = new ItemStack(995, value);
            if (!((Player)player).getInventoryManager().containsItemStack(itemStack)) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("travel ship blocked no-fare player=" + GameplayTrace.describe((Player)player) + " route=" + shipRoute + " fare=" + value);
                }
                if (!((Player)player).botEnabled) {
                    ((Player)player).getDialogueManager().showPlayerOneLineDialogue("Sorry, I don't have enough coins for that.", 599);
                } else {
                    ((Player)player).currentBotTask.startWalkToBank((Player)player);
                }
                ((Player)player).getDialogueManager().finishDialogue();
                return false;
            }
            ((Player)player).getInventoryManager().removeItem(itemStack);
        }
        if (shipRoute == ShipRoute.PORT_SARIM_TO_ENTRANA && ((Player)player).hasRestrictedCombatEquipment()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("travel ship blocked restricted-equipment player=" + GameplayTrace.describe((Player)player) + " route=" + shipRoute);
            }
            DialogueManager.continueDialogue((Player)player, 657, 4, 0);
            return false;
        }
        ((Player)player).setActionLocked(true);
        Object value2 = ((Player)player).botEnabled || shipRoute.interfaceDestinationPosition == null ? shipRoute.destinationPosition.copy() : shipRoute.interfaceDestinationPosition.copy();
        ((Player)player).moveTo((Position)value2);
        if (shipRoute.ordinal() < 16) {
            value2 = player;
            ((Player)value2).packetSender.sendConfig(75, shipRoute.ordinal() + 1);
            value2 = player;
            ((Player)value2).packetSender.showInterface(3281);
        } else {
            value2 = player;
            ((Player)value2).packetSender.showInterface(11092);
        }
        value2 = player;
        ((Player)value2).packetSender.sendMusicJingle(shipRoute.jingleDelayTicks > 250 ? 225 : 298, shipRoute.jingleDelayTicks);
        value2 = player;
        ((Player)value2).packetSender.sendMinimapState(2);
        TickTask arrivalTask = new ShipTravelArrivalTask(shipRoute.arrivalDelayTicks, shipRoute, (Player)player);
        World.getTaskScheduler().schedule(arrivalTask);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel ship scheduled player=" + GameplayTrace.describe((Player)player) + " route=" + shipRoute + " destination=" + shipRoute.destinationPosition + " arrivalDelay=" + shipRoute.arrivalDelayTicks);
        }
        return true;
    }

    public static boolean handleHajedyCartRoute(Player player, HajedyCartRoute hajedyCartRoute) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel hajedy-cart request player=" + GameplayTrace.describe(player) + " route=" + hajedyCartRoute + " fare=" + hajedyCartRoute.fareCoins + " destination=" + hajedyCartRoute.destination);
        }
        if (hajedyCartRoute.fareCoins > 0) {
            ItemStack itemStack = new ItemStack(995, hajedyCartRoute.fareCoins);
            if (!player.getInventoryManager().containsItemStack(itemStack)) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("travel hajedy-cart blocked no-fare player=" + GameplayTrace.describe(player) + " route=" + hajedyCartRoute + " fare=" + hajedyCartRoute.fareCoins);
                }
                player.getDialogueManager().showPlayerOneLineDialogue("Sorry, I don't have enough coins for that.", 599);
                player.getDialogueManager().finishDialogue();
                return false;
            }
            player.getInventoryManager().removeItem(itemStack);
        }
        player.scheduleDelayedMove(hajedyCartRoute.destination.copy());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel hajedy-cart scheduled player=" + GameplayTrace.describe(player) + " route=" + hajedyCartRoute + " destination=" + hajedyCartRoute.destination);
        }
        return true;
    }
}
