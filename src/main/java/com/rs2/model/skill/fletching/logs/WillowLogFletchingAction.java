package com.rs2.model.skill.fletching.logs;

import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;
import com.rs2.model.skill.fletching.logs.WillowLogFletchingRecipe;

public final class WillowLogFletchingAction
extends LogFletchingAction {
    private WillowLogFletchingAction(Player player, int value6, int value22, int value32, double value7, int value43, int value52) {
        super(player, value6, value22, value32, value7, value43, value52);
    }

    public static WillowLogFletchingAction create(Player player, int value3, int value22) {
        WillowLogFletchingRecipe willowLogFletchingRecipe = WillowLogFletchingRecipe.forButtonId(value3);
        if (willowLogFletchingRecipe == null || willowLogFletchingRecipe.getMenuQuantity() == 0 && value22 == 0) {
            return null;
        }
        return new WillowLogFletchingAction(player, willowLogFletchingRecipe.getLogItemId(), willowLogFletchingRecipe.getProductItemId(), willowLogFletchingRecipe.getRequiredLevel(), willowLogFletchingRecipe.getExperience(), willowLogFletchingRecipe.getMenuQuantity(), value22);
    }
}

