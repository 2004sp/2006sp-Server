package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.BlueDragonhideRecipe;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;

public final class BlueDragonhideCrafting
extends CraftedArmorAction {
    private BlueDragonhideCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static BlueDragonhideCrafting createForButton(Player player, int buttonId, int value2) {
        BlueDragonhideRecipe blueDragonhideRecipe = BlueDragonhideRecipe.forButtonId(buttonId);
        if (blueDragonhideRecipe == null || blueDragonhideRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new BlueDragonhideCrafting(player, blueDragonhideRecipe.getMaterialItemId(), blueDragonhideRecipe.getMaterialAmount(), blueDragonhideRecipe.getProductItemId(), blueDragonhideRecipe.getQuantity(), value2, blueDragonhideRecipe.getRequiredLevel(), blueDragonhideRecipe.getExperience());
    }
}

