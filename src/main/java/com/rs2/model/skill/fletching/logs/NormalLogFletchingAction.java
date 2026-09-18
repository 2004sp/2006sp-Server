package com.rs2.model.skill.fletching.logs;

import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;
import com.rs2.model.skill.fletching.logs.NormalLogFletchingRecipe;

public final class NormalLogFletchingAction
extends LogFletchingAction {
    private NormalLogFletchingAction(Player player, int value6, int value22, int value32, double value7, int value43, int value52) {
        super(player, value6, value22, value32, value7, value43, value52);
    }

    public static NormalLogFletchingAction create(Player player, int value3, int value22) {
        NormalLogFletchingRecipe normalLogFletchingRecipe = NormalLogFletchingRecipe.forButtonId(value3);
        if (normalLogFletchingRecipe == null || normalLogFletchingRecipe.getMenuQuantity() == 0 && value22 == 0) {
            return null;
        }
        return new NormalLogFletchingAction(player, normalLogFletchingRecipe.getLogItemId(), normalLogFletchingRecipe.getProductItemId(), normalLogFletchingRecipe.getRequiredLevel(), normalLogFletchingRecipe.getExperience(), normalLogFletchingRecipe.getMenuQuantity(), value22);
    }
}

