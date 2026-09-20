package com.rs2.model.item;

import com.rs2.model.item.ItemContainerTab;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ItemContainer {
    private int capacity;
    private ItemStack[] items;
    private List updateListeners = new LinkedList();
    private ArrayList tabs = new ArrayList();
    private int tabLimit;
    private ItemContainerType containerType;
    private boolean updatesEnabled = true;

    public ItemContainer(ItemContainerType itemContainerType, int capacity) {
        this.containerType = itemContainerType;
        this.capacity = capacity;
        this.items = new ItemStack[capacity];
    }

    public ItemContainer(ItemContainerType itemContainerType, int capacity, int tabLimit) {
        this.containerType = itemContainerType;
        this.capacity = capacity;
        this.tabLimit = tabLimit;
        this.tabs.add(new ItemContainerTab(true));
    }

    public final int getTabCount() {
        return this.tabs.size();
    }

    public final int getTabLimit() {
        return this.tabLimit;
    }

    public final void compactTab(int value2) {
        if (value2 > this.tabLimit - 1) {
            value2 = this.tabLimit - 1;
        }
        if (value2 > this.tabs.size() - 1) {
            return;
        }
        ItemContainerTab itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
        Iterator iterator = itemContainerTab.items.iterator();
        while (iterator.hasNext()) {
            ItemStack itemStack = (ItemStack)iterator.next();
            if (itemStack == null) {
                iterator.remove();
                continue;
            }
            if (itemStack.getId() != -1) continue;
            iterator.remove();
        }
    }

    public final void removeEmptyTabs() {
        try {
            Iterator iterator = this.tabs.iterator();
            while (iterator.hasNext()) {
                boolean enabled = true;
                Object itemContainerTab = (ItemContainerTab)iterator.next();
                if (itemContainerTab == null || ((ItemContainerTab)itemContainerTab).persistent) continue;
                Object value = itemContainerTab;
                if (((ItemContainerTab)value).items.size() == 0) {
                    iterator.remove();
                    continue;
                }
                value = itemContainerTab;
                for (Object itemObject : ((ItemContainerTab)value).items) {
                    if (itemObject == null || ((ItemStack)itemObject).getId() == -1) continue;
                    enabled = false;
                    break;
                }
                if (!enabled) continue;
                iterator.remove();
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final int getFirstFreeSlot() {
        int index = 0;
        while (index < this.items.length) {
            if (this.items[index] == null) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private int getFirstFreeTabSlot(int slot) {
        if (slot > this.tabLimit - 1) {
            slot = this.tabLimit - 1;
        }
        if (slot > this.tabs.size() - 1) {
            return 0;
        }
        ItemContainerTab itemContainerTab = (ItemContainerTab)this.tabs.get(slot);
        Object value = itemContainerTab;
        value = itemContainerTab;
        value = itemContainerTab.items;
        int index = 0;
        while (index < ((ArrayList)value).size()) {
            if (((ArrayList)value).get(index) == null) {
                return index;
            }
            if (((ItemStack)((ArrayList)value).get(index)).getId() == -1) {
                return index;
            }
            ++index;
        }
        return ((ArrayList)value).size();
    }

    public final ItemStack[] getItems() {
        if (this.tabLimit == 0) {
            return this.items;
        }
        ArrayList arrayList = new ArrayList();
        int index = 0;
        while (index < this.tabs.size()) {
            ItemContainerTab itemContainerTab = (ItemContainerTab)this.tabs.get(index);
            arrayList.addAll(itemContainerTab.items);
            ++index;
        }
        return (ItemStack[])arrayList.toArray(new ItemStack[0]);
    }

    public final boolean addToTab(ItemStack itemStack, int value2) {
        if (value2 > this.tabLimit - 1) {
            value2 = this.tabLimit - 1;
        }
        if (value2 > this.tabs.size() - 1) {
            this.tabs.add(new ItemContainerTab('\u0000'));
        }
        return this.addToTabInternal(itemStack, -1, value2);
    }

    public final boolean add(ItemStack itemStack, int value3) {
        int value2;
        if (itemStack == null) {
            return false;
        }
        int firstFreeSlot = value2 = value3 >= 0 ? value3 : this.getFirstFreeSlot();
        if ((itemStack.getDefinition().isStackable() || this.containerType.equals((Object)ItemContainerType.b)) && !this.containerType.equals((Object)ItemContainerType.c) && this.getItemAmount(itemStack.getId()) > 0 && itemStack.getMetadata() == -1) {
            value2 = this.indexOfItem(itemStack.getId());
        }
        if (value2 == -1) {
            return false;
        }
        if (this.getItemAt(value2) != null) {
            value2 = this.getFirstFreeSlot();
        }
        if ((itemStack.getDefinition().isStackable() || this.containerType.equals((Object)ItemContainerType.b)) && !this.containerType.equals((Object)ItemContainerType.c)) {
            int index = 0;
            while (index < this.items.length) {
                if (this.items[index] != null && this.items[index].getId() == itemStack.getId() && itemStack.getMetadata() == -1) {
                    int amount = itemStack.getAmount() + this.items[index].getAmount();
                    if (amount > Integer.MAX_VALUE || amount <= 0) {
                        return false;
                    }
                    this.setItem(index, new ItemStack(this.items[index].getId(), this.items[index].getAmount() + itemStack.getAmount(), this.items[index].getMetadata()));
                    return true;
                }
                ++index;
            }
            if (value2 == -1) {
                return false;
            }
            this.setItem(value3 >= 0 ? value2 : this.getFirstFreeSlot(), itemStack);
            return true;
        }
        int freeSlots = this.getFreeSlots();
        if (freeSlots >= itemStack.getAmount()) {
            boolean enabled = this.updatesEnabled;
            this.updatesEnabled = false;
            try {
                freeSlots = 0;
                while (freeSlots < itemStack.getAmount()) {
                    this.setItem(value3 >= 0 ? value2 : this.getFirstFreeSlot(), new ItemStack(itemStack.getId(), 1, itemStack.getMetadata()));
                    ++freeSlots;
                }
                if (enabled) {
                    this.notifyFullRefresh();
                }
                return true;
            }
            finally {
                this.updatesEnabled = enabled;
            }
        }
        return false;
    }

    private boolean addToTabInternal(ItemStack itemStack, int value3, int value22) {
        if (itemStack == null) {
            return false;
        }
        value3 = this.getFirstFreeTabSlot(value22);
        if ((itemStack.getDefinition().isStackable() || this.containerType.equals((Object)ItemContainerType.b)) && !this.containerType.equals((Object)ItemContainerType.c) && this.getItemAmount(itemStack.getId()) > 0 && itemStack.getMetadata() == -1) {
            value3 = this.indexOfItemInTab(itemStack.getId(), value22);
        }
        if (value3 == -1) {
            return false;
        }
        if (this.getItemAtTabSlot(value3, value22) != null) {
            value3 = this.getFirstFreeTabSlot(value22);
        }
        if ((itemStack.getDefinition().isStackable() || this.containerType.equals((Object)ItemContainerType.b)) && !this.containerType.equals((Object)ItemContainerType.c)) {
            Object itemContainerTab = (ItemContainerTab)this.tabs.get(value22);
            itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
            int index = 0;
            while (index < ((ArrayList)itemContainerTab).size()) {
                if (((ArrayList)itemContainerTab).get(index) != null && ((ItemStack)((ArrayList)itemContainerTab).get(index)).getId() == itemStack.getId() && itemStack.getMetadata() == -1) {
                    value3 = itemStack.getAmount() + ((ItemStack)((ArrayList)itemContainerTab).get(index)).getAmount();
                    if (value3 >= Integer.MAX_VALUE || value3 <= 0) {
                        return false;
                    }
                    this.setTabItem(index, new ItemStack(((ItemStack)((ArrayList)itemContainerTab).get(index)).getId(), ((ItemStack)((ArrayList)itemContainerTab).get(index)).getAmount() + itemStack.getAmount(), ((ItemStack)((ArrayList)itemContainerTab).get(index)).getMetadata()), value22);
                    return true;
                }
                ++index;
            }
            if (value3 == -1) {
                return false;
            }
            this.setTabItem(this.getFirstFreeTabSlot(value22), itemStack, value22);
            return true;
        }
        int freeSlots = this.getFreeSlots();
        if (freeSlots >= itemStack.getAmount()) {
            boolean enabled = this.updatesEnabled;
            this.updatesEnabled = false;
            try {
                value3 = 0;
                while (value3 < itemStack.getAmount()) {
                    this.setTabItem(this.getFirstFreeSlot(), new ItemStack(itemStack.getId(), 1, itemStack.getMetadata()), value22);
                    ++value3;
                }
                if (enabled) {
                    this.notifyFullRefresh();
                }
                return true;
            }
            finally {
                this.updatesEnabled = enabled;
            }
        }
        return false;
    }

    public final int getFreeSlots() {
        return this.capacity - this.getUsedSlots();
    }

    public final ItemStack getItemAt(int itemId) {
        if (itemId == -1) {
            return null;
        }
        return this.items[itemId];
    }

    public final ItemStack getItemAtTabSlot(int itemId, int value2) {
        if (value2 > this.tabLimit - 1) {
            value2 = this.tabLimit - 1;
        }
        if (value2 > this.tabs.size() - 1) {
            return null;
        }
        ItemContainerTab itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
        if (itemId > itemContainerTab.items.size() - 1) {
            return null;
        }
        itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
        return (ItemStack)itemContainerTab.items.get(itemId);
    }

    public final ItemStack findFlatItem(int itemId) {
        ItemStack[] itemStackArray = this.items;
        int length = this.items.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null && itemStack.getId() == itemId) {
                return itemStack;
            }
            ++index;
        }
        return null;
    }

    public final int indexOfItem(int itemId) {
        int index = 0;
        while (index < this.items.length) {
            if (this.items[index] != null && this.items[index].getId() == itemId) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public final int indexOfItemInTab(int itemId, int value2) {
        if (value2 > this.tabLimit - 1) {
            value2 = this.tabLimit - 1;
        }
        if (value2 > this.tabs.size() - 1) {
            return -1;
        }
        Object itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
        itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
        int index = 0;
        while (index < ((ArrayList)itemContainerTab).size()) {
            if (((ArrayList)itemContainerTab).get(index) != null && ((ItemStack)((ArrayList)itemContainerTab).get(index)).getId() == itemId) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public final int indexOfPlaceholderInTab(int index, int value2, int value32) {
        if (value32 > this.tabLimit - 1) {
            value32 = this.tabLimit - 1;
        }
        if (value32 > this.tabs.size() - 1) {
            return -1;
        }
        Object itemContainerTab = (ItemContainerTab)this.tabs.get(value32);
        itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
        value32 = 0;
        while (value32 < ((ArrayList)itemContainerTab).size()) {
            if (((ArrayList)itemContainerTab).get(value32) != null && ((ItemStack)((ArrayList)itemContainerTab).get(value32)).getId() == index && ((ItemStack)((ArrayList)itemContainerTab).get(value32)).getAmount() == 0) {
                return value32;
            }
            ++value32;
        }
        return -1;
    }

    public final void setItem(int itemId, ItemStack itemStack) {
        this.items[itemId] = itemStack;
        if (this.updatesEnabled) {
            this.notifySlotUpdated(itemId);
        }
    }

    public final void setTabItem(int itemId, ItemStack itemStack, int value2) {
        if (value2 > this.tabLimit - 1) {
            value2 = this.tabLimit - 1;
        }
        if (value2 > this.tabs.size() - 1) {
            this.tabs.add(new ItemContainerTab('\u0000'));
        }
        ItemContainerTab itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
        if (itemId > itemContainerTab.items.size() - 1) {
            itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
            itemContainerTab.items.add(itemStack);
        } else {
            itemContainerTab = (ItemContainerTab)this.tabs.get(value2);
            itemContainerTab.items.set(itemId, itemStack);
        }
        if (this.updatesEnabled) {
            this.notifySlotUpdated(itemId);
        }
    }

    public final void replaceItemId(int itemId, int value2) {
        ItemStack itemStack = new ItemStack(itemId);
        ItemContainer itemContainer = this;
        itemContainer.removeInternal(itemStack, -1, false);
        itemStack = new ItemStack(value2);
        itemContainer = this;
        itemContainer.add(itemStack, -1);
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final int getUsedSlots() {
        int index = 0;
        if (this.tabLimit == 0) {
            ItemStack[] itemStackArray = this.items;
            int length = this.items.length;
            int index2 = 0;
            while (index2 < length) {
                ItemStack itemStack = itemStackArray[index2];
                if (itemStack != null) {
                    ++index;
                }
                ++index2;
            }
        } else {
            for (Object itemContainerTabObject : this.tabs) {
                ItemContainerTab itemContainerTab = (ItemContainerTab)itemContainerTabObject;
                if (itemContainerTab == null) continue;
                for (Object itemStackObject : itemContainerTab.items) {
                    ItemStack itemStack = (ItemStack)itemStackObject;
                    if (itemStack == null || itemStack.getId() == -1) continue;
                    ++index;
                }
            }
            return index;
        }
        return index;
    }

    public void clear() {
        if (this.tabLimit == 0) {
            this.items = new ItemStack[this.items.length];
            if (this.updatesEnabled) {
                this.notifyFullRefresh();
                return;
            }
        } else {
            for (Object itemContainerTabObject : this.tabs) {
                ItemContainerTab itemContainerTab = (ItemContainerTab)itemContainerTabObject;
                if (itemContainerTab == null) continue;
                itemContainerTab.items.clear();
            }
        }
    }

    public final ItemStack[] getRawItems() {
        return this.items;
    }

    public final ItemStack[] getTabItems(int itemId) {
        ItemStack[] itemStackArray = new ItemStack[288];
        if (itemId > this.tabLimit - 1) {
            itemId = this.tabLimit - 1;
        }
        if (itemId > this.tabs.size() - 1) {
            return itemStackArray;
        }
        Object itemContainerTab = (ItemContainerTab)this.tabs.get(itemId);
        itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
        int index = 0;
        while (index < ((ArrayList)itemContainerTab).size()) {
            itemStackArray[index] = (ItemStack)((ArrayList)itemContainerTab).get(index);
            ++index;
        }
        return itemStackArray;
    }

    public final boolean hasItemAtSlot(int itemId) {
        return this.items[itemId] != null;
    }

    public final int remove(ItemStack itemStack) {
        return this.removeInternal(itemStack, -1, false);
    }

    public final int removeKeepingPlaceholder(ItemStack itemStack) {
        return this.removeInternal(itemStack, -1, true);
    }

    public final int removeFromSlot(ItemStack itemStack, int slot) {
        return this.removeInternal(itemStack, slot, false);
    }

    public final int removeFromTab(ItemStack itemStack, int value4, int value22) {
        int value3 = value22;
        boolean enabled = false;
        value22 = value4;
        ItemStack itemStack2 = itemStack;
        ItemContainer itemContainer = this;
        if (itemStack2 == null) {
            return -1;
        }
        int index = 0;
        if ((itemStack2.getDefinition().isStackable() || itemContainer.containerType.equals((Object)ItemContainerType.b)) && !itemContainer.containerType.equals((Object)ItemContainerType.c)) {
            ItemStack itemStack3;
            int id = itemContainer.indexOfItemInTab(itemStack2.getId(), value3);
            if (itemStack2.getMetadata() != -1) {
                id = value22;
            }
            if ((itemStack3 = itemContainer.getItemAtTabSlot(id, value3)) == null) {
                return -1;
            }
            if (itemStack3.getAmount() > itemStack2.getAmount()) {
                index = itemStack2.getAmount();
                itemContainer.setTabItem(id, new ItemStack(itemStack3.getId(), itemStack3.getAmount() - itemStack2.getAmount(), itemStack3.getMetadata()), value3);
            } else {
                index = itemStack3.getAmount();
                itemContainer.setTabItem(id, null, value3);
            }
        } else {
            int index2 = 0;
            while (index2 < itemStack2.getAmount()) {
                ItemStack itemStack4;
                int id2 = itemContainer.indexOfItemInTab(itemStack2.getId(), value3);
                if (index2 == 0 && value22 != -1 && (itemStack4 = itemContainer.getItemAtTabSlot(value22, value3)).getId() == itemStack2.getId()) {
                    id2 = value22;
                }
                if (id2 == -1) break;
                ++index;
                itemContainer.setTabItem(id2, null, value3);
                ++index2;
            }
        }
        return index;
    }

    private int removeInternal(ItemStack itemStack, int value2, boolean enabled2) {
        if (itemStack == null) {
            return -1;
        }
        int index = 0;
        if ((itemStack.getDefinition().isStackable() || this.containerType.equals((Object)ItemContainerType.b)) && !this.containerType.equals((Object)ItemContainerType.c)) {
            ItemStack itemStack2;
            int id = this.indexOfItem(itemStack.getId());
            if (itemStack.getMetadata() != -1) {
                id = value2;
            }
            if ((itemStack2 = this.getItemAt(id)) == null) {
                return -1;
            }
            if (itemStack2.getAmount() > itemStack.getAmount()) {
                index = itemStack.getAmount();
                this.setItem(id, new ItemStack(itemStack2.getId(), itemStack2.getAmount() - itemStack.getAmount(), itemStack2.getMetadata()));
            } else {
                index = itemStack2.getAmount();
                this.setItem(id, enabled2 ? new ItemStack(itemStack2.getId(), 0, itemStack2.getMetadata()) : null);
            }
        } else {
            int index2 = 0;
            while (index2 < itemStack.getAmount()) {
                ItemStack itemStack3;
                int id2 = this.indexOfItem(itemStack.getId());
                if (index2 == 0 && value2 != -1 && (itemStack3 = this.getItemAt(value2)).getId() == itemStack.getId()) {
                    id2 = value2;
                }
                if (id2 == -1) break;
                ++index;
                this.setItem(id2, null);
                ++index2;
            }
        }
        return index;
    }

    public final void swapSlots(int slot, int value2) {
        ItemStack itemStack = this.getItemAt(slot);
        boolean enabled = this.updatesEnabled;
        this.updatesEnabled = false;
        try {
            if (value2 > this.items.length - 1) {
                value2 = this.items.length - 1;
            }
            this.setItem(slot, this.getItemAt(value2));
            this.setItem(value2, itemStack);
            if (enabled) {
                this.notifySlotsUpdated(new int[]{slot, value2});
            }
        }
        finally {
            this.updatesEnabled = enabled;
        }
    }

    public final void swapTabSlots(int slot, int value2, int value32, int value42) {
        ItemStack itemStack = this.getItemAtTabSlot(slot, value32);
        boolean enabled = this.updatesEnabled;
        this.updatesEnabled = false;
        try {
            this.setTabItem(slot, this.getItemAtTabSlot(value2, value42), value32);
            this.setTabItem(value2, itemStack, value42);
            if (enabled) {
                this.notifySlotsUpdated(new int[]{slot, value2});
            }
        }
        finally {
            this.updatesEnabled = enabled;
        }
    }

    public final int getItemAmount(int itemId) {
        int index = 0;
        if (this.tabLimit == 0) {
            ItemStack[] itemStackArray = this.items;
            int length = this.items.length;
            int index2 = 0;
            while (index2 < length) {
                ItemStack itemStack = itemStackArray[index2];
                if (itemStack != null && itemStack.getId() == itemId) {
                    index += itemStack.getAmount();
                }
                ++index2;
            }
        } else {
            for (Object itemContainerTabObject : this.tabs) {
                ItemContainerTab itemContainerTab = (ItemContainerTab)itemContainerTabObject;
                if (itemContainerTab == null) continue;
                for (Object itemStackObject : itemContainerTab.items) {
                    ItemStack itemStack = (ItemStack)itemStackObject;
                    if (itemStack == null || itemStack.getId() != itemId) continue;
                    index += itemStack.getAmount();
                }
            }
        }
        return index;
    }

    public final ItemStack findItem(int itemId) {
        if (this.tabLimit == 0) {
            ItemStack[] itemStackArray = this.items;
            int length = this.items.length;
            int index = 0;
            while (index < length) {
                ItemStack itemStack = itemStackArray[index];
                if (itemStack != null && itemStack.getId() == itemId) {
                    return itemStack;
                }
                ++index;
            }
        } else {
            for (Object itemContainerTabObject : this.tabs) {
                ItemContainerTab itemContainerTab = (ItemContainerTab)itemContainerTabObject;
                if (itemContainerTab == null) continue;
                for (Object itemStackObject : itemContainerTab.items) {
                    ItemStack itemStack = (ItemStack)itemStackObject;
                    if (itemStack == null || itemStack.getId() != itemId) continue;
                    return itemStack;
                }
            }
        }
        return null;
    }

    public final int findTabContainingPlaceholder(int value3, int value22) {
        value22 = 0;
        while (value22 < this.tabs.size()) {
            Object itemContainerTab = (ItemContainerTab)this.tabs.get(value22);
            itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
            Iterator iterator = ((ArrayList)itemContainerTab).iterator();
            while (iterator.hasNext()) {
                itemContainerTab = (ItemStack)iterator.next();
                if (itemContainerTab == null || ((ItemStack)itemContainerTab).getId() != value3 || ((ItemStack)itemContainerTab).getAmount() != 0) continue;
                return value22;
            }
            ++value22;
        }
        return -1;
    }

    public final int findTabContainingItem(int itemId) {
        int index = 0;
        while (index < this.tabs.size()) {
            Object itemContainerTab = (ItemContainerTab)this.tabs.get(index);
            itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
            Iterator iterator = ((ArrayList)itemContainerTab).iterator();
            while (iterator.hasNext()) {
                itemContainerTab = (ItemStack)iterator.next();
                if (itemContainerTab == null || ((ItemStack)itemContainerTab).getId() != itemId) continue;
                return index;
            }
            ++index;
        }
        return -1;
    }

    public final ItemContainerTab getTab(int value2) {
        if (value2 > this.tabLimit - 1) {
            value2 = this.tabLimit - 1;
        }
        if (value2 > this.tabs.size() - 1) {
            return null;
        }
        return (ItemContainerTab)this.tabs.get(value2);
    }

    public final void moveTabItem(int itemId, int value2, int value32) {
        if (value32 > this.tabLimit - 1) {
            value32 = this.tabLimit - 1;
        }
        if (value32 > this.tabs.size() - 1) {
            return;
        }
        Object itemContainerTab = (ItemContainerTab)this.tabs.get(value32);
        itemContainerTab = ((ItemContainerTab)itemContainerTab).items;
        ItemStack itemStack = (ItemStack)((ArrayList)itemContainerTab).get(itemId);
        if (itemStack == null) {
            return;
        }
        if (value2 > itemId) {
            while (itemId < value2) {
                itemStack = (ItemStack)((ArrayList)itemContainerTab).get(itemId);
                ((ArrayList)itemContainerTab).set(itemId, (ItemStack)((ArrayList)itemContainerTab).get(itemId + 1));
                ((ArrayList)itemContainerTab).set(itemId + 1, itemStack);
                ++itemId;
            }
        } else if (itemId > value2) {
            while (itemId > value2) {
                itemStack = (ItemStack)((ArrayList)itemContainerTab).get(itemId);
                ((ArrayList)itemContainerTab).set(itemId, (ItemStack)((ArrayList)itemContainerTab).get(itemId - 1));
                ((ArrayList)itemContainerTab).set(itemId - 1, itemStack);
                --itemId;
            }
        }
        if (this.updatesEnabled) {
            this.notifyFullRefresh();
        }
    }

    private void notifySlotUpdated(int slot) {
        Iterator iterator = this.updateListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    private void notifyFullRefresh() {
        Iterator iterator = this.updateListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    private void notifySlotsUpdated(int[] slot) {
        Iterator iterator = this.updateListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    public final boolean containsItem(int itemId) {
        return this.indexOfItem(itemId) != -1;
    }

    public final boolean canAdd(ItemStack itemStack) {
        if ((itemStack.getDefinition().isStackable() || this.containerType.equals((Object)ItemContainerType.b)) && !this.containerType.equals((Object)ItemContainerType.c)) {
            ItemStack[] itemStackArray = this.items;
            int length = this.items.length;
            int index = 0;
            while (index < length) {
                ItemStack itemStack2 = itemStackArray[index];
                if (itemStack2 != null && itemStack2.getId() == itemStack.getId()) {
                    long value;
                    long amount = itemStack.getAmount();
                    long amount2 = amount + (value = (long)itemStack2.getAmount());
                    return amount2 <= Integer.MAX_VALUE && amount2 >= 1L;
                }
                ++index;
            }
            int firstFreeSlot = this.getFirstFreeSlot();
            return firstFreeSlot != -1;
        }
        int freeSlots = this.getFreeSlots();
        return freeSlots >= itemStack.getAmount();
    }

}
