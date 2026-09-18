package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.skill.crafting.armor.SnakeskinAccessoryRecipe;

public final class SnakeskinAccessoryCrafting
extends CraftedArmorAction {
    private SnakeskinAccessoryCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static SnakeskinAccessoryCrafting createForButton(Player player, int buttonId, int value2) {
        SnakeskinAccessoryRecipe snakeskinAccessoryRecipe = SnakeskinAccessoryRecipe.forButtonId(buttonId);
        if (snakeskinAccessoryRecipe == null || snakeskinAccessoryRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new SnakeskinAccessoryCrafting(player, snakeskinAccessoryRecipe.getMaterialItemId(), snakeskinAccessoryRecipe.getMaterialAmount(), snakeskinAccessoryRecipe.getProductItemId(), snakeskinAccessoryRecipe.getQuantity(), value2, snakeskinAccessoryRecipe.getRequiredLevel(), snakeskinAccessoryRecipe.getExperience());
    }
}

