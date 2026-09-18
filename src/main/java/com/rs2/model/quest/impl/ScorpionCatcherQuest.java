package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.ScorpionCatcherCageHandoffDialogueTask;
import com.rs2.model.quest.impl.SeerMirrorGazeTask;

public final class ScorpionCatcherQuest
extends QuestScript {
    public ScorpionCatcherQuest(int value2) {
        super(80);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Thormac who is in the", "Sorcerer's Tower", "", "Requirements:", "You'll need level 31 Prayer"};
        }
        if (value2 == 2) {
            return new String[]{"I should go to Seers village and find someone to help me", "locate the Kharid scorpions."};
        }
        if (value2 == 3) {
            return new String[]{"I should now go and look for the scorpions and return", "to Thormac when done."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "6625 Strength  XP"};
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
        player2.packetSender.sendInterfaceText("6625 Strength  XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(2, 6625.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 463);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstNpcAction(Player player, int npcId, int value2) {
        if (value2 != 1) {
            if (npcId == 385 && ScorpionCatcherQuest.hasCaughtFirstScorpion(player)) {
                return false;
            }
            if (npcId == 386 && ScorpionCatcherQuest.hasCaughtSecondScorpion(player)) {
                return false;
            }
            if (npcId == 387 && ScorpionCatcherQuest.hasCaughtThirdScorpion(player)) {
                return false;
            }
            if (npcId == 385 || npcId == 386 || npcId == 387) {
                player.applyDirectHit(3, HitType.NORMAL);
                player.packetSender.sendGameMessage("The scorpion stings you!");
                return true;
            }
        }
        return false;
    }

    private static boolean hasCaughtFirstScorpion(Player player) {
        return player.ownsItem(463) || player.ownsItem(457) || player.ownsItem(458) || player.ownsItem(459);
    }

    private static boolean hasCaughtSecondScorpion(Player player) {
        return player.ownsItem(463) || player.ownsItem(460) || player.ownsItem(458) || player.ownsItem(461);
    }

    private static boolean hasCaughtThirdScorpion(Player player) {
        return player.ownsItem(463) || player.ownsItem(462) || player.ownsItem(459) || player.ownsItem(461);
    }

    @Override
    public final boolean handleItemOnNpc(Player player, int npcId, int value2, int value32) {
        if (value32 >= 2) {
            if (npcId == 385 && value2 >= 456 && value2 < 463 && !ScorpionCatcherQuest.hasCaughtFirstScorpion(player)) {
                value32 = 457;
                if (ScorpionCatcherQuest.hasCaughtSecondScorpion(player) && !ScorpionCatcherQuest.hasCaughtThirdScorpion(player)) {
                    value32 = 458;
                }
                if (ScorpionCatcherQuest.hasCaughtThirdScorpion(player) && !ScorpionCatcherQuest.hasCaughtSecondScorpion(player)) {
                    value32 = 459;
                }
                if (ScorpionCatcherQuest.hasCaughtThirdScorpion(player) && ScorpionCatcherQuest.hasCaughtSecondScorpion(player)) {
                    value32 = 463;
                }
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(value32, 1));
                Player player2 = player;
                player2.packetSender.sendGameMessage("You catch a scorpion!");
                Npc npc = Npc.findByDefinitionId(npcId);
                CombatManager.finishDeath(npc, player, false);
                return true;
            }
            if (npcId == 386 && value2 >= 456 && value2 < 463 && !ScorpionCatcherQuest.hasCaughtSecondScorpion(player)) {
                value32 = 460;
                if (ScorpionCatcherQuest.hasCaughtFirstScorpion(player) && !ScorpionCatcherQuest.hasCaughtThirdScorpion(player)) {
                    value32 = 458;
                }
                if (ScorpionCatcherQuest.hasCaughtThirdScorpion(player) && !ScorpionCatcherQuest.hasCaughtFirstScorpion(player)) {
                    value32 = 461;
                }
                if (ScorpionCatcherQuest.hasCaughtThirdScorpion(player) && ScorpionCatcherQuest.hasCaughtFirstScorpion(player)) {
                    value32 = 463;
                }
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(value32, 1));
                Player player3 = player;
                player3.packetSender.sendGameMessage("You catch a scorpion!");
                Npc npc = Npc.findByDefinitionId(npcId);
                CombatManager.finishDeath(npc, player, false);
                return true;
            }
            if (npcId == 387 && value2 >= 456 && value2 < 463 && !ScorpionCatcherQuest.hasCaughtThirdScorpion(player)) {
                value32 = 462;
                if (ScorpionCatcherQuest.hasCaughtSecondScorpion(player) && !ScorpionCatcherQuest.hasCaughtFirstScorpion(player)) {
                    value32 = 461;
                }
                if (ScorpionCatcherQuest.hasCaughtFirstScorpion(player) && !ScorpionCatcherQuest.hasCaughtSecondScorpion(player)) {
                    value32 = 459;
                }
                if (ScorpionCatcherQuest.hasCaughtFirstScorpion(player) && ScorpionCatcherQuest.hasCaughtSecondScorpion(player)) {
                    value32 = 463;
                }
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(value32, 1));
                Player player4 = player;
                player4.packetSender.sendGameMessage("You catch a scorpion!");
                Npc npc = Npc.findByDefinitionId(npcId);
                CombatManager.finishDeath(npc, player, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2117 && value2 == 2875 && value32 == 9799 && value42 >= 2) {
            if (player.getPosition().getY() >= 9799) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("You've found a secret door");
            }
            Player player3 = player;
            player3.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9799 ? 1 : -1, true);
            player3 = player;
            player3.packetSender.openSingleDoor(2117, 2875, 9799, 0);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value5, int value32, int value42) {
        if (npcId == 389) {
            if (value42 == 0) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hello I am Thormac the sorceror. I don't suppose you", "could be of assistance to me?", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showTwoOptions("What do you need assistance with?", "I'm a little busy.");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("What do you need assistance with?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I've lost my pet scorpions. They're lesser Kharid", "scorpions, a very rare breed.", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I left their cage door open, now I don't know where", "they've gone.", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("There's three of them, and they're quick little beasties.", "They're all over RuneScape.", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showThreeOptions("So how would I go about catching them then?", "What's in it for me?", "I'm not interested then.");
                    return true;
                }
                if (value5 == 8) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So how would I go about catching them then?", 591);
                        player.getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well I have a scorpion cage here which you can use to", "catch them in.", 591);
                    boolean enabled = false;
                    npcId = 456;
                    while (npcId < 464) {
                        if (player.ownsItem(npcId)) {
                            enabled = true;
                        }
                        ++npcId;
                    }
                    if (!enabled) {
                        player.getInventoryManager().addOrDropItem(new ItemStack(456, 1));
                        Player player2 = player;
                        player2.packetSender.sendGameMessage("Thormac gives you a cage.");
                        player.getDialogueManager().setNextDialogueStep(52);
                    }
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("If you go up to the village of Seers, to the North of", "here, one of them will be able to tell you where the", "scorpions are now.", 591);
                    return true;
                }
                if (value5 == 11) {
                    player.getDialogueManager().showTwoOptions("What's in it for me?", "Ok, I will do it then");
                    return true;
                }
                if (value5 == 12) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I will do it then.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 52) {
                    player.setActionLocked(true);
                    Player player3 = player;
                    player3.packetSender.closeInterfaces();
                    ScorpionCatcherCageHandoffDialogueTask scorpionCatcherCageHandoffDialogueTask = new ScorpionCatcherCageHandoffDialogueTask(this, 4, player);
                    World.getTaskScheduler().schedule(scorpionCatcherCageHandoffDialogueTask);
                    return true;
                }
            }
            if (value42 >= 2 && !player.getInventoryManager().containsItem(463)) {
                int value2 = 456;
                while (value2 < 464) {
                    if (player.ownsItem(value2)) {
                        return false;
                    }
                    ++value2;
                }
                if (value5 == 1) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(456, 1));
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("Thormac gives you a cage.");
                    player.getDialogueManager().showNpcThreeLineDialogue("If you go up to the village of Seers, to the North of", "here, one of them will be able to tell you where the", "scorpions are now.", 591);
                    return true;
                }
            }
            if (value42 == 3) {
                if (!player.getInventoryManager().containsItem(463)) {
                    return false;
                }
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("How goes your quest?", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have retrieved all your scorpions.", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Aha, my little scorpions home at last!", 591);
                    return true;
                }
                if (value5 == 4 && player.getInventoryManager().containsItem(463)) {
                    player.getInventoryManager().removeItem(new ItemStack(463, 1));
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().markDialogueInactive();
                    return true;
                }
            }
        }
        if (npcId == 388 && value42 == 2) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Many greetings.", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showThreeOptions("I need to locate some scorpions.", "Your friend Thormac sent me to speak to you.", "I seek knowledge and power!");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Your friend Thormac sent me to speak to you.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("What does the old fellow want?", 591);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showPlayerOneLineDialogue("He's lost his valuable lesser Kharid scorpions.", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showNpcTwoLineDialogue("Well you have come to the right place, I am a master", "of animal detection.", 591);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcOneLineDialogue("Let me look into my looking glass.", 591);
                Player player5 = player;
                player5.packetSender.sendGameMessage("The seer produces a small mirror");
                player.getDialogueManager().setNextDialogueStep(52);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showNpcThreeLineDialogue("I can see a scorpion that you seek. It would appear to", "be near some nasty spiders. I can see two coffins there", "as well.", 591);
                return true;
            }
            if (value5 == 9) {
                player.getDialogueManager().showNpcTwoLineDialogue("The scorpion seems to be going through some crack in", "the wall. It's gone into some sort of secret room.", 591);
                return true;
            }
            if (value5 == 10) {
                player.getDialogueManager().showNpcTwoLineDialogue("Well see if you can find that scorpion then, and I'll try", "and get you some information on the others.", 591);
                player.setQuestState(this.getQuestId(), 3);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 52) {
                Player player6 = player;
                player6.packetSender.closeInterfaces();
                player.setActionLocked(true);
                SeerMirrorGazeTask seerMirrorGazeTask = new SeerMirrorGazeTask(this, 4, player);
                World.getTaskScheduler().schedule(seerMirrorGazeTask);
                return true;
            }
        }
        return false;
    }
}

