package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.model.player.BankManager;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.ClientPackets;
import com.rs2.net.packet.InterfaceBridge;
import com.rs2.model.item.ItemStack;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.net.packet.SpellWidgets;
import com.rs2.util.GameplayTrace;

/**
 * Decodes revision 443 widget actions without feeding packed 443 widget ids into
 * the legacy 377 button-id dispatcher.
 */
public final class InterfaceActionPacketHandler implements PacketHandler {
    private final ButtonClickPacketHandler buttonHandler = new ButtonClickPacketHandler();

    private boolean handleAdditionalSkillGuideButton(Player player, int packedWidgetId,
                                                     int operation, int parameter) {
        if ((packedWidgetId >>> 16) != 320
                || (operation != -1 && parameter != 0)) {
            return false;
        }

        int child = packedWidgetId & 0xFFFF;
        // Native skill-tab child 148 is Construction; 149 is Hunter.
        if (child == 148) {
            player.getSkillGuideManager().selectedSkillIndex = 22;
            player.getSkillGuideManager().showConstructionGuide(1);
            return true;
        }
        if (child == 149) {
            player.getSkillGuideManager().selectedSkillIndex = 21;
            player.getSkillGuideManager().showHunterGuide(1);
            return true;
        }
        return false;
    }

    private boolean handleSkillGuideCategory(Player player, int packedWidgetId,
                                             int operation, int parameter) {
        if ((packedWidgetId >>> 16) != 308) {
            return false;
        }

        int child = packedWidgetId & 0xFFFF;

        // Normal button packet is operation == -1. Keep parameter == 0
        // compatible with interface-operation packets too.
        if (operation != -1 && parameter != 0) {
            return false;
        }

        int category;
        switch (child) {
            case 131: category = 1; break;
            case 108: category = 2; break;
            case 109: category = 3; break;
            case 112: category = 4; break;
            case 122: category = 5; break;
            case 125: category = 6; break;
            case 128: category = 7; break;
            case 143: category = 8; break;
            case 146: category = 9; break;
            case 149: category = 10; break;
            case 159: category = 11; break;
            case 162: category = 12; break;
            case 165: category = 13; break;
            default: return false;
        }

        System.out.println("[SKILL GUIDE] category click group=" + (packedWidgetId >>> 16)
                + " child=" + child + " category=" + category);
        player.getSkillGuideManager().showSelectedSkillCategory(category);
        return true;
    }

