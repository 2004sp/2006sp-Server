package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class HeroesQuest
extends QuestScript {
    public HeroesQuest(int value2) {
        super(50);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            value2 = player.getSkillManager().getBaseLevel(15);
            int skillManager = player.getSkillManager().getBaseLevel(14);
            int skillManager2 = player.getSkillManager().getBaseLevel(10);
            int skillManager3 = player.getSkillManager().getBaseLevel(7);
            boolean enabled = false;
            if (player.getQuestPoints() >= 56 && player.getQuestState(16) == 1 && player.getQuestState(58) == 1 && player.getQuestState(5) == 1 && player.getQuestState(61) == 1 && player.getQuestState(29) == 1) {
                enabled = true;
            }
            if (enabled) {
                return new String[]{"I can start this quest by speaking to Achietties at the", "Heroes' Guild located North of Taverley", "as all reguired quests are complete, and I have enough QP.", "To complete this quest I need:", String.valueOf(value2 >= 25 ? "@str@" : "") + "Level 25 Herblore", String.valueOf(skillManager >= 50 ? "@str@" : "") + "Level 50 Mining", String.valueOf(skillManager2 >= 53 ? "@str@" : "") + "Level 53 Fishing", String.valueOf(skillManager3 >= 53 ? "@str@" : "") + "Level 53 Cooking"};
            }
            return new String[]{"I can start this quest by speaking to Achietties at the", "Heroes' Guild located North of Taverley.", "To start this quest I need:", String.valueOf(player.getQuestPoints() >= 56 ? "@str@" : "") + "56 Quest Points", String.valueOf(player.getQuestState(16) == 1 ? "@str@" : "") + "Complete Shield of Arrav", String.valueOf(player.getQuestState(58) == 1 ? "@str@" : "") + "Complete Lost City", String.valueOf(player.getQuestState(5) == 1 ? "@str@" : "") + "Complete Dragon Slayer", String.valueOf(player.getQuestState(61) == 1 ? "@str@" : "") + "Complete Merlin's Crystal", String.valueOf(player.getQuestState(29) == 1 ? "@str@" : "") + "Complete Druidic Ritual", "To complete this quest I need:", String.valueOf(value2 >= 25 ? "@str@" : "") + "Level 25 Herblore", String.valueOf(skillManager >= 50 ? "@str@" : "") + "Level 50 Mining", String.valueOf(skillManager2 >= 53 ? "@str@" : "") + "Level 53 Fishing", String.valueOf(skillManager3 >= 53 ? "@str@" : "") + "Level 53 Cooking"};
        }
        if (value2 == 2 || value2 == 8) {
            String[] stringValues = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", HeroesQuest.hasAllHeroesGuildEntryItems(player) ? "I should bring the items to Achietties now." : ""};
            return stringValues;
        }
        if (value2 == 3) {
            String[] stringValues2 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", String.valueOf(player.gangAffiliation == 1 ? "She" : "He") + " told me I can get one by stealing Pete's Candlestick", "I should use the password " + (player.gangAffiliation == 1 ? "she" : "he") + " gave me at Brimhaven"};
            return stringValues2;
        }
        if (value2 == 4 && player.gangAffiliation == 1) {
            String[] stringValues3 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", "I should impersonate Hartigen the Black Knight to gain", "access to the mansion."};
            return stringValues3;
        }
        if (value2 == 4 && player.gangAffiliation == 2) {
            String[] stringValues4 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", "I should go talk to Charlie the Cook in the back", "of the bar."};
            return stringValues4;
        }
        if (value2 == 5 && player.gangAffiliation == 1) {
            String[] stringValues5 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", "I should go talk to Grip."};
            return stringValues5;
        }
        if (value2 == 5 && player.gangAffiliation == 2) {
            String[] stringValues6 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", "Charlie told me that there is a side entrance to the mansion", "that I can use."};
            return stringValues6;
        }
        if (value2 == 6 && player.gangAffiliation == 1) {
            String[] stringValues7 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", player.ownsItem(1586) ? "I should find out what the key Grip gave me opens." : "I should talk to Grip."};
            return stringValues7;
        }
        if (value2 == 7 && player.gangAffiliation == 1) {
            String[] stringValues8 = new String[]{"Achietties will let me into the Heroes' Guild if I can get:", String.valueOf(player.ownsItem(1583) ? "@str@" : "") + "An Entranan Firebird Feather - I should check on Entrana", String.valueOf(player.ownsItem(2149) ? "@str@" : "") + "A cooked lava eel - I should speak to a fishing expert", String.valueOf(player.ownsItem(1579) ? "@str@" : "") + "A Master Thieves Armband - the " + (player.gangAffiliation == 1 ? "Black Arm" : "Phoenix") + " Gang can help me", "@str@I spoke to " + (player.gangAffiliation == 1 ? "Katrine" : "Straven") + " about the Master Thieves Armband.", "I should now give the other candlestick to my partner and", "then return to Katrine."};
            return stringValues8;
        }
        if (value2 == 1) {
            String[] stringValues9 = new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "Access to the Heroes' Guild", "A total of 29,232 XP spread", "over twelve skills"};
            return stringValues9;
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
        player2.packetSender.sendInterfaceText("Access to the Heroes' Guild", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("A total of 29,232 XP spread", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("over twelve skills", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(0, 3075.0);
        player.getSkillManager().addQuestExperience(1, 3075.0);
        player.getSkillManager().addQuestExperience(2, 3075.0);
        player.getSkillManager().addQuestExperience(3, 3075.0);
        player.getSkillManager().addQuestExperience(4, 2075.0);
        player.getSkillManager().addQuestExperience(10, 2725.0);
        player.getSkillManager().addQuestExperience(7, 2825.0);
        player.getSkillManager().addQuestExperience(8, 1575.0);
        player.getSkillManager().addQuestExperience(11, 1575.0);
        player.getSkillManager().addQuestExperience(13, 2257.0);
        player.getSkillManager().addQuestExperience(14, 2575.0);
        player.getSkillManager().addQuestExperience(15, 1325.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1377);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    private static boolean hasAllHeroesGuildEntryItems(Player player) {
        return player.getInventoryManager().containsItem(1583) && player.getInventoryManager().containsItem(2149) && player.getInventoryManager().containsItem(1579);
    }

    @Override
    public final boolean handleGroundItemInteraction(Player player, int itemId, int value2) {
        if (itemId == 1583) {
            if (value2 == 0) {
                player.packetSender.sendGameMessage("It looks dangerously hot, and you have no reason to take it.");
                return true;
            }
            if (value2 >= 2 && player.getEquipmentManager().getItemIdAtSlot(9) != 1580) {
                player.packetSender.sendGameMessage("It looks dangerously hot, you should find a way to pick it up.");
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 1586 && value2 == 2622) {
            Player player2 = player;
            player2.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3197 ? 1 : -1, true);
            player2 = player;
            player2.packetSender.openSingleDoor(value2, 2781, 3197, 0);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2633 && value52 == 2766 && value62 == 3199 && value8 == 1 && value72 == 6) {
            if (value32 == 1) {
                player.getDialogueManager().showTwoLineStatement("You find two candlesticks in the chest. So that will be one for you,", "and one for the person who killed Grip for you.");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().finishDialogue();
                player.getInventoryManager().addOrDropItem(new ItemStack(1577, 2));
                ObjectManager.getInstance().removeDynamicObjectAt(2766, 3199, 0, 0);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(2632, 2766, 3199, 0, 2, 10, 2632, 999999999), true);
                player.setQuestState(this.getQuestId(), 7);
                return false;
            }
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2621 && value2 == 2764 && value32 == 3197) {
            if (value42 >= 6) {
                if (player.getPosition().getX() < 2764) {
                    if (player.getInventoryManager().containsItem(1588)) {
                        Player player2 = player;
                        player2.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2764 ? 1 : -1, 0, true);
                        player2 = player;
                        player2.packetSender.openSingleDoor(objectId, value2, value32, 0);
                        player2 = player;
                        player2.packetSender.sendGameMessage("Grip's key unlocks the door.");
                        return true;
                    }
                } else {
                    Player player3 = player;
                    player3.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2764 ? 1 : -1, 0, true);
                    player3 = player;
                    player3.packetSender.openSingleDoor(objectId, value2, value32, 0);
                    player3 = player;
                    player3.packetSender.sendGameMessage("The door locks shut behind you.");
                    return true;
                }
            }
            player.getDialogueManager().showOneLineStatement("This door is locked.");
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId == 2632 && value2 == 2766 && value32 == 3199) {
            if (value42 == 6) {
                ObjectManager.getInstance().removeDynamicObjectAt(2766, 3199, 0, 0);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(2633, 2766, 3199, 0, 2, 10, 2632, 999999999), true);
                Player player4 = player;
                player4.packetSender.sendGameMessage("You open the chest.");
            }
            return true;
        }
        if (objectId == 2626 && value2 == 2811 && value32 == 3170) {
            if (value42 == 3 && player.gangAffiliation == 1) {
                DialogueManager.continueDialogue(player, 789, 100, 0);
            }
            if (value42 >= 4 && player.gangAffiliation == 1) {
                Player player5 = player;
                player5.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2811 ? 1 : -1, 0, true);
                player5 = player;
                player5.packetSender.openSingleDoor(objectId, value2, value32, 0);
            }
            return true;
        }
        if (objectId == 2627 && value2 == 2774 && value32 == 3187) {
            if (value42 == 4 && player.gangAffiliation == 1) {
                DialogueManager.continueDialogue(player, 788, 100, 0);
            }
            if (value42 >= 5 && player.gangAffiliation == 1) {
                Player player6 = player;
                player6.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3188 ? 1 : -1, true);
                player6 = player;
                player6.packetSender.openSingleDoor(objectId, value2, value32, 0);
            }
            return true;
        }
        if (objectId == 2628 && value2 == 2788 && value32 == 3189) {
            if (value42 < 4 && player.gangAffiliation == 2) {
                DialogueManager.continueDialogue(player, 793, 100, 0);
            }
            if (value42 >= 4 && player.gangAffiliation == 2) {
                Player player7 = player;
                player7.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3190 ? 1 : -1, true);
                player7 = player;
                player7.packetSender.openSingleDoor(objectId, value2, value32, 0);
            }
            return true;
        }
        if (objectId == 2629 && value2 == 2787 && value32 == 3190) {
            if (value42 >= 5) {
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2787 ? 1 : -1, 0, true);
                player8 = player;
                player8.packetSender.openSingleDoor(objectId, value2, value32, 0);
            }
            return true;
        }
        if (objectId == 2622 && value2 == 2781 && value32 == 3197) {
            if (player.getPosition().getY() < 3197) {
                player.getDialogueManager().showOneLineStatement("This door is locked.");
                player.getDialogueManager().finishDialogue();
            } else {
                Player player9 = player;
                player9.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3197 ? 1 : -1, true);
                player9 = player;
                player9.packetSender.openSingleDoor(objectId, value2, value32, 0);
            }
            return true;
        }
        if ((objectId == 2635 || objectId == 2636) && value2 == 2775 && value32 == 3196) {
            if (value42 == 6) {
                if (objectId == 2635) {
                    ObjectManager.getInstance().removeDynamicObjectAt(2775, 3196, 0, 0);
                    ObjectManager.getInstance().addDynamicObject(new DynamicObject(2636, 2775, 3196, 0, 3, 10, 2635, 999999999), true);
                }
                DialogueManager.continueDialogue(player, 799, 100, 0);
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 792) {
            Npc npc = Npc.findByDefinitionId(792);
            GroundItem groundItem = new GroundItem(new ItemStack(1588, 1), npc.getPosition(), false, true);
            GroundItemManager.getInstance().spawn(groundItem);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        if (value32 >= 2) {
            if (itemId == 97 && value2 == 1581 || itemId == 1581 && value2 == 97) {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(1582, 1));
                player.getInventoryManager().addItem(new ItemStack(3377, 1));
                player.packetSender.sendGameMessage("You mix the slime into your potion.");
                return true;
            }
            if (itemId == 1582 && value2 == 307 || itemId == 307 && value2 == 1582) {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(1585, 1));
                player.getInventoryManager().addItem(new ItemStack(229, 1));
                player.packetSender.sendGameMessage("You rub the oil into the fishing rod.");
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        if (npcId == 792 && player.gangAffiliation == 1) {
            player.getDialogueManager().showPlayerFourLineDialogue("I can't attack the head guard here! There are too", "many witnesses around to see me do it! I'd have the", "whole of Brimhaven after me! Besides, if he dies I want", "the promotion!", 591);
            player.getDialogueManager().finishDialogue();
            return false;
        }
        return true;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        Player player2;
        if (npcId == 796) {
            player2 = player;
            if (!(player2.getSkillManager().getBaseLevel(15) < 25 || player2.getSkillManager().getBaseLevel(14) < 50 || player2.getSkillManager().getBaseLevel(10) < 53 || player2.getSkillManager().getBaseLevel(7) < 53 ? false : player2.getQuestPoints() >= 56 && player2.getQuestState(16) == 1 && player2.getQuestState(58) == 1 && player2.getQuestState(5) == 1 && player2.getQuestState(61) == 1 && player2.getQuestState(29) == 1)) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Greetings. Welcome to the Heroes' Guild.", 591);
                return true;
            }
            if (value42 == 0) {
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Only the greatest heroes of this land may gain", "entrance to this guild.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("I'm a hero, may I apply to join?", "Good for the foremost heroes of the land.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm a hero - may I apply to join?", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well, you have a lot of quest points, and you have done", "all of the required quests, so you may now begin the", "tasks to meet the entry requirements for membership in", "the Heroes' Guild. The three items required", 591);
                    this.startQuest(player);
                    return true;
                }
            }
            if (value42 >= 2) {
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("How goes thy quest adventurer?", 591);
                    return true;
                }
                if (value2 == 3) {
                    if (HeroesQuest.hasAllHeroesGuildEntryItems(player) && value42 == 8) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have all the required items.", 591);
                        player.getDialogueManager().setNextDialogueStep(14);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("It's tough. I've not done it yet.", 591);
                    }
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Remember, the items you need to enter are:", 591);
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("for entrance are: An Entranan Firebird feather, a", "Master Thieves' armband, and a cooked Lava Eel.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showFourOptions("Any hints on getting the armband?", "Any hints on getting the feather?", "Any hints on getting the eel?", "I'll start looking for all those things then.");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Any hints on getting the thieves armband?", 591);
                        player.getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Any hints on getting the feather?", 591);
                        player.getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Any hints on getting the eel?", 591);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    if (value32 == 4) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'll start looking for all those things then.", 591);
                        player.getDialogueManager().setNextDialogueStep(13);
                        return true;
                    }
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm sure you have relevant contacts to find out about", "that.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("An Entranan Firebirds' feather, A Master Thieves", "armband, and a cooked Lava Eel.", 591);
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Not really - other than Entranan firebirds tend to live", "on Entrana.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Maybe go and find someone who knows a lot about", "fishing?", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good luck with that.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I see that you have. Well done; Now, to complete the", "quest, and gain entry to the Heroes' Guild in your final", "task all that you have to do is...", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showPlayerOneLineDialogue("W-what? What do you mean? There's MORE???", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I'm sorry, I was just having a little fun with you. Just", "a little Heroes' Guild humour there. What I really", "meant was", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Congratulations! You have completed the Heroes' Guild", "entry requirements! You will find the door now open", "for you! Enter, Hero! And take this reward!", 591);
                    return true;
                }
                if (value2 == 18 && HeroesQuest.hasAllHeroesGuildEntryItems(player)) {
                    player.getDialogueManager().finishDialogue();
                    player.getInventoryManager().removeItem(new ItemStack(1583, 1));
                    player.getInventoryManager().removeItem(new ItemStack(2149, 1));
                    player.getInventoryManager().removeItem(new ItemStack(1579, 1));
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 558 && value42 >= 2) {
            if (player.ownsItem(1581) || player.ownsItem(1582) || player.ownsItem(1585) || player.ownsItem(2148) || player.ownsItem(2149)) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcTwoLineDialogue("Welcome! You can buy fishing equipment at my store.", "We'll also buy anything you catch off you.", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showThreeOptions("Let's see what you've got then.", "Sorry, I'm not interested.", "I want to find out how to catch a lava eel.");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I want to find out how to catch a lava eel.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcFourLineDialogue("Lava eels eh? That's a tricky one that is, you'll need a", "lava-proof fishing line. The method for making this would", "be to take an ordinary fishing rod, and then cover it", "with the fire-proof Blamish Oil.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcThreeLineDialogue("You know... thinking about it... I may have a jar of", "Blamish Slime around here somewhere... Now where did", "I put it?", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showOneLineStatement("Gerrant searches around a bit.");
                return true;
            }
            if (value2 == 7) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1581, 1));
                player.getDialogueManager().showNpcThreeLineDialogue("Aha! Here it is! Take this slime, mix it with some", "Harralander and water and you'll have the Blamish Oil", "you need for treating your fishing rod.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 644) {
            if (player.gangAffiliation != 2) {
                return false;
            }
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("How would I go about getting a Master Thief", "armband?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ooh... tricky stuff. Took me YEARS to get that rank.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well, what some of the more aspiring thieves in our", "gang are working on right now is to steal some very", "valuable candlesticks from Scarface Pete - the pirate", "leader on Karamja.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("His security is excellent, and the target very valuable so", "that might be enough to get you the rank.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Go talk to our man Alfonse, the waiter in the Shrimp", "and Parrot.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("Use the secret word 'gherkin' to show you're one of us.", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 5) {
                if (!player.getInventoryManager().containsItem(1577)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have retrieved a candlestick!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hmmm. Not bad, not bad. Let's see it, make sure it's", "genuine.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("You hand Straven the candlestick.");
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So is this enough to get me a Master Thief armband?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hmm...", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("I dunno...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Aww, go on then. I suppose I'm in a generous mood", "today.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showOneLineStatement("Straven hands you a Master Thief armband.");
                    return true;
                }
                if (value2 == 9) {
                    player.setQuestState(this.getQuestId(), 8);
                    player.getInventoryManager().removeItem(new ItemStack(1577, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(1579, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 8) {
                if (player.ownsItem(1579)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showOneLineStatement("Straven hands you a Master Thief armband.");
                    return true;
                }
                if (value2 == 2) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1579, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 642) {
            if (player.gangAffiliation != 1) {
                return false;
            }
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hey.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hey.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("Who are all those people in there?", "Is there any way I can get the rank of master thief?");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Is there any way I can get the rank of master thief?", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Master thief? Ain't we the ambitious one!", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, you're gonna have to do something pretty", "amazing.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Anything you can suggest?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, some of the MOST coveted prizes in thiefdom", "right now are in the pirate town of Brimhaven on", "Karamja.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The pirate leader Scarface Pete has a pair of extremely", "valuable candlesticks.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcOneLineDialogue("His security is VERY good.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("We, of course, have gang members in a town like", "Brimhaven who may be able to help you.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("Visit our hideout in the alleyway on palm street.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("To get in you will need to tell them the secret password", "'four leafed clover'.", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 7) {
                if (!player.getInventoryManager().containsItem(1577)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showTwoOptions("Who are all those people in there?", "I have a candlestick now.");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have a candlestick now!", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Wow.... is... it REALLY it?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("This really is a FINE bit of thievery.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Us thieves have been trying to get hold of this one for", "a while!", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You wanted to be ranked as a master thief didn't you?", "Well, I guess this just about ranks as good enough!", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showOneLineStatement("Katrine gives you a master thief armband.");
                    return true;
                }
                if (value2 == 8) {
                    player.setQuestState(this.getQuestId(), 8);
                    player.getInventoryManager().removeItem(new ItemStack(1577, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(1579, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 8) {
                if (player.ownsItem(1579)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showOneLineStatement("Katrine gives you a master thief armband.");
                    return true;
                }
                if (value2 == 2) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1579, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 789) {
            if (player.gangAffiliation != 1) {
                return false;
            }
            if (value42 == 3) {
                if (value2 == 100) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes? What do you want?", 591);
                    return true;
                }
                if (value2 == 101) {
                    player.getDialogueManager().showFourOptions("Rabbit's foot.", "Four leaved clover.", "Lucky horseshoe.", "Black cat.");
                    return true;
                }
                if (value2 == 102) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Four leaved clover.", 591);
                        player.getDialogueManager().setNextDialogueStep(103);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 103) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Oh, you're one of the gang are you? Ok, hold up a", "second, I'll just let you in through here.", 591);
                    return true;
                }
                if (value2 == 104) {
                    player.getDialogueManager().showOneLineStatement("You hear the door being unbarred from inside.");
                    player.setQuestState(this.getQuestId(), 4);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 != 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hi.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hi. I'm a little busy right now.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 790) {
            if (player.gangAffiliation != 1) {
                return false;
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hi. Welcome to our Brimhaven headquarters. I'm", "Trobert and I'm in charge here.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("So can you help me get Scarface Pete's candlesticks?", "Pleased to meet you.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So can you help me get Scarface Pete's candlesticks?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcFourLineDialogue("Well, we have made some progress there. We know that", "one of the only keys to Pete's treasure room is carried", "by Grip, the head guard, so we thought it might be good", "to get close to him somehow.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("Grip was taking on a new deputy called Hartigen, an", "Asgarnian Black Knight who was deserting the Black", "Knight Fortress and seeking new employment here on", "Brimhaven.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("We managed to waylay him on the journey here, and", "steal his I.D. papers. Now all we need is to find", "somebody willing to impersonate him and take the", "deputy role to get that key for us.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showTwoOptions("I volunteer to undertake that mission!", "Well, good luck then.");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I volunteer to undertake that mission!", 591);
                        player.getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Good good. Well, Here's the I.D. papers, take them and", "introduce yourself to the guards at Scarface Pete's", "mansion, we'll have that treasure in no time.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1584, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 788) {
            if (player.gangAffiliation != 1) {
                return false;
            }
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Hello. What do you want?", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showTwoOptions("Can I go in there?", "I want for nothing!");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can I go in there?", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I want for nothing!", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
            }
            if (value2 == 4) {
                if (value42 <= 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("No. In there is private.", 591);
                }
                if (value42 >= 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Yes, of course you can. You DO work here.", 591);
                }
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("You're one of a very lucky few then.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showPlayerOneLineDialogue("Can I go in there?", 591);
                player.getDialogueManager().setNextDialogueStep(4);
                return true;
            }
            if (value2 == 100) {
                player.getDialogueManager().showNpcOneLineDialogue("Oi! Where do you think you're going pal?", 591);
                if (value42 < 4) {
                    player.getDialogueManager().setNextDialogueStep(6);
                }
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().showPlayerOneLineDialogue("Hi. I'm Hartigen. I've come to work here.", 591);
                return true;
            }
            if (value2 == 102) {
                player2 = player;
                if (!(player2.getEquipmentManager().getItemIdAtSlot(0) == 1165 && player2.getEquipmentManager().getItemIdAtSlot(4) == 1125 && player2.getEquipmentManager().getItemIdAtSlot(7) == 1077)) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hartigen the Black Knight? I don't think so. He doesn't", "dress like that.", 591);
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getDialogueManager().showNpcOneLineDialogue("I assume you have your I.D. papers then?", 591);
                }
                return true;
            }
            if (value2 == 103) {
                if (!player.getInventoryManager().containsItem(1584)) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Actually I don't.", 591);
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getDialogueManager().showNpcTwoLineDialogue("You'd better come in then. Grip will want to talk to", "you.", 591);
                    player.setQuestState(this.getQuestId(), 5);
                }
                return true;
            }
        }
        if (npcId == 792) {
            if (player.gangAffiliation != 1) {
                return false;
            }
            if (value42 == 5) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Hi there. I am Hartigen. reporting for duty as your", "new deputy sir!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah good, at last. You took your time getting here! Now", "let me see...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcFourLineDialogue("I'll get your hours and duty roster sorted out in a", "while. Oh, and do you have your I.D. papers with you?", "Internal security is almost as important as external", "security for a guard.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Right here sir!", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("You hand the ID papers over to Grip.");
                    return true;
                }
                if (value2 == 6 && player.getInventoryManager().containsItem(1584)) {
                    player.getDialogueManager().showThreeOptions("So can I guard the treasure room please?", "So what do my duties involve?", "Well, I'd better sort my new room out.");
                    player.getInventoryManager().removeItem(new ItemStack(1584, 1));
                    player.setQuestState(this.getQuestId(), 6);
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
            }
            if (value42 == 6) {
                if (value2 == 1 && !player.ownsItem(1586)) {
                    player.getDialogueManager().showNpcFourLineDialogue("You'll have various guard related duties on various", "shifts. I'll assign specific duties as they are reguired as", "and when they become necessary. Just so you know, if", "anything happens to me", 591);
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 2) {
                        player.getDialogueManager().showNpcFourLineDialogue("You'll have various guard related duties on various", "shifts. I'll assign specific duties as they are reguired as", "and when they become necessary. Just so you know, if", "anything happens to me", 591);
                        player.getDialogueManager().setNextDialogueStep(2);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcFourLineDialogue("you'll need to take over as head guard here. You'll find", "important keys to the treasure room and Pete's", "quarters inside my jacket - although I doubt anything", "bad's going to happen to", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("me anytime soon!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showOneLineStatement("Grip laughs to himself at the thought.");
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showThreeOptions("So can I guard the treasure room please?", "Well, I'd better sort my new room out.", "Anything I can do now?");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Anything I can do now?", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Hmm. Well, you could find out what this key opens for", "me. Apparently it's for something in this building, but", "for the life of me I can't find what.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showOneLineStatement("Grip hands you a key.");
                    return true;
                }
                if (value2 == 9) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1586, 1));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId == 793) {
            if (player.gangAffiliation != 2) {
                return false;
            }
            if (value42 < 4 && value2 == 100) {
                player.getDialogueManager().showNpcOneLineDialogue("Hey, you can't go in there!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Welcome to the Shrimp and Parrot.", "Would you like to order, " + (player.getGender() == 0 ? "sir" : "madam") + "?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showFourOptions("Yes please.", "No thank you.", "Do you sell Gherkins?", "Where do you get your Karambwan from?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Do you sell Gherkins?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hmmmm Gherkins eh? Ask Charlie the cook, round the", "back. He may have some 'gherkins' for you!", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("Alfonse winks at you.");
                    player.setQuestState(this.getQuestId(), 4);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 794) {
            if (player.gangAffiliation != 2) {
                return false;
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hey! What are you doing back here?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showThreeOptions("I'm looking for a gherkin...", "I'm a fellow member of the Phoenix gang.", "Just exploring...");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm looking for a gherkin...", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Aaaaaah... a fellow Phoenix! So, tell me compadre... what", "brings you to sunny Brimhaven?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showTwoOptions("Sun, sand and the fresh sea air!", "I want to steal Scarface Pete's candlesticks.");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I want to steal Scarface Pete's candlesticks.", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcFourLineDialogue("Ah yes, of course. The candlesticks. Well, I have to be", "honest with you compadre, we haven't made much", "progress in that task ourselves so far. We can however", "offer", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcFourLineDialogue("a little assistance. The setting up of this restaurant was", "the start of things; we have a secret door out the back", "of here that leads through the back of Mr Olbors'", "garden.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcFourLineDialogue("Now, at the other side of Mr Olbors' garden, is an old", "side entrance to Scarface Pete's mansion. It seems to", "have been blocked off from the rest of the mansion", "some years ago", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcFourLineDialogue("and we can't seem to find a way through. We're", "positive this is the key to entering the house undetected", "however, and I promise to let you know if we find", "anything there.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Mind if I check it out for myself?", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Not at all! The more minds we have working on the", "problem, the quicker we get that loot!", 591);
                    player.setQuestState(this.getQuestId(), 5);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 799) {
            if (value2 == 100) {
                player.getDialogueManager().showNpcTwoLineDialogue("I don't think Mr Grip will like you opening that. That's", "his private drinks cabinet.", 591);
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().showTwoOptions("He won't notice me having a quick look.", "Ok, I'll leave it.");
                return true;
            }
            if (value2 == 102) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("He won't notice me having a quick look.", 591);
                    player.getDialogueManager().setNextDialogueStep(103);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 103) {
                Npc npc = Npc.findByDefinitionId(792);
                if (npc != null && !npc.isDead()) {
                    npc.getUpdateState().setForcedText("Stay out of my drinks cabinet!");
                    if (npc.getPosition().getY() < 3196) {
                        npc.queueScriptedPath(new Position[]{new Position(2777, 3194, 0), new Position(2777, 3198, 0)});
                    } else {
                        npc.queueScriptedPath(new Position[]{new Position(2777, 3198, 0)});
                    }
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        return false;
    }
}

