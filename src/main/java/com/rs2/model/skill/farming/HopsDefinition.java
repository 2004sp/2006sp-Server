package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum HopsDefinition {
    BARLEY(5305, 6006, 4, 3, new int[]{6032, 3}, 40, 0.35, 8.5, 9.5, 49, 53, 103, 180),
    HAMMERSTONE(5307, 5994, 4, 4, new int[]{6010, 1}, 40, 0.35, 9.0, 10.0, 4, 8, 104, 180),
    ASGARNIAN(5308, 5996, 4, 8, new int[]{5458, 1}, 50, 0.3, 10.5, 12.0, 11, 16, 108, 180),
    JUTE(5306, 5931, 3, 13, new int[]{6008, 6}, 50, 0.3, 13.0, 14.5, 56, 61, 113, 180),
    YANILLIAN(5309, 5998, 4, 16, new int[]{5968, 1}, 60, 0.25, 14.5, 16.0, 19, 25, 116, 180),
    KRANDORIAN(5310, 6000, 4, 21, new int[]{5478, 1}, 70, 0.25, 17.5, 19.5, 28, 35, 121, 180),
    WILDBLOOD(5311, 6002, 4, 28, new int[]{6012, 1}, 80, 0.2, 23.0, 26.0, 38, 46, 128, 180);

    private int seedId;
    private int produceItemId;
    private int seedAmount;
    private int requiredLevel;
    private int[] protectionPayment;
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
        HopsDefinition[] hopsDefinitionArray = HopsDefinition.values();
        int length = hopsDefinitionArray.length;
        int index = 0;
        while (index < length) {
            HopsDefinition hopsDefinition = hopsDefinitionArray[index];
            definitionsBySeedId.put(hopsDefinition.seedId, hopsDefinition);
            ++index;
        }
    }

    private HopsDefinition(int seedId, int produceItemId, int seedAmount, int requiredLevel, int[] protectionPayment, int totalGrowthTicks, double diseaseChance, double plantingExperience, double harvestExperience, int configStartStage, int configEndStage, int harvestChanceLow, int value92) {
        this.seedId = seedId;
        this.produceItemId = produceItemId;
        this.seedAmount = seedAmount;
        this.requiredLevel = requiredLevel;
        this.protectionPayment = protectionPayment;
        this.totalGrowthTicks = totalGrowthTicks;
        this.diseaseChance = diseaseChance;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = configStartStage;
        this.configEndStage = configEndStage;
        this.harvestChanceLow = harvestChanceLow;
        this.harvestChanceHigh = 180;
    }

    public static HopsDefinition forSeedId(int value2) {
        return (HopsDefinition)((Object)definitionsBySeedId.get(value2));
    }

    public final int getSeedId() {
        return this.seedId;
    }

    public final int getProduceItemId() {
        return this.produceItemId;
    }

    public final int getSeedAmount() {
        return this.seedAmount;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int[] getProtectionPayment() {
        return this.protectionPayment;
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

    public final int getGrowthStageCount() {
        HopsDefinition hopsDefinition = this;
        HopsDefinition hopsDefinition2 = hopsDefinition;
        hopsDefinition2 = this;
        return hopsDefinition.configEndStage - hopsDefinition2.configStartStage;
    }

    public final int getGrowthCycleTicks() {
        HopsDefinition hopsDefinition = this;
        return hopsDefinition.totalGrowthTicks / this.getGrowthStageCount();
    }

    public final int getHarvestChanceLow() {
        return this.harvestChanceLow;
    }

    public final int getHarvestChanceHigh() {
        return this.harvestChanceHigh;
    }
}

