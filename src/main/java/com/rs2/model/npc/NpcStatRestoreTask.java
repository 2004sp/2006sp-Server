package com.rs2.model.npc;

import com.rs2.model.npc.Npc;
import com.rs2.model.task.TickTask;

public final class NpcStatRestoreTask
extends TickTask {
    private final Npc npc;
    private final Npc lifecycleNpc;
    private final int skillId;

    public NpcStatRestoreTask(Npc npc, int value3, Npc npc2, int skillId) {
        super(100);
        this.npc = npc;
        this.lifecycleNpc = npc2;
        this.skillId = skillId;
    }

    @Override
    public final void execute() {
        if (this.lifecycleNpc.isDead()) {
            this.stop();
            return;
        }
        if (this.npc.getCurrentLevelForSkill(this.skillId) != this.npc.getBaseLevelForSkill(this.skillId)) {
            if (this.npc.getCurrentLevelForSkill(this.skillId) > this.npc.getBaseLevelForSkill(this.skillId)) {
                this.npc.adjustCurrentLevel(this.skillId, -1);
            } else {
                this.npc.adjustCurrentLevel(this.skillId, 1);
            }
        }
        if (!this.npc.isStatModified(this.skillId)) {
            this.stop();
        }
    }
}
