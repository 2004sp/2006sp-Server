package com.rs2.util;

public final class WeightedChanceEntry {
    public final int lowChance;
    public final int highChance;
    public final int requiredLevel;

    public WeightedChanceEntry(int lowChance, int highChance, int requiredLevel) {
        this.lowChance = lowChance;
        this.highChance = highChance;
        this.requiredLevel = requiredLevel;
    }
}

