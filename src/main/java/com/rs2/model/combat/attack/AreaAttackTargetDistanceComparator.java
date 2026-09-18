package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.util.GameUtil;
import java.util.Comparator;

public final class AreaAttackTargetDistanceComparator
implements Comparator {
    private BaseCombatAttack attack;

    public AreaAttackTargetDistanceComparator(BaseCombatAttack baseCombatAttack) {
        this.attack = baseCombatAttack;
    }

    public final int compare(Object value3, Object value22) {
        Entity entity = (Entity)value22;
        value22 = (Entity)value3;
        value3 = this;
        int distance = GameUtil.getDistance(((AreaAttackTargetDistanceComparator)value3).attack.getTarget().getPosition(), ((Entity)value22).getPosition());
        int distance2 = GameUtil.getDistance(((AreaAttackTargetDistanceComparator)value3).attack.getTarget().getPosition(), entity.getPosition());
        return Long.compare(distance, distance2);
    }
}

