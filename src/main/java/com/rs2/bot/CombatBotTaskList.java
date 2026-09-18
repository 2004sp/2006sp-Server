package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

public final class CombatBotTaskList
extends ArrayList {
    public CombatBotTaskList() {
        this.add(BotTaskDefinition.getDraynorChickenCombatTask());
        this.add(BotTaskDefinition.getFaladorCowCombatTask());
        this.add(BotTaskDefinition.getKaramjaVolcanoNorthLesserDemonCombatTask());
        this.add(BotTaskDefinition.getKaramjaVolcanoSouthLesserDemonCombatTask());
        this.add(BotTaskDefinition.getLumbridgeEastChickenCombatTask());
        this.add(BotTaskDefinition.getLumbridgeWestChickenCombatTask());
        this.add(BotTaskDefinition.getLumbridgeCowCombatTask());
        this.add(BotTaskDefinition.getEdgevilleDungeonHillGiantCombatTask());
        this.add(BotTaskDefinition.getEdgevilleDungeonNorthMossGiantCombatTask());
        this.add(BotTaskDefinition.getEdgevilleDungeonSouthMossGiantCombatTask());
        this.add(BotTaskDefinition.getDraynorGoblinCombatTask());
        this.add(BotTaskDefinition.lumbridgeGoblinCombatTask);
        this.add(BotTaskDefinition.getAlKharidWarriorCombatTask());
        this.add(BotTaskDefinition.getVarrockGuardCombatTask());
        this.add(BotTaskDefinition.getVarrockSewerGiantRatCombatTask());
        this.add(BotTaskDefinition.getBarbarianVillageBarbarianCombatTask());
        this.add(BotTaskDefinition.getDwarvenMineDwarfCombatTask());
        this.add(BotTaskDefinition.getFaladorGuardCombatTask());
        this.add(BotTaskDefinition.getEdgevilleDungeonSpiderRatCombatTask());
        this.add(BotTaskDefinition.getEdgevilleDungeonSkeletonCombatTask());
        this.add(BotTaskDefinition.getVarrockSouthChickenCombatTask());
        this.add(BotTaskDefinition.faladorImpCombatTask);
        this.add(BotTaskDefinition.brimhavenDungeonBlueDragonNorthCombatTask);
        this.add(BotTaskDefinition.getBrimhavenDungeonBlueDragonSouthCombatTask());
        this.add(BotTaskDefinition.getBrimhavenDungeonRedDragonCombatTask());
        this.add(BotTaskDefinition.getTaverleyDungeonHellhoundCombatTask());
    }
}

