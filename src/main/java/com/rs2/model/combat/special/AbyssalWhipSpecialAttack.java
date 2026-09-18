package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.AbyssalWhipSpecialDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class AbyssalWhipSpecialAttack
extends WeaponCombatAttack {
    public AbyssalWhipSpecialAttack(AbyssalWhipSpecialDefinition abyssalWhipSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile) {
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        double maxHit = this.calculateMaxHit();
        if (GameUtil.randomInclusive(1) == 1) {
            maxHit = 0.0;
        }
        this.setAttackSoundId(1081);
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, maxHit).setGraphic(new GraphicEffect(341, 100)).setAlwaysHits(true)});
        return true;
    }
}

