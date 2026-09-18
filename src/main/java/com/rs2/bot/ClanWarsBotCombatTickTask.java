package com.rs2.bot;

import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ClanWarsBotCombatTickTask
extends TickTask {
    private final Player player;

    public ClanWarsBotCombatTickTask(int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        int value;
        if (this.player.isDead() || !this.player.isRegistered() || !this.player.isInWilderness()) {
            this.stop();
            return;
        }
        if (this.player.botCombatEscapeActive) {
            if (this.player.getPosition().equals(this.player.botEscapeLastPosition) && this.player.getRecentCombatTimer().hasElapsed()) {
                ++this.player.botEscapeStuckTicks;
            } else {
                this.player.botEscapeLastPosition = this.player.getPosition().copy();
                this.player.botEscapeStuckTicks = 0;
            }
            if (this.player.botEscapeStuckTicks >= 10) {
                ClanWarsBotManager.hideClanWarsBot(this.player);
            }
        }
        if (ClanWarsBotManager.clanWarsRetreatActive && !this.player.botCombatEscapeActive) {
            BotCombatEscapeHandler.tryStartBotCombatEscape(this.player);
            return;
        }
        if (BotCombatHelper.hasExternalCombatTarget(this.player) && this.player.botCombatState == null && !this.player.botCombatEscapeActive && (value = GameUtil.getDistance(this.player.getPosition(), ClanWarsBotManager.clanWarsRallyPosition)) > 20) {
            CombatManager.stopCombat(this.player);
            BotCombatHelper.walkBotTowardPosition(this.player, ClanWarsBotManager.clanWarsRallyPosition);
            return;
        }
        if (!BotCombatHelper.hasExternalCombatTarget(this.player) && this.player.botCombatState == null && !this.player.botCombatEscapeActive) {
            if (!this.player.isInWilderness()) {
                ClanWarsBotManager.hideClanWarsBot(this.player);
            }
            if (this.player.getEquipmentManager().getItemIdAtSlot(3) != this.player.botWeaponItemId) {
                this.player.getEquipmentManager().equipFromInventorySlot(this.player.getInventoryManager().getContainer().indexOfItem(this.player.botWeaponItemId));
                if (this.player.botShieldItemId != 0 && this.player.getEquipmentManager().getItemIdAtSlot(5) != this.player.botShieldItemId) {
                    this.player.getEquipmentManager().equipFromInventorySlot(this.player.getInventoryManager().getContainer().indexOfItem(this.player.botShieldItemId));
                }
                this.player.botActiveCombatStyle = this.player.botPrimaryCombatStyle;
            }
            if (this.player.botMagicPenaltyGearUnequipped) {
                BotCombatHelper.reequipMagicPenaltyGear(this.player);
            }
            if (this.player.getMovementQueue().isRunning()) {
                this.player.getMovementQueue().setRunning(false);
            }
            BotCombatHelper.disableBotCombatPrayers(this.player);
            this.player.botMagicGearSwapDelayTicks = 0;
            this.player.botThreatEscapeDelayTicks = 0;
            this.player.botPrayerSwitchDelayTicks = 0;
            this.player.botQueuedPrayerId = -1;
            this.player.botEatDelayTicks = 0;
            this.player.botWeaponSwapDelayTicks = 0;
            if (!(this.player.getCombatTarget() == null || this.player.getCombatTarget().isDead() || this.player.botCombatTickTask != null && this.player.botCombatTickTask.isActive())) {
                this.player.getMovementQueue().setRunning(true);
                CombatManager.startCombat(this.player, this.player.getCombatTarget());
            }
            FoodDefinition foodDefinition = FoodDefinition.forItemId(this.player.botFoodItemId);
            int healAmount = foodDefinition.getHealAmount();
            int skillManager = this.player.getSkillManager().getCurrentLevels()[3] + healAmount;
            this.player.getSkillManager();
            int levelForExperience = healAmount = skillManager <= SkillManager.getLevelForExperience(this.player.getSkillManager().getExperience()[3]) ? 1 : 0;
            if (!this.player.botFoodDepleted && healAmount != 0) {
                int value2 = healAmount = this.player.isTeleblocked() ? 6 : 4;
                if (!BotCombatHelper.eatBotFood(this.player) || this.player.getInventoryManager().getItemAmount(this.player.botFoodItemId) <= healAmount) {
                    BotCombatEscapeHandler.tryStartBotCombatEscape(this.player);
                }
            }
            if (this.player.getPoisonDamage() > 0.0) {
                BotCombatHelper.drinkAntipoisonPotion(this.player);
            }
            healAmount = 0;
            Player player = ClanWarsBotManager.findClanWarsOpponent(this.player);
            if (player != null && !player.isDead()) {
                healAmount = 1;
                if (!this.player.getMovementQueue().isRunning()) {
                    this.player.getMovementQueue().setRunning(true);
                }
                if (GameUtil.randomInt(3) == 0) {
                    this.player.queuePublicChatMessage("atk " + player.getUsername());
                }
                CombatManager.startCombat(this.player, player);
            }
            if (healAmount == 0) {
                Position position = ClanWarsBotManager.clanWarsRallyPosition;
                if (this.player.getPosition().getY() >= 3885 && this.player.getPosition().getY() <= 3901) {
                    position.setY(this.player.getPosition().getY());
                }
                BotCombatHelper.walkBotTowardPosition(this.player, position);
            }
        }
    }
}

