package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum BushGrowthDefinition {
    REDBERRY(5101, (String[][])new String[][]{{"The Redberry seeds have only just been planted."}, {"The Redberry bush grows larger."}, {"The Redberry bush grows larger."}, {"The Redberry bush grows small, unripe,", "green berries."}, {"The berries grow larger, and pink."}, {"The Redberry bush is ready to harvest.", "The berries on the bush are red."}}),
    CADAVABERRY(5102, (String[][])new String[][]{{"The Cadavaberry seeds have only just been planted."}, {"The Cadavaberry bush grows larger."}, {"The Cadavaberry bush grows larger."}, {"The Cadavaberry bush grows larger."}, {"The Cadavaberry bush grows small, unripe,", "green berries."}, {"The berries grow larger, and pink."}, {"The Cadavaberry bush is ready to harvest.", "The berries on the bush are purple."}}),
    DWELLBERRY(5103, (String[][])new String[][]{{"The Dwellbery seeds have only just been planted."}, {"The Dwellbery bush grows larger."}, {"The Dwellbery bush grows larger."}, {"The Dwellbery bush grows larger."}, {"The Dwellbery bush grows larger."}, {"The Dwellbery bush grows small, unripe,", "green berries."}, {"The berries grow larger, and light blue."}, {"The Dwellbery bush is ready to harvest.", "The berries on the bush are blue."}}),
    JANGERBERRY(5104, (String[][])new String[][]{{"The Jangerberry seeds have only just been planted."}, {"The Jangerberry bush grows larger."}, {"The Jangerberry bush grows larger."}, {"The Jangerberry bush grows larger."}, {"The Jangerberry bush grows larger."}, {"The Jangerberry bush grows small, unripe,", "green berries."}, {"The berries grow larger."}, {"The berries grow larger, and light green."}, {"The Jangerberry bush is ready to harvest.", "The berries on the bush are green."}}),
    WHITEBERRY(5105, (String[][])new String[][]{{"The Whiteberry seeds have only just been planted."}, {"The Whiteberry bush grows larger."}, {"The Whiteberry bush grows larger."}, {"The Whiteberry bush grows larger."}, {"The Whiteberry bush grows larger."}, {"The Whiteberry bush grows larger."}, {"The Whiteberry bush grows small, unripe,", "green berries."}, {"The berries grow larger."}, {"The Whiteberry bush is ready to harvest.", "The berries on the bush are white."}}),
    POISON_IVY(5106, (String[][])new String[][]{{"The Poison ivy seeds have only just been planted."}, {"The Poison ivy bush grows larger."}, {"The Poison ivy bush grows larger."}, {"The Poison ivy bush grows larger."}, {"The Poison ivy bush grows larger."}, {"The Poison ivy bush grows small, unripe,", "green berries."}, {"The berries grow larger."}, {"The berries grow larger, and light green."}, {"The Poison ivy bush is ready to harvest.", "The berries on the bush are pale yellow."}});

    private int cropId;
    private String[][] growthMessages;
    private static Map definitionsByCropId;

    static {
        definitionsByCropId = new HashMap();
        BushGrowthDefinition[] bushGrowthDefinitionArray = BushGrowthDefinition.values();
        int length = bushGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            BushGrowthDefinition bushGrowthDefinition = bushGrowthDefinitionArray[index];
            definitionsByCropId.put(bushGrowthDefinition.cropId, bushGrowthDefinition);
            ++index;
        }
    }
    private BushGrowthDefinition(int cropId, String[][] growthMessages) {
        this.cropId = cropId;
        this.growthMessages = growthMessages;
    }

    public static BushGrowthDefinition forCropId(int value2) {
        return (BushGrowthDefinition)((Object)definitionsByCropId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

