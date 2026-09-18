package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.EnchantmentChamberController;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class EnchantmentChamberColorTask
extends TickTask {
    public EnchantmentChamberColorTask(int value2) {
        super(40);
    }

    @Override
    public final void execute() {
        EnchantmentChamberController.setCurrentBonusColor(EnchantmentChamberController.getBonusColorNames()[EnchantmentChamberController.random.nextInt(EnchantmentChamberController.getBonusColorNames().length)]);
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Player player = playerArray[index];
            if (player != null && player.getEnchantmentChamberController().isInsideChamber()) {
                player.getEnchantmentChamberController().refreshBonusColorIndicator(EnchantmentChamberController.getCurrentBonusColor());
            }
            ++index;
        }
        if (EnchantmentChamberController.findEnchantmentGuardianNpc() != null) {
            EnchantmentChamberController.findEnchantmentGuardianNpc().getUpdateState().setForcedTextAndMarkUpdated("The color shape is now " + EnchantmentChamberController.getCurrentBonusColor() + "!");
        }
    }
}

