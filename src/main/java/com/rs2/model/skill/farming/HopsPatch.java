package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import java.util.HashMap;
import java.util.Map;

public enum HopsPatch {
    LUMBRIDGE(0, new Position[]{new Position(3227, 3313, 0), new Position(3231, 3317, 0)}, 2333),
    SEERS_VILLAGE(1, new Position[]{new Position(2664, 3523, 0), new Position(2669, 3528, 0)}, 2334),
    YANILLE(2, new Position[]{new Position(2574, 3103, 0), new Position(2577, 3106, 0)}, 2332),
    ENTRANA(3, new Position[]{new Position(2809, 3335, 0), new Position(2812, 3338, 0)}, 2327);

    private int index;
    private Position[] bounds;
    private int objectId;
    private static Map patchByObjectId;

    static {
        patchByObjectId = new HashMap();
        HopsPatch[] hopsPatchArray = HopsPatch.values();
        int length = hopsPatchArray.length;
        int index = 0;
        while (index < length) {
            HopsPatch hopsPatch = hopsPatchArray[index];
            patchByObjectId.put(hopsPatch.objectId, hopsPatch);
            ++index;
        }
    }

    public static HopsPatch forObjectId(int objectId) {
        return (HopsPatch)((Object)patchByObjectId.get(objectId));
    }

    private HopsPatch(int index, Position[] positionArray, int objectId) {
        this.index = index;
        this.bounds = positionArray;
        this.objectId = objectId;
    }

    public static HopsPatch forPosition(Position position) {
        HopsPatch[] hopsPatchArray = HopsPatch.values();
        int length = hopsPatchArray.length;
        int index = 0;
        while (index < length) {
            HopsPatch hopsPatch;
            HopsPatch hopsPatch2 = hopsPatch = hopsPatchArray[index];
            hopsPatch2 = hopsPatch;
            if (FarmingPatchUtils.containsPosition(hopsPatch.bounds[0], hopsPatch2.bounds[1], position)) {
                return hopsPatch;
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

