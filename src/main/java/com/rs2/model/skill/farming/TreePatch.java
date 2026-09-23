package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import java.util.HashMap;
import java.util.Map;

public enum TreePatch {
    VARROCK(0, new Position[]{new Position(3228, 3458), new Position(3230, 3460)}, 2341),
    LUMBRIDGE(1, new Position[]{new Position(3192, 3230), new Position(3194, 3232)}, 2342),
    TAVERLEY(2, new Position[]{new Position(2935, 3437), new Position(2937, 3439)}, 2339),
    FALADOR(3, new Position[]{new Position(3003, 3372), new Position(3005, 3374)}, 2340);

    private int index;
    private Position[] bounds;
    private int objectId;
    private static Map patchByObjectId;

    static {
        patchByObjectId = new HashMap();
        TreePatch[] treePatchArray = TreePatch.values();
        int length = treePatchArray.length;
        int index = 0;
        while (index < length) {
            TreePatch treePatch = treePatchArray[index];
            patchByObjectId.put(treePatch.objectId, treePatch);
            ++index;
        }
    }

    public static TreePatch forObjectId(int objectId) {
        return (TreePatch)((Object)patchByObjectId.get(objectId));
    }

    private TreePatch(int index, Position[] positionArray, int objectId) {
        this.index = index;
        this.bounds = positionArray;
        this.objectId = objectId;
    }

    public static TreePatch forPosition(Position position) {
        TreePatch[] treePatchArray = TreePatch.values();
        int length = treePatchArray.length;
        int index = 0;
        while (index < length) {
            TreePatch treePatch;
            TreePatch treePatch2 = treePatch = treePatchArray[index];
            treePatch2 = treePatch;
            if (FarmingPatchUtils.containsPosition(treePatch.bounds[0], treePatch2.bounds[1], position)) {
                return treePatch;
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

