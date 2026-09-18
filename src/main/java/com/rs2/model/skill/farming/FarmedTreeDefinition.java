package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FarmedTreeDefinition {
    OAK(5370, 6043, 15, new int[]{5968, 1}, 200, 0.2, 14.0, 467.3, 8, 12, 13, 14, 1281),
    WILLOW(5371, 6045, 30, new int[]{5386, 1}, 280, 0.2, 25.0, 1456.3, 15, 21, 22, 23, 1308),
    MAPLE(5372, 6047, 45, new int[]{5396, 1}, 320, 0.25, 45.0, 3403.4, 24, 32, 33, 34, 1307),
    YEW(5373, 6049, 60, new int[]{6016, 10}, 400, 0.25, 81.0, 7069.9, 35, 45, 46, 47, 1309),
    MAGIC(5374, 6051, 75, new int[]{5976, 25}, 480, 0.25, 145.5, 13768.3, 48, 60, 61, 62, 1306);

    private int saplingId;
    private int rootItemId;
    private int requiredLevel;
    private int[] protectionPayment;
    private int totalGrowthTicks;
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

    private FarmedTreeDefinition(int saplingId, int rootItemId, int requiredLevel, int[] protectionPayment, int totalGrowthTicks, double diseaseChance, double plantingExperience, double healthCheckExperience, int configStartStage, int configEndStage, int checkedTreeConfigStage, int stumpConfigStage, int treeObjectId) {
        this.saplingId = saplingId;
        this.rootItemId = rootItemId;
        this.requiredLevel = requiredLevel;
        this.protectionPayment = protectionPayment;
        this.totalGrowthTicks = totalGrowthTicks;
        this.diseaseChance = diseaseChance;
        this.plantingExperience = plantingExperience;
        this.healthCheckExperience = healthCheckExperience;
        this.configStartStage = configStartStage;
        this.configEndStage = configEndStage;
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
        FarmedTreeDefinition farmedTreeDefinition = this;
        FarmedTreeDefinition farmedTreeDefinition2 = farmedTreeDefinition;
        FarmedTreeDefinition farmedTreeDefinition3 = farmedTreeDefinition2 = this;
        farmedTreeDefinition3 = farmedTreeDefinition2;
        return farmedTreeDefinition.totalGrowthTicks / (farmedTreeDefinition2.configEndStage - farmedTreeDefinition3.configStartStage);
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

