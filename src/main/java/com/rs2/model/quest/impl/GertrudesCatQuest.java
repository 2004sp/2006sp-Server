package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestHook;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.FluffsInteractionHintTask;
import com.rs2.model.quest.impl.FluffsKittenReunionStartTask;
import com.rs2.model.quest.impl.GertrudeRewardFoodTask;
import com.rs2.model.quest.impl.KittenCrateSearchTask;
import com.rs2.model.task.TickTask;

public final class GertrudesCatQuest
extends QuestScript {
    public GertrudesCatQuest(int value2) {
        super(42);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Gertrude.", "She can be found to the west of Varrock."};
        }
        if (value2 == 2) {
            return new String[]{"I should go talk to the boys who can be found", "in the Varrock marketplace."};
        }
        if (value2 == 3) {
            return new String[]{"The boys told me to go to the abandoned lumber", "mill just beyond Jolly Boar Inn."};
        }
        if (value2 == 4) {
            return new String[]{"Maybe I should bring Fluffs something to eat."};
        }
        if (value2 == 5) {
            return new String[]{"I should go look for a kitten."};
        }
        if (value2 == 6) {
            return new String[]{"I should now go talk to Gertrude."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "A kitten!", "1525 Cooking XP", "A chocolate cake", "A bowl of stew.", "Raise cats."};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        Object value = player;
        ((Player)value).packetSender.sendInterfacePosition(12145, 60, 130);
        super.markQuestComplete(player);
        Player player2 = player;
        value = player2;
        value = this;
        player2.packetSender.sendInterfaceText("You have completed " + QuestDefinition.forId(((QuestHook)value).getQuestId()).getName() + "!", 12144);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("1 Quest Point", 12150);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("A kitten!", 12151);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("1525 Cooking XP", 12152);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("A chocolate cake", 12153);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("A bowl of stew.", 12154);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("Raise cats.", 12155);
        player.getSkillManager().addQuestExperience(7, 1525.0);
        value = player;
        ((Player)value).packetSender.sendMusicJingle(238, 320);
        value = player;
        ((Player)value).packetSender.sendInterfaceText("" + player.getQuestPoints(), 12147);
        value = player;
        ((Player)value).packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1561);
        value = player;
        ((Player)value).packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        value = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2618 && value2 == 3305 && value32 == 3493) {
            Player player2 = player;
            player2.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3493 ? 2 : -2, true);
            player.getUpdateState().setAnimation(882);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstNpcAction(Player npcId, int npcId2, int value3) {
        Object value2;
        if (npcId2 == 759) {
            value2 = Npc.findByDefinitionId(759);
            if (value3 >= 3) {
                ((Entity)value2).getUpdateState().setForcedText("Hisss!");
                ((Entity)npcId).getUpdateState().setForcedText("Ouch!");
                ((Player)npcId).setActionLocked(true);
                World.getTaskScheduler().schedule(new FluffsInteractionHintTask(this, 2, value3, (Player)npcId));
                return true;
            }
        }
        if (npcId2 == 767 && value3 == 5 && !((Player)npcId).ownsItem(1554)) {
            Player player = npcId;
            player.packetSender.sendGameMessage("You search the crate.");
            ((Player)npcId).setActionLocked(true);
            World.getTaskScheduler().schedule(new KittenCrateSearchTask(this, 3, (Player)npcId));
        }
        return false;
    }

    @Override
    public final boolean handleItemOnNpc(Player npcId, int npcId2, int value2, int value32) {
        Npc npc;
        if (value2 == 1927 && npcId2 == 759) {
            npc = Npc.findByDefinitionId(759);
            if (value32 == 3) {
                ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(1927, 1), new ItemStack(1925, 1));
                npc.getUpdateState().setForcedText("Mew!");
                ((Player)npcId).setQuestState(this.getQuestId(), 4);
                return true;
            }
        }
        if (value2 == 1552 && npcId2 == 759) {
            npc = Npc.findByDefinitionId(759);
            if (value32 == 4) {
                ((Player)npcId).getInventoryManager().removeItem(new ItemStack(1552, 1));
                npc.getUpdateState().setForcedText("Mew!");
                ((Player)npcId).setQuestState(this.getQuestId(), 5);
                return true;
            }
        }
        if (value2 == 1554 && npcId2 == 759) {
            npc = Npc.findByDefinitionId(759);
            if (value32 == 5) {
                ((Player)npcId).getInventoryManager().removeItem(new ItemStack(1554, 1));
                ((Entity)npcId).getUpdateState().setAnimation(827);
                Npc npc2 = new Npc(760);
                GameplayHelper.spawnOwnedNpcAtPosition((Player)npcId, ((Entity)npcId).getPosition(), npc2, false, false);
                npc.getUpdateState().setForcedText("Purr...");
                npc2.getUpdateState().setForcedText("Purr...");
                ((Player)npcId).setQuestState(this.getQuestId(), 6);
                ((Player)npcId).setActionLocked(true);
                World.getTaskScheduler().schedule(new FluffsKittenReunionStartTask(this, 2, npc2, npc, (Player)npcId));
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        if (value32 != 0 && (itemId == 1573 && value2 == 327 || itemId == 327 && value2 == 1573)) {
            player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
            player.getInventoryManager().removeItem(new ItemStack(value2, 1));
            player.getInventoryManager().addItem(new ItemStack(1552, 1));
            player.getDialogueManager().showOneLineStatement("You rub the doogle leaves over the sardine.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player npcId, int npcId2, int value2, int value32, int value42) {
        if (npcId2 == 780) {
            if (value42 == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello, are you ok?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Do I look ok? Those kids drive me crazy.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I'm sorry. It's just that I've lost her.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Lost who?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Fluffs, poor Fluffs. She never hurt anyone.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Who's Fluffs?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("My beloved feline friend Fluffs. She's been purring by", "my side for almost a decade. Please, could you go", "search for her while I look over the kids?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Well, I suppose I could.", "What's in it for me?", "Sorry, I'm too busy to play pet rescue.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well, I suppose I could.", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Really? Thank you so much! I really have no idea", "where she could be!", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I think my sons, Shilop and Wilough, saw the cat last.", "They'll be out in the market place.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Alright then, I'll see what I can do.", 591);
                    this.startQuest((Player)npcId);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 6) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello Gertrude. Fluffs ran off with her kitten.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You're back! Thank you! Thank you! Fluffs just came", "back! I think she was just upset as she couldn't find her", "kitten.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("Gertrude gives you a hug.");
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("If you hadn't found her kitten it would have died out", "there!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("That's ok, I like to do my bit.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I don't know how to thank you. I have no real material", "possessions. I do have kittens! I can only really look", "after one.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well, if it needs a home.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I would sell it to my cousin in West Ardougne. I hear", "there's a rat epidemic there. But it's too far.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Here you go, look after her and thank you again!", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Oh by the way, the kitten can live in your backpack,", "but to make it grow you must take it out and feed and", "stroke it often.", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("Gertrude gives you a kitten.");
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    ((Player)npcId).getPetManager().spawnQuestCatFollower(1561, 768);
                    ((Player)npcId).setActionLocked(true);
                    World.getTaskScheduler().schedule(new GertrudeRewardFoodTask(this, 5, (Player)npcId));
                    return false;
                }
            }
        }
        if (npcId2 == 783) {
            if (value42 == 2) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there, I've been looking for you.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I didn't mean to take it! I just forgot to pay.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What? I'm trying to help your mum find Fluffs.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ohh...well, in that case I might be able to help. Fluffs", "followed me to my secret play area, I haven't seen her", "since.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Where is this play area?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("If I told you that, it wouldn't be a secret.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Tell me sonny, or I will hurt you.", "What will make you tell me?", "Well never mind, it's Fluffs' loss.");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What will make you tell me?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Well...now you ask, I am a bit short on cash.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("How much?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("100 coins should cover it.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("100 coins! Why should I pay you?", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("You shouldn't, but I won't help otherwise. I never liked", "that cat anyway, so what do you say?", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("I'm not paying you a penny.", "Okay then, I'll pay.");
                    return true;
                }
                if (value2 == 15) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Okay then, I'll pay.", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(16);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 16 && ((Player)npcId).getInventoryManager().containsItemAmount(995, 100)) {
                    ((Player)npcId).getInventoryManager().removeItem(new ItemStack(995, 100));
                    ((Player)npcId).getDialogueManager().showItemMessage("You give the lad 100 coins.", new ItemStack(995, 100));
                    ((Player)npcId).setQuestState(this.getQuestId(), 3);
                    return true;
                }
            }
            if (value42 == 3) {
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("There you go, now where did you see Fluffs?", 591);
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I play at an abandoned lumber mill to the north east.", "Just beyond the Jolly Boar Inn. I saw Fluffs running", "around in there.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Anything else?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Well, you'll have to find the broken fence to get in. I'm", "sure you can manage that.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

