package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AmmunitionProfile;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.combat.special.MagicShortbowArrowRequirement;
import com.rs2.model.combat.special.MagicShortbowDelayedGraphicTask;
import com.rs2.model.combat.special.MagicShortbowSpecialDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventContainer;

public final class MagicShortbowSpecialAttack
extends WeaponCombatAttack {
    private final WeaponProfile sourceWeaponProfile;
    private final Player player;

    public MagicShortbowSpecialAttack(MagicShortbowSpecialDefinition magicShortbowSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile, WeaponProfile weaponProfile2, Player player2) {
        super(player, entity, weaponProfile);
        this.sourceWeaponProfile = weaponProfile2;
        this.player = player2;
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        Object ammunition = this.getAmmunition();
        AmmunitionProfile ammunitionProfile = this.sourceWeaponProfile.getAmmunitionProfile();
        if (ammunition == null || ammunitionProfile == null) {
            return false;
        }
        ammunition = this.player.isPlayer() ? this.player.getEquipmentManager().getContainer().getItemAt(ammunitionProfile.getEquipmentSlot()) : null;
        if (ammunition == null) {
            return false;
        }
        this.setRequirements(new CombatRequirement[]{new MagicShortbowArrowRequirement(this, ammunitionProfile.getEquipmentSlot(), ((ItemStack)ammunition).getId(), 2, true)});
        double maxHit = this.calculateMaxHit();
        this.setAnimationId(1074);
        this.setAttackSoundId(386);
        this.setAttackerGraphic(null);
        ProjectileTiming projectileTiming = ammunitionProfile.getProjectileTiming();
        ProjectileDefinition firstProjectile = new ProjectileDefinition(249, projectileTiming.copy().setStartDelay(20).setSpeed(3));
        ProjectileDefinition secondProjectile = new ProjectileDefinition(249, projectileTiming.copy().setStartDelay(50).setSpeed(2));
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setAccuracyMultiplier(1.1).setProjectile(firstProjectile).setDelay(1).enableAccuracyCheck(true), new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setAccuracyMultiplier(1.1).setProjectile(secondProjectile).setDelay(0).enableAccuracyCheck(true)});
        return true;
    }

    @Override
    public final int execute(CycleEventContainer cycleEventContainer) {
        World.getTaskScheduler().schedule(new MagicShortbowDelayedGraphicTask(this, 1, this.player));
        return super.execute(cycleEventContainer) + 1;
    }
}

