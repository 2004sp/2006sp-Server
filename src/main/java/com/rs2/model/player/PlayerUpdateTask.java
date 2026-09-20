package com.rs2.model.player;

import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.model.Entity;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.DelayedBotLevelReplyTask;
import com.rs2.model.player.DelayedBotTradeRequestTask;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.AccessMode;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketWriter;
import com.rs2.util.GameUtil;
import java.util.Iterator;

public class PlayerUpdateTask {
    int itemContainerInterfaceId;
    private int bankTabButtonId;

    private PlayerUpdateTask(int itemContainerInterfaceId, int bankTabButtonId, int value32) {
        this.itemContainerInterfaceId = itemContainerInterfaceId;
        this.bankTabButtonId = bankTabButtonId;
    }

    public final int getBankTabButtonId() {
        return this.bankTabButtonId;
    }

    PlayerUpdateTask(int value5, int value22, int value32, byte value6) {
        this(value5, 19509, 19508);
    }

    PlayerUpdateTask(int value5, int value22, int value32, int value42) {
        this(value5, 19509, 19508);
    }

    public static void updatePlayer(Player player) {
        Object value;
        int value2;
        int position2 = player.getPosition().getX() - (player.getLastKnownRegionPosition().getRegionX() << 3);
        int position3 = player.getPosition().getY() - (player.getLastKnownRegionPosition().getRegionY() << 3);
        if (position2 < 16 || position2 >= 88 || position3 < 16 || position3 > 88) {
            Player player2 = player;
            player2.packetSender.sendMapRegion();
            player.lastRegionChangeMillis = System.currentTimeMillis();
        }
        if (player.isBot) {
            return;
        }
        // Dense minigames can have hundreds of local-player update blocks
        // in one packet. Start larger to avoid repeated reallocations; PacketWriter
        // can grow further if a particularly busy tick still exceeds these sizes.
        PacketWriter packetWriter = PacketBuffer.allocateWriter(32768);
        PacketWriter packetWriter2 = PacketBuffer.allocateWriter(16384);
        packetWriter.startVariableShortPacket(player.getOutboundCipher(), 81);
        packetWriter.setAccessMode(AccessMode.BIT_ACCESS);
        PacketWriter packetWriter3 = packetWriter;
        Object value3 = player;
        int index = 0;
        boolean updateRequired = ((Entity)value3).getUpdateState().isUpdateRequired();
        if (((Player)value3).isTeleporting()) {
            packetWriter3.writeBoolean(true);
            ((Entity)value3).getPosition();
            value2 = Position.updateLocalX((Player)value3);
            ((Entity)value3).getPosition();
            int localY = Position.updateLocalY((Player)value3);
            boolean teleportPlacementUpdateRequired = ((Player)value3).isTeleportPlacementUpdateRequired();
            int plane = ((Entity)value3).getPosition().getPlane();
            PacketWriter movementWriter = packetWriter3;
            movementWriter.writeBits(2, 3);
            movementWriter.writeBits(2, plane);
            movementWriter.writeBoolean(teleportPlacementUpdateRequired);
            movementWriter.writeBoolean(updateRequired);
            movementWriter.writeBits(7, localY);
            movementWriter.writeBits(7, value2);
        } else {
            value2 = ((Entity)value3).getWalkDirection();
            int runDirection = ((Entity)value3).getRunDirection();
            if (value2 != -1) {
                packetWriter3.writeBoolean(true);
                if (runDirection != -1) {
                    PlayerUpdateTask.writeRunMovement(packetWriter3, value2, runDirection, updateRequired);
                } else {
                    PlayerUpdateTask.writeWalkMovement(packetWriter3, value2, updateRequired);
                }
            } else if (updateRequired) {
                packetWriter3.writeBoolean(true);
                PacketWriter packetWriter4 = packetWriter3;
                packetWriter4.writeBits(2, 0);
            } else {
                packetWriter3.writeBoolean(false);
            }
        }
        if (player.getUpdateState().isUpdateRequired()) {
            PlayerUpdateTask.writeUpdateBlock(player, packetWriter2, false, !player.publicChatUpdatePending, null);
            player.publicChatUpdatePending = false;
        }
        packetWriter.writeBits(8, player.getLocalPlayers().size());
        Iterator iterator = player.getLocalPlayers().iterator();
        while (iterator.hasNext()) {
            updatePlayerControlExit1: {
                Player player3;
                updatePlayerControlExit2: {
                    Object value4;
                    Object value5;
                    updatePlayerControlExit3: {
                        player3 = (Player)iterator.next();
                        if (!isWithinLocalPlayerViewport(player, player3) || !player3.isVisibleToOtherPlayers() || player3.getConnectionState() == PlayerConnectionState.DISCONNECTED || player3.isTeleporting()) break updatePlayerControlExit1;
                        Object value6 = packetWriter;
                        value3 = player3;
                        boolean localUpdateRequired = ((Entity)value3).getUpdateState().isUpdateRequired();
                        int walkDirection = ((Entity)value3).getWalkDirection();
                        int runDirection2 = ((Entity)value3).getRunDirection();
                        if (walkDirection != -1) {
                            ((PacketWriter)value6).writeBoolean(true);
                            if (runDirection2 != -1) {
                                PlayerUpdateTask.writeRunMovement((PacketWriter)value6, walkDirection, runDirection2, localUpdateRequired);
                            } else {
                                PlayerUpdateTask.writeWalkMovement((PacketWriter)value6, walkDirection, localUpdateRequired);
                            }
                        } else if (localUpdateRequired) {
                            ((PacketWriter)value6).writeBoolean(true);
                            PacketWriter packetWriter5 = (PacketWriter)value6;
                            packetWriter5.writeBits(2, 0);
                        } else {
                            ((PacketWriter)value6).writeBoolean(false);
                        }
                        if (!player3.isBot || player.pendingPublicChatText.equals("")) break updatePlayerControlExit2;
                        value6 = player;
                        value3 = player3;
                        index = 3 + GameUtil.randomInt(3);
                        value5 = value3;
                        value4 = ((Player)value6).pendingPublicChatText;
                        value = "";
                        if (((Player)value3).botMode != 2) break updatePlayerControlExit3;
                        if (!((String)value4).toLowerCase().startsWith("buy") && !((String)value4).toLowerCase().startsWith("sell") || ((Player)value3).botAdvertItemId <= 0) break updatePlayerControlExit2;
                        ItemDefinition itemDefinition = ItemDefinition.forId(((Player)value3).botAdvertItemId);
                        if (itemDefinition.isNote()) {
                            itemDefinition = ItemDefinition.forId(itemDefinition.getUnnotedId());
                        }
                        String name = itemDefinition.getName().toLowerCase();
                        String shortName = itemDefinition.getShortName();
                        shortName = shortName != null ? shortName.toLowerCase() : name;
                        if (!((String)value4).contains(name) && !((String)value4).contains(shortName)) break updatePlayerControlExit2;
                        if (GameUtil.randomInt(3) == 0) {
                            World.getTaskScheduler().schedule(new DelayedBotTradeRequestTask((Player)value3, index, (Player)value5, (String)value4, (Player)value6));
                        }
                    }
                    if ((((Player)value3).botMode == 0 || ((Player)value3).botMode == 4) && ((Player)value3).botTaskState.equals("do task") && (((String)value4).toLowerCase().equals("lvls?") || ((String)value4).toLowerCase().equals("lvls") || ((String)value4).toLowerCase().equals("lvl?") || ((String)value4).toLowerCase().equals("lvl") || ((String)value4).toLowerCase().equals("levels?") || ((String)value4).toLowerCase().equals("levels") || ((String)value4).toLowerCase().equals("level?") || ((String)value4).toLowerCase().equals("level"))) {
                        int value7;
                        int initialValue = -1;
                        int initialValue2 = -1;
                        value4 = ((Player)value3).currentBotTask;
                        int value8 = BotTaskDefinition.brassKeyTasks.contains(value4) ? 0 : (BotTaskDefinition.shopTasks.contains(value4) ? 1 : (BotTaskDefinition.fishingTasks.contains(value4) ? 2 : (BotTaskDefinition.cookingTasks.contains(value4) ? 3 : (BotTaskDefinition.miningTasks.contains(value4) ? 4 : (BotTaskDefinition.smeltingTasks.contains(value4) ? 5 : (BotTaskDefinition.smithingTasks.contains(value4) ? 6 : (BotTaskDefinition.woodcuttingTasks.contains(value4) ? 7 : (BotTaskDefinition.runecraftingTasks.contains(value4) ? 8 : (BotTaskDefinition.moneyMakingTasks.contains(value4) ? 9 : (BotTaskDefinition.combatTasks.contains(value4) ? 10 : (BotTaskDefinition.sheepShearingTasks.contains(value4) ? 11 : (BotTaskDefinition.spinningTasks.contains(value4) ? 12 : (BotTaskDefinition.tanningTasks.contains(value4) ? 13 : (((Player)value3).currentBotTaskTypeId = BotTaskDefinition.leatherCraftingTasks.contains(value4) ? 14 : -1))))))))))))));
                        if (((Player)value3).currentBotTaskTypeId == 2) {
                            initialValue = 10;
                        } else if (((Player)value3).currentBotTaskTypeId == 3) {
                            initialValue = 7;
                        } else if (((Player)value3).currentBotTaskTypeId == 4) {
                            initialValue = 14;
                        } else if (((Player)value3).currentBotTaskTypeId == 5 || ((Player)value3).currentBotTaskTypeId == 6) {
                            initialValue = 13;
                        } else if (((Player)value3).currentBotTaskTypeId == 7) {
                            initialValue = 8;
                        } else if (((Player)value3).currentBotTaskTypeId == 8) {
                            initialValue = 20;
                        } else if (((Player)value3).currentBotTaskTypeId == 11 || ((Player)value3).currentBotTaskTypeId == 12 || ((Player)value3).currentBotTaskTypeId == 13 || ((Player)value3).currentBotTaskTypeId == 14) {
                            initialValue = 12;
                        }
                        if (((Player)value3).currentBotTaskTypeId == 8 && (value7 = ((Player)value3).currentBotTask.getTaskIndexForType(((Player)value3).currentBotTaskTypeId)) == 0) {
                            initialValue = 14;
                        }
                        if (initialValue != -1) {
                            initialValue2 = ((Player)value3).getSkillManager().getBaseLevel(initialValue);
                        } else if (((Player)value3).currentBotTaskTypeId == 10) {
                            if (((Player)value3).botActiveCombatStyle == BotPvpCombatHandler.MAGIC_COMBAT_STYLE) {
                                initialValue2 = ((Player)value3).getSkillManager().getBaseLevel(6);
                            } else if (((Player)value3).botActiveCombatStyle == BotPvpCombatHandler.RANGED_COMBAT_STYLE) {
                                initialValue2 = ((Player)value3).getSkillManager().getBaseLevel(4);
                            } else if (((Player)value3).botActiveCombatStyle == 0) {
                                int skillManager = ((Player)value3).getSkillManager().getBaseLevel(0);
                                value = String.valueOf(value) + "atk: " + skillManager + " ";
                                int skillManager2 = ((Player)value3).getSkillManager().getBaseLevel(1);
                                value = String.valueOf(value) + "def: " + skillManager2 + " ";
                                int skillManager3 = ((Player)value3).getSkillManager().getBaseLevel(2);
                                value = String.valueOf(value) + "str: " + skillManager3 + " ";
                            }
                        }
                        if (initialValue2 != -1) {
                            value = GameUtil.randomInt(10) == 0 ? String.valueOf(initialValue2) + " " + (GameUtil.randomInt(2) == 0 ? "u" : "u?") : String.valueOf(initialValue2);
                        }
                        if (!((String)value).equals("") && GameUtil.randomInt(10) != 0) {
                            Object value9 = value;
                            DelayedBotLevelReplyTask delayedBotLevelReplyTask = new DelayedBotLevelReplyTask((Player)value3, index, (Player)value5, (String)value9);
                            World.getTaskScheduler().schedule(delayedBotLevelReplyTask);
                        }
                    }
                }
                if (!player3.getUpdateState().isUpdateRequired()) continue;
                PlayerUpdateTask.writeUpdateBlock(player3, packetWriter2, false, false, player);
                continue;
            }
            packetWriter.writeBoolean(true);
            packetWriter.writeBits(2, 3);
            iterator.remove();
        }
        player.pendingPublicChatText = "";
        int index2 = 0;
        int index3 = 0;
        while (index3 < World.getPlayers().length) {
            if (index2 > 15 || player.getLocalPlayers().size() >= 255) break;
            Player player4 = World.getPlayers()[index3];
            if (player4 != null && player4 != player && player4.getConnectionState() != PlayerConnectionState.DISCONNECTED && !player.getLocalPlayers().contains(player4) && isWithinLocalPlayerViewport(player, player4) && player4.isVisibleToOtherPlayers()) {
                ++index2;
                player.getLocalPlayers().add(player4);
                Player player5 = player4;
                Player player6 = player;
                value3 = packetWriter;
                ((PacketWriter)value3).writeBits(11, player5.getIndex());
                ((PacketWriter)value3).writeBoolean(true);
                ((PacketWriter)value3).writeBoolean(true);
                Position position = GameUtil.getDelta(player6.getPosition(), player5.getPosition());
                ((PacketWriter)value3).writeBits(5, position.getY());
                ((PacketWriter)value3).writeBits(5, position.getX());
                PlayerUpdateTask.writeUpdateBlock(player4, packetWriter2, true, false, player);
            }
            ++index3;
        }
        if (packetWriter2.getBuffer().position() > 0) {
            packetWriter.writeBits(11, 2047);
            packetWriter.setAccessMode(AccessMode.BYTE_ACCESS);
            packetWriter.writeBuffer(packetWriter2.getBuffer());
        } else {
            packetWriter.setAccessMode(AccessMode.BYTE_ACCESS);
        }
        packetWriter.finishVariableShortPacket();
        player.writePacketBuffer(packetWriter.getBuffer());
        if (player.planeChangeRefreshPending) {
            ObjectManager.getInstance().refreshDynamicObjectsForPlayer(player);
            GroundItemManager.getInstance().refreshForPlayer(player);
            player.planeChangeRefreshPending = false;
        }
    }

