package com.rs2.model;

import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public class Position {
    private int x;
    private int y;
    private int plane;
    private int previousX;
    private int previousY;

    public Position(int x, int y) {
        this(x, y, 0);
    }

    public Position(int x, int y, int plane) {
        this.x = x;
        this.y = y;
        this.plane = plane;
    }

    public String toString() {
        return "Position(" + this.x + ", " + this.y + ", " + this.plane + ")";
    }

    public boolean equals(Object value2) {
        if (value2 instanceof Position) {
            value2 = (Position)value2;
            return this.x == ((Position)value2).x && this.y == ((Position)value2).y && this.plane == ((Position)value2).plane;
        }
        return false;
    }

    public final void set(Position position) {
        this.x = position.x;
        this.y = position.y;
        this.previousX = position.previousX;
        this.previousY = position.previousY;
        this.plane = position.plane;
    }

    public final void translate(int deltaX, int deltaY) {
        this.previousX = this.x;
        this.previousY = this.y;
        this.x += deltaX;
        this.y += deltaY;
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final int getX() {
        return this.x;
    }

    public final void setY(int y) {
        this.y = y;
    }

    public final int getY() {
        return this.y;
    }

    public final void setPlane(int plane) {
        this.plane = plane;
    }

    public final int getPlane() {
        return this.plane;
    }

    public final void setPreviousX(int previousX) {
        this.previousX = previousX;
    }

    public final int getPreviousX() {
        return this.previousX;
    }

    public final void setPreviousY(int previousY) {
        this.previousY = previousY;
    }

    public final int getPreviousY() {
        return this.previousY;
    }

    public final int getRegionX() {
        return (this.x >> 3) - 6;
    }

    public final int getRegionY() {
        return (this.y >> 3) - 6;
    }

    public final int getLocalX() {
        return this.x - 8 * this.getRegionX();
    }

    public final int getLocalY() {
        return this.y - 8 * this.getRegionY();
    }

    public static int updateLocalX(Player player) {
        int regionBaseX = player.getLastKnownRegionPosition().getRegionX() << 3;
        player.localX = player.getPosition().x - regionBaseX;
        return player.localX;
    }

    public static int updateLocalY(Player player) {
        int regionBaseY = player.getLastKnownRegionPosition().getRegionY() << 3;
        player.localY = player.getPosition().y - regionBaseY;
        return player.localY;
    }

    public final boolean isWithinViewport(Position position) {
        if (this.plane != position.plane) {
            return false;
        }
        Position delta = GameUtil.getDelta(this, position);
        return delta.x >= -15 && delta.x <= 14 && delta.y >= -15 && delta.y <= 14;
    }

    public final boolean isWithinDistance(Position position, int distance) {
        if (this.plane != position.plane) {
            return false;
        }
        return GameUtil.isWithinDistance(this, position, distance);
    }

    public final Position copy() {
        return new Position(this.x, this.y, this.plane);
    }

    public final Position centerForSize(int size) {
        if (size == 1) {
            return this;
        }
        int centerOffset = size / 2;
        return new Position(this.x + centerOffset, this.y + centerOffset, this.plane);
    }

    public final boolean isOrthogonallyAlignedWith(Position position) {
        Position delta = GameUtil.getDelta(this, position);
        return delta.x == 0 || delta.y == 0;
    }

}
