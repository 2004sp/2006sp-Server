package com.rs2.bot;

import com.rs2.bot.tasks.LeatherCraftingBotTask;
import java.util.ArrayList;

public final class LeatherCraftingBotTaskList
extends ArrayList {
    public LeatherCraftingBotTaskList() {
        this.add(new LeatherCraftingBotTask(1));
    }
}

