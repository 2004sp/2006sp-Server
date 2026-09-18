package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonAxeSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonAxeSpecialAttack
extends WeaponCombatAttack {
    public DragonAxeSpecialAttack(DragonAxeSpecialDefinition dragonAxeSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(2876);
        this.setAttackerGraphic(new GraphicEffect(479, 100));
        double maxHit = this.calculateMaxHit();
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setSpecialEffectId(3).enableAccuracyCheck(true)});
        return true;
    }
}

