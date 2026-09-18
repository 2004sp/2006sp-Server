package com.rs2.model.gameplay.duel;

import com.rs2.model.combat.CombatType;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class NoRangedDuelRule
extends DuelRule {
    public NoRangedDuelRule(int value4, int value22, int value32) {
        super(value4, value22, value32);
    }

    @Override
    public final void toggleForPlayer(Player player, boolean enabled4) {
        boolean enabled2;
        boolean enabled3 = enabled4;
        Player player2 = player;
        Object value = this;
        if (NO_MELEE.isEnabledFor(player2) && NO_MAGIC.isEnabledFor(player2)) {
            if (enabled3) {
                value = player2;
                ((Player)value).packetSender.sendGameMessage("You can't have no ranged, no melee and no magic - how would you fight?");
            }
            enabled2 = false;
        } else {
            if (!((DuelRule)value).isEnabledFor(player2)) {
                if (player2.getDuelSession().getCurrentCombatType() == CombatType.RANGED) {
                    player2.getDuelSession().getEquipmentToRemove().add(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(3)));
                }
            } else if (player2.getDuelSession().getCurrentCombatType() == CombatType.RANGED) {
                player2.getDuelSession().getEquipmentToRemove().remove(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(3)));
            }
            enabled2 = true;
        }
        if (enabled2) {
            player.getDuelInterfaceManager().toggleRule(this.ruleIndex, "Players cannot use range");
        }
    }

    @Override
    public final boolean isEnabledFor(Player player) {
        return player.getDuelSession().getEnabledRules()[this.ruleIndex];
    }
}

