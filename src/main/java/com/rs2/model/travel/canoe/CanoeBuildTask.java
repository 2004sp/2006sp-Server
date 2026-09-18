package com.rs2.model.travel.canoe;

import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class CanoeBuildTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final CanoeTypeDefinition canoeType;
    private final GatheringToolDefinition gatheringTool;

    public CanoeBuildTask(Player player, int actionSequence, CanoeTypeDefinition canoeTypeDefinition, GatheringToolDefinition gatheringToolDefinition) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.canoeType = canoeTypeDefinition;
        this.gatheringTool = gatheringToolDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        this.player.getSkillManager().addExperience(8, this.canoeType.experience);
        this.player.configStates[CanoeTravelManager.CANOE_CONFIG_ID] = this.canoeType.configValue << (this.player.canoeStationIndex << 3);
        this.player.packetSender.sendConfig(CanoeTravelManager.CANOE_CONFIG_ID, this.player.configStates[CanoeTravelManager.CANOE_CONFIG_ID]);
        this.player.builtCanoeTypeConfigValue = this.canoeType.configValue;
        this.player.packetSender.sendSoundEffect(473, 1, 0);
        this.player.getUpdateState().setAnimation(this.gatheringTool.getCanoeAnimationId(), 0);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.getMovementQueue().clear();
        this.player.getUpdateState().setAnimation(-1, 0);
    }
}
