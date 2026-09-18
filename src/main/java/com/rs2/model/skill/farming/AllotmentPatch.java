package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import java.util.ArrayList;

public enum AllotmentPatch {
    CATHERBY_NORTH(0, new Position[]{new Position(2805, 3466), new Position(2806, 3468), new Position(2805, 3467), new Position(2814, 3468)}, 2324),
    CATHERBY_SOUTH(1, new Position[]{new Position(2805, 3459), new Position(2806, 3461), new Position(2805, 3459), new Position(2814, 3460)}, 2324),
    FALADOR_WEST(2, new Position[]{new Position(3050, 3307), new Position(3051, 3312), new Position(3050, 3311), new Position(3054, 3312)}, 2323),
    FALADOR_EAST(3, new Position[]{new Position(3055, 3303), new Position(3059, 3304), new Position(3058, 3303), new Position(3059, 3308)}, 2323),
    MORYTANIA_WEST(4, new Position[]{new Position(3597, 3525), new Position(3598, 3530), new Position(3597, 3529), new Position(3601, 3530)}, 2326),
    MORYTANIA_EAST(5, new Position[]{new Position(3602, 3521), new Position(3606, 3522), new Position(3605, 3521), new Position(3606, 3526)}, 2326),
    ARDOUGNE_NORTH(6, new Position[]{new Position(2662, 3377), new Position(2663, 3379), new Position(2662, 3378), new Position(2671, 3379)}, 2325),
    ARDOUGNE_SOUTH(7, new Position[]{new Position(2662, 3370), new Position(2663, 3372), new Position(2662, 3370), new Position(2671, 3371)}, 2325);

    private int index;
    private Position[] bounds;
    private int objectId;

    private AllotmentPatch(int index, Position[] positionArray, int objectId) {
        this.index = index;
        this.bounds = positionArray;
        this.objectId = objectId;
    }

    public static AllotmentPatch forPosition(Position position) {
        AllotmentPatch[] allotmentPatchArray = AllotmentPatch.values();
        int length = allotmentPatchArray.length;
        int index = 0;
        while (index < length) {
            forPositionControlExit1: {
                AllotmentPatch allotmentPatch;
                forPositionControlExit2: {
                    AllotmentPatch allotmentPatch2;
                    AllotmentPatch allotmentPatch3 = allotmentPatch = allotmentPatchArray[index];
                    allotmentPatch3 = allotmentPatch;
                    if (FarmingPatchUtils.containsPosition(allotmentPatch.bounds[0], allotmentPatch3.bounds[1], position)) break forPositionControlExit2;
                    allotmentPatch3 = allotmentPatch;
                    allotmentPatch3 = allotmentPatch;
                    if (!FarmingPatchUtils.containsPosition(allotmentPatch.bounds[2], allotmentPatch3.bounds[3], position)) break forPositionControlExit1;
                }
                return allotmentPatch;
            }
            ++index;
        }
        return null;
    }

    public static ArrayList getIndexesForObjectId(int objectId) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        AllotmentPatch[] allotmentPatchArray = AllotmentPatch.values();
        int length = allotmentPatchArray.length;
        int index = 0;
        while (index < length) {
            AllotmentPatch allotmentPatch;
            AllotmentPatch allotmentPatch2 = allotmentPatch = allotmentPatchArray[index];
            if (allotmentPatch.objectId == objectId) {
                arrayList.add(allotmentPatch.index);
            }
            ++index;
        }
        return arrayList;
    }

    public final int getIndex() {
        return this.index;
    }
}

