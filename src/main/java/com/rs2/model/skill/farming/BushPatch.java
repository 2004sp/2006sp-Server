package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import java.util.HashMap;
import java.util.Map;

public enum BushPatch {
    PATCH_0_OBJECT_2337(0, new Position[]{new Position(2591, 3863), new Position(2592, 3864)}, 2337),
    PATCH_1_OBJECT_2338(1, new Position[]{new Position(2617, 3225), new Position(2618, 3226)}, 2338),
    PATCH_2_OBJECT_2335(2, new Position[]{new Position(3181, 3357), new Position(3182, 3358)}, 2335),
    PATCH_3_OBJECT_2336(3, new Position[]{new Position(2940, 3221), new Position(2941, 3222)}, 2336);

    private int index;
    private Position[] bounds;
    private int objectId;
    private static Map patchByObjectId;

    static {
        patchByObjectId = new HashMap();
        BushPatch[] bushPatchArray = BushPatch.values();
        int length = bushPatchArray.length;
        int index = 0;
        while (index < length) {
            BushPatch bushPatch = bushPatchArray[index];
            patchByObjectId.put(bushPatch.objectId, bushPatch);
            ++index;
        }
    }

    public static BushPatch forObjectId(int objectId) {
        return (BushPatch)((Object)patchByObjectId.get(objectId));
    }

    private BushPatch(int index, Position[] positionArray, int objectId) {
        this.index = index;
        this.bounds = positionArray;
        this.objectId = objectId;
    }

    public static BushPatch forPosition(Position position) {
        BushPatch[] bushPatchArray = BushPatch.values();
        int length = bushPatchArray.length;
        int index = 0;
        while (index < length) {
            BushPatch bushPatch;
            BushPatch bushPatch2 = bushPatch = bushPatchArray[index];
            bushPatch2 = bushPatch;
            if (FarmingPatchUtils.containsPosition(bushPatch.bounds[0], bushPatch2.bounds[1], position)) {
                return bushPatch;
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

