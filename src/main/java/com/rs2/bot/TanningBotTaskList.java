package com.rs2.bot;

import com.rs2.bot.tasks.AlKharidCowhideTanningBotTask;
import java.util.ArrayList;

public final class TanningBotTaskList
extends ArrayList {
    public TanningBotTaskList() {
        this.add(new AlKharidCowhideTanningBotTask(1));
    }
}

