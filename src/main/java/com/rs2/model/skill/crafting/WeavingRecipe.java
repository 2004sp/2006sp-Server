package com.rs2.model.skill.crafting;

import java.util.HashMap;

public enum WeavingRecipe {
    CLOTH(8889, 1759, 3224, 1, 4, 10, 12.0),
    CLOTH5(8888, 1759, 3224, 5, 4, 10, 12.0),
    CLOTH10(8887, 1759, 3224, 10, 4, 10, 12.0),
    CLOTHX(8886, 1759, 3224, 0, 4, 10, 12.0),
    SACKS(8893, 5931, 5418, 1, 4, 21, 38.0),
    SACKS5(8892, 5931, 5418, 5, 4, 21, 38.0),
    SACKS10(8891, 5931, 5418, 10, 4, 21, 38.0),
    SACKSX(8890, 5931, 5418, 0, 4, 21, 38.0),
    BASKET(8897, 5933, 5376, 1, 6, 36, 56.0),
    BASKET5(8896, 5933, 5376, 5, 6, 36, 56.0),
    BASKET10(8895, 5933, 5376, 10, 6, 36, 56.0),
    BASKETX(8894, 5933, 5376, 0, 6, 36, 56.0);

    private int buttonId;
    private int ingredientItemId;
    private int productItemId;
    private int quantity;
    private int ingredientAmount;
    private int requiredLevel;
    private double experience;
    private static HashMap definitionsByButtonId;

    static {
        definitionsByButtonId = new HashMap();
        WeavingRecipe[] weavingRecipeArray = WeavingRecipe.values();
        int length = weavingRecipeArray.length;
        int index = 0;
        while (index < length) {
            WeavingRecipe weavingRecipe = weavingRecipeArray[index];
            definitionsByButtonId.put(weavingRecipe.buttonId, weavingRecipe);
            ++index;
        }
    }

    public static WeavingRecipe forButtonId(int buttonId) {
        return (WeavingRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private WeavingRecipe(int buttonId, int ingredientItemId, int productItemId, int quantity, int ingredientAmount, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.ingredientItemId = ingredientItemId;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.ingredientAmount = ingredientAmount;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getIngredientItemId() {
        return this.ingredientItemId;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getQuantity() {
        return this.quantity;
    }

    public final int getIngredientAmount() {
        return this.ingredientAmount;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

