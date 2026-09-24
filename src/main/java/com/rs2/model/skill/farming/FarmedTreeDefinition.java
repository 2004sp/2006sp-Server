package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FarmedTreeDefinition {
    OAK(5370, 6043, 15, new int[]{5968, 1}, 40, new int[]{8, 9, 10, 11, 12}, 0.2, 14.0, 467.3, 13, 14, 1281),
    WILLOW(5371, 6045, 30, new int[]{5386, 1}, 40, new int[]{15, 16, 17, 18, 19, 20, 21}, 0.2, 25.0, 1456.3, 22, 23, 1308),
    MAPLE(5372, 6047, 45, new int[]{5396, 1}, 40, new int[]{24, 25, 26, 27, 28, 29, 30, 31, 32}, 0.25, 45.0, 3403.4, 33, 34, 1307),
    YEW(5373, 6049, 60, new int[]{6016, 10}, 40, new int[]{35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45}, 0.25, 81.0, 7069.9, 46, 47, 1309),
    MAGIC(5374, 6051, 75, new int[]{5976, 25}, 40, new int[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60}, 0.25, 145.5, 13768.3, 61, 62, 1306);

    private int saplingId;
    private int rootItemId;
    private int requiredLevel;
    private int[] protectionPayment;
    private int totalGrowthTicks;
    private int growthCycleTicks;
    private int[] healthyConfigStages;
    private double diseaseChance;
    private double plantingExperience;
    private double healthCheckExperience;
    private int configStartStage;
    private int configEndStage;
    private int checkedTreeConfigStage;
    private int stumpConfigStage;
    private int treeObjectId;
    private static Map definitionsBySaplingId;

    static {
        definitionsBySaplingId = new HashMap();
        FarmedTreeDefinition[] farmedTreeDefinitionArray = FarmedTreeDefinition.values();
        int length = farmedTreeDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FarmedTreeDefinition farmedTreeDefinition = farmedTreeDefinitionArray[index];
            definitionsBySaplingId.put(farmedTreeDefinition.saplingId, farmedTreeDefinition);
            ++index;
        }
    }

    private FarmedTreeDefinition(int saplingId, int rootItemId, int requiredLevel, int[] protectionPayment, int growthCycleTicks, int[] healthyConfigStages, double diseaseChance, double plantingExperience, double healthCheckExperience, int checkedTreeConfigStage, int stumpConfigStage, int treeObjectId) {
        this.saplingId = saplingId;
        this.rootItemId = rootItemId;
        this.requiredLevel = requiredLevel;
        this.protectionPayment = protectionPayment;
        this.growthCycleTicks = growthCycleTicks;
        this.healthyConfigStages = healthyConfigStages;
        this.totalGrowthTicks = growthCycleTicks * (healthyConfigStages.length - 1);
        this.diseaseChance = diseaseChance;
        this.plantingExperience = plantingExperience;
        this.healthCheckExperience = healthCheckExperience;
        this.configStartStage = healthyConfigStages[0];
        this.configEndStage = healthyConfigStages[healthyConfigStages.length - 1];
        this.checkedTreeConfigStage = checkedTreeConfigStage;
        this.stumpConfigStage = stumpConfigStage;
        this.treeObjectId = treeObjectId;
    }

    public static FarmedTreeDefinition forSaplingId(int value2) {
        return (FarmedTreeDefinition)((Object)definitionsBySaplingId.get(value2));
    }

    public final int getRootItemId() {
        return this.rootItemId;
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

    public final double getHealthCheckExperience() {
        return this.healthCheckExperience;
    }

    public final int getConfigStartStage() {
        return this.configStartStage;
    }

    public final int getConfigEndStage() {
        return this.configEndStage;
    }

    public final int getGrowthCycleTicks() {
        return this.growthCycleTicks;
    }

    public final int getGrowthCycleCount() {
        return this.healthyConfigStages.length - 1;
    }

    public final int getConfigStageOffset(int completedCycles) {
        int cycle = Math.max(0, Math.min(completedCycles, this.getGrowthCycleCount()));
        return this.healthyConfigStages[cycle] - this.configStartStage;
    }

    public final int getCheckedTreeConfigStage() {
        return this.checkedTreeConfigStage;
    }

    public final int getStumpConfigStage() {
        return this.stumpConfigStage;
    }

    public final int getTreeObjectId() {
        return this.treeObjectId;
    }
}

