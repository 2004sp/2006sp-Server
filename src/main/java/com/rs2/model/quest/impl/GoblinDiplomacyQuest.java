package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.util.GameUtil;

public final class GoblinDiplomacyQuest
extends QuestScript {
    public GoblinDiplomacyQuest(int value2) {
        super(7);
        super.setQuestPointReward(5);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Generals Wartface", "and Bentnoze in the Goblin Village.", "There are no requirements for this quest."};
        }
        if (value2 == 2) {
            return new String[]{"I should bring orange goblin armour to Generals Wartface", "and Bentnoze in the Goblin Village."};
        }
        if (value2 == 3) {
            return new String[]{"I should bring blue goblin armour to Generals Wartface", "and Bentnoze in the Goblin Village."};
        }
        if (value2 == 4) {
            return new String[]{"I should bring brown goblin armour to Generals Wartface", "and Bentnoze in the Goblin Village."};
        }
        if (value2 == 5) {
            return new String[]{"I should speak with Generals Wartface and Bentnoze", "in the Goblin Village to finish this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "5 Quest Points", "200 Crafting XP", "A gold bar"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("5 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("200 Crafting XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("A gold bar", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(12, 200.0);
        player.getInventoryManager().addOrDropItem(new ItemStack(2357, 1));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 288);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        if (value32 == 1) {
            return false;
        }
        if (value32 != 0) {
            if (itemId == 1769 && value2 == 288 || itemId == 288 && value2 == 1769) {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(286, 1));
                player.packetSender.sendGameMessage("You dye the goblin armour orange.");
                return true;
            }
            if (itemId == 1767 && value2 == 288 || itemId == 288 && value2 == 1767) {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().addItem(new ItemStack(287, 1));
                player.packetSender.sendGameMessage("You dye the goblin armour blue.");
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value5, int value32, int value42) {
        if (value5 == 1) {
            player.pendingGameMode = GameUtil.randomInt(4);
        }
        int value2 = player.pendingGameMode;
        if (npcId == 296 || npcId == 297) {
            if (value42 == 5) {
                if (value5 == 34) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("It a deal then. Brown armour it is.", 591);
                    player.getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value5 == 1) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcTwoLineDialogue("Thank you for sorting out our argument. Take this", "gold bar as reward!", 591);
                    return true;
                }
                if (value5 == 2) {
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().markDialogueInactive();
                    return true;
                }
            }
            if (value2 == 0) {
                if (value5 == 1) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("I tell all goblins in village to wear green armour now!", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcTwoLineDialogue("They not listen to you! I already tell them wear red", "armour!", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcTwoLineDialogue("They listen to me not you! They know me bigger", "general!", 591);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Me bigger general! They listen to me!", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Human! What colour armour they wearing out there?", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Half of them are wearing red and half of them green.", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcTwoLineDialogue("Shut up human! They wearing green armour really!", "Human lying because he scared of you!", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcTwoLineDialogue("Human scared of me not you! Then you think me", "bigger general!", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("What? Me mean...", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Shut up! Me bigger general!", 591);
                    player.getDialogueManager().setNextDialogueStep(50);
                    return true;
                }
            }
            if (value5 == 50) {
                if (value42 == 0) {
                    player.getDialogueManager().showThreeOptions("Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?", "Do you want me to pick an armour colour for you?");
                }
                if (value42 == 2 && !player.getInventoryManager().containsItemAmount(286, 1) || value42 == 3 && !player.getInventoryManager().containsItemAmount(287, 1) || value42 == 4 && !player.getInventoryManager().containsItemAmount(288, 1)) {
                    player.getDialogueManager().showTwoOptions("Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?");
                }
                if (value42 == 2 && player.getInventoryManager().containsItemAmount(286, 1)) {
                    player.getDialogueManager().showThreeOptions("Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?", "I have some orange armour here");
                }
                if (value42 == 3 && player.getInventoryManager().containsItemAmount(287, 1)) {
                    player.getDialogueManager().showThreeOptions("Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?", "I have some blue armour here");
                }
                if (value42 == 4 && player.getInventoryManager().containsItemAmount(288, 1)) {
                    player.getDialogueManager().showThreeOptions("Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?", "I have some brown armour here");
                }
                return true;
            }
            if (value5 == 51) {
                if (value32 == 3 && value42 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have some brown armour here.", 591);
                    player.getDialogueManager().setNextDialogueStep(33);
                    return true;
                }
                if (value32 == 3 && value42 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have some blue armour here.", 591);
                    player.getDialogueManager().setNextDialogueStep(28);
                    return true;
                }
                if (value32 == 3 && value42 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have some orange armour here.", 591);
                    player.getDialogueManager().setNextDialogueStep(23);
                    return true;
                }
                if (value32 == 3 && value42 == 0) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Do you want me to pick an armour colour for you?", 591);
                    player.getDialogueManager().setNextDialogueStep(13);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(50);
                return true;
            }
            if (value5 == 13) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("Yes, as long as you pick green.", 591);
                return true;
            }
            if (value5 == 14) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcOneLineDialogue("No you have to pick red!", 591);
                return true;
            }
            if (value5 == 15) {
                player.getDialogueManager().showThreeOptions("You should wear red", "You should wear green", "What about a different colour?");
                return true;
            }
            if (value5 == 16) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What about a different colour? Not green or red?", 591);
                    player.getDialogueManager().setNextDialogueStep(17);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(15);
                return true;
            }
            if (value5 == 17) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcTwoLineDialogue("That would mean me wrong... but at least Wartface", "not right!", 591);
                return true;
            }
            if (value5 == 18) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcTwoLineDialogue("Me dunno what that look like. Have to see armour", "before we decide.", 591);
                return true;
            }
            if (value5 == 19) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcOneLineDialogue("Human! You bring us armour in new colour!", 591);
                return true;
            }
            if (value5 == 20) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("What colour we try?", 591);
                return true;
            }
            if (value5 == 21) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcOneLineDialogue("Orange armour might be good.", 591);
                return true;
            }
            if (value5 == 22) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("Yep. bring us orange armour.", 591);
                this.startQuest(player);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 23) {
                if (player.getInventoryManager().containsItemAmount(286, 1)) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("No I don't like that much.", 591);
                    player.getInventoryManager().removeItem(new ItemStack(286, 1));
                    player.setQuestState(this.getQuestId(), 3);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 24) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcOneLineDialogue("It clashes with skin colour.", 591);
                return true;
            }
            if (value5 == 25) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("We need darker colour, like blue.", 591);
                return true;
            }
            if (value5 == 26) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcOneLineDialogue("Yeah blue might be good.", 591);
                return true;
            }
            if (value5 == 27) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("Human! Get us blue armour!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 28) {
                if (player.getInventoryManager().containsItemAmount(287, 1)) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("That not right. Not goblin colour at all.", 591);
                    player.getInventoryManager().removeItem(new ItemStack(287, 1));
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 29) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("Goblins wear dark earthy colours like brown.", 591);
                return true;
            }
            if (value5 == 30) {
                player.getDialogueManager().setDialogueNpcId(296);
                player.getDialogueManager().showNpcOneLineDialogue("Yeah brown might be good.", 591);
                return true;
            }
            if (value5 == 31) {
                player.getDialogueManager().setDialogueNpcId(297);
                player.getDialogueManager().showNpcOneLineDialogue("Human! Get us brown armour!", 591);
                return true;
            }
            if (value5 == 32) {
                player.getDialogueManager().showPlayerTwoLineDialogue("I thought that was the armour you were changing", "from. Never mind, anything is worth a try.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 33) {
                if (player.getInventoryManager().containsItemAmount(288, 1)) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcTwoLineDialogue("That colour quite nice. Me can see myself wearing", "that", 591);
                    player.getInventoryManager().removeItem(new ItemStack(288, 1));
                    player.setQuestState(this.getQuestId(), 5);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 1) {
                if (value5 == 1) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Red armour best.", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("No it has to be green!", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Go away human, we busy.", 591);
                    player.getDialogueManager().setNextDialogueStep(50);
                    return true;
                }
            }
            if (value2 == 2) {
                if (value5 == 1) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("All goblins should wear red armour!", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Not red! Red armour make you look fat.", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Everything make YOU look fat!", 591);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Shut up!", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Fatty!", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("SHUT UP!", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Even this human think you look fat! Don't you, human?", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Um...", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showTwoOptions("Yes, Wartface looks fat", "No, he doesn't look fat");
                    return true;
                }
                if (value5 == 10) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("No, he doesn't look fat.", 591);
                        player.getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value5 == 11) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Shut up human! Wartface fat and human stupid!", 591);
                    return true;
                }
                if (value5 == 12) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Shut up Bentnoze!", 591);
                    player.getDialogueManager().setNextDialogueStep(50);
                    return true;
                }
            }
            if (value2 == 3) {
                if (value5 == 1) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("We should wear green armour!", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Green armour? Are you stupid?", 591);
                    return true;
                }
                if (value5 == 3) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcTwoLineDialogue("You stupid! Only stupid goblins think red armour", "better!", 591);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("No they don't! Me think red armour better!", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("That because you stupid!", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Then why you not like green armour?", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Me not stupid!", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().setDialogueNpcId(296);
                    player.getDialogueManager().showNpcOneLineDialogue("Because red armour better!", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().setDialogueNpcId(297);
                    player.getDialogueManager().showNpcOneLineDialogue("Only stupid goblins think that! You stupid!", 591);
                    player.getDialogueManager().setNextDialogueStep(50);
                    return true;
                }
            }
        }
        return false;
    }
}

