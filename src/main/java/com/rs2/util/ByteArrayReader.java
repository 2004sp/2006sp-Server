package com.rs2.util;

import com.rs2.util.CacheableNode;
import com.rs2.util.LinkedNodeList;

public final class ByteArrayReader
extends CacheableNode {
    private byte[] buffer;
    public int position;

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
        new LinkedNodeList();
    }

    private ByteArrayReader() {
    }

    public ByteArrayReader(byte[] buffer) {
        this.buffer = buffer;
        this.position = 0;
    }

    public final int readUnsignedByte() {
        return this.buffer[this.position++] & 0xFF;
    }

    public final byte readByte() {
        return this.buffer[this.position++];
    }

    public final int readUnsignedShort() {
        this.position += 2;
        return ((this.buffer[this.position - 2] & 0xFF) << 8) + (this.buffer[this.position - 1] & 0xFF);
    }

    public final int readShort() {
        this.position += 2;
        int value = ((this.buffer[this.position - 2] & 0xFF) << 8) + (this.buffer[this.position - 1] & 0xFF);
        if (value > Short.MAX_VALUE) {
            value -= 65536;
        }
        return value;
    }

    public final int readInt() {
        this.position += 4;
        return ((this.buffer[this.position - 4] & 0xFF) << 24) + ((this.buffer[this.position - 3] & 0xFF) << 16) + ((this.buffer[this.position - 2] & 0xFF) << 8) + (this.buffer[this.position - 1] & 0xFF);
    }

    public final long readLong() {
        long value = (long)this.readInt() & 0xFFFFFFFFL;
        long value2 = (long)this.readInt() & 0xFFFFFFFFL;
        return (value << 32) + value2;
    }

    public final String readString() {
        int value = this.position;
        while (this.buffer[this.position++] != 10) {
        }
        return new String(this.buffer, value, this.position - value - 1);
    }

    public final byte[] readLineBytes() {
        int value = this.position;
        while (this.buffer[this.position++] != 10) {
        }
        byte[] byteValues = new byte[this.position - value - 1];
        System.arraycopy(this.buffer, value, byteValues, value - value, this.position - 1 - value);
        return byteValues;
    }
}

