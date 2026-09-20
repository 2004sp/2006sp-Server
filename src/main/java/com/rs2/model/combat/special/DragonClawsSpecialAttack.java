package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class DragonClawsSpecialAttack
extends WeaponCombatAttack {
    private static final int SPECIAL_ANIMATION_ID = 10961;
    private static final int SPECIAL_GRAPHIC_ID = 1950;

    public DragonClawsSpecialAttack(DragonClawsSpecialDefinition definition,
                                    Player player, Entity target,
                                    WeaponProfile weaponProfile) {
        super(player, target, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }

        this.setAnimationId(SPECIAL_ANIMATION_ID);
        this.setAttackerGraphic(new GraphicEffect(SPECIAL_GRAPHIC_ID, 100));

        int maxHit = Math.max(1, (int)Math.floor(this.calculateMaxHit()));
        int[] damage = calculateSpecialDamage(maxHit);
        HitDefinition[] hits = new HitDefinition[4];
        for (int index = 0; index < hits.length; ++index) {
            int delay = index < 2 ? 0 : 1;
            hits[index] = new HitDefinition(
                    this.getAttackStyle(), HitType.NORMAL, damage[index])
                    .setAlwaysHits(true)
                    .setDelay(delay);
        }
        this.setHitDefinitions(hits);
        return true;
    }

    private int[] calculateSpecialDamage(int maxHit) {
        int[] damage = new int[4];

        if (rollNormalAccuracy()) {
            int firstMinimum = Math.max(1, maxHit / 2);
            int firstMaximum = Math.max(firstMinimum, maxHit - 1);
            damage[0] = GameUtil.randomBetweenInclusive(firstMinimum, firstMaximum);
            damage[1] = damage[0] / 2;
            damage[2] = damage[1] / 2;
            damage[3] = damage[1] - damage[2];
            return damage;
        }

        if (rollNormalAccuracy()) {
            int secondMinimum = Math.max(1, (maxHit * 3) / 8);
            int secondMaximum = Math.max(secondMinimum, (maxHit * 7) / 8);
            damage[1] = GameUtil.randomBetweenInclusive(secondMinimum, secondMaximum);
            damage[2] = damage[1] / 2;
            damage[3] = damage[1] - damage[2];
            return damage;
        }

        if (rollNormalAccuracy()) {
            int thirdMinimum = Math.max(1, maxHit / 4);
            int thirdMaximum = Math.max(thirdMinimum, (maxHit * 3) / 4);
            damage[2] = GameUtil.randomBetweenInclusive(thirdMinimum, thirdMaximum);
            damage[3] = damage[2] + GameUtil.randomInt(2);
            return damage;
        }

        if (rollNormalAccuracy()) {
            int fourthMinimum = Math.max(1, maxHit / 4);
            int fourthMaximum = Math.max(fourthMinimum, (maxHit * 5) / 4);
            damage[3] = GameUtil.randomBetweenInclusive(fourthMinimum, fourthMaximum);
            return damage;
        }

        if (GameUtil.randomInt(3) != 0) {
            int pattern = GameUtil.randomInt(4);
            if (pattern == 0) {
                damage[0] = 1;
                damage[1] = 1;
            } else if (pattern == 1) {
                damage[2] = 1;
                damage[3] = 1;
            } else if (pattern == 2) {
                damage[0] = 1;
                damage[2] = 1;
            } else {
                damage[1] = 1;
                damage[3] = 1;
            }
        }
        return damage;
    }

    private boolean rollNormalAccuracy() {
        HitDefinition accuracyProbe = new HitDefinition(
                this.getAttackStyle(), HitType.NORMAL, 0)
                .enableAccuracyCheck();
        double attackRoll = CombatManager.calculateAttackRoll(
                this.getAttacker(), accuracyProbe);
        double defenceRoll = CombatManager.calculateDefenceRoll(
                this.getTarget(), accuracyProbe);
        return CombatManager.rollAccuracy(
                CombatManager.calculateHitChance(attackRoll, defenceRoll));
    }
}
