package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AmmunitionDefinition;
import com.rs2.model.combat.AmmunitionProfile;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.combat.special.DarkBowArrowRequirement;
import com.rs2.model.combat.special.DarkBowSpecialDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class DarkBowSpecialAttack
extends WeaponCombatAttack {
    private final WeaponProfile sourceWeaponProfile;
    private final Player player;

    public DarkBowSpecialAttack(DarkBowSpecialDefinition darkBowSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile, WeaponProfile weaponProfile2, Player player2) {
        super(player, entity, weaponProfile);
        this.sourceWeaponProfile = weaponProfile2;
        this.player = player2;
    }

    @Override
    public final boolean prepareSpecialAttack() {
        int value;
        int value2;
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        Object ammunition = this.getAmmunition();
        Object ammunitionProfile = this.sourceWeaponProfile.getAmmunitionProfile();
        if (ammunition == null || ammunitionProfile == null) {
            return false;
        }
        ItemStack itemStack = this.player.isPlayer() ? this.player.getEquipmentManager().getContainer().getItemAt(((AmmunitionProfile)((Object)ammunitionProfile)).getEquipmentSlot()) : null;
        if (itemStack == null) {
            return false;
        }
        this.setRequirements(new CombatRequirement[]{new DarkBowArrowRequirement(this, ((AmmunitionProfile)((Object)ammunitionProfile)).getEquipmentSlot(), itemStack.getId(), 2, true)});
        ammunitionProfile = ((AmmunitionProfile)((Object)ammunitionProfile)).getProjectileTiming();
        double maxHit = this.calculateMaxHit();
        if (ammunition == AmmunitionDefinition.DRAGON_ARROW) {
            value2 = 1100;
            value = 8;
            ammunition = new ProjectileDefinition(1099, ((ProjectileTiming)ammunitionProfile).copy().setStartDelay(40).setSpeed(3));
            ammunitionProfile = new ProjectileDefinition(1099, ((ProjectileTiming)ammunitionProfile).copy().setStartDelay(41).setSpeed(2));
            maxHit *= 1.5;
            if (maxHit >= 48.0) {
                maxHit = 48.0;
            }
        } else {
            value2 = 1103;
            value = 5;
            ammunition = new ProjectileDefinition(1101, ((ProjectileTiming)ammunitionProfile).copy().setStartDelay(40).setSpeed(3));
            ammunitionProfile = new ProjectileDefinition(1101, ((ProjectileTiming)ammunitionProfile).copy().setStartDelay(41).setSpeed(2));
            maxHit *= 1.3;
        }
        GraphicEffect graphicEffect = new GraphicEffect(value2, 96);
        this.setAnimationId(426);
        this.setAttackSoundId(3731);
        this.setAttackerGraphic(new GraphicEffect(this.getAmmunition().getAlternateGraphicId(), 90));
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setAccuracyMultiplier(1.1).setProjectile((ProjectileDefinition)ammunition).enableAccuracyCheck(true).setMinimumDamage(value), new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setAccuracyMultiplier(1.1).setProjectile((ProjectileDefinition)ammunitionProfile).enableAccuracyCheck(true).setMinimumDamage(value).setGraphic(graphicEffect)});
        return true;
    }
}

