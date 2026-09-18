package com.rs2.model.skill.crafting.armor;

import java.util.HashMap;

public enum HardLeatherRecipe {
    BODY(2799, 1743, 1, 1131, 1, 28, 35.0),
    BODY5(2798, 1743, 1, 1131, 5, 28, 35.0),
    BODY10(1747, 1743, 1, 1131, 27, 28, 35.0),
    BODYX(1748, 1743, 1, 1131, 0, 28, 35.0);

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
        HardLeatherRecipe[] hardLeatherRecipeArray = HardLeatherRecipe.values();
        int length = hardLeatherRecipeArray.length;
        int index = 0;
        while (index < length) {
            HardLeatherRecipe hardLeatherRecipe = hardLeatherRecipeArray[index];
            definitionsByButtonId.put(hardLeatherRecipe.buttonId, hardLeatherRecipe);
            ++index;
        }
    }

    public static HardLeatherRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (HardLeatherRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private HardLeatherRecipe(int buttonId, int materialItemId, int materialAmount, int productItemId, int quantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.materialItemId = materialItemId;
        this.materialAmount = materialAmount;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getButtonId() {
        return this.buttonId;
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

