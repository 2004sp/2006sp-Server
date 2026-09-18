package com.rs2.model.skill.fishing;

public enum FishingWhirlpool {
    RIVER_WHIRLPOOL(403, new int[]{309}),
    NET_BAIT_WHIRLPOOL(404, new int[]{316}),
    CAGE_HARPOON_WHIRLPOOL(405, new int[]{312}),
    BIG_NET_HARPOON_WHIRLPOOL(406, new int[]{313}),
    MONKFISH_WHIRLPOOL(3849, new int[]{3848});

    private int whirlpoolNpcId;
    private int[] sourceNpcIds;
    private FishingWhirlpool(int whirlpoolNpcId, int[] sourceNpcIds) {
        this.whirlpoolNpcId = whirlpoolNpcId;
        this.sourceNpcIds = sourceNpcIds;
    }

    public final int[] getSourceNpcIds() {
        return this.sourceNpcIds;
    }

    public final int getWhirlpoolNpcId() {
        return this.whirlpoolNpcId;
    }

    public static FishingWhirlpool forSourceNpcId(int npcId) {
        FishingWhirlpool[] fishingWhirlpoolArray = FishingWhirlpool.values();
        int length = fishingWhirlpoolArray.length;
        int index = 0;
        while (index < length) {
            FishingWhirlpool fishingWhirlpool;
            FishingWhirlpool fishingWhirlpool2 = fishingWhirlpool = fishingWhirlpoolArray[index];
            int[] integerValues = fishingWhirlpool.sourceNpcIds;
            int length2 = fishingWhirlpool.sourceNpcIds.length;
            int index2 = 0;
            while (index2 < length2) {
                int value = integerValues[index2];
                if (value == npcId) {
                    return fishingWhirlpool;
                }
                ++index2;
            }
            ++index;
        }
        return null;
    }

    public static FishingWhirlpool forWhirlpoolNpcId(int npcId) {
        FishingWhirlpool[] fishingWhirlpoolArray = FishingWhirlpool.values();
        int length = fishingWhirlpoolArray.length;
        int index = 0;
        while (index < length) {
            FishingWhirlpool fishingWhirlpool;
            FishingWhirlpool fishingWhirlpool2 = fishingWhirlpool = fishingWhirlpoolArray[index];
            if (fishingWhirlpool.whirlpoolNpcId == npcId) {
                return fishingWhirlpool;
            }
            ++index;
        }
        return null;
    }
}

