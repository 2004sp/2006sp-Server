package com.rs2.model.randomevent;

public enum SkillRandomEventNpc {
    ZOMBIE(419),
    SHADE(425),
    TREE_SPIRIT(438),
    ROCK_GOLEM(413),
    RIVER_TROLL(391),
    EVIL_CHICKEN(2463);

    int baseNpcId;

    private SkillRandomEventNpc(int baseNpcId) {
        this.baseNpcId = baseNpcId;
    }

    public final int getBaseNpcId() {
        return this.baseNpcId;
    }
}
