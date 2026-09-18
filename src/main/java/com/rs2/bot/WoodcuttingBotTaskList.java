package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class WoodcuttingBotTaskList
extends ArrayList {
    public WoodcuttingBotTaskList() {
        this.add(BotTaskDefinition.draynorWillowWoodcuttingTask);
        this.add(BotTaskDefinition.getEdgevilleYewWoodcuttingTask());
        this.add(BotTaskDefinition.getFaladorYewWoodcuttingTask());
        this.add(BotTaskDefinition.getVarrockPalaceYewWoodcuttingTask());
        this.add(BotTaskDefinition.getDraynorOakWoodcuttingTask());
        this.add(BotTaskDefinition.getDraynorTreeWoodcuttingTask());
        this.add(BotTaskDefinition.getVarrockWestOakWoodcuttingTask());
        this.add(BotTaskDefinition.getVarrockWestTreeWoodcuttingTask());
        this.add(BotTaskDefinition.getVarrockEastOakWoodcuttingTask());
        this.add(BotTaskDefinition.getVarrockEastTreeWoodcuttingTask());
        this.add(BotTaskDefinition.getEdgevilleTreeWoodcuttingTask());
        this.add(BotTaskDefinition.getDraynorYewWoodcuttingTask());
        this.add(BotTaskDefinition.getSeersMapleWoodcuttingTask());
        this.add(BotTaskDefinition.getSeersYewWoodcuttingTask());
        this.add(BotTaskDefinition.getSeersMagicTreeWoodcuttingTask());
        this.add(BotTaskDefinition.getSorcerersTowerMagicTreeWoodcuttingTask());
    }
}

