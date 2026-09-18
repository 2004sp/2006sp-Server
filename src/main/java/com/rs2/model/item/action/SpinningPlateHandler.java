package com.rs2.model.item.action;

import com.rs2.model.item.action.SpinningPlateResultEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;

public final class SpinningPlateHandler {
    private static int spinningPlateItemId = 4613;
    private static int brokenPlateItemId = 4614;
    private static int startSpinAnimationId = 1902;
    private static int catchPlateAnimationId = 1904;
    private static int breakPlateAnimationId = 1906;

    public static boolean spinPlate(Player player, int value2) {
        if (value2 == spinningPlateItemId) {
            player.setActionLocked(true);
            player.getUpdateState().setAnimation(startSpinAnimationId, 0);
            CycleEventHandler.getInstance().schedule(player, new SpinningPlateResultEvent(player), 5);
            return true;
        }
        return false;
    }

    static int getBreakPlateAnimationId() {
        return breakPlateAnimationId;
    }

    static int getSpinningPlateItemId() {
        return spinningPlateItemId;
    }

    static int getBrokenPlateItemId() {
        return brokenPlateItemId;
    }

    static int getCatchPlateAnimationId() {
        return catchPlateAnimationId;
    }
}

