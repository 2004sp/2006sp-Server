package com.rs2.net.packet;

import com.rs2.net.packet.AccessMode;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketWriter;
import java.nio.ByteBuffer;

public abstract class PacketBuffer {
    public static final int[] BIT_MASKS;
    private AccessMode accessMode = AccessMode.BYTE_ACCESS;
    private int bitPosition = 0;

    static {
        int[] integerValues = new int[33];
        integerValues[1] = 1;
        integerValues[2] = 3;
        integerValues[3] = 7;
        integerValues[4] = 15;
        integerValues[5] = 31;
        integerValues[6] = 63;
        integerValues[7] = 127;
        integerValues[8] = 255;
        integerValues[9] = 511;
        integerValues[10] = 1023;
        integerValues[11] = 2047;
        integerValues[12] = 4095;
        integerValues[13] = 8191;
        integerValues[14] = 16383;
        integerValues[15] = Short.MAX_VALUE;
        integerValues[16] = 65535;
        integerValues[17] = 131071;
        integerValues[18] = 262143;
        integerValues[19] = 524287;
        integerValues[20] = 1048575;
        integerValues[21] = 0x1FFFFF;
        integerValues[22] = 0x3FFFFF;
        integerValues[23] = 0x7FFFFF;
        integerValues[24] = 0xFFFFFF;
        integerValues[25] = 0x1FFFFFF;
        integerValues[26] = 0x3FFFFFF;
        integerValues[27] = 0x7FFFFFF;
        integerValues[28] = 0xFFFFFFF;
        integerValues[29] = 0x1FFFFFFF;
        integerValues[30] = 0x3FFFFFFF;
        integerValues[31] = Integer.MAX_VALUE;
        integerValues[32] = -1;
        BIT_MASKS = integerValues;
    }

    public static final PacketReader wrapReader(ByteBuffer byteBuffer) {
        return new PacketReader(byteBuffer, 0);
    }

    public static final PacketWriter allocateWriter(int value2) {
        return new PacketWriter(value2, 0);
    }

    abstract void onAccessModeChanged(AccessMode accessMode);

    public final void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
        this.onAccessModeChanged(accessMode);
    }

    public final AccessMode getAccessMode() {
        return this.accessMode;
    }

    public final void setBitPosition(int bitPosition) {
        this.bitPosition = bitPosition;
    }

    public final int getBitPosition() {
        return this.bitPosition;
    }
}