    @Override
    public void handle(Player player, IncomingPacket packet) {
        int opcode = packet.getOpcode();
        if (opcode == ClientPackets.WIDGET_DRAG_DROP) {
            int targetChild = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
            int sourceChild = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
            int targetWidgetId = ClientPackets.readIntLittle(packet.getReader());
            int sourceWidgetId = ClientPackets.readIntInverseMiddle(packet.getReader());
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 widget-drag-drop player=" + GameplayTrace.describe(player)
                        + " sourceWidget=" + sourceWidgetId + " sourceChild=" + sourceChild
                        + " targetWidget=" + targetWidgetId + " targetChild=" + targetChild);
            }
            if (player.isInteractionDebugEnabled()) {
                player.packetSender.sendGameMessage("443 widget drag/drop: " + sourceWidgetId + ":" + sourceChild
                        + " -> " + targetWidgetId + ":" + targetChild);
            }
            return;
        }
        if (opcode == ClientPackets.SPELL_ON_WIDGET) {
            int spellWidgetId = packet.getReader().readInt();
            int targetWidgetId = packet.getReader().readInt();
            int spellChild = packet.getReader().readSignedShort(ByteTransform.ADD) & 0xFFFF;
            int targetParameter = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 spell-on-widget player=" + GameplayTrace.describe(player)
                        + " spellWidget=" + spellWidgetId + " spellChild=" + spellChild
                        + " targetWidget=" + targetWidgetId + " targetParameter=" + targetParameter);
            }
            if (player.isInteractionDebugEnabled()) {
                player.packetSender.sendGameMessage("443 spell-on-widget: spell=" + spellWidgetId
                        + ":" + spellChild + " target=" + targetWidgetId + ":" + targetParameter);
            }
            if (SpellWidgets.isSpellWidget(spellWidgetId)
                    && InterfaceBridge.toLegacyComponent(targetWidgetId) == 3214
                    && targetParameter < 28 && player.isInterfaceIdOpen(3214)) {
                SpellDefinition spell = Spellbook.getSpellForButtonId(player, spellChild);
                ItemStack item = player.getInventoryManager().getContainer().getItemAt(targetParameter);
                if (spell != null && item != null && item.isValid()) {
                    MagicSpellAction.castItemSpell(player, spell, item.getId(), targetParameter);
                }
            }
            return;
        }
        if (opcode == ClientPackets.WIDGET_SELECT) {
            int child = packet.getReader().readSignedShort(ByteOrder.LITTLE) & 0xFFFF;
            int packedWidgetId = packet.getReader().readInt();
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 widget-select player=" + GameplayTrace.describe(player)
                        + " widget=" + packedWidgetId + " group=" + (packedWidgetId >>> 16)
                        + " child=" + child);
            }
            if (player.isInteractionDebugEnabled()) {
                player.packetSender.sendGameMessage("443 widget select: widget=" + packedWidgetId
                        + " child=" + child);
            }
            return;
        }
        if (opcode == ClientPackets.WIDGET_ITEM_DRAG) {
            int sourceSlot = packet.getReader().readSignedShort() & 0xFFFF;
            int packedWidgetId = packet.getReader().readInt();
            int insertMode = packet.getReader().readUnsignedByte(false);
            int targetSlot = packet.getReader().readSignedShort() & 0xFFFF;
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("443 widget-item-drag player=" + GameplayTrace.describe(player)
                        + " widget=" + packedWidgetId + " group=" + (packedWidgetId >>> 16)
                        + " child=" + (packedWidgetId & 0xFFFF) + " sourceSlot=" + sourceSlot
                        + " targetSlot=" + targetSlot + " insertMode=" + insertMode);
            }
            if (player.isInteractionDebugEnabled()) {
                player.packetSender.sendGameMessage("443 widget drag: widget=" + packedWidgetId
                        + " " + sourceSlot + "->" + targetSlot + " mode=" + insertMode);
            }
            int legacyWidgetId = InterfaceBridge.toLegacyComponent(packedWidgetId);
            if (!player.isActionLocked() && legacyWidgetId == 3214
                    && player.isInterfaceIdOpen(3214)
                    && sourceSlot < 28 && targetSlot < 28) {
                ItemStack item = player.getInventoryManager().getContainer().getItemAt(sourceSlot);
                if (item != null && player.getInventoryManager().containsItemStack(item)) {
                    player.getInventoryManager().swapSlots(sourceSlot, targetSlot);
                    player.getInventoryManager().refresh();
                }
            } else if (!player.isActionLocked() && legacyWidgetId == 5382
                    && player.getOpenInterfaceId() == 5292) {
                BankManager.rearrangeRevision443BankItem(player, sourceSlot, targetSlot);
            }
            return;
        }
        int packedWidgetId = packet.getReader().readInt();
        int operation = ClientPackets.getInterfaceOperation(opcode);
        int parameter = -1;
        if (operation != -1) {
            parameter = packet.getReader().readSignedShort();
        }

        if (handleAdditionalSkillGuideButton(player, packedWidgetId, operation, parameter)
                || handleSkillGuideCategory(player, packedWidgetId, operation, parameter)) {
            return;
        }

        if (GameplayTrace.enabled()) {
            GameplayTrace.log("443 interface action player=" + GameplayTrace.describe(player)
                    + " opcode=" + opcode
                    + " operation=" + operation
                    + " widget=" + packedWidgetId
                    + " group=" + (packedWidgetId >>> 16)
                    + " child=" + (packedWidgetId & 0xFFFF)
                    + " parameter=" + parameter);
        }
        if (player.isInteractionDebugEnabled()) {
            String action = operation == -1 ? "button" : "op" + operation;
            player.packetSender.sendGameMessage("443 interface " + action
                    + ": widget=" + packedWidgetId + " param=" + parameter);
        }
        // The native staff tab has two Spell controls for its attack-mode
        // variants; both open the same picker in the legacy gameplay handler.
        if (packedWidgetId == (90 << 16 | 5)
                && (operation == -1 || parameter == 0)) {
            buttonHandler.handleButton(player, 353);
            return;
        }
        int legacyButtonId = InterfaceBridge.toLegacyComponent(packedWidgetId);

        System.out.println(
                "[SKILL DEBUG] packed=" + packedWidgetId
                        + " group=" + (packedWidgetId >>> 16)
                        + " child=" + (packedWidgetId & 0xFFFF)
                        + " legacy=" + legacyButtonId
                        + " operation=" + operation
                        + " parameter=" + parameter
        );
        if (legacyButtonId != InterfaceBridge.UNMAPPED
                && legacyButtonId != 3214 && legacyButtonId != 1688
                && (operation == -1 || parameter == 0)) {
            buttonHandler.handleButton(player, legacyButtonId);
        }
    }
}
