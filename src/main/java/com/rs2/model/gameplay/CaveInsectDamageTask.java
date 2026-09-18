package com.rs2.model.gameplay;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.CaveInsectSwarmTask;
import com.rs2.model.gameplay.CaveLightManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class CaveInsectDamageTask
extends TickTask {
    private final Player player;

    public CaveInsectDamageTask(CaveInsectSwarmTask caveInsectSwarmTask, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (CaveLightManager.isInCaveInsectRegion(this.player)) {
            if (this.player.getActiveCaveLightLevel() == 0) {
                this.player.applyDirectHit(1, HitType.NORMAL);
                this.player.getRecentCombatTimer().setTargetDelay(null, 17);
                return;
            }
            this.player.caveInsectSwarmStage = 0;
            this.stop();
            return;
        }
        this.player.caveInsectSwarmStage = 0;
        this.stop();
    }
}

