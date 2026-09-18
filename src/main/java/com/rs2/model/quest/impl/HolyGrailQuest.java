package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;

public final class HolyGrailQuest
extends QuestScript {
    private final RectangularArea realmCrossingArea = new RectangularArea(2740, 3234, 2743, 3237, 0);

    public HolyGrailQuest(int value2) {
        super(51);
        super.setQuestPointReward(2);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            if (player.getQuestState(61) == 1) {
                return new String[]{"I can start this quest by speaking to King Arthur at", "Camelot Castle, just North West of Catherby", "To complete this quest I must be able to defeat a Level", "120 Black Knight Titan."};
            }
            return new String[]{"To start this quest I need to:", String.valueOf(player.getQuestState(61) == 1 ? "@str@" : "") + "-Complete The Merlin's Crystal quest", "", "To complete this quest I need:", "-To be able to defeat a level 120 Black Knight Titan."};
        }
        if (value2 == 2) {
            return new String[]{"I should go ask Merlin for clues about Holy Grail."};
        }
        if (value2 == 3) {
            return new String[]{"Merlin told me that I should speak someone on the holy", "island, and could also go speak to Sir Galahad who lives", "west of McGrubor's Wood."};
        }
        if (value2 == 4) {
            return new String[]{"I need a magic whistle, which can be found inside", "a haunted manor, then I need to find the point of", "realm crossing."};
        }
        if (value2 == 5) {
            return new String[]{"I should find a way to enter the castle."};
        }
        if (value2 == 6) {
            return new String[]{"I should go look for Fisher King's son Percival", "who also was a knight of the round table."};
        }
        if (value2 == 7) {
            return new String[]{"King Arthur gave me a magic gold feather, which", "should assist me on finding Percival."};
        }
        if (value2 == 8) {
            return new String[]{"I should now go back to Fisher King's realm to", "meet with Percival."};
        }
        if (value2 == 9) {
            return new String[]{"I should now return the Holy Grail to King Arhur", "in Camelot."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "2 Quest Points", "11,000 Prayer XP", "15,300 Defence XP"};
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
        player2.packetSender.sendInterfaceText("11,000 Prayer XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("15,300 Defence XP", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(5, 11000.0);
        player.getSkillManager().addQuestExperience(1, 15300.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 19);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (player.getQuestState(61) != 1) {
            return false;
        }
        if (value22 == 23 && value52 == 2962 && value62 == 3506 && value8 == 2) {
            if (value72 != 7 || !player.getInventoryManager().containsItem(16)) {
                return false;
            }
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("You hear muffled noises from the sack. You open the sack.");
                return true;
            }
            if (value32 == 2) {
                Npc npc = new Npc(211);
                GameplayHelper.spawnOwnedNpcAtPosition(player, new Position(2961, 3505, 0), npc, false, false);
                player.getUpdateState().setFaceEntity(npc.getEncodedIndex());
                npc.getUpdateState().setFaceEntity(player.getEncodedIndex());
                DialogueManager.continueDialogue(player, 211, 100, 0);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214) {
            if (value2 == 16 && value32 >= 4) {
                itemId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
                if (this.realmCrossingArea.contains(player.getPosition())) {
                    if (value32 < 8 && value32 != 1) {
                        player.moveTo(new Position(2805, 4715, 0));
                    } else {
                        player.moveTo(new Position(2678, 4716, 0));
                    }
                    return true;
                }
                if (itemId == 10569 || itemId == 11081) {
                    player.moveTo(new Position(2741, 3235, 0));
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("The whistle makes no noise. It will not work in this location.");
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 17 && (player.getPosition().getX() == 2761 || player.getPosition().getX() == 2762) && player.getPosition().getY() == 4694) {
                DialogueManager.continueDialogue(player, 210, 100, 0);
                return true;
            }
            if (value2 == 18 && value32 == 7) {
                itemId = player.getPosition().getX() - 2962;
                value2 = player.getPosition().getY() - 3506;
                if (Math.abs(itemId) > Math.abs(value2)) {
                    player.packetSender.sendGameMessage("The feather points to the " + (itemId < 0 ? "east" : "west") + ".");
                } else {
                    player.packetSender.sendGameMessage("The feather points to the " + (value2 < 0 ? "north" : "south") + ".");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player objectId, int objectId2, int value2, int value32, int value42) {
        if (objectId2 == 24 && value2 == 2764 && value32 == 3503) {
            if (((Player)objectId).getQuestState(61) == 1) {
                Player player = objectId;
                player.packetSender.openSingleDoor(objectId2, value2, value32, 1);
                player = objectId;
                player.packetSender.queueRelativeMovementStep(((Entity)objectId).getPosition().getX() < 2765 ? 1 : -1, 0, true);
            } else {
                Player player = objectId;
                player.packetSender.sendGameMessage("The door won't open.");
            }
            return true;
        }
        if (objectId2 == 22 && value2 == 3106 && value32 == 3361) {
            Player player = objectId;
            player.packetSender.openSingleDoor(objectId2, value2, value32, 2);
            player = objectId;
            player.packetSender.queueRelativeMovementStep(0, ((Entity)objectId).getPosition().getY() < 3362 ? 1 : -1, true);
            if (((Player)objectId).getQuestState(61) == 1 && ((Player)objectId).getInventoryManager().containsItem(15) && !((Player)objectId).ownsItemAmount(16, 2) && value42 >= 4) {
                GroundItem groundItem = new GroundItem(new ItemStack(16, 1), (Entity)objectId, new Position(3107, 3359, 2));
                GroundItemManager.getInstance().spawn(groundItem);
                if (value42 < 8) {
                    GroundItemManager.getInstance().spawn(groundItem);
                }
            }
            return true;
        }
        if (((Player)objectId).getQuestState(61) != 1) {
            return false;
        }
        if (objectId2 == 23 && value2 == 2962 && value32 == 3506 && value42 == 7) {
            ((Player)objectId).getDialogueManager().showOneLineStatement("You hear a muffled groan. The sack wriggles slightly.");
            ((Player)objectId).getDialogueManager().finishDialogue();
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc() && entity.isPlayer()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 221) {
                if (((Player)entity).getEquipmentManager().getItemIdAtSlot(3) != 35) {
                    ((Npc)entity2).setCurrentHitpoints(((Npc)entity2).getMaxHitpoints());
                } else {
                    entity2.setDead(true);
                    CombatManager.handleDeath(entity2);
                    entity2 = entity;
                    ((Player)entity2).packetSender.queueRelativeMovementStep(-2, 0, true);
                    entity2 = entity;
                    ((Player)entity2).packetSender.sendGameMessage("Well done! You have defeated the Black Knight Titan!");
                    ((Player)entity).setQuestState(this.getQuestId(), 5);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        return npcId != 221 || value2 == 4;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (player.getQuestState(61) != 1) {
            return false;
        }
        if (npcId == 251) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Now I am a knight of the round table, do you have", "any more quests for me?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Aha! I'm glad you are here! I am sending out various", "knights on an important quest. I was wondering if you", "too would like to take up this quest?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("Tell me of this quest.", "I am weary of questing for the time being...");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Tell me of this quest.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, we recently found out that the Holy Grail has", "passed into the RuneScape world.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("This is most fortuitous!.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("None of my knights ever did return with it last time.", "Now we have the opportunity to give it another go,", "maybe this time we will have more luck!", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showTwoOptions("I'd enjoy trying that.", "I may come back and try that later.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'd enjoy trying that.", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Go speak to Merlin. He may be able to give a better", "clue as to where it is now you have freed him from that", "crystal.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("He has set up his workshop in the room next to the", "library.", 591);
                    this.startQuest(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 6 && value42 < 8) {
                if (player.ownsItem(18)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello, do you have a knight named Sir Percival?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Ah yes. I remember young Percival. He rode off on a", "quest a couple of months ago. We are getting a bit", "worried, he's not back yet...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("He was going to try and recover the golden boots of", "Arkaneeses.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Any idea which way that would be?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Not exactly.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("We discovered, some magic golden feathers that are said", "to point the way to the boots...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("They certainly point somewhere.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Just blowing gently on them will supposedly show the", "way to go.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showOneLineStatement("King Arthur gives you a feather.");
                    player.getInventoryManager().addOrDropItem(new ItemStack(18, 1));
                    player.setQuestState(this.getQuestId(), 7);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 9) {
                if (!player.getInventoryManager().containsItem(19)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("How goes thy quest?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have retrieved the Grail!", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Wow! Incredible! You truly are a splendid knight!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getInventoryManager().removeItem(new ItemStack(19, 1));
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 213) {
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Hello. King Arthur has sent me on a quest for the", "Holy Grail. He thought you could offer some assistance.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ah yes... the Holy Grail...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("That is a powerful artefact indeed. Returning it here", "would help Camelot a lot.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Due to its nature the Holy Grail is likely to reside in a", "holy place.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Any suggestions?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("I believe there is a holy island somewhere not far", "away... I'm not entirely sure... I spent too long inside", "that crystal! Anyway, go and talk to someone over", "there.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("I suppose you could also try speaking to Sir Galahad?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcFourLineDialogue("He returned from the quest many years after", "everyone else. He seems to know something about it,", "but he can only speak about those experiences", "cryptically.", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 9) {
                    player.getDialogueManager().showTwoOptions("Thank you for the advice.", "Where can I find Sir Galahad?");
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 1) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where can I find Sir Galahad?", 591);
                        player.getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Galahad now lives a life of religious contemplation. He", "lives somewhere west of McGrubor's Wood I think.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 216 && value42 == 3) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Many greetings. Welcome to our fair island.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showPlayerOneLineDialogue("Hello, I am in search of the Holy Grail.", 591);
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showNpcTwoLineDialogue("The object of which you speak did once pass through", "holy Entrana. I know not where it is now however.", 591);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("Nor do I really care.", 591);
                return true;
            }
            if (value2 == 5) {
                Npc npc = new Npc(217);
                GameplayHelper.spawnOwnedNpcAtPosition(player, new Position(2851, 3342, 0), npc, false, false);
                npc.queueScriptedPath(new Position[]{new Position(player.getPosition().getX(), player.getPosition().getY() - 1, 0)});
                player.getUpdateState().setFaceEntity(npc.getEncodedIndex());
                DialogueManager.continueDialogue(player, 217, 100, 0);
                return true;
            }
        }
        if (npcId == 217) {
            if (value42 == 3) {
                if (value2 == 100) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Did you say the Grail? You are a Grail Knight, yes?", "Well you'd better hurry. A Fisher King is in pain.", 591);
                    return true;
                }
                if (value2 == 101) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Well I would, but I don't know where I am going!", 591);
                    return true;
                }
                if (value2 == 102) {
                    player.pendingGameMode = 0;
                    player.getDialogueManager().showNpcTwoLineDialogue("Go to where the six heads face, blow the whistle and", "away you go!", 591);
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 103) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showFourOptions("What are the six heads?", "What's a Fisher King?", "Ok, I will go searching.", "What do you mean by the whistle?");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showFourOptions("What's a Fisher King?", "Ok, I will go searching.", "What do you mean by the whistle?", "The point of realm crossing?");
                    }
                    if (player.pendingGameMode == 2) {
                        player.getDialogueManager().showThreeOptions("What are the six heads?", "Ok, I will go searching.", "What do you mean by the whistle?");
                    }
                    if (player.pendingGameMode == 4) {
                        player.getDialogueManager().showThreeOptions("What are the six heads?", "What's a Fisher King?", "Ok, I will go searching.");
                    }
                    return true;
                }
                if (value2 == 104) {
                    if (value32 == 1) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 2 || player.pendingGameMode == 4) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What are the six heads?", 591);
                            player.getDialogueManager().setNextDialogueStep(109);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("What's a Fisher King?", 591);
                            player.getDialogueManager().setNextDialogueStep(108);
                        }
                        return true;
                    }
                    if (value32 == 2) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 4) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What's a Fisher King?", 591);
                            player.getDialogueManager().setNextDialogueStep(108);
                        } else {
                            player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                            player.getDialogueManager().setNextDialogueStep(103);
                        }
                        return true;
                    }
                    if (value32 == 3) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 4) {
                            player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                            player.getDialogueManager().setNextDialogueStep(103);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("What do you mean by the whistle?", 591);
                            player.getDialogueManager().setNextDialogueStep(105);
                        }
                        return true;
                    }
                    if (value32 == 4) {
                        if (player.pendingGameMode == 0) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What do you mean by the whistle?", 591);
                            player.getDialogueManager().setNextDialogueStep(105);
                            return true;
                        }
                        if (player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("The point of realm crossing?", 591);
                            player.getDialogueManager().setNextDialogueStep(111);
                            return true;
                        }
                        return false;
                    }
                }
                if (value2 == 105) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You don't know about the whistles yet? The whistles are", "easy.", 591);
                    return true;
                }
                if (value2 == 106) {
                    player.getDialogueManager().showNpcFourLineDialogue("You will need one to get to and from the Fisher King's", "realm. They reside in a haunted manor house in", "Misthalin, though you may not perceive them unless", "you carry something from the realm of the Fisher", 591);
                    return true;
                }
                if (value2 == 107) {
                    player.pendingGameMode = 4;
                    player.getDialogueManager().showNpcOneLineDialogue("King...", 591);
                    player.getDialogueManager().setNextDialogueStep(103);
                    return true;
                }
                if (value2 == 108) {
                    player.pendingGameMode = 2;
                    player.getDialogueManager().showNpcOneLineDialogue("The Fisher King is the owner and slave of the Grail...", 591);
                    player.getDialogueManager().setNextDialogueStep(103);
                    return true;
                }
                if (value2 == 109) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The six stone heads have appeared just recently in the", "world. They all face the point of realm crossing. Find", "where two of the heads face,", 591);
                    return true;
                }
                if (value2 == 110) {
                    player.pendingGameMode = 1;
                    player.getDialogueManager().showNpcOneLineDialogue("and you should be able to pinpoint where it is.", 591);
                    player.getDialogueManager().setNextDialogueStep(103);
                    return true;
                }
                if (value2 == 111) {
                    player.getDialogueManager().showNpcFourLineDialogue("The realm of the Fisher King is not quite of this", "reality. It is of a reality very close to ours though...", "Where it is easiest to cross, THAT is a point of realm", "crossing.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 218 && value42 >= 3 && !player.ownsItem(15)) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("Welcome to my home. It's rare for me to have quests!", "Would you like a cup of tea? I'll just put the kettle on.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showOneLineStatement("Brother Galahad hangs a kettle over the fire.");
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showFourOptions("Are you any relation to Sir Galahad?", "I'm on a quest to find the Holy Grail!", "Do you get lonely here on your own?", "I seek an item from the realm of the Fisher King.");
                return true;
            }
            if (value2 == 4) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Are you any relation to Sir Galahad?", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm on a quest to find the Holy Grail!", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Do you get lonely out here on your own?", 591);
                    player.getDialogueManager().setNextDialogueStep(13);
                    return true;
                }
                if (value32 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I seek an item from the realm of the Fisher King.", 591);
                    player.getDialogueManager().setNextDialogueStep(14);
                    return true;
                }
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("I AM Sir Galahad.", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcFourLineDialogue("Although I've retired as a Knight, and now live as a", "solitary monk. Also, I prefer to be known as brother", "rather than sir now. Half a moment, your cup of tea is", "ready.", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showOneLineStatement("Sir Galahad gives you a cup of tea.");
                return true;
            }
            if (value2 == 8) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1978, 1));
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcThreeLineDialogue("Ah... the Grail... yes... that did fill me with wonder! Oh,", "that I could have stayed forever! The spear, the food,", "the people...", 591);
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showFourOptions("So how can I find it?", "What are you talking about?", "Why did you leave?", "Why didn't you bring the Grail with you?");
                return true;
            }
            if (value2 == 11 && value32 == 4) {
                player.getDialogueManager().showPlayerOneLineDialogue("Why didn't you bring the Grail with you?", 591);
                player.getDialogueManager().setNextDialogueStep(12);
                return true;
            }
            if (value2 == 12) {
                player.getDialogueManager().showNpcThreeLineDialogue("...I'm not sure. Because... it seemed to be... NEEDED", "in the Grail castle? Half a moment, your cup of tea is", "ready.", 591);
                player.getDialogueManager().setNextDialogueStep(7);
                return true;
            }
            if (value2 == 13) {
                player.getDialogueManager().showNpcFourLineDialogue("Sometimes I do, yes. Still not many people to share my", "solidarity with, as most of the religious men around here", "are worshippers of Saradomin. Half a moment, your cup", "of tea is ready.", 591);
                player.getDialogueManager().setNextDialogueStep(7);
                return true;
            }
            if (value2 == 14) {
                player.getDialogueManager().showNpcTwoLineDialogue("Funny you should mention that, but when I left there", "I took this small cloth from the table as a keepsake.", 591);
                return true;
            }
            if (value2 == 15) {
                player.getDialogueManager().showPlayerTwoLineDialogue("I don't suppose I could borrow that? It could come in", "useful on my quest.", 591);
                return true;
            }
            if (value2 == 16) {
                player.getDialogueManager().showOneLineStatement("Galahad reluctantly passes you a small cloth.");
                return true;
            }
            if (value2 == 17) {
                player.getInventoryManager().addOrDropItem(new ItemStack(15, 1));
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        if (npcId == 219) {
            if (player.ownsItem(17)) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Hi! I don't get many visitors here!", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showThreeOptions("How's the fishing?", "Any idea how to get into the castle?", "Yes, well, this place is a dump.");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How's the fishing?", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Any idea how to get into the castle?", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value32 == 3) {
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcThreeLineDialogue("Not amazing. Not many fish can live in this gungey", "stuff. I remember when this was a pleasant river", "teeming with every sort of fish...", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("Why, that's easy!", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcOneLineDialogue("Just ring one of the bells outside.", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showPlayerOneLineDialogue("...I didn't see any bells.", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcTwoLineDialogue("You must be blind then. There's ALWAYS bells there", "when I go to the castle.", 591);
                GroundItem groundItem = new GroundItem(new ItemStack(17, 1), player, new Position(2762, 4694, 0));
                GroundItemManager.getInstance().spawn(groundItem);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 210) {
            if (value2 == 100) {
                player.getDialogueManager().showOneLineStatement("Ting-a-ling-a-ling!");
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().showNpcTwoLineDialogue("Welcome to the Grail castle. You should come inside, it's", "cold out here.", 591);
                return true;
            }
            if (value2 == 102) {
                player.getDialogueManager().showOneLineStatement("Somehow you are now inside the castle.");
                Player player2 = player;
                player2.packetSender.queueRelativeMovementStep(0, -2, true);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 220 && value42 == 5) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcThreeLineDialogue("Ah! You got inside at last! You spent all that time", "fumbling around outside, I thought you'd never make it", "here.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showThreeOptions("How did you know what I have been doing?", "I seek the Holy Grail", "You don't look too well.");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("You don't look too well.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("Nope, I don't feel so good either.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcThreeLineDialogue("I fear my life is running short... Alas, my son and heir", "is not here. I am waiting for my son to return to this", "castle.", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcTwoLineDialogue("If you could find my son, that would be a great weight", "off my shoulders.", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showPlayerOneLineDialogue("Who is your son?", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcOneLineDialogue("He is known as Percival.", 591);
                return true;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcOneLineDialogue("I believe he is a knight of the round table.", 591);
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showPlayerOneLineDialogue("I shall go and see if I can find him.", 591);
                player.setQuestState(this.getQuestId(), 6);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 211) {
            if (value2 == 100) {
                player.pendingGameMode = 0;
                player.getDialogueManager().showNpcOneLineDialogue("Wow, thank you! I could hardly breathe in there!", 591);
                return true;
            }
            if (value2 == 101) {
                if (player.pendingGameMode == 0) {
                    player.getDialogueManager().showThreeOptions("How did you end up in a sack?", "Come with me, I shall make you a king.", "Your father wishes to speak to you.");
                }
                if (player.pendingGameMode == 1) {
                    player.getDialogueManager().showTwoOptions("Come with me, I shall make you a king.", "Your father wishes to speak to you.");
                }
                return true;
            }
            if (value2 == 102) {
                if (value32 == 1) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("How did you end up in a sack?", 591);
                        player.getDialogueManager().setNextDialogueStep(103);
                    } else {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
                if (value32 == 2) {
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Your father wishes to speak to you.", 591);
                        player.getDialogueManager().setNextDialogueStep(108);
                    } else {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
                if (value32 == 3 && player.pendingGameMode == 0) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Your father wishes to speak to you.", 591);
                    player.getDialogueManager().setNextDialogueStep(108);
                    return true;
                }
            }
            if (value2 == 103) {
                player.getDialogueManager().showNpcOneLineDialogue("It's a little embarrassing really.", 591);
                return true;
            }
            if (value2 == 104) {
                player.getDialogueManager().showNpcThreeLineDialogue("After going on a long and challenging quest to retrieve", "the boots of Arkaneeses, defeating many powerful", "enemies on the way, I fell into a goblin trap!", 591);
                return true;
            }
            if (value2 == 105) {
                player.getDialogueManager().showNpcOneLineDialogue("I've been kept as a slave here for the last 3 months!", 591);
                return true;
            }
            if (value2 == 106) {
                player.getDialogueManager().showNpcTwoLineDialogue("A day or so ago, they decided it was a fun game to put", "me in this sack: then they forgot about me!", 591);
                return true;
            }
            if (value2 == 107) {
                player.pendingGameMode = 1;
                player.getDialogueManager().showNpcOneLineDialogue("I'm now very hungry, and my bones feel very stiff.", 591);
                player.getDialogueManager().setNextDialogueStep(101);
                return true;
            }
            if (value2 == 108) {
                player.getDialogueManager().showNpcOneLineDialogue("My father? You have spoken to him recently?", 591);
                return true;
            }
            if (value2 == 109) {
                player.getDialogueManager().showPlayerOneLineDialogue("He is dying and wishes you to be his heir.", 591);
                return true;
            }
            if (value2 == 110) {
                player.getDialogueManager().showNpcOneLineDialogue("I have been told that before.", 591);
                return true;
            }
            if (value2 == 111) {
                player.getDialogueManager().showNpcOneLineDialogue("I have not been able to find that castle again though...", 591);
                return true;
            }
            if (value2 == 112) {
                player.getDialogueManager().showPlayerTwoLineDialogue("Well, I do have the means to get us there - a magic", "whistle!", 591);
                return true;
            }
            if (value2 == 113) {
                player.getDialogueManager().showTwoLineStatement("You give a whistle to Sir Percival. You tell sir Percival what to do", "with the whistle.");
                return true;
            }
            if (value2 == 114) {
                player.getDialogueManager().showNpcOneLineDialogue("Ok, I will see you there then!", 591);
                return true;
            }
            if (value2 == 115 && player.getInventoryManager().containsItem(16)) {
                player.getInventoryManager().removeItem(new ItemStack(16, 1));
                player.setQuestState(this.getQuestId(), 8);
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        if (npcId == 212 && value42 == 8) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("You missed all the excitement!", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showNpcFourLineDialogue("I got here and agreed to take over duties as king here,", "then before my eyes the most miraculous changes", "occured here... grass and trees were growing outside", "before our very eyes!", 591);
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showNpcOneLineDialogue("Thank you very much for showing me the way home.", 591);
                player.setQuestState(this.getQuestId(), 9);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 221 && value42 == 5 && value2 == 1 && player.getPosition().getX() > 2790) {
            Player player3 = player;
            player3.packetSender.queueRelativeMovementStep(-2, 0, true);
            return false;
        }
        return false;
    }
}
