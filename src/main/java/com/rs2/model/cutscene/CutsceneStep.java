package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;

public class CutsceneStep {
    private int delayTicks;

    public CutsceneStep(Cutscene cutscene, int delayTicks) {
        this.delayTicks = delayTicks;
    }

    public final int getDelayTicks() {
        return this.delayTicks;
    }

    public void executeStep() {
    }
}

