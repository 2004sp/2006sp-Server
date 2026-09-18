package com.rs2.model.grandexchange;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.grandexchange.GrandExchangePriceSample;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.player.CharacterFileRecord;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Iterator;

public final class GrandExchangeOffer {
    private String ownerUsername;
    private boolean sellOffer;
    int itemId;
    private int slotIndex;
    int quantity;
    private int remainingQuantity;
    int unitPrice;
    private long serverOfferCreatedAtMillis;
    private int serverOfferLifetimeMinutes;
    private static ArrayList sellOffers = new ArrayList();
    private static ArrayList buyOffers = new ArrayList();
    private static ArrayList serverOffers = new ArrayList();
    private static ArrayList pendingBuyOfferRemovals = new ArrayList();
    private static ArrayList pendingSellOfferRemovals = new ArrayList();

    public GrandExchangeOffer(String text2, int value6, boolean enabled2, int value22, int value32, int value42, int value52) {
        new GrandExchangeOffer(text2, value6, enabled2, value22, value32, value42, value52, 0L, 0);
    }

    private GrandExchangeOffer(String ownerUsername, int slotIndex, boolean sellOffer, int itemId, int remainingQuantity, int quantity, int unitPrice, long serverOfferCreatedAtMillis, int serverOfferLifetimeMinutes) {
        this.ownerUsername = ownerUsername;
        this.itemId = itemId;
        this.sellOffer = sellOffer;
        this.remainingQuantity = remainingQuantity;
        this.unitPrice = unitPrice;
        this.slotIndex = slotIndex;
        this.quantity = quantity;
        this.serverOfferCreatedAtMillis = serverOfferCreatedAtMillis;
        this.serverOfferLifetimeMinutes = serverOfferLifetimeMinutes;
        if (sellOffer) {
            sellOffers.add(this);
        } else {
            buyOffers.add(this);
        }
        this.matchOffer(this, sellOffer);
    }

    public static void initializeServerOffers() {
        int index = 0;
        while (index < ServerSettings.grandExchangeServerOfferCount) {
            GrandExchangeOffer.createServerOffer();
            ++index;
        }
    }

