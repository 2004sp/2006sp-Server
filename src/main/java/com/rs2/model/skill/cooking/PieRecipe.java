package com.rs2.model.skill.cooking;

import java.util.HashMap;

public enum PieRecipe {
    INGREDIENTS_6032_2953_434_COOKED_7168(6032, 2953, 434, 2315, 7164, 7166, 7168, 29, 128.0, true, 3727, 3727, 0),
    INGREDIENTS_1982_1957_1965_COOKED_7176(1982, 1957, 1965, 2315, 7172, 7174, 7176, 34, 138.0, true, 0, 0, 0),
    INGREDIENTS_339_333_1942_COOKED_7186(339, 333, 1942, 2315, 7182, 7184, 7186, 47, 164.0, true, 0, 0, 0),
    INGREDIENTS_329_361_1942_COOKED_7196(329, 361, 1942, 2315, 7192, 7194, 7196, 70, 210.0, true, 0, 0, 0),
    INGREDIENTS_2136_2876_3226_COOKED_7206(2136, 2876, 3226, 2315, 7202, 7204, 7206, 85, 240.0, true, 0, 0, 0),
    INGREDIENTS_5504_5982_1955_COOKED_7216(5504, 5982, 1955, 2315, 7212, 7214, 7216, 95, 260.0, true, 0, 0, 0);

    private int firstIngredientItemId;
    private int secondIngredientItemId;
    private int thirdIngredientItemId;
    private int pieShellItemId;
    private int firstStagePieItemId;
    private int secondStagePieItemId;
    private int rawPieItemId;
    private int requiredLevel;
    private double experience;
    private boolean putIntoMessage;
    private int firstStageReturnedItemId;
    private int secondStageReturnedItemId;
    private int thirdStageReturnedItemId;
    private static HashMap recipesByFirstIngredientItemId;
    private static HashMap recipesByFirstStagePieItemId;
    private static HashMap recipesBySecondStagePieItemId;

    static {
        recipesByFirstIngredientItemId = new HashMap();
        recipesByFirstStagePieItemId = new HashMap();
        recipesBySecondStagePieItemId = new HashMap();
        PieRecipe[] pieRecipeArray = PieRecipe.values();
        int length = pieRecipeArray.length;
        int index = 0;
        while (index < length) {
            PieRecipe pieRecipe = pieRecipeArray[index];
            recipesByFirstIngredientItemId.put(pieRecipe.firstIngredientItemId, pieRecipe);
            recipesByFirstStagePieItemId.put(pieRecipe.firstStagePieItemId, pieRecipe);
            recipesBySecondStagePieItemId.put(pieRecipe.secondStagePieItemId, pieRecipe);
            ++index;
        }
    }

    public static PieRecipe forFirstIngredientItemId(int itemId) {
        return (PieRecipe)((Object)recipesByFirstIngredientItemId.get(itemId));
    }

    public static PieRecipe forFirstStagePieItemId(int itemId) {
        return (PieRecipe)((Object)recipesByFirstStagePieItemId.get(itemId));
    }

    public static PieRecipe forSecondStagePieItemId(int itemId) {
        return (PieRecipe)((Object)recipesBySecondStagePieItemId.get(itemId));
    }

    private PieRecipe(int firstIngredientItemId, int secondIngredientItemId, int thirdIngredientItemId, int value42, int firstStagePieItemId, int secondStagePieItemId, int rawPieItemId, int requiredLevel, double experience, boolean enabled2, int firstStageReturnedItemId, int secondStageReturnedItemId, int value112) {
        this.firstIngredientItemId = firstIngredientItemId;
        this.secondIngredientItemId = secondIngredientItemId;
        this.thirdIngredientItemId = thirdIngredientItemId;
        this.pieShellItemId = 2315;
        this.firstStagePieItemId = firstStagePieItemId;
        this.secondStagePieItemId = secondStagePieItemId;
        this.rawPieItemId = rawPieItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.putIntoMessage = true;
        this.firstStageReturnedItemId = firstStageReturnedItemId;
        this.secondStageReturnedItemId = secondStageReturnedItemId;
        this.thirdStageReturnedItemId = 0;
    }

    public final int getFirstIngredientItemId() {
        return this.firstIngredientItemId;
    }

    public final int getSecondIngredientItemId() {
        return this.secondIngredientItemId;
    }

    public final int getThirdIngredientItemId() {
        return this.thirdIngredientItemId;
    }

    public final int getFirstStagePieItemId() {
        return this.firstStagePieItemId;
    }

    public final int getSecondStagePieItemId() {
        return this.secondStagePieItemId;
    }

    public final int getPieShellItemId() {
        return this.pieShellItemId;
    }

    public final int getRawPieItemId() {
        return this.rawPieItemId;
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

    public final int getSecondStageReturnedItemId() {
        return this.secondStageReturnedItemId;
    }

    public final int getThirdStageReturnedItemId() {
        return this.thirdStageReturnedItemId;
    }
}

