package com.rs2.model.skill.fletching.logs;

import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.AcheyLogFletchingRecipe;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;

public final class AcheyLogFletchingAction
extends LogFletchingAction {
    private AcheyLogFletchingAction(Player player, int value6, int value22, int value32, double value7, int value43, int value52) {
        super(player, value6, value22, value32, value7, value43, value52);
    }

    public static AcheyLogFletchingAction create(Player player, int value3, int value22) {
        AcheyLogFletchingRecipe acheyLogFletchingRecipe = AcheyLogFletchingRecipe.forButtonId(value3);
        if (acheyLogFletchingRecipe == null || acheyLogFletchingRecipe.getMenuQuantity() == 0 && value22 == 0) {
            return null;
        }
        return new AcheyLogFletchingAction(player, acheyLogFletchingRecipe.getLogItemId(), acheyLogFletchingRecipe.getProductItemId(), acheyLogFletchingRecipe.getRequiredLevel(), acheyLogFletchingRecipe.getExperience(), acheyLogFletchingRecipe.getMenuQuantity(), value22);
    }
}

