package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.skill.crafting.armor.LeatherRecipe;

public final class LeatherCrafting
extends CraftedArmorAction {
    private LeatherCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static LeatherCrafting createForButton(Player player, int buttonId, int value2) {
        LeatherRecipe leatherRecipe = LeatherRecipe.forButtonId(buttonId);
        if (leatherRecipe == null || leatherRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new LeatherCrafting(player, leatherRecipe.getMaterialItemId(), leatherRecipe.getMaterialAmount(), leatherRecipe.getProductItemId(), leatherRecipe.getQuantity(), value2, leatherRecipe.getRequiredLevel(), leatherRecipe.getExperience());
    }
}

