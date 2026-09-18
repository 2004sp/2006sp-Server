package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.skill.agility.AgilityObstacleHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class TreeGnomeVillageQuest
extends QuestScript {
    private final List strongholdCoordinateChoices = Arrays.asList(1, 2, 3, 4);

    public TreeGnomeVillageQuest(int value2) {
        super(95);
        super.setQuestPointReward(2);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        int questState = player.getQuestState(this.getQuestId()) - 5;
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Bolren at the center", "of the Tree Gnome maze, West of Port Khazard", "", "I need to be able to defeat a level 112 Warlord"};
        }
        if (value2 == 2) {
            return new String[]{"I should speak with Commander Montai, who can be found", "somewhere in the battlefield."};
        }
        if (value2 == 3) {
            return new String[]{"Commander Montai asked me to bring: 6 x Logs."};
        }
        if (value2 == 4) {
            return new String[]{"I should speak with Commander Montai for further", "instructions."};
        }
        if (value2 >= 5 && value2 < 19) {
            return new String[]{"I need to find the tracker gnomes to get the coordinates:", String.valueOf((questState & GameUtil.bitFlag(1)) == 0 || questState == 0 ? "" : "@str@") + "Tracker gnome 1", String.valueOf((questState & GameUtil.bitFlag(2)) == 0 || questState == 0 ? "" : "@str@") + "Tracker gnome 2", String.valueOf((questState & GameUtil.bitFlag(3)) == 0 || questState == 0 ? "" : "@str@") + "Tracker gnome 3"};
        }
        if (value2 == 19) {
            return new String[]{"I should fire the ballista."};
        }
        if (value2 == 20) {
            if (!player.ownsItem(587)) {
                return new String[]{"I should go search the stronghold for the orb."};
            }
            return new String[]{"I should bring the orb to King Bolren."};
        }
        if (value2 == 21) {
            if (!player.ownsItem(588)) {
                return new String[]{"I need to find the Khazard warlord and bring back the orbs."};
            }
            return new String[]{"I should bring the orbs to King Bolren."};
        }
        if (value2 == 22) {
            return new String[]{"I should speak with King Bolren to finish this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "2 Quest Points", "11,450 Attack XP"};
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
        player2.packetSender.sendInterfaceText("11,450 Attack XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("Gnome Amulet of Protection", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(0, 11450.0);
        player.getInventoryManager().addOrDropItem(new ItemStack(589, 1));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 589);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2184 && value2 == 2502 && value32 == 3250) {
            if (player.getPosition().getY() < 3251) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("It is locked.");
            } else {
                Player player3 = player;
                player3.packetSender.queueRelativeMovementStep(0, -1, true);
                player3 = player;
                player3.packetSender.openSingleDoor(objectId, value2, value32, 0);
            }
            return true;
        }
        if (objectId == 2183 && value2 == 2506 && value32 == 3259) {
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2182, 2506, 3259, 1, 2, 10, 2183, 50), true);
            return true;
        }
        if (objectId == 2182 && value2 == 2506 && value32 == 3259 && value42 == 20 && !player.ownsItem(587)) {
            player.getDialogueManager().showTwoLineStatement("You search the chest. Inside you find the gnomes' stolen orb of", "protection.");
            player.getInventoryManager().addOrDropItem(new ItemStack(587, 1));
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2181 && value52 == 2508 && value62 == 3210 && value8 == 2 && value72 == 19) {
            if (value32 == 1) {
                player.getDialogueManager().showPlayerTwoLineDialogue("That tracker gnome was a bit vague about the x", "coordinate! What could it be?", 591);
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showFourOptionsWithTitle("Enter the x-coordinate of the stronghold", "0001", "0002", "0003", "0004");
                return true;
            }
            if (value32 == 3) {
                ArrayList arrayList = new ArrayList(this.strongholdCoordinateChoices);
                Collections.shuffle(arrayList, new Random(player.questRandomSeed));
                player.getDialogueManager().showTwoLineStatement("You enter the height and y coordinates you got from the tracker", "gnomes.");
                if ((Integer)arrayList.get(0) == value42) {
                    player.getDialogueManager().setNextDialogueStep(4);
                } else {
                    player.getDialogueManager().setNextDialogueStep(5);
                }
                return true;
            }
            if (value32 == 4) {
                player.getDialogueManager().showThreeLineStatement("The huge spear flies through the air and screams down directly into", "the Khazard stronghold. A deafening crash echoes over the battlefield", "as the front entrance is reduced to rubble.");
                player.setQuestState(this.getQuestId(), 20);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value32 == 5) {
                player.getDialogueManager().showOneLineStatement("The huge spear completely misses the Khazard stronghold!");
                return true;
            }
            if (value32 == 6) {
                player.getDialogueManager().showPlayerOneLineDialogue("Evidently that wasn't the right x coordinate.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (value22 == 2185 && value52 == 2509 && value62 == 3253 && value8 == 1) {
            if (player.getPosition().getY() >= 3254) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("I can't get over the wall from this side.");
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value72 == 20) {
                if (value32 == 1) {
                    player.getDialogueManager().showTwoLineStatement("The wall has been reduced to rubble. It should be possible to climb", "over the remains.");
                    return true;
                }
                if (value32 == 2) {
                    AgilityObstacleHandler.startAgilityMovement(player, 0.0, 0, 2, -1, 839, -1, 1, null, null);
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 477 && value2 == 21 && !player.ownsItem(588)) {
            player.getDialogueManager().showFourLineStatement("As the warlord falls to the ground, a ghostly vapour floats upwards", "from his battle-worn armour. Out of sight you hear a shrill scream in", "the still air of the valley. You search his satchel and find the orbs of", "protection.");
            player.getInventoryManager().addOrDropItem(new ItemStack(588, 1));
            return true;
        }
        return false;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        if (npcId == 477) {
            if (value2 != 21) {
                return false;
            }
            if (value2 == 21 && player.ownsItem(588)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 469) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well hello stranger. My name's Bolren, I'm the king of", "the tree gnomes.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm suprised you made it in, maybe I made the maze", "too easy.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Maybe.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm afraid I have more serious concerns at the", "moment. Very serious.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showTwoOptions("I'll leave you to it then.", "Can I help at all?");
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Can I help at all?", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("I'm glad you asked.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The truth is my people are in grave danger. We have", "always been protected by the Spirit Tree. No creature", "of dark can harm us while its three orbs are in place.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("We are not a violent race, but we fight when we must.", "Many gnomes have fallen battling the dark forces of", "Khazard to the North.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcTwoLineDialogue("We became desperate, so we took one orb of protection", "to the battlefield. It was a foolish move.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Khazard troops seized the orb. Now we are completely", "defenceless.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How can I help?", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You would be a huge benefit on the battlefield. If you", "would go there and try to retrieve the orb, my people", "and I will be forever grateful.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showTwoOptions("I would be glad to help.", "I'm sorry but I won't be involved.");
                    return true;
                }
                if (value2 == 16) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I would be glad to help.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(17);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 17) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Thank you. The battlefield is to the north of the maze.", "Commander Montai will inform you of their current", "situation.", 591);
                    return true;
                }
                if (value2 == 18) {
                    player.getDialogueManager().showNpcOneLineDialogue("That is if he's still alive.", 591);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("My assistant shall guide you out. Good luck friend, try", "your best to return the orb.", 591);
                    return true;
                }
                if (value2 == 2) {
                    DialogueManager.continueDialogue(player, 474, 100, 0);
                    player.moveToPreservingInteractionState(new Position(2504, 3191, 0));
                    return true;
                }
            }
            if (value42 == 20 && player.getInventoryManager().containsItem(587)) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have the orb.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Oh my... The misery, the horror!", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("King Bolren, are you OK?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thank you traveller, but it's too late. We're all doomed.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What happened?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("They came in the night. I don't know how many, but", "enough.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Who?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Khazard troops. They slaughtered anyone who got in", "their way. Women, children, my wife.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm sorry.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcOneLineDialogue("They took the other orbs, now we are defenceless.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Where did they take them?", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("They headed north of the stronghold. A warlord carries", "the orbs.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showTwoOptions("I will find the warlord and bring back the orbs.", "I'm sorry but I can't help.");
                    return true;
                }
                if (value2 == 14) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I will find the warlord and bring back the orbs.", 591);
                        player.getDialogueManager().setNextDialogueStep(15);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You are brave, but this task will be tough even for you.", "I wish you the best of luck. Once again you are our", "only hope.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getInventoryManager().removeItem(new ItemStack(587, 1));
                    player.getDialogueManager().showNpcTwoLineDialogue("I will safeguard this orb and pray for your safe return.", "My assistant will guide you out.", 591);
                    player.setQuestState(this.getQuestId(), 21);
                    return true;
                }
            }
            if (value42 == 21) {
                if (value2 == 17) {
                    DialogueManager.continueDialogue(player, 474, 100, 0);
                    player.moveToPreservingInteractionState(new Position(2504, 3191, 0));
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Bolren, I have returned.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("You made it back! Do you have the orbs?", 591);
                    return true;
                }
                if (value2 == 3) {
                    if (player.getInventoryManager().containsItem(588)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have them here.", 591);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("Unfortunately I don't have them with me.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hooray, you're amazing. I didn't think it was possible", "but you've saved us.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Once the orbs are replaced we will be safe once more.", "We must begin the ceremony immediately.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What does the ceremony involve?", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The spirit tree has looked over us for centuries. Now", "we must pay our respects.", 591);
                    return true;
                }
                if (value2 == 8 && player.getInventoryManager().containsItem(588)) {
                    player.getInventoryManager().removeItem(new ItemStack(588, 1));
                    player.getDialogueManager().showTwoLineStatement("The gnomes begin to chant. Meanwhile, King Bolren holds the orbs", "of protection out in front of him.");
                    player.setQuestState(this.getQuestId(), 22);
                    return true;
                }
            }
            if (value42 == 22) {
                if (value2 == 9) {
                    player.getDialogueManager().showTwoLineStatement("The orbs of protection come to rest gently in the branches of the", "ancient spirit tree.");
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Now at last my people are safe once more. We can live", "in peace again.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm pleased I could help.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("You are modest brave traveller.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Please, for your efforts take this amulet. It's made", "from the same sacred stone as the orbs of protection. It", "will help keep you safe on your journeys.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thank you King Bolren.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("The tree has many other powers, some of which I", "cannot reveal. As a friend of the gnome people, I can", "now allow you to use the tree's magic to teleport to", "other trees grown from related seeds.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        if (npcId == 474) {
            if (value42 == 1) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello Elkoy.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Would you like me to show you the way to the Village?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("Yes please.", "No thanks Elkoy.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes please.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ok then, follow me.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.moveTo(new Position(2515, 3159, 0));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2 && value2 == 100) {
                player.getDialogueManager().showNpcTwoLineDialogue("We're out the maze now. Please hurry, we must have", "the orb if we are to survive.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 20) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello Elkoy.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("You're back! And the orb?", 591);
                    return true;
                }
                if (value2 == 3) {
                    if (player.getInventoryManager().containsItem(587)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have it here.", 591);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("I don't have it with me.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You're our saviour. Please return it to the village and", "we are all saved. Would you like me to show you the", "way to the village?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showTwoOptions("Yes please.", "No thanks Elkoy.");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes please.", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ok then, follow me.", 591);
                    return true;
                }
                if (value2 == 8) {
                    DialogueManager.continueDialogue(player, 473, 100, 0);
                    player.moveToPreservingInteractionState(new Position(2515, 3159, 0));
                    return true;
                }
            }
            if (value42 == 21) {
                if (value2 == 100) {
                    player.getDialogueManager().showNpcOneLineDialogue("Good luck friend.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (player.getInventoryManager().containsItem(588)) {
                    if (value2 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Hello Elkoy.", 591);
                        return true;
                    }
                    if (value2 == 2) {
                        player.getDialogueManager().showNpcOneLineDialogue("You truly are a hero.", 591);
                        return true;
                    }
                    if (value2 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Thanks.", 591);
                        return true;
                    }
                    if (value2 == 4) {
                        player.getDialogueManager().showNpcTwoLineDialogue("You saved us by returning the orbs of protection. I'm", "humbled and wish you well.", 591);
                        return true;
                    }
                    if (value2 == 5) {
                        player.getDialogueManager().showNpcOneLineDialogue("Would you like me to show you the way to the Village?", 591);
                        return true;
                    }
                    if (value2 == 6) {
                        player.getDialogueManager().showTwoOptions("Yes please.", "No thanks Elkoy.");
                        return true;
                    }
                    if (value2 == 7) {
                        if (value32 == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Yes please.", 591);
                            player.getDialogueManager().setNextDialogueStep(8);
                            return true;
                        }
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value2 == 8) {
                        player.getDialogueManager().showNpcOneLineDialogue("Ok then, follow me.", 591);
                        return true;
                    }
                    if (value2 == 9) {
                        DialogueManager.continueDialogue(player, 473, 100, 0);
                        player.moveToPreservingInteractionState(new Position(2515, 3159, 0));
                        return true;
                    }
                }
            }
        }
        if (npcId == 473) {
            if (value42 == 1) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello Elkoy.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Would you like me to show you the way out of the Village?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("Yes please.", "No thanks Elkoy.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yes please.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ok then, follow me.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.moveTo(new Position(2504, 3191, 0));
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 20 && value2 == 100) {
                player.getDialogueManager().showNpcTwoLineDialogue("Here we are. Take the orb to King Bolren, I'm sure", "he'll be pleased to see you.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 21 && value2 == 100) {
                player.getDialogueManager().showNpcOneLineDialogue("Here we are. Feel free to have a look around.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 470) {
            if (value42 == 2) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello traveller, are you here to help or just to watch?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I've been sent by King Bolren to retrieve the orb of", "protection.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Excellent we need all the help we can get.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I'm commander Montai. The orb is in the Khazard", "stronghold to the north, but until we weaken their", "defences we can't get close.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What can I do?", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcFourLineDialogue("Firstly we need to strengthen our own defences. We", "desperately need wood to make more battlements, once", "the battlements are gone it's all over. Six loads of", "normal logs should do it.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showTwoOptions("Ok, I'll gather some wood.", "Sorry, I no longer want to be involved.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll gather some wood.", 591);
                        player.setQuestState(this.getQuestId(), 3);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 3) {
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Please be as quick as you can, I don't know how much", "longer we can hold out.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello again, we're still desperate for wood soldier.", 591);
                    return true;
                }
                if (value2 == 3) {
                    if (player.getInventoryManager().containsItemAmount(1511, 6)) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("I have some here.", "@whi@(You give six loads of logs to the commander.)", 591);
                        player.getInventoryManager().removeItem(new ItemStack(1511, 6));
                        player.setQuestState(this.getQuestId(), 4);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("Sorry, I don't have them yet.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 4) {
                    player.getDialogueManager().showNpcFourLineDialogue("That's excellent, now we can make more defensive", "battlements. Give me a moment to organise the troops", "and then come speak to me. I'll inform you of our next", "phase of attack.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How are you doing Montai?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("We're hanging in there soldier. For the next phase of", "our attack we need to breach their stronghold.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The ballista can break through the stronghold wall, and", "then we can advance and seize back the orb.", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So what's the problem?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("From this distance we can't get an accurate enough", "shot. We need the correct coordinates of the", "stronghold for a direct hit. I've sent out three tracker", "gnomes to gather them.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Have they returned?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I'm afraid not, and we're running out of time. I need", "you to go into the heart of the battlefield, find the", "trackers, and bring back the coordinates.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcOneLineDialogue("Do you think you can do it?", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showTwoOptions("No, I've had enough of your battle.", "I'll try my best.");
                    return true;
                }
                if (value2 == 11) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'll try my best.", 591);
                        player.setQuestState(this.getQuestId(), 5);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 5) {
                if (value2 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thank you, you're braver than most.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I don't know how long I will be able to hold out. Once", "you have the coordinates come back and fire the ballista", "right into those monsters.", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("If you can retrieve the orb and bring safety back to", "my people, none of the blood spilled on this field will be", "in vain.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 481 && value42 >= 5 && value42 < 20) {
            value32 = player.getQuestState(this.getQuestId()) - 5;
            if ((value32 & GameUtil.bitFlag(1)) == 0 || value32 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Do you know the coordinates of the Khazard", "stronghold?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("I managed to get one, although it wasn't easy.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("The gnome tells you the @blu@height@bla@ coordinate.");
                    player.addQuestState(this.getQuestId(), GameUtil.bitFlag(1));
                    return true;
                }
            }
            if (value2 == 4) {
                player.getDialogueManager().showPlayerOneLineDialogue("Well done.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("The other two tracker gnomes should have the other", "coordinates if they're still alive.", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showPlayerOneLineDialogue("OK, take care.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 482 && value42 >= 5 && value42 < 20) {
            value32 = player.getQuestState(this.getQuestId()) - 5;
            if ((value32 & GameUtil.bitFlag(2)) == 0 || value32 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Are you OK?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("They caught me spying on the stronghold. They beat", "and tortured me.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("But I didn't crack. I told them nothing. They can't", "break me!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm sorry little man.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Don't be. I have the position of the stronghold!", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showOneLineStatement("The gnome tells you the @blu@y coordinate@bla@.");
                    player.addQuestState(this.getQuestId(), GameUtil.bitFlag(2));
                    return true;
                }
            }
            if (value2 == 7) {
                player.getDialogueManager().showPlayerOneLineDialogue("Well done.", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcOneLineDialogue("Now leave before they find you and all is lost.", 591);
                return true;
            }
            if (value2 == 9) {
                player.getDialogueManager().showPlayerOneLineDialogue("Hang in there.", 591);
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showNpcOneLineDialogue("Go!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 483 && value42 >= 5 && value42 < 20) {
            value32 = player.getQuestState(this.getQuestId()) - 5;
            if (value2 == 1) {
                player.getDialogueManager().showPlayerOneLineDialogue("Are you OK?", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showNpcOneLineDialogue("OK? Who's OK? Not me! Hee hee!", 591);
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showPlayerOneLineDialogue("What's wrong?", 591);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("You can't see me, no one can. Monsters, demons,", "they're all around me!", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showPlayerOneLineDialogue("What do you mean?", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcOneLineDialogue("They're dancing, all of them, hee hee.", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showOneLineStatement("He's clearly lost the plot.");
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showPlayerTwoLineDialogue("Do you have the coordinate for the Khazard", "stronghold?", 591);
                return true;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcOneLineDialogue("Who holds the stronghold?", 591);
                return true;
            }
            if (value2 == 10) {
                player.getDialogueManager().showPlayerOneLineDialogue("What?", 591);
                return true;
            }
            if (value2 == 11) {
                ArrayList arrayList = new ArrayList(this.strongholdCoordinateChoices);
                Collections.shuffle(arrayList, new Random(player.questRandomSeed));
                String text = "If you see this, theres a bug!";
                if ((Integer)arrayList.get(0) == 1) {
                    text = "Less than my hands.";
                } else if ((Integer)arrayList.get(0) == 2) {
                    text = "More than my head, less than my fingers.";
                } else if ((Integer)arrayList.get(0) == 3) {
                    text = "More than we, less than our feet.";
                } else if ((Integer)arrayList.get(0) == 4) {
                    text = "My legs and your legs, ha ha ha!";
                }
                player.getDialogueManager().showNpcOneLineDialogue(text, 591);
                if ((value32 & GameUtil.bitFlag(3)) == 0 || value32 == 0) {
                    player.addQuestState(this.getQuestId(), GameUtil.bitFlag(3));
                }
                return true;
            }
            if (value2 == 12) {
                player.getDialogueManager().showPlayerOneLineDialogue("You're mad.", 591);
                return true;
            }
            if (value2 == 13) {
                player.getDialogueManager().showNpcOneLineDialogue("Dance with me, and Khazard's men are beat.", 591);
                return true;
            }
            if (value2 == 14) {
                player.getDialogueManager().showOneLineStatement("The toll of war has affected his mind.");
                return true;
            }
            if (value2 == 15) {
                player.getDialogueManager().showPlayerOneLineDialogue("I'll pray for you little man.", 591);
                return true;
            }
            if (value2 == 16) {
                player.getDialogueManager().showNpcOneLineDialogue("All day we pray in the hay, hee hee.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 477 && value42 == 21) {
            if (value2 == 1) {
                player.getDialogueManager().showPlayerOneLineDialogue("You there, stop!", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showNpcOneLineDialogue("Go back to your pesky little green friends.", 591);
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showPlayerOneLineDialogue("I've come for the orbs.", 591);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("You're out of your depth traveller. These orbs are part", "of a much larger picture.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showPlayerOneLineDialogue("They're stolen goods, now give them here!", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcTwoLineDialogue("Ha, you really think you stand a chance? I'll crush", "you.", 591);
                return true;
            }
            if (value2 == 7) {
                Npc npc = Npc.findByDefinitionId(477);
                CombatManager.startCombat(npc, player);
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        return false;
    }
}
