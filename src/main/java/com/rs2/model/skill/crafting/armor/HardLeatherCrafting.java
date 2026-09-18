package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.skill.crafting.armor.HardLeatherRecipe;

public final class HardLeatherCrafting
extends CraftedArmorAction {
    private HardLeatherCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static HardLeatherCrafting createForButton(Player player, int buttonId, int value2) {
        HardLeatherRecipe hardLeatherRecipe = HardLeatherRecipe.forButtonId(buttonId);
        if (hardLeatherRecipe == null || hardLeatherRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new HardLeatherCrafting(player, hardLeatherRecipe.getMaterialItemId(), hardLeatherRecipe.getMaterialAmount(), hardLeatherRecipe.getProductItemId(), hardLeatherRecipe.getQuantity(), value2, hardLeatherRecipe.getRequiredLevel(), hardLeatherRecipe.getExperience());
    }
}

