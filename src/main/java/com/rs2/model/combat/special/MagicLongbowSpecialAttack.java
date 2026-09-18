package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AmmunitionProfile;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.combat.special.MagicLongbowArrowRequirement;
import com.rs2.model.combat.special.MagicLongbowSpecialDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class MagicLongbowSpecialAttack
extends WeaponCombatAttack {
    private final WeaponProfile sourceWeaponProfile;
    private final Player player;

    public MagicLongbowSpecialAttack(MagicLongbowSpecialDefinition magicLongbowSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile, WeaponProfile weaponProfile2, Player player2) {
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
        this.setRequirements(new CombatRequirement[]{new MagicLongbowArrowRequirement(this, ammunitionProfile.getEquipmentSlot(), ((ItemStack)ammunition).getId(), 2, true)});
        double maxHit = this.calculateMaxHit();
        this.setAnimationId(426);
        this.setAttackSoundId(391);
        this.setAttackerGraphic(new GraphicEffect(250, 100));
        ammunition = ammunitionProfile.getProjectileTiming();
        ammunition = new ProjectileDefinition(249, ((ProjectileTiming)ammunition).copy());
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setProjectile((ProjectileDefinition)ammunition).setDelay(1).setAccuracyMultiplier(5.0).enableAccuracyCheck(true)});
        return true;
    }
}

