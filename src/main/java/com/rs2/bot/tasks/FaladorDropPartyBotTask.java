package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.RectangularArea;

public final class FaladorDropPartyBotTask
extends BotTaskDefinition {
    private static Position configuredStartPosition = new Position(2945, 3368, 0);
    private static BotRoute configuredPretaskRoute = new BotRoute(new Position[]{new Position(2945, 3374, 0), new Position(2954, 3379, 0), new Position(2962, 3380, 0), new Position(2975, 3379, 0), new Position(2987, 3374, 0), new Position(2999, 3364, 0), new Position(3013, 3359, 0)});
    private static BotRoute configuredTaskRoute = new BotRoute(new Position[]{new Position(3024, 3359, 0), new Position(3034, 3356, 0)});
    private static RectangularArea[] configuredTaskAreas = new RectangularArea[]{new RectangularArea(3032, 3348, 3045, 3360)};

    public FaladorDropPartyBotTask(int value2) {
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

