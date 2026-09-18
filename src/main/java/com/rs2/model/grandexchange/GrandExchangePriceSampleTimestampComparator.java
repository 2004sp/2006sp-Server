package com.rs2.model.grandexchange;

import java.util.Comparator;

public final class GrandExchangePriceSampleTimestampComparator
implements Comparator {
    public GrandExchangePriceSampleTimestampComparator() {
    }
    public final int compare(Object value3, Object value22) {
        GrandExchangePriceSample sample = (GrandExchangePriceSample)value3;
        GrandExchangePriceSample sample2 = (GrandExchangePriceSample)value22;
        return Long.compare(sample2.timestampMillis, sample.timestampMillis);
    }

}

