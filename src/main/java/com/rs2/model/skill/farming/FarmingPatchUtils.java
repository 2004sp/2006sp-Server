package com.rs2.model.skill.farming;

import com.rs2.Server;
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

    public static long getCurrentGrowthCycleStart(int growthCycleMinutes) {
        if (growthCycleMinutes <= 0) {
            throw new IllegalArgumentException("Growth cycle must be positive.");
        }
        long elapsedMinutes = Math.max(1L, Server.getElapsedMinutes());
        return elapsedMinutes - (elapsedMinutes - 1L) % growthCycleMinutes;
    }

    public static Position[] getInteractionBounds(Position position) {
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(position);
        if (allotmentPatch != null) return allotmentPatch.getInteractionBounds(position);
        FlowerPatch flowerPatch = FlowerPatch.forPosition(position);
        if (flowerPatch != null) return flowerPatch.getInteractionBounds();
        HerbPatch herbPatch = HerbPatch.forPosition(position);
        if (herbPatch != null) return herbPatch.getInteractionBounds();
        HopsPatch hopsPatch = HopsPatch.forPosition(position);
        if (hopsPatch != null) return hopsPatch.getInteractionBounds();
        BushPatch bushPatch = BushPatch.forPosition(position);
        if (bushPatch != null) return bushPatch.getInteractionBounds();
        TreePatch treePatch = TreePatch.forPosition(position);
        if (treePatch != null) return treePatch.getInteractionBounds();
        FruitTreePatch fruitTreePatch = FruitTreePatch.forPosition(position);
        if (fruitTreePatch != null) return fruitTreePatch.getInteractionBounds();
        SpecialTreePatch specialTreePatch = SpecialTreePatch.forPosition(position);
        if (specialTreePatch != null) return specialTreePatch.getInteractionBounds();
        SpecialCropPatch specialCropPatch = SpecialCropPatch.forPosition(position);
        return specialCropPatch == null ? null : specialCropPatch.getInteractionBounds();
    }
}

