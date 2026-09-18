package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class DoricsQuest
extends QuestScript {
    public DoricsQuest(int value2) {
        super(4);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Doric who is North of", "Falador", "", "There aren't any requirements but Level 15 Mining will help"};
        }
        if (value2 == 2) {
            return new String[]{"I should bring these items to Doric at his house in", "North of Falador:", "6 x Clay", "4 x Copper ore", "2 x iron ore"};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "1300 Mining XP", "180 Coins", "Use of Doric's Anvils"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("1 Quest Point", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("1300 Mining XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("180 Coins", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("Use of Doric's Anvils", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(14, 1300.0);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 180));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1269);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    private static boolean hasAnyDoricMaterial(Player player) {
        return player.getInventoryManager().containsItem(434) || player.getInventoryManager().containsItem(436) || player.getInventoryManager().containsItem(440);
    }

    private static boolean hasAllDoricMaterials(Player player) {
        return player.getInventoryManager().containsItemAmount(434, 6) && player.getInventoryManager().containsItemAmount(436, 4) && player.getInventoryManager().containsItemAmount(440, 2);
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 284) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello traveller, what brings you to my humble smithy?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showFiveOptions("I wanted to use your anvils.", "I want to use your whetstone.", "Mind your own business, shortstuff!", "I was just checking out the landscape.", "What do you make here?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I wanted to use your anvils.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcFourLineDialogue("My anvils get enough work with my own use. I make", "pickaxes, and it takes a lot of hard work. If you could", "get me some more materials, then I could let you use", "them.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showTwoOptions("Yes, I will get you the materials.", "No, hitting rocks is for the boring people, sorry.");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes, I will get you the materials.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 7) {
                    player.getDialogueManager().showNpcFourLineDialogue("Clay is what I use more than anything, to make casts.", "Could you get me 6 clay, 4 copper ore, and 2 iron ore,", "please? I could pay a little, and let you use my anvils.", "Take this pickaxe with you just in case you need it.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1265, 1));
                    player.getDialogueManager().showTwoOptions("Where can I find those?", "Certainly, I'll be right back!");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where can I find those?", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You'll be able to find all those ores in the rocks just", "inside the Dwarven Mine. Head east from here and", "you'll find the entrance in the side of Ice Mountain.", 591);
                    if (player.getSkillManager().getBaseLevel(14) >= 15) {
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showPlayerOneLineDialogue("But I'm not a good enough miner to get iron ore.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcFourLineDialogue("Oh well, you could practice mining until you can. Can't", "beat a bit of mining - it's a useful skill. Failing that, you", "might be able to find a more experienced adventurer to", "buy the iron ore off.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Have you got my materials yet, traveller?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (!DoricsQuest.hasAnyDoricMaterial(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("No, I have none of them yet.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    if (DoricsQuest.hasAnyDoricMaterial(player) && !DoricsQuest.hasAllDoricMaterials(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have found some of the things you asked for.", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    if (DoricsQuest.hasAllDoricMaterials(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have everything you need.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Great, but I'll need the other materials as well.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Many thanks. Pass them here, please. I can spare you", "some coins for your trouble, and please use my anvils", "any time you want.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showItemMessage("You hand the clay, copper, and iron to Doric.", new ItemStack(436, 1));
                    return true;
                }
                if (value2 == 6 && DoricsQuest.hasAllDoricMaterials(player)) {
                    player.getInventoryManager().removeItem(new ItemStack(434, 6));
                    player.getInventoryManager().removeItem(new ItemStack(436, 4));
                    player.getInventoryManager().removeItem(new ItemStack(440, 2));
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

