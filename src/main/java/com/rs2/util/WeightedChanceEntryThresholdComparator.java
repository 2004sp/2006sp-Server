package com.rs2.util;

import java.util.Comparator;

public final class WeightedChanceEntryThresholdComparator
implements Comparator {
    public WeightedChanceEntryThresholdComparator() {
    }
    public final int compare(Object value3, Object value22) {
        WeightedChanceEntry entry = (WeightedChanceEntry)value3;
        WeightedChanceEntry entry2 = (WeightedChanceEntry)value22;
        return Integer.compare(entry2.requiredLevel, entry.requiredLevel);
    }

}

