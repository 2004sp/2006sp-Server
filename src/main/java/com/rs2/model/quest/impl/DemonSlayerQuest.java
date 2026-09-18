package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class DemonSlayerQuest
extends QuestScript {
    private final List incantationWords = Arrays.asList("Carlem", "Aber", "Camerinthum", "Purchai", "Gabindo");

    public DemonSlayerQuest(int value2) {
        super(3);
        super.setQuestPointReward(3);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            String[] stringValues = new String[]{"I can start this quest by speaking to the Gypsy in the tent", "in Varrock's main square.", "", "", "I must be able to defeat a level 27 apocalyptic demon!"};
            return stringValues;
        }
        if (value2 == 2) {
            String[] stringValues2 = new String[]{"Gypsy told me to go talk to Sir Prysin who can be found", "in the palace in Varrock."};
            return stringValues2;
        }
        if (value2 == 3) {
            String[] stringValues3 = new String[]{"I need 3 keys to get the Silverlight from Sir Prysin:", String.valueOf(player.ownsItem(2400) ? "@str@" : "") + "Key from Captain Rovin", String.valueOf(player.ownsItem(2401) ? "@str@" : "") + "Key from the drain", String.valueOf(player.ownsItem(2399) ? "@str@" : "") + "Key from Wizard Traiborn"};
            return stringValues3;
        }
        if (value2 >= 4 && value2 < 29) {
            value2 = 29 - value2;
            return new String[]{"I need 3 keys to get the Silverlight from Sir Prysin:", String.valueOf(player.ownsItem(2400) ? "@str@" : "") + "Key from Captain Rovin", String.valueOf(player.ownsItem(2401) ? "@str@" : "") + "Key from the drain", String.valueOf(player.ownsItem(2399) ? "@str@" : "") + "Key from Wizard Traiborn", "I need to bring " + value2 + " bones to Wizard Traiborn."};
        }
        if (value2 == 29) {
            String[] stringValues4 = new String[]{"I need 3 keys to get the Silverlight from Sir Prysin:", String.valueOf(player.ownsItem(2400) ? "@str@" : "") + "Key from Captain Rovin", String.valueOf(player.ownsItem(2401) ? "@str@" : "") + "Key from the drain", String.valueOf(player.ownsItem(2399) ? "@str@" : "") + "Key from Wizard Traiborn", String.valueOf(player.ownsItem(2399) ? "" : "I should talk to Wizard Traiborn to get the key.")};
            return stringValues4;
        }
        if (value2 == 30) {
            String[] stringValues5 = new String[]{"I should now go slay the demon South of Varrock", "with the Silverlight."};
            return stringValues5;
        }
        if (value2 == 1) {
            String[] stringValues6 = new String[]{"Quest Completed!", "", "You were awarded:", "3 Quest Points", "Silverlight"};
            return stringValues6;
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
        player2.packetSender.sendInterfaceText("Silverlight", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 2402);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc() && entity.isPlayer()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 879) {
                if (((Player)entity).getEquipmentManager().getItemIdAtSlot(3) != 2402) {
                    ((Npc)entity2).setCurrentHitpoints(((Npc)entity2).getMaxHitpoints());
                } else {
                    CombatManager.stopCombat(entity2);
                    CombatManager.stopCombat(entity);
                    DialogueManager.continueDialogue((Player)entity, 879, 100, 0);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        return npcId != 879 || value2 == 30;
    }

    @Override
    public final int getQuestDamageOverride(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 879 && ((Player)entity).getEquipmentManager().getItemIdAtSlot(3) != 2402) {
                ((Player)entity).packetSender.sendGameMessage("You should use the silverlight againts the demon.");
                return 0;
            }
        }
        return -1;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 879 && value2 == 30) {
            DialogueManager.continueDialogue(player, 879, 104, 0);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 1929 && value2 == 2843 && value32 >= 3 && player.temporaryActionValue != 2401 && !player.ownsItem(2401) && value32 < 30) {
            player.getDialogueManager().showPlayerTwoLineDialogue("OK, I think I've washed the key down into the sewer.", "I'd better go down and get it!", 591);
            player.getDialogueManager().finishDialogue();
            player.getUpdateState().setAnimation(827);
            player.getInventoryManager().replaceItem(new ItemStack(1929, 1), new ItemStack(1925, 1));
            Player player2 = player;
            player2.packetSender.sendGameMessage("You pour the liquid down the drain.");
            player.temporaryActionValue = 2401;
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 882 && value2 == 3237 && value32 == 3458 && value42 >= 3 && player.temporaryActionValue == 2401 && !player.ownsItem(2401) && value42 < 30) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(player.getPosition().getX(), player.getPosition().getY() + 6400));
            GroundItem groundItem = new GroundItem(new ItemStack(2401, 1), player, new Position(3225, 9897, 0));
            GroundItemManager.getInstance();
            if (!GroundItemManager.isVisible(player, groundItem)) {
                GroundItemManager.getInstance().spawn(groundItem);
            }
            return true;
        }
        return false;
    }

    private static boolean hasAllSilverlightKeys(Player player) {
        return player.getInventoryManager().containsItem(2400) && player.getInventoryManager().containsItem(2401) && player.getInventoryManager().containsItem(2399);
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        ArrayList arrayList = new ArrayList(this.incantationWords);
        Collections.shuffle(arrayList, new Random(player.questRandomSeed));
        if (npcId == 882) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello young one.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Cross my palm with silver and the future will be", "revealed to you.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showFourOptionsWithTitle("What would you like to say?", "Ok, here you go.", "Who are you calling young one?!", "No, I don't believe in that stuff.", "With silver?");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, here you go.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5 && player.getInventoryManager().containsItemAmount(995, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(995, 1));
                    player.getDialogueManager().showNpcThreeLineDialogue("Come closer, and listen carefully to what the future", "holds for you, as I peer into the swirling mists of the", "crystal ball.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("I can see images forming. I can see you.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You are holding a very impressive looking sword. I'm", "sure I recognise that sword...", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("There is a big dark shadow appearing now.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Aaargh!", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Are you all right?", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcOneLineDialogue("It's Delrith! Delrith is coming!", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Who's Delrith?", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcOneLineDialogue("Delrith...", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("Delrith is a powerful demon.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Oh! I really hope he didn't see me looking at him", "through my crystal ball!", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcTwoLineDialogue("He tried to destroy this city 150 years ago. He was", "stopped just in time by the great hero Wally.", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Using his magic sword Silverlight, Wally managed to", "trap the demon in the stone circle just south", "of this city.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.pendingGameMode = 0;
                    player.getDialogueManager().showNpcThreeLineDialogue("Ye gods! Silverlight was the sword you were holding in", "my vision! You are the one destined to stop the demon", "this time.", 591);
                    return true;
                }
                if (value2 == 19) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showThreeOptionsWithTitle("What would you like to say?", "How am I meant to fight a demon who can destroy cities?", "Okay, where is he? I'll kill him for you!", "Wally doesn't sound like a very heroic name.");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showThreeOptionsWithTitle("What would you like to say?", "Okay, where is he? I'll kill him for you!", "Wally doesn't sound like a very heroic name.", "So how did Wally kill Delrith?");
                    }
                    if (player.pendingGameMode == 3) {
                        player.getDialogueManager().showThreeOptionsWithTitle("What would you like to say?", "How am I meant to fight a demon who can destroy cities?", "Okay, where is he? I'll kill him for you!", "So how did Wally kill Delrith?");
                    }
                    return true;
                }
                if (value2 == 20) {
                    if (value32 == 1) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 3) {
                            player.getDialogueManager().showPlayerTwoLineDialogue("How am I meant to fight a demon who can destroy", "cities?!", 591);
                            player.getDialogueManager().setNextDialogueStep(24);
                            return true;
                        }
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                    if (value32 == 2) {
                        if (player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Wally doesn't sound a very heroic name.", 591);
                            player.getDialogueManager().setNextDialogueStep(21);
                            return true;
                        }
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                    if (value32 == 3) {
                        if (player.pendingGameMode == 0) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Wally doesn't sound a very heroic name.", 591);
                            player.getDialogueManager().setNextDialogueStep(21);
                            return true;
                        }
                        player.getDialogueManager().showPlayerOneLineDialogue("So how did Wally kill Delrith?", 591);
                        player.getDialogueManager().setNextDialogueStep(26);
                        return true;
                    }
                }
                if (value2 == 21) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yes I know. Maybe that is why history doesn't", "remember him. However he was a very great hero.", 591);
                    return true;
                }
                if (value2 == 22) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Who knows how much pain and suffering Delrith would", "have brought forth without Wally to stop him!", 591);
                    return true;
                }
                if (value2 == 23) {
                    player.pendingGameMode = 3;
                    player.getDialogueManager().showNpcTwoLineDialogue("It looks like you are going to need to perform similar", "heroics.", 591);
                    player.getDialogueManager().setNextDialogueStep(19);
                    return true;
                }
                if (value2 == 24) {
                    player.getDialogueManager().showNpcThreeLineDialogue("If you face Delrith while he is still weak from being", "summoned, and use the correct weapon, you will not", "find the task too arduous.", 591);
                    return true;
                }
                if (value2 == 25) {
                    player.pendingGameMode = 1;
                    player.getDialogueManager().showNpcTwoLineDialogue("Do not fear. If you follow the path of the great hero", "Wally, then you are sure to defeat the demon.", 591);
                    player.getDialogueManager().setNextDialogueStep(19);
                    return true;
                }
                if (value2 == 26) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Wally managed to arrive at the stone circle just as", "Delrith was summoned by a cult of chaos druids...", 591);
                    return true;
                }
                if (value2 == 27) {
                    player.getDialogueManager().showNpcFourLineDialogue("By reciting the correct magical incantation, and", "thrusting Silverlight into Delrith while he was newly", "summoned, Wally was able to imprison Delrith in the", "stone block in the centre of the circle.", 591);
                    return true;
                }
                if (value2 == 28) {
                    player.getDialogueManager().showNpcOneLineDialogue("Delrith will come forth from the stone circle again.", 591);
                    return true;
                }
                if (value2 == 29) {
                    player.pendingGameMode = 0;
                    player.getDialogueManager().showNpcTwoLineDialogue("I would imagine an evil sorceror is already starting on", "the rituals to summon Delrith as we speak.", 591);
                    return true;
                }
                if (value2 == 30) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showFourOptionsWithTitle("What would you like to say?", "How am I meant to fight a demon who can destroy cities?", "Okay, where is he? I'll kill him for you!", "What is the magical incantation?", "Where can I find Silverlight?");
                    }
                    if (player.pendingGameMode == 3) {
                        player.getDialogueManager().showFiveOptionsWithTitle("What would you like to say?", "How am I meant to fight a demon who can destroy cities?", "Okay, where is he? I'll kill him for you!", "Wally doesn't sound like a very heroic name.", "Where can I find Silverlight?", "Okay, thanks. I'll do my best to stop the demon.");
                    }
                    if (player.pendingGameMode == 4) {
                        player.getDialogueManager().showFiveOptionsWithTitle("What would you like to say?", "How am I meant to fight a demon who can destroy cities?", "Okay, where is he? I'll kill him for you!", "Wally doesn't sound like a very heroic name.", "What is the magical incantation?", "Okay, thanks. I'll do my best to stop the demon.");
                    }
                    return true;
                }
                if (value2 == 31) {
                    if (value32 == 1) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(30);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(30);
                        return true;
                    }
                    if (value32 == 3) {
                        if (player.pendingGameMode == 0) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What is the magical incantation?", 591);
                            player.getDialogueManager().setNextDialogueStep(34);
                            return true;
                        }
                        if (player.pendingGameMode == 3 || player.pendingGameMode == 4) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Wally doesn't sound like a very heroic name.", 591);
                            player.getDialogueManager().setNextDialogueStep(21);
                            return true;
                        }
                    }
                    if (value32 == 4) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 3) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Where can I find Silverlight?", 591);
                            player.getDialogueManager().setNextDialogueStep(32);
                            return true;
                        }
                        if (player.pendingGameMode == 4) {
                            player.getDialogueManager().showPlayerOneLineDialogue("What is the magical incantation?", 591);
                            player.getDialogueManager().setNextDialogueStep(34);
                            return true;
                        }
                    }
                    if (value32 == 5 && (player.pendingGameMode == 3 || player.pendingGameMode == 4)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Okay, thanks. I'll do my best to stop the demon.", 591);
                        player.getDialogueManager().setNextDialogueStep(37);
                        return true;
                    }
                }
                if (value2 == 32) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Silverlight has been passed down through Wally's", "descendants. I believe it is currently in the care of one", "of the King's knights called Sir Prysin.", 591);
                    return true;
                }
                if (value2 == 33) {
                    player.pendingGameMode = 4;
                    player.getDialogueManager().showNpcTwoLineDialogue("He shouldn't be too hard to find. He lives in the royal", "palace in this city. Tell him Gypsy Aris sent you.", 591);
                    player.getDialogueManager().setNextDialogueStep(30);
                    return true;
                }
                if (value2 == 34) {
                    player.getDialogueManager().showNpcOneLineDialogue("Oh yes, let me think a second...", 591);
                    return true;
                }
                if (value2 == 35) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Alright, I think I've got it now, it goes.... " + (String)arrayList.get(0) + "...", String.valueOf((String)arrayList.get(1)) + "... " + (String)arrayList.get(2) + "... " + (String)arrayList.get(3) + "... " + (String)arrayList.get(4) + ". Have you got", "that?", 591);
                    return true;
                }
                if (value2 == 36) {
                    player.pendingGameMode = 3;
                    player.getDialogueManager().showPlayerOneLineDialogue("I think so, yes.", 591);
                    player.getDialogueManager().setNextDialogueStep(30);
                    return true;
                }
                if (value2 == 37) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good luck, and may Guthix be with you!", 591);
                    this.startQuest(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What was the magical incantation again?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Let me think a second...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Alright, I think I've got it now, it goes.... " + (String)arrayList.get(0) + "...", String.valueOf((String)arrayList.get(1)) + "... " + (String)arrayList.get(2) + "... " + (String)arrayList.get(3) + "... " + (String)arrayList.get(4) + ". Have you got", "that?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I think so, yes.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 883) {
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello, who are you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("I am a mighty adventurer. Who are you?", "I'm not sure, I was hoping you could tell me.", "Gypsy Aris said I should come and talk to you.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I am a mighty adventurer. Who are you?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(2);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Gypsy Aris said I should come and talk to you.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I am Sir Prysin. A bold and famous knight of the", "realm.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Gypsy Aris? Is she still alive? I remember her from", "when I was pretty young. Well what do you need to", "talk to me about?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showTwoOptions("I need to find Silverlight.", "Yes, she is still alive.");
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I need to find Silverlight.", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                    } else {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(6);
                    }
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("What do you need to find that for?", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I need it to fight Delrith.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Delrith? I thought the world was rid of him, thanks to", "my great-grandfather.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showTwoOptions("Well, the gypsy's crystal ball seems to think otherwise.", "He's back and unfortunately I've got to deal with him.");
                    return true;
                }
                if (value2 == 12) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Well, the gypsy's crystal ball seems to think otherwise.", 591);
                        player.getDialogueManager().setNextDialogueStep(13);
                    } else {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(11);
                    }
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well if the ball says so, I'd better help you.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("The problem is getting Silverlight.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showPlayerOneLineDialogue("You mean you don't have it?", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcFourLineDialogue("Oh I do have it, but it is so powerful that the king", "made me put it in a special box which needs three", "different keys to open it. That way it won't fall into the", "wrong hands.", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showTwoOptions("So give me the keys!", "And why is this a problem?");
                    return true;
                }
                if (value2 == 18) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("And why is this a problem?", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                    } else {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(17);
                    }
                    return true;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I kept one of the keys. I gave the other two to other", "people for safe keeping.", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showNpcOneLineDialogue("One I gave to Rovin, the captain of the palace guard.", 591);
                    return true;
                }
                if (value2 == 21) {
                    player.pendingGameMode = 0;
                    player.setQuestState(this.getQuestId(), 3);
                    player.getDialogueManager().showNpcOneLineDialogue("I gave the other to the wizard Traiborn.", 591);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 22) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showThreeOptions("Can you give me your key?", "Where can I find Captain Rovin?", "Where does the wizard live?");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showThreeOptions("So what does the drain lead to?", "Where can I find Captain Rovin?", "Where does the wizard live?");
                    }
                    if (player.pendingGameMode == 2) {
                        player.getDialogueManager().showThreeOptions("Can you give me your key?", "Where does the wizard live?", "Well I'd better go key hunting.");
                    }
                    if (player.pendingGameMode == 3) {
                        player.getDialogueManager().showThreeOptions("Can you give me your key?", "Where can I find Captain Rovin?", "Well I'd better go key hunting.");
                    }
                    if (player.pendingGameMode == 11) {
                        player.getDialogueManager().showThreeOptions("Where can I find Captain Rovin?", "Where does the wizard live?", "Well I'd better go key hunting.");
                    }
                    return true;
                }
                if (value2 == 23) {
                    if (value32 == 1) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 2 || player.pendingGameMode == 3) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Can you give me your key?", 591);
                            player.getDialogueManager().setNextDialogueStep(27);
                            return true;
                        }
                        if (player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("So what does the drain connect to?", 591);
                            player.getDialogueManager().setNextDialogueStep(30);
                            return true;
                        }
                        if (player.pendingGameMode == 11) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Where can I find Captain Rovin?", 591);
                            player.getDialogueManager().setNextDialogueStep(26);
                            return true;
                        }
                    }
                    if (value32 == 2) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 1 || player.pendingGameMode == 3) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Where can I find Captain Rovin?", 591);
                            player.getDialogueManager().setNextDialogueStep(26);
                            return true;
                        }
                        player.getDialogueManager().showPlayerOneLineDialogue("Where does the wizard live?", 591);
                        player.getDialogueManager().setNextDialogueStep(24);
                        return true;
                    }
                    if (value32 == 3) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Where does the wizard live?", 591);
                            player.getDialogueManager().setNextDialogueStep(24);
                            return true;
                        }
                        player.getDialogueManager().showPlayerOneLineDialogue("Well I'd better go key hunting.", 591);
                        player.getDialogueManager().setNextDialogueStep(31);
                        return true;
                    }
                }
                if (value2 == 24) {
                    player.getDialogueManager().showNpcOneLineDialogue("Wizard Traiborn?", 591);
                    return true;
                }
                if (value2 == 25) {
                    player.pendingGameMode = 3;
                    player.getDialogueManager().showNpcThreeLineDialogue("He is one of the wizards who lives in the tower on the", "little island just off the south coast. I believe his", "quarters are on the first floor of the tower.", 591);
                    player.getDialogueManager().setNextDialogueStep(22);
                    return true;
                }
                if (value2 == 26) {
                    player.pendingGameMode = 2;
                    player.getDialogueManager().showNpcTwoLineDialogue("Captain Rovin lives at the top of the guards' quarters in", "the north-west wing of this palace.", 591);
                    player.getDialogueManager().setNextDialogueStep(22);
                    return true;
                }
                if (value2 == 27) {
                    player.getDialogueManager().showNpcOneLineDialogue("Um.... ah....", 591);
                    return true;
                }
                if (value2 == 28) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well there's a problem there as well.", 591);
                    return true;
                }
                if (value2 == 29) {
                    player.pendingGameMode = 1;
                    player.getDialogueManager().showNpcTwoLineDialogue("I managed to drop the key in the drain just outside the", "palace kitchen. It is just inside and I can't reach it.", 591);
                    player.getDialogueManager().setNextDialogueStep(22);
                    return true;
                }
                if (value2 == 30) {
                    player.pendingGameMode = 11;
                    player.getDialogueManager().showNpcTwoLineDialogue("It is the drain for the drainpipe running from the sink", "in the kitchen down to the palace sewers.", 591);
                    player.getDialogueManager().setNextDialogueStep(22);
                    return true;
                }
                if (value2 == 31) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ok, goodbye.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 3) {
                if (value42 == 30 && !player.ownsItem(2402)) {
                    value2 = 5;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("So how are you doing with getting the keys?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (DemonSlayerQuest.hasAllSilverlightKeys(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I've got all three keys!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("I don't have them yet.", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well, come back when you do.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Excellent! Now I can give you Silverlight.", 591);
                    return true;
                }
                if (value2 == 5) {
                    if (value42 < 30) {
                        if (!DemonSlayerQuest.hasAllSilverlightKeys(player)) {
                            return false;
                        }
                        player.getInventoryManager().removeItem(new ItemStack(2400, 1));
                        player.getInventoryManager().removeItem(new ItemStack(2401, 1));
                        player.getInventoryManager().removeItem(new ItemStack(2399, 1));
                        player.setQuestState(this.getQuestId(), 30);
                    }
                    player.getInventoryManager().addOrDropItem(new ItemStack(2402, 1));
                    player.getDialogueManager().showItemMessage("Sir Prysin hands you a very shiny sword.", new ItemStack(2402, 1));
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("That sword belonged to my great-grandfather. Make", "sure you treat it with respect!", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Now go kill that demon!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 884 && value42 >= 3 && value42 < 30) {
            if (player.ownsItem(2400)) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("What are you doing up here? Only the palace guards", "are allowed up here.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showThreeOptions("I am one of the palace guards.", "What about the King?", "Yes I know, but this is important.");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes, I know, but this is important.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                } else {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                }
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("Ok, I'm listening. Tell me what's so important.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showThreeOptions("There's a demon who wants to invade this city.", "Erm I forgot.", "The castle has just received its ale delivery.");
                return true;
            }
            if (value2 == 6) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("There's a demon who wants to invade this city.", 591);
                    player.getDialogueManager().setNextDialogueStep(7);
                } else {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(5);
                }
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showNpcOneLineDialogue("Is it a powerful demon?", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showTwoOptions("Not really.", "Yes, very.");
                return true;
            }
            if (value2 == 9) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes, very.", 591);
                    player.getDialogueManager().setNextDialogueStep(10);
                } else {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(8);
                }
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showNpcTwoLineDialogue("As good as the palace guards are, I don't know if", "they're up to taking on a very powerful demon.", 591);
                return true;
            }
            if (value2 == 11) {
                player.getDialogueManager().showTwoOptions("Yeah, the palace guards are rubbish!", "It's not them who are going to fight the demon, it's me.");
                return true;
            }
            if (value2 == 12) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("It's not them who are going to fight the demon, it's me.", 591);
                    player.getDialogueManager().setNextDialogueStep(13);
                } else {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(11);
                }
                return true;
            }
            if (value2 == 13) {
                player.getDialogueManager().showNpcOneLineDialogue("What, all by yourself? How are you going to do that?", 591);
                return true;
            }
            if (value2 == 14) {
                player.getDialogueManager().showPlayerTwoLineDialogue("I'm going to use the powerful sword Silverlight, which I", "believe you have one of the keys for?", 591);
                return true;
            }
            if (value2 == 15) {
                player.pendingGameMode = 0;
                player.getDialogueManager().showNpcOneLineDialogue("Yes, I do. But why should I give it to you?", 591);
                return true;
            }
            if (value2 == 16) {
                if (player.pendingGameMode == 0) {
                    player.getDialogueManager().showThreeOptions("Gypsy Aris said I was destined to kill the demon.", "Otherwise the demon will destroy the city!", "Sir Prysin said you would give me the key.");
                }
                if (player.pendingGameMode == 1) {
                    player.getDialogueManager().showTwoOptions("Otherwise the demon will destroy the city!", "Sir Prysin said you would give me the key.");
                }
                if (player.pendingGameMode == 2) {
                    player.getDialogueManager().showTwoOptions("Gypsy Aris said I was destined to kill the demon.", "Sir Prysin said you would give me the key.");
                }
                if (player.pendingGameMode == 13) {
                    player.getDialogueManager().showThreeOptions("Why did he give you one of the keys then?", "Gypsy Aris said I was destined to kill the demon.", "Otherwise the demon will destroy the city!");
                }
                return true;
            }
            if (value2 == 17) {
                if (value32 == 1) {
                    if (player.pendingGameMode == 0 || player.pendingGameMode == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Gypsy Aris said I was destined to kill the demon.", 591);
                        player.getDialogueManager().setNextDialogueStep(18);
                        return true;
                    }
                    if (player.pendingGameMode == 13) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Why did he give you one of the keys then?", 591);
                        player.getDialogueManager().setNextDialogueStep(22);
                        return true;
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Otherwise the demon will destroy the city!", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                }
                if (value32 == 2) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Otherwise the demon will destroy the city!", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                    if (player.pendingGameMode == 1 || player.pendingGameMode == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Sir Prysin said you would give me the key.", 591);
                        player.getDialogueManager().setNextDialogueStep(20);
                        return true;
                    }
                    if (player.pendingGameMode == 13) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Gypsy Aris said I was destined to kill the demon.", 591);
                        player.getDialogueManager().setNextDialogueStep(18);
                        return true;
                    }
                }
                if (value32 == 3) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Sir Prysin said you would give me the key.", 591);
                        player.getDialogueManager().setNextDialogueStep(20);
                        return true;
                    }
                    if (player.pendingGameMode == 13) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Otherwise the demon will destroy the city!", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                }
            }
            if (value2 == 18) {
                player.pendingGameMode = 1;
                player.getDialogueManager().showNpcThreeLineDialogue("A gypsy? Destiny? I don't believe in that stuff. I got", "where I am today by hard work, not by destiny! Why", "should I care what that mad old gypsy says?", 591);
                player.getDialogueManager().setNextDialogueStep(16);
                return true;
            }
            if (value2 == 19) {
                player.pendingGameMode = 2;
                player.getDialogueManager().showNpcTwoLineDialogue("You can't fool me! How do I know you haven't just", "made that story up to get my key?", 591);
                player.getDialogueManager().setNextDialogueStep(16);
                return true;
            }
            if (value2 == 20) {
                player.getDialogueManager().showNpcTwoLineDialogue("Oh, he did, did he? Well I don't report to Sir Prysin, I", "report directly to the king!", 591);
                return true;
            }
            if (value2 == 21) {
                player.pendingGameMode = 13;
                player.getDialogueManager().showNpcFourLineDialogue("I didn't work my way up through the ranks of the", "palace guards so I could take orders from an ill-bred", "moron who only has his job because his great-", "grandfather was a hero with a silly name!", 591);
                player.getDialogueManager().setNextDialogueStep(16);
                return true;
            }
            if (value2 == 22) {
                player.getDialogueManager().showNpcFourLineDialogue("Only because the king ordered him to! The king", "couldn't get Sir Prysin to part with his precious", "ancestral sword, but he made him lock it up so he", "couldn't lose it.", 591);
                return true;
            }
            if (value2 == 23) {
                player.getDialogueManager().showNpcTwoLineDialogue("I got one key and I think some wizard got another.", "Now what happened to the third one?", 591);
                return true;
            }
            if (value2 == 24) {
                player.getDialogueManager().showPlayerOneLineDialogue("Sir Prysin dropped it down a drain!", 591);
                return true;
            }
            if (value2 == 25) {
                player.getDialogueManager().showNpcOneLineDialogue("Ha ha ha! The idiot!", 591);
                return true;
            }
            if (value2 == 26) {
                player.getDialogueManager().showNpcTwoLineDialogue("Okay, I'll give you the key, just so that it's you that", "kills the demon and not Sir Prysin!", 591);
                return true;
            }
            if (value2 == 27) {
                player.getInventoryManager().addOrDropItem(new ItemStack(2400, 1));
                player.getDialogueManager().showItemMessage("Captain Rovin hands you a key.", new ItemStack(2400, 1));
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 881) {
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ello young thingummywut.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("What's a thingummywut?", "Teach me to be a mighty and powerful wizard.", "I need to get a key given to you by Sir Prysin.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I need to get a key given to you by Sir Prysin.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Sir Prysin? Who's that? What would I want his key", "for?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showThreeOptions("He told me you were looking after it for him.", "He's one of the King's knights.", "Well, have you got any keys knocking around?");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("He told me you were looking after it for him.", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Well, have you got any keys knocking around?", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("That wasn't very clever of him. I'd lose my head if it", "wasn't screwed on. Go and tell him to find someone else", "to look after his valuables in future.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showTwoOptions("Ok, I'll go and tell him that.", "Well, have you got any keys knocking around?");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Well, have you got any keys knocking around?", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Now you come to mention it, yes I do have a key. It's", "in my special closet of valuable stuff. Now how do I get", "into that?", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I sealed it using one of my magic rituals. So it would", "make sense that another ritual would open it again.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So do you know what ritual to use?", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcOneLineDialogue("Let me think a second.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcFourLineDialogue("Yes a simple drazier style ritual should suffice. Hmm,", "main problem with that is I'll need 25 sets of bones.", "Now where am I going to get hold of something like", "that?", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showTwoOptions("Hmm, that's too bad. I really need that key.", "I'll get the bones for you.");
                    return true;
                }
                if (value2 == 16) {
                    if (value32 == 1) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(15);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'll help get the bones for you.", 591);
                        player.getDialogueManager().setNextDialogueStep(17);
                        return true;
                    }
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ooh that would be very good of you.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok I'll speak to you when I've got some bones.", 591);
                    player.setQuestState(this.getQuestId(), 4);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 4 && value42 < 30) {
                if (value2 == 1 && player.ownsItem(2399)) {
                    return false;
                }
                if (value2 == 1 && value42 == 29) {
                    value2 = 7;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("How are you doing finding bones?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (player.getInventoryManager().containsItem(526)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have some bones.", 591);
                        return true;
                    }
                    player.getDialogueManager().showPlayerOneLineDialogue("I have none.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Give 'em here then.", 591);
                    return true;
                }
                if (value2 == 4) {
                    npcId = player.getInventoryManager().getItemAmount(526);
                    npcId = npcId < (value2 = 29 - value42) ? npcId : value2;
                    player.getDialogueManager().showOneLineStatement("You give Traiborn " + npcId + " sets of bones.");
                    if ((value42 += npcId) < 29) {
                        player.getDialogueManager().setNextDialogueStep(10);
                    }
                    if (value42 == 29) {
                        player.getDialogueManager().setNextDialogueStep(5);
                    }
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hurrah! That's all 25 sets of bones.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("Wings of dark and colour too,", "Spreading in the morning dew;", "Locked away I have a key;", "Return it now, please, unto me.", 591);
                    return true;
                }
                if (value2 == 7) {
                    if (value42 < 29) {
                        npcId = player.getInventoryManager().getItemAmount(526);
                        npcId = npcId < (value2 = 29 - value42) ? npcId : value2;
                        player.addQuestState(this.getQuestId(), npcId);
                        player.getInventoryManager().removeItem(new ItemStack(526, npcId));
                    }
                    player.getInventoryManager().addOrDropItem(new ItemStack(2399, 1));
                    player.getDialogueManager().showItemMessage("Traiborn hands you a key.", new ItemStack(2399, 1));
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thank you very much.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Not a problem for a friend of Sir What's-his-face.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 10) {
                    npcId = player.getInventoryManager().getItemAmount(526);
                    npcId = npcId < (value2 = 29 - value42) ? npcId : value2;
                    player.addQuestState(this.getQuestId(), npcId);
                    player.getInventoryManager().removeItem(new ItemStack(526, npcId));
                    value2 = 29 - (value42 += npcId);
                    player.getDialogueManager().showNpcOneLineDialogue("You still need to bring me " + value2 + " more.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 879 && value42 == 30) {
            if (value2 == 100) {
                player.pendingGameMode = 0;
                player.temporaryActionValue = 0;
                player.getDialogueManager().showPlayerOneLineDialogue("Now what was that incantation again?", 591);
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().showFiveOptionsWithTitle("Select a word", "Carlem", "Aber", "Camerinthum", "Purchai", "Gabindo");
                return true;
            }
            if (value2 == 102) {
                if (value32 == 1) {
                    if (((String)arrayList.get(player.temporaryActionValue)).equals(this.incantationWords.get(0))) {
                        ++player.pendingGameMode;
                    }
                    String text = "Carlem" + (player.temporaryActionValue == 4 ? "!" : "...");
                    player.getDialogueManager().showPlayerOneLineDialogue(text, 591);
                    player.getUpdateState().setForcedText(text);
                    if (player.pendingGameMode == 5) {
                        player.getDialogueManager().setNextDialogueStep(103);
                        return true;
                    }
                    ++player.temporaryActionValue;
                    if (player.temporaryActionValue == 5) {
                        Npc npc = Npc.findByDefinitionId(879);
                        npc.setCurrentHitpoints(npc.getMaxHitpoints());
                        player.getPacketSender().sendGameMessage("You didn't remember the words correctly!");
                        player.getDialogueManager().finishDialogue();
                    } else {
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
                if (value32 == 2) {
                    if (((String)arrayList.get(player.temporaryActionValue)).equals(this.incantationWords.get(1))) {
                        ++player.pendingGameMode;
                    }
                    String text2 = "Aber" + (player.temporaryActionValue == 4 ? "!" : "...");
                    player.getDialogueManager().showPlayerOneLineDialogue(text2, 591);
                    player.getUpdateState().setForcedText(text2);
                    if (player.pendingGameMode == 5) {
                        player.getDialogueManager().setNextDialogueStep(103);
                        return true;
                    }
                    ++player.temporaryActionValue;
                    if (player.temporaryActionValue == 5) {
                        Npc npc = Npc.findByDefinitionId(879);
                        npc.setCurrentHitpoints(npc.getMaxHitpoints());
                        player.getPacketSender().sendGameMessage("You didn't remember the words correctly!");
                        player.getDialogueManager().finishDialogue();
                    } else {
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
                if (value32 == 3) {
                    if (((String)arrayList.get(player.temporaryActionValue)).equals(this.incantationWords.get(2))) {
                        ++player.pendingGameMode;
                    }
                    String text3 = "Camerinthum" + (player.temporaryActionValue == 4 ? "!" : "...");
                    player.getDialogueManager().showPlayerOneLineDialogue(text3, 591);
                    player.getUpdateState().setForcedText(text3);
                    if (player.pendingGameMode == 5) {
                        player.getDialogueManager().setNextDialogueStep(103);
                        return true;
                    }
                    ++player.temporaryActionValue;
                    if (player.temporaryActionValue == 5) {
                        Npc npc = Npc.findByDefinitionId(879);
                        npc.setCurrentHitpoints(npc.getMaxHitpoints());
                        player.getPacketSender().sendGameMessage("You didn't remember the words correctly!");
                        player.getDialogueManager().finishDialogue();
                    } else {
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
                if (value32 == 4) {
                    if (((String)arrayList.get(player.temporaryActionValue)).equals(this.incantationWords.get(3))) {
                        ++player.pendingGameMode;
                    }
                    String text4 = "Purchai" + (player.temporaryActionValue == 4 ? "!" : "...");
                    player.getDialogueManager().showPlayerOneLineDialogue(text4, 591);
                    player.getUpdateState().setForcedText(text4);
                    if (player.pendingGameMode == 5) {
                        player.getDialogueManager().setNextDialogueStep(103);
                        return true;
                    }
                    ++player.temporaryActionValue;
                    if (player.temporaryActionValue == 5) {
                        Npc npc = Npc.findByDefinitionId(879);
                        npc.setCurrentHitpoints(npc.getMaxHitpoints());
                        player.getPacketSender().sendGameMessage("You didn't remember the words correctly!");
                        player.getDialogueManager().finishDialogue();
                    } else {
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
                if (value32 == 5) {
                    if (((String)arrayList.get(player.temporaryActionValue)).equals(this.incantationWords.get(4))) {
                        ++player.pendingGameMode;
                    }
                    String text5 = "Gabindo" + (player.temporaryActionValue == 4 ? "!" : "...");
                    player.getDialogueManager().showPlayerOneLineDialogue(text5, 591);
                    player.getUpdateState().setForcedText(text5);
                    if (player.pendingGameMode == 5) {
                        player.getDialogueManager().setNextDialogueStep(103);
                        return true;
                    }
                    ++player.temporaryActionValue;
                    if (player.temporaryActionValue == 5) {
                        Npc npc = Npc.findByDefinitionId(879);
                        npc.setCurrentHitpoints(npc.getMaxHitpoints());
                        player.getPacketSender().sendGameMessage("You didn't remember the words correctly!");
                        player.getDialogueManager().finishDialogue();
                    } else {
                        player.getDialogueManager().setNextDialogueStep(101);
                    }
                    return true;
                }
            }
            if (value2 == 103) {
                player.pendingGameMode = 0;
                player.temporaryActionValue = 0;
                player.getDialogueManager().showOneLineChatboxMessage("Delrith is sucked into the vortex...");
                Npc npc = Npc.findByDefinitionId(879);
                npc.setDead(true);
                CombatManager.handleDeath(npc);
                return true;
            }
            if (value2 == 104) {
                player.getDialogueManager().showOneLineStatement("...back into the dark dimension from which he came.");
                return true;
            }
            if (value2 == 105) {
                player.getDialogueManager().finishDialogue();
                this.awardCompletionRewards(player);
                return true;
            }
        }
        return false;
    }
}
