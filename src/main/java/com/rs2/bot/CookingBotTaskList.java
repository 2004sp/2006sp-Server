package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class CookingBotTaskList
extends ArrayList {
    public CookingBotTaskList() {
        this.add(BotTaskDefinition.getVarrockLobsterCookingTask());
        this.add(BotTaskDefinition.getAlKharidLobsterCookingTask());
        this.add(BotTaskDefinition.getCatherbyLobsterCookingTask());
    }
}

