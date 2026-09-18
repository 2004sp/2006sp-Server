package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.BlackDragonhideRecipe;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;

public final class BlackDragonhideCrafting
extends CraftedArmorAction {
    private BlackDragonhideCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static BlackDragonhideCrafting createForButton(Player player, int buttonId, int value2) {
        BlackDragonhideRecipe blackDragonhideRecipe = BlackDragonhideRecipe.forButtonId(buttonId);
        if (blackDragonhideRecipe == null || blackDragonhideRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new BlackDragonhideCrafting(player, blackDragonhideRecipe.getMaterialItemId(), blackDragonhideRecipe.getMaterialAmount(), blackDragonhideRecipe.getProductItemId(), blackDragonhideRecipe.getQuantity(), value2, blackDragonhideRecipe.getRequiredLevel(), blackDragonhideRecipe.getExperience());
    }
}

