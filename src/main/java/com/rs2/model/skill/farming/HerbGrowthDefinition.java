package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum HerbGrowthDefinition {
    GUAM(5291, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    MARRENTILL(5292, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    TARROMIN(5293, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    HARRALANDER(5294, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    GOUTWEED(6311, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    RANARR(5295, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    TOADFLAX(5296, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    IRIT(5297, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    AVANTOE(5298, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    KWUARM(5299, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    SNAPDRAGON(5300, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    CADANTINE(5301, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    LANTADYME(5302, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    DWARF_WEED(5303, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}}),
    TORSTOL(5304, (String[][])new String[][]{{"The seed has only just been planted."}, {"The herb is now ankle height."}, {"The herb is now knee height."}, {"The herb is now mid-thigh height."}, {"The herb is fully grown and ready to harvest."}});

    private int cropId;
    private String[][] growthMessages;
    private static Map definitionsByCropId;

    static {
        definitionsByCropId = new HashMap();
        HerbGrowthDefinition[] herbGrowthDefinitionArray = HerbGrowthDefinition.values();
        int length = herbGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            HerbGrowthDefinition herbGrowthDefinition = herbGrowthDefinitionArray[index];
            definitionsByCropId.put(herbGrowthDefinition.cropId, herbGrowthDefinition);
            ++index;
        }
    }
    private HerbGrowthDefinition(int cropId, String[][] growthMessages) {
        this.cropId = cropId;
        this.growthMessages = growthMessages;
    }

    public static HerbGrowthDefinition forCropId(int value2) {
        return (HerbGrowthDefinition)((Object)definitionsByCropId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

