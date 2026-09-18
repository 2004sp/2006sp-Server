package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.VampireCoffinRiseTask;
import com.rs2.model.task.TickTask;

public final class VampireAttackStartTask
extends TickTask {
    private final Npc vampireNpc;
    private final Player player;

    public VampireAttackStartTask(VampireCoffinRiseTask vampireCoffinRiseTask, int value2, Npc npc, Player player) {
        super(5);
        this.vampireNpc = npc;
        this.player = player;
    }

    @Override
    public final void execute() {
        this.vampireNpc.getUpdateState().setAnimation(808);
        CombatManager.startCombat(this.vampireNpc, this.player);
        this.vampireNpc.moveTo(new Position(3077, 9774, 0));
        if (this.player.getInventoryManager().containsItem(1550)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("The vampire seems to weaken.");
        }
        this.stop();
    }
}

