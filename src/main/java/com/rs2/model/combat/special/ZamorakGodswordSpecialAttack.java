package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.effect.MovementLockEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.ZamorakGodswordSpecialDefinition;
import com.rs2.model.player.Player;

public final class ZamorakGodswordSpecialAttack
extends WeaponCombatAttack {
    public ZamorakGodswordSpecialAttack(ZamorakGodswordSpecialDefinition zamorakGodswordSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(7070);
        this.setAttackSoundId(3846);
        this.setAttackerGraphic(new GraphicEffect(1221, 0));
        double maxHit = this.calculateMaxHit() * 1.1;
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).enableRandomDamage().setGraphic(new GraphicEffect(369, 0)).addEffect(new MovementLockEffect(33)).setAccuracyMultiplier(2.0)});
        return true;
    }
}

