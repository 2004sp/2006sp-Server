package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class DaeroTrainingXpRewardTask
extends TickTask {
    private MonkeyMadnessQuest quest;
    private final Player player;
    private final int rewardChoice;
    private final int questFlagIndex;

    public DaeroTrainingXpRewardTask(MonkeyMadnessQuest monkeyMadnessQuest, int value4, Player player, int rewardChoice, int questFlagIndex) {
        super(10);
        this.quest = monkeyMadnessQuest;
        this.player = player;
        this.rewardChoice = rewardChoice;
        this.questFlagIndex = questFlagIndex;
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.showWalkableInterface(-1);
        player = this.player;
        player.packetSender.closeInterfaces();
        this.player.setActionLocked(false);
        if (this.rewardChoice == 1) {
            player = this.player;
            player.packetSender.sendGameMessage("You gain 35,000 Strength XP.");
            player = this.player;
            player.packetSender.sendGameMessage("You gain 35,000 Hitpoints XP.");
            player = this.player;
            player.packetSender.sendGameMessage("You gain 20,000 Attack XP.");
            player = this.player;
            player.packetSender.sendGameMessage("You gain 20,000 Defence XP.");
            this.player.getSkillManager().addQuestExperience(2, 35000.0);
            this.player.getSkillManager().addQuestExperience(3, 35000.0);
            this.player.getSkillManager().addQuestExperience(0, 20000.0);
            this.player.getSkillManager().addQuestExperience(1, 20000.0);
        } else {
            player = this.player;
            player.packetSender.sendGameMessage("You gain 35,000 Attack XP.");
            player = this.player;
            player.packetSender.sendGameMessage("You gain 35,000 Defence XP.");
            player = this.player;
            player.packetSender.sendGameMessage("You gain 20,000 Strength XP.");
            player = this.player;
            player.packetSender.sendGameMessage("You gain 20,000 Hitpoints XP.");
            this.player.getSkillManager().addQuestExperience(0, 35000.0);
            this.player.getSkillManager().addQuestExperience(1, 35000.0);
            this.player.getSkillManager().addQuestExperience(2, 20000.0);
            this.player.getSkillManager().addQuestExperience(3, 20000.0);
        }
        if (!this.quest.isProgressFlagSet(this.player, 17)) {
            int value = this.questFlagIndex;
            this.player.questProgressFlags[value] = this.player.questProgressFlags[value] + GameUtil.bitFlag(17);
        }
        this.stop();
    }
}

