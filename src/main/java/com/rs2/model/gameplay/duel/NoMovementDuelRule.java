package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

public final class NoMovementDuelRule
extends DuelRule {
    public NoMovementDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled4) {
        boolean enabled2;
        boolean enabled3 = enabled4;
        Player player2 = player;
        if (OBSTACLES.isEnabledFor(player2)) {
            if (enabled3) {
                player2.packetSender.sendGameMessage("You can't have no movement and obstacles");
            }
            enabled2 = false;
        } else if (NO_FORFEIT.isEnabledFor(player2)) {
            if (enabled3) {
                player2.packetSender.sendGameMessage("You can't have no forfeit and no movement - you could run out of ammo");
            }
            enabled2 = false;
        } else {
            enabled2 = true;
        }
        if (enabled2) {
            player.getDuelInterfaceManager().toggleRule(this.ruleIndex, "Players cannot move.");
        }
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

