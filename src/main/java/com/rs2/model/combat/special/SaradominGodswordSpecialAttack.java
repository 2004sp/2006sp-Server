package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.SaradominGodswordSpecialDefinition;
import com.rs2.model.player.Player;

public final class SaradominGodswordSpecialAttack
extends WeaponCombatAttack {
    public SaradominGodswordSpecialAttack(SaradominGodswordSpecialDefinition saradominGodswordSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(7071);
        this.setAttackSoundId(3857);
        this.setAttackerGraphic(new GraphicEffect(1220, 0));
        double maxHit = this.calculateMaxHit() * 1.1;
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setSpecialEffectId(12).setAccuracyMultiplier(2.0)});
        return true;
    }
}

