package com.rs2.model.randomevent;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventManager;
import com.rs2.model.randomevent.RandomEventNpcDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class RandomEventRollEvent
extends CycleEvent {
    private final Player player;

    public RandomEventRollEvent(Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.player.getActiveRandomEventNpc() != null || this.player.isInTeleportRestrictedArea()) {
            return;
        }
        if (this.player.botEnabled) {
            return;
        }
        int value = GameUtil.randomInclusive(70);
        boolean enabled = true;
        if (ServerSettings.freeToPlayWorld || !this.player.isMember()) {
            enabled = false;
        }
        if (value == 3 && !enabled) {
            value = 1;
        }
        switch (value) {
            case 0: {
                this.player.getSingleCombatTimer().setDelayTicks(0);
                this.player.getSingleCombatTimer().reset();
                GameplayHelper.spawnOwnedNpcAdjacentToPlayer(this.player, new Npc(411), true, false);
                return;
            }
            case 1: {
                RandomEventManager.spawnRandomEventNpc(this.player, RandomEventNpcDefinition.DRUNKEN_DWARF);
                return;
            }
            case 2: {
                RandomEventManager.spawnRandomEventNpc(this.player, RandomEventNpcDefinition.GENIE);
                return;
            }
            case 3: {
                RandomEventManager.spawnRandomEventNpc(this.player, RandomEventNpcDefinition.DR_JEKYLL);
            }
        }
    }

    @Override
    public final void onStop() {
    }
}

