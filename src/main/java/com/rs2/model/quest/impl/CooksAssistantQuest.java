package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class CooksAssistantQuest
extends QuestScript {
    public CooksAssistantQuest(int value2) {
        super(2);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value3) {
        if (value3 == 0) {
            return new String[]{"I can start this quest by speaking to the Cook in the", "Kitchen on the ground floor of Lumbridge Castle."};
        }
        if (value3 >= 2 && value3 < 9) {
            int value2 = value3 - 2;
            String[] stringValues = new String[]{"I should bring these items to the Cook in the", "Kitchen of Lumbridge Castle:", String.valueOf((value2 & 1) != 0 ? "@str@" : "") + "Bucket of milk", String.valueOf((value2 & 2) != 0 ? "@str@" : "") + "Pot of flour", String.valueOf((value2 & 4) != 0 ? "@str@" : "") + "Egg"};
            return stringValues;
        }
        if (value3 == 9) {
            return new String[]{"I should talk to the Cook in the Kitchen of Lumbridge", "Castle to finish this quest."};
        }
        if (value3 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "300 Cooking XP"};
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
        player2.packetSender.sendInterfaceText("300 Cooking XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(7, 300.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1891);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value5, int value32, int value42) {
        int value2 = value42 - 2;
        if (npcId == 3806 && value42 >= 2 && value42 < 9) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("Hello Adventurer. Welcome to Mill Lane Mill. Can I", "help you?", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showFourOptions("Who are you?", "What is this place?", "How do I mill flour?", "I'm fine, thanks.");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How do I mill flour?", 591);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcFourLineDialogue("Making flour is pretty easy. First of all you need to", "get some grain. You can pick some from wheat fields.", "There is one just outside the Mill, but there are many", "others scattered across RuneScape. Feel free to pick", 591);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("wheat from our field! There always seems to be plenty", "of wheat there.", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showPlayerOneLineDialogue("Then I bring my wheat here?", 591);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcFourLineDialogue("Yes, or one of the other mills in RuneScape. They all", "work the same way. Just take your grain to the top", "floor of the mill (up two ladders, there are three floors", "including this one) and then place some grain into the", 591);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showNpcOneLineDialogue("hopper.", 591);
                return true;
            }
            if (value5 == 9) {
                player.getDialogueManager().showNpcFourLineDialogue("Then you need to start the grinding process by pulling", "the hopper lever. You can add more grain, but each", "time you add grain you have to pull the hopper lever", "again.", 591);
                return true;
            }
            if (value5 == 10) {
                player.getDialogueManager().showPlayerOneLineDialogue("So where does the flour go then?", 591);
                return true;
            }
            if (value5 == 11) {
                player.getDialogueManager().showNpcThreeLineDialogue("The flour appears in this room here, you'll need a pot", "to put the flour into. One pot will hold the flour made", "by one load of grain", 591);
                return true;
            }
            if (value5 == 12) {
                player.getDialogueManager().showNpcFourLineDialogue("And that's it! You now have some pots of finely ground", "flour of the highest quality. Ideal for making tasty cakes", "or delicious bread. I'm not a cook so you'll have to ask a", "cook to find out how to bake things.", 591);
                return true;
            }
            if (value5 == 13) {
                player.getDialogueManager().showPlayerOneLineDialogue("Great! Thanks for your help.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 278) {
            if (value42 == 0) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("What am I to do?", 611);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showFourOptions("What's wrong?", "Can you make me a cake?", "You don't look very happy.", "Nice hat!");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("What's wrong?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Oh dear, oh dear, oh dear, I'm in a terrible terrible", "mess! It's the Duke's birthday today, and I should be", "making him a lovely big birthday cake.", 598);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("I've forgotten to buy the ingredients. I'll never get", "them in time now. He'll sack me! What will I do? I have", "four children and a goat to look after. Would you help", "me? Please?", 613);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showTwoOptions("I'm always happy to help a cook in distress.", "I can't right now, Maybe later.");
                    return true;
                }
                if (value5 == 7) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes, I'll help you.", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Oh thank you, thank you. I need milk, an egg and", "flour. I'd be very grateful if you can get them for me.", 590);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So where do I find these ingredients then?", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showFourOptions("Where do I find some flour?", "How about milk?", "And eggs? Where are they found?", "Actually, I know where to find this stuff.");
                    this.startQuest(player);
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
            }
            if (value42 >= 2 && value42 < 9) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("How are you getting on with finding the ingredients?", 610);
                    if ((value2 & 1) == 0 && player.getInventoryManager().containsItem(1927)) {
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if ((value2 & 2) == 0 && player.getInventoryManager().containsItem(1933)) {
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    if ((value2 & 4) == 0 && player.getInventoryManager().containsItem(1944)) {
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showFourOptions("Where do I find some flour?", "How about milk?", "And eggs? Where are they found?", "Actually, I know where to find this stuff.");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showNpcThreeLineDialogue("There is a Mill fairly close, Go North and then West.", "Mill Lane Mill is just off the road to Draynor. I", "usually get my flour from there.", 589);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showNpcTwoLineDialogue("There is a cattle field on the other side of the river,", "just across the road from the Groats' Farm.", 589);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showNpcTwoLineDialogue("I normally get my eggs from the Groats' farm, on the", "other side of the river.", 589);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    if (value32 == 4) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Actually, I know where to find this stuff.", 591);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                }
                if (value5 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Talk to Millie, she'll help, she's a lovely girl and a fine", "Miller..", 591);
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Talk to Gillie Groats, she looks after the Dairy cows -", "she'll tell you everything you need to know about", "milking cows!", 591);
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcOneLineDialogue("But any chicken should lay eggs.", 591);
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Here's a bucket of milk.", 591);
                    player.getInventoryManager().removeItem(new ItemStack(1927, 1));
                    player.addQuestState(this.getQuestId(), 1);
                    if ((++value2 & 2) == 0 && player.getInventoryManager().containsItem(1933)) {
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    if ((value2 & 4) == 0 && player.getInventoryManager().containsItem(1944)) {
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                    if ((value2 & 1) != 0 && (value2 & 2) != 0 && (value2 & 4) != 0) {
                        player.setQuestState(this.getQuestId(), 9);
                        player.getDialogueManager().setNextDialogueStep(1);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Here's a pot of flour.", 591);
                    player.getInventoryManager().removeItem(new ItemStack(1933, 1));
                    player.addQuestState(this.getQuestId(), 2);
                    if (((value2 += 2) & 4) == 0 && player.getInventoryManager().containsItem(1944)) {
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                    if ((value2 & 1) != 0 && (value2 & 2) != 0 && (value2 & 4) != 0) {
                        player.setQuestState(this.getQuestId(), 9);
                        player.getDialogueManager().setNextDialogueStep(1);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Here's a fresh egg.", 591);
                    player.getInventoryManager().removeItem(new ItemStack(1944, 1));
                    player.addQuestState(this.getQuestId(), 4);
                    if (((value2 += 4) & 1) != 0 && (value2 & 2) != 0 && (value2 & 4) != 0) {
                        player.setQuestState(this.getQuestId(), 9);
                        player.getDialogueManager().setNextDialogueStep(1);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 9) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You've brought me everything I need! I am saved!", "Thank you!", 590);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So do I get to go to the Duke's Party?", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm afraid not, only the big cheeses get to dine with the", "Duke.", 611);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Well, maybe one day I'll be important enough to sit on", "the Duke's table.", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Maybe, but I won't be holding my breath.", 588);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        return false;
    }
}

