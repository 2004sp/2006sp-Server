package com.rs2.model.skill.crafting;

import java.util.HashMap;

public enum BattlestaffRecipe {
    AIR(573, 1397, 66, 137.5),
    WATER(571, 1395, 54, 100.0),
    EARTH(575, 1399, 58, 112.5),
    FIRE(569, 1393, 62, 125.0);

    private int orbItemId;
    private int battlestaffItemId;
    private byte requiredLevel;
    private double experience;
    private static HashMap definitionsByOrbItemId;

    static {
        definitionsByOrbItemId = new HashMap();
        BattlestaffRecipe[] battlestaffRecipeArray = BattlestaffRecipe.values();
        int length = battlestaffRecipeArray.length;
        int index = 0;
        while (index < length) {
            BattlestaffRecipe battlestaffRecipe;
            BattlestaffRecipe battlestaffRecipe2 = battlestaffRecipe = battlestaffRecipeArray[index];
            definitionsByOrbItemId.put(battlestaffRecipe2.orbItemId, battlestaffRecipe);
            ++index;
        }
    }

    public static BattlestaffRecipe forOrbItemId(int itemId) {
        return (BattlestaffRecipe)((Object)definitionsByOrbItemId.get(itemId));
    }

    private BattlestaffRecipe(int orbItemId, int battlestaffItemId, int requiredLevel, double experience) {
        this.orbItemId = orbItemId;
        this.battlestaffItemId = battlestaffItemId;
        this.requiredLevel = (byte)requiredLevel;
        this.experience = experience;
    }

    public final int getOrbItemId() {
        return this.orbItemId;
    }

    public final int getBattlestaffItemId() {
        return this.battlestaffItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

