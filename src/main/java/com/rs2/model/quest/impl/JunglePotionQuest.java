package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.JunglePotionHerbSearchEvent;
import com.rs2.model.task.CycleEventHandler;

public final class JunglePotionQuest
extends QuestScript {
    public JunglePotionQuest(int value2) {
        super(56);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Trufitus Shakaya", "who lives in the main hut in Tai Bwo Wannai", "village on the island of Karamja."};
        }
        if (value2 == 2) {
            return new String[]{"I should go look for snake weed, which can be found near", "the vines south-west of the village."};
        }
        if (value2 == 3) {
            return new String[]{"I should go look for ardrigal, which can be found from the", "peninsula east of the village."};
        }
        if (value2 == 4) {
            return new String[]{"I should go look for sito foil, which can be found where", "ground is blackened by living flame."};
        }
        if (value2 == 5) {
            return new String[]{"I should go look for volencia moss, which can be found near", "the rocks south-east of the village."};
        }
        if (value2 == 6) {
            return new String[]{"I should go look for rogues purse, which can be found in", "caverns located in the northern part of the island."};
        }
        if (value2 == 7) {
            return new String[]{"I should speak with Trufitus to finish this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "775 Herblore  XP"};
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
        player2.packetSender.sendInterfaceText("775 Herblore  XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(15, 775.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1528);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2585 && value52 == 2830 && value62 == 9522 && value8 == 1) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("You attempt to climb the rocks back out.");
                return true;
            }
            if (value32 == 2) {
                player.moveTo(new Position(2823, 3120, 0));
                return false;
            }
        }
        if (value22 == 2584 && value52 == 2824 && value62 == 3118 && value8 == 2) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("You search the rocks... You find an entrance into some caves.");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showTwoOptionsWithTitle("Would you like to enter the caves?", "Yes, I'll enter the cave.", "No thanks, I'll give it a miss.");
                return true;
            }
            if (value32 == 3) {
                if (value42 == 1) {
                    player.getDialogueManager().showTwoLineStatement("You decide to enter the caves. You climb down several steep rock", "faces into the cavern below.");
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value32 == 4) {
                player.moveTo(new Position(2830, 9520, 0));
                return false;
            }
        }
        return false;
    }

    private void startDelayedHerbSearch(Player player, int delayTicks, int value22, int value32, int value42, boolean delayTicks2) {
        int value = player.nextActionSequence();
        player.resetAnimation();
        player.getUpdateState().setAnimation(832);
        player.setActiveCycleEvent(new JunglePotionHerbSearchEvent(this, player, value, value32, value42, value22, delayTicks, true));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 3);
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2575 && value42 == 2) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You search the vine...");
            this.startDelayedHerbSearch(player, 1525, objectId, value2, value32, true);
            return true;
        }
        if (objectId == 2577 && value42 == 3) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You search the palm...");
            player.getDialogueManager().showItemMessage("You find a herb.", new ItemStack(1527, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(1527, 1));
            return true;
        }
        if (objectId == 2579 && value42 == 4) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("You search the scorched earth...");
            player.getDialogueManager().showItemMessage("You find a herb.", new ItemStack(1529, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(1529, 1));
            return true;
        }
        if (objectId == 2581 && value42 == 5) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You search the rock...");
            player.getDialogueManager().showItemMessage("You find a herb.", new ItemStack(1531, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(1531, 1));
            return true;
        }
        if (objectId == 2583 && value42 == 6) {
            Player player6 = player;
            player6.packetSender.sendGameMessage("You search the wall...");
            this.startDelayedHerbSearch(player, 1533, objectId, value2, value32, true);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 740) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Greetings Bwana! I am Trufitus Shakaya of the Tai", "Bwo Wannai village.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Welcome to our humble village.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showThreeOptions("What does Bwana mean?", "Tai Bwo Wannai? What does that mean?", "It's a nice village, where is everyone?");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("It's a nice village, where is everyone?", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("My people are afraid to stay in the village. They have", "returned to the jungle. I need to commune with the", "gods to see what fate befalls us.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("You may be able to help with this.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showTwoOptions("Me? How can I help?", "I am sorry, but I am very busy.");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Me? How can I help?", 591);
                        player.getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I need to make a special brew! A potion that helps me", "to commune with the gods. For this potion, I need very", "special herbs, that are only found in the deep jungle.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcFourLineDialogue("I can only guide you so far as the herbs are not easy", "to find. With some luck, you will find each herb in turn", "and bring it to me. I will then give you details of where", "to find the next herb.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("In return for this great favour I will give you training", "in Herblore.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showTwoOptions("Hmmm, sounds difficult, I don't know if I am ready for the challenge.", "It sounds like just the challenge for me.");
                    return true;
                }
                if (value2 == 13) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("It sounds like just the challenge for me. And it would", "make a nice break from killing things!", 591);
                        player.getDialogueManager().setNextDialogueStep(14);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcTwoLineDialogue("That is excellent Bwana! The first herb that you need", "to gather is called", 591);
                    this.startQuest(player);
                    return true;
                }
            }
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello Bwana, do you have the Snake Weed?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("Of course!", "Not yet, sorry, what's the clue again?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Of course!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Not yet, sorry, what's the clue again?", 591);
                        player.getDialogueManager().setNextDialogueStep(16);
                        return true;
                    }
                }
                if (value2 == 4 && player.getInventoryManager().containsItemAmount(1526, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1526, 1));
                    player.getDialogueManager().showItemMessage("You give the Snake Weed to Trufitus.", new ItemStack(1526, 1));
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcOneLineDialogue("Snake Weed.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcOneLineDialogue("It grows near vines in an area to the south west where", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcOneLineDialogue("the ground turns soft and the water kisses your feet.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello Bwana, have you been able to get the Ardrigal?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("Of course!", "Not yet, sorry, what's the clue again?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Of course!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Not yet, sorry, what's the clue again?", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                }
                if (value2 == 4 && player.getInventoryManager().containsItemAmount(1528, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1528, 1));
                    player.getDialogueManager().showItemMessage("You give the Ardrigal to Trufitus.", new ItemStack(1528, 1));
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Great, you have the Snake Weed! Many thanks. Ok,", "the next herb is called Ardrigal. It is related to the palm", "and grows to the east in its brother's shady profusion.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("To the east you will find a small peninsula, it is just", "after the cliffs come down to meet the sands, here is", "where you should search for it.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hello Bwana, have you been successful in getting", "the Sito Foil?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("Of course!", "Not yet, sorry, what's the clue again?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Of course!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Not yet, sorry, what's the clue again?", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                }
                if (value2 == 4 && player.getInventoryManager().containsItemAmount(1530, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1530, 1));
                    player.getDialogueManager().showItemMessage("You give the Sito Foil to Trufitus.", new ItemStack(1530, 1));
                    player.setQuestState(this.getQuestId(), 5);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Great, you have the Ardrigal! Many thanks.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You are doing well Bwana. The next herb is called Sito", "Foil, and it grows best where the ground has been", "blackened by the living flame.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 5) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Greetings Bwana, have you been successful in getting", "the Volencia Moss?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("Of course!", "Not yet, sorry, what's the clue again?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Of course!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Not yet, sorry, what's the clue again?", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                }
                if (value2 == 4 && player.getInventoryManager().containsItemAmount(1532, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1532, 1));
                    player.getDialogueManager().showItemMessage("You give the Volencia Moss to Trufitus.", new ItemStack(1532, 1));
                    player.setQuestState(this.getQuestId(), 6);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well done Bwana, just two more herbs to collect.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The next herb is called Volencia Moss. It clings to", "rocks for its existence. It is difficult to see, so you", "must search for it well.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("It prefers rocks of high metal content and a frequently", "disturbed environment. There is some, I believe to the", "south east of this village.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 6) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Greetings Bwana, have you been successful in getting", "the Rogues Purse?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("Of course!", "Not yet, sorry, what's the clue again?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Of course!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Not yet, sorry, what's the clue again?", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                }
                if (value2 == 4 && player.getInventoryManager().containsItemAmount(1534, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1534, 1));
                    player.getDialogueManager().showItemMessage("You give the Rogues Purse to Trufitus.", new ItemStack(1534, 1));
                    player.setQuestState(this.getQuestId(), 7);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah Volencia Moss, beautiful. One final herb and the", "potion will be complete.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("This is the most difficult to find as it inhabits the", "darkness of the underground. It is called Rogues", "Purse, and is only to be found in caverns", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("in the northern part of this island. A secret entrance to", "the caverns is set into the northern cliffs of this land.", "Take care Bwana as it may be dangerous.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 7) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Most excellent Bwana! You have returned all the herbs", "to me and, I can finish the preparations for the potion,", "and at last divine with the gods.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Many blessings on you! I must now prepare, please", "excuse me while I make the arrangements.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoLineStatement("Trufitus shows you some techniques in Herblore. You gain some", "experience in Herblore.");
                    return true;
                }
                if (value2 == 4) {
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().markDialogueInactive();
                    return true;
                }
            }
        }
        return false;
    }
}

