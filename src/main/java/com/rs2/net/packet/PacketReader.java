package com.rs2.net.packet;

import com.rs2.net.packet.AccessMode;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.PacketBuffer;
import java.nio.ByteBuffer;

public final class PacketReader
extends PacketBuffer {
    private ByteBuffer buffer;

    private PacketReader(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
    }

    @Override
    final void onAccessModeChanged(AccessMode accessMode) {
        if (accessMode == AccessMode.BIT_ACCESS) {
            throw new UnsupportedOperationException("Reading bits is not implemented!");
        }
    }

    public final int readByte(boolean enabled2, ByteTransform byteTransform) {
        int value = this.buffer.get();
        switch (byteTransform) {
            case ADD: {
                value -= 128;
                break;
            }
            case NEGATE: {
                value = -value;
                break;
            }
            case SUBTRACT: {
                value = 128 - value;
            }
        }
        if (enabled2) {
            return value;
        }
        return value & 0xFF;
    }

    public final int readSignedByte() {
        return this.readByte(true, ByteTransform.NONE);
    }

    public final int readUnsignedByte(boolean enabled2) {
        return this.readByte(false, ByteTransform.NONE);
    }

    public final int readSignedByte(ByteTransform byteTransform) {
        return this.readByte(true, byteTransform);
    }

    public final int readShort(boolean enabled2, ByteTransform byteTransform, ByteOrder byteOrder) {
        int index = 0;
        switch (byteOrder) {
            case BIG: {
                index = 0 | this.readUnsignedByte(false) << 8;
                index |= this.readByte(false, byteTransform);
                break;
            }
            case MIDDLE: {
                throw new UnsupportedOperationException("Middle-endian short is impossible!");
            }
            case INVERSE_MIDDLE: {
                throw new UnsupportedOperationException("Inverse-middle-endian short is impossible!");
            }
            case LITTLE: {
                index = 0 | this.readByte(false, byteTransform);
                index |= this.readUnsignedByte(false) << 8;
            }
        }
        if (enabled2) {
            return index;
        }
        return index & 0xFFFF;
    }

    public final int readSignedShort() {
        return this.readShort(true, ByteTransform.NONE, ByteOrder.BIG);
    }

    public final int readSignedShort(boolean enabled2) {
        return this.readShort(true, ByteTransform.NONE, ByteOrder.BIG);
    }

    public final int readSignedShort(ByteTransform byteTransform) {
        return this.readShort(true, byteTransform, ByteOrder.BIG);
    }

    public final int readSignedShort(boolean enabled2, ByteTransform byteTransform) {
        return this.readShort(true, byteTransform, ByteOrder.BIG);
    }

    public final int readSignedShort(ByteOrder byteOrder) {
        return this.readShort(true, ByteTransform.NONE, byteOrder);
    }

    public final int readSignedShort(boolean enabled2, ByteOrder byteOrder) {
        return this.readShort(true, ByteTransform.NONE, byteOrder);
    }

    public final int readSignedShort(ByteTransform byteTransform, ByteOrder byteOrder) {
        return this.readShort(true, byteTransform, byteOrder);
    }

    public final int readInt() {
        ByteOrder byteOrder = ByteOrder.BIG;
        ByteTransform byteTransform = ByteTransform.NONE;
        boolean enabled = true;
        PacketReader packetReader = this;
        long value = 0L;
        switch (byteOrder) {
            case BIG: {
                value = 0L | (long)(packetReader.readUnsignedByte(false) << 24);
                value |= (long)(packetReader.readUnsignedByte(false) << 16);
                value |= (long)(packetReader.readUnsignedByte(false) << 8);
                value |= (long)packetReader.readByte(false, byteTransform);
                break;
            }
            case MIDDLE: {
                value = 0L | (long)(packetReader.readUnsignedByte(false) << 8);
                value |= (long)packetReader.readByte(false, byteTransform);
                value |= (long)(packetReader.readUnsignedByte(false) << 24);
                value |= (long)(packetReader.readUnsignedByte(false) << 16);
                break;
            }
            case INVERSE_MIDDLE: {
                value = 0L | (long)(packetReader.readUnsignedByte(false) << 16);
                value |= (long)(packetReader.readUnsignedByte(false) << 24);
                value |= (long)packetReader.readByte(false, byteTransform);
                value |= (long)(packetReader.readUnsignedByte(false) << 8);
                break;
            }
            case LITTLE: {
                value = 0L | (long)packetReader.readByte(false, byteTransform);
                value |= (long)(packetReader.readUnsignedByte(false) << 8);
                value |= (long)(packetReader.readUnsignedByte(false) << 16);
                value |= (long)(packetReader.readUnsignedByte(false) << 24);
            }
        }
        return (int)value;
    }

    public final long readLong() {
        ByteOrder byteOrder = ByteOrder.BIG;
        ByteTransform byteTransform = ByteTransform.NONE;
        PacketReader packetReader = this;
        long value = 0L;
        switch (byteOrder) {
            case BIG: {
                value = 0L | (long)packetReader.readUnsignedByte(false) << 56;
                value |= (long)packetReader.readUnsignedByte(false) << 48;
                value |= (long)packetReader.readUnsignedByte(false) << 40;
                value |= (long)packetReader.readUnsignedByte(false) << 32;
                value |= (long)packetReader.readUnsignedByte(false) << 24;
                value |= (long)packetReader.readUnsignedByte(false) << 16;
                value |= (long)packetReader.readUnsignedByte(false) << 8;
                value |= (long)packetReader.readByte(false, byteTransform);
                break;
            }
            case MIDDLE: {
                throw new UnsupportedOperationException("middle-endian long is not implemented!");
            }
            case INVERSE_MIDDLE: {
                throw new UnsupportedOperationException("inverse-middle-endian long is not implemented!");
            }
            case LITTLE: {
                value = 0L | (long)packetReader.readByte(false, byteTransform);
                value |= (long)packetReader.readUnsignedByte(false) << 8;
                value |= (long)packetReader.readUnsignedByte(false) << 16;
                value |= (long)packetReader.readUnsignedByte(false) << 24;
                value |= (long)packetReader.readUnsignedByte(false) << 32;
                value |= (long)packetReader.readUnsignedByte(false) << 40;
                value |= (long)packetReader.readUnsignedByte(false) << 48;
                value |= (long)packetReader.readUnsignedByte(false) << 56;
            }
        }
        return value;
    }

    public final String readString() {
        byte value;
        StringBuilder stringBuilder = new StringBuilder();
        while ((value = (byte)this.readSignedByte()) != 10) {
            stringBuilder.append((char)value);
        }
        return stringBuilder.toString();
    }

    public final byte[] readBytes(int value3) {
        ByteTransform byteTransform = ByteTransform.NONE;
        int value2 = value3;
        PacketReader packetReader = this;
        byte[] byteValues = new byte[value2];
        int index = 0;
        while (index < value2) {
            ByteTransform byteTransform2 = byteTransform;
            PacketReader packetReader2 = packetReader;
            byteValues[index] = (byte)packetReader2.readByte(true, byteTransform2);
            ++index;
        }
        return byteValues;
    }

    public final byte[] readBytesReverse(int value3, ByteTransform byteTransform) {
        byte[] byteValues = new byte[value3];
        int index = 0;
        value3 = this.buffer.position() + value3 - 1;
        while (value3 >= this.buffer.position()) {
            int value2 = this.buffer.get(value3);
            switch (byteTransform) {
                case ADD: {
                    value2 -= 128;
                    break;
                }
                case NEGATE: {
                    value2 = -value2;
                    break;
                }
                case SUBTRACT: {
                    value2 = 128 - value2;
                }
            }
            byteValues[index++] = (byte)value2;
            --value3;
        }
        return byteValues;
    }

    public final ByteBuffer getBuffer() {
        return this.buffer;
    }

    PacketReader(ByteBuffer byteBuffer, byte value2) {
        this(byteBuffer);
    }

    PacketReader(ByteBuffer byteBuffer, int value2) {
        this(byteBuffer);
    }
}

