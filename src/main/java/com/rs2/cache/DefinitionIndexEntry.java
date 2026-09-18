package com.rs2.cache;

import com.rs2.cache.CacheIndexEntry;

public final class DefinitionIndexEntry
extends CacheIndexEntry {
    private int dataOffset;

    public DefinitionIndexEntry(int value3, int dataOffset) {
        super(value3);
        this.dataOffset = dataOffset;
    }

    public final int getDataOffset() {
        return this.dataOffset;
    }
}

