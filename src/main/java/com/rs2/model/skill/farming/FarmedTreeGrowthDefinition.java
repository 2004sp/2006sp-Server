package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FarmedTreeGrowthDefinition {
    OAK(5370, (String[][])new String[][]{{"The acorn sapling has only just been planted."}, {"The acorn sapling grows larger."}, {"The oak tree produces a small canopy."}, {"The oak tree grows larger."}, {"The oak tree is ready to harvest."}}),
    WILLOW(5371, (String[][])new String[][]{{"The willow sapling has only just been planted."}, {"The willow sapling grows a few small branches."}, {"The willow tree develops a small canopy."}, {"The willow tree trunk becomes dark brown,", "and the canopy grows."}, {"The willow tree trunk becomes a lighter shade", "of brown, and the canopy grows more."}, {"The trunk thickens, and the canopy grows", "yet larger."}, {"The willow tree is fully grown."}}),
    MAPLE(5372, (String[][])new String[][]{{"The maple sapling has only just been planted."}, {"The mapling sapling grows a few small branches."}, {"The maple tree develops a small canopy."}, {"The maple tree trunk straightens and the canopy", "grows larger."}, {"The maple tree canopy grows."}, {"The maple tree canopy grows."}, {"The maple tree grows."}, {"The maple tree grows."}, {"The maple tree is ready to be harvested."}}),
    YEW(5373, (String[][])new String[][]{{"The yew sapling has only just been planted."}, {"The yew sapling grows a few small branches."}, {"The yew tree develops several small canopies."}, {"The yew tree trunk texture becomes smoother and", "the canopies grow larger."}, {"The yew tree grows larger."}, {"The yew tree grows larger."}, {"The yew tree canopies become more angular and", "cone shaped due to texture placement."}, {"The yew tree gains a rougher tree bark texture and", "the base becomes darker."}, {"The yew tree base becomes light again, and the", "trunk loses its texture."}, {"The yew tree bark gains a stripy texture."}, {"The yew tree is ready to be harvested."}}),
    MAGIC(5374, (String[][])new String[][]{{"The magic sapling has only just been planted."}, {"The magic sapling grows a little bit."}, {"The magic sapling grows a little bit more."}, {"The magic sapling grows a few small branches."}, {"The magic tree grows a small canopy."}, {"The magic tree canopy becomes larger, and", "starts producing sparkles."}, {"The magic tree grows and the base becomes lighter."}, {"The magic tree grows and the base becomes darker."}, {"The magic tree's bark is more prominent and the", "canopy gains more sparkles."}, {"The magic tree grows taller."}, {"The magic tree grows taller."}, {"The magic tree grows taller."}, {"The magic tree is ready to be harvested."}});

    private int treeId;
    private String[][] growthMessages;
    private static Map definitionsByTreeId;

    static {
        definitionsByTreeId = new HashMap();
        FarmedTreeGrowthDefinition[] farmedTreeGrowthDefinitionArray = FarmedTreeGrowthDefinition.values();
        int length = farmedTreeGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FarmedTreeGrowthDefinition farmedTreeGrowthDefinition = farmedTreeGrowthDefinitionArray[index];
            definitionsByTreeId.put(farmedTreeGrowthDefinition.treeId, farmedTreeGrowthDefinition);
            ++index;
        }
    }
    private FarmedTreeGrowthDefinition(int treeId, String[][] growthMessages) {
        this.treeId = treeId;
        this.growthMessages = growthMessages;
    }

    public static FarmedTreeGrowthDefinition forTreeId(int value2) {
        return (FarmedTreeGrowthDefinition)((Object)definitionsByTreeId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

