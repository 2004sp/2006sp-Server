package com.rs2.model.skill.herblore;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.herblore.FinishedPotionTask;
import com.rs2.model.skill.herblore.UnfinishedPotionTask;
import com.rs2.model.task.CycleEventHandler;

public final class HerbloreHandler {
    private static final double[][] POTION_RECIPES = new double[][]{{249.0, 221.0, 91.0, 121.0, 25.0, 0.0}, {251.0, 235.0, 93.0, 175.0, 37.5, 5.0}, {253.0, 225.0, 95.0, 115.0, 50.0, 12.0}, {253.0, 592.0, 95.0, 3408.0, 50.0, 15.0}, {255.0, 223.0, 97.0, 127.0, 62.5, 22.0}, {255.0, 1581.0, 97.0, 1582.0, 80.0, 25.0}, {255.0, 1975.0, 97.0, 3010.0, 67.5, 26.0}, {257.0, 239.0, 99.0, 133.0, 75.0, 30.0}, {2998.0, 2152.0, 3002.0, 3034.0, 80.0, 34.0}, {257.0, 231.0, 99.0, 139.0, 87.5, 38.0}, {259.0, 221.0, 101.0, 145.0, 100.0, 45.0}, {259.0, 235.0, 101.0, 181.0, 106.3, 48.0}, {261.0, 231.0, 103.0, 151.0, 112.5, 50.0}, {261.0, 2970.0, 103.0, 3018.0, 117.5, 52.0}, {263.0, 225.0, 105.0, 157.0, 125.0, 55.0}, {263.0, 241.0, 105.0, 187.0, 137.5, 60.0}, {3000.0, 223.0, 3004.0, 3026.0, 142.5, 63.0}, {265.0, 239.0, 107.0, 163.0, 150.0, 66.0}, {2998.0, 6049.0, 5942.0, 5945.0, 175.0, 68.0}, {2481.0, 241.0, 2483.0, 2454.0, 157.5, 69.0}, {267.0, 245.0, 109.0, 169.0, 162.5, 72.0}, {6016.0, 223.0, 5936.0, 5937.0, 175.0, 73.0}, {2481.0, 3138.0, 2483.0, 3042.0, 172.5, 76.0}, {269.0, 247.0, 111.0, 189.0, 175.0, 78.0}, {259.0, 6051.0, 101.0, 5954.0, 175.0, 79.0}, {2998.0, 6693.0, 3002.0, 6687.0, 175.0, 81.0}, {2398.0, 6018.0, 5939.0, 5940.0, 175.0, 82.0}};

    public static boolean combinePotionDoses(Player player, int value9, int value22, int value32, int value42) {
        combinePotionDosesControlExit1: {
            int value5;
            int value6;
            String text;
            Object value7;
            combinePotionDosesControlExit2: {
                Object value8;
                combinePotionDosesControlExit3: {
                    value7 = new ItemStack(value9);
                    value8 = new ItemStack(value22);
                    text = ((ItemStack)value7).getDefinition().getName().toLowerCase();
                    String definition = ((ItemStack)value8).getDefinition().getName().toLowerCase();
                    if (text.contains("(4)") || definition.contains("(4)") || text.contains("watering") || definition.contains("watering")) {
                        return false;
                    }
                    try {
                        if (!text.substring(0, text.indexOf("(")).equalsIgnoreCase(definition.substring(0, definition.indexOf("(")))) break combinePotionDosesControlExit1;
                        value6 = Integer.parseInt(text.substring(text.indexOf("(") + 1, text.indexOf("(") + 2));
                        value5 = Integer.parseInt(definition.substring(definition.indexOf("(") + 1, definition.indexOf("(") + 2));
                        value5 = value6 + value5;
                        if (player.getInventoryManager().containsItem(((ItemStack)value7).getId()) && player.getInventoryManager().containsItem(((ItemStack)value8).getId())) break combinePotionDosesControlExit3;
                        return false;
                    }
                    catch (Exception exception) {
                        return false;
                    }
                }
                if (value5 <= 4) break combinePotionDosesControlExit2;
                value7 = String.valueOf(text.substring(0, text.indexOf("(") + 1)) + 4 + ")";
                value8 = String.valueOf(text.substring(0, text.indexOf("(") + 1)) + (value5 -= 4) + ")";
                player.getInventoryManager().removeItemAtSlot(value32);
                player.getInventoryManager().removeItemAtSlot(value42);
                player.getInventoryManager().setItemInSlot(new ItemStack(ItemDefinition.findIdByName((String)value7)), value42);
                player.getInventoryManager().setItemInSlot(new ItemStack(ItemDefinition.findIdByName((String)value8)), value32);
                return true;
            }
            value6 = value5;
            value7 = String.valueOf(text.substring(0, text.indexOf("(") + 1)) + value6 + ")";
            player.getInventoryManager().removeItemAtSlot(value32);
            player.getInventoryManager().removeItemAtSlot(value42);
            player.getInventoryManager().setItemInSlot(new ItemStack(ItemDefinition.findIdByName((String)value7)), value42);
            player.getInventoryManager().setItemInSlot(new ItemStack(229, 1), value32);
            return true;
        }
        return false;
    }

