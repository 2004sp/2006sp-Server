package com.rs2.util;

import com.rs2.model.GameplayHelper;
import java.io.File;
import java.util.Comparator;

public final class BackupRepairTimestampComparator
implements Comparator {
    public BackupRepairTimestampComparator() {
    }

    public final int compare(Object value3, Object value22) {
        File file = (File)value3;
        File file2 = (File)value22;
        String[] timestampParts = file.getName().split("-");
        String[] timestampParts2 = file2.getName().split("-");
        long timestamp = GameplayHelper.buildTimestampMillis(Integer.parseInt(timestampParts[0]), Integer.parseInt(timestampParts[1]), Integer.parseInt(timestampParts[2]), Integer.parseInt(timestampParts[3]), Integer.parseInt(timestampParts[4]));
        long timestamp2 = GameplayHelper.buildTimestampMillis(Integer.parseInt(timestampParts2[0]), Integer.parseInt(timestampParts2[1]), Integer.parseInt(timestampParts2[2]), Integer.parseInt(timestampParts2[3]), Integer.parseInt(timestampParts2[4]));
        return Long.compare(timestamp2, timestamp);
    }
}
