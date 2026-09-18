package com.rs2.model;

import com.rs2.model.Entity;
import com.rs2.model.combat.hit.DamageContribution;
import java.util.Comparator;

public final class DamageContributionComparator
implements Comparator {
    public DamageContributionComparator(Entity entity) {
    }

    public final int compare(Object value3, Object value22) {
        value22 = (DamageContribution)value22;
        value3 = (DamageContribution)value3;
        return ((DamageContribution)value22).getDamage() - ((DamageContribution)value3).getDamage();
    }
}

