package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FruitTreeDefinition {
    APPLE(5496, 1955, 1, 27, new int[]{5986, 9}, 160, new int[]{8, 9, 10, 11, 12, 13, 14}, 0.2, 22.0, 8.5, 20, 33, 34, 1199.5, 12, 18),
    BANANA(5497, 1963, 1, 33, new int[]{5386, 4}, 160, new int[]{35, 36, 37, 38, 39, 40, 41}, 0.2, 28.0, 10.5, 47, 60, 61, 1750.5, 12, 18),
    ORANGE(5498, 2108, 1, 39, new int[]{5406, 3}, 160, new int[]{72, 73, 74, 75, 76, 77, 78}, 0.2, 35.5, 13.5, 84, 97, 98, 2470.2, 12, 18),
    CURRY(5499, 5970, 1, 42, new int[]{5416, 5}, 160, new int[]{99, 100, 101, 102, 103, 104, 105}, 0.25, 40.0, 15.0, 111, 124, 125, 2906.9, 12, 18),
    PINEAPPLE(5500, 2114, 1, 51, new int[]{5982, 10}, 160, new int[]{136, 137, 138, 139, 140, 141, 142}, 0.25, 57.0, 21.5, 148, 161, 162, 4605.7, 12, 18),
    PAPAYA(5501, 5972, 1, 57, new int[]{2114, 10}, 160, new int[]{163, 164, 165, 166, 167, 168, 169}, 0.25, 72.0, 27.0, 175, 188, 189, 6146.4, 12, 18),
    PALM(5502, 5974, 1, 68, new int[]{5972, 15}, 160, new int[]{200, 201, 202, 203, 204, 205, 206}, 0.25, 170.5, 41.5, 212, 225, 226, 10150.1, 12, 18);

    private int saplingId;
    private int produceItemId;
    private int requiredLevel;
    private int[] protectionPayment;
    private int totalGrowthTicks;
    private int growthCycleTicks;
    private int[] healthyConfigStages;
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

    private FruitTreeDefinition(int saplingId, int produceItemId, int value33, int requiredLevel, int[] protectionPayment, int growthCycleTicks, int[] healthyConfigStages, double diseaseChance, double plantingExperience, double harvestExperience, int configEndStage, int stumpConfigStage, int healthCheckConfigStage, double healthCheckExperience, int value112, int value122) {
        this.saplingId = saplingId;
        this.produceItemId = produceItemId;
        this.requiredLevel = requiredLevel;
        this.protectionPayment = protectionPayment;
        this.growthCycleTicks = growthCycleTicks;
        this.healthyConfigStages = healthyConfigStages;
        this.totalGrowthTicks = growthCycleTicks * (healthyConfigStages.length - 1);
        this.diseaseChance = diseaseChance;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = healthyConfigStages[0];
        this.configEndStage = configEndStage;
        this.matureConfigStage = healthyConfigStages[healthyConfigStages.length - 1];
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
        return this.growthCycleTicks;
    }

    public final int getGrowthCycleCount() {
        return this.healthyConfigStages.length - 1;
    }

    public final int getConfigStageOffset(int completedCycles) {
        int cycleCount = this.getGrowthCycleCount();
        if (completedCycles <= cycleCount) {
            return this.healthyConfigStages[Math.max(0, completedCycles)] - this.configStartStage;
        }
        return this.matureConfigStage - this.configStartStage + completedCycles - cycleCount;
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

