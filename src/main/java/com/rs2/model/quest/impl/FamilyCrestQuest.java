package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
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

public final class FamilyCrestQuest
extends QuestScript {
    public FamilyCrestQuest(int value2) {
        super(35);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            value2 = player.getSkillManager().getBaseLevel(14);
            int skillManager = player.getSkillManager().getBaseLevel(12);
            int skillManager2 = player.getSkillManager().getBaseLevel(13);
            int skillManager3 = player.getSkillManager().getBaseLevel(6);
            String[] stringValues = new String[]{"I can start this quest by speaking to Dimintheis in East", "Varrock", "To complete this quest I need:", String.valueOf(value2 >= 40 ? "@str@" : "") + "Level 40 Mining", String.valueOf(skillManager >= 40 ? "@str@" : "") + "Level 40 Crafting", String.valueOf(skillManager2 >= 40 ? "@str@" : "") + "Level 40 Smithing", String.valueOf(skillManager3 >= 59 ? "@str@" : "") + "Level 59 Magic", "I also need to be able to defeat a level 170 Demon"};
            return stringValues;
        }
        if (value2 == 2) {
            String[] stringValues2 = new String[]{"@str@I have agreed to restore Dimintheis' family crest to him.", "I need to find his son Caleb. The last Dimintheis heard of", "him he was a great chef beyond White Wolf Mountain"};
            return stringValues2;
        }
        if (value2 == 3) {
            String[] stringValues3 = new String[]{"I should bring the following cooked fish to Caleb:", "Swordfish", "Bass", "Tuna", "Salmon", "Shrimps"};
            return stringValues3;
        }
        if (value2 == 4) {
            String[] stringValues4 = new String[]{"I have one crest piece, I need to find the other two"};
            return stringValues4;
        }
        if (value2 == 5) {
            String[] stringValues5 = new String[]{"Caleb told me his brother Avan is on a treasure hunt in the", "desert somewhere - I should search for him there"};
            return stringValues5;
        }
        if (value2 == 6) {
            String[] stringValues6 = new String[]{"The Al-Kharid Gem Trader told me that Avan is wearing a", "yellow cape, and headed into the desert by the scorpions"};
            return stringValues6;
        }
        if (value2 == 7) {
            String[] stringValues7 = new String[]{"I should find a dwarf named Boot who lives somewhere in", "the mountains and knows where to find 'Perfect Gold'"};
            return stringValues7;
        }
        if (value2 == 8) {
            String[] stringValues8 = new String[]{"Avan will give me his crest piece if I can bring him a ruby", "ring and ruby necklace made from 'perfect gold'", "Boot told me 'perfect gold' can be found underground", "somewhere East of Ardougne, but is difficult to get to", "I need to make a ruby ring made of 'perfect gold'", "I need to make a ruby necklace made of 'perfect gold'"};
            return stringValues8;
        }
        if (value2 == 9) {
            String[] stringValues9 = new String[]{"I should ask Avan if he knows where to find his brother."};
            return stringValues9;
        }
        if (value2 == 10) {
            String[] stringValues10 = new String[]{"Avan told me I could possibly find Johnathon in a tavern", "near the edge of Wilderness."};
            return stringValues10;
        }
        if (value2 == 11) {
            String[] stringValues11 = new String[]{"I should try giving antipoison to Johnathon"};
            return stringValues11;
        }
        if (value2 == 12) {
            String[] stringValues12 = new String[]{"Johnathon is cured, I should now ask him for crest piece"};
            return stringValues12;
        }
        if (value2 == 13) {
            if (!player.ownsItem(781) && !player.ownsItem(782)) {
                String[] stringValues13 = new String[]{"I can find Chronozon somewhere below Obelisk of Air"};
                return stringValues13;
            }
            String[] stringValues14 = new String[]{"I should now bring the family crest back to Dimintheis"};
            return stringValues14;
        }
        if (value2 == 1) {
            String[] stringValues15 = new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "Steel Gauntlets", "A skill imbue for the gauntlets"};
            return stringValues15;
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
        player2.packetSender.sendInterfaceText("Steel Gauntlets", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("A skill imbue for the gauntlets", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getInventoryManager().addOrDropItem(new ItemStack(778, 1));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 778);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2425 && value2 == 2722 && value32 == 9718) {
            Player player2;
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(1)) != 0) {
                int questId = this.getQuestId();
                player.questProgressFlags[questId] = player.questProgressFlags[questId] - GameUtil.bitFlag(1);
                player2 = player;
                player2.packetSender.sendGameMessage("The lever is now down.");
            } else {
                int questId2 = this.getQuestId();
                player.questProgressFlags[questId2] = player.questProgressFlags[questId2] + GameUtil.bitFlag(1);
                player2 = player;
                player2.packetSender.sendGameMessage("The lever is now up.");
            }
            player2 = player;
            player2.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            return true;
        }
        if (objectId == 2421 && value2 == 2722 && value32 == 9710) {
            Player player3;
            if (value42 > 1 && value42 < 8) {
                Player player4 = player;
                player4.packetSender.sendGameMessage("You have no reason to do that.");
                return true;
            }
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(2)) != 0) {
                int questId3 = this.getQuestId();
                player.questProgressFlags[questId3] = player.questProgressFlags[questId3] - GameUtil.bitFlag(2);
                player3 = player;
                player3.packetSender.sendGameMessage("The lever is now down.");
            } else {
                int questId4 = this.getQuestId();
                player.questProgressFlags[questId4] = player.questProgressFlags[questId4] + GameUtil.bitFlag(2);
                player3 = player;
                player3.packetSender.sendGameMessage("The lever is now up.");
            }
            player3 = player;
            player3.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            return true;
        }
        if (objectId == 2423 && value2 == 2724 && value32 == 9669) {
            Player player5;
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) != 0) {
                int questId5 = this.getQuestId();
                player.questProgressFlags[questId5] = player.questProgressFlags[questId5] - GameUtil.bitFlag(3);
                player5 = player;
                player5.packetSender.sendGameMessage("The lever is now down.");
            } else {
                int questId6 = this.getQuestId();
                player.questProgressFlags[questId6] = player.questProgressFlags[questId6] + GameUtil.bitFlag(3);
                player5 = player;
                player5.packetSender.sendGameMessage("The lever is now up.");
            }
            player5 = player;
            player5.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            return true;
        }
        if (objectId == 2431 && value2 == 2723 && value32 == 9711) {
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(2)) == 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) != 0) {
                Player player6 = player;
                player6.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player6 = player;
                player6.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9711 ? 1 : -1, true);
            } else {
                Player player7 = player;
                player7.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 2430 && value2 == 2727 && value32 == 9690) {
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(1)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(2)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) == 0) {
                Player player8 = player;
                player8.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2728 ? 1 : -1, 0, true);
            } else {
                Player player9 = player;
                player9.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 2427 && value2 == 2719 && value32 == 9671) {
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(2)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) != 0) {
                Player player10 = player;
                player10.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player10 = player;
                player10.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9672 ? 1 : -1, true);
            } else {
                Player player11 = player;
                player11.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 2429 && value2 == 2722 && value32 == 9671) {
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(2)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) == 0) {
                Player player12 = player;
                player12.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player12 = player;
                player12.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9672 ? 1 : -1, true);
            } else {
                Player player13 = player;
                player13.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        Player player2 = player;
        if (player2.getInventoryManager().containsItem(779) && player2.getInventoryManager().containsItem(780) && player2.getInventoryManager().containsItem(781) && FamilyCrestQuest.isCrestPiece(itemId) && FamilyCrestQuest.isCrestPiece(value2)) {
            player.getInventoryManager().removeItem(new ItemStack(779, 1));
            player.getInventoryManager().removeItem(new ItemStack(780, 1));
            player.getInventoryManager().removeItem(new ItemStack(781, 1));
            player.getInventoryManager().addItem(new ItemStack(782, 1));
            player.packetSender.sendGameMessage("You have restored the Family Crest.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnNpc(Player player, int npcId, int value2, int value32) {
        if (value32 == 11) {
            boolean enabled;
            value32 = value2;
            switch (value32) {
                case 175: 
                case 177: 
                case 179: 
                case 181: 
                case 183: 
                case 185: 
                case 2446: 
                case 2448: 
                case 5943: 
                case 5945: 
                case 5947: 
                case 5949: 
                case 5952: 
                case 5954: 
                case 5956: 
                case 5958: {
                    enabled = true;
                    break;
                }
                default: {
                    enabled = false;
                }
            }
            if (enabled && npcId == 668) {
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.setQuestState(this.getQuestId(), 12);
                DialogueManager.continueDialogue(player, 668, 100, 0);
                return true;
            }
        }
        return false;
    }

    private static boolean isCrestPiece(int value2) {
        return value2 == 779 || value2 == 780 || value2 == 781;
    }

    @Override
    public final boolean handleNpcDeathDrop(Player npcId, int npcId2, Position position, int value2) {
        if (npcId2 == 667 && value2 == 13 && !((Player)npcId).ownsItem(781) && !((Player)npcId).ownsItem(782)) {
            GroundItem groundItem = new GroundItem(new ItemStack(781, 1), (Entity)npcId, position);
            GroundItemManager.getInstance().spawn(groundItem);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc() && entity.isPlayer()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 667) {
                if (!(((Npc)entity2).chronozonHitByWindBlast && ((Npc)entity2).chronozonHitByWaterBlast && ((Npc)entity2).chronozonHitByEarthBlast && ((Npc)entity2).chronozonHitByFireBlast)) {
                    ((Npc)entity2).setCurrentHitpoints(((Npc)entity2).getMaxHitpoints());
                    ((Npc)entity2).chronozonHitByWindBlast = false;
                    ((Npc)entity2).chronozonHitByWaterBlast = false;
                    ((Npc)entity2).chronozonHitByEarthBlast = false;
                    ((Npc)entity2).chronozonHitByFireBlast = false;
                    ((Player)entity).packetSender.sendGameMessage("Chronozon regenerates.");
                } else {
                    entity2.setDead(true);
                    ((Npc)entity2).chronozonHitByWindBlast = false;
                    ((Npc)entity2).chronozonHitByWaterBlast = false;
                    ((Npc)entity2).chronozonHitByEarthBlast = false;
                    ((Npc)entity2).chronozonHitByFireBlast = false;
                    CombatManager.handleDeath(entity2);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean hasFamilyGauntlets(Player player) {
        return player.getInventoryManager().containsItem(778) || player.getInventoryManager().containsItem(775) || player.getInventoryManager().containsItem(776) || player.getInventoryManager().containsItem(777);
    }

    private static boolean hasAnyCalebFishIngredient(Player player) {
        return player.getInventoryManager().containsItem(373) || player.getInventoryManager().containsItem(365) || player.getInventoryManager().containsItem(361) || player.getInventoryManager().containsItem(329) || player.getInventoryManager().containsItem(315);
    }

    private static boolean hasAllCalebFishIngredients(Player player) {
        return player.getInventoryManager().containsItem(373) && player.getInventoryManager().containsItem(365) && player.getInventoryManager().containsItem(361) && player.getInventoryManager().containsItem(329) && player.getInventoryManager().containsItem(315);
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 664) {
            if (value42 == 1) {
                Player player2 = player;
                if (player2.ownsItem(778) || player2.ownsItem(775) || player2.ownsItem(776) || player2.ownsItem(777)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I lost my gauntlets.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Here you go.", 591);
                    player.getInventoryManager().addOrDropItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hello. My name is Dimintheis, of the noble family", "Fitzharmon.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("Why would a nobleman live in a dump like this?", "You're rich then? Can I have some money?", "Hi, I am a bold adventurer.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Why would a nobleman live in a dump like this?", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Hi, I am a bold adventurer.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("An adventurer hmmm? How lucky. I may have an", "adventure for you. I desperately need my family crest", "returning to me. It is of utmost importance.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showThreeOptions("Why are you so desperate for it?", "So where is this crest?", "I'm not interested in that adventure right now.");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Why are you so desperate for it?", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So where is this crest?", 591);
                        player.getDialogueManager().setNextDialogueStep(13);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm not interested in that adventure right now.", 591);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, there is a long standing rule of chivalry amongst", "the Varrockian aristocracy, where each noble family is", "in", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("possession of a unique crest, which signifies the honour", "and lineage of the family. More than this however, it", "also represents the lawful rights of each family", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("to prove their ownership of their wealth and lands. If", "the family crest is lost, then the family's estate is handed", "over to the current monarch until the crest is restored.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("This dates back to the times when there was much in-", "fighting amongst the noble families and their clans, and", "was introduced as a way of reducing the", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("bloodshed that was devestating the ranks of the ruling", "classes at that time. When you captured a rival family's", "clan, you also captured their lands and wealth.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So where is this crest?", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, my three sons took it with them many years ago", "when they rode out to fight in the war against the", "undead necromancer and his army in", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("the battle to save Varrock. For many years I had", "assumed them all dead, as I had heard no word from", "them, but recently I heard word that my son", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Caleb is alive and well, and trying to earn his fortune", "as a great chef in the lands beyond White Wolf", "Mountain, although I know not where.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showTwoOptions("Ok, I will help you.", "I'm not interested in that adventure right now.");
                    return true;
                }
                if (value2 == 17) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will help you.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(18);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 19) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The King has taken my estate from me until such time", "as I can show my family crest to him.", 591);
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showTwoOptions("Why would he do that?", "So where is this crest?");
                    return true;
                }
                if (value2 == 21) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Why would he do that?", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So where is this crest?", 591);
                        player.getDialogueManager().setNextDialogueStep(13);
                        return true;
                    }
                }
            }
            if (value42 == 2 && value2 == 18) {
                player.getDialogueManager().showNpcTwoLineDialogue("I thank you greatly adventurer! Also... if you find", "Caleb... please... let him know his father still loves him...", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 13) {
                if (value2 == 1 && player.getInventoryManager().containsItem(782)) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have retrieved your crest.", 591);
                    return true;
                }
                if (value2 == 2 && player.getInventoryManager().containsItem(782)) {
                    player.getInventoryManager().removeItem(new ItemStack(782, 1));
                    player.setQuestState(this.getQuestId(), 14);
                    player.getDialogueManager().showNpcThreeLineDialogue("Adventurer... I can only thank you for your kindness,", "although the words are insufficient to express the", "gratitude I feel!", 591);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
            }
            if (value42 == 14) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You are truly a hero in every sense, and perhaps your", "efforts can begin to patch the wounds that have torn", "this family apart...", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I know not how I can adequately reward you for your", "efforts... although", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I do have these mystical gauntlets, a family heirloom", "that through some power unknown to me, have always", "returned to the head of the family", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("whenever lost, or if the owner has died. I will pledge", "these to you, and if you should lose them return to me,", "and they will be here.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("They can also be granted extra powers. Take them to", "one of my sons, they should be able to imbue them with", "a skill for you.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 666) {
            if (value42 == 1 && FamilyCrestQuest.hasFamilyGauntlets(player) && player.familyCrestGauntletItemId != 775) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I hear you have brought the completed crest to my", "father. I must say, That was awfully impressive work.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I believe your father mentioned you would be able to", "improve these gauntlets for me...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, that is correct. I can slightly alter these gauntlets", "to allow you some of my skill at preparing seafood by", "making them cooking gauntlets.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("When you wear them you will burn lobsters, sharks and", "swordfish a lot less. It will be a permanent modification", "however, so make sure this is what you want.", 591);
                    return true;
                }
                if (value2 == 5) {
                    if (player.familyCrestGauntletItemId != 778) {
                        player.getDialogueManager().showTwoOptions("Can you change my gauntlets for me?", "No thanks.");
                    } else {
                        player.getDialogueManager().showTwoOptions("Yes, please do that for me.", "I'll see what your brothers have to offer first.");
                    }
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        if (player.familyCrestGauntletItemId != 778) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Can you change my gauntlets for me?", 591);
                            player.getDialogueManager().setNextDialogueStep(8);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("Yes, please do that for me.", 591);
                            player.getDialogueManager().setNextDialogueStep(7);
                        }
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 7) {
                    if (player.familyCrestGauntletItemId != 778) {
                        player.getInventoryManager().removeItem(new ItemStack(995, 25000));
                    }
                    player.getDialogueManager().showThreeLineItemMessage("Caleb takes the gauntlets from you, pours some herbs", "and seasonings on them, bakes them on his range for a", "short time, then hands them back to you.", new ItemStack(775, 1));
                    player.getInventoryManager().removeItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.familyCrestGauntletItemId = 775;
                    player.getInventoryManager().addOrDropItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes certainly, though it will cost you 25,000 coins?", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showTwoOptions("Great thanks!", "No that's ok thanks.");
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 1 && player.getInventoryManager().containsItemAmount(995, 25000)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Great thanks!", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Who are you? What are you after?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("Are you Caleb Fitzharmon?", "Nothing, I will be on my way.", "I see you are a chef, will you cook me anything?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Are you Caleb Fitzharmon?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Why... yes I am, but I don't believe I know you... how", "did you know my name?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I have been sent by your father. He wishes the", "Fitzharmon Crest to be restored.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah... well... hmmm... yes... I do have a piece of it", "anyway...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showTwoOptions("Uh... what happened to the rest of it?", "So can I have your bit?");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Uh... what happened to the rest of it?", 591);
                        player.getDialogueManager().setNextDialogueStep(100);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So can I have your bit?", 591);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, I am the oldest son, so by the rules of chivalry, I", "am most entitled to be rightful bearer of the crest.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("It's not really much use without the other fragments is", "it though?", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well that is true... perhaps it is time to put my pride", "aside... I'll tell you what: I'm struggling to complete this", "fish salad of mine,", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcThreeLineDialogue("so if you will assist me in my search for the", "ingredients, then I will let you take my piece as reward", "for your assistance.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So what ingredients are you missing?", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I require the following cooked fish: Swordfish, Bass,", "Tuna, Salmon and Shrimp.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showTwoOptions("Ok, I will get those.", "Why don't you just give me the crest?");
                    return true;
                }
                if (value2 == 19) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will get those.", 591);
                        player.setQuestState(this.getQuestId(), 3);
                        player.getDialogueManager().setNextDialogueStep(20);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(18);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("How is the fish collecting going?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (!FamilyCrestQuest.hasAnyCalebFishIngredient(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have none of them yet.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    if (FamilyCrestQuest.hasAnyCalebFishIngredient(player) && !FamilyCrestQuest.hasAllCalebFishIngredients(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I don't have all of them yet.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    if (FamilyCrestQuest.hasAllCalebFishIngredients(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Got them all with me.", 591);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("You exchange the fish for Caleb's piece of the crest.");
                    return true;
                }
                if (value2 == 4 && FamilyCrestQuest.hasAllCalebFishIngredients(player)) {
                    player.getInventoryManager().removeItem(new ItemStack(373, 1));
                    player.getInventoryManager().removeItem(new ItemStack(365, 1));
                    player.getInventoryManager().removeItem(new ItemStack(361, 1));
                    player.getInventoryManager().removeItem(new ItemStack(329, 1));
                    player.getInventoryManager().removeItem(new ItemStack(315, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(780, 1));
                    player.setQuestState(this.getQuestId(), 4);
                    player.getDialogueManager().showTwoOptions("Uh... what happened to the rest of it?", "Thank you very much!");
                    return true;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showNpcOneLineDialogue("You will? It would help me a lot!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 4 && !player.ownsItem(780) && !player.ownsItem(782) && value2 == 1) {
                player.getInventoryManager().addOrDropItem(new ItemStack(780, 1));
                player.getDialogueManager().showNpcOneLineDialogue("Don't lose it this time!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hello again. I'm just putting the finishing touches to my", "masterful salad.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("Uh.. what happened to the rest of the crest?", "Good luck with that then.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Uh... what happened to the rest of it?", 591);
                        player.getDialogueManager().setNextDialogueStep(100);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 5) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Uh... what happened to the rest of it?", 591);
                        player.getDialogueManager().setNextDialogueStep(100);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Thank you very much.", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("You're welcome.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 2 && value42 <= 4) {
                if (value2 == 100) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well... my brothers and I had a slight disagreement", "about it... we all wanted to be heir to my fathers' lands,", "and we each ended up with a piece of the crest.", 591);
                    return true;
                }
                if (value2 == 101) {
                    player.getDialogueManager().showNpcThreeLineDialogue("None of us wanted to give up our rights to our", "brothers, so we didn't want to give up our pieces of the", "crest, but none of us wanted to face our father by", 591);
                    return true;
                }
                if (value2 == 102) {
                    player.getDialogueManager().showNpcThreeLineDialogue("returning to him with an incomplete crest... We each", "went our seperate ways many years past, none of us", "seeing our father or willing to give up our fragments.", 591);
                    if (value42 < 4) {
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value2 == 103) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("So do you know where I could find any of your", "brothers?", 591);
                    return true;
                }
                if (value2 == 104) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, we haven't really kept in touch... what with the", "dispute over the crest and all... I did hear from my", "brother Avan a while ago though..", 591);
                    return true;
                }
                if (value2 == 105) {
                    player.getDialogueManager().showNpcThreeLineDialogue("He said he was on some kind of search for treasure, or", "gold, or something out in the desert. You might want to", "ask around there for him.", 591);
                    return true;
                }
                if (value2 == 106) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Avan always did have expensive tastes however. You", "may find he is not prepared to hand over his crest", "piece to you as easily as I have.", 591);
                    player.setQuestState(this.getQuestId(), 5);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 540 && value42 == 5) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("Good day to you traveller. Would you be interested in", "buying some gems?", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showThreeOptions("Yes please.", "No thank you.", "I'm in search of a man named Avan Fitzharmon.");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm in search of a man named Avan Fitzharmon.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcThreeLineDialogue("Fitzharmon eh? Hmmm.... if I'm not mistaken that's the", "family name of a member of the Varrockian nobility.", "You know, I HAVE seen someone of that", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcThreeLineDialogue("persuasion around here recently... Wearing a poncey", "yellow cape he was! Came in here all la-di-dah, high and", "mighty, asking for jewelry made", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcThreeLineDialogue("from 'perfect gold' - whatever that is - like 'normal'", "golds just not good enough for little lord fancy pants", "there! I told him to head to the desert", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showNpcThreeLineDialogue("'Cos I know theres gold out there, in them there sand", "dunes... and if it's not up to his lordships high standards", "of 'gold perfection'...", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcTwoLineDialogue("Maybe we'll all get lucky and the scorpions will deal with", "him.", 591);
                player.setQuestState(this.getQuestId(), 6);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 663) {
            if (value42 == 1 && FamilyCrestQuest.hasFamilyGauntlets(player) && player.familyCrestGauntletItemId != 776) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I have received word from my father of your assistance", "to our family in this matter. You have my thanks for", "restoring our honour.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Not a problem.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes well... Is there anything I can help you with?", 591);
                    return true;
                }
                if (value2 == 4) {
                    if (player.familyCrestGauntletItemId != 778) {
                        player.getDialogueManager().showTwoOptions("Can you change my gauntlets for me?", "No thanks.");
                    } else {
                        player.getDialogueManager().showTwoOptions("Enchant my gauntlets please.", "I'll see what your brothers have to offer first.");
                    }
                    return true;
                }
                if (value2 == 5) {
                    if (value32 == 1) {
                        if (player.familyCrestGauntletItemId != 778) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Can you change my gauntlets for me?", 591);
                            player.getDialogueManager().setNextDialogueStep(6);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("Enchant my gauntlets please.", 591);
                            player.getDialogueManager().setNextDialogueStep(9);
                        }
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes certainly, though it will cost you 25,000 coins?", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showTwoOptions("Great thanks!", "No that's ok thanks.");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1 && player.getInventoryManager().containsItemAmount(995, 25000)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Great thanks!", 591);
                        player.getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 9) {
                    if (player.familyCrestGauntletItemId != 778) {
                        player.getInventoryManager().removeItem(new ItemStack(995, 25000));
                    }
                    player.getDialogueManager().showTwoLineStatement("Avan takes your gauntlets, takes out a small hammer, and pounds", "them into a slightly new shape, then hands them back to you.");
                    player.getInventoryManager().removeItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.familyCrestGauntletItemId = 776;
                    player.getInventoryManager().addOrDropItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 6) {
                if (value2 == 1) {
                    player.getDialogueManager().showTwoOptions("Why are you hanging around in a scorpion pit?", "I'm looking for a man named Avan Fitzharmon.");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm looking for a man... his name is Avan Fitzharmon.", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Then you have found him. My name is Avan", "Fitzharmon.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("You have a part of your family crest. I am on a quest", "to retrieve all of the fragmented pieces and restore the", "crest.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ha! I suppose one of my worthless brothers has sent", "you on this quest then?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("No, it was your father who has asked me to do this for", "him.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("My... my father wishes this? Then that is a different", "matter. I will let you have my crest shard, adventurer,", "but you must first do something for me.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("There is a certain lady I am trying to impress. As a", "man of noble birth, I can not give her just any old", "trinket to show my devotion. What I intend to give her,", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("is a golden ring, embedded with the finest precious red", "stone available, and a necklace to match this ring. The", "problem however for me, is that", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("not just any gold will be suitable. I seek only the", "purest, the most high quality of gold - what I seek, if", "you will, is perfect gold.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("None of the gold around here is even remotely suitable", "in terms of quality. I have searched far and wide for", "the perfect gold I desire, but have had no success so", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcThreeLineDialogue("in finding it I am afraid. If you can find me my", "perfect gold, make a ring and necklace from it, and add", "rubies to them, I will", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("gladly hand over my fragment of the family crest to", "you.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can you give me any help on finding this 'perfect gold'?", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I thought I had found a solid lead on its whereabouts", "when I heard of a dwarf who is an expert on gold who", "goes by the name of 'Boot'.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Unfortunately he has apparently returned to his home,", "somewhere in the mountains, and I have no idea how to", "find him.", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Well, I'll see what I can do.", 591);
                    player.setQuestState(this.getQuestId(), 7);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 7 && value42 < 9) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("So how are you doing getting me my perfect gold", "jewelry?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (player.getInventoryManager().containsItem(774) && player.getInventoryManager().containsItem(773)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have the ring and necklace right here.", 591);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("Still working on getting them.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("You hand Avan the perfect gold ring and necklace.");
                    return true;
                }
                if (value2 == 4 && player.getInventoryManager().containsItem(774) && player.getInventoryManager().containsItem(773)) {
                    player.getInventoryManager().removeItem(new ItemStack(773, 1));
                    player.getInventoryManager().removeItem(new ItemStack(774, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(779, 1));
                    player.getDialogueManager().showNpcThreeLineDialogue("These... these are exquisite! EXACTLY what I was", "searching for all of this time! Please, take my crest", "fragment!", 591);
                    player.getDialogueManager().setNextDialogueStep(1);
                    player.setQuestState(this.getQuestId(), 9);
                    return true;
                }
            }
            if (value42 >= 9 && !player.ownsItem(779) && !player.ownsItem(782) && value2 == 1) {
                player.getInventoryManager().addOrDropItem(new ItemStack(779, 1));
                player.getDialogueManager().showNpcOneLineDialogue("Don't lose it this time!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 9 || value42 == 10) {
                if (value2 == 1) {
                    if (value42 == 9) {
                        player.getDialogueManager().showNpcThreeLineDialogue("Now, I suppose you will be wanting to find my brother", "Johnathon who is in possession of the final piece of the", "family's crest?", 591);
                    } else {
                        player.getDialogueManager().showPlayerTwoLineDialogue("Where did you say I could find your brother", "Johnathon again?", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("That's correct.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, the last I heard of my brother Johnathon, he was", "studying the magical arts, and trying to hunt some", "demon or other out in The Wilderness.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Unsurprisingly, I do not believe he is doing a", "particularly good job of things, and spends most of his", "time recovering from his injuries in", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("some tavern or other near the edge of The Wilderness.", "You'll probably find him still there.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thanks Avan.", 591);
                    if (value42 == 9) {
                        player.setQuestState(this.getQuestId(), 10);
                    }
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 665 && value42 >= 7) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Hello tall person.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showThreeOptions("Hello. I'm in search of very high quality gold.", "Hello short person.", "Why are you called boot?");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello. I'm in search of very high quality gold.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcThreeLineDialogue("High quality gold eh? Hmmm... Well, the very best", "quality gold that I know of, is just East of the large", "city Ardougne, underground somewhere.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("I don't believe it's exactly easy to get to though...", 591);
                if (value42 == 7) {
                    player.setQuestState(this.getQuestId(), 8);
                }
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 668) {
            if (value42 == 1 && FamilyCrestQuest.hasFamilyGauntlets(player) && player.familyCrestGauntletItemId != 777) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello again.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Your father tells me you can improve these gauntlets", "for me...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcFourLineDialogue("And right he is at that. Although my skill with Death", "Rune magicks was not great enough to defeat", "Chronozon, I am adequately skilled at Chaos Rune", "magicks.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("If you give me your gauntlets I will bestow upon them", "some of my power, and make any bolt spells you wish", "to cast more effective.", 591);
                    return true;
                }
                if (value2 == 5) {
                    if (player.familyCrestGauntletItemId != 778) {
                        player.getDialogueManager().showTwoOptions("Can you change my gauntlets for me?", "No thanks.");
                    } else {
                        player.getDialogueManager().showTwoOptions("That sounds good to me.", "I shall see what options your brothers can offer me first.");
                    }
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        if (player.familyCrestGauntletItemId != 778) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Can you change my gauntlets for me?", 591);
                            player.getDialogueManager().setNextDialogueStep(8);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("That sounds good to me!", 591);
                            player.getDialogueManager().setNextDialogueStep(7);
                        }
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 7) {
                    if (player.familyCrestGauntletItemId != 778) {
                        player.getInventoryManager().removeItem(new ItemStack(995, 25000));
                    }
                    player.getDialogueManager().showThreeLineStatement("Johnathon takes your gauntlets from you, and begins a low chant", "over them. You see them begin to glow and sparkle, before he", "returns them to you.");
                    player.getInventoryManager().removeItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.familyCrestGauntletItemId = 777;
                    player.getInventoryManager().addOrDropItem(new ItemStack(player.familyCrestGauntletItemId, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes certainly, though it will cost you 25,000 coins?", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showTwoOptions("Great thanks!", "No that's ok thanks.");
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 1 && player.getInventoryManager().containsItemAmount(995, 25000)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Great thanks!", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 10) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Greetings. Would you happen to be Johnathon", "Fitzharmon?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("That... I am...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I am here to retrieve your fragment of the Fitzharmon", "family crest.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The... poison... it is all... too much... My head... will", "not... stop spinning...", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("Sweat is pouring down Johnathons' face.");
                    player.setQuestState(this.getQuestId(), 11);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 12) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ooooh... thank you... Wow! I'm feeling a lot better now!", "That potion really seems to have done the trick!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("How can I reward you?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I've come here for your piece of the Fitzharmon family", "crest.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You have? Unfortunately I don't have it any more... in", "my attempts to slay the fiendish Chronozon, the blood", "demon, I lost a lot of equipment in", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.setQuestState(this.getQuestId(), 13);
                    player.pendingGameMode = 0;
                    player.getDialogueManager().showNpcTwoLineDialogue("our last battle when he bested me and forced me away", "from his den. He probably still has it now.", 591);
                    return true;
                }
                if (value2 == 100) {
                    player.getDialogueManager().showOneLineStatement("You use the potion on Johnathon.");
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
            }
            if (value42 == 13) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I'm trying to kill this demon Chronozon that you", "mentioned...", 591);
                    player.pendingGameMode = 13;
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 6) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showThreeOptions("So is this Chronozon hard to defeat?", "Where can I find Chronozon?", "So how did you end up getting poisoned?");
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showThreeOptions("Where can I find Chronozon?", "So how did you end up getting poisoned?", "I will be on my way now.");
                    }
                    if (player.pendingGameMode == 2) {
                        player.getDialogueManager().showThreeOptions("So is this Chronozon hard to defeat?", "So how did you end up getting poisoned?", "I will be on my way now.");
                    }
                    if (player.pendingGameMode == 3) {
                        player.getDialogueManager().showThreeOptions("So is this Chronozon hard to defeat?", "Where can I find Chronozon?", "I will be on my way now.");
                    }
                    if (player.pendingGameMode == 13) {
                        player.getDialogueManager().showThreeOptions("So is this Chronozon hard to defeat?", "Where can I find Chronozon?", "Wish me luck.");
                    }
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 1) {
                        if (player.pendingGameMode == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Where can I find Chronozon?", 591);
                            player.getDialogueManager().setNextDialogueStep(11);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("So is this Chronozon hard to defeat?", 591);
                            player.getDialogueManager().setNextDialogueStep(8);
                        }
                        return true;
                    }
                    if (value32 == 2) {
                        if (player.pendingGameMode == 0 || player.pendingGameMode == 3) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Where can I find Chronozon?", 591);
                            player.getDialogueManager().setNextDialogueStep(11);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("So how did you end up getting poisoned?", 591);
                            player.getDialogueManager().setNextDialogueStep(10);
                        }
                        return true;
                    }
                    if (value32 == 3) {
                        if (player.pendingGameMode == 0) {
                            player.getDialogueManager().showPlayerOneLineDialogue("So how did you end up getting poisoned?", 591);
                            player.getDialogueManager().setNextDialogueStep(10);
                        } else {
                            player.getDialogueManager().showPlayerOneLineDialogue("I will be on my way now.", 591);
                            player.getDialogueManager().setNextDialogueStep(12);
                        }
                        return true;
                    }
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well... you will have to be a skilled Mage to defeat him,", "and my powers are not good enough yet. You will need", "to hit him once with each of the four elemental spells of", "death", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("before he will be defeated.", 591);
                    player.pendingGameMode = 1;
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Those accursed poison spiders that surround the", "entrance to Chronozon's lair... I must have taken a nip", "from one of them as I attempted to make my escape.", 591);
                    player.pendingGameMode = 3;
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The fiend has made his lair in the Wilderness below the", "Obelisk of Air.", 591);
                    player.pendingGameMode = 2;
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("My thanks for the assistance adventurer.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}
