package com.rs2.model.quest.impl;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.task.TickTask;

public final class ErnestSecretDoorReturnTask
extends TickTask {
    private final Player player;

    public ErnestSecretDoorReturnTask(ErnestTheChickenQuest ernestTheChickenQuest, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        ObjectManager.getInstance().addDynamicObject(new DynamicObject(ServerSettings.placeholderObjectId, 3097, 3358, 0, 0, 10, 155, 2), true);
        ObjectManager.getInstance().addDynamicObject(new DynamicObject(ServerSettings.placeholderObjectId, 3097, 3359, 0, 0, 10, 156, 2), true);
        ObjectManager.getInstance().addDynamicObject(new DynamicObject(155, 3097, 3357, 0, 0, 10, 11474, 2), true);
        ObjectManager.getInstance().addDynamicObject(new DynamicObject(156, 3097, 3360, 0, 0, 10, 11512, 2), true);
        Player player = this.player;
        player.packetSender.queueRelativeMovementStep(2, 0, true);
        this.stop();
    }
}

