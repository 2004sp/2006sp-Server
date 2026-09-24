package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class RuneMysteriesQuest
extends QuestScript {
    public RuneMysteriesQuest(int value2) {
        super(14);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Duke Horacio of ", "Lumbridge, upstairs in Lumbridge Castle."};
        }
        if (value2 == 2) {
            return new String[]{"I should bring the air talisman to head wizard at the", "Wizards' Tower, south-west of Lumbridge."};
        }
        if (value2 == 3) {
            return new String[]{"I should talk to head wizard at the Wizards' Tower", "to continue this quest."};
        }
        if (value2 == 4) {
            return new String[]{"I should deliver the package to Aubury, owner of the", "rune shop in Varrock."};
        }
        if (value2 == 5) {
            return new String[]{"I should talk to Aubury at Varrock rune shop", "to continue this quest."};
        }
        if (value2 == 6) {
            return new String[]{"I should bring the research notes to head wizard at the", "Wizards' Tower, south-west of Lumbridge."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "Runecrafting skill", "Air talisman"};
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
        player2.packetSender.sendInterfaceText("Runecrafting skill", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("Air talisman", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1438);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 741 && value42 == 0) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Greetings. Welcome to my castle.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showTwoOptions("Have you any quest for me?", "Where can I find money?");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Have you any quest for me?", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("Well, it's not really a quest but I recently discovered", "this strange talisman.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("It seems to be mystical and I have never seen anything", "like it before. Would you take it to the head wizard at", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcThreeLineDialogue("the Wizards' Tower for me? It's just south-west of here", "and should not take you very long at all. I would be", "awfully grateful.", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showTwoOptions("Sure, no problem.", "Not right now.");
                return true;
            }
            if (value2 == 8) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Sure, no problem.", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcTwoLineDialogue("Thank you very much, stranger. I am sure the head", "wizard will reward you for such an interesting find.", 591);
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showOneLineStatement("The Duke hands you an @dbl@air talisman.");
                return true;
            }
            if (value2 == 11) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1438, 1));
                this.startQuest(player);
                player.getDialogueManager().finishDialogue();
                player.packetSender.closeInterfaces();
                return false;
            }
        }
        if (npcId == 300) {
            if (value42 == 2) {
                if (!player.getInventoryManager().containsItem(1438)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Welcome adventurer, to the world renowned", "Wizards' Tower. How may I help you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("Nothing thanks. I'm just looking around.", "What are you doing down here?", "I'm looking for the head wizard.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm looking for the head wizard.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Oh, you are, are you?", "And just why would you be doing that?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("The Duke of Lumbridge sent me to find him. I have", "this weird talisman he found. He said the head wizard", "would be very interested in it.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("Did he now? HmmmMMMMMmmmmm.", "Well that IS interesting. Hand it over then adventurer,", "let me see what all the hubbub about it is.", "Just some amulet I'll wager.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showTwoOptions("Ok, here you are.", "No, I'll only give it to the head wizard.");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, here you are.", 591);
                        player.getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(7);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showOneLineStatement("You hand the Talisman to the wizard.");
                    return true;
                }
                if (value2 == 10 && player.getInventoryManager().containsItem(1438)) {
                    player.setQuestState(this.getQuestId(), 3);
                    player.getInventoryManager().removeItem(new ItemStack(1438, 1));
                    player.getDialogueManager().showNpcOneLineDialogue("Wow! This is... incredible!", 591);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Th-this talisman you brought me...! It is the last piece", "of the puzzle, I think! Finally! The legacy of our", "ancestors... it will return to us once more!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I need time to study this, " + player.getUsername() + ". Can you please", "do me this task while I study this talisman you have", "brought me? In the mighty town of Varrock, which", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("is located North East of here, there is a certain shop", "that sells magical runes. I have in this package all of the", "research I have done relating to the Rune Stones, and", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("reguire somebody to take them to the shopkeeper so that", "he may share my research and offer me his insights.", "Do this thing for me, and bring back what he gives you,", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("and if my suspicions are correct, I will let you into the", "knowledge of one of the greatest secrets this world has", "ever known! A secret so powerful that it destroyed the", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("original Wizards' Tower all of those centuries", "ago! My research, combined with this mysterious", "talisman... I cannot believe the answer to", "the mysteries is so close now!", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Do this thing for me " + player.getUsername() + ". Be rewarded in a", "way you can never imagine.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showTwoOptions("Yes, certainly.", "No, I'm busy.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes, certainly.", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcFourLineDialogue("Take this package, and head directly North", "from here, through Draynor Village, until you reach", "the Barbarian Village. Then head East from there", "until you reach Varrock.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Once in Varrock, take this package to the owner of the", "rune shop. His name is Aubury. You may find it", "helpful to ask one of Varrock's citizens for directions,", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcThreeLineDialogue("as Varrock can be confusing place for the first time", "visitor. He will give you a special item - bring it back to", "me, and I shall show you the mystery of the runes...", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showOneLineStatement("The head wizard gives you a package.");
                    return true;
                }
                if (value2 == 14) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(290, 1));
                    player.setQuestState(this.getQuestId(), 4);
                    player.getDialogueManager().showNpcOneLineDialogue("Best of luck with your quest, " + player.getUsername() + ".", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4 || value42 == 6) {
                if (value42 == 6 && !player.getInventoryManager().containsItem(291)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Welcome adventurer, to the world renowned", "Wizards' Tower. How may I help you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah, " + player.getUsername() + ". How goes your quest? Have you", "delivered the research notes to my friend Aubury yet?", 591);
                    if (value42 == 4 && !player.ownsItem(290)) {
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    if (value42 == 4 && player.ownsItem(290)) {
                        player.getDialogueManager().setNextDialogueStep(7);
                    }
                    if (value42 == 6) {
                        player.getDialogueManager().setNextDialogueStep(8);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("No... I lost them.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well fortunately I have another copy of it.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("The head wizard gives you a package.");
                    return true;
                }
                if (value2 == 6) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(290, 1));
                    player.getDialogueManager().showNpcOneLineDialogue("Try not to lose it this time.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Not yet.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Yes, I have. He gave me some research notes", "to pass on to you.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("May I have his notes then?", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Sure. I have them here.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well, before you hand them over to me, as you", "have been nothing but truthful with me to this point,", "and I admire that in adventurer, I will let you", "into the secret of our research.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcFourLineDialogue("Now as you may or may not know, many", "centuries ago, the wizards at this Tower", "learnt the secret of creating Rune Stones, which", "allowed us to cast Magic very easily.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcFourLineDialogue("When this Tower was burnt down the secret of", "creating runes was lost to us for all time... except it", "wasn't. Some months ago, while searching these ruins", "for information from the old days,", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I came upon a scroll, almost destroyed, that detailed a", "magical rock deep in the icefields of the North, closed off", "from access by anything other than magical means.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcFourLineDialogue("This rock was called the 'Rune Essence' by the", "magicians who studied its powers. Apparently, by simply", "breaking a chunk from it, a Rune Stone could be", "fashioned very quickly and easily at certain", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcFourLineDialogue("elemental altars that were scattered across the land", "back then. Now, this is an interesting little piece of", "history, but not much use to us as modern wizards", "without access to the Rune Essence,", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcFourLineDialogue("or these elemental altars. This is where you and", "Aubury come into this story. A few weeks back,", "Aubury discovered in a standard delivery of runes", "to his store, a parchment detailing a", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcFourLineDialogue("teleportation spell that he had never come across", "before. To his shock, when cast it took him to a", "strange rock he had never encountered before...", "yet that felt strangely familiar...", 591);
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcThreeLineDialogue("As I'm sure you have now guessed, he had discovered a", "portal leading to the mythical Rune Essence. As soon as", "he told me of this spell, I saw the importance of his find,", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showNpcFourLineDialogue("for if we could but find the elemental altars spoken", "of in the ancient texts, we would once more be able", "to create runes as our ancestors had done! It would", "be the saviour of the wizards' art!", 591);
                    return true;
                }
                if (value2 == 21) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I'm still not sure how I fit into", "this little story of yours...", 591);
                    return true;
                }
                if (value2 == 22) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You haven't guessed? This talisman you brought me...", "it is the key to the elemental altar of air! When", "you hold it next, it will direct you towards", 591);
                    return true;
                }
                if (value2 == 23) {
                    player.getDialogueManager().showNpcThreeLineDialogue("the entrance to the long forgotten Air Altar! By", "bringing pieces of the Rune Essence to the Air Temple,", "you will be able to fashion your own Air Runes!", 591);
                    return true;
                }
                if (value2 == 24) {
                    player.getDialogueManager().showNpcThreeLineDialogue("And this is not all! By finding other talismans similar", "to this one, you will eventually be able to to craft every", "rune that is available on this world! Just", 591);
                    return true;
                }
                if (value2 == 25) {
                    player.getDialogueManager().showNpcThreeLineDialogue("as our ancestors did! I cannot stress enough what a", "find this is! Now, due to the risks involved of letting", "this mighty power fall into the wrong hands", 591);
                    return true;
                }
                if (value2 == 26) {
                    player.getDialogueManager().showNpcFourLineDialogue("I will keep the teleport skill to the Rune Essence", "a closely guarded secret, shared only by myself", "and those Magic users around the world", "whom I trust enough to keep it.", 591);
                    return true;
                }
                if (value2 == 27) {
                    player.getDialogueManager().showNpcFourLineDialogue("This means that if any evil power should discover", "the talisman required to enter the elemental", "temples, we will be able to prevent their access", "to the Rune Essence and prevent", 591);
                    return true;
                }
                if (value2 == 28) {
                    player.getDialogueManager().showNpcThreeLineDialogue("tragedy befalling this world. I know not where the", "temples are located, nor do I know where the talismans", "have been scattered to in this land, but I now", 591);
                    return true;
                }
                if (value2 == 29) {
                    player.getDialogueManager().showNpcThreeLineDialogue("return your Air Talisman to you. Find the Air", "Temple, and you will be able to charge your Rune", "Essences to become Air Runes at will. Any time", 591);
                    return true;
                }
                if (value2 == 30) {
                    player.getDialogueManager().showNpcThreeLineDialogue("you wish to visit the Rune Essence, speak to me", "or Aubury and we will open a portal to that", "mystical place for you to visit.", 591);
                    return true;
                }
                if (value2 == 31) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("So only you and Aubury know the teleport", "spell to the Rune Essence?", 591);
                    return true;
                }
                if (value2 == 32) {
                    player.getDialogueManager().showNpcFourLineDialogue("No... there are others... whom I will tell of your", "authorisation to visit that place. When you speak", "to them, they will know you, and grant you", "access to that place when asked.", 591);
                    return true;
                }
                if (value2 == 33) {
                    player.getDialogueManager().showNpcFourLineDialogue("Use the Air Talisman to locate the air temple,", "and use any further talismans you find to locate", "the other missing elemental temples.", "Now... my research notes please?", 591);
                    return true;
                }
                if (value2 == 34) {
                    player.getDialogueManager().showTwoLineStatement("You hand the head wizard the research notes.", "He hands you back the Air Talisman.");
                    return true;
                }
                if (value2 == 35 && player.getInventoryManager().containsItem(291)) {
                    player.getInventoryManager().removeItem(new ItemStack(291, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(1438, 1));
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 553) {
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Do you want to buy some runes?", 591);
                    if (!player.getInventoryManager().containsItem(290)) {
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("Yes please!", "Oh, it's a rune shop. No thank you, then.", "I have been sent here with a package for you.");
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("Yes please!", "Oh, it's a rune shop. No thank you, then.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        GameplayHelper.openNpcShop(player, npcId);
                        player.getDialogueManager().markDialogueInactive();
                        return false;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("I have been sent here with a package for you. It's from", "the head wizard at the Wizards' Tower.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Really? But... surely he can't have..? Please, let me", "have it, it must be extremely important for him to have", "sent a stranger.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showOneLineStatement("You hand Aubury the research package.");
                    return true;
                }
                if (value2 == 7 && player.getInventoryManager().containsItem(290)) {
                    player.setQuestState(this.getQuestId(), 5);
                    player.getInventoryManager().removeItem(new ItemStack(290, 1));
                    player.getDialogueManager().showNpcTwoLineDialogue("This... this is incredible. Please, give me a few moments", "to quickly look over this, and then talk to me again.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 5) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcFourLineDialogue("My gratitude to you adventurer for bringing me these", "research notes. I notice that you brought the head", "wizard a special talisman that was the key to our finally", "unlocking the puzzle.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Combined with the information I had already collated", "regarding the Rune Essence, I think we have finally", "unlocked the power to", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcFourLineDialogue("...no. I am getting ahead of myself. Please take this", "summary of my research back to the head wizard at", "the Wizards' Tower. I trust his judgement on whether", "to let you in on our little secret or not.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showOneLineStatement("Aubury gives you his research notes.");
                    player.getInventoryManager().addOrDropItem(new ItemStack(291, 1));
                    player.setQuestState(this.getQuestId(), 6);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 6) {
                if (player.getInventoryManager().containsItem(291)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I lost your notes.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well fortunately I have another copy of it.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("Aubury gives you his research notes.");
                    return true;
                }
                if (value2 == 4) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(291, 1));
                    player.getDialogueManager().showNpcOneLineDialogue("Try not to lose it this time.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

