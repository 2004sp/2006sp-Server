package com.rs2.model.skill.crafting.armor;

import java.util.HashMap;

public enum SplitbarkRecipe {
    HELM(8949, 3385, 1, 2, 2, 6000),
    HELM5(8948, 3385, 5, 2, 2, 6000),
    HELM10(8947, 3385, 10, 2, 2, 6000),
    HELMX(8946, 3385, 0, 2, 2, 6000),
    BODY(8953, 3387, 1, 4, 4, 37000),
    BODY5(8952, 3387, 5, 4, 4, 37000),
    BODY10(8951, 3387, 10, 4, 4, 37000),
    BODYX(8950, 3387, 0, 4, 4, 37000),
    LEGS(8957, 3389, 1, 3, 3, 32000),
    LEGS5(8956, 3389, 5, 3, 3, 32000),
    LEGS10(8955, 3389, 10, 3, 3, 32000),
    LEGSX(8954, 3389, 0, 3, 3, 32000),
    GAUNTLETS(8961, 3391, 1, 1, 1, 1000),
    GAUNTLETS5(8960, 3391, 5, 1, 1, 1000),
    GAUNTLETS10(8959, 3391, 10, 1, 1, 1000),
    GAUNTLETSX(8958, 3391, 0, 1, 1, 1000),
    BOOTS(8965, 3393, 1, 1, 1, 1000),
    BOOTS5(8964, 3393, 5, 1, 1, 1000),
    BOOTS10(8963, 3393, 10, 1, 1, 1000),
    BOOTSX(8962, 3393, 0, 1, 1, 1000);

    private int buttonId;
    private int productItemId;
    private int quantity;
    private int materialAmount;
    private int coinAmount;
    private static HashMap definitionsByButtonId;

    static {
        definitionsByButtonId = new HashMap();
        SplitbarkRecipe[] splitbarkRecipeArray = SplitbarkRecipe.values();
        int length = splitbarkRecipeArray.length;
        int index = 0;
        while (index < length) {
            SplitbarkRecipe splitbarkRecipe = splitbarkRecipeArray[index];
            definitionsByButtonId.put(splitbarkRecipe.buttonId, splitbarkRecipe);
            ++index;
        }
    }

    public static SplitbarkRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (SplitbarkRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private SplitbarkRecipe(int buttonId, int productItemId, int quantity, int materialAmount, int value52, int coinAmount) {
        this.buttonId = buttonId;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.materialAmount = materialAmount;
        this.coinAmount = coinAmount;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getMaterialAmount() {
        return this.materialAmount;
    }

    public final int getCoinAmount() {
        return this.coinAmount;
    }

    public final int getQuantity() {
        return this.quantity;
    }
}

