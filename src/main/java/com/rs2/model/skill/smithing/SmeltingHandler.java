package com.rs2.model.skill.smithing;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.smithing.SmeltingTask;
import com.rs2.model.task.CycleEventHandler;

public final class SmeltingHandler {
    private static final int[][][] SMELTING_DEFINITIONS = new int[][][]{new int[][]{{2349, 1, 6}, {436, 1}, {438, 1}}, new int[][]{{2351, 15, 13}, {440, 1}}, new int[][]{{2355, 20, 14}, {442, 1}}, new int[][]{{2353, 30, 18}, {440, 1}, {453, 2}}, new int[][]{{2893, 20, 8}, {2892, 1}, {453, 4}}, new int[][]{{2357, 40, 23}, {444, 1}}, new int[][]{{2365, 40, 23}, {446, 1}}, new int[][]{{2359, 50, 30}, {447, 1}, {453, 4}}, new int[][]{{2361, 70, 38}, {449, 1}, {453, 6}}, new int[][]{{2363, 85, 50}, {451, 1}, {453, 8}}};
    private static int[][] SMELTING_BUTTONS = new int[][]{{3987, 2349, 1}, {3986, 2349, 5}, {2807, 2349, 10}, {2414, 2349, 28}, {3991, 2351, 1}, {3990, 2351, 5}, {3989, 2351, 10}, {3988, 2351, 28}, {3995, 2355, 1}, {3994, 2355, 5}, {3993, 2355, 10}, {3992, 2355, 28}, {3999, 2353, 1}, {3998, 2353, 5}, {3997, 2353, 10}, {3996, 2353, 28}, {4003, 2357, 1}, {4002, 2357, 5}, {4001, 2357, 10}, {4000, 2357, 28}, {7441, 2359, 1}, {7440, 2359, 5}, {6397, 2359, 10}, {4158, 2359, 28}, {7446, 2361, 1}, {7444, 2361, 5}, {7443, 2361, 10}, {7442, 2361, 28}, {7450, 2363, 1}, {7449, 2363, 5}, {7448, 2363, 10}, {7447, 2363, 28}};

    public static void handleOreOnFurnace(Player player, int value2) {
        int[][][] integerValues = SMELTING_DEFINITIONS;
        int index = 0;
        while (index < 10) {
            int[][] integerValues2 = integerValues[index];
            if (value2 == integerValues2[1][0] || integerValues2.length > 2 && value2 == integerValues2[2][0]) {
                player.setSelectedSmithingBarItemId(integerValues2[0][0]);
                SmeltingHandler.startSmeltingTask(player, 1);
                return;
            }
            ++index;
        }
    }

    public static int selectBestBotSmeltingBarItemId(Player player) {
        int initialValue = -1;
        int value = 9;
        while (value >= 0) {
            int value2 = SMELTING_DEFINITIONS[value][0][0];
            int value3 = SMELTING_DEFINITIONS[value][0][1];
            if (player.getSkillManager().getCurrentLevels()[13] >= value3) {
                boolean enabled;
                value3 = SMELTING_DEFINITIONS[value][1][0];
                int value4 = SMELTING_DEFINITIONS[value][1][1];
                int index = 0;
                int index2 = 0;
                if (SMELTING_DEFINITIONS[value].length > 2) {
                    index = SMELTING_DEFINITIONS[value][2][0];
                    index2 = SMELTING_DEFINITIONS[value][2][1];
                }
                ItemStack itemStack = new ItemStack(value3, value4);
                ItemStack itemStack2 = new ItemStack(index, index2);
                boolean enabled2 = enabled = index > 0;
                if (player.ownsItemAmount(itemStack.getId(), itemStack.getAmount()) && (!enabled || player.ownsItemAmount(itemStack2.getId(), itemStack2.getAmount()))) {
                    initialValue = 0;
                    value = 0;
                    int index3 = 0;
                    while (initialValue + value4 + index2 <= 28) {
                        initialValue = (value += value4) + (index3 += index2);
                    }
                    ItemStack[] itemStackArray = enabled ? new ItemStack[]{new ItemStack(value3, value), new ItemStack(index, index3)} : new ItemStack[]{new ItemStack(value3, value)};
                    player.botTaskRequiredItems = itemStackArray;
                    player.botTaskItemId = value2;
                    initialValue = value2;
                    break;
                }
            }
            --value;
        }
        return initialValue;
    }

    public static int getCoalRequiredForBar(int value4) {
        int index = 0;
        int value2 = 9;
        while (value2 >= 0) {
            int value3 = SMELTING_DEFINITIONS[value2][0][0];
            if (value4 == value3) {
                value3 = 0;
                int index2 = 0;
                if (SMELTING_DEFINITIONS[value2].length > 2) {
                    value3 = SMELTING_DEFINITIONS[value2][2][0];
                    index2 = SMELTING_DEFINITIONS[value2][2][1];
                }
                if (value3 == 453) {
                    index = index2;
                }
            }
            --value2;
        }
        return index;
    }

