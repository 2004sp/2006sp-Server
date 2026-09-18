package com.rs2.model.skill.crafting;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.GemDefinition;

public final class GemCuttingHandler {
    public static int CHISEL_ITEM_ID = 1755;

    public static boolean handleGemCutting(Player player, int value3, int value22) {
        if (value3 != CHISEL_ITEM_ID && value22 != CHISEL_ITEM_ID) {
            return false;
        }
        GemDefinition gemDefinition = GemDefinition.forUncutItemId(value3 = value3 != CHISEL_ITEM_ID ? value3 : value22);
        if (gemDefinition != null) {
            if (!ServerSettings.craftingEnabled) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (!player.getInventoryManager().getContainer().containsItem(CHISEL_ITEM_ID)) {
                return true;
            }
            if (player.getSkillManager().getCurrentLevels()[12] < gemDefinition.getRequiredLevel()) {
                player.getDialogueManager().showOneLineStatement("You need a crafting level of " + gemDefinition.getRequiredLevel() + " to cut this gem.");
                return true;
            }
            player.getUpdateState().setAnimation(gemDefinition.getAnimationId());
            Player player3 = player;
            player3.packetSender.sendSoundEffect(464, 1, 0);
            if (player.getInventoryManager().containsItem(CHISEL_ITEM_ID) && player.getInventoryManager().containsItem(value3)) {
                player.getInventoryManager().removeItem(new ItemStack(value3, 1));
                player.getInventoryManager().addItem(new ItemStack(gemDefinition.getCutItemId(), 1));
                player.getSkillManager().addExperience(12, gemDefinition.getExperience());
            }
            return true;
        }
        return false;
    }
}

