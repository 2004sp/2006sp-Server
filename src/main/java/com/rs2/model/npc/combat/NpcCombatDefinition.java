package com.rs2.model.npc.combat;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.npc.combat.DefaultNpcCombatDefinition;
import java.util.HashMap;
import java.util.Map;

public abstract class NpcCombatDefinition {
    private static final Map definitionsByNpcId = new HashMap();
    private int deathDelayTicks = 6;
    private int respawnDelayTicks = 30;
    private boolean attackBonusesSet;
    private boolean defenceBonusesSet;
    private Map combatBonuses = new HashMap();

    public abstract CombatAttack[] createAttacks(Entity attacker, Entity target);

    public NpcCombatDefinition() {
        AttackBonusType[] attackBonusTypeArray = AttackBonusType.values();
        int length = attackBonusTypeArray.length;
        int index = 0;
        while (index < length) {
            AttackBonusType attackBonusType = attackBonusTypeArray[index];
            this.combatBonuses.put(attackBonusType.getIndex(), 0);
            this.combatBonuses.put(attackBonusType.getIndex() + AttackBonusType.values().length, 0);
            ++index;
        }
    }

    public final NpcCombatDefinition addAttackBonuses(int value6, int value22, int value32, int value42, int value52) {
        this.combatBonuses.put(0, (Integer)this.combatBonuses.get(0) + value6);
        this.combatBonuses.put(1, (Integer)this.combatBonuses.get(1) + value22);
        this.combatBonuses.put(2, (Integer)this.combatBonuses.get(2) + value32);
        this.combatBonuses.put(3, (Integer)this.combatBonuses.get(3) + value42);
        this.combatBonuses.put(4, (Integer)this.combatBonuses.get(4) + value52);
        value6 = 1;
        NpcCombatDefinition npcCombatDefinition = this;
        this.attackBonusesSet = true;
        return this;
    }

    public final NpcCombatDefinition addDefenceBonuses(int value6, int value22, int value32, int value42, int value52) {
        this.combatBonuses.put(5, (Integer)this.combatBonuses.get(5) + value6);
        this.combatBonuses.put(6, (Integer)this.combatBonuses.get(6) + value22);
        this.combatBonuses.put(7, (Integer)this.combatBonuses.get(7) + value32);
        this.combatBonuses.put(8, (Integer)this.combatBonuses.get(8) + value42);
        this.combatBonuses.put(9, (Integer)this.combatBonuses.get(9) + value52);
        value6 = 1;
        NpcCombatDefinition npcCombatDefinition = this;
        this.defenceBonusesSet = true;
        return this;
    }

    public final NpcCombatDefinition setRespawnDelayTicks(int delayTicks) {
        this.respawnDelayTicks = delayTicks > 0 ? delayTicks : 30;
        return this;
    }

    public final NpcCombatDefinition setRespawnDelaySeconds(int delayTicks) {
        return this.setRespawnDelayTicks((int)Math.ceil((double)delayTicks * 1000.0 / 600.0));
    }

    public final int getDeathDelayTicks() {
        return this.deathDelayTicks;
    }

    public final int getRespawnDelayTicks() {
        return this.respawnDelayTicks;
    }

    public final Map getCombatBonuses() {
        return this.combatBonuses;
    }

    public static void register(int[] integerValues3, NpcCombatDefinition npcCombatDefinition) {
        int[] integerValues2 = integerValues3;
        int length = integerValues3.length;
        int index = 0;
        while (index < length) {
            int value = integerValues2[index];
            NpcDefinition npcDefinition = NpcDefinition.forId(value);
            if (npcDefinition != null && npcDefinition.respawnDelaySeconds > 0) {
                double value2;
                double combatLevel = npcDefinition.getCombatLevel();
                if (combatLevel <= 0.0) {
                    npcCombatDefinition.respawnDelayTicks = npcDefinition.respawnDelaySeconds;
                } else {
                    double value3;
                    if (combatLevel > 443.0) {
                        combatLevel = 443.0;
                    }
                    if (ServerSettings.modernCombatSystemEnabled) {
                        value3 = npcDefinition.getRespawnDelayTicks();
                    } else {
                        double value4 = Math.pow(combatLevel, 2.0);
                        double value5 = value4 * -4.0E-4;
                        double value6 = combatLevel * 0.3547;
                        value3 = value5 + value6 + 24.646;
                    }
                    npcCombatDefinition.respawnDelayTicks = (int)(value3 *= ServerSettings.npcRespawnDelayMultiplier);
                }
            }
            definitionsByNpcId.put(value, npcCombatDefinition);
            ++index;
        }
    }

    public static boolean isRegistered(int value2) {
        NpcCombatDefinition npcCombatDefinition = (NpcCombatDefinition)definitionsByNpcId.get(value2);
        return npcCombatDefinition != null;
    }

    public static NpcCombatDefinition forNpcId(int npcId) {
        NpcCombatDefinition npcCombatDefinition = (NpcCombatDefinition)definitionsByNpcId.get(npcId);
        if (npcCombatDefinition == null) {
            npcCombatDefinition = new DefaultNpcCombatDefinition();
            definitionsByNpcId.put(npcId, npcCombatDefinition);
        }
        return npcCombatDefinition;
    }

    public final boolean hasAttackBonuses() {
        return this.attackBonusesSet;
    }

    public final boolean hasDefenceBonuses() {
        return this.defenceBonusesSet;
    }
}
