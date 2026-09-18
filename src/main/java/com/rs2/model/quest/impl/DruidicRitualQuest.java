package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class DruidicRitualQuest
extends QuestScript {
    public DruidicRitualQuest(int value2) {
        super(29);
        super.setQuestPointReward(4);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Kaqemeex who is at", "the Druids Circle just North of Taverley."};
        }
        if (value2 == 2) {
            return new String[]{"I should go and speak with Sanfew, who can be found in", "Taverley."};
        }
        if (value2 == 3) {
            return new String[]{"I should go and dip the following meats in the", "cauldron of thunder, located somewhere underground", "south of Taverley. And then bring the meats back to Sanfew.", "Raw bear meat", "Raw rat meat", "Raw beef", "Raw chicken"};
        }
        if (value2 == 4) {
            return new String[]{"I should go and speak with Kaqemeex to finish this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "4 Quest Points", "Herblore skill", "250 Herblore XP"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("4 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("Herblore skill", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("250 Herblore XP", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(15, 250.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 251);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    private static boolean hasAnyEnchantedMeat(Player player) {
        return player.getInventoryManager().containsItem(524) || player.getInventoryManager().containsItem(523) || player.getInventoryManager().containsItem(522) || player.getInventoryManager().containsItem(525);
    }

    private static boolean hasAllEnchantedMeats(Player player) {
        return player.getInventoryManager().containsItem(524) && player.getInventoryManager().containsItem(523) && player.getInventoryManager().containsItem(522) && player.getInventoryManager().containsItem(525);
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (player.getQuestState(this.getQuestId()) == 3 && value2 == 2142) {
            if (objectId == 2136 && player.getInventoryManager().containsItem(2136)) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("You dip the raw bear meat in the cauldron.");
                player.getInventoryManager().removeItem(new ItemStack(2136, 1));
                player.getInventoryManager().addItem(new ItemStack(524, 1));
                return true;
            }
            if (objectId == 2134 && player.getInventoryManager().containsItem(2134)) {
                Player player3 = player;
                player3.packetSender.sendGameMessage("You dip the raw rat meat in the cauldron.");
                player.getInventoryManager().removeItem(new ItemStack(2134, 1));
                player.getInventoryManager().addItem(new ItemStack(523, 1));
                return true;
            }
            if (objectId == 2132 && player.getInventoryManager().containsItem(2132)) {
                Player player4 = player;
                player4.packetSender.sendGameMessage("You dip the raw beef in the cauldron.");
                player.getInventoryManager().removeItem(new ItemStack(2132, 1));
                player.getInventoryManager().addItem(new ItemStack(522, 1));
                return true;
            }
            if (objectId == 2138 && player.getInventoryManager().containsItem(2138)) {
                Player player5 = player;
                player5.packetSender.sendGameMessage("You dip the raw chicken in the cauldron.");
                player.getInventoryManager().removeItem(new ItemStack(2138, 1));
                player.getInventoryManager().addItem(new ItemStack(525, 1));
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 455) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello there.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("What brings you to our holy monument?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showThreeOptions("Who are you?", "I'm in search of a quest.", "Did you build this?");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm in search of a quest.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Hmm. I think I may have a worthwhile quest for you", "actually. I don't know if you are familiar with the stone", "circle south of Varrock or not, but...", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("That used to be OUR stone circle. Unfortunately,", "many many years ago, dark wizards cast a wicked spell", "upon it so that they could corrupt its power for their", "own and evil ends.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcFourLineDialogue("When they cursed the rocks for their rituals they made", "them useless to us and our magics. We require a brave", "adventurer to go on a quest for us to help purify the", "circle of Varrock.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showThreeOptions("Ok, I will try and help.", "No, that doesn't sound very interesting.", "So... is there anything in this for me?");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will try and help.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 10) {
                    player.getDialogueManager().showNpcFourLineDialogue("Excellent. Go to the village south of this place and speak", "to my fellow Sanfew who is working on the purification", "ritual. He knows better than I what is required to", "complete it.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Will do.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello there.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcFourLineDialogue("I have word from Sanfew that you have been very", "helpful in assisting him with his preparations for the", "purification ritual. As promised I will now teach you the", "ancient arts of Herblore.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I will now explain the fundamentals of Herblore:", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Herblore is the skill of working with herbs and other", "ingredients, to make useful potions and poison.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("First you will need a vial, which can be found or made", "with the crafting skill.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Then you must gather the herbs needed to make the", "potion you want.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Refer to the Council's instructions in the Skills section", "of the website for the items needed to make a particular", "kind of potion.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You must fill your vial with water and add the", "ingredients you need. There are normally 2 ingredients", "to each type of potion.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Bear in mind, you must first identify each herb, to see", "what it is.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You may also have to grind some herbs, before you can", "use them. You will need a pestle and mortar in order", "to do this.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Herbs can be found on the ground, and are also", "dropped by some monsters when you kill them.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Let's try an example Attack potion: The first ingredient", "is Guam leaf, the next is Eye of Newt.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Mix these in your water-filled vial, and you will produce", "an Attack potion.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("Drink this potion to increase your Attack level.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Different potions also require different Herblore levels", "before you can make them.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Once again, check the instructions found on the", "Council's website for the levels needed to make a", "particular potion.", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Good luck with your Herblore practices, Good day", "Adventurer.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thanks for your help.", 591);
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 454) {
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("What can I do for you young 'un?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("I've been sent to help purify the Varrock stone circle.", "Actually, I don't need to speak to you.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("I've been sent to assist you with the ritual to purify the", "Varrockian stone circle.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well, what I'm struggling with right now is the meats", "needed for the potion to honour Guthix. I need the raw", "meat of four different animals for it, but not just any", "old meats will do.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Each meat has to be dipped individually into the", "Cauldron of Thunder for it to work correctly.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showTwoOptions("Where can I find this cauldron?", "Ok, I'll do that then.");
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where can I find this cauldron?", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll do that then.", 591);
                    player.getDialogueManager().finishDialogue();
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcFourLineDialogue("It is located somewhere in the mysterious underground", "halls which are located somewhere in the woods just", "South of here. They are too dangerous for me to go", "myself however.", 591);
                    player.getDialogueManager().finishDialogue();
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Did you bring me the required ingredients for the potion?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (!DruidicRitualQuest.hasAnyEnchantedMeat(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("No, I have none of them yet.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    if (DruidicRitualQuest.hasAnyEnchantedMeat(player) && !DruidicRitualQuest.hasAllEnchantedMeats(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have some of the things you asked for.", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                    }
                    if (DruidicRitualQuest.hasAllEnchantedMeats(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes, I have all four now!", 591);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well hand 'em over then lad!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Thank you so much adventurer! These meats will allow", "our potion to honour Guthix to be completed, and bring", "one step closer to reclaiming our stone circle!", 591);
                    return true;
                }
                if (value2 == 5 && DruidicRitualQuest.hasAllEnchantedMeats(player)) {
                    player.getInventoryManager().removeItem(new ItemStack(524, 1));
                    player.getInventoryManager().removeItem(new ItemStack(523, 1));
                    player.getInventoryManager().removeItem(new ItemStack(522, 1));
                    player.getInventoryManager().removeItem(new ItemStack(525, 1));
                    player.getDialogueManager().showNpcThreeLineDialogue("Now go and talk to Kaqemeex and he will introduce", "you to the wonderful world of herblore and potion", "making!", 591);
                    player.getDialogueManager().finishDialogue();
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("Great, but I'll need the other ingredients as well.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

