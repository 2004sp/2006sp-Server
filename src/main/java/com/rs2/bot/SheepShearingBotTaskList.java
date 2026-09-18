package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class SheepShearingBotTaskList
extends ArrayList {
    public SheepShearingBotTaskList() {
        this.add(BotTaskDefinition.getDraynorSheepShearingTask());
    }
}

