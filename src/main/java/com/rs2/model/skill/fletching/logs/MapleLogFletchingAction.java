package com.rs2.model.skill.fletching.logs;

import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;
import com.rs2.model.skill.fletching.logs.MapleLogFletchingRecipe;

public final class MapleLogFletchingAction
extends LogFletchingAction {
    private MapleLogFletchingAction(Player player, int value6, int value22, int value32, double value7, int value43, int value52) {
        super(player, value6, value22, value32, value7, value43, value52);
    }

    public static MapleLogFletchingAction create(Player player, int value3, int value22) {
        MapleLogFletchingRecipe mapleLogFletchingRecipe = MapleLogFletchingRecipe.forButtonId(value3);
        if (mapleLogFletchingRecipe == null || mapleLogFletchingRecipe.getMenuQuantity() == 0 && value22 == 0) {
            return null;
        }
        return new MapleLogFletchingAction(player, mapleLogFletchingRecipe.getLogItemId(), mapleLogFletchingRecipe.getProductItemId(), mapleLogFletchingRecipe.getRequiredLevel(), mapleLogFletchingRecipe.getExperience(), mapleLogFletchingRecipe.getMenuQuantity(), value22);
    }
}

