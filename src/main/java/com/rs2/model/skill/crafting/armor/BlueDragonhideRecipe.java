package com.rs2.model.skill.crafting.armor;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum BlueDragonhideRecipe {
    VAMB(ServerSettings.cacheVersion < 274 ? 2471 : 8889, 2505, 1, 2487, 1, 66, 70.0),
    VAMB5(8888, 2505, 1, 2487, 5, 66, 70.0),
    VAMB10(8887, 2505, 1, 2487, 10, 66, 70.0),
    VAMBX(8886, 2505, 1, 2487, 0, 66, 70.0),
    CHAPS(ServerSettings.cacheVersion < 274 ? 2472 : 8893, 2505, 2, 2493, 1, 68, 140.0),
    CHAPS5(8892, 2505, 2, 2493, 5, 68, 140.0),
    CHAPS10(8891, 2505, 2, 2493, 10, 68, 140.0),
    CHAPSX(8890, 2505, 2, 2493, 0, 68, 140.0),
    BODY(ServerSettings.cacheVersion < 274 ? 2473 : 8897, 2505, 3, 2499, 1, 71, 210.0),
    BODY5(8896, 2505, 3, 2499, 5, 71, 210.0),
    BODY10(8895, 2505, 3, 2499, 10, 71, 210.0),
    BODYX(8894, 2505, 3, 2499, 0, 71, 210.0);

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
        BlueDragonhideRecipe[] blueDragonhideRecipeArray = BlueDragonhideRecipe.values();
        int length = blueDragonhideRecipeArray.length;
        int index = 0;
        while (index < length) {
            BlueDragonhideRecipe blueDragonhideRecipe = blueDragonhideRecipeArray[index];
            definitionsByButtonId.put(blueDragonhideRecipe.buttonId, blueDragonhideRecipe);
            ++index;
        }
    }

    public static BlueDragonhideRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (BlueDragonhideRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private BlueDragonhideRecipe(int buttonId, int value22, int materialAmount, int productItemId, int quantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.materialItemId = 2505;
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

