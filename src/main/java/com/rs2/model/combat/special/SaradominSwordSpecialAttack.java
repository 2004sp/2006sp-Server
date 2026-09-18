package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.SaradominSwordSpecialDefinition;
import com.rs2.model.player.Player;

public final class SaradominSwordSpecialAttack
extends WeaponCombatAttack {
    public SaradominSwordSpecialAttack(SaradominSwordSpecialDefinition saradominSwordSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(7072);
        this.setAttackSoundId(3853);
        double maxHit = this.calculateMaxHit() * 1.1;
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setGraphic(new GraphicEffect(1194, 0)).setSpecialEffectId(13).setAccuracyMultiplier(1.0)});
        return true;
    }
}

