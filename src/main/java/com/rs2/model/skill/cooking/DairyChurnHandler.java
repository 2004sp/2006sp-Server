package com.rs2.model.skill.cooking;

import com.rs2.model.player.Player;
import com.rs2.model.skill.cooking.DairyChurnRecipe;
import com.rs2.model.skill.cooking.DairyChurnTask;
import com.rs2.model.task.CycleEventHandler;

public class DairyChurnHandler {
    public DairyChurnHandler(Player player) {
    }

    public static boolean handleButtonClick(Player player, int buttonId) {
        DairyChurnRecipe dairyChurnRecipe = DairyChurnRecipe.forButtonId(buttonId);
        if (dairyChurnRecipe == null) {
            return false;
        }
        Player player2 = player;
        if (player2.interfaceAction == "dairyChurn") {
            if (player.getSkillManager().getCurrentLevels()[7] < dairyChurnRecipe.getRequiredLevel()) {
                player.getDialogueManager().showOneLineStatement("You need a cooking level of " + dairyChurnRecipe.getRequiredLevel() + " to make this.");
                return true;
            }
            player2 = player;
            player2.packetSender.closeInterfaces();
            int value = player.nextActionSequence();
            player.setActiveCycleEvent(new DairyChurnTask(player, value, dairyChurnRecipe));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
            return true;
        }
        return false;
    }
}

