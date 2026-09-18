package com.rs2.model.player;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import java.util.Comparator;

public final class DeathItemValueComparator
implements Comparator {
    public DeathItemValueComparator(Player player) {
    }

    public final int compare(Object value3, Object value22) {
        value22 = (ItemStack)value22;
        value3 = (ItemStack)value3;
        return ItemDefinition.forId(((ItemStack)value22).getId()).getHighAlchemyValue() - ItemDefinition.forId(((ItemStack)value3).getId()).getHighAlchemyValue();
    }
}

