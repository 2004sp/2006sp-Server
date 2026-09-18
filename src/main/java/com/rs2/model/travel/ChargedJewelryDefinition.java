package com.rs2.model.travel;

public enum ChargedJewelryDefinition {
    AMULET_OF_GLORY(new int[]{1712, 1710, 1708, 1706, 1704}),
    RING_OF_DUELING(new int[]{2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, -1}),
    GAMES_NECKLACE(new int[]{3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867, -1});

    int[] itemIdsByDescendingCharge;

    private ChargedJewelryDefinition(int[] itemIdsByDescendingCharge) {
        this.itemIdsByDescendingCharge = itemIdsByDescendingCharge;
    }

    public final int[] getItemIdsByDescendingCharge() {
        return this.itemIdsByDescendingCharge;
    }
}

