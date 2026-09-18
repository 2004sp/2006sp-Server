package com.rs2.model.skill;

import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

public final class SpecialEnergyRestoreTask
extends TickTask {
    private SkillManager skillManager;

    public SpecialEnergyRestoreTask(SkillManager skillManager, int value2) {
        super(50);
        this.skillManager = skillManager;
    }

    @Override
    public final void execute() {
        if (SkillManager.getPlayer(this.skillManager) == null) {
            this.stop();
            return;
        }
        if (!SkillManager.getPlayer((SkillManager)this.skillManager).loginInitializationComplete || SkillManager.getPlayer(this.skillManager).isDead()) {
            this.stop();
            return;
        }
        if (SkillManager.getPlayer(this.skillManager).getSpecialEnergy() < 100) {
            SkillManager.getPlayer(this.skillManager).setSpecialEnergy(SkillManager.getPlayer(this.skillManager).getSpecialEnergy() + 10);
            SkillManager.getPlayer(this.skillManager).refreshSpecialAttackWidgets();
        }
        if (!this.skillManager.isSpecialEnergyBelowMaximum()) {
            this.stop();
        }
    }
}

