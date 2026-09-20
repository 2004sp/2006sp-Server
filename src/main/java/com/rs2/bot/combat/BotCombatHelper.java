package com.rs2.bot.combat;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotGroundItemPickupTask;
import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.PotionHandler;
import com.rs2.model.item.consumable.PotionDefinition;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerGroup;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import com.rs2.util.Vector2f;
import com.rs2.util.path.PathFinder;
import com.rs2.util.path.WalkingCollisionMap;
import java.util.ArrayList;
import java.util.Iterator;

public final class BotCombatHelper {
    private static RectangularArea[] blockedPvpSearchAreas = new RectangularArea[]{new RectangularArea(3176, 3846, 3190, 3859), new RectangularArea(3226, 3817, 3236, 3828), new RectangularArea(3306, 3852, 3315, 3862)};
    private static RectangularArea[] freeToPlayBlockedPvpSearchAreas = new RectangularArea[]{new RectangularArea(3007, 3839, 3023, 3856), new RectangularArea(3170, 3798, 3240, 3863), new RectangularArea(3061, 3851, 3072, 3864), new RectangularArea(3078, 3560, 3099, 3581)};
    private static RectangularArea[] hotzonePvpSearchAreas = new RectangularArea[]{new RectangularArea(3225, 3521, 3275, 3551), new RectangularArea(3068, 3521, 3103, 3551)};

    public static void disableBotCombatPrayers(Player player) {
        if (player.getActivePrayers()[10]) {
            player.getPrayerManager().togglePrayer(10);
        }
        if (player.getActivePrayers()[8]) {
            player.getPrayerManager().togglePrayer(8);
        }
        if (player.getActivePrayers()[14]) {
            player.getPrayerManager().togglePrayer(14);
        }
        if (player.getActivePrayers()[13]) {
            player.getPrayerManager().togglePrayer(13);
        }
        if (player.getActivePrayers()[12]) {
            player.getPrayerManager().togglePrayer(12);
        }
        if (player.getActivePrayers()[17]) {
            player.getPrayerManager().togglePrayer(17);
        }
    }

    public static void setBotSkillLevel(Player player, int level, int value2) {
        if (value2 > 99) {
            value2 = 99;
        }
        if (value2 <= 0) {
            value2 = 1;
        }
        player.executeCheatCommand("setlevel", new String[]{String.valueOf(level), String.valueOf(value2)}, false);
    }

    public static boolean isPlayerInAnyArea(Player player, RectangularArea[] areas) {
        int length = areas.length;
        int index = 0;
        while (index < length) {
            RectangularArea area = areas[index];
            if (area.contains(player.getPosition())) {
                return true;
            }
            ++index;
        }
        return false;
    }

