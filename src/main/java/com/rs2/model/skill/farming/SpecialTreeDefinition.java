package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum SpecialTreeDefinition {
    // The legacy timer has fewer real cycles than these client config ranges. These
    // tables make the reconstructed visual skips explicit instead of interpolating.
    SPIRIT_TREE(5375, -1, 1, 83, 320, new int[]{8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20}, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12}, 0.15, 199.5, 0.0, 44, 19301.8, 12, 23),
    CALQUAT(5503, 5980, 1, 72, 160, new int[]{4, 5, 7, 9, 11, 12, 14, 16, 18}, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 9}, 0.15, 129.5, 48.5, 34, 12096.0, 14, 20);

    private int saplingId;
    private int produceItemId;
    private int requiredLevel;
    private int totalGrowthTicks;
    private int growthCycleTicks;
    private int[] healthyConfigStages;
    private int[] growthMessageStages;
    private double diseaseChance;
    private double plantingExperience;
    private double harvestExperience;
    private int configStartStage;
    private int configEndStage;
    private int healthCheckConfigStage;
    private double healthCheckExperience;
    private int diseasedConfigOffset;
    private int deadConfigOffset;
    private static Map definitionsBySaplingId;

    static {
        definitionsBySaplingId = new HashMap();
        SpecialTreeDefinition[] specialTreeDefinitionArray = SpecialTreeDefinition.values();
        int length = specialTreeDefinitionArray.length;
        int index = 0;
        while (index < length) {
            SpecialTreeDefinition specialTreeDefinition = specialTreeDefinitionArray[index];
            definitionsBySaplingId.put(specialTreeDefinition.saplingId, specialTreeDefinition);
            ++index;
        }
    }

    private SpecialTreeDefinition(int saplingId, int produceItemId, int value33, int requiredLevel, int growthCycleTicks, int[] healthyConfigStages, int[] growthMessageStages, double value11, double plantingExperience, double harvestExperience, int healthCheckConfigStage, double healthCheckExperience, int diseasedConfigOffset, int deadConfigOffset) {
        this.saplingId = saplingId;
        this.produceItemId = produceItemId;
        this.requiredLevel = requiredLevel;
        this.growthCycleTicks = growthCycleTicks;
        this.healthyConfigStages = healthyConfigStages;
        this.growthMessageStages = growthMessageStages;
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

    public static SpecialTreeDefinition forSaplingId(int value2) {
        return (SpecialTreeDefinition)((Object)definitionsBySaplingId.get(value2));
    }

    public final int getProduceItemId() {
        return this.produceItemId;
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
        SpecialTreeDefinition specialTreeDefinition = this;
        SpecialTreeDefinition specialTreeDefinition2 = specialTreeDefinition;
        specialTreeDefinition2 = this;
        return specialTreeDefinition.configEndStage - specialTreeDefinition2.configStartStage;
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

    public final int getGrowthMessageStage(int completedCycles) {
        int cycle = Math.max(0, Math.min(completedCycles, this.getGrowthCycleCount()));
        return this.growthMessageStages[cycle];
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

