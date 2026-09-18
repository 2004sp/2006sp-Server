package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

public final class BotGroupCombatTickTask
extends TickTask {
    private final Player bot;

    public BotGroupCombatTickTask(int value2, Player player) {
        super(1);
        this.bot = player;
    }

    @Override
    public final void execute() {
        if (this.bot.isDead() || !this.bot.isRegistered() || !this.bot.botEnabled) {
            this.stop();
            return;
        }
        if (this.bot.botCombatEscapeActive) {
            if (this.bot.getPosition().equals(this.bot.botEscapeLastPosition) && this.bot.getRecentCombatTimer().hasElapsed()) {
                ++this.bot.botEscapeStuckTicks;
            } else {
                this.bot.botEscapeLastPosition = this.bot.getPosition().copy();
                this.bot.botEscapeStuckTicks = 0;
            }
        }
        if (!BotCombatHelper.hasExternalCombatTarget(this.bot) && this.bot.botCombatState == null && !this.bot.botCombatEscapeActive) {
            if (this.bot.getEquipmentManager().getItemIdAtSlot(3) != this.bot.botWeaponItemId) {
                this.bot.getEquipmentManager().equipFromInventorySlot(this.bot.getInventoryManager().getContainer().indexOfItem(this.bot.botWeaponItemId));
                if (this.bot.botShieldItemId != 0 && this.bot.getEquipmentManager().getItemIdAtSlot(5) != this.bot.botShieldItemId) {
                    this.bot.getEquipmentManager().equipFromInventorySlot(this.bot.getInventoryManager().getContainer().indexOfItem(this.bot.botShieldItemId));
                }
                this.bot.botActiveCombatStyle = this.bot.botPrimaryCombatStyle;
            }
            if (this.bot.botMagicPenaltyGearUnequipped) {
                BotCombatHelper.reequipMagicPenaltyGear(this.bot);
            }
            BotCombatHelper.disableBotCombatPrayers(this.bot);
            this.bot.botMagicGearSwapDelayTicks = 0;
            this.bot.botThreatEscapeDelayTicks = 0;
            this.bot.botPrayerSwitchDelayTicks = 0;
            this.bot.botQueuedPrayerId = -1;
            this.bot.botEatDelayTicks = 0;
            this.bot.botWeaponSwapDelayTicks = 0;
            if (!(this.bot.getCombatTarget() == null || this.bot.getCombatTarget().isDead() || this.bot.botCombatTickTask != null && this.bot.botCombatTickTask.isActive())) {
                this.bot.getMovementQueue().setRunning(true);
                CombatManager.startCombat(this.bot, this.bot.getCombatTarget());
            }
            FoodDefinition foodDefinition = FoodDefinition.forItemId(this.bot.botFoodItemId);
            int healAmount = foodDefinition.getHealAmount();
            int skillManager = this.bot.getSkillManager().getCurrentLevels()[3] + healAmount;
            this.bot.getSkillManager();
            int levelForExperience = healAmount = skillManager <= SkillManager.getLevelForExperience(this.bot.getSkillManager().getExperience()[3]) ? 1 : 0;
            if (!this.bot.botFoodDepleted && healAmount != 0) {
                int value = healAmount = this.bot.isTeleblocked() ? 6 : 4;
                if (!BotCombatHelper.eatBotFood(this.bot) || this.bot.getInventoryManager().getItemAmount(this.bot.botFoodItemId) <= healAmount) {
                    BotCombatEscapeHandler.tryStartBotCombatEscape(this.bot);
                }
            }
            if (this.bot.getPoisonDamage() > 0.0) {
                BotCombatHelper.drinkAntipoisonPotion(this.bot);
            }
            if (this.bot.currentGroup != null && this.bot.currentGroup.leader != this.bot) {
                if (this.bot.getMovementTarget() == this.bot.currentGroup.leader && this.bot.getMovementQueue().isRunning() != this.bot.currentGroup.leader.getMovementQueue().isRunning()) {
                    this.bot.getMovementQueue().setRunning(this.bot.currentGroup.leader.getMovementQueue().isRunning());
                }
                if (this.bot.getMovementTarget() == null) {
                    this.bot.currentGroup.refreshGroupFollowChain();
                }
                if (this.bot.currentGroup.leader.getCombatTarget() != null && !this.bot.currentGroup.leader.getCombatTarget().isDead()) {
                    this.bot.getMovementQueue().setRunning(true);
                    CombatManager.startCombat(this.bot, this.bot.currentGroup.leader.getCombatTarget());
                }
            }
            if (this.bot.isInWilderness() && this.bot.currentGroup == null) {
                BotCombatEscapeHandler.tryStartBotCombatEscape(this.bot);
            }
        }
    }
}