    public static boolean handlePotionMaking(Player player, ItemStack itemStack, ItemStack itemStack2, int value7, int value22) {
        value7 = itemStack.getId();
        int id = itemStack2.getId();
        double[][] doubleValues = POTION_RECIPES;
        int index = 0;
        while (index < 27) {
            double[] recipe = doubleValues[index];
            int value3 = (int)recipe[0];
            double ingredientId = recipe[1];
            double unfinishedPotionId = recipe[2];
            double finishedPotionId = recipe[3];
            double experience = recipe[4];
            double requiredLevel = recipe[5];
            int value4 = value3 == 2398 || ingredientId == 6018.0 || value3 == 6016 || ingredientId == 6049.0 ? 5935 : 227;
            if (value7 == value3 && id == value4 || value7 == value4 && id == value3) {
                if ((double)player.getSkillManager().getCurrentLevels()[15] < requiredLevel) {
                    player.getDialogueManager().showOneLineStatement("You need a Herblore level of " + (int)requiredLevel + " in order to make this potion.");
                    return true;
                }
                int vialItemId = value4;
                ItemStack itemStack3 = new ItemStack(value3, 1);
                ItemStack itemStack4 = new ItemStack(vialItemId, 1);
                if (!ServerSettings.herbloreEnabled) {
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("This skill is currently disabled.");
                } else if (player.getQuestState(29) != 1) {
                    QuestDefinition questDefinition = QuestDefinition.forId(29);
                    String name = questDefinition.getName();
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("You need to complete " + name + " to do this.");
                } else {
                    player.getUpdateState().setAnimation(363);
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("You put the " + itemStack3.getDefinition().getName().replace(" herb", "").toLowerCase() + " into the " + itemStack4.getDefinition().getName() + ".");
                    int value5 = player.nextActionSequence();
                    CycleEventHandler.getInstance().schedule(player, new UnfinishedPotionTask(player, value5, itemStack3, vialItemId, unfinishedPotionId), 1);
                }
                return true;
            }
            if ((double)value7 == unfinishedPotionId && (double)id == ingredientId || (double)value7 == ingredientId && (double)id == unfinishedPotionId) {
                if ((double)player.getSkillManager().getCurrentLevels()[15] < requiredLevel) {
                    player.getDialogueManager().showOneLineStatement("You need a Herblore level of " + (int)requiredLevel + " in order to make this potion.");
                    return true;
                }
                ItemStack ingredientStack = new ItemStack((int)ingredientId, 1);
                if (!ServerSettings.herbloreEnabled) {
                    Player player5 = player;
                    player5.packetSender.sendGameMessage("This skill is currently disabled.");
                } else if (player.getQuestState(29) != 1) {
                    QuestDefinition questDefinition = QuestDefinition.forId(29);
                    String name2 = questDefinition.getName();
                    Player player6 = player;
                    player6.packetSender.sendGameMessage("You need to complete " + name2 + " to do this.");
                } else {
                    player.getUpdateState().setAnimation(363);
                    Player player7 = player;
                    player7.packetSender.sendGameMessage("You mix the " + ingredientStack.getDefinition().getName().toLowerCase() + " into your potion");
                    int value6 = player.nextActionSequence();
                    CycleEventHandler.getInstance().schedule(player, new FinishedPotionTask(player, value6, ingredientStack, unfinishedPotionId, finishedPotionId, experience), 1);
                }
                return true;
            }
            ++index;
        }
        return false;
    }

    public static boolean emptyContainer(Player player, ItemStack itemStack, int value2) {
        Object definition = itemStack.getDefinition().getDescription().toLowerCase();
        String definition2 = itemStack.getDefinition().getName().toLowerCase();
        if (((String)definition).contains("bucket") || ((String)definition).contains("potion") || ((String)definition).contains("dose") || ((String)definition).contains("jug") || definition2.contains("jug") || ((String)definition).contains("bowl") || ((String)definition).contains("vial") || definition2.contains("flour") || ((String)definition).contains("bucket") || ((String)definition).contains("cup")) {
            definition = player;
            ((Player)definition).packetSender.sendGameMessage("You empty your " + definition2 + ".");
            if (player.getInventoryManager().removeItemFromSlot(itemStack, value2)) {
                player.getInventoryManager().setItemInSlot(new ItemStack(HerbloreHandler.getEmptyContainerItemId(itemStack)), value2);
            } else if (player.getInventoryManager().removeItem(itemStack)) {
                player.getInventoryManager().addItem(new ItemStack(HerbloreHandler.getEmptyContainerItemId(itemStack)));
            }
            return true;
        }
        return false;
    }

    private static int getEmptyContainerItemId(ItemStack itemStack) {
        String definition = itemStack.getDefinition().getDescription().toLowerCase();
        String definition2 = itemStack.getDefinition().getName().toLowerCase();
        if (definition.contains("potion") || definition.contains("vial") || definition.contains("dose")) {
            return 229;
        }
        if (definition.contains("bucket") || definition.contains("compost")) {
            return 1925;
        }
        if (definition.contains("bowl") || definition.contains("curry")) {
            return 1923;
        }
        if (definition2.contains("jug") || definition.contains("jug")) {
            return 1935;
        }
        if (definition2.contains("flour")) {
            return 1931;
        }
        if (definition.contains("cup")) {
            return 1980;
        }
        return -1;
    }

}

