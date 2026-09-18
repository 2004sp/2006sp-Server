package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum SpecialTreeDefinition {
    SPIRIT_TREE(5375, -1, 1, 83, 3520, 0.15, 199.5, 0.0, 8, 20, 44, 19301.8, 12, 23),
    CALQUAT(5503, 5980, 1, 72, 1280, 0.15, 129.5, 48.5, 4, 18, 34, 12096.0, 14, 20);

    private int saplingId;
    private int produceItemId;
    private int requiredLevel;
    private int totalGrowthTicks;
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

    private SpecialTreeDefinition(int saplingId, int produceItemId, int value33, int requiredLevel, int totalGrowthTicks, double value11, double plantingExperience, double harvestExperience, int configStartStage, int configEndStage, int healthCheckConfigStage, double healthCheckExperience, int diseasedConfigOffset, int deadConfigOffset) {
        this.saplingId = saplingId;
        this.produceItemId = produceItemId;
        this.requiredLevel = requiredLevel;
        this.totalGrowthTicks = totalGrowthTicks;
        this.diseaseChance = 0.15;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = configStartStage;
        this.configEndStage = configEndStage;
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
        SpecialTreeDefinition specialTreeDefinition = this;
        return specialTreeDefinition.totalGrowthTicks / this.getGrowthStageCount();
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

