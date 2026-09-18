package com.rs2.util.path;

public final class MapDataReader {
    private byte[] data;
    private int offset;

    public MapDataReader(byte[] data) {
        this.data = data;
        this.offset = 0;
    }

    public final void skipByte(int value2) {
        ++this.offset;
    }

    public final int readUnsignedByte() {
        return this.data[this.offset++] & 0xFF;
    }

    public final int readUnsignedSmart() {
        int value = this.data[this.offset] & 0xFF;
        if (value < 128) {
            return this.readUnsignedByte();
        }
        MapDataReader mapDataReader = this;
        return (mapDataReader.readUnsignedByte() << 8) + mapDataReader.readUnsignedByte() - 32768;
    }
}

