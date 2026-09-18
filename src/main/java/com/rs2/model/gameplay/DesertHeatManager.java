package com.rs2.model.gameplay;

import com.rs2.model.World;
import com.rs2.model.gameplay.DesertThirstTask;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class DesertHeatManager {
    public static int DESERT_SHIRT_ITEM_ID = 1833;
    public static int DESERT_ROBE_ITEM_ID = 1835;
    public static int DESERT_BOOTS_ITEM_ID = 1837;
    public static int MENAPHITE_PURPLE_HAT_ITEM_ID = 6392;
    public static int MENAPHITE_PURPLE_TOP_ITEM_ID = 6394;
    public static int MENAPHITE_PURPLE_ROBE_ITEM_ID = 6396;
    public static int MENAPHITE_PURPLE_KILT_ITEM_ID = 6398;
    public static int MENAPHITE_RED_HAT_ITEM_ID = 6400;
    public static int MENAPHITE_RED_TOP_ITEM_ID = 6402;
    public static int MENAPHITE_RED_ROBE_ITEM_ID = 6404;
    public static int MENAPHITE_RED_KILT_ITEM_ID = 6406;
    private static int[] desertHeatRegionIds = new int[]{12589, 12844, 12845, 12846, 12848, 12847, 13100, 13101, 13102, 13356, 13357, 13359, 13360, 13614, 13615, 13616, 13617, 13872, 13873};

    static boolean isInDesertHeatRegion(Player player) {
        int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
        int[] integerValues = desertHeatRegionIds;
        int length = desertHeatRegionIds.length;
        int index = 0;
        while (index < length) {
            int value = integerValues[index];
            if (value == regionId) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static boolean updateDesertHeatHazard(Player player) {
        if (player.activeEnvironmentalHazardId == 1234) {
            return true;
        }
        if (DesertHeatManager.isInDesertHeatRegion(player)) {
            DesertThirstTask desertThirstTask = new DesertThirstTask(150, player);
            World.getTaskScheduler().schedule(desertThirstTask);
            player.activeEnvironmentalHazardId = 1234;
            return true;
        }
        return false;
    }
}

