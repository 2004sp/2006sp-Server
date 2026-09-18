package com.rs2.model.objects;

public final class ObjectRegionKey {
    private int regionX;
    private int regionY;

    public ObjectRegionKey(int regionX, int regionY) {
        this.regionX = regionX;
        this.regionY = regionY;
    }

    public final int hashCode() {
        int value = 31 + this.regionX;
        value = value * 31 + this.regionY;
        return value;
    }

    public final boolean equals(Object value2) {
        if (this == value2) {
            return true;
        }
        if (value2 == null) {
            return false;
        }
        if (this.getClass() != value2.getClass()) {
            return false;
        }
        value2 = (ObjectRegionKey)value2;
        if (this.regionX != ((ObjectRegionKey)value2).regionX) {
            return false;
        }
        return this.regionY == ((ObjectRegionKey)value2).regionY;
    }
}

