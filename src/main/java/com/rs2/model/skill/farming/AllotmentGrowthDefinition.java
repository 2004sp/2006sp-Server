package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum AllotmentGrowthDefinition {
    POTATO(5318, (String[][])new String[][]{{"The potato seeds have only just been planted."}, {"The potato plants have grown to double their", "previous height."}, {"The potato plants now are the same height as the", "surrounding weeds."}, {"The potato plants now spread their branches wider,", "not growing as much as before."}, {"The potato plants are ready to harvest. A white", "flower at the top of each plant opens up."}}),
    ONION(5319, (String[][])new String[][]{{"The onion seeds have only just been planted."}, {"The onions are partially visible and the stems", "have grown."}, {"The top of the onion of the onion plant is clear", "above the ground and the onion is white."}, {"The onion plant is slightly larger than before and", "the onion is cream coloured."}, {"The onion stalks are larger than before and the", "onion is now light and brown coloured."}}),
    CABBAGE(5324, (String[][])new String[][]{{"The cabbage seeds have only just been planted,", "the cabbages are small and bright green."}, {"The cabbages are much larger, with more leaves", "surrounding the head."}, {"The cabbages are larger than before, and textures", "of leaves are now easily observable."}, {"The cabbage head has swollen larger, and the", "surrounding leaves are more close to the ground."}, {"The cabbage plants are ready to harvest. The", "centre of each cabbage head is light green coloured."}}),
    TOMATO(5322, (String[][])new String[][]{{"The tomato seeds have only just been planted."}, {"The tomato plants grow twice as large as before."}, {"The tomato plants grow larger, and small green", "tomatoes are now observable."}, {"The tomato plants grow thicker to hold up the", "weight of the tomatoes. The tomatoes are now light", "orange and slightly larger on the plant."}, {"The tomato plants are ready to harvest. The tomato", "plants leaves are larger and the tomatoes are", "ripe red."}}),
    SWEETCORN(5320, (String[][])new String[][]{{"The sweetcorn plants have only just been planted."}, {"The sweetcorn plants are waist tall now and are", "leafy."}, {"The sweetcorn plants are slightly taller than", "before and slightly thicker."}, {"The sweetcorn leaves are larger at the base, and", "the plants are slightly taller."}, {"Closed corn cobs are now observable on the", "sweetcorn plants."}, {"Closed corn cobs are now observable on the", "sweetcorn plants."}, {"The sweetcorn plants are ready to harvest. The", "corn cobs are open and visibly yellow."}}),
    STRAWBERRY(5323, (String[][])new String[][]{{"The strawberry seeds have only just been planted."}, {"The strawberry plants have more leaves than before."}, {"The strawberry plants have even more leaves and is", "slightly taller than before."}, {"Each strawberry plant has opened one white", "flower each."}, {"The strawberry plants are slightly larger, and", "have small strawberries visible at their bases."}, {"The strawberry plants are slightly larger, opened", "a second flower each, and have more strawberries."}, {"The strawberry plants are ready to harvest. The", "strawberries are almost as large as the flowers."}}),
    WATERMELON(5321, (String[][])new String[][]{{"The watermelon seeds have only just been planted."}, {"The watermelon vines have grown longer than before."}, {"The watermelon vines have grown longer than before."}, {"The watermelon vines have started to curl, and", "another vine has sprouted from the centre."}, {"The watermelon vines have continued growing longer,", "and another vine has sprouted."}, {"Small watermelons are visibly growing on the vines now."}, {"The watermelons on the vines have grown larger", "than before."}, {"The watermelons on the vines have grown larger", "than before."}, {"The watermelon plants are ready to harvest.", "The watermelons on the vines are large and ripe."}});

    private int cropId;
    private String[][] growthMessages;
    private static Map definitionsByCropId;

    static {
        definitionsByCropId = new HashMap();
        AllotmentGrowthDefinition[] allotmentGrowthDefinitionArray = AllotmentGrowthDefinition.values();
        int length = allotmentGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            AllotmentGrowthDefinition allotmentGrowthDefinition = allotmentGrowthDefinitionArray[index];
            definitionsByCropId.put(allotmentGrowthDefinition.cropId, allotmentGrowthDefinition);
            ++index;
        }
    }
    private AllotmentGrowthDefinition(int cropId, String[][] growthMessages) {
        this.cropId = cropId;
        this.growthMessages = growthMessages;
    }

    public static AllotmentGrowthDefinition forCropId(int value2) {
        return (AllotmentGrowthDefinition)((Object)definitionsByCropId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

