package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.ArmadylGodswordSpecialDefinition;
import com.rs2.model.player.Player;

public final class ArmadylGodswordSpecialAttack
extends WeaponCombatAttack {
    public ArmadylGodswordSpecialAttack(ArmadylGodswordSpecialDefinition armadylGodswordSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(7074);
        this.setAttackSoundId(3865);
        this.setAttackerGraphic(new GraphicEffect(1222, 0));
        double maxHit = this.calculateMaxHit() * 1.375;
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setAccuracyMultiplier(2.0)});
        return true;
    }
}

