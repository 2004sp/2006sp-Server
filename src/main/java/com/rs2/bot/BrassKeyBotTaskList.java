package com.rs2.bot;

import com.rs2.bot.tasks.EdgevilleDungeonBrassKeyBotTask;
import java.util.ArrayList;

public final class BrassKeyBotTaskList
extends ArrayList {
    public BrassKeyBotTaskList() {
        this.add(new EdgevilleDungeonBrassKeyBotTask(1));
    }
}

