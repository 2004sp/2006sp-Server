package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;

public final class ForcedProjectileCombatAttack
extends BaseCombatAttack {
    private final AttackXpMode xpMode;
    private final CombatType combatType;
    private final ProjectileTiming projectileTiming;
    private final int projectileId;
    private final boolean alwaysHits;
    private final int maxHit;
    private final int hitDelay;
    private final GraphicEffect hitGraphic;
    private final CombatEffect combatEffect;
    private final int animationId;
    private final int attackDelay;
    private final GraphicEffect attackerGraphic;

    public ForcedProjectileCombatAttack(Entity entity, Entity entity2, AttackXpMode attackXpMode, CombatType combatType, ProjectileTiming projectileTiming, int projectileId, boolean alwaysHits, int maxHit, int hitDelay, GraphicEffect graphicEffect, CombatEffect combatEffect, int animationId, int attackDelay, GraphicEffect graphicEffect2) {
        super(entity, entity2);
        this.xpMode = attackXpMode;
        this.combatType = combatType;
        this.projectileTiming = projectileTiming;
        this.projectileId = projectileId;
        this.alwaysHits = alwaysHits;
        this.maxHit = maxHit;
        this.hitDelay = hitDelay;
        this.hitGraphic = graphicEffect;
        this.combatEffect = combatEffect;
        this.animationId = animationId;
        this.attackDelay = attackDelay;
        this.attackerGraphic = graphicEffect2;
    }

    @Override
    public final int getAttackRange() {
        if (this.xpMode == AttackXpMode.DRAGONFIRE) {
            return 1;
        }
        if (this.xpMode == AttackXpMode.ICY_BREATH) {
            return 4;
        }
        if (this.xpMode == AttackXpMode.DRAGONFIRE_FAR) {
            return 8;
        }
        if (this.xpMode == AttackXpMode.KBD_SPECIAL) {
            return 8;
        }
        if (this.combatType == CombatType.MAGIC || this.xpMode == AttackXpMode.MELEE_FAR) {
            return 10;
        }
        if (this.xpMode == AttackXpMode.LONGRANGE) {
            return 10;
        }
        return 8;
    }

    @Override
    public final CombatType getCombatType() {
        return this.combatType;
    }

    @Override
    public final void prepare() {
        ProjectileDefinition projectileDefinition;
        ProjectileDefinition projectileDefinition2 = projectileDefinition = this.projectileTiming != null && this.projectileId != -1 ? new ProjectileDefinition(this.projectileId, this.projectileTiming) : null;
        if (this.alwaysHits) {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(new AttackStyleDefinition(this.combatType, this.xpMode, this.combatType == CombatType.RANGED ? AttackBonusType.RANGED : AttackBonusType.MAGIC), HitType.NORMAL, this.maxHit, true).setProjectile(projectileDefinition).setDelay(this.hitDelay).setGraphic(this.hitGraphic).enableRandomDamage().setAlwaysHits(this.alwaysHits)});
        } else {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(new AttackStyleDefinition(this.combatType, this.xpMode, this.combatType == CombatType.RANGED ? AttackBonusType.RANGED : AttackBonusType.MAGIC), HitType.NORMAL, this.maxHit, true).setProjectile(projectileDefinition).setDelay(this.hitDelay).setGraphic(this.hitGraphic).enableAccuracyCheck().enableRandomDamage().setAlwaysHits(this.alwaysHits)});
        }
        if (this.combatEffect != null) {
            this.addEffect(this.combatEffect);
        }
        this.setAnimationId(this.animationId);
        this.setAttackDelay(this.attackDelay);
        this.setAttackerGraphic(this.attackerGraphic);
    }
}

