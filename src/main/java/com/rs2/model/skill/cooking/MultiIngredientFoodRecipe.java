package com.rs2.model.skill.cooking;

import java.util.HashMap;

public enum MultiIngredientFoodRecipe {
    INGREDIENTS_2142_1942_1921_PRODUCT_2001(2142, 1942, 1921, 1999, 2001, 25, 117.0, true, 0, 0),
    INGREDIENTS_1942_2142_1921_PRODUCT_2001(1942, 2142, 1921, 1997, 2001, 25, 117.0, true, 0, 0),
    INGREDIENTS_2140_1942_1921_PRODUCT_2001(2140, 1942, 1921, 1999, 2001, 25, 117.0, true, 0, 0),
    INGREDIENTS_1942_2140_1921_PRODUCT_2001(1942, 2140, 1921, 1997, 2001, 25, 117.0, true, 0, 0),
    INGREDIENTS_1982_1985_2283_PRODUCT_2287(1982, 1985, 2283, 2285, 2287, 35, 143.0, true, 0, 0),
    INGREDIENTS_361_5988_1923_PRODUCT_7068(361, 5988, 1923, 7086, 7068, 67, 204.0, true, 0, 0),
    INGREDIENTS_5988_361_1923_PRODUCT_7068(5988, 361, 1923, 7088, 7068, 67, 204.0, true, 0, 0);

    private int firstIngredientItemId;
    private int secondIngredientItemId;
    private int baseItemId;
    private int firstStageProductItemId;
    private int finalProductItemId;
    private int requiredLevel;
    private double experience;
    private boolean putIntoMessage;
    private int firstStageReturnedItemId;
    private int finalStageReturnedItemId;
    private static HashMap recipesByFirstIngredientItemId;
    private static HashMap recipesByFirstStageProductItemId;

    static {
        recipesByFirstIngredientItemId = new HashMap();
        recipesByFirstStageProductItemId = new HashMap();
        MultiIngredientFoodRecipe[] multiIngredientFoodRecipeArray = MultiIngredientFoodRecipe.values();
        int length = multiIngredientFoodRecipeArray.length;
        int index = 0;
        while (index < length) {
            MultiIngredientFoodRecipe multiIngredientFoodRecipe = multiIngredientFoodRecipeArray[index];
            recipesByFirstIngredientItemId.put(multiIngredientFoodRecipe.firstIngredientItemId, multiIngredientFoodRecipe);
            recipesByFirstStageProductItemId.put(multiIngredientFoodRecipe.firstStageProductItemId, multiIngredientFoodRecipe);
            ++index;
        }
    }

    public static MultiIngredientFoodRecipe forFirstIngredientItemId(int itemId) {
        return (MultiIngredientFoodRecipe)((Object)recipesByFirstIngredientItemId.get(itemId));
    }

    public static MultiIngredientFoodRecipe forFirstStageProductItemId(int itemId) {
        return (MultiIngredientFoodRecipe)((Object)recipesByFirstStageProductItemId.get(itemId));
    }

    private MultiIngredientFoodRecipe(int firstIngredientItemId, int secondIngredientItemId, int baseItemId, int firstStageProductItemId, int finalProductItemId, int requiredLevel, double experience, boolean enabled2, int value73, int value82) {
        this.firstIngredientItemId = firstIngredientItemId;
        this.secondIngredientItemId = secondIngredientItemId;
        this.baseItemId = baseItemId;
        this.firstStageProductItemId = firstStageProductItemId;
        this.finalProductItemId = finalProductItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.putIntoMessage = true;
        this.firstStageReturnedItemId = 0;
        this.finalStageReturnedItemId = 0;
    }

    public final int getFirstIngredientItemId() {
        return this.firstIngredientItemId;
    }

    public final int getSecondIngredientItemId() {
        return this.secondIngredientItemId;
    }

    public final int getFirstStageProductItemId() {
        return this.firstStageProductItemId;
    }

    public final int getBaseItemId() {
        return this.baseItemId;
    }

    public final int getFinalProductItemId() {
        return this.finalProductItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final boolean usesPutIntoMessage() {
        return this.putIntoMessage;
    }

    public final int getFirstStageReturnedItemId() {
        return this.firstStageReturnedItemId;
    }

    public final int getFinalStageReturnedItemId() {
        return this.finalStageReturnedItemId;
    }
}

