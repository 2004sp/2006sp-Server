package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class SpinningBotTaskList
extends ArrayList {
    public SpinningBotTaskList() {
        this.add(BotTaskDefinition.getLumbridgeWoolSpinningTask());
        this.add(BotTaskDefinition.getSeersFlaxSpinningTask());
    }
}

