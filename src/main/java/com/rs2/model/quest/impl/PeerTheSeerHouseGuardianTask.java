package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.task.TickTask;

public final class PeerTheSeerHouseGuardianTask
extends TickTask {
    private final Player player;

    public PeerTheSeerHouseGuardianTask(FremennikTrialsQuest fremennikTrialsQuest, int value2, Player player) {
        super(10);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        boolean enabled = false;
        int index = 0;
        while (index < FremennikTrialsQuest.peerHouseGuardianAreas.length) {
            if (FremennikTrialsQuest.peerHouseGuardianAreas[index].containsExclusive(this.player.getPosition())) {
                enabled = true;
                if (!GameplayHelper.hasActiveTemporaryNpc(this.player, 1290)) {
                    Npc npc = new Npc(1290);
                    GameplayHelper.spawnOwnedNpcAdjacentToPlayer(this.player, npc, true, true);
                    npc.getUpdateState().setForcedText("Prepare to die, " + this.player.getUsername() + "!");
                }
                this.stop();
            }
            ++index;
        }
        if (!enabled) {
            this.player.activeRecurringEffectId = -1;
            this.stop();
        }
    }
}

