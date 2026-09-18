package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestConstants;
import com.rs2.model.quest.QuestScript;

public final class ShieldOfArravQuest
extends QuestScript {
    private int shieldOfArravBookLastPageIndex = 2;

    public ShieldOfArravQuest(int value2) {
        super(16);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Reldo in Varrock's", "Palace library, or by speaking to Charlie the Tramp near", "the Blue Moon Inn in Varrock.", "I will need a friend to help me and some combat experience", "may be an advantage."};
        }
        if (value2 == 2) {
            return new String[]{"Reldo says there is a quest hidden in one of the books in", "his library somewhere. I should look for it and see."};
        }
        if (value2 == 3) {
            return new String[]{"I should show the book to Reldo now."};
        }
        if (value2 == 4) {
            return new String[]{"Reldo told me to talk to baraek about Phoenix Gang."};
        }
        if (value2 == 5) {
            return new String[]{"Baraek gave me the location of the Phoenix Gang HQ, and", "revealed they are operating under the name of", "VTAM corporation."};
        }
        if (value2 == 99) {
            return new String[]{"I spoke to Charlie the tramp, who gave me the location", "of the Black Arm gang HQ."};
        }
        if (value2 == 6 && player.gangAffiliation == 1) {
            return new String[]{"Katrine said if I wanted to join the Black Arm Gang, I'd", "have to steal two Phoenix crossbows from the rival gang.", "Maybe Charlie the tramp can give me some ideas about", "how to do this."};
        }
        if (value2 == 7 && player.gangAffiliation == 1) {
            return new String[]{"I should now enter the Black Arm Gang HQ and search", "for the shield."};
        }
        if (value2 == 6 && player.gangAffiliation == 2) {
            if (!player.ownsItem(761)) {
                return new String[]{"I was told to kill Jonny the Beard in Blue Moon Inn", "and bring back intelligence report to Phoenix Gang HQ", "from him."};
            }
            return new String[]{"I should bring the intelligence report back to", "Phoenix Gang HQ."};
        }
        if (value2 == 7 && player.gangAffiliation == 2) {
            return new String[]{"I should now enter the Phoenix Gang HQ and search", "for the shield."};
        }
        if (value2 == 8) {
            if (!player.ownsItem(769)) {
                return new String[]{"Either you or your friend should now go talk to", "curator of Varrock museum with both of the shield", "halves with him."};
            }
            return new String[]{"I should now go show the certificate to King roald", "to claim my reward."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "600 Coins"};
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
        player2.packetSender.sendInterfaceText("600 Coins", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 600));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 767);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleGroundItemInteraction(Player player, int itemId, int value2) {
        if (itemId == 767) {
            Npc npc = Npc.findByDefinitionId(643);
            if (!(player.isWithinReach(npc, 10) && player.isOverlapping(npc) || player.getPosition().getPlane() == npc.getPosition().getPlane())) {
                return false;
            }
            if (!npc.isDead() && player.gangAffiliation == 1) {
                player.getDialogueManager().setDialogueNpcId(643);
                player.getDialogueManager().showNpcOneLineDialogue("Stop! Thief!", 591);
                player.getDialogueManager().finishDialogue();
                CombatManager.startCombat(npc, player);
                return true;
            }
            if (!npc.isDead() && player.gangAffiliation == 2) {
                player.getDialogueManager().setDialogueNpcId(643);
                player.getDialogueManager().showNpcOneLineDialogue("Hey, you are not allowed to take it!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2398 && value2 == 3251 && value32 == 3385) {
            if (player.getInventoryManager().containsItem(759)) {
                Player player2 = player;
                player2.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3386 ? 1 : -1, true);
                player2 = player;
                player2.packetSender.openSingleDoor(2398, 3251, 3385, 0);
                player2 = player;
                player2.packetSender.sendGameMessage("You unlock the door.");
                return true;
            }
            Player player3 = player;
            player3.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2397 && value2 == 3247 && value32 == 9779) {
            if (player.gangAffiliation == 2 && (player.getQuestState(this.getQuestId()) == 1 || player.getQuestState(this.getQuestId()) >= 7)) {
                Player player4 = player;
                player4.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9780 ? 1 : -1, true);
                player4 = player;
                player4.packetSender.openSingleDoor(2397, 3247, 9779, 0);
                player4 = player;
                player4.packetSender.sendGameMessage("The door automatically opens for you.");
                return true;
            }
            player.getDialogueManager().setDialogueNpcId(644);
            player.getDialogueManager().showNpcTwoLineDialogue("Hey! You can't go in there. Only authorised personnel", "of the VTAM Corporation are allowed beyond this point.", 591);
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId == 2399 && value2 == 3185 && value32 == 3388) {
            if (player.gangAffiliation == 1 && (player.getQuestState(this.getQuestId()) == 1 || player.getQuestState(this.getQuestId()) >= 7)) {
                Player player5 = player;
                player5.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3388 ? 1 : -1, true);
                player5 = player;
                player5.packetSender.openSingleDoor(2399, 3185, 3388, 0);
                player5 = player;
                player5.packetSender.sendGameMessage("You hear heavy bolts being drawn back as the door is unlocked.");
                return true;
            }
            Player player6 = player;
            player6.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2403 && value2 == 3235 && value32 == 9761) {
            ObjectManager.getInstance().removeDynamicObjectAt(3235, 9761, 0, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2404, 3235, 9761, 0, 0, 10, 2403, 999999999), true);
            return true;
        }
        if (objectId == 2400 && (value2 == 3188 || value2 == 3189) && value32 == 3385) {
            ObjectManager.getInstance().removeDynamicObjectAt(3189, 3385, 1, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2401, value2, 3385, 1, 2, 10, 2400, 999999999), true);
            return true;
        }
        if (objectId == 2401 && (value2 == 3188 || value2 == 3189) && value32 == 3385) {
            ObjectManager.getInstance().removeDynamicObjectAt(3189, 3385, 1, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2400, value2, 3385, 1, 2, 10, 2400, 999999999), true);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2404 && value2 == 3235 && value32 == 9761) {
            ObjectManager.getInstance().removeDynamicObjectAt(3235, 9761, 0, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2403, 3235, 9761, 0, 0, 10, 2403, 999999999), true);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2402 && value52 == 3212 && value62 == 3493 && value8 == 2 && value72 >= 2 && !player.ownsItem(757)) {
            if (value32 == 1) {
                player.getDialogueManager().showPlayerTwoLineDialogue("Aha! 'The Shield Of Arrav'! Exactly what I was looking", "for.", 591);
            }
            if (value32 == 2) {
                player.getDialogueManager().showOneLineStatement("You take the book from the bookcase.");
            }
            if (value32 == 3) {
                player.getInventoryManager().addOrDropItem(new ItemStack(757, 1));
                player.getDialogueManager().finishDialogue();
                Player player2 = player;
                player2.packetSender.closeInterfaces();
                player.setQuestState(this.getQuestId(), 3);
            }
            return true;
        }
        if (value22 == 2404 && value52 == 3235 && value62 == 9761 && value8 == 1 && !player.ownsItem(763) && value72 != 1) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("You search the chest.");
            }
            if (value32 == 2) {
                player.getDialogueManager().showItemMessage("You find half of a shield, which you take.", new ItemStack(763, 1));
            }
            if (value32 == 3) {
                player.getInventoryManager().addOrDropItem(new ItemStack(763, 1));
                player.setQuestState(this.getQuestId(), 8);
                player.getDialogueManager().finishDialogue();
                Player player3 = player;
                player3.packetSender.closeInterfaces();
            }
            return true;
        }
        if (!(value22 != 2401 || value52 != 3188 && value52 != 3189 || value62 != 3385 || value8 != 2 || player.ownsItem(765) || value72 == 1)) {
            if (value32 == 1) {
                player.getDialogueManager().showItemMessage("You find half of a shield, which you take.", new ItemStack(765, 1));
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(765, 1));
                player.setQuestState(this.getQuestId(), 8);
                player.getDialogueManager().finishDialogue();
                Player player4 = player;
                player4.packetSender.closeInterfaces();
            }
            return true;
        }
        return false;
    }

    private void showShieldOfArravBookPage(Player player, int value4) {
        String[] stringValues;
        Player player2;
        Player player3 = player2 = player;
        player2.packetSender.sendInterfaceText("", 14165);
        player3 = player2;
        player3.packetSender.sendInterfaceText("", 14166);
        int value2 = 843;
        while (value2 <= 864) {
            player3 = player2;
            player3.packetSender.sendInterfaceText("", value2);
            ++value2;
        }
        int value3 = value4;
        if (value3 == 0) {
            String[] stringValues2 = new String[]{"The Shield of Arrav", "", "", "The Shield of Arrav", "", "by A. R. Wright", "", "", "", "Arrav is probably the best", "known hero of the 4th", "age. Many legends are", "told of his heroics. One", "surviving artefact from", "the 4th age is a fabulous", "shield.", "", "This shield is believed to", "have once belonged to", "Arrav and is now indeed", "known as the Shield of", "Arrav. For over 150", "years it was the prize", "piece in the royal", "museum of Varrock."};
            stringValues = stringValues2;
        } else if (value3 == 1) {
            String[] stringValues3 = new String[]{"The Shield of Arrav", "", "", "However, in the year 143", "of the fifth age a gang of", "thieves called the Phoenix", "Gang broke into the", "museum and stole the", "shield in a daring raid. As", "a result, the current", "ruler, King Roald, put a", "1200 gold bounty (a", "massive sum of money in", "those days) on the return", "of the shield, hoping that", "one of the culprits would", "betray his fellows out of", "greed."};
            stringValues = stringValues3;
        } else {
            String[] stringValues4;
            stringValues = value3 == 2 ? (stringValues4 = new String[]{"The Shield of Arrav", "", "", "This tactic did not work", "however, and the thieves", "who stole the shield have", "since gone on to become", "the most powerful crime", "gang in Varrock, despite", "making an enemy of the", "Royal Family many", "years ago.", "", "", "The reward for the", "return of the shield still", "stands."}) : null;
        }
        String[] stringValues5 = stringValues;
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues5[0], 903);
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues5[1], 14165);
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues5[2], 14166);
        value3 = 3;
        while (value3 < stringValues5.length) {
            player3 = player;
            player3.packetSender.sendInterfaceText((String)stringValues5[value3], value3 + 843 - 3);
            ++value3;
        }
        player3 = player;
        player3.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == 0 ? 1 : 0, 840);
        player3 = player;
        player3.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == this.shieldOfArravBookLastPageIndex ? 1 : 0, 842);
    }

    @Override
    public final boolean handleButtonClick(Player player, int buttonId, int value2) {
        if (player.activeBookItemId == 757) {
            if (buttonId == 841 && player.activeBookPageIndex < this.shieldOfArravBookLastPageIndex) {
                ++player.activeBookPageIndex;
                this.showShieldOfArravBookPage(player, player.activeBookPageIndex);
                return true;
            }
            if (buttonId == 839 && player.activeBookPageIndex > 0) {
                --player.activeBookPageIndex;
                this.showShieldOfArravBookPage(player, player.activeBookPageIndex);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214 && value2 == 757) {
            this.showShieldOfArravBookPage(player, 0);
            player.activeBookItemId = value2;
            player.activeBookPageIndex = 0;
            player.packetSender.showInterface(837);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == QuestConstants.RELDO_NPC_ID) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello stranger.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("I'm in search of a quest.", "Do you have anything to trade?", "What do you do?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm in search of a quest.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hmmm. I don't... believe there are any here...", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Let me think actually...", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah yes. I know. If you look in a book called 'The Shield", "of Arrav', you'll find a quest in there.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm not sure where the book is mind you... but I'm", "sure it's around here somewhere.", 591);
                    this.startQuest(player);
                    return true;
                }
            }
            if (value42 == 2 && value2 == 8) {
                player.getDialogueManager().showPlayerOneLineDialogue("Thank you.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Ok. I've read the book. Do you know where I can find", "the Phoenix Gang?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("No, I don't. I think I know someone who might", "however.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("If I were you I would talk to Baraek, the fur trader in", "the market place. I've heard he has connections with the", "Phoenix Gang.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thanks, I'll try that!", 591);
                    player.getDialogueManager().finishDialogue();
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
            }
        }
        if (npcId == 547) {
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showThreeOptions("Can you tell me where I can find the Phoenix Gang?", "Can you sell me some furs?", "Hello. I am in search of a quest.");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Can you tell me where I can find the Phoenix Gang?", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Sh sh sh, not so loud! You don't want to get me in", "trouble!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So DO you know where they are?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("I may do.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("But I don't want to get into trouble for revealing their", "hideout.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Of course, if I was, say 20 gold coins richer I may", "happen to be more inclined to take that sort of risk...", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showThreeOptions("Okay. Have 20 gold coins.", "No. I don't like things like bribery.", "Yes. I'd like to be 20 gold coins richer too.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Okay. Have 20 gold coins.", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                if (value2 == 10) {
                    if (!player.getInventoryManager().containsItemAmount(995, 20)) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    player.getDialogueManager().showNpcFourLineDialogue("Ok, to get to the gang hideout, enter Varrock through", "the south gate. Then, if you take the first turning east,", "somewhere along there is an alleyway to the south. The", "door at the end of there is the entrance to the Phoenix", 591);
                    player.getInventoryManager().removeItem(new ItemStack(995, 20));
                    player.setQuestState(this.getQuestId(), 5);
                    return true;
                }
            }
            if (value42 == 5) {
                if (value2 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Gang. They're operating there under the name of the", "VTAM Corporation. Be careful. The Phoenixes ain't", "the types to be messed about.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thanks!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 641 && (value42 == 0 || value42 >= 2 && value42 < 6)) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Spare some change guv?", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showFiveOptions("Who are you?", "Sorry, I haven't got any.", "Go get a job!", "Ok. Here you go.", "Is there anything down this alleyway?");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Is there anything down this alleyway?", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("Funny you should mention that...there is actually.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("The ruthless and notorious criminal gang known as the", "Black Arm Gang have their headquarters down there.", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showTwoOptions("Thanks for the warning!", "Do you think they would let me join?");
                return true;
            }
            if (value2 == 7) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Do you think they would let me join?", 591);
                    player.getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(6);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcTwoLineDialogue("You never know. You'll find a lady down there called", "Katrine. Speak to her.", 591);
                return true;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcOneLineDialogue("But don't upset her, she's pretty dangerous.", 591);
                player.getDialogueManager().finishDialogue();
                if (player.getQuestState(this.getQuestId()) == 0) {
                    this.startQuest(player);
                    player.setQuestState(this.getQuestId(), 99);
                }
                return true;
            }
        }
        if (npcId == 644) {
            if (value42 == 5) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What's through that door?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hey! You can't go in there. Only authorised personnel", "of the VTAM Corporation are allowed beyond this point.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showThreeOptions("I know who you are!", "How do I get a job with the VTAM corporation?", "Why not?");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I know who you are!", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Really.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well?", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Who are we then?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("This is the headquarters of the Phoenix Gang, the most", "powerful crime syndicate this city has ever seen!", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("No, this is a legitimate business run by legitimate", "businessmen.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Supposing we were this crime gang however, what would", "you want with us?", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showTwoOptions("I'd like to offer you my services.", "I want nothing. I was just making sure you were them.");
                    return true;
                }
                if (value2 == 12) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'd like to offer you my services.", 591);
                        player.getDialogueManager().setNextDialogueStep(13);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(11);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcOneLineDialogue("You mean you'd like to join the Phoenix Gang?", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, obviously I can't speak for them, but the Phoenix", "Gang doesn't let people join just like that.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcOneLineDialogue("You can't be too careful, you understand.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Generally someone has to prove their loyalty before", "they can join.", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How would I go about doing that?", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcOneLineDialogue("Obviously, I would have no idea about that.", 591);
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcFourLineDialogue("Although having said that, a rival gang of ours, er,", "theirs, called the Black Arm Gang is supposedly meeting", "a contact from Port Sarim today in the Blue Moon", "Inn.", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The Blue Moon Inn is just by the south entrance to", "this city, and supposedly the name of the contact is", "Jonny the Beard.", 591);
                    return true;
                }
                if (value2 == 21) {
                    player.getDialogueManager().showNpcFourLineDialogue("OBVIOUSLY I know NOTHING about the dealings", "of the Phoenix Gang, but I bet if SOMEBODY were", "to kill him and bring back his intelligence report, they", "would be considered loyal enough to join.", 591);
                    return true;
                }
                if (value2 == 22) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll get right on it.", 591);
                    player.getDialogueManager().finishDialogue();
                    player.setQuestState(this.getQuestId(), 6);
                    player.gangAffiliation = 2;
                    return true;
                }
            }
            if (value42 == 6 && player.gangAffiliation == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("How's your little mission going?", 591);
                    if (player.getInventoryManager().containsItem(761)) {
                        player.getDialogueManager().setNextDialogueStep(3);
                    } else {
                        player.getDialogueManager().setNextDialogueStep(2);
                    }
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm still working on it.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have the intelligence report!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Let's see it then.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("You hand over the report. The man reads the report.");
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes. Yes, this is very good.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ok! You can join the Phoenix Gang! I am Straven, one", "of the gang leaders.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Nice to meet you.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Take this key.", 591);
                    return true;
                }
                if (value2 == 10) {
                    if (player.getInventoryManager().containsItem(761)) {
                        player.getInventoryManager().removeItem(new ItemStack(761, 1));
                        player.getInventoryManager().addOrDropItem(new ItemStack(759, 1));
                        player.getDialogueManager().showItemMessage("Straven hands you a key.", new ItemStack(759, 1));
                        player.setQuestState(this.getQuestId(), 7);
                        player.getDialogueManager().setNextDialogueStep(2);
                    } else {
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
            }
            if (value42 >= 7 && player.gangAffiliation == 2) {
                if (value2 == 1 && !player.ownsItem(759)) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(759, 1));
                    player.getDialogueManager().showItemMessage("Straven hands you a key.", new ItemStack(759, 1));
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("This key will give you access to our weapons supply", "depot round the front of this building.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 642) {
            if (value42 >= 2 && value42 < 6 || value42 == 99) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What is this place?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("It's a private business. Can I help you at all?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showThreeOptions("I've heard you're the Black Arm Gang.", "What sort of business?", "I'm looking for fame and riches.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I've heard you're the Black Arm Gang.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Who told you that?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showThreeOptions("I'd rather not reveal my sources.", "It was Charlie, the tramp outside.", "Everyone knows - it's no great secret.");
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("It was Charlie, the tramp outside.", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Is that guy still out there? He's getting to be a", "nuisance. Remind me to send someone to kill him.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("So now you've found us, what do you want?", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showThreeOptions("I want to become a member of your gang.", "I want some hints for becoming a thief.", "I'm looking for the door out of here.");
                    return true;
                }
                if (value2 == 11) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I want to become a member of your gang.", 591);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("How unusual.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Normally we recruit for our gang by watching local", "thugs and thieves in action. People don't normally waltz", "in here saying 'hello, can I play'.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("How can I be sure you can be trusted?", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showTwoOptions("Well, you can give me a try can't you?", "Well, people tell me I have an honest face.");
                    return true;
                }
                if (value2 == 16) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Well, you can give me a try can't you?", 591);
                        player.getDialogueManager().setNextDialogueStep(17);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(15);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcOneLineDialogue("I'm not so sure.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thinking about it... I may have a solution actually.", 591);
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Our rival gang - the Phoenix Gang - has a weapons", "stash a little east of here.", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showNpcThreeLineDialogue("We're fresh out of crossbows, so if you could steal a", "couple of crossbows for us it would be very much", "appreciated.", 591);
                    return true;
                }
                if (value2 == 21) {
                    player.getDialogueManager().showNpcOneLineDialogue("Then I'll be happy to call you a Black Arm.", 591);
                    return true;
                }
                if (value2 == 22) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Sounds simple enough. Any particular reason you need", "two of them?", 591);
                    return true;
                }
                if (value2 == 23) {
                    player.getDialogueManager().showNpcFourLineDialogue("I have an idea for framing a local merchant who is", "refusing to pay our, very reasonable, 'keep-your-life-", "pleasant' insurance rates. I need two phoenix crossbows;", "one to kill somebody important with and other to", 591);
                    return true;
                }
                if (value2 == 24) {
                    player.getDialogueManager().showNpcFourLineDialogue("hide in the merchant's house where the local law can", "find it! When they find it, they'll suspect him of", "murdering the target for the Phoenix gang and,", "hopefully, arrest the whole gang! Leaving us as the only", 591);
                    return true;
                }
                if (value2 == 25) {
                    player.getDialogueManager().showNpcOneLineDialogue("thieves gang in Varrock! Brilliant, eh?", 591);
                    return true;
                }
                if (value2 == 26) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yeah, brilliant. So who are you planning to murder?", 591);
                    return true;
                }
                if (value2 == 27) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I haven't decided yet, but it'll need to be somebody", "important. Say, why you being so nosey? You aren't", "with the law are you?", 591);
                    return true;
                }
                if (value2 == 28) {
                    player.getDialogueManager().showPlayerOneLineDialogue("No, no! Just curious.", 591);
                    return true;
                }
                if (value2 == 29) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You'd better just keep your mouth shut about this plan,", "or I'll make sure it stays shut for you. Now, are you", "going to go get those crossbows or not?", 591);
                    return true;
                }
                if (value2 == 30) {
                    player.getDialogueManager().showTwoOptions("Ok, no problem.", "Sounds a little tricky. Got anything easier?");
                    return true;
                }
                if (value2 == 31) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, no problem.", 591);
                        player.getDialogueManager().setNextDialogueStep(32);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(30);
                    return true;
                }
                if (value2 == 32) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Great! You'll find the Phoenix gang's weapon stash just", "next to a temple, due east of here.", 591);
                    player.getDialogueManager().finishDialogue();
                    player.setQuestState(this.getQuestId(), 6);
                    player.gangAffiliation = 1;
                    return true;
                }
            }
            if (value42 == 6 && player.gangAffiliation == 1) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Have you got those crossbows for me yet?", 591);
                    if (player.getInventoryManager().containsItemAmount(767, 2)) {
                        player.getDialogueManager().setNextDialogueStep(3);
                    } else {
                        player.getDialogueManager().setNextDialogueStep(2);
                    }
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Not yet.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes, I have.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showOneLineStatement("You give the crossbows to Katrine.");
                    return true;
                }
                if (value2 == 5) {
                    if (player.getInventoryManager().containsItemAmount(767, 2)) {
                        player.getInventoryManager().removeItem(new ItemStack(767, 2));
                        player.getDialogueManager().showNpcTwoLineDialogue("Ok. You can join our gang now. Feel free to enter", "any of the rooms of the ganghouse.", 591);
                        player.getDialogueManager().finishDialogue();
                        player.setQuestState(this.getQuestId(), 7);
                    } else {
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
            }
        }
        if (npcId == 646 && value42 == 8) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Welcome to the museum of Varrock.", 591);
                return true;
            }
            if (value2 == 2) {
                if (player.getInventoryManager().containsItemAmount(763, 1) && player.getInventoryManager().containsItemAmount(765, 1)) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I have the shield of Arrav here. Can I get a", "reward?", 591);
                } else {
                    player.getDialogueManager().finishDialogue();
                    Player player2 = player;
                    player2.packetSender.closeInterfaces();
                }
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showNpcThreeLineDialogue("The Shield of Arrav! Goodness, the Museum has been", "searching for that for years! The late King Roald II", "offered a reward for it years ago!", 591);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showPlayerOneLineDialogue("Well, I'm here to claim it.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("Let me have a look at it first.", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showOneLineStatement("The curator peers at the shield.");
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showNpcOneLineDialogue("This is incredible!", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcOneLineDialogue("That shield has been missing for over twenty-five years!.", 591);
                return true;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcThreeLineDialogue("Leave the shield here with me and I'll write you out a", "certificate saying that you have returned the shield, so", "that you can claim your reward from the King.", 591);
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showPlayerOneLineDialogue("Can I have two certificates please?", 591);
                return true;
            }
            if (value2 == 11) {
                player.getDialogueManager().showNpcOneLineDialogue("Yes, certainly. Please hand over the shield.", 591);
                return true;
            }
            if (value2 == 12) {
                player.getDialogueManager().showOneLineStatement("You hand over the shield halves.");
                return true;
            }
            if (value2 == 13) {
                if (player.getInventoryManager().containsItemAmount(763, 1) && player.getInventoryManager().containsItemAmount(765, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(763, 1));
                    player.getInventoryManager().removeItem(new ItemStack(765, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(769, 2));
                    player.getDialogueManager().showItemMessage("The curator writes out two certificates.", new ItemStack(769, 1));
                } else {
                    player.getDialogueManager().finishDialogue();
                }
                return true;
            }
            if (value2 == 14) {
                player.getDialogueManager().finishDialogue();
                Player player3 = player;
                player3.packetSender.closeInterfaces();
            }
        }
        if (npcId == 648 && value42 == 8) {
            if (value2 == 1) {
                player.getDialogueManager().showPlayerOneLineDialogue("Greetings, your majesty.", 591);
                return true;
            }
            if (value2 == 2) {
                if (player.getInventoryManager().containsItemAmount(769, 1)) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Your majesty, I have come to claim the reward for the", "return of the Shield Of Arrav.", 591);
                } else {
                    player.getDialogueManager().finishDialogue();
                    Player player4 = player;
                    player4.packetSender.closeInterfaces();
                }
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showItemMessage("You show the certificate to the king.", new ItemStack(769, 1));
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("My goodness! This claim is for the reward offered by", "my father many years ago!", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("I never thought I would live to see the day when", "someone came forward to claim this reward!", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcTwoLineDialogue("I heard that you found half the shield, so I will give", "you half of the bounty. That comes to exactly 600gp!", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showItemMessage("You hand over the certificate. The king gives you 600gp.", new ItemStack(995, 600));
                return true;
            }
            if (value2 == 8) {
                if (player.getInventoryManager().containsItemAmount(769, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(769, 1));
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().markDialogueInactive();
                } else {
                    player.getDialogueManager().finishDialogue();
                    Player player5 = player;
                    player5.packetSender.closeInterfaces();
                }
                return true;
            }
        }
        return false;
    }
}

