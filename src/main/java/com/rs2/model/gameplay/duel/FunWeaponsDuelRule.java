package com.rs2.model.gameplay.duel;

import com.rs2.ServerSettings;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

public final class FunWeaponsDuelRule
extends DuelRule {
    public FunWeaponsDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled4) {
        boolean enabled2 = enabled4;
        Player player2 = player;
        boolean enabled3 = false;
        int[] integerValues = ServerSettings.FUN_WEAPON_IDS;
        int index = 0;
        while (index < 13) {
            int value = integerValues[index];
            if (player2.getInventoryManager().getContainer().containsItem(value)) {
                enabled3 = true;
            }
            ++index;
        }
        if (!enabled3 && enabled2) {
            player2.packetSender.sendGameMessage("Neither player has a 'fun weapon' for that.");
        }
        if (enabled3) {
            player.getDuelInterfaceManager().toggleRule(this.ruleIndex, "'Fun weapons' will be allowed.");
        }
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

