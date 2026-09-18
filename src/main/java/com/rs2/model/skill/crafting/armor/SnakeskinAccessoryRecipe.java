package com.rs2.model.skill.crafting.armor;

import java.util.HashMap;

public enum SnakeskinAccessoryRecipe {
    BANDANA(8874, 6287, 5, 6326, 1, 5, 48, 45.0),
    BANDANA5(8873, 6287, 5, 6326, 5, 5, 48, 45.0),
    BANDANA10(8872, 6287, 5, 6326, 10, 5, 48, 45.0),
    BANDANAX(8871, 6287, 5, 6326, 0, 5, 48, 45.0),
    BOOTS(8878, 6287, 6, 6328, 1, 6, 45, 30.0),
    BOOTS5(8877, 6287, 6, 6328, 5, 6, 45, 30.0),
    BOOTS10(8876, 6287, 6, 6328, 10, 6, 45, 30.0),
    BOOTSX(8875, 6287, 6, 6328, 0, 6, 45, 30.0);

    private int buttonId;
    private int materialItemId;
    private int materialAmount;
    private int productItemId;
    private int quantity;
    private int requiredLevel;
    private double experience;
    private static HashMap definitionsByButtonId;

    static {
        definitionsByButtonId = new HashMap();
        SnakeskinAccessoryRecipe[] snakeskinAccessoryRecipeArray = SnakeskinAccessoryRecipe.values();
        int length = snakeskinAccessoryRecipeArray.length;
        int index = 0;
        while (index < length) {
            SnakeskinAccessoryRecipe snakeskinAccessoryRecipe = snakeskinAccessoryRecipeArray[index];
            definitionsByButtonId.put(snakeskinAccessoryRecipe.buttonId, snakeskinAccessoryRecipe);
            ++index;
        }
    }

    public static SnakeskinAccessoryRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (SnakeskinAccessoryRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private SnakeskinAccessoryRecipe(int buttonId, int value22, int materialAmount, int productItemId, int quantity, int value62, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.materialItemId = 6287;
        this.materialAmount = materialAmount;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getMaterialItemId() {
        return this.materialItemId;
    }

    public final int getMaterialAmount() {
        return this.materialAmount;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getQuantity() {
        return this.quantity;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

