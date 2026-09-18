package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestConstants;
import com.rs2.model.quest.QuestScript;
import com.rs2.util.GameUtil;

public final class KnightsSwordQuest
extends QuestScript {
    public KnightsSwordQuest(int value2) {
        super(9);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to the Squire in the", "courtyard of the White Knights' Castle in southern Falador", "To complete this quest I need:", "Level 10 Mining", "and to be unafraid of Level 57 Ice Warriors."};
        }
        if (value2 == 2) {
            return new String[]{"I should go speak with Reldo in the library of the", "Palace of Varrock."};
        }
        if (value2 == 3) {
            return new String[]{"Reldo couldn't give me much information about the", "Imcando except a few live on the southern peninsula of", "Asgarnia, they dislike strangers, and LOVE redberry pies."};
        }
        if (value2 == 4) {
            return new String[]{"I gave Thurgo a redberry pie, I should now ask him", "to make me the sword."};
        }
        if (value2 == 5) {
            return new String[]{"Thurgo needs a picture of the sword before he can help.", "I should probably ask the Squire about obtaining one"};
        }
        if (value2 == 6) {
            return new String[]{"Squire told me there might be a picture of the sword", "in the cupboard in Sir Vyvin's room."};
        }
        if (value2 == 7) {
            return new String[]{"I found a picture of the sword. I should now take", "the picture to Thurgo."};
        }
        if (value2 == 8) {
            return new String[]{"According to Thurgo to make a replica sword he will need", "two Iron Bars and some Blurite Ore. Blurite Ore can only be", "found deep in the caves below Thurgo's house"};
        }
        if (value2 == 9) {
            return new String[]{"I should now bring the sword back to the squire."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "12,725 Smithing XP"};
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
        player2.packetSender.sendInterfaceText("12,725 Smithing XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(13, 12725.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 667);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2271 && value2 == 2984 && value32 == 3336) {
            ObjectManager.getInstance().removeDynamicObjectAt(2984, 3336, 2, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2272, 2984, 3336, 2, 1, 10, 2271, 999999999), true);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2272 && value52 == 2984 && value62 == 3336 && value8 == 1 && value72 >= 6 && value72 < 8 && !player.ownsItem(666)) {
            Entity entity = Npc.findByDefinitionId(605);
            value22 = 0;
            if (entity.getInteractionTarget() != null && entity.getInteractionTarget() != player) {
                value22 = 1;
            }
            if (!GameUtil.hasClearPath(player.getPosition(), entity.getPosition(), false)) {
                value22 = 1;
            }
            if (value32 == 1) {
                if (value22 != 0) {
                    player.getDialogueManager().showOneLineStatement("You find a small portrait in here which you take.");
                    return true;
                }
                player.getDialogueManager().setDialogueNpcId(605);
                player.getDialogueManager().showNpcTwoLineDialogue("HEY! Just WHAT do you THINK you are", "DOING??? STAY OUT of MY cupboard!", 591);
                player.getDialogueManager().finishDialogue();
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(666, 1));
                player.setQuestState(this.getQuestId(), 7);
                entity = player;
                ((Player)entity).packetSender.closeInterfaces();
                player.getDialogueManager().finishDialogue();
            }
            return true;
        }
        return false;
    }

    private static boolean hasBluriteSwordMaterials(Player player) {
        return player.getInventoryManager().containsItemAmount(668, 1) && player.getInventoryManager().containsItemAmount(2351, 2);
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 606) {
            if (value42 == 0) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Hello. I am the squire to Sir Vyvin.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptions("And how is life as a squire?", "Wouldn't you prefer to be a squire for me?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("And how is life as a squire?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, Sir Vyvin is a good guy to work for, however,", "I'm in a spot of trouble today. I've gone and lost Sir", "Vyvin's sword!", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showThreeOptions("Do you know where you lost it?", "I can make a new sword if you like...", "Is he angry?");
                    return true;
                }
                if (value2 == 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(5);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I can make a new sword if you like...", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Is he angry?", 591);
                        player.getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("He doesn't know yet. I was hoping I could think of", "something to do before he does find out, But I find", "myself at a loss.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showFourOptions("Well, do you know the VAGUE AREA you lost it?", "I can make a new sword if you like...", "Well, the kingdom is fairly abundant with swords...", "Well, I hope you find it soon.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I can make a new sword if you like...", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Thanks for the offer. I'd be surprised if you could", "though.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcFourLineDialogue("The thing is, this sword is a family heirloom. It has been", "passed down through Vyvin's family for five", "generations! It was originally made by the Imcando", "dwarves, who were", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("a particularly skilled tribe of dwarven smiths. I doubt", "anyone could make it in the style they do.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showTwoOptions("So would these dwarves make another one?", "Well I hope you find it soon.");
                    return true;
                }
                if (value2 == 14) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("So would these dwarves make another one?", 591);
                        player.getDialogueManager().setNextDialogueStep(15);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(13);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcFourLineDialogue("I'm not a hundred percent sure the Imcando tribe", "exists anymore. I should think Reldo, the palace", "librarian in Varrock, will know; he has done a lot of", "research on the races of RuneScape.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I don't suppose you could try and track down the", "Imcando dwarves for me? I've got so much work to", "do...", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showTwoOptions("Ok, I'll give it a go.", "No, I've got lots of mining work to do.");
                    return true;
                }
                if (value2 == 18) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll give it a go.", 591);
                        player.getDialogueManager().setNextDialogueStep(19);
                        this.startQuest(player);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2 && value2 == 19) {
                player.getDialogueManager().showNpcTwoLineDialogue("Thank you very much! As I say, the best place to start", "should be with Reldo...", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 5) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("So how are you doing getting a sword?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I've found an Imcando dwarf but he needs a picture of", "the sword before he can make it.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("A picture eh? Hmmm.... The only one I can think of is", "in a small portrait of Sir Vyvin's father... Sir Vyvin", "keeps it in a cupboard in his room I think.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll try and get that then.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Please don't let him catch you! He MUSTN'T know", "what happened!", 591);
                    player.setQuestState(this.getQuestId(), 6);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 9 && player.getInventoryManager().containsItem(667)) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have retrieved your sword for you.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Thank you, thank you, thank you! I was seriously", "worried I would have to own up to Sir Vyvin!", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("You give the sword to the squire.");
                    return true;
                }
                if (value2 == 4) {
                    player.getInventoryManager().removeItem(new ItemStack(667, 1));
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().markDialogueInactive();
                    return true;
                }
            }
        }
        if (npcId == QuestConstants.RELDO_NPC_ID && value42 == 2) {
            if (value2 == 1) {
                value2 = 100;
            }
            if (value2 == 100) {
                player.getDialogueManager().showPlayerOneLineDialogue("What do you know about the Imcando dwarves?", 591);
                player.getDialogueManager().setNextDialogueStep(101);
                return true;
            }
            if (value2 == 101) {
                player.getDialogueManager().showNpcOneLineDialogue("The Imcando dwarves, you say?", 591);
                return true;
            }
            if (value2 == 102) {
                player.getDialogueManager().showNpcThreeLineDialogue("Ah yes... for many hundreds of years they were the", "world's most skilled smiths. They used secret smithing", "knowledge passed down from generation to generation.", 591);
                return true;
            }
            if (value2 == 103) {
                player.getDialogueManager().showNpcThreeLineDialogue("Unfortunately, about a century ago, the once thriving", "race was wiped out during the barbarian invasions of", "that time.", 591);
                return true;
            }
            if (value2 == 104) {
                player.getDialogueManager().showPlayerOneLineDialogue("So are there any Imcando left at all?", 591);
                return true;
            }
            if (value2 == 105) {
                player.getDialogueManager().showNpcThreeLineDialogue("I believe a few of them survived, but with the bulk of", "their population destroyed their numbers have dwindled", "even further.", 591);
                return true;
            }
            if (value2 == 106) {
                player.getDialogueManager().showNpcThreeLineDialogue("I believe I remember a couple living in Asgarnia near", "the cliffs on the Asgarnian southern peninsula, but they", "DO tend to keep to themselves.", 591);
                return true;
            }
            if (value2 == 107) {
                player.getDialogueManager().showNpcFourLineDialogue("They tend not to tell people that they're the", "descendants of the Imcando, which is why people think", "that the tribe has died out totally, but you may well", "have more luck talking to them if you bring them some", 591);
                return true;
            }
            if (value2 == 108) {
                player.getDialogueManager().showNpcOneLineDialogue("redberry pie. They REALLY like redberry pie.", 591);
                player.setQuestState(this.getQuestId(), 3);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 604) {
            if (value42 == 3) {
                if (value2 == 1) {
                    player.getDialogueManager().showTwoOptions("Hello. Are you an Imcando dwarf?", "Would you like some redberry pie?");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Hello. Are you an Imcando dwarf?", 591);
                        player.getDialogueManager().setNextDialogueStep(3);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Would you like some redberry pie?", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Maybe. Who wants to know?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showTwoOptions("Would you like some redberry pie?", "Can you make me a special sword?");
                    return true;
                }
                if (value2 == 5) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Would you like some redberry pie?", 591);
                        player.getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showOneLineStatement("You see Thurgo's eyes light up.");
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'd never say no to a redberry pie! They're GREAT", "stuff!", 591);
                    return true;
                }
                if (value2 == 8 && player.getInventoryManager().containsItemAmount(2325, 1)) {
                    player.getDialogueManager().showTwoLineStatement("You hand over the pie. Thurgo eats the pie. Thurgo pats his", "stomach.");
                    return true;
                }
                if (value2 == 9 && player.getInventoryManager().containsItemAmount(2325, 1)) {
                    player.getInventoryManager().removeItem(new ItemStack(2325, 1));
                    player.getDialogueManager().showNpcTwoLineDialogue("By Guthix! THAT was good pie! Anyone who makes pie", "like THAT has got to be alright!", 591);
                    player.setQuestState(this.getQuestId(), 4);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can you make me a special sword?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, after bringing me my favorite food I guess I", "should give it a go. What sort of sword is it?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerFourLineDialogue("I need you to make a sword for one of Falador's", "knights. He had one which was passed down through five", "generations, but his squire has lost it. So we need an", "identical one to replace it.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("A Knight's sword eh? Well I'd need to know exactly", "how it looked before I could make a new one.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("All the Faladian knights used to have swords with unique", "designs according to their position. Could you bring me", "a picture or something?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'll go and ask his squire and see if I can find one.", 591);
                    player.setQuestState(this.getQuestId(), 5);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 7 && player.getInventoryManager().containsItem(666)) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("About that sword...", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I have found a picture of the sword I would like you to", "make.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showOneLineStatement("You give the portrait to Thurgo. Thurgo studies the portrait.");
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ok. You'll need to get me some stuff in order for me", "to make this.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("I'll need two iron bars to make the sword to start with.", "I'll also need an ore called blurite. It's useless for", "making actual weapons for fighting with except", "crossbows, but I'll need some as decoration for the hilt.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("It is fairly rare sort of ore... The only place I know", "where to get it is under this cliff here...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("But it is guarded by a very powerful ice giant.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Most of the rocks in that cliff are pretty useless, and", "don't contain much of anything, but there's", "DEFINITELY some blurite in there.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You'll need a little bit of mining experience to be able to", "find it.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok. I'll go and find them then.", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getInventoryManager().removeItem(new ItemStack(666, 1));
                    player.setQuestState(this.getQuestId(), 8);
                    player.getDialogueManager().finishDialogue();
                    player.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 >= 8) {
                if (value42 == 9 && player.ownsItem(667) && value2 != 7) {
                    return false;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("About that sword...", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("How are you doing finding those sword materials?", 591);
                    if (!KnightsSwordQuest.hasBluriteSwordMaterials(player)) {
                        player.getDialogueManager().setNextDialogueStep(3);
                    } else {
                        player.getDialogueManager().setNextDialogueStep(4);
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I don't have them yet.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have them right here.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showTwoLineStatement("You give the blurite ore and two iron bars to Thurgo. Thurgo starts", "to make the sword. Thurgo hands you a sword.");
                    return true;
                }
                if (value2 == 6 && KnightsSwordQuest.hasBluriteSwordMaterials(player)) {
                    player.getInventoryManager().removeItem(new ItemStack(668, 1));
                    player.getInventoryManager().removeItem(new ItemStack(2351, 2));
                    player.getInventoryManager().addOrDropItem(new ItemStack(667, 1));
                    player.getDialogueManager().showPlayerOneLineDialogue("Thank you very much!", 591);
                    player.setQuestState(this.getQuestId(), 9);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Just remember to call in with more pie some time!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

