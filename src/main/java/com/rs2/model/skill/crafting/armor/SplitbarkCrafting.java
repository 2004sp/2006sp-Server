package com.rs2.model.skill.crafting.armor;

import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.SplitbarkCraftingAction;
import com.rs2.model.skill.crafting.armor.SplitbarkRecipe;

public final class SplitbarkCrafting
extends SplitbarkCraftingAction {
    private SplitbarkCrafting(Player player, int value7, int value22, int value32, int value42, int value52, int value62) {
        super(player, value7, value22, value32, value42, value52, value62);
    }

    public static SplitbarkCrafting createForButton(Player player, int buttonId, int value2) {
        SplitbarkRecipe splitbarkRecipe = SplitbarkRecipe.forButtonId(buttonId);
        if (splitbarkRecipe == null || splitbarkRecipe.getQuantity() == 0 && value2 == 0) {
            return null;
        }
        return new SplitbarkCrafting(player, splitbarkRecipe.getProductItemId(), splitbarkRecipe.getQuantity(), value2, splitbarkRecipe.getMaterialAmount(), splitbarkRecipe.getMaterialAmount(), splitbarkRecipe.getCoinAmount());
    }
}

