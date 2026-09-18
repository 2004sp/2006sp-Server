package com.rs2.model.travel.canoe;

public enum CanoeTreeDefinition {
    LUMBRIDGE_TREE(new int[]{12163}, 12, 0),
    CHAMPIONS_GUILD_TREE(new int[]{12164}, 12, 8),
    BARBARIAN_VILLAGE_TREE(new int[]{12165}, 12, 16),
    EDGEVILLE_TREE(new int[]{12166}, 12, 24);

    private int[] objectIds;
    private int requiredLevel;
    private int configShift;

    public static CanoeTreeDefinition forObjectId(int objectId) {
        CanoeTreeDefinition[] canoeTreeDefinitionArray = CanoeTreeDefinition.values();
        int length = canoeTreeDefinitionArray.length;
        int index = 0;
        while (index < length) {
            CanoeTreeDefinition canoeTreeDefinition;
            CanoeTreeDefinition canoeTreeDefinition2 = canoeTreeDefinition = canoeTreeDefinitionArray[index];
            int[] integerValues = canoeTreeDefinition.objectIds;
            int length2 = canoeTreeDefinition.objectIds.length;
            int index2 = 0;
            while (index2 < length2) {
                int value = integerValues[index2];
                if (value == objectId) {
                    return canoeTreeDefinition;
                }
                ++index2;
            }
            ++index;
        }
        return null;
    }

    private CanoeTreeDefinition(int[] objectIds, int value3, int configShift) {
        this.objectIds = objectIds;
        this.requiredLevel = 12;
        this.configShift = configShift;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getConfigShift() {
        return this.configShift;
    }
}

