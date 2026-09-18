package com.rs2.model.skill.smithing;

import com.rs2.model.skill.smithing.AdamantBarDefinition;
import com.rs2.model.skill.smithing.BronzeBarDefinition;
import com.rs2.model.skill.smithing.IronBarDefinition;
import com.rs2.model.skill.smithing.MithrilBarDefinition;
import com.rs2.model.skill.smithing.RuniteBarDefinition;
import com.rs2.model.skill.smithing.SmithableItemDefinition;
import com.rs2.model.skill.smithing.SteelBarDefinition;

public class SmithingBarDefinition {
    private static SmithingBarDefinition BRONZE_BAR;
    private static SmithingBarDefinition IRON_BAR;
    private static SmithingBarDefinition STEEL_BAR;
    private static SmithingBarDefinition MITHRIL_BAR;
    private static SmithingBarDefinition ADAMANT_BAR;
    private static SmithingBarDefinition RUNITE_BAR;
    public static SmithingBarDefinition[] VALUES;
    private SmithableItemDefinition[] smithableItems;
    private int requiredLevel;
    private double experiencePerBar;
    private int barItemId;
    public static int[] productNameTextIds;
    public static int[] barRequirementTextIds;
    public static int[] productItemInterfaceIds;
    public static int[] productItemSlots;

