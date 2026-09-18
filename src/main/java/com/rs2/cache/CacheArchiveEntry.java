package com.rs2.cache;

import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.bot.trade.BotTradeCoinShortageResetTask;
import com.rs2.bot.trade.BotTradeItemShortageResetTask;
import com.rs2.bot.trade.BotTradeOfferTickTask;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.clue.MapClue;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.grandexchange.GrandExchangeOffer;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.Random;

public class CacheArchiveEntry {
    private int nameHash;
    private int compressedSize;
    private int dataOffset;

    public static void startTradeOfferTick(Player ticks) {
        World.getTaskScheduler().schedule(new BotTradeOfferTickTask(3, (Player)ticks));
    }

    public static void completeTradeAdvertOffer(Player player, boolean enabled3) {
        ((Player)player).queuePublicChatMessage("ty");
        boolean enabled2 = true;
        if (((Player)player).tradeAdvertScam || ((Player)player).botAdvertItemId == 0 || ((Player)player).botAdvertItemId == 381) {
            enabled2 = false;
        }
        if (!enabled3 && enabled2) {
            GrandExchangeOffer.recordPriceSample(((Player)player).botAdvertItemId, ((Player)player).tradeAdvertAcceptedQuantity, ((Player)player).tradeAdvertUnitPrice);
        }
        ((Player)player).tradeAdvertQuantityRemaining -= ((Player)player).tradeAdvertAcceptedQuantity;
        BotTradeAdvertManager.updateTradeAdvertMessage((Player)player);
        ((Player)player).tradeAdvertAcceptedQuantity = -1;
        if (((Player)player).tradeAdvertMode == 1) {
            if (!((Player)player).getInventoryManager().containsItem(995) || ((Player)player).tradeAdvertScam) {
                ((Player)player).botTaskState = "wait for new task";
                World.getTaskScheduler().schedule(new BotTradeCoinShortageResetTask(10, (Player)player));
                return;
            }
        } else if (!((Player)player).getInventoryManager().containsItem(((Player)player).botAdvertItemId) || ((Player)player).tradeAdvertScam) {
            ((Player)player).botTaskState = "wait for new task";
            World.getTaskScheduler().schedule(new BotTradeItemShortageResetTask(10, (Player)player));
        }
    }

    public CacheArchiveEntry(int nameHash, int value22, int compressedSize, int dataOffset) {
        this.nameHash = nameHash;
        this.compressedSize = compressedSize;
        this.dataOffset = dataOffset;
    }

    public int getNameHash() {
        return this.nameHash;
    }

    public int getCompressedSize() {
        return this.compressedSize;
    }

    public int getDataOffset() {
        return this.dataOffset;
    }

    public static int randomMapClueItemForLevel(int itemId) {
        int value = new Random().nextInt(MapClue.values().length);
        while (MapClue.values()[value].getLevel() != itemId) {
            value = new Random().nextInt(MapClue.values().length);
        }
        return MapClue.values()[value].getClueItemId();
    }

    public static boolean searchMapClueObject(Player player, int objectId, int value2) {
        MapClue mapClue = MapClue.forPosition(new Position(objectId, value2));
        if (mapClue == null) {
            return false;
        }
        if (!mapClue.isObjectSearchClue()) {
            return false;
        }
        if (!player.getInventoryManager().containsItem(mapClue.getClueItemId())) {
            return false;
        }
        player.getInventoryManager().removeItem(new ItemStack(mapClue.getClueItemId(), 1));
        player.getUpdateState().setAnimation(832);
        TreasureTrailManager.advanceOrCompleteTrail(player, mapClue.getLevel(), "You've found another clue!", false, "");
        return true;
    }
}

