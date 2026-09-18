package com.rs2.model.skill.crafting;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.JewelleryCraftingData;
import com.rs2.model.skill.crafting.JewelleryCraftingTask;
import com.rs2.model.skill.crafting.JewelleryDefinition;
import com.rs2.model.task.CycleEventHandler;
import java.util.HashMap;

public final class JewelleryCraftingHandler
extends JewelleryCraftingData {
    private static HashMap definitionsByMaterialItemId = new HashMap();

    static {
        JewelleryDefinition[] jewelleryDefinitionArray = JewelleryDefinition.values();
        int length = jewelleryDefinitionArray.length;
        int index = 0;
        while (index < length) {
            JewelleryDefinition jewelleryDefinition = jewelleryDefinitionArray[index];
            definitionsByMaterialItemId.put(JewelleryDefinition.getRecipeData(jewelleryDefinition)[0], jewelleryDefinition);
            ++index;
        }
    }

    public static JewelleryDefinition forMaterialItemId(int itemId) {
        return (JewelleryDefinition)((Object)definitionsByMaterialItemId.get(itemId));
    }

    private static void sendMouldOptions(Player player, int value3) {
        Player player2;
        int value2;
        if (!ServerSettings.craftingEnabled) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        value2 = value3 == 1592 ? 0 : (value3 == 1597 ? 1 : (value3 == 1595 ? 2 : -1));
        if (value2 < 0) {
            return;
        }
        if (player.getInventoryManager().getContainer().containsItem(value3)) {
            value3 = 0;
            while (value3 < JewelleryCraftingData.productsByJewelleryType[value2].length) {
                player2 = player;
                player2.packetSender.sendInterfaceSlotItem(value3, interfaceIdsByJewelleryType[value2][1], new ItemStack(JewelleryCraftingData.productsByJewelleryType[value2][value3], 1));
                ++value3;
            }
            player2 = player;
            player2.packetSender.sendInterfaceModel(interfaceIdsByJewelleryType[value2][0], 0, -1);
            player2 = player;
            player2.packetSender.sendInterfaceText("Choose an item to make.", interfaceIdsByJewelleryType[value2][1] - 3);
        } else {
            player2 = player;
            player2.packetSender.sendInterfaceModel(interfaceIdsByJewelleryType[value2][0], 120, 1595);
            player2 = player;
            player2.packetSender.sendInterfaceText(missingMouldMessages[value2], interfaceIdsByJewelleryType[value2][1] - 3);
            value3 = 0;
            while (value3 < JewelleryCraftingData.productsByJewelleryType[value2].length) {
                player2 = player;
                player2.packetSender.sendInterfaceSlotItem(value3, interfaceIdsByJewelleryType[value2][1], new ItemStack(0));
                ++value3;
            }
        }
        player2 = player;
        player2.packetSender.sendInterfaceText("What would you like to make?", 4226);
    }

    public static void openJewelleryCraftingInterface(Player player, int interfaceId) {
        if (!ServerSettings.craftingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        JewelleryCraftingHandler.sendMouldOptions(player, 1592);
        JewelleryCraftingHandler.sendMouldOptions(player, 1595);
        JewelleryCraftingHandler.sendMouldOptions(player, 1597);
        player.setSelectedSkillItemId(interfaceId);
        player.packetSender.showInterface(4161);
    }

    public static void startJewelleryCraftingTask(Player player, int value5, int value22, int value32) {
        if (JewelleryCraftingHandler.forMaterialItemId(value5) == null || value22 <= 0 || value32 < 0) {
            return;
        }
        if (!player.getInventoryManager().getContainer().containsItem(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(value5))[0])) {
            player.getDialogueManager().showOneLineStatement("You do not have the required items to do that.");
            return;
        }
        Player player2 = player;
        player2.packetSender.closeInterfaces();
        int value4 = player.nextActionSequence();
        player.setActiveCycleEvent(new JewelleryCraftingTask(value5, value22, value32, player, value4));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
    }

    public static void stringAmulet(Player player, int value2) {
        if (!player.getInventoryManager().getContainer().containsItem(1759) || !player.getInventoryManager().getContainer().containsItem(amuletStringingRecipes[value2][0])) {
            return;
        }
        if (!ServerSettings.craftingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        player.getInventoryManager().removeItem(new ItemStack(amuletStringingRecipes[value2][0], 1));
        player.getInventoryManager().removeItem(new ItemStack(1759, 1));
        player.getSkillManager().addExperience(12, 4.0);
        player.getInventoryManager().addItem(new ItemStack(amuletStringingRecipes[value2][1], 1));
        if (amuletStringingRecipes[value2][1] == 4021) {
            player.packetSender.sendGameMessage("You put some string on your amulet. It makes a slight 'Ook' sound.");
            return;
        }
        player.packetSender.sendGameMessage("You attach a string to the " + new ItemStack(amuletStringingRecipes[value2][0]).getDefinition().getName().toLowerCase() + ".");
    }
}

