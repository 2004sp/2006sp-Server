package com.rs2.model.skill.crafting;

import java.util.HashMap;

public enum DramenStaffRecipe {
    STAFF(2799, 771, 772, 1, 1, 31, 0.0),
    STAFF5(2798, 771, 772, 5, 1, 31, 0.0),
    STAFF10(1747, 771, 772, 27, 1, 31, 0.0),
    STAFFX(1748, 771, 772, 0, 1, 31, 0.0);

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
        DramenStaffRecipe[] dramenStaffRecipeArray = DramenStaffRecipe.values();
        int length = dramenStaffRecipeArray.length;
        int index = 0;
        while (index < length) {
            DramenStaffRecipe dramenStaffRecipe = dramenStaffRecipeArray[index];
            definitionsByButtonId.put(dramenStaffRecipe.buttonId, dramenStaffRecipe);
            ++index;
        }
    }

    public static DramenStaffRecipe forButtonId(int buttonId) {
        return (DramenStaffRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private DramenStaffRecipe(int buttonId, int ingredientItemId, int productItemId, int quantity, int ingredientAmount, int requiredLevel, double experience) {
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

