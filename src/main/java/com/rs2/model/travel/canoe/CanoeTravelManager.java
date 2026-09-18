package com.rs2.model.travel.canoe;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.task.CycleEventHandler;

public final class CanoeTravelManager {
    static int CANOE_CONFIG_ID = 674;

    private static boolean canTravelToStation(Player player, CanoeStation station, int stationIndex) {
        if (station == CanoeStation.WILDERNESS) {
            CanoeTypeDefinition canoeType = CanoeTypeDefinition.values()[player.builtCanoeTypeConfigValue - 1];
            if (!canoeType.canEnterWilderness) {
                return false;
            }
        }
        return Math.abs(player.canoeStationIndex - stationIndex) <= CanoeTypeDefinition.values()[player.builtCanoeTypeConfigValue - 1].travelRange;
    }

    public static void handleCanoeTreeClick(Player player, int objectId, int x, int y) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(x, y, player.getPosition().getPlane());
        if (dynamicObject != null && dynamicObject.getWorldObject().getObjectId() != objectId) {
            return;
        }
        CanoeTreeDefinition treeDefinition = CanoeTreeDefinition.forObjectId(objectId);
        if (treeDefinition == null) {
            return;
        }
        if (player.builtCanoeTypeConfigValue != 0) {
            if (player.configStates[CANOE_CONFIG_ID] == player.builtCanoeTypeConfigValue << treeDefinition.getConfigShift()) {
                player.configStates[CanoeTravelManager.CANOE_CONFIG_ID] = (player.builtCanoeTypeConfigValue + 10) << treeDefinition.getConfigShift();
                player.packetSender.sendConfig(CANOE_CONFIG_ID, player.configStates[CANOE_CONFIG_ID]);
                return;
            }
            if (player.configStates[CANOE_CONFIG_ID] == (player.builtCanoeTypeConfigValue + 10) << treeDefinition.getConfigShift()) {
                int stationIndex = 0;
                CanoeStation[] stations = CanoeStation.values();
                int stationCount = stations.length;
                int index = 0;
                while (index < stationCount) {
                    CanoeStation station = stations[index];
                    if (stationIndex == player.canoeStationIndex) {
                        player.packetSender.setInterfaceVisible(station.destinationInterfaceIds[0], false);
                        player.packetSender.setInterfaceVisible(station.destinationInterfaceIds[1], true);
                    } else if (CanoeTravelManager.canTravelToStation(player, station, stationIndex)) {
                        player.packetSender.setInterfaceVisible(station.destinationInterfaceIds[0], true);
                        if (station != CanoeStation.WILDERNESS) {
                            player.packetSender.setInterfaceVisible(station.destinationInterfaceIds[1], false);
                        }
                    } else {
                        player.packetSender.setInterfaceVisible(station.destinationInterfaceIds[0], false);
                        if (station != CanoeStation.WILDERNESS) {
                            player.packetSender.setInterfaceVisible(station.destinationInterfaceIds[1], false);
                        }
                    }
                    ++stationIndex;
                    ++index;
                }
                player.packetSender.showInterface(18220);
                return;
            }
        }
        player.builtCanoeTypeConfigValue = 0;
        if (player.configStates[CANOE_CONFIG_ID] != 10 << treeDefinition.getConfigShift()) {
            if (!ServerSettings.woodcuttingEnabled) {
                player.packetSender.sendGameMessage("This skill is currently disabled.");
                return;
            }
            GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(player, 8);
            if (gatheringToolDefinition == null) {
                player.packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
                return;
            }
            if (player.getSkillManager().getCurrentLevels()[8] < treeDefinition.getRequiredLevel()) {
                player.packetSender.sendGameMessage("You need a Woodcutting level of " + treeDefinition.getRequiredLevel() + " to cut this tree.");
                return;
            }
            player.getUpdateState().setAnimation(gatheringToolDefinition.getGatherAnimationId(), 0);
            int actionSequence = player.nextActionSequence();
            player.setActiveCycleEvent(new CanoeTreeCutTask(player, actionSequence, treeDefinition, gatheringToolDefinition));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 4);
            return;
        }
        if (player.configStates[CANOE_CONFIG_ID] == 10 << treeDefinition.getConfigShift()) {
            CanoeTypeDefinition[] canoeTypes = CanoeTypeDefinition.values();
            int canoeTypeCount = canoeTypes.length;
            int index = 0;
            while (index < canoeTypeCount) {
                CanoeTypeDefinition canoeType = canoeTypes[index];
                if (canoeType != CanoeTypeDefinition.LOG) {
                    if (player.getSkillManager().getCurrentLevels()[8] >= canoeType.requiredLevel) {
                        player.packetSender.setInterfaceVisible(canoeType.levelInterfaceIds[0], false);
                        player.packetSender.setInterfaceVisible(canoeType.levelInterfaceIds[1], true);
                    } else {
                        player.packetSender.setInterfaceVisible(canoeType.levelInterfaceIds[0], true);
                        player.packetSender.setInterfaceVisible(canoeType.levelInterfaceIds[1], false);
                    }
                }
                ++index;
            }
            player.packetSender.showInterface(18178);
            player.canoeStationIndex = treeDefinition.getConfigShift() / 8;
            return;
        }
    }

    public static boolean handleBuildButton(Player player, int buttonId) {
        CanoeTypeDefinition[] canoeTypes = CanoeTypeDefinition.values();
        int canoeTypeCount = canoeTypes.length;
        int index = 0;
        while (index < canoeTypeCount) {
            CanoeTypeDefinition canoeType = canoeTypes[index];
            if (buttonId == canoeType.buttonId) {
                if (!ServerSettings.woodcuttingEnabled) {
                    player.packetSender.sendGameMessage("This skill is currently disabled.");
                } else {
                    GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(player, 8);
                    if (gatheringToolDefinition == null) {
                        player.packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
                    } else if (player.getSkillManager().getCurrentLevels()[8] < canoeType.requiredLevel) {
                        player.packetSender.sendGameMessage("You need a Woodcutting level of " + canoeType.requiredLevel + " to cut this tree.");
                    } else {
                        player.getUpdateState().setAnimation(gatheringToolDefinition.getCanoeAnimationId(), 0);
                        player.packetSender.sendSoundEffect(471, 1, 0);
                        int actionSequence = player.nextActionSequence();
                        player.setActiveCycleEvent(new CanoeBuildTask(player, actionSequence, canoeType, gatheringToolDefinition));
                        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 4);
                    }
                }
                player.packetSender.closeInterfaces();
                return true;
            }
            ++index;
        }
        return false;
    }

    public static boolean handleDestinationButton(Player player, int buttonId) {
        int stationIndex = 0;
        CanoeStation[] stations = CanoeStation.values();
        int stationCount = stations.length;
        int index = 0;
        while (index < stationCount) {
            CanoeStation station = stations[index];
            if (buttonId == station.buttonId) {
                if (CanoeTravelManager.canTravelToStation(player, station, stationIndex)) {
                    player.moveTo(station.destination);
                    player.packetSender.sendGameMessage("You arrive at the " + station.displayName + "." + (station == CanoeStation.WILDERNESS ? " There are no trees suitable to make a canoe." : ""));
                    player.packetSender.sendGameMessage("Your canoe sinks into the water after the hard journey.");
                    player.canoeStationIndex = 0;
                    player.builtCanoeTypeConfigValue = 0;
                    player.configStates[CanoeTravelManager.CANOE_CONFIG_ID] = 0;
                    player.packetSender.sendConfig(CANOE_CONFIG_ID, player.configStates[CANOE_CONFIG_ID]);
                    player.packetSender.closeInterfaces();
                } else {
                    player.packetSender.sendGameMessage("You cannot travel there!");
                }
                return true;
            }
            ++stationIndex;
            ++index;
        }
        return false;
    }
}
