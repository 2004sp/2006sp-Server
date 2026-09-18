package com.rs2.model.randomevent.sandwichlady;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.sandwichlady.SandwichLadyManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SandwichLadyCleanupEvent
extends CycleEvent {
    private SandwichLadyManager manager;
    private final Npc npc;

    public SandwichLadyCleanupEvent(SandwichLadyManager sandwichLadyManager, Npc npc) {
        this.manager = sandwichLadyManager;
        this.npc = npc;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = SandwichLadyManager.getPlayer(this.manager);
        player.packetSender.sendStillGraphic(86, this.npc.getPosition(), 0x640000);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        GameplayHelper.unregisterTemporaryNpc(this.npc);
    }
}

