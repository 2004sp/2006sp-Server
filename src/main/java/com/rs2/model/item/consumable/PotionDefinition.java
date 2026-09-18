package com.rs2.model.item.consumable;

import com.rs2.model.item.consumable.PotionEffectMode;

public final class PotionDefinition {
    private boolean antipoison;
    private String name;
    private PotionEffectMode effectMode;
    private int[] doseItemIds;
    private int[] skillIds;
    private int[] flatBoosts;
    private double[] percentBoosts;

    public final boolean isAntipoison() {
        return this.antipoison;
    }

    public final String getName() {
        return this.name;
    }

    public final int[] getDoseItemIds() {
        return this.doseItemIds;
    }

    public final int[] getSkillIds() {
        return this.skillIds;
    }

    public final int[] getFlatBoosts() {
        return this.flatBoosts;
    }

    public final double[] getPercentBoosts() {
        return this.percentBoosts;
    }

    public final PotionEffectMode getEffectMode() {
        return this.effectMode;
    }

    static void setEffectMode(PotionDefinition potionDefinition, PotionEffectMode potionEffectMode) {
        potionDefinition.effectMode = potionEffectMode;
    }

    static void setDoseItemIds(PotionDefinition potionDefinition, int[] itemId) {
        potionDefinition.doseItemIds = itemId;
    }

    static void setAntipoison(PotionDefinition potionDefinition, boolean enabled2) {
        potionDefinition.antipoison = enabled2;
    }

    static int[] getMutableDoseItemIds(PotionDefinition potionDefinition) {
        return potionDefinition.doseItemIds;
    }

    static void setName(PotionDefinition potionDefinition, String text2) {
        potionDefinition.name = text2;
    }

    static void setSkillIds(PotionDefinition potionDefinition, int[] skillId) {
        potionDefinition.skillIds = skillId;
    }

    static void setFlatBoosts(PotionDefinition potionDefinition, int[] integerValues2) {
        potionDefinition.flatBoosts = integerValues2;
    }

    static void setPercentBoosts(PotionDefinition potionDefinition, double[] doubleValues) {
        potionDefinition.percentBoosts = doubleValues;
    }

    static int[] getMutableSkillIds(PotionDefinition potionDefinition) {
        return potionDefinition.skillIds;
    }

    static int[] getMutableFlatBoosts(PotionDefinition potionDefinition) {
        return potionDefinition.flatBoosts;
    }

    static double[] getMutablePercentBoosts(PotionDefinition potionDefinition) {
        return potionDefinition.percentBoosts;
    }
}

