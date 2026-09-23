package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum FlowerPatch {
    ARDOUGNE(0, new Position[]{new Position(2666, 3374), new Position(2667, 3375)}),
    MORYTANIA(1, new Position[]{new Position(3601, 3525), new Position(3602, 3526)}),
    FALADOR(2, new Position[]{new Position(3054, 3307), new Position(3055, 3308)}),
    CATHERBY(3, new Position[]{new Position(2809, 3463), new Position(2810, 3464)});

    private int index;
    private Position[] bounds;
    private FlowerPatch(int index, Position[] positionArray) {
        this.index = index;
        this.bounds = positionArray;
    }

    public static FlowerPatch forPosition(Position position) {
        FlowerPatch[] flowerPatchArray = FlowerPatch.values();
        int length = flowerPatchArray.length;
        int index = 0;
        while (index < length) {
            FlowerPatch flowerPatch;
            FlowerPatch flowerPatch2 = flowerPatch = flowerPatchArray[index];
            flowerPatch2 = flowerPatch;
            if (FarmingPatchUtils.containsPosition(flowerPatch.bounds[0], flowerPatch2.bounds[1], position)) {
                return flowerPatch;
            }
            ++index;
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }

    public final Position[] getInteractionBounds() {
        return this.bounds;
    }
}

