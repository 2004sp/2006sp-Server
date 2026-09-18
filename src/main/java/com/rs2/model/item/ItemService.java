package com.rs2.model.item;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.LootNextItemTask;
import com.rs2.model.item.PickupItemTask;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;

public final class ItemService {
    private static ItemService instance = new ItemService();
    static PathReachability pathReachability = new PathReachability();

    public static boolean isEssencePouch(int value2) {
        if (value2 >= 5510 && value2 <= 5515) {
            return true;
        }
        return value2 == 5519;
    }

    public final void pickupItem(Player player, int itemId, Position position) {
        int value = player.nextActionSequence();
        World.scheduleTickTask(new PickupItemTask(this, 1, true, player, value, itemId, position));
    }

    public final void handleBotGroundItemPickupResult(Player player, int itemId, GroundItem groundItem, boolean itemId2) {
        int equipmentManager = player.getEquipmentManager().getItemIdAtSlot(3);
        boolean enabled = false;
        if (equipmentManager > 0) {
            enabled = ItemDefinition.forId(equipmentManager).isStackable();
        }
        if (itemId == player.getEquipmentManager().getItemIdAtSlot(13) || itemId == player.getEquipmentManager().getItemIdAtSlot(3) && enabled) {
            if (itemId2) {
                player.getEquipmentManager().equipFromInventorySlot(player.getInventoryManager().getContainer().indexOfItem(itemId));
            }
            if (player.botCombatState != null && player.botCombatState.equals("loot arrows")) {
                player.botCombatState = null;
                if (player.botLootResumeTarget != null && !player.botLootResumeTarget.isDead()) {
                    CombatManager.startCombat(player, player.botLootResumeTarget);
                } else if (player.currentBotTask != null) {
                    player.interactWithBotNpcTargets(player.botInteractionTargetIds);
                }
            }
        }
        if (player.botCombatState != null && player.botCombatState.equals("loot items")) {
            player.botLootPickupTargets.remove(0);
            if (player.currentBotTask != null) {
                if (groundItem.getItem().getDefinition().getName().toLowerCase().endsWith("bones")) {
                    if (itemId2) {
                        player.getBoneBuryingHandler().handleBuryBone(itemId, player.getInventoryManager().getContainer().indexOfItem(itemId));
                    }
                    if (player.botLootPickupTargets.size() > 0) {
                        LootNextItemTask lootNextItemTask = new LootNextItemTask(this, 1, player);
                        World.getTaskScheduler().schedule(lootNextItemTask);
                        return;
                    }
                } else if (player.currentBotTask.lootSellShopIds.size() > 0 && player.botLootSellGroundItems.contains(groundItem) && itemId2) {
                    player.botLootSellItems.add(groundItem.getItem());
                    player.botLootSellGroundItems.remove(groundItem);
                }
            }
            if (player.currentBotTask == null && !player.clanWarsBot && player.botLootSellGroundItems.contains(groundItem) && itemId2) {
                player.botLootSellItems.add(groundItem.getItem());
                player.botLootSellGroundItems.remove(groundItem);
            }
            if (player.botLootPickupTargets.size() > 0) {
                BotCombatHelper.pickupBotCombatGroundItem(player, ((GroundItem)player.botLootPickupTargets.get(0)).getItem().getId(), ((GroundItem)player.botLootPickupTargets.get(0)).getPosition());
                return;
            }
            player.botCombatState = null;
            player.botLootGroundItems.clear();
            player.botLootPickupTargets.clear();
            if (player.currentBotTask != null) {
                GameplayHelper.shouldReturnToBankForBotTask(player);
                int value = itemId = player.isInWilderness() ? 8 : 0;
                if (player.getInventoryManager().getItemAmount(player.botFoodItemId) <= itemId && player.currentBotTask.getForcedCombatStyle() != 2 || player.botTaskReturnToBankRequested) {
                    player.currentBotTask.startWalkToBank(player);
                    return;
                }
                player.interactWithBotNpcTargets(player.botInteractionTargetIds);
            }
        }
    }

    public static int getPrice(int value4, String text2, int value22) {
        double value3 = 0.0;
        if (text2.equals("donator")) {
            value3 = new ItemStack(value4).getDefinition().getDonatorPointValue();
        } else if (text2.equals("buyfromshop")) {
            if (value22 == 995) {
                value3 = new ItemStack(value4).getDefinition().getShopValue();
            }
            if (value22 == 6529) {
                value3 = new ItemStack(value4).getDefinition().getTokkulValue();
            }
            value3 = value3 < 1.0 ? 1.0 : value3;
        } else if (text2.startsWith("selltoshop")) {
            if (value22 == 995) {
                value3 = text2.contains("Specialty") ? (double)new ItemStack(value4).getDefinition().getHighAlchemyValue() : (double)new ItemStack(value4).getDefinition().getLowAlchemyValue();
            }
            if (value22 == 6529) {
                value3 = new ItemStack(value4).getDefinition().getTokkulValue() * 15 / 100;
            }
        } else if (text2.equals("lowalch") && value22 == 995) {
            value3 = new ItemStack(value4).getDefinition().getLowAlchemyValue();
        } else if (text2.equals("highalch") && value22 == 995) {
            value3 = new ItemStack(value4).getDefinition().getHighAlchemyValue();
        }
        return (int)value3;
    }

    public static String getItemName(int itemId) {
        return new ItemStack(itemId).getDefinition().getName();
    }

    public static ItemService getInstance() {
        return instance;
    }
}

