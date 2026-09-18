package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.ApothecaryCadavaPotionMixTask;
import com.rs2.model.task.TickTask;

public final class RomeoAndJulietQuest
extends QuestScript {
    public RomeoAndJulietQuest(int value2) {
        super(13);
        super.setQuestPointReward(5);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Romeo in Varrock", "central square by the fountain."};
        }
        if (value2 == 2) {
            return new String[]{"Romeo told me to find Juliet."};
        }
        if (value2 == 3) {
            return new String[]{"I should bring the message from Juliet to Romeo."};
        }
        if (value2 == 4) {
            return new String[]{"I should speak to Father Lawrence in the church", "located North East of Varrock."};
        }
        if (value2 == 5) {
            return new String[]{"Father Lawrence told me to go to the Apothecary", "and ask for Cadava potion."};
        }
        if (value2 == 6) {
            return new String[]{"I should find and bring Cadava berries to Apothecary."};
        }
        if (value2 == 7) {
            return new String[]{"I should bring the Cadava potion to Juliet."};
        }
        if (value2 == 8) {
            return new String[]{"I should go tell Romeo that he can find Juliet from", "the crypt."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "5 Quest Points"};
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
        player2.packetSender.sendInterfaceText("", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 756);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleNpcDialogue(Player npcId, int npcId2, int value2, int value32, int value42) {
        if (npcId2 == 639) {
            if (value42 == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh woe is me that I cannot find my Juliet! You haven't", "seen Juliet have you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Yes, I have seen her actually!", "No, sorry. I haven't seen her.", "Perhaps I could help to find her for you?");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 3) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Perhaps I can help find her for you? What does she", "look like?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(2);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh would you? That would be great! She has this sort", "of hair...", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hair...check..", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("...and she these...great lips...", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Lips...right.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Oh and she has these lovely shoulders as well..", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Shoulders...right, so she has hair, lips and shoulders...that", "should cut it down a bit.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh yes, Juliet is very different...please tell her that she", "is the love of my long and that I life to be with her?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("What?", "Surely you mean that 'she is the love of your life and", "that you long to be with her?'", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Oh yeah...what you said...tell her that, it sounds much", "better!", "Oh you're so good at this!", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes, ok, I'll let her know.", "Sorry Romeo, I've got better things to do right now but maybe later?");
                    return true;
                }
                if (value2 == 14) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes, ok, I'll let her know.", 591);
                        this.startQuest((Player)npcId);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(15);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Oh great! And tell her that I want to kiss her a give.", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You mean you want to give her a kiss!", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Oh you're good...you are good!", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I see I've picked a true professional...!", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Where can I find Juliet?", "Is there anything else you can tell me about Juliet?", "Ok, thanks.");
                    return true;
                }
                if (value2 == 20) {
                    if (value32 == 3) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Ok, thanks.", 591);
                        ((Player)npcId).getDialogueManager().finishDialogue();
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(19);
                    return true;
                }
            }
            if (value42 == 3 && ((Player)npcId).getInventoryManager().containsItem(755)) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Romeo...great news...I've been in touch with Juliet!", "She's written a message for you...", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showItemMessage("You hand over Juliet's message to Romeo.", new ItemStack(755, 1));
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh, a message! A message! I've never had a message", "before...", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Really?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("No, no, not one!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Oh, well, except for the occasional court summons.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("But they're not really 'nice' messages. Not like this one!", "I'm sure that this message will be lovely.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well are you going to open it or not?", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Oh yes, yes, of course!", "'Dearest Romeo, I am very pleased that you sent", String.valueOf(((Player)npcId).getUsername()) + " to look for me and to tell me that you still", "hold affliction...', Affliction! She thinks I'm diseased?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("'Affection?'", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ahh yes...'still hold affection for me. I still feel great", "affection for you, but unfortunately my Father opposes", "our marriage.'", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Oh dear...that doesn't sound too good.", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("What? '...great affection for you. Father opposes our..", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("'...marriage and will...", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("...will kill you if he sees you again!'", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I have to be honest, it's not getting any better...", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("'Our only hope is that Father Lawrence, our long time", "confidant, can help us in some way.'", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showItemMessage("Romeo folds the message away.", new ItemStack(755, 1));
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Well, that's it then...we haven't got a chance...", 591);
                    return true;
                }
                if (value2 == 20) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What about Father Lawrence?", 591);
                    return true;
                }
                if (value2 == 21) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("...our love is over...the great romance, the life of my", "love...", 591);
                    return true;
                }
                if (value2 == 22) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("...or you could speak to Father Lawrence!", 591);
                    return true;
                }
                if (value2 == 23) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh, my aching, breaking, heart...how useless the situation", "is now...we have no one to turn to...", 591);
                    return true;
                }
                if (value2 == 24) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("FATHER LAWRENCE!", 591);
                    return true;
                }
                if (value2 == 25) {
                    ((Player)npcId).getInventoryManager().removeItem(new ItemStack(755, 1));
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Father Lawrence?", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 4);
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 26) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Oh yes, Father Lawrence...he's our long time confidant,", "he might have a solution! Yes, yes, you have to go and", "talk to Lather Fawrence for us and ask him if he's got", "any suggestions for our predicament?", 591);
                    return true;
                }
                if (value2 == 27) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Where can I find Father Lawrence?", 591);
                    return true;
                }
                if (value2 == 28) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Lather Fawrence! Oh he's...", 591);
                    return true;
                }
                if (value2 == 29) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("You know he's not my 'real' Father don't you?", 591);
                    return true;
                }
                if (value2 == 30) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I think I suspected that he wasn't.", 591);
                    return true;
                }
                if (value2 == 31) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well anyway...he tells these song, loring bermons...and", "keeps these here Carrockian vitizens snoring in his", "church to the East North.", 591);
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showFourOptions("How are you?", "Where can I find Father Lawrence?", "Have you heard anything from Juliet?", "Ok, thanks.");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Where can I find Father Lawrence?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(28);
                        return true;
                    }
                    if (value32 == 4) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Ok, thanks.", 591);
                        ((Player)npcId).getDialogueManager().finishDialogue();
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(1);
                    return true;
                }
            }
            if (value42 == 8) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Romeo, it's all set. Juliet has drunk the potion.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Ah right, the potion! Great...", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("What potion would that be then?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("The one to get her to the crypt.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Ah right, So she is dead then, Ah thats a shame.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Thanks for your help anyway.", 591);
                    return true;
                }
                if (value2 == 7) {
                    this.awardCompletionRewards((Player)npcId);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 637) {
            if (value42 == 2) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Juliet, I come from Romeo.", "He begs me to tell you that he cares still.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh how my heart soars to hear this news! Please take", "this message to him with great haste.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Well, I hope it's good news...he was quite upset when I", "left him.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("He's quite often upset...the poor sensitive soul. But I", "don't think he's going to take this news very well,", "however, all is not lost.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Everything is explained in the letter, would you be so", "kind and deliver it to him please?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Certainly, I'll do so straight away.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Many thanks! Oh, I'm so very grateful. You may be", "our only hope.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(755, 1));
                    ((Player)npcId).getDialogueManager().showItemMessage("Juliet gives you a message.", new ItemStack(755, 1));
                    ((Player)npcId).setQuestState(this.getQuestId(), 3);
                    return true;
                }
            }
            if (value42 >= 2 && value42 < 4) {
                if (!((Player)npcId).ownsItem(755) && value2 == 1) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(755, 1));
                    ((Player)npcId).getDialogueManager().showItemMessage("Juliet gives you a message.", new ItemStack(755, 1));
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(52);
                    return true;
                }
                if (value2 == 52) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Now don't lose it this time.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 3 && value2 == 9) {
                Player player = npcId;
                player.packetSender.closeInterfaces();
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 7 && ((Player)npcId).getInventoryManager().containsItem(756)) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerFourLineDialogue("Hi Juliet! I have an interesting proposition for", "you...suggested by Father Lawrence. It may be the", "only way you'll be able to escape from this house and", "be with Romeo.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Go on...", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I have a Cadava potion here, suggested by Father", "Lawrence. If you drink it, it will make you appear dead!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes...", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("And when you appear dead...your still and lifeless", "corpse will be removed to the crypt!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Oooooh, a cold dark creepy crypt...", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("...sounds just peachy!", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Then...Romeo can steal into the crypt and rescue you", "just as you wake up!", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("...and this is the great idea for getting me out of here?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("To be fair, I can't take all the credit...in fact...it was all", "Father Lawrence's suggestion...", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Ok...if this is the best we can do...hand over the potion!", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showTwoItemMessage("You pass the suspicious potion to Juliet.", "", new ItemStack(-1, 1), new ItemStack(756, 1));
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Wonderful! I just hope Romeo can remember to get", "me from the crypt.", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Please go to Romeo and make sure he understands.", "Although I love his gormless, lovelorn soppy ways, he", "can be a bit dense sometimes and I don't want to wake", "up in that crypt on my own.", 591);
                    ((Player)npcId).getInventoryManager().removeItem(new ItemStack(756, 1));
                    ((Player)npcId).setQuestState(this.getQuestId(), 8);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 640 && value42 == 4) {
            if (value2 == 1) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("\"...and let Saradomin light the way for you... \"", "Urgh!", 591);
                return true;
            }
            if (value2 == 2) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Can't you see that I'm in the middle of a Sermon?!", 591);
                return true;
            }
            if (value2 == 3) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("But Romeo sent me!", 591);
                return true;
            }
            if (value2 == 4) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("But I'm busy delivering a sermon to my congregation!", 591);
                return true;
            }
            if (value2 == 5) {
                ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Yes, well, it certainly seems like you have a captive", "audience!", 591);
                return true;
            }
            if (value2 == 6) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Ok, ok...what do you want so I can get rid of you and", "continue with my sermon?", 591);
                return true;
            }
            if (value2 == 7) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Romeo sent me. He says you may be able to help.", 591);
                return true;
            }
            if (value2 == 8) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Ah Romeo, yes. A fine lad, but a little bit confused.", 591);
                return true;
            }
            if (value2 == 9) {
                ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("Yes, very confused...Anyway, Romeo wishes to be", "married to Juliet! She must be rescued from her", "father's control!", 591);
                return true;
            }
            if (value2 == 10) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I agree, and I think I have an idea! A potion to make", "her appear dead...", 591);
                return true;
            }
            if (value2 == 11) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Dead! Sounds a bit creepy to me...but please, continue.", 591);
                return true;
            }
            if (value2 == 12) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("The potion will only make Juliet 'appear' dead...then", "she'll be taken to the crypt...", 591);
                return true;
            }
            if (value2 == 13) {
                ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Crypt! Again...very creepy! You must have some", "strange hobbies.", 591);
                return true;
            }
            if (value2 == 14) {
                ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Then Romeo can collect her from the crypt! Go to the", "Apothecary, tell him I sent you and that you'll need a", "'Cadava' potion.", 591);
                return true;
            }
            if (value2 == 15) {
                ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Apart from the strong overtones of death, this is", "turning out to be a real love story.", 591);
                ((Player)npcId).setQuestState(this.getQuestId(), 5);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 638) {
            if (value42 == 5) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Apothecary. Father Lawrence sent me.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I need a Cadava potion to help Romeo and Juliet.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Cadava potion. It's pretty nasty. And hard to make.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Wing of rat. tail of frog.", "Ear of snake and horn of dog.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I have all that, but I need some Cadava berries.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("You will have to find them while I get the rest ready.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Bring them here when you have them. But be careful.", "They are nasty.", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 6);
                    return true;
                }
            }
            if (value42 == 6) {
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showFourOptions("What do these berries look like?", "Where can I get these berries?", "How are these berries dangerous?", "Ok, thanks.");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 4) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Ok, thanks.", 591);
                        ((Player)npcId).getDialogueManager().finishDialogue();
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                if (((Player)npcId).getInventoryManager().containsItem(753)) {
                    if (value2 == 1) {
                        ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Well done. You have the berries.", 591);
                        return true;
                    }
                    if (value2 == 2) {
                        ((Player)npcId).getDialogueManager().showTwoItemMessage("You hand over the berries, which the Apothecary shakes", "up in a vial of strange liquid.", new ItemStack(-1, 1), new ItemStack(753, 1));
                        return true;
                    }
                    if (value2 == 3) {
                        Player player = npcId;
                        player.packetSender.closeInterfaces();
                        World.getTaskScheduler().schedule(new ApothecaryCadavaPotionMixTask(this, 2, (Player)npcId));
                        return true;
                    }
                    if (value2 == 4) {
                        ((Player)npcId).getInventoryManager().removeItem(new ItemStack(753, 1));
                        ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(756, 1));
                        ((Player)npcId).getDialogueManager().showTwoItemMessage("Apothecary gives you a Cadava potion.", "", new ItemStack(-1, 1), new ItemStack(756, 1));
                        ((Player)npcId).setQuestState(this.getQuestId(), 7);
                        return true;
                    }
                }
            }
            if (value42 == 7) {
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    Player player = npcId;
                    player.packetSender.closeInterfaces();
                    return true;
                }
                if (!((Player)npcId).ownsItem(756) && value2 == 1) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(756, 1));
                    ((Player)npcId).getDialogueManager().showTwoItemMessage("Apothecary gives you a Cadava potion.", "", new ItemStack(-1, 1), new ItemStack(756, 1));
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Now don't lose it this time.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}

