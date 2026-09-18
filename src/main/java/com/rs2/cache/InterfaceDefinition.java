package com.rs2.cache;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheStore;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketReader;

public final class InterfaceDefinition {
    public static int interfaceCount = 0;
    private static InterfaceDefinition[] definitionsById;
    private int parentInterfaceId;

    private InterfaceDefinition(int value3, byte value4, int parentInterfaceId, boolean enabled2) {
        this.parentInterfaceId = parentInterfaceId;
    }

    public final int getParentInterfaceId() {
        return this.parentInterfaceId;
    }

    public static InterfaceDefinition forId(int value2) {
        if (value2 < 0 || value2 >= interfaceCount) {
            return null;
        }
        return definitionsById[value2];
    }

    public static void loadDefinitions() {
        try {
            Object instance = new CacheArchive(CacheStore.getInstance().readFile(0, 3));
            instance = PacketBuffer.wrapReader(((CacheArchive)instance).getFileBuffer("data"));
            interfaceCount = ((PacketReader)instance).readSignedShort();
            definitionsById = new InterfaceDefinition[interfaceCount];
            int initialValue = -1;
            while (((PacketReader)instance).getBuffer().hasRemaining()) {
                int value = ((PacketReader)instance).readSignedShort();
                if (value == 65535) {
                    initialValue = ((PacketReader)instance).readSignedShort();
                    value = ((PacketReader)instance).readSignedShort();
                }
                int value2 = ((PacketReader)instance).readSignedByte();
                int value3 = ((PacketReader)instance).readSignedByte();
                ((PacketReader)instance).readSignedShort();
                int value4 = ((PacketReader)instance).readSignedShort();
                int value5 = ((PacketReader)instance).readSignedShort();
                if (ServerSettings.cacheVersion > 237) {
                    ((PacketReader)instance).readSignedByte();
                }
                int value6 = ((PacketReader)instance).readSignedByte();
                int value7 = value4 = value4 == 512 && value5 == 334 ? 1 : 0;
                if (value < interfaceCount && value > 0) {
                    InterfaceDefinition.definitionsById[value] = new InterfaceDefinition(value, (byte)value2, initialValue, value4 != 0);
                }
                if (value6 != 0) {
                    ((PacketReader)instance).readSignedByte();
                }
                if ((value = ((PacketReader)instance).readSignedByte()) > 0) {
                    ((PacketReader)instance).readBytes(value * 3);
                }
                if ((value = ((PacketReader)instance).readSignedByte()) > 0) {
                    value4 = 0;
                    while (value4 < value) {
                        value5 = ((PacketReader)instance).readSignedShort();
                        ((PacketReader)instance).readBytes(value5 << 1);
                        ++value4;
                    }
                }
                if (value2 == 0) {
                    ((PacketReader)instance).readSignedShort();
                    ((PacketReader)instance).readSignedByte();
                    value = ((PacketReader)instance).readSignedShort();
                    ((PacketReader)instance).readBytes(value * 6);
                }
                if (value2 == 1) {
                    ((PacketReader)instance).readBytes(3);
                }
                if (value2 == 2) {
                    value4 = 6;
                    if (ServerSettings.cacheVersion < 245) {
                        value4 = 5;
                    }
                    ((PacketReader)instance).readBytes(value4);
                    value5 = 0;
                    while (value5 < 20) {
                        value = ((PacketReader)instance).readSignedByte();
                        if (value == 1) {
                            ((PacketReader)instance).readBytes(4);
                            ((PacketReader)instance).readString();
                        }
                        ++value5;
                    }
                    value5 = 0;
                    while (value5 < 5) {
                        ((PacketReader)instance).readString();
                        ++value5;
                    }
                }
                if (value2 == 3) {
                    ((PacketReader)instance).readSignedByte();
                }
                if (value2 == 4 || value2 == 1) {
                    ((PacketReader)instance).readBytes(3);
                }
                if (value2 == 4) {
                    ((PacketReader)instance).readString();
                    ((PacketReader)instance).readString();
                }
                if (value2 == 1 || value2 == 3 || value2 == 4) {
                    ((PacketReader)instance).readInt();
                    if (value2 != 1) {
                        value4 = 12;
                        if (ServerSettings.cacheVersion < 245) {
                            value4 = 8;
                        }
                        ((PacketReader)instance).readBytes(value4);
                    }
                }
                if (value2 == 5 || value2 == 17 || value2 == 18 || value2 == 19) {
                    ((PacketReader)instance).readString();
                    ((PacketReader)instance).readString();
                    if (value2 == 17) {
                        ((PacketReader)instance).readString();
                        ((PacketReader)instance).readString();
                    }
                }
                if (value2 == 6) {
                    value4 = ((PacketReader)instance).readSignedByte();
                    if (value4 != 0) {
                        ((PacketReader)instance).readSignedByte();
                    }
                    if ((value4 = ((PacketReader)instance).readSignedByte()) != 0) {
                        ((PacketReader)instance).readSignedByte();
                    }
                    if ((value4 = ((PacketReader)instance).readSignedByte()) != 0) {
                        ((PacketReader)instance).readSignedByte();
                    }
                    if ((value4 = ((PacketReader)instance).readSignedByte()) != 0) {
                        ((PacketReader)instance).readSignedByte();
                    }
                    ((PacketReader)instance).readBytes(6);
                }
                if (value2 == 7) {
                    ((PacketReader)instance).readBytes(12);
                    value4 = 0;
                    while (value4 < 5) {
                        ((PacketReader)instance).readString();
                        ++value4;
                    }
                }
                if (value3 == 2 || value2 == 2) {
                    ((PacketReader)instance).readString();
                    ((PacketReader)instance).readString();
                    ((PacketReader)instance).readSignedShort();
                }
                if (value2 == 8) {
                    ((PacketReader)instance).readString();
                }
                if (value3 != 1 && value3 != 4 && value3 != 5 && value3 != 6) continue;
                ((PacketReader)instance).readString();
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }
}

