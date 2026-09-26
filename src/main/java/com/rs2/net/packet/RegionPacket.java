package com.rs2.net.packet;

import com.rs2.cache.js5.XteaKeys;
import com.rs2.net.IsaacCipher;

/** Static region rebuild packet from the revision 443 client reader. */
public final class RegionPacket {
    private RegionPacket() {
    }

    public static void write(PacketWriter packet, IsaacCipher cipher, int centerX,
                             int centerY, int localX, int localY, int plane) {
        packet.startVariableShortPacket(cipher, 121);
        packet.writeShort(centerX, ByteOrder.LITTLE);
        packet.writeShort(localY, ByteOrder.LITTLE);
        packet.writeShort(localX, ByteTransform.ADD, ByteOrder.LITTLE);
        packet.writeByte(plane, ByteTransform.ADD);

        boolean special = ((centerX / 8 == 48 || centerX / 8 == 49)
                && centerY / 8 == 48)
                || (centerX / 8 == 48 && centerY / 8 == 148);
        for (int x = (centerX - 6) / 8; x <= (centerX + 6) / 8; x++) {
            for (int y = (centerY - 6) / 8; y <= (centerY + 6) / 8; y++) {
                if (special && (y == 49 || y == 149 || y == 147 || x == 50
                        || (x == 49 && y == 47))) {
                    continue;
                }
                int[] key = XteaKeys.forMapSquare(x, y);
                for (int word : key) {
                    packet.writeInt(word);
                }
            }
        }
        packet.writeShort(centerY, ByteTransform.ADD, ByteOrder.BIG);
        packet.finishVariableShortPacket();
    }
}
