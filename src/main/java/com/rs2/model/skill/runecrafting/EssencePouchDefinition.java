package com.rs2.model.skill.runecrafting;

import java.util.HashMap;
import java.util.Map;

public enum EssencePouchDefinition {
    SMALL_POUCH(0, 5509, -1, 3, 1, -1, 0),
    MEDIUM_POUCH(1, 5510, 5511, 6, 25, 270, 3),
    LARGE_POUCH(2, 5512, 5513, 9, 50, 261, 2),
    GIANT_POUCH(3, 5514, 5515, 12, 75, 120, 3);

    private int pouchIndex;
    private int itemId;
    private int degradedItemId;
    private int capacity;
    private int requiredLevel;
    private int degradeAfterUses;
    private int degradedCapacityPenalty;
    private static Map lookupById;

    static {
        lookupById = new HashMap();
        EssencePouchDefinition[] essencePouchDefinitionArray = EssencePouchDefinition.values();
        int length = essencePouchDefinitionArray.length;
        int index = 0;
        while (index < length) {
            EssencePouchDefinition essencePouchDefinition = essencePouchDefinitionArray[index];
            lookupById.put(essencePouchDefinition.pouchIndex, essencePouchDefinition);
            lookupById.put(essencePouchDefinition.itemId, essencePouchDefinition);
            lookupById.put(essencePouchDefinition.degradedItemId, essencePouchDefinition);
            ++index;
        }
    }

    private EssencePouchDefinition(int pouchIndex, int itemId, int degradedItemId, int capacity, int requiredLevel, int degradeAfterUses, int degradedCapacityPenalty) {
        this.pouchIndex = pouchIndex;
        this.itemId = itemId;
        this.degradedItemId = degradedItemId;
        this.capacity = capacity;
        this.requiredLevel = requiredLevel;
        this.degradeAfterUses = degradeAfterUses;
        this.degradedCapacityPenalty = degradedCapacityPenalty;
    }

    public static EssencePouchDefinition forItemOrIndex(int itemId) {
        return (EssencePouchDefinition)((Object)lookupById.get(itemId));
    }

    public final int getPouchIndex() {
        return this.pouchIndex;
    }

    public final int getItemId() {
        return this.itemId;
    }

    public final int getDegradedItemId() {
        return this.degradedItemId;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getDegradeAfterUses() {
        return this.degradeAfterUses;
    }

    public final int getDegradedCapacityPenalty() {
        return this.degradedCapacityPenalty;
    }
}

