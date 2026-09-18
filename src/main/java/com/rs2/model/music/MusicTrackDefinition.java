package com.rs2.model.music;

import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;

public final class MusicTrackDefinition {
    private int trackId;
    private String name;
    private int buttonId;
    private int unlockConfigId;
    private int unlockBitMask;
    public static int trackCount = 0;
    private static MusicTrackDefinition[] definitionsByTrackId = new MusicTrackDefinition[0];

    public static void loadDefinitions() {
        Object value = FileUtil.readBytes("./data/Songs.dat");
        ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])value);
        value = byteArrayReader;
        trackCount = byteArrayReader.readUnsignedShort();
        definitionsByTrackId = new MusicTrackDefinition[trackCount];
        int index = 0;
        while (index < trackCount) {
            int value2;
            int value3;
            int value4;
            String text;
            int value5 = ((ByteArrayReader)value).readUnsignedByte();
            if (value5 == 0) {
                MusicTrackDefinition.definitionsByTrackId[index] = new MusicTrackDefinition(index, " ", -1, -1, -1);
            }
            if (value5 == 1) {
                text = ((ByteArrayReader)value).readString();
                MusicTrackDefinition.definitionsByTrackId[index] = new MusicTrackDefinition(index, text, -1, -1, -1);
            }
            if (value5 == 2) {
                text = ((ByteArrayReader)value).readString();
                value4 = ((ByteArrayReader)value).readUnsignedShort();
                value3 = ((ByteArrayReader)value).readUnsignedByte();
                value2 = (int)Math.pow(2.0, value3 - 1);
                if (value3 == 32) {
                    value2 = Integer.MIN_VALUE;
                }
                value3 = ((ByteArrayReader)value).readUnsignedShort();
                MusicTrackDefinition.definitionsByTrackId[index] = new MusicTrackDefinition(index, text, value3, value4, value2);
            }
            if (value5 == 3) {
                text = ((ByteArrayReader)value).readString();
                value3 = ((ByteArrayReader)value).readUnsignedShort();
                MusicTrackDefinition.definitionsByTrackId[index] = new MusicTrackDefinition(index, text, value3, -1, -1);
            }
            if (value5 == 4) {
                text = ((ByteArrayReader)value).readString();
                value4 = ((ByteArrayReader)value).readUnsignedShort();
                value3 = ((ByteArrayReader)value).readUnsignedByte();
                value2 = (int)Math.pow(2.0, value3 - 1);
                if (value3 == 32) {
                    value2 = Integer.MIN_VALUE;
                }
                MusicTrackDefinition.definitionsByTrackId[index] = new MusicTrackDefinition(index, text, -1, value4, value2);
            }
            ++index;
        }
    }

    private MusicTrackDefinition(int trackId, String name, int buttonId, int unlockConfigId, int unlockBitMask) {
        this.trackId = trackId;
        this.name = name;
        this.buttonId = buttonId;
        this.unlockConfigId = unlockConfigId;
        this.unlockBitMask = unlockBitMask;
    }

    public final int getTrackId() {
        return this.trackId;
    }

    public final String getName() {
        return this.name;
    }

    public final int getButtonId() {
        return this.buttonId;
    }

    public final int getUnlockConfigId() {
        return this.unlockConfigId;
    }

    public final int getUnlockBitMask() {
        return this.unlockBitMask;
    }

    public static MusicTrackDefinition forTrackId(int trackId) {
        MusicTrackDefinition musicTrackDefinition;
        if (trackId < 0) {
            trackId = 1;
        }
        if ((musicTrackDefinition = definitionsByTrackId[trackId]) == null) {
            musicTrackDefinition = new MusicTrackDefinition(trackId, " ", -1, -1, -1);
        }
        return musicTrackDefinition;
    }
}

