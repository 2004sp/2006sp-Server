package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class PriestInPerilQuest
extends QuestScript {
    private final List monumentObjectIds = Arrays.asList(3493, 3494, 3495, 3496, 3497, 3498, 3499);

    public PriestInPerilQuest(int value2) {
        super(72);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value3) {
        if (value3 == 0) {
            return new String[]{"I can start this quest by speaking to King Roald in Varrock", "Palace", "", "I must be able to defeat a level 30 enemy"};
        }
        if (value3 == 2) {
            return new String[]{"I should investigate what happened to Drezel, who lives", "in a temple east of Varrock."};
        }
        if (value3 == 3) {
            return new String[]{"Drezel told me there is an annoying dog below the temple", "and asked me to kill it."};
        }
        if (value3 == 4) {
            return new String[]{"I should go tell Drezel now that I have taken", "care of the dog."};
        }
        if (value3 == 5) {
            return new String[]{"I should go tell King Roald that everything is fine now", "and claim my reward."};
        }
        if (value3 == 6) {
            return new String[]{"I must return to the temple and find out what happened", "to the real Drezel, or the King will have me executed!"};
        }
        if (value3 == 7) {
            return new String[]{"I found the real Drezel locked in a makeshift cell", "upstairs, guarded by a vampyre.", "", "I need to find the key to his cell and free him!"};
        }
        if (value3 == 8) {
            return new String[]{"I unlocked the door to Drezel's cell, I should go", "and ask him what to do about the vampyre."};
        }
        if (value3 == 10) {
            return new String[]{"Drezel told me that I should try to get hold of", "some water of the Salve."};
        }
        if (value3 == 11) {
            return new String[]{"I should tell Drezel that the vampire is trapped", "now."};
        }
        if (value3 == 12) {
            return new String[]{"Drezel told me to meet him down by the monument", "below the temple."};
        }
        if (value3 >= 13 && value3 < 63) {
            int value2 = 63 - value3;
            String[] stringValues = new String[]{"Drezel told me to bring him some rune essence.", "", "Drezel needs " + value2 + " more essences."};
            return stringValues;
        }
        if (value3 == 63) {
            return new String[]{"I should speak with Drezel to finish this quest."};
        }
        if (value3 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "1406 Prayer XP", "Wolfbane dagger", "Route to Canifis"};
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
        player2.packetSender.sendInterfaceText("1406 Prayer XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("Wolfbane dagger", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("Route to Canifis", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(5, 1406.0);
        player.getInventoryManager().addOrDropItem(new ItemStack(2952, 1));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 2952);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        ArrayList arrayList = new ArrayList(this.monumentObjectIds);
        Collections.shuffle(arrayList, new Random(player.questRandomSeed));
        if (value2 == (Integer)arrayList.get(5) && objectId == 2944) {
            player.getInventoryManager().removeItem(new ItemStack(2944, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(2945, 1));
            Player player2 = player;
            player2.packetSender.sendGameMessage("You swap the Golden key for the Iron key.");
            player.getUpdateState().setAnimation(832);
            return true;
        }
        if (value2 == 3463 && objectId == 2945) {
            player.getDialogueManager().setDialogueNpcId(1048);
            player.getDialogueManager().showNpcOneLineDialogue("Oh! Thank you! You have found the key!", 591);
            player.getInventoryManager().removeItem(new ItemStack(2945, 1));
            player.setQuestState(this.getQuestId(), 8);
            return true;
        }
        if (value2 == 3485 && objectId == 1925) {
            player.getInventoryManager().removeItem(new ItemStack(1925, 1));
            if (value32 == 1) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1929, 1));
            } else {
                player.getInventoryManager().addOrDropItem(new ItemStack(2953, 1));
            }
            Player player3 = player;
            player3.packetSender.sendGameMessage("You fill the bucket from the well.");
            player.getUpdateState().setAnimation(832);
            player3 = player;
            player3.packetSender.sendSoundEffect(1039, 1, 0);
            return true;
        }
        if (value2 == 3480 && value32 == 10 && objectId == 2954) {
            player.getInventoryManager().removeItem(new ItemStack(2954, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(1925, 1));
            Player player4 = player;
            player4.packetSender.sendGameMessage("You pour the blessed water over the coffin...");
            player.getUpdateState().setAnimation(832);
            player.setQuestState(this.getQuestId(), 11);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 3496 && value2 == 3423 && value32 == 9884 || objectId == 3498 && value2 == 3427 && value32 == 9885 || objectId == 3495 && value2 == 3428 && value32 == 9890 || objectId == 3497 && value2 == 3427 && value32 == 9894 || objectId == 3494 && value2 == 3423 && value32 == 9895 || objectId == 3499 && value2 == 3418 && value32 == 9894 || objectId == 3493 && value2 == 3416 && value32 == 9890) {
            if (value42 != 1) {
                player.applyDirectHit(GameUtil.randomInclusive(6), HitType.NORMAL);
                Player player2 = player;
                player2.packetSender.sendGameMessage("A holy power prevents you stealing from the monument!");
                player.getUpdateState().setAnimation(832);
            } else {
                Player player3 = player;
                player3.packetSender.sendGameMessage("It would be wrong to dishonour this monument.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        ArrayList arrayList = new ArrayList(this.monumentObjectIds);
        Collections.shuffle(arrayList, new Random(player.questRandomSeed));
        if (objectId == 3443 && value2 == 3440 && value32 == 9886) {
            if (value42 == 1) {
                player.moveTo(new Position(3423, 3485, 0));
                Player player2 = player;
                player2.packetSender.sendGameMessage("You step through the holy barrier and appear in Canifis.");
            } else {
                Player player3 = player;
                player3.packetSender.sendGameMessage("You need to complete Priest in Peril Quest to pass-through.");
            }
            return true;
        }
        if (objectId == 3480 && value2 == 3413 && value32 == 3486) {
            player.getDialogueManager().showPlayerTwoLineDialogue("It sounds like there's something alive inside it. I don't", "think it would be a very good idea to open it...", 591);
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId == 3463 && value2 == 3415 && value32 == 3489) {
            if (value42 >= 8 || value42 == 1) {
                Player player4 = player;
                player4.packetSender.queueRelativeMovementStep(player.getPosition().getX() > 3415 ? -1 : 1, 0, true);
                player4 = player;
                player4.packetSender.openSingleDoor(3463, 3415, 3489, player.getPosition().getPlane());
            } else {
                Player player5 = player;
                player5.packetSender.sendGameMessage("The door is securely locked shut.");
            }
            return true;
        }
        if (objectId == 3444 && value2 == 3405 && value32 == 9895) {
            if (value42 >= 7 || value42 == 1) {
                Player player6 = player;
                player6.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() > 9894 ? -1 : 1, true);
                player6 = player;
                player6.packetSender.openSingleDoor(3444, 3405, 9895, player.getPosition().getPlane());
            } else {
                Player player7 = player;
                player7.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 3445 && value2 == 3431 && value32 == 9897) {
            if (value42 >= 12 || value42 == 1) {
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() > 3431 ? -1 : 1, 0, true);
                player8 = player;
                player8.packetSender.openSingleDoor(3445, 3431, 9897, player.getPosition().getPlane());
            } else {
                Player player9 = player;
                player9.packetSender.sendGameMessage("This door is locked.");
            }
            return true;
        }
        if (objectId == 3489 && value2 == 3408 && value32 == 3489 || objectId == 3490 && value2 == 3408 && value32 == 3488) {
            if (value42 == 0 || value42 >= 2 && value42 < 6) {
                Player player10 = player;
                player10.packetSender.sendGameMessage("This door is securely locked from inside.");
                return true;
            }
            Player player11 = player;
            player11.packetSender.queueRelativeMovementStep(player.getPosition().getX() > 3408 ? -1 : 1, 0, true);
            player11 = player;
            player11.packetSender.openDoubleDoorPair(3489, 3490, 3408, 3489, 3408, 3488, 0);
            return true;
        }
        if (objectId == 3485 && value2 == 3423 && value32 == 9890) {
            if (value42 != 1) {
                player.getDialogueManager().showTwoLineStatement("You look down the well and see the filthy polluted water of the river", "Salve moving slowly along.");
            } else {
                player.getDialogueManager().showTwoLineStatement("You look down the well and see the fresh water of the river Salve", "moving swiftly along.");
            }
            return true;
        }
        if (value42 != 1) {
            if (objectId == (Integer)arrayList.get(0)) {
                Player player12 = player;
                player12.packetSender.sendInterfaceModel(8111, 250, 2949);
                player12 = player;
                player12.packetSender.sendInterfaceText("Saradomin is the\\nhammer that\\ncrushes evil\\neverywhere.", 8112);
                player12 = player;
                player12.packetSender.showInterface(8016);
                return true;
            }
            if (objectId == (Integer)arrayList.get(1)) {
                Player player13 = player;
                player13.packetSender.sendInterfaceModel(8111, 200, 2951);
                player13 = player;
                player13.packetSender.sendInterfaceText("Saradomin is the\\nneedle that binds\\nour lives\\ntogether.", 8112);
                player13 = player;
                player13.packetSender.showInterface(8016);
                return true;
            }
            if (objectId == (Integer)arrayList.get(2)) {
                Player player14 = player;
                player14.packetSender.sendInterfaceModel(8111, 250, 2948);
                player14 = player;
                player14.packetSender.sendInterfaceText("Saradomin is the\\nvessel that\\nkeeps us safe\\nfrom harm.", 8112);
                player14 = player;
                player14.packetSender.showInterface(8016);
                return true;
            }
            if (objectId == (Integer)arrayList.get(3)) {
                Player player15 = player;
                player15.packetSender.sendInterfaceModel(8111, 200, 2950);
                player15 = player;
                player15.packetSender.sendInterfaceText("Saradomin is the\\ndelicate touch\\nthat brushes us\\nwith love.", 8112);
                player15 = player;
                player15.packetSender.showInterface(8016);
                return true;
            }
            if (objectId == (Integer)arrayList.get(4)) {
                Player player16 = player;
                player16.packetSender.sendInterfaceModel(8111, 250, 2947);
                player16 = player;
                player16.packetSender.sendInterfaceText("Saradomin is the\\nlight that shines\\nthroughout\\nour lives.", 8112);
                player16 = player;
                player16.packetSender.showInterface(8016);
                return true;
            }
            if (objectId == (Integer)arrayList.get(5)) {
                Player player17 = player;
                player17.packetSender.sendInterfaceModel(8111, 250, !player.ownsItem(2945) && value42 < 8 ? 2945 : 2944);
                player17 = player;
                player17.packetSender.sendInterfaceText("Saradomin is the\\nkey that unlocks\\nthe mysteries\\nof life.", 8112);
                player17 = player;
                player17.packetSender.showInterface(8016);
                return true;
            }
            if (objectId == (Integer)arrayList.get(6)) {
                Player player18 = player;
                player18.packetSender.sendInterfaceModel(8111, 250, 2946);
                player18 = player;
                player18.packetSender.sendInterfaceText("Saradomin is the\\nspark that lights\\nthe fire in\\nour hearts.", 8112);
                player18 = player;
                player18.packetSender.showInterface(8016);
                return true;
            }
        } else if (objectId == 3496 && value2 == 3423 && value32 == 9884 || objectId == 3498 && value2 == 3427 && value32 == 9885 || objectId == 3495 && value2 == 3428 && value32 == 9890 || objectId == 3497 && value2 == 3427 && value32 == 9894 || objectId == 3494 && value2 == 3423 && value32 == 9895 || objectId == 3499 && value2 == 3418 && value32 == 9894 || objectId == 3493 && value2 == 3416 && value32 == 9890) {
            Player player19 = player;
            player19.packetSender.sendGameMessage("A monument dedicated to the fallen.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value8 == 2) {
            if (value22 == 3463 && value52 == 3415 && value62 == 3489 && value72 == 6) {
                player.getDialogueManager().setDialogueNpcId(1048);
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Oh! You do not appear to be one of those Zamorakians", "who imprisoned me here! Who are you and why are", "you here?", 591);
                    return true;
                }
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("My name's " + player.getUsername() + ". King Roald sent me to find", "out what was going on at the temple. I take it you are", "Drezel?", 591);
                    return true;
                }
                if (value32 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("That is right! Oh, praise be to Saradomin! All is not yet", "lost! I feared that when those Zamorakians attacked this", "place and imprisoned", 591);
                    return true;
                }
                if (value32 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("me up here, Misthalin would be doomed! If they should", "manage to desecrate the holy river Salve we would be", "defenceless against Morytania!", 591);
                    return true;
                }
                if (value32 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How is a river a good defence then?", 591);
                    return true;
                }
                if (value32 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well, it is a long tale, and I am not sure we have time!", 591);
                    return true;
                }
                if (value32 == 8) {
                    player.getDialogueManager().showTwoOptions("Tell me anyway.", "You're right, we don't.");
                    return true;
                }
                if (value32 == 9) {
                    if (value42 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("Tell me anyway. I'd like to know the full facts before", "acting any further.", 591);
                        player.getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    if (value42 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("You're right, we don't. it doesn't matter anyway.", 591);
                        player.getDialogueManager().setNextDialogueStep(48);
                        return true;
                    }
                }
                if (value32 == 48) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, let's just say if we cannot undo whatever damage", "has been done here, the entire land is in grave peril!", 591);
                    player.getDialogueManager().setNextDialogueStep(32);
                    return true;
                }
                if (value32 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Ah, Saradomin has granted you wisdom I see. Well, the", "story of the river Salve and how it protects Misthalin", "is the story of this temple,", 591);
                    return true;
                }
                if (value32 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("and of the seven warrior priests who died here long ago,", "from whom I am descended. Once long ago Misthalin", "did not have the borders that", 591);
                    return true;
                }
                if (value32 == 12) {
                    player.getDialogueManager().showNpcThreeLineDialogue("it currently does. This entire area, as far West as", "Varrock itself was under the control of an evil god.", "There was frequent skirmishing", 591);
                    return true;
                }
                if (value32 == 13) {
                    player.getDialogueManager().showNpcFourLineDialogue("along the borders, as the brave heroes of Varrock", "fought to keep the evil creatures that now are trapped", "on the eastern side of the River Salve from over", "running", 591);
                    return true;
                }
                if (value32 == 14) {
                    player.getDialogueManager().showNpcFourLineDialogue("the human encampments, who worshipped Saradomin.", "Then one day, Saradomin himself appeared to one of", "our mighty heroes, whose name has been forgotten by", "history,", 591);
                    return true;
                }
                if (value32 == 15) {
                    player.getDialogueManager().showNpcThreeLineDialogue("and told him that should we be able to take the pass that", "this temple now stands in, Saradomin would use his", "power to bless this river, and make it", 591);
                    return true;
                }
                if (value32 == 16) {
                    player.getDialogueManager().showNpcThreeLineDialogue("impassable to all creatures with evil in their hearts. This", "unknown hero grouped together all of the mightiest", "fighters whose hearts were pure", 591);
                    return true;
                }
                if (value32 == 17) {
                    player.getDialogueManager().showNpcThreeLineDialogue("that he could find, and the seven of them rode here to", "make a final stand. The enemies swarmed across the", "Salve but they did not yield.", 591);
                    return true;
                }
                if (value32 == 18) {
                    player.getDialogueManager().showNpcThreeLineDialogue("For ten days and nights they fought, never sleeping,", "never eating, fuelled by their desire to make the world a", "better place for humans to live.", 591);
                    return true;
                }
                if (value32 == 19) {
                    player.getDialogueManager().showNpcThreeLineDialogue("On the eleventh day they were to be joined by", "reinforcements from a neighbouring encampment, but", "when those reinforcements arrived all they found", 591);
                    return true;
                }
                if (value32 == 20) {
                    player.getDialogueManager().showNpcThreeLineDialogue("were the bodies of these seven brave but unknown", "heroes, surrounded by the piles of the dead creatures of", "evil that had tried to defeat them.", 591);
                    return true;
                }
                if (value32 == 21) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The men were saddened at the loss of such pure and", "mighty warriors, yet their sacrifice had not been in", "vain; for the water of the Salve", 591);
                    return true;
                }
                if (value32 == 22) {
                    player.getDialogueManager().showNpcThreeLineDialogue("had indeed been filled with the power of Saradomin, and", "the evil creatures of Morytania were trapped beyond", "the river banks forever, by their own evil.", 591);
                    return true;
                }
                if (value32 == 23) {
                    player.getDialogueManager().showNpcThreeLineDialogue("In memory of this brave sacrifice my ancestors built", "this temple so that the land would always be free of the", "evil creatures", 591);
                    return true;
                }
                if (value32 == 24) {
                    player.getDialogueManager().showNpcThreeLineDialogue("who wish to destroy it, and laid the bodies of those brave", "warriors in tombs of honour below this temple with", "golden gifts on the tombs as marks of respect.", 591);
                    return true;
                }
                if (value32 == 25) {
                    player.getDialogueManager().showNpcThreeLineDialogue("They also built a statue on the river mouth so that all", "who might try and cross into Misthalin from Morytania", "would know that these lands are protected", 591);
                    return true;
                }
                if (value32 == 26) {
                    player.getDialogueManager().showNpcThreeLineDialogue("by the glory of Saradomin and that good will always", "defeat evil, no matter how the odds are stacked against", "them.", 591);
                    return true;
                }
                if (value32 == 27) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("Ok, I can see how the river protects the border, but I", "can't see how anything could affect that from this", "temple.", 591);
                    return true;
                }
                if (value32 == 28) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, as much as it saddens me to say so adventurer,", "Lord Saradomin's presence has not been felt on the", "land for many years now, and even", 591);
                    return true;
                }
                if (value32 == 29) {
                    player.getDialogueManager().showNpcThreeLineDialogue("though all true Saradominists know that he watches over", "us, his power upon the land is not as strong as it once", "was.", 591);
                    return true;
                }
                if (value32 == 30) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I fear that should those Zamorakians somehow pollute", "the Salve and desecrate his blessing, his power might not", "be able to stop", 591);
                    return true;
                }
                if (value32 == 31) {
                    player.getDialogueManager().showNpcTwoLineDialogue("the army of evil that lurks to the east, longing for the", "opportunity to invade and destroy us all!", 591);
                    return true;
                }
                if (value32 == 32) {
                    player.getDialogueManager().showNpcTwoLineDialogue("So what do you say adventurer? Will you aid me and", "all of Misthalin in foiling this Zamorakian plot?", 591);
                    return true;
                }
                if (value32 == 33) {
                    player.getDialogueManager().showTwoOptions("Yes.", "No.");
                    return true;
                }
                if (value32 == 34) {
                    if (value42 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("Yes, of course. Any threat to Misthalin must be", "neutralised immediately. So what can I do to help?", 591);
                        player.getDialogueManager().setNextDialogueStep(35);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value32 == 35) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, the immediate problem is that I am trapped in this", "cell. I know that the key to free me is nearby, for none", "of the Zamorakians", 591);
                    return true;
                }
                if (value32 == 36) {
                    player.getDialogueManager().showNpcThreeLineDialogue("who imprisoned me here were ever gone for long", "periods of time. Should you find the key however, as", "you may have noticed,", 591);
                    return true;
                }
                if (value32 == 37) {
                    player.getDialogueManager().showNpcThreeLineDialogue("there is a vampire in that coffin over there. I do not", "know how they managed to find it, but it is one of the", "ones that somehow", 591);
                    return true;
                }
                if (value32 == 38) {
                    player.getDialogueManager().showNpcThreeLineDialogue("survived the battle here all those years ago, and is by", "now quite, quite, mad. It has been trapped on this side", "of the river for centuries,", 591);
                    return true;
                }
                if (value32 == 39) {
                    player.getDialogueManager().showNpcThreeLineDialogue("and as those fiendish Zamorakians pointed out to me", "with delight, as a descendant of one of those who", "trapped it here, it will recognise", 591);
                    return true;
                }
                if (value32 == 40) {
                    player.getDialogueManager().showNpcThreeLineDialogue("the smell of my blood should I come anywhere near it.", "It will of course then wake up and kill me, very", "probably slowly and painfully.", 591);
                    return true;
                }
                if (value32 == 41) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Maybe I could kill it somehow then while it is asleep?", 591);
                    return true;
                }
                if (value32 == 42) {
                    player.getDialogueManager().showNpcThreeLineDialogue("No adventurer, I do not think it would be wise for you", "to wake it at all. As I say, it is little more than a wild", "animal, and must", 591);
                    return true;
                }
                if (value32 == 43) {
                    player.getDialogueManager().showNpcThreeLineDialogue("be extremely powerful to have survived until today. I", "suspect your best chance would be to incapacitate it", "somehow.", 591);
                    return true;
                }
                if (value32 == 44) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Okay, find the key to your cell, and do something about", "the vampire.", 591);
                    return true;
                }
                if (value32 == 45) {
                    player.getDialogueManager().showNpcThreeLineDialogue("When you have done both of those I will be able to", "inspect the damage which those Zamorakians have done", "to the purity of the Salve.", 591);
                    return true;
                }
                if (value32 == 46) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Depending on the severity of the damage, I may", "require further assistance from you in restoring its", "purity.", 591);
                    return true;
                }
                if (value32 == 47) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Okay, well first thing's first; let's get you out of here.", 591);
                    player.setQuestState(this.getQuestId(), 7);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value22 == 3489 && value52 == 3408 && value62 == 3489 || value22 == 3490 && value52 == 3408 && value62 == 3488) {
                if (value72 == 2) {
                    if (value32 == 1) {
                        player.getDialogueManager().showTwoLineStatement("You knock at the door...You hear a voice from inside. @dbl@Who are you", "@dbl@and what do you want?");
                        Player player2 = player;
                        player2.packetSender.sendSoundEffect(2466, 1, 0);
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Ummmm.....", 591);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showFourOptions("Roald sent me to check on Drezel.", "Hi, I just moved in next door...", "I hear this place is of historic interest", "The council sent me to check your pipes.");
                        return true;
                    }
                    if (value32 == 4) {
                        if (value42 == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Roald sent me to check on Drezel.", 591);
                            player.getDialogueManager().setNextDialogueStep(5);
                            return true;
                        }
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value32 == 5) {
                        player.getDialogueManager().showFiveLineStatement("@dbl@(Psst... Hey... Who's Roald? Who's Drezel?)@bla@(Uh... isn't Drezel that", "lude upstairs? Oh, wait, Roald's the King of Varrock right?)@dbl@(He is???", "@dbl@Aw man... Hey, you deal with this okay?) He's just coming! Wait a", "@dbl@second!@bla@Hello, my name is Drevil. @dbl@(Drezel!)@bla@ I mean Drezel. How can", "I help?");
                        return true;
                    }
                    if (value32 == 6) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("Well, as I say, the King sent me to make sure", "everything's okay with you.", 591);
                        return true;
                    }
                    if (value32 == 7) {
                        player.getDialogueManager().showOneLineStatement("And, uh, what would you do if everything wasn't okay with me?");
                        return true;
                    }
                    if (value32 == 8) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm not sure. Ask you what help you need I suppose.", 591);
                        return true;
                    }
                    if (value32 == 9) {
                        player.getDialogueManager().showTwoLineStatement("Ah, good, well, I don't think... @dbl@(Psst... hey... the dog!)@bla@ OH! Yes, of", "course! Will you do me a favor adventurer?");
                        return true;
                    }
                    if (value32 == 10) {
                        player.getDialogueManager().showTwoOptions("Sure.", "Nope.");
                        return true;
                    }
                    if (value32 == 11) {
                        if (value42 == 1) {
                            player.getDialogueManager().showPlayerOneLineDialogue("Sure. I'm helpful person!", 591);
                            player.getDialogueManager().setNextDialogueStep(12);
                            return true;
                        }
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (value32 == 12) {
                        player.getDialogueManager().showFourLineStatement("HAHAHAHA! Really? Thanks buddy! You see that mausoleum out", "there? There's a horrible big dog underneath it that I'd like you to", "kill for me! It's been really bugging me! Barking all the time and", "stuff! Please kill it for me buddy!");
                        player.setQuestState(this.getQuestId(), 3);
                        return true;
                    }
                }
                if (value72 == 3 && value32 == 13) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Okey-dokey, one dead dog coming up.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value72 == 4) {
                    if (value32 == 1) {
                        player.getDialogueManager().showTwoLineStatement("You knock at the door...You hear a voice from inside.@dbl@You again?", "@dbl@What do you want now?");
                        return true;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I killed that dog for you.", 591);
                        return true;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showTwoLineStatement("@dbl@HAHAHAHAHA! Really? Hey, that's great!@bla@ Yeah thanks a lot buddy!", "HAHAHAHAHAHA");
                        return true;
                    }
                    if (value32 == 4) {
                        player.getDialogueManager().showPlayerOneLineDialogue("What's so funny?", 591);
                        return true;
                    }
                    if (value32 == 5) {
                        player.getDialogueManager().showThreeLineStatement("@dbl@HAHAHAHA nothing buddy! We're just so grateful to you!", "@dbl@HAHAHA@bla@ Yeah, maybe you should go tell the King what a great job", "you did buddy! HAHAHA");
                        player.setQuestState(this.getQuestId(), 5);
                        player.getDialogueManager().finishDialogue();
                        return true;
                    }
                }
                if (value72 == 1 || value72 >= 6) {
                    if (value32 == 1) {
                        player.getDialogueManager().showOneLineStatement("You knock at the door...The door swings open as you knock.");
                        return true;
                    }
                    if (value32 == 2) {
                        Player player3 = player;
                        player3.packetSender.openDoubleDoorPair(3489, 3490, 3408, 3489, 3408, 3488, 0);
                        player3 = player;
                        player3.packetSender.queueRelativeMovementStep(player.getPosition().getX() > 3408 ? -1 : 1, 0, true);
                        player.getDialogueManager().finishDialogue();
                        player3 = player;
                        player3.packetSender.closeInterfaces();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        return npcId != 1047 || value2 == 3;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 1047 && value2 == 3) {
            player.setQuestState(this.getQuestId(), 4);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDeathDrop(Player npcId, int npcId2, Position position, int value2) {
        if (npcId2 == 1046 && value2 != 1 && !((Player)npcId).ownsItem(2944) && !((Player)npcId).ownsItem(2945) && value2 < 8) {
            GroundItem groundItem = new GroundItem(new ItemStack(2944, 1), (Entity)npcId, position);
            GroundItemManager.getInstance().spawn(groundItem);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 648) {
            if (value42 == 0 || value42 == 5) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Greetings, your majesty.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcOneLineDialogue("Well hello there. What do you want?", 591);
                    return true;
                }
            }
            if (value42 == 0) {
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I am looking for a quest!", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("A quest you say? Hmm, what an odd request to make", "of the king. It's funny you should mention it though, as", "there is something you can do for me.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Are you aware of the temple east of here? It stands on", "the river Salve and guards the entrance to the lands of", "Morytania?", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("No, I don't think I know it...", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Hmm, how strange that you don't. Well anyway, it has", "been some days since last I heard from Drezel, the", "priest who lives there.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Be a sport and go make sure that nothing untoward", "has happened to the silly old codger for me, would you?", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showTwoOptions("Sure.", "No, that sounds boring.");
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Sure. I don't have anything better to do right now.", 591);
                        player.getDialogueManager().setNextDialogueStep(11);
                        this.startQuest(player);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2 && value2 == 11) {
                player.getDialogueManager().showNpcTwoLineDialogue("Many thanks adventurer! I would have sent one of my", "squires but they wanted payment for it!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 5) {
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("You have news of Drezel for me?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("Yeah, I spoke to the guys at the temple and they said", "they were being bothered by that dog in the crypt, so I", "went and killed it for them. No problem.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("YOU DID WHAT???", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Are you mentally deficient??? That guard dog was", "protecting the route to Morytania! Without it we could", "be in severe peril of attack!", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Did I make a mistake?", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("YES YOU DID!!!!! You need to get there right now", "and find out what is happening! Before it is too late for", "us all!", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showPlayerOneLineDialogue("B-but Drezel TOLD me to...!", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showNpcThreeLineDialogue("No, you absolute cretin! Obviously some fiend has done", "something to Drezel and tricked your feeble intellect", "into helping them kill that guard dog!", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You get back there and do whatever is necessary to", "safeguard my kingdom from attack, or I will see you", "beheaded for high treason!", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Y-yes your Highness.", 591);
                    player.setQuestState(this.getQuestId(), 6);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 1048) {
            if (value42 >= 8 && value42 <= 10) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("The key fitted the lock! You're free to leave now!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well excellent work adventurer! Unfortunately, as you", "know, I cannot risk waking that vampire in the coffin.", 591);
                    if (value42 >= 9) {
                        if (player.getInventoryManager().containsItem(2953)) {
                            player.getDialogueManager().setNextDialogueStep(6);
                        }
                        if (player.getInventoryManager().containsItem(2954)) {
                            player.getDialogueManager().setNextDialogueStep(8);
                        }
                    }
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Do you have any ideas about dealing with the vampire?", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, the water of the Salve should still have enough", "power to work against the vampire despite what those", "Zamorakians might have done to it...", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Maybe you should try and get hold of some from", "somewhere?", 591);
                    player.setQuestState(this.getQuestId(), 10);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("I have some water from the Salve. It seems to have", "been desecrated though. Do you think you could bless", "it for me?", 591);
                    return true;
                }
                if (value2 == 7 && player.getInventoryManager().containsItem(2953)) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yes, good thinking adventurer! Give it to me, I will bless", "it!", 591);
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("The priest blesses the water for you.");
                    player.getInventoryManager().removeItem(new ItemStack(2953, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(2954, 1));
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I have some blessed water from the Salve in this bucket.", "Do you think it would help against that vampire?", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes! Great idea! If his coffin is closed in the blessed", "water he will be unable to leave it! Use it on his coffin,", "quickly!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 11) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerThreeLineDialogue("I poured the blessed water over the vampires coffin. I", "think that should trap him in there long enough for you", "to escape.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Excellent work adventurer! I am free at last! Let me", "ensure that evil vampire is trapped for good. I will meet", "you down by the monument.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Look for me down there, I need to assess what damage", "has been done to our holy barrier by those evil", "Zamorakians!", 591);
                    player.setQuestState(this.getQuestId(), 12);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 1049) {
            if (value42 == 12) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Ah, " + player.getUsername() + ". I see you finally made it down here.", "Things are worse than I feared. I'm not sure if I will", "be able to repair the damage.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Why, what's happened?", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("From what I can tell, after you killed the guard dog", "who protected the entrance to the monuments, those", "Zamorakians forced the door into the main chamber", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcThreeLineDialogue("and have used some kind of evil potion upon the well", "which leads to the source of the river Salve. As they", "have done this at the very mouth of the river", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("it will spread along the entire river, disrupting the", "blessing placed upon it and allowing the evil creatures of", "Morytania to invade at their leisure.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What can we do to prevent that?", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well, as you can see, I have placed a holy barrier on", "the entrance to this room from the South, but it is not", "very powerful and requires me to remain", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcThreeLineDialogue("here focussing upon it to keep it intact. Should an", "attack come, they would be able to breach this defence", "very quickly indeed. What we need to do is", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("find some kind of way of removing or counteracting the", "evil magic that has been put into the river source at the", "well, so that the river will flow pure once again.", 591);
                    return true;
                }
                if (value2 == 10) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Couldn't you bless the river to purify it? Like you did", "with the water I took from the well?", 591);
                    return true;
                }
                if (value2 == 11) {
                    player.getDialogueManager().showNpcThreeLineDialogue("No, that would not work, the power I have from", "Saradomin is not great enough to cleanse an entire", "river of this foul Zamorakian pollutant.", 591);
                    return true;
                }
                if (value2 == 12) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I have only one idea how we could possibly cleanse the", "river.", 591);
                    return true;
                }
                if (value2 == 13) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What's that?", 591);
                    return true;
                }
                if (value2 == 14) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I have heard rumours recently that Mages have found", "some secret ore that absorbs magic into it and allows", "them to create runes.", 591);
                    return true;
                }
                if (value2 == 15) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Should you be able to collect enough of this ore, it is", "possible it will soak up the evil potion that has been", "poured into the river, and purify it.", 591);
                    return true;
                }
                if (value2 == 16) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Kind of like a filter? Okay, I guess it's worth a try.", "How many should I get?", 591);
                    return true;
                }
                if (value2 == 17) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Well I have no knowledge of these ores other than", "speculation and gossip, but if the things I hear are true", "around fifty should be sufficient for the task.", 591);
                    player.setQuestState(this.getQuestId(), 13);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 13 && value42 < 63 && (player.getInventoryManager().containsItem(1436) || player.getInventoryManager().containsItem(7936))) {
                if (value2 == 1) {
                    if (value42 == 13) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I brought you some Rune Essence.", 591);
                    } else {
                        player.getDialogueManager().showPlayerOneLineDialogue("I brought you some more essences.", 591);
                    }
                    return true;
                }
                if (value2 == 2) {
                    if (value42 == 13) {
                        player.getDialogueManager().showNpcOneLineDialogue("Quickly, give it to me!", 591);
                    } else {
                        player.getDialogueManager().showNpcOneLineDialogue("Quickly, give them to me!", 591);
                    }
                    return true;
                }
                if (value2 == 3) {
                    npcId = player.getInventoryManager().getItemAmount(1436);
                    npcId = npcId < (value2 = 63 - value42) ? npcId : value2;
                    player.getInventoryManager().removeItem(new ItemStack(1436, npcId));
                    player.addQuestState(this.getQuestId(), npcId);
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("You give the priest your blank runes.");
                    if (63 - player.getQuestState(this.getQuestId()) != 0 && player.getInventoryManager().containsItem(7936)) {
                        int inventoryManager = player.getInventoryManager().getItemAmount(7936);
                        inventoryManager = inventoryManager < (value2 = 63 - player.getQuestState(this.getQuestId())) ? inventoryManager : value2;
                        player.getInventoryManager().removeItem(new ItemStack(7936, inventoryManager));
                        player.addQuestState(this.getQuestId(), inventoryManager);
                    }
                    if (63 - player.getQuestState(this.getQuestId()) == 0) {
                        player.getDialogueManager().showNpcThreeLineDialogue("Excellent! That should do it! I will bless these stones", "and place them within the well, and Misthalin should be", "protected once more!", 591);
                        player.getDialogueManager().setNextDialogueStep(2);
                    } else {
                        player.getDialogueManager().finishDialogue();
                        Player player4 = player;
                        player4.packetSender.closeInterfaces();
                    }
                    return true;
                }
            }
            if (value42 == 63) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Excellent! That should do it! I will bless these stones", "and place them within the well, and Misthalin should be", "protected once more!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Please take this dagger; it has been handled down within", "my family for generations and is filled with the power of", "Saradomin. You will find that", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("it has the power to prevent werewolves from adopting", "their wolf form in combat as long as you have it", "equipped.", 591);
                    return true;
                }
                if (value2 == 4) {
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}
