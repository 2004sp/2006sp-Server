package com.rs2.model.gameplay.barrows;

import com.rs2.model.World;
import com.rs2.model.gameplay.barrows.BarrowsManager;
import com.rs2.model.gameplay.barrows.BarrowsPrayerDrainResetTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class BarrowsPrayerDrainTask
extends TickTask {
    private final Player player;

    public BarrowsPrayerDrainTask(int value2, Player player) {
        super(30);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (this.player.isInBarrows()) {
            int value = BarrowsManager.prayerDrainModelItemIds[GameUtil.randomInt(12)];
            int value2 = BarrowsManager.prayerDrainModelInterfaceIds[GameUtil.randomInt(6)];
            Player player = this.player;
            player.packetSender.sendInterfaceModel(value2, 100, value);
            player = this.player;
            player.packetSender.sendInterfaceAnimation(value2, 2085);
            value = BarrowsManager.countKilledBrothers(this.player);
            int[] skillManager = this.player.getSkillManager().getCurrentLevels();
            skillManager[5] = skillManager[5] - (value += 8);
            if (this.player.getSkillManager().getCurrentLevels()[5] < 0) {
                this.player.getSkillManager().getCurrentLevels()[5] = 0;
            }
            this.player.getSkillManager().refreshSkill(5);
            BarrowsPrayerDrainResetTask barrowsPrayerDrainResetTask = new BarrowsPrayerDrainResetTask(this, 3, this.player, value2);
            World.getTaskScheduler().schedule(barrowsPrayerDrainResetTask);
            return;
        }
        this.player.activeRecurringEffectId = -1;
        this.stop();
    }
}

