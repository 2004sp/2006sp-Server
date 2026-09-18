package com.rs2.model.gameplay;

import com.rs2.model.World;
import com.rs2.model.gameplay.CaveInsectSwarmTask;
import com.rs2.model.gameplay.SwampGasExplosionTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;

public final class CaveLightManager {
    private static RectangularArea[] swampGasAreas = new RectangularArea[]{new RectangularArea(3155, 9584, 3174, 9596), new RectangularArea(3203, 9551, 3213, 9560)};
    private static int[] caveInsectRegionIds = new int[]{12693, 12949};

    static boolean isInCaveInsectRegion(Player player) {
        int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
        int[] integerValues = caveInsectRegionIds;
        int length = caveInsectRegionIds.length;
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

    static boolean isInSwampGasArea(Player player) {
        Object value;
        RectangularArea[] rectangularAreaArray = swampGasAreas;
        int length = swampGasAreas.length;
        int index = 0;
        while (index < length) {
            value = rectangularAreaArray[index];
            if (((RectangularArea)value).contains(player.getPosition())) {
                return true;
            }
            ++index;
        }
        if (player.swampGasFlareState == 1 && (value = player.findLitCaveLightSource()) != null) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Your " + ((ItemStack)value).getDefinition().getName().toLowerCase() + " stops flaring.");
        }
        player.swampGasFlareState = 0;
        return false;
    }

    public static boolean updateCaveLightHazards(Player player) {
        Object litCaveLightSource = player.findLitCaveLightSource();
        if (CaveLightManager.isInSwampGasArea(player) && litCaveLightSource != null && player.swampGasFlareState == 0) {
            player.swampGasFlareState = 1;
            Player player2 = player;
            player2.packetSender.sendGameMessage("Your " + ((ItemStack)litCaveLightSource).getDefinition().getName().toLowerCase() + " flares brightly!");
            World.getTaskScheduler().schedule(new SwampGasExplosionTask(7, player));
        }
        if (player.activeEnvironmentalHazardId == 1657) {
            return true;
        }
        if (CaveLightManager.isInCaveInsectRegion(player)) {
            World.getTaskScheduler().schedule(new CaveInsectSwarmTask(15, player));
            player.activeEnvironmentalHazardId = 1657;
            return true;
        }
        return false;
    }
}

