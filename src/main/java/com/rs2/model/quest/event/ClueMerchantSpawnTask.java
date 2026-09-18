package com.rs2.model.quest.event;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.path.WalkingCollisionMap;

public final class ClueMerchantSpawnTask
extends TickTask {
    private final ServerMaintenanceEventHook maintenanceHook;

    public ClueMerchantSpawnTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(1500);
        this.maintenanceHook = serverMaintenanceEventHook;
    }

    @Override
    public final void execute() {
        int index = 0;
        while (index < this.maintenanceHook.clueMerchantCount) {
            int value = 2945 + GameUtil.randomInt(350);
            int value2 = 3145 + GameUtil.randomInt(370);
            boolean enabled = false;
            if (WalkingCollisionMap.getTileFlags(value, value2, 0) != 0) {
                enabled = true;
            }
            while (enabled) {
                value = 2945 + GameUtil.randomInt(350);
                enabled = WalkingCollisionMap.getTileFlags(value, value2 = 3145 + GameUtil.randomInt(370), 0) != 0;
            }
            Npc npc = new Npc(3886);
            GameplayHelper.spawnNpcWithRemovalDelay(npc, value, value2, 0, 1500);
            ++index;
        }
    }
}
