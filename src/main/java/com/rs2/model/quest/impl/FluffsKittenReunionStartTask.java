package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FluffsKittenReunionFinishTask;
import com.rs2.model.quest.impl.GertrudesCatQuest;
import com.rs2.model.task.TickTask;

public final class FluffsKittenReunionStartTask
extends TickTask {
    private final TickTask reunionFinishTask;
    private final Npc kitten;
    private final Npc fluffs;

    public FluffsKittenReunionStartTask(GertrudesCatQuest gertrudesCatQuest, int value2, Npc npc, Npc npc2, Player player) {
        super(2);
        this.kitten = npc;
        this.fluffs = npc2;
        this.reunionFinishTask = new FluffsKittenReunionFinishTask(this, 3, npc2, player, npc);
    }

    @Override
    public final void execute() {
        this.kitten.queueScriptedPath(new Position[]{new Position(3309, 3509, 1)});
        this.fluffs.setAttackRange(1);
        this.fluffs.setMovementTarget(this.fluffs);
        World.getTaskScheduler().schedule(this.reunionFinishTask);
        this.stop();
    }
}
