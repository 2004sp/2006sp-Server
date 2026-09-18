package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AmmunitionDefinition;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.requirement.AmmunitionRequirement;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;

public class WeaponCombatAttack extends BaseCombatAttack {
    private WeaponProfile weaponProfile;
    private int attackStyleIndex;
    private AttackStyleDefinition attackStyle;
    private AmmunitionDefinition ammunition;
    private SpecialAttackDefinition specialAttack;
    private boolean cancelled;
    private PoisonEffect poisonEffect;
    private int droppedAmmunitionId;
    private int droppedAmmunitionAmount;
    private static PoisonEffect MELEE_POISON_EFFECT = new PoisonEffect(4.0);
    private static PoisonEffect RANGED_POISON_EFFECT = new PoisonEffect(2.0);
    private static PoisonEffect MELEE_POISON_PLUS_EFFECT = new PoisonEffect(5.0);
    private static PoisonEffect RANGED_POISON_PLUS_EFFECT = new PoisonEffect(3.0);
    private static PoisonEffect MELEE_POISON_PLUS_PLUS_EFFECT = new PoisonEffect(6.0);
    private static PoisonEffect RANGED_POISON_PLUS_PLUS_EFFECT = new PoisonEffect(4.0);
    private static PoisonEffect KARAMBWAN_POISON_EFFECT = new PoisonEffect(6.0);

    public WeaponCombatAttack(Player player, Entity target, WeaponProfile weaponProfile) {
        super(player, target);
        this.weaponProfile = weaponProfile;
        this.attackStyleIndex = player.getFightMode();
        this.attackStyle = weaponProfile.getInterfaceDefinition().getAttackStyles()[this.attackStyleIndex];
        this.droppedAmmunitionId = -1;
    }

    public final AmmunitionDefinition getAmmunition() {
        return this.ammunition;
    }

    public final double calculateMaxHit() {
        return CombatManager.calculateWeaponMaxHit((Player)this.getAttacker(), this);
    }

    public final AttackStyleDefinition getAttackStyle() {
        return this.attackStyle;
    }

    @Override
    public final CombatType getCombatType() {
        return this.attackStyle.getCombatType();
    }

