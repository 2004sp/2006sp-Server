package com.rs2.model.gameplay.fightcave;

import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcDefinition;
import java.util.Comparator;

public final class FightCaveNpcLevelComparator
implements Comparator {
    public FightCaveNpcLevelComparator() {
    }

    public final int compare(Object value3, Object value22) {
        value22 = (Npc)value22;
        value3 = (Npc)value3;
        value3 = NpcDefinition.forId(((Npc)value3).getNpcId());
        value22 = NpcDefinition.forId(((Npc)value22).getNpcId());
        int combatLevel = ((NpcDefinition)value3).getCombatLevel();
        int combatLevel2 = ((NpcDefinition)value22).getCombatLevel();
        return Integer.compare(combatLevel2, combatLevel);
    }
}