    private static void createServerOffer() {
        double value;
        boolean enabled = GameUtil.randomInt(2) == 0;
        int value2 = GameUtil.randomInt(100);
        Object value3 = value2 >= BotTradeAdvertManager.commonItemChancePercent ? BotTradeAdvertManager.tradeAdvertOfferPool : BotTradeAdvertManager.commonTradeAdvertOfferPool;
        int value4 = GameUtil.randomInt(((ArrayList)value3).size());
        value3 = (GameplayHelper)((ArrayList)value3).get(value4);
        value4 = BotTradeAdvertManager.tradeAdvertOfferPool.indexOf(value3);
        int tradeAdvertItemId = ((GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(value4)).getTradeAdvertItemId();
        ItemDefinition.forId(tradeAdvertItemId);
        double guidePrice = GrandExchangeManager.getGuidePrice(tradeAdvertItemId);
        if (guidePrice < 1.0) {
            guidePrice = 1.0;
        }
        int value5 = GameUtil.rollPriceFluctuationPercent(11);
        double value6 = guidePrice / 100.0 * (double)value5;
        if (enabled && GameUtil.randomInt(10) == 0) {
            value6 = -value6;
        }
        if (!enabled && GameUtil.randomInt(10) == 0) {
            value6 = -value6;
        }
        double value7 = enabled ? guidePrice + value6 : guidePrice - value6;
        value5 = (int)value7;
        int tradeAdvertQuantityOptions = GameUtil.randomInt(((GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(value4)).getTradeAdvertQuantityOptions().length);
        value4 = GameUtil.randomInt(((GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(value4)).getTradeAdvertQuantityOptions()[tradeAdvertQuantityOptions]) + 1;
        value5 = value4 * value5;
        if ((value5 = BotTradeAdvertManager.roundAdvertUnitPrice(enabled, value5, value4)) <= 0) {
            value5 = 1 + GameUtil.randomInt(5);
        }
        tradeAdvertQuantityOptions = 2 + GameUtil.randomInt(4);
        GrandExchangeOffer grandExchangeOffer = new GrandExchangeOffer("[SERVER]", 0, enabled, tradeAdvertItemId, value4, value4, value5, System.currentTimeMillis(), tradeAdvertQuantityOptions);
        serverOffers.add(grandExchangeOffer);
    }

    public static void processServerOffers() {
        int index = 0;
        ArrayList<GrandExchangeOffer> arrayList = new ArrayList<GrandExchangeOffer>();
        ArrayList<GrandExchangeOffer> arrayList2 = new ArrayList<GrandExchangeOffer>();
        ArrayList<GrandExchangeOffer> arrayList3 = new ArrayList<GrandExchangeOffer>();
        Iterator iterator = serverOffers.iterator();
        while (iterator.hasNext()) {
            GrandExchangeOffer grandExchangeOffer;
            GrandExchangeOffer grandExchangeOffer2 = grandExchangeOffer = (GrandExchangeOffer)iterator.next();
            if (!(System.currentTimeMillis() >= grandExchangeOffer2.serverOfferCreatedAtMillis + (long)GameUtil.minutesToMillis(grandExchangeOffer2.serverOfferLifetimeMinutes))) continue;
            if (grandExchangeOffer.sellOffer) {
                arrayList.add(grandExchangeOffer);
            } else {
                arrayList2.add(grandExchangeOffer);
            }
            arrayList3.add(grandExchangeOffer);
            ++index;
        }
        for (GrandExchangeOffer grandExchangeOffer : arrayList) {
            sellOffers.remove(grandExchangeOffer);
        }
        for (GrandExchangeOffer grandExchangeOffer : arrayList2) {
            buyOffers.remove(grandExchangeOffer);
        }
        for (GrandExchangeOffer grandExchangeOffer : arrayList3) {
            serverOffers.remove(grandExchangeOffer);
        }
        int index2 = 0;
        while (index2 < index) {
            GrandExchangeOffer.createServerOffer();
            ++index2;
        }
    }

    public static void cancelOffer(Player player, int value2) {
        GrandExchangeOffer grandExchangeOffer;
        GrandExchangeOffer grandExchangeOffer22;
        Iterator iterator;
        String username = player.getUsername();
        if (player.grandExchangeSellOfferFlags[value2]) {
            iterator = sellOffers.iterator();
            while (iterator.hasNext()) {
                grandExchangeOffer = grandExchangeOffer22 = (GrandExchangeOffer)iterator.next();
                if (!grandExchangeOffer22.ownerUsername.equals(username)) continue;
                grandExchangeOffer = grandExchangeOffer22;
                if (grandExchangeOffer.slotIndex != value2 || !GrandExchangeOffer.isPlayerOfferCurrent(player, grandExchangeOffer22)) continue;
                pendingSellOfferRemovals.add(grandExchangeOffer22);
                break;
            }
        } else {
            iterator = buyOffers.iterator();
            while (iterator.hasNext()) {
                grandExchangeOffer = grandExchangeOffer22 = (GrandExchangeOffer)iterator.next();
                if (!grandExchangeOffer22.ownerUsername.equals(username)) continue;
                grandExchangeOffer = grandExchangeOffer22;
                if (grandExchangeOffer.slotIndex != value2 || !GrandExchangeOffer.isPlayerOfferCurrent(player, grandExchangeOffer22)) continue;
                pendingBuyOfferRemovals.add(grandExchangeOffer22);
                break;
            }
        }
        for (Object pendingBuyOfferObject : pendingBuyOfferRemovals) {
            GrandExchangeOffer pendingBuyOffer = (GrandExchangeOffer)pendingBuyOfferObject;
            buyOffers.remove(pendingBuyOffer);
        }
        for (Object pendingSellOfferObject : pendingSellOfferRemovals) {
            GrandExchangeOffer pendingSellOffer = (GrandExchangeOffer)pendingSellOfferObject;
            sellOffers.remove(pendingSellOffer);
        }
    }

    private void matchOffer(GrandExchangeOffer grandExchangeOffer, boolean enabled2) {
        GrandExchangeOffer grandExchangeOffer2;
        Iterator iterator;
        pendingBuyOfferRemovals.clear();
        pendingSellOfferRemovals.clear();
        int index = 0;
        if (enabled2) {
            iterator = buyOffers.iterator();
            while (iterator.hasNext()) {
                GrandExchangeOffer grandExchangeOffer3;
                grandExchangeOffer2 = grandExchangeOffer3 = (GrandExchangeOffer)iterator.next();
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer3.ownerUsername.equals(grandExchangeOffer2.ownerUsername)) continue;
                GrandExchangeOffer grandExchangeOffer4 = grandExchangeOffer3;
                grandExchangeOffer2 = grandExchangeOffer4;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer4.itemId != grandExchangeOffer2.itemId) continue;
                GrandExchangeOffer grandExchangeOffer5 = grandExchangeOffer3;
                grandExchangeOffer2 = grandExchangeOffer5;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer5.unitPrice < grandExchangeOffer2.unitPrice) continue;
                this.settleMatchedOffers(grandExchangeOffer, grandExchangeOffer3);
                grandExchangeOffer2 = grandExchangeOffer3;
                if (grandExchangeOffer2.remainingQuantity == 0) {
                    pendingBuyOfferRemovals.add(grandExchangeOffer3);
                }
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer2.remainingQuantity != 0) continue;
                pendingSellOfferRemovals.add(grandExchangeOffer);
                break;
            }
        } else {
            iterator = sellOffers.iterator();
            while (iterator.hasNext()) {
                GrandExchangeOffer grandExchangeOffer6;
                grandExchangeOffer2 = grandExchangeOffer6 = (GrandExchangeOffer)iterator.next();
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer6.ownerUsername.equals(grandExchangeOffer2.ownerUsername)) continue;
                GrandExchangeOffer grandExchangeOffer7 = grandExchangeOffer6;
                grandExchangeOffer2 = grandExchangeOffer7;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer7.itemId != grandExchangeOffer2.itemId) continue;
                GrandExchangeOffer grandExchangeOffer8 = grandExchangeOffer6;
                grandExchangeOffer2 = grandExchangeOffer8;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer8.unitPrice > grandExchangeOffer2.unitPrice) continue;
                this.settleMatchedOffers(grandExchangeOffer6, grandExchangeOffer);
                grandExchangeOffer2 = grandExchangeOffer6;
                if (grandExchangeOffer2.remainingQuantity == 0) {
                    pendingSellOfferRemovals.add(grandExchangeOffer6);
                }
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer2.remainingQuantity != 0) continue;
                pendingBuyOfferRemovals.add(grandExchangeOffer);
                break;
            }
        }
        for (Object pendingBuyOfferObject : pendingBuyOfferRemovals) {
            GrandExchangeOffer pendingBuyOffer = (GrandExchangeOffer)pendingBuyOfferObject;
            buyOffers.remove(pendingBuyOffer);
            if (!serverOffers.contains(pendingBuyOffer)) continue;
            serverOffers.remove(pendingBuyOffer);
            ++index;
        }
        for (Object pendingSellOfferObject : pendingSellOfferRemovals) {
            GrandExchangeOffer pendingSellOffer = (GrandExchangeOffer)pendingSellOfferObject;
            sellOffers.remove(pendingSellOffer);
            if (!serverOffers.contains(pendingSellOffer)) continue;
            serverOffers.remove(pendingSellOffer);
            ++index;
        }
        int index2 = 0;
        while (index2 < index) {
            GrandExchangeOffer.createServerOffer();
            ++index2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void settleMatchedOffers(GrandExchangeOffer grandExchangeOffer, GrandExchangeOffer grandExchangeOffer2) {
        boolean enabled = false;
        boolean enabled2 = false;
        GrandExchangeOffer grandExchangeOffer3 = grandExchangeOffer;
        String text = grandExchangeOffer3.ownerUsername;
        grandExchangeOffer3 = grandExchangeOffer2;
        String text2 = grandExchangeOffer3.ownerUsername;
        if (text.equals("[SERVER]")) {
            enabled = true;
        }
        if (text2.equals("[SERVER]")) {
            enabled2 = true;
        }
        Object value = null;
        Object value2 = null;
        Object value3 = null;
        Object value4 = null;
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Object value5 = playerArray[index];
            if (value5 != null) {
                if (((Player)value5).getUsername().equals(text)) {
                    if (!GrandExchangeOffer.isPlayerOfferCurrent((Player)value5, grandExchangeOffer)) {
                        pendingSellOfferRemovals.add(grandExchangeOffer);
                        return;
                    }
                    value = value5;
                    enabled = true;
                } else if (((Player)value5).getUsername().equals(text2)) {
                    if (!GrandExchangeOffer.isPlayerOfferCurrent((Player)value5, grandExchangeOffer2)) {
                        pendingBuyOfferRemovals.add(grandExchangeOffer2);
                        return;
                    }
                    value2 = value5;
                    enabled2 = true;
                }
                if (enabled && enabled2) break;
            }
            ++index;
        }
        if (!enabled || !enabled2) {
            for (Object value6 : CharacterFileManager.liveHiscoreRecords) {
                if (value6 == null) continue;
                if (!enabled) {
                    Object value7 = value6;
                    if (((CharacterFileRecord)value7).username.equals(text)) {
                        if (!GrandExchangeOffer.isRecordOfferCurrent((CharacterFileRecord)value6, grandExchangeOffer)) {
                            pendingSellOfferRemovals.add(grandExchangeOffer);
                            return;
                        }
                        value3 = value6;
                        enabled = true;
                    }
                }
                if (!enabled2) {
                    Object value8 = value6;
                    if (((CharacterFileRecord)value8).username.equals(text2)) {
                        if (!GrandExchangeOffer.isRecordOfferCurrent((CharacterFileRecord)value6, grandExchangeOffer2)) {
                            pendingBuyOfferRemovals.add(grandExchangeOffer2);
                            return;
                        }
                        value4 = value6;
                        enabled2 = true;
                    }
                }
                if (enabled && enabled2) break;
            }
        }
        if (value4 != null && value3 != null) {
            GrandExchangeOffer.settleRecordVsRecordMatch((CharacterFileRecord)value4, grandExchangeOffer2, (CharacterFileRecord)value3, grandExchangeOffer);
            return;
        }
        if (value2 != null && value != null) {
            ((Player)value2).grandExchangeSettlementInProgress = true;
            ((Player)value).grandExchangeSettlementInProgress = true;
            GrandExchangeOffer.settlePlayerVsPlayerMatch((Player)value2, grandExchangeOffer2, (Player)value, grandExchangeOffer);
            ((Player)value2).grandExchangeSettlementInProgress = false;
            ((Player)value).grandExchangeSettlementInProgress = false;
            return;
        }
        if (value4 != null && value != null) {
            ((Player)value).grandExchangeSettlementInProgress = true;
            GrandExchangeOffer.settleRecordVsPlayerMatch((CharacterFileRecord)value4, grandExchangeOffer2, (Player)value, grandExchangeOffer);
            ((Player)value).grandExchangeSettlementInProgress = false;
            return;
        }
        if (value2 != null && value3 != null) {
            ((Player)value2).grandExchangeSettlementInProgress = true;
            GrandExchangeOffer.settlePlayerVsRecordMatch((Player)value2, grandExchangeOffer2, (CharacterFileRecord)value3, grandExchangeOffer);
            ((Player)value2).grandExchangeSettlementInProgress = false;
            return;
        }
        if (value4 != null && text.equals("[SERVER]")) {
            GrandExchangeOffer.settleRecordVsRecordMatch((CharacterFileRecord)value4, grandExchangeOffer2, (CharacterFileRecord)value3, grandExchangeOffer);
            return;
        }
        if (value2 != null && text.equals("[SERVER]")) {
            ((Player)value2).grandExchangeSettlementInProgress = true;
            GrandExchangeOffer.settlePlayerVsPlayerMatch((Player)value2, grandExchangeOffer2, (Player)value, grandExchangeOffer);
            ((Player)value2).grandExchangeSettlementInProgress = false;
            return;
        }
        if (text2.equals("[SERVER]") && value != null) {
            ((Player)value).grandExchangeSettlementInProgress = true;
            GrandExchangeOffer.settleRecordVsPlayerMatch((CharacterFileRecord)value4, grandExchangeOffer2, (Player)value, grandExchangeOffer);
            ((Player)value).grandExchangeSettlementInProgress = false;
            return;
        }
        if (text2.equals("[SERVER]") && value3 != null) {
            GrandExchangeOffer.settlePlayerVsRecordMatch((Player)value2, grandExchangeOffer2, (CharacterFileRecord)value3, grandExchangeOffer);
        }
    }

    private static void settleRecordVsRecordMatch(CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer, CharacterFileRecord characterFileRecord2, GrandExchangeOffer grandExchangeOffer2) {
        int value;
        GrandExchangeOffer grandExchangeOffer3 = grandExchangeOffer2;
        int value2 = grandExchangeOffer3.remainingQuantity;
        grandExchangeOffer3 = grandExchangeOffer;
        if (grandExchangeOffer3.remainingQuantity >= value2) {
            value = value2;
        } else {
            grandExchangeOffer3 = grandExchangeOffer;
            value = grandExchangeOffer3.remainingQuantity;
        }
        value2 = value;
        grandExchangeOffer3 = grandExchangeOffer2;
        int value3 = grandExchangeOffer3.unitPrice;
        grandExchangeOffer3 = grandExchangeOffer2;
        int value4 = grandExchangeOffer3.unitPrice * value2;
        grandExchangeOffer3 = grandExchangeOffer;
        int value5 = grandExchangeOffer3.remainingQuantity - value2;
        grandExchangeOffer3 = grandExchangeOffer;
        grandExchangeOffer.remainingQuantity = value5;
        grandExchangeOffer3 = grandExchangeOffer2;
        value5 = grandExchangeOffer3.remainingQuantity - value2;
        grandExchangeOffer3 = grandExchangeOffer2;
        grandExchangeOffer2.remainingQuantity = value5;
        grandExchangeOffer3 = grandExchangeOffer;
        int value6 = grandExchangeOffer3.slotIndex;
        characterFileRecord.grandExchangeCompletedQuantities[value6] = characterFileRecord.grandExchangeCompletedQuantities[value6] + value2;
        if (characterFileRecord2 != null) {
            grandExchangeOffer3 = grandExchangeOffer2;
            int value7 = grandExchangeOffer3.slotIndex;
            characterFileRecord2.grandExchangeCompletedQuantities[value7] = characterFileRecord2.grandExchangeCompletedQuantities[value7] + value2;
        }
        grandExchangeOffer3 = grandExchangeOffer;
        int value8 = grandExchangeOffer3.slotIndex;
        characterFileRecord.grandExchangeTotalPrices[value8] = characterFileRecord.grandExchangeTotalPrices[value8] + value4;
        if (characterFileRecord2 != null) {
            grandExchangeOffer3 = grandExchangeOffer2;
            int value9 = grandExchangeOffer3.slotIndex;
            characterFileRecord2.grandExchangeTotalPrices[value9] = characterFileRecord2.grandExchangeTotalPrices[value9] + value4;
        }
        grandExchangeOffer3 = grandExchangeOffer;
        if (value3 < grandExchangeOffer3.unitPrice) {
            grandExchangeOffer3 = grandExchangeOffer;
            value3 = grandExchangeOffer3.unitPrice - value3;
            grandExchangeOffer3 = grandExchangeOffer;
            int value10 = grandExchangeOffer3.slotIndex;
            characterFileRecord.grandExchangeSecondaryCollectAmounts[value10] = characterFileRecord.grandExchangeSecondaryCollectAmounts[value10] + (value3 *= value2);
        }
        grandExchangeOffer3 = grandExchangeOffer;
        int value11 = grandExchangeOffer3.slotIndex;
        characterFileRecord.grandExchangePrimaryCollectAmounts[value11] = characterFileRecord.grandExchangePrimaryCollectAmounts[value11] + value2;
        if (characterFileRecord2 != null) {
            grandExchangeOffer3 = grandExchangeOffer2;
            int value12 = grandExchangeOffer3.slotIndex;
            characterFileRecord2.grandExchangePrimaryCollectAmounts[value12] = characterFileRecord2.grandExchangePrimaryCollectAmounts[value12] + value4;
        }
        grandExchangeOffer3 = grandExchangeOffer;
        if (grandExchangeOffer3.remainingQuantity == 0) {
            grandExchangeOffer3 = grandExchangeOffer;
            characterFileRecord.grandExchangeFinishMessagePending[grandExchangeOffer3.slotIndex] = true;
        }
        grandExchangeOffer3 = grandExchangeOffer2;
        if (grandExchangeOffer3.remainingQuantity == 0) {
            if (characterFileRecord2 != null) {
                grandExchangeOffer3 = grandExchangeOffer2;
                characterFileRecord2.grandExchangeFinishMessagePending[grandExchangeOffer3.slotIndex] = true;
            }
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        CharacterFileManager.saveCharacterFileRecord(characterFileRecord);
        if (characterFileRecord2 != null) {
            CharacterFileManager.saveCharacterFileRecord(characterFileRecord2);
        }
    }

    private static void settlePlayerVsPlayerMatch(Player player, GrandExchangeOffer grandExchangeOffer, Player player2, GrandExchangeOffer grandExchangeOffer2) {
        int value;
        Object value2 = grandExchangeOffer2;
        int value3 = ((GrandExchangeOffer)value2).remainingQuantity;
        value2 = grandExchangeOffer;
        if (((GrandExchangeOffer)value2).remainingQuantity >= value3) {
            value = value3;
        } else {
            value2 = grandExchangeOffer;
            value = ((GrandExchangeOffer)value2).remainingQuantity;
        }
        value3 = value;
        value2 = grandExchangeOffer2;
        int value4 = ((GrandExchangeOffer)value2).unitPrice;
        value2 = grandExchangeOffer2;
        int value5 = ((GrandExchangeOffer)value2).unitPrice * value3;
        value2 = grandExchangeOffer;
        int value6 = ((GrandExchangeOffer)value2).remainingQuantity - value3;
        value2 = grandExchangeOffer;
        grandExchangeOffer.remainingQuantity = value6;
        value2 = grandExchangeOffer2;
        value6 = ((GrandExchangeOffer)value2).remainingQuantity - value3;
        value2 = grandExchangeOffer2;
        grandExchangeOffer2.remainingQuantity = value6;
        value2 = grandExchangeOffer;
        int value7 = ((GrandExchangeOffer)value2).slotIndex;
        player.grandExchangeCompletedQuantities[value7] = player.grandExchangeCompletedQuantities[value7] + value3;
        if (player2 != null) {
            value2 = grandExchangeOffer2;
            int value8 = ((GrandExchangeOffer)value2).slotIndex;
            player2.grandExchangeCompletedQuantities[value8] = player2.grandExchangeCompletedQuantities[value8] + value3;
        }
        value2 = grandExchangeOffer;
        int value9 = ((GrandExchangeOffer)value2).slotIndex;
        player.grandExchangeTotalPrices[value9] = player.grandExchangeTotalPrices[value9] + value5;
        if (player2 != null) {
            value2 = grandExchangeOffer2;
            int value10 = ((GrandExchangeOffer)value2).slotIndex;
            player2.grandExchangeTotalPrices[value10] = player2.grandExchangeTotalPrices[value10] + value5;
        }
        value2 = grandExchangeOffer;
        if (value4 < ((GrandExchangeOffer)value2).unitPrice) {
            value2 = grandExchangeOffer;
            value4 = ((GrandExchangeOffer)value2).unitPrice - value4;
            value2 = grandExchangeOffer;
            int value11 = ((GrandExchangeOffer)value2).slotIndex;
            player.grandExchangeSecondaryCollectAmounts[value11] = player.grandExchangeSecondaryCollectAmounts[value11] + (value4 *= value3);
        }
        value2 = grandExchangeOffer;
        int value12 = ((GrandExchangeOffer)value2).slotIndex;
        player.grandExchangePrimaryCollectAmounts[value12] = player.grandExchangePrimaryCollectAmounts[value12] + value3;
        if (player2 != null) {
            value2 = grandExchangeOffer2;
            int value13 = ((GrandExchangeOffer)value2).slotIndex;
            player2.grandExchangePrimaryCollectAmounts[value13] = player2.grandExchangePrimaryCollectAmounts[value13] + value5;
        }
        value2 = grandExchangeOffer;
        if (((GrandExchangeOffer)value2).remainingQuantity == 0 && player != null) {
            value2 = grandExchangeOffer;
            GrandExchangeManager.sendOfferCompletionMessage(player, ((GrandExchangeOffer)value2).slotIndex);
        }
        value2 = grandExchangeOffer2;
        if (((GrandExchangeOffer)value2).remainingQuantity == 0) {
            if (player2 != null) {
                value2 = grandExchangeOffer2;
                GrandExchangeManager.sendOfferCompletionMessage(player2, ((GrandExchangeOffer)value2).slotIndex);
            }
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        if (player.getOpenInterfaceId() == 19018) {
            GrandExchangeManager.refreshOfferSlots(player);
        }
        if (player.getOpenInterfaceId() == 18984) {
            value2 = grandExchangeOffer;
            if (player.selectedGrandExchangeSlot == ((GrandExchangeOffer)value2).slotIndex) {
                GrandExchangeManager.refreshSelectedOfferDetails(player);
            }
        }
        if (player2 != null) {
            if (player2.getOpenInterfaceId() == 19018) {
                GrandExchangeManager.refreshOfferSlots(player2);
            }
            if (player2.getOpenInterfaceId() == 18984) {
                value2 = grandExchangeOffer2;
                if (player2.selectedGrandExchangeSlot == ((GrandExchangeOffer)value2).slotIndex) {
                    GrandExchangeManager.refreshSelectedOfferDetails(player2);
                }
            }
        }
        value2 = player;
        CharacterFileManager.savePlayer((Player)value2);
        if (player2 != null) {
            value2 = player2;
            CharacterFileManager.savePlayer((Player)value2);
        }
    }

    private static void settlePlayerVsRecordMatch(Player player, GrandExchangeOffer grandExchangeOffer, CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer2) {
        int value;
        Object value2 = grandExchangeOffer2;
        int value3 = ((GrandExchangeOffer)value2).remainingQuantity;
        value2 = grandExchangeOffer;
        if (((GrandExchangeOffer)value2).remainingQuantity >= value3) {
            value = value3;
        } else {
            value2 = grandExchangeOffer;
            value = ((GrandExchangeOffer)value2).remainingQuantity;
        }
        value3 = value;
        value2 = grandExchangeOffer2;
        int value4 = ((GrandExchangeOffer)value2).unitPrice;
        value2 = grandExchangeOffer2;
        int value5 = ((GrandExchangeOffer)value2).unitPrice * value3;
        value2 = grandExchangeOffer;
        int value6 = ((GrandExchangeOffer)value2).remainingQuantity - value3;
        value2 = grandExchangeOffer;
        grandExchangeOffer.remainingQuantity = value6;
        value2 = grandExchangeOffer2;
        value6 = ((GrandExchangeOffer)value2).remainingQuantity - value3;
        value2 = grandExchangeOffer2;
        grandExchangeOffer2.remainingQuantity = value6;
        if (player != null) {
            value2 = grandExchangeOffer;
            int value7 = ((GrandExchangeOffer)value2).slotIndex;
            player.grandExchangeCompletedQuantities[value7] = player.grandExchangeCompletedQuantities[value7] + value3;
        }
        value2 = grandExchangeOffer2;
        int value8 = ((GrandExchangeOffer)value2).slotIndex;
        characterFileRecord.grandExchangeCompletedQuantities[value8] = characterFileRecord.grandExchangeCompletedQuantities[value8] + value3;
        if (player != null) {
            value2 = grandExchangeOffer;
            int value9 = ((GrandExchangeOffer)value2).slotIndex;
            player.grandExchangeTotalPrices[value9] = player.grandExchangeTotalPrices[value9] + value5;
        }
        value2 = grandExchangeOffer2;
        int value10 = ((GrandExchangeOffer)value2).slotIndex;
        characterFileRecord.grandExchangeTotalPrices[value10] = characterFileRecord.grandExchangeTotalPrices[value10] + value5;
        value2 = grandExchangeOffer;
        if (value4 < ((GrandExchangeOffer)value2).unitPrice) {
            value2 = grandExchangeOffer;
            value4 = ((GrandExchangeOffer)value2).unitPrice - value4;
            value4 *= value3;
            if (player != null) {
                value2 = grandExchangeOffer;
                int value11 = ((GrandExchangeOffer)value2).slotIndex;
                player.grandExchangeSecondaryCollectAmounts[value11] = player.grandExchangeSecondaryCollectAmounts[value11] + value4;
            }
        }
        if (player != null) {
            value2 = grandExchangeOffer;
            int value12 = ((GrandExchangeOffer)value2).slotIndex;
            player.grandExchangePrimaryCollectAmounts[value12] = player.grandExchangePrimaryCollectAmounts[value12] + value3;
        }
        value2 = grandExchangeOffer2;
        int value13 = ((GrandExchangeOffer)value2).slotIndex;
        characterFileRecord.grandExchangePrimaryCollectAmounts[value13] = characterFileRecord.grandExchangePrimaryCollectAmounts[value13] + value5;
        value2 = grandExchangeOffer;
        if (((GrandExchangeOffer)value2).remainingQuantity == 0 && player != null) {
            value2 = grandExchangeOffer;
            GrandExchangeManager.sendOfferCompletionMessage(player, ((GrandExchangeOffer)value2).slotIndex);
        }
        value2 = grandExchangeOffer2;
        if (((GrandExchangeOffer)value2).remainingQuantity == 0) {
            value2 = grandExchangeOffer2;
            characterFileRecord.grandExchangeFinishMessagePending[((GrandExchangeOffer)value2).slotIndex] = true;
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        if (player != null) {
            if (player.getOpenInterfaceId() == 19018) {
                GrandExchangeManager.refreshOfferSlots(player);
            }
            if (player.getOpenInterfaceId() == 18984) {
                value2 = grandExchangeOffer;
                if (player.selectedGrandExchangeSlot == ((GrandExchangeOffer)value2).slotIndex) {
                    GrandExchangeManager.refreshSelectedOfferDetails(player);
                }
            }
        }
        CharacterFileManager.saveCharacterFileRecord(characterFileRecord);
        if (player != null) {
            value2 = player;
            CharacterFileManager.savePlayer((Player)value2);
        }
    }

    private static void settleRecordVsPlayerMatch(CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer, Player player, GrandExchangeOffer grandExchangeOffer2) {
        int value;
        Object value2 = grandExchangeOffer2;
        int value3 = ((GrandExchangeOffer)value2).remainingQuantity;
        value2 = grandExchangeOffer;
        if (((GrandExchangeOffer)value2).remainingQuantity >= value3) {
            value = value3;
        } else {
            value2 = grandExchangeOffer;
            value = ((GrandExchangeOffer)value2).remainingQuantity;
        }
        value3 = value;
        value2 = grandExchangeOffer2;
        int value4 = ((GrandExchangeOffer)value2).unitPrice;
        value2 = grandExchangeOffer2;
        int value5 = ((GrandExchangeOffer)value2).unitPrice * value3;
        value2 = grandExchangeOffer;
        int value6 = ((GrandExchangeOffer)value2).remainingQuantity - value3;
        value2 = grandExchangeOffer;
        grandExchangeOffer.remainingQuantity = value6;
        value2 = grandExchangeOffer2;
        value6 = ((GrandExchangeOffer)value2).remainingQuantity - value3;
        value2 = grandExchangeOffer2;
        grandExchangeOffer2.remainingQuantity = value6;
        if (characterFileRecord != null) {
            value2 = grandExchangeOffer;
            int value7 = ((GrandExchangeOffer)value2).slotIndex;
            characterFileRecord.grandExchangeCompletedQuantities[value7] = characterFileRecord.grandExchangeCompletedQuantities[value7] + value3;
        }
        value2 = grandExchangeOffer2;
        int value8 = ((GrandExchangeOffer)value2).slotIndex;
        player.grandExchangeCompletedQuantities[value8] = player.grandExchangeCompletedQuantities[value8] + value3;
        if (characterFileRecord != null) {
            value2 = grandExchangeOffer;
            int value9 = ((GrandExchangeOffer)value2).slotIndex;
            characterFileRecord.grandExchangeTotalPrices[value9] = characterFileRecord.grandExchangeTotalPrices[value9] + value5;
        }
        value2 = grandExchangeOffer2;
        int value10 = ((GrandExchangeOffer)value2).slotIndex;
        player.grandExchangeTotalPrices[value10] = player.grandExchangeTotalPrices[value10] + value5;
        value2 = grandExchangeOffer;
        if (value4 < ((GrandExchangeOffer)value2).unitPrice) {
            value2 = grandExchangeOffer;
            value4 = ((GrandExchangeOffer)value2).unitPrice - value4;
            value4 *= value3;
            if (characterFileRecord != null) {
                value2 = grandExchangeOffer;
                int value11 = ((GrandExchangeOffer)value2).slotIndex;
                characterFileRecord.grandExchangeSecondaryCollectAmounts[value11] = characterFileRecord.grandExchangeSecondaryCollectAmounts[value11] + value4;
            }
        }
        if (characterFileRecord != null) {
            value2 = grandExchangeOffer;
            int value12 = ((GrandExchangeOffer)value2).slotIndex;
            characterFileRecord.grandExchangePrimaryCollectAmounts[value12] = characterFileRecord.grandExchangePrimaryCollectAmounts[value12] + value3;
        }
        value2 = grandExchangeOffer2;
        int value13 = ((GrandExchangeOffer)value2).slotIndex;
        player.grandExchangePrimaryCollectAmounts[value13] = player.grandExchangePrimaryCollectAmounts[value13] + value5;
        value2 = grandExchangeOffer2;
        if (((GrandExchangeOffer)value2).remainingQuantity == 0) {
            value2 = grandExchangeOffer2;
            GrandExchangeManager.sendOfferCompletionMessage(player, ((GrandExchangeOffer)value2).slotIndex);
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        value2 = grandExchangeOffer;
        if (((GrandExchangeOffer)value2).remainingQuantity == 0 && characterFileRecord != null) {
            value2 = grandExchangeOffer;
            characterFileRecord.grandExchangeFinishMessagePending[((GrandExchangeOffer)value2).slotIndex] = true;
        }
        if (player.getOpenInterfaceId() == 19018) {
            GrandExchangeManager.refreshOfferSlots(player);
        }
        if (player.getOpenInterfaceId() == 18984) {
            value2 = grandExchangeOffer2;
            if (player.selectedGrandExchangeSlot == ((GrandExchangeOffer)value2).slotIndex) {
                GrandExchangeManager.refreshSelectedOfferDetails(player);
            }
        }
        if (characterFileRecord != null) {
            CharacterFileManager.saveCharacterFileRecord(characterFileRecord);
        }
        value2 = player;
        CharacterFileManager.savePlayer((Player)value2);
    }

    private static boolean isRecordOfferCurrent(CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer) {
        if (characterFileRecord.grandExchangeCancelledFlags[grandExchangeOffer.slotIndex]) {
            return false;
        }
        GrandExchangeOffer grandExchangeOffer2 = grandExchangeOffer;
        if (characterFileRecord.grandExchangeSellOfferFlags[grandExchangeOffer.slotIndex] == grandExchangeOffer2.sellOffer) {
            grandExchangeOffer2 = grandExchangeOffer;
            if (characterFileRecord.grandExchangeItemIds[grandExchangeOffer.slotIndex] == grandExchangeOffer2.itemId) {
                grandExchangeOffer2 = grandExchangeOffer;
                if (characterFileRecord.grandExchangeQuantities[grandExchangeOffer.slotIndex] == grandExchangeOffer2.quantity) {
                    grandExchangeOffer2 = grandExchangeOffer;
                    if (characterFileRecord.grandExchangeUnitPrices[grandExchangeOffer.slotIndex] == grandExchangeOffer2.unitPrice) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isPlayerOfferCurrent(Player player, GrandExchangeOffer grandExchangeOffer) {
        if (player.grandExchangeCancelledFlags[grandExchangeOffer.slotIndex]) {
            return false;
        }
        GrandExchangeOffer grandExchangeOffer2 = grandExchangeOffer;
        if (player.grandExchangeSellOfferFlags[grandExchangeOffer.slotIndex] == grandExchangeOffer2.sellOffer) {
            grandExchangeOffer2 = grandExchangeOffer;
            if (player.grandExchangeItemIds[grandExchangeOffer.slotIndex] == grandExchangeOffer2.itemId) {
                grandExchangeOffer2 = grandExchangeOffer;
                if (player.grandExchangeQuantities[grandExchangeOffer.slotIndex] == grandExchangeOffer2.quantity) {
                    grandExchangeOffer2 = grandExchangeOffer;
                    if (player.grandExchangeUnitPrices[grandExchangeOffer.slotIndex] == grandExchangeOffer2.unitPrice) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void recordPriceSample(int value4, int value22, int value32) {
        ItemDefinition itemDefinition = ItemDefinition.forId(value4);
        boolean enabled = itemDefinition.isNote();
        int unnotedId = enabled ? itemDefinition.getUnnotedId() : itemDefinition.getId();
        new GrandExchangePriceSample(unnotedId, value22, value32);
    }
}

