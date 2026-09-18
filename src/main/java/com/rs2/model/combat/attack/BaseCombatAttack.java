package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.World;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.requirement.CombatCostRequirement;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseCombatAttack extends CombatAttack {
    private CombatRequirement[] requirements;
    private HitDefinition[] hitDefinitions;
    private GraphicEffect attackerGraphic;
    private int animationId = -1;
    private int attackDelay = -1;
    private int attackSoundId = -1;

    public BaseCombatAttack(Entity attacker, Entity target) {
        super(attacker, target);
    }

    @Override
    public int execute(CycleEventContainer container) {
        if (this.requirements != null) {
            for (CombatRequirement requirement : this.requirements) {
                if (requirement instanceof CombatCostRequirement) {
                    ((CombatCostRequirement)requirement).consume(this.getAttacker());
                }
            }
        }
        if (this.getAttacker().isPlayer() && this.getTarget().isPlayer()) {
            Player attacker = (Player)this.getAttacker();
            switch (new WeaponCombatAttack(attacker, this.getTarget(), WeaponProfile.forItem(new ItemStack(attacker.getEquipmentManager().getItemIdAtSlot(3)))).getAttackStyle().getCombatType()) {
                case MELEE: {
                    if (this instanceof MagicCombatAttack || !DuelRule.NO_MELEE.isEnabledFor(attacker) || attacker.getEquipmentManager().getContainer().getItemAt(3) == null) {
                        break;
                    }
                    attacker.packetSender.sendGameMessage("Melee attacks have been disabled during this fight!");
                    attacker.nextActionSequence();
                    return this.attackDelay;
                }
                case RANGED: {
                    if (!DuelRule.NO_RANGED.isEnabledFor(attacker)) {
                        break;
                    }
                    attacker.packetSender.sendGameMessage("Ranged attacks have been disabled during this fight!");
                    attacker.nextActionSequence();
                    return this.attackDelay;
                }
            }
            if (this.animationId != 1818) {
                ((Player)this.getAttacker()).recordPvpAttack((Player)this.getTarget());
            }
        }
        if (this.attackerGraphic != null) {
            this.getAttacker().getUpdateState().setGraphic(this.attackerGraphic.getId(), this.attackerGraphic.getPackedDelay());
        }
        if (this.getAttacker().isPlayer()) {
            Player player = (Player)this.getAttacker();
            if (player.npcTransformationId > 1) {
                this.animationId = new Npc(player.npcTransformationId).getDefinition().getAttackAnimationId();
            }
        }
        if (this.animationId != -1) {
            if (this.getAttacker().isNpc()) {
                Npc npc = (Npc)this.getAttacker();
                if (npc.getNpcId() == 907 || npc.getNpcId() == 908 || npc.getNpcId() == 909 || npc.getNpcId() == 910 || npc.getNpcId() == 911 || npc.getNpcId() == 912 || npc.getNpcId() == 913 || npc.getNpcId() == 914) {
                    this.animationId = npc.getDefinition().getAttackAnimationId();
                }
                if (npc.getNpcId() == 912) {
                    npc.getUpdateState().setForcedText("Feel the wrath of Zamorak.");
                }
                if (npc.getNpcId() == 913) {
                    npc.getUpdateState().setForcedText("Feel the wrath of Saradomin.");
                }
                if (npc.getNpcId() == 914) {
                    npc.getUpdateState().setForcedText("Feel the wrath of Guthix.");
                }
            }
            this.getAttacker().getUpdateState().setAnimation(this.animationId);
            if (this.getAttacker().isPlayer()) {
                Player attacker = (Player)this.getAttacker();
                attacker.packetSender.sendSoundEffect(this.attackSoundId, 1, 20);
            }
            if (this.getTarget().isPlayer()) {
                Player target = (Player)this.getTarget();
                if (this.getAttacker().isNpc()) {
                    Npc npc = (Npc)this.getAttacker();
                    if (this.attackSoundId == -1 && npc.getAttackSoundId() != -1) {
                        this.attackSoundId = npc.getAttackSoundId();
                    }
                }
                target.packetSender.sendSoundEffect(this.attackSoundId, 1, 20);
            }
        }
        if (this.hitDefinitions != null) {
            for (HitDefinition hitDefinition : this.hitDefinitions) {
                if (this.getAttacker().isNpc()) {
                    Npc npc = (Npc)this.getAttacker();
                    if (hitDefinition.getAttackStyle().getCombatType() == CombatType.MELEE && npc.getDefinition().getPoisonDamage() > 0 && npc.getDefinition().getPoisonChance() > 0.0 && GameUtil.rollChance(npc.getDefinition().getPoisonChance())) {
                        hitDefinition.addEffect(new PoisonEffect(npc.getDefinition().getPoisonDamage()));
                    }
                }
                CombatAction combatAction = new CombatAction(this.getAttacker(), this.getTarget(), hitDefinition);
                if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KQ_RANGED) {
                    Player[] players = World.getPlayers();
                    for (Player player : players) {
                        if (player != null && player != this.getAttacker() && player != this.getTarget() && GameUtil.isWithinDistance(this.getAttacker().getPosition(), player.getPosition(), 10) && CombatCycleEvent.validateAttack(this.getAttacker(), player) == AttackValidationResult.VALID) {
                            hitDefinition.setDelay(-1);
                            new CombatAction(this.getAttacker(), player, hitDefinition).queue();
                        }
                    }
                }
                if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KQ_MAGIC) {
                    ArrayList players = new ArrayList();
                    for (Player player : World.getPlayers()) {
                        if (player != null && player != this.getAttacker() && player != this.getTarget() && GameUtil.isWithinDistance(this.getAttacker().getPosition(), player.getPosition(), 6)) {
                            players.add(player);
                        }
                    }
                    Collections.sort(players, new AreaAttackTargetDistanceComparator(this));
                    hitDefinition.setChainedSource(this.getAttacker());
                    hitDefinition.setChainedTargets(players);
                }
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("combat base queue-hit attacker=" + GameplayTrace.describe(this.getAttacker()) + " target=" + GameplayTrace.describe(this.getTarget()) + " style=" + hitDefinition.getAttackStyle().getXpMode() + "/" + hitDefinition.getAttackStyle().getCombatType() + " maxDamage=" + hitDefinition.getMaxDamage() + " minDamage=" + hitDefinition.getMinimumDamage() + " random=" + hitDefinition.isRandomDamageEnabled() + " accuracy=" + hitDefinition.isAccuracyCheckEnabled() + " always=" + hitDefinition.isAlwaysHit());
                }
                combatAction.queue();
            }
        }
        return this.attackDelay;
    }

    @Override
    public CombatAttackState getState() {
        Entity attacker = this.getAttacker();
        Entity target = this.getTarget();
        if (this.requirements != null) {
            for (CombatRequirement requirement : this.requirements) {
                if (!requirement.validate(attacker)) {
                    this.onRequirementFailed();
                    return CombatAttackState.a;
                }
            }
        }
        int range = this.getAttackRange() + (!target.isMoving() || !attacker.isMoving() ? 0 : (target.isRunningMovement() ? 2 : (target.isMoving() ? 1 : 0)));
        if (!EntityTargetMovement.canReachTarget(attacker, target, range)) {
            return CombatAttackState.b;
        }
        return CombatAttackState.c;
    }

    public void onRequirementFailed() {
    }

    public final void setRequirements(CombatRequirement[] requirements) {
        this.requirements = requirements;
    }

    public final void setHitDefinitions(HitDefinition[] hitDefinitions) {
        this.hitDefinitions = hitDefinitions;
    }

    public final HitDefinition[] getHitDefinitions() {
        return this.hitDefinitions;
    }

    public final void setAttackDelay(int attackDelay) {
        this.attackDelay = attackDelay;
    }

    public final void setAttackerGraphic(GraphicEffect attackerGraphic) {
        this.attackerGraphic = attackerGraphic;
    }

    public final void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public final void setAttackSoundId(int attackSoundId) {
        this.attackSoundId = attackSoundId;
    }

    public final BaseCombatAttack addEffect(CombatEffect combatEffect) {
        if (this.hitDefinitions == null) {
            return this;
        }
        for (HitDefinition hitDefinition : this.hitDefinitions) {
            hitDefinition.addEffect(combatEffect);
        }
        return this;
    }

    public static BaseCombatAttack createForcedMeleeAttack(Entity entity, Entity target, AttackXpMode xpMode, AttackBonusType attackBonusType, int maxHit, int attackDelay, int animationId, boolean deferProtectionPrayerReduction) {
        return new ForcedMeleeCombatAttack(entity, target, xpMode, attackBonusType, maxHit, deferProtectionPrayerReduction, animationId, attackDelay);
    }

    public static BaseCombatAttack createMeleeAttack(Entity entity, Entity target, AttackXpMode xpMode, AttackBonusType attackBonusType, int maxHit, int attackDelay, int animationId) {
        return new MeleeCombatAttack(entity, target, xpMode, attackBonusType, maxHit, animationId, attackDelay);
    }

    public static BaseCombatAttack createWeaponProfileMeleeAttack(Entity entity, Entity target, AttackXpMode xpMode, int styleIndex, WeaponProfile weaponProfile) {
        styleIndex = -1;
        for (int index = 0; index < weaponProfile.getAttackAnimations().length; ++index) {
            if (weaponProfile.getInterfaceDefinition().getAttackStyles()[index].getXpMode() == xpMode) {
                styleIndex = index;
                break;
            }
        }
        if (styleIndex == -1) {
            throw new IllegalArgumentException("That weapon does not contain an attack style with the given mode!");
        }
        AttackStyleDefinition attackStyleDefinition = weaponProfile.getInterfaceDefinition().getAttackStyles()[styleIndex];
        int maxHit = entity.isNpc() ? CombatManager.calculateMeleeMaxHit(entity, null) : 0;
        return BaseCombatAttack.createMeleeAttack(entity, target, xpMode, attackStyleDefinition.getAttackBonusType(), maxHit, weaponProfile.getAttackDelay(), weaponProfile.getAttackAnimations()[styleIndex]);
    }

    private static BaseCombatAttack createProjectileAttackInternal(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming, int hitDelay, CombatEffect combatEffect) {
        return new ProjectileCombatAttack(entity, target, xpMode, combatType, projectileTiming, projectileId, maxHit, hitDelay, hitGraphic, combatEffect, animationId, attackDelay, attackerGraphic);
    }

    private static BaseCombatAttack createForcedProjectileAttackInternal(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming, int hitDelay, CombatEffect combatEffect, boolean alwaysHits) {
        return new ForcedProjectileCombatAttack(entity, target, xpMode, combatType, projectileTiming, projectileId, alwaysHits, maxHit, hitDelay, hitGraphic, combatEffect, animationId, attackDelay, attackerGraphic);
    }

    public static BaseCombatAttack createFlaggedProjectileAttack(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming, boolean alwaysHits, boolean deferProtectionPrayerReduction) {
        return new FlaggedProjectileCombatAttack(entity, target, xpMode, combatType, projectileTiming, projectileId, alwaysHits, maxHit, deferProtectionPrayerReduction, 0, hitGraphic, null, animationId, attackDelay, attackerGraphic);
    }

    public static BaseCombatAttack createProjectileAttack(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming) {
        return BaseCombatAttack.createProjectileAttackInternal(entity, target, combatType, xpMode, maxHit, attackDelay, animationId, attackerGraphic, hitGraphic, projectileId, projectileTiming, 0, null);
    }

    public static BaseCombatAttack createKalphiteQueenMagicAttack(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming, boolean alwaysHits) {
        return BaseCombatAttack.createForcedProjectileAttackInternal(entity, target, combatType, xpMode, maxHit, attackDelay, animationId, attackerGraphic, hitGraphic, projectileId, projectileTiming, 0, null, alwaysHits);
    }

    public static BaseCombatAttack createProjectileAttackWithEffect(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming, CombatEffect combatEffect) {
        return BaseCombatAttack.createProjectileAttackInternal(entity, target, combatType, xpMode, maxHit, attackDelay, animationId, attackerGraphic, hitGraphic, projectileId, projectileTiming, 0, combatEffect);
    }

    public static BaseCombatAttack createKalphiteQueenRangedAttackWithEffect(Entity entity, Entity target, CombatType combatType, AttackXpMode xpMode, int maxHit, int attackDelay, int animationId, GraphicEffect attackerGraphic, GraphicEffect hitGraphic, int projectileId, ProjectileTiming projectileTiming, CombatEffect combatEffect, boolean alwaysHits) {
        return BaseCombatAttack.createForcedProjectileAttackInternal(entity, target, combatType, xpMode, maxHit, attackDelay, animationId, attackerGraphic, hitGraphic, projectileId, projectileTiming, 0, combatEffect, alwaysHits);
    }

    public static BaseCombatAttack createMagicAttack(Entity entity, Entity target, SpellDefinition spellDefinition) {
        return new MagicCombatAttack(entity, target, spellDefinition);
    }
}
