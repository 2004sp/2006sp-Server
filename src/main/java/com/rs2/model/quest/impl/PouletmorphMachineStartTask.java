package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.quest.impl.PouletmorphMachineTransformTask;
import com.rs2.model.task.TickTask;

public final class PouletmorphMachineStartTask
extends TickTask {
    private final TickTask transformTask;
    final ErnestTheChickenQuest quest;
    private final Player player;

    public PouletmorphMachineStartTask(ErnestTheChickenQuest ernestTheChickenQuest, int value2, Player player) {
        super(value2);
        this.quest = ernestTheChickenQuest;
        this.player = player;
        this.transformTask = new PouletmorphMachineTransformTask(this, 1, player);
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.sendGameMessage("Oddenstein starts up the machine.");
        World.getTaskScheduler().schedule(this.transformTask);
        this.stop();
    }
}
