package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.WitchsHouseGardenEjectEvent;
import com.rs2.model.quest.impl.WitchsHouseQuest;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class WitchsHouseGardenTrespassTask
extends TickTask {
    private final WitchsHouseQuest quest;
    private final Player player;

    public WitchsHouseGardenTrespassTask(WitchsHouseQuest witchsHouseQuest, int value2, Player player) {
        super(1);
        this.quest = witchsHouseQuest;
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (this.quest.gardenTrespassArea.contains(this.player.getPosition())) {
            Object byDefinitionId = Npc.findByDefinitionId(896);
            if (GameUtil.isNpcWaypointFacingPlayer(this.player, (Npc)byDefinitionId)) {
                ((Entity)byDefinitionId).getUpdateState().setForcedText("Get out of my property!");
                Player player = this.player;
                byDefinitionId = this.quest;
                player.getUpdateState().setGraphicHeight100(110);
                CycleEventHandler.getInstance().schedule(player, new WitchsHouseGardenEjectEvent((WitchsHouseQuest)byDefinitionId, player), 1);
                return;
            }
        } else {
            this.player.activeRecurringEffectId = -1;
            this.stop();
        }
    }
}
