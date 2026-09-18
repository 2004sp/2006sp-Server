package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCutscene;
import com.rs2.model.item.ItemStack;

public final class RestlessGhostCompletionStep
extends CutsceneStep {
    private RestlessGhostCutscene cutscene;

    public RestlessGhostCompletionStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int value2) {
        super(cutscene, 5);
        this.cutscene = restlessGhostCutscene;
    }

    @Override
    public final void executeStep() {
        this.cutscene.player.getInventoryManager().removeItem(new ItemStack(553));
        RestlessGhostCutscene.questScript.awardCompletionRewards(this.cutscene.player);
    }
}

