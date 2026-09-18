package com.rs2.model.item.action;

import com.rs2.model.item.action.ToyHorseyUnlockEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class ToyHorseyHandler {
    private static String[] forcedTextLines = new String[]{"Come on Dobbin, we can win the race!", "Hi-ho Silver, and away!", "Neaahhhyyy! Giddy-up horsey!"};
    private static int[][] itemAnimationPairs = new int[][]{{2520, 918}, {2522, 919}, {2524, 920}, {2526, 921}};

    public static boolean play(Player player, int value2) {
        int[][] integerValues = itemAnimationPairs;
        int length = itemAnimationPairs.length;
        int index = 0;
        while (index < length) {
            int[] integerValues2 = integerValues[index];
            if (integerValues2[0] == value2) {
                player.setActionLocked(true);
                player.getUpdateState().setAnimation(integerValues2[1], 0);
                value2 = GameUtil.randomInt(forcedTextLines.length);
                player.getUpdateState().setForcedText(forcedTextLines[value2]);
                CycleEventHandler.getInstance().schedule(player, new ToyHorseyUnlockEvent(player), 3);
                return true;
            }
            ++index;
        }
        return false;
    }
}

