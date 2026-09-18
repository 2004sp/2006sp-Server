package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class SmeltingBotTaskList
extends ArrayList {
    public SmeltingBotTaskList() {
        this.add(BotTaskDefinition.getAlKharidSteelSmeltingTask());
        this.add(BotTaskDefinition.getFaladorSteelSmeltingTask());
    }
}

