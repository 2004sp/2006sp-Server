package com.rs2.model.item;

import java.util.ArrayList;

public final class ItemContainerTab {
    ArrayList items = new ArrayList();
    boolean persistent = false;

    private ItemContainerTab() {
    }

    private ItemContainerTab(byte value2) {
        this.persistent = true;
    }

    public final ArrayList getItems() {
        return this.items;
    }

    ItemContainerTab(boolean enabled2) {
        this((byte)0);
    }

    ItemContainerTab(char character) {
        this();
    }
}

