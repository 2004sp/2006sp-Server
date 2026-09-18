package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.RectangularArea;

public final class VarrockDropPartyBotTask
extends BotTaskDefinition {
    private static Position configuredStartPosition = new Position(3185, 3436, 0);
    private static BotRoute configuredPretaskRoute = new BotRoute(new Position[]{new Position(3182, 3432, 0), new Position(3191, 3430, 0), new Position(3205, 3430, 0), new Position(3220, 3429, 0), new Position(3237, 3429, 0), new Position(3254, 3428, 0)});
    private static BotRoute configuredTaskRoute = new BotRoute(new Position[]{new Position(3243, 3428, 0), new Position(3244, 3417, 0)});
    private static RectangularArea[] configuredTaskAreas = new RectangularArea[]{new RectangularArea(3242, 3411, 3246, 3421)};

    public VarrockDropPartyBotTask(int value2) {
        super(configuredStartPosition, configuredTaskRoute, 0, false, 1);
        super.setPretaskRoute(configuredPretaskRoute);
        super.setTaskAreas(configuredTaskAreas);
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        DropPartyBotManager.prepareDropPartyInventory(player);
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        DropPartyBotManager.prepareDropPartyCombatLoadout(player);
    }

    @Override
    public final void prepareDropPartyState(Player player) {
        DropPartyBotManager.startDropPartyTick(player);
    }
}

