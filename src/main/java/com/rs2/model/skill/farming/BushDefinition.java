package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum BushDefinition {
    REDBERRY(5101, 1951, 1, 10, new int[]{5478, 4}, 20, new int[]{5, 6, 8, 10, 12, 14}, 0.2, 11.5, 4.5, 58, 64.0),
    CADAVABERRY(5102, 753, 1, 22, new int[]{5968, 3}, 20, new int[]{15, 16, 18, 20, 21, 23, 25}, 0.2, 18.0, 7.0, 59, 102.5),
    DWELLBERRY(5103, 2126, 1, 36, new int[]{5406, 3}, 20, new int[]{26, 27, 29, 30, 32, 33, 35, 37}, 0.2, 31.5, 12.0, 60, 177.5),
    JANGERBERRY(5104, 247, 1, 48, new int[]{5982, 6}, 20, new int[]{38, 39, 41, 42, 44, 45, 47, 48, 50}, 0.2, 50.5, 19.0, 61, 284.5),
    WHITEBERRY(5105, 239, 1, 59, new int[]{6004, 8}, 20, new int[]{51, 52, 54, 55, 57, 58, 60, 61, 63}, 0.2, 78.0, 29.0, 62, 437.5),
    POISON_IVY(5106, 6018, 1, 70, null, 20, new int[]{197, 198, 200, 201, 203, 204, 206, 207, 209}, 0.2, 120.0, 45.0, 63, 674.0);

    private int seedId;
    private int produceItemId;
    private int seedAmount;
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
    private int healthCheckConfigStage;
    private double healthCheckExperience;
    private static Map definitionsBySeedId;

    static {
        definitionsBySeedId = new HashMap();
        BushDefinition[] bushDefinitionArray = BushDefinition.values();
        int length = bushDefinitionArray.length;
        int index = 0;
        while (index < length) {
            BushDefinition bushDefinition = bushDefinitionArray[index];
            definitionsBySeedId.put(bushDefinition.seedId, bushDefinition);
            ++index;
        }
    }

    private BushDefinition(int seedId, int produceItemId, int value33, int requiredLevel, int[] protectionPayment, int growthCycleTicks, int[] healthyConfigStages, double value10, double plantingExperience, double harvestExperience, int healthCheckConfigStage, double healthCheckExperience) {
        this.seedId = seedId;
        this.produceItemId = produceItemId;
        this.seedAmount = 1;
        this.requiredLevel = requiredLevel;
        this.protectionPayment = protectionPayment;
        this.growthCycleTicks = growthCycleTicks;
        this.healthyConfigStages = healthyConfigStages;
        this.totalGrowthTicks = growthCycleTicks * (healthyConfigStages.length - 1);
        this.diseaseChance = 0.2;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = healthyConfigStages[0];
        this.configEndStage = healthyConfigStages[healthyConfigStages.length - 1];
        this.healthCheckConfigStage = healthCheckConfigStage;
        this.healthCheckExperience = healthCheckExperience;
    }

    public static BushDefinition forSeedId(int value2) {
        return (BushDefinition)((Object)definitionsBySeedId.get(value2));
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
        BushDefinition bushDefinition = this;
        BushDefinition bushDefinition2 = bushDefinition;
        bushDefinition2 = this;
        return bushDefinition.configEndStage - bushDefinition2.configStartStage;
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
}

