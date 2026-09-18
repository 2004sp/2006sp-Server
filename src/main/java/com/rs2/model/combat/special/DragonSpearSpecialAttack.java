package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonSpearSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonSpearSpecialAttack
extends WeaponCombatAttack {
    public DragonSpearSpecialAttack(DragonSpearSpecialDefinition dragonSpearSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(1064);
        this.setAttackSoundId(388);
        this.setAttackerGraphic(new GraphicEffect(253, 100));
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, 0).setSpecialEffectId(5).setAlwaysHits(true)});
        return true;
    }
}

