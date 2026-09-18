package com.rs2.model.skill.runecrafting;

import com.rs2.ServerSettings;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemService;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.runecrafting.MysteriousRuinsTeleportTask;
import com.rs2.model.skill.runecrafting.RuneDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingAltarDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.PacketSender;

public class RunecraftingObjectHandler {
    private Player player;
    private int[] toolInteractionNpcIds = new int[]{519, 594};

    public static boolean handleAltarOrAbyssObject(Player player, int objectId) {
        switch (objectId) {
            case 2489: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.SOUL_RUNE);
                return true;
            }
            case 2490: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.BLOOD_RUNE);
                return true;
            }
            case 2482: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.FIRE_RUNE);
                return true;
            }
            case 2480: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.WATER_RUNE);
                return true;
            }
            case 2478: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.AIR_RUNE);
                return true;
            }
            case 2481: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.EARTH_RUNE);
                return true;
            }
            case 2479: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.MIND_RUNE);
                return true;
            }
            case 2483: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.BODY_RUNE);
                return true;
            }
            case 2488: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.DEATH_RUNE);
                return true;
            }
            case 2486: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.NATURE_RUNE);
                return true;
            }
            case 2487: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.CHAOS_RUNE);
                return true;
            }
            case 2485: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.LAW_RUNE);
                return true;
            }
            case 2484: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.COSMIC_RUNE);
                return true;
            }
            case 7141: {
                player.packetSender.sendGameMessage("This has been disabled temporarily.");
                return true;
            }
            case 7138: {
                player.packetSender.sendGameMessage("This has been disabled temporarily.");
                return true;
            }
            case 7133: {
                player.getTeleportManager().startDelayedTeleport(2400, 4835, 0);
                return true;
            }
            case 7132: {
                objectId = player.getQuestState(58) == 1 ? 1 : 0;
                if (objectId == 0) {
                    Object value = QuestDefinition.forId(58);
                    value = ((QuestDefinition)value).getName();
                    player.getDialogueManager().showOneLineStatement("You need to complete " + (String)value + " first.");
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getTeleportManager().startDelayedTeleport(2142, 4813, 0);
                }
                return true;
            }
            case 7129: {
                player.getTeleportManager().startDelayedTeleport(2574, 4849, 0);
                return true;
            }
            case 7130: {
                player.getTeleportManager().startDelayedTeleport(2655, 4830, 0);
                return true;
            }
            case 7131: {
                player.getTeleportManager().startDelayedTeleport(2523, 4826, 0);
                return true;
            }
            case 7140: {
                player.getTeleportManager().startDelayedTeleport(2793, 4828, 0);
                return true;
            }
            case 7139: {
                player.getTeleportManager().startDelayedTeleport(2841, 4829, 0);
                return true;
            }
            case 7137: {
                player.getTeleportManager().startDelayedTeleport(2726, 4832, 0);
                return true;
            }
            case 7136: {
                if (player.getPlayerRights() < 2) {
                    player.packetSender.sendGameMessage("This has been disabled temporarily.");
                } else {
                    player.getTeleportManager().startDelayedTeleport(2208, 4830, 0);
                }
                return true;
            }
            case 7135: {
                if (player.hasRestrictedCombatEquipment()) {
                    player.getDialogueManager().showOneLineStatement("You cannot bring combat items to Entrana!");
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getTeleportManager().startDelayedTeleport(2464, 4818, 0);
                }
                return true;
            }
            case 7134: {
                player.getTeleportManager().startDelayedTeleport(2281, 4837, 0);
                return true;
            }
        }
        return false;
    }

    public static boolean handleRuinsOrPortalObject(Player player, int objectId) {
        RunecraftingAltarDefinition ruinsDefinition = null;
        for (RunecraftingAltarDefinition candidate : RunecraftingAltarDefinition.values()) {
            if (objectId == candidate.ruinsObjectId) {
                ruinsDefinition = candidate;
                break;
            }
        }
        RunecraftingAltarDefinition portalDefinition = null;
        for (RunecraftingAltarDefinition candidate : RunecraftingAltarDefinition.values()) {
            if (objectId == candidate.portalObjectId) {
                portalDefinition = candidate;
                break;
            }
        }
        if (ruinsDefinition == null && portalDefinition == null) {
            return false;
        }
        if (!ServerSettings.runecraftingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (ruinsDefinition != null) {
            if (ruinsDefinition.membersOnly && !player.isMember()) {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return true;
            }
            if (ruinsDefinition.membersOnly && ServerSettings.freeToPlayWorld) {
                player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                return true;
            }
        }
        if (player.getQuestState(14) != 1) {
            QuestDefinition questDefinition = QuestDefinition.forId(14);
            String name = questDefinition.getName();
            player.packetSender.sendGameMessage("You need to complete " + name + " to do this.");
            return true;
        }
        if (ruinsDefinition != null) {
            player.moveTo(ruinsDefinition.altarPosition);
            player.packetSender.sendGameMessage("You feel a powerful force take hold of you...");
            return true;
        }
        if (portalDefinition != null) {
            player.moveTo(portalDefinition.ruinsPosition);
            player.packetSender.sendGameMessage("You step through the portal...");
            return true;
        }
        return false;
    }

    public static boolean handleTalismanOnMysteriousRuins(Player player, int value3, int value22) {
        RunecraftingAltarDefinition altarDefinition = null;
        for (RunecraftingAltarDefinition candidate : RunecraftingAltarDefinition.values()) {
            if (value3 == candidate.talismanItemId && value22 == candidate.ruinsObjectId) {
                altarDefinition = candidate;
                break;
            }
        }
        if (altarDefinition == null) {
            return false;
        }
        if (!ServerSettings.runecraftingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (altarDefinition.membersOnly && !player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return true;
        }
        if (altarDefinition.membersOnly && ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return true;
        }
        if (player.getQuestState(14) != 1) {
            QuestDefinition questDefinition = QuestDefinition.forId(14);
            String name = questDefinition.getName();
            player.packetSender.sendGameMessage("You need to complete " + name + " to do this.");
            return true;
        }
        PacketSender packetSender = player.packetSender;
        StringBuilder stringBuilder = new StringBuilder("You hold the ");
        ItemService.getInstance();
        packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(altarDefinition.talismanItemId)).append(" towards the mysterious ruins.").toString());
        player.getUpdateState().setAnimation(827);
        player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(player, new MysteriousRuinsTeleportTask(player, altarDefinition), 2);
        return true;
    }

    public RunecraftingObjectHandler(Player player) {
        this.player = player;
    }

    public boolean handleToolOnNpc(int npcId, int value3) {
        int initialValue = -1;
        int[] integerValues = this.toolInteractionNpcIds;
        int length = this.toolInteractionNpcIds.length;
        int index = 0;
        while (index < length) {
            int value2 = integerValues[index];
            if (npcId == value2) {
                initialValue = value2;
                break;
            }
            ++index;
        }
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.forBrokenToolItemId(value3);
        if (initialValue != -1 && gatheringToolDefinition != null) {
            this.player.temporaryActionValue = value3;
            this.player.sharedActionValue = initialValue;
            DialogueManager.startDialogue(this.player, 10088);
            return true;
        }
        return false;
    }
}

