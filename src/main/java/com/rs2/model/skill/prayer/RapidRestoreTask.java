package com.rs2.model.skill.prayer;

import com.rs2.model.skill.prayer.PrayerManager;
import com.rs2.model.task.TickTask;

public final class RapidRestoreTask
extends TickTask {
    private PrayerManager prayerManager;

    public RapidRestoreTask(PrayerManager prayerManager, int value2) {
        super(100);
        this.prayerManager = prayerManager;
    }

    @Override
    public final void execute() {
        if (PrayerManager.getPlayer(this.prayerManager) == null) {
            this.stop();
            return;
        }
        if (!PrayerManager.getPlayer((PrayerManager)this.prayerManager).loginInitializationComplete || PrayerManager.getPlayer(this.prayerManager).isDead()) {
            this.stop();
            return;
        }
        PrayerManager.getPlayer(this.prayerManager).getSkillManager().restoreNonPrayerLevels();
        if (!this.prayerManager.isRapidRestoreActive()) {
            this.stop();
        }
    }
}

