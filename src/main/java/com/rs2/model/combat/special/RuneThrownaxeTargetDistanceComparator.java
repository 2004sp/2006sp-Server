package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.combat.special.RuneThrownaxeSpecialAttack;
import com.rs2.util.GameUtil;
import java.util.Comparator;

public final class RuneThrownaxeTargetDistanceComparator
implements Comparator {
    private RuneThrownaxeSpecialAttack attack;

    public RuneThrownaxeTargetDistanceComparator(RuneThrownaxeSpecialAttack runeThrownaxeSpecialAttack) {
        this.attack = runeThrownaxeSpecialAttack;
    }

    public final int compare(Object value3, Object value22) {
        Entity entity = (Entity)value22;
        value22 = (Entity)value3;
        value3 = this;
        int distance = GameUtil.getDistance(((RuneThrownaxeTargetDistanceComparator)value3).attack.getTarget().getPosition(), ((Entity)value22).getPosition());
        int distance2 = GameUtil.getDistance(((RuneThrownaxeTargetDistanceComparator)value3).attack.getTarget().getPosition(), entity.getPosition());
        return Long.compare(distance, distance2);
    }
}

