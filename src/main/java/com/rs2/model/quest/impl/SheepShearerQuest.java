package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class SheepShearerQuest
extends QuestScript {
    public SheepShearerQuest(int value2) {
        super(15);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value3) {
        if (value3 == 0) {
            return new String[]{"I can start this quest by speaking to Farmer Fred at his", "farm just a little way North West of Lumbridge"};
        }
        if (value3 >= 2 && value3 < 22) {
            int value2 = 22 - value3;
            String[] stringValues = new String[]{"I should bring these items to the Farmer Fred at his", "farm just a little way North West of Lumbridge:", String.valueOf(value2) + " x Ball of wool"};
            return stringValues;
        }
        if (value3 == 22) {
            return new String[]{"I should talk to Farmer Fred at his farm to", "finish this quest."};
        }
        if (value3 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "150 Crafting XP", "60 Coins"};
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
        player2.packetSender.sendInterfaceText("150 Crafting XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("60 Coins", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(12, 150.0);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 60));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1735);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 758) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("What are you doing on my land? You're not the one", "who keeps leaving all my gates open and letting out all", "my sheep are you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("I'm looking for a quest.", "I'm looking for something to kill.", "I'm lost.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm looking for a quest.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You're after a quest, you say? Actually I could do with", "a bit of help.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("My sheep are getting mighty woolly. I'd be much", "obliged if you could shear them. And while you're at it", "spin the wool for me too.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, that's it. Bring me 20 balls of wool. And I'm sure", "I could sort out some sort of payment. Of course,", "there's the small matter of The Thing.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showThreeOptions("Yes okay. I can do that.", "That doesn't sound a very exciting quest.", "What do you mean, The Thing?");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes okay. I can do that.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(7);
                    return true;
                }
            }
            if (value42 >= 2) {
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Good! Now one more thing, do you actually know how", "to shear a sheep?", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showTwoOptions("Of course!", "Err. No, I don't know actually.");
                    return true;
                }
                if (value2 == 12) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Of course!", 591);
                        player.getDialogueManager().setNextDialogueStep(23);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Err. No, I don't know actually..", 591);
                        player.getDialogueManager().setNextDialogueStep(13);
                        return true;
                    }
                }
                if (value2 == 13) {
                    if (player.getInventoryManager().containsItem(1735)) {
                        player.getDialogueManager().showNpcThreeLineDialogue("Well, you're halfway there already! You have a set of", "shears in your inventory. Just use those on a Sheep to", "shear it.", 591);
                    } else {
                        player.getDialogueManager().showNpcTwoLineDialogue("Well, you have to find a set of shears first.", "Then just use those on a Sheep to shear it.", 591);
                    }
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showPlayerOneLineDialogue("That's all I have to do?", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well once you've collected some wool you'll need to spin", "it into balls.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcOneLineDialogue("Do you know how to spin wool?", 591);
                    return true;
                }
                if (value2 == 23) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good, do you know how to spin wool?", 591);
                    player.getDialogueManager().setNextDialogueStep(17);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showTwoOptions("I don't know how to spin wool, sorry.", "I'm something of an expert actually!");
                    return true;
                }
                if (value2 == 18) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I don't know how to spin wool, sorry.", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcOneLineDialogue("Don't worry, it's quite simple!", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The nearest Spinning Wheel can be found on the first", "floor of Lumbridge Castle.", 591);
                    return true;
                }
                if (value2 == 21) {
                    player.getDialogueManager().showNpcOneLineDialogue("To get to Lumbridge Castle just follow the road east.", 591);
                    return true;
                }
                if (value2 == 22) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thank you!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("What are you doing on my land?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("I'm back!", "Fred! Fred! I've seen The Thing!");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm back!", 591);
                        if (value42 < 22) {
                            player.getDialogueManager().setNextDialogueStep(4);
                        } else {
                            player.getDialogueManager().setNextDialogueStep(8);
                        }
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("How are you doing getting those balls of wool?", 591);
                    return true;
                }
                if (value2 == 5) {
                    if (player.getInventoryManager().containsItem(1759)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have some.", 591);
                        return true;
                    }
                    player.getDialogueManager().showPlayerOneLineDialogue("I have none.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("Give 'em here then.", 591);
                    return true;
                }
                if (value2 == 7) {
                    npcId = player.getInventoryManager().getItemAmount(1759);
                    npcId = npcId < (value2 = 22 - value42) ? npcId : value2;
                    player.addQuestState(this.getQuestId(), npcId);
                    if ((value42 += npcId) < 22) {
                        player.getDialogueManager().showPlayerOneLineDialogue("That's all I have for now.", 591);
                        player.getInventoryManager().removeItem(new ItemStack(1759, npcId));
                        player.getDialogueManager().setNextDialogueStep(24);
                    }
                    if (value42 == 22) {
                        player.getDialogueManager().showPlayerOneLineDialogue("That's the last of them.", 591);
                        player.getInventoryManager().removeItem(new ItemStack(1759, npcId));
                    }
                    return true;
                }
                if (value2 == 24) {
                    npcId = 22 - value42;
                    player.getDialogueManager().showNpcOneLineDialogue("You still need to bring me " + npcId + " more.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("I guess I'd better pay you then.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        return false;
    }
}

