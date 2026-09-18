package com.rs2.model.npc.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class NpcDefinitionAttackStyleCombatDefinition
extends NpcCombatDefinition {
    private final NpcDefinition npcDefinition;

    public NpcDefinitionAttackStyleCombatDefinition(NpcDefinition npcDefinition) {
        this.npcDefinition = npcDefinition;
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        int attackBonusTypeId = NpcDefinition.getAttackBonusTypeId(this.npcDefinition);
        return new CombatAttack[]{BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, attackBonusTypeId == 0 ? AttackBonusType.STAB : (attackBonusTypeId == 1 ? AttackBonusType.SLASH : (attackBonusTypeId == 2 ? AttackBonusType.CRUSH : (attackBonusTypeId == 3 ? AttackBonusType.MAGIC : (attackBonusTypeId == 4 ? AttackBonusType.RANGED : null)))), NpcDefinition.getDefaultMaxHit(this.npcDefinition), NpcDefinition.getDefaultAttackDelay(this.npcDefinition), NpcDefinition.getDefaultAttackAnimationId(this.npcDefinition))};
    }
}

