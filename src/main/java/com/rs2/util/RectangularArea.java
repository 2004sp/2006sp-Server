package com.rs2.util;

import com.rs2.model.Position;

public final class RectangularArea {
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private byte plane;

    public RectangularArea(int minX, int minY, int maxX, int maxY, byte plane) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.plane = plane;
    }

    public RectangularArea(int value5, int value22, int value32, int value42, int plane) {
        this(value5, value22, value32, value42, (byte)plane);
    }

    public RectangularArea(int minX, int minY, int value32, int value42) {
        this.minX = minX <= value32 ? minX : value32;
        this.minY = minY <= value42 ? minY : value42;
        this.maxX = minX >= value32 ? minX : value32;
        this.maxY = minY >= value42 ? minY : value42;
    }

    public final int getMinX() {
        return this.minX;
    }

    public final int getMinY() {
        return this.minY;
    }

    public final int getMaxX() {
        return this.maxX;
    }

    public final int getMaxY() {
        return this.maxY;
    }

    public final Position[] getPositions() {
        Position[] positionArray = new Position[(this.maxX - this.minX + 1) * (this.maxY - this.minY + 1)];
        int index = 0;
        int value = this.minX;
        while (value <= this.maxX) {
            int value2 = this.minY;
            while (value2 <= this.maxY) {
                positionArray[index++] = new Position(value, value2, this.plane);
                ++value2;
            }
            ++value;
        }
        return positionArray;
    }

    public static RectangularArea fromPositionOffset(Position position, int value3, int value22) {
        int x = position.getX();
        int y = position.getY();
        value3 = position.getX() + value3;
        value22 = position.getY() + value22;
        return new RectangularArea(x, y, value3, value22, (byte)position.getPlane());
    }

    public final boolean containsExclusive(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x > this.minX && x < this.maxX && y > this.minY && y < this.maxY;
    }

    public final boolean contains(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY;
    }

    public final boolean containsExclusiveOnPlane(Position position) {
        if (this.plane != position.getPlane()) {
            return false;
        }
        int x = position.getX();
        int y = position.getY();
        return x > this.minX && x < this.maxX && y > this.minY && y < this.maxY;
    }
}

