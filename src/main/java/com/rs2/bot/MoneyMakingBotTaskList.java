package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class MoneyMakingBotTaskList
extends ArrayList {
    public MoneyMakingBotTaskList() {
        this.add(BotTaskDefinition.getFaladorWineOfZamorakTelegrabTask());
        this.add(BotTaskDefinition.wizardsTowerLesserDemonMagicTask);
        this.add(BotTaskDefinition.seersFlaxPickingTask);
    }
}

