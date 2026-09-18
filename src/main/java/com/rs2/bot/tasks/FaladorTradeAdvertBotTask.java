package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.RectangularArea;

public final class FaladorTradeAdvertBotTask
extends BotTaskDefinition {
    private static Position tradeAdvertStartPosition = new Position(3013, 3355, 0);
    private static BotRoute unusedTradeAdvertRoute = null;
    private static RectangularArea[] tradeAdvertTaskAreas = new RectangularArea[]{new RectangularArea(3009, 3359, 3018, 3366)};

    public FaladorTradeAdvertBotTask(int value2) {
        super(tradeAdvertStartPosition, (BotRoute)null, 0, false, 30);
        super.setTaskAreas(tradeAdvertTaskAreas);
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        BotTradeAdvertManager.prepareTradeAdvertInventory(player);
    }

    @Override
    public final void prepareTradeAdvertState(Player player) {
        BotTradeAdvertManager.prepareTradeAdvertOffer(player);
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        BotTradeAdvertManager.prepareTradeAdvertCombatLoadout(player);
    }
}

