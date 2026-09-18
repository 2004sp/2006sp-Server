package com.rs2;

import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.path.WalkingCollisionMap;

public final class CacheCoordinateTranslator {
    public static boolean dungeonCoordinateShiftActive = false;
    private static int[] dungeonCoordinateShiftSourceRegionIds = new int[]{9540, 9541, 9797};
    private static int[] dungeonCoordinateShiftTargetRegionIds = new int[]{12692, 12693, 12949};
    private static int dungeonCoordinateShiftXOffset = 768;
    private static int dungeonCoordinateShiftYOffset = 5120;

    public static boolean isDungeonCoordinateShiftSourceRegion(int value3, int value22) {
        value3 = GameUtil.getRegionId(value3, value22);
        int[] integerValues = dungeonCoordinateShiftSourceRegionIds;
        int length = dungeonCoordinateShiftSourceRegionIds.length;
        int index = 0;
        while (index < length) {
            value22 = integerValues[index];
            if (value3 == value22) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static void detectDungeonCoordinateShift() {
        if (!WalkingCollisionMap.hasDungeonCoordinateShiftRegion(9797)) {
            dungeonCoordinateShiftActive = true;
        }
    }

    public static void translateSavedDungeonPosition(Player player) {
        int position = player.getPosition().getX();
        int position2 = player.getPosition().getY();
        if (player.savedCacheVersion < 319 && !dungeonCoordinateShiftActive) {
            boolean enabled;
            translateSavedDungeonPositionControlExit1: {
                int value = position2;
                int value2 = position;
                value2 = GameUtil.getRegionId(value2, value);
                int[] integerValues = dungeonCoordinateShiftTargetRegionIds;
                int length = dungeonCoordinateShiftTargetRegionIds.length;
                int index = 0;
                while (index < length) {
                    value = integerValues[index];
                    if (value2 == value) {
                        enabled = true;
                        break translateSavedDungeonPositionControlExit1;
                    }
                    ++index;
                }
                enabled = false;
            }
            if (enabled) {
                player.getPosition().setX(position - dungeonCoordinateShiftXOffset);
                player.getPosition().setY(position2 - dungeonCoordinateShiftYOffset);
                player.getPosition().setPreviousX(player.getPosition().getX());
                player.getPosition().setPreviousY(player.getPosition().getY());
            }
        }
        if (player.savedCacheVersion > 289 && dungeonCoordinateShiftActive && CacheCoordinateTranslator.isDungeonCoordinateShiftSourceRegion(position, position2)) {
            player.getPosition().setX(position + dungeonCoordinateShiftXOffset);
            player.getPosition().setY(position2 + dungeonCoordinateShiftYOffset);
            player.getPosition().setPreviousX(player.getPosition().getX());
            player.getPosition().setPreviousY(player.getPosition().getY());
        }
    }
}
