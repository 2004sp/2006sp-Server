package com.rs2.model.gameplay.fightcave;

import com.rs2.model.gameplay.fightcave.FightCaveController;
import com.rs2.model.task.TickTask;

public final class FightCaveCompletionTask
extends TickTask {
    private FightCaveController controller;

    public FightCaveCompletionTask(FightCaveController fightCaveController, int value2) {
        super(3);
        this.controller = fightCaveController;
    }

    @Override
    public final void execute() {
        ++FightCaveController.getPlayer((FightCaveController)this.controller).fightCaveWaveIndex;
        this.controller.leaveFightCave();
        FightCaveController.getPlayer(this.controller).getDialogueManager().setDialogueNpcId(2617);
        FightCaveController.getPlayer(this.controller).getDialogueManager().showNpcTwoLineDialogue("You even defeated TzTok-Jad, I am most impressed!", "Please accept this gift as a reward.", 591);
        FightCaveController.getPlayer(this.controller).getDialogueManager().finishDialogue();
        this.stop();
    }
}

