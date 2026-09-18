package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class BlackKnightsFortressQuest
extends QuestScript {
    public BlackKnightsFortressQuest(int value2) {
        super(1);
        super.setQuestPointReward(3);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Sir Amik Varze at the", "White Knights' Castle in Falador.", String.valueOf(player.getQuestPoints() >= 12 ? "@str@" : "") + "I have a total of at least 12 Quest Points", "I would have an advantage if I could fight Level 33 Knights", "and if I had a smithing level of 26."};
        }
        if (value2 == 2) {
            return new String[]{"I should find and sabotage Black Knights' secret weapon,", "which can be found somewhere in the Black Knights'", "fortress. I will need bronze med helm and iron chainbody to", "disguise myself."};
        }
        if (value2 == 3) {
            return new String[]{"It seems that I could sabotage the potion by putting", "ordinary cabbage in there. Just need to find a way", "to do that."};
        }
        if (value2 == 4) {
            return new String[]{"I should now return to Sir Amik Varze to finish", "this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "3 Quest Points", "2500 Coins"};
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
        player2.packetSender.sendInterfaceText("2500 Coins", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 2500));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1965);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 73 && value2 == 3007 && value32 == 3516 || objectId == 74 && value2 == 3007 && value32 == 3515) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2342 && value2 == 3025 && value32 == 3507 && value42 == 2) {
            DialogueManager.continueDialogue(player, 610, 200, 0);
            return true;
        }
        if (objectId == 2340 && value2 == 3028 && value32 == 3510) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2341 && value2 == 3016 && value32 == 3517) {
            Player player4 = player;
            player4.packetSender.openSingleDoor(objectId, value2, value32, 0);
            player4 = player;
            player4.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3517 ? 1 : -1, true);
            player4 = player;
            player4.packetSender.sendGameMessage("You push against the wall. You find a secret passage.");
            return true;
        }
        if (objectId == 2341 && value2 == 3030 && value32 == 3510) {
            Player player5 = player;
            player5.packetSender.openSingleDoor(objectId, value2, value32, 1);
            player5 = player;
            player5.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3510 ? 1 : -1, true);
            player5 = player;
            player5.packetSender.sendGameMessage("You push against the wall. You find a secret passage.");
            return true;
        }
        if (objectId == 2339 && value2 == 3025 && value32 == 3511) {
            Player player6 = player;
            player6.packetSender.openSingleDoor(objectId, value2, value32, 1);
            player6 = player;
            player6.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3026 ? 1 : -1, 0, true);
            return true;
        }
        if (objectId == 2337 && value2 == 3016 && value32 == 3514) {
            if (player.getPosition().getY() < 3515) {
                if (player.getEquipmentManager().getItemIdAtSlot(0) == 1139 && player.getEquipmentManager().getItemIdAtSlot(4) == 1101 && value42 != 0) {
                    Player player7 = player;
                    player7.packetSender.openSingleDoor(objectId, value2, value32, 0);
                    player7 = player;
                    player7.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3515 ? 1 : -1, true);
                } else {
                    player.getDialogueManager().setDialogueNpcId(609);
                    player.getDialogueManager().showNpcOneLineDialogue("Hey! You are not allowed in there!", 591);
                    player.getDialogueManager().finishDialogue();
                }
                return true;
            }
            Player player8 = player;
            player8.packetSender.openSingleDoor(objectId, value2, value32, 0);
            player8 = player;
            player8.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3515 ? 1 : -1, true);
            return true;
        }
        if (objectId == 2338 && value2 == 3020 && value32 == 3515) {
            if (player.getPosition().getX() < 3020) {
                DialogueManager.continueDialogue(player, 609, 100, 0);
                return true;
            }
            Player player9 = player;
            player9.packetSender.openSingleDoor(objectId, value2, value32, 0);
            player9 = player;
            player9.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3020 ? 1 : -1, 0, true);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 1965 && value2 == 2336) {
            if (value32 == 3) {
                player.getInventoryManager().removeItem(new ItemStack(1965, 1));
                player.setQuestState(this.getQuestId(), 4);
                DialogueManager.continueDialogue(player, 611, 100, 0);
            } else {
                player.packetSender.sendGameMessage("You have no reason to do this.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 608) {
            if (value42 == 0) {
                if (player.getQuestPoints() < 12) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I am the leader of the White Knights of Falador. Why", "do you seek my audience?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("I seek a quest!", "I don't, I'm just looking around.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I seek a quest.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, I need some spy work doing but it's quite", "dangerous. It will involve going into the Black Knights'", "fortress.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showTwoOptions("I laugh in the face of danger!", "I go and cower in a corner at the first sign of danger!");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I laugh in the face of danger!", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well that's good. Don't get too overconfident though.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You've come along at just the right time actually. All of", "my knights are already known to the Black Knights.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Subtlety isn't exactly our strong point.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Can't you just take your White Knights' armour off?", "They wouldn't recognise you then!", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I am afraid our charter prevents us using espionage in", "any form, that is the domain of the Temple Knights.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Temple Knights? Who are they?", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("That information is classified. I am forbidden to share it", "with outsiders.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So... what do you need doing?", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well, the Black Knights have started making strange", "threats to us; demanding large amounts of money and", "land, and threatening to invade Falador if we don't pay", "them.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcOneLineDialogue("Now, NORMALLY this wouldn't be a problem...", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcOneLineDialogue("But they claim to have a powerful new secret weapon.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Your mission, should you decide to accept it, is to", "infiltrate their fortress, find out what their secret", "weapon is, and then sabotage it.", 591);
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcOneLineDialogue("You will be well paid.", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showTwoOptions("Ok, I'll do my best.", "No, I'm not ready to do that.");
                    return true;
                }
                if (value2 == 21) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll do my best.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("I have ruined the Black Knights' invincibility potion.", "That should put a stop to your problem and an end to", "their little schemes.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, we have just received a message from the Black", "Knights saying they withdraw their demands, which", "would seem to confirm your story.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Now I believe there was some talk of a cash reward...", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Absolutely right. Please accept this reward.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("Sir Amik hands you 2500 coins.");
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 609) {
            if (value2 == 100) {
                player.getDialogueManager().showNpcThreeLineDialogue("I wouldn't go in there if I were you. Those Black", "Knights are in an important meeting. They said they'd", "kill anyone who went in there!", 591);
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().showTwoOptions("Okay, I won't.", "I don't care. I'm going in anyway.");
                return true;
            }
            if (value2 == 102) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I don't care. I'm going in anyway.", 591);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 103) {
                Player player2 = player;
                player2.packetSender.openSingleDoor(2338, 3020, 3515, 0);
                player2 = player;
                player2.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3020 ? 1 : -1, 0, true);
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        if (npcId == 611 || npcId == 610 || npcId == 612) {
            if (value2 == 200) {
                player.getDialogueManager().setDialogueNpcId(610);
                player.getDialogueManager().showNpcOneLineDialogue("So... how's the secret weapon coming along?", 591);
                return true;
            }
            if (value2 == 201) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcOneLineDialogue("The invincibility potion is almost ready...", 591);
                return true;
            }
            if (value2 == 202) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcOneLineDialogue("It's taken me FIVE YEARS, but it's almost ready.", 591);
                return true;
            }
            if (value2 == 203) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcTwoLineDialogue("Greldo the Goblin here is just going to fetch the last", "ingredient for me.", 591);
                return true;
            }
            if (value2 == 204) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcTwoLineDialogue("It's a specially grown cabbage grown by my cousin", "Helda who lives in Draynor Manor.", 591);
                return true;
            }
            if (value2 == 205) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcTwoLineDialogue("The soil there is slightly magical and it gives the", "cabbages slight magical properties....", 591);
                return true;
            }
            if (value2 == 206) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcOneLineDialogue("...not to mention the trees!", 591);
                return true;
            }
            if (value2 == 207) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcThreeLineDialogue("Now remember Greldo, only a Draynor Manor", "cabbage will do! Don't get lazy and bring any old", "cabbage, THAT would ENTIRELY wreck the potion!", 591);
                return true;
            }
            if (value2 == 208) {
                player.getDialogueManager().setDialogueNpcId(612);
                player.getDialogueManager().showNpcOneLineDialogue("Yeth, Mithtreth.", 591);
                player.setQuestState(this.getQuestId(), 3);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 100) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcOneLineDialogue("Where has Greldo got to with that magic cabbage!", 591);
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().setDialogueNpcId(610);
                player.getDialogueManager().showNpcOneLineDialogue("What's that noise?", 591);
                return true;
            }
            if (value2 == 102) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcTwoLineDialogue("Hopefully Greldo with the cabbage... yes, look here it", "co....NOOOOOoooo!", 591);
                return true;
            }
            if (value2 == 103) {
                player.getDialogueManager().setDialogueNpcId(611);
                player.getDialogueManager().showNpcOneLineDialogue("My potion!", 591);
                return true;
            }
            if (value2 == 104) {
                player.getDialogueManager().setDialogueNpcId(610);
                player.getDialogueManager().showNpcOneLineDialogue("Oh boy, this doesn't look good!", 591);
                return true;
            }
            if (value2 == 105) {
                player.getDialogueManager().showPlayerTwoLineDialogue("Looks like my work here is done. Seems like that's", "successfully sabotaged their little secret weapon plan.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        return false;
    }
}

