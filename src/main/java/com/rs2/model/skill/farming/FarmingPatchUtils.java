package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.Position;
import com.rs2.model.player.Player;

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
        long utcMinutes = System.currentTimeMillis() / 60000L;
        return elapsedMinutes - Math.floorMod(utcMinutes, (long)growthCycleMinutes);
    }

    /**
     * Returns the total number of logical cycles a patch should have been offered.
     * Login processing catches up elapsed cycles. During a session only one cycle is
     * offered, and only when the player's five-minute farm tick lands in the first
     * five-minute window of the globally aligned crop-family cycle.
     */
    public static int getGrowthCycleTarget(Player player, long lastUpdateMinute, int growthCycleMinutes, int completedCycles) {
        if (player.isFarmingLoginCatchUp()) {
            long elapsedMinutes = Server.getElapsedMinutes() - lastUpdateMinute;
            return (int)Math.max(0L, elapsedMinutes / (long)growthCycleMinutes);
        }
        if (!isGrowthWindowOpen(growthCycleMinutes)) {
            return completedCycles;
        }
        return completedCycles + 1;
    }

    private static boolean isGrowthWindowOpen(int growthCycleMinutes) {
        long utcMinutes = System.currentTimeMillis() / 60000L;
        return Math.floorMod(utcMinutes, (long)growthCycleMinutes) < 5L;
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

