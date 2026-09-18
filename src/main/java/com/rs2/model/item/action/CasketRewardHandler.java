package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class CasketRewardHandler {
    private static int[] commonRewards = new int[]{995, 1623, 1454};
    private static int[] uncommonRewards = new int[]{1621, 1619, 1452};
    private static int[] rareRewards = new int[]{1617, 985, 987, 1462};

    public static void openCasket(Player player) {
        int value = GameUtil.randomInclusive(999) == 0 ? rareRewards[GameUtil.randomExclusive(4)] : (GameUtil.randomInclusive(99) == 0 ? uncommonRewards[GameUtil.randomExclusive(3)] : commonRewards[GameUtil.randomExclusive(3)]);
        player.getInventoryManager().addItem(new ItemStack(value, value == 995 ? GameUtil.randomInclusive(2980) + 20 : 1));
        player.packetSender.sendGameMessage("You open the casket and find some treasure inside.");
    }
}

