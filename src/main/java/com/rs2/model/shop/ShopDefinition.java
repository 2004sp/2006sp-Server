package com.rs2.model.shop;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.shop.ShopCurrency;
import com.rs2.model.task.TickTask;

public final class ShopDefinition {
    private int shopId;
    private String name;
    private boolean generalStore;
    private boolean membersOnly = false;
    private ShopCurrency currency;
    private int currencyItemId;
    private ItemContainer originalStock;
    private ItemContainer stock;
    private int buyPricePercent;
    private int sellPricePercent;
    private int priceChangeRateTenths;
    private int[] restockDelayTicks;
    private TickTask[] restockTasks;

    public final void setBuyPricePercent(int buyPricePercent) {
        this.buyPricePercent = buyPricePercent;
    }

    public final int getBuyPricePercent() {
        return this.buyPricePercent;
    }

    public final void setSellPricePercent(int sellPricePercent) {
        this.sellPricePercent = sellPricePercent;
    }

    public final int getSellPricePercent() {
        return this.sellPricePercent;
    }

    public final void setPriceChangeRateTenths(int priceChangeRateTenths) {
        this.priceChangeRateTenths = priceChangeRateTenths;
    }

    public final double getPriceChangeRate() {
        double value = this.priceChangeRateTenths;
        return value /= 10.0;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean isMembersOnly() {
        return this.membersOnly;
    }

    public final void setMembersOnly(boolean membersOnly) {
        this.membersOnly = membersOnly;
    }

    public final int getShopId() {
        return this.shopId;
    }

    public final void setGeneralStore(boolean generalStore) {
        this.generalStore = generalStore;
    }

    public final boolean isGeneralStore() {
        return this.generalStore;
    }

    public final void setCurrencyItemId(int itemId) {
        this.currencyItemId = itemId;
    }

    public final int getCurrencyItemId() {
        return this.currencyItemId;
    }

    public final ShopCurrency getCurrency() {
        return this.currency;
    }

    public final void setCurrency(ShopCurrency shopCurrency) {
        this.currency = shopCurrency;
    }

    public final void setOriginalStock(ItemContainer itemContainer) {
        this.originalStock = itemContainer;
    }

    public final ItemContainer getOriginalStock() {
        return this.originalStock;
    }

    public final void setStock(ItemContainer itemContainer) {
        this.stock = itemContainer;
    }

    public final ItemContainer getStock() {
        return this.stock;
    }

    static void setRestockDelayTicks(ShopDefinition shopDefinition, int[] delayTicks) {
        shopDefinition.restockDelayTicks = delayTicks;
    }

    static void setRestockTasks(ShopDefinition shopDefinition, TickTask[] tickTaskArray) {
        shopDefinition.restockTasks = tickTaskArray;
    }

    static int[] getRestockDelayTicks(ShopDefinition shopDefinition) {
        return shopDefinition.restockDelayTicks;
    }

    static void setShopId(ShopDefinition shopDefinition, int value2) {
        shopDefinition.shopId = value2;
    }

    static TickTask[] getRestockTasks(ShopDefinition shopDefinition) {
        return shopDefinition.restockTasks;
    }
}

