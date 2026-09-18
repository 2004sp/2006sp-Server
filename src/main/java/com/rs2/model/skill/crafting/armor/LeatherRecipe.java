package com.rs2.model.skill.crafting.armor;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum LeatherRecipe {
    ARMOUR(ServerSettings.cacheVersion < 270 ? 2400 : 8635, 1741, 1, 1129, 1, 14, 25.0),
    ARMOUR5(8634, 1741, 1, 1129, 5, 14, 25.0),
    ARMOUR10(8633, 1741, 1, 1129, 10, 14, 25.0),
    GLOVES(ServerSettings.cacheVersion < 270 ? 2401 : 8638, 1741, 1, 1059, 1, 1, 13.8),
    GLOVES5(8637, 1741, 1, 1059, 5, 1, 13.8),
    GLOVES10(8636, 1741, 1, 1059, 10, 1, 13.8),
    BOOTS(ServerSettings.cacheVersion < 270 ? 2402 : 8641, 1741, 1, 1061, 1, 7, 16.25),
    BOOTS5(8640, 1741, 1, 1061, 5, 7, 16.25),
    BOOTS10(8639, 1741, 1, 1061, 10, 7, 16.25),
    VAMB(ServerSettings.cacheVersion < 270 ? 2403 : 8644, 1741, 1, 1063, 1, 11, 22.0),
    VAMB5(8643, 1741, 1, 1063, 5, 11, 22.0),
    VAMB10(8642, 1741, 1, 1063, 10, 11, 22.0),
    CHAPS(ServerSettings.cacheVersion < 270 ? 2409 : 8647, 1741, 1, 1095, 1, 18, 27.0),
    CHAPS5(8646, 1741, 1, 1095, 5, 18, 27.0),
    CHAPS10(8645, 1741, 1, 1095, 10, 18, 27.0),
    COIF(ServerSettings.cacheVersion < 270 ? 2411 : 8650, 1741, 1, 1169, 1, 38, 37.0),
    COIF5(8649, 1741, 1, 1169, 5, 38, 37.0),
    COIF10(8648, 1741, 1, 1169, 10, 38, 37.0),
    COWL(ServerSettings.cacheVersion < 270 ? 2412 : 8653, 1741, 1, 1167, 1, 9, 18.5),
    COWL5(8652, 1741, 1, 1167, 5, 9, 18.5),
    COWL10(8651, 1741, 1, 1167, 10, 9, 18.5);

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
        LeatherRecipe[] leatherRecipeArray = LeatherRecipe.values();
        int length = leatherRecipeArray.length;
        int index = 0;
        while (index < length) {
            LeatherRecipe leatherRecipe = leatherRecipeArray[index];
            definitionsByButtonId.put(leatherRecipe.buttonId, leatherRecipe);
            ++index;
        }
    }

    public static LeatherRecipe forButtonId(int buttonId) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (LeatherRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private LeatherRecipe(int buttonId, int value22, int value32, int productItemId, int quantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.materialItemId = 1741;
        this.materialAmount = 1;
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

