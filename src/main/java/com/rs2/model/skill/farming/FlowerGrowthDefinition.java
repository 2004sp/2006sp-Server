package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FlowerGrowthDefinition {
    MARIGOLD(5096, (String[][])new String[][]{{"The seeds have only just been planted."}, {"The marigold plants have developed leaves."}, {"The marigold plants have begun to grow their", "flowers. The new flowers are orange and small at", "first."}, {"The marigold plants are larger, and more", "developed in their petals."}, {"The marigold plants are ready to harvest. Their", "flowers are fully matured."}}),
    ROSEMARY(5097, (String[][])new String[][]{{"The seeds have only just been planted."}, {"The rosemary plant is taller than before."}, {"The rosemary plant is bushier and taller than", "before."}, {"The rosemary plant is developing a flower bud at", "its top."}, {"The plant is ready to harvest. The rosemary", "plant's flower has opened."}}),
    NASTURTIUM(5098, (String[][])new String[][]{{"The nasturtium seed has only just been planted."}, {"The nasturtium plants have started to develop", "leaves."}, {"The nasturtium plants have grown more leaves,", "and nine flower buds."}, {"The nasturtium plants open their flower buds."}, {"The plants are ready to harvest. The nasturtium", "plants grow larger than before and the flowers", "fully open."}}),
    WOAD(5099, (String[][])new String[][]{{"The woad seed has only just been planted."}, {"The woad plant produces more stalks, that split", "in tow near the top."}, {"The woad plant grows more segments from its", "intitial stalks."}, {"The woad plant develops flower buds on the end", "of each of its stalks."}, {"The woad plant is ready to harvest. The plant has", "all of its stalks pointing directly up, with", "all flowers open."}}),
    LIMPWURT(5100, (String[][])new String[][]{{"The seed has only just been planted."}, {"The limpwurt plant produces more roots."}, {"The limpwurt plant produces an unopened pink", "flower bud and continues to grow larger."}, {"The limpwurt plant grows larger, with more loops", "in its roots. The flower bud is still unopened."}, {"The limpwurt plant is ready to harvest. The", "flower finally opens wide, with a spike in the", "middle."}});

    private int cropId;
    private String[][] growthMessages;
    private static Map definitionsByCropId;

    static {
        definitionsByCropId = new HashMap();
        FlowerGrowthDefinition[] flowerGrowthDefinitionArray = FlowerGrowthDefinition.values();
        int length = flowerGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FlowerGrowthDefinition flowerGrowthDefinition = flowerGrowthDefinitionArray[index];
            definitionsByCropId.put(flowerGrowthDefinition.cropId, flowerGrowthDefinition);
            ++index;
        }
    }
    private FlowerGrowthDefinition(int cropId, String[][] growthMessages) {
        this.cropId = cropId;
        this.growthMessages = growthMessages;
    }

    public static FlowerGrowthDefinition forCropId(int value2) {
        return (FlowerGrowthDefinition)((Object)definitionsByCropId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

