package com.rs2.model.skill.crafting.armor;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum BlackDragonhideRecipe {
    VAMB(ServerSettings.cacheVersion < 274 ? 2471 : 8889, 2509, 1, 2491, 1, 79, 86.0),
    VAMB5(8888, 2509, 1, 2491, 5, 79, 86.0),
    VAMB10(8887, 2509, 1, 2491, 10, 79, 86.0),
    VAMBX(8886, 2509, 1, 2491, 0, 79, 86.0),
    CHAPS(ServerSettings.cacheVersion < 274 ? 2472 : 8893, 2509, 2, 2497, 1, 82, 172.0),
    CHAPS5(8892, 2509, 2, 2497, 5, 82, 172.0),
    CHAPS10(8891, 2509, 2, 2497, 10, 82, 172.0),
    CHAPSX(8890, 2509, 3, 2497, 0, 82, 172.0),
    BODY(ServerSettings.cacheVersion < 274 ? 2473 : 8897, 2509, 3, 2503, 1, 84, 258.0),
    BODY5(8896, 2509, 3, 2503, 5, 84, 258.0),
    BODY10(8895, 2509, 3, 2503, 10, 84, 258.0),
    BODYX(8894, 2509, 3, 2503, 0, 84, 258.0);

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
        BlackDragonhideRecipe[] blackDragonhideRecipeArray = BlackDragonhideRecipe.values();
        int length = blackDragonhideRecipeArray.length;
        int index = 0;
        while (index < length) {
            BlackDragonhideRecipe blackDragonhideRecipe = blackDragonhideRecipeArray[index];
            definitionsByButtonId.put(blackDragonhideRecipe.buttonId, blackDragonhideRecipe);
            ++index;
        }
    }

    public static BlackDragonhideRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (BlackDragonhideRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private BlackDragonhideRecipe(int buttonId, int value22, int materialAmount, int productItemId, int quantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.materialItemId = 2509;
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

