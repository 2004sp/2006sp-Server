package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.ClientPackets;
import com.rs2.net.packet.SpellWidgets;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;

public final class PlayerInteractionPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        if (player.isActionLocked()) {
            return;
        }
        if (ServerSettings.clientBuild == 443
                && (ClientPackets.isPlayerOption(incomingPacket.getOpcode())
                || incomingPacket.getOpcode() == ClientPackets.ITEM_ON_PLAYER
                || incomingPacket.getOpcode() == ClientPackets.SPELL_ON_PLAYER)) {
            handleRevision443(player, incomingPacket);
            return;
        }
        player.packetSender.closeInterfaces();
        player.resetInteractionState();
        switch (incomingPacket.getOpcode()) {
            case 73:
            case 136:
            case 139: {
                int targetIndex = incomingPacket.getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (targetIndex < 0 || targetIndex > World.getPlayers().length) {
                    return;
                }
                Player targetPlayer = World.getPlayers()[targetIndex];
                if (targetPlayer == null || !GameUtil.isWithinDistance(player.getPosition(), targetPlayer.getPosition(), 15)) {
                    return;
                }
                int actionSequence = player.nextActionSequence();
                if (targetPlayer.getTradePartner() == player) {
                    GameplayHelper.declineTrade(player);
                } else if (targetPlayer.getOpenInterfaceId() > 0) {
                    player.packetSender.sendGameMessage("This player is busy.");
                    return;
                }
                player.setInteractionTarget(targetPlayer);
                player.setAttackRange(1);
                player.setMovementTarget(targetPlayer);
                World.scheduleTickTask(new TradeRequestTask(this, 1, targetPlayer, player, actionSequence));
                return;
            }
            case 153: {
                int targetIndex = incomingPacket.getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (targetIndex < 0 || targetIndex > World.getPlayers().length) {
                    return;
                }
                Player targetPlayer = World.getPlayers()[targetIndex];
                if (targetPlayer == null || !GameUtil.isWithinDistance(player.getPosition(), targetPlayer.getPosition(), 15)) {
                    return;
                }
                player.getUpdateState().setFaceEntity(targetPlayer.getEncodedIndex());
                player.setAttackRange(1);
                player.setMovementTarget(targetPlayer);
                return;
            }
            case 128: {
                int targetIndex = incomingPacket.getReader().readSignedShort();
                if (targetIndex < 0 || targetIndex > World.getPlayers().length) {
                    return;
                }
                Player targetPlayer = World.getPlayers()[targetIndex];
                if (targetPlayer == null || !isWithinCombatInteractionRange(player, targetPlayer)) {
                    return;
                }
                int actionSequence = player.nextActionSequence();
                player.setQueuedCombatSpell(null);
                player.getUpdateState().setFaceEntity(targetPlayer.getEncodedIndex());

                boolean playerInCastleWars = CastleWarsManager.isInGame(player);
                boolean targetInCastleWars = CastleWarsManager.isInGame(targetPlayer);
                if (playerInCastleWars || targetInCastleWars) {
                    if (!playerInCastleWars || !targetInCastleWars
                            || !CastleWarsManager.areOpponents(player, targetPlayer)) {
                        player.packetSender.sendGameMessage("That player is not your Castle Wars opponent.");
                        return;
                    }
                    CombatManager.startCombat(player, targetPlayer);
                    return;
                }

                if (!player.isInDuelArena() && !player.isInWilderness()) {
                    if (ServerSettings.duelingDisabled) {
                        player.packetSender.sendGameMessage("This feature is currently disabled.");
                        return;
                    }
                    if (targetPlayer.getOpenInterfaceId() > 0) {
                        player.packetSender.sendGameMessage("This player is busy.");
                        return;
                    }
                    player.setInteractionTarget(targetPlayer);
                    player.setAttackRange(1);
                    player.setMovementTarget(targetPlayer);
                    World.scheduleTickTask(new DuelRequestTask(this, 1, targetPlayer, player, actionSequence));
                    return;
                }
                CombatManager.startCombat(player, targetPlayer);
                return;
            }
            case 249: {
                int targetIndex = incomingPacket.getReader().readSignedShort(true, ByteTransform.ADD);
                if (targetIndex < 0 || targetIndex > World.getPlayers().length) {
                    return;
                }
                Player targetPlayer = World.getPlayers()[targetIndex];
                if (targetPlayer == null || !isWithinCombatInteractionRange(player, targetPlayer)) {
                    return;
                }
                int spellButtonId = incomingPacket.getReader().readSignedShort(true, ByteOrder.LITTLE);
                SpellDefinition spellDefinition = Spellbook.getSpellForButtonId(player, spellButtonId);
                if (spellDefinition == null) {
                    if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                        System.out.println("Magic ID: " + spellButtonId);
                    }
                    return;
                }
                if (!player.isInMageArena()) {
                    if (spellDefinition == SpellDefinition.SARADOMIN_STRIKE && player.mageArenaSaradominStrikeCastsRemaining > 0) {
                        player.packetSender.sendGameMessage("You need to cast this spell " + player.mageArenaSaradominStrikeCastsRemaining + " times at Mage arena first.");
                        return;
                    }
                    if (spellDefinition == SpellDefinition.FLAMES_OF_ZAMORAK && player.mageArenaFlamesOfZamorakCastsRemaining > 0) {
                        player.packetSender.sendGameMessage("You need to cast this spell " + player.mageArenaFlamesOfZamorakCastsRemaining + " times at Mage arena first.");
                        return;
                    }
                    if (spellDefinition == SpellDefinition.CLAWS_OF_GUTHIX && player.mageArenaClawsOfGuthixCastsRemaining > 0) {
                        player.packetSender.sendGameMessage("You need to cast this spell " + player.mageArenaClawsOfGuthixCastsRemaining + " times at Mage arena first.");
                        return;
                    }
                }
                player.setQueuedCombatSpell(spellDefinition);
                if (spellDefinition != SpellDefinition.TELEOTHER_CAMELOT && spellDefinition != SpellDefinition.TELEOTHER_FALADOR && spellDefinition != SpellDefinition.TELEOTHER_LUMBRIDGE) {
                    CombatManager.startCombat(player, targetPlayer);
                    return;
                }
                MagicSpellAction.castTeleotherSpell(player, targetPlayer, spellDefinition);
                return;
            }
            case 14: {
                int targetIndex = incomingPacket.getReader().readSignedShort();
                if (targetIndex < 0 || targetIndex > World.getPlayers().length) {
                    return;
                }
                Player targetPlayer = World.getPlayers()[targetIndex];
                if (targetPlayer == null || !GameUtil.isWithinDistance(player.getPosition(), targetPlayer.getPosition(), 15)) {
                    return;
                }
                int inventorySlot = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
                ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(inventorySlot);
                if (itemStack == null) {
                    return;
                }
                int actionSequence = player.nextActionSequence();
                if (targetPlayer.getOpenInterfaceId() > 0) {
                    player.packetSender.sendGameMessage("This player is busy.");
                    return;
                }
                player.setInteractionTarget(targetPlayer);
                player.setAttackRange(1);
                player.setMovementTarget(targetPlayer);
                World.scheduleTickTask(new ItemOnPlayerTask(this, 1, targetPlayer, player, actionSequence, itemStack, inventorySlot));
                return;
            }
            case 39: {
                int targetIndex = incomingPacket.getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (targetIndex < 0 || targetIndex > World.getPlayers().length) {
                    return;
                }
                Player targetPlayer = World.getPlayers()[targetIndex];
                if (targetPlayer == null || !GameUtil.isWithinDistance(player.getPosition(), targetPlayer.getPosition(), 15)) {
                    return;
                }
                player.setInteractionTarget(targetPlayer);
                player.setAttackRange(1);
                player.setMovementTarget(targetPlayer);
                int actionSequence = player.nextActionSequence();
                World.scheduleTickTask(new FollowPlayerTask(this, 1, targetPlayer, player, actionSequence));
                return;
            }
        }
    }

    private static void handleRevision443(Player player, IncomingPacket packet) {
        int opcode = packet.getOpcode();
        int option = ClientPackets.getPlayerOption(opcode);
        if (option != -1) {
            int targetIndex;
            switch (option) {
                case 1:
                case 2:
                case 4:
                    targetIndex = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
                    break;
                case 3:
                    targetIndex = packet.getReader().readSignedShort(ByteTransform.ADD) & 0xFFFF;
                    break;
                default:
                    targetIndex = packet.getReader().readSignedShort() & 0xFFFF;
                    break;
            }
            Player target = targetIndex < World.getPlayers().length ? World.getPlayers()[targetIndex] : null;
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 player-option-" + option + " decoded player="
                        + GameplayTrace.describe(player) + " targetIndex=" + targetIndex
                        + " target=" + (target == null ? "null" : GameplayTrace.describe(target)));
            }
            if (player.isInteractionDebugEnabled()) {
                player.packetSender.sendGameMessage("443 player option " + option
                        + " decoded: target=" + targetIndex);
            }
            if (target == null || target == player || !isWithinCombatInteractionRange(player, target)) return;
            String label = player.playerOptionTextCache[option - 1];
            if (label == null || "null".equalsIgnoreCase(label)) return;
            player.packetSender.closeInterfaces();
            player.resetInteractionState();
            if ("Follow".equalsIgnoreCase(label)) {
                player.setInteractionTarget(target);
                player.setAttackRange(1);
                player.setMovementTarget(target);
                World.scheduleTickTask(new FollowPlayerTask(null, 1, target, player, player.nextActionSequence()));
            } else if ("Trade with".equalsIgnoreCase(label)) {
                if (target.getTradePartner() == player) GameplayHelper.declineTrade(player);
                else if (target.getOpenInterfaceId() > 0) {
                    player.packetSender.sendGameMessage("This player is busy.");
                    return;
                }
                player.setInteractionTarget(target);
                player.setAttackRange(1);
                player.setMovementTarget(target);
                World.scheduleTickTask(new TradeRequestTask(null, 1, target, player, player.nextActionSequence()));
            } else if ("Attack".equalsIgnoreCase(label) || "Challenge".equalsIgnoreCase(label)) {
                player.setQueuedCombatSpell(null);
                player.getUpdateState().setFaceEntity(target.getEncodedIndex());
                boolean castleWars = CastleWarsManager.isInGame(player) || CastleWarsManager.isInGame(target);
                if (castleWars) {
                    if (!CastleWarsManager.areOpponents(player, target)) {
                        player.packetSender.sendGameMessage("That player is not your Castle Wars opponent.");
                        return;
                    }
                } else if (!player.isInDuelArena() && !player.isInWilderness()) {
                    if (ServerSettings.duelingDisabled) {
                        player.packetSender.sendGameMessage("This feature is currently disabled.");
                        return;
                    }
                    if (target.getOpenInterfaceId() > 0) {
                        player.packetSender.sendGameMessage("This player is busy.");
                        return;
                    }
                    player.setInteractionTarget(target);
                    player.setAttackRange(1);
                    player.setMovementTarget(target);
                    World.scheduleTickTask(new DuelRequestTask(null, 1, target, player, player.nextActionSequence()));
                    return;
                }
                CombatManager.startCombat(player, target);
            }
            return;
        }

        if (opcode == ClientPackets.ITEM_ON_PLAYER) {
            int packedInterface = ClientPackets.readIntInverseMiddle(packet.getReader());
            int inventorySlot = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
            int targetIndex = packet.getReader().readSignedShort(ByteOrder.LITTLE) & 0xFFFF;
            int itemId = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
            if (targetIndex >= World.getPlayers().length || inventorySlot >= 28) return;
            Player target = World.getPlayers()[targetIndex];
            if (target == null || !GameUtil.isWithinDistance(player.getPosition(), target.getPosition(), 15)) return;
            ItemStack item = player.getInventoryManager().getContainer().getItemAt(inventorySlot);
            if (item == null || item.getId() != itemId) return;
            player.packetSender.closeInterfaces();
            player.resetInteractionState();
            player.setSelectedItemInterfaceId(packedInterface);
            player.setSelectedItemSlot(inventorySlot);
            player.setSelectedItemId(itemId);
            int actionSequence = player.nextActionSequence();
            if (target.getOpenInterfaceId() > 0) {
                player.packetSender.sendGameMessage("This player is busy.");
                return;
            }
            player.setInteractionTarget(target);
            player.setAttackRange(1);
            player.setMovementTarget(target);
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 item-on-player decoded player=" + GameplayTrace.describe(player)
                        + " target=" + GameplayTrace.describe(target) + " targetIndex=" + targetIndex
                        + " itemId=" + itemId + " slot=" + inventorySlot
                        + " interface=" + packedInterface);
            }
            World.scheduleTickTask(new ItemOnPlayerTask(null, 1, target, player,
                    actionSequence, item, inventorySlot));
            return;
        }

        if (opcode == ClientPackets.SPELL_ON_PLAYER) {
            int targetIndex = packet.getReader().readSignedShort(ByteTransform.ADD) & 0xFFFF;
            int spellChild = packet.getReader().readSignedShort(ByteTransform.ADD) & 0xFFFF;
            int spellInterface = ClientPackets.readIntLittle(packet.getReader());
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 spell-on-player decoded player=" + GameplayTrace.describe(player)
                        + " targetIndex=" + targetIndex + " spell=" + spellInterface + ":" + spellChild);
            }
            if (player.isInteractionDebugEnabled()) {
                player.packetSender.sendGameMessage("443 spell-on-player decoded: spell="
                        + spellInterface + ":" + spellChild + " target=" + targetIndex);
            }
            if (!SpellWidgets.isSpellWidget(spellInterface)
                    || targetIndex >= World.getPlayers().length) return;
            Player target = World.getPlayers()[targetIndex];
            if (target == null || target == player
                    || !isWithinCombatInteractionRange(player, target)) return;
            SpellDefinition spell = Spellbook.getSpellForButtonId(player, spellChild);
            if (spell == null) return;
            if (!player.isInMageArena()) {
                if (spell == SpellDefinition.SARADOMIN_STRIKE && player.mageArenaSaradominStrikeCastsRemaining > 0
                        || spell == SpellDefinition.FLAMES_OF_ZAMORAK && player.mageArenaFlamesOfZamorakCastsRemaining > 0
                        || spell == SpellDefinition.CLAWS_OF_GUTHIX && player.mageArenaClawsOfGuthixCastsRemaining > 0) {
                    player.packetSender.sendGameMessage("You need to cast this spell at Mage arena first.");
                    return;
                }
            }
            player.packetSender.closeInterfaces();
            player.resetInteractionState();
            player.setQueuedCombatSpell(spell);
            if (spell == SpellDefinition.TELEOTHER_CAMELOT
                    || spell == SpellDefinition.TELEOTHER_FALADOR
                    || spell == SpellDefinition.TELEOTHER_LUMBRIDGE) {
                MagicSpellAction.castTeleotherSpell(player, target, spell);
            } else {
                CombatManager.startCombat(player, target);
            }
        }
    }

    public static void dispatchDeferredTradeRequest(Player player, Player player2) {
        if (player2 == null || !GameUtil.isWithinDistance(player.getPosition(), player2.getPosition(), 15)) {
            player.pendingTradeTarget = null;
            return;
        }
        int value = player.nextActionSequence();
        if (player2.getTradePartner() == player) {
            GameplayHelper.declineTrade(player);
            player.pendingTradeTarget = null;
        } else if (player2.getOpenInterfaceId() > 0) {
            player2 = player;
            player2.packetSender.sendGameMessage("This player is busy.");
            player.pendingTradeTarget = null;
            return;
        }
        player.setInteractionTarget(player2);
        player.setAttackRange(1);
        player.setMovementTarget(player2);
        World.scheduleTickTask(new DeferredTradeRequestTask(1, player2, player, value));
    }

    private static boolean isWithinCombatInteractionRange(Player player, Player targetPlayer) {
        if (player == null || targetPlayer == null) {
            return false;
        }
        return GameUtil.isWithinDistance(player.getPosition(), targetPlayer.getPosition(), 15);
    }
}
