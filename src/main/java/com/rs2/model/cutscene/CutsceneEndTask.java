package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.task.TickTask;

public final class CutsceneEndTask
extends TickTask {
    private Cutscene cutscene;

    public CutsceneEndTask(Cutscene cutscene, int value2) {
        super(value2);
        this.cutscene = cutscene;
    }

    @Override
    public final void execute() {
        this.cutscene.finishCutscene();
        this.stop();
    }
}

