package com.rs2.launcher;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

public final class TransparentColorFilter
extends RGBImageFilter {
    private int transparentRgb;

    public TransparentColorFilter(Color color) {
        this.transparentRgb = color.getRGB() | 0xFF000000;
    }

    @Override
    public final int filterRGB(int value4, int value22, int value32) {
        if ((value32 | 0xFF000000) == this.transparentRgb) {
            return 0xFFFFFF & value32;
        }
        return value32;
    }
}

