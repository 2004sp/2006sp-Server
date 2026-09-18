package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.skill.crafting.armor.GreenDragonhideRecipe;

public final class GreenDragonhideCrafting
extends CraftedArmorAction {
    private GreenDragonhideCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static GreenDragonhideCrafting createForButton(Player player, int buttonId, int value2) {
        GreenDragonhideRecipe greenDragonhideRecipe = GreenDragonhideRecipe.forButtonId(buttonId);
        if (greenDragonhideRecipe == null || greenDragonhideRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new GreenDragonhideCrafting(player, greenDragonhideRecipe.getMaterialItemId(), greenDragonhideRecipe.getMaterialAmount(), greenDragonhideRecipe.getProductItemId(), greenDragonhideRecipe.getQuantity(), value2, greenDragonhideRecipe.getRequiredLevel(), greenDragonhideRecipe.getExperience());
    }
}