    private static boolean isWithinLocalPlayerViewport(Player viewer, Player other) {
        if (viewer == null || other == null) {
            return false;
        }
        return other.getPosition().isWithinViewport(viewer.getPosition());
    }

    public static void writeUpdateBlock(Player player, PacketWriter packetWriter, boolean enabled3, boolean enabled22, Player player22) {
        int value;
        int index = 0;
        if (player.getUpdateState().isForcedMovementUpdateRequired()) {
            index = 1024;
        }
        if (player.getUpdateState().isGraphicUpdateRequired()) {
            index |= 0x100;
        }
        if (player.getUpdateState().isAnimationUpdateRequired()) {
            index |= 8;
        }
        if (player.getUpdateState().isForcedTextUpdateRequired()) {
            index |= 4;
        }
        if (player.getUpdateState().isAppearanceUpdateRequired() && !enabled22) {
            index |= 0x80;
        }
        if (player.getUpdateState().isFaceEntityUpdateRequired()) {
            index |= 1;
        }
        if (player.isAppearanceUpdateRequired() || enabled3) {
            index |= 0x10;
        }
        if (player.getUpdateState().isFacePositionUpdateRequired()) {
            index |= 2;
        }
        if (player.getUpdateState().isPrimaryHitUpdateRequired()) {
            index |= 0x20;
        }
        if (player.getUpdateState().isSecondaryHitUpdateRequired()) {
            index |= 0x200;
        }
        if (index >= 256) {
            packetWriter.writeShort(index |= 0x40, ByteOrder.LITTLE);
        } else {
            packetWriter.writeByte(index);
        }
        if (player.getUpdateState().isForcedMovementUpdateRequired()) {
            if (player22 == null) {
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalXForUpdate(player), ByteTransform.SUBTRACT);
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalYForUpdate(player), ByteTransform.SUBTRACT);
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalXForUpdate(player) + player.getUpdateState().getForcedMovementEndXOffset(), ByteTransform.SUBTRACT);
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalYForUpdate(player) + player.getUpdateState().getForcedMovementEndYOffset(), ByteTransform.SUBTRACT);
            } else {
                index = player.getPosition().getX() - ((Entity)player22).getPosition().getX();
                value = player.getPosition().getY() - ((Entity)player22).getPosition().getY();
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalXForUpdate((Player)player22) + index, ByteTransform.SUBTRACT);
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalYForUpdate((Player)player22) + value, ByteTransform.SUBTRACT);
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalXForUpdate((Player)player22) + index + player.getUpdateState().getForcedMovementEndXOffset(), ByteTransform.SUBTRACT);
                player.getUpdateState();
                packetWriter.writeByte(EntityUpdateState.getLocalYForUpdate((Player)player22) + value + player.getUpdateState().getForcedMovementEndYOffset(), ByteTransform.SUBTRACT);
            }
            packetWriter.writeShort(player.getUpdateState().getForcedMovementStartDelay(), ByteTransform.ADD, ByteOrder.LITTLE);
            packetWriter.writeShort(player.getUpdateState().getForcedMovementEndDelay(), ByteTransform.ADD);
            packetWriter.writeByte(player.getUpdateState().getForcedMovementDirection(), ByteTransform.SUBTRACT);
        }
        if (player.getUpdateState().isGraphicUpdateRequired()) {
            packetWriter.writeShort(player.getUpdateState().getGraphicId(), ByteOrder.LITTLE);
            packetWriter.writeInt(player.getUpdateState().getGraphicDelay());
        }
        if (player.getUpdateState().isAnimationUpdateRequired()) {
            boolean suppressMorphAnimation =
                    CastleWarsManager.isWaitingRoomGodTransformation(player);
            packetWriter.writeShort(
                    suppressMorphAnimation ? -1 : player.getUpdateState().getAnimationId(),
                    ByteOrder.LITTLE);
            packetWriter.writeByte(
                    suppressMorphAnimation ? 0 : player.getUpdateState().getAnimationDelay(),
                    ByteTransform.NEGATE);
        }
        if (player.getUpdateState().isForcedTextUpdateRequired()) {
            packetWriter.writeString(player.getUpdateState().getForcedText());
        }
        if (player.getUpdateState().isAppearanceUpdateRequired() && !enabled22) {
            PacketWriter chatWriter = packetWriter;
            Player player2 = player;
            chatWriter.writeShort(((player2.getPublicChatColor() & 0xFF) << 8) + (player2.getPublicChatEffects() & 0xFF), ByteOrder.LITTLE);
            chatWriter.writeByte(player2.getPublicChatPayload().length, ByteTransform.NEGATE);
            byte[] publicChatPayload = player2.getPublicChatPayload();
            int value2 = publicChatPayload.length - 1;
            while (value2 >= 0) {
                chatWriter.writeByte(publicChatPayload[value2]);
                --value2;
            }
        }
        if (player.getUpdateState().isFaceEntityUpdateRequired()) {
            packetWriter.writeShort(player.getUpdateState().getFaceEntityId(), ByteOrder.LITTLE);
        }
        if (player.isAppearanceUpdateRequired() || enabled3) {
            PacketWriter appearanceWriter = packetWriter;
            Player player3 = player;
            PacketWriter packetWriter2 = PacketBuffer.allocateWriter(128);
            packetWriter2.writeByte(player3.getGender());
            packetWriter2.writeByte(player3.getPrayerHeadIcon());
            packetWriter2.writeByte(player3.getSkullIcon());
            if (player3.npcTransformationId <= 0) {
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(0) && player3.getEquipmentManager().getContainer().getItemAt(0).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(0).getId());
                } else {
                    packetWriter2.writeByte(0);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(1) && player3.getEquipmentManager().getContainer().getItemAt(1).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(1).getId());
                } else {
                    packetWriter2.writeByte(0);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(2) && player3.getEquipmentManager().getContainer().getItemAt(2).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(2).getId());
                } else {
                    packetWriter2.writeByte(0);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(3) && !player3.shouldHideHeldItemsInAppearance() && player3.getEquipmentManager().getContainer().getItemAt(3).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(3).getId());
                } else {
                    packetWriter2.writeByte(0);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(4) && player3.getEquipmentManager().getContainer().getItemAt(4).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(4).getId());
                } else {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[0]);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(5) && !player3.shouldHideHeldItemsInAppearance() && player3.getEquipmentManager().getContainer().getItemAt(5).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(5).getId());
                } else {
                    packetWriter2.writeByte(0);
                }
                value = 0;
                ItemStack itemStack = player3.getEquipmentManager().getContainer().getItemAt(4);
                if (itemStack != null) {
                    value = itemStack.getDefinition().getEquipmentAppearanceType();
                }
                if (itemStack == null || itemStack != null && value != 1 && itemStack.isEquippable()) {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[1]);
                } else {
                    packetWriter2.writeShort(512 + itemStack.getId());
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(7) && player3.getEquipmentManager().getContainer().getItemAt(7).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(7).getId());
                } else {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[2]);
                }
                ItemStack itemStack2 = player3.getEquipmentManager().getContainer().getItemAt(0);
                value = 0;
                if (itemStack2 != null) {
                    value = itemStack2.getDefinition().getEquipmentAppearanceType();
                }
                if (itemStack2 == null || itemStack2 != null && value != 3 && value != 2 && itemStack2.isEquippable()) {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[3]);
                } else {
                    packetWriter2.writeByte(0);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(9) && player3.getEquipmentManager().getContainer().getItemAt(9).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(9).getId());
                } else {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[4]);
                }
                if (player3.getEquipmentManager().getContainer().hasItemAtSlot(10) && player3.getEquipmentManager().getContainer().getItemAt(10).isEquippable()) {
                    packetWriter2.writeShort(512 + player3.getEquipmentManager().getContainer().getItemAt(10).getId());
                } else {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[5]);
                }
                if (player3.getGender() == 0 && (itemStack2 == null || itemStack2 != null && value != 4 && value != 2)) {
                    packetWriter2.writeShort(256 + player3.getAppearanceParts()[6]);
                } else {
                    packetWriter2.writeByte(0);
                }
            } else {
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(player3.npcTransformationId);
            }
            packetWriter2.writeByte(player3.getAppearanceColors()[0]);
            packetWriter2.writeByte(player3.getAppearanceColors()[1]);
            packetWriter2.writeByte(player3.getAppearanceColors()[2]);
            packetWriter2.writeByte(player3.getAppearanceColors()[3]);
            packetWriter2.writeByte(player3.getAppearanceColors()[4]);
            if (CastleWarsManager.isWaitingRoomGodTransformation(player3)) {
                // Waiting-room god disguises should be static. Sending player
                // movement/emote sequences to these NPC models produces the
                // wrong-looking morph animations on the 377 client.
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(-1);
                packetWriter2.writeShort(-1);
            } else {
                packetWriter2.writeShort(player3.getStandAnimation());
                Player player4 = player3;
                int walkAnimation = player4.getWalkAnimation();
                packetWriter2.writeShort(walkAnimation != WeaponProfile.FISTS.getMovementAnimations()[1] ? walkAnimation : 823);
                packetWriter2.writeShort(player3.getWalkAnimation());
                Player player5 = player3;
                int walkAnimation2 = player5.getWalkAnimation();
                packetWriter2.writeShort(walkAnimation2 != WeaponProfile.FISTS.getMovementAnimations()[1] ? walkAnimation2 : 820);
                Player player6 = player3;
                int walkAnimation3 = player6.getWalkAnimation();
                packetWriter2.writeShort(walkAnimation3 != WeaponProfile.FISTS.getMovementAnimations()[1] ? walkAnimation3 : 821);
                Player player7 = player3;
                int walkAnimation4 = player7.getWalkAnimation();
                packetWriter2.writeShort(walkAnimation4 != WeaponProfile.FISTS.getMovementAnimations()[1] ? walkAnimation4 : 822);
                packetWriter2.writeShort(player3.getRunAnimation());
            }
            packetWriter2.writeLong(player3.getNameHash());
            packetWriter2.writeByte(player3.getCombatLevel());
            packetWriter2.writeByte(player3.getPlayerRights());
            packetWriter2.writeByte(0);
            int appearanceGameMode = player3.gameMode;
            if (appearanceGameMode < 0 || appearanceGameMode > 3) {
                System.err.println("Invalid appearance game mode " + appearanceGameMode + " for " + player3.getUsername() + "; resetting to normal mode (0).");
                appearanceGameMode = 0;
                player3.gameMode = 0;
            }
            packetWriter2.writeByte(appearanceGameMode);
            packetWriter2.writeByte(0);
            packetWriter2.writeShort(0);
            appearanceWriter.writeByte(packetWriter2.getBuffer().position(), ByteTransform.NEGATE);
            appearanceWriter.writeBuffer(packetWriter2.getBuffer());
        }
        if (player.getUpdateState().isFacePositionUpdateRequired()) {
            packetWriter.writeShort((player.getUpdateState().getFacePosition().getX() << 1) + 1, ByteTransform.ADD, ByteOrder.LITTLE);
            packetWriter.writeShort((player.getUpdateState().getFacePosition().getY() << 1) + 1, ByteOrder.LITTLE);
        }
        if (player.getUpdateState().isPrimaryHitUpdateRequired()) {
            packetWriter.writeByte(player.getUpdateState().getPrimaryHitDamage());
            packetWriter.writeByte(0);
            packetWriter.writeByte(player.getUpdateState().getPrimaryHitType(), ByteTransform.ADD);
            packetWriter.writeShort(player.getSkillManager().getCurrentLevels()[3]);
            player.getSkillManager();
            packetWriter.writeShort(SkillManager.getLevelForExperience((int)player.getSkillManager().getExperience()[3]));
        }
        if (player.getUpdateState().isSecondaryHitUpdateRequired()) {
            packetWriter.writeByte(player.getUpdateState().getSecondaryHitDamage());
            packetWriter.writeByte(0);
            packetWriter.writeByte(player.getUpdateState().getSecondaryHitType(), ByteTransform.SUBTRACT);
            packetWriter.writeShort(player.getSkillManager().getCurrentLevels()[3]);
            player.getSkillManager();
            packetWriter.writeShort(SkillManager.getLevelForExperience((int)player.getSkillManager().getExperience()[3]));
        }
    }

    public static void writeWalkMovement(PacketWriter packetWriter, int value2, boolean enabled2) {
        packetWriter.writeBits(2, 1);
        packetWriter.writeBits(3, value2);
        packetWriter.writeBoolean(enabled2);
    }

    public static void writeRunMovement(PacketWriter packetWriter, int value3, int value22, boolean enabled2) {
        packetWriter.writeBits(2, 2);
        packetWriter.writeBits(3, value3);
        packetWriter.writeBits(3, value22);
        packetWriter.writeBoolean(enabled2);
    }
}
