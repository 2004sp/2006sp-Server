package com.rs2.model.combat.special;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.DragonLongswordSpecialDefinition;
import com.rs2.model.player.Player;

public final class DragonLongswordSpecialAttack
extends WeaponCombatAttack {
    public DragonLongswordSpecialAttack(DragonLongswordSpecialDefinition dragonLongswordSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        this.setAnimationId(1058);
        this.setAttackSoundId(390);
        this.setAttackerGraphic(new GraphicEffect(248, 100));
        double maxHit = this.calculateMaxHit();
        if (!ServerSettings.modernCombatSystemEnabled) {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.25).enableRandomDamage().setAccuracyMultiplier(1.75)});
        } else {
            this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit * 1.25).enableRandomDamage().setAccuracyMultiplier(1.0)});
        }
        return true;
    }
}

