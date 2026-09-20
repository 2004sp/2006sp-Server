package com.rs2.model.player;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.BotTaskPlanner;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.bankpin.BankPinEntryMode;
import com.rs2.model.item.ItemStack;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Iterator;

public final class BankManager {
    public static int freeBankCapacity = 64;
    public static int memberBankCapacity = 64;
    private static int mainBankContainerInterfaceId = 5382;
    public static PlayerUpdateTask[] bankTabUpdateTasks = new PlayerUpdateTask[]{
        new PlayerUpdateTask(mainBankContainerInterfaceId, 19509, 19508, 0),
        new PlayerUpdateTask(19532, 19511, 19510, 0),
        new PlayerUpdateTask(19533, 19513, 19512, 0),
        new PlayerUpdateTask(19534, 19515, 19514, 0),
        new PlayerUpdateTask(19535, 19517, 19516, 0),
        new PlayerUpdateTask(19536, 19519, 19518, 0),
        new PlayerUpdateTask(19537, 19521, 19520, 0),
        new PlayerUpdateTask(19538, 19523, 19522, 0),
        new PlayerUpdateTask(19539, 19525, 19524, 0),
        new PlayerUpdateTask(19540, 19527, 19526, 0)
    };

    public static int getBankTabIndexForButtonId(int buttonId) {
        int index = 0;
        while (index < bankTabUpdateTasks.length) {
            if (bankTabUpdateTasks[index].getBankTabButtonId() == buttonId) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public static boolean isBankControlButton(int buttonId) {
        return buttonId == 18885
                || buttonId == 18886
                || getBankTabIndexForButtonId(buttonId) != -1;
    }

    public static boolean isBankItemContainerInterfaceId(int interfaceId) {
        int index = 0;
        while (index < bankTabUpdateTasks.length) {
            if (bankTabUpdateTasks[index].itemContainerInterfaceId == interfaceId) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static void openBank(Player player) {
        if (player.botEnabled) {
            World.getTaskScheduler().schedule(new DelayedBankOpenTask(3 + GameUtil.randomInt(3), player));
            return;
        }
        BankManager.openBank(player, player);
    }

    public static void selectTab(Player player, int tabIndex) {
        if (player.getBankContainer().getTabCount() - 1 < tabIndex) {
            return;
        }
        player.currentBankTab = tabIndex;
        int index = 0;
        while (index < player.getBankContainer().getTabLimit()) {
            player.getBankContainer().compactTab(index);
            BankManager.sendBankTab(player, index);
            ++index;
        }
    }

    public static void openBank(Player bankOwner, Player player) {
        if (player.gameMode == 2) {
            player.packetSender.sendGameMessage("You are ultimate ironman and cannot use banks.");
            return;
        }
        if (bankOwner.equals(player) && ServerSettings.cacheVersion >= 336) {
            if (bankOwner.getBankPinManager().hasPin()) {
                if (!bankOwner.getBankPinManager().isVerified()) {
                    bankOwner.getBankPinManager().setEntryMode(BankPinEntryMode.a);
                    return;
                }
            } else if (!bankOwner.isBankPinReminderShown()) {
                bankOwner.packetSender.sendGameMessage("You do not have a bank pin, it is highly recommended you get one.");
                bankOwner.setBankPinReminderShown(true);
            }
        }
        ItemStack[] inventoryItems = player.getInventoryManager().getContainer().getRawItems();
        int tabIndex = 0;
        while (tabIndex < bankOwner.getBankContainer().getTabLimit()) {
            try {
                bankOwner.getBankContainer().compactTab(tabIndex);
                ItemStack[] tabItems = bankOwner.getBankContainer().getTabItems(tabIndex);
                player.packetSender.sendItemContainer(bankTabUpdateTasks[tabIndex].itemContainerInterfaceId, tabItems);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            ++tabIndex;
        }
        player.packetSender.sendItemContainer(5064, inventoryItems);
        player.packetSender.showInterfaceWithInventory(5292, 5063);
        player.getAttributes().put("isBanking", Boolean.TRUE);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("bank open player=" + GameplayTrace.describe(player) + " owner=" + GameplayTrace.describe(bankOwner) + " usedSlots=" + bankOwner.getBankContainer().getUsedSlots() + " inventoryFree=" + player.getInventoryManager().getContainer().getFreeSlots());
        }
        if (player.getQuestState(0) == 51) {
            player.advanceTutorialStage();
        }
        if (player.botEnabled) {
            if (player.currentBotTask.lootSellShopIds.size() > 0 && player.botMode != 4) {
                BotCombatHelper.sellBotLootItems(player);
            }
            if (player.botTaskState.equals("empty inventory")) {
                BankManager.depositInventory(player);
                if (player.botCombatLoadoutItemIds != null && player.botCombatLoadoutItemIds.size() > 0) {
                    BankManager.depositEquipment(player);
                }
            }
            if (!player.botTaskReturnToBankRequested) {
                GameplayHelper.shouldReturnToBankForBotTask(player);
            }
            if (player.botTaskReturnToBankRequested) {
                BotBankContinuationTask botBankContinuationTask = new BotBankContinuationTask(10 + GameUtil.randomInt(10), player);
                World.getTaskScheduler().schedule(botBankContinuationTask);
                return;
            }
            if (player.botTaskRequiredItems != null) {
                ItemStack[] requiredItems = player.botTaskRequiredItems;
                int length = player.botTaskRequiredItems.length;
                int index = 0;
                while (index < length) {
                    ItemStack itemStack = requiredItems[index];
                    if (itemStack.getDefinition().getEquipmentSlot() == -1 || itemStack.getAmount() != 1 || !player.getEquipmentManager().containsItem(itemStack.getId())) {
                        BankManager.withdrawItem(player, itemStack.getId(), itemStack.getAmount());
                    }
                    ++index;
                }
                if (player.botTaskReturnToBankRequested) {
                    if (BotTaskDefinition.leatherCraftingTasks.contains(player.currentBotTask) || BotTaskDefinition.tanningTasks.contains(player.currentBotTask) || BotTaskDefinition.spinningTasks.contains(player.currentBotTask) || BotTaskDefinition.cookingTasks.contains(player.currentBotTask) || BotTaskDefinition.smeltingTasks.contains(player.currentBotTask) || BotTaskDefinition.smithingTasks.contains(player.currentBotTask)) {
                        GameplayHelper.selectAndStartProgressiveBotTask(player, true);
                    } else {
                        GameplayHelper.selectAndStartNextProgressiveBotTask(player);
                    }
                    BankManager.depositInventory(player);
                    return;
                }
                BankManager.equipBotInventoryItems(player);
            }
            if (player.botCombatLoadoutItemIds != null && player.currentBotTask.combatTask) {
                player.queuePublicChatMessage("Im going to train some combat stats.");
                if (player.botCombatLoadoutItemIds.size() > 0) {
                    int index = 0;
                    while (index < player.botCombatLoadoutItemIds.size()) {
                        ItemStack itemStack = new ItemStack((Integer)player.botCombatLoadoutItemIds.get(index));
                        BankManager.withdrawItem(player, itemStack.getId(), itemStack.getAmount());
                        ++index;
                    }
                    BankManager.equipBotInventoryItems(player);
                    player.botCombatLoadoutItemIds.clear();
                }
                BotTaskPlanner.selectMeleeTrainingFightMode(player);
            }
            BankManager.depositInventory(player);
            if (player.botTaskRequiredItems != null) {
                ItemStack[] requiredItems = player.botTaskRequiredItems;
                int length = player.botTaskRequiredItems.length;
                int index = 0;
                while (index < length) {
                    ItemStack itemStack = requiredItems[index];
                    if (itemStack.getDefinition().getEquipmentSlot() == -1 || itemStack.getAmount() != 1 || !player.getEquipmentManager().containsItem(itemStack.getId())) {
                        BankManager.withdrawItem(player, itemStack.getId(), itemStack.getAmount());
                    }
                    ++index;
                }
            }
            if (player.botShopSellItemIds != null && player.botShopSellItemIds.size() > 0) {
                player.setBankWithdrawNoteMode(true);
                Iterator iterator = player.botShopSellItemIds.iterator();
                while (iterator.hasNext()) {
                    int itemId = (Integer)iterator.next();
                    ItemStack bankItem = player.getBankContainer().findItem(itemId);
                    if (bankItem == null) continue;
                    BankManager.withdrawItem(player, bankItem.getId(), bankItem.getAmount());
                }
                player.setBankWithdrawNoteMode(false);
            }
            player.currentBotTask.startWalkToTask(player);
            player.botInteractionOption = 1;
            player.packetSender.closeInterfaces();
        }
    }

    public static void openDepositBox(Player player) {
        if (player.gameMode == 2) {
            player.packetSender.sendGameMessage("You are ultimate ironman and cannot use banks.");
            return;
        }
        ItemStack[] inventoryItems = player.getInventoryManager().getContainer().getRawItems();
        player.packetSender.sendItemContainer(7423, inventoryItems);
        player.packetSender.showInterfaceWithInventory(4465, 197);
        player.getAttributes().put("isBanking", Boolean.TRUE);
    }

    public static boolean depositEquipment(Player player) {
        ArrayList<ItemStack> depositItems = new ArrayList<ItemStack>();
        ItemStack[] equipmentItems = player.getEquipmentManager().getContainer().getItems();
        int length = equipmentItems.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = equipmentItems[index];
            if (itemStack != null && !depositItems.contains(itemStack)) {
                depositItems.add(itemStack);
            }
            ++index;
        }
        ItemStack[] items = BankManager.toItemArray(depositItems);
        if (BankManager.canDepositItems(player, items)) {
            equipmentItems = player.getEquipmentManager().getContainer().getItems();
            length = equipmentItems.length;
            index = 0;
            while (index < length) {
                ItemStack itemStack = equipmentItems[index];
                if (itemStack != null) {
                    player.getEquipmentManager().removeItemWithoutRefresh(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++index;
            }
            player.getEquipmentManager().finishBulkEquipmentRemoval();
            BankManager.refreshBankAndInventory(player);
            return true;
        }
        return false;
    }

    public static boolean depositInventory(Player player) {
        ArrayList<ItemStack> depositItems = new ArrayList<ItemStack>();
        ItemStack[] inventoryItems = player.getInventoryManager().getContainer().getItems();
        int length = inventoryItems.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = inventoryItems[index];
            if (itemStack != null) {
                depositItems.add(itemStack);
            }
            ++index;
        }
        ItemStack[] items = BankManager.toItemArray(depositItems);
        if (BankManager.canDepositItems(player, items)) {
            inventoryItems = player.getInventoryManager().getContainer().getItems();
            length = inventoryItems.length;
            index = 0;
            while (index < length) {
                ItemStack itemStack = inventoryItems[index];
                if (itemStack != null) {
                    player.getInventoryManager().removeItem(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++index;
            }
            BankManager.refreshBankAndInventory(player);
            return true;
        }
        return false;
    }

    public static boolean depositInventoryAndEquipment(Player player) {
        ArrayList<ItemStack> depositItems = new ArrayList<ItemStack>();
        ItemStack[] inventoryItems = player.getInventoryManager().getContainer().getItems();
        int length = inventoryItems.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = inventoryItems[index];
            if (itemStack != null && !depositItems.contains(itemStack)) {
                depositItems.add(itemStack);
            }
            ++index;
        }
        ItemStack[] equipmentItems = player.getEquipmentManager().getContainer().getItems();
        length = equipmentItems.length;
        index = 0;
        while (index < length) {
            ItemStack itemStack = equipmentItems[index];
            if (itemStack != null && !depositItems.contains(itemStack)) {
                depositItems.add(itemStack);
            }
            ++index;
        }
        ItemStack[] items = BankManager.toItemArray(depositItems);
        if (BankManager.canDepositItems(player, items)) {
            inventoryItems = player.getInventoryManager().getContainer().getItems();
            length = inventoryItems.length;
            index = 0;
            while (index < length) {
                ItemStack itemStack = inventoryItems[index];
                if (itemStack != null) {
                    player.getInventoryManager().removeItem(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++index;
            }
            equipmentItems = player.getEquipmentManager().getContainer().getItems();
            length = equipmentItems.length;
            index = 0;
            while (index < length) {
                ItemStack itemStack = equipmentItems[index];
                if (itemStack != null) {
                    player.getEquipmentManager().removeItemWithoutRefresh(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++index;
            }
            player.getEquipmentManager().finishBulkEquipmentRemoval();
            BankManager.refreshBankAndInventory(player);
            return true;
        }
        return false;
    }

    private static boolean canDepositItem(Player player, ItemStack itemStack, int usedSlots) {
        int bankItemId = itemStack.getDefinition().isNote() ? itemStack.getDefinition().getUnnotedId() : itemStack.getDefinition().getId();
        int existingAmount = player.getBankContainer().getItemAmount(bankItemId);
        if (itemStack.getMetadata() != -1) {
            existingAmount = 0;
        }
        if (existingAmount > 0) {
            return true;
        }
        int capacity = player.isBot ? 288 : (player.isMember() ? memberBankCapacity : freeBankCapacity);
        if (usedSlots >= capacity) {
            player.packetSender.sendGameMessage("You don't have enough space in your bank account.");
            return false;
        }
        return true;
    }

    private static void depositToBank(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        int amount = itemStack.getAmount();
        int tab = player.currentBankTab;
        int bankItemId = itemStack.getDefinition().isNote() ? itemStack.getDefinition().getUnnotedId() : itemStack.getDefinition().getId();
        int existingAmount = player.getBankContainer().getItemAmount(bankItemId);
        int metadata = itemStack.getMetadata();
        if (!BankManager.canDepositItem(player, itemStack, player.getBankContainer().getUsedSlots())) {
            return;
        }
        int existingTab = player.getBankContainer().findTabContainingItem(bankItemId);
        int existingSlot = -1;
        if (existingTab != -1) {
            existingSlot = player.getBankContainer().indexOfItemInTab(bankItemId, existingTab);
            tab = existingTab;
        }
        if (metadata != -1) {
            existingSlot = -1;
            existingTab = player.getBankContainer().findTabContainingPlaceholder(bankItemId, 0);
            if (existingTab != -1) {
                existingSlot = player.getBankContainer().indexOfPlaceholderInTab(bankItemId, 0, existingTab);
                tab = existingTab;
            }
            existingAmount = 0;
            amount = 1;
        }
        if (existingSlot == -1) {
            player.getBankContainer().addToTab(new ItemStack(bankItemId, amount, metadata), tab);
            return;
        }
        player.getBankContainer().setTabItem(existingSlot, new ItemStack(bankItemId, existingAmount + amount, metadata), tab);
    }

    private static boolean canDepositItems(Player player, ItemStack[] items) {
        ArrayList<Integer> newItemIds = new ArrayList<Integer>();
        int usedSlots = player.getBankContainer().getUsedSlots();
        int length = items.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = items[index];
            int bankItemId = itemStack.getDefinition().isNote() ? itemStack.getDefinition().getUnnotedId() : itemStack.getDefinition().getId();
            if (!newItemIds.contains(bankItemId)) {
                int existingAmount = player.getBankContainer().getItemAmount(bankItemId);
                if (itemStack.getMetadata() != -1) {
                    existingAmount = 0;
                }
                if (!BankManager.canDepositItem(player, itemStack, usedSlots)) {
                    return false;
                }
                if (existingAmount == 0) {
                    ++usedSlots;
                    newItemIds.add(bankItemId);
                }
            }
            ++index;
        }
        return true;
    }

    private static void refreshBankAndInventory(Player player) {
        player.getInventoryManager().sendToInterface(5064);
        if (ServerSettings.cacheVersion >= 336) {
            player.getInventoryManager().sendToInterface(7423);
        }
        BankManager.refreshBankTabs(player);
    }

    public static void depositInventoryItem(Player player, int slot, int itemId, int amount) {
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(slot);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("bank deposit request player=" + GameplayTrace.describe(player) + " slot=" + slot + " itemId=" + itemId + " amount=" + amount + " selected=" + (itemStack == null ? "null" : itemStack.getId() + ":" + itemStack.getDefinition().getName() + "x" + itemStack.getAmount()));
        }
        if (itemStack == null || itemStack.getId() != itemId || !itemStack.isValid()) {
            return;
        }
        int inventoryAmount = player.getInventoryManager().getContainer().getItemAmount(itemId);
        int tab = player.currentBankTab;
        int metadata = itemStack.getMetadata();
        int bankItemId;
        if (itemStack.getDefinition().getId() > 11883) {
            player.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        bankItemId = itemStack.getDefinition().isNote() ? itemStack.getDefinition().getUnnotedId() : itemStack.getDefinition().getId();
        int existingAmount = player.getBankContainer().getItemAmount(bankItemId);
        int existingTab = player.getBankContainer().findTabContainingItem(bankItemId);
        int existingSlot = -1;
        if (existingTab != -1) {
            existingSlot = player.getBankContainer().indexOfItemInTab(bankItemId, existingTab);
            tab = existingTab;
        }
        if (metadata != -1) {
            existingSlot = -1;
            existingTab = player.getBankContainer().findTabContainingPlaceholder(bankItemId, 0);
            if (existingTab != -1) {
                existingSlot = player.getBankContainer().indexOfPlaceholderInTab(bankItemId, 0, existingTab);
                tab = existingTab;
            }
            existingAmount = 0;
            inventoryAmount = 1;
        }
        if (!BankManager.canDepositItem(player, itemStack, player.getBankContainer().getUsedSlots())) {
            return;
        }
        if (inventoryAmount > amount) {
            inventoryAmount = amount;
        }
        if (!itemStack.getDefinition().isStackable()) {
            int index = 0;
            while (index < inventoryAmount) {
                if (metadata != -1) {
                    player.getInventoryManager().removeItemFromSlot(new ItemStack(itemId, 1, metadata), slot);
                } else {
                    player.getInventoryManager().removeItem(new ItemStack(itemId, 1, metadata));
                }
                ++index;
            }
        } else if (metadata != -1) {
            player.getInventoryManager().removeItemFromSlot(new ItemStack(itemId, inventoryAmount, metadata), slot);
        } else {
            player.getInventoryManager().removeItem(new ItemStack(itemId, inventoryAmount, metadata));
        }
        if (existingSlot == -1) {
            player.getBankContainer().addToTab(new ItemStack(bankItemId, inventoryAmount, metadata), tab);
        } else {
            player.getBankContainer().setTabItem(existingSlot, new ItemStack(bankItemId, existingAmount + inventoryAmount, metadata), tab);
        }
        BankManager.refreshBankAndInventory(player);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("bank deposit success player=" + GameplayTrace.describe(player) + " itemId=" + bankItemId + " item=" + itemStack.getDefinition().getName() + " amount=" + inventoryAmount + " tab=" + tab + " bankAmount=" + player.getBankContainer().getItemAmount(bankItemId));
        }
    }

    private static void withdrawItem(Player player, int itemId, int amount) {
        ItemStack itemStack = new ItemStack(itemId);
        int bankAmount = player.getBankContainer().getItemAmount(itemId);
        int tab = 0;
        if (bankAmount != 0) {
            tab = player.getBankContainer().findTabContainingItem(itemId);
        }
        int slot = 0;
        if (bankAmount != 0) {
            slot = player.getBankContainer().indexOfItemInTab(itemId, tab);
        }
        boolean noteMode = player.isBankWithdrawNoteMode();
        boolean hasNote = itemStack.getDefinition().hasNote();
        int notedId = itemStack.getDefinition().getNotedId();
        if (player.botMode == 4 && player.botEnabled && player.currentBotTask != null && BotTaskDefinition.smeltingTasks.contains(player.currentBotTask) && itemId == 453 && bankAmount < SmeltingHandler.getCoalRequiredForBar(player.botTaskItemId)) {
            player.botTaskReturnToBankRequested = true;
            return;
        }
        if (bankAmount <= 0 || itemId < 0 || player.getBankContainer().getItemAtTabSlot(slot, tab) == null || player.getBankContainer().getItemAtTabSlot(slot, tab).getId() != itemStack.getId() || !itemStack.isValid()) {
            if (player.botEnabled && player.currentBotTask != null && player.botTaskRequiredItems != null && player.botTaskRequiredItems.length > 0) {
                int index = 0;
                while (index < player.botTaskRequiredItems.length) {
                    if (player.botTaskRequiredItems[index].getId() == itemId && player.getInventoryManager().getItemAmount(itemId) < player.botTaskRequiredItems[index].getAmount()) {
                        player.botTaskReturnToBankRequested = true;
                    }
                    ++index;
                }
            }
            return;
        }
        int metadata = player.getBankContainer().getItemAtTabSlot(slot, tab).getMetadata();
        if (metadata != -1) {
            amount = 1;
        }
        if (itemId > 11883) {
            player.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if (bankAmount < amount) {
            amount = bankAmount;
        }
        if (noteMode && !hasNote) {
            player.packetSender.sendGameMessage("This item cannot be withdrawn as a note.");
            noteMode = false;
        }
        int addedAmount = 0;
        if (!noteMode || !hasNote) {
            addedAmount = player.getInventoryManager().addItemPartial(new ItemStack(itemId, amount, metadata));
        } else if (noteMode) {
            addedAmount = player.getInventoryManager().addItemPartial(new ItemStack(notedId, amount, metadata));
        }
        player.getBankContainer().removeFromTab(new ItemStack(itemId, addedAmount, metadata), slot, tab);
        player.getInventoryManager().sendToInterface(5064);
        BankManager.refreshBankTabs(player);
    }

    public static void withdrawItemFromTab(Player player, int slot, int itemId, int amount, int interfaceId) {
        ItemStack itemStack = new ItemStack(itemId);
        int tab = player.currentBankTab;
        int index = 0;
        while (index < bankTabUpdateTasks.length) {
            PlayerUpdateTask playerUpdateTask = bankTabUpdateTasks[index];
            if (playerUpdateTask.itemContainerInterfaceId == interfaceId) {
                tab = index;
                break;
            }
            ++index;
        }
        boolean noteMode = player.isBankWithdrawNoteMode();
        boolean hasNote = itemStack.getDefinition().hasNote();
        int notedId = itemStack.getDefinition().getNotedId();
        int bankAmount = player.getBankContainer().getItemAmount(itemId);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("bank withdraw request player=" + GameplayTrace.describe(player) + " slot=" + slot + " itemId=" + itemId + " item=" + itemStack.getDefinition().getName() + " amount=" + amount + " interfaceId=" + interfaceId + " tab=" + tab + " bankAmount=" + bankAmount + " noteMode=" + noteMode);
        }
        if (amount <= 0 || player.getBankContainer().getItemAtTabSlot(slot, tab) == null || player.getBankContainer().getItemAtTabSlot(slot, tab).getId() != itemStack.getId()) {
            return;
        }
        int metadata = player.getBankContainer().getItemAtTabSlot(slot, tab).getMetadata();
        int slotAmount = player.getBankContainer().getItemAtTabSlot(slot, tab).getAmount();
        int addedAmount = 0;
        if (slotAmount <= 0) {
            player.getBankContainer().removeFromTab(new ItemStack(itemId, 0, metadata), slot, tab);
            player.getBankContainer().removeEmptyTabs();
            BankManager.refreshBankTabs(player);
            return;
        }
        if (itemId < 0 || !itemStack.isValid()) {
            return;
        }
        if (metadata != -1) {
            amount = 1;
        }
        if (itemId > 11883) {
            player.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if (bankAmount < amount) {
            amount = bankAmount;
        }
        if (noteMode && !hasNote) {
            player.packetSender.sendGameMessage("This item cannot be withdrawn as a note.");
            noteMode = false;
        }
        if (!noteMode || !hasNote) {
            addedAmount = player.getInventoryManager().addItemPartial(new ItemStack(itemId, amount, metadata));
        } else if (noteMode) {
            addedAmount = player.getInventoryManager().addItemPartial(new ItemStack(notedId, amount, metadata));
        }
        player.getBankContainer().removeFromTab(new ItemStack(itemId, addedAmount, metadata), slot, tab);
        player.getBankContainer().removeEmptyTabs();
        player.getInventoryManager().sendToInterface(5064);
        BankManager.refreshBankTabs(player);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("bank withdraw success player=" + GameplayTrace.describe(player) + " itemId=" + itemId + " item=" + itemStack.getDefinition().getName() + " requested=" + amount + " added=" + addedAmount + " remainingBankAmount=" + player.getBankContainer().getItemAmount(itemId));
        }
    }

    private static void refreshBankTabs(Player player) {
        int index = 0;
        while (index < player.getBankContainer().getTabLimit()) {
            BankManager.sendBankTab(player, index);
            ++index;
        }
    }

    public static void rearrangeBankItem(Player player, int sourceSlot, int targetSlot, int sourceInterfaceId, int targetInterfaceId) {
        if (sourceSlot < 0 || targetSlot < 0
                || targetSlot >= player.getBankContainer().getCapacity()
                || sourceSlot >= player.getBankContainer().getCapacity()) {
            return;
        }
        int targetTab = 0;
        int sourceTab = 0;
        int index = 0;
        while (index < bankTabUpdateTasks.length) {
            PlayerUpdateTask task = bankTabUpdateTasks[index];
            if (task.itemContainerInterfaceId == sourceInterfaceId) {
                sourceTab = index;
            }
            task = bankTabUpdateTasks[index];
            if (task.itemContainerInterfaceId == targetInterfaceId) {
                targetTab = index;
            }
            ++index;
        }
        boolean moveBetweenTabs = false;
        if (sourceInterfaceId == 19531) {
            sourceTab = sourceSlot;
            moveBetweenTabs = true;
        }
        if (targetInterfaceId == 19531) {
            targetTab = targetSlot;
            moveBetweenTabs = true;
        } else {
            int targetButtonTab = BankManager.getBankTabIndexForButtonId(targetInterfaceId);
            if (targetButtonTab != -1) {
                targetTab = targetButtonTab;
                moveBetweenTabs = true;
            }
        }
        if (moveBetweenTabs) {
            if (sourceTab < 0 || sourceTab >= player.getBankContainer().getTabLimit()
                    || targetTab < 0 || targetTab >= player.getBankContainer().getTabLimit()
                    || sourceTab == targetTab) {
                return;
            }
            ItemStack sourceItem = player.getBankContainer().getItemAtTabSlot(sourceSlot, sourceTab);
            if (sourceItem == null) {
                return;
            }
            ItemStack movedItem = new ItemStack(sourceItem.getId(), sourceItem.getAmount(), sourceItem.getMetadata());
            int removedAmount = player.getBankContainer().removeFromTab(sourceItem, sourceSlot, sourceTab);
            if (removedAmount != movedItem.getAmount()) {
                if (removedAmount > 0) {
                    player.getBankContainer().setTabItem(sourceSlot, movedItem, sourceTab);
                }
                return;
            }
            boolean addedToTarget = false;
            try {
                addedToTarget = player.getBankContainer().addToTab(movedItem, targetTab);
            } catch (RuntimeException exception) {
                player.getBankContainer().setTabItem(sourceSlot, movedItem, sourceTab);
                throw exception;
            }
            if (!addedToTarget) {
                player.getBankContainer().setTabItem(sourceSlot, movedItem, sourceTab);
                return;
            }
        } else if (!player.getBankRearrangeMode().equals(BankRearrangeMode.SWAP) && player.getBankRearrangeMode().equals(BankRearrangeMode.INSERT) && targetTab == sourceTab) {
            player.getBankContainer().moveTabItem(sourceSlot, targetSlot, sourceTab);
        } else {
            player.getBankContainer().swapTabSlots(sourceSlot, targetSlot, sourceTab, targetTab);
        }
        player.getBankContainer().removeEmptyTabs();
        index = 0;
        while (index < player.getBankContainer().getTabLimit()) {
            player.getBankContainer().compactTab(index);
            BankManager.sendBankTab(player, index);
            ++index;
        }
    }

    private static void equipBotInventoryItems(Player player) {
        int index = 0;
        while (index < player.getInventoryManager().getContainer().getItems().length) {
            ItemStack itemStack = player.getInventoryManager().getContainer().getItems()[index];
            if (itemStack != null) {
                if (player.botMode != 4) {
                    if (itemStack.getDefinition().getEquipmentSlot() == 13) {
                        player.getEquipmentManager().equipFromInventorySlot(index);
                    }
                } else if (itemStack.getDefinition().getEquipmentSlot() != -1) {
                    player.getEquipmentManager().equipFromInventorySlot(index);
                }
            }
            ++index;
        }
    }

    private static ItemStack[] toItemArray(ArrayList<ItemStack> items) {
        ItemStack[] itemArray = new ItemStack[items.size()];
        int index = 0;
        while (index < itemArray.length) {
            itemArray[index] = items.get(index);
            ++index;
        }
        return itemArray;
    }

    private static void sendBankTab(Player player, int tabIndex) {
        ItemStack[] tabItems = player.getBankContainer().getTabItems(tabIndex);
        player.packetSender.sendItemContainer(bankTabUpdateTasks[tabIndex].itemContainerInterfaceId, tabItems);
    }
}
