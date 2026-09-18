package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum OakLogFletchingRecipe {
    SHORTBOW_ONE(ServerSettings.cacheVersion < 274 ? 2461 : 8874, 1521, 54, 1, 20, 16.5),
    SHORTBOW_FIVE(8873, 1521, 54, 5, 20, 16.5),
    SHORTBOW_TEN(8872, 1521, 54, 10, 20, 16.5),
    SHORTBOW_ALL(8871, 1521, 54, 0, 20, 16.5),
    LONGBOW_ONE(ServerSettings.cacheVersion < 274 ? 2462 : 8878, 1521, 56, 1, 25, 25.0),
    LONGBOW_FIVE(8877, 1521, 56, 5, 25, 25.0),
    LONGBOW_TEN(8876, 1521, 56, 10, 25, 25.0),
    LONGBOW_ALL(8875, 1521, 56, 0, 25, 25.0);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        OakLogFletchingRecipe[] oakLogFletchingRecipeArray = OakLogFletchingRecipe.values();
        int length = oakLogFletchingRecipeArray.length;
        int index = 0;
        while (index < length) {
            OakLogFletchingRecipe oakLogFletchingRecipe = oakLogFletchingRecipeArray[index];
            recipesByButtonId.put(oakLogFletchingRecipe.buttonId, oakLogFletchingRecipe);
            ++index;
        }
    }

    public static OakLogFletchingRecipe forButtonId(int buttonId) {
        return (OakLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private OakLogFletchingRecipe(int buttonId, int value22, int productItemId, int menuQuantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.logItemId = 1521;
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
