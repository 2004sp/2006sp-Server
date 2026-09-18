package com.rs2.model.gameplay;

import com.rs2.model.World;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.CaveInsectDamageTask;
import com.rs2.model.gameplay.CaveLightManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class CaveInsectSwarmTask
extends TickTask {
    private final Player player;

    public CaveInsectSwarmTask(int value2, Player player) {
        super(15);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (CaveLightManager.isInCaveInsectRegion(this.player)) {
            if (this.player.getActiveCaveLightLevel() == 0 && this.player.caveInsectSwarmStage == 0) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You hear tiny insects skittering over the ground...");
                this.player.caveInsectSwarmStage = 1;
                return;
            }
            if (this.player.getActiveCaveLightLevel() == 0 && this.player.caveInsectSwarmStage == 1) {
                Object value = this.player;
                ((Player)value).packetSender.sendGameMessage("Tiny biting insects swarm all over you!");
                this.player.applyDirectHit(1, HitType.NORMAL);
                this.player.getRecentCombatTimer().setTargetDelay(null, 17);
                this.player.caveInsectSwarmStage = 2;
                World.getTaskScheduler().schedule(new CaveInsectDamageTask(this, 3, this.player));
                return;
            }
        } else {
            this.player.activeEnvironmentalHazardId = -1;
            this.stop();
        }
    }
}

