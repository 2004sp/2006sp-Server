package com.rs2.model.skill.fletching.logs;

import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;
import com.rs2.model.skill.fletching.logs.OakLogFletchingRecipe;

public final class OakLogFletchingAction
extends LogFletchingAction {
    private OakLogFletchingAction(Player player, int value6, int value22, int value32, double value7, int value43, int value52) {
        super(player, value6, value22, value32, value7, value43, value52);
    }

    public static OakLogFletchingAction create(Player player, int value3, int value22) {
        OakLogFletchingRecipe oakLogFletchingRecipe = OakLogFletchingRecipe.forButtonId(value3);
        if (oakLogFletchingRecipe == null || oakLogFletchingRecipe.getMenuQuantity() == 0 && value22 == 0) {
            return null;
        }
        return new OakLogFletchingAction(player, oakLogFletchingRecipe.getLogItemId(), oakLogFletchingRecipe.getProductItemId(), oakLogFletchingRecipe.getRequiredLevel(), oakLogFletchingRecipe.getExperience(), oakLogFletchingRecipe.getMenuQuantity(), value22);
    }
}

