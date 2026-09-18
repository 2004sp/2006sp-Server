package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum WillowLogFletchingRecipe {
    SHORTBOW_ONE(ServerSettings.cacheVersion < 274 ? 2461 : 8874, 1519, 60, 1, 35, 33.3),
    SHORTBOW_FIVE(8873, 1519, 60, 5, 35, 33.3),
    SHORTBOW_TEN(8872, 1519, 60, 10, 35, 33.3),
    SHORTBOW_ALL(8871, 1519, 60, 0, 35, 33.3),
    LONGBOW_ONE(ServerSettings.cacheVersion < 274 ? 2462 : 8878, 1519, 58, 1, 40, 41.5),
    LONGBOW_FIVE(8877, 1519, 58, 5, 40, 41.5),
    LONGBOW_TEN(8876, 1519, 58, 10, 40, 41.5),
    LONGBOW_ALL(8875, 1519, 58, 0, 40, 41.5);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        WillowLogFletchingRecipe[] willowLogFletchingRecipeArray = WillowLogFletchingRecipe.values();
        int length = willowLogFletchingRecipeArray.length;
        int index = 0;
        while (index < length) {
            WillowLogFletchingRecipe willowLogFletchingRecipe = willowLogFletchingRecipeArray[index];
            recipesByButtonId.put(willowLogFletchingRecipe.buttonId, willowLogFletchingRecipe);
            ++index;
        }
    }

    public static WillowLogFletchingRecipe forButtonId(int buttonId) {
        return (WillowLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private WillowLogFletchingRecipe(int buttonId, int value22, int productItemId, int menuQuantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.logItemId = 1519;
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
