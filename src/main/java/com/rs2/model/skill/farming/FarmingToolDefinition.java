package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FarmingToolDefinition {
    RAKE(0, 5341, 1, 1, 15596, 15597, "Rake"),
    SEED_DIBBER(1, 5343, 2, 1, 15598, 15599, "Dibber"),
    SPADE(2, 952, 4, 1, 15600, 15601, "Spade"),
    SECATEURS(3, 5329, 8, 1, 15602, 15603, "Secateurs"),
    MAGIC_SECATEURS(4, 7409, 8, 1, 15602, 15603, "Secateurs"),
    WATERING_CAN_EMPTY(5, 5331, 16, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_1(6, 5333, 32, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_2(7, 5334, 48, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_3(8, 5335, 64, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_4(9, 5336, 80, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_5(10, 5337, 96, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_6(11, 5338, 112, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_7(12, 5339, 128, 1, 15604, 15605, "Watering Can"),
    WATERING_CAN_8(13, 5340, 144, 1, 15604, 15605, "Watering Can"),
    GARDENING_TROWEL(14, 5325, 256, 1, 15606, 15607, "Trowel"),
    BUCKET(15, 1925, 512, 31, 15608, 15609, "Buckets"),
    COMPOST(16, 6032, 16384, 255, 15610, 15611, "Compost"),
    SUPER_COMPOST(17, 6034, 0x400000, 255, 15612, 15613, "Super Compost");

    private int storageIndex;
    private int itemId;
    private int configValue;
    private int maxStoredAmount;
    private int nameTextInterfaceId;
    private int amountTextInterfaceId;
    private String displayName;
    private static Map definitionsByItemId;
    private static Map definitionsByStorageIndex;

    static {
        definitionsByItemId = new HashMap();
        definitionsByStorageIndex = new HashMap();
        FarmingToolDefinition[] farmingToolDefinitionArray = FarmingToolDefinition.values();
        int length = farmingToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FarmingToolDefinition farmingToolDefinition = farmingToolDefinitionArray[index];
            definitionsByItemId.put(farmingToolDefinition.itemId, farmingToolDefinition);
            definitionsByStorageIndex.put(farmingToolDefinition.storageIndex, farmingToolDefinition);
            ++index;
        }
    }

    public static FarmingToolDefinition forItemId(int itemId) {
        return (FarmingToolDefinition)((Object)definitionsByItemId.get(itemId));
    }

    public static FarmingToolDefinition forStorageIndex(int index) {
        return (FarmingToolDefinition)((Object)definitionsByStorageIndex.get(index));
    }

    private FarmingToolDefinition(int storageIndex, int itemId, int configValue, int maxStoredAmount, int nameTextInterfaceId, int amountTextInterfaceId, String displayName) {
        this.storageIndex = storageIndex;
        this.itemId = itemId;
        this.configValue = configValue;
        this.maxStoredAmount = maxStoredAmount;
        this.nameTextInterfaceId = nameTextInterfaceId;
        this.amountTextInterfaceId = amountTextInterfaceId;
        this.displayName = displayName;
    }

    public final int getStorageIndex() {
        return this.storageIndex;
    }

    public final int getItemId() {
        return this.itemId;
    }

    public final int getConfigValue() {
        return this.configValue;
    }

    public final int getMaxStoredAmount() {
        return this.maxStoredAmount;
    }

    public final int getNameTextInterfaceId() {
        return this.nameTextInterfaceId;
    }

    public final int getAmountTextInterfaceId() {
        return this.amountTextInterfaceId;
    }

    public final String getDisplayName() {
        return this.displayName;
    }
}

