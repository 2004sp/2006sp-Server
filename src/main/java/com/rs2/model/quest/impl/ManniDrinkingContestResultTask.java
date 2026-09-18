package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ManniDrinkingContestPlayerDrinkTask;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ManniDrinkingContestResultTask
extends TickTask {
    private final Player player;
    private final int questId;

    public ManniDrinkingContestResultTask(ManniDrinkingContestPlayerDrinkTask manniDrinkingContestPlayerDrinkTask, int value3, Player player, int questId) {
        super(7);
        this.player = player;
        this.questId = questId;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        if ((this.player.questProgressFlags[this.questId] & GameUtil.bitFlag(1)) == 0) {
            int value = this.questId;
            this.player.questProgressFlags[value] = this.player.questProgressFlags[value] + GameUtil.bitFlag(1);
        }
        if (this.player.pendingGameMode == 3711) {
            if ((this.player.getQuestState(this.questId) & GameUtil.bitFlag(3)) == 0) {
                this.player.addQuestState(this.questId, GameUtil.bitFlag(3));
            }
            this.player.getDialogueManager().showPlayerTwoLineDialogue("Aaaah, lovely stuff. So you want to get the next round", "in, or shall I? You don't look so good there!", 591);
            Player player = this.player;
            player.packetSender.sendGameMessage("Congratulations! You have completed the Revellers' trial.");
            this.player.getDialogueManager().setNextDialogueStep(8);
        } else {
            this.player.getDialogueManager().showPlayerTwoLineDialogue("Ish no' fair! (hic) I canna drink another drop! I alsho", "feel veddy, veddy ill...", 591);
        }
        this.stop();
    }
}
