package com.rs2.model.travel;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.model.travel.ShipRoute;
import com.rs2.util.GameplayTrace;

public final class ShipTravelArrivalTask
extends TickTask {
    private final ShipRoute route;
    private final Player player;

    public ShipTravelArrivalTask(int value2, ShipRoute shipRoute, Player player) {
        super(value2);
        this.route = shipRoute;
        this.player = player;
    }

    @Override
    public final void execute() {
        Player player;
        if (this.route.ordinal() < 16) {
            player = this.player;
            player.packetSender.sendConfig(75, -1);
        }
        if (this.route == ShipRoute.PORT_SARIM_TO_CRANDOR) {
            DialogueManager.continueDialogue(this.player, 743, 5, 0);
        } else if (this.route.arrivalName != null) {
            this.player.getDialogueManager().showOneLineStatement("The ship arrives at " + this.route.arrivalName + ".");
            this.player.getDialogueManager().finishDialogue();
        }
        player = this.player;
        player.packetSender.sendMinimapState(0);
        this.player.setActionLocked(false);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel ship arrival player=" + GameplayTrace.describe(this.player) + " route=" + this.route);
        }
        if (this.player.currentBotRoute != null) {
            this.player.botRouteTravelPending = false;
            this.player.continueBotRoute();
        }
        this.stop();
    }
}

