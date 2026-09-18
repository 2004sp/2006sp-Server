package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

public final class NoMagicDuelRule
extends DuelRule {
    public NoMagicDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled4) {
        boolean enabled2;
        boolean enabled3 = enabled4;
        Player player2 = player;
        if (NO_RANGED.isEnabledFor(player2) && NO_MELEE.isEnabledFor(player2)) {
            if (enabled3) {
                player2.packetSender.sendGameMessage("You can't have no ranged, no melee and no magic - how would you fight?");
            }
            enabled2 = false;
        } else {
            enabled2 = true;
        }
        if (enabled2) {
            player.getDuelInterfaceManager().toggleRule(this.ruleIndex, "Players cannot use magic");
        }
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

