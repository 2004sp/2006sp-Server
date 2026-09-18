package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum AllotmentCropDefinition {
    POTATO(5318, 1942, 5096, 3, 1, new int[]{6032, 2}, 40, 0.3, 8.0, 9.5, 6, 10, 101, 180),
    ONION(5319, 1957, 5096, 3, 5, new int[]{5438, 1}, 40, 0.3, 9.5, 10.5, 13, 17, 105, 180),
    CABBAGE(5324, 1965, 5097, 3, 7, new int[]{5458, 1}, 40, 0.25, 10.0, 11.5, 20, 24, 107, 180),
    TOMATO(5322, 1982, 5096, 3, 12, new int[]{5478, 2}, 40, 0.25, 12.5, 14.0, 27, 31, 112, 180),
    SWEETCORN(5320, 5986, 6059, 3, 20, new int[]{5931, 10}, 60, 0.2, 17.0, 19.0, 34, 40, 88, 180),
    STRAWBERRY(5323, 5504, -1, 3, 31, new int[]{5386, 1}, 60, 0.2, 26.0, 29.0, 43, 49, 103, 180),
    WATERMELON(5321, 5982, 5098, 3, 47, new int[]{5970, 10}, 80, 0.2, 48.5, 54.5, 52, 60, 126, 180);

    private int seedId;
    private int produceItemId;
    private int protectionCropId;
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
        AllotmentCropDefinition[] allotmentCropDefinitionArray = AllotmentCropDefinition.values();
        int length = allotmentCropDefinitionArray.length;
        int index = 0;
        while (index < length) {
            AllotmentCropDefinition allotmentCropDefinition = allotmentCropDefinitionArray[index];
            definitionsBySeedId.put(allotmentCropDefinition.seedId, allotmentCropDefinition);
            ++index;
        }
    }

    private AllotmentCropDefinition(int seedId, int produceItemId, int protectionCropId, int value42, int requiredLevel, int[] protectionPayment, int totalGrowthTicks, double diseaseChance, double plantingExperience, double harvestExperience, int configStartStage, int configEndStage, int harvestChanceLow, int value102) {
        this.seedId = seedId;
        this.produceItemId = produceItemId;
        this.protectionCropId = protectionCropId;
        this.seedAmount = 3;
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

    public static AllotmentCropDefinition forSeedId(int value2) {
        return (AllotmentCropDefinition)((Object)definitionsBySeedId.get(value2));
    }

    public final int getSeedId() {
        return this.seedId;
    }

    public final int getProduceItemId() {
        return this.produceItemId;
    }

    public final int getProtectionCropId() {
        return this.protectionCropId;
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
        AllotmentCropDefinition allotmentCropDefinition = this;
        AllotmentCropDefinition allotmentCropDefinition2 = allotmentCropDefinition;
        allotmentCropDefinition2 = this;
        return allotmentCropDefinition.configEndStage - allotmentCropDefinition2.configStartStage;
    }

    public final int getGrowthCycleTicks() {
        AllotmentCropDefinition allotmentCropDefinition = this;
        return allotmentCropDefinition.totalGrowthTicks / this.getGrowthStageCount();
    }

    public final int getHarvestChanceLow() {
        return this.harvestChanceLow;
    }

    public final int getHarvestChanceHigh() {
        return this.harvestChanceHigh;
    }
}

