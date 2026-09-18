package com.rs2.model.skill.firemaking;

public enum FiremakingLog {
    NORMAL_LOGS(1511, 1, 40, 2732, 100),
    BLUE_LOGS(7406, 1, 250, 11406, 150),
    GREEN_LOGS(7405, 1, 250, 11405, 150),
    RED_LOGS(7404, 1, 250, 11404, 150),
    ACHEY_LOGS(2862, 1, 40, 2732, 100),
    OAK_LOGS(1521, 15, 60, 2732, 150),
    WILLOW_LOGS(1519, 30, 90, 2732, 200),
    TEAK_LOGS(6333, 35, 105, 2732, 250),
    MAPLE_LOGS(1517, 45, 135, 2732, 300),
    ARCTIC_PINE_LOGS(10810, 45, 135, 2732, 300),
    MAHOGANY_LOGS(6332, 50, 158, 2732, 350),
    YEW_LOGS(1515, 60, 203, 2732, 400),
    MAGIC_LOGS(1513, 75, 304, 2732, 500),
    PYRE_LOGS(3438, 5, 50, 2732, 150),
    OAK_PYRE_LOGS(3440, 20, 70, 2732, 200),
    WILLOW_PYRE_LOGS(3442, 35, 100, 2732, 250),
    MAPLE_PYRE_LOGS(3444, 50, 175, 2732, 350),
    YEW_PYRE_LOGS(3446, 65, 225, 2732, 450),
    MAGIC_PYRE_LOGS(3448, 80, 405, 2732, 550);

    private int logItemId;
    private int requiredLevel;
    private int experience;
    private int fireObjectId;

    private FiremakingLog(int logItemId, int requiredLevel, int experience, int fireObjectId, int value52) {
        this.logItemId = logItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.fireObjectId = fireObjectId;
    }

    public final int getLogItemId() {
        return this.logItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getExperience() {
        return this.experience;
    }

    public final int getFireObjectId() {
        return this.fireObjectId;
    }
}

