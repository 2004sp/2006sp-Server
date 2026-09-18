package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.VampireAttackStartTask;
import com.rs2.model.quest.impl.VampireSlayerQuest;
import com.rs2.model.task.TickTask;

public final class VampireCoffinRiseTask
extends TickTask {
    private final TickTask attackStartTask;
    private final Npc vampireNpc;
    private final Player player;

    public VampireCoffinRiseTask(VampireSlayerQuest vampireSlayerQuest, int value2, Npc npc, Player player) {
        super(3);
        this.vampireNpc = npc;
        this.player = player;
        this.attackStartTask = new VampireAttackStartTask(this, 5, npc, player);
    }

    @Override
    public final void execute() {
        if (NpcDefinition.isDefined(3154)) {
            this.vampireNpc.transformToNpcId(3154, 5);
        }
        GameplayHelper.spawnOwnedGroundPlaneNpcAtPosition(this.player, this.vampireNpc, 3077, 9775, 0, -1, false, false);
        this.player.setActionLocked(false);
        this.vampireNpc.getUpdateState().setFacePositionValue(new Position(3077, 9770));
        this.vampireNpc.getUpdateState().setAnimation(3114);
        World.getTaskScheduler().schedule(this.attackStartTask);
        this.stop();
    }
}
