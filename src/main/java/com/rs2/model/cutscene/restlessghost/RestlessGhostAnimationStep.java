package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCutscene;

public final class RestlessGhostAnimationStep
extends CutsceneStep {
    private RestlessGhostCutscene cutscene;

    public RestlessGhostAnimationStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int value2) {
        super(cutscene, 3);
        this.cutscene = restlessGhostCutscene;
    }

    @Override
    public final void executeStep() {
        this.cutscene.ghost.getUpdateState().setAnimation(1500);
        this.cutscene.ghost.getUpdateState().setGraphic(189, 25);
    }
}

