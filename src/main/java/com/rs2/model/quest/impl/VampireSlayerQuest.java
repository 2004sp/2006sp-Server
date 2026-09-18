package com.rs2.model.quest.impl;

import com.rs2.cache.CacheStore;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.DraynorManorCandlesBurnTask;
import com.rs2.model.quest.impl.VampireCoffinRiseTask;
import com.rs2.model.task.TickTask;
import com.rs2.util.RectangularArea;

public final class VampireSlayerQuest
extends QuestScript {
    final String[] candleBurnReactions = new String[]{"Ow!", "Eeek!", "Oooch!", "Gah!"};
    final RectangularArea candleHazardArea = new RectangularArea(3075, 9768, 3080, 9778, 0);

    public VampireSlayerQuest(int value2) {
        super(17);
        super.setQuestPointReward(3);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Morgan who is in", "Draynor Village.", "", "Requirements:", "Must be able to kill a level 34 Vampire"};
        }
        if (value2 == 2) {
            return new String[]{"Morgan told me to speak with Dr Harlow, who can", "normally be found in Jolly Boar Inn in Varrock."};
        }
        if (value2 == 3) {
            return new String[]{"I should go and get some beer for Dr Harlow."};
        }
        if (value2 == 4) {
            return new String[]{"I should now ask Dr Harlow some tips to kill", "vampires."};
        }
        if (value2 == 5) {
            return new String[]{"Dr Harlow told me that I need the following items", "to kill a vampire:", "Garlic", "Stake", "Hammer"};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "3 Quest Points", "4825 Attack XP"};
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
        player2.packetSender.sendInterfaceText("4825 Attack XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(0, 4825.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1549);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        if (entity2.isNpc() && entity.isPlayer()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 757) {
                if (!((Player)entity).getInventoryManager().containsItem(2347) || !((Player)entity).getInventoryManager().containsItem(1549)) {
                    ((Npc)entity2).setCurrentHitpoints(((Npc)entity2).getMaxHitpoints());
                    ((Player)entity).packetSender.sendGameMessage("The vampire regenerates.");
                } else {
                    entity2.setDead(true);
                    CombatManager.handleDeath(entity2);
                    ((Player)entity).packetSender.sendGameMessage("You hammer the stake into the vampire's chest!");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public final int getQuestDamageOverride(Entity entity, Entity entity2, int value2) {
        if (CacheStore.cacheVerificationFailed) {
            return 0;
        }
        if (entity2.isNpc()) {
            entity = (Player)entity;
            if (((Npc)(entity2 = (Npc)entity2)).getNpcId() == 757 && !((Player)entity).getInventoryManager().containsItem(1550)) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public final boolean handleMovementStep(Player player, int value2) {
        if (player.activeRecurringEffectId == 757) {
            return true;
        }
        if (this.candleHazardArea.containsExclusive(player.getPosition())) {
            DraynorManorCandlesBurnTask draynorManorCandlesBurnTask = new DraynorManorCandlesBurnTask(this, 5, player);
            World.getTaskScheduler().schedule(draynorManorCandlesBurnTask);
            player.activeRecurringEffectId = 757;
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player objectId, int objectId2, int value2, int value32, int value42) {
        if (CacheStore.cacheVerificationFailed) {
            return true;
        }
        if (objectId2 == 2612 && value2 == 3096 && value32 == 3269) {
            ObjectManager.getInstance().removeDynamicObjectAt(3096, 3269, 1, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2613, 3096, 3269, 1, 0, 10, 2612, 999999999), true);
            return true;
        }
        if (objectId2 == 2613 && value2 == 3096 && value32 == 3269) {
            String text = "The cupboard contains garlic. You take a clove.";
            if (((Player)objectId).pendingGameMode == 1550) {
                text = "You take a clove of garlic.";
            }
            Player player = objectId;
            player.packetSender.sendGameMessage(text);
            ((Player)objectId).getInventoryManager().addOrDropItem(new ItemStack(1550, 1));
            ((Player)objectId).pendingGameMode = 1550;
            return true;
        }
        if (objectId2 == 2612 && value2 == 3096 && value32 == 3268) {
            ObjectManager.getInstance().removeDynamicObjectAt(3096, 3268, 1, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2613, 3096, 3268, 1, 0, 10, 2612, 999999999), true);
            return true;
        }
        if (objectId2 == 2613 && value2 == 3096 && value32 == 3268) {
            String text2 = "The cupboard contains garlic. You take a clove.";
            if (((Player)objectId).pendingGameMode == 1550) {
                text2 = "You take a clove of garlic.";
            }
            Player player = objectId;
            player.packetSender.sendGameMessage(text2);
            ((Player)objectId).getInventoryManager().addOrDropItem(new ItemStack(1550, 1));
            ((Player)objectId).pendingGameMode = 1550;
            return true;
        }
        if (objectId2 == 2614 && value2 == 3077 && value32 == 9775) {
            if (value42 == 1) {
                return true;
            }
            if (((Player)objectId).ownedNpc != null && !((Player)objectId).ownedNpc.isDead() && ((Player)objectId).ownedNpc.getNpcId() == 757) {
                System.out.println("[Vampire Slayer]: vampire not spawned! (reason: spawnedNpcId = vampire id)");
                return true;
            }
            if (GameplayHelper.isObjectDefinitionIdValid(11208)) {
                ObjectManager.getInstance().removeDynamicObjectAt(3077, 9775, 0, 0);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(11208, 3077, 9775, 0, 3, 10, 2614, 10), true);
            }
            Npc npc = new Npc(757);
            ((Player)objectId).setActionLocked(true);
            World.getTaskScheduler().schedule(new VampireCoffinRiseTask(this, 3, npc, (Player)objectId));
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (CacheStore.cacheVerificationFailed) {
            return true;
        }
        if (objectId == 2613 && value2 == 3096 && value32 == 3269) {
            ObjectManager.getInstance().removeDynamicObjectAt(3096, 3269, 1, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2612, 3096, 3269, 1, 0, 10, 2612, 999999999), true);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 757 && value2 == 5 && player.getInventoryManager().containsItem(1549)) {
            player.getInventoryManager().removeItem(new ItemStack(1549, 1));
            this.awardCompletionRewards(player);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 755 && value42 == 0) {
            if (value2 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Please please help us, bold adventurer!", 598);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showPlayerOneLineDialogue("What's the problem?", 591);
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showNpcFourLineDialogue("Our little village has been dreadfully ravaged by an evil", "vampire! He lives in the basement of the manor to the", "north, we need someone to get rid of him once and for", "all!", 598);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showThreeOptions("No, vampires are scary!", "Ok, I'm up for an adventure.", "Have you got any tips on killing the vampire?");
                return true;
            }
            if (value2 == 5) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'm up for an adventure.", 591);
                    player.getDialogueManager().setNextDialogueStep(6);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(4);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcFourLineDialogue("I think first you should seek help. I have a friend who", "is a retired vampire hunter, his name is Dr. Harlow. He", "may be able to give you some tips. He can normally be", "found in the Jolly Boar Inn in Varrock, he's a bit of", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showNpcTwoLineDialogue("an old soak these days. Mention his old friend Morgan,", "I'm sure he wouldn't want me killed by a vampire.", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showPlayerOneLineDialogue("I'll look him up then.", 591);
                this.startQuest(player);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 756) {
            if (value42 >= 2 && value42 < 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Buy me a drrink pleassh...", 600);
                    if (value42 == 3 && player.getInventoryManager().containsItem(1917)) {
                        player.getDialogueManager().setNextDialogueStep(9);
                    }
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("No, you've had enough.", "Morgan needs your help!");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Morgan needs your help!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Morgan you shhay..?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("His village is being terrorised by a vampire! He told me", "to ask you about how I can stop it.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Buy me a beer... then I'll teash you what you need to", "know...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("But this is your friend Morgan we're talking about!", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("Buy ush a drink anyway...", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Here you go.", 591);
                    return true;
                }
                if (value2 == 10 && player.getInventoryManager().containsItem(1917)) {
                    player.getInventoryManager().removeItem(new ItemStack(1917, 1));
                    player.getDialogueManager().showTwoItemMessage("You give a beer to Dr Harlow.", "", new ItemStack(-1, 1), new ItemStack(1917, 1));
                    player.getDialogueManager().setNextDialogueStep(1);
                    player.setQuestState(this.getQuestId(), 4);
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Cheersh matey...", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So tell me how to kill vampires then.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yesh Yesh vampires, I was very good at", "killing em once...", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showOneLineStatement("Dr Harlow appears to sober up slightly.");
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well you're gonna to need a stake, otherwise he'll just", "regenerate. Yes, you must have a stake to finish it off...", "I just happen to have one with me.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1549, 1));
                    player.getDialogueManager().showItemMessage("Dr Harlow hands you a stake.", new ItemStack(1549, 1));
                    player.setQuestState(this.getQuestId(), 5);
                    return true;
                }
            }
            if (value42 == 5) {
                if (value2 == 1 && !player.ownsItem(1549)) {
                    player.getInventoryManager().addOrDropItem(new ItemStack(1549, 1));
                    player.getDialogueManager().showItemMessage("Dr Harlow hands you a stake.", new ItemStack(1549, 1));
                    player.getDialogueManager().setNextDialogueStep(7);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcFourLineDialogue("You'll need a hammer as well, to drive it in properly,", "your everyday general store hammer will do. One last", "thing... It's wise to carry garlic with you, vampires are", "somewhat weakened if they can smell garlic. Morgan", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("always liked garlic, you should try his house. But", "remember, a vampire is still a dangerous foe!", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Thank you very much!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}
