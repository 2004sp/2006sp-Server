package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FlowerDefinition {
    MARIGOLD(5096, 6010, 2, 20, 0.35, 8.5, 47.0, 8, 12),
    ROSEMARY(5097, 6014, 11, 20, 0.32, 12.0, 66.5, 13, 17),
    NASTURTIUM(5098, 6012, 24, 20, 0.3, 19.5, 111.0, 18, 22),
    WOAD(5099, 1793, 25, 20, 0.27, 20.5, 115.5, 23, 27),
    LIMPWURT(5100, 225, 26, 20, 0.215, 8.5, 120.0, 28, 32);

    private int seedId;
    private int produceItemId;
    private int requiredLevel;
    private int totalGrowthTicks;
    private double diseaseChance;
    private double plantingExperience;
    private double harvestExperience;
    private int configStartStage;
    private int configEndStage;
    private static Map definitionsBySeedId;

    static {
        definitionsBySeedId = new HashMap();
        FlowerDefinition[] flowerDefinitionArray = FlowerDefinition.values();
        int length = flowerDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FlowerDefinition flowerDefinition = flowerDefinitionArray[index];
            definitionsBySeedId.put(flowerDefinition.seedId, flowerDefinition);
            ++index;
        }
    }

    private FlowerDefinition(int seedId, int produceItemId, int requiredLevel, int value42, double diseaseChance, double plantingExperience, double harvestExperience, int configStartStage, int configEndStage) {
        this.seedId = seedId;
        this.produceItemId = produceItemId;
        this.requiredLevel = requiredLevel;
        this.totalGrowthTicks = 20;
        this.diseaseChance = diseaseChance;
        this.plantingExperience = plantingExperience;
        this.harvestExperience = harvestExperience;
        this.configStartStage = configStartStage;
        this.configEndStage = configEndStage;
    }

    public static FlowerDefinition forSeedId(int value2) {
        return (FlowerDefinition)((Object)definitionsBySeedId.get(value2));
    }

    public final int getProduceItemId() {
        return this.produceItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
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
        FlowerDefinition flowerDefinition = this;
        FlowerDefinition flowerDefinition2 = flowerDefinition;
        flowerDefinition2 = this;
        return flowerDefinition.configEndStage - flowerDefinition2.configStartStage;
    }

    public final int getGrowthCycleTicks() {
        FlowerDefinition flowerDefinition = this;
        return flowerDefinition.totalGrowthTicks / this.getGrowthStageCount();
    }
}

