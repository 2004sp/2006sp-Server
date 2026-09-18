package com.rs2.model.combat.hit;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AmmunitionDefinition;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class HitDefinition {
    private int attackAnimationId;
    private int impactSoundId;
    private int maxDamage;
    private int minimumDamage = -1;
    private int blockAnimationId;
    private HitType hitType;
    private AttackStyleDefinition attackStyle;
    private GraphicEffect graphic;
    private ProjectileDefinition projectile;
    private int delay;
    private int specialEffectId;
    private boolean accuracyCheckEnabled;
    private boolean randomDamageEnabled;
    private boolean alwaysHits;
    private boolean multiTargetSpreadEnabled;
    private boolean blockAnimationEnabled;
    private boolean protectionPrayerReductionDeferred = false;
    private SpellDefinition spell;
    private ArrayList chainedTargets = new ArrayList();
    private Entity chainedSource;
    private AmmunitionDefinition ammunition;
    private List effects = new LinkedList();
    private ItemStack droppedAmmunition;
    private double accuracyMultiplier = 1.0;

    public final HitDefinition setChainedTargets(ArrayList arrayList) {
        this.chainedTargets = arrayList;
        return this;
    }

    public final ArrayList getChainedTargets() {
        return this.chainedTargets;
    }

    public final HitDefinition setChainedSource(Entity entity) {
        this.chainedSource = entity;
        return this;
    }

    public final Entity getChainedSource() {
        return this.chainedSource;
    }

    public final void setAmmunition(AmmunitionDefinition ammunitionDefinition) {
        this.ammunition = ammunitionDefinition;
    }

    public final AmmunitionDefinition getAmmunition() {
        return this.ammunition;
    }

    public HitDefinition(AttackStyleDefinition attackStyleDefinition, HitType hitType, int maxDamage) {
        this.attackStyle = attackStyleDefinition;
        this.hitType = hitType;
        this.maxDamage = maxDamage;
        this.attackAnimationId = -1;
        this.impactSoundId = -1;
        this.graphic = null;
        this.delay = 0;
        this.alwaysHits = false;
        this.multiTargetSpreadEnabled = true;
        this.blockAnimationEnabled = true;
    }

    public HitDefinition(AttackStyleDefinition attackStyleDefinition, HitType hitType, int maxDamage, boolean protectionPrayerReductionDeferred) {
        this.attackStyle = attackStyleDefinition;
        this.hitType = hitType;
        this.maxDamage = maxDamage;
        this.attackAnimationId = -1;
        this.impactSoundId = -1;
        this.graphic = null;
        this.delay = 0;
        this.alwaysHits = false;
        this.multiTargetSpreadEnabled = true;
        this.blockAnimationEnabled = true;
        this.protectionPrayerReductionDeferred = protectionPrayerReductionDeferred;
    }

    public final HitDefinition copy() {
        HitDefinition hitDefinition = new HitDefinition(this.attackStyle, this.hitType, this.maxDamage);
        hitDefinition.attackAnimationId = this.attackAnimationId;
        hitDefinition.graphic = this.graphic;
        hitDefinition.impactSoundId = this.impactSoundId;
        hitDefinition.projectile = this.projectile;
        hitDefinition.delay = this.delay;
        hitDefinition.droppedAmmunition = this.droppedAmmunition;
        hitDefinition.blockAnimationId = this.blockAnimationId;
        hitDefinition.alwaysHits = this.alwaysHits;
        hitDefinition.multiTargetSpreadEnabled = this.multiTargetSpreadEnabled;
        hitDefinition.blockAnimationEnabled = this.blockAnimationEnabled;
        hitDefinition.minimumDamage = this.minimumDamage;
        hitDefinition.protectionPrayerReductionDeferred = this.protectionPrayerReductionDeferred;
        hitDefinition.specialEffectId = this.specialEffectId;
        hitDefinition.spell = this.spell;
        hitDefinition.ammunition = this.ammunition;
        hitDefinition.chainedSource = this.chainedSource;
        hitDefinition.chainedTargets = new ArrayList(this.chainedTargets);
        HitDefinition hitDefinition2 = this;
        if (hitDefinition2.accuracyCheckEnabled) {
            hitDefinition2 = this;
            hitDefinition.setAccuracyMultiplier(hitDefinition2.accuracyMultiplier);
        }
        hitDefinition2 = this;
        if (hitDefinition2.randomDamageEnabled) {
            hitDefinition.enableRandomDamage();
        }
        hitDefinition2 = this;
        if (hitDefinition2.effects != null) {
            hitDefinition2 = this;
            if (hitDefinition2.effects.size() > 0) {
                CombatEffect[] effects = (CombatEffect[])this.effects.toArray(new CombatEffect[this.effects.size()]);
                hitDefinition.addEffects(effects);
            }
        }
        return hitDefinition;
    }

    public HitDefinition(AttackStyleDefinition attackStyleDefinition, HitType hitType, double value2) {
        this(attackStyleDefinition, hitType, (int)Math.floor(value2));
    }

    public final boolean isProtectionPrayerReductionDeferred() {
        return this.protectionPrayerReductionDeferred;
    }

    public final HitDefinition addEffects(CombatEffect[] combatEffectValues) {
        if (combatEffectValues != null) {
            CombatEffect[] combatEffectArray = combatEffectValues;
            int length = combatEffectValues.length;
            int index = 0;
            while (index < length) {
                CombatEffect combatEffect = combatEffectArray[index];
                if (combatEffect != null) {
                    this.effects.add(combatEffect);
                }
                ++index;
            }
        }
        return this;
    }

    public final HitDefinition addEffect(CombatEffect combatEffect) {
        this.effects.add(combatEffect);
        return this;
    }

    public final List getEffects() {
        return this.effects;
    }

    public final HitDefinition clearEffects() {
        this.effects = null;
        return this;
    }

    public final HitDefinition enableRandomDamage() {
        if (this.maxDamage == -1) {
            return this;
        }
        boolean enabled = true;
        HitDefinition hitDefinition = this;
        this.randomDamageEnabled = true;
        return this;
    }

    public final HitDefinition setAccuracyMultiplier(double accuracyMultiplier) {
        if (this.attackStyle == null) {
            return this;
        }
        boolean enabled = true;
        HitDefinition hitDefinition = this;
        this.accuracyCheckEnabled = true;
        this.accuracyCheckEnabled = true;
        double value = accuracyMultiplier;
        HitDefinition hitDefinition2 = this;
        this.accuracyMultiplier = value;
        return this;
    }

    public final HitDefinition setSpecialEffectId(int specialEffectId) {
        this.specialEffectId = specialEffectId;
        return this;
    }

    public final HitDefinition setDroppedAmmunition(ItemStack itemStack) {
        this.droppedAmmunition = itemStack;
        return this;
    }

    public final HitDefinition enableAccuracyCheck() {
        return this.setAccuracyMultiplier(1.0);
    }

    public final HitDefinition setAlwaysHits(boolean alwaysHits) {
        this.alwaysHits = alwaysHits;
        return this;
    }

    public final boolean isAlwaysHit() {
        return this.alwaysHits;
    }

    public final HitDefinition setGraphic(GraphicEffect graphicEffect) {
        this.graphic = graphicEffect;
        return this;
    }

    public final HitDefinition setImpactSoundId(int soundId) {
        this.impactSoundId = soundId;
        return this;
    }

    public final HitDefinition setDelay(int delayTicks) {
        this.delay = delayTicks;
        return this;
    }

    public final HitDefinition setProjectile(ProjectileDefinition projectileDefinition) {
        this.projectile = projectileDefinition;
        return this;
    }

    public final AttackStyleDefinition getAttackStyle() {
        return this.attackStyle;
    }

    public final ProjectileDefinition getProjectile() {
        return this.projectile;
    }

    public final GraphicEffect getGraphic() {
        return this.graphic;
    }

    public final int getImpactSoundId() {
        return this.impactSoundId;
    }

    public final ItemStack getDroppedAmmunition() {
        return this.droppedAmmunition;
    }

    public final int getAttackAnimationId() {
        return this.attackAnimationId;
    }

    public final int getMaxDamage() {
        return this.maxDamage;
    }

    public final int getMinimumDamage() {
        return this.minimumDamage;
    }

    public final HitDefinition setMinimumDamage(int minimumDamage) {
        this.minimumDamage = minimumDamage;
        return this;
    }

    public final HitType getHitType() {
        return this.hitType;
    }

    public final boolean isAccuracyCheckEnabled() {
        return this.accuracyCheckEnabled;
    }

    public final int getSpecialEffectId() {
        return this.specialEffectId;
    }

    public final int calculateDelay(Position position, Position position2) {
        int value = this.delay;
        if (position != null && this.projectile != null) {
            int distance = GameUtil.getDistance(position, position2);
            ProjectileTiming timing = this.projectile.getTiming();
            double startDelay = (double)(timing.getStartDelay() + timing.getSpeed()) + (double)distance * 5.0;
            startDelay = Math.ceil(startDelay * 12.0 / 600.0);
            if (distance > 1) {
                startDelay += 1.0;
            }
            value += (int)startDelay;
        } else {
            ++value;
        }
        return value;
    }

    public final void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public final double getAccuracyMultiplier() {
        return this.accuracyMultiplier;
    }

    public final HitDefinition enableAccuracyCheck(boolean enabled2) {
        this.accuracyCheckEnabled = true;
        return this;
    }

    public final boolean isRandomDamageEnabled() {
        return this.randomDamageEnabled;
    }

    public final HitDefinition scaleMaxDamage(double multiplier) {
        this.maxDamage = (int)((double)this.maxDamage * multiplier);
        return this;
    }

    public final HitDefinition setSpell(SpellDefinition spellDefinition) {
        this.spell = spellDefinition;
        return this;
    }

    public final SpellDefinition getSpell() {
        return this.spell;
    }

    public final HitDefinition setBlockAnimationId(int animationId) {
        this.blockAnimationId = 734;
        return this;
    }

    public final int getBlockAnimationId() {
        return this.blockAnimationId;
    }

    public final HitDefinition setMultiTargetSpreadEnabled(boolean multiTargetSpreadEnabled) {
        this.multiTargetSpreadEnabled = false;
        return this;
    }

    public final boolean isMultiTargetSpreadEnabled() {
        return this.multiTargetSpreadEnabled;
    }

    public final HitDefinition setBlockAnimationEnabled(boolean animationId) {
        this.blockAnimationEnabled = false;
        return this;
    }

    public final boolean isBlockAnimationEnabled() {
        return this.blockAnimationEnabled;
    }

}
