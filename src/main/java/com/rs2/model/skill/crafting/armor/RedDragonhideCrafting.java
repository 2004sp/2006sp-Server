package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.skill.crafting.armor.RedDragonhideRecipe;

public final class RedDragonhideCrafting
extends CraftedArmorAction {
    private RedDragonhideCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static RedDragonhideCrafting createForButton(Player player, int buttonId, int value2) {
        RedDragonhideRecipe redDragonhideRecipe = RedDragonhideRecipe.forButtonId(buttonId);
        if (redDragonhideRecipe == null || redDragonhideRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new RedDragonhideCrafting(player, redDragonhideRecipe.getMaterialItemId(), redDragonhideRecipe.getMaterialAmount(), redDragonhideRecipe.getProductItemId(), redDragonhideRecipe.getQuantity(), value2, redDragonhideRecipe.getRequiredLevel(), redDragonhideRecipe.getExperience());
    }
}

