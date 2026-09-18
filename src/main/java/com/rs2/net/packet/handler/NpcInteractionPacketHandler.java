package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.interaction.InteractionType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameplayTrace;

public final class NpcInteractionPacketHandler implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket packet) {
        if (player.isActionLocked()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("npc packet ignored action-locked player=" + GameplayTrace.describe(player) + " opcode=" + packet.getOpcode());
            }
            return;
        }
        player.packetSender.closeInterfaces();
        player.resetInteractionState();
        switch (packet.getOpcode()) {
            case 155: {
                int index = packet.getReader().readSignedShort(true, ByteOrder.LITTLE);
                Npc npc = getInteractableNpc(index);
                if (npc == null) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc first-option decoded missing-npc player=" + GameplayTrace.describe(player) + " index=" + index);
                        traceMissingNpcSlot("npc first-option", index);
                    }
                    break;
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("npc first-option decoded player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " index=" + index);
                }
                setNpcInteractionTarget(player, npc, index);
                if (ServerSettings.debugModeEnabled) {
                    player.packetSender.sendGameMessage("First click npc: " + player.getInteractionTargetId());
                }
                InteractionDispatcher.setCurrentInteractionType(InteractionType.FIRST_NPC);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 17: {
                int index = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
                Npc npc = getInteractableNpc(index);
                if (npc == null) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc second-option decoded missing-npc player=" + GameplayTrace.describe(player) + " index=" + index);
                        traceMissingNpcSlot("npc second-option", index);
                    }
                    break;
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("npc second-option decoded player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " index=" + index);
                }
                setNpcInteractionTarget(player, npc, index);
                if (ServerSettings.debugModeEnabled) {
                    player.packetSender.sendGameMessage("Second click npc: " + player.getInteractionTargetId());
                }
                InteractionDispatcher.setCurrentInteractionType(InteractionType.SECOND_NPC);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 21: {
                int index = packet.getReader().readSignedShort(true);
                Npc npc = getInteractableNpc(index);
                if (npc == null) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc third-option decoded missing-npc player=" + GameplayTrace.describe(player) + " index=" + index);
                        traceMissingNpcSlot("npc third-option", index);
                    }
                    break;
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("npc third-option decoded player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " index=" + index);
                }
                setNpcInteractionTarget(player, npc, index);
                if (ServerSettings.debugModeEnabled) {
                    player.packetSender.sendGameMessage("Third click npc: " + player.getInteractionTargetId());
                }
                InteractionDispatcher.setCurrentInteractionType(InteractionType.THIRD_NPC);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 230: {
                return;
            }
            case 72: {
                int index = packet.getReader().readSignedShort(ByteTransform.ADD);
                Npc npc = getInteractableNpc(index);
                if (npc == null) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc attack decoded missing-npc player=" + GameplayTrace.describe(player) + " index=" + index);
                        traceMissingNpcSlot("npc attack", index);
                    }
                    break;
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("npc attack decoded player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " index=" + index + " attackable=" + npc.getDefinition().isAttackable() + " doorSupport=" + npc.isDoorSupportNpc());
                }
                player.setQueuedCombatSpell(null);
                if (npc.getOwnerPlayer() != null && npc.getOwnerPlayer() != player) {
                    player.packetSender.sendGameMessage(String.valueOf(npc.getDefinition().getName()) + " is not interested in interacting with you right now.");
                    break;
                }
                if (npc.getDefinition().isAttackable() || npc.isDoorSupportNpc()) {
                    int npcId = npc.getDefinition().getId();
                    if (player.gangAffiliation != 2 || npcId != 643) {
                        if (GameplayTrace.enabled()) {
                            GameplayTrace.log("npc attack start-combat player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc));
                        }
                        CombatManager.startCombat(player, npc);
                        break;
                    }
                }
                player.packetSender.sendGameMessage("You cannot attack that npc!");
                return;
            }
            case 131: {
                player.getMovementQueue().clear();
                int index = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
                if (index < 0 || index >= World.getNpcs().length) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc magic decoded missing-npc-out-of-range player=" + GameplayTrace.describe(player) + " index=" + index + " npcSlots=" + World.getNpcs().length);
                    }
                    break;
                }
                int buttonId = packet.getReader().readSignedShort(ByteTransform.ADD);
                Npc npc = World.getNpcs()[index];
                if (npc == null || !npc.isInteractable()) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc magic decoded missing-npc player=" + GameplayTrace.describe(player) + " index=" + index + " buttonId=" + buttonId);
                        traceMissingNpcSlot("npc magic", index);
                    }
                    break;
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("npc magic decoded player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " index=" + index + " buttonId=" + buttonId + " attackable=" + npc.getDefinition().isAttackable());
                }
                if (npc.getOwnerPlayer() != null && npc.getOwnerPlayer() != player) {
                    player.packetSender.sendGameMessage(String.valueOf(npc.getDefinition().getName()) + " is not interested in interacting with you right now.");
                    break;
                }
                SpellDefinition spellDefinition = Spellbook.getSpellForButtonId(player, buttonId);
                if (spellDefinition != null) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc magic spell resolved player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " buttonId=" + buttonId + " spell=" + spellDefinition);
                    }
                    if (!player.isInMageArena()) {
                        if (spellDefinition == SpellDefinition.SARADOMIN_STRIKE && player.mageArenaSaradominStrikeCastsRemaining > 0) {
                            player.packetSender.sendGameMessage("You need to cast this spell " + player.mageArenaSaradominStrikeCastsRemaining + " times at Mage arena first.");
                            break;
                        }
                        if (spellDefinition == SpellDefinition.FLAMES_OF_ZAMORAK && player.mageArenaFlamesOfZamorakCastsRemaining > 0) {
                            player.packetSender.sendGameMessage("You need to cast this spell " + player.mageArenaFlamesOfZamorakCastsRemaining + " times at Mage arena first.");
                            break;
                        }
                        if (spellDefinition == SpellDefinition.CLAWS_OF_GUTHIX && player.mageArenaClawsOfGuthixCastsRemaining > 0) {
                            player.packetSender.sendGameMessage("You need to cast this spell " + player.mageArenaClawsOfGuthixCastsRemaining + " times at Mage arena first.");
                            break;
                        }
                    }
                    player.setQueuedCombatSpell(spellDefinition);
                    if (spellDefinition == SpellDefinition.TELEOTHER_CAMELOT || spellDefinition == SpellDefinition.TELEOTHER_FALADOR || spellDefinition == SpellDefinition.TELEOTHER_LUMBRIDGE || spellDefinition == SpellDefinition.TELE_BLOCK) {
                        player.packetSender.sendGameMessage("Nothing interesting happens.");
                        break;
                    }
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("npc magic start-combat player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " spell=" + spellDefinition);
                    }
                    CombatManager.startCombat(player, npc);
                    break;
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("npc magic spell unresolved player=" + GameplayTrace.describe(player) + " npc=" + GameplayTrace.describe(npc) + " buttonId=" + buttonId);
                }
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println("Magic id: " + buttonId);
                }
                return;
            }
            case 57: {
                int itemId = packet.getReader().readSignedShort(ByteTransform.ADD);
                int npcIndex = packet.getReader().readSignedShort(ByteTransform.ADD);
                int itemSlot = packet.getReader().readSignedShort(ByteOrder.LITTLE);
                if (itemSlot < 0 || itemSlot > 28) {
                    break;
                }
                ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(itemSlot);
                if (itemStack == null || itemStack.getId() != itemId) {
                    return;
                }
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println(String.valueOf(itemId) + " " + npcIndex + " " + itemSlot);
                }
                if (npcIndex < 0 || npcIndex >= World.getNpcs().length) {
                    return;
                }
                Npc npc = World.getNpcs()[npcIndex];
                if (npc == null || !npc.isInteractable()) {
                    return;
                }
                player.setSelectedItemSlot(itemSlot);
                player.setInteractionTargetIndex(npcIndex);
                player.setInteractionTarget(npc);
                player.setSelectedItemId(itemId);
                player.setInteractionTargetId(npc.getNpcId());
                player.setInteractionTargetX(npc.getPosition().getX());
                player.setInteractionTargetY(npc.getPosition().getY());
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                player.getUpdateState().setFaceEntity(npcIndex);
                player.setAttackRange(1);
                player.setMovementTarget(npc);
                InteractionDispatcher.setCurrentInteractionType(InteractionType.ITEM_ON_NPC);
                InteractionDispatcher.dispatchCurrentInteraction(player);
            }
        }
    }

    private static Npc getInteractableNpc(int index) {
        if (index < 0 || index >= World.getNpcs().length) {
            return null;
        }
        Npc npc = World.getNpcs()[index];
        if (npc == null || !npc.isInteractable()) {
            return null;
        }
        return npc;
    }

    private static void traceMissingNpcSlot(String action, int index) {
        Npc[] npcs = World.getNpcs();
        if (index < 0 || index >= npcs.length) {
            GameplayTrace.log(action + " slot-state out-of-range index=" + index + " npcSlots=" + npcs.length);
        } else {
            Npc npc = npcs[index];
            if (npc == null) {
                GameplayTrace.log(action + " slot-state null index=" + index);
            } else {
                GameplayTrace.log(action + " slot-state not-interactable index=" + index + " npc=" + GameplayTrace.describe(npc) + " active=" + npc.isActive() + " interactable=" + npc.isInteractable());
            }
        }
        traceNpcIds(action, new int[]{47, 86, 87, 170, 278, 284, 376, 377, 378, 380, 381, 510, 511, 950, 1800, 2436, 2437, 3809, 3810, 3811, 3812});
    }

    private static void traceNpcIds(String action, int[] npcIds) {
        Npc[] npcs = World.getNpcs();
        int matches = 0;
        for (int slot = 0; slot < npcs.length; ++slot) {
            Npc npc = npcs[slot];
            if (npc == null) {
                continue;
            }
            int npcId = npc.getNpcId();
            for (int id : npcIds) {
                if (npcId == id) {
                    ++matches;
                    GameplayTrace.log(action + " target-scan slot=" + slot + " npc=" + GameplayTrace.describe(npc) + " active=" + npc.isActive() + " interactable=" + npc.isInteractable());
                    break;
                }
            }
        }
        if (matches == 0) {
            GameplayTrace.log(action + " target-scan no-matches");
        }
    }

    private static void setNpcInteractionTarget(Player player, Npc npc, int index) {
        player.setInteractionTargetId(npc.getNpcId());
        player.setInteractionTargetX(npc.getPosition().getX());
        player.setInteractionTargetY(npc.getPosition().getY());
        player.setInteractionTargetPlane(player.getPosition().getPlane());
        player.setInteractionTargetIndex(index);
        player.getUpdateState().setFaceEntity(index);
        player.setAttackRange(1);
        player.setMovementTarget(npc);
    }
}
