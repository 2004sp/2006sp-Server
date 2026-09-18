package com.rs2.model.skill.herblore;

import java.util.HashMap;
import java.util.Map;

public enum PoisonedWeaponDefinition {
    BRONZE_ARROW(882, 883, 5616, 883),
    IRON_ARROW(884, 885, 5617, 885),
    STEEL_ARROW(886, 887, 5618, 887),
    MITHRIL_ARROW(888, 889, 5619, 889),
    ADAMANT_ARROW(890, 891, 5620, 891),
    RUNE_ARROW(892, 893, 5621, 893),
    IRON_KNIFE(863, 871, 5655, 5662),
    BRONZE_KNIFE(864, 870, 5654, 5661),
    STEEL_KNIFE(865, 872, 5656, 5663),
    BLACK_KNIFE(869, 874, 5658, 5665),
    MITHRIL_KNIFE(866, 873, 5657, 5664),
    ADAMANT_KNIFE(867, 875, 5659, 5666),
    RUNE_KNIFE(868, 876, 5660, 5667),
    BRONZE_DART(806, 812, 5628, 5635),
    IRON_DART(807, 813, 5629, 5636),
    STEEL_DART(808, 814, 5630, 5637),
    BLACK_DART(3093, 3094, 5631, 5638),
    MITHRIL_DART(809, 815, 5632, 5639),
    ADAMANT_DART(810, 816, 5633, 5640),
    RUNE_DART(811, 817, 5634, 5641),
    IRON_JAVELIN(825, 831, 5641, 5648),
    BRONZE_JAVELIN(826, 832, 5642, 5649),
    STEEL_JAVELIN(827, 833, 5643, 5650),
    MITHRIL_JAVELIN(828, 834, 5644, 5651),
    ADAMANT_JAVELIN(829, 835, 5645, 5652),
    RUNE_JAVELIN(830, 836, 5646, 5653),
    IRON_DAGGER(1203, 1219, 5668, 5686),
    BRONZE_DAGGER(1205, 1221, 5670, 5688),
    STEEL_DAGGER(1207, 1223, 5672, 5690),
    BLACK_DAGGER(1217, 1233, 5682, 5700),
    MITHRIL_DAGGER(1209, 1225, 5674, 5692),
    ADAMANT_DAGGER(1211, 1227, 5676, 5694),
    RUNE_DAGGER(1213, 1229, 5678, 5696),
    DRAGON_DAGGER(1215, 1231, 5680, 5698),
    BRONZE_SPEAR(1237, 1251, 5704, 5618),
    IRON_SPEAR(1239, 1253, 5706, 5620),
    STEEL_SPEAR(1241, 1255, 5708, 5622),
    BLACK_SPEAR(4580, 4582, 5734, 5636),
    MITHRIL_SPEAR(1243, 1257, 5710, 5624),
    ADAMANT_SPEAR(1245, 1259, 5712, 5626),
    RUNE_SPEAR(1247, 1261, 5714, 5628),
    DRAGON_SPEAR(1249, 1263, 5716, 5630);

    private int unpoisonedItemId;
    private int poisonedItemId;
    private int poisonPlusItemId;
    private int poisonPlusPlusItemId;
    private static Map definitionsByUnpoisonedItemId;

    static {
        definitionsByUnpoisonedItemId = new HashMap();
        PoisonedWeaponDefinition[] poisonedWeaponDefinitionArray = PoisonedWeaponDefinition.values();
        int length = poisonedWeaponDefinitionArray.length;
        int index = 0;
        while (index < length) {
            PoisonedWeaponDefinition poisonedWeaponDefinition = poisonedWeaponDefinitionArray[index];
            definitionsByUnpoisonedItemId.put(poisonedWeaponDefinition.unpoisonedItemId, poisonedWeaponDefinition);
            ++index;
        }
    }

    public static PoisonedWeaponDefinition forUnpoisonedItemId(int itemId) {
        return (PoisonedWeaponDefinition)((Object)definitionsByUnpoisonedItemId.get(itemId));
    }

    private PoisonedWeaponDefinition(int unpoisonedItemId, int poisonedItemId, int poisonPlusItemId, int poisonPlusPlusItemId) {
        this.unpoisonedItemId = unpoisonedItemId;
        this.poisonedItemId = poisonedItemId;
        this.poisonPlusItemId = poisonPlusItemId;
        this.poisonPlusPlusItemId = poisonPlusPlusItemId;
    }

    public final int getPoisonedItemId() {
        return this.poisonedItemId;
    }

    public final int getPoisonPlusItemId() {
        return this.poisonPlusItemId;
    }

    public final int getPoisonPlusPlusItemId() {
        return this.poisonPlusPlusItemId;
    }
}

