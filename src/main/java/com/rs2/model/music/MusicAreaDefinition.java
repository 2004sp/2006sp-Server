package com.rs2.model.music;

import com.rs2.CacheCoordinateTranslator;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;

public final class MusicAreaDefinition {
    private int areaId;
    private int regionCount;
    private int priority;
    private int trackId;
    private RectangularArea areaBounds;
    private int[] regionIds;
    public static int areaCount = 0;
    private static MusicAreaDefinition[] definitionsByAreaId = new MusicAreaDefinition[0];

    public static void loadDefinitions() {
        Object value = FileUtil.readBytes("./data/areas/Music.dat");
        ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])value);
        value = byteArrayReader;
        areaCount = byteArrayReader.readUnsignedShort();
        definitionsByAreaId = new MusicAreaDefinition[areaCount];
        int index = 0;
        while (index < areaCount) {
            int value2;
            int value3;
            int value4;
            int[] integerValues = null;
            RectangularArea rectangularArea = null;
            int value5 = ((ByteArrayReader)value).readUnsignedByte();
            int value6 = ((ByteArrayReader)value).readUnsignedByte();
            int value7 = ((ByteArrayReader)value).readUnsignedShort();
            if (value5 == 0) {
                int value8 = ((ByteArrayReader)value).readUnsignedShort();
                value4 = ((ByteArrayReader)value).readUnsignedShort();
                value3 = ((ByteArrayReader)value).readUnsignedShort();
                value2 = ((ByteArrayReader)value).readUnsignedShort();
                rectangularArea = new RectangularArea(value8, value4, value3, value2, 0);
            } else {
                integerValues = new int[value5];
                value4 = 0;
                while (value4 < value5) {
                    integerValues[value4] = ((ByteArrayReader)value).readUnsignedShort();
                    ++value4;
                }
            }
            if (ServerSettings.cacheVersion < 319 && value7 == 28) {
                value5 = 0;
                value6 = 1;
                rectangularArea = new RectangularArea(2706, 9801, 2731, 9830, 0);
            }
            if (CacheCoordinateTranslator.dungeonCoordinateShiftActive) {
                if (value7 == 181 || value7 == 118) {
                    int[] integerValues2 = new int[integerValues.length];
                    value3 = 0;
                    while (value3 < integerValues.length) {
                        Position position = GameUtil.getRegionBasePosition(integerValues[value3]);
                        int x = position.getX() + 768;
                        value2 = position.getY() + 5120;
                        integerValues2[value3] = value2 = GameUtil.getRegionId(x, value2);
                        ++value3;
                    }
                    integerValues = integerValues2;
                }
                if (value7 == 389) {
                    value5 = 0;
                    value6 = 0;
                    rectangularArea = new RectangularArea(0, 0, 0, 0, 0);
                }
            }
            MusicAreaDefinition.definitionsByAreaId[index] = new MusicAreaDefinition(index, value5, value6, value7, rectangularArea, integerValues);
            ++index;
        }
    }

    private MusicAreaDefinition(int areaId, int regionCount, int priority, int trackId, RectangularArea rectangularArea, int[] regionIds) {
        this.areaId = areaId;
        this.regionCount = regionCount;
        this.priority = priority;
        this.trackId = trackId;
        this.areaBounds = rectangularArea;
        this.regionIds = regionIds;
    }

    public final int getAreaId() {
        return this.areaId;
    }

    public final int getRegionCount() {
        return this.regionCount;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final int getTrackId() {
        return this.trackId;
    }

    public final RectangularArea getAreaBounds() {
        return this.areaBounds;
    }

    public final int[] getRegionIds() {
        return this.regionIds;
    }

    public static MusicAreaDefinition forAreaId(int value2) {
        MusicAreaDefinition musicAreaDefinition;
        if (value2 < 0) {
            value2 = 1;
        }
        if ((musicAreaDefinition = definitionsByAreaId[value2]) == null) {
            musicAreaDefinition = new MusicAreaDefinition(value2, 0, 0, 0, null, null);
        }
        return musicAreaDefinition;
    }
}

