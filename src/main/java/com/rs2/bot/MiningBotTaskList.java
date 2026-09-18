package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class MiningBotTaskList
extends ArrayList {
    public MiningBotTaskList() {
        this.add(BotTaskDefinition.getAlKharidMineTask());
        this.add(BotTaskDefinition.getCraftingGuildMineTask());
        this.add(BotTaskDefinition.dwarvenMineTask);
        this.add(BotTaskDefinition.getMiningGuildMineTask());
        this.add(BotTaskDefinition.getVarrockEastMineTask());
        this.add(BotTaskDefinition.varrockWestMineTask);
        this.add(BotTaskDefinition.getWildernessRuniteMineTask());
        this.add(BotTaskDefinition.rimmingtonMineTask);
    }
}

