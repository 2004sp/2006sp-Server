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
        if (legacyButtonId != InterfaceBridge.UNMAPPED
                && legacyButtonId != 3214 && legacyButtonId != 1688
                && (operation == -1 || parameter == 0)) {
            buttonHandler.handleButton(player, legacyButtonId);
        }
    }
}
