package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum HerbDefinition {
    GUAM(5291, 199, 9, 80, 0.211, 11.0, 12.5, 4, 8, 25, 80),
    MARRENTILL(5292, 201, 14, 80, 0.211, 13.5, 15.0, 11, 15, 28, 80),
    TARROMIN(5293, 203, 19, 80, 0.211, 16.0, 18.0, 18, 22, 31, 80),
    HARRALANDER(5294, 205, 26, 80, 0.211, 21.5, 24.0, 25, 29, 36, 80),
    GOUTWEED(6311, 3261, 29, 80, 0.211, 105.0, 45.0, 192, 196, 39, 80),
    RANARR(5295, 207, 32, 80, 0.211, 27.0, 30.5, 32, 36, 39, 80),
    TOADFLAX(5296, 3049, 38, 80, 0.211, 34.0, 38.5, 39, 43, 43, 80),
    IRIT(5297, 209, 44, 80, 0.211, 43.0, 48.5, 46, 50, 46, 80),
    AVANTOE(5298, 211, 50, 80, 0.211, 54.5, 61.5, 53, 57, 50, 80),
    KWUARM(5299, 213, 56, 80, 0.211, 69.0, 78.0, 68, 72, 54, 80),
    SNAPDRAGON(5300, 3051, 62, 80, 0.211, 87.5, 98.5, 75, 79, 57, 80),
    CADANTINE(5301, 215, 67, 80, 0.211, 106.5, 120.0, 82, 86, 60, 80),
    LANTADYME(5302, 2485, 73, 80, 0.211, 134.5, 151.5, 89, 93, 64, 80),
    DWARF_WEED(5303, 217, 79, 80, 0.211, 170.5, 192.0, 96, 100, 67, 80),
    TORSTOL(5304, 219, 85, 80, 0.211, 199.5, 224.5, 103, 107, 71, 80);

    private int seedId;
    private int produceItemId;
    private int requiredLevel;
    private int totalGrowthTicks;
    private double diseaseChance;
    private double plantingExperience;
    private double harvestExperience;
    private int configStartStage;
    private int configEndStage;
    private int harvestChanceLow;
    private int harvestChanceHigh;
    private static Map definitionsBySeedId;

    static {
        definitionsBySeedId = new HashMap();
        HerbDefinition[] herbDefinitionArray = HerbDefinition.values();
        int length = herbDefinitionArray.length;
        int index = 0;
        while (index < length) {
            HerbDefinition herbDefinition = herbDefinitionArray[index];
            definitionsBySeedId.put(herbDefinition.seedId, herbDefinition);
            ++index;
        }
    }

    private HerbDefinition(int seedId, int produceItemId, int requiredLevel, int value42, double value9, double plantingExperience, double harvestExperience, int configStartStage, int configEndStage, int harvestChanceLow, int value82) {
        this.seedId = seedId;
        this.produceItemId = produceItemId;
        this.requiredLevel = requiredLevel;
        this.totalGrowthTicks = 80;
        this.diseaseChance = 0.211;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = configStartStage;
        this.configEndStage = configEndStage;
        this.harvestChanceLow = harvestChanceLow;
        this.harvestChanceHigh = 80;
    }

    public static HerbDefinition forSeedId(int value2) {
        return (HerbDefinition)((Object)definitionsBySeedId.get(value2));
    }

    public final int getProduceItemId() {
        return this.produceItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getDiseaseChance() {
        return this.diseaseChance;
    }

    public final double getPlantingExperience() {
        return this.plantingExperience;
    }

    public final double getHarvestExperience() {
        return this.harvestExperience;
    }

    public final int getConfigStartStage() {
        return this.configStartStage;
    }

    public final int getConfigEndStage() {
        return this.configEndStage;
    }

    public final int getGrowthCycleTicks() {
        HerbDefinition herbDefinition = this;
        HerbDefinition herbDefinition2 = herbDefinition;
        HerbDefinition herbDefinition3 = herbDefinition2 = this;
        herbDefinition3 = herbDefinition2;
        return herbDefinition.totalGrowthTicks / (herbDefinition2.configEndStage - herbDefinition3.configStartStage);
    }

    public final int getHarvestChanceLow() {
        return this.harvestChanceLow;
    }

    public final int getHarvestChanceHigh() {
        return this.harvestChanceHigh;
    }
}

