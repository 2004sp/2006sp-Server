package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum HerbPatch {
    ARDOUGNE(0, new Position[]{new Position(2670, 3374), new Position(2671, 3375)}),
    MORYTANIA(1, new Position[]{new Position(3605, 3529), new Position(3606, 3530)}),
    FALADOR(2, new Position[]{new Position(3058, 3311), new Position(3059, 3312)}),
    CATHERBY(3, new Position[]{new Position(2813, 3463), new Position(2814, 3464)});

    private int index;
    private Position[] bounds;
    private HerbPatch(int index, Position[] positionArray) {
        this.index = index;
        this.bounds = positionArray;
    }

    public static HerbPatch forPosition(Position position) {
        HerbPatch[] herbPatchArray = HerbPatch.values();
        int length = herbPatchArray.length;
        int index = 0;
        while (index < length) {
            HerbPatch herbPatch;
            HerbPatch herbPatch2 = herbPatch = herbPatchArray[index];
            herbPatch2 = herbPatch;
            if (FarmingPatchUtils.containsPosition(herbPatch.bounds[0], herbPatch2.bounds[1], position)) {
                return herbPatch;
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

