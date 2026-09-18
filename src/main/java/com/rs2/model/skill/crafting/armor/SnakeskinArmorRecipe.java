package com.rs2.model.skill.crafting.armor;

import java.util.HashMap;

public enum SnakeskinArmorRecipe {
    VAMB(8889, 6289, 8, 6330, 1, 8, 47, 35.0),
    VAMB5(8888, 6289, 8, 6330, 5, 8, 47, 35.0),
    VAMB10(8887, 6289, 8, 6330, 10, 8, 47, 35.0),
    VAMBX(8886, 6289, 8, 6330, 0, 8, 47, 35.0),
    CHAPS(8893, 6289, 12, 6324, 1, 12, 51, 50.0),
    CHAPS5(8892, 6289, 12, 6324, 5, 12, 51, 50.0),
    CHAPS10(8891, 6289, 12, 6324, 10, 12, 51, 50.0),
    CHAPSX(8890, 6289, 12, 6324, 0, 12, 51, 50.0),
    BODY(8897, 6289, 15, 6322, 1, 15, 53, 55.0),
    BODY5(8896, 6289, 15, 6322, 5, 15, 53, 55.0),
    BODY10(8895, 6289, 15, 6322, 10, 15, 53, 55.0),
    BODYX(8894, 6289, 15, 6322, 0, 15, 53, 55.0);

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
        SnakeskinArmorRecipe[] snakeskinArmorRecipeArray = SnakeskinArmorRecipe.values();
        int length = snakeskinArmorRecipeArray.length;
        int index = 0;
        while (index < length) {
            SnakeskinArmorRecipe snakeskinArmorRecipe = snakeskinArmorRecipeArray[index];
            definitionsByButtonId.put(snakeskinArmorRecipe.buttonId, snakeskinArmorRecipe);
            ++index;
        }
    }

    public static SnakeskinArmorRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (SnakeskinArmorRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private SnakeskinArmorRecipe(int buttonId, int value22, int materialAmount, int productItemId, int quantity, int value62, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.materialItemId = 6289;
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

