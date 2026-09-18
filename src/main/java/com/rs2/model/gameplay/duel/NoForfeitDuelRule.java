package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

public final class NoForfeitDuelRule
extends DuelRule {
    public NoForfeitDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled4) {
        boolean enabled2;
        boolean enabled3 = enabled4;
        Player player2 = player;
        if (NO_MOVEMENT.isEnabledFor(player2)) {
            if (enabled3) {
                player2.packetSender.sendGameMessage("You can't have no forfeit and no movement - you could run out of ammo");
            }
            enabled2 = false;
        } else if (NO_MELEE.isEnabledFor(player2)) {
            if (enabled3) {
                player2.packetSender.sendGameMessage("You can't have no forfeit and no melee - you could run out of ammo");
            }
            enabled2 = false;
        } else {
            enabled2 = true;
        }
        if (enabled2) {
            player.getDuelInterfaceManager().toggleRule(this.ruleIndex, "Players cannot forfeit!");
        }
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

