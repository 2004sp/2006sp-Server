package com.rs2.model.travel.canoe;

import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.travel.canoe.CanoeTravelManager;
import com.rs2.model.travel.canoe.CanoeTreeDefinition;

public final class CanoeTreeCutTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final CanoeTreeDefinition treeDefinition;
    private final GatheringToolDefinition gatheringTool;

    public CanoeTreeCutTask(Player player, int actionSequence, CanoeTreeDefinition canoeTreeDefinition, GatheringToolDefinition gatheringToolDefinition) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.treeDefinition = canoeTreeDefinition;
        this.gatheringTool = gatheringToolDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        this.player.configStates[CanoeTravelManager.CANOE_CONFIG_ID] = 10 << this.treeDefinition.getConfigShift();
        Player player = this.player;
        player.packetSender.sendConfig(CanoeTravelManager.CANOE_CONFIG_ID, this.player.configStates[CanoeTravelManager.CANOE_CONFIG_ID]);
        player = this.player;
        player.packetSender.sendSoundEffect(1312, 1, 0);
        this.player.getUpdateState().setAnimation(this.gatheringTool.getGatherAnimationId(), 0);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.getMovementQueue().clear();
        this.player.getUpdateState().setAnimation(-1, 0);
    }
}