    public static void prepareBotSmeltingRequirements(Player player, int value5) {
        int value2 = 9;
        while (value2 >= 0) {
            int value3 = SMELTING_DEFINITIONS[value2][0][0];
            if (value5 == value3) {
                value5 = SMELTING_DEFINITIONS[value2][1][0];
                int value4 = SMELTING_DEFINITIONS[value2][1][1];
                int index = 0;
                int index2 = 0;
                if (SMELTING_DEFINITIONS[value2].length > 2) {
                    index = SMELTING_DEFINITIONS[value2][2][0];
                    index2 = SMELTING_DEFINITIONS[value2][2][1];
                }
                ItemStack itemStack = new ItemStack(value5, value4);
                ItemStack itemStack2 = new ItemStack(index, index2);
                boolean enabled = index > 0;
                int index3 = 0;
                int index4 = 0;
                int index5 = 0;
                while (index3 + value4 + index2 <= 28) {
                    index3 = (index4 += value4) + (index5 += index2);
                }
                ItemStack[] itemStackArray = enabled ? new ItemStack[]{new ItemStack(value5, index4), new ItemStack(index, index5)} : new ItemStack[]{new ItemStack(value5, index4)};
                player.botTaskRequiredItems = itemStackArray;
                player.botTaskItemId = value3;
                if (!player.getInventoryManager().containsItemAmount(itemStack.getId(), itemStack.getAmount()) || enabled && !player.getInventoryManager().containsItemAmount(itemStack2.getId(), itemStack2.getAmount())) {
                    player.currentBotTask.startWalkToBank(player);
                    return;
                }
                return;
            }
            --value2;
        }
    }

    public static void openSmeltingInterface(Player player) {
        if (!ServerSettings.smithingEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (player.botEnabled) {
            player.setSelectedSmithingBarItemId(player.botTaskItemId);
            SmeltingHandler.startSmeltingTask(player, 28);
            return;
        }
        String text = "smelt";
        Player player3 = player;
        player.interfaceAction = text;
        player3 = player;
        player3.packetSender.sendInterfaceModel(2405, 150, 2349);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2406, 150, 2351);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2407, 150, 2355);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2409, 150, 2353);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2410, 150, 2357);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2411, 150, 2359);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2412, 150, 2361);
        player3 = player;
        player3.packetSender.sendInterfaceModel(2413, 150, 2363);
        player3 = player;
        player3.packetSender.showChatboxInterface(2400);
    }

    public static boolean handleSmeltingButton(Player player, int buttonId, int value2) {
        if (player.interfaceAction != "smelt") {
            return false;
        }
        int[][] integerValues = SMELTING_BUTTONS;
        int index = 0;
        while (index < 32) {
            int[] buttonDefinition = integerValues[index];
            if (buttonId == buttonDefinition[0]) {
                player.setSelectedSmithingBarItemId(buttonDefinition[1]);
                if (buttonDefinition[2] == 28 && value2 <= 0) {
                    player.packetSender.sendEnterInputPrompt(buttonId);
                    return true;
                }
                SmeltingHandler.startSmeltingTask(player, value2 <= 0 ? buttonDefinition[2] : value2);
                return true;
            }
            ++index;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean startSmeltingTask(Player player, int value6) {
        Player player2 = player;
        player2.packetSender.closeInterfaces();
        String text = "";
        player2 = player;
        player.interfaceAction = text;
        int selectedSmithingBarItemId = player.getSelectedSmithingBarItemId();
        int index = 0;
        while (index < 10) {
            if (selectedSmithingBarItemId == SMELTING_DEFINITIONS[index][0][0]) {
                if (!ServerSettings.smithingEnabled) {
                    player2 = player;
                    player2.packetSender.sendGameMessage("This skill is currently disabled.");
                    return false;
                }
                int value2 = SMELTING_DEFINITIONS[index][0][1];
                int value3 = SMELTING_DEFINITIONS[index][0][2];
                int value4 = SMELTING_DEFINITIONS[index][1][0];
                int value5 = SMELTING_DEFINITIONS[index][1][1];
                int secondaryOreId = 0;
                int secondaryOreAmount = 0;
                if (SMELTING_DEFINITIONS[index].length > 2) {
                    secondaryOreId = SMELTING_DEFINITIONS[index][2][0];
                    secondaryOreAmount = SMELTING_DEFINITIONS[index][2][1];
                }
                if (!SkillActionHelper.checkSkillRequirement(player, 13, value2, "smelt this bar")) {
                    if (player.botEnabled) {
                        player.currentBotTask.startWalkToBank(player);
                    }
                    return true;
                }
                index = player.nextActionSequence();
                ItemStack itemStack = new ItemStack(value4, value5);
                ItemStack itemStack2 = new ItemStack(secondaryOreId, secondaryOreAmount);
                ItemStack itemStack3 = new ItemStack(selectedSmithingBarItemId);
                boolean enabled = secondaryOreId > 0;
                if (!player.getInventoryManager().containsItemStack(itemStack) || enabled && !player.getInventoryManager().containsItemStack(itemStack2)) {
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("You have run out of ore to smith!");
                    if (player.botEnabled) {
                        player.currentBotTask.startWalkToBank(player);
                    }
                    return true;
                }
                if (player.getQuestState(0) != 1) {
                    player.getDialogueManager().showOneLineStatement("You smelt the " + itemStack.getDefinition().getName().toLowerCase() + " " + (enabled ? "and " + itemStack2.getDefinition().getName().toLowerCase() + " together " : "") + "in the furnace.");
                    player.setInteractionTargetId(0);
                } else if (itemStack3.getId() == 2365) {
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("You place a lump of gold in the furnace.");
                } else {
                    Player player5 = player;
                    player5.packetSender.sendGameMessage("You smelt the " + itemStack.getDefinition().getName().toLowerCase() + " " + (enabled ? "and " + itemStack2.getDefinition().getName().toLowerCase() + " together " : "") + "in the furnace.");
                }
                player.getUpdateState().setAnimation(899);
                Player player6 = player;
                player6.packetSender.sendSoundEffect(469, 1, 0);
                player.setActionLocked(true);
                player.setActiveCycleEvent(new SmeltingTask(value6, player, index, itemStack, enabled, itemStack2, selectedSmithingBarItemId, itemStack3, value3));
                CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
                return true;
            }
            ++index;
        }
        return false;
    }
}

