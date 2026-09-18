package com.rs2.model.skill.cooking;

import java.util.HashMap;

public enum DairyChurnRecipe {
    CREAM(15342, new int[]{1927}, 2130, 21, 18.0),
    BUTTER(15343, new int[]{1927, 2130}, 6697, 38, 40.0),
    CHEESE(15344, new int[]{1927, 2130, 6697}, 1985, 48, 64.0);

    private int buttonId;
    private int[] ingredientItemIds;
    private int productItemId;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        DairyChurnRecipe[] dairyChurnRecipeArray = DairyChurnRecipe.values();
        int length = dairyChurnRecipeArray.length;
        int index = 0;
        while (index < length) {
            DairyChurnRecipe dairyChurnRecipe = dairyChurnRecipeArray[index];
            recipesByButtonId.put(dairyChurnRecipe.buttonId, dairyChurnRecipe);
            ++index;
        }
    }

    public static DairyChurnRecipe forButtonId(int buttonId) {
        return (DairyChurnRecipe)((Object)recipesByButtonId.get(buttonId));
    }

    private DairyChurnRecipe(int buttonId, int[] ingredientItemIds, int productItemId, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.ingredientItemIds = ingredientItemIds;
        this.productItemId = productItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int[] getIngredientItemIds() {
        return this.ingredientItemIds;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