    static {
        SmithableItemDefinition[] smithableItemDefinitionArray = new SmithableItemDefinition[25];
        smithableItemDefinitionArray[0] = SmithableItemDefinition.BRONZE_DAGGER;
        smithableItemDefinitionArray[1] = SmithableItemDefinition.BRONZE_AXE;
        smithableItemDefinitionArray[2] = SmithableItemDefinition.BRONZE_CHAIN_BODY;
        smithableItemDefinitionArray[3] = SmithableItemDefinition.BRONZE_MEDIUM_HELM;
        smithableItemDefinitionArray[4] = SmithableItemDefinition.BRONZE_DART_TIPS;
        smithableItemDefinitionArray[5] = SmithableItemDefinition.BRONZE_SWORD;
        smithableItemDefinitionArray[6] = SmithableItemDefinition.BRONZE_MACE;
        smithableItemDefinitionArray[7] = SmithableItemDefinition.BRONZE_PLATE_LEGS;
        smithableItemDefinitionArray[8] = SmithableItemDefinition.BRONZE_FULL_HELM;
        smithableItemDefinitionArray[9] = SmithableItemDefinition.BRONZE_ARROWTIPS;
        smithableItemDefinitionArray[10] = SmithableItemDefinition.BRONZE_SCIMITAR;
        smithableItemDefinitionArray[11] = SmithableItemDefinition.BRONZE_WARHAMMER;
        smithableItemDefinitionArray[12] = SmithableItemDefinition.BRONZE_PLATE_SKIRT;
        smithableItemDefinitionArray[13] = SmithableItemDefinition.BRONZE_SQUARE_SHIELD;
        smithableItemDefinitionArray[14] = SmithableItemDefinition.BRONZE_KNIVES;
        smithableItemDefinitionArray[15] = SmithableItemDefinition.BRONZE_LONG_SWORD;
        smithableItemDefinitionArray[16] = SmithableItemDefinition.BRONZE_BATTLE_AXE;
        smithableItemDefinitionArray[17] = SmithableItemDefinition.BRONZE_PLATE_BODY;
        smithableItemDefinitionArray[18] = SmithableItemDefinition.BRONZE_KITE_SHIELD;
        smithableItemDefinitionArray[19] = SmithableItemDefinition.BRONZE_NAILS;
        smithableItemDefinitionArray[20] = SmithableItemDefinition.BRONZE_TWO_HANDED_SWORD;
        smithableItemDefinitionArray[21] = SmithableItemDefinition.BRONZE_CLAWS;
        smithableItemDefinitionArray[24] = SmithableItemDefinition.BRONZE_WIRE;
        BRONZE_BAR = new BronzeBarDefinition(1, 12.5, 2349, smithableItemDefinitionArray);
        SmithableItemDefinition[] smithableItemDefinitionArray2 = new SmithableItemDefinition[25];
        smithableItemDefinitionArray2[0] = SmithableItemDefinition.IRON_DAGGER;
        smithableItemDefinitionArray2[1] = SmithableItemDefinition.IRON_AXE;
        smithableItemDefinitionArray2[2] = SmithableItemDefinition.IRON_CHAIN_BODY;
        smithableItemDefinitionArray2[3] = SmithableItemDefinition.IRON_MEDIUM_HELM;
        smithableItemDefinitionArray2[4] = SmithableItemDefinition.IRON_DART_TIPS;
        smithableItemDefinitionArray2[5] = SmithableItemDefinition.IRON_SWORD;
        smithableItemDefinitionArray2[6] = SmithableItemDefinition.IRON_MACE;
        smithableItemDefinitionArray2[7] = SmithableItemDefinition.IRON_PLATE_LEGS;
        smithableItemDefinitionArray2[8] = SmithableItemDefinition.IRON_FULL_HELM;
        smithableItemDefinitionArray2[9] = SmithableItemDefinition.IRON_ARROWTIPS;
        smithableItemDefinitionArray2[10] = SmithableItemDefinition.IRON_SCIMITAR;
        smithableItemDefinitionArray2[11] = SmithableItemDefinition.IRON_WARHAMMER;
        smithableItemDefinitionArray2[12] = SmithableItemDefinition.IRON_PLATE_SKIRT;
        smithableItemDefinitionArray2[13] = SmithableItemDefinition.IRON_SQUARE_SHIELD;
        smithableItemDefinitionArray2[14] = SmithableItemDefinition.IRON_KNIVES;
        smithableItemDefinitionArray2[15] = SmithableItemDefinition.IRON_LONG_SWORD;
        smithableItemDefinitionArray2[16] = SmithableItemDefinition.IRON_BATTLE_AXE;
        smithableItemDefinitionArray2[17] = SmithableItemDefinition.IRON_PLATE_BODY;
        smithableItemDefinitionArray2[18] = SmithableItemDefinition.IRON_KITE_SHIELD;
        smithableItemDefinitionArray2[19] = SmithableItemDefinition.IRON_NAILS;
        smithableItemDefinitionArray2[20] = SmithableItemDefinition.IRON_TWO_HANDED_SWORD;
        smithableItemDefinitionArray2[21] = SmithableItemDefinition.IRON_CLAWS;
        smithableItemDefinitionArray2[22] = SmithableItemDefinition.IRON_OIL_LAMP;
        smithableItemDefinitionArray2[24] = SmithableItemDefinition.IRON_SPIT;
        IRON_BAR = new IronBarDefinition(15, 25.0, 2351, smithableItemDefinitionArray2);
        SmithableItemDefinition[] smithableItemDefinitionArray3 = new SmithableItemDefinition[25];
        smithableItemDefinitionArray3[0] = SmithableItemDefinition.STEEL_DAGGER;
        smithableItemDefinitionArray3[1] = SmithableItemDefinition.STEEL_AXE;
        smithableItemDefinitionArray3[2] = SmithableItemDefinition.STEEL_CHAIN_BODY;
        smithableItemDefinitionArray3[3] = SmithableItemDefinition.STEEL_MEDIUM_HELM;
        smithableItemDefinitionArray3[4] = SmithableItemDefinition.STEEL_DART_TIPS;
        smithableItemDefinitionArray3[5] = SmithableItemDefinition.STEEL_SWORD;
        smithableItemDefinitionArray3[6] = SmithableItemDefinition.STEEL_MACE;
        smithableItemDefinitionArray3[7] = SmithableItemDefinition.STEEL_PLATE_LEGS;
        smithableItemDefinitionArray3[8] = SmithableItemDefinition.STEEL_FULL_HELM;
        smithableItemDefinitionArray3[9] = SmithableItemDefinition.STEEL_ARROWTIPS;
        smithableItemDefinitionArray3[10] = SmithableItemDefinition.STEEL_SCIMITAR;
        smithableItemDefinitionArray3[11] = SmithableItemDefinition.STEEL_WARHAMMER;
        smithableItemDefinitionArray3[12] = SmithableItemDefinition.STEEL_PLATE_SKIRT;
        smithableItemDefinitionArray3[13] = SmithableItemDefinition.STEEL_SQUARE_SHIELD;
        smithableItemDefinitionArray3[14] = SmithableItemDefinition.STEEL_KNIVES;
        smithableItemDefinitionArray3[15] = SmithableItemDefinition.STEEL_LONG_SWORD;
        smithableItemDefinitionArray3[16] = SmithableItemDefinition.STEEL_BATTLE_AXE;
        smithableItemDefinitionArray3[17] = SmithableItemDefinition.STEEL_PLATE_BODY;
        smithableItemDefinitionArray3[18] = SmithableItemDefinition.STEEL_KITE_SHIELD;
        smithableItemDefinitionArray3[19] = SmithableItemDefinition.STEEL_NAILS;
        smithableItemDefinitionArray3[20] = SmithableItemDefinition.STEEL_TWO_HANDED_SWORD;
        smithableItemDefinitionArray3[21] = SmithableItemDefinition.STEEL_CLAWS;
        smithableItemDefinitionArray3[22] = SmithableItemDefinition.STEEL_BULLSEYE_LAMP;
        smithableItemDefinitionArray3[24] = SmithableItemDefinition.STEEL_STUDS;
        STEEL_BAR = new SteelBarDefinition(30, 37.5, 2353, smithableItemDefinitionArray3);
        SmithableItemDefinition[] smithableItemDefinitionArray4 = new SmithableItemDefinition[25];
        smithableItemDefinitionArray4[0] = SmithableItemDefinition.MITHRIL_DAGGER;
        smithableItemDefinitionArray4[1] = SmithableItemDefinition.MITHRIL_AXE;
        smithableItemDefinitionArray4[2] = SmithableItemDefinition.MITHRIL_CHAIN_BODY;
        smithableItemDefinitionArray4[3] = SmithableItemDefinition.MITHRIL_MEDIUM_HELM;
        smithableItemDefinitionArray4[4] = SmithableItemDefinition.MITHRIL_DART_TIPS;
        smithableItemDefinitionArray4[5] = SmithableItemDefinition.MITHRIL_SWORD;
        smithableItemDefinitionArray4[6] = SmithableItemDefinition.MITHRIL_MACE;
        smithableItemDefinitionArray4[7] = SmithableItemDefinition.MITHRIL_PLATE_LEGS;
        smithableItemDefinitionArray4[8] = SmithableItemDefinition.MITHRIL_FULL_HELM;
        smithableItemDefinitionArray4[9] = SmithableItemDefinition.MITHRIL_ARROWTIPS;
        smithableItemDefinitionArray4[10] = SmithableItemDefinition.MITHRIL_SCIMITAR;
        smithableItemDefinitionArray4[11] = SmithableItemDefinition.MITHRIL_WARHAMMER;
        smithableItemDefinitionArray4[12] = SmithableItemDefinition.MITHRIL_PLATE_SKIRT;
        smithableItemDefinitionArray4[13] = SmithableItemDefinition.MITHRIL_SQUARE_SHIELD;
        smithableItemDefinitionArray4[14] = SmithableItemDefinition.MITHRIL_KNIVES;
        smithableItemDefinitionArray4[15] = SmithableItemDefinition.MITHRIL_LONG_SWORD;
        smithableItemDefinitionArray4[16] = SmithableItemDefinition.MITHRIL_BATTLE_AXE;
        smithableItemDefinitionArray4[17] = SmithableItemDefinition.MITHRIL_PLATE_BODY;
        smithableItemDefinitionArray4[18] = SmithableItemDefinition.MITHRIL_KITE_SHIELD;
        smithableItemDefinitionArray4[19] = SmithableItemDefinition.MITHRIL_NAILS;
        smithableItemDefinitionArray4[20] = SmithableItemDefinition.MITHRIL_TWO_HANDED_SWORD;
        smithableItemDefinitionArray4[21] = SmithableItemDefinition.MITHRIL_CLAWS;
        MITHRIL_BAR = new MithrilBarDefinition(50, 50.0, 2359, smithableItemDefinitionArray4);
        SmithableItemDefinition[] smithableItemDefinitionArray5 = new SmithableItemDefinition[25];
        smithableItemDefinitionArray5[0] = SmithableItemDefinition.ADAMANT_DAGGER;
        smithableItemDefinitionArray5[1] = SmithableItemDefinition.ADAMANT_AXE;
        smithableItemDefinitionArray5[2] = SmithableItemDefinition.ADAMANT_CHAIN_BODY;
        smithableItemDefinitionArray5[3] = SmithableItemDefinition.ADAMANT_MEDIUM_HELM;
        smithableItemDefinitionArray5[4] = SmithableItemDefinition.ADAMANT_DART_TIPS;
        smithableItemDefinitionArray5[5] = SmithableItemDefinition.ADAMANT_SWORD;
        smithableItemDefinitionArray5[6] = SmithableItemDefinition.ADAMANT_MACE;
        smithableItemDefinitionArray5[7] = SmithableItemDefinition.ADAMANT_PLATE_LEGS;
        smithableItemDefinitionArray5[8] = SmithableItemDefinition.ADAMANT_FULL_HELM;
        smithableItemDefinitionArray5[9] = SmithableItemDefinition.ADAMANT_ARROWTIPS;
        smithableItemDefinitionArray5[10] = SmithableItemDefinition.ADAMANT_SCIMITAR;
        smithableItemDefinitionArray5[11] = SmithableItemDefinition.ADAMANT_WARHAMMER;
        smithableItemDefinitionArray5[12] = SmithableItemDefinition.ADAMANT_PLATE_SKIRT;
        smithableItemDefinitionArray5[13] = SmithableItemDefinition.ADAMANT_SQUARE_SHIELD;
        smithableItemDefinitionArray5[14] = SmithableItemDefinition.ADAMANT_KNIVES;
        smithableItemDefinitionArray5[15] = SmithableItemDefinition.ADAMANT_LONG_SWORD;
        smithableItemDefinitionArray5[16] = SmithableItemDefinition.ADAMANT_BATTLE_AXE;
        smithableItemDefinitionArray5[17] = SmithableItemDefinition.ADAMANT_PLATE_BODY;
        smithableItemDefinitionArray5[18] = SmithableItemDefinition.ADAMANT_KITE_SHIELD;
        smithableItemDefinitionArray5[19] = SmithableItemDefinition.ADAMANT_NAILS;
        smithableItemDefinitionArray5[20] = SmithableItemDefinition.ADAMANT_TWO_HANDED_SWORD;
        smithableItemDefinitionArray5[21] = SmithableItemDefinition.ADAMANT_CLAWS;
        ADAMANT_BAR = new AdamantBarDefinition(70, 62.5, 2361, smithableItemDefinitionArray5);
        SmithableItemDefinition[] smithableItemDefinitionArray6 = new SmithableItemDefinition[25];
        smithableItemDefinitionArray6[0] = SmithableItemDefinition.RUNE_DAGGER;
        smithableItemDefinitionArray6[1] = SmithableItemDefinition.RUNE_AXE;
        smithableItemDefinitionArray6[2] = SmithableItemDefinition.RUNE_CHAIN_BODY;
        smithableItemDefinitionArray6[3] = SmithableItemDefinition.RUNE_MEDIUM_HELM;
        smithableItemDefinitionArray6[4] = SmithableItemDefinition.RUNE_DART_TIPS;
        smithableItemDefinitionArray6[5] = SmithableItemDefinition.RUNE_SWORD;
        smithableItemDefinitionArray6[6] = SmithableItemDefinition.RUNE_MACE;
        smithableItemDefinitionArray6[7] = SmithableItemDefinition.RUNE_PLATE_LEGS;
        smithableItemDefinitionArray6[8] = SmithableItemDefinition.RUNE_FULL_HELM;
        smithableItemDefinitionArray6[9] = SmithableItemDefinition.RUNE_ARROWTIPS;
        smithableItemDefinitionArray6[10] = SmithableItemDefinition.RUNE_SCIMITAR;
        smithableItemDefinitionArray6[11] = SmithableItemDefinition.RUNE_WARHAMMER;
        smithableItemDefinitionArray6[12] = SmithableItemDefinition.RUNE_PLATE_SKIRT;
        smithableItemDefinitionArray6[13] = SmithableItemDefinition.RUNE_SQUARE_SHIELD;
        smithableItemDefinitionArray6[14] = SmithableItemDefinition.RUNE_KNIVES;
        smithableItemDefinitionArray6[15] = SmithableItemDefinition.RUNE_LONG_SWORD;
        smithableItemDefinitionArray6[16] = SmithableItemDefinition.RUNE_BATTLE_AXE;
        smithableItemDefinitionArray6[17] = SmithableItemDefinition.RUNE_PLATE_BODY;
        smithableItemDefinitionArray6[18] = SmithableItemDefinition.RUNE_KITE_SHIELD;
        smithableItemDefinitionArray6[19] = SmithableItemDefinition.RUNE_NAILS;
        smithableItemDefinitionArray6[20] = SmithableItemDefinition.RUNE_TWO_HANDED_SWORD;
        smithableItemDefinitionArray6[21] = SmithableItemDefinition.RUNE_CLAWS;
        RUNITE_BAR = new RuniteBarDefinition(85, 75.0, 2363, smithableItemDefinitionArray6);
        VALUES = new SmithingBarDefinition[]{BRONZE_BAR, IRON_BAR, STEEL_BAR, MITHRIL_BAR, ADAMANT_BAR, RUNITE_BAR};
        productNameTextIds = new int[]{1094, 1091, 1098, 1102, 1107, 1085, 1093, 1099, 1103, 1108, 1087, 1083, 1100, 1104, 1106, 1086, 1092, 1101, 1105, 1096, 1088, 8429, 11461, 13358, 1134};
        barRequirementTextIds = new int[]{1125, 1126, 1109, 1127, 1128, 1124, 1129, 1110, 1113, 1130, 1116, 1118, 1111, 1114, 1131, 1089, 1095, 1112, 1115, 1132, 1090, 8428, 11459, 13357, 1135};
        productItemInterfaceIds = new int[]{1119, 1120, 1121, 1122, 1123, 1119, 1120, 1121, 1122, 1123, 1119, 1120, 1121, 1122, 1123, 1119, 1120, 1121, 1122, 1123, 1119, 1120, 1121, 1122, 1123};
        int[] integerValues = new int[25];
        integerValues[5] = 1;
        integerValues[6] = 1;
        integerValues[7] = 1;
        integerValues[8] = 1;
        integerValues[9] = 1;
        integerValues[10] = 2;
        integerValues[11] = 2;
        integerValues[12] = 2;
        integerValues[13] = 2;
        integerValues[14] = 2;
        integerValues[15] = 3;
        integerValues[16] = 3;
        integerValues[17] = 3;
        integerValues[18] = 3;
        integerValues[19] = 3;
        integerValues[20] = 4;
        integerValues[21] = 4;
        integerValues[22] = 4;
        integerValues[23] = 4;
        integerValues[24] = 4;
        productItemSlots = integerValues;
    }

    public static SmithingBarDefinition forBarItemId(int itemId) {
        SmithingBarDefinition[] smithingBarDefinitionArray = VALUES;
        int length = VALUES.length;
        int index = 0;
        while (index < length) {
            SmithingBarDefinition smithingBarDefinition;
            SmithingBarDefinition smithingBarDefinition2 = smithingBarDefinition = smithingBarDefinitionArray[index];
            if (smithingBarDefinition.barItemId == itemId) {
                return smithingBarDefinition;
            }
            ++index;
        }
        return null;
    }

    public final SmithableItemDefinition[] getSmithableItems() {
        return this.smithableItems;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperiencePerBar() {
        return this.experiencePerBar;
    }

    public final int getBarItemId() {
        return this.barItemId;
    }

    public SmithingBarDefinition(int requiredLevel, double experiencePerBar, int barItemId, SmithableItemDefinition[] smithableItemDefinitionArray) {
        this.smithableItems = smithableItemDefinitionArray;
        this.requiredLevel = requiredLevel;
        this.experiencePerBar = experiencePerBar;
        this.barItemId = barItemId;
    }
}

