package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

public final class GloughDemonSpawnTask
extends TickTask {
    private final Player player;

    public GloughDemonSpawnTask(GrandTreeQuest grandTreeQuest, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        Npc npc;
        if (this.player.getInteractionTarget() != null && this.player.getInteractionTarget().isNpc() && (npc = (Npc)this.player.getInteractionTarget()).getNpcId() == 671) {
            npc.setActive(false);
            World.unregisterNpc(npc);
        }
        npc = new Npc(677);
        GameplayHelper.replaceOwnedRoamingNpcAtPosition(this.player, npc, 2484, 9864, 0, -1, true, false);
        this.player.setActionLocked(false);
        this.stop();
    }
}

