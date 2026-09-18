package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundController;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class AlchemistPlaygroundRotationTask
extends TickTask {
    public AlchemistPlaygroundRotationTask(int value2) {
        super(70);
    }

    @Override
    public final void execute() {
        AlchemistPlaygroundController.rotateCupboardObjectIds();
        AlchemistPlaygroundController.randomizeAlchemyItemValues();
        int alchemyItemIds = AlchemistPlaygroundController.getAlchemyItemIds()[AlchemistPlaygroundController.random.nextInt(AlchemistPlaygroundController.getAlchemyItemIds().length)];
        while (alchemyItemIds == AlchemistPlaygroundController.currentFreeAlchemyItemId) {
            alchemyItemIds = AlchemistPlaygroundController.getAlchemyItemIds()[AlchemistPlaygroundController.random.nextInt(AlchemistPlaygroundController.getAlchemyItemIds().length)];
        }
        AlchemistPlaygroundController.currentFreeAlchemyItemId = alchemyItemIds;
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Player player = playerArray[index];
            if (player != null && player.getAlchemistPlaygroundController().isInsidePlayground()) {
                player.getAlchemistPlaygroundController().refreshFreeAlchemyItemIndicator();
            }
            ++index;
        }
    }
}

