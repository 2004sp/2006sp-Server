package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.RectangularArea;

public final class EdgevilleTradeAdvertBotTask
extends BotTaskDefinition {
    private static Position tradeAdvertStartPosition = new Position(3094, 3491, 0);
    private static BotRoute unusedTradeAdvertRoute = null;
    private static RectangularArea[] tradeAdvertTaskAreas = new RectangularArea[]{new RectangularArea(3092, 3489, 3094, 3493), new RectangularArea(3093, 3494, 3097, 3497)};

    public EdgevilleTradeAdvertBotTask(int value2) {
        super(tradeAdvertStartPosition, (BotRoute)null, 0, false, 5);
        value2 = 1;
        EdgevilleTradeAdvertBotTask edgevilleTradeAdvertBotTask = this;
        this.usesCombatTradeAdvertItems = true;
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

