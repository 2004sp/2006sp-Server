package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum SaplingDefinition {
    OAK(5312, 5358, 5364, 5370),
    WILLOW(5313, 5359, 5365, 5371),
    MAPLE(5314, 5360, 5366, 5372),
    YEW(5315, 5361, 5367, 5373),
    MAGIC(5316, 5362, 5368, 5374),
    SPIRIT_TREE(5317, 5363, 5369, 5375),
    APPLE(5283, 5480, 5488, 5496),
    BANANA(5284, 5481, 5489, 5497),
    ORANGE(5285, 5482, 5490, 5498),
    CURRY(5286, 5483, 5491, 5499),
    PINEAPPLE(5287, 5484, 5492, 5500),
    PAPAYA(5288, 5485, 5493, 5501),
    PALM(5289, 5486, 5494, 5502),
    CALQUAT(5290, 5487, 5495, 5503);

    private int seedId;
    private int seedlingId;
    private int wateredSeedlingId;
    private int saplingId;
    private static Map definitionsBySeedId;
    private static Map definitionsBySeedlingId;
    private static Map definitionsByWateredSeedlingId;

    static {
        definitionsBySeedId = new HashMap();
        definitionsBySeedlingId = new HashMap();
        definitionsByWateredSeedlingId = new HashMap();
        SaplingDefinition[] saplingDefinitionArray = SaplingDefinition.values();
        int length = saplingDefinitionArray.length;
        int index = 0;
        while (index < length) {
            SaplingDefinition saplingDefinition = saplingDefinitionArray[index];
            definitionsBySeedId.put(saplingDefinition.seedId, saplingDefinition);
            definitionsBySeedlingId.put(saplingDefinition.seedlingId, saplingDefinition);
            definitionsByWateredSeedlingId.put(saplingDefinition.wateredSeedlingId, saplingDefinition);
            ++index;
        }
    }

    private SaplingDefinition(int seedId, int seedlingId, int wateredSeedlingId, int saplingId) {
        this.seedId = seedId;
        this.seedlingId = seedlingId;
        this.wateredSeedlingId = wateredSeedlingId;
        this.saplingId = saplingId;
    }

    public static SaplingDefinition forSeedId(int value2) {
        return (SaplingDefinition)((Object)definitionsBySeedId.get(value2));
    }

    public static SaplingDefinition forSeedlingId(int value2) {
        return (SaplingDefinition)((Object)definitionsBySeedlingId.get(value2));
    }

    public static SaplingDefinition forWateredSeedlingId(int value2) {
        return (SaplingDefinition)((Object)definitionsByWateredSeedlingId.get(value2));
    }

    public final int getSeedId() {
        return this.seedId;
    }

    public final int getSeedlingId() {
        return this.seedlingId;
    }

    public final int getWateredSeedlingId() {
        return this.wateredSeedlingId;
    }

    public final int getSaplingId() {
        return this.saplingId;
    }
}

