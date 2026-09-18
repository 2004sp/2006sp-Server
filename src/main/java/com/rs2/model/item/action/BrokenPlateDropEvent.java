package com.rs2.model.item.action;

import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.SpinningPlateHandler;
import com.rs2.model.item.action.SpinningPlateResultEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class BrokenPlateDropEvent
extends CycleEvent {
    private final Player player;

    public BrokenPlateDropEvent(SpinningPlateResultEvent spinningPlateResultEvent, Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(SpinningPlateHandler.getBrokenPlateItemId(), 1), this.player));
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

