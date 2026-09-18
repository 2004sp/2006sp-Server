package com.rs2.model.item.consumable;

import java.util.HashMap;
import java.util.Map;

public enum FoodDefinition {
    HEALS_1(1, -1, new int[]{1971, 319, 1965, 1967, 2128, 1957, 1942, 2130}),
    HEALS_2(2, -1, new int[]{1963, 1985, 2126, 247, 2120, 2120, 2108, 7072, 1969, 1982, 2162}),
    HEALS_3(3, -1, new int[]{7928, 7929, 7930, 7931, 7932, 7933, 2142, 4293, 2140, 4291, 1973, 3151, 315, 1861, 2152}),
    HEALS_4(4, -1, new int[]{6701, 325, 1977, 403}),
    HEALS_5(5, -1, new int[]{2309, 7062, 3228, 347, 7082, 7084, 7078}),
    HEALS_6(6, -1, new int[]{355, 6961, 6962, 6965}),
    HEALS_7(7, -1, new int[]{2213, 2209, 2239, 339, 7223, 333}),
    HEALS_8(8, -1, new int[]{7064, 5972, 6883, 351, 2217, 2244, 2205, 2237}),
    HEALS_9(9, -1, new int[]{329}),
    HEALS_10(10, -1, new int[]{2878, 7228, 361}),
    HEALS_11(11, -1, new int[]{2259, 2149, 2277, 7066, 2255, 2281, 2253}),
    HEALS_12(12, -1, new int[]{379, 739, 2195, 2191}),
    HEALS_13(13, -1, new int[]{365, 7068}),
    HEALS_14(14, -1, new int[]{373, 2345, 6703, 7054, 1961}),
    HEALS_15(15, -1, new int[]{2185, 2187}),
    HEALS_16(16, -1, new int[]{7056, 6705}),
    HEALS_18(18, -1, new int[]{3144, 3147}),
    HEALS_19(19, -1, new int[]{1883, 1885}),
    HEALS_20(20, -1, new int[]{7058, 385}),
    HEALS_21(21, -1, new int[]{397}),
    HEALS_22(22, -1, new int[]{391, 7060}),
    RANDOM_HEAL_5_TO_7(5 + (int)(Math.random() * 3.0), -1, new int[]{3369}),
    RANDOM_HEAL_6_TO_8(6 + (int)(Math.random() * 3.0), -1, new int[]{3371}),
    RANDOM_HEAL_7_TO_9(7 + (int)(Math.random() * 3.0), -1, new int[]{3373}),
    RANDOM_HEAL_6_TO_10(6 + (int)(Math.random() * 5.0), -1, new int[]{3381}),
    RANDOM_HEAL_8_TO_12(8 + (int)(Math.random() * 5.0), -1, new int[]{5003, 5007}),
    RANDOM_HEAL_7_TO_18(7 + (int)(Math.random() * 12.0), -1, new int[]{6293, 6295, 6297, 6299, 6303}),
    REDBERRY_PIE(5, 2333, new int[]{2325}),
    HALF_REDBERRY_PIE(5, 2313, new int[]{2333}),
    MEAT_PIE(6, 2331, new int[]{2327}),
    HALF_MEAT_PIE(5, 2313, new int[]{2331}),
    GARDEN_PIE(6, 7190, new int[]{7188}),
    HALF_GARDEN_PIE(6, 2313, new int[]{7190}),
    FISH_PIE(6, 7180, new int[]{7178}),
    HALF_FISH_PIE(6, 2313, new int[]{7180}),
    APPLE_PIE(7, 2335, new int[]{2323}),
    HALF_APPLE_PIE(7, 2313, new int[]{2335}),
    ADMIRAL_PIE(8, 7200, new int[]{7198}),
    HALF_ADMIRAL_PIE(8, 2313, new int[]{7200}),
    WILD_PIE(8, 7220, new int[]{7218}),
    HALF_WILD_PIE(8, 2313, new int[]{7220}),
    SUMMER_PIE(8, 7210, new int[]{7208}),
    HALF_SUMMER_PIE(8, 2313, new int[]{7210}),
    PLAIN_PIZZA(7, 2291, new int[]{2289}),
    HALF_PLAIN_PIZZA(7, -1, new int[]{2291}),
    MEAT_PIZZA(8, 2295, new int[]{2293}),
    HALF_MEAT_PIZZA(8, -1, new int[]{2295}),
    ANCHOVY_PIZZA(9, 2299, new int[]{2297}),
    HALF_ANCHOVY_PIZZA(9, -1, new int[]{2299}),
    PINEAPPLE_PIZZA(9, 2303, new int[]{2301}),
    HALF_PINEAPPLE_PIZZA(9, -1, new int[]{2303}),
    CHOCOLATE_MILK(4, 1925, new int[]{1977}),
    WINE(11, 1935, new int[]{1993}),
    CHOCOLATE_CAKE(5, 1899, new int[]{1897}),
    TWO_THIRDS_CHOCOLATE_CAKE(5, 1901, new int[]{1899}),
    CHOCOLATE_CAKE_SLICE(5, -1, new int[]{1901}),
    CAKE(5, 1893, new int[]{1891}),
    TWO_THIRDS_CAKE(5, 1895, new int[]{1893}),
    CAKE_SLICE(5, -1, new int[]{1895}),
    STEW(11, 1923, new int[]{2003}),
    CURRY(19, 1923, new int[]{2011});

    private static Map definitionsByItemId;
    private int[] itemIds;
    private int healAmount;
    private int replacementItemId;

    static {
        definitionsByItemId = new HashMap();
        for (FoodDefinition definition : FoodDefinition.values()) {
            for (int itemId : definition.itemIds) {
                definitionsByItemId.put(itemId, definition);
            }
        }
    }

    public static FoodDefinition forItemId(int itemId) {
        return (FoodDefinition)((Object)definitionsByItemId.get(itemId));
    }

    private FoodDefinition(int healAmount, int replacementItemId, int[] itemIds) {
        this.healAmount = healAmount;
        this.replacementItemId = replacementItemId;
        this.itemIds = itemIds;
    }

    public final int getHealAmount() {
        return this.healAmount;
    }

    public final int getReplacementItemId() {
        return this.replacementItemId;
    }

    public final int[] getItemIds() {
        return this.itemIds;
    }
}
