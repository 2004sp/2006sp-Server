package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum YewLogFletchingRecipe {
    SHORTBOW_ONE(ServerSettings.cacheVersion < 274 ? 2461 : 8874, 1515, 68, 1, 65, 68.5),
    SHORTBOW_FIVE(8873, 1515, 68, 5, 65, 68.5),
    SHORTBOW_TEN(8872, 1515, 68, 10, 65, 68.5),
    SHORTBOW_ALL(8871, 1515, 68, 0, 65, 68.5),
    LONGBOW_ONE(ServerSettings.cacheVersion < 274 ? 2462 : 8878, 1515, 66, 1, 70, 75.0),
    LONGBOW_FIVE(8877, 1515, 66, 5, 70, 75.0),
    LONGBOW_TEN(8876, 1515, 66, 10, 70, 75.0),
    LONGBOW_ALL(8875, 1515, 66, 0, 70, 75.0);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        YewLogFletchingRecipe[] yewLogFletchingRecipeArray = YewLogFletchingRecipe.values();
        int length = yewLogFletchingRecipeArray.length;
        int index = 0;
        while (index < length) {
            YewLogFletchingRecipe yewLogFletchingRecipe = yewLogFletchingRecipeArray[index];
            recipesByButtonId.put(yewLogFletchingRecipe.buttonId, yewLogFletchingRecipe);
            ++index;
        }
    }

    public static YewLogFletchingRecipe forButtonId(int buttonId) {
        return (YewLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private YewLogFletchingRecipe(int buttonId, int value22, int productItemId, int menuQuantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.logItemId = 1515;
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