    private static boolean isPositionInAnyArea(Position position, RectangularArea[] areas) {
        int length = areas.length;
        int index = 0;
        while (index < length) {
            RectangularArea area = areas[index];
            if (area.contains(position)) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static void advanceBotEscapeWaypoints(Player player, Position[] positionArray) {
        Position position = positionArray[player.botPathWaypointIndex];
        if (GameUtil.isWithinDistance(player.getPosition(), position, 1)) {
            if (player.botPathWaypointIndex == positionArray.length - 1) {
                player.botEscapeRouteName = "";
                player.botPathWaypointIndex = -1;
                return;
            }
            ++player.botPathWaypointIndex;
            position = positionArray[player.botPathWaypointIndex];
        }
        BotCombatHelper.walkBotTowardPosition(player, position);
    }

    public static void restorePrimaryCombatGear(Player player) {
        if (player.getEquipmentManager().getItemIdAtSlot(3) != player.botWeaponItemId) {
            int weaponSlot = player.getInventoryManager().getContainer().indexOfItem(player.botWeaponItemId);
            if (weaponSlot >= 0) {
                player.getEquipmentManager().equipFromInventorySlot(weaponSlot);
            }
        }
        if (player.botShieldItemId != 0 && player.getEquipmentManager().getItemIdAtSlot(5) != player.botShieldItemId) {
            int shieldSlot = player.getInventoryManager().getContainer().indexOfItem(player.botShieldItemId);
            if (shieldSlot >= 0) {
                player.getEquipmentManager().equipFromInventorySlot(shieldSlot);
            }
        }
        player.botActiveCombatStyle = player.botPrimaryCombatStyle;
        BotCombatHelper.syncPrimaryMagicAutocast(player);
    }

    public static void syncPrimaryMagicAutocast(Player player) {
        if (player == null || !player.botEnabled
                || player.botPrimaryCombatStyle != BotPvpCombatHandler.MAGIC_COMBAT_STYLE) {
            return;
        }

        boolean primaryMagicWeaponEquipped = player.botWeaponItemId > 0
                && player.getEquipmentManager().getItemIdAtSlot(3) == player.botWeaponItemId;
        if (!primaryMagicWeaponEquipped) {
            if (player.getAutocastSpell() != null || player.isAutocastEnabled()) {
                player.setAutocastSpell(null);
            }
            return;
        }

        SpellDefinition preferredSpell = player.botPrimaryAutocastSpell;
        if (preferredSpell == null
                || preferredSpell.getRequiredLevel() > player.getSkillManager().getCurrentLevels()[6]
                || !BotCombatHelper.hasRunesForSpell(player, preferredSpell)) {
            return;
        }

        player.botActiveCombatStyle = BotPvpCombatHandler.MAGIC_COMBAT_STYLE;
        if (player.getAutocastSpell() != preferredSpell || !player.isAutocastEnabled()) {
            player.setAutocastSpell(preferredSpell);
        }
    }

    public static double calculateBotHitpointsExperience(Player player) {
        double skillManager = player.getSkillManager().getExperience()[0] + player.getSkillManager().getExperience()[1] + player.getSkillManager().getExperience()[2] + player.getSkillManager().getExperience()[4] + player.getSkillManager().getExperience()[6];
        double value = skillManager / 3.0 + 1154.0;
        return value;
    }

    public static void sellBotLootItems(Player player) {
        if (player.botLootSellItems.size() > 0) {
            int value;
            if (player.currentBotTask == null) {
                value = GameUtil.randomInt(3) == 0 ? 151 : 50;
            } else {
                BotTaskDefinition botTaskDefinition = player.currentBotTask;
                int value2 = GameUtil.randomInt(botTaskDefinition.lootSellShopIds.size());
                value = (Integer)botTaskDefinition.lootSellShopIds.get(value2);
            }
            ShopManager.openShop(player, value);
            for (Object itemStackObject : player.botLootSellItems) {
                ItemStack itemStack = (ItemStack)itemStackObject;
                int guidePrice = GrandExchangeManager.getGuidePrice(itemStack.getId());
                if (guidePrice >= 10000) continue;
                ShopManager.sellItemStack(player, itemStack);
            }
            player.botLootSellItems.clear();
            player.botLootSellGroundItems.clear();
        }
    }

    public static boolean processBotLootQueue(Player player) {
        if (player.botLootGroundItems.size() <= 0) {
            player.botCombatState = null;
            if (player.currentBotTask != null) {
                if (player.getInventoryManager().getItemAmount(player.botFoodItemId) <= 2 && player.currentBotTask.getForcedCombatStyle() != 2 || player.botTaskReturnToBankRequested) {
                    player.currentBotTask.startWalkToBank(player);
                } else {
                    player.interactWithBotNpcTargets(player.botInteractionTargetIds);
                }
            }
            return false;
        }
        player.botLootPickupTargets.clear();
        for (Object groundItemObject : player.botLootGroundItems) {
            GroundItem groundItem = (GroundItem)groundItemObject;
            int value;
            int value2;
            ItemStack itemStack = groundItem.getItem();
            if (itemStack == null) continue;
            GroundItemManager.getInstance();
            GroundItem groundItem2 = GroundItemManager.findVisibleItem(player, groundItem.getItem().getId(), groundItem.getPosition());
            if (groundItem2 == null) continue;
            if (player.currentBotTask != null) {
                value2 = 0;
                if (player.currentBotTask.ignoredLootItemIds != null) {
                    int[] integerValues = player.currentBotTask.ignoredLootItemIds;
                    int length = player.currentBotTask.ignoredLootItemIds.length;
                    int index = 0;
                    while (index < length) {
                        value = integerValues[index];
                        if (groundItem2.getItem().getId() == value) {
                            value2 = 1;
                            break;
                        }
                        ++index;
                    }
                    if (value2 != 0) continue;
                }
            }
            value2 = GrandExchangeManager.getGuidePrice(itemStack.getId());
            int value3 = value = player.currentBotTask == null ? 1000 : 0;
            if (groundItem2.getItem().getDefinition().isStackable() && player.getInventoryManager().containsItem(groundItem2.getItem().getId()) || groundItem2.getItem().getDefinition().isStackable() && player.getInventoryManager().containsItem(groundItem2.getItem().getId())) {
                player.botLootPickupTargets.add(groundItem);
                continue;
            }
            if (value2 * itemStack.getAmount() < value) continue;
            player.botLootPickupTargets.add(groundItem);
            player.botLootSellGroundItems.add(groundItem);
        }
        if (player.botLootPickupTargets.size() <= 0) {
            player.botCombatState = null;
            player.botLootGroundItems.clear();
            if (player.currentBotTask != null) {
                if (player.getInventoryManager().getItemAmount(player.botFoodItemId) <= 2 && player.currentBotTask.getForcedCombatStyle() != 2 || player.botTaskReturnToBankRequested) {
                    player.currentBotTask.startWalkToBank(player);
                } else {
                    player.interactWithBotNpcTargets(player.botInteractionTargetIds);
                }
            }
            return false;
        }
        if (player.currentBotTask == null) {
            player.getMovementQueue().setRunning(true);
        }
        BotCombatHelper.pickupBotCombatGroundItem(player, ((GroundItem)player.botLootPickupTargets.get(0)).getItem().getId(), ((GroundItem)player.botLootPickupTargets.get(0)).getPosition());
        return false;
    }

    public static boolean pickupVisibleGroundItem(Player player, int itemId, Position position) {
        if (((Boolean)player.getAttributes().get("canPickup")).booleanValue()) {
            GroundItemManager.getInstance();
            GroundItem groundItem = GroundItemManager.findVisibleItem(player, itemId, position);
            if (groundItem != null) {
                player.setInteractionTargetY(position.getY());
                player.setInteractionTargetId(itemId);
                player.setInteractionTargetX(position.getX());
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                ItemStack itemStack = groundItem.getItem();
                PathFinder.getInstance();
                PathFinder.findPath(player, position.getX(), position.getY(), true, 0, 0);
                ItemService.getInstance().pickupItem(player, itemStack.getId(), groundItem.getPosition());
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean pickupBotCombatGroundItem(Player itemId, int itemId2, Position position) {
        if ((Boolean)((Entity)itemId).getAttributes().get("canPickup") == false) return false;
        GroundItemManager.getInstance();
        GroundItem groundItem = GroundItemManager.findVisibleItem((Player)itemId, itemId2, position);
        if (groundItem != null) {
            boolean enabled = false;
            if (((Player)itemId).botCombatState != null && ((Player)itemId).botCombatState.equals("loot arrows")) {
                enabled = true;
            }
            if (((Player)itemId).currentBotTask != null && !enabled) {
                PathFinder.getInstance();
                enabled = PathFinder.findPath((Player)itemId, position.getX(), position.getY(), false, 0, 0);
                if (!enabled && SpellDefinition.TELEKINETIC_GRAB.getRequiredLevel() <= ((Player)itemId).getSkillManager().getCurrentLevels()[6] && BotCombatHelper.hasRunesForSpell((Player)itemId, SpellDefinition.TELEKINETIC_GRAB)) {
                    MagicSpellAction.scheduleTelekineticGrab((Player)itemId, SpellDefinition.TELEKINETIC_GRAB, itemId2, position);
                    return true;
                }
                if (((Player)itemId).getInventoryManager().getItemAmount(((Player)itemId).botFoodItemId) == 0 && ((Player)itemId).currentBotTask.combatTask && ((Player)itemId).currentBotTask.getForcedCombatStyle() != 2 || ((Player)itemId).getInventoryManager().getContainer().getFreeSlots() == 0) {
                    ((Player)itemId).botLootPickupTargets.clear();
                    ((Player)itemId).botLootGroundItems.clear();
                    ((Player)itemId).currentBotTask.startWalkToBank((Player)itemId);
                    return false;
                }
                ItemStack itemStack = ((GroundItem)((Player)itemId).botLootPickupTargets.get(0)).getItem();
                InventoryManager inventoryManager = ((Player)itemId).getInventoryManager();
                if (!(itemStack == null ? false : inventoryManager.hasSpaceFor(itemStack)) && ((Player)itemId).getInventoryManager().getItemAmount(((Player)itemId).botFoodItemId) > 0) {
                    BotCombatHelper.eatBotFood((Player)itemId);
                    World.getTaskScheduler().schedule(new BotGroundItemPickupTask(2, (Player)itemId, position, itemId2, groundItem));
                    return true;
                }
            }
            ((Player)itemId).setInteractionTargetY(position.getY());
            ((Player)itemId).setInteractionTargetId(itemId2);
            ((Player)itemId).setInteractionTargetX(position.getX());
            ((Player)itemId).setInteractionTargetPlane(((Entity)itemId).getPosition().getPlane());
            ItemStack itemStack = groundItem.getItem();
            PathFinder.getInstance();
            PathFinder.findPath((Player)itemId, position.getX(), position.getY(), true, 0, 0);
            ItemService.getInstance().pickupItem((Player)itemId, itemStack.getId(), groundItem.getPosition());
            return true;
        }
        if (((Player)itemId).botCombatState != null && ((Player)itemId).botCombatState.equals("loot arrows")) {
            ((Player)itemId).botCombatState = null;
            ((Player)itemId).setAutoRetaliate(true);
            if (((Player)itemId).botLootResumeTarget != null && !((Player)itemId).botLootResumeTarget.isDead()) {
                CombatManager.startCombat((Entity)itemId, ((Player)itemId).botLootResumeTarget);
                return true;
            }
            if (((Player)itemId).currentBotTask == null) return true;
        } else {
            ((Player)itemId).botCombatState = null;
            if (((Player)itemId).currentBotTask == null) return true;
            if (((Player)itemId).getInventoryManager().getItemAmount(((Player)itemId).botFoodItemId) <= 2 && ((Player)itemId).currentBotTask.getForcedCombatStyle() != 2 || ((Player)itemId).botTaskReturnToBankRequested) {
                ((Player)itemId).currentBotTask.startWalkToBank((Player)itemId);
                return true;
            }
        }
        ((Player)itemId).interactWithBotNpcTargets(((Player)itemId).botInteractionTargetIds);
        return true;
    }

    public static void unequipMagicPenaltyGear(Player player) {
        ItemStack[] itemStackArray = player.getEquipmentManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null && itemStack.getDefinition().getBonus(8) < 0) {
                player.getEquipmentManager().unequipSlot(itemStack.getDefinition().getEquipmentSlot());
            }
            ++index;
        }
        player.botMagicPenaltyGearUnequipped = true;
    }

    public static void reequipMagicPenaltyGear(Player player) {
        int index = 0;
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index2 = 0;
        while (index2 < length) {
            ItemStack itemStack = itemStackArray[index2];
            ++index;
            if (itemStack != null && itemStack.getDefinition().getBonus(8) < 0) {
                player.getEquipmentManager().equipFromInventorySlot(index - 1);
            }
            ++index2;
        }
        player.botMagicPenaltyGearUnequipped = false;
    }

    public static boolean hasRunesForSpell(Player player, SpellDefinition spellDefinition) {
        ItemStack[] itemStackArray = spellDefinition.getRuneCosts();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            ItemStack runeCost = itemStackArray[index];
            if (!(runeCost.getId() == 556 && player.getEquipmentManager().getItemIdAtSlot(3) == 1381 || runeCost.getId() == 555 && player.getEquipmentManager().getItemIdAtSlot(3) == 1383 || runeCost.getId() == 557 && player.getEquipmentManager().getItemIdAtSlot(3) == 1385 || runeCost.getId() == 554 && player.getEquipmentManager().getItemIdAtSlot(3) == 1387 || player.getInventoryManager().containsItemAmount(runeCost.getId(), runeCost.getAmount()))) {
                return false;
            }
            ++index;
        }
        return true;
    }

    public static boolean eatBotFood(Player player) {
        int index = 0;
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index2 = 0;
        while (index2 < length) {
            ItemStack itemStack = itemStackArray[index2];
            ++index;
            if (itemStack != null && itemStack.getId() == player.botFoodItemId) {
                player.getFoodHandler().eatFood(itemStack.getId(), index - 1);
                return true;
            }
            ++index2;
        }
        player.botFoodDepleted = true;
        return false;
    }

    public static boolean drinkStrengthPotion(Player player) {
        int index = 0;
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index2 = 0;
        while (index2 < length) {
            ItemStack itemStack = itemStackArray[index2];
            ++index;
            if (itemStack != null && player.getPotionHandler().selectPotionForItemId(itemStack.getId())) {
                PotionDefinition definition = PotionHandler.definitions[player.getPotionHandler().selectedDefinitionIndex];
                int[] skillIds = definition == null ? null : definition.getSkillIds();
                if (skillIds != null) {
                    int skillIndex = 0;
                    while (skillIndex < skillIds.length) {
                        if (skillIds[skillIndex] == 2) {
                            player.getPotionHandler().drinkPotion(itemStack.getId(), index - 1);
                            return true;
                        }
                        ++skillIndex;
                    }
                }
            }
            ++index2;
        }
        player.botStrengthPotionDepleted = true;
        return false;
    }

    public static boolean hasPrayerLevelForCombatStyle(Player player, int level) {
        if (level == 0) {
            player.getSkillManager();
            if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 43) {
                return true;
            }
        }
        if (level == BotPvpCombatHandler.RANGED_COMBAT_STYLE) {
            player.getSkillManager();
            if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 40) {
                return true;
            }
        }
        if (level == BotPvpCombatHandler.MAGIC_COMBAT_STYLE) {
            player.getSkillManager();
            if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 37) {
                return true;
            }
        }
        return false;
    }

    private static void updateProtectionPrayerForStyle(Player player, Player player2, boolean prayerId) {
        int index = 0;
        if (player.botActiveCombatStyle == 0 && player2.getActivePrayers()[14]) {
            index = 1;
        } else if (player.botActiveCombatStyle == BotPvpCombatHandler.RANGED_COMBAT_STYLE && player2.getActivePrayers()[13]) {
            index = 1;
        } else if (player.botActiveCombatStyle == BotPvpCombatHandler.MAGIC_COMBAT_STYLE && player2.getActivePrayers()[12]) {
            index = 1;
        }
        boolean enabled = true;
        if (prayerId && index == 0 && player2.getSkillManager().getCurrentLevels()[5] > 15) {
            enabled = false;
        }
        if (enabled) {
            if (player.botOpponentCombatStyle == 0 && !player.getActivePrayers()[14]) {
                Player player3 = player2;
                player2 = player;
                index = 1;
                if (player3.isMoving() && !player3.isRunningMovement()) {
                    ++index;
                }
                if (player3.isMoving() && player3.isRunningMovement()) {
                    index += 2;
                }
                if (GameUtil.isWithinDistance(player2.getPosition(), player3.getPosition(), index)) {
                    if (player.botQueuedPrayerId != 14) {
                        player.botQueuedPrayerId = 14;
                        player.botPrayerSwitchDelayTicks = 0;
                        return;
                    }
                    if (player.botPrayerSwitchDelayTicks < 3) {
                        ++player.botPrayerSwitchDelayTicks;
                        return;
                    }
                    player.getPrayerManager().togglePrayer(14);
                    player.botPrayerSwitchDelayTicks = 0;
                    return;
                }
            }
            if (player.botOpponentCombatStyle == BotPvpCombatHandler.RANGED_COMBAT_STYLE && !player.getActivePrayers()[13]) {
                if (player.botQueuedPrayerId != 13) {
                    player.botQueuedPrayerId = 13;
                    player.botPrayerSwitchDelayTicks = 0;
                    return;
                }
                if (player.botPrayerSwitchDelayTicks < 3) {
                    ++player.botPrayerSwitchDelayTicks;
                    return;
                }
                player.getPrayerManager().togglePrayer(13);
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
            if (player.botOpponentCombatStyle == BotPvpCombatHandler.MAGIC_COMBAT_STYLE && !player.getActivePrayers()[12]) {
                if (player.botQueuedPrayerId != 12) {
                    player.botQueuedPrayerId = 12;
                    player.botPrayerSwitchDelayTicks = 0;
                    return;
                }
                if (player.botPrayerSwitchDelayTicks < 3) {
                    ++player.botPrayerSwitchDelayTicks;
                    return;
                }
                player.getPrayerManager().togglePrayer(12);
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
        } else {
            if (player.botOpponentCombatStyle == 0 && player.getActivePrayers()[14]) {
                if (player.botQueuedPrayerId != 14) {
                    player.botQueuedPrayerId = 14;
                    player.botPrayerSwitchDelayTicks = 0;
                    return;
                }
                if (player.botPrayerSwitchDelayTicks < 3) {
                    ++player.botPrayerSwitchDelayTicks;
                    return;
                }
                player.getPrayerManager().togglePrayer(14);
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
            if (player.botOpponentCombatStyle == BotPvpCombatHandler.RANGED_COMBAT_STYLE && player.getActivePrayers()[13]) {
                if (player.botQueuedPrayerId != 13) {
                    player.botQueuedPrayerId = 13;
                    player.botPrayerSwitchDelayTicks = 0;
                    return;
                }
                if (player.botPrayerSwitchDelayTicks < 3) {
                    ++player.botPrayerSwitchDelayTicks;
                    return;
                }
                player.getPrayerManager().togglePrayer(13);
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
            if (player.botOpponentCombatStyle == BotPvpCombatHandler.MAGIC_COMBAT_STYLE && player.getActivePrayers()[12]) {
                if (player.botQueuedPrayerId != 12) {
                    player.botQueuedPrayerId = 12;
                    player.botPrayerSwitchDelayTicks = 0;
                    return;
                }
                if (player.botPrayerSwitchDelayTicks < 3) {
                    ++player.botPrayerSwitchDelayTicks;
                    return;
                }
                player.getPrayerManager().togglePrayer(12);
                player.botPrayerSwitchDelayTicks = 0;
            }
        }
    }

    public static void toggleProtectionPrayerForOpponentStyle(Player player) {
        if (player.botOpponentCombatStyle == 0 && !player.getActivePrayers()[14]) {
            if (player.botQueuedPrayerId != 14) {
                player.botQueuedPrayerId = 14;
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
            if (player.botPrayerSwitchDelayTicks < 3) {
                ++player.botPrayerSwitchDelayTicks;
                return;
            }
            player.getPrayerManager().togglePrayer(14);
            player.botPrayerSwitchDelayTicks = 0;
            return;
        }
        if (player.botOpponentCombatStyle == BotPvpCombatHandler.RANGED_COMBAT_STYLE && !player.getActivePrayers()[13]) {
            if (player.botQueuedPrayerId != 13) {
                player.botQueuedPrayerId = 13;
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
            if (player.botPrayerSwitchDelayTicks < 3) {
                ++player.botPrayerSwitchDelayTicks;
                return;
            }
            player.getPrayerManager().togglePrayer(13);
            player.botPrayerSwitchDelayTicks = 0;
            return;
        }
        if (player.botOpponentCombatStyle == BotPvpCombatHandler.MAGIC_COMBAT_STYLE && !player.getActivePrayers()[12]) {
            if (player.botQueuedPrayerId != 12) {
                player.botQueuedPrayerId = 12;
                player.botPrayerSwitchDelayTicks = 0;
                return;
            }
            if (player.botPrayerSwitchDelayTicks < 3) {
                ++player.botPrayerSwitchDelayTicks;
                return;
            }
            player.getPrayerManager().togglePrayer(12);
            player.botPrayerSwitchDelayTicks = 0;
        }
    }

    public static boolean updateBotDefensivePrayers(Player player, Player player2) {
        boolean enabled = true;
        if (player.getSkillManager().getCurrentLevels()[5] <= 10) {
            enabled = false;
        }
        if (BotCombatHelper.hasPrayerLevelForCombatStyle(player, player.botOpponentCombatStyle) && enabled && player2.getCombatTarget() != null && player2.getCombatTarget() == player) {
            if (!BotCombatHelper.hasPrayerLevelForCombatStyle(player2, player.botActiveCombatStyle)) {
                BotCombatHelper.updateProtectionPrayerForStyle(player, player2, false);
            } else if (!BotCombatHelper.isFreeToPlayWorld()) {
                player.getSkillManager();
                if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 52 && !player.botCombatEscapeActive && ServerSettings.cacheVersion >= 336) {
                    if (!player.getActivePrayers()[17]) {
                        player.getPrayerManager().togglePrayer(17);
                    }
                } else {
                    BotCombatHelper.updateProtectionPrayerForStyle(player, player2, true);
                }
            } else {
                BotCombatHelper.updateProtectionPrayerForStyle(player, player2, true);
            }
        }
        if (player.botActiveCombatStyle == 0 && enabled) {
            player.getSkillManager();
            if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 31 && !player.getActivePrayers()[10]) {
                player.getPrayerManager().togglePrayer(10);
            }
        }
        if (!enabled) {
            BotCombatHelper.disableBotCombatPrayers(player);
        }
        return true;
    }

    public static void stopBotCombatTick(Player player) {
        if (player.botCombatTickTask != null && player.botCombatTickTask.isActive()) {
            player.botCombatTickTask.stop();
        }
        player.getMovementQueue().setRunning(false);
        player.getPrayerManager().deactivateAll();
    }

    public static void prepareBotPvpSearchPosition(Player player) {
        int value;
        int value2 = 2951;
        int value3 = 3376;
        int value4 = 3520;
        double combatLevel = player.getCombatLevel();
        double value5 = combatLevel * 1.5;
        if (ServerSettings.wildyBotsUseNewGeneration && ServerSettings.wildyBotsIgnoreCombatForDeepWilderness) {
            value5 = 52.0;
        }
        if (value5 > 52.0) {
            value5 = 52.0;
        }
        int value6 = (int)value5;
        if ((value6 = (value6 << 3) + 3520 - 1) > 3965) {
            value6 = 3965;
        }
        if (ServerSettings.freeToPlayWorld && value6 > 3895) {
            value6 = 3895;
        }
        player.botWildernessMaxY = value6;
        if (ServerSettings.hotzonesForWildyBotsEnabled && (value = GameUtil.randomInt(hotzonePvpSearchAreas.length + 1)) < hotzonePvpSearchAreas.length) {
            RectangularArea rectangularArea = hotzonePvpSearchAreas[value];
            value2 = rectangularArea.getMinX();
            value4 = rectangularArea.getMinY();
            value3 = rectangularArea.getMaxX();
            value6 = rectangularArea.getMaxY();
        }
        value = value3 - value2;
        value3 = value2 + GameUtil.randomInt(value);
        int value7 = value4 + GameUtil.randomInt(value6 -= value4);
        boolean enabled = true;
        while (enabled) {
            value3 = value2 + GameUtil.randomInt(value);
            value7 = value4 + GameUtil.randomInt(value6);
            boolean enabled2 = true;
            if (ServerSettings.freeToPlayWorld && BotCombatHelper.isPositionInAnyArea(new Position(value3, value7), freeToPlayBlockedPvpSearchAreas)) {
                enabled2 = false;
            }
            if (BotCombatHelper.isPositionInAnyArea(new Position(value3, value7), blockedPvpSearchAreas)) {
                enabled2 = false;
            }
            if (!enabled2) continue;
            enabled = WalkingCollisionMap.getTileFlags(value3, value7, 0) != 0;
        }
        player.moveTo(new Position(value3, value7, 0));
    }

    static boolean isTargetLootWorthRisk(Player player, Player player2) {
        if (player.skulled) {
            return true;
        }
        if (player.currentGroup != null && player2.currentGroup == null && player.isInMultiCombatArea() && player2.isInMultiCombatArea()) {
            return true;
        }
        double riskRatio = player2.getCombatLevel() > player.getCombatLevel() + 5 ? 0.3 : 0.2;
        int targetLootValue = 0;
        for (Object itemStackObject : player2.getUnprotectedItems(player2.getEquipmentManager().getContainer().getItems())) {
            ItemStack itemStack = (ItemStack)itemStackObject;
            if (itemStack == null) continue;
            int guidePrice = GrandExchangeManager.getGuidePrice(itemStack.getId());
            targetLootValue += guidePrice * itemStack.getAmount();
        }
        int playerRiskValue = 0;
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null) {
                int guidePrice = GrandExchangeManager.getGuidePrice(itemStack.getId());
                playerRiskValue += guidePrice * itemStack.getAmount();
            }
            ++index;
        }
        itemStackArray = player.getEquipmentManager().getContainer().getItems();
        length = itemStackArray.length;
        index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null) {
                int guidePrice = GrandExchangeManager.getGuidePrice(itemStack.getId());
                playerRiskValue += guidePrice * itemStack.getAmount();
            }
            ++index;
        }
        return !((double)targetLootValue < (double)playerRiskValue * riskRatio);
    }

    public static int selectBotLoadoutItemId(Player player, int[] freeItemIds, int[] memberItemIds, boolean randomize) {
        ArrayList<Integer> itemIds = new ArrayList<Integer>();
        if (freeItemIds != null) {
            int length = freeItemIds.length;
            int index = 0;
            while (index < length) {
                itemIds.add(freeItemIds[index]);
                ++index;
            }
        }
        if (!BotCombatHelper.isFreeToPlayWorld() && memberItemIds != null) {
            int length2 = memberItemIds.length;
            int index2 = 0;
            while (index2 < length2) {
                itemIds.add(memberItemIds[index2]);
                ++index2;
            }
        }
        if (!randomize) {
            int selectedIndex = 0;
            int index3 = 0;
            while (index3 < itemIds.size()) {
                int itemId = (Integer)itemIds.get(index3);
                if (ItemDefinition.isDefined(itemId)) {
                    if (!player.getEquipmentManager().canEquipItem(itemId)) {
                        selectedIndex = index3 - 1;
                        break;
                    }
                    selectedIndex = index3;
                }
                ++index3;
            }
            if (selectedIndex < 0 || itemIds.size() == 0) {
                return -1;
            }
            return (Integer)itemIds.get(selectedIndex);
        }
        ArrayList<Integer> equippableItemIds = new ArrayList<Integer>();
        Iterator iterator = itemIds.iterator();
        while (iterator.hasNext()) {
            int itemId = (Integer)iterator.next();
            if (!ItemDefinition.isDefined(itemId) || !player.getEquipmentManager().canEquipItem(itemId)) continue;
            equippableItemIds.add(itemId);
        }
        if (equippableItemIds.size() == 0) {
            return -1;
        }
        return (Integer)equippableItemIds.get(GameUtil.randomInt(equippableItemIds.size()));
    }

    public static int[] filterEquippableMemberLoadoutItems(Player player, int[] freeItemIds, int[] memberItemIds) {
        ArrayList<Integer> candidateItemIds = new ArrayList<Integer>();
        if (!BotCombatHelper.isFreeToPlayWorld() && memberItemIds != null) {
            int length = memberItemIds.length;
            int index = 0;
            while (index < length) {
                candidateItemIds.add(memberItemIds[index]);
                ++index;
            }
        }
        ArrayList<Integer> equippableItemIds = new ArrayList<Integer>();
        Iterator iterator = candidateItemIds.iterator();
        while (iterator.hasNext()) {
            int itemId = (Integer)iterator.next();
            if (!ItemDefinition.isDefined(itemId) || !player.getEquipmentManager().canEquipItem(itemId)) continue;
            equippableItemIds.add(itemId);
        }
        int[] itemIds = new int[equippableItemIds.size()];
        int index2 = 0;
        while (index2 < equippableItemIds.size()) {
            itemIds[index2] = (Integer)equippableItemIds.get(index2);
            ++index2;
        }
        return itemIds;
    }

    static int selectBestBotLoadoutItemId(Player player, int[] itemId, int[] value2) {
        return BotCombatHelper.selectBotLoadoutItemId(player, itemId, value2, false);
    }

    public static void operateGloryTeleport(Player player) {
        player.setSelectedItemId(1712);
        player.setSelectedItemSlot(2);
        String text = "operate";
        Player player2 = player;
        player.interfaceAction = text;
        GameplayHelper.castSelectedItemTeleport(player, TeleportManager.EDGEVILLE_TELEPORT_POSITION);
    }

    public static void grantBotSpellRunes(Player player, SpellDefinition spellDefinition, int value2) {
        if (player.botMode == 4 || BotPlayer.defaultProgressiveBotNames.contains(player.getUsername().toLowerCase())) {
            String username = "CRITICAL BUG, REPORT! BotUtil " + player.getUsername() + " " + player.botMode + " " + player.currentBotTaskIndex + " " + player.currentBotTaskTypeId + " " + player.currentBotTask;
            System.out.println(username);
            GameplayHelper.appendLogLine(username, "errors");
            return;
        }
        ArrayList<ItemStack> grantedRuneCosts = new ArrayList<ItemStack>();
        ItemStack[] itemStackArray = spellDefinition.getRuneCosts();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            ItemStack runeCost = itemStackArray[index];
            if (!(runeCost.getId() == 556 && player.getEquipmentManager().getItemIdAtSlot(3) == 1381 || runeCost.getId() == 555 && player.getEquipmentManager().getItemIdAtSlot(3) == 1383 || runeCost.getId() == 557 && player.getEquipmentManager().getItemIdAtSlot(3) == 1385 || runeCost.getId() == 554 && player.getEquipmentManager().getItemIdAtSlot(3) == 1387)) {
                ItemStack grantedRuneCost = new ItemStack(runeCost.getId(), runeCost.getAmount() * value2);
                grantedRuneCosts.add(grantedRuneCost);
                player.getInventoryManager().addItem(grantedRuneCost);
            }
            ++index;
        }
        if (player.currentBotTask != null) {
            ItemStack[] requiredItems;
            if (player.botTaskRequiredItems != null) {
                requiredItems = new ItemStack[player.botTaskRequiredItems.length + grantedRuneCosts.size()];
                index = 0;
                while (index < player.botTaskRequiredItems.length) {
                    requiredItems[index] = player.botTaskRequiredItems[index];
                    ++index;
                }
                index = 0;
                while (index < grantedRuneCosts.size()) {
                    ItemStack grantedRuneCost = (ItemStack)grantedRuneCosts.get(index);
                    requiredItems[player.botTaskRequiredItems.length + index] = grantedRuneCost;
                    player.getBankContainer().addToTab(new ItemStack(grantedRuneCost.getId(), grantedRuneCost.getAmount() * 10), 0);
                    ++index;
                }
            } else {
                requiredItems = new ItemStack[grantedRuneCosts.size()];
                index = 0;
                while (index < grantedRuneCosts.size()) {
                    ItemStack grantedRuneCost = (ItemStack)grantedRuneCosts.get(index);
                    requiredItems[index] = grantedRuneCost;
                    player.getBankContainer().addToTab(new ItemStack(grantedRuneCost.getId(), grantedRuneCost.getAmount() * 10), 0);
                    ++index;
                }
            }
            player.botTaskRequiredItems = requiredItems;
        }
    }

    public static void walkBotTowardPosition(Player player, Position position) {
        Position position2 = player.getPosition();
        int x = position2.getX();
        int y = position2.getY();
        int x2 = position.getX();
        int y2 = position.getY();
        Vector2f vector2f = new Vector2f(x2 - x, y2 - y);
        vector2f.normalize();
        int distance = GameUtil.getDistance(position2, position);
        distance = distance > 20 ? 20 : distance;
        int x3 = (int)((float)distance * vector2f.getX());
        y2 = (int)((float)distance * vector2f.getY());
        x3 = x + x3 - 1 + GameUtil.randomInclusive(2);
        y2 = y + y2 - 1 + GameUtil.randomInclusive(2);
        boolean enabled = false;
        if (WalkingCollisionMap.getTileFlags(x3, y2, 0) != 0) {
            enabled = true;
        }
        while (enabled && distance > 1) {
            x3 = (int)((float)(--distance) * vector2f.getX());
            y2 = (int)((float)distance * vector2f.getY());
            enabled = WalkingCollisionMap.getTileFlags(x3 = x + x3 - 1 + GameUtil.randomInclusive(2), y2 = y + y2 - 1 + GameUtil.randomInclusive(2), 0) != 0;
        }
        if (!player.isMovementLocked()) {
            PathFinder.getInstance();
            PathFinder.findPath(player, x3, y2, true, 0, 0);
            player.getMovementQueue().clearMovementActions();
        }
    }

    public static int getEscapeCombatLevelMargin(Player player) {
        boolean enabled = player.botCombatStyle == 4 || player.botCombatStyle == 6 || player.botCombatStyle == 5;
        int value = 20;
        if (player.getCombatLevel() < 20 || !enabled) {
            value = 5;
            if (player.getCombatLevel() >= 20) {
                value = 15;
            }
            if (player.currentGroup != null) {
                value *= player.currentGroup.members.size();
            }
        }
        return value;
    }

    public static boolean hasExternalCombatTarget(Player player) {
        Player player2;
        if (player.getCombatTarget() == null) {
            return false;
        }
        if (player.getMovementTarget() == null) {
            return false;
        }
        return player.currentGroup == null || !player.getMovementTarget().isPlayer() || !player.currentGroup.containsMember(player2 = (Player)player.getMovementTarget());
    }

    public static boolean drinkAntipoisonPotion(Player player) {
        if (!player.botAntipoisonAvailable) {
            return false;
        }
        int index = 0;
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index2 = 0;
        while (index2 < length) {
            ItemStack itemStack = itemStackArray[index2];
            ++index;
            if (itemStack != null && player.getPotionHandler().selectPotionForItemId(itemStack.getId()) && PotionHandler.definitions[player.getPotionHandler().selectedDefinitionIndex].isAntipoison()) {
                player.getPotionHandler().drinkPotion(itemStack.getId(), index - 1);
                return true;
            }
            ++index2;
        }
        player.botAntipoisonAvailable = false;
        return false;
    }

    public static void dropInventoryItem(Player player, ItemStack itemStack) {
        if (player.getInventoryManager().getContainer().containsItem(itemStack.getId())) {
            Player player2 = player;
            player2.packetSender.sendSoundEffect(376, 1, 0);
            if (!ServerSettings.adminInteractionsAllowed && player.getPlayerRights() >= 2) {
                player2 = player;
                player2.packetSender.sendGameMessage("Your item disappears because you're an administrator.");
            } else {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), itemStack.getAmount()), player));
            }
            if (!player.getInventoryManager().removeItemFromSlot(itemStack, player.getSelectedItemSlot())) {
                player.getInventoryManager().removeItem(itemStack);
            }
        }
        player.getEquipmentManager().refreshCarriedValue();
    }

    public static boolean tryHandleBotPvpTeamGrouping(Player player, Player player2) {
        if (!player.isInMultiCombatArea() || !player2.isInMultiCombatArea()) {
            return false;
        }
        if (player.getPlayerRights() >= 2 || player2.getPlayerRights() >= 2) {
            return false;
        }
        if (!player.botEnabled || !player2.botEnabled) {
            return false;
        }
        if (player.clanWarsBot || player2.clanWarsBot) {
            return false;
        }
        if (player.getCombatLevel() < 10 || player2.getCombatLevel() < 10) {
            return false;
        }
        if (!player.getSingleCombatTimer().hasElapsed()) {
            return false;
        }
        if (player.botCombatEscapeActive || player2.botCombatEscapeActive) {
            return false;
        }
        if (player.currentGroup != null) {
            if (player2.currentGroup != null) {
                return false;
            }
            if (!player.currentGroup.isFull()) {
                Player player3 = player.currentGroup.leader;
                if (!player3.botEnabled) {
                    return false;
                }
                if (Math.abs(player3.getCombatLevel() - player2.getCombatLevel()) > 2) {
                    return false;
                }
                player.currentGroup.addMember(player2);
                player.currentGroup.refreshGroupFollowChain();
            }
            return true;
        }
        if (player2.currentGroup != null) {
            if (player.currentGroup != null) {
                return false;
            }
            if (!player2.currentGroup.isFull()) {
                Player player4 = player2.currentGroup.leader;
                if (!player4.isBot) {
                    return false;
                }
                if (Math.abs(player4.getCombatLevel() - player.getCombatLevel()) > 2) {
                    return false;
                }
                player2.currentGroup.addMember(player);
                player2.currentGroup.refreshGroupFollowChain();
            }
            return true;
        }
        if (Math.abs(player.getCombatLevel() - player2.getCombatLevel()) <= 2) {
            PlayerGroup playerGroup = new PlayerGroup(player, player2);
            playerGroup.refreshGroupFollowChain();
            return true;
        }
        return true;
    }

    public static boolean canTeamWithBotPvpPlayer(Player player, Player player2, boolean enabled2) {
        int value = 2;
        if (!enabled2) {
            value = 6;
        }
        if (!player.getSingleCombatTimer().hasElapsed()) {
            return false;
        }
        if (player.botCombatEscapeActive) {
            return false;
        }
        if (player.currentGroup != null) {
            return false;
        }
        if (player2.currentGroup != null && !player2.currentGroup.isFull()) {
            player2 = player2.currentGroup.leader;
            return Math.abs(player2.getCombatLevel() - player.getCombatLevel()) <= value;
        }
        return Math.abs(player.getCombatLevel() - player2.getCombatLevel()) <= value;
    }

    public static boolean isFreeToPlayWorld() {
        return ServerSettings.freeToPlayWorld;
    }
}

