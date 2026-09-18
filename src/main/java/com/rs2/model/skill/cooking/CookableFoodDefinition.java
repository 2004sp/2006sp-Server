package com.rs2.model.skill.cooking;

import java.util.HashMap;
import java.util.Map;

public enum CookableFoodDefinition {
    RAW_2142_COOKED_2146_BURNT_2146(2142, 2146, 2146, 1, 0.0, 0, 0, true, 255, 255),
    RAW_401_COOKED_1781_BURNT_1781(401, 1781, 1781, 1, 0.0, 0, 0, true, 255, 255),
    RAW_2132_COOKED_2142_BURNT_2146(2132, 2142, 2146, 1, 30.0, 7, 7, true, 128, 512),
    RAW_2134_COOKED_2142_BURNT_2146(2134, 2142, 2146, 1, 30.0, 7, 7, true, 128, 512),
    RAW_2136_COOKED_2142_BURNT_2146(2136, 2142, 2146, 1, 30.0, 7, 7, true, 128, 512),
    RAW_2138_COOKED_2140_BURNT_2144(2138, 2140, 2144, 1, 30.0, 7, 7, true, 128, 512),
    RAW_3226_COOKED_3228_BURNT_7222(3226, 3228, 7222, 1, 30.0, 7, 7, true, 128, 512),
    RAW_1859_COOKED_1861_BURNT_323(1859, 1861, 323, 1, 40.0, 7, 7, true, 30, 253),
    RAW_3363_COOKED_3369_BURNT_3375(3363, 3369, 3375, 12, 70.0, 17, 17, true, 93, 444),
    RAW_3365_COOKED_3371_BURNT_3375(3365, 3371, 3375, 17, 80.0, 26, 26, true, 85, 428),
    RAW_6293_COOKED_6297_BURNT_6301(6293, 6297, 6301, 16, 80.0, 25, 25, true, 91, 438),
    RAW_6295_COOKED_6299_BURNT_6303(6295, 6299, 6303, 16, 80.0, 25, 25, true, 91, 438),
    RAW_3226_COOKED_7223_BURNT_7222(3226, 7223, 7222, 16, 72.0, 25, 25, true, 160, 255),
    RAW_3367_COOKED_3373_BURNT_3375(3367, 3373, 3375, 22, 95.0, 28, 28, true, 73, 402),
    RAW_2876_COOKED_2878_BURNT_2880(2876, 2878, 2880, 30, 140.0, 38, 38, true, 200, 255),
    RAW_2321_COOKED_2325_BURNT_2329(2321, 2325, 2329, 10, 78.0, 15, 15, false, 98, 452),
    RAW_2317_COOKED_2327_BURNT_2329(2317, 2327, 2329, 20, 110.0, 25, 25, false, 78, 412),
    RAW_2319_COOKED_7170_BURNT_2329(2319, 7170, 2329, 29, 128.0, 35, 35, false, 58, 372),
    RAW_7168_COOKED_2323_BURNT_2329(7168, 2323, 2329, 30, 130.0, 35, 35, false, 58, 372),
    RAW_7176_COOKED_7178_BURNT_2329(7176, 7178, 2329, 34, 138.0, 39, 39, false, 48, 352),
    RAW_7186_COOKED_7188_BURNT_2329(7186, 7188, 2329, 47, 164.0, 52, 52, false, 38, 332),
    RAW_7196_COOKED_7198_BURNT_2329(7196, 7198, 2329, 70, 210.0, 77, 77, false, 15, 270),
    RAW_7206_COOKED_7208_BURNT_2329(7206, 7208, 2329, 85, 240.0, 90, 90, false, 1, 222),
    RAW_7216_COOKED_7218_BURNT_2329(7216, 7218, 2329, 95, 260.0, 100, 100, false, 1, 212),
    RAW_2001_COOKED_2003_BURNT_2005(2001, 2003, 2005, 25, 117.0, 30, 30, true, 68, 392),
    RAW_2009_COOKED_2011_BURNT_2013(2009, 2011, 2013, 60, 280.0, 65, 65, true, 38, 332),
    RAW_2287_COOKED_2289_BURNT_2305(2287, 2289, 2305, 35, 143.0, 38, 38, true, 48, 352),
    RAW_1889_COOKED_1891_BURNT_1903(1889, 1891, 1903, 40, 180.0, 120, 120, false, 38, 332),
    RAW_2307_COOKED_2309_BURNT_2311(2307, 2309, 2311, 1, 40.0, 5, 5, false, 118, 492),
    RAW_1863_COOKED_1865_BURNT_1867(1863, 1865, 1867, 58, 40.0, 65, 65, true, 118, 492),
    RAW_7072_COOKED_7072_BURNT_2880(7072, 7072, 2880, 9, 25.0, 38, 38, true, 128, 512),
    RAW_7076_COOKED_7078_BURNT_7090(7076, 7078, 7090, 13, 50.0, 16, 16, true, 90, 438),
    RAW_1871_COOKED_7084_BURNT_7092(1871, 7084, 7092, 42, 60.0, 45, 45, true, 36, 322),
    RAW_7080_COOKED_7082_BURNT_7094(7080, 7082, 7094, 46, 60.0, 52, 52, true, 16, 282),
    RAW_1942_COOKED_6701_BURNT_6699(1942, 6701, 6699, 9, 25.0, 38, 38, false, 108, 472),
    RAW_4237_COOKED_4239_BURNT_6699(4237, 4239, 6699, 9, 25.0, 38, 38, true, 78, 412),
    RAW_6006_COOKED_6008_BURNT_6008(6006, 6008, 6008, 1, 0.0, 1, 1, true, 128, 512),
    RAW_317_COOKED_315_BURNT_323(317, 315, 323, 1, 30.0, 34, 34, true, 128, 512),
    RAW_3150_COOKED_3151_BURNT_3148(3150, 3151, 3148, 1, 10.0, 34, 34, true, 200, 400),
    RAW_327_COOKED_325_BURNT_369(327, 325, 369, 1, 40.0, 38, 38, true, 118, 492),
    RAW_321_COOKED_319_BURNT_323(321, 319, 323, 1, 30.0, 34, 34, true, 128, 512),
    RAW_345_COOKED_347_BURNT_357(345, 347, 357, 5, 50.0, 37, 37, true, 108, 472),
    RAW_353_COOKED_355_BURNT_357(353, 355, 357, 10, 60.0, 35, 35, true, 98, 452),
    RAW_335_COOKED_333_BURNT_343(335, 333, 343, 15, 70.0, 50, 50, true, 88, 432),
    RAW_341_COOKED_339_BURNT_343(341, 339, 343, 17, 75.0, 39, 39, true, 88, 432),
    RAW_349_COOKED_351_BURNT_343(349, 351, 343, 20, 80.0, 52, 52, true, 78, 412),
    RAW_331_COOKED_329_BURNT_343(331, 329, 343, 25, 90.0, 58, 58, true, 68, 392),
    RAW_3379_COOKED_3381_BURNT_3383(3379, 3381, 3383, 28, 95.0, 58, 58, true, 63, 382),
    RAW_359_COOKED_361_BURNT_367(359, 361, 367, 30, 100.0, 65, 65, true, 58, 372),
    RAW_3142_COOKED_3144_BURNT_3148(3142, 3144, 3148, 30, 190.0, 100, 100, true, 70, 255),
    RAW_5001_COOKED_5003_BURNT_5002(5001, 5003, 5002, 38, 115.0, 40, 40, true, 38, 332),
    RAW_377_COOKED_379_BURNT_381(377, 379, 381, 40, 120.0, 74, 66, true, 38, 332),
    RAW_363_COOKED_365_BURNT_367(363, 365, 367, 43, 130.0, 80, 80, true, 33, 312),
    RAW_371_COOKED_373_BURNT_375(371, 373, 375, 45, 140.0, 86, 81, true, 18, 292),
    RAW_2148_COOKED_2149_BURNT_3383(2148, 2149, 3383, 53, 60.0, 72, 72, true, 18, 292),
    RAW_383_COOKED_385_BURNT_387(383, 385, 387, 80, 210.0, 104, 94, true, 1, 232),
    RAW_395_COOKED_397_BURNT_399(395, 397, 399, 82, 212.0, 110, 110, true, 1, 222),
    RAW_389_COOKED_391_BURNT_393(389, 391, 393, 91, 216.0, 112, 112, true, 1, 222);

