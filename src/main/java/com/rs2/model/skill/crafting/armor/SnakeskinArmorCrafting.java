package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.skill.crafting.armor.SnakeskinArmorRecipe;

public final class SnakeskinArmorCrafting
extends CraftedArmorAction {
    private SnakeskinArmorCrafting(Player player, int value8, int value22, int value32, int value42, int value52, int value62, double value9) {
        super(player, value8, value22, value32, value42, value52, value62, value9);
    }

    public static SnakeskinArmorCrafting createForButton(Player player, int buttonId, int value2) {
        SnakeskinArmorRecipe snakeskinArmorRecipe = SnakeskinArmorRecipe.forButtonId(buttonId);
        if (snakeskinArmorRecipe == null || snakeskinArmorRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new SnakeskinArmorCrafting(player, snakeskinArmorRecipe.getMaterialItemId(), snakeskinArmorRecipe.getMaterialAmount(), snakeskinArmorRecipe.getProductItemId(), snakeskinArmorRecipe.getQuantity(), value2, snakeskinArmorRecipe.getRequiredLevel(), snakeskinArmorRecipe.getExperience());
    }
}

