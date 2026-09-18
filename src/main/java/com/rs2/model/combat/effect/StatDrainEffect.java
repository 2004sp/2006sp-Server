package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class StatDrainEffect extends CombatEffect {
    private int skillId;
    private int fixedDrainAmount;
    private double drainFraction;

    public StatDrainEffect(int skillId, double drainFraction) {
        this.skillId = skillId;
        this.drainFraction = drainFraction;
    }

    public StatDrainEffect(int skillId, int fixedDrainAmount) {
        this.skillId = skillId;
        this.fixedDrainAmount = fixedDrainAmount;
    }

    public final int getSkillId() {
        return this.skillId;
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        Entity target = combatAction.getTarget();
        if (target.isPlayer()) {
            Player player = (Player)target;
            if (this.skillId != -1) {
                int drainAmount = this.calculateDrainAmount(this.getBaseLevelForSkill(target));
                int[] currentLevels = player.getSkillManager().getCurrentLevels();
                currentLevels[this.skillId] = currentLevels[this.skillId] - drainAmount;
                if (player.getSkillManager().getCurrentLevels()[this.skillId] < 0) {
                    player.getSkillManager().getCurrentLevels()[this.skillId] = 0;
                }
                player.getSkillManager().refreshSkill(this.skillId);
                return;
            }
            for (int index = 0; index < player.getSkillManager().getCurrentLevels().length - 1; ++index) {
                int drainAmount = this.calculateDrainAmount(StatDrainEffect.getBaseLevel(target, index));
                int[] currentLevels = player.getSkillManager().getCurrentLevels();
                currentLevels[index] = currentLevels[index] - drainAmount;
                if (player.getSkillManager().getCurrentLevels()[index] < 0) {
                    player.getSkillManager().getCurrentLevels()[index] = 0;
                }
            }
            player.getSkillManager().refreshAllSkills();
            return;
        }
        Npc npc = (Npc)target;
        if (this.skillId != -1) {
            int drainAmount = this.calculateDrainAmount(this.getBaseLevelForSkill(target));
            if (this.skillId == 0) {
                int level = npc.getCurrentAttackLevel() - drainAmount;
                if (level < 0) {
                    level = 0;
                }
                npc.setCurrentAttackLevel(level);
                return;
            }
            if (this.skillId == 2) {
                int level = npc.getCurrentStrengthLevel() - drainAmount;
                if (level < 0) {
                    level = 0;
                }
                npc.setCurrentStrengthLevel(level);
                return;
            }
            if (this.skillId == 1) {
                int level = npc.getCurrentDefenceLevel() - drainAmount;
                if (level < 0) {
                    level = 0;
                }
                npc.setCurrentDefenceLevel(level);
                return;
            }
            if (this.skillId == 6) {
                int level = npc.getCurrentMagicLevel() - drainAmount;
                if (level < 0) {
                    level = 0;
                }
                npc.setCurrentMagicLevel(level);
                return;
            }
            if (this.skillId == 4) {
                int level = npc.getCurrentRangedLevel() - drainAmount;
                if (level < 0) {
                    level = 0;
                }
                npc.setCurrentRangedLevel(level);
            }
        }
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity target) {
        return null;
    }

    @Override
    public final boolean equals(Object value2) {
        return false;
    }

    private int getBaseLevelForSkill(Entity entity) {
        return StatDrainEffect.getBaseLevel(entity, this.skillId);
    }

    private static int getBaseLevel(Entity entity, int skillId) {
        if (entity.isPlayer()) {
            return ((Player)entity).getSkillManager().getBaseLevel(skillId);
        }
        Npc npc = (Npc)entity;
        if (skillId == 0) {
            return npc.getBaseAttackLevel();
        }
        if (skillId == 2) {
            return npc.getBaseStrengthLevel();
        }
        if (skillId == 1) {
            return npc.getBaseDefenceLevel();
        }
        if (skillId == 6) {
            return npc.getBaseMagicLevel();
        }
        if (skillId == 4) {
            return npc.getBaseRangedLevel();
        }
        return 0;
    }

    private int calculateDrainAmount(int baseLevel) {
        if (this.fixedDrainAmount > 0) {
            return this.fixedDrainAmount;
        }
        return (int)Math.ceil((double)baseLevel * this.drainFraction);
    }
}
