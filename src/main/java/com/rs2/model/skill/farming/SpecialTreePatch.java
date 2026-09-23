package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum SpecialTreePatch {
    BRIMHAVEN_SPIRIT_TREE(0, new Position[]{new Position(2801, 3202), new Position(2803, 3204)}, 5375),
    TAI_BWO_WANNAI_CALQUAT(1, new Position[]{new Position(2795, 3100), new Position(2797, 3102)}, 5503),
    PORT_SARIM_SPIRIT_TREE(2, new Position[]{new Position(3059, 3257), new Position(3061, 3259)}, 5375),
    ETCETERIA_SPIRIT_TREE(3, new Position[]{new Position(2612, 3857), new Position(2614, 3859)}, 5375);

    private int index;
    private Position[] bounds;
    private int objectId;

    private SpecialTreePatch(int index, Position[] positionArray, int objectId) {
        this.index = index;
        this.bounds = positionArray;
        this.objectId = objectId;
    }

    public static SpecialTreePatch forPosition(Position position) {
        SpecialTreePatch[] specialTreePatchArray = SpecialTreePatch.values();
        int length = specialTreePatchArray.length;
        int index = 0;
        while (index < length) {
            SpecialTreePatch specialTreePatch;
            SpecialTreePatch specialTreePatch2 = specialTreePatch = specialTreePatchArray[index];
            specialTreePatch2 = specialTreePatch;
            if (FarmingPatchUtils.containsPosition(specialTreePatch.bounds[0], specialTreePatch2.bounds[1], position)) {
                return specialTreePatch;
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

    public final int getObjectId() {
        return this.objectId;
    }
}

