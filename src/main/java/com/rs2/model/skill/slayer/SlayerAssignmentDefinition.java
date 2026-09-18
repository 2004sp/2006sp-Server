package com.rs2.model.skill.slayer;

import com.rs2.model.player.Player;
import com.rs2.model.skill.slayer.SlayerAssignmentRequirement;

public final class SlayerAssignmentDefinition {
    String taskName;
    int minAmount;
    int maxAmount;
    int weight;
    private SlayerAssignmentRequirement[] requirements;

    public SlayerAssignmentDefinition(String taskName, int minAmount, int maxAmount, int weight) {
        this.taskName = taskName;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.weight = weight;
    }

    public SlayerAssignmentDefinition(String taskName, int minAmount, int maxAmount, int weight, SlayerAssignmentRequirement[] slayerAssignmentRequirementArray) {
        this.taskName = taskName;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.weight = weight;
        this.requirements = slayerAssignmentRequirementArray;
    }

    public final boolean isAvailableFor(Player player) {
        if (this.requirements == null) {
            return true;
        }
        SlayerAssignmentRequirement[] slayerAssignmentRequirementArray = this.requirements;
        int length = this.requirements.length;
        int index = 0;
        while (index < length) {
            SlayerAssignmentRequirement slayerAssignmentRequirement;
            SlayerAssignmentRequirement slayerAssignmentRequirement2 = slayerAssignmentRequirement = slayerAssignmentRequirementArray[index];
            if (slayerAssignmentRequirement.requirementType == 0) {
                slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                if (player.getSkillManager().getBaseLevel(18) < slayerAssignmentRequirement2.requiredValue) {
                    return false;
                }
            } else {
                slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                if (slayerAssignmentRequirement2.requirementType == SlayerAssignmentRequirement.COMBAT_LEVEL) {
                    slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                    if (player.getCombatLevel() < slayerAssignmentRequirement2.requiredValue) {
                        return false;
                    }
                } else {
                    slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                    if (slayerAssignmentRequirement2.requirementType == SlayerAssignmentRequirement.QUEST_STATE) {
                        slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                        if (player.getQuestState(slayerAssignmentRequirement2.requiredValue) != 1) {
                            return false;
                        }
                    } else {
                        slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                        if (slayerAssignmentRequirement2.requirementType == SlayerAssignmentRequirement.DEFENCE_LEVEL) {
                            slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                            if (player.getSkillManager().getBaseLevel(1) < slayerAssignmentRequirement2.requiredValue) {
                                return false;
                            }
                        } else {
                            slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                            if (slayerAssignmentRequirement2.requirementType == SlayerAssignmentRequirement.AGILITY_LEVEL) {
                                slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                                if (player.getSkillManager().getBaseLevel(16) < slayerAssignmentRequirement2.requiredValue) {
                                    return false;
                                }
                            } else {
                                slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                                if (slayerAssignmentRequirement2.requirementType == SlayerAssignmentRequirement.FIREMAKING_LEVEL) {
                                    slayerAssignmentRequirement2 = slayerAssignmentRequirement;
                                    if (player.getSkillManager().getBaseLevel(11) < slayerAssignmentRequirement2.requiredValue) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ++index;
        }
        return true;
    }
}

