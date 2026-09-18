package com.rs2.util;

import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CountingDataOutputStream
extends FilterOutputStream
implements DataOutput {
    private int bytesWritten;
    private byte[] scratch = new byte[8];

    public CountingDataOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public final void flush() throws IOException {
        super.flush();
    }

    @Override
    public final void write(byte[] byteValues2, int value3, int value22) throws IOException {
        this.out.write(byteValues2, value3, value22);
        this.bytesWritten += value22;
    }

    @Override
    public final void write(int value2) throws IOException {
        this.out.write(value2);
        ++this.bytesWritten;
    }

    @Override
    public final void writeBoolean(boolean enabled2) throws IOException {
        this.out.write(enabled2 ? 1 : 0);
        ++this.bytesWritten;
    }

    public final void writeUnsignedByte(int value2) throws IOException {
        this.out.write(value2 & 0xFF);
        ++this.bytesWritten;
    }

    @Override
    public final void writeByte(int value2) throws IOException {
        this.out.write(value2);
        ++this.bytesWritten;
    }

    @Override
    public final void writeBytes(String text2) throws IOException {
        if (text2.length() == 0) {
            return;
        }
        byte[] byteValues = new byte[text2.length()];
        int index = 0;
        while (index < text2.length()) {
            byteValues[index] = (byte)text2.charAt(index);
            ++index;
        }
        this.out.write(byteValues);
        this.bytesWritten += byteValues.length;
    }

    @Override
    public final void writeChar(int value2) throws IOException {
        this.scratch[0] = (byte)(value2 >> 8);
        this.scratch[1] = (byte)value2;
        this.out.write(this.scratch, 0, 2);
        this.bytesWritten += 2;
    }

    @Override
    public final void writeChars(String text2) throws IOException {
        byte[] byteValues = new byte[text2.length() << 1];
        int index = 0;
        while (index < text2.length()) {
            int value = index == 0 ? index : index << 1;
            byteValues[value] = (byte)(text2.charAt(index) >> 8);
            byteValues[value + 1] = (byte)text2.charAt(index);
            ++index;
        }
        this.out.write(byteValues);
        this.bytesWritten += byteValues.length;
    }

    @Override
    public final void writeDouble(double value2) throws IOException {
        this.writeLong(Double.doubleToLongBits(value2));
    }

    @Override
    public final void writeFloat(float value2) throws IOException {
        this.writeInt(Float.floatToIntBits(value2));
    }

    @Override
    public final void writeInt(int value2) throws IOException {
        this.scratch[0] = (byte)(value2 >> 24);
        this.scratch[1] = (byte)(value2 >> 16);
        this.scratch[2] = (byte)(value2 >> 8);
        this.scratch[3] = (byte)value2;
        this.out.write(this.scratch, 0, 4);
        this.bytesWritten += 4;
    }

    @Override
    public final void writeLong(long value2) throws IOException {
        this.scratch[0] = (byte)(value2 >> 56);
        this.scratch[1] = (byte)(value2 >> 48);
        this.scratch[2] = (byte)(value2 >> 40);
        this.scratch[3] = (byte)(value2 >> 32);
        this.scratch[4] = (byte)(value2 >> 24);
        this.scratch[5] = (byte)(value2 >> 16);
        this.scratch[6] = (byte)(value2 >> 8);
        this.scratch[7] = (byte)value2;
        this.out.write(this.scratch, 0, 8);
        this.bytesWritten += 8;
    }

    @Override
    public final void writeShort(int value2) throws IOException {
        this.scratch[0] = (byte)(value2 >> 8);
        this.scratch[1] = (byte)value2;
        this.out.write(this.scratch, 0, 2);
        this.bytesWritten += 2;
    }

    @Override
    public final void writeUTF(String text4) throws IOException {
        int value;
        String text2 = text4;
        int index = 0;
        int value2 = text2.length();
        int index2 = 0;
        while (index2 < value2) {
            value = text2.charAt(index2);
            index = value > 0 && value <= 127 ? ++index : (value <= 2047 ? (index += 2) : (index += 3));
            ++index2;
        }
        long value3 = index;
        byte[] byteValues = new byte[(int)value3 + 2];
        index = 0;
        byte[] byteValues2 = byteValues;
        int value4 = (int)value3;
        byteValues2[0] = (byte)(value4 >> 8);
        byteValues2[1] = (byte)value4;
        index = value4 = 2;
        byteValues2 = byteValues;
        String text3 = text4;
        int value5 = text3.length();
        value = 0;
        while (value < value5) {
            char character = text3.charAt(value);
            if (character > '\u0000' && character <= '\u007f') {
                byteValues2[index++] = (byte)character;
            } else if (character <= '\u07ff') {
                byteValues2[index++] = (byte)(0xC0 | 0x1F & character >> 6);
                byteValues2[index++] = (byte)(0x80 | 0x3F & character);
            } else {
                byteValues2[index++] = (byte)(0xE0 | 0xF & character >> 12);
                byteValues2[index++] = (byte)(0x80 | 0x3F & character >> 6);
                byteValues2[index++] = (byte)(0x80 | 0x3F & character);
            }
            ++value;
        }
        int value6 = index;
        this.write(byteValues, 0, value6);
    }
}

