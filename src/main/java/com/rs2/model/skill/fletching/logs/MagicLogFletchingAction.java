package com.rs2.model.skill.fletching.logs;

import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;
import com.rs2.model.skill.fletching.logs.MagicLogFletchingRecipe;

public final class MagicLogFletchingAction
extends LogFletchingAction {
    private MagicLogFletchingAction(Player player, int value6, int value22, int value32, double value7, int value43, int value52) {
        super(player, value6, value22, value32, value7, value43, value52);
    }

    public static MagicLogFletchingAction create(Player player, int value3, int value22) {
        MagicLogFletchingRecipe magicLogFletchingRecipe = MagicLogFletchingRecipe.forButtonId(value3);
        if (magicLogFletchingRecipe == null || magicLogFletchingRecipe.getMenuQuantity() == 0 && value22 == 0) {
            return null;
        }
        return new MagicLogFletchingAction(player, magicLogFletchingRecipe.getLogItemId(), magicLogFletchingRecipe.getProductItemId(), magicLogFletchingRecipe.getRequiredLevel(), magicLogFletchingRecipe.getExperience(), magicLogFletchingRecipe.getMenuQuantity(), value22);
    }
}

