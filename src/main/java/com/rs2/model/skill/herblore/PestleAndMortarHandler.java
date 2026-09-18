package com.rs2.model.skill.herblore;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;

public final class PestleAndMortarHandler {
    private static int PESTLE_AND_MORTAR_ITEM_ID = 233;
    private static final int[][] GRINDING_RECIPES = new int[][]{{237, 235}, {1973, 1975}, {5075, 6693}, {243, 241}};

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean handlePestleAndMortar(Player player, ItemStack itemStack, ItemStack itemStack2, int value6, int value22) {
        try {
            value22 = itemStack.getId();
            int id = itemStack2.getId();
            int[][] integerValues = GRINDING_RECIPES;
            int index = 0;
            while (true) {
                if (index >= 4) {
                    return false;
                }
                int[] integerValues2 = integerValues[index];
                int value3 = integerValues2[0];
                int value4 = integerValues2[1];
                if (value22 == value3 && id == PESTLE_AND_MORTAR_ITEM_ID || value22 == PESTLE_AND_MORTAR_ITEM_ID && id == value3) {
                    player.getInventoryManager().getContainer().getItemAt(value6).getId();
                    if (!ServerSettings.herbloreEnabled) {
                        Player player2 = player;
                        player2.packetSender.sendGameMessage("This skill is currently disabled.");
                        return true;
                    }
                    if (player.getQuestState(29) != 1) {
                        Object value5 = QuestDefinition.forId(29);
                        value5 = ((QuestDefinition)value5).getName();
                        Player player3 = player;
                        player3.packetSender.sendGameMessage("You need to complete " + (String)value5 + " to do this.");
                        return true;
                    }
                    if (!player.getInventoryManager().containsItem(value3)) return true;
                    player.getInventoryManager().removeItem(new ItemStack(value3, 1));
                    player.getInventoryManager().addItem(new ItemStack(value4, 1));
                    Player player4 = player;
                    player4.packetSender.sendSoundEffect(373, 1, 0);
                    player4 = player;
                    player4.packetSender.sendGameMessage("You grind the " + new ItemStack(value3).getDefinition().getName() + ".");
                    return true;
                }
                ++index;
            }
        }
        catch (Exception exception) {}
        return false;
    }
}

