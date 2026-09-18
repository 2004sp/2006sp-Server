package com.rs2.model.skill.herblore;

import java.util.HashMap;
import java.util.Map;

public enum CleanHerbDefinition {
    GUAM(199, 249, 1, 2.5),
    MARRENTILL(201, 251, 5, 3.8),
    TARROMIN(203, 253, 11, 5.0),
    HARRALANDER(205, 255, 20, 6.3),
    RANARR(207, 257, 25, 7.5),
    TOADFLAX(3049, 2998, 30, 8.0),
    IRIT(209, 259, 40, 8.8),
    AVANTOE(211, 261, 48, 10.0),
    KWUARM(213, 263, 54, 11.3),
    SNAPDRAGON(3051, 3000, 59, 11.8),
    CADANTINE(215, 265, 65, 12.5),
    LANTADYME(2485, 2481, 67, 13.1),
    DWARF_WEED(217, 267, 70, 13.8),
    TORSTOL(219, 269, 75, 15.0),
    SNAKE_WEED(1525, 1526, 3, 2.5),
    ARDRIGAL(1527, 1528, 3, 2.5),
    SITO_FOIL(1529, 1530, 3, 2.5),
    VOLENCIA_MOSS(1531, 1532, 3, 2.5),
    ROGUES_PURSE(1533, 1534, 3, 2.5);

    private int grimyItemId;
    private int cleanItemId;
    private int requiredLevel;
    private double experience;
    private static Map definitionsByGrimyItemId;

    static {
        definitionsByGrimyItemId = new HashMap();
        CleanHerbDefinition[] cleanHerbDefinitionArray = CleanHerbDefinition.values();
        int length = cleanHerbDefinitionArray.length;
        int index = 0;
        while (index < length) {
            CleanHerbDefinition cleanHerbDefinition = cleanHerbDefinitionArray[index];
            definitionsByGrimyItemId.put(cleanHerbDefinition.grimyItemId, cleanHerbDefinition);
            ++index;
        }
    }

    public static CleanHerbDefinition forGrimyItemId(int itemId) {
        return (CleanHerbDefinition)((Object)definitionsByGrimyItemId.get(itemId));
    }

    private CleanHerbDefinition(int grimyItemId, int cleanItemId, int requiredLevel, double experience) {
        this.grimyItemId = grimyItemId;
        this.cleanItemId = cleanItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getCleanItemId() {
        return this.cleanItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

