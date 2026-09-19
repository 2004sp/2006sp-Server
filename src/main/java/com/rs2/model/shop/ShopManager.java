package com.rs2.model.shop;

import com.rs2.ServerSettings;
import com.rs2.model.World;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopCurrency;
import com.rs2.model.shop.ShopDefinition;
import com.rs2.model.shop.ShopRestockTask;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import java.util.ArrayList;
import java.util.List;

public final class ShopManager {
    private static List shopDefinitions = new ArrayList(40);

    private static final int CASTLE_WARS_TICKET_ID = 4067;
    private static final int[] CASTLE_WARS_REWARD_ITEM_IDS = new int[]{
            // Novice decorative armour: helm, shield, legs, body, sword.
            4071, 4072, 4070, 4069, 4068,
            // Intermediate decorative armour.
            4506, 4507, 4505, 4504, 4503,
            // Advanced decorative armour.
            4511, 4512, 4510, 4509, 4508,
            // Team rewards: red (Zamorak) cloak/hood, blue (Saradomin) cloak/hood.
            4516, 4515, 4514, 4513
    };
    private static final int[] CASTLE_WARS_REWARD_PRICES = new int[]{
            4, 6, 6, 8, 5,
            40, 60, 60, 80, 50,
            400, 600, 600, 800, 500,
            10, 10, 10, 10
    };
    private static int castleWarsRewardShopId = -1;

