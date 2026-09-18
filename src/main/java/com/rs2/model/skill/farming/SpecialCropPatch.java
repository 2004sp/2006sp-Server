package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum SpecialCropPatch {
    BELLADONNA(0, new Position[]{new Position(3086, 3354), new Position(3087, 3355)}, 5281),
    CACTUS(2, new Position[]{new Position(3315, 3202), new Position(3316, 3203)}, 5280),
    MUSHROOM(3, new Position[]{new Position(3451, 3472), new Position(3452, 3473)}, 5282);

    private int index;
    private Position[] bounds;
    private int objectId;

    private SpecialCropPatch(int index, Position[] positionArray, int objectId) {
        this.index = index;
        this.bounds = positionArray;
        this.objectId = objectId;
    }

    public static SpecialCropPatch forPosition(Position position) {
        SpecialCropPatch[] specialCropPatchArray = SpecialCropPatch.values();
        int length = specialCropPatchArray.length;
        int index = 0;
        while (index < length) {
            SpecialCropPatch specialCropPatch;
            SpecialCropPatch specialCropPatch2 = specialCropPatch = specialCropPatchArray[index];
            specialCropPatch2 = specialCropPatch;
            if (FarmingPatchUtils.containsPosition(specialCropPatch.bounds[0], specialCropPatch2.bounds[1], position)) {
                return specialCropPatch;
            }
            ++index;
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }

    public final int getObjectId() {
        return this.objectId;
    }
}

