package com.rs2.model.shop;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemStack;
import com.rs2.model.shop.ShopDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.task.TickTask;

public final class ShopRestockTask
extends TickTask {
    private final ShopDefinition shopDefinition;
    private final int itemId;
    private final int slotIndex;

    public ShopRestockTask(int value4, ShopDefinition shopDefinition, int itemId, int slotIndex) {
        super(value4);
        this.shopDefinition = shopDefinition;
        this.itemId = itemId;
        this.slotIndex = slotIndex;
    }

    @Override
    public final void execute() {
        if (this.shopDefinition.getOriginalStock().containsItem(this.itemId)) {
            int originalStock = this.shopDefinition.getOriginalStock().findFlatItem(this.itemId).getAmount();
            int stock = this.shopDefinition.getStock().findFlatItem(this.itemId).getAmount();
            if (stock < originalStock) {
                ItemStack itemStack = new ItemStack(this.itemId);
                ItemContainer itemContainer = this.shopDefinition.getStock();
                itemContainer.add(itemStack, -1);
            } else if (stock > originalStock) {
                this.shopDefinition.getStock().removeKeepingPlaceholder(new ItemStack(this.itemId));
            }
        } else {
            int value = ShopManager.indexOfStockItem(this.shopDefinition, this.itemId);
            if (this.shopDefinition.getStock().containsItem(this.itemId) && value == this.slotIndex) {
                this.shopDefinition.getStock().remove(new ItemStack(this.itemId));
            }
        }
        ShopManager.refreshShopForPlayers(this.shopDefinition.getShopId());
        if (!ShopManager.needsRestock(this.shopDefinition, this.itemId, this.slotIndex)) {
            this.stop();
        }
    }
}

