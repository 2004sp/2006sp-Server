package com.rs2.model.path;

public class PathStep {
    private final int x;
    private final int y;

    public PathStep(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        int result = 31 + this.x;
        result = result * 31 + this.y;
        return result;
    }

    public boolean equals(Object value2) {
        if (this == value2) {
            return true;
        }
        if (value2 == null) {
            return false;
        }
        if (this.getClass() != value2.getClass()) {
            return false;
        }
        value2 = (PathStep)value2;
        if (this.x != ((PathStep)value2).x) {
            return false;
        }
        return this.y == ((PathStep)value2).y;
    }

    public String toString() {
        return String.valueOf(PathStep.class.getName()) + " [x=" + this.x + ", y=" + this.y + "]";
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }
}
