package com.rs2.model.skill.farming;

import com.rs2.model.Position;

public final class FarmingPatchUtils {
    public static final int[] wateredSeedlingItemIds = new int[]{5364, 5365, 5366, 5367, 5368, 5369, 5488, 5489, 5490, 5491, 5492, 5493, 5494, 5495};

    public static boolean containsPosition(Position position, Position position2, Position position3) {
        int x = position.getX();
        int y = position.getY();
        int x2 = position2.getX();
        int y2 = position2.getY();
        return position3.getX() >= x && position3.getY() >= y && position3.getX() <= x2 && position3.getY() <= y2;
    }
}

