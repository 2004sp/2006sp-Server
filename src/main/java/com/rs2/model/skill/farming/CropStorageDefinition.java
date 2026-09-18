package com.rs2.model.skill.farming;

import java.util.HashMap;

public enum CropStorageDefinition {
    POTATO(5420, 1942, true),
    ONION(5440, 1957, true),
    CABBAGE(5460, 1965, true),
    APPLE(5378, 1955, false),
    ORANGE(5388, 2108, false),
    STRAWBERRY(5398, 5504, false),
    BANANA(5408, 1963, false),
    TOMATO(5960, 1982, false);

    private int baseContainerItemId;
    private int produceItemId;
    private boolean sack;
    private static HashMap definitionsByProduceItemId;

    static {
        definitionsByProduceItemId = new HashMap();
        CropStorageDefinition[] cropStorageDefinitionArray = CropStorageDefinition.values();
        int length = cropStorageDefinitionArray.length;
        int index = 0;
        while (index < length) {
            CropStorageDefinition cropStorageDefinition;
            CropStorageDefinition cropStorageDefinition2 = cropStorageDefinition = cropStorageDefinitionArray[index];
            definitionsByProduceItemId.put(cropStorageDefinition2.produceItemId, cropStorageDefinition);
            ++index;
        }
    }

    public static CropStorageDefinition forProduceItemId(int itemId) {
        return (CropStorageDefinition)((Object)definitionsByProduceItemId.get(itemId));
    }

    private CropStorageDefinition(int baseContainerItemId, int produceItemId, boolean sack) {
        this.baseContainerItemId = baseContainerItemId;
        this.produceItemId = produceItemId;
        this.sack = sack;
    }

    public final int getBaseContainerItemId() {
        return this.baseContainerItemId;
    }

    public final int getProduceItemId() {
        return this.produceItemId;
    }

    public final boolean isSack() {
        return this.sack;
    }

    static boolean isSack(CropStorageDefinition cropStorageDefinition) {
        return cropStorageDefinition.sack;
    }
}

