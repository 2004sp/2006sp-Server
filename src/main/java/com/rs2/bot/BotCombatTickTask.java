package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

public final class BotCombatTickTask
extends TickTask {
    private BotTaskDefinition taskDefinition;
    private final Npc targetNpc;
    private final Player bot;

    public BotCombatTickTask(BotTaskDefinition botTaskDefinition, int value2, Npc npc, Player player) {
        super(1);
        this.taskDefinition = botTaskDefinition;
        this.targetNpc = npc;
        this.bot = player;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void execute() {
        boolean enabled;
        Player player;
        Npc npc;
        executeControlExit1: {
            executeControlExit2: {
                BotTaskDefinition botTaskDefinition;
                executeControlExit3: {
                    if (this.targetNpc.isDead()) {
                        CombatManager.stopCombat(this.bot);
                        this.stop();
                        return;
                    }
                    if (this.bot.isDead() || !this.bot.isRegistered()) {
                        CombatManager.stopCombat(this.bot);
                        this.stop();
                        return;
                    }
                    if (this.bot.getCombatTarget() == null) {
                        CombatManager.stopCombat(this.bot);
                        this.stop();
                        return;
                    }
                    if (!this.bot.botTaskState.equals("do task")) {
                        CombatManager.stopCombat(this.bot);
                        this.stop();
                        return;
                    }
                    npc = this.targetNpc;
                    player = this.bot;
                    botTaskDefinition = this.taskDefinition;
                    if (player.botCombatState != null && player.botCombatState.equals("escape")) {
                        CombatManager.stopCombat(player);
                        botTaskDefinition.startWalkToBank(player);
                        return;
                    }
                    if (player.botCombatState != null) return;
                    enabled = false;
                    player.getSkillManager();
                    int levelForExperience = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
                    int skillManager = player.getSkillManager().getCurrentLevels()[3];
                    FoodDefinition foodDefinition = FoodDefinition.forItemId(player.botFoodItemId);
                    if (foodDefinition == null) {
                        CombatManager.stopCombat(player);
                        botTaskDefinition.startWalkToBank(player);
                        return;
                    }
                    int healAmount = foodDefinition.getHealAmount();
                    if (skillManager + healAmount < levelForExperience) {
                        enabled = true;
                    }
                    if (!enabled) break executeControlExit1;
                    if (!BotCombatHelper.eatBotFood(player)) break executeControlExit3;
                    if (player.getInventoryManager().getItemAmount(player.botFoodItemId) > 2) break executeControlExit2;
                    BotTaskDefinition botTaskDefinition2 = botTaskDefinition;
                    if (botTaskDefinition2.forcedCombatStyle == 2) break executeControlExit2;
                }
                CombatManager.stopCombat(player);
                botTaskDefinition.startWalkToBank(player);
                return;
            }
            if (npc != null && !npc.isDead()) {
                CombatManager.startCombat(player, npc);
                return;
            }
            player.interactWithBotNpcTargets(player.botInteractionTargetIds);
            return;
        }
        int equipmentManager = player.getEquipmentManager().getItemIdAtSlot(3);
        enabled = false;
        if (equipmentManager > 0) {
            enabled = ItemDefinition.forId(equipmentManager).isStackable();
        }
        if (player.getEquipmentManager().getItemIdAtSlot(13) == 0) {
            if (!enabled) return;
        }
        equipmentManager = enabled ? 3 : 13;
        GroundItemManager.getInstance();
        Object visibleItem = GroundItemManager.findVisibleItem(player, player.getEquipmentManager().getItemIdAtSlot(equipmentManager), npc.getPosition());
        if (visibleItem == null) return;
        if (((ItemStack)(visibleItem = ((GroundItem)visibleItem).getItem())).getAmount() >= 6 && !player.isMovementLocked()) {
            player.setAutoRetaliate(false);
            CombatManager.stopCombat(player);
            player.botCombatState = "loot arrows";
            player.getMovementQueue().setRunning(true);
            player.botLootResumeTarget = npc;
            BotCombatHelper.pickupBotCombatGroundItem(player, player.getEquipmentManager().getItemIdAtSlot(equipmentManager), npc.getPosition());
            return;
        }
        if (player.getEquipmentManager().getContainer().getItemAt(equipmentManager).getAmount() > 3) return;
        if (player.isMovementLocked()) return;
        player.setAutoRetaliate(false);
        CombatManager.stopCombat(player);
        player.botCombatState = "loot arrows";
        player.getMovementQueue().setRunning(true);
        player.botLootResumeTarget = npc;
        BotCombatHelper.pickupBotCombatGroundItem(player, player.getEquipmentManager().getItemIdAtSlot(equipmentManager), npc.getPosition());
    }
}
