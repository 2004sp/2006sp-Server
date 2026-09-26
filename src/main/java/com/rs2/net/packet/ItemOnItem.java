package com.rs2.net.packet;

/** Fields written by the 443 client for opcode 147 (class38, menu action 18). */
public final class ItemOnItem {
    public final int selectedSlot;
    public final int selectedItemId;
    public final int targetItemId;
    public final int targetWidgetId;
    public final int selectedWidgetId;
    public final int targetSlot;

    private ItemOnItem(int selectedSlot, int selectedItemId, int targetItemId,
                                  int targetWidgetId, int selectedWidgetId, int targetSlot) {
        this.selectedSlot = selectedSlot;
        this.selectedItemId = selectedItemId;
        this.targetItemId = targetItemId;
        this.targetWidgetId = targetWidgetId;
        this.selectedWidgetId = selectedWidgetId;
        this.targetSlot = targetSlot;
    }

    public static ItemOnItem decode(PacketReader reader) {
        int selectedSlot = reader.readSignedShort(ByteTransform.ADD) & 0xFFFF;
        int selectedItemId = reader.readSignedShort(ByteOrder.LITTLE) & 0xFFFF;
        int targetItemId = reader.readSignedShort(ByteOrder.LITTLE) & 0xFFFF;
        int targetWidgetId = ClientPackets.readIntLittle(reader);
        int selectedWidgetId = ClientPackets.readIntInverseMiddle(reader);
        int targetSlot = reader.readSignedShort() & 0xFFFF;
        return new ItemOnItem(selectedSlot, selectedItemId, targetItemId,
                targetWidgetId, selectedWidgetId, targetSlot);
    }
}
