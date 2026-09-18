package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonHalberdSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonHalberdSpecialAttack
extends WeaponCombatAttack {
    private final Entity primaryTarget;

    public DragonHalberdSpecialAttack(DragonHalberdSpecialDefinition dragonHalberdSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile, Entity entity2) {
        super(player, entity, weaponProfile);
        this.primaryTarget = entity2;
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(1203);
        this.setAttackerGraphic(new GraphicEffect(282, 100));
        double maxHit = this.calculateMaxHit();
        if (this.primaryTarget.getSize() > 1) {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.1).enableRandomDamage().enableAccuracyCheck(true), new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.1).enableRandomDamage().setAccuracyMultiplier(0.75)});
        } else {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.1).enableRandomDamage().enableAccuracyCheck(true)});
        }
        return true;
    }
}

