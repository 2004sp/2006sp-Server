package com.rs2.model.combat;

public final class ProjectileTiming {
    public static ProjectileTiming STANDARD = new ProjectileTiming(44, 3, 43, 31, 15);
    public static ProjectileTiming KNIFE = new ProjectileTiming(33, 3, 45, 37, 5);
    public static ProjectileTiming DART;
    public static ProjectileTiming MAGIC;
    public static ProjectileTiming JAD_MAGIC;
    public static ProjectileTiming DRAGONFIRE_AND_JAD_RANGED;
    private int startDelay;
    private int speed;
    private int startHeight;
    private int endHeight;
    private int slope;

    static {
        ProjectileTiming projectileTiming = KNIFE.copy();
        projectileTiming.startDelay = 40;
        projectileTiming.speed = 2;
        DART = projectileTiming;
        MAGIC = new ProjectileTiming(50, 6, 45, 30, 15);
        JAD_MAGIC = new ProjectileTiming(50, 25, 100, 26, 15);
        DRAGONFIRE_AND_JAD_RANGED = new ProjectileTiming(50, 50, 60, 26, 15);
        new ProjectileTiming(50, 6, 45, 0, 15);
    }

    private ProjectileTiming(int startDelay, int speed, int startHeight, int endHeight, int slope) {
        this.startDelay = startDelay;
        this.speed = speed;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        this.slope = slope;
    }

    public final int getStartDelay() {
        return this.startDelay;
    }

    public final int getSpeed() {
        return this.speed;
    }

    public final int getStartHeight() {
        return this.startHeight;
    }

    public final int getEndHeight() {
        return this.endHeight;
    }

    public final int getSlope() {
        return this.slope;
    }

    public final ProjectileTiming setStartDelay(int delayTicks) {
        this.startDelay = delayTicks;
        return this;
    }

    public final ProjectileTiming setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public final ProjectileTiming copy() {
        return new ProjectileTiming(this.startDelay, this.speed, this.startHeight, this.endHeight, this.slope);
    }

}
