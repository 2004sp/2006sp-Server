package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum MapleLogFletchingRecipe {
    SHORTBOW_ONE(ServerSettings.cacheVersion < 274 ? 2461 : 8874, 1517, 64, 1, 50, 50.0),
    SHORTBOW_FIVE(8873, 1517, 64, 5, 50, 50.0),
    SHORTBOW_TEN(8872, 1517, 64, 10, 50, 50.0),
    SHORTBOW_ALL(8871, 1517, 64, 0, 50, 50.0),
    LONGBOW_ONE(ServerSettings.cacheVersion < 274 ? 2462 : 8878, 1517, 62, 1, 55, 58.3),
    LONGBOW_FIVE(8877, 1517, 62, 5, 55, 58.3),
    LONGBOW_TEN(8876, 1517, 62, 10, 55, 58.3),
    LONGBOW_ALL(8875, 1517, 62, 0, 55, 58.3);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        MapleLogFletchingRecipe[] mapleLogFletchingRecipeArray = MapleLogFletchingRecipe.values();
        int length = mapleLogFletchingRecipeArray.length;
        int index = 0;
        while (index < length) {
            MapleLogFletchingRecipe mapleLogFletchingRecipe = mapleLogFletchingRecipeArray[index];
            recipesByButtonId.put(mapleLogFletchingRecipe.buttonId, mapleLogFletchingRecipe);
            ++index;
        }
    }

    public static MapleLogFletchingRecipe forButtonId(int buttonId) {
        return (MapleLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private MapleLogFletchingRecipe(int buttonId, int value22, int productItemId, int menuQuantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.logItemId = 1517;
        this.productItemId = productItemId;
        this.menuQuantity = menuQuantity;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getLogItemId() {
        return this.logItemId;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getMenuQuantity() {
        return this.menuQuantity;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}
