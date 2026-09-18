package com.rs2.model.npc;

import com.rs2.model.npc.Npc;
import com.rs2.model.task.TickTask;

public final class NpcSequenceAdvanceTask
extends TickTask {
    private Npc npc;

    public NpcSequenceAdvanceTask(Npc npc, int value2) {
        super(10);
        this.npc = npc;
    }

    @Override
    public final void execute() {
        ++this.npc.scriptedPathStage;
        if (this.npc.scriptedPathStage > 7) {
            this.npc.scriptedPathStage = 0;
            ++this.npc.scriptedSequenceLoopCount;
        }
        this.npc.queueSequenceAdvancePath(this.npc.scriptedPathStage);
        this.stop();
    }
}

