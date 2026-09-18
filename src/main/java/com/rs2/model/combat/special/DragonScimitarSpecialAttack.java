package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonScimitarSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonScimitarSpecialAttack
extends WeaponCombatAttack {
    public DragonScimitarSpecialAttack(DragonScimitarSpecialDefinition dragonScimitarSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(1872);
        this.setAttackSoundId(1197);
        this.setAttackerGraphic(new GraphicEffect(347, 100));
        double maxHit = this.calculateMaxHit();
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setSpecialEffectId(2).setAccuracyMultiplier(1.25)});
        return true;
    }
}

