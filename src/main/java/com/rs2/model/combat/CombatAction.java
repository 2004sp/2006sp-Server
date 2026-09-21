package com.rs2.model.combat;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AmmunitionDefinition;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.StatDrainEffect;
import com.rs2.model.combat.effect.WallBeastStunEffect;
import com.rs2.model.combat.hit.DamageContribution;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.DegradableEquipmentHandler;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.skill.prayer.PrayerManager;
import com.rs2.model.skill.thieving.PickpocketDefinition;
import com.rs2.model.skill.thieving.PickpocketTask;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;

public class CombatAction {
    private HitDefinition hitDefinition;
    private int delay;
    private int damage;
    private Entity attacker;
    private Entity target;
    private boolean hitSuccessful;
    private Entity projectileSource;

    public static boolean handlePickpocketAttempt(Player player, Npc npc) {
        if (player == null || player.isStunned() || !player.getSkillManager().tryStartActionDelay(2200)) {
            return true;
        }
        String npcName = npc.getDefinition().getName().toLowerCase();
        PickpocketDefinition definition = null;
        PickpocketDefinition[] definitions = PickpocketDefinition.class.getEnumConstants();
        if (definitions != null) {
            for (PickpocketDefinition candidate : definitions) {
                for (String name : candidate.getNpcNames()) {
                    if (npcName.equalsIgnoreCase(name)) {
                        definition = candidate;
                        break;
                    }
                }
                if (definition != null) {
                    break;
                }
            }
        }
        if (definition == null) {
            return false;
        }
        if (!ServerSettings.thievingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return true;
        }
        if (ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return true;
        }
        if (!SkillActionHelper.checkSkillRequirement(player, 17, definition.getRequiredLevel(), "pickpocket this npc")) {
            return true;
        }
        int lowChance = definition.getSuccessChanceLow();
        int highChance = definition.getSuccessChanceHigh();
        boolean successful = GameUtil.rollLevelScaledChance(lowChance, highChance, player.getSkillManager().getCurrentLevels()[17]);
        ItemStack reward = definition.getRareRewards() != null && GameUtil.randomInclusive(30) == 0 ? definition.getRareRewards()[GameUtil.randomExclusive(definition.getRareRewards().length)] : definition.getCommonRewards()[GameUtil.randomExclusive(definition.getCommonRewards().length)];
        reward = new ItemStack(reward.getId(), reward.getAmount());
        int damage = GameUtil.randomBetweenInclusive(definition.getMinDamage(), definition.getMaxDamage());
        player.setActionLocked(true);
        player.getUpdateState().setAnimation(881);
        player.packetSender.sendGameMessage("You attempt to pick the " + npcName + "'s pocket.");
        CycleEventHandler.getInstance().schedule(player, new PickpocketTask(successful, player, npc, reward, definition, npcName, damage), 2);
        return true;
    }

    public CombatAction(Entity entity, Entity entity2, HitDefinition hitDefinition) {
        this.attacker = entity;
        this.target = entity2;
        this.hitDefinition = hitDefinition;
        this.damage = hitDefinition.getMaxDamage();
    }

    public CombatAction(Entity entity, Entity entity2, HitDefinition hitDefinition, Entity entity3) {
        this.attacker = entity;
        this.target = entity2;
        this.hitDefinition = hitDefinition;
        this.damage = hitDefinition.getMaxDamage();
        this.projectileSource = entity3;
    }

    public void tickDelay() {
        --this.delay;
    }

    public void queue() {
        this.queue(true);
    }

