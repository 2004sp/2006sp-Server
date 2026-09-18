package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class BirdNestSearchHandler {
    private static int[] commonSeedNestRewards = new int[]{5312, 5283, 5284, 5285, 5286, 5313};
    private static int[] uncommonSeedNestRewards = new int[]{5314, 5288, 5287, 5315, 5289};
    private static int[] rareSeedNestRewards = new int[]{5316, 5290};
    private static int[] veryRareSeedNestRewards = new int[]{5317};
    private static int[] commonRingNestRewards = new int[]{1635, 1637};
    private static int[] uncommonRingNestRewards = new int[]{1639};
    private static int[] rareRingNestRewards = new int[]{1641};
    private static int[] veryRareRingNestRewards = new int[]{1643};

    public static boolean searchNest(Player player, int value5) {
        int[] integerValues;
        int[] integerValues2;
        Object value2;
        int[] integerValues3;
        switch (value5) {
            case 5070: {
                player.getInventoryManager().removeItem(new ItemStack(value5));
                player.getInventoryManager().addOrDropItem(new ItemStack(5076));
                player.getInventoryManager().addOrDropItem(new ItemStack(5075));
                return true;
            }
            case 5071: {
                player.getInventoryManager().removeItem(new ItemStack(value5));
                player.getInventoryManager().addOrDropItem(new ItemStack(5078));
                player.getInventoryManager().addOrDropItem(new ItemStack(5075));
                return true;
            }
            case 5072: {
                player.getInventoryManager().removeItem(new ItemStack(value5));
                player.getInventoryManager().addOrDropItem(new ItemStack(5077));
                player.getInventoryManager().addOrDropItem(new ItemStack(5075));
                return true;
            }
            case 5073: {
                integerValues3 = commonSeedNestRewards;
                value2 = uncommonSeedNestRewards;
                integerValues2 = rareSeedNestRewards;
                integerValues = veryRareSeedNestRewards;
                break;
            }
            case 5074: {
                integerValues3 = commonRingNestRewards;
                value2 = uncommonRingNestRewards;
                integerValues2 = rareRingNestRewards;
                integerValues = veryRareRingNestRewards;
                break;
            }
            default: {
                return false;
            }
        }
        int value3 = GameUtil.randomInclusive(100);
        int value4 = value3 <= 60 ? integerValues3[GameUtil.randomInclusive(integerValues3.length - 1)] : (value3 <= 80 ? ((int[])value2)[GameUtil.randomInclusive(((int[])value2).length - 1)] : (value3 <= 95 ? integerValues2[GameUtil.randomInclusive(integerValues2.length - 1)] : integerValues[GameUtil.randomInclusive(0)]));
        Player player2 = player;
        value2 = player2;
        player2.packetSender.sendGameMessage("You search the nest...and find something in it!");
        player.getInventoryManager().removeItem(new ItemStack(value5));
        player.getInventoryManager().addOrDropItem(new ItemStack(value4));
        player.getInventoryManager().addOrDropItem(new ItemStack(5075));
        return true;
    }
}

