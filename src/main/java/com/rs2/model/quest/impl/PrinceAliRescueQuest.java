package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class PrinceAliRescueQuest
extends QuestScript {
    public PrinceAliRescueQuest(int value2) {
        super(11);
        super.setQuestPointReward(3);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Hassan at the palace", "in Al-Kharid."};
        }
        if (value2 == 2) {
            return new String[]{"I should go talk to Osman outside the palace in", "Al-Kharid."};
        }
        if (value2 == 3) {
            return new String[]{"Osman told me find Leela near Draynor Village and", "get these items:", "Rope", "Pink skirt", "Blonde wig", "Skin paste", "", "He also told me to bring imprint of the key and a", "bronze bar to him."};
        }
        if (value2 == 4) {
            return new String[]{"Leela told me I need the following items to help", "the prince escape:", "Rope", "Skirt", "Blonde wig", "Skin paste", "Key"};
        }
        if (value2 == 5) {
            return new String[]{"Leela told me I need the following items to help", "the prince escape:", "Rope", "Skirt", "Blonde wig", "Skin paste", "Key - I can get this from Leela"};
        }
        if (value2 == 6) {
            return new String[]{"I should now go talk to Prison guard Joe and", "find a weakness in him."};
        }
        if (value2 == 7) {
            return new String[]{"Prison guard Joe seems to like beer, maybe I", "should get him drunk."};
        }
        if (value2 == 8) {
            return new String[]{"Prison guard Joe is drunk, I should now tie up Lady", "Keli with a rope, and then go rescue Prince Ali."};
        }
        if (value2 == 9) {
            return new String[]{"I should go talk to Hassan at the palace in Al-Kharid", "to finish this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "3 Quest Points", "700 Coins"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("3 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("700 Coins", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 700));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 995);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    private static boolean hasAllRescueSupplies(Player player) {
        return player.getInventoryManager().containsItemAmount(2419, 1) && player.getInventoryManager().containsItemAmount(2418, 1) && player.getInventoryManager().containsItemAmount(2424, 1) && player.getInventoryManager().containsItemAmount(954, 1) && player.getInventoryManager().containsItemAmount(1013, 1);
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2881 && value2 == 3123 && value32 == 3243) {
            if (player.getInventoryManager().containsItem(2418) && value42 == 8 || value42 == 9) {
                Npc npc;
                if (value42 == 8 && (npc = Npc.findByDefinitionId(919)) != null && npc.isActive()) {
                    return true;
                }
                Player player2 = player;
                player2.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3244 ? 1 : -1, true);
                player2 = player;
                player2.packetSender.openSingleDoor(2881, 3123, 3243, 0);
                if (value42 == 8) {
                    player2 = player;
                    player2.packetSender.sendGameMessage("You unlock the door.");
                }
                return true;
            }
            Player player3 = player;
            player3.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        if (value32 != 0 && (itemId == 1765 && value2 == 2421 || itemId == 2421 && value2 == 1765)) {
            player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
            player.getInventoryManager().removeItem(new ItemStack(value2, 1));
            player.getInventoryManager().addItem(new ItemStack(2419, 1));
            player.packetSender.sendGameMessage("You dye the wig blonde.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnNpc(Player player, int npcId, int value2, int value32) {
        if (value32 == 8 && value2 == 954 && npcId == 919) {
            DialogueManager.continueDialogue(player, 919, 100, 0);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 923) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Greetings I am Hassan, Chancellor to the Emir of Al-", "Kharid.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("Can I help you? You must need some help here in the desert.", "It's just too hot here. How can you stand it?", "Do you mind if I just kill your warriors?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("Can I help you? You must need some help here in the", "desert.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I need the services of someone, yes. If you are", "interested, see the spymaster, Osman. I manage the", "finances here. Come to me when you need payment.", 591);
                    this.startQuest(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 9) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You have the eternal gratitude of the Emir for", "rescuing his son. I am authorised to pay you 700", "coins.", 591);
                    return true;
                }
                if (value2 == 2) {
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 924) {
            if (value42 >= 2 && value42 < 4) {
                if (value42 > 2 && value2 == 1) {
                    value2 = 3;
                    player.pendingGameMode = 2;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("The chancellor trusts me. I have come for instructions.", 591);
                    player.pendingGameMode = 0;
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Our prince is captive by the Lady Keli. We just need", "to make the rescue. There are two things we need", "you to do.", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
                if (value2 == 3) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showTwoOptions("What is the first thing I must do?", "What is the second thing you need?");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showThreeOptions("Explain the first thing again.", "What is the second thing you need?", "Okay, I better go find some things.");
                    }
                    if (player.pendingGameMode == 2) {
                        player.getDialogueManager().showThreeOptions("What is the first thing I must do?", "What exactly is the second thing you need?", "Okay, I better go find some things.");
                    }
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 2) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What is the first thing I must do?", 591);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("Explain the first thing again.", 591);
                        }
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    if (value32 == 2) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What is the second thing you need?", 591);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("What exactly is the second thing you need?", 591);
                        }
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    if (player.pendingGameMode > 0 && value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Okay, I had better go find some things.", 591);
                        player.getDialogueManager().setNextDialogueStep(15);
                        return true;
                    }
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The prince is guarded by some stupid guards and a", "clever woman. The woman is our only way to get the", "prince out. Only she can walk freely about the area.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I think you will need to tie her up. One coil of rope", "should do for that. Then, disguise the prince as her to", "get him out without suspicion.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How good must the disguise be?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Only enough to fool the guards at a distance. Get a", "skirt like hers. Same colour, same style. We will only", "have a short time.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Get a blonde wig, too. That is up to you to make or", "find. Something to colour the skin of the prince.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("My daughter and top spy, Leela, can help you. She has", "sent word that she has discovered where they are", "keeping the prince.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("It's near Draynor Village. She is lurking somewhere", "near there now.", 591);
                    player.pendingGameMode = 1;
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("We need the key, or we need a copy made. If you can", "get some soft clay then you can copy the key...", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcThreeLineDialogue("...If you can convince Lady Keli to show it to you", "for a moment. She is very boastful.", "It should not be too hard.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("Bring the imprint to me, with a bar of bronze.", 591);
                    player.pendingGameMode = 2;
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcTwoLineDialogue("May good luck travel with you. Don't forget to find", "Leela. It can't be done without her help.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4 && player.getInventoryManager().containsItemAmount(2423, 1) && player.getInventoryManager().containsItemAmount(2349, 1)) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well done; we can make the key now.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getInventoryManager().removeItem(new ItemStack(2423, 1));
                    player.getInventoryManager().removeItem(new ItemStack(2349, 1));
                    player.getDialogueManager().showOneLineStatement("Osman takes the key imprint and the bronze bar.");
                    player.setQuestState(this.getQuestId(), 5);
                    return true;
                }
            }
            if (value42 == 5) {
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Pick the key up from Leela.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showTwoOptions("Thank you. I will try to find the other items.", "Can you tell me what I still need to get?");
                    return true;
                }
                if (value2 == 5) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Thank you. I will try to find the other items.", 591);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
            }
        }
        if (npcId == 915) {
            if (value42 >= 3 && value42 < 5) {
                if (value42 > 3 && value2 == 1) {
                    value2 = 3;
                    player.pendingGameMode = 0;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I am here to help you free the prince.", 591);
                    player.pendingGameMode = 0;
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Your employment is known to me. Now, do you know", "all that we need to make the break?", 591);
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
                if (value2 == 3) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showFourOptions("I must make a disguise. What do you suggest?", "I need to get the key made.", "What can I do with the guards?", "I will go and get the rest of the escape equipment.");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showThreeOptions("I need to get the key made.", "What can I do with the guards?", "I will go and get the rest of the escape equipment.");
                    }
                    if (player.pendingGameMode == 2) {
                        player.getDialogueManager().showThreeOptions("I must make a disguise. What do you suggest?", "What can I do with the guards?", "I will go and get the rest of the escape equipment.");
                    }
                    if (player.pendingGameMode == 3) {
                        player.getDialogueManager().showThreeOptions("I must make a disguise. What do you suggest?", "I need to get the key made.", "I will go and get the rest of the escape equipment.");
                    }
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        if (player.pendingGameMode != 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("I must make a disguise. What do you suggest?", 591);
                            player.getDialogueManager().setNextDialogueStep(5);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("I need to get the key made.", 591);
                            player.getDialogueManager().setNextDialogueStep(7);
                        }
                        return true;
                    }
                    if (value32 == 2) {
                        if (player.pendingGameMode != 0 && player.pendingGameMode != 3) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What can I do with the guards?", 591);
                            player.getDialogueManager().setNextDialogueStep(9);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("I need to get the key made.", 591);
                            player.getDialogueManager().setNextDialogueStep(7);
                        }
                        return true;
                    }
                    if (value32 == 3) {
                        if (player.pendingGameMode != 0) {
                            player.getDialogueManager().showPlayerOneLineDialogue("I will go and get the rest of the escape equipment.", 591);
                            player.getDialogueManager().setNextDialogueStep(11);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("What can I do with the guards?", 591);
                            player.getDialogueManager().setNextDialogueStep(9);
                        }
                        return true;
                    }
                    if (value32 == 4 && player.pendingGameMode == 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I will go and get the rest of the escape equipment.", 591);
                        player.getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("Only the lady Keli, can wander about outside the jail.", "The guards will shoot to kill if they see the prince out", "so we need a disguise good enough to fool them at a", "distance.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You need a wig, maybe made from wool. If you find", "someone who can work with wool ask them about it.", "There's a witch nearby may be able to help you dye it.", 591);
                    player.getDialogueManager().setNextDialogueStep(12);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, that is most important. There is no way you can", "get the real key. It is on a chain around Keli's neck.", "Almost impossible to steal.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Get some soft clay and get her to show you the key", "somehow. Then take the print, with bronze, to my", "father.", 591);
                    player.pendingGameMode = 2;
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Most of the guards will be easy. The disguise will get", "past them. The only guard who will be a problem will be", "the one at the door.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("We can discuss this more when you have the rest of", "the escape kit.", 591);
                    player.pendingGameMode = 3;
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good, I shall await your return with everything.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 12) {
                    if (player.getInventoryManager().containsItemAmount(1013, 1)) {
                        player.getDialogueManager().showNpcOneLineDialogue("You have got the skirt, good.", 591);
                    } else {
                        player.getDialogueManager().showNpcOneLineDialogue("You also need to find a pink skirt.", 591);
                    }
                    return true;
                }
                if (value2 == 13) {
                    if (player.getInventoryManager().containsItemAmount(2424, 1)) {
                        player.getDialogueManager().showNpcOneLineDialogue("You have got the skin paste, good.", 591);
                    } else {
                        player.getDialogueManager().showNpcFourLineDialogue("We still need something to colour the Prince's skin", "lighter. There's a witch close to here. She knows about", "many things. She may know some way to make the", "skin lighter.", 591);
                    }
                    return true;
                }
                if (value2 == 14) {
                    if (player.getInventoryManager().containsItemAmount(954, 1)) {
                        player.getDialogueManager().showNpcTwoLineDialogue("You have a rope I see, to tie up Keli. That will be the", "most dangerous part of the plan.", 591);
                    } else {
                        player.getDialogueManager().showNpcOneLineDialogue("You need to get some rope somewhere too.", 591);
                    }
                    player.pendingGameMode = 1;
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
            }
            if (value42 == 5) {
                if (!player.ownsItem(2418)) {
                    if (value2 == 1) {
                        player.getDialogueManager().showNpcTwoLineDialogue("My father sent this key for you.", "Be careful not to lose it.", 591);
                        return true;
                    }
                    if (value2 == 2) {
                        player.getDialogueManager().showOneLineStatement("Leela gives you a copy of the key to the prince's door.");
                        return true;
                    }
                    if (value2 == 3) {
                        player.getInventoryManager().addOrDropItem(new ItemStack(2418, 1));
                        if (PrinceAliRescueQuest.hasAllRescueSupplies(player)) {
                            player.getDialogueManager().showNpcThreeLineDialogue("Good, you have all the basic equipment. Next to deal", "with the guard on the door. He is talkative, try to find", "a weakness in him.", 591);
                            player.setQuestState(this.getQuestId(), 6);
                        } else {
                            player.getDialogueManager().showNpcTwoLineDialogue("Come back to me when you have the rest of the", "items we need.", 591);
                        }
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                } else if (value2 == 1) {
                    if (PrinceAliRescueQuest.hasAllRescueSupplies(player)) {
                        player.getDialogueManager().showNpcThreeLineDialogue("Good, you have all the basic equipment. Next to deal", "with the guard on the door. He is talkative, try to find", "a weakness in him.", 591);
                        player.setQuestState(this.getQuestId(), 6);
                    } else {
                        player.getDialogueManager().showNpcTwoLineDialogue("Come back to me when you have the rest of the", "items we need.", 591);
                    }
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 6 && !player.ownsItem(2418)) {
                if (value2 == 1) {
                    player.getDialogueManager().showOneLineStatement("Leela gives you a copy of the key to the prince's door.");
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Do not lose the key this time.", 591);
                    player.getInventoryManager().addOrDropItem(new ItemStack(2418, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 918 && value42 >= 4 && value42 < 9) {
            if (value2 < 111 && (player.ownsItem(2421) || player.ownsItem(2419))) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcThreeLineDialogue("Why, hello there, lad. Me friends call me Ned. I was a", "man of the sea, but it's past me now. Could I be", "making or selling you some rope?", 591);
                player.getDialogueManager().setNextDialogueStep(100);
                return true;
            }
            if (value2 == 100) {
                player.getDialogueManager().showThreeOptions("Ned, could you make other things from wool?", "Yes, I would like some rope.", "No thanks Ned, I don't need any.");
                return true;
            }
            if (value2 == 101) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ned, could you make other things from wool?", 591);
                    player.getDialogueManager().setNextDialogueStep(102);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(100);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 102) {
                player.getDialogueManager().showNpcOneLineDialogue("I am sure I can. What are you thinking of?", 591);
                return true;
            }
            if (value2 == 103) {
                player.getDialogueManager().showThreeOptions("Could you knit me a sweater?", "How about some sort of a wig?", "Could you repair the arrow holes in the back of my shirt?");
                return true;
            }
            if (value2 == 104) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How about some sort of a wig?", 591);
                    player.getDialogueManager().setNextDialogueStep(105);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(103);
                return true;
            }
            if (value2 == 105) {
                player.getDialogueManager().showNpcThreeLineDialogue("Well... That's an interesting thought. Yes, I think I", "could do something. Give me 3 balls of wool and I", "might be able to do it.", 591);
                return true;
            }
            if (value2 == 106) {
                player.getDialogueManager().showTwoOptions("I have that now. Please, make me a wig.", "I will come back when I need you to make me one.");
                return true;
            }
            if (value2 == 107) {
                if (value32 == 1 && player.getInventoryManager().containsItemAmount(1759, 3)) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have that now. Please, make me a wig.", 591);
                    player.getDialogueManager().setNextDialogueStep(108);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 108) {
                player.getDialogueManager().showNpcOneLineDialogue("Okay, I will have a go.", 591);
                return true;
            }
            if (value2 == 109) {
                player.getDialogueManager().showTwoLineStatement("You hand Ned 3 balls of wool. Ned works with the wool.", "His hands move with a speed you couldn't imagine.");
                return true;
            }
            if (value2 == 110 && player.getInventoryManager().containsItemAmount(1759, 3)) {
                player.getInventoryManager().removeItem(new ItemStack(1759, 3));
                player.getInventoryManager().addOrDropItem(new ItemStack(2421, 1));
                player.getDialogueManager().showNpcTwoLineDialogue("Here you go, how's that for a quick effort?", "Not bad I think!", 591);
                player.getDialogueManager().setNextDialogueStep(111);
                return true;
            }
            if (value2 == 111) {
                player.getDialogueManager().showOneLineStatement("Ned gives you a pretty good wig.");
                return true;
            }
            if (value2 == 112) {
                player.getDialogueManager().showPlayerOneLineDialogue("Thanks Ned, there's more to you than meets the eye.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 922 && value42 >= 4 && value42 < 9) {
            if (value2 != 109 && player.ownsItem(2424)) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("What can I help you with?", 591);
                player.getDialogueManager().setNextDialogueStep(100);
                return true;
            }
            if (value2 == 100) {
                player.getDialogueManager().showFiveOptions("Could you think of a way to make skin paste?", "What could you make for me?", "Cool, do you turn people into frogs?", "You mad old witch, you can't help me.", "Can you make dyes for me please?");
                return true;
            }
            if (value2 == 101) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Could you think of a way to make skin paste?", 591);
                    player.getDialogueManager().setNextDialogueStep(102);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(100);
                return true;
            }
            if (value2 == 102) {
                if (player.getInventoryManager().containsItemAmount(592, 1) && player.getInventoryManager().containsItemAmount(1951, 1) && player.getInventoryManager().containsItemAmount(1933, 1) && player.getInventoryManager().containsItemAmount(1929, 1)) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yes I can, I see you already have the ingredients.", "Would you like me to mix some for you now?", 591);
                } else {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yes I can, I need the following ingredients:", "ashes, flour, water and redberries.", 591);
                    player.getDialogueManager().finishDialogue();
                }
                return true;
            }
            if (value2 == 103) {
                player.getDialogueManager().showTwoOptions("Yes please. Mix me some skin paste.", "No thank you, I don't need any skin paste right now.");
                return true;
            }
            if (value2 == 104) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes please. Mix me some skin paste.", 591);
                    player.getDialogueManager().setNextDialogueStep(105);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 105) {
                player.getDialogueManager().showNpcOneLineDialogue("That should be simple. Hand the things to Aggie then.", 591);
                return true;
            }
            if (value2 == 106) {
                player.getDialogueManager().showThreeLineStatement("You hand the ash, flour, water and redberries to Aggie.", "Aggies tips the ingredients into a cauldron", "and mutters some words.");
                return true;
            }
            if (value2 == 107) {
                player.getDialogueManager().showNpcTwoLineDialogue("Tourniquet, Fenderbaum, Tottenham, Marshmallow,", "MarbleArch.", 591);
                return true;
            }
            if (value2 == 108 && player.getInventoryManager().containsItemAmount(592, 1) && player.getInventoryManager().containsItemAmount(1951, 1) && player.getInventoryManager().containsItemAmount(1933, 1) && player.getInventoryManager().containsItemAmount(1929, 1)) {
                player.getInventoryManager().removeItem(new ItemStack(592, 1));
                player.getInventoryManager().removeItem(new ItemStack(1951, 1));
                player.getInventoryManager().removeItem(new ItemStack(1933, 1));
                player.getInventoryManager().removeItem(new ItemStack(1929, 1));
                player.getInventoryManager().addOrDropItem(new ItemStack(2424, 1));
                player.getDialogueManager().showOneLineStatement("Aggie hands you the skin paste.");
                player.getDialogueManager().setNextDialogueStep(123);
                return true;
            }
            if (value2 == 109) {
                player.getDialogueManager().showNpcTwoLineDialogue("There you go dearie, your skin potion. That will make", "you look good at the Varrock dances.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 919) {
            if (value42 == 4) {
                if (value2 != 25 && player.ownsItem(2423)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Are you the famous Lady Keli? Leader of the toughest", "gang of mercenary killers around?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am Keli, you have heard of me then?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showFourOptions("Heard of you? You are famous in RuneScape!", "I have heard a little, but I think Katrine is tougher.", "I have heard rumours that you kill people.", "No I have never really heard of you.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("The great Lady Keli, of course I have heard of you.", "You are famous in RuneScape!", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("That's very kind of you to say. Reputations are", "not easily earned. I have managed to succeed", "where many fail.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showFourOptions("I think Katrine is still tougher.", "What is your latest plan then?", "You must have trained a lot for this work.", "I should not disturb someone as tough as you.");
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("What is your latest plan then? Of course, you need", "not go into specific details.", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, I can tell you I have a valuable prisoner here", "in my cells.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I can expect a high reward to be paid very soon for", "this guy. I can't tell you who he is, but he is a lot", "colder now.", 591);
                    player.pendingGameMode = 0;
                    return true;
                }
                if (value2 == 10) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showFourOptions("Ah, I see. You must have been very skillful.", "Thats great, are you sure they will pay?", "Can you be sure they will not try to get him out?", "I should not disturb someone as tough as you.");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showThreeOptions("Are you sure they will pay?", "Can you be sure they will not try to get him out?", "I should not disturb someone as tough as you.");
                    }
                    return true;
                }
                if (value2 == 11) {
                    if (value32 == 1) {
                        if (player.pendingGameMode == 0) {
                            player.getDialogueManager().showPlayerOneLineDialogue("You must have been very skillful.", 591);
                            player.getDialogueManager().setNextDialogueStep(12);
                            return true;
                        }
                    } else if (value32 == 2) {
                        if (player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Can you be sure they will not try to get him out?", 591);
                            player.getDialogueManager().setNextDialogueStep(14);
                            return true;
                        }
                    } else {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes, I did most of the work. We had to grab the Pr...", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Er, we had to grab him without his ten bodyguards", "noticing. It was a stroke of genius.", 591);
                    player.pendingGameMode = 1;
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("There is no way to release him. The only key to the", "door is on a chain around my neck and the locksmith", "who made the lock died suddenly when he had finished.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcOneLineDialogue("There is not another key like this in the world.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showThreeOptions("Could I see the key please?", "That is a good way to keep secrets.", "I should not disturb someone as tough as you.");
                    return true;
                }
                if (value2 == 17) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerThreeLineDialogue("Could I see the key please? Just for a moment. It", "would be something I can tell my grandchildren. When", "you are even more famous than you are now.", 591);
                        player.getDialogueManager().setNextDialogueStep(18);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(16);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcTwoLineDialogue("As you put it that way I am sure you can see it. You", "cannot steal the key, it is on a Runite chain.", 591);
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showOneLineStatement("Keli shows you a small key on a strong looking chain.");
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showTwoOptions("Could I touch the key for a moment please?", "I should not disturb someone as tough as you.");
                    return true;
                }
                if (value2 == 21) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Could I touch the key for a moment please?", 591);
                        player.getDialogueManager().setNextDialogueStep(22);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(20);
                    return true;
                }
                if (value2 == 22) {
                    if (!player.getInventoryManager().containsItemAmount(1761, 1)) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    player.getDialogueManager().showNpcOneLineDialogue("Only for a moment then.", 591);
                    return true;
                }
                if (value2 == 23) {
                    player.getDialogueManager().showTwoLineStatement("You put a piece of your soft clay in your hand. As you touch the", "key, you take an imprint of it.");
                    return true;
                }
                if (value2 == 24 && player.getInventoryManager().containsItemAmount(1761, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1761, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(2423, 1));
                    player.getDialogueManager().showPlayerOneLineDialogue("Thank you so much, you are too kind, o great Keli.", 591);
                    return true;
                }
                if (value2 == 25) {
                    player.getDialogueManager().showNpcOneLineDialogue("You are welcome, run along now. I am very busy.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 8) {
                if (value2 == 100) {
                    player.getDialogueManager().showOneLineStatement("You overpower Keli, tie her up, and put her in a cupboard.");
                    return true;
                }
                if (value2 == 101 && player.getInventoryManager().containsItemAmount(954, 1)) {
                    Npc npc = Npc.findByDefinitionId(919);
                    if (npc != null && !npc.isDead()) {
                        player.getInventoryManager().removeItem(new ItemStack(954, 1));
                        CombatManager.finishDeath(npc, player, false);
                    }
                    player.getDialogueManager().finishDialogue();
                    player.packetSender.closeInterfaces();
                    return true;
                }
            }
        }
        if (npcId == 916) {
            if (value42 == 6) {
                if (value2 == 1) {
                    player.getDialogueManager().showFourOptions("I have some beer here, fancy one?", "Tell me about the life of a guard.", "What did you want to be when you were a boy?", "I had better leave, I don't want trouble.");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have some beer here, fancy one?", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah, that would be lovely, just one now,", "just to wet my throat.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Of course, it must be tough being here without a drink.", 591);
                    return true;
                }
                if (value2 == 5 && player.getInventoryManager().containsItemAmount(1917, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(1917, 1));
                    player.getDialogueManager().showOneLineStatement("You hand a beer to the guard, he drinks it in seconds.");
                    player.setQuestState(this.getQuestId(), 7);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
            }
            if (value42 == 7) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("That was perfect, I can't thank you enough.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How are you? Still ok? Not too drunk?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Would you care for another, my friend?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("I better not, I don't want to be drunk on duty.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Here, just keep these for later,", "I hate to see a thirsty guard.", 591);
                    return true;
                }
                if (value2 == 6 && player.getInventoryManager().containsItemAmount(1917, 2)) {
                    player.getInventoryManager().removeItem(new ItemStack(1917, 2));
                    player.getDialogueManager().showTwoLineStatement("You hand two more beers to the guard.", "He takes a sip of one, and then he drinks them both.");
                    player.setQuestState(this.getQuestId(), 8);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
            }
            if (value42 == 8) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Franksh, that was just what I need to shtay on guard.", "No more beersh, I don't want to get drunk.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showOneLineStatement("The guard is drunk, and no longer a problem.");
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 920) {
            if (value42 == 8) {
                if (!(player.getInventoryManager().containsItemAmount(2418, 1) && player.getInventoryManager().containsItemAmount(2419, 1) && player.getInventoryManager().containsItemAmount(1013, 1) && player.getInventoryManager().containsItemAmount(2424, 1))) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Prince, I come to rescue you.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("That is very kind of you, how do I get out?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("With a disguise. I have removed the Lady Keli. She is", "tied up, but will not stay tied up for long.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Take this disguise, and this key.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("You hand the disguise and the key to the prince.");
                    return true;
                }
                if (value2 == 6) {
                    Npc npc = Npc.findByDefinitionId(920);
                    if (npc != null && npc.isActive()) {
                        player.getInventoryManager().removeItem(new ItemStack(2418, 1));
                        player.getInventoryManager().removeItem(new ItemStack(2419, 1));
                        player.getInventoryManager().removeItem(new ItemStack(1013, 1));
                        player.getInventoryManager().removeItem(new ItemStack(2424, 1));
                        npc.transformToNpcId(921, 100);
                        player.getDialogueManager().setDialogueNpcId(921);
                        player.getDialogueManager().showNpcTwoLineDialogue("Thank you my friend, I must leave you now. My", "father will pay you well for this.", 591);
                        player.setQuestState(this.getQuestId(), 9);
                    }
                    return true;
                }
            }
            if (value42 == 9) {
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Go to Leela, she is close to here.", 591);
                    return true;
                }
                if (value2 == 8) {
                    Npc npc = Npc.findByDefinitionId(921);
                    if (npc != null && npc.isActive()) {
                        CombatManager.finishDeath(npc, player, false);
                    }
                    player.getDialogueManager().showTwoLineStatement("The prince has escaped, well done! You are now a friend of Al-", "Kharid and may pass through the Al-Kharid toll gate for free.");
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

