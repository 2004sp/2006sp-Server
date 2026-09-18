package com.rs2.model.skill.smithing;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;

public final class DragonSquareShieldSmithing {
    private static int LEFT_SHIELD_HALF_ITEM_ID = 2366;
    private static int RIGHT_SHIELD_HALF_ITEM_ID = 2368;
    private static int DRAGON_SQUARE_SHIELD_ITEM_ID = 1187;

    public static boolean handleItemOnAnvil(Player player, int itemId, int value2) {
        if (value2 == 2783 && (itemId == LEFT_SHIELD_HALF_ITEM_ID || itemId == RIGHT_SHIELD_HALF_ITEM_ID) && player.getInventoryManager().containsItem(LEFT_SHIELD_HALF_ITEM_ID) && player.getInventoryManager().containsItem(RIGHT_SHIELD_HALF_ITEM_ID)) {
            DialogueManager.continueDialogue(player, 11287, 1, 0);
            return true;
        }
        return false;
    }

    public static void forgeDragonSquareShield(Player player) {
        if (player.getSkillManager().getBaseLevel(13) >= 60) {
            if (!player.getInventoryManager().getContainer().containsItem(2347)) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("You need a hammer to smith on an anvil.");
                return;
            }
            if (!player.getInventoryManager().getContainer().containsItem(LEFT_SHIELD_HALF_ITEM_ID) || !player.getInventoryManager().getContainer().containsItem(RIGHT_SHIELD_HALF_ITEM_ID)) {
                return;
            }
            player.getInventoryManager().removeItem(new ItemStack(LEFT_SHIELD_HALF_ITEM_ID, 1));
            player.getInventoryManager().removeItem(new ItemStack(RIGHT_SHIELD_HALF_ITEM_ID, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(DRAGON_SQUARE_SHIELD_ITEM_ID, 1));
            Player player3 = player;
            player3.packetSender.sendSoundEffect(468, 1, 0);
            player.getUpdateState().setAnimation(898);
            player.getSkillManager().addExperience(13, 75.0);
            return;
        }
        Player player4 = player;
        player4.packetSender.sendGameMessage("You need a " + SkillManager.SKILL_NAMES[13] + " level of 60" + " to do that.");
    }
}

