package com.rs2.model.skill.cooking;

public enum FoodPreparationRecipe {
    INGREDIENT_1953_WITH_2313_PRODUCT_2315(1953, 0, 2313, 2315, 1, 0.0, true, 0),
    INGREDIENT_1951_WITH_2315_PRODUCT_2321(1951, 0, 2315, 2321, 10, 78.0, true, 0),
    INGREDIENT_1955_WITH_2315_PRODUCT_2317(1955, 0, 2315, 2317, 30, 130.0, true, 0),
    INGREDIENT_2142_WITH_2315_PRODUCT_2319(2142, 0, 2315, 2319, 20, 110.0, true, 0),
    INGREDIENT_2007_WITH_1921_PRODUCT_2009(2007, 0, 1921, 2009, 60, 280.0, true, 7496),
    INGREDIENT_5970_WITH_1921_PRODUCT_2009(5970, 3, 1921, 2009, 60, 280.0, true, 0),
    INGREDIENT_2142_WITH_2289_PRODUCT_2293(2142, 0, 2289, 2293, 45, 169.0, true, 0),
    INGREDIENT_319_WITH_2289_PRODUCT_2297(319, 0, 2289, 2297, 45, 169.0, true, 0),
    INGREDIENT_2116_WITH_2289_PRODUCT_2301(2116, 0, 2289, 2301, 65, 195.0, true, 0),
    INGREDIENT_2118_WITH_2289_PRODUCT_2301(2118, 0, 2289, 2301, 65, 195.0, true, 0),
    INGREDIENT_1973_WITH_1891_PRODUCT_1897(1973, 0, 1891, 1897, 50, 210.0, true, 0),
    INGREDIENT_1975_WITH_1891_PRODUCT_1897(1975, 0, 1891, 1897, 50, 210.0, true, 0),
    INGREDIENT_1975_WITH_1927_PRODUCT_1977(1975, 0, 1927, 1977, 4, 0.0, true, 0),
    INGREDIENT_1933_WITH_1887_PRODUCT_1889(1933, 0, 1887, 1889, 40, 0.0, true, 0),
    INGREDIENT_1927_WITH_1887_PRODUCT_1889(1927, 0, 1887, 1889, 40, 0.0, true, 0),
    INGREDIENT_1944_WITH_1887_PRODUCT_1889(1944, 0, 1887, 1889, 40, 0.0, true, 0),
    INGREDIENT_1550_WITH_1923_PRODUCT_7074(1550, 0, 1923, 7074, 9, 25.0, true, 0),
    INGREDIENT_2169_WITH_7074_PRODUCT_7072(2169, 0, 7074, 7072, 9, 25.0, true, 0),
    INGREDIENT_2142_WITH_7072_PRODUCT_7062(2142, 0, 7072, 7062, 11, 55.0, true, 0),
    INGREDIENT_1944_WITH_1923_PRODUCT_7076(1944, 0, 1923, 7076, 13, 50.0, true, 0),
    INGREDIENT_1982_WITH_7078_PRODUCT_7064(1982, 0, 7078, 7064, 23, 50.0, true, 0),
    INGREDIENT_1957_WITH_1923_PRODUCT_1871(1957, 0, 1923, 1871, 42, 60.0, true, 0),
    INGREDIENT_6004_WITH_1923_PRODUCT_7080(6004, 0, 1923, 7080, 46, 60.0, true, 0),
    INGREDIENT_7082_WITH_7084_PRODUCT_7066(7082, 0, 7084, 7066, 57, 120.0, false, 1923),
    INGREDIENT_6697_WITH_6705_PRODUCT_6703(6697, 0, 6705, 6703, 39, 95.5, false, 1923),
    INGREDIENT_6703_WITH_7062_PRODUCT_7054(6703, 0, 7062, 7054, 41, 165.5, false, 1923),
    INGREDIENT_6703_WITH_1985_PRODUCT_6705(6703, 0, 1985, 6705, 47, 199.5, false, 1923),
    INGREDIENT_6703_WITH_7064_PRODUCT_7056(6703, 0, 7064, 7056, 51, 195.5, false, 1923),
    INGREDIENT_7082_WITH_7066_PRODUCT_7058(7082, 0, 7066, 7058, 64, 270.5, false, 1923),
    INGREDIENT_6703_WITH_7068_PRODUCT_7060(6703, 0, 7068, 7060, 68, 309.5, false, 1923),
    INGREDIENT_1987_WITH_1937_PRODUCT_1993(1987, 0, 1937, 1993, 35, 309.5, false, 0),
    INGREDIENT_4241_WITH_1921_PRODUCT_4237(4241, 0, 1921, 4237, 20, 52.0, false, 0),
    INGREDIENT_4239_WITH_1980_PRODUCT_4242(4239, 0, 1980, 4242, 20, 52.0, false, 1923);

    private int ingredientItemId;
    private int ingredientAmount;
    private int baseItemId;
    private int productItemId;
    private int requiredLevel;
    private double experience;
    private boolean putIntoMessage;
    private int returnedItemId;

    public static FoodPreparationRecipe forIngredients(int value3, int value22) {
        FoodPreparationRecipe[] foodPreparationRecipeArray = FoodPreparationRecipe.values();
        int length = foodPreparationRecipeArray.length;
        int index = 0;
        while (index < length) {
            FoodPreparationRecipe foodPreparationRecipe = foodPreparationRecipeArray[index];
            if (foodPreparationRecipe.ingredientItemId == value3 && foodPreparationRecipe.baseItemId == value22 || foodPreparationRecipe.ingredientItemId == value22 && foodPreparationRecipe.baseItemId == value3) {
                return foodPreparationRecipe;
            }
            ++index;
        }
        return null;
    }

    private FoodPreparationRecipe(int ingredientItemId, int ingredientAmount, int baseItemId, int productItemId, int requiredLevel, double experience, boolean putIntoMessage, int returnedItemId) {
        this.ingredientItemId = ingredientItemId;
        this.ingredientAmount = ingredientAmount;
        this.baseItemId = baseItemId;
        this.productItemId = productItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.putIntoMessage = putIntoMessage;
        this.returnedItemId = returnedItemId;
    }

    public final int getIngredientItemId() {
        return this.ingredientItemId;
    }

    public final int getIngredientAmount() {
        return this.ingredientAmount;
    }

    public final int getBaseItemId() {
        return this.baseItemId;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final boolean usesPutIntoMessage() {
        return this.putIntoMessage;
    }

    public final int getReturnedItemId() {
        return this.returnedItemId;
    }
}

