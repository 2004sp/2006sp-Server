package com.rs2.net.packet;

import com.rs2.net.IsaacCipher;
import com.rs2.net.packet.AccessMode;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.PacketBuffer;
import java.nio.ByteBuffer;

public final class PacketWriter
extends PacketBuffer {
    private ByteBuffer buffer;
    private int lengthPlaceholderPosition = 0;

    private PacketWriter(int value2) {
        this.buffer = ByteBuffer.allocate(value2);
    }

    @Override
    final void onAccessModeChanged(AccessMode accessMode) {
        switch (accessMode) {
            case BIT_ACCESS: {
                this.setBitPosition(this.buffer.position() << 3);
                return;
            }
            case BYTE_ACCESS: {
                this.buffer.position((this.getBitPosition() + 7) / 8);
            }
        }
    }

    public final void writeOpcode(IsaacCipher isaacCipher, int opcode) {
        this.writeByte(opcode + isaacCipher.nextInt());
    }

    public final void startVariableBytePacket(IsaacCipher isaacCipher, int packetId) {
        this.writeOpcode(isaacCipher, packetId);
        this.lengthPlaceholderPosition = this.buffer.position();
        this.writeByte(0);
    }

    public final void startVariableShortPacket(IsaacCipher isaacCipher, int packetId) {
        this.writeOpcode(isaacCipher, packetId);
        this.lengthPlaceholderPosition = this.buffer.position();
        this.writeShort(0);
    }

    public final void finishVariableBytePacket() {
        this.buffer.put(this.lengthPlaceholderPosition, (byte)(this.buffer.position() - this.lengthPlaceholderPosition - 1));
    }

    public final void finishVariableShortPacket() {
        this.buffer.putShort(this.lengthPlaceholderPosition, (short)(this.buffer.position() - this.lengthPlaceholderPosition - 2));
    }

    public final void writeBuffer(ByteBuffer byteBuffer) {
        int index = 0;
        while (index < byteBuffer.position()) {
            this.writeByte(byteBuffer.get(index));
            ++index;
        }
    }

    public final void writeBytes(byte[] byteValues2, int value2) {
        this.buffer.put(byteValues2, 0, value2);
    }

    public final void writeBits(int value7, int value22) {
        if (this.getAccessMode() != AccessMode.BIT_ACCESS) {
            throw new IllegalStateException("Illegal access type.");
        }
        if (value7 < 0 || value7 > 32) {
            throw new IllegalArgumentException("Number of bits must be between 1 and 32 inclusive.");
        }
        int bitPosition = this.getBitPosition() >> 3;
        int bitPosition2 = 8 - (this.getBitPosition() & 7);
        this.setBitPosition(this.getBitPosition() + value7);
        int value3 = bitPosition - this.buffer.position() + 1;
        if (this.buffer.remaining() < (value3 += (value7 + 7) / 8)) {
            ByteBuffer byteBuffer = this.buffer;
            this.buffer = ByteBuffer.allocate(byteBuffer.capacity() + value3);
            byteBuffer.flip();
            this.buffer.put(byteBuffer);
        }
        while (value7 > bitPosition2) {
            byte value4 = this.buffer.get(bitPosition);
            value4 = (byte)(value4 & ~BIT_MASKS[bitPosition2]);
            value4 = (byte)(value4 | value22 >> value7 - bitPosition2 & BIT_MASKS[bitPosition2]);
            this.buffer.put(bitPosition++, value4);
            value7 -= bitPosition2;
            bitPosition2 = 8;
        }
        if (value7 == bitPosition2) {
            byte value5 = this.buffer.get(bitPosition);
            value5 = (byte)(value5 & ~BIT_MASKS[bitPosition2]);
            value5 = (byte)(value5 | value22 & BIT_MASKS[bitPosition2]);
            this.buffer.put(bitPosition, value5);
            return;
        }
        byte value6 = this.buffer.get(bitPosition);
        value6 = (byte)(value6 & ~(BIT_MASKS[value7] << bitPosition2 - value7));
        value6 = (byte)(value6 | (value22 & BIT_MASKS[value7]) << bitPosition2 - value7);
        this.buffer.put(bitPosition, value6);
    }

    public final void writeBoolean(boolean enabled2) {
        this.writeBits(1, enabled2 ? 1 : 0);
    }

    public final void writeByte(int value2, ByteTransform byteTransform) {
        if (this.getAccessMode() != AccessMode.BYTE_ACCESS) {
            throw new IllegalStateException("Illegal access type.");
        }
        switch (byteTransform) {
            case ADD: {
                value2 += 128;
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
        this.buffer.put((byte)value2);
    }

    public final void writeByte(int value2) {
        this.writeByte(value2, ByteTransform.NONE);
    }

    public final void writeShort(int value2, ByteTransform byteTransform, ByteOrder byteOrder) {
        switch (byteOrder) {
            case BIG: {
                this.writeByte(value2 >> 8);
                this.writeByte(value2, byteTransform);
                return;
            }
            case MIDDLE: {
                throw new IllegalArgumentException("Middle-endian short is impossible!");
            }
            case INVERSE_MIDDLE: {
                throw new IllegalArgumentException("Inverse-middle-endian short is impossible!");
            }
            case LITTLE: {
                this.writeByte(value2, byteTransform);
                this.writeByte(value2 >> 8);
            }
        }
    }

    public final void writeShort(int value2) {
        this.writeShort(value2, ByteTransform.NONE, ByteOrder.BIG);
    }

    public final void writeShort(int value2, ByteTransform byteTransform) {
        this.writeShort(value2, byteTransform, ByteOrder.BIG);
    }

    public final void writeShort(int value2, ByteOrder byteOrder) {
        this.writeShort(value2, ByteTransform.NONE, byteOrder);
    }

    private void writeInt(int value2, ByteTransform byteTransform, ByteOrder byteOrder) {
        switch (byteOrder) {
            case BIG: {
                this.writeByte(value2 >> 24);
                this.writeByte(value2 >> 16);
                this.writeByte(value2 >> 8);
                this.writeByte(value2, byteTransform);
                return;
            }
            case MIDDLE: {
                this.writeByte(value2 >> 8);
                this.writeByte(value2, byteTransform);
                this.writeByte(value2 >> 24);
                this.writeByte(value2 >> 16);
                return;
            }
            case INVERSE_MIDDLE: {
                this.writeByte(value2 >> 16);
                this.writeByte(value2 >> 24);
                this.writeByte(value2, byteTransform);
                this.writeByte(value2 >> 8);
                return;
            }
            case LITTLE: {
                this.writeByte(value2, byteTransform);
                this.writeByte(value2 >> 8);
                this.writeByte(value2 >> 16);
                this.writeByte(value2 >> 24);
            }
        }
    }

    public final void writeInt(int value2) {
        this.writeInt(value2, ByteTransform.NONE, ByteOrder.BIG);
    }

    public final void writeInt(int value2, ByteOrder byteOrder) {
        this.writeInt(value2, ByteTransform.NONE, byteOrder);
    }

    public final void writeLong(long value3) {
        ByteOrder byteOrder = ByteOrder.BIG;
        ByteTransform byteTransform = ByteTransform.NONE;
        long value2 = value3;
        PacketWriter packetWriter = this;
        switch (byteOrder) {
            case BIG: {
                packetWriter.writeByte((int)(value2 >> 56));
                packetWriter.writeByte((int)(value2 >> 48));
                packetWriter.writeByte((int)(value2 >> 40));
                packetWriter.writeByte((int)(value2 >> 32));
                packetWriter.writeByte((int)(value2 >> 24));
                packetWriter.writeByte((int)(value2 >> 16));
                packetWriter.writeByte((int)(value2 >> 8));
                packetWriter.writeByte((int)value2, byteTransform);
                return;
            }
            case MIDDLE: {
                throw new UnsupportedOperationException("Middle-endian long is not implemented!");
            }
            case INVERSE_MIDDLE: {
                throw new UnsupportedOperationException("Inverse-middle-endian long is not implemented!");
            }
            case LITTLE: {
                packetWriter.writeByte((int)value2, byteTransform);
                packetWriter.writeByte((int)(value2 >> 8));
                packetWriter.writeByte((int)(value2 >> 16));
                packetWriter.writeByte((int)(value2 >> 24));
                packetWriter.writeByte((int)(value2 >> 32));
                packetWriter.writeByte((int)(value2 >> 40));
                packetWriter.writeByte((int)(value2 >> 48));
                packetWriter.writeByte((int)(value2 >> 56));
            }
        }
    }

    public final void writeString(String text2) {
        byte[] bytes = text2.getBytes();
        int length = bytes.length;
        int index = 0;
        while (index < length) {
            byte value = bytes[index];
            this.writeByte(value);
            ++index;
        }
        this.writeByte(10);
    }

    public final ByteBuffer getBuffer() {
        return this.buffer;
    }

    PacketWriter(int value3, byte value4) {
        this(value3);
    }

    PacketWriter(int value3, int value22) {
        this(value3);
    }
}