    @Override
    public final void prepare() {
        Player player = (Player)this.getAttacker();
        if (this.attackStyle.getCombatType() == CombatType.MELEE && this.getTarget().isDoorSupportNpc()) {
            this.cancelled = true;
            CombatManager.stopCombat(player);
            return;
        }
        ItemStack weaponItem = player.getEquipmentManager().getContainer().getItemAt(3);
        String weaponName = "";
        if (weaponItem != null) {
            weaponName = ItemDefinition.forId(weaponItem.getId()).getName().toLowerCase();
        }
        if (this.attackStyle.getCombatType() == CombatType.RANGED && weaponName.contains("crystal bow")) {
            this.ammunition = AmmunitionDefinition.CRYSTAL_BOW_ARROW;
        } else if (this.attackStyle.getCombatType() == CombatType.RANGED && !weaponName.contains("crystal bow")) {
            this.ammunition = AmmunitionDefinition.findEquippedAmmunition(player, this.weaponProfile, true);
            if (this.ammunition == null) {
                this.cancelled = true;
                return;
            }
            int slot = this.weaponProfile.getAmmunitionProfile().getEquipmentSlot();
            this.droppedAmmunitionId = player.getEquipmentManager().getItemIdAtSlot(slot);
            this.droppedAmmunitionAmount = 1;
            this.setRequirements(new CombatRequirement[]{new AmmunitionRequirement(this, slot, this.droppedAmmunitionId, this.droppedAmmunitionAmount, true)});
            this.poisonEffect = WeaponCombatAttack.getWeaponPoisonEffect(player, CombatType.RANGED, slot);
        } else if (this.attackStyle.getCombatType() == CombatType.MELEE) {
            this.poisonEffect = WeaponCombatAttack.getWeaponPoisonEffect(player, CombatType.MELEE, 3);
        }
        double maxHit = this.calculateMaxHit();
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("weapon prepare player=" + GameplayTrace.describe(player) + " target=" + GameplayTrace.describe(this.getTarget()) + " profile=" + this.weaponProfile + " style=" + this.attackStyle.getXpMode() + "/" + this.attackStyle.getCombatType() + " maxHit=" + maxHit + " attackStyleIndex=" + this.attackStyleIndex);
        }
        HitDefinition[] hitDefinitions;
        if (this.weaponProfile == WeaponProfile.DARK_BOW) {
            ProjectileTiming timing = this.weaponProfile.getAmmunitionProfile().getProjectileTiming();
            ProjectileDefinition firstProjectile = new ProjectileDefinition(this.ammunition.getProjectileId(), timing.copy().setStartDelay(40).setSpeed(3));
            ProjectileDefinition secondProjectile = new ProjectileDefinition(this.ammunition.getProjectileId(), timing.copy().setStartDelay(41).setSpeed(2));
            HitDefinition firstHit = new HitDefinition(this.attackStyle, HitType.NORMAL, maxHit).enableRandomDamage().enableAccuracyCheck().setProjectile(firstProjectile);
            HitDefinition secondHit = new HitDefinition(this.attackStyle, HitType.NORMAL, maxHit).enableRandomDamage().enableAccuracyCheck().setProjectile(secondProjectile);
            hitDefinitions = new HitDefinition[]{firstHit, secondHit};
        } else {
            ProjectileDefinition projectile = null;
            if (this.ammunition != null) {
                projectile = new ProjectileDefinition(this.ammunition.getProjectileId(), this.weaponProfile.getAmmunitionProfile().getProjectileTiming());
            }
            HitDefinition hitDefinition = new HitDefinition(this.attackStyle, HitType.NORMAL, maxHit).enableRandomDamage().enableAccuracyCheck().setProjectile(projectile);
            if (this.ammunition != null) {
                hitDefinition.setAmmunition(this.ammunition);
            }
            hitDefinitions = new HitDefinition[]{hitDefinition};
        }
        this.setHitDefinitions(hitDefinitions);
        int delayModifier = 0;
        if (this.attackStyle.getXpMode() == AttackXpMode.RAPID) {
            delayModifier = -1;
        }
        this.setAttackDelay(this.weaponProfile.getAttackDelay() + delayModifier);
        this.setAttackerGraphic(this.ammunition != null && this.weaponProfile.getAmmunitionProfile() != null ? (this.weaponProfile == WeaponProfile.DARK_BOW ? new GraphicEffect(this.ammunition.getAlternateGraphicId(), this.weaponProfile.getAmmunitionProfile().getGraphicDelay()) : new GraphicEffect(this.ammunition.getGraphicId(), this.weaponProfile.getAmmunitionProfile().getGraphicDelay())) : null);
        this.setAnimationId(this.weaponProfile.getAttackAnimations()[this.attackStyleIndex]);
        this.setAttackSoundId(this.weaponProfile.getInterfaceDefinition().attackSoundId);
        this.cancelled = !this.prepareSpecialAttack();
    }

    @Override
    public final int getAttackRange() {
        switch (this.attackStyle.getCombatType()) {
            case RANGED: {
                if (this.weaponProfile == WeaponProfile.LONG_BOW || this.weaponProfile == WeaponProfile.SPECIAL_BOW || this.weaponProfile == WeaponProfile.CRYSTAL_BOW) {
                    if (this.attackStyle.getXpMode() == AttackXpMode.LONGRANGE) {
                        return 11;
                    }
                    return 10;
                }
                if (this.attackStyle.getXpMode() == AttackXpMode.LONGRANGE) {
                    return 9;
                }
                return 7;
            }
            case MAGIC: {
                if (this.attackStyle.getXpMode() == AttackXpMode.DRAGONFIRE) {
                    return 1;
                }
                return 10;
            }
            case MELEE: {
                if (this.weaponProfile == WeaponProfile.HALBERD) {
                    return 2;
                }
                return 1;
            }
        }
        return 1;
    }

    public boolean prepareSpecialAttack() {
        return true;
    }

    @Override
    public final CombatAttackState getState() {
        SpecialAttackDefinition specialAttackDefinition = ((Player)this.getAttacker()).getSpecialAttackDefinition();
        boolean specialAttackEnabled = ((Player)this.getAttacker()).isSpecialAttackEnabled();
        if (specialAttackDefinition != null && specialAttackEnabled) {
            Player player = (Player)this.getAttacker();
            this.specialAttack = specialAttackDefinition;
            if (player.getSpecialEnergy() < specialAttackDefinition.getEnergyCost()) {
                player.packetSender.sendGameMessage("You have no special energy left.");
                return CombatAttackState.a;
            }
            if (DuelRule.NO_SPECIAL_ATTACK.isEnabledFor(player)) {
                player.packetSender.sendGameMessage("Special attacks have been disabled during this fight!");
                return CombatAttackState.a;
            }
        }
        return super.getState();
    }

    @Override
    public int execute(CycleEventContainer cycleEventContainer) {
        Player player = (Player)this.getAttacker();
        if (player.isSpecialAttackEnabled() && this.specialAttack != null) {
            player.setSpecialEnergy(player.getSpecialEnergy() - this.specialAttack.getEnergyCost());
            player.refreshSpecialAttackWidgets();
            player.setSpecialAttackEnabled(false);
            player.refreshSpecialAttackWidgets();
        }
        if (this.getHitDefinitions() != null) {
            for (HitDefinition hitDefinition : this.getHitDefinitions()) {
                if (this.droppedAmmunitionId != -1) {
                    hitDefinition.setDroppedAmmunition(new ItemStack(this.droppedAmmunitionId, this.droppedAmmunitionAmount));
                }
                if (this.poisonEffect != null) {
                    if (this.attackStyle.getCombatType() == CombatType.MELEE) {
                        if (GameUtil.rollChance(0.25)) {
                            hitDefinition.addEffect(this.poisonEffect);
                        }
                    } else if (this.attackStyle.getCombatType() == CombatType.RANGED && GameUtil.rollChance(0.125)) {
                        hitDefinition.addEffect(this.poisonEffect);
                    }
                }
            }
        }
        return super.execute(cycleEventContainer);
    }

    private static PoisonEffect getWeaponPoisonEffect(Player player, CombatType combatType, int slot) {
        if (combatType == CombatType.MAGIC) {
            return null;
        }
        ItemStack itemStack = player.getEquipmentManager().getContainer().getItemAt(slot);
        if (itemStack == null) {
            return null;
        }
        String itemName = ItemDefinition.forId(itemStack.getId()).getName().toLowerCase();
        if (itemName.contains("(kp)")) {
            return KARAMBWAN_POISON_EFFECT;
        }
        if (itemName.contains("(s)") || itemName.contains("(p++)") || itemName.contains("++")) {
            if (combatType == CombatType.MELEE) {
                return MELEE_POISON_PLUS_PLUS_EFFECT;
            }
            return RANGED_POISON_PLUS_PLUS_EFFECT;
        }
        if (itemName.contains("(+)") || itemName.contains("(p+)")) {
            if (combatType == CombatType.MELEE) {
                return MELEE_POISON_PLUS_EFFECT;
            }
            return RANGED_POISON_PLUS_EFFECT;
        }
        if (itemName.contains("(p)")) {
            if (combatType == CombatType.MELEE) {
                return MELEE_POISON_EFFECT;
            }
            return RANGED_POISON_EFFECT;
        }
        return null;
    }
}