    private int rawItemId;
    private int cookedItemId;
    private int burntItemId;
    private int requiredLevel;
    private double experience;
    private boolean cookableOnFire;
    private int successChanceLow;
    private int successChanceHigh;
    private static Map definitionsByRawItemId;

    static {
        definitionsByRawItemId = new HashMap();
        CookableFoodDefinition[] cookableFoodDefinitionArray = CookableFoodDefinition.values();
        int length = cookableFoodDefinitionArray.length;
        int index = 0;
        while (index < length) {
            CookableFoodDefinition cookableFoodDefinition = cookableFoodDefinitionArray[index];
            definitionsByRawItemId.put(cookableFoodDefinition.rawItemId, cookableFoodDefinition);
            ++index;
        }
    }

    public static CookableFoodDefinition forRawItemId(int itemId) {
        return (CookableFoodDefinition)((Object)definitionsByRawItemId.get(itemId));
    }

    private CookableFoodDefinition(int rawItemId, int cookedItemId, int burntItemId, int requiredLevel, double experience, int value53, int value62, boolean cookableOnFire, int successChanceLow, int successChanceHigh) {
        this.rawItemId = rawItemId;
        this.cookedItemId = cookedItemId;
        this.burntItemId = burntItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.cookableOnFire = cookableOnFire;
        this.successChanceLow = successChanceLow;
        this.successChanceHigh = successChanceHigh;
    }

    public final int getCookedItemId() {
        return this.cookedItemId;
    }

    public final int getBurntItemId() {
        return this.burntItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final boolean canCookOnFire() {
        return this.cookableOnFire;
    }

    public final int getSuccessChanceLow() {
        return this.successChanceLow;
    }

    public final int getSuccessChanceHigh() {
        return this.successChanceHigh;
    }
}

