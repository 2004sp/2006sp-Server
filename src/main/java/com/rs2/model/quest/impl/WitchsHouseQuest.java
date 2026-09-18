package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.WitchsHouseGardenTrespassTask;
import com.rs2.util.RectangularArea;

public final class WitchsHouseQuest
extends QuestScript {
    RectangularArea gardenTrespassArea = new RectangularArea(2901, 3460, 2933, 3466, 0);
    private int witchesDiaryLastPageIndex = 3;

    public WitchsHouseQuest(int value2) {
        super(103);
        super.setQuestPointReward(4);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to the little boy", "standing by the long garden just north of taverley", "I must be able to defeat a level 53 enemy"};
        }
        if (value2 == 2) {
            return new String[]{"I should find a way to enter the witches house."};
        }
        if (value2 == 3) {
            return new String[]{"I should be able to get to the ball with the clues", "from the diary."};
        }
        if (value2 == 4) {
            return new String[]{"I should now take the ball back to the boy."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "4 Quest Points", "6325 Hitpoints XP"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("4 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("6325 Hitpoints XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(3, 6325.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 2407);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2867 && value52 == 2900 && value62 == 3474 && value8 == 1 && value72 != 0 && !player.ownsItem(2409)) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("You find a key hidden under the flower pot.");
                return true;
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(2409, 1));
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        if (value22 == 2869 && value52 == 2898 && value62 == 9873 && value8 == 1 && !player.ownsItem(2410)) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("You find a magnet in the cupboard.");
                return true;
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(2410, 1));
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        if (value22 == 2864 && value52 == 2909 && value62 == 3470 && value8 == 2 && !player.ownsItem(2411)) {
            if (value32 == 1) {
                player.getDialogueManager().showTwoLineStatement("You search for the secret compartment mentioned in the diary.", "Inside it you find a small key. You take the key.");
                return true;
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(2411, 1));
                player.getDialogueManager().finishDialogue();
                return false;
            }
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2861 && value2 == 2901 && value32 == 3473) {
            if (player.getInventoryManager().containsItem(2409) || player.getPosition().getX() >= 2901) {
                Player player2 = player;
                player2.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player2 = player;
                player2.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2901 ? 1 : -1, 0, true);
            } else {
                Player player3 = player;
                player3.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 2866 && value2 == 2902 && value32 == 9873 || objectId == 2865 && value2 == 2902 && value32 == 9874) {
            if (player.getEquipmentManager().getItemIdAtSlot(9) == 1059) {
                Player player4 = player;
                player4.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player4 = player;
                player4.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2903 ? 1 : -1, 0, true);
            } else {
                Player player5 = player;
                player5.packetSender.sendGameMessage("The gate does not seem to open.");
            }
            return true;
        }
        if (objectId == 2868 && value2 == 2898 && value32 == 9873) {
            ObjectManager.getInstance().removeDynamicObjectAt(2898, 9873, 0, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2869, 2898, 9873, 0, 0, 10, 2868, 999999999), true);
            return true;
        }
        if (objectId == 2862 && value2 == 2901 && value32 == 3465) {
            if (player.pendingGameMode == 2410 || player.getPosition().getY() < 3466) {
                Player player6 = player;
                player6.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player6 = player;
                player6.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3466 ? 1 : -1, true);
                player.pendingGameMode = 0;
            } else {
                Player player7 = player;
                player7.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 2863 && value2 == 2934 && value32 == 3463) {
            if (player.getPosition().getX() >= 2934) {
                Player player8 = player;
                player8.packetSender.openSingleDoor(objectId, value2, value32, 0);
                player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2934 ? 1 : -1, 0, true);
            } else {
                Player player9 = player;
                player9.packetSender.sendGameMessage("The shed door is locked.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 1985 && value2 == 2870) {
            if (value32 < 3) {
                return false;
            }
            player.getInventoryManager().removeItem(new ItemStack(1985, 1));
            Npc npc = new Npc(901);
            GameplayHelper.spawnOwnedNpcAtPosition(player, new Position(2903, 3466, 0), npc, false, false);
            player.getDialogueManager().showOneLineStatement("A mouse runs out of a hole.");
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId == 2411 && value2 == 2863) {
            if (player.getInventoryManager().containsItem(2411)) {
                Player player2 = player;
                player2.packetSender.openSingleDoor(value2, 2934, 3463, 0);
                player2 = player;
                player2.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2934 ? 1 : -1, 0, true);
                if (value32 == 3) {
                    Npc npc = new Npc(897);
                    GameplayHelper.spawnOwnedNpcAtPosition(player, new Position(2935, 3462, 0), npc, false, false);
                }
            } else {
                Player player2 = player;
                player2.packetSender.sendGameMessage("The shed door is locked.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnNpc(Player player, int npcId, int value2, int value32) {
        if (value2 == 2410 && npcId == 901) {
            if (player.ownedNpc.getNpcId() == 901) {
                player.getDialogueManager().showFourLineStatement("You attach the magnet to the mouse's harness. The mouse finishes", "the cheese and runs back into its hole. You hear some odd noises", "from inside the walls. There is a strange whirring noise from above", "the door frame.");
                Npc npc = player.ownedNpc;
                GameplayHelper.unregisterTemporaryNpc(npc);
                player.pendingGameMode = 2410;
                player.getInventoryManager().removeItem(new ItemStack(2410, 1));
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc() && entity.isPlayer()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 897) {
                entity2.setDead(true);
                CombatManager.handleDeath(entity2);
                entity2 = entity;
                ((Player)entity2).packetSender.sendGameMessage("The shapeshifters' body begins to deform!");
                entity2 = entity;
                ((Player)entity2).packetSender.sendGameMessage("The shapeshifter turns into a spider!");
                entity2 = new Npc(898);
                GameplayHelper.spawnOwnedNpcAtPosition((Player)entity, new Position(2935, 3462, 0), (Npc)entity2, false, false);
                return true;
            }
            if (((Npc)entity2).getNpcId() == 898) {
                entity2.setDead(true);
                CombatManager.handleDeath(entity2);
                entity2 = entity;
                ((Player)entity2).packetSender.sendGameMessage("The shapeshifters' body begins to twist!");
                entity2 = entity;
                ((Player)entity2).packetSender.sendGameMessage("The shapeshifter turns into a bear!");
                entity2 = new Npc(899);
                GameplayHelper.spawnOwnedNpcAtPosition((Player)entity, new Position(2935, 3462, 0), (Npc)entity2, false, false);
                return true;
            }
            if (((Npc)entity2).getNpcId() == 899) {
                entity2.setDead(true);
                CombatManager.handleDeath(entity2);
                entity2 = entity;
                ((Player)entity2).packetSender.sendGameMessage("The shapeshifters' body pulses!");
                entity2 = entity;
                ((Player)entity2).packetSender.sendGameMessage("The shapeshifter turns into a wolf!");
                entity2 = new Npc(900);
                GameplayHelper.spawnOwnedNpcAtPosition((Player)entity, new Position(2935, 3462, 0), (Npc)entity2, false, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 900) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You finally kill the shapeshifter once and for all.");
            player.setQuestState(this.getQuestId(), 4);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleGroundItemInteraction(Player player, int itemId, int value2) {
        if (itemId == 2407) {
            if (player.getQuestState(this.getQuestId()) == 4 && !player.ownsItem(2407)) {
                return false;
            }
            player.packetSender.sendGameMessage("You have to defeat the witches experiment first.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleMovementStep(Player player, int value2) {
        if (player.activeRecurringEffectId == 896) {
            return true;
        }
        if (this.gardenTrespassArea.contains(player.getPosition())) {
            WitchsHouseGardenTrespassTask witchsHouseGardenTrespassTask = new WitchsHouseGardenTrespassTask(this, 1, player);
            World.getTaskScheduler().schedule(witchsHouseGardenTrespassTask);
            player.activeRecurringEffectId = 896;
            return true;
        }
        return false;
    }

    private void showWitchesDiaryPage(Player player, int value5) {
        String[] stringValues;
        Player player2;
        Player player3 = player2 = player;
        player2.packetSender.sendInterfaceText("", 14165);
        player3 = player2;
        player3.packetSender.sendInterfaceText("", 14166);
        int value2 = 843;
        while (value2 <= 864) {
            player3 = player2;
            player3.packetSender.sendInterfaceText("", value2);
            ++value2;
        }
        int value3 = value5;
        if (value3 == 0) {
            String[] stringValues2 = new String[]{"Witches' Diary", "", "", "@red@2nd of Pentember", "Experiment is growing", "larger daily. Making", "excellent progress now. I", "am currently feeding it", "on a mixture of fungus,", "tar and clay.", "It seems to like this", "combination a lot!", "", "", "@red@3rd of Pentember", "Experiment still going", "extremely well. Moved it", "to the wooden garden", "shed; it does too much", "damage in the house! It", "is getting very strong", "now, but unfortunately is", "not too intelligent yet. It", "has a really mean stare", "too!"};
            stringValues = stringValues2;
        } else if (value3 == 1) {
            String[] stringValues3 = new String[]{"Witches' Diary", "", "", "@red@4th of Pentember", "Sausages for dinner", "tonight! Lovely!", "", "@red@5th of Pentember", "A guy called Professor", "Oddenstein installed a", "new security system for", "me in the basement. He", "seems to have a lot of", "good security ideas.", "@red@6th of Pentember", "Don't want people getting", "into back garden to see", "the experiment. Professor", "Oddenstein is fitting me a", "new security system,", "after his successful", "installation in the cellar."};
            stringValues = stringValues3;
        } else if (value3 == 2) {
            String[] stringValues4 = new String[]{"Witches' Diary", "", "", "@red@7th of Pentember", "That pesky kid keeps", "kicking his ball into my", "garden. I swear, if he", "does it AGAIN, I'm going", "to lock his ball away in", "the shed.", "", "@red@8th of Pentember", "The security system is", "done. By Zamorak! Wow,", "is it contrived! Now, to", "open my own back door,", "I lure a mouse out of a", "hole in the back porch, I", "fit a magic curved piece", "of metal to the harness", "on its back, the mouse", "goes back in the hole, and", "the door unlocks! The", "prof tells me that this is", "cutting edge technology!"};
            stringValues = stringValues4;
        } else {
            String[] stringValues5;
            stringValues = value3 == 3 ? (stringValues5 = new String[]{"Witches' Diary", "", "", "As an added precaution I", "have hidden the key to", "the shed in a secret", "compartment of the", "fountain in the garden.", "No one will ever look", "there!", "", "@red@9th of Pentember", "Still can't think of a good", "name for 'The", "Experiment'. Leaning", "towards 'Fritz'... Although", "am considering Lucy as", "it reminds me of my", "mother!"}) : null;
        }
        String[] stringValues6 = stringValues;
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues6[0], 903);
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues6[1], 14165);
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues6[2], 14166);
        int value4 = 3;
        while (value4 < stringValues6.length) {
            player3 = player;
            player3.packetSender.sendInterfaceText((String)stringValues6[value4], value4 + 843 - 3);
            ++value4;
        }
        player3 = player;
        player3.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == 0 ? 1 : 0, 840);
        player3 = player;
        player3.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == this.witchesDiaryLastPageIndex ? 1 : 0, 842);
        if (player.getQuestState(this.getQuestId()) == 2 && value5 == 3) {
            player.setQuestState(this.getQuestId(), 3);
        }
    }

    @Override
    public final boolean handleButtonClick(Player player, int buttonId, int value2) {
        if (player.activeBookItemId == 2408) {
            if (buttonId == 841 && player.activeBookPageIndex < this.witchesDiaryLastPageIndex) {
                ++player.activeBookPageIndex;
                this.showWitchesDiaryPage(player, player.activeBookPageIndex);
                return true;
            }
            if (buttonId == 839 && player.activeBookPageIndex > 0) {
                --player.activeBookPageIndex;
                this.showWitchesDiaryPage(player, player.activeBookPageIndex);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214 && value2 == 2408) {
            this.showWitchesDiaryPage(player, 0);
            player.activeBookItemId = value2;
            player.activeBookPageIndex = 0;
            player.packetSender.showInterface(837);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 895) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello young man.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showOneLineStatement("The boy sobs.");
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showTwoOptions("What's the matter?", "Well if you're not going to answer, I'll go.");
                    return true;
                }
                if (value2 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("What's the matter?", 591);
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("I've kicked my ball over that hedge, into that garden!", "The old lady who lives there is scary... She's locked the", "ball in her wooden shed! Can you get my ball back for", "me please?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showTwoOptions("Ok, I'll see what I can do.", "Get it back yourself.");
                    return true;
                }
                if (value2 == 7) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll see what I can do.", 591);
                        player.getDialogueManager().setNextDialogueStep(8);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thanks mister!", 591);
                    this.startQuest(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4) {
                if (!player.getInventoryManager().containsItem(2407)) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Hi, I have got your ball back. It was MUCH harder", "than I thought it would be.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showOneLineStatement("You give the ball back.");
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thank you so much!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getInventoryManager().removeItem(new ItemStack(2407, 1));
                    player.getDialogueManager().finishDialogue();
                    this.awardCompletionRewards(player);
                    return true;
                }
            }
        }
        return false;
    }
}

