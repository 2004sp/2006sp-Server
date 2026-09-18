package com.rs2.model.area;

import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.RectangularArea;

public final class MultiwayAreaDefinition {
    private int regionCount;
    private RectangularArea areaBounds;
    private int[] regionIds;
    private static int definitionCount = 0;
    public static MultiwayAreaDefinition[] definitions = new MultiwayAreaDefinition[0];

    public static void loadDefinitions() {
        Object value = FileUtil.readBytes("./data/areas/Multiway.dat");
        ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])value);
        value = byteArrayReader;
        definitionCount = byteArrayReader.readUnsignedShort();
        definitions = new MultiwayAreaDefinition[definitionCount];
        int index = 0;
        while (index < definitionCount) {
            int value2;
            int[] integerValues = null;
            RectangularArea rectangularArea = null;
            int value3 = ((ByteArrayReader)value).readUnsignedByte();
            if (value3 == 0) {
                int value4 = ((ByteArrayReader)value).readUnsignedShort();
                value2 = ((ByteArrayReader)value).readUnsignedShort();
                int value5 = ((ByteArrayReader)value).readUnsignedShort();
                int value6 = ((ByteArrayReader)value).readUnsignedShort();
                rectangularArea = new RectangularArea(value4, value2, value5, value6, 0);
            } else {
                integerValues = new int[value3];
                value2 = 0;
                while (value2 < value3) {
                    integerValues[value2] = ((ByteArrayReader)value).readUnsignedShort();
                    ++value2;
                }
            }
            MultiwayAreaDefinition.definitions[index] = new MultiwayAreaDefinition(index, value3, rectangularArea, integerValues);
            ++index;
        }
    }

    private MultiwayAreaDefinition(int value3, int regionCount, RectangularArea rectangularArea, int[] regionIds) {
        this.regionCount = regionCount;
        this.areaBounds = rectangularArea;
        this.regionIds = regionIds;
    }

    public final RectangularArea getAreaBounds() {
        return this.areaBounds;
    }

    public final int getRegionCount() {
        return this.regionCount;
    }

    public final int[] getRegionIds() {
        return this.regionIds;
    }

    public static MultiwayAreaDefinition forDefinitionId(int value2) {
        MultiwayAreaDefinition multiwayAreaDefinition;
        if (value2 < 0) {
            value2 = 1;
        }
        if ((multiwayAreaDefinition = definitions[value2]) == null) {
            multiwayAreaDefinition = new MultiwayAreaDefinition(value2, 0, null, null);
        }
        return multiwayAreaDefinition;
    }
}

