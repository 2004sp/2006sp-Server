package com.rs2.model.gameplay.barrows;

public final class BarrowsRewardEntry {
    public int itemId;
    public int minAmount;
    public int maxAmount;
    public int rollThreshold;

    public BarrowsRewardEntry(int itemId, int minAmount, int maxAmount, int rollThreshold) {
        this.itemId = itemId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.rollThreshold = rollThreshold;
    }
}

