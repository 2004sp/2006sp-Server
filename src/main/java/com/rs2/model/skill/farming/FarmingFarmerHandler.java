package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.AllotmentCropDefinition;
import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.BushDefinition;
import com.rs2.model.skill.farming.BushPatch;
import com.rs2.model.skill.farming.FarmedTreeDefinition;
import com.rs2.model.skill.farming.FruitTreeDefinition;
import com.rs2.model.skill.farming.FruitTreePatch;
import com.rs2.model.skill.farming.HopsDefinition;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.TreePatch;
import com.rs2.util.GameUtil;

public final class FarmingFarmerHandler {
    private static String[][] adviceMessages = new String[][]{{"You don't have to buy all your plantpots you know,", "you can make them yourself on a pottery wheel. If", "you are a good enough craftsman, that is."}, {"Don't just throw away your weeds after you've", "raked a patch - put them in a compost bin and", "make some compost."}, {"Tree seeds must be grown in a plantpot of soil", "into a tree sapling, and then transferred to a", "tree patch to continue growing to adulthood."}, {"You can put up to ten potatoes, cabbages, or", "onions in vegetable sacks, although you can't", "have a mix in the same sack."}, {"You can buy all the farming tools from farming", "shops which can be found close to the allotments"}, {"You can fill plantpots with soil from Farming", "patches, if you have a gardening trowel."}, {"If you want to make your own sacks and baskets", "you'll need to use the loom that's near the", "Farming shop in Falador."}, {"Bittercap mushrooms can only be grown in special", "patches in Morytania, near the Mort Myre swamp."}, {"Applying compost to a patch will not only reduce", "the chance that your crops will get diseased, but", "you will also grow more crops to harvest."}, {"Hops are good for brewing ales. I believe there", "is a brewery up in Keldagrim somewhere."}};

