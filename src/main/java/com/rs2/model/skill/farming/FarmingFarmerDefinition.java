package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FarmingFarmerDefinition {
    ALLOTMENT_FARMER_NPC_2324(2324, 41, "allotment", new String[]{"Northern patch", "Southern patch"}),
    ALLOTMENT_FARMER_NPC_2323(2323, 54, "allotment", new String[]{"North-west patch", "South-East patch"}),
    ALLOTMENT_FARMER_NPC_2326(2326, 123, "allotment", new String[]{"North-west patch", "South-East patch"}),
    ALLOTMENT_FARMER_NPC_2325(2325, 99, "allotment", new String[]{"Northern patch", "Southern patch"}),
    BUSHES_FARMER_NPC_2337(2337, 162, "bushes", null),
    BUSHES_FARMER_NPC_2336(2336, 139, "bushes", null),
    BUSHES_FARMER_NPC_2335(2335, 47, "bushes", null),
    BUSHES_FARMER_NPC_2338(2338, 144, "bushes", null),
    HOPS_FARMER_NPC_2334(2334, 123, "hops", null),
    HOPS_FARMER_NPC_2332(2332, 134, "hops", null),
    HOPS_FARMER_NPC_2333(2333, 153, "hops", null),
    HOPS_FARMER_NPC_2327(2327, 62, "hops", null),
    FRUITTREE_FARMER_NPC_2343(2343, 32, "fruitTree", null),
    FRUITTREE_FARMER_NPC_2331(2331, 53, "fruitTree", null),
    FRUITTREE_FARMER_NPC_2344(2344, 74, "fruitTree", null),
    FRUITTREE_FARMER_NPC_2330(2330, 70, "fruitTree", null),
    TREE_FARMER_NPC_2340(2340, 85, "tree", null),
    TREE_FARMER_NPC_2339(2339, 163, "tree", null),
    TREE_FARMER_NPC_2341(2341, 146, "tree", null),
    TREE_FARMER_NPC_2342(2342, 58, "tree", null);

    private int npcId;
    private String patchType;
    private String[] patchLabels;
    private static Map definitionsByNpcId;

    static {
        definitionsByNpcId = new HashMap();
        FarmingFarmerDefinition[] farmingFarmerDefinitionArray = FarmingFarmerDefinition.values();
        int length = farmingFarmerDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FarmingFarmerDefinition farmingFarmerDefinition = farmingFarmerDefinitionArray[index];
            definitionsByNpcId.put(farmingFarmerDefinition.npcId, farmingFarmerDefinition);
            ++index;
        }
    }

    private FarmingFarmerDefinition(int npcId, int value22, String patchType, String[] patchLabels) {
        this.npcId = npcId;
        this.patchType = patchType;
        this.patchLabels = patchLabels;
    }

    public static FarmingFarmerDefinition forNpcId(int npcId) {
        return (FarmingFarmerDefinition)((Object)definitionsByNpcId.get(npcId));
    }

    public final String getPatchType() {
        return this.patchType;
    }

    public final String[] getPatchLabels() {
        return this.patchLabels;
    }
}

