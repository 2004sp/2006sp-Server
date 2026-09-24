package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum SpecialCropDefinition {
    BELLADONNA(5281, 2398, 1, 63, 80, new int[]{4, 5, 6, 7, 8}, 0.15, 91.0, 512.0, -1, 0.0, 5, 8),
    CACTUS(5280, 6016, 1, 55, 80, new int[]{8, 9, 10, 12, 13, 15, 16, 18}, 0.15, 66.5, 25.0, 31, 374.0, 11, 17),
    MUSHROOM(5282, 6004, 1, 53, 40, new int[]{4, 5, 7, 9, 11, 13, 15}, 0.15, 61.5, 57.7, -1, 0.0, 12, 17);

    private int seedId;
    private int produceItemId;
    private int seedAmount;
    private int requiredLevel;
    private int totalGrowthTicks;
    private int growthCycleTicks;
    private int[] healthyConfigStages;
    private double diseaseChance;
    private double plantingExperience;
    private double harvestExperience;
    private int configStartStage;
    private int configEndStage;
    private int healthCheckConfigStage;
    private double healthCheckExperience;
    private int diseasedConfigOffset;
    private int deadConfigOffset;
    private static Map definitionsBySeedId;

    static {
        definitionsBySeedId = new HashMap();
        SpecialCropDefinition[] specialCropDefinitionArray = SpecialCropDefinition.values();
        int length = specialCropDefinitionArray.length;
        int index = 0;
        while (index < length) {
            SpecialCropDefinition specialCropDefinition = specialCropDefinitionArray[index];
            definitionsBySeedId.put(specialCropDefinition.seedId, specialCropDefinition);
            ++index;
        }
    }

    private SpecialCropDefinition(int seedId, int produceItemId, int value33, int requiredLevel, int growthCycleTicks, int[] healthyConfigStages, double value11, double plantingExperience, double harvestExperience, int healthCheckConfigStage, double healthCheckExperience, int diseasedConfigOffset, int deadConfigOffset) {
        this.seedId = seedId;
        this.produceItemId = produceItemId;
        this.seedAmount = 1;
        this.requiredLevel = requiredLevel;
        this.growthCycleTicks = growthCycleTicks;
        this.healthyConfigStages = healthyConfigStages;
        this.totalGrowthTicks = growthCycleTicks * (healthyConfigStages.length - 1);
        this.diseaseChance = 0.15;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = healthyConfigStages[0];
        this.configEndStage = healthyConfigStages[healthyConfigStages.length - 1];
        this.healthCheckConfigStage = healthCheckConfigStage;
        this.healthCheckExperience = healthCheckExperience;
        this.diseasedConfigOffset = diseasedConfigOffset;
        this.deadConfigOffset = deadConfigOffset;
    }

    public static SpecialCropDefinition forSeedId(int value2) {
        return (SpecialCropDefinition)((Object)definitionsBySeedId.get(value2));
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
        SpecialCropDefinition specialCropDefinition = this;
        SpecialCropDefinition specialCropDefinition2 = specialCropDefinition;
        specialCropDefinition2 = this;
        return specialCropDefinition.configEndStage - specialCropDefinition2.configStartStage;
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
        return this.getGrowthStageCount() + completedCycles - cycleCount;
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

