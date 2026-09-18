package com.rs2.model.skill;

import com.rs2.model.item.ItemStack;

public enum GatheringToolDefinition {
    DRAGON_AXE(6739, 61, 3.75, new int[]{2846, 2847}, 0, 6743, 6741, 8),
    RUNE_AXE(1359, 41, 3.5, new int[]{867, 868}, 0, 520, 506, 8),
    ADAMANT_AXE(1357, 31, 3.0, new int[]{869, 870}, 0, 518, 504, 8),
    MITHRIL_AXE(1355, 21, 2.5, new int[]{871, 872}, 0, 516, 502, 8),
    BLACK_AXE(1361, 11, 2.25, new int[]{873, 874}, 0, 514, 500, 8),
    STEEL_AXE(1353, 6, 2.0, new int[]{875, 876}, 0, 512, 498, 8),
    IRON_AXE(1349, 1, 1.5, new int[]{877, 878}, 0, 510, 496, 8),
    BRONZE_AXE(1351, 1, 1.0, new int[]{879, 880}, 0, 508, 494, 8),
    RUNE_PICKAXE(1275, 41, 3.0, new int[]{624}, 432, 490, 478, 14),
    ADAMANT_PICKAXE(1271, 31, 4.0, new int[]{628}, 432, 488, 476, 14),
    MITHRIL_PICKAXE(1273, 21, 5.0, new int[]{629}, 432, 486, 474, 14),
    STEEL_PICKAXE(1269, 6, 6.0, new int[]{627}, 432, 484, 472, 14),
    IRON_PICKAXE(1267, 1, 7.0, new int[]{626}, 432, 482, 470, 14),
    BRONZE_PICKAXE(1265, 1, 8.0, new int[]{625}, 432, 480, 468, 14);

    private int toolItemId;
    private int requiredLevel;
    private int[] animationIds;
    private double toolSpeed;
    private int graphicId;
    private int toolHeadItemId;
    private int brokenToolItemId;
    private int skillId;

    private GatheringToolDefinition(int toolItemId, int requiredLevel, double toolSpeed, int[] animationIds, int graphicId, int toolHeadItemId, int brokenToolItemId, int skillId) {
        this.toolItemId = toolItemId;
        this.requiredLevel = requiredLevel;
        this.toolSpeed = toolSpeed;
        this.animationIds = animationIds;
        this.graphicId = graphicId;
        this.toolHeadItemId = toolHeadItemId;
        this.brokenToolItemId = brokenToolItemId;
        this.skillId = skillId;
    }

    public final int getToolItemId() {
        return this.toolItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getToolHeadItemId() {
        return this.toolHeadItemId;
    }

    public final int getGatherAnimationId() {
        return this.animationIds[0];
    }

    public final int getCanoeAnimationId() {
        return this.animationIds[1];
    }

    public final double getToolSpeed() {
        return this.toolSpeed;
    }

    public final int getGraphicId() {
        return this.graphicId;
    }

    public final int getBrokenToolItemId() {
        return this.brokenToolItemId;
    }

    public final int getSkillId() {
        return this.skillId;
    }

    public final int getRepairCostCoins() {
        ItemStack itemStack = new ItemStack(this.brokenToolItemId, 1);
        return itemStack.getDefinition().getValue();
    }
}

