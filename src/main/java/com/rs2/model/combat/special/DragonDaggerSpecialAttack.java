package com.rs2.model.combat.special;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonDaggerSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonDaggerSpecialAttack
extends WeaponCombatAttack {
    public DragonDaggerSpecialAttack(DragonDaggerSpecialDefinition dragonDaggerSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(1062);
        this.setAttackSoundId(385);
        this.setAttackerGraphic(new GraphicEffect(252, 100));
        double maxHit = this.calculateMaxHit();
        if (!ServerSettings.modernCombatSystemEnabled) {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.15).enableRandomDamage().setAccuracyMultiplier(2.25), new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.15).enableRandomDamage().setAccuracyMultiplier(2.25)});
        } else {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.15).enableRandomDamage().setAccuracyMultiplier(1.15), new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.15).enableRandomDamage().setAccuracyMultiplier(1.15)});
        }
        return true;
    }
}

