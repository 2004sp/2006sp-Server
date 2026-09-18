package com.rs2.model.quest.impl;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ApeAtollDungeonHazardDamageTask
extends TickTask {
    private final MonkeyMadnessQuest quest;
    private final Player player;

    public ApeAtollDungeonHazardDamageTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(25);
        this.quest = monkeyMadnessQuest;
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (this.quest.apeAtollDungeonHazardArea.containsExclusive(this.player.getPosition()) && !this.quest.apeAtollDungeonSafeArea.containsExclusive(this.player.getPosition())) {
            this.player.getUpdateState().setGraphic(60, 0);
            this.player.applyDirectHit(GameUtil.randomInclusive(2), HitType.NORMAL);
            return;
        }
        this.player.activeEnvironmentalHazardId = -1;
        this.stop();
    }
}
