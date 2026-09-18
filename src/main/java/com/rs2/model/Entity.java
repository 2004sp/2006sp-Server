package com.rs2.model;

import com.rs2.ServerSettings;
import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.model.DamageContributionComparator;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.MovementQueue;
import com.rs2.model.MovementStep;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.area.MultiwayAreaDefinition;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatTargetDelayTimer;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.CombatAttackProvider;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.combat.effect.MovementLockEffect;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.effect.StatDrainEffect;
import com.rs2.model.combat.hit.DamageContribution;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.npc.Npc;
import com.rs2.model.path.DirectPathStrategy;
import com.rs2.model.path.PathResult;
import com.rs2.model.path.PathStep;
import com.rs2.model.player.Player;
import com.rs2.model.skill.smithing.SmithingHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.DelayTimer;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import com.rs2.util.path.WalkingCollisionMap;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Entity {
    private int index = -1;
    private int encodedIndex;
    private Entity interactionTarget;
    private Entity tradePartner;
    private Entity combatTarget;
    private Entity movementTarget;
    private Position deathPosition;
    private int referenceId;
    private boolean dead;
    private boolean scriptedMovementEnabled = false;
    private boolean targetMovementDisabled = false;
    private boolean autoRetaliateDisabled = false;
    private int combatTransformNpcId;
    private int attackRange = 1;
    private int actionSequence;
    private int interruptibleActionCheckpoint;
    private int interruptibleActionCounter;
    private double poisonDamage;
    private CombatTargetDelayTimer recentCombatTimer = new CombatTargetDelayTimer(this, 0);
    private CombatTargetDelayTimer singleCombatTimer = new CombatTargetDelayTimer(this, 0);
    private DelayTimer attackDelayTimer = new DelayTimer(0);
    private CombatAttackProvider combatAttackProvider = CombatAttackProvider.DEFAULT;
    private CycleEvent activeCycleEvent;
    private EntityTargetMovement targetMovement = new EntityTargetMovement(this);
    private DelayTimer movementDelayTimer = new DelayTimer(0);
    private DelayTimer teleblockTimer = new DelayTimer(0);
    private DelayTimer movementLockTimer = new DelayTimer(0);
    private DelayTimer stunTimer = new DelayTimer(0);
    private DelayTimer poisonImmunityTimer = new DelayTimer(0);
    private DelayTimer movementLockImmunityTimer = new DelayTimer(0);
    private DelayTimer antifireTimer = new DelayTimer(0);
    private List<CombatEffectTask> combatEffectTasks;
    private DelayTimer chargeCooldownTimer;
    private DelayTimer chargeSpellTimer;
    private MovementQueue movementQueue;
    private Queue<DamageContribution> damageContributions;
    private int size;
    private int walkDirection;
    private int runDirection;
    private Map attributes;
    private Position position;
    private EntityUpdateState updateState;
    private static Polygon wildernessDitchSafeZone = null;

    public Entity() {
        new ArrayList();
        this.combatEffectTasks = new LinkedList<CombatEffectTask>();
        this.chargeCooldownTimer = new DelayTimer(0);
        this.chargeSpellTimer = new DelayTimer(0);
        this.movementQueue = new MovementQueue(this);
        this.damageContributions = new PriorityQueue<DamageContribution>(1, new DamageContributionComparator(this));
        this.walkDirection = -1;
        this.runDirection = -1;
        this.attributes = new HashMap();
        this.updateState = new EntityUpdateState(this);
    }

    public abstract void dropDeathItems(Entity killer);

    public abstract void heal(int amount);

    public final void applyDirectHit(int value3, HitType hitType) {
        Object value2 = new HitDefinition(null, hitType, value3).setDelay(-1).setAlwaysHits(true).setBlockAnimationEnabled(false);
        value2 = new CombatAction(null, this, (HitDefinition)value2);
        ((CombatAction)value2).queue();
    }

    public final void pruneExpiredDamageContributions() {
        Iterator iterator = this.damageContributions.iterator();
        while (iterator.hasNext()) {
            if (!((DamageContribution)iterator.next()).isExpired()) continue;
            iterator.remove();
        }
    }

    public final void setWalkDirection(int direction) {
        this.walkDirection = direction;
    }

    public final int getWalkDirection() {
        return this.walkDirection;
    }

    public final void setRunDirection(int direction) {
        this.runDirection = direction;
    }

    public final int getRunDirection() {
        return this.runDirection;
    }

    public final boolean isMoving() {
        Queue steps = this.movementQueue.getSteps();
        return !steps.isEmpty() && ((MovementStep)steps.peek()).getDirection() != -1;
    }

    public final boolean isRunningMovement() {
        isRunningMovementControlExit1: {
            isRunningMovementControlExit2: {
                if (!this.isMoving()) break isRunningMovementControlExit1;
                Entity entity = this;
                if (entity.movementQueue.isRunning()) break isRunningMovementControlExit2;
                entity = this;
                if (!entity.movementQueue.isRunPath()) break isRunningMovementControlExit1;
            }
            return true;
        }
        return false;
    }

    public final int getCombatBonus(int value2) {
        if (this.isPlayer()) {
            Player player = (Player)this;
            return (Integer)player.getCombatBonuses().get(value2);
        }
        Npc npc = (Npc)this;
        return (Integer)npc.getCombatDefinition().getCombatBonuses().get(value2);
    }

    public final boolean isInArea(int value5, int value22, int value32, int value42) {
        Entity entity = this;
        if (entity.position.getX() >= value5) {
            entity = this;
            if (entity.position.getX() <= value22) {
                entity = this;
                if (entity.position.getY() >= value32) {
                    entity = this;
                    if (entity.position.getY() <= value42) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isPointInArea(int value7, int value22, int value32, int value42, int value52, int value62) {
        return value52 >= value7 && value52 <= value22 && value62 >= value32 && value62 <= value42;
    }

    public final boolean isInCastleWars() {
        Entity entity;
        return this.isInArea(2368, 2431, 9479, 9535) || this.isInArea(2368, 2431, 3072, 3135) && !(entity = this).isInArea(2368, 2392, 9479, 9498) && !(entity = this).isInArea(2409, 2431, 9511, 9535);
    }

    public final boolean isInApeAtoll() {
        return this.isInArea(2688, 2820, 2688, 2820);
    }

    private boolean isInFightPits() {
        return this.isInArea(2370, 2430, 5122, 5168);
    }

    public final boolean isInFightCave() {
        return this.isInArea(2371, 2422, 5062, 5117);
    }

    public final boolean isInTutorialIsland() {
        return this.isInArea(3072, 3135, 3072, 3135) || this.isInArea(3072, 3135, 9472, 9535);
    }

    private boolean isInWildernessDitchSafeZone() {
        if (wildernessDitchSafeZone == null) {
            wildernessDitchSafeZone = new Polygon();
            wildernessDitchSafeZone.addPoint(2994, 3516);
            wildernessDitchSafeZone.addPoint(3040, 3516);
            wildernessDitchSafeZone.addPoint(3040, 3520);
            wildernessDitchSafeZone.addPoint(3026, 3533);
            wildernessDitchSafeZone.addPoint(3025, 3541);
            wildernessDitchSafeZone.addPoint(3020, 3545);
            wildernessDitchSafeZone.addPoint(3015, 3543);
            wildernessDitchSafeZone.addPoint(3012, 3543);
            wildernessDitchSafeZone.addPoint(3009, 3544);
            wildernessDitchSafeZone.addPoint(3005, 3542);
            wildernessDitchSafeZone.addPoint(2999, 3534);
            wildernessDitchSafeZone.addPoint(2995, 3534);
            wildernessDitchSafeZone.addPoint(2995, 3527);
            wildernessDitchSafeZone.addPoint(2994, 3523);
        }
        if (ServerSettings.cacheVersion > 245) {
            Entity entity2 = this;
            if (wildernessDitchSafeZone.contains(this.position.getX(), entity2.position.getY())) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInTenthSquadSigilInstance() {
        return this.isInArea(2688, 2751, 9152, 9215);
    }

    public final boolean isInWilderness() {
        return (this.isInArea(2942, 3391, 3520, 3966) || this.isInArea(3076, 3135, 9919, 10000) || this.isInArea(2990, 3071, 10239, 10366)) && !this.isInWildernessDitchSafeZone();
    }

    public final boolean isWildernessCoordinate(int value3, int value22) {
        return (Entity.isPointInArea(2942, 3391, 3517, 3966, value3, value22) || Entity.isPointInArea(3076, 3135, 9917, 10000, value3, value22) || Entity.isPointInArea(2990, 3071, 10239, 10366, value3, value22)) && !this.isInWildernessDitchSafeZone() || this.isInWildernessDitchSafeZone() && Entity.isPointInArea(2996, 2999, 3529, 3535, value3, value22);
    }

    public final int getWildernessLevel() {
        int value;
        if (!this.isInWilderness()) {
            return 0;
        }
        Entity entity = this;
        if (entity.position.getY() > 6400) {
            entity = this;
            value = entity.position.getY() - 6400;
        } else {
            entity = this;
            value = entity.position.getY();
        }
        int value2 = value;
        return (value - 3520) / 8 + 1;
    }

    public final boolean isInSmokeDungeon() {
        return this.isInArea(3200, 3328, 9344, 9408);
    }

    public final boolean isInMultiCombatArea() {
        boolean enabled;
        isInMultiCombatAreaControlExit1: {
            Entity entity = this;
            Object value = entity;
            value = entity;
            int regionId = GameUtil.getRegionId(entity.position.getX(), ((Entity)value).position.getY());
            int length = MultiwayAreaDefinition.definitions.length;
            int index = 0;
            while (index < length) {
                value = MultiwayAreaDefinition.forDefinitionId(index);
                if (value != null) {
                    if (((MultiwayAreaDefinition)value).getRegionCount() == 0) {
                        RectangularArea rectangularArea = ((MultiwayAreaDefinition)value).getAreaBounds();
                        value = entity;
                        if (rectangularArea.contains(((Entity)value).position)) {
                            enabled = true;
                            break isInMultiCombatAreaControlExit1;
                        }
                    } else {
                        int[] regionIds = ((MultiwayAreaDefinition)value).getRegionIds();
                        int index2 = 0;
                        while (index2 < ((MultiwayAreaDefinition)value).getRegionCount()) {
                            if (regionId == regionIds[index2]) {
                                enabled = true;
                                break isInMultiCombatAreaControlExit1;
                            }
                            ++index2;
                        }
                    }
                }
                ++index;
            }
            enabled = false;
        }
        return enabled || this.isInCastleWars() || this.isInFightPits() || this.isInFightCave();
    }

    public final boolean isInCastleWarsObstacleArea(int value3, int value22) {
        return Entity.isPointInArea(2414, 2416, 3074, 3085, value3, value22) || Entity.isPointInArea(2418, 2424, 3087, 3089, value3, value22) || Entity.isPointInArea(2412, 2417, 3086, 3091, value3, value22) || Entity.isPointInArea(2383, 2385, 3122, 3133, value3, value22) || Entity.isPointInArea(2375, 2381, 3118, 3120, value3, value22) || Entity.isPointInArea(2382, 2387, 3116, 3121, value3, value22);
    }

    public final boolean isInFiremakingRestrictedArea() {
        return this.isInArea(3090, 3099, 3487, 3500) || this.isInArea(3089, 3090, 3492, 3498) || this.isInArea(3248, 3258, 3413, 3428) || this.isInArea(3179, 3191, 3432, 3448) || this.isInArea(2944, 2948, 3365, 3374) || this.isInArea(2942, 2948, 3367, 3374) || this.isInArea(2944, 2950, 3365, 3370) || this.isInArea(3008, 3019, 3352, 3359) || this.isInArea(3017, 3022, 3352, 3357) || this.isInArea(3203, 3213, 3200, 3237) || this.isInArea(3212, 3215, 3200, 3235) || this.isInArea(3215, 3220, 3202, 3235) || this.isInArea(3220, 3227, 3202, 3229) || this.isInArea(3227, 3230, 3208, 3226) || this.isInArea(3226, 3228, 3230, 3211) || this.isInArea(3227, 3229, 3208, 3226);
    }

    public final boolean isInBarrows() {
        return this.isInArea(3520, 9664, 3583, 9727);
    }

    public final boolean isInDuelArena() {
        return this.isInArea(3333, 3357, 3244, 3258) || this.isInArea(3333, 3357, 3225, 3239) || this.isInArea(3333, 3357, 3206, 3220) || this.isInArea(3364, 3388, 3244, 3258) || this.isInArea(3364, 3388, 3225, 3239) || this.isInArea(3364, 3388, 3206, 3220);
    }

    public final boolean isInDuelArenaLobby() {
        return (this.isInArea(3325, 3410, 3200, 3266) || this.isInArea(3341, 3410, 3267, 3288) || this.isInArea(3312, 3322, 3224, 3247)) && !this.isInDuelArena();
    }

    public final boolean isInMageArena() {
        return this.isInArea(3087, 3122, 3919, 3947);
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setEncodedIndex(int index) {
        this.encodedIndex = index;
    }

    public final int getEncodedIndex() {
        return this.encodedIndex;
    }

    public final boolean isPlayer() {
        return this.encodedIndex >= 32768;
    }

    public final boolean isNpc() {
        return this.encodedIndex < 32768;
    }

    public final void setInteractionTarget(Entity entity) {
        if (this.isNpc() && entity != null && !((Npc)this).isFaceEntityUpdateDisabled()) {
            this.updateState.setFaceEntityId(entity.encodedIndex);
        }
        this.interactionTarget = entity;
    }

    public final Entity getInteractionTarget() {
        return this.interactionTarget;
    }

    public final void setPosition(Position position) {
        this.position = position;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final EntityUpdateState getUpdateState() {
        return this.updateState;
    }

    public final void setDead(boolean dead) {
        this.dead = dead;
    }

    public final boolean isDead() {
        return this.dead;
    }

    public final Map getAttributes() {
        return this.attributes;
    }

    public final void setCombatTarget(Entity entity) {
        this.combatTarget = entity;
    }

    public final Entity getCombatTarget() {
        return this.combatTarget;
    }

    public final void setMovementTarget(Entity entity) {
        if (this.isPlayer()) {
            Player player = (Player)this;
            if (player.botEnabled && player.botCombatEscapeActive) {
                this.movementTarget = null;
                return;
            }
        }
        this.movementTarget = entity;
    }

    public final Entity getMovementTarget() {
        return this.movementTarget;
    }

    public final void setSize(int size) {
        this.size = size;
    }

    public final int getSize() {
        return this.size;
    }

    public final void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public final int getAttackRange() {
        return this.attackRange;
    }

    public abstract int getCurrentHitpoints();

    public abstract void setCurrentHitpoints(int hitpoints);

    public abstract int getMaxHitpoints();

    public abstract int getDeathAnimationId();

    public abstract int getBlockAnimationId();

    public abstract int getDeathDelayTicks();

    public abstract int getAttackLevelFor(CombatType combatType);

    public abstract int getDefenceLevelFor(CombatType combatType);

    public abstract boolean isProtectedFrom(CombatType combatType);

    public abstract void moveTo(Position position);

    public final DelayTimer getMovementDelayTimer() {
        return this.movementDelayTimer;
    }

    public final Entity getTopDamageContributor() {
        DamageContribution damageContribution = (DamageContribution)this.damageContributions.peek();
        if (damageContribution != null) {
            Entity contributor = damageContribution.resolve();
            if (this.damageContributions.size() > 1 && contributor != null && contributor.isPlayer()) {
                Player player = (Player)contributor;
                if (player.gameMode != 0) {
                    player.packetSender.sendGameMessage("You are not playing on normal gamemode and cannot receive the loot.");
                    return null;
                }
            }
            return contributor;
        }
        return null;
    }

    public final ArrayList getDamageContributorList() {
        ArrayList<Entity> arrayList = new ArrayList<Entity>();
        for (DamageContribution damageContribution : this.damageContributions) {
            Entity contributor = damageContribution.resolve();
            if (contributor == null) {
                continue;
            }
            arrayList.add(contributor);
        }
        return arrayList;
    }

    public final CombatTargetDelayTimer getRecentCombatTimer() {
        return this.recentCombatTimer;
    }

    public final CombatTargetDelayTimer getSingleCombatTimer() {
        return this.singleCombatTimer;
    }

    public final DelayTimer getAttackDelayTimer() {
        return this.attackDelayTimer;
    }

    public final void setAttackDelayTicks(int delayTicks) {
        this.attackDelayTimer.setDelayTicks(delayTicks);
        this.attackDelayTimer.reset();
    }

    public final Queue getDamageContributions() {
        return this.damageContributions;
    }

    public final DamageContribution getDamageContribution(Entity entity) {
        for (DamageContribution damageContribution : this.damageContributions) {
            if (damageContribution.resolve() != entity) {
                continue;
            }
            return damageContribution;
        }
        return null;
    }

    public final int collectCombatAttackOptions(List list, Entity entity, int value2) {
        CombatAttack[] combatAttackArray = this.combatAttackProvider.createAttacks(this, entity);
        int usableAttackCount = combatAttackArray.length;
        int kbdSpecialIndex = -1;
        for (int attackIndex = 0; attackIndex < combatAttackArray.length; ++attackIndex) {
            CombatAttack combatAttack = combatAttackArray[attackIndex];
            combatAttack.prepare();
            BaseCombatAttack baseCombatAttack = (BaseCombatAttack)combatAttack;
            if (baseCombatAttack == null || baseCombatAttack.getHitDefinitions() == null) {
                continue;
            }
            HitDefinition[] hitDefinitions = baseCombatAttack.getHitDefinitions();
            for (HitDefinition hitDefinition : hitDefinitions) {
                if (hitDefinition != null && hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                    kbdSpecialIndex = attackIndex;
                }
            }
        }
        if (kbdSpecialIndex != -1) {
            CombatAttack[] kbdSpecialAttacks = new CombatAttack[]{
                BaseCombatAttack.createProjectileAttackWithEffect(this, entity, CombatType.MAGIC, AttackXpMode.KBD_SPECIAL, 50, 4, 81, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 394, ProjectileTiming.STANDARD, new PoisonEffect(8.0)),
                BaseCombatAttack.createProjectileAttackWithEffect(this, entity, CombatType.MAGIC, AttackXpMode.KBD_SPECIAL, 50, 4, 81, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 395, ProjectileTiming.STANDARD, new MovementLockEffect(10)),
                BaseCombatAttack.createProjectileAttackWithEffect(this, entity, CombatType.MAGIC, AttackXpMode.KBD_SPECIAL, 50, 4, 81, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 396, ProjectileTiming.STANDARD, new StatDrainEffect(-1, 2))
            };
            CombatAttack selectedAttack = kbdSpecialAttacks[GameUtil.randomInt(3)];
            selectedAttack.prepare();
            combatAttackArray[kbdSpecialIndex] = selectedAttack;
        }
        for (CombatAttack combatAttack : combatAttackArray) {
            CombatAttackState combatAttackState = combatAttack.getState();
            if (this.isNpc() && entity.isPlayer()) {
                Npc npc = (Npc)this;
                Player player = (Player)entity;
                if (npc.getNpcId() == 1264) {
                    if (player.getActivePrayers()[14]) {
                        if (combatAttack.getCombatType() == CombatType.MELEE) {
                            combatAttackState = CombatAttackState.a;
                        }
                    } else if (player.getActivePrayers()[12] && combatAttack.getCombatType() != CombatType.MELEE) {
                        combatAttackState = CombatAttackState.a;
                    }
                }
            }
            if (combatAttackState == CombatAttackState.a) {
                if (this.isPlayer()) {
                    Player player = (Player)this;
                    if (player.botEnabled && (combatAttack.getCombatType() == CombatType.MAGIC || combatAttack.getCombatType() == CombatType.RANGED)) {
                        if (player.isInWilderness()) {
                            BotCombatEscapeHandler.tryStartBotCombatEscape(player);
                        } else if (player.currentBotTask != null) {
                            player.botCombatState = "escape";
                        }
                    }
                }
                --usableAttackCount;
                if (this.isPlayer() && ((Player)this).isSpecialAttackEnabled()) {
                    ((Player)this).setSpecialAttackEnabled(false);
                    ((Player)this).refreshSpecialAttackWidgets();
                }
                continue;
            }
            int attackRange = combatAttack.getAttackRange();
            if (this.isMoving() && !this.isRunningMovement() && entity.isMoving() && !entity.isRunningMovement()) {
                ++attackRange;
            } else if (this.isMoving() && this.isRunningMovement() && entity.isMoving() && entity.isRunningMovement()) {
                attackRange += 2;
            }
            if (!EntityTargetMovement.canReachTarget(this, entity, attackRange)) {
                combatAttackState = CombatAttackState.b;
            }
            list.add(new SmithingHandler(combatAttack, combatAttackState));
        }
        return usableAttackCount;
    }

    public final void setActiveCycleEvent(CycleEvent cycleEvent) {
        this.activeCycleEvent = cycleEvent;
    }

    public final CycleEvent getActiveCycleEvent() {
        return this.activeCycleEvent;
    }

    public final int nextActionSequence() {
        ++this.actionSequence;
        if (this.actionSequence > 0x7FFFFFFD) {
            this.actionSequence = 0;
        }
        return this.actionSequence;
    }

    public final boolean isCurrentActionSequence(int value2) {
        return value2 == this.actionSequence;
    }

    public final boolean canApplyCombatEffect(CombatEffect combatEffect) {
        for (CombatEffectTask combatEffectTask : this.combatEffectTasks) {
            if (!combatEffectTask.getEffect().equals(combatEffect)) {
                continue;
            }
            return false;
        }
        return combatEffect.canApplyTo(this);
    }

    public final void clearCombatEffectTasks() {
        this.clearCombatEffectTasks(null);
    }

    public final void clearCombatEffectTasks(Class clazz) {
        LinkedList<CombatEffectTask> linkedList = new LinkedList<CombatEffectTask>();
        linkedList.addAll(this.combatEffectTasks);
        for (CombatEffectTask combatEffectTask : linkedList) {
            if (clazz != null && combatEffectTask.getEffect().getClass() != clazz) continue;
            combatEffectTask.stop();
        }
        linkedList.clear();
    }

    public final void addCombatEffectTask(CombatEffectTask combatEffectTask) {
        this.combatEffectTasks.add(combatEffectTask);
    }

    public final void removeCombatEffect(CombatEffect combatEffect) {
        CombatEffectTask combatEffectTask = null;
        for (CombatEffectTask combatEffectTask2 : this.combatEffectTasks) {
            if (combatEffectTask2.getEffect() != combatEffect) continue;
            combatEffectTask = combatEffectTask2;
        }
        if (combatEffectTask != null) {
            this.combatEffectTasks.remove(combatEffectTask);
        }
    }

    public final boolean isChargeSpellActive() {
        return !this.chargeSpellTimer.hasElapsed();
    }

    public final void activateChargeSpell() {
        this.chargeSpellTimer.setDelayTicks(700);
        this.chargeSpellTimer.reset();
        this.chargeCooldownTimer.setDelayTicks(100);
        this.chargeCooldownTimer.reset();
    }

    public final DelayTimer getChargeCooldownTimer() {
        return this.chargeCooldownTimer;
    }

    public final boolean hasCombatTarget() {
        Entity entity = this;
        return entity.combatTarget != null;
    }

    public final MovementQueue getMovementQueue() {
        return this.movementQueue;
    }

    public final void queuePathTo(int value3, int value22, boolean enabled2) {
        Entity entity = this;
        this.queuePathTo(new Position(value3, value22, entity.position.getPlane()), true);
    }

    public final void queuePathTo(Position position, boolean enabled2) {
        PathResult pathResult = new DirectPathStrategy().buildPath(this, position, enabled2);
        this.movementQueue.clear();
        while (!pathResult.getSteps().isEmpty()) {
            PathStep pathStep = (PathStep)pathResult.getSteps().poll();
            this.movementQueue.addStep(new Position(pathStep.getX(), pathStep.getY(), this.position.getPlane()));
        }
        this.movementQueue.removeFirstStep();
    }

    public final EntityTargetMovement getTargetMovement() {
        return this.targetMovement;
    }

    public final boolean isWithinReach(Entity entity, int value2) {
        Rectangle sourceArea = new Rectangle(this.position.getX() - value2, this.position.getY() - value2, 2 * value2 + this.size, 2 * value2 + this.size);
        Rectangle targetArea = new Rectangle(entity.position.getX(), entity.position.getY(), entity.size, entity.size);
        return sourceArea.intersects(targetArea);
    }

    public final boolean isOverlapping(Entity entity) {
        Rectangle sourceArea = new Rectangle(this.position.getX(), this.position.getY(), this.size, this.size);
        Rectangle targetArea = new Rectangle(entity.position.getX(), entity.position.getY(), entity.size, entity.size);
        return sourceArea.intersects(targetArea);
    }

    public final void beginInterruptibleAction() {
        ++this.interruptibleActionCounter;
        this.interruptibleActionCheckpoint = this.interruptibleActionCounter;
        if (this.interruptibleActionCheckpoint > 0x7FFFFFFD || this.interruptibleActionCounter > 0x7FFFFFFD) {
            this.interruptibleActionCheckpoint = 0;
            this.interruptibleActionCounter = 0;
        }
    }

    public final void invalidateInterruptibleAction() {
        ++this.interruptibleActionCounter;
    }

    public final boolean isInterruptibleActionActive() {
        return this.interruptibleActionCheckpoint == this.interruptibleActionCounter;
    }

    public final int getReferenceId() {
        return this.referenceId;
    }

    public final void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public final Position getDeathPosition() {
        return this.deathPosition;
    }

    public final void setDeathPosition(Position position) {
        this.deathPosition = position;
    }

    public final void setTradePartner(Entity entity) {
        this.tradePartner = entity;
    }

    public final Entity getTradePartner() {
        return this.tradePartner;
    }

    public final boolean canTravelBetween(int value8, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value52 < 0 && this.isPlayer()) {
            System.out.println(String.valueOf(((Player)this).getUsername()) + " negative height value!");
            value52 = 0;
        }
        return WalkingCollisionMap.canTravelBetween(value8, value22, value32, value42, value52, value62, value72);
    }

    public final boolean canStepToOffset(int value3, int value22) {
        Entity entity = this;
        Entity entity2 = entity;
        Entity entity3 = this;
        entity2 = entity3;
        Entity entity4 = this;
        entity2 = entity4;
        Entity entity5 = this;
        entity2 = entity5;
        Entity entity6 = this;
        entity2 = entity6;
        Entity entity7 = this;
        entity2 = entity7;
        entity2 = this;
        return WalkingCollisionMap.canTravelBetween(entity.position.getX(), entity3.position.getY(), entity4.position.getX() + value3, entity5.position.getY() + value22, entity6.position.getPlane(), entity7.size, entity2.size);
    }

    public final DelayTimer getMovementLockImmunityTimer() {
        return this.movementLockImmunityTimer;
    }

    public final DelayTimer getPoisonImmunityTimer() {
        return this.poisonImmunityTimer;
    }

    public final DelayTimer getAntifireTimer() {
        return this.antifireTimer;
    }

    public final DelayTimer getTeleblockTimer() {
        return this.teleblockTimer;
    }

    public final DelayTimer getMovementLockTimer() {
        return this.movementLockTimer;
    }

    public final DelayTimer getStunTimer() {
        return this.stunTimer;
    }

    public final boolean isMovementLockImmune() {
        return !this.movementLockImmunityTimer.hasElapsed();
    }

    public final boolean isAntifireActive() {
        return !this.antifireTimer.hasElapsed();
    }

    public final boolean isTeleblocked() {
        return !this.teleblockTimer.hasElapsed();
    }

    public final boolean isInTeleportRestrictedArea() {
        Entity entity = this;
        return entity.isInArea(2587, 2619, 4760, 4785) || this.isInFightPits() || (entity = this).isInArea(2394, 2404, 5169, 5175) || this.isInFightCave() || this.isInDuelArena();
    }

    public final boolean isMovementLocked() {
        return !this.movementLockTimer.hasElapsed();
    }

    public final boolean isStunned() {
        return !this.stunTimer.hasElapsed();
    }

    public final void clearNegativeStatusTimers() {
        this.teleblockTimer.setDelayTicks(0);
        this.movementLockTimer.setDelayTicks(0);
        this.stunTimer.setDelayTicks(0);
        this.teleblockTimer.reset();
        this.movementLockTimer.reset();
        this.stunTimer.reset();
    }

    public final void clearImmunityTimers() {
        this.poisonImmunityTimer.setDelayTicks(0);
        this.movementLockImmunityTimer.setDelayTicks(0);
        this.antifireTimer.setDelayTicks(0);
        this.poisonImmunityTimer.reset();
        this.movementLockImmunityTimer.reset();
        this.antifireTimer.reset();
    }

    public final void setPoisonDamage(double poisonDamage) {
        this.poisonDamage = poisonDamage;
        if (this.isPlayer()) {
            Player player = (Player)this;
            Player player2 = player;
            player2 = player;
        }
    }

    public final double getPoisonDamage() {
        return this.poisonDamage;
    }

    public final void setCombatTransformNpcId(int npcId) {
        this.combatTransformNpcId = npcId;
    }

    public final int getCombatTransformNpcId() {
        return this.combatTransformNpcId;
    }

    public final void setScriptedMovementEnabled(boolean scriptedMovementEnabled) {
        this.scriptedMovementEnabled = scriptedMovementEnabled;
    }

    public final boolean isScriptedMovementEnabled() {
        return this.scriptedMovementEnabled;
    }

    public final void setTargetMovementDisabled(boolean targetMovementDisabled) {
        this.targetMovementDisabled = true;
    }

    public final boolean isTargetMovementDisabled() {
        return this.targetMovementDisabled;
    }

    public final boolean isDoorSupportNpc() {
        return this.isNpc() && (((Npc)this).getNpcId() == 2440 || ((Npc)this).getNpcId() == 2443 || ((Npc)this).getNpcId() == 2446);
    }

    public final void setAutoRetaliateDisabled(boolean autoRetaliateDisabled) {
        this.autoRetaliateDisabled = true;
    }

    public final boolean isAutoRetaliateDisabled() {
        return this.autoRetaliateDisabled;
    }
}
