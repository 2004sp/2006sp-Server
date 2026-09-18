package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum NormalLogFletchingRecipe {
    ARROW_SHAFTS_ONE(ServerSettings.cacheVersion < 274 ? 2471 : 8889, 1511, 52, 1, 1, 5.0),
    ARROW_SHAFTS_FIVE(8888, 1511, 52, 5, 1, 5.0),
    ARROW_SHAFTS_TEN(8887, 1511, 52, 10, 1, 5.0),
    ARROW_SHAFTS_ALL(8886, 1511, 52, 0, 1, 5.0),
    SHORTBOW_ONE(ServerSettings.cacheVersion < 274 ? 2472 : 8893, 1511, 50, 1, 5, 5.0),
    SHORTBOW_FIVE(8892, 1511, 50, 5, 5, 5.0),
    SHORTBOW_TEN(8891, 1511, 50, 10, 5, 5.0),
    SHORTBOW_ALL(8890, 1511, 50, 0, 5, 5.0),
    LONGBOW_ONE(ServerSettings.cacheVersion < 274 ? 2473 : 8897, 1511, 48, 1, 10, 10.0),
    LONGBOW_FIVE(8896, 1511, 48, 5, 10, 10.0),
    LONGBOW_TEN(8895, 1511, 48, 10, 10, 10.0),
    LONGBOW_ALL(8894, 1511, 48, 0, 10, 10.0);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        for (NormalLogFletchingRecipe recipe : NormalLogFletchingRecipe.values()) {
            recipesByButtonId.put(recipe.buttonId, recipe);
        }
    }

    public static NormalLogFletchingRecipe forButtonId(int buttonId) {
        return (NormalLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private NormalLogFletchingRecipe(int buttonId, int logItemId, int productItemId, int menuQuantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.logItemId = logItemId;
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
