package com.rs2.model.skill;

import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

public final class SkillLevelRestoreTask
extends TickTask {
    private SkillManager skillManager;
    private final int skillId;

    public SkillLevelRestoreTask(SkillManager skillManager, int value3, int skillId) {
        super(value3);
        this.skillManager = skillManager;
        this.skillId = skillId;
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
        if (SkillManager.getCurrentLevels(this.skillManager)[this.skillId] != SkillManager.getLevelForExperience(this.skillManager.getExperience()[this.skillId])) {
            if (SkillManager.getCurrentLevels(this.skillManager)[this.skillId] > SkillManager.getLevelForExperience(this.skillManager.getExperience()[this.skillId])) {
                int[] currentLevels = SkillManager.getCurrentLevels(this.skillManager);
                int value = this.skillId;
                currentLevels[value] = currentLevels[value] - 1;
            } else {
                int[] currentLevels2 = SkillManager.getCurrentLevels(this.skillManager);
                int value2 = this.skillId;
                currentLevels2[value2] = currentLevels2[value2] + 1;
            }
            this.skillManager.refreshSkill(this.skillId);
        }
        if (!this.skillManager.isLevelModified(this.skillId)) {
            this.stop();
        }
    }
}

