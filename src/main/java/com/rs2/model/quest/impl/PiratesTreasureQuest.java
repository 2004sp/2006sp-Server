package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.BananaCratePromptTask;
import com.rs2.model.quest.impl.HectorsChestMessageTask;
import com.rs2.model.quest.impl.InitialBananaCratePromptTask;
import com.rs2.model.quest.impl.PiratesTreasureDigCompletionEvent;
import com.rs2.model.quest.impl.RumBananaCrateSearchTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class PiratesTreasureQuest
extends QuestScript {
    public PiratesTreasureQuest(int value2) {
        super(10);
        super.setQuestPointReward(2);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Redbeard Frank who", "is at Port Sarim.", "", "There aren't any requirements for this quest."};
        }
        if (value2 == 2) {
            return new String[]{"I should bring a bottle of karamjan rum to Redbeard Frank."};
        }
        if (value2 == 3) {
            return new String[]{"I have stashed a bottle of karamjan rum in the crate.", "I should fill the crate with bananas and talk to Luthas", "when done."};
        }
        if (value2 == 4) {
            return new String[]{"I should go find the crate of bananas in Port Sarim and", "retrieve the karamjan rum."};
        }
        if (value2 == 5) {
            return new String[]{"I should go get white apron to get a job at the store."};
        }
        if (value2 == 6) {
            return new String[]{"I should now search for the crate."};
        }
        if (value2 == 7) {
            return new String[]{"I should bring a bottle of karamjan rum to Redbeard Frank."};
        }
        if (value2 == 8) {
            return new String[]{"I should go to Blue Moon Inn in Varrock and try the key", "to a chest in Hector's old room."};
        }
        if (value2 == 9 || value2 == 10) {
            return new String[]{"I should solve Hector's clue in the message", "to find the treasure."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "2 Quest Points", "450 Coins", "Gold ring", "Emerald"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("2 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("450 Coins", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("Gold ring", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("Emerald", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 450));
        player.getInventoryManager().addOrDropItem(new ItemStack(1635, 1));
        player.getInventoryManager().addOrDropItem(new ItemStack(1605, 1));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1605);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214) {
            if (value2 == 433) {
                itemId = 0;
                while (itemId < 8) {
                    Player player2 = player;
                    player2.packetSender.sendInterfaceText("", itemId + 6968);
                    ++itemId;
                }
                Player player3 = player;
                player3.packetSender.sendInterfaceText("Visit the city of the White Knights.", 6970);
                player3 = player;
                player3.packetSender.sendInterfaceText("In the park, Saradomin points to the X", 6971);
                player3 = player;
                player3.packetSender.sendInterfaceText("which marks the spot.", 6972);
                player3 = player;
                player3.packetSender.showInterface(6965);
                return true;
            }
            if (value2 == 952 && player.getPosition().getY() == 3383 && (player.getPosition().getX() == 2999 || player.getPosition().getX() == 3000)) {
                if (!player.getSingleCombatTimer().hasElapsed()) {
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("You can't dig during combat.");
                    return true;
                }
                if (value32 == 9) {
                    player.setQuestState(this.getQuestId(), 10);
                    itemId = 0;
                    if (NpcDefinition.isDefined(1217)) {
                        Npc npc = new Npc(1217);
                        GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, npc, true, true);
                        npc.getUpdateState().setForcedText("First moles, now this! Take this, vandal!");
                        return true;
                    }
                    Npc npc = Npc.findByDefinitionId(36);
                    if (npc.getInteractionTarget() != null && npc.getInteractionTarget() != player) {
                        itemId = 1;
                    }
                    if (!GameUtil.isNpcWaypointFacingPlayer(player, npc)) {
                        itemId = 1;
                    }
                    if (itemId != 0) {
                        value32 = 10;
                    } else {
                        npc = Npc.findByDefinitionId(36);
                        npc.getUpdateState().setForcedText("First moles, now this! Take this, vandal!");
                        CombatManager.startCombat(npc, player);
                        return true;
                    }
                }
                if (value32 == 10) {
                    player.getUpdateState().setAnimation(830);
                    Player player5 = player;
                    player5.packetSender.sendSoundEffect(232, 1, 0);
                    player5 = player;
                    player5.packetSender.sendGameMessage("You dig into the ground...");
                    itemId = player.nextActionSequence();
                    player.setActiveCycleEvent(new PiratesTreasureDigCompletionEvent(this, player, itemId));
                    CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 2);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2071 && value52 == 3009 && value62 == 3207) {
            if (value72 == 6 && player.ownsItem(431)) {
                player.setQuestState(this.getQuestId(), 7);
            }
            if (value72 >= 6 && value42 == 0) {
                RumBananaCrateSearchTask rumBananaCrateSearchTask = new RumBananaCrateSearchTask(this, 3, player);
                BananaCratePromptTask bananaCratePromptTask = new BananaCratePromptTask(this, 2, player);
                Player player2 = player;
                player2.packetSender.sendGameMessage("There are a lot of bananas in the crate.");
                if (!player.ownsItem(431) && value32 == 1) {
                    World.getTaskScheduler().schedule(rumBananaCrateSearchTask);
                } else if (value32 == 1) {
                    World.getTaskScheduler().schedule(bananaCratePromptTask);
                }
            } else if (value42 == 0 && value32 == 0) {
                InitialBananaCratePromptTask initialBananaCratePromptTask = new InitialBananaCratePromptTask(this, 2, player);
                Player player3 = player;
                player3.packetSender.sendGameMessage("There are a lot of bananas in the crate.");
                World.getTaskScheduler().schedule(initialBananaCratePromptTask);
            } else if (value42 == 1) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1963, 1));
                player.getUpdateState().setAnimation(832);
                Player player4 = player;
                player4.packetSender.sendGameMessage("You take a banana.");
                player.getDialogueManager().finishDialogue();
                player4 = player;
                player4.packetSender.closeInterfaces();
            } else if (value42 == 2) {
                player.getDialogueManager().finishDialogue();
                Player player5 = player;
                player5.packetSender.closeInterfaces();
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2072 && value2 == 2943 && value32 == 3151) {
            if (player.piratesTreasureBananaCrateCount == 0) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("The crate is completely empty.");
            }
            if (player.piratesTreasureBananaCrateCount > 0 && player.piratesTreasureBananaCrateCount < 4) {
                Player player3 = player;
                player3.packetSender.sendGameMessage("There is a few bananas.");
            }
            if (player.piratesTreasureBananaCrateCount >= 4 && player.piratesTreasureBananaCrateCount <= 6) {
                Player player4 = player;
                player4.packetSender.sendGameMessage("The crate is around half full.");
            }
            if (player.piratesTreasureBananaCrateCount > 6 && player.piratesTreasureBananaCrateCount < 10) {
                Player player5 = player;
                player5.packetSender.sendGameMessage("The crate is almost full.");
            }
            if (player.piratesTreasureBananaCrateCount == 10) {
                Player player6 = player;
                player6.packetSender.sendGameMessage("The crate is full.");
            }
            return true;
        }
        if (objectId == 2069 && value2 == 3012 && value32 == 3204) {
            if (value42 == 4) {
                DialogueManager.continueDialogue(player, 557, 100, 0);
            }
            if (value42 == 5) {
                DialogueManager.continueDialogue(player, 557, 108, 0);
            }
            if (value42 >= 6 && player.getEquipmentManager().getContainer().getItemAt(4) == null) {
                DialogueManager.continueDialogue(player, 557, 112, 0);
            } else if (value42 >= 6 && player.getEquipmentManager().getContainer().getItemAt(4).getId() != 1005) {
                DialogueManager.continueDialogue(player, 557, 112, 0);
            } else if (value42 >= 6 && player.getEquipmentManager().getContainer().getItemAt(4).getId() == 1005) {
                Player player7 = player;
                player7.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3012 ? 1 : -1, 0, true);
                player7 = player;
                player7.packetSender.openSingleDoor(2069, 3012, 3204, 0);
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 432 && value2 == 2079 && player.getInventoryManager().containsItem(432)) {
            HectorsChestMessageTask hectorsChestMessageTask = new HectorsChestMessageTask(this, 4, player);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2080, 3218, 3396, 1, 1, 10, 2079, 4), true);
            Player player2 = player;
            player2.packetSender.sendGameMessage("You unlock the chest.");
            player2 = player;
            player2.packetSender.sendGameMessage("All that's in the chest is a message...");
            player.setQuestState(this.getQuestId(), 9);
            World.getTaskScheduler().schedule(hectorsChestMessageTask);
            return true;
        }
        if (objectId == 1963 && value2 == 2072 && player.getInventoryManager().containsItem(1963)) {
            if (player.piratesTreasureBananaCrateCount < 10) {
                ++player.piratesTreasureBananaCrateCount;
                player.getInventoryManager().removeItem(new ItemStack(1963, 1));
                if (player.piratesTreasureBananaCrateCount < 10) {
                    player.getDialogueManager().showOneLineStatement("You pack a banana into the crate.");
                } else {
                    player.getDialogueManager().showTwoLineStatement("You pack a banana into the crate.", "The crate is now full.");
                }
                player.resetInteractionState();
                player.getDialogueManager().finishDialogue();
            } else {
                player.getDialogueManager().showOneLineStatement("The crate is already full.");
                player.resetInteractionState();
                player.getDialogueManager().finishDialogue();
            }
            return true;
        }
        if (objectId == 431 && value2 == 2072 && player.getInventoryManager().containsItem(431)) {
            if (value32 == 2) {
                player.getInventoryManager().removeItem(new ItemStack(431, 1));
                player.getDialogueManager().showOneLineStatement("You stash the rum in the crate.");
                player.setQuestState(this.getQuestId(), 3);
                player.resetInteractionState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value32 == 3) {
                player.getDialogueManager().showOneLineStatement("You have already stashed a bottle of rum there.");
                player.resetInteractionState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 375) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Arr, Matey!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("I'm in search of treasure.", "Arr!", "Do you have anything for trade?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm in search of treasure.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Arr, treasure you be after eh? Well I might be able to", "tell you where to find some... For a price...", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What sort of price?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well for example if you can get me a bottle of rum...", "Not just any rum mind...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'd like some rum made on Karamja Island. There's no", "rum like Karamja Rum!", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showTwoOptions("Ok, I will bring you some rum", "Not right now");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will bring you some rum.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 >= 2) {
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yer a saint, although it'll take a miracle to get it off", "Karamja.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What do you mean?", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcFourLineDialogue("The Customs office has been clampin' down on the", "export of spirits. You seem like a resourceful young", "lass, I'm sure ye'll be able to find a way to slip the stuff", "past them.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Well I'll give it a shot.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("Arr, that's the spirit!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1 && player.getQuestState(this.getQuestId()) >= 8 && !player.ownsItem(432) && !player.ownsItem(433)) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(432, 1));
                    player.getDialogueManager().showItemMessage("Frank hands you a key", new ItemStack(432, 1));
                    player.getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                if (value2 == 1 && player.getQuestState(this.getQuestId()) < 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("Arr, Matey!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Have ye brought some rum for yer ol' mate Frank?", 591);
                    if (player.getInventoryManager().containsItem(431)) {
                        player.getDialogueManager().setNextDialogueStep(3);
                        return true;
                    }
                    player.getDialogueManager().setNextDialogueStep(17);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes, I've got some.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Now a deal's a deal, I'll tell ye about the treasure. I", "used to serve under a pirate captain called One-Eyed", "Hector.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Hector were very succesful and became very rich.", "But about a year ago we were boarded by the Customs", "and Excise Agents.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hector were killed along with many of the crew, I were", "one of the few to escape and I escaped with this.", 591);
                    return true;
                }
                if (value2 == 7 && player.getInventoryManager().containsItem(431)) {
                    player.getInventoryManager().removeItem(new ItemStack(431, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(432, 1));
                    player.setQuestState(this.getQuestId(), 8);
                    player.getDialogueManager().showItemMessage("Frank happily takes the rum... ... and hands you a key", new ItemStack(432, 1));
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("This be Hector's key. I believe it opens his chest in his", "old room in the Blue Moon Inn in Varrock.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("With any luck his treasure will be in there.", 591);
                    player.getDialogueManager().setNextDialogueStep(15);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showTwoOptions("Ok thanks, I'll go and get it.", "So why didn't you ever get it?");
                    return true;
                }
                if (value2 == 16) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok thanks, I'll go and get it.", 591);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showPlayerOneLineDialogue("No, not yet.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcOneLineDialogue("Not surprising, tis no easy task to get it off Karamja.", 591);
                    player.getDialogueManager().setNextDialogueStep(11);
                    return true;
                }
            }
        }
        if (npcId != 557) return false;
        if (value2 == 100) {
            player.getDialogueManager().showNpcTwoLineDialogue("Hey, you can't go in there. Only employees of the", "grocery store can go in.", 591);
            return true;
        }
        if (value2 == 101) {
            player.getDialogueManager().showTwoOptions("Well, can I get a job here?", "Sorry, I didn't realise.");
            return true;
        }
        if (value2 == 102) {
            if (value32 == 1) {
                player.getDialogueManager().showPlayerOneLineDialogue("Can I get a job here?", 591);
                player.getDialogueManager().setNextDialogueStep(103);
                return true;
            }
            player.getDialogueManager().finishDialogue();
            return false;
        }
        if (value2 == 103) {
            player.getDialogueManager().showNpcTwoLineDialogue("Well, you're keen, I'll give you that. Okay, I'll give you", "a go. Have you got your own white apron?", 591);
            if (player.getEquipmentManager().getContainer().getItemAt(4) != null || player.getInventoryManager().containsItem(1005)) {
                if (player.getEquipmentManager().getContainer().getItemAt(4) == null && player.getInventoryManager().containsItem(1005)) {
                    player.getDialogueManager().setNextDialogueStep(104);
                    return true;
                }
                if (player.getEquipmentManager().getContainer().getItemAt(4).getId() == 1005 || player.getInventoryManager().containsItem(1005)) {
                    player.getDialogueManager().setNextDialogueStep(104);
                    return true;
                }
            }
            player.getDialogueManager().setNextDialogueStep(106);
            return true;
        }
        if (value2 == 104) {
            player.getDialogueManager().showPlayerOneLineDialogue("Yes, I have one right here.", 591);
            return true;
        }
        if (value2 == 105) {
            player.getDialogueManager().showNpcTwoLineDialogue("Wow - you are well prepared! You're hired. Go through", "to the back and tidy up for me, please.", 591);
            player.setQuestState(this.getQuestId(), 6);
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (value2 == 106) {
            player.getDialogueManager().showPlayerOneLineDialogue("No, I don't.", 591);
            return true;
        }
        if (value2 == 107) {
            player.getDialogueManager().showNpcOneLineDialogue("Well, if you find one come back.", 591);
            player.setQuestState(this.getQuestId(), 5);
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (value2 == 108) {
            player.getDialogueManager().showNpcOneLineDialogue("Have you found the apron yet?", 591);
            if (player.getEquipmentManager().getContainer().getItemAt(4) != null || player.getInventoryManager().containsItem(1005)) {
                if (player.getEquipmentManager().getContainer().getItemAt(4) == null && player.getInventoryManager().containsItem(1005)) {
                    player.getDialogueManager().setNextDialogueStep(110);
                    return true;
                }
                if (player.getEquipmentManager().getContainer().getItemAt(4).getId() == 1005 || player.getInventoryManager().containsItem(1005)) {
                    player.getDialogueManager().setNextDialogueStep(110);
                    return true;
                }
            }
            player.getDialogueManager().setNextDialogueStep(109);
            return true;
        }
        if (value2 == 109) {
            player.getDialogueManager().showPlayerOneLineDialogue("No, I'm still looking.", 591);
            player.getDialogueManager().setNextDialogueStep(107);
            return true;
        }
        if (value2 == 110) {
            player.getDialogueManager().showPlayerOneLineDialogue("Yes, I have.", 591);
            return true;
        }
        if (value2 == 111) {
            player.getDialogueManager().showNpcTwoLineDialogue("Ok, You're hired. Go through to the back and", "tidy up for me, please.", 591);
            player.setQuestState(this.getQuestId(), 6);
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (value2 != 112) return false;
        player.getDialogueManager().showNpcTwoLineDialogue("Hey! Employees need to wear their", "white apron!", 591);
        player.getDialogueManager().finishDialogue();
        return true;
    }
}

