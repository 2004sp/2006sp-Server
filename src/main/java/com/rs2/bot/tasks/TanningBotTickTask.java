package com.rs2.bot.tasks;

import com.rs2.bot.tasks.AlKharidCowhideTanningBotTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class TanningBotTickTask
extends TickTask {
    private final Player player;

    public TanningBotTickTask(AlKharidCowhideTanningBotTask alKharidCowhideTanningBotTask, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered() || !this.player.currentBotTask.usesCustomTaskAction || !this.player.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (!this.player.getInventoryManager().containsItem(this.player.botTaskItemId)) {
            this.player.currentBotTask.startWalkToBank(this.player);
            this.stop();
            return;
        }
        if (this.player.getOpenInterfaceId() == 14670 || this.player.getOpenInterfaceId() == 679) {
            Player player = this.player;
            int value = player.botTaskItemId;
            int initialValue = 1;
            int initialValue2 = 1;
            if (value == 1739) {
                if (player.getSkillManager().getCurrentLevels()[12] < 28) {
                    initialValue = 1;
                    initialValue2 = 1741;
                } else {
                    initialValue = 3;
                    initialValue2 = 1743;
                }
            } else if (value == 1753) {
                initialValue = 20;
                initialValue2 = 1745;
            } else if (value == 1751) {
                initialValue = 20;
                initialValue2 = 2505;
            } else if (value == 1749) {
                initialValue = 20;
                initialValue2 = 2507;
            } else if (value == 1747) {
                initialValue = 20;
                initialValue2 = 2509;
            }
            GameplayHelper.tanHide(player, 27, initialValue, value, initialValue2);
            return;
        }
        this.player.botInteractionOption = 2;
        this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
    }
}

