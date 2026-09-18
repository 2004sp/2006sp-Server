package com.rs2.model.skill.crafting;

import com.rs2.model.item.ItemDefinition;
import java.util.HashMap;

public enum GlassblowingRecipe {
    VIAL(ItemDefinition.isDefined(4529) ? 11474 : 2471, 1775, 1781, 229, 1, 33, 35.0),
    VIAL5(11473, 1775, 1781, 229, 5, 33, 35.0),
    VIAL10(11472, 1775, 1781, 229, 10, 33, 35.0),
    VIALX(11471, 1775, 1781, 229, 0, 33, 35.0),
    ORB(ItemDefinition.isDefined(4529) ? 12396 : 2472, 1775, 1781, 567, 1, 46, 52.5),
    ORB5(12395, 1775, 1781, 567, 5, 46, 52.5),
    ORB10(12394, 1775, 1781, 567, 10, 46, 52.5),
    ORBX(11475, 1775, 1781, 567, 0, 46, 52.5),
    BEER(ItemDefinition.isDefined(4529) ? 12400 : 2473, 1775, 1781, 1919, 1, 1, 17.5),
    BEER5(12399, 1775, 1781, 1919, 5, 1, 17.5),
    BEER10(12398, 1775, 1781, 1919, 10, 1, 17.5),
    BEERX(12397, 1775, 1781, 1919, 0, 1, 17.5),
    CANDLE(12404, 1775, 1781, 4527, 1, 4, 19.0),
    CANDLE5(12403, 1775, 1781, 4527, 5, 4, 19.0),
    CANDLE10(12402, 1775, 1781, 4527, 10, 4, 19.0),
    CANDLEX(12401, 1775, 1781, 4527, 0, 4, 19.0),
    OIL_LAMP(12408, 1775, 1781, 4525, 1, 12, 25.0),
    OIL_LAMP5(12407, 1775, 1781, 4525, 5, 12, 25.0),
    OIL_LAMP10(12406, 1775, 1781, 4525, 10, 12, 25.0),
    OIL_LAMPX(12405, 1775, 1781, 4525, 0, 12, 25.0),
    FISHBOWL(6203, 1775, 1781, 6667, 1, 42, 42.5),
    FISHBOWL5(6202, 1775, 1781, 6667, 5, 42, 42.5),
    FISHBOWL10(6201, 1775, 1781, 6667, 10, 42, 42.5),
    FISHBOWLX(6200, 1775, 1781, 6667, 0, 12, 25.0),
    LANTERN_LENS(12412, 1775, 1781, 4542, 1, 49, 55.0),
    LANTERN_LENS5(12411, 1775, 1781, 4542, 5, 49, 55.0),
    LANTERN_LENS10(12410, 1775, 1781, 4542, 10, 49, 55.0),
    LANTERN_LENSX(12409, 1775, 1781, 4542, 0, 49, 55.0);

    private int buttonId;
    private int productItemId;
    private int quantity;
    private int requiredLevel;
    private double experience;
    private static HashMap definitionsByButtonId;

    static {
        definitionsByButtonId = new HashMap();
        GlassblowingRecipe[] glassblowingRecipeArray = GlassblowingRecipe.values();
        int length = glassblowingRecipeArray.length;
        int index = 0;
        while (index < length) {
            GlassblowingRecipe glassblowingRecipe = glassblowingRecipeArray[index];
            definitionsByButtonId.put(glassblowingRecipe.buttonId, glassblowingRecipe);
            ++index;
        }
    }

    public static GlassblowingRecipe forButtonId(int buttonId) {
        return (GlassblowingRecipe)((Object)definitionsByButtonId.get(buttonId));
    }

    private GlassblowingRecipe(int buttonId, int value22, int value32, int productItemId, int quantity, int requiredLevel, double experience) {
        this.buttonId = buttonId;
        this.productItemId = productItemId;
        this.quantity = quantity;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
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

