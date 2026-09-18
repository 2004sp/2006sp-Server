package com.rs2.model.skill.fletching.logs;

import java.util.HashMap;

public enum AcheyLogFletchingRecipe {
    BUTTON_2799_AMOUNT_1(2799, 2862, 4825, 1, 30, 45.0),
    BUTTON_2798_AMOUNT_5(2798, 2862, 4825, 5, 30, 45.0),
    BUTTON_1747_AMOUNT_28(1747, 2862, 4825, 28, 30, 45.0),
    BUTTON_1748_AMOUNT_0(1748, 2862, 4825, 0, 30, 45.0);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        AcheyLogFletchingRecipe[] acheyLogFletchingRecipeArray = AcheyLogFletchingRecipe.values();
        int length = acheyLogFletchingRecipeArray.length;
        int index = 0;
        while (index < length) {
            AcheyLogFletchingRecipe acheyLogFletchingRecipe = acheyLogFletchingRecipeArray[index];
            recipesByButtonId.put(acheyLogFletchingRecipe.buttonId, acheyLogFletchingRecipe);
            ++index;
        }
    }

    public static AcheyLogFletchingRecipe forButtonId(int buttonId) {
        return (AcheyLogFletchingRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private AcheyLogFletchingRecipe(int buttonId, int logItemId, int productItemId, int menuQuantity, int requiredLevel, double experience) {
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

