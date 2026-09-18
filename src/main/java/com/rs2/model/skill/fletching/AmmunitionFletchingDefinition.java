package com.rs2.model.skill.fletching;

import java.util.HashMap;

public enum AmmunitionFletchingDefinition {
    ITEMS_314_52_PRODUCT_53(314, 52, 53, 1, 0.4),
    ITEMS_39_53_PRODUCT_882(39, 53, 882, 1, 1.3),
    ITEMS_40_53_PRODUCT_884(40, 53, 884, 15, 2.5),
    ITEMS_41_53_PRODUCT_886(41, 53, 886, 30, 5.0),
    ITEMS_42_53_PRODUCT_888(42, 53, 888, 45, 7.5),
    ITEMS_43_53_PRODUCT_890(43, 53, 890, 60, 10.0),
    ITEMS_44_53_PRODUCT_892(44, 53, 892, 75, 12.5),
    ITEMS_819_314_PRODUCT_806(819, 314, 806, 1, 1.8),
    ITEMS_820_314_PRODUCT_807(820, 314, 807, 22, 3.8),
    ITEMS_821_314_PRODUCT_808(821, 314, 808, 37, 7.5),
    ITEMS_822_314_PRODUCT_809(822, 314, 809, 52, 11.2),
    ITEMS_823_314_PRODUCT_810(823, 314, 810, 67, 15.0),
    ITEMS_824_314_PRODUCT_811(824, 314, 811, 81, 18.8),
    ITEMS_4819_53_PRODUCT_4773(4819, 53, 4773, 7, 1.4),
    ITEMS_4820_53_PRODUCT_4778(4820, 53, 4778, 18, 2.6),
    ITEMS_1539_53_PRODUCT_4783(1539, 53, 4783, 33, 5.1),
    ITEMS_4821_53_PRODUCT_4788(4821, 53, 4788, 38, 6.4),
    ITEMS_4822_53_PRODUCT_4793(4822, 53, 4793, 49, 7.5),
    ITEMS_4823_53_PRODUCT_4798(4823, 53, 4798, 62, 10.1),
    ITEMS_4824_53_PRODUCT_4803(4824, 53, 4803, 77, 12.5),
    ITEMS_46_877_PRODUCT_880(46, 877, 880, 41, 3.2),
    ITEMS_45_877_PRODUCT_879(45, 877, 879, 11, 1.6);

    private int componentItemId;
    private int baseItemId;
    private int productItemId;
    private int requiredLevel;
    private double experience;

    static {
        new HashMap();
    }

    public static AmmunitionFletchingDefinition forComponents(int componentId, int value2) {
        AmmunitionFletchingDefinition[] ammunitionFletchingDefinitionArray = AmmunitionFletchingDefinition.values();
        int length = ammunitionFletchingDefinitionArray.length;
        int index = 0;
        while (index < length) {
            AmmunitionFletchingDefinition ammunitionFletchingDefinition = ammunitionFletchingDefinitionArray[index];
            if (ammunitionFletchingDefinition.componentItemId == componentId && ammunitionFletchingDefinition.baseItemId == value2 || ammunitionFletchingDefinition.baseItemId == componentId && ammunitionFletchingDefinition.componentItemId == value2) {
                return ammunitionFletchingDefinition;
            }
            ++index;
        }
        return null;
    }

    private AmmunitionFletchingDefinition(int componentItemId, int baseItemId, int productItemId, int requiredLevel, double experience) {
        this.componentItemId = componentItemId;
        this.baseItemId = baseItemId;
        this.productItemId = productItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getComponentItemId() {
        return this.componentItemId;
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
}

