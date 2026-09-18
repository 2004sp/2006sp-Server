package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonMaceSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonMaceSpecialAttack
extends WeaponCombatAttack {
    public DragonMaceSpecialAttack(DragonMaceSpecialDefinition dragonMaceSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(1060);
        this.setAttackSoundId(387);
        this.setAttackerGraphic(new GraphicEffect(251, 100));
        double maxHit = this.calculateMaxHit();
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.5).enableRandomDamage().setAccuracyMultiplier(1.25)});
        return true;
    }
}

