package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum HopsGrowthDefinition {
    BARLEY(5305, (String[][])new String[][]{{"The barley seeds have only just been planted."}, {"Grain heads develop at the upper part of the stalks,", "as the barley grows taller."}, {"The barley grows taller, the heads weighing", "slightly on the stalks."}, {"The barley grows taller."}, {"The barley is ready to harvest. The heads of grain", "are weighing down heavily on the stalks!"}}),
    HAMMERSTONE(5307, (String[][])new String[][]{{"The Hammerstone seeds have only just been planted."}, {"The Hammerstone hops plant grows a little bit taller."}, {"The Hammerstone hops plant grows a bit taller."}, {"The Hammerstone hops plant grows a bit taller."}, {"The Hammerstone hops plant is ready to harvest."}}),
    ASGARNIAN(5308, (String[][])new String[][]{{"The Asgarnian seeds have only just been planted."}, {"The Asgarnian hops plant grows a bit taller."}, {"The Asgarnian hops plant grows a bit taller."}, {"The Asgarnian hops plant grows a bit taller."}, {"The upper new leaves appear dark green to the", "rest of the plant."}, {"The Asgarnian hops plant is ready to harvest."}}),
    JUTE(5306, (String[][])new String[][]{{"The Jute seeds have only just been planted."}, {"The jute plants grow taller."}, {"The jute plants grow taller."}, {"The jute plants grow taller."}, {"The jute plant grows taller. They are as high", "as the player."}, {"The jute plants are ready to harvest."}}),
    YANILLIAN(5309, (String[][])new String[][]{{"The Yanillian seeds have only just been planted."}, {"The Yanillian hops plant grows a bit taller."}, {"The Yanillian hops plant grows a bit taller."}, {"The Yanillian hops plant grows a bit taller."}, {"The new leaves on the top of the Yanillian hops", "plant are dark green."}, {"The new leaves on the top of the Yanillian hops", "plant are dark green."}, {"The Yanillian hops plant is ready to harvest."}}),
    KRANDORIAN(5310, (String[][])new String[][]{{"The Krandorian seeds have only just been planted."}, {"The Krandorian plant grows a bit taller."}, {"The Krandorian plant grows a bit taller."}, {"The Krandorian plant grows a bit taller."}, {"The new leaves on top of the Krandorian plant are", "dark green."}, {"The Krandorian plant grows a bit taller."}, {"The new leaves on top of the Krandorian plant", "are dark green."}, {"The Krandorian plant is ready for harvesting."}}),
    WILDBLOOD(5311, (String[][])new String[][]{{"The wildblood seeds have only just been planted."}, {"The wildblood hops plant grows a bit taller."}, {"The wildblood hops plant grows a bit taller."}, {"The wildblood hops plant grows a bit taller."}, {"The wildblood hops plant grows a bit taller."}, {"The wildblood hops plant grows a bit taller."}, {"The wildblood hops plant grows a bit taller."}, {"The new leaves at the top of the wildblood hops plant", "are dark green."}, {"The wildblood hops plant is ready to harvest."}});

    private int cropId;
    private String[][] growthMessages;
    private static Map definitionsByCropId;

    static {
        definitionsByCropId = new HashMap();
        HopsGrowthDefinition[] hopsGrowthDefinitionArray = HopsGrowthDefinition.values();
        int length = hopsGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            HopsGrowthDefinition hopsGrowthDefinition = hopsGrowthDefinitionArray[index];
            definitionsByCropId.put(hopsGrowthDefinition.cropId, hopsGrowthDefinition);
            ++index;
        }
    }
    private HopsGrowthDefinition(int cropId, String[][] growthMessages) {
        this.cropId = cropId;
        this.growthMessages = growthMessages;
    }

    public static HopsGrowthDefinition forCropId(int value2) {
        return (HopsGrowthDefinition)((Object)definitionsByCropId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

