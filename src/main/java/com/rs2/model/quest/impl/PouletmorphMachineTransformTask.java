package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestHumanDialogueTask;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.quest.impl.PouletmorphMachineStartTask;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class PouletmorphMachineTransformTask
extends TickTask {
    private final PouletmorphMachineStartTask startTask;
    private final Player player;

    public PouletmorphMachineTransformTask(PouletmorphMachineStartTask pouletmorphMachineStartTask, int value2, Player player) {
        super(1);
        this.startTask = pouletmorphMachineStartTask;
        this.player = player;
    }

    @Override
    public final void execute() {
        Entity entity = this.player;
        ((Player)entity).packetSender.sendGameMessage("The machine hums and shakes.");
        entity = Npc.findByDefinitionId(288);
        int value = 605;
        Position position = entity.getPosition();
        Position position2 = new Position(3111, 3366, 2);
        Object value2 = this.player;
        int distance = GameUtil.getDistance(position2, position);
        new WoodcuttingHandler(position2, 0, position, 0, new ProjectileDefinition(605, ProjectileTiming.MAGIC)).sendProjectileToNearbyPlayers();
        World.getTaskScheduler().schedule(new ErnestHumanDialogueTask(this.startTask.quest, distance, (Player)value2));
        this.stop();
    }
}
