package com.rs2.model.skill.smithing;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;

public final class DragonfireShieldSmithing {
    private static int ANTI_DRAGON_SHIELD_ITEM_ID = 1540;
    private static int DRAGONFIRE_SHIELD_ITEM_ID = 11284;
    private static int DRACONIC_VISAGE_ITEM_ID = 11286;

    public static boolean handleItemOnAnvil(Player player, int itemId, int value2) {
        if (value2 == 2783 && (itemId == ANTI_DRAGON_SHIELD_ITEM_ID || itemId == DRACONIC_VISAGE_ITEM_ID) && player.getInventoryManager().containsItem(ANTI_DRAGON_SHIELD_ITEM_ID) && player.getInventoryManager().containsItem(DRACONIC_VISAGE_ITEM_ID)) {
            if (player.getSkillManager().getBaseLevel(13) >= 90) {
                if (!player.getInventoryManager().getContainer().containsItem(2347)) {
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You need a hammer to smith on an anvil.");
                } else {
                    player.getInventoryManager().removeItem(new ItemStack(ANTI_DRAGON_SHIELD_ITEM_ID, 1));
                    player.getInventoryManager().removeItem(new ItemStack(DRACONIC_VISAGE_ITEM_ID, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(DRAGONFIRE_SHIELD_ITEM_ID, 1));
                    Player player3 = player;
                    player3.packetSender.sendSoundEffect(468, 1, 0);
                    player.getUpdateState().setAnimation(898);
                    player.getSkillManager().addExperience(13, 2000.0);
                }
            } else {
                Player player4 = player;
                player4.packetSender.sendGameMessage("You need a " + SkillManager.SKILL_NAMES[13] + " level of 90" + " to do that.");
            }
            return true;
        }
        return false;
    }
}