    public static void refreshShopForPlayers(int value2) {
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(value2);
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Player player = playerArray[index];
            if (player != null && player.getCurrentShopId() == value2) {
                ItemStack[] itemStackArray = shopDefinition.getStock().getRawItems();
                player.packetSender.sendItemContainer(3900, itemStackArray);
            }
            ++index;
        }
    }

    public static void openShop(Player player, int value2) {
        Player player2;
        if (value2 >= shopDefinitions.toArray().length) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop open invalid-id player=" + GameplayTrace.describe(player) + " shopId=" + value2 + " loaded=" + shopDefinitions.size());
            }
            return;
        }
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(value2);
        if (shopDefinition.isMembersOnly()) {
            if (player.isMember()) {
                if (ServerSettings.freeToPlayWorld) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("shop open blocked-free-world player=" + GameplayTrace.describe(player) + " shopId=" + value2 + " name=" + shopDefinition.getName());
                    }
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    return;
                }
            } else {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("shop open blocked-nonmember player=" + GameplayTrace.describe(player) + " shopId=" + value2 + " name=" + shopDefinition.getName());
                }
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return;
            }
        }
        String name = shopDefinition.getName();
        if (shopDefinition.getCurrency() == ShopCurrency.DONATOR_POINTS) {
            name = "<img=2>" + name + "<img=2>";
            player2 = player;
            player2.packetSender.sendGameMessage("You have " + player.getDonatorPoints() + " Donator points.");
        }
        ItemStack[] itemStackArray = shopDefinition.getStock().getRawItems();
        player.getInventoryManager().sendToInterface(3823);
        player2 = player;
        player2.packetSender.sendItemContainer(3900, itemStackArray);
        player2 = player;
        player2.packetSender.sendInterfaceText(name, 3901);
        player2 = player;
        player2.packetSender.showInterfaceWithInventory(3824, 3822);
        player.setCurrentShopId(value2);
        player.getAttributes().put("isShopping", Boolean.TRUE);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("shop open success player=" + GameplayTrace.describe(player) + " shopId=" + value2 + " name=" + name + " stockSlots=" + itemStackArray.length + " currency=" + shopDefinition.getCurrency());
        }
    }

    private static boolean isSkillcapeBundleItem(int itemId) {
        return itemId >= 7992 && itemId <= 7997 || itemId >= 8001 && itemId <= 8012 || itemId >= 8016 && itemId <= 8065;
    }

    private static boolean requiresSecondaryCurrencyItem(int itemId) {
        return itemId == 7958 || itemId == 7972 || itemId == 7974 || itemId == 7976 || itemId == 7982 || itemId == 7984 || itemId == 7986 || itemId == 8068 || itemId == 8076 || itemId == 8078;
    }

    public static void buyItemStack(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(player.getCurrentShopId());
        if (shopDefinition == null) {
            return;
        }
        int stock = shopDefinition.getStock().indexOfItem(itemStack.getId());
        if (stock == -1) {
            return;
        }
        ShopManager.buyItem(player, stock, itemStack.getId(), itemStack.getAmount());
    }

    public static void buyItem(Player player, int itemId, int value10, int value32) {
        int value2;
        int value4;
        int value5;
        Object value6;
        int value7;
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(player.getCurrentShopId());
        ItemContainer itemContainer = player.getInventoryManager().getContainer();
        ItemStack itemStack = shopDefinition.getStock().getItemAt(itemId);
        int traceRequestedAmount = value32;
        int traceStockItemId = itemStack != null ? itemStack.getId() : -1;
        int traceStockSlotAmountBefore = itemStack != null ? itemStack.getAmount() : -1;
        if (shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY) {
            value5 = shopDefinition.getCurrencyItemId();
        } else {
            ShopDefinition shopDefinition2 = shopDefinition;
            value6 = player;
            switch (shopDefinition2.getCurrency()) {
                case DONATOR_POINTS: {
                    value5 = ((Player)value6).getDonatorPoints();
                    break;
                }
                default: {
                    value5 = -1;
                }
            }
        }
        int traceCurrencyBefore = shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY ? itemContainer.getItemAmount(value5) : -1;
        int traceInventoryBefore = itemContainer.getItemAmount(value10);
        int traceStockBefore = itemStack != null ? shopDefinition.getStock().getItemAmount(value10) : -1;
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("shop buy request player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " slot=" + itemId + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " stockSlotItemId=" + traceStockItemId + " stockSlotAmountBefore=" + traceStockSlotAmountBefore + " stockItemAmountBefore=" + traceStockBefore + " currency=" + shopDefinition.getCurrency() + " currencyRef=" + value5 + " currencyBefore=" + traceCurrencyBefore + " inventoryItemBefore=" + traceInventoryBefore);
        }
        if (value32 <= 0 || value10 < 0 || itemStack == null || !itemStack.isValid()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop buy rejected-invalid player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " slot=" + itemId + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " stockSlotItemId=" + traceStockItemId);
            }
            if (player.botEnabled) {
                player.deferredBotTask = null;
                player.botTaskReturnToBankRequested = true;
                player.currentBotTask.startWalkToBank(player);
            }
            return;
        }
        if (value10 != itemStack.getId()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop buy rejected-item-mismatch player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " slot=" + itemId + " requestedItemId=" + value10 + " stockItemId=" + itemStack.getId());
            }
            return;
        }
        boolean enabled = false;
        if (ShopManager.requiresSecondaryCurrencyItem(itemStack.getId())) {
            enabled = true;
        }
        if (shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY) {
            ItemService.getInstance();
            value4 = ItemService.getPrice(value10, "buyfromshop", value5);
        } else {
            ItemService.getInstance();
            value4 = ItemService.getPrice(value10, "donator", value5);
            if (value5 < value4 * value32) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("shop buy rejected-donator-points player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " points=" + value5 + " unitPrice=" + value4);
                }
                value6 = player;
                ((Player)value6).packetSender.sendGameMessage("You do not have enough donator points to buy this item.");
                return;
            }
            if (enabled) {
                ItemDefinition itemDefinition = ItemDefinition.forId(itemStack.getId());
                value2 = player.getInventoryManager().getItemAmount(itemDefinition.secondaryCurrencyItemId);
                if (value2 < value32) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("shop buy rejected-secondary-currency player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " secondaryItemId=" + itemDefinition.secondaryCurrencyItemId + " secondaryAmount=" + value2);
                    }
                    value6 = player;
                    PacketSender packetSender = ((Player)value6).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You do not have enough ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(itemDefinition.secondaryCurrencyItemId)).append("(s) to buy this item.").toString());
                    return;
                }
            }
        }
        if (shopDefinition.getStock().getItemAt(itemId).getAmount() < value32) {
            value32 = shopDefinition.getStock().getItemAt(itemId).getAmount();
        }
        int freeSlots = itemContainer.getFreeSlots();
        int value8 = value4;
        int value9 = value5;
        value6 = player.getInventoryManager();
        if (((InventoryManager)value6).containsItem(value9) && ((InventoryManager)value6).getContainer().getItemAmount(value9) == value8) {
            ++freeSlots;
        }
        int definition = value2 = !itemStack.getDefinition().isStackable() ? value32 : 1;
        if (ShopManager.isSkillcapeBundleItem(itemStack.getId())) {
            value2 = 2 * value32;
        }
        if (!(itemStack.getDefinition().isStackable() && player.getInventoryManager().containsItem(value10) || freeSlots >= value2)) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop buy adjusted-no-space player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " adjustedAmount=" + freeSlots + " freeSlots=" + itemContainer.getFreeSlots() + " requiredSlots=" + value2);
            }
            value32 = freeSlots;
            value6 = player;
            ((Player)value6).packetSender.sendGameMessage("Not enough space in your inventory.");
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
            if (ShopManager.isSkillcapeBundleItem(itemStack.getId())) {
                return;
            }
        }
        if (shopDefinition.getStock().getItemAt(itemId).getAmount() < value32) {
            value32 = shopDefinition.getStock().getItemAt(itemId).getAmount();
        }
        if (shopDefinition.isGeneralStore() && shopDefinition.getStock().getItemAt(itemId).getAmount() == 0) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop buy rejected-out-of-stock player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount);
            }
            value6 = player;
            ((Player)value6).packetSender.sendGameMessage("This item is out of stock.");
            return;
        }
        itemId = 0;
        if (shopDefinition.getOriginalStock().findFlatItem(value10) != null) {
            itemId = shopDefinition.getOriginalStock().findFlatItem(value10).getAmount();
        }
        freeSlots = shopDefinition.getStock().getItemAmount(value10);
        value2 = 0;
        if (player.gameMode != 0 && freeSlots > itemId) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop buy rejected-overstock-gamemode player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " stockAmount=" + freeSlots + " originalAmount=" + itemId + " gameMode=" + player.gameMode);
            }
            value6 = player;
            ((Player)value6).packetSender.sendGameMessage("You are not playing on normal gamemode and cannot buy overstocked items.");
            return;
        }
        freeSlots = 0;
        while (freeSlots < value32) {
            if (shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY) {
                int itemAmount = itemContainer.getItemAmount(value5);
                itemId = ShopManager.calculateBuyPrice(shopDefinition, value10, freeSlots);
                if (itemId > itemAmount) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("shop buy rejected-currency player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " purchasedBeforeFailure=" + value2 + " unitPrice=" + itemId + " currencyHeld=" + itemAmount + " currencyItemId=" + value5);
                    }
                    Player player2 = player;
                    PacketSender packetSender = player2.packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You do not have enough ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value5)).append(" to buy this item.").toString());
                    value32 = value2;
                    if (!player.botEnabled) break;
                    player.currentBotTask.startWalkToBank(player);
                    break;
                }
                player.getInventoryManager().removeItem(new ItemStack(value5, itemId));
                value2 = freeSlots + 1;
                if (value5 == 995) {
                    player.boughtItemsValue += value4;
                }
            } else {
                if (enabled) {
                    ItemDefinition itemDefinition = ItemDefinition.forId(itemStack.getId());
                    player.getInventoryManager().removeItem(new ItemStack(itemDefinition.secondaryCurrencyItemId, 1));
                }
                value8 = value4;
                ShopDefinition shopDefinition3 = shopDefinition;
                Player player3 = player;
                switch (shopDefinition3.getCurrency()) {
                    case DONATOR_POINTS: {
                        player3.subtractDonatorPoints(value8);
                    }
                }
                if (shopDefinition.getCurrency() == ShopCurrency.DONATOR_POINTS) {
                    player3 = player;
                    player3.packetSender.sendGameMessage("You have " + player.getDonatorPoints() + " Donator points remaining.");
                }
            }
            ++freeSlots;
        }
        freeSlots = ShopManager.indexOfStockItem(shopDefinition, itemStack.getId());
        if (shopDefinition.getOriginalStock().containsItem(itemStack.getId())) {
            shopDefinition.getStock().removeKeepingPlaceholder(new ItemStack(itemStack.getId(), value32));
        } else {
            shopDefinition.getStock().remove(new ItemStack(itemStack.getId(), value32));
        }
        ShopManager.updateRestockSchedule(shopDefinition, itemStack.getId(), freeSlots);
        if (!ShopManager.isSkillcapeBundleItem(value10)) {
            ItemStack itemStack2 = new ItemStack(value10, value32);
            player.getInventoryManager().addItem(itemStack2);
            if (player.botEnabled) {
                player.botTaskReturnToBankRequested = true;
                player.currentBotTask.startWalkToBank(player);
            }
            if (shopDefinition.getCurrency() == ShopCurrency.DONATOR_POINTS) {
                GameUtil.addTrackedRareItemAmount(itemStack2);
            }
        } else {
            player.getInventoryManager().addItem(new ItemStack(player.getMaxedSkillCount() >= 2 && value10 != 8058 ? value10 + 1 : value10, value32));
            player.getInventoryManager().addItem(new ItemStack(value10 != 8058 ? value10 + 2 : value10 + 1, value32));
        }
        player.getInventoryManager().sendToInterface(3823);
        ShopManager.refreshShopForPlayers(player.getCurrentShopId());
        if (GameplayTrace.enabled()) {
            int traceCurrencyAfter = shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY ? itemContainer.getItemAmount(value5) : -1;
            String traceResult = value32 > 0 ? "shop buy success" : "shop buy completed-none";
            GameplayTrace.log(traceResult + " player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " stockSlot=" + freeSlots + " itemId=" + value10 + " requestedAmount=" + traceRequestedAmount + " purchasedAmount=" + value32 + " currency=" + shopDefinition.getCurrency() + " currencyRef=" + value5 + " currencyBefore=" + traceCurrencyBefore + " currencyAfter=" + traceCurrencyAfter + " inventoryItemBefore=" + traceInventoryBefore + " inventoryItemAfter=" + itemContainer.getItemAmount(value10) + " stockItemAmountBefore=" + traceStockBefore + " stockItemAmountAfter=" + shopDefinition.getStock().getItemAmount(value10));
        }
    }

    private static int calculateBuyPrice(ShopDefinition shopDefinition, int value3, int value22) {
        int castleWarsPrice = ShopManager.getCastleWarsRewardPrice(shopDefinition, value3);
        if (castleWarsPrice >= 0) {
            return castleWarsPrice;
        }
        int definition = new ItemStack(value3).getDefinition().getShopValue();
        int index = 0;
        if (shopDefinition.getOriginalStock().findFlatItem(value3) != null) {
            index = shopDefinition.getOriginalStock().findFlatItem(value3).getAmount();
        }
        value3 = shopDefinition.getStock().getItemAmount(value3) - value22;
        value3 -= index;
        if (!ServerSettings.dynamicShopPricesEnabled) {
            value3 = 0;
        }
        value22 = definition * 30;
        int buyPricePercent = (int)((double)definition * ((double)shopDefinition.getBuyPricePercent() - shopDefinition.getPriceChangeRate() * (double)value3));
        buyPricePercent /= 100;
        buyPricePercent = Math.max(buyPricePercent, value22 /= 100);
        return buyPricePercent;
    }

    private static int calculateSellPrice(ShopDefinition shopDefinition, int value3, int value22) {
        int castleWarsPrice = ShopManager.getCastleWarsRewardPrice(shopDefinition, value3);
        if (castleWarsPrice >= 0) {
            return castleWarsPrice;
        }
        int definition = new ItemStack(value3).getDefinition().getShopValue();
        int index = 0;
        if (shopDefinition.getOriginalStock().findFlatItem(value3) != null) {
            index = shopDefinition.getOriginalStock().findFlatItem(value3).getAmount();
        }
        value3 = shopDefinition.getStock().getItemAmount(value3) + value22;
        value3 -= index;
        if (!ServerSettings.dynamicShopPricesEnabled) {
            value3 = 0;
        }
        int sellPricePercent = (int)(((double)shopDefinition.getSellPricePercent() - shopDefinition.getPriceChangeRate() * (double)Math.min(value3, 10)) * (double)definition);
        value3 = definition / 10;
        sellPricePercent = (sellPricePercent /= 100) < value3 ? value3 : sellPricePercent;
        return sellPricePercent;
    }

    public static void sellItem(Player player, int itemId, int value5, int value32) {
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(player.getCurrentShopId());
        ItemContainer itemContainer = player.getInventoryManager().getContainer();
        ItemStack itemStack = itemContainer.getItemAt(itemId);
        int currencyItemId = shopDefinition.getCurrencyItemId();
        int traceRequestedAmount = value32;
        int traceCurrencyBefore = itemContainer.getItemAmount(currencyItemId);
        int traceInventoryBefore = itemContainer.getItemAmount(value5);
        if (itemStack == null) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-empty-slot player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " slot=" + itemId + " itemId=" + value5 + " requestedAmount=" + traceRequestedAmount);
            }
            return;
        }
        int value2 = value5;
        if (itemStack.getDefinition().isNote()) {
            value2 = itemStack.getDefinition().getUnnotedId();
        }
        int stock = shopDefinition.getStock().getItemAmount(value2);
        int traceStockBefore = stock;
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("shop sell request player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " slot=" + itemId + " itemId=" + value5 + " stockItemId=" + value2 + " requestedAmount=" + traceRequestedAmount + " inventoryItemBefore=" + traceInventoryBefore + " stockItemAmountBefore=" + traceStockBefore + " currencyItemId=" + currencyItemId + " currencyBefore=" + traceCurrencyBefore);
        }
        if (!ServerSettings.adminInteractionsAllowed && player.getPlayerRights() >= 2) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-admin player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5);
            }
            Player player2 = player;
            player2.packetSender.sendGameMessage("This action is not allowed.");
            return;
        }
        if (itemStack.getId() != value5 || !itemStack.isValid()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-item-mismatch player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " slot=" + itemId + " requestedItemId=" + value5 + " slotItemId=" + itemStack.getId());
            }
            return;
        }
        if (shopDefinition.getCurrency() != ShopCurrency.ITEM_CURRENCY) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-non-item-currency player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5 + " currency=" + shopDefinition.getCurrency());
            }
            Player player3 = player;
            player3.packetSender.sendGameMessage("This shop can't buy anything.");
            return;
        }
        if (shopDefinition.getStock().getFreeSlots() <= 0 && stock <= 0) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-shop-full player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5 + " stockItemId=" + value2);
            }
            Player player4 = player;
            player4.packetSender.sendGameMessage("The shop is currently full!");
            return;
        }
        if (value5 == 995) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-currency-item player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5);
            }
            Player player5 = player;
            player5.packetSender.sendGameMessage("You cannot sell coins to the shop.");
            return;
        }
        boolean castleWarsRefund = ShopManager.getCastleWarsRewardPrice(shopDefinition, value2) >= 0;
        if (!shopDefinition.isGeneralStore() && !shopDefinition.getStock().containsItem(value2)
                || itemStack.getDefinition().isUntradeable() && !castleWarsRefund) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-unsellable player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5 + " stockItemId=" + value2 + " generalStore=" + shopDefinition.isGeneralStore() + " untradeable=" + itemStack.getDefinition().isUntradeable());
            }
            Player player6 = player;
            player6.packetSender.sendGameMessage("You cannot sell this item in this shop.");
            return;
        }
        int itemAmount = itemContainer.getItemAmount(value5);
        if (value32 <= 0 || value5 < 0) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-invalid player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5 + " requestedAmount=" + traceRequestedAmount);
            }
            return;
        }
        if (value5 > 11883) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-unsupported-item player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5);
            }
            Player player7 = player;
            player7.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if (!itemContainer.containsItem(value5)) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-not-in-inventory player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5);
            }
            return;
        }
        if (value32 >= itemAmount) {
            value32 = itemAmount;
        }
        itemAmount = 0;
        if (shopDefinition.getOriginalStock().findFlatItem(value2) != null) {
            itemAmount = shopDefinition.getOriginalStock().findFlatItem(value2).getAmount();
        }
        if (!ShopManager.isCastleWarsRewardShop(shopDefinition) && player.gameMode != 0 && stock < itemAmount) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("shop sell rejected-understock-gamemode player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value5 + " stockItemId=" + value2 + " stockAmount=" + stock + " originalAmount=" + itemAmount + " gameMode=" + player.gameMode);
            }
            Player player8 = player;
            player8.packetSender.sendGameMessage("You are not playing on normal gamemode and cannot sell understocked items.");
            return;
        }
        player.getInventoryManager().removeItem(new ItemStack(value5, value32));
        double value4 = 0.0;
        itemAmount = 0;
        while (itemAmount < value32) {
            value4 += (double)ShopManager.calculateSellPrice(shopDefinition, value5, itemAmount);
            ++itemAmount;
        }
        if (!player.botEnabled || player.currentBotTask != null) {
            player.getInventoryManager().addItem(new ItemStack(currencyItemId, (int)value4));
        }
        if (currencyItemId == 995) {
            player.soldItemsValue = (int)((double)player.soldItemsValue + value4);
        }
        itemAmount = ShopManager.indexOfStockItem(shopDefinition, value2);
        if (shopDefinition.isGeneralStore() && stock <= 0) {
            ItemStack itemStack2 = new ItemStack(value2, value32);
            ItemContainer itemContainer2 = shopDefinition.getStock();
            itemContainer2.add(itemStack2, -1);
        } else {
            shopDefinition.getStock().setItem(shopDefinition.getStock().indexOfItem(value2), new ItemStack(value2, stock + value32));
        }
        ShopManager.updateRestockSchedule(shopDefinition, value2, itemAmount);
        if (player.botShopSellItemIds.contains(value2)) {
            itemAmount = player.botShopSellItemIds.indexOf(value2);
            player.botShopSellItemIds.remove(itemAmount);
        }
        if (player.botEnabled && player.currentBotTask != null && player.botShopSellItemIds.size() == 0) {
            player.botTaskReturnToBankRequested = true;
            player.currentBotTask.startWalkToBank(player);
        }
        player.getInventoryManager().sendToInterface(3823);
        ShopManager.refreshShopForPlayers(player.getCurrentShopId());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("shop sell success player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " stockSlot=" + itemAmount + " itemId=" + value5 + " stockItemId=" + value2 + " requestedAmount=" + traceRequestedAmount + " soldAmount=" + value32 + " currencyItemId=" + currencyItemId + " currencyBefore=" + traceCurrencyBefore + " currencyAfter=" + itemContainer.getItemAmount(currencyItemId) + " currencyAdded=" + (int)value4 + " inventoryItemBefore=" + traceInventoryBefore + " inventoryItemAfter=" + itemContainer.getItemAmount(value5) + " stockItemAmountBefore=" + traceStockBefore + " stockItemAmountAfter=" + shopDefinition.getStock().getItemAmount(value2));
        }
    }

    public static void sellItemStack(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        ShopManager.sellItem(player, player.getInventoryManager().getContainer().indexOfItem(itemStack.getId()), itemStack.getId(), itemStack.getAmount());
    }

    public static void sendBuyPrice(Player player, int value3) {
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(player.getCurrentShopId());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("shop buy-price request player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value3 + " currency=" + shopDefinition.getCurrency() + " currencyItemId=" + shopDefinition.getCurrencyItemId());
        }
        if (shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY) {
            int value2 = value3;
            ItemService.getInstance();
            String itemName = ItemService.getItemName(shopDefinition.getCurrencyItemId());
            int buyPrice = ShopManager.calculateBuyPrice(shopDefinition, value2, 0);
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder();
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value3)).append(": currently costs ").append(GameUtil.formatNumber((double)buyPrice)).append(" ").append(itemName).append(".").toString());
            return;
        }
        ItemService.getInstance();
        int price = ItemService.getPrice(value3, "donator", shopDefinition.getCurrencyItemId());
        boolean enabled = false;
        if (ShopManager.requiresSecondaryCurrencyItem(value3)) {
            enabled = true;
        }
        if (!enabled) {
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder();
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value3)).append(": currently costs ").append(price).append(" ").append(ShopManager.getCurrencyDisplayName(shopDefinition)).append(".").toString());
            return;
        }
        ItemDefinition itemDefinition = ItemDefinition.forId(value3);
        itemDefinition = ItemDefinition.forId(itemDefinition.secondaryCurrencyItemId);
        PacketSender packetSender = player.packetSender;
        StringBuilder stringBuilder = new StringBuilder();
        ItemService.getInstance();
        packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value3)).append(": currently costs ").append(price).append(" ").append(ShopManager.getCurrencyDisplayName(shopDefinition)).append(" + ").append(itemDefinition.getName()).append(".").toString());
    }

    public static void sendSellPrice(Player player, int value4) {
        ShopDefinition shopDefinition = (ShopDefinition)shopDefinitions.get(player.getCurrentShopId());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("shop sell-price request player=" + GameplayTrace.describe(player) + " shopId=" + player.getCurrentShopId() + " itemId=" + value4 + " currency=" + shopDefinition.getCurrency() + " currencyItemId=" + shopDefinition.getCurrencyItemId());
        }
        Object value2 = ItemDefinition.forId(value4);
        shopDefinition.getCurrencyItemId();
        int value3 = value4;
        if (((ItemDefinition)value2).isNote()) {
            value3 = ((ItemDefinition)value2).getUnnotedId();
        }
        if (shopDefinition.getCurrency() != ShopCurrency.ITEM_CURRENCY) {
            player.packetSender.sendGameMessage("This shop can't buy anything.");
            return;
        }
        if (value4 == 995) {
            player.packetSender.sendGameMessage("You cannot sell coins to the shop.");
            return;
        }
        boolean castleWarsRefund = ShopManager.getCastleWarsRewardPrice(shopDefinition, value3) >= 0;
        if (!shopDefinition.isGeneralStore() && !shopDefinition.getStock().containsItem(value3)
                || ((ItemDefinition)value2).isUntradeable() && !castleWarsRefund) {
            player.packetSender.sendGameMessage("You cannot sell this item in this shop.");
            return;
        }
        if (shopDefinition.getCurrency() == ShopCurrency.ITEM_CURRENCY || shopDefinition.isGeneralStore()) {
            value2 = shopDefinition;
            int sellPrice = ShopManager.calculateSellPrice((ShopDefinition)value2, value3, 0);
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder();
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value4)).append(": shop will buy for ").append(GameUtil.formatNumber((double)sellPrice)).append(" ").append(ShopManager.getCurrencyDisplayName(shopDefinition)).append(".").toString());
            return;
        }
        player.packetSender.sendGameMessage("You cannot sell this item to this shop.");
    }

    private static String getCurrencyDisplayName(ShopDefinition shopDefinition) {
        switch (shopDefinition.getCurrency()) {
            case DONATOR_POINTS: {
                return "Donator Points";
            }
        }
        ItemService.getInstance();
        return ItemService.getItemName(shopDefinition.getCurrencyItemId());
    }

    public static void openCastleWarsRewardShop(Player player) {
        if (player == null) {
            return;
        }
        if (castleWarsRewardShopId < 0) {
            player.packetSender.sendGameMessage("The Castle Wars Ticket Exchange is currently unavailable.");
            return;
        }
        int ticketCount = player.getInventoryManager().getItemAmount(CASTLE_WARS_TICKET_ID);
        player.packetSender.sendGameMessage("You have " + ticketCount + " Castle Wars ticket" + (ticketCount == 1 ? "." : "s."));
        ShopManager.openShop(player, castleWarsRewardShopId);
    }

    private static boolean isCastleWarsRewardShop(ShopDefinition shopDefinition) {
        return shopDefinition != null
                && castleWarsRewardShopId >= 0
                && shopDefinition.getShopId() == castleWarsRewardShopId;
    }

    private static int getCastleWarsRewardPrice(ShopDefinition shopDefinition, int itemId) {
        if (!ShopManager.isCastleWarsRewardShop(shopDefinition)) {
            return -1;
        }
        int index = 0;
        while (index < CASTLE_WARS_REWARD_ITEM_IDS.length) {
            if (CASTLE_WARS_REWARD_ITEM_IDS[index] == itemId) {
                return CASTLE_WARS_REWARD_PRICES[index];
            }
            ++index;
        }
        return -1;
    }

    private static void ensureCastleWarsRewardStock(ShopDefinition shopDefinition) {
        if (shopDefinition == null) {
            return;
        }
        ItemContainer originalStock = shopDefinition.getOriginalStock();
        ItemContainer stock = shopDefinition.getStock();
        for (int itemId : CASTLE_WARS_REWARD_ITEM_IDS) {
            if (!originalStock.containsItem(itemId)) {
                originalStock.add(new ItemStack(itemId, 1), -1);
            }
            if (!stock.containsItem(itemId)) {
                stock.add(new ItemStack(itemId, 1), -1);
            }
        }
    }

    public static void loadShops() {
        try {
            castleWarsRewardShopId = -1;
            byte[] byteValues = FileUtil.readBytes("./data/content/Shops.dat");
            ByteArrayReader byteArrayReader = new ByteArrayReader(byteValues);
            int value = byteArrayReader.readUnsignedShort();
            int index = 0;
            while (index < value) {
                ShopDefinition shopDefinition = new ShopDefinition();
                String text = byteArrayReader.readString();
                boolean enabled = byteArrayReader.readUnsignedByte() == 1;
                int value2 = byteArrayReader.readUnsignedByte();
                int value3 = byteArrayReader.readUnsignedByte();
                int value4 = byteArrayReader.readUnsignedByte();
                int value5 = byteArrayReader.readUnsignedByte();
                int value6 = byteArrayReader.readUnsignedByte();
                ItemContainer itemContainer = new ItemContainer(ItemContainerType.b, 40);
                ItemContainer itemContainer2 = new ItemContainer(ItemContainerType.b, 40);
                ShopDefinition.setRestockDelayTicks(shopDefinition, new int[40]);
                ShopDefinition.setRestockTasks(shopDefinition, new TickTask[40]);
                int index2 = 0;
                while (index2 < 40) {
                    ShopDefinition.getRestockDelayTicks((ShopDefinition)shopDefinition)[index2] = 100;
                    ++index2;
                }
                index2 = 0;
                while (index2 < value6) {
                    int value7 = byteArrayReader.readUnsignedShort();
                    int value8 = byteArrayReader.readUnsignedShort();
                    int value9 = byteArrayReader.readUnsignedShort();
                    value8 = (int)((double)value8 * ServerSettings.shopItemMultiplier);
                    if (!(ServerSettings.freeToPlayWorld && index != 169 && new ItemStack(value7).getDefinition().isMembersOnly() || !ItemDefinition.isDefined(value7))) {
                        ShopDefinition.getRestockDelayTicks((ShopDefinition)shopDefinition)[index2] = value9;
                        ItemStack itemStack = new ItemStack(value7, value8);
                        ItemContainer itemContainer3 = itemContainer;
                        itemContainer3.add(itemStack, -1);
                        itemStack = new ItemStack(value7, value8);
                        itemContainer3 = itemContainer2;
                        itemContainer3.add(itemStack, -1);
                    }
                    ++index2;
                }
                shopDefinition.setGeneralStore(value2 == 0);
                shopDefinition.setOriginalStock(itemContainer);
                shopDefinition.setStock(itemContainer2);
                index2 = byteArrayReader.readUnsignedShort();
                shopDefinition.setName(text);
                shopDefinition.setMembersOnly(enabled);
                shopDefinition.setCurrencyItemId(index2);
                shopDefinition.setCurrency(ShopCurrency.values()[value2 == 2 ? 1 : 0]);
                shopDefinition.setBuyPricePercent(value3);
                shopDefinition.setSellPricePercent(value4);
                shopDefinition.setPriceChangeRateTenths(value5);
                ShopDefinition.setShopId(shopDefinition, index);
                if ("Castle Wars Ticket Exchange".equals(text) && index2 == CASTLE_WARS_TICKET_ID) {
                    castleWarsRewardShopId = index;
                    ShopManager.ensureCastleWarsRewardStock(shopDefinition);
                }
                shopDefinitions.add(shopDefinition);
                ++index;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static void updateRestockSchedule(ShopDefinition shopDefinition, int value4, int value22) {
        int value3;
        TickTask tickTask = null;
        if (value22 != -1) {
            tickTask = ShopDefinition.getRestockTasks(shopDefinition)[value22];
        }
        if (shopDefinition.getOriginalStock().containsItem(value4)) {
            value3 = shopDefinition.getOriginalStock().findFlatItem(value4).getAmount();
            int stock = shopDefinition.getStock().findFlatItem(value4).getAmount();
            if (stock < value3) {
                ShopManager.startRestockTask(shopDefinition, value4);
                return;
            }
            if (stock > value3) {
                ShopManager.startRestockTask(shopDefinition, value4);
                return;
            }
        } else if (shopDefinition.getStock().containsItem(value4)) {
            ShopManager.startRestockTask(shopDefinition, value4);
            return;
        }
        if (tickTask != null) {
            tickTask.stop();
            value3 = ShopDefinition.getRestockDelayTicks(shopDefinition)[value22];
            tickTask.setIntervalTicks(value3);
            tickTask.setRemainingTicks(value3);
        }
    }

    public static int indexOfStockItem(ShopDefinition shopDefinition, int itemId) {
        if (shopDefinition.getStock().containsItem(itemId)) {
            return shopDefinition.getStock().indexOfItem(itemId);
        }
        return -1;
    }

    private static void startRestockTask(ShopDefinition shopDefinition, int value4) {
        int value2 = ShopManager.indexOfStockItem(shopDefinition, value4);
        int restockDelayTicks = ShopDefinition.getRestockDelayTicks(shopDefinition)[value2];
        double value3 = (double)restockDelayTicks * ServerSettings.shopRestockTimeMultiplier;
        restockDelayTicks = (int)value3;
        if (ShopDefinition.getRestockTasks(shopDefinition)[value2] != null && ShopDefinition.getRestockTasks(shopDefinition)[value2].isActive()) {
            return;
        }
        ShopDefinition.getRestockTasks((ShopDefinition)shopDefinition)[value2] = new ShopRestockTask(restockDelayTicks, shopDefinition, value4, value2);
        World.getTaskScheduler().schedule(ShopDefinition.getRestockTasks(shopDefinition)[value2]);
    }

    public static boolean needsRestock(ShopDefinition shopDefinition, int value4, int value22) {
        if (shopDefinition.getOriginalStock().containsItem(value4)) {
            int originalStock = shopDefinition.getOriginalStock().findFlatItem(value4).getAmount();
            int stock = shopDefinition.getStock().findFlatItem(value4).getAmount();
            if (stock < originalStock) {
                return true;
            }
            if (stock > originalStock) {
                return true;
            }
        } else {
            int value3 = ShopManager.indexOfStockItem(shopDefinition, value4);
            if (value3 != value22) {
                return false;
            }
            if (shopDefinition.getStock().containsItem(value4)) {
                return true;
            }
        }
        return false;
    }

    public static List getShopDefinitions() {
        return shopDefinitions;
    }
}
