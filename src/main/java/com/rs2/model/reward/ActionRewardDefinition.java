package com.rs2.model.reward;

public final class ActionRewardDefinition {
    private int quantity;
    private int targetId;

    static {
        ActionRewardDefinition[] actionRewardDefinitionArray = new ActionRewardDefinition[]{new ActionRewardDefinition(10, 1, 100, 317), new ActionRewardDefinition(10, 20, 200, 335), new ActionRewardDefinition(10, 40, 200, 377), new ActionRewardDefinition(14, 1, 100, 436), new ActionRewardDefinition(14, 1, 300, 1436), new ActionRewardDefinition(14, 30, 500, 453), new ActionRewardDefinition(13, 1, 100, 2349), new ActionRewardDefinition(13, 15, 100, 2351), new ActionRewardDefinition(13, 40, 200, 2357), new ActionRewardDefinition(8, 1, 50, 1511), new ActionRewardDefinition(8, 15, 100, 1521), new ActionRewardDefinition(8, 30, 200, 1519), new ActionRewardDefinition(10, 76, 100, 383), new ActionRewardDefinition(14, 55, 100, 447), new ActionRewardDefinition(13, 50, 100, 2359), new ActionRewardDefinition(8, 45, 400, 1517), new ActionRewardDefinition(15, 5, 30, 175), new ActionRewardDefinition(15, 22, 20, 127), new ActionRewardDefinition(15, 26, 20, 3010), new ActionRewardDefinition(15, 38, 10, 139), new ActionRewardDefinition(0, 1, 100, 81), new ActionRewardDefinition(0, 25, 100, 117), new ActionRewardDefinition(0, 60, 50, 941), new ActionRewardDefinition(0, 80, 100, 55), new ActionRewardDefinition(0, 80, 100, 53), new ActionRewardDefinition(0, 80, 50, 54), new ActionRewardDefinition(0, 35, 100, 112), new ActionRewardDefinition(0, 45, 100, 111), new ActionRewardDefinition(0, 70, 100, 110), new ActionRewardDefinition(15, 60, 10, 187), new ActionRewardDefinition(8, 75, 400, 1513), new ActionRewardDefinition(0, 90, 50, 1590), new ActionRewardDefinition(0, 100, 50, 1591), new ActionRewardDefinition(0, 110, 50, 1592)};
    }

    private ActionRewardDefinition(int value5, int value22, int quantity, int targetId) {
        this.quantity = quantity;
        this.targetId = targetId;
    }
}

