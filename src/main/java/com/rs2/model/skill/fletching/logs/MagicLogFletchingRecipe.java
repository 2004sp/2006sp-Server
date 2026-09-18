package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum MagicLogFletchingRecipe {
    SHORTBOW_ONE(ServerSettings.cacheVersion < 274 ? 2461 : 8874, 1513, 72, 1, 80, 83.3),
    SHORTBOW_FIVE(8873, 1513, 72, 5, 65, 68.5),
    SHORTBOW_TEN(8872, 1513, 72, 10, 65, 68.5),
    SHORTBOW_ALL(8871, 1513, 72, 0, 65, 68.5),
    LONGBOW_ONE(ServerSettings.cacheVersion < 274 ? 2462 : 8878, 1513, 70, 1, 85, 91.5),
    LONGBOW_FIVE(8877, 1513, 70, 5, 85, 91.5),
    LONGBOW_TEN(8876, 1513, 70, 10, 85, 91.5),
    LONGBOW_ALL(8875, 1513, 70, 0, 85, 91.5);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        for (MagicLogFletchingRecipe recipe : MagicLogFletchingRecipe.values()) {
            recipesByButtonId.put(recipe.buttonId, recipe);
        }
    }

    public static MagicLogFletchingRecipe forButtonId(int buttonId) {
        return (MagicLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private MagicLogFletchingRecipe(int buttonId, int logItemId, int productItemId, int menuQuantity, int requiredLevel, double experience) {
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