    public static void handlePatchProtectionDialogue(Player player, int value4, String text2, int value22, int value32) {
        int index = 0;
        int[] integerValues = new int[2];
        if (value32 == 1) {
            if (text2 == "allotment") {
                index = (Integer)AllotmentPatch.getIndexesForObjectId(value22).get(value4);
                if (AllotmentCropDefinition.forSeedId(player.getAllotmentPatchManager().cropIds[index]) != null) {
                    integerValues = AllotmentCropDefinition.forSeedId(player.getAllotmentPatchManager().cropIds[index]).getProtectionPayment();
                }
                if (player.getAllotmentPatchManager().growthStages[index] <= 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am sorry but you have no crops growing in this patch.", 595);
                    player.getDialogueManager().finishDialogue();
                } else if (player.getAllotmentPatchManager().protectionFlags[index]) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am already watching over your plants.", 595);
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getDialogueManager().showNpcOneLineDialogue("If you like, but I want " + integerValues[1] + " " + FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()) + (FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()).endsWith("s") ? "" : "s") + " for that.", 602);
                    player.getDialogueManager().setNextDialogueStep(18);
                    player.setSelectedSkillItemId(value4);
                }
            }
            if (text2 == "bushes") {
                index = BushPatch.forObjectId(value22).getIndex();
                if (BushDefinition.forSeedId(player.getBushPatchManager().cropIds[index]) != null) {
                    integerValues = BushDefinition.forSeedId(player.getBushPatchManager().cropIds[index]).getProtectionPayment();
                }
                if (player.getBushPatchManager().growthStages[index] <= 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am sorry but you have no crops growing in this patch.", 595);
                    player.getDialogueManager().finishDialogue();
                } else if (player.getBushPatchManager().protectionFlags[index]) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am already watching over your plants.", 595);
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getDialogueManager().showNpcOneLineDialogue("If you like, but I want " + integerValues[1] + " " + FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()) + (FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()).endsWith("s") ? "" : "s") + " for that.", 602);
                    player.getDialogueManager().setNextDialogueStep(18);
                    player.setSelectedSkillItemId(value4);
                }
            }
            if (text2 == "fruitTree") {
                index = FruitTreePatch.forObjectId(value22).getIndex();
                if (FruitTreeDefinition.forSaplingId(player.getFruitTreePatchManager().treeIds[index]) != null) {
                    integerValues = FruitTreeDefinition.forSaplingId(player.getFruitTreePatchManager().treeIds[index]).getProtectionPayment();
                }
                if (player.getFruitTreePatchManager().growthStages[index] <= 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am sorry but you have no crops growing in this patch.", 595);
                    player.getDialogueManager().finishDialogue();
                } else if (player.getFruitTreePatchManager().protectionFlags[index]) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am already watching over your plants.", 595);
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getDialogueManager().showNpcOneLineDialogue("If you like, but I want " + integerValues[1] + " " + FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()) + (FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()).endsWith("s") ? "" : "s") + " for that.", 602);
                    player.getDialogueManager().setNextDialogueStep(18);
                    player.setSelectedSkillItemId(value4);
                }
            }
            if (text2 == "hops") {
                index = HopsPatch.forObjectId(value22).getIndex();
                if (HopsDefinition.forSeedId(player.getHopsPatchManager().cropIds[index]) != null) {
                    integerValues = HopsDefinition.forSeedId(player.getHopsPatchManager().cropIds[index]).getProtectionPayment();
                }
                if (player.getHopsPatchManager().growthStages[index] <= 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am sorry but you have no crops growing in this patch.", 595);
                    player.getDialogueManager().finishDialogue();
                } else if (player.getHopsPatchManager().protectionFlags[index]) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am already watching over your plants.", 595);
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getDialogueManager().showNpcOneLineDialogue("If you like, but I want " + integerValues[1] + " " + FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()) + (FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()).endsWith("s") ? "" : "s") + " for that.", 602);
                    player.getDialogueManager().setNextDialogueStep(18);
                    player.setSelectedSkillItemId(value4);
                }
            }
            if (text2 == "tree") {
                index = TreePatch.forObjectId(value22).getIndex();
                if (FarmedTreeDefinition.forSaplingId(player.getTreePatchManager().treeIds[index]) != null) {
                    integerValues = FarmedTreeDefinition.forSaplingId(player.getTreePatchManager().treeIds[index]).getProtectionPayment();
                }
                if (player.getTreePatchManager().growthStages[index] <= 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am sorry but you have no crops growing in this patch.", 595);
                    player.getDialogueManager().finishDialogue();
                    return;
                }
                if (player.getTreePatchManager().protectionFlags[index]) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am already watching over your plants.", 595);
                    player.getDialogueManager().finishDialogue();
                    return;
                }
                player.getDialogueManager().showNpcOneLineDialogue("If you like, but I want " + integerValues[1] + " " + FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()) + (FarmingFarmerHandler.formatPaymentItemName(ItemDefinition.forId(integerValues[0]).getName().toLowerCase()).endsWith("s") ? "" : "s") + " for that.", 602);
                player.getDialogueManager().setNextDialogueStep(18);
                player.setSelectedSkillItemId(value4);
                return;
            }
        } else if (value32 == 2) {
            if (text2 == "allotment") {
                index = (Integer)AllotmentPatch.getIndexesForObjectId(value22).get(value4);
                integerValues = AllotmentCropDefinition.forSeedId(player.getAllotmentPatchManager().cropIds[index]).getProtectionPayment();
            }
            if (text2 == "bushes") {
                index = BushPatch.forObjectId(value22).getIndex();
                integerValues = BushDefinition.forSeedId(player.getBushPatchManager().cropIds[index]).getProtectionPayment();
            }
            if (text2 == "fruitTree") {
                index = FruitTreePatch.forObjectId(value22).getIndex();
                integerValues = FruitTreeDefinition.forSaplingId(player.getFruitTreePatchManager().treeIds[index]).getProtectionPayment();
            }
            if (text2 == "hops") {
                index = HopsPatch.forObjectId(value22).getIndex();
                integerValues = HopsDefinition.forSeedId(player.getHopsPatchManager().cropIds[index]).getProtectionPayment();
            }
            if (text2 == "tree") {
                index = TreePatch.forObjectId(value22).getIndex();
                integerValues = FarmedTreeDefinition.forSaplingId(player.getTreePatchManager().treeIds[index]).getProtectionPayment();
            }
            if (player.getInventoryManager().getItemAmount(integerValues[0]) < integerValues[1]) {
                player.getDialogueManager().showNpcTwoLineDialogue("Sorry, but you do not have the required items", "I need, for letting me take care of this patch", 595);
                player.getDialogueManager().finishDialogue();
                return;
            }
            if (text2 == "allotment") {
                player.getAllotmentPatchManager().protectionFlags[index] = true;
            }
            if (text2 == "bushes") {
                player.getBushPatchManager().protectionFlags[index] = true;
            }
            if (text2 == "fruitTree") {
                player.getFruitTreePatchManager().protectionFlags[index] = true;
            }
            if (text2 == "hops") {
                player.getHopsPatchManager().protectionFlags[index] = true;
            }
            if (text2 == "tree") {
                player.getTreePatchManager().protectionFlags[index] = true;
            }
            player.getInventoryManager().removeItem(new ItemStack(integerValues[0], integerValues[1]));
            player.getDialogueManager().showNpcThreeLineDialogue("Here you go, I will be taking care of this patch", "as soon as it become diseased, I will cure it", "so you don't have to worry about it.", 588);
            player.getDialogueManager().finishDialogue();
        }
    }

    public static void chopTreeForFee(Player player, int value2) {
        int index = TreePatch.forObjectId(value2).getIndex();
        if (player.getTreePatchManager().growthStages[index] < 3) {
            DialogueManager.continueDialogue(player, value2, 15, 0);
            return;
        }
        if (player.getInventoryManager().getItemAmount(995) < 200) {
            player.getDialogueManager().showNpcTwoLineDialogue("I am sorry, but you do not have enough money", "to pay me to chop down this tree.", 595);
            player.getDialogueManager().finishDialogue();
            return;
        }
        player.getInventoryManager().removeItem(new ItemStack(995, 200));
        player.getDialogueManager().showNpcTwoLineDialogue("There you go, I have chopped down your tree but I am ", "keeping the logs and roots as compensation.", 595);
        player.getTreePatchManager().resetPatch(index);
        player.getTreePatchManager().growthStages[index] = 3;
        player.getTreePatchManager().lastUpdateTicks[index] = Server.getElapsedMinutes();
        player.getTreePatchManager().refreshConfig();
        player.getDialogueManager().finishDialogue();
    }

    public static void showRandomFarmingAdvice(Player player) {
        player.getDialogueManager().showNpcDialogue(adviceMessages[GameUtil.randomInclusive(9)], 588);
        player.getDialogueManager().finishDialogue();
    }

    private static String formatPaymentItemName(String itemId) {
        if (itemId.contains("(5)")) {
            return "baskets of " + itemId.replace("(5)", "");
        }
        if (itemId.contains("(10)")) {
            return "sacks of " + itemId.replace("(10)", "");
        }
        return itemId;
    }
}

