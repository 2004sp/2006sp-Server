package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.BrokenPlateDropEvent;
import com.rs2.model.item.action.SpinningPlateHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class SpinningPlateResultEvent
extends CycleEvent {
    private final Player player;

    public SpinningPlateResultEvent(Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.setActionLocked(false);
        if (GameUtil.randomInt(3) == 0) {
            this.player.getUpdateState().setAnimation(SpinningPlateHandler.getBreakPlateAnimationId(), 0);
            this.player.getInventoryManager().removeItem(new ItemStack(SpinningPlateHandler.getSpinningPlateItemId(), 1));
            CycleEventHandler.getInstance().schedule(this.player, new BrokenPlateDropEvent(this, this.player), 1);
        } else {
            this.player.getUpdateState().setAnimation(SpinningPlateHandler.getCatchPlateAnimationId(), 0);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

