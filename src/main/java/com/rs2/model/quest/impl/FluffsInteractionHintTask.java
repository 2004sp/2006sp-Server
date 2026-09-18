package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GertrudesCatQuest;
import com.rs2.model.task.TickTask;

public final class FluffsInteractionHintTask
extends TickTask {
    private final int questStage;
    private final Player player;

    public FluffsInteractionHintTask(GertrudesCatQuest gertrudesCatQuest, int value3, int questStage, Player player) {
        super(2);
        this.questStage = questStage;
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.questStage == 3) {
            this.player.getDialogueManager().showOneLineStatement("Maybe the cat is thirsty?");
        }
        if (this.questStage == 4) {
            this.player.getDialogueManager().showOneLineStatement("Maybe the cat is hungry?");
        }
        if (this.questStage == 5) {
            this.player.getDialogueManager().showTwoLineStatement("The cat seems afraid to leave.", "In the distance you can hear kittens mewing...");
        }
        this.player.setActionLocked(false);
        this.stop();
    }
}
