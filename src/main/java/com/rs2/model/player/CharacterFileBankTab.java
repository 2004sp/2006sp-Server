package com.rs2.model.player;

import java.util.ArrayList;

public final class CharacterFileBankTab {
    ArrayList items = new ArrayList();

    private CharacterFileBankTab() {
    }

    public final ArrayList getItems() {
        return this.items;
    }

    CharacterFileBankTab(byte value2) {
        this();
    }

    CharacterFileBankTab(int value2) {
        this();
    }
}

