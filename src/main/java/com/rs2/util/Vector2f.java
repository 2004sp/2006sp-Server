package com.rs2.util;

public final class Vector2f {
    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final Vector2f normalize() {
        Vector2f vector2f = this;
        float value = (float)Math.sqrt(vector2f.x * vector2f.x + vector2f.y * vector2f.y);
        this.x /= value;
        this.y /= value;
        return this;
    }
}

