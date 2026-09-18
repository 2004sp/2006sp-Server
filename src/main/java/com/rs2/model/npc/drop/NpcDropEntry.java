package com.rs2.model.npc.drop;

public final class NpcDropEntry {
    private int itemId;
    private int fixedAmount;
    private int minAmount;
    private int maxAmount;
    private int[] itemIds;
    private int[] amountOptions;
    private int chanceType = -1;
    private int chanceNumerator = -1;
    private int chanceDenominator = -1;
    private int[] minAmounts;
    private int[] maxAmounts;

    public NpcDropEntry(int itemId, int fixedAmount, int minAmount, int maxAmount, int[] itemIds, int[] amountOptions, int chanceType, int chanceNumerator, int chanceDenominator, int[] minAmounts, int[] maxAmounts) {
        this.itemId = itemId;
        this.fixedAmount = fixedAmount;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.itemIds = itemIds;
        this.amountOptions = amountOptions;
        this.chanceType = chanceType;
        this.chanceNumerator = chanceNumerator;
        this.chanceDenominator = chanceDenominator;
        this.minAmounts = minAmounts;
        this.maxAmounts = maxAmounts;
    }

    public final void setChanceNumerator(int chanceNumerator) {
        this.chanceNumerator = chanceNumerator;
    }

    public final void setChanceDenominator(int chanceDenominator) {
        this.chanceDenominator = chanceDenominator;
    }

    public final int getChanceType() {
        return this.chanceType;
    }

    public final int getChanceNumerator() {
        return this.chanceNumerator;
    }

    public final int getChanceDenominator() {
        return this.chanceDenominator;
    }

    public final int getItemId() {
        return this.itemId;
    }

    public final int getFixedAmount() {
        return this.fixedAmount;
    }

    public final int getMinAmount() {
        return this.minAmount;
    }

    public final int getMaxAmount() {
        return this.maxAmount;
    }

    public final int[] getItemIds() {
        if (this.itemIds == null) {
            return new int[0];
        }
        return this.itemIds;
    }

    public final int[] getAmountOptions() {
        if (this.amountOptions == null) {
            return new int[0];
        }
        return this.amountOptions;
    }

    public final int[] getMinAmounts() {
        if (this.minAmounts == null) {
            return new int[0];
        }
        return this.minAmounts;
    }

    public final int[] getMaxAmounts() {
        if (this.maxAmounts == null) {
            return new int[0];
        }
        return this.maxAmounts;
    }
}

