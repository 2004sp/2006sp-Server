package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FruitTreeDefinition {
    APPLE(5496, 1955, 1, 27, new int[]{5986, 9}, 960, 0.2, 22.0, 8.5, 8, 20, 14, 33, 34, 1199.5, 12, 18),
    BANANA(5497, 1963, 1, 33, new int[]{5386, 4}, 960, 0.2, 28.0, 10.5, 35, 47, 41, 60, 61, 1750.5, 12, 18),
    ORANGE(5498, 2108, 1, 39, new int[]{5406, 3}, 960, 0.2, 35.5, 13.5, 72, 84, 78, 97, 98, 2470.2, 12, 18),
    CURRY(5499, 5970, 1, 42, new int[]{5416, 5}, 960, 0.25, 40.0, 15.0, 99, 111, 105, 124, 125, 2906.9, 12, 18),
    PINEAPPLE(5500, 2114, 1, 51, new int[]{5982, 10}, 960, 0.25, 57.0, 21.5, 136, 148, 142, 161, 162, 4605.7, 12, 18),
    PAPAYA(5501, 5972, 1, 57, new int[]{2114, 10}, 960, 0.25, 72.0, 27.0, 163, 175, 169, 188, 189, 6146.4, 12, 18),
    PALM(5502, 5974, 1, 68, new int[]{5972, 15}, 960, 0.25, 170.5, 41.5, 200, 212, 206, 225, 226, 10150.1, 12, 18);

    private int saplingId;
    private int produceItemId;
    private int requiredLevel;
    private int[] protectionPayment;
    private int totalGrowthTicks;
    private double diseaseChance;
    private double plantingExperience;
    private double harvestExperience;
    private int configStartStage;
    private int configEndStage;
    private int matureConfigStage;
    private int stumpConfigStage;
    private int healthCheckConfigStage;
    private double healthCheckExperience;
    private int diseasedConfigOffset;
    private int deadConfigOffset;
    private static Map definitionsBySaplingId;

    static {
        definitionsBySaplingId = new HashMap();
        FruitTreeDefinition[] fruitTreeDefinitionArray = FruitTreeDefinition.values();
        int length = fruitTreeDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FruitTreeDefinition fruitTreeDefinition = fruitTreeDefinitionArray[index];
            definitionsBySaplingId.put(fruitTreeDefinition.saplingId, fruitTreeDefinition);
            ++index;
        }
    }

    private FruitTreeDefinition(int saplingId, int produceItemId, int value33, int requiredLevel, int[] protectionPayment, int value52, double diseaseChance, double plantingExperience, double harvestExperience, int configStartStage, int configEndStage, int matureConfigStage, int stumpConfigStage, int healthCheckConfigStage, double healthCheckExperience, int value112, int value122) {
        this.saplingId = saplingId;
        this.produceItemId = produceItemId;
        this.requiredLevel = requiredLevel;
        this.protectionPayment = protectionPayment;
        this.totalGrowthTicks = 960;
        this.diseaseChance = diseaseChance;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = configStartStage;
        this.configEndStage = configEndStage;
        this.matureConfigStage = matureConfigStage;
        this.stumpConfigStage = stumpConfigStage;
        this.healthCheckConfigStage = healthCheckConfigStage;
        this.healthCheckExperience = healthCheckExperience;
        this.diseasedConfigOffset = 12;
        this.deadConfigOffset = 18;
    }

    public static FruitTreeDefinition forSaplingId(int value2) {
        return (FruitTreeDefinition)((Object)definitionsBySaplingId.get(value2));
    }

    public final int getProduceItemId() {
        return this.produceItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int[] getProtectionPayment() {
        return this.protectionPayment;
    }

    public final int getTotalGrowthTicks() {
        return this.totalGrowthTicks;
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

    public final int getGrowthStageCount() {
        FruitTreeDefinition fruitTreeDefinition = this;
        FruitTreeDefinition fruitTreeDefinition2 = fruitTreeDefinition;
        fruitTreeDefinition2 = this;
        return fruitTreeDefinition.configEndStage - fruitTreeDefinition2.configStartStage;
    }

    public final int getGrowthCycleTicks() {
        FruitTreeDefinition fruitTreeDefinition = this;
        return fruitTreeDefinition.totalGrowthTicks / this.getGrowthStageCount();
    }

    public final int getMatureConfigStage() {
        return this.matureConfigStage;
    }

    public final int getStumpConfigStage() {
        return this.stumpConfigStage;
    }

    public final int getHealthCheckConfigStage() {
        return this.healthCheckConfigStage;
    }

    public final double getHealthCheckExperience() {
        return this.healthCheckExperience;
    }

    public final int getDiseasedConfigOffset() {
        return this.diseasedConfigOffset;
    }

    public final int getDeadConfigOffset() {
        return this.deadConfigOffset;
    }
}

