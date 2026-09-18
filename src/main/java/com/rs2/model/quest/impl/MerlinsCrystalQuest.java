package com.rs2.model.quest.impl;

import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.MerlinsCrystalThrantaxSummonTask;
import com.rs2.model.task.TickTask;

public final class MerlinsCrystalQuest
extends QuestScript {
    public MerlinsCrystalQuest(int value2) {
        super(61);
        super.setQuestPointReward(6);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value3) {
        int value2 = value3 - 7;
        if (value3 == 0) {
            String[] stringValues = new String[]{"I can start this quest by speaking to King Arthur at", "Camelot Castle, just North West of Catherby", "I must be able to defeat a level 37 enemy"};
            return stringValues;
        }
        if (value3 == 2) {
            String[] stringValues2 = new String[]{"I should ask if the other knights know a way to", "free Merlin from the crystal."};
            return stringValues2;
        }
        if (value3 == 3) {
            String[] stringValues3 = new String[]{"I should find a way to get into Morgan Le Faye's", "stronghold."};
            return stringValues3;
        }
        if (value3 == 4) {
            String[] stringValues4 = new String[]{"Sir Lancelot hinted that the fort takes all its", "deliveries by boat."};
            return stringValues4;
        }
        if (value3 == 5) {
            String[] stringValues5 = new String[]{"I should find a way to get into Arhein's ship", "for the next delivery."};
            return stringValues5;
        }
        if (value3 == 6) {
            String[] stringValues6 = new String[]{"I should search the fort."};
            return stringValues6;
        }
        if (value3 >= 7 && value3 < 23) {
            String text = "";
            String text2 = "";
            String text3 = "";
            String text4 = "";
            if ((value2 & 1) != 0 && !player.ownsItem(38) && !player.ownsItem(32) && ((value2 & 2) == 0 || (value2 & 2) != 0 && (value2 & 4) != 0)) {
                text = "Candle maker agreed to make me a black candle";
                text2 = "if I brought him bucket full of wax.";
            }
            if ((value2 & 2) != 0 && (value2 & 1) == 0 && (value2 & 4) == 0) {
                text = "Lady of the lake told me to go upstairs of the";
                text2 = "jewellery store in Port Sarim.";
            }
            if ((value2 & 2) != 0 && (value2 & 1) != 0 && (value2 & 4) == 0) {
                text = "Lady of the lake told me to go upstairs of the";
                text2 = "jewellery store in Port Sarim.";
                if (!player.ownsItem(38) && !player.ownsItem(32)) {
                    text3 = "Candle maker agreed to make me a black candle";
                    text4 = "if I brought him bucket full of wax.";
                }
            }
            return new String[]{"Morgan le faye told me that I need the following", "things to untrap Merlin:", String.valueOf(player.ownsItem(35) ? "@str@" : "") + "Excalibur, from the lady of the lake.", String.valueOf((value2 & 8) != 0 ? "@str@" : "") + "Some magic words, from one of the chaos altars.", String.valueOf(player.getInventoryManager().containsItemAmount(530, 1) ? "@str@" : "") + "Bat bones", String.valueOf(player.getInventoryManager().containsItemAmount(32, 1) ? "@str@" : "") + "Lit black candle", "", text, text2, "", text3, text4};
        }
        if (value3 == 23) {
            String[] stringValues7 = new String[]{"I should now be able to shatter the crystal", "with excalibur."};
            return stringValues7;
        }
        if (value3 == 24) {
            String[] stringValues8 = new String[]{"I should now go talk to King Arthur to finish", "this quest."};
            return stringValues8;
        }
        if (value3 == 1) {
            String[] stringValues9 = new String[]{"Quest Completed!", "", "You were awarded:", "6 Quest Points", "Excalibur"};
            return stringValues9;
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("6 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("Excalibur", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 35);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 28 && value2 == 68) {
            player.getDialogueManager().showTwoLineStatement("You pour insect repellent on the beehive. You see bees leaving the", "hive.");
            player.temporaryActionValue = 28;
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId == 1925 && value2 == 68) {
            if (player.temporaryActionValue != 28) {
                player.applyDirectHit(2, HitType.NORMAL);
                Player player2 = player;
                player2.packetSender.sendGameMessage("Suddenly bees fly out of the hive and sting you.");
                return true;
            }
            DialogueManager.continueContextDialogue(1, player, value2, 100, 0, player.getPosition().getX(), player.getPosition().getY());
            player.temporaryActionValue = 0;
            return true;
        }
        if (objectId == 35 && value2 == 62) {
            if (value32 == 23) {
                new DynamicObject(ServerSettings.placeholderObjectId, 2767, 3493, player.getPosition().getPlane(), 0, 10, value2, 100);
                Npc npc = new Npc(249);
                GameplayHelper.replaceOwnedRoamingNpcAtPosition(player, npc, 2767, 3493, 2, -1, false, false);
                DialogueManager.continueDialogue(player, 249, 1, 0);
                player.packetSender.sendGameMessage("You attempt to smash the crystal...");
                player.packetSender.sendGameMessage("... and it shatters under the force of Excalibur!");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 68) {
            if (value32 == 100) {
                player.getDialogueManager().showOneLineStatement("You try and get some wax from the beehive.");
                return true;
            }
            if (value32 == 101 && player.getInventoryManager().containsItemAmount(1925, 1)) {
                player.getInventoryManager().replaceItem(new ItemStack(1925, 1), new ItemStack(30, 1));
                player.getDialogueManager().showOneLineStatement("You get some wax from the hive.");
                return true;
            }
            if (value32 == 102) {
                player.getDialogueManager().showOneLineStatement("The bees fly back to the hive as the repellent wears off.");
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (value22 == 63 && value52 == 2801 && value62 == 3442 && value8 == 2 && value72 == 5) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("The crate is empty. It's just about big enough to hide inside.");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showTwoOptionsWithTitle("Would you like to hide inside the crate?", "Yes.", "No.");
                return true;
            }
            if (value32 == 3) {
                if (value42 == 1) {
                    player.getDialogueManager().showOneLineStatement("You climb inside the crate and wait.");
                    player.getDialogueManager().setNextDialogueStep(4);
                    player.getUpdateState().setAnimation(827);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value32 == 4) {
                player.getDialogueManager().showOneLineStatement("You wait.");
                player.moveToPreservingInteractionState(new Position(2793, 9819, 0));
                return true;
            }
            if (value32 == 5) {
                player.getDialogueManager().showOneLineStatement("And wait...");
                return true;
            }
            if (value32 == 6) {
                player.getDialogueManager().showTwoLineStatement("You hear voices outside the crate.", "@dbl@Is this your crate, Arhein?");
                return true;
            }
            if (value32 == 7) {
                player.getDialogueManager().showTwoLineStatement("Yeah, I think so. Pack it aboard soon as you can.", "I'm on a tight schedule for deliveries!");
                return true;
            }
            if (value32 == 8) {
                player.getDialogueManager().showThreeLineStatement("You feel the crate being lifted.", "@dbl@Oof. Wow, this is pretty heavy!", "@dbl@I never knew candles weighed so much!");
                return true;
            }
            if (value32 == 9) {
                player.getDialogueManager().showOneLineStatement("Quit your whining, and stow it in the hold.");
                return true;
            }
            if (value32 == 10) {
                player.getDialogueManager().showOneLineStatement("You feel the crate being put down inside the ship.");
                return true;
            }
            if (value32 == 11) {
                player.getDialogueManager().showOneLineStatement("You wait...");
                return true;
            }
            if (value32 == 12) {
                player.getDialogueManager().showOneLineStatement("And wait...");
                return true;
            }
            if (value32 == 13) {
                player.getDialogueManager().showOneLineStatement("Casting off!");
                return true;
            }
            if (value32 == 14) {
                player.getDialogueManager().showOneLineStatement("You feel the ship start to move.");
                return true;
            }
            if (value32 == 15) {
                player.getDialogueManager().showOneLineStatement("Feels like you're now out at sea.");
                return true;
            }
            if (value32 == 16) {
                player.getDialogueManager().showOneLineStatement("The ship comes to a stop.");
                return true;
            }
            if (value32 == 17) {
                player.getDialogueManager().showTwoLineStatement("Unload Mordred's deliveries onto the jetty.", "@dbl@Aye-aye cap'n!");
                return true;
            }
            if (value32 == 18) {
                player.getDialogueManager().showOneLineStatement("You feel the crate being lifted.");
                return true;
            }
            if (value32 == 19) {
                player.getDialogueManager().showFourLineStatement("You can hear someone mumbling outside the crate.", "", "@dbl@...stupid Arhein... making me... candles...", "@dbl@never weigh THIS much....hurts....union about this!...");
                return true;
            }
            if (value32 == 20) {
                player.getDialogueManager().showTwoLineStatement("@dbl@...if....MY ship be different!...", "@dbl@stupid Arhein...");
                return true;
            }
            if (value32 == 21) {
                player.getDialogueManager().showOneLineStatement("You feel the crate being put down.");
                player.moveToPreservingInteractionState(new Position(2778, 9839, 0));
                return true;
            }
            if (value32 == 22) {
                player.getDialogueManager().showTwoOptionsWithTitle("Would you like to get back out of the crate?", "Yes.", "No.");
                return true;
            }
            if (value32 == 23) {
                if (value42 == 1) {
                    player.getDialogueManager().showOneLineStatement("You climb out of the crate.");
                    player.getDialogueManager().setNextDialogueStep(25);
                    return true;
                }
                if (value42 == 2) {
                    player.getDialogueManager().showOneLineStatement("You wait.");
                    player.getDialogueManager().setNextDialogueStep(24);
                    return true;
                }
            }
            if (value32 == 24) {
                player.getDialogueManager().showOneLineStatement("And wait...");
                player.getDialogueManager().setNextDialogueStep(22);
                return true;
            }
            if (value32 == 25) {
                player.moveTo(new Position(2778, 3401, 0));
                player.setQuestState(this.getQuestId(), 6);
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        return false;
    }

    @Override
    public final boolean handleDropItem(Player itemId, int itemId2, int value2) {
        if (itemId2 == 530 && value2 == 22 && ((Entity)itemId).getPosition().getX() == 2780 && ((Entity)itemId).getPosition().getY() == 3515) {
            itemId2 = 0;
            if (((Player)itemId).getEquipmentManager().getContainer().getItemAt(3) != null && ((Player)itemId).getEquipmentManager().getContainer().getItemAt(3).getId() == 35) {
                itemId2 = 1;
            }
            if (((Player)itemId).getInventoryManager().containsItemAmount(32, 1) && (((Player)itemId).getInventoryManager().containsItemAmount(35, 1) || itemId2 != 0)) {
                ((Player)itemId).setActionLocked(true);
                Player player = itemId;
                player.packetSender.queueRelativeMovementStep(-2, 0, true);
                World.getTaskScheduler().schedule(new MerlinsCrystalThrantaxSummonTask(this, 3, (Player)itemId));
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value5, int value32, int value42) {
        int value2 = value42 - 7;
        if (objectId == 61 && value5 == 3259 && value32 == 3381 && value42 >= 7 && (value2 & 8) == 0) {
            player.addQuestState(this.getQuestId(), 8);
            player.getDialogueManager().showTwoLineStatement("You find a small inscription at the bottom of the altar. It reads:", "'Snarthon Candtrick Termanto'.");
            player.getDialogueManager().finishDialogue();
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value5, int value32, int value42) {
        int value2 = value42 - 7;
        if (objectId == 59 && value5 == 3016 && value32 == 3246) {
            if ((value2 & 2) != 0 && value42 > 7 && !player.ownsItem(35)) {
                if ((value2 & 4) == 0 && player.getInventoryManager().containsItemAmount(2309, 1) || (value2 & 4) != 0) {
                    Npc npc = new Npc(252);
                    Player player2 = player;
                    if (player2.ownedNpc != null) {
                        player2 = player;
                        if (player2.ownedNpc.getNpcId() != 252) {
                            player2 = player;
                            if (player2.ownedNpc.getNpcId() != 250) {
                                GameplayHelper.replaceOwnedRoamingNpcAtPosition(player, npc, 3016, 3247, 0, -1, false, false);
                            }
                        }
                    } else {
                        GameplayHelper.replaceOwnedRoamingNpcAtPosition(player, npc, 3016, 3247, 0, -1, false, false);
                    }
                    DialogueManager.continueDialogue(player, 252, 100, 0);
                }
            } else {
                Player player3 = player;
                player3.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3016 ? 1 : -1, 0, true);
                player3 = player;
                player3.packetSender.openSingleDoor(59, 3016, 3246, 0);
            }
            return true;
        }
        if (objectId == 66 && value42 == 5) {
            player.moveTo(new Position(2802, 3442, 0));
            return true;
        }
        if (objectId == 65 && value42 == 5) {
            player.moveTo(new Position(2778, 3401, 0));
            player.setQuestState(this.getQuestId(), 6);
            return true;
        }
        if (objectId == 71 && value5 == 2763 && value32 == 3402 || objectId == 72 && value5 == 2763 && value32 == 3401) {
            if (value42 >= 6 || value42 == 1) {
                Player player4 = player;
                player4.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2764 ? 1 : -1, 0, true);
                player4 = player;
                player4.packetSender.openDoubleDoorPair(71, 72, 2763, 3402, 2763, 3401, 0);
            } else {
                Player player5 = player;
                player5.packetSender.sendGameMessage("You should find another way in.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc() && entity.isPlayer()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 247) {
                CombatManager.stopCombat(entity2);
                CombatManager.stopCombat(entity);
                entity2 = new Npc(248);
                GameplayHelper.replaceOwnedRoamingNpcAtPosition((Player)entity, (Npc)entity2, 2770, 3403, 2, 86, false, false);
                DialogueManager.continueDialogue((Player)entity, 248, 100, 0);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        if (npcId == 247 && value2 != 6) {
            return false;
        }
        return npcId != 238;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value5, int value32, int value42) {
        Player player2;
        int value2 = value42 - 7;
        if (npcId == 251) {
            if (value42 == 0) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Welcome to my court. I am King Arthur.", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showThreeOptions("I want to become a knight of the round table!", "So what are you doing in RuneScape?", "Thank you very much.");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I want to become a knight of the round table!", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So what are you doing in RuneScape?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well legend says we will return to Britain in its time of", "greatest need. But that's not for quite a while yet.", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("So we've moved the whole outfit here for now.", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("We're passing the time in RuneScape!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, in that case I think you need to go on a quest to", "prove yourself worthy.", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("My knights all appreciate a good quest.", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Unfortunately, our current quest is to rescue Merlin.", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Back in England, he got himself trapped in some sort of", "magical Crystal. We've moved him from the cave we", "found him in and now he's upstairs in his tower.", 591);
                    return true;
                }
                if (value5 == 11) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I will see what I can do then.", 591);
                    return true;
                }
                if (value5 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("Talk to my knights if you need any help.", 591);
                    this.startQuest(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 24) {
                if (value5 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have freed Merlin from his crystal!", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah. A good job, well done. I dub thee a Knight Of The", "Round Table. You are now an honorary knight.", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 240) {
            if (value42 == 2) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good day to you sir!", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showThreeOptions("Good day.", "Any ideas on how to get Merlin out of that crystal?", "Do you know how Merlin got trapped?");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Any ideas on how to get Merlin out of that crystal?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Do you know how Merlin got trapped?", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm a little stumped myself. We've tried opening it with", "anything and everything!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I would guess this is the work of the evil Morgan Le", "Faye!", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("And where could I find her?", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("She lives in her stronghold to the south of here,", "guarded by some renegade knights led by Sir Mordred.", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value5 == 8) {
                    player.getDialogueManager().showTwoOptions("Any idea how to get into Morgan Le Faye's stronghold?", "Thank you for the information.");
                    return true;
                }
                if (value5 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Any idea how to get into Morgan Le Faye's stronghold?", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcOneLineDialogue("No, you've got me stumped there...", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 239 && value42 == 3) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("Greetings! I am Sir Lancelot, the greatest Knight in the", "land! What do you want?", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showThreeOptions("I want to get Merlin out of the crystal.", "You're a little full of yourself aren't you?", "Any ideas on how to get into Morgan Le Faye's stronghold?");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I want to get Merlin out of the crystal.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Any ideas on how to get into Morgan Le Faye's", "stronghold?", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcThreeLineDialogue("Well, if the Knights of the Round Table can't manage", "it, I can't see how a commoner like you could succeed", "where we have failed.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("That stronghold is built in a strong defensive position.", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showNpcOneLineDialogue("It's on a big rock sticking out into the sea.", 591);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcThreeLineDialogue("There are two ways in that I know of, the large heavy", "front doors, and the sea entrance, only penetrable by", "boat.", 591);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showNpcOneLineDialogue("They take all their deliveries by boat.", 591);
                player.setQuestState(this.getQuestId(), 4);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 563) {
            if (value42 == 4) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello! Would you like to trade?", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showThreeOptions("Yes.", "No thank you.", "Is that your ship?");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("No thanks.", 591);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Is that your ship?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, I use it to make deliveries to my customers up", "and down the coast. These crates here are all ready for", "my next trip.", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showThreeOptions("Do you deliver to the fort just down the coast?", "Where do you deliver to?", "Are you rich then?");
                    return true;
                }
                if (value5 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Do you deliver to the fort just down the coast?", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, I do have orders to deliver there from time to", "time. I think I may have some bits and pieces for them", "when I leave here next actually.", 591);
                    player.setQuestState(this.getQuestId(), 5);
                    return true;
                }
            }
            if (value42 == 5) {
                if (value5 == 8) {
                    player.getDialogueManager().showTwoOptions("Can you drop me off on the way down please?", "Aren't you worried about supplying evil knights?");
                    return true;
                }
                if (value5 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Can you drop me off on the way down please?", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I don't think Sir Mordred would like that. He wants as", "few outsiders visiting as possible. I wouldn't want to lose", "his business.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 247 && value42 == 6) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("You DARE to invade MY stronghold?!?!", "Have at thee knave!!!", 591);
                return true;
            }
            if (value5 == 2) {
                Npc npc = Npc.findByDefinitionId(247);
                if (npc != null && !npc.isDead() && !npc.hasCombatTarget()) {
                    CombatManager.startCombat(npc, player);
                }
                return false;
            }
        }
        if (npcId == 248) {
            player2 = player;
            if (player2.ownedNpc.getNpcId() == 248) {
                if (value42 == 6) {
                    if (value5 == 1) {
                        value5 = 100;
                    }
                    if (value5 == 100) {
                        player.getDialogueManager().showNpcOneLineDialogue("STOP! Please... spare my son.", 591);
                        player.getDialogueManager().setNextDialogueStep(2);
                        return true;
                    }
                    if (value5 == 2) {
                        player.getDialogueManager().showThreeOptions("Tell me how to untrap Merlin and I might.", "No. He deserves to die.", "Ok then.");
                        return true;
                    }
                    if (value5 == 3) {
                        if (value32 == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Tell me how to untrap Merlin and I might.", 591);
                            player.getDialogueManager().setNextDialogueStep(4);
                            return true;
                        }
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(2);
                        return true;
                    }
                    if (value5 == 4) {
                        player.getDialogueManager().showNpcTwoLineDialogue("You have guessed correctly that I'm responsible for", "that.", 591);
                        Npc npc = new Npc(247);
                        npc.setCurrentHitpoints(npc.getMaxHitpoints());
                        return true;
                    }
                    if (value5 == 5) {
                        player.getDialogueManager().showNpcTwoLineDialogue("I suppose I can live with that fool Merlin being loose", "for the sake of my son.", 591);
                        return true;
                    }
                    if (value5 == 6) {
                        player.getDialogueManager().showNpcOneLineDialogue("Setting him free won't be easy though.", 591);
                        return true;
                    }
                    if (value5 == 7) {
                        player.getDialogueManager().showNpcTwoLineDialogue("You will need to find a magic symbol as close to the", "crystal as you can find.", 591);
                        return true;
                    }
                    if (value5 == 8) {
                        player.getDialogueManager().showNpcTwoLineDialogue("You will then need to drop some bats' bones on the", "magic symbol while holding a lit black candle.", 591);
                        return true;
                    }
                    if (value5 == 9) {
                        player.getDialogueManager().showNpcOneLineDialogue("This will summon a mighty spirit named Thrantax.", 591);
                        return true;
                    }
                    if (value5 == 10) {
                        player.getDialogueManager().showNpcOneLineDialogue("You will need to bind him with magic words.", 591);
                        return true;
                    }
                    if (value5 == 11) {
                        player.pendingGameMode = 0;
                        player.setQuestState(this.getQuestId(), 7);
                        player.getDialogueManager().showNpcTwoLineDialogue("Then you will need the sword Excalibur with which the", "spell was bound in order to shatter the crystal.", 591);
                        return true;
                    }
                }
                if (value42 == 7) {
                    if (value5 == 1) {
                        value5 = 12;
                    }
                    if (value5 == 12) {
                        if (player.pendingGameMode == 0) {
                            player.getDialogueManager().showThreeOptions("So where can I find Excalibur?", "OK I will do all that.", "What are the magic words?");
                        }
                        if (player.pendingGameMode == 1) {
                            player.getDialogueManager().showTwoOptions("OK, I will go do all that.", "What are the magic words?");
                        }
                        if (player.pendingGameMode == 2) {
                            player.getDialogueManager().showTwoOptions("So where can I find Excalibur?", "OK I will go do all that.");
                        }
                        return true;
                    }
                    if (value5 == 13) {
                        if (value32 == 1) {
                            if (player.pendingGameMode == 0 || player.pendingGameMode == 2) {
                                player.getDialogueManager().showPlayerOneLineDialogue("So where can I find Excalibur?", 591);
                                player.getDialogueManager().setNextDialogueStep(14);
                            }
                            if (player.pendingGameMode == 1) {
                                player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will go do all that.", 591);
                                player.getDialogueManager().setNextDialogueStep(17);
                            }
                            return true;
                        }
                        if (value32 == 2) {
                            if (player.pendingGameMode == 0 || player.pendingGameMode == 2) {
                                player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will go do all that.", 591);
                                player.getDialogueManager().setNextDialogueStep(17);
                            }
                            if (player.pendingGameMode == 1) {
                                player.getDialogueManager().showPlayerOneLineDialogue("What are the magic words?", 591);
                                player.getDialogueManager().setNextDialogueStep(15);
                            }
                            return true;
                        }
                        if (value32 == 3) {
                            if (player.pendingGameMode == 0) {
                                player.getDialogueManager().showPlayerOneLineDialogue("What are the magic words?", 591);
                                player.getDialogueManager().setNextDialogueStep(15);
                            }
                            return true;
                        }
                    }
                    if (value5 == 14) {
                        player.pendingGameMode = 1;
                        player.getDialogueManager().showNpcTwoLineDialogue("The lady of the lake has it. I don't know if she'll give it", "to you though, she can be rather temperamental.", 591);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    if (value5 == 15) {
                        player.pendingGameMode = 2;
                        player.getDialogueManager().showNpcTwoLineDialogue("You will find the magic words at the base of one of the", "chaos altars.", 591);
                        return true;
                    }
                    if (value5 == 16) {
                        player.getDialogueManager().showNpcOneLineDialogue("Which chaos altar I cannot remember.", 591);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    if (value5 == 17) {
                        player.getDialogueManager().showOneLineStatement("Morgan Le Faye vanishes.");
                        Npc npc = Npc.findByDefinitionId(248);
                        if (npc != null) {
                            npc.setActive(false);
                            World.unregisterNpc(npc);
                            player2 = player;
                            player2.packetSender.sendStillGraphicToNearbyPlayers(86, npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane(), 0);
                        }
                        return true;
                    }
                }
            }
        }
        if (npcId == 562) {
            if (value42 < 7) {
                return false;
            }
            if ((value2 & 1) == 0) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hi! Would you be interested in some of my fine", "candles?", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showThreeOptions("Have you got any black candles?", "Yes please.", "No thank you.");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Have you got any black candles?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("BLACK candles???", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hmmm. In the candle making trade, we have a tradition", "that it's very bad luck to make black candles.", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("VERY bad luck.", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I will pay good money for one...", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("I still dunno...", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Tell you what. I'll supply you with a black candle...", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcOneLineDialogue("IF you can bring me a bucket FULL of wax.", 591);
                    player.addQuestState(this.getQuestId(), 1);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value2 & 1) != 0 && !player.ownsItem(38) && !player.ownsItem(32)) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Have you got any wax yet?", 591);
                    return true;
                }
                if (value5 == 2) {
                    if (player.getInventoryManager().containsItemAmount(30, 1)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes, I have some now.", 591);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("Nope.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().showOneLineStatement("You exchange the wax with the candle maker for a black candle.");
                    return true;
                }
                if (value5 == 4 && player.getInventoryManager().containsItemAmount(30, 1)) {
                    player.getInventoryManager().replaceItem(new ItemStack(30, 1), new ItemStack(38, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 250) {
            if (value42 == 1) {
                if (player.ownsItem(35)) {
                    return false;
                }
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Here is Excalibur, guard it well.", 591);
                    player.getInventoryManager().addOrDropItem(new ItemStack(35, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 < 7) {
                return false;
            }
            if ((value2 & 2) == 0) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good day to you " + (player.getGender() == 0 ? "sir" : "madam") + ".", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showThreeOptions("Who are you?", "Good day.", "I seek the sword Excalibur.");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I seek the sword Excalibur.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Aye, I have that artefact in my possession.", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("'Tis very valuable, and not an artefact to be given", "away lightly.", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I would want to give it away only to one who is worthy", "and good.", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("And how am I meant to prove that?", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("I shall set a test for you.", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("First I need you to travel to Port Sarim. Then go to", "the upstairs room of the jeweller's shop there.", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok. That seems easy enough.", 591);
                    player.addQuestState(this.getQuestId(), 2);
                    return true;
                }
            }
        }
        if (npcId == 252) {
            player2 = player;
            if (player2.ownedNpc == null) {
                return false;
            }
            if (value42 < 7) {
                return false;
            }
            if ((value2 & 2) != 0) {
                if (value5 == 1 || value5 == 100) {
                    value5 = (value2 & 4) == 0 ? 100 : 7;
                }
                if (value5 <= 7 || value5 == 100) {
                    player2 = player;
                    if (player2.ownedNpc.getNpcId() != 252) {
                        return false;
                    }
                }
                if (value5 >= 8 && value5 != 100) {
                    player2 = player;
                    if (player2.ownedNpc.getNpcId() != 250) {
                        return false;
                    }
                }
                if (value5 == 100) {
                    player.getDialogueManager().showNpcOneLineDialogue("Please kind " + (player.getGender() == 0 ? "sir" : "lady") + "... my family and I are starving...", 591);
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Could you find it in your heart to spare me a simple", "loaf of bread?", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().showTwoOptions("Yes certainly.", "No I don't have any bread with me.");
                    return true;
                }
                if (value5 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes, certainly.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showOneLineStatement("You give the bread to the beggar.");
                    return true;
                }
                if (value5 == 6 && player.getInventoryManager().containsItemAmount(2309, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(2309, 1));
                    player.getDialogueManager().showNpcOneLineDialogue("Thank you very much!", 591);
                    player.addQuestState(this.getQuestId(), 4);
                    return true;
                }
                if (value5 == 7) {
                    player2 = player;
                    player2.ownedNpc.transformToNpcId(250, 100);
                    player.getDialogueManager().showOneLineStatement("The beggar has turned into the Lady of the Lake!");
                    player.getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().setDialogueNpcId(250);
                    player.getDialogueManager().showNpcOneLineDialogue("Well done. You have passed my test.", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().setDialogueNpcId(250);
                    player.getDialogueManager().showNpcOneLineDialogue("Here is Excalibur, guard it well.", 591);
                    return true;
                }
                if (value5 == 10) {
                    player2 = player;
                    if (player2.ownedNpc != null) {
                        player2 = player;
                        player2.ownedNpc.setActive(false);
                        player2 = player;
                        World.unregisterNpc(player2.ownedNpc);
                    }
                    player.getInventoryManager().addOrDropItem(new ItemStack(35, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 238) {
            player2 = player;
            if (player2.ownedNpc.getNpcId() == 238) {
                if (value42 < 22) {
                    return false;
                }
                if (value5 == 100) {
                    player.getDialogueManager().showOneLineStatement("Suddenly a mighty spirit appears!");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Now what were those magic words again?", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().showThreeOptions("Snarthtrick Candanto Termon", "Snarthon Candtrick Termanto", "Snarthanto Candon Termtrick");
                    return true;
                }
                if (value5 == 4) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Snarthon...", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Candtrick...", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Termanto!", 591);
                    return true;
                }
                if (value5 == 7) {
                    player2 = player;
                    Npc npc = player2.ownedNpc;
                    if (npc != null) {
                        player.getUpdateState().setFaceEntity(npc.getEncodedIndex());
                    }
                    player.getDialogueManager().showNpcOneLineDialogue("GRAAAAAARGH!", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Thou hast me in thine control. So that I mayst return", "from whence I came, I must grant thee a boon.", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("What dost thou wish of me?", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I wish to free Merlin from his giant crystal!", 591);
                    return true;
                }
                if (value5 == 11) {
                    player.getDialogueManager().showNpcOneLineDialogue("GRAAAAAARGH!", 591);
                    return true;
                }
                if (value5 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("The deed is done.", 591);
                    return true;
                }
                if (value5 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Thou mayst now shatter Merlins' crystal with", "Excalibur,", 591);
                    return true;
                }
                if (value5 == 14) {
                    player.getDialogueManager().showNpcTwoLineDialogue("and I can once more rest. Begone! And leave me once", "more in peace.", 591);
                    return true;
                }
                if (value5 == 15) {
                    player2 = player;
                    Npc npc = player2.ownedNpc;
                    if (npc != null) {
                        npc.setActive(false);
                        World.unregisterNpc(npc);
                    }
                    player.setQuestState(this.getQuestId(), 23);
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 249) {
            player2 = player;
            if (player2.ownedNpc.getNpcId() == 249 && value42 == 23) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thank you! Thank you! Thank you!", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("It's not fun being trapped in a giant crystal!", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Go speak to King Arthur, I'm sure he'll reward you!", 591);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showOneLineStatement("You have set Merlin free. Now talk to King Arthur.");
                    return true;
                }
                if (value5 == 5) {
                    player2 = player;
                    Npc npc = player2.ownedNpc;
                    if (npc != null) {
                        npc.setActive(false);
                        World.unregisterNpc(npc);
                    }
                    player.setQuestState(this.getQuestId(), 24);
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        return false;
    }
}

