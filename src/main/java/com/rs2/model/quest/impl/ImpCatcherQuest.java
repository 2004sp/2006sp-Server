package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;

public final class ImpCatcherQuest
extends QuestScript {
    public ImpCatcherQuest(int value2) {
        super(8);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Wizard Mizgog who is", "in the Wizards' Tower.", "", "There aren't any requirements for this quest."};
        }
        if (value2 == 2) {
            return new String[]{"I should bring these items to Wizard Mizgog who is", "in the Wizards' Tower:", "White bead", "Red bead", "Black bead", "Yellow bead"};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "875 Magic XP", "Amulet of Accuracy"};
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
        player2.packetSender.sendInterfaceText("875 Magic XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("Amulet of Accuracy", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(6, 875.0);
        player.getInventoryManager().addOrDropItem(new ItemStack(1478, 1));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1478);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    private static boolean hasAnyImpBead(Player player) {
        return player.getInventoryManager().containsItem(1476) || player.getInventoryManager().containsItem(1470) || player.getInventoryManager().containsItem(1474) || player.getInventoryManager().containsItem(1472);
    }

    private static boolean hasAllImpBeads(Player player) {
        return player.getInventoryManager().containsItem(1476) && player.getInventoryManager().containsItem(1470) && player.getInventoryManager().containsItem(1474) && player.getInventoryManager().containsItem(1472);
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 706) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Give me a quest!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Give me a quest what?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showThreeOptions("Give me a quest please.", "Give me a quest or else!", "Just stop messing around and give me a quest!");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Give me a quest please.", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well seeing as you asked nicely... I could do with some", "help.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("The wizard Grayzag next door decided he didn't like", "me so he enlisted an army of hundreds of imps.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("These imps stole all sorts of my things. Most of these", "things I don't really care about, just eggs and balls of", "string and things.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("But they stole my four magical beads. There was a red", "one, a yellow one, a black one, and a white one.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("These imps have now spread out all over the kingdom.", "Could you get my beads back for me?", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showTwoOptions("I'll try.", "I've better things to do than chase imps.");
                    return true;
                }
                if (value2 == 11) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'll try.", 591);
                        this.startQuest(player);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("That's great, thank you.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("So how are you doing finding my beads?", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (!ImpCatcherQuest.hasAnyImpBead(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have none of them yet.", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    if (ImpCatcherQuest.hasAnyImpBead(player) && !ImpCatcherQuest.hasAllImpBeads(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I have found some of them.", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    if (ImpCatcherQuest.hasAllImpBeads(player)) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I've got all four beads. It was hard work I can tell you.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Great, but I'll need the other ones as well.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Give them here and I'll check that they really are MY", "beads, before I give you your reward. You'll like it, it's", "an amulet of accuracy.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showOneLineStatement("You give four coloured beads to Wizard Mizgog.");
                    return true;
                }
                if (value2 == 6 && ImpCatcherQuest.hasAllImpBeads(player)) {
                    player.getInventoryManager().removeItem(new ItemStack(1476, 1));
                    player.getInventoryManager().removeItem(new ItemStack(1470, 1));
                    player.getInventoryManager().removeItem(new ItemStack(1474, 1));
                    player.getInventoryManager().removeItem(new ItemStack(1472, 1));
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

