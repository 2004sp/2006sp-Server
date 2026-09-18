package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class NoHelmetDuelRule
extends DuelRule {
    public NoHelmetDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled2) {
        NoHelmetDuelRule noHelmetDuelRule = this;
        Player player2 = player;
        if (!noHelmetDuelRule.isEnabledFor(player2)) {
            player2.getDuelSession().getEquipmentToRemove().add(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(0)));
        } else {
            player2.getDuelSession().getEquipmentToRemove().remove(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(0)));
        }
        player.getDuelInterfaceManager().toggleRule(this.ruleIndex, null);
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