    public void queue(boolean value10) {
        Entity entity;
        Entity entity2;
        if (this.hitDefinition.isRandomDamageEnabled()) {
            if (this.attacker.isNpc() && this.hitDefinition.getAttackStyle().getCombatType() == CombatType.MELEE) {
                this.damage = CombatManager.calculateMeleeMaxHit(this.getAttacker(), null);
            }
            this.damage = GameUtil.randomInclusive(this.damage);
        }
        if (this.hitDefinition.getMinimumDamage() != -1 && this.damage < this.hitDefinition.getMinimumDamage()) {
            this.damage = this.hitDefinition.getMinimumDamage();
        }
        if (this.attacker != null && !this.hitDefinition.isAlwaysHit() && this.hitDefinition.isAccuracyCheckEnabled()) {
            double value2;
            double value3;
            double value4;
            CombatAction combatAction = this;
            this.hitSuccessful = true;
            if (combatAction.attacker != null && GameUtil.randomInclusive(4) == 0) {
                if (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).isGuthanSetEffectActive() || combatAction.attacker.isNpc() && ((Npc)combatAction.attacker).getDefinition().getId() == 2027) {
                    combatAction.hitDefinition.setSpecialEffectId(7);
                } else if (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).isToragSetEffectActive() || combatAction.attacker.isNpc() && ((Npc)combatAction.attacker).getDefinition().getId() == 2029) {
                    combatAction.hitDefinition.setSpecialEffectId(8);
                } else if (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).isAhrimSetEffectActive() || combatAction.attacker.isNpc() && ((Npc)combatAction.attacker).getDefinition().getId() == 2025) {
                    combatAction.hitDefinition.setSpecialEffectId(9);
                } else if (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).isKarilSetEffectActive() || combatAction.attacker.isNpc() && ((Npc)combatAction.attacker).getDefinition().getId() == 2028) {
                    combatAction.hitDefinition.setSpecialEffectId(10);
                } else if (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).isVeracSetEffectActive() || combatAction.attacker.isNpc() && ((Npc)combatAction.attacker).getDefinition().getId() == 2030) {
                    combatAction.hitDefinition.setSpecialEffectId(11);
                }
            }
            if (combatAction.attacker != null && (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).isDharokSetEffectActive() || combatAction.attacker.isNpc() && ((Npc)combatAction.attacker).getDefinition().getId() == 2026)) {
                value4 = combatAction.attacker.getMaxHitpoints();
                value3 = combatAction.attacker.getCurrentHitpoints();
                value2 = 1.0 + (value4 - value3) / 100.0 * (value4 / 100.0);
                double value5 = combatAction.damage;
                combatAction.damage = (int)(value5 *= value2);
            }
            if (combatAction.getAttacker().isNpc() && combatAction.getTarget().isPlayer() && !((Player)combatAction.getTarget()).getSlayerManager().canAttackSlayerMonster((Npc)combatAction.getAttacker())) {
                String attacker = ((Npc)combatAction.getAttacker()).getDefinition().getName().toLowerCase();
                if (attacker.equalsIgnoreCase("banshee")) {
                    combatAction.damage = 8;
                    combatAction.hitDefinition.addEffects(new CombatEffect[]{new StatDrainEffect(0, 1), new StatDrainEffect(2, 1), new StatDrainEffect(1, 1), new StatDrainEffect(4, 1), new StatDrainEffect(6, 1)});
                } else if (attacker.equalsIgnoreCase("cockatrice")) {
                    combatAction.damage = 11;
                    combatAction.hitDefinition.addEffects(new CombatEffect[]{new StatDrainEffect(0, 3), new StatDrainEffect(2, 3), new StatDrainEffect(1, 3), new StatDrainEffect(4, 3), new StatDrainEffect(6, 3), new StatDrainEffect(16, 3)});
                } else if (attacker.equalsIgnoreCase("basilisk")) {
                    combatAction.damage = 12;
                    combatAction.hitDefinition.addEffects(new CombatEffect[]{new StatDrainEffect(0, 3), new StatDrainEffect(2, 3), new StatDrainEffect(1, 3), new StatDrainEffect(4, 3), new StatDrainEffect(6, 3)});
                } else if (attacker.equalsIgnoreCase("wall beast")) {
                    combatAction.damage = 18;
                    combatAction.hitDefinition.addEffect(new WallBeastStunEffect(5)).setBlockAnimationId(734);
                } else if (attacker.equalsIgnoreCase("aberrant specter")) {
                    combatAction.damage = 14;
                    combatAction.hitDefinition.addEffects(new CombatEffect[]{new StatDrainEffect(0, 5), new StatDrainEffect(2, 5), new StatDrainEffect(1, 5), new StatDrainEffect(4, 5), new StatDrainEffect(6, 5)});
                } else if (attacker.equalsIgnoreCase("dust devil")) {
                    combatAction.damage = 14;
                }
            }
            if (combatAction.getTarget().isPlayer() && ((Player)combatAction.getTarget()).getQuestState(0) != 1 && ((Player)combatAction.getTarget()).getSkillManager().getCurrentLevels()[3] == 1) {
                combatAction.hitSuccessful = false;
            }
            if (combatAction.getAttacker().isPlayer() && combatAction.getTarget().isNpc() && !((Player)combatAction.getAttacker()).getSlayerManager().canAttackSlayerMonster((Npc)combatAction.getTarget())) {
                combatAction.hitSuccessful = false;
            }
            if (combatAction.attacker.isPlayer() && ((Player)combatAction.attacker).getQuestState(0) == 65) {
                ((Player)combatAction.attacker).advanceTutorialStage();
                combatAction.hitSuccessful = false;
            }
            if (combatAction.hitSuccessful) {
                value4 = CombatManager.calculateDefenceRoll(combatAction.target, combatAction.hitDefinition);
                value3 = CombatManager.calculateAttackRoll(combatAction.attacker, combatAction.hitDefinition);
                if (!ServerSettings.modernCombatSystemEnabled) {
                    Player player;
                    if (combatAction.hitDefinition.getAttackStyle().getCombatType() == CombatType.MELEE && combatAction.attacker.isPlayer()) {
                        Player player2 = (Player)combatAction.attacker;
                        if (player2.hasFullVoidMeleeSet()) {
                            combatAction.damage = (int)((double)combatAction.damage * 1.1);
                        }
                        if (player2.getEquipmentManager().getItemIdAtSlot(2) == 11128 && (player2.getEquipmentManager().getItemIdAtSlot(3) == 6523 || player2.getEquipmentManager().getItemIdAtSlot(3) == 6528 || player2.getEquipmentManager().getItemIdAtSlot(3) == 6527 || player2.getEquipmentManager().getItemIdAtSlot(3) == 6525)) {
                            combatAction.damage = (int)((double)combatAction.damage * 1.2);
                        }
                    }
                    if (combatAction.hitDefinition.getAttackStyle().getCombatType() == CombatType.RANGED && combatAction.attacker.isPlayer() && (player = (Player)combatAction.attacker).hasFullVoidRangedSet()) {
                        combatAction.damage = (int)((double)combatAction.damage * 1.2);
                    }
                }
                value2 = CombatManager.calculateHitChance(value3, value4);
                boolean enabled = CombatManager.rollAccuracy(value2);
                if (combatAction.getAttacker().isPlayer()) {
                    entity2 = (Player)combatAction.getAttacker();
                }
                if (combatAction.getTarget().isPlayer()) {
                    entity2 = (Player)combatAction.getTarget();
                }
                combatAction.hitSuccessful = enabled;
            }
            if ((combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.ICY_BREATH || combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE || combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE_FAR || combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) && combatAction.target.isPlayer()) {
                int initialValue = -1;
                if (combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE || combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE_FAR || combatAction.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                    int value6;
                    HitDefinition hitDefinition = combatAction.hitDefinition;
                    boolean enabled2 = combatAction.hitSuccessful;
                    entity2 = combatAction.target;
                    entity = combatAction.attacker;
                    int index = 0;
                    int index2 = 0;
                    if (entity == null || entity2 == null) {
                        value6 = -1;
                    } else if (!entity.isNpc() || !entity2.isPlayer()) {
                        value6 = -1;
                    } else {
                        int value7;
                        entity2 = (Player)entity2;
                        Entity entity3 = entity = (Npc)entity;
                        if (((Npc)entity).getNpcId() == 53 || ((Npc)entity3).getNpcId() == 54 || ((Npc)entity3).getNpcId() == 55 || ((Npc)entity3).getNpcId() == 941 || ((Npc)entity3).getNpcId() == 3885 || ((Npc)entity3).getNpcId() == 5362) {
                            switch (((Player)entity2).getDragonfireProtectionState()) {
                                case 0: {
                                    if (enabled2) {
                                        index = 30;
                                        break;
                                    }
                                    index = 50;
                                    break;
                                }
                                case 1: {
                                    index = enabled2 ? 30 : 50;
                                    index2 = 15;
                                    break;
                                }
                                case 2: {
                                    index = 10;
                                    break;
                                }
                                case 3: {
                                    index = 5;
                                    break;
                                }
                                case 4: {
                                    index = 10;
                                    index2 = 15;
                                    break;
                                }
                                case 5: {
                                    index = 5;
                                    index2 = 15;
                                    break;
                                }
                                case 6: {
                                    index = 5;
                                    break;
                                }
                                case 7: {
                                    index = 5;
                                    index2 = 15;
                                }
                            }
                        } else {
                            entity3 = entity;
                            if (((Npc)entity3).getNpcId() == 1590 || ((Npc)entity3).getNpcId() == 1591 || ((Npc)entity3).getNpcId() == 1592 || ((Npc)entity3).getNpcId() == 5363) {
                                switch (((Player)entity2).getDragonfireProtectionState()) {
                                    case 0: {
                                        if (enabled2) {
                                            index = 30;
                                            break;
                                        }
                                        index = 50;
                                        break;
                                    }
                                    case 1: {
                                        index = enabled2 ? 30 : 50;
                                        index2 = 15;
                                        break;
                                    }
                                    case 2: {
                                        if (enabled2) {
                                            index = 30;
                                            break;
                                        }
                                        index = 50;
                                        break;
                                    }
                                    case 3: {
                                        index = 5;
                                        break;
                                    }
                                    case 4: {
                                        index = enabled2 ? 30 : 50;
                                        index2 = 15;
                                        break;
                                    }
                                    case 5: {
                                        index = 5;
                                        index2 = 15;
                                        break;
                                    }
                                    case 6: {
                                        index = 5;
                                        break;
                                    }
                                    case 7: {
                                        index = 5;
                                        index2 = 15;
                                    }
                                }
                            } else if (((Npc)entity).getNpcId() == 50) {
                                switch (((Player)entity2).getDragonfireProtectionState()) {
                                    case 0: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 50;
                                            break;
                                        }
                                        index = 65;
                                        break;
                                    }
                                    case 1: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 50;
                                            break;
                                        }
                                        index = 65;
                                        index2 = 15;
                                        break;
                                    }
                                    case 2: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 15;
                                            break;
                                        }
                                        index = 20;
                                        break;
                                    }
                                    case 3: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 10;
                                            break;
                                        }
                                        index = 15;
                                        break;
                                    }
                                    case 4: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 15;
                                            break;
                                        }
                                        index = 20;
                                        index2 = 15;
                                        break;
                                    }
                                    case 5: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 10;
                                            break;
                                        }
                                        index = 15;
                                        index2 = 15;
                                        break;
                                    }
                                    case 6: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 10;
                                            break;
                                        }
                                        index = 15;
                                        break;
                                    }
                                    case 7: {
                                        if (hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) {
                                            index = 10;
                                            break;
                                        }
                                        index = 15;
                                        index2 = 15;
                                    }
                                }
                            } else if (((Npc)entity).getNpcId() == 742) {
                                switch (((Player)entity2).getDragonfireProtectionState()) {
                                    case 0: {
                                        index = 70;
                                        break;
                                    }
                                    case 1: {
                                        index = 70;
                                        index2 = 15;
                                        break;
                                    }
                                    case 2: {
                                        index = 55;
                                        break;
                                    }
                                    case 3: {
                                        index = 10;
                                        break;
                                    }
                                    case 4: {
                                        index = 55;
                                        index2 = 15;
                                        break;
                                    }
                                    case 5: {
                                        index = 10;
                                        index2 = 3;
                                        break;
                                    }
                                    case 6: {
                                        index = 7;
                                        break;
                                    }
                                    case 7: {
                                        index = 7;
                                        index2 = 3;
                                    }
                                }
                            }
                        }
                        if ((value7 = GameUtil.randomInt(index + 1) - index2) < 0) {
                            value7 = 0;
                        }
                        value6 = value7;
                    }
                    initialValue = value6;
                } else {
                    Player player = (Player)combatAction.target;
                    if (player.getActivePrayers()[12]) {
                        initialValue = GameUtil.randomInt(21);
                    }
                }
                if (initialValue != -1) {
                    combatAction.damage = initialValue;
                }
            } else {
                if (combatAction.target.isProtectedFrom(combatAction.hitDefinition.getAttackStyle().getCombatType()) && combatAction.hitDefinition.getSpecialEffectId() != 11 && !combatAction.hitDefinition.isProtectionPrayerReductionDeferred() && combatAction.damage > 0) {
                    combatAction.damage = combatAction.attacker != null && combatAction.attacker.isPlayer() ? (int)Math.ceil((double)combatAction.damage * 0.6) : 0;
                }
            }
            if (ServerSettings.modernCombatSystemEnabled && combatAction.hitDefinition.getSpecialEffectId() == 11) {
                combatAction.hitSuccessful = true;
                ++combatAction.damage;
            }
            if (!combatAction.hitSuccessful && !combatAction.hitDefinition.isAlwaysHit()) {
                if (combatAction.hitDefinition.getAttackStyle() != null && combatAction.hitDefinition.getAttackStyle().getCombatType() == CombatType.MAGIC && combatAction.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.ICY_BREATH && combatAction.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.DRAGONFIRE && combatAction.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.DRAGONFIRE_FAR && combatAction.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.KBD_SPECIAL) {
                    combatAction.hitDefinition.setGraphic(new GraphicEffect(85, 100));
                }
                combatAction.hitDefinition.clearEffects();
            }
        }
        if (this.hitDefinition.getProjectile() != null) {
            if (this.projectileSource != null) {
                new WoodcuttingHandler(this.projectileSource, this.target, this.hitDefinition.getProjectile()).sendProjectileToNearbyPlayers();
            } else {
                new WoodcuttingHandler(this.attacker, this.target, this.hitDefinition.getProjectile()).sendProjectileToNearbyPlayers();
            }
        }
        this.delay = this.hitDefinition.calculateDelay(this.attacker != null ? this.attacker.getPosition() : null, this.target.getPosition());
        if (GameplayTrace.enabled()) {
            String style = this.hitDefinition.getAttackStyle() == null ? "null" : this.hitDefinition.getAttackStyle().getXpMode() + "/" + this.hitDefinition.getAttackStyle().getCombatType();
            GameplayTrace.log("combat action queued attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " style=" + style + " maxDamage=" + this.hitDefinition.getMaxDamage() + " damage=" + this.damage + " hitSuccessful=" + this.hitSuccessful + " random=" + this.hitDefinition.isRandomDamageEnabled() + " accuracy=" + this.hitDefinition.isAccuracyCheckEnabled() + " always=" + this.hitDefinition.isAlwaysHit() + " delay=" + this.delay + " canTakeDamage=" + this.target.getAttributes().get("canTakeDamage"));
        }
        CombatManager.getInstance().queueAction(this);
        if (this.target != null && this.attacker != null) {
            int questDamageOverride = this.attacker.isPlayer() ? ((Player)this.attacker).getQuestManager().getQuestDamageOverride(this.attacker, this.target) : ((Player)this.target).getQuestManager().getQuestDamageOverride(this.attacker, this.target);
            if (this.attacker.isInMageArena() && this.hitDefinition.getAttackStyle().getCombatType() != CombatType.MAGIC) {
                questDamageOverride = 0;
            }
            if (questDamageOverride != -1) {
                this.damage = questDamageOverride;
            }
        }
        if (this.hitDefinition.getSpell() != null && (this.hitDefinition.getSpell() == SpellDefinition.FLAMES_OF_ZAMORAK || this.hitDefinition.getSpell() == SpellDefinition.SARADOMIN_STRIKE || this.hitDefinition.getSpell() == SpellDefinition.CLAWS_OF_GUTHIX)) {
            int godSpellSoundId = 0;
            if (this.hitDefinition.getSpell() == SpellDefinition.FLAMES_OF_ZAMORAK) {
                godSpellSoundId = this.hitSuccessful ? 290 : 293;
            }
            if (this.hitDefinition.getSpell() == SpellDefinition.SARADOMIN_STRIKE) {
                godSpellSoundId = this.hitSuccessful ? 297 : 299;
            }
            if (this.hitDefinition.getSpell() == SpellDefinition.CLAWS_OF_GUTHIX) {
                godSpellSoundId = this.hitSuccessful ? 291 : 296;
            }
            if (this.attacker.isPlayer()) {
                entity = (Player)this.getAttacker();
                if (this.hitDefinition.getSpell() == SpellDefinition.SARADOMIN_STRIKE && ((Player)entity).mageArenaSaradominStrikeCastsRemaining > 0) {
                    --((Player)entity).mageArenaSaradominStrikeCastsRemaining;
                    if (((Player)entity).mageArenaSaradominStrikeCastsRemaining == 0) {
                        entity2 = entity;
                        ((Player)entity2).packetSender.sendGameMessage("You can now cast Saradomin Strike outside the Arena.");
                    }
                }
                if (this.hitDefinition.getSpell() == SpellDefinition.FLAMES_OF_ZAMORAK && ((Player)entity).mageArenaFlamesOfZamorakCastsRemaining > 0) {
                    --((Player)entity).mageArenaFlamesOfZamorakCastsRemaining;
                    if (((Player)entity).mageArenaFlamesOfZamorakCastsRemaining == 0) {
                        entity2 = entity;
                        ((Player)entity2).packetSender.sendGameMessage("You can now cast Flames of Zamorak outside the Arena.");
                    }
                }
                if (this.hitDefinition.getSpell() == SpellDefinition.CLAWS_OF_GUTHIX && ((Player)entity).mageArenaClawsOfGuthixCastsRemaining > 0) {
                    --((Player)entity).mageArenaClawsOfGuthixCastsRemaining;
                    if (((Player)entity).mageArenaClawsOfGuthixCastsRemaining == 0) {
                        entity2 = entity;
                        ((Player)entity2).packetSender.sendGameMessage("You can now cast Claws of Guthix outside the Arena.");
                    }
                }
                entity2 = entity;
                ((Player)entity2).packetSender.sendSoundEffect(godSpellSoundId, 1, 0);
            }
            if (this.target.isPlayer()) {
                entity2 = entity = (Player)this.getTarget();
                ((Player)entity).packetSender.sendSoundEffect(godSpellSoundId, 1, 0);
            }
        }
        if (this.hitSuccessful && this.target.isPlayer()) {
            int value8 = this.damage;
            Player player = (Player)this.target;
            Entity entity4 = this.attacker;
            ItemStack itemStack = player.getEquipmentManager().getContainer().getItemAt(12);
            if (itemStack != null && itemStack.getId() == 2550 && player != null && value8 > 0) {
                int value9 = (int)Math.ceil((double)value8 * 0.1);
                entity4.applyDirectHit(value9, HitType.NORMAL);
                if (entity4 != null) {
                    DamageContribution damageContribution = entity4.getDamageContribution(player);
                    if (damageContribution == null) {
                        damageContribution = new DamageContribution(player);
                    } else {
                        entity4.getDamageContributions().remove(damageContribution);
                    }
                    damageContribution.addDamage(value8);
                    entity4.getDamageContributions().add(damageContribution);
                }
                player.setRingOfRecoilLife(player.getRingOfRecoilLife() - value9);
                if (player.getRingOfRecoilLife() <= 0) {
                    entity2 = player;
                    ((Player)entity2).packetSender.sendGameMessage("Your ring shatters!");
                    player.getEquipmentManager().consumeSlotItemAmount(12, 1);
                    player.setRingOfRecoilLife(40);
                }
            }
        }
        if (this.attacker != null && this.attacker.isPlayer()) {
            DegradableEquipmentHandler.degradeEquipmentAfterCombat((Player)this.attacker);
        }
    }

    public void applyHitUpdate() {
        Object value;
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("combat hit-update attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " damage=" + this.damage + " canTakeDamage=" + this.target.getAttributes().get("canTakeDamage"));
        }
        if (!this.canTargetTakeDamage() || this.damage < 0) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("combat hit-update blocked attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " damage=" + this.damage);
            }
            return;
        }
        Object updateState = this.target.getUpdateState();
        Object hitType = value = this.damage == 0 ? HitType.BLOCKED : this.hitDefinition.getHitType();
        if (!((EntityUpdateState)updateState).isPrimaryHitDamageOverridden()) {
            Object value2;
            ((EntityUpdateState)updateState).setPrimaryHitUpdateRequired(true);
            ((EntityUpdateState)updateState).setPrimaryHitDamage(this.damage);
            if (this.attacker != null && this.hitDefinition.getAttackStyle() != null && this.hitDefinition.getAttackStyle().getCombatType() != CombatType.MELEE && this.hitDefinition.getAttackStyle().getCombatType() != CombatType.RANGED) {
                this.hitDefinition.getAttackStyle().getCombatType();
            }
            ((EntityUpdateState)updateState).setPrimaryHitType(((HitType)((Object)value)).getClientId());
            if (this.target.isPlayer()) {
                updateState = (Player)this.getTarget();
                if (this.damage > 0) {
                    value2 = updateState;
                    ((Player)value2).packetSender.sendSoundEffect(69, 1, 0);
                } else {
                    value2 = updateState;
                    ((Player)value2).packetSender.sendSoundEffect(((Player)updateState).getBlockSoundId(), 1, 0);
                }
                if (this.hitDefinition.getAttackStyle() != null && this.hitDefinition.getAttackStyle().getCombatType() == CombatType.MAGIC) {
                    value2 = updateState;
                    ((Player)value2).packetSender.sendSoundEffect(this.hitDefinition.getImpactSoundId(), 1, 0);
                }
                if (this.attacker != null && this.attacker.isPlayer()) {
                    value2 = value = (Player)this.getAttacker();
                    ((Player)value).packetSender.sendSoundEffect(((Player)updateState).getBlockSoundId(), 1, 0);
                    if (this.hitDefinition.getAttackStyle() != null && this.hitDefinition.getAttackStyle().getCombatType() == CombatType.MAGIC) {
                        value2 = value;
                        ((Player)value2).packetSender.sendSoundEffect(this.hitDefinition.getImpactSoundId(), 1, 0);
                    }
                }
            }
            if (this.target != null && this.attacker != null && this.target.isNpc() && this.attacker.isPlayer()) {
                updateState = (Player)this.getAttacker();
                value = (Npc)this.getTarget();
                value2 = updateState;
                ((Player)value2).packetSender.sendSoundEffect(((Npc)value).getHitSoundId(), 1, 0);
                if (this.hitDefinition.getAttackStyle() != null && this.hitDefinition.getAttackStyle().getCombatType() == CombatType.MAGIC) {
                    value2 = updateState;
                    ((Player)value2).packetSender.sendSoundEffect(this.hitDefinition.getImpactSoundId(), 1, 0);
                    if (((Npc)value).getNpcId() == 667 && this.hitDefinition.getSpell() != null && this.damage > 0) {
                        if (this.hitDefinition.getSpell() == SpellDefinition.WIND_BLAST) {
                            ((Npc)value).chronozonHitByWindBlast = true;
                            value2 = updateState;
                            ((Player)value2).packetSender.sendGameMessage("Chronozon weakens...");
                        }
                        if (this.hitDefinition.getSpell() == SpellDefinition.WATER_BLAST) {
                            ((Npc)value).chronozonHitByWaterBlast = true;
                            value2 = updateState;
                            ((Player)value2).packetSender.sendGameMessage("Chronozon weakens...");
                        }
                        if (this.hitDefinition.getSpell() == SpellDefinition.EARTH_BLAST) {
                            ((Npc)value).chronozonHitByEarthBlast = true;
                            value2 = updateState;
                            ((Player)value2).packetSender.sendGameMessage("Chronozon weakens...");
                        }
                        if (this.hitDefinition.getSpell() == SpellDefinition.FIRE_BLAST) {
                            ((Npc)value).chronozonHitByFireBlast = true;
                            value2 = updateState;
                            ((Player)value2).packetSender.sendGameMessage("Chronozon weakens...");
                        }
                    }
                    if (this.damage > 0 && ((Player)updateState).getQuestState(0) == 66) {
                        ((Player)updateState).advanceTutorialStage();
                        return;
                    }
                }
            }
        } else {
            if (!((EntityUpdateState)updateState).isSecondaryHitDamageOverridden()) {
                ((EntityUpdateState)updateState).setSecondaryHitUpdateRequired(true);
                ((EntityUpdateState)updateState).setSecondaryHitDamage(this.damage);
                if (this.attacker != null && this.hitDefinition.getAttackStyle() != null && this.hitDefinition.getAttackStyle().getCombatType() != CombatType.MELEE && this.hitDefinition.getAttackStyle().getCombatType() != CombatType.RANGED) {
                    this.hitDefinition.getAttackStyle().getCombatType();
                }
                ((EntityUpdateState)updateState).setSecondaryHitType(((HitType)((Object)value)).getClientId());
                return;
            }
            ((EntityUpdateState)updateState).queueHit(this.damage, ((HitType)((Object)value)).getClientId());
        }
    }

    public void applyHit() {
        Object value;
        Object value2;
        Object value3;
        if (GameplayTrace.enabled()) {
            String style = this.hitDefinition.getAttackStyle() == null ? "null" : this.hitDefinition.getAttackStyle().getXpMode() + "/" + this.hitDefinition.getAttackStyle().getCombatType();
            GameplayTrace.log("combat apply-hit start attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " style=" + style + " damage=" + this.damage + " hitSuccessful=" + this.hitSuccessful + " always=" + this.hitDefinition.isAlwaysHit() + " canTakeDamage=" + this.target.getAttributes().get("canTakeDamage"));
        }
        ItemStack droppedAmmunition = this.hitDefinition.getDroppedAmmunition();
        boolean droppedAmmunitionIsArrow = droppedAmmunition != null
                && ItemDefinition.forId(droppedAmmunition.getId()).getName().toLowerCase().contains("arrow");
        boolean suppressCastleWarsArrowDrop = droppedAmmunitionIsArrow
                && this.attacker != null
                && this.attacker.isPlayer()
                && CastleWarsManager.isInGame((Player)this.attacker);
        if (droppedAmmunition != null
                && !suppressCastleWarsArrowDrop
                && this.attacker != null
                && this.attacker.isPlayer()
                && this.hitDefinition.getChainedTargets().isEmpty()
                && ((Player)(value3 = (Player)this.attacker)).isAmmunitionDropsEnabled()
                && GameUtil.randomInt(5) > 0) {
            value2 = new GroundItem(
                    new ItemStack(droppedAmmunition.getId(), droppedAmmunition.getAmount()),
                    (Entity)value3,
                    this.target.getPosition().copy());
            GroundItemManager.getInstance().spawn((GroundItem)value2);
        }
        if (!this.canTargetTakeDamage()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("combat apply-hit blocked canTakeDamage=false attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " damage=" + this.damage);
            }
            return;
        }
        if (!this.hitDefinition.getChainedTargets().isEmpty()) {
            value3 = (Entity)this.hitDefinition.getChainedTargets().get(0);
            value2 = CombatCycleEvent.validateAttack(this.getAttacker(), (Entity)value3);
            boolean enabled = false;
            if (((Entity)value3).isNpc()) {
                value = (Npc)value3;
                enabled = ((Entity)value).isDoorSupportNpc();
            }
            value = new CombatAction(this.hitDefinition.getChainedSource(), (Entity)value3, this.hitDefinition, this.getTarget());
            this.hitDefinition.getChainedTargets().remove(0);
            this.hitDefinition.setChainedSource(this.getAttacker());
            this.hitDefinition.setChainedTargets(this.hitDefinition.getChainedTargets());
            if (value2 == AttackValidationResult.VALID || enabled) {
                ((CombatAction)value).queue();
            }
        }
        if (this.attacker != null && !this.hitDefinition.isAlwaysHit() && this.hitDefinition.getHitType() != HitType.POISON && this.hitDefinition.getHitType() != HitType.DISEASE) {
            if (this.target.isNpc() && !this.target.isAutoRetaliateDisabled()) {
                value3 = (Npc)this.target;
                if (((Npc)value3).getNpcId() != 1472) {
                    CombatManager.startCombat(this.target, this.attacker);
                }
            } else if (this.target.isPlayer() && !this.target.isMoving()) {
                value3 = (Player)this.target;
                boolean enabled2 = false;
                if (((Entity)value3).getCombatTarget() != null && !((Entity)value3).getCombatTarget().isDead()) {
                    enabled2 = true;
                }
                if (((Player)value3).isAutoRetaliate() && !enabled2) {
                    CombatManager.startCombat(this.target, this.attacker);
                }
            }
        }
        if (this.hitDefinition.getSpell() != null && this.hitDefinition.getSpell() == SpellDefinition.MELZAR_CABBAGE_SPELL) {
            if (this.attacker == null || this.target == null) {
                return;
            }
            Position position = this.target.getPosition();
            int x = position.getX() + GameUtil.randomBetweenInclusive(-1, 1);
            int y = position.getY() + GameUtil.randomBetweenInclusive(-1, 1);
            int plane = position.getPlane();
            GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(1965, 1), this.attacker, this.target, new Position(x, y, plane)));
            if (this.target.isPlayer()) {
                Player player = (Player)this.target;
                value = player;
                player.packetSender.sendStillGraphicToNearbyPlayers(86, x, y, plane, 0);
            }
            return;
        }
        if (this.hitDefinition.isBlockAnimationEnabled() && !this.target.getUpdateState().isAnimationUpdateRequired()) {
            if (this.hitDefinition.getBlockAnimationId() > 0) {
                this.target.getUpdateState().setAnimation(this.hitDefinition.getBlockAnimationId());
            } else if (this.target.isPlayer()) {
                value3 = (Player)this.target;
                if (((Player)value3).npcTransformationId > 1) {
                    this.target.getUpdateState().setAnimation(new Npc(((Player)value3).npcTransformationId).getDefinition().getBlockAnimationId());
                } else {
                    this.target.getUpdateState().setAnimation(this.target.getBlockAnimationId());
                }
            } else {
                this.target.getUpdateState().setAnimation(this.target.getBlockAnimationId());
            }
        }
        if (this.target.isPlayer()) {
            value = value3 = (Player)this.target;
            ((Player)value3).packetSender.closeInterfaces();
        }
        if (this.hitDefinition.getGraphic() != null) {
            this.target.getUpdateState().setGraphic(this.hitDefinition.getGraphic());
        }
        if (this.attacker != null && this.attacker.isPlayer() && this.target.isNpc()) {
            value3 = (Player)this.attacker;
            Npc npc = (Npc)this.target;
            if (npc.getNpcId() >= 1024 && npc.getNpcId() <= 1029 && (this.hitDefinition.getAttackStyle().getCombatType() != CombatType.MELEE || ((Player)value3).getEquipmentManager().getItemIdAtSlot(3) != 2952)) {
                npc.getUpdateState().setGraphic(86, 25);
                npc.transformToNpcIdWithAnimation(npc.getNpcId() + 6, 100000, 38);
                value = value3;
                ((Player)value).packetSender.sendSoundEffect(267, 1, 0);
            }
        }
        if (this.hitDefinition.getAmmunition() != null && this.hitDefinition.getAmmunition() == AmmunitionDefinition.DRAGON_BOLTS_E) {
            this.hitDefinition.setSpecialEffectId(14);
        }
        if (this.hitDefinition.getSpell() != null && (this.hitDefinition.getSpell() == SpellDefinition.CHAOS_ELEMENTAL_RANDOM_TELEPORT || this.hitDefinition.getSpell() == SpellDefinition.CHAOS_ELEMENTAL_DISARM)) {
            if (this.hitDefinition.getSpell().getPostHitEffect() != null) {
                this.hitDefinition.addEffect(this.hitDefinition.getSpell().getPostHitEffect());
            }
            if (this.hitDefinition.getEffects() != null && this.hitDefinition.getEffects().size() > 0) {
                for (Object effectObject : this.hitDefinition.getEffects()) {
                    if (!this.target.canApplyCombatEffect((CombatEffect)effectObject)) continue;
                    ((CombatEffect)effectObject).apply(this);
                }
            }
            if (this.hitDefinition.getEffects() != null && this.hitDefinition.getEffects().size() > 0) {
                for (Object effectObject : this.hitDefinition.getEffects()) {
                    if (effectObject == null) continue;
                    ((CombatEffect)effectObject).afterApply(this);
                }
            }
            return;
        }
        int index = 0;
        if (!(this.hitDefinition.getAttackStyle() == null || this.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.DRAGONFIRE && this.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.DRAGONFIRE_FAR && this.hitDefinition.getAttackStyle().getXpMode() != AttackXpMode.KBD_SPECIAL || this.hitSuccessful)) {
            this.hitSuccessful = true;
            index = 1;
        }
        if (!this.hitSuccessful && !this.hitDefinition.isAlwaysHit()) {
            if (this.damage > 0) {
                this.damage = 0;
            }
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("combat apply-hit miss attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " damage=" + this.damage);
            }
            if (this.hitDefinition.getAttackStyle() != null && this.hitDefinition.getAttackStyle().getCombatType() != CombatType.MAGIC) {
                this.applyHitUpdate();
                return;
            }
            if (this.hitDefinition.getAttackStyle().getCombatType() == CombatType.MAGIC) {
                if (this.hitDefinition.getSpell() != null && (this.hitDefinition.getSpell() == SpellDefinition.FLAMES_OF_ZAMORAK || this.hitDefinition.getSpell() == SpellDefinition.SARADOMIN_STRIKE || this.hitDefinition.getSpell() == SpellDefinition.CLAWS_OF_GUTHIX)) {
                    return;
                }
                if (this.attacker.isPlayer()) {
                    Player player = (Player)this.getAttacker();
                    value = player;
                    player.packetSender.sendSoundEffect(940, 1, 0);
                }
                if (this.target.isPlayer()) {
                    Player player = (Player)this.getTarget();
                    value = player;
                    player.packetSender.sendSoundEffect(940, 1, 0);
                }
            }
            return;
        }
        if (this.attacker != null && this.attacker.isPlayer() && this.target.isNpc()) {
            Player player = (Player)this.attacker;
            if (player.getSlayerManager().requiresFinishingItem((Npc)this.target, true) && this.damage >= this.target.getCurrentHitpoints()) {
                this.damage = this.target.getCurrentHitpoints() - 1;
            }
        }
        if (this.hitDefinition.getAttackStyle() != null) {
            if (this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.ICY_BREATH && this.target.isPlayer()) {
                Player player;
                Player player2 = player = (Player)this.target;
                if (player.getEquipmentManager().getItemIdAtSlot(5) == 11283 || player2.getEquipmentManager().getItemIdAtSlot(5) == 11284 || player2.getEquipmentManager().getItemIdAtSlot(5) == 2890) {
                    value = player;
                    ((Player)value).packetSender.sendGameMessage("Your shield absorbs most of the wyvern's icy breath!");
                    this.damage = GameUtil.randomInt(11);
                }
            }
            if ((this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE || this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE_FAR || this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) && this.target.isNpc()) {
                Npc npc = (Npc)this.target;
                if (npc.getNpcId() == 50 || npc.getNpcId() == 51 || npc.getNpcId() == 52 || npc.getNpcId() == 53 || npc.getNpcId() == 54 || npc.getNpcId() == 55 || npc.getNpcId() == 941 || npc.getNpcId() == 1589 || npc.getNpcId() == 3068 || npc.getNpcId() == 3069 || npc.getNpcId() == 3070 || npc.getNpcId() == 3071) {
                    this.damage = 0;
                }
                if (this.hitDefinition.getEffects() != null) {
                    for (Object effectObject : this.hitDefinition.getEffects()) {
                        CombatEffect combatEffect = (CombatEffect)effectObject;
                        if (!this.target.canApplyCombatEffect(combatEffect)) continue;
                        combatEffect.apply(this);
                    }
                }
            }
            if ((this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE || this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.DRAGONFIRE_FAR || this.hitDefinition.getAttackStyle().getXpMode() == AttackXpMode.KBD_SPECIAL) && this.target.isPlayer()) {
                Player player = (Player)this.target;
                if (index != 0) {
                    value = player;
                    ((Player)value).packetSender.sendGameMessage("You manage to resist some of the dragon fire!");
                } else {
                    value = player;
                    ((Player)value).packetSender.sendGameMessage("You're horribly burnt by the dragon fire!");
                }
                if (!this.attacker.isPlayer() && player.getEquipmentManager().getContainer().getItemAt(5) != null) {
                    int equipmentManager = player.getEquipmentManager().getContainer().getItemAt(5).getMetadata();
                    if (player.getEquipmentManager().getItemIdAtSlot(5) == 11284) {
                        player.getEquipmentManager().getContainer().setItem(5, new ItemStack(11283));
                        player.getEquipmentManager().getContainer().getItemAt(5).setMetadata(1);
                        value = player;
                        ((Player)value).packetSender.sendGameMessage("Your dragonfire shield is recharging.");
                        player.getUpdateState().setAnimation(6695, 0);
                        player.getUpdateState().setGraphic(1164, 25);
                        player.getEquipmentManager().refresh();
                    } else if (player.getEquipmentManager().getItemIdAtSlot(5) == 11283 && equipmentManager < 50) {
                        player.getEquipmentManager().getContainer().getItemAt(5).setMetadata(equipmentManager + 1);
                        value = player;
                        ((Player)value).packetSender.sendGameMessage("Your dragonfire shield is recharging.");
                        player.getUpdateState().setAnimation(6695, 0);
                        player.getUpdateState().setGraphic(1164, 25);
                        player.getEquipmentManager().refresh();
                    }
                }
                if (this.hitDefinition.getEffects() != null) {
                    for (Object effectObject : this.hitDefinition.getEffects()) {
                        CombatEffect combatEffect = (CombatEffect)effectObject;
                        if (!this.target.canApplyCombatEffect(combatEffect)) continue;
                        combatEffect.apply(this);
                    }
                }
            } else if (this.target.isProtectedFrom(this.hitDefinition.getAttackStyle().getCombatType()) && this.hitDefinition.getSpecialEffectId() != 11 && this.hitDefinition.isProtectionPrayerReductionDeferred() && this.damage > 0) {
                this.damage = this.attacker != null && this.attacker.isPlayer() ? (int)Math.ceil((double)this.damage * 0.6) : 0;
            }
        }
        SpecialAttackDefinition.applyHitSpecialEffect(this.attacker, this.target, this.hitDefinition, this.damage);
        int currentHitpoints = this.target.getCurrentHitpoints();
        int hpBefore = currentHitpoints;
        if (this.damage > currentHitpoints) {
            this.damage = currentHitpoints;
        }
        if ((this.damage > 0 || this.hitDefinition.getMaxDamage() <= 0) && this.hitDefinition.getEffects() != null && this.hitDefinition.getEffects().size() > 0) {
            for (Object effectObject : this.hitDefinition.getEffects()) {
                CombatEffect combatEffect = (CombatEffect)effectObject;
                if (combatEffect == null || !this.target.canApplyCombatEffect(combatEffect)) continue;
                combatEffect.apply(this);
            }
        }
        if (this.attacker != null && this.hitDefinition != null && this.attacker.isPlayer() && this.attacker.isPlayer()) {
            Player player = (Player)this.attacker;
            if (player.gameMode == 0 || !this.target.isPlayer()) {
                value = this.hitDefinition.getAttackStyle();
                index = this.damage;
                if (value != null && index > 0) {
                    double value4 = (double)index * 4.0;
                    double xpMode = value4 / (double)((AttackStyleDefinition)value).getXpMode().getSkillIds().length;
                    int[] xpMode2 = ((AttackStyleDefinition)value).getXpMode().getSkillIds();
                    int length = xpMode2.length;
                    int index2 = 0;
                    while (index2 < length) {
                        index = xpMode2[index2];
                        player.getSkillManager().addExperience(index, xpMode);
                        ++index2;
                    }
                    player.getSkillManager().addExperience(3, value4 / 3.0);
                }
            }
        }
        if (this.damage > 0) {
            currentHitpoints -= this.damage;
        }
        if (this.hitDefinition.getAttackAnimationId() != -1) {
            this.target.getUpdateState().setAnimation(this.hitDefinition.getAttackAnimationId());
        }
        if (this.attacker != null && this.damage > 0) {
            Player player;
            DamageContribution damageContribution = this.target.getDamageContribution(this.attacker);
            if (damageContribution == null) {
                damageContribution = new DamageContribution(this.attacker);
            } else {
                this.target.getDamageContributions().remove(damageContribution);
            }
            damageContribution.addDamage(this.damage);
            this.target.getDamageContributions().add(damageContribution);
            if (this.attacker.isPlayer() && (player = (Player)this.attacker).getActivePrayers()[17] && this.target.isPlayer()) {
                PrayerManager.drainPrayerForSmite((Player)this.target, this.damage);
            }
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("combat apply-hit final attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " damage=" + this.damage + " hpBefore=" + hpBefore + " hpAfter=" + currentHitpoints + " hitSuccessful=" + this.hitSuccessful);
        }
        this.target.setCurrentHitpoints(currentHitpoints);
        if (this.target.isPlayer()) {
            int value5;
            Player player = (Player)this.target;
            if (currentHitpoints > 0 && currentHitpoints < (value5 = (int)Math.ceil((double)this.target.getMaxHitpoints() * 0.1))) {
                ItemStack itemStack;
                if (player.getActivePrayers()[16]) {
                    PrayerManager.triggerRedemption(player, this.target, currentHitpoints);
                }
                if ((itemStack = player.getEquipmentManager().getContainer().getItemAt(12)) != null && itemStack.getId() == 2570 && player.getTeleportManager().castItemTeleport(TeleportManager.RESPAWN_TELEPORT_POSITION)) {
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("Your ring shatters!");
                    player.getEquipmentManager().consumeSlotItemAmount(12, 1);
                }
            }
        }
        if (this.hitDefinition.getSpecialEffectId() != 5) {
            this.applyHitUpdate();
        }
    }

    public boolean isReady() {
        return this.delay <= 0;
    }

    public boolean canTargetTakeDamage() {
        return (Boolean)this.target.getAttributes().get("canTakeDamage");
    }

    public Entity getTarget() {
        return this.target;
    }

    public Entity getAttacker() {
        return this.attacker;
    }

    public int getDamage() {
        return this.damage;
    }
}
