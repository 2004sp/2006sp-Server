package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.BandosGodswordSpecialDefinition;
import com.rs2.model.player.Player;

public final class BandosGodswordSpecialAttack
extends WeaponCombatAttack {
    public BandosGodswordSpecialAttack(BandosGodswordSpecialDefinition bandosGodswordSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(7073);
        this.setAttackSoundId(3834);
        this.setAttackerGraphic(new GraphicEffect(1223, 0));
        double maxHit = this.calculateMaxHit() * 1.21;
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setSpecialEffectId(11).setAccuracyMultiplier(2.0)});
        return true;
    }
}

