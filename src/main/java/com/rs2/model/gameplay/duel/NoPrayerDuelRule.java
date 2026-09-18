package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

public final class NoPrayerDuelRule
extends DuelRule {
    public NoPrayerDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled2) {
        player.getDuelInterfaceManager().toggleRule(this.ruleIndex, "Players cannot use Prayers.");
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

