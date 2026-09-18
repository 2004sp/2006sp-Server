package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.task.TickTask;

public final class CutsceneStepTask
extends TickTask {
    private final CutsceneStep step;

    public CutsceneStepTask(Cutscene cutscene, int value2, CutsceneStep cutsceneStep) {
        super(value2);
        this.step = cutsceneStep;
    }

    @Override
    public final void execute() {
        this.step.executeStep();
        this.stop();
    }
}

