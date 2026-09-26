package com.rs2.cache;

import com.rs2.ServerSettings;
import com.rs2.cache.js5.Interfaces;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheStore;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketReader;

public final class InterfaceDefinition {
    public static int interfaceCount = 0;
    private static InterfaceDefinition[] definitionsById;
    private static int[] childXById;
    private static int[] childYById;
    private final int interfaceId;
    private final int widgetType;
    private final int actionType;
    private final int parentInterfaceId;
    private final int width;
    private final int height;

    private InterfaceDefinition(int interfaceId, int widgetType, int actionType,
                                int parentInterfaceId, int width, int height,
                                boolean enabled2) {
        this.interfaceId = interfaceId;
        this.widgetType = widgetType;
        this.actionType = actionType;
        this.parentInterfaceId = parentInterfaceId;
        this.width = width;
        this.height = height;
    }

    public final int getInterfaceId() {
        return this.interfaceId;
    }

    public final int getParentInterfaceId() {
        return this.parentInterfaceId;
    }

    public final int getWidgetType() {
        return this.widgetType;
    }

    public final int getActionType() {
        return this.actionType;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getParentChildX() {
        return childXById == null || interfaceId < 0 || interfaceId >= childXById.length
                ? Integer.MIN_VALUE : childXById[interfaceId];
    }

    public final int getParentChildY() {
        return childYById == null || interfaceId < 0 || interfaceId >= childYById.length
                ? Integer.MIN_VALUE : childYById[interfaceId];
    }

    public static InterfaceDefinition forId(int value2) {
        if (value2 < 0 || value2 >= interfaceCount) {
            return null;
        }
        return definitionsById[value2];
    }

    public static void loadDefinitions() {
        if (ServerSettings.cacheVersion == 443) {
            try {
                Interfaces.load();
            } catch (java.io.IOException exception) {
                throw new IllegalStateException("Unable to load revision 443 interfaces", exception);
            }
            return;
        }
        try {
            Object instance = new CacheArchive(CacheStore.getInstance().readFile(0, 3));
            instance = PacketBuffer.wrapReader(((CacheArchive)instance).getFileBuffer("data"));
            interfaceCount = ((PacketReader)instance).readSignedShort();
            definitionsById = new InterfaceDefinition[interfaceCount];
            childXById = new int[interfaceCount];
            childYById = new int[interfaceCount];
            for (int interfaceId = 0; interfaceId < interfaceCount; ++interfaceId) {
                childXById[interfaceId] = Integer.MIN_VALUE;
                childYById[interfaceId] = Integer.MIN_VALUE;
            }
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
                int widgetWidth = value4;
                int widgetHeight = value5;
                if (ServerSettings.cacheVersion > 237) {
                    ((PacketReader)instance).readSignedByte();
                }
                int value6 = ((PacketReader)instance).readSignedByte();
                int value7 = value4 = value4 == 512 && value5 == 334 ? 1 : 0;
                if (value < interfaceCount && value > 0) {
                    InterfaceDefinition.definitionsById[value] =
                            new InterfaceDefinition(value, value2, value3, initialValue,
                                    widgetWidth, widgetHeight, value4 != 0);
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
                    int childIndex = 0;
                    while (childIndex < value) {
                        int childId = ((PacketReader)instance).readSignedShort();
                        int childX = ((PacketReader)instance).readSignedShort();
                        int childY = ((PacketReader)instance).readSignedShort();
                        if (childId >= 0 && childId < interfaceCount) {
                            childXById[childId] = childX;
                            childYById[childId] = childY;
                        }
                        ++childIndex;
                    }
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

