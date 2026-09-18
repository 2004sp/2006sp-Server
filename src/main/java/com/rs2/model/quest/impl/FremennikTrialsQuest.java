package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestArea;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.LyrePerformanceStartTask;
import com.rs2.model.quest.impl.ManniDrinkingContestPlayerDrinkTask;
import com.rs2.model.quest.impl.PeerTheSeerHouseGuardianTask;
import com.rs2.model.quest.impl.SwensenMazeObject4150ExitStepTask;
import com.rs2.model.quest.impl.SwensenMazeObject4151ExitStepTask;
import com.rs2.model.quest.impl.SwensenMazeObject4152ExitStepTask;
import com.rs2.model.quest.impl.SwensenMazeObject4153ExitStepTask;
import com.rs2.model.quest.impl.SwensenMazeObject4154ExitStepTask;
import com.rs2.model.quest.impl.SwensenMazeObject4155ExitStepTask;
import com.rs2.model.quest.impl.SwensenMazeRandomObjectExitStepTask;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class FremennikTrialsQuest
extends QuestScript {
    private static QuestArea[] swensenMazeAreas = new QuestArea[]{new QuestArea(1, new Position(2630, 10037, 0), new RectangularArea(2627, 10034, 2633, 10040, 0)), new QuestArea(2, new Position(2642, 10039, 0), new RectangularArea(2639, 10035, 2645, 10042, 0)), new QuestArea(3, new Position(2642, 10026, 0), new RectangularArea(2639, 10023, 2645, 10031, 0)), new QuestArea(4, new Position(2654, 10026, 0), new RectangularArea(2651, 10023, 2657, 10030, 0)), new QuestArea(5, new Position(2631, 10015, 0), new RectangularArea(2628, 10012, 2634, 10018, 0)), new QuestArea(6, new Position(2653, 10015, 0), new RectangularArea(2649, 10012, 2656, 10019, 0)), new QuestArea(7, new Position(2642, 10005, 0), new RectangularArea(2639, 10002, 2645, 10008, 0)), new QuestArea(8, new Position(2664, 10004, 0), new RectangularArea(2661, 10000, 2667, 10007, 0))};
    public static RectangularArea[] peerHouseGuardianAreas = new RectangularArea[]{new RectangularArea(2653, 10087, 2666, 10095, 2), new RectangularArea(2661, 10078, 2667, 10085, 2), new RectangularArea(2651, 10068, 2659, 10075, 2), new RectangularArea(2645, 10073, 2649, 10078, 2), new RectangularArea(2644, 10081, 2648, 10085, 2)};
    private static Position[] draugenTalismanTargetPositions = new Position[]{new Position(2697, 3561), new Position(2649, 3569), new Position(2678, 3605), new Position(2618, 3626), new Position(2666, 3568), new Position(2689, 3571)};
    private RectangularArea longhallDistractionArea = new RectangularArea(2655, 3665, 2662, 3677, 0);
    private RectangularArea lyrePerformanceArea = new RectangularArea(2655, 3682, 2662, 3685, 0);
    private String[] doorRiddleLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private List doorRiddleAnswers = Arrays.asList("TIME", "MIND", "WIND", "TREE", "LIFE");
    private List femaleFremennikNamePool = Arrays.asList("Baldur", "Baldar", "Baldor", "Balkal", "Balkar", "Balkur", "Ballah", "Ballor", "Baltin", "Bardor", "Barkar", "Barkir", "Barlah", "Barlak", "Barlim", "Barlor", "Barrak", "Bartor", "Barton", "Barvald", "Berkal", "Daldar", "Daldor", "Daldur", "Dalkal", "Dalkar", "Dalkir", "Dallah", "Dallak", "Daltin", "Dalton", "Daltor", "Danlek", "Dardar", "Darkal", "Darkar", "Darkir", "Darkur", "Darlah", "Darlim", "Darlor", "Dartin", "Dartor", "Dendor", "Dendur", "Denkal", "Denkar", "Denkir", "Denkur", "Denlah", "Denlak", "Denrak", "Denlim", "Denlor", "Dentor", "Dokdor", "Dokdur", "Dokkal", "Dokkir", "Dokkur", "Doklah", "Doklim", "Doklak", "Doklor", "Dokrak", "Doktin", "Dokton", "Doktor", "Jardar", "Jarlah", "Jarlak", "Jarlor", "Jarkal", "Jarkur", "Jarrak", "Jartor", "Jikarak", "Jikdar", "Jikdor", "Jikkal", "Jikkar", "Jikkir", "Jikkur", "Jiklah", "Jiklak", "Jiklim", "Jiklor", "Jiktin", "Jiktor", "Jikvald", "Jokkul", "Lardar", "Larkal", "Larkir", "Larkur", "Larlah", "Larlak", "Larlim", "Larlor", "Larton", "Lartor", "Larvald", "Rakdar", "Rakdur", "Rakkal", "Rakkar", "Rakkir", "Rakkur", "Raklak", "Raklim", "Raklor", "Rakrak", "Rakton", "Raktor", "Rakvald", "Raldar", "Raldur", "Raldor", "Ralkar", "Ralkur", "Rallah", "Rallak", "Rallim", "Rallor", "Raltin", "Ralton", "Raltor", "Ralvald", "Rildar", "Rildor", "Rilkal", "Rilkur", "Rilkir", "Rillah", "Rillak", "Rillim", "Rillor", "Riltin", "Rilton", "Riltor", "Rivgar", "Rilvald", "Sigdar", "Sigdor", "Sigdur", "Sigkal", "Sigkar", "Sigkir", "Sigkur", "Siglah", "Siglak", "Siglim", "Sigrak", "Sigvald", "Sigtin", "Sigton", "Sigtor", "Taldar", "Taldur", "Talkal", "Talkar", "Talkir", "Tallah", "Tallak", "Tallim", "Tallor", "Talrak", "Talton", "Tarlak", "Thordar", "Thordur", "Thorkur", "Thortin", "Thortor", "Thorkal", "Thorkar", "Thorkir", "Thorlah", "Thorlim", "Thorlor", "Thorrak", "Thorton", "Thortor", "Thorvald", "Tondar", "Tondur", "Tonkal", "Tonkar", "Tonkir", "Tonkur", "Tonlah", "Tonlim", "Tonlin", "Tonlor", "Tonrak", "Tontin", "Tonton", "Tontor", "Tonvald");
    private List maleFremennikNamePool = Arrays.asList("Baldar", "Baldor", "Baldur", "Balkal", "Balkar", "Balkir", "Balkur", "Ballah", "Ballak", "Ballim", "Ballor", "Balrak", "Baltin", "Balton", "Baltor", "Balvald", "Bardar", "Bardor", "Barkal", "Barlah", "Barlak", "Barlim", "Barlor", "Barkar", "Barkir", "Barkur", "Barrak", "Bartin", "Barton", "Bartor", "Barvald", "Dalkal", "Daldar", "Daldor", "Daldur", "Dalkir", "Dalkur", "Dallah", "Dallak", "Dallim", "Dallor", "Dalrak", "Daltin", "Daltor", "Dalvald", "Dardar", "Dardor", "Dardur", "Darlak", "Darlor", "Darlim", "Darkal", "Darlah", "Darkar", "Darkir", "Darkur", "Darrak", "Dartin", "Dartis", "Darton", "Dartor", "Darvald", "Dendar", "Dendor", "Dendur", "Denkar", "Denkal", "Denkir", "Denkur", "Denlah", "Denlak", "Denlim", "Denlor", "Denrak", "Dentin", "Denton", "Dentor", "Denvald", "Dentin", "Dokdar", "Dokdor", "Dokdur", "Dokkal", "Dokkar", "Dokkir", "Dokkur", "Doklah", "Doklak", "Doklim", "Doklor", "Dokrak", "Doktin", "Dokton", "Doktor", "Dokvald", "Jardar", "Jardor", "Jardur", "Jarkal", "Jarlor", "Jarkar", "Jarkir", "Jarkur", "Jarlah", "Jarlak", "Jarlim", "Jarlor", "Jarrak", "Jartin", "Jarton", "Jardor", "Jartor", "Jarvald", "Jikdar", "Jikdor", "Jikdur", "Jikkal", "Jikkar", "Jikkir", "Jikkur", "Jiklah", "Jiklak", "Jiklim", "Jiklor", "Jikrak", "Jiktin", "Jikton", "Jiktor", "Jikvald", "Lardar", "Larkal", "Lardor", "Larkar", "Larkir", "Larkur", "Larlah", "Larlak", "Larlim", "Larlor", "Larrak", "Larravak", "Lartin", "Larton", "Lartor", "Larvald", "Lardur", "Rakdar", "Rakdur", "Rakkal", "Rakkar", "Rakkir", "Rakkur", "Raklak", "Raklah", "Raklim", "Rakrak", "Raktin", "Rakton", "Rakdor", "Raklor", "Rakrak", "Raktor", "Rakvald", "Raldor", "Raldar", "Ralkal", "Ralkar", "Ralkir", "Ralkur", "Rallah", "Rallak", "Rallim", "Rallor", "Ralrak", "Raltin", "Ralton", "Raltor", "Ralvald", "Riklar", "Rildur", "Rilkar", "Rilkir", "Rilkur", "Rillim", "Rillah", "Rildor", "Rillor", "Rillak", "Rilrak", "Rilrar", "Riltin", "Rilton", "Riltor", "Rilvald", "Rildar", "Rilkal", "Rallor", "Sigkal", "Sigkar", "Sigkir", "Sigkur", "Siglah", "Siglim", "Siglor", "Sigrak", "Sigtin", "Sigton", "Sigtor", "Sigvald", "Siglak", "Sigdar", "Sigdor", "Sigdur", "Taldar", "Taldor", "Taldur", "Talkal", "Talkar", "Talkir", "Talkur", "Tallah", "Tallak", "Tallim", "Tallor", "Talrak", "Taltin", "Talton", "Taltor", "Talvald", "Thorak", "Thordar", "Thordor", "Thordur", "Thorkal", "Thorkir", "Thorkar", "Thorkur", "Thorlah", "Thorlak", "Thorlim", "Thorlor", "Thorrak", "Thortin", "Thorton", "Thortor", "Thorvald", "Tondar", "Tondor", "Tondur", "Tonkal", "Tonkar", "Tonkir", "Tonlah", "Tonlak", "Tonlim", "Tonlor", "Tonrak", "Tonton", "Tontor", "Tonvald", "Tontin", "Tonkur");
    String[][] lyrePerformanceLines = new String[][]{{"The thought of lots of questing,", "Leaves some people unfulfilled,", "But I have done my simple best, in", "Entering the Champions Guild."}, {"PLAYERNAME, is my name,", "I haven't much to say.", "But since I have to sing this song,", "I'll just go ahead and play."}};
    String[] lyreAudienceHeckleLines = new String[]{"Please, ye gods! Make it stop!", "YOU ARE A TERRIBLE BARD!"};
    int[] lyreAudienceNpcIds = new int[]{1274, 1275, 1276, 1277};

    public FremennikTrialsQuest(int value2) {
        super(40);
        super.setQuestPointReward(3);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Chieftain Brundt in", "the Fremennik Longhall, which is in the town of Rellekka to", "the north of Sinclair Mansion.", "To complete this quest I need:", "Level 40 Woodcutting", "Level 40 Crafting", "Level 25 Fletching", "I must also be able to defeat a level 69 enemy and must", "not be afraid of combat without any weapons or armour"};
        }
        if (value2 >= 2) {
            return new String[]{"I need the votes from following persons before I", "should go talk to Brundt the Chieftain again:", String.valueOf((value2 & GameUtil.bitFlag(3)) != 0 ? "@str@" : "") + "Manni the Reveller", String.valueOf((value2 & GameUtil.bitFlag(5)) != 0 ? "@str@" : "") + "Olaf the Bard", String.valueOf((value2 & GameUtil.bitFlag(7)) != 0 ? "@str@" : "") + "Sigmund the Merchant", String.valueOf((value2 & GameUtil.bitFlag(9)) != 0 ? "@str@" : "") + "Sigli the Huntsman", String.valueOf((value2 & GameUtil.bitFlag(11)) != 0 ? "@str@" : "") + "Swensen the Navigator", String.valueOf((value2 & GameUtil.bitFlag(13)) != 0 ? "@str@" : "") + "Peer the Seer", String.valueOf((value2 & GameUtil.bitFlag(15)) != 0 ? "@str@" : "") + "Thorvald the Warrior"};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "3 Quest Points", "2.8k XP in:", "Strength, Defence, Attack,", "Hitpoints, Fishing, Thieving,", "Agility, Crafting, Fletching,", "Woodcutting"};
        }
        return null;
    }

    private static boolean hasRequiredSkillLevels(Player player) {
        return player.getSkillManager().getBaseLevel(8) >= 40 && player.getSkillManager().getBaseLevel(12) >= 40 && player.getSkillManager().getBaseLevel(9) >= 25;
    }

    private boolean hasAllCouncilVotes(Player player) {
        int questState = player.getQuestState(this.getQuestId());
        return (questState & GameUtil.bitFlag(3)) != 0 && (questState & GameUtil.bitFlag(5)) != 0 && (questState & GameUtil.bitFlag(7)) != 0 && (questState & GameUtil.bitFlag(9)) != 0 && (questState & GameUtil.bitFlag(11)) != 0 && (questState & GameUtil.bitFlag(13)) != 0 && (questState & GameUtil.bitFlag(15)) != 0;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("3 Quest Points, 2.8k XP in:", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("Strength, Defence, Attack,", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("Hitpoints, Fishing, Thieving,", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("Agility, Crafting, Fletching,", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("Woodcutting", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(2, 2800.0);
        player.getSkillManager().addQuestExperience(1, 2800.0);
        player.getSkillManager().addQuestExperience(0, 2800.0);
        player.getSkillManager().addQuestExperience(3, 2800.0);
        player.getSkillManager().addQuestExperience(10, 2800.0);
        player.getSkillManager().addQuestExperience(17, 2800.0);
        player.getSkillManager().addQuestExperience(16, 2800.0);
        player.getSkillManager().addQuestExperience(12, 2800.0);
        player.getSkillManager().addQuestExperience(9, 2800.0);
        player.getSkillManager().addQuestExperience(8, 2800.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 3748);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (value32 < 2) {
            return false;
        }
        if (objectId == 3727 && value2 == 4176) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You fill the bucket from the tap.");
            player.getInventoryManager().replaceItem(new ItemStack(3727, 1), new ItemStack(3722, 1));
            return true;
        }
        if (objectId >= 3722 && objectId <= 3726 && value2 == 4175) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You empty the bucket down the drain.");
            player.getInventoryManager().replaceItem(new ItemStack(objectId, 1), new ItemStack(3727, 1));
            return true;
        }
        if (objectId == 3732 && value2 == 4176) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("You fill the jug from the tap.");
            player.getInventoryManager().replaceItem(new ItemStack(3732, 1), new ItemStack(3729, 1));
            return true;
        }
        if (objectId == 3723 && value2 == 4170) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You take a strange looking vase out of the chest.");
            player.getInventoryManager().replaceItem(new ItemStack(3723, 1), new ItemStack(3734, 1));
            return true;
        }
        if (objectId == 3742 && value2 == 4172) {
            player.getDialogueManager().showTwoLineStatement("As you cook the herring on the stove, the colouring on it peels off", "separately as a red sticky goop...");
            player.getInventoryManager().replaceItem(new ItemStack(3742, 1), new ItemStack(347, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(3746, 1));
            return true;
        }
        if (objectId == 3743 && value2 == 4179) {
            Player player6 = player;
            player6.packetSender.sendGameMessage("You put the red disk into the empty hole on the mural.");
            player6 = player;
            player6.packetSender.sendGameMessage("It is a perfect fit!");
            player.getInventoryManager().removeItem(new ItemStack(3743, 1));
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(23)) == 0) {
                int questId = this.getQuestId();
                player.questProgressFlags[questId] = player.questProgressFlags[questId] + GameUtil.bitFlag(23);
            } else if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(22)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(23)) != 0) {
                player6 = player;
                player6.packetSender.sendGameMessage("The center of the mural falls out!");
                player.getInventoryManager().addOrDropItem(new ItemStack(3737, 1));
            }
            return true;
        }
        if (objectId == 3734 && value2 == 4176) {
            Player player7 = player;
            player7.packetSender.sendGameMessage("You fill the strange looking vase with water.");
            player.getInventoryManager().replaceItem(new ItemStack(3734, 1), new ItemStack(3735, 1));
            return true;
        }
        if (objectId == 3740 && value2 == 4169) {
            Player player8 = player;
            player8.packetSender.sendGameMessage("The water expands as it freezes, and shatters the vase.");
            player8 = player;
            player8.packetSender.sendGameMessage("You are left with a key encased in ice.");
            player.getInventoryManager().replaceItem(new ItemStack(3740, 1), new ItemStack(3741, 1));
            return true;
        }
        if (objectId == 3741 && value2 == 4172) {
            Player player9 = player;
            player9.packetSender.sendGameMessage("The heat of the range melts the ice around the key.");
            player.getInventoryManager().replaceItem(new ItemStack(3741, 1), new ItemStack(3745, 1));
            return true;
        }
        if (objectId == 3714 && value2 == 4162 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(2)) == 0) {
            Player player10 = player;
            player10.packetSender.sendGameMessage("You put the lit strange object into the pipe.");
            player.getInventoryManager().removeItem(new ItemStack(3714, 1));
            int questId2 = this.getQuestId();
            player.questProgressFlags[questId2] = player.questProgressFlags[questId2] + GameUtil.bitFlag(2);
            player.getDialogueManager().showPlayerThreeLineDialogue("That is going to make a really loud bang when it goes", "off! It would be a perfect distraction to help me cheat in", "the drinking contest!", 591);
            player.getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId == 3695 && value2 == 4149 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(4)) == 0) {
            Player player11 = player;
            player11.packetSender.sendGameMessage("You put your pet rock into the cauldron.");
            player.getInventoryManager().removeItem(new ItemStack(3695, 1));
            int questId3 = this.getQuestId();
            player.questProgressFlags[questId3] = player.questProgressFlags[questId3] + GameUtil.bitFlag(4);
            DialogueManager.continueDialogue(player, 1270, 100, 0);
            return true;
        }
        if (objectId == 1965 && value2 == 4149 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(5)) == 0) {
            Player player12 = player;
            player12.packetSender.sendGameMessage("You put a cabbage into the cauldron.");
            player.getInventoryManager().removeItem(new ItemStack(1965, 1));
            int questId4 = this.getQuestId();
            player.questProgressFlags[questId4] = player.questProgressFlags[questId4] + GameUtil.bitFlag(5);
            DialogueManager.continueDialogue(player, 1270, 100, 0);
            return true;
        }
        if (objectId == 1942 && value2 == 4149 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(6)) == 0) {
            Player player13 = player;
            player13.packetSender.sendGameMessage("You put a potato into the cauldron.");
            player.getInventoryManager().removeItem(new ItemStack(1942, 1));
            int questId5 = this.getQuestId();
            player.questProgressFlags[questId5] = player.questProgressFlags[questId5] + GameUtil.bitFlag(6);
            DialogueManager.continueDialogue(player, 1270, 100, 0);
            return true;
        }
        if (objectId == 1957 && value2 == 4149 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(7)) == 0) {
            Player player14 = player;
            player14.packetSender.sendGameMessage("You put an onion into the cauldron.");
            player.getInventoryManager().removeItem(new ItemStack(1957, 1));
            int questId6 = this.getQuestId();
            player.questProgressFlags[questId6] = player.questProgressFlags[questId6] + GameUtil.bitFlag(7);
            DialogueManager.continueDialogue(player, 1270, 100, 0);
            return true;
        }
        if (objectId == 3693 && value2 == 2644) {
            Player player15 = player;
            player15.packetSender.sendGameMessage("You spin the fleece into some golden string.");
            player.getUpdateState().setAnimation(896);
            player.getInventoryManager().replaceItem(new ItemStack(3693, 1), new ItemStack(3694, 1));
            return true;
        }
        if ((objectId == 383 || objectId == 389 || objectId == 395) && value2 == 4141 && player.getInventoryManager().containsItemAmount(3689, 1)) {
            player.getInventoryManager().removeItem(new ItemStack(3689, 1));
            player.getInventoryManager().replaceItem(new ItemStack(objectId, 1), new ItemStack(3690, 1));
            Npc npc = new Npc(1273);
            GameplayHelper.spawnOwnedNpcAtPosition(player, new Position(2626, 3596, 0), npc, false, false);
            DialogueManager.continueDialogue(player, 1273, 100, 0);
            Player player16 = player;
            player16.packetSender.sendGameMessage("Fossegrimen has enchanted your lyre so that you may play it.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 4181 && value52 == 2632 && value62 == 3660 && value8 == 1) {
            if (player.getInventoryManager().containsItemAmount(3743, 1)) {
                return false;
            }
            if (value32 == 1) {
                player.getDialogueManager().showTwoLineStatement("You notice there is something unusual about the left eye of this", "unicorn head...");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showTwoLineStatement("It is not an eye at all, but some kind of red coloured disk. You take", "it from the head.");
                player.getInventoryManager().addOrDropItem(new ItemStack(3743, 1));
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (value22 == 4182 && value52 == 2634 && value62 == 3660 && value8 == 1) {
            if (player.getInventoryManager().containsItemAmount(3744, 1)) {
                return false;
            }
            if (value32 == 1) {
                player.getDialogueManager().showTwoLineStatement("You notice there is something unusual about the right eye of this", "bulls' head...");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showTwoLineStatement("It is not an eye at all, but some kind of disk made of wood. You", "take it from the head.");
                player.getInventoryManager().addOrDropItem(new ItemStack(3744, 1));
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (value22 == 4165 && value52 == 2631 && value62 == 3667 && value8 == 1) {
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(21)) != 0 || value72 <= 1 || (player.getQuestState(this.getQuestId()) & GameUtil.bitFlag(12)) == 0) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("This door is locked tightly shut.");
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value32 == 1) {
                player.getDialogueManager().showTwoLineStatement("There is a combination lock on this door. Above the lock you can see", "that there is a metal plaque with a riddle on it.");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showThreeOptionsWithTitle("What would you like to do?", "Read the riddle", "Solve the riddle", "Forget it");
                return true;
            }
            if (value32 == 3) {
                if (value42 == 1) {
                    ArrayList arrayList = new ArrayList(this.doorRiddleAnswers);
                    Collections.shuffle(arrayList, new Random(player.questRandomSeed));
                    FremennikTrialsQuest.showDoorRiddleText(player, (String)arrayList.get(0), 1);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value42 == 2) {
                    player.getDialogueManager().finishDialogue();
                    player.fremennikDoorRiddleFirstLetterIndex = 0;
                    player.fremennikDoorRiddleSecondLetterIndex = 0;
                    player.fremennikDoorRiddleThirdLetterIndex = 0;
                    player.fremennikDoorRiddleFourthLetterIndex = 0;
                    Player player3 = player;
                    player3.packetSender.sendInterfaceText(this.doorRiddleLetters[0], 13884);
                    player3 = player;
                    player3.packetSender.sendInterfaceText(this.doorRiddleLetters[0], 13885);
                    player3 = player;
                    player3.packetSender.sendInterfaceText(this.doorRiddleLetters[0], 13886);
                    player3 = player;
                    player3.packetSender.sendInterfaceText(this.doorRiddleLetters[0], 13887);
                    player3 = player;
                    player3.packetSender.showInterface(671);
                    return true;
                }
                if (value42 == 3) {
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value32 == 4) {
                ArrayList arrayList = new ArrayList(this.doorRiddleAnswers);
                Collections.shuffle(arrayList, new Random(player.questRandomSeed));
                FremennikTrialsQuest.showDoorRiddleText(player, (String)arrayList.get(0), 2);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        return false;
    }

    private static void showDoorRiddleText(Player player, String text2, int value2) {
        if (text2.equals("TIME")) {
            if (value2 == 1) {
                player.getDialogueManager().showFourLineStatement("My first is in water, and also in tea.", "My second in fish, but not in the sea.", "My third in mountains, but not underground.", "My last is in strike, but not in pound.");
                return;
            }
            player.getDialogueManager().showThreeLineStatement("My whole crushes mountains, drains rivers, and destroys civilisations.", "All that live fear my passing.", "What am I?");
            return;
        }
        if (text2.equals("MIND")) {
            if (value2 == 1) {
                player.getDialogueManager().showFourLineStatement("My first is in mage, but not in wizard.", "My second in goblin, and also in lizard.", "My third is in night, but not in the day.", "My last is in fields, but not in the hay.");
                return;
            }
            player.getDialogueManager().showTwoLineStatement("My whole is the most powerful tool you will ever possess.", "What am I?");
            return;
        }
        if (text2.equals("WIND")) {
            if (value2 == 1) {
                player.getDialogueManager().showFourLineStatement("My first is in wizard, but not in a mage.", "My second in jail, but not in a cage.", "My third is in anger, but not in a rage.", "My last is in a drawing, but not on a page.");
                return;
            }
            player.getDialogueManager().showTwoLineStatement("My whole helps to make bread, let birds fly and boats sail.", "What am I?");
            return;
        }
        if (text2.equals("TREE")) {
            if (value2 == 1) {
                player.getDialogueManager().showFourLineStatement("My first is in tar, but not in a swamp.", "My second in fire, but not in a camp.", "My third is in eagle, but never in air.", "My last is in hate, but also in care.");
                return;
            }
            player.getDialogueManager().showTwoLineStatement("My whole wears more rings, the older I get.", "What am I?");
            return;
        }
        if (text2.equals("LIFE")) {
            if (value2 == 1) {
                player.getDialogueManager().showFourLineStatement("My first is in the well, but not at sea.", "My second in 'I', but not in 'me'.", "My third is in flies, but insects not found.", "My last is in earth, but not in the ground.");
                return;
            }
            player.getDialogueManager().showTwoLineStatement("My whole when stolen from you, causes you death.", "What am I?");
        }
    }

    @Override
    public final boolean handleFirstObjectAction(Player objectId, int objectId2, int value6, int value32, int value42) {
        if (objectId2 == 4171 && (value6 == 2634 || value6 == 2635) && value32 == 3665) {
            if (((Player)objectId).getInventoryManager().containsItemAmount(3742, 1)) {
                return false;
            }
            Player player = objectId;
            player.packetSender.sendGameMessage("You search the bookcase...");
            player = objectId;
            player.packetSender.sendGameMessage("Hidden behind some old books, you find a red herring.");
            ((Player)objectId).getInventoryManager().addOrDropItem(new ItemStack(3742, 1));
            return true;
        }
        if (objectId2 == 4148 && value6 == 2667 && value32 == 3683) {
            if (((Entity)objectId).getPosition().getX() < 2667) {
                Player player = objectId;
                player.packetSender.openSingleDoor(objectId2, value6, value32, 0);
                player = objectId;
                player.packetSender.queueRelativeMovementStep(1, 0, true);
            } else if (((Player)objectId).getInventoryManager().containsItemAmount(3690, 1)) {
                Player player = objectId;
                player.packetSender.openSingleDoor(objectId2, value6, value32, 0);
                player = objectId;
                player.packetSender.queueRelativeMovementStep(-1, 0, true);
                DialogueManager.continueDialogue((Player)objectId, 1278, 100, 0);
            } else {
                DialogueManager.continueDialogue((Player)objectId, 1278, 101, 0);
            }
            return true;
        }
        if (objectId2 == 4166 && value6 == 2636 && value32 == 3667) {
            if (((Entity)objectId).getPosition().getY() < 3667 && ((Player)objectId).getInventoryManager().containsItemAmount(3745, 1)) {
                Player player = objectId;
                player.packetSender.openSingleDoor(objectId2, value6, value32, 0);
                player = objectId;
                player.packetSender.queueRelativeMovementStep(0, 1, true);
                player = objectId;
                ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
                int length = itemStackArray.length;
                int index = 0;
                while (index < length) {
                    ItemStack itemStack = itemStackArray[index];
                    if (itemStack != null && itemStack.getDefinition().isUntradeable()) {
                        player.getInventoryManager().removeItem(itemStack);
                    }
                    ++index;
                }
                player = objectId;
                player.packetSender.sendGameMessage("You unlock the door with your key.");
                DialogueManager.continueDialogue((Player)objectId, 1288, 100, 0);
            } else {
                Player player = objectId;
                player.packetSender.sendGameMessage("This door is locked tightly shut.");
            }
            return true;
        }
        if (objectId2 == 4165 && value6 == 2631 && value32 == 3667) {
            if ((((Player)objectId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(21)) != 0 && value42 >= 2 && (value42 & GameUtil.bitFlag(13)) == 0) {
                boolean enabled;
                Player player;
                handleFirstObjectActionControlExit1: {
                    ItemStack itemStack;
                    player = objectId;
                    ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
                    int length2 = itemStackArray.length;
                    int index2 = 0;
                    while (index2 < length2) {
                        itemStack = itemStackArray[index2];
                        if (itemStack != null) {
                            enabled = true;
                            break handleFirstObjectActionControlExit1;
                        }
                        ++index2;
                    }
                    itemStackArray = player.getEquipmentManager().getContainer().getItems();
                    length2 = itemStackArray.length;
                    index2 = 0;
                    while (index2 < length2) {
                        itemStack = itemStackArray[index2];
                        if (itemStack != null) {
                            enabled = true;
                            break handleFirstObjectActionControlExit1;
                        }
                        ++index2;
                    }
                    enabled = false;
                }
                if (!enabled) {
                    player = objectId;
                    player.packetSender.openSingleDoor(objectId2, value6, value32, 0);
                    player = objectId;
                    player.packetSender.queueRelativeMovementStep(0, ((Entity)objectId).getPosition().getY() < 3667 ? 1 : -1, true);
                } else {
                    player = objectId;
                    player.packetSender.sendGameMessage("You can't bring any items inside!");
                }
                return true;
            }
            return false;
        }
        if (objectId2 == 4177 && value6 == 2629 && value32 == 3660) {
            ObjectManager.getInstance().removeDynamicObjectAt(2629, 3660, 2, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(4178, 2629, 3660, 2, 3, 10, 4177, 999999999), true);
            return true;
        }
        if (objectId2 == 4167 && value6 == 2635 && value32 == 3660) {
            ObjectManager.getInstance().removeDynamicObjectAt(2635, 3660, 2, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(4168, 2635, 3660, 2, 0, 10, 4167, 999999999), true);
            return true;
        }
        if (objectId2 == 4167 && value6 == 2638 && value32 == 3662) {
            ObjectManager.getInstance().removeDynamicObjectAt(2638, 3662, 2, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(4168, 2638, 3662, 2, 3, 10, 4167, 999999999), true);
            return true;
        }
        if (objectId2 == 4188 && value6 == 2672 && value32 == 10099) {
            AttackStyleDefinition.startDelayedObjectMove((Player)objectId, new Position(2666, 3694, 0));
            return true;
        }
        if (objectId2 == 4158 && value6 == 2644 && value32 == 3657) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(10)) != 0 && (value42 & GameUtil.bitFlag(11)) == 0) {
                AttackStyleDefinition.startDelayedObjectMove((Player)objectId, new Position(2631, 10004, 0));
                return true;
            }
        }
        if (objectId2 == 100 && value6 == 2650 && value32 == 3661) {
            ((Player)objectId).getDialogueManager().showTwoLineStatement("You try to open the trapdoor but it won't budge! It looks like the", "trapdoor can only be opened from the other side.");
            Player player = objectId;
            player.packetSender.sendGameMessage("You try to open the trapdoor but it won't budge!");
            player = objectId;
            player.packetSender.sendGameMessage("It looks like the trapdoor can only be opened from the other side.");
            ((Player)objectId).getDialogueManager().finishDialogue();
            return true;
        }
        if (objectId2 == 4160 && value6 == 2665 && value32 == 10037) {
            ((Entity)objectId).getUpdateState().setAnimation(828);
            ((Player)objectId).moveTo(new Position(2649, 3661, 0));
            DialogueManager.continueDialogue((Player)objectId, 1283, 100, 0);
            return true;
        }
        if (objectId2 == 4159 && value6 == 2631 && value32 == 10005) {
            AttackStyleDefinition.startDelayedObjectMove((Player)objectId, new Position(2644, 3658, 0));
            return true;
        }
        if (objectId2 == 4161) {
            AttackStyleDefinition.startDelayedObjectMove((Player)objectId, new Position(2647, 3658, 0));
            return true;
        }
        if (objectId2 == 4150 && value6 == 2631 && value32 == 10002) {
            ((Player)objectId).moveTo(new Position(2642, 10018, 0));
            SwensenMazeObject4150ExitStepTask swensenMazeObject4150ExitStepTask = new SwensenMazeObject4150ExitStepTask(this, 1, (Player)objectId);
            World.getTaskScheduler().schedule(swensenMazeObject4150ExitStepTask);
            return true;
        }
        if (objectId2 == 4151 && value6 == 2639 && value32 == 10015) {
            ((Player)objectId).moveTo(new Position(2650, 10004, 0));
            SwensenMazeObject4151ExitStepTask swensenMazeObject4151ExitStepTask = new SwensenMazeObject4151ExitStepTask(this, 1, (Player)objectId);
            World.getTaskScheduler().schedule(swensenMazeObject4151ExitStepTask);
            return true;
        }
        if (objectId2 == 4152 && value6 == 2656 && value32 == 10004) {
            ((Player)objectId).moveTo(new Position(2668, 10015, 0));
            SwensenMazeObject4152ExitStepTask swensenMazeObject4152ExitStepTask = new SwensenMazeObject4152ExitStepTask(this, 1, (Player)objectId);
            World.getTaskScheduler().schedule(swensenMazeObject4152ExitStepTask);
            return true;
        }
        if (objectId2 == 4153 && value6 == 2665 && value32 == 10018) {
            ((Player)objectId).moveTo(new Position(2630, 10029, 0));
            SwensenMazeObject4153ExitStepTask swensenMazeObject4153ExitStepTask = new SwensenMazeObject4153ExitStepTask(this, 1, (Player)objectId);
            World.getTaskScheduler().schedule(swensenMazeObject4153ExitStepTask);
            return true;
        }
        if (objectId2 == 4154 && value6 == 2630 && value32 == 10023) {
            ((Player)objectId).moveTo(new Position(2653, 10034, 0));
            SwensenMazeObject4154ExitStepTask swensenMazeObject4154ExitStepTask = new SwensenMazeObject4154ExitStepTask(this, 1, (Player)objectId);
            World.getTaskScheduler().schedule(swensenMazeObject4154ExitStepTask);
            return true;
        }
        if (objectId2 == 4155 && value6 == 2656 && value32 == 10037) {
            ((Player)objectId).moveTo(new Position(2669, 10026, 0));
            SwensenMazeObject4155ExitStepTask swensenMazeObject4155ExitStepTask = new SwensenMazeObject4155ExitStepTask(this, 1, (Player)objectId);
            World.getTaskScheduler().schedule(swensenMazeObject4155ExitStepTask);
            return true;
        }
        if (objectId2 == 4156 && value6 == 2666 && value32 == 10029) {
            ((Player)objectId).moveTo(new Position(2665, 10038, 0));
            return true;
        }
        if (objectId2 == 4157) {
            Object value2;
            objectId2 = 0;
            value42 = 0;
            while (value42 < swensenMazeAreas.length) {
                QuestArea questArea = swensenMazeAreas[value42];
                if (questArea.getAreaBounds().contains(((Entity)objectId).getPosition())) {
                    objectId2 = questArea.getAreaId();
                    break;
                }
                ++value42;
            }
            value42 = 0;
            int value5 = 3;
            if (objectId2 != 0) {
                value42 = 0;
                value5 = 0;
                value2 = swensenMazeAreas[objectId2 - 1];
                if (value6 > ((QuestArea)value2).getTargetPosition().getX()) {
                    value42 = 3;
                }
                if (value6 < ((QuestArea)value2).getTargetPosition().getX()) {
                    value42 = -3;
                }
                if (value32 > ((QuestArea)value2).getTargetPosition().getY()) {
                    value5 = 3;
                }
                if (value32 < ((QuestArea)value2).getTargetPosition().getY()) {
                    value5 = -3;
                }
            }
            value2 = new ArrayList();
            value6 = 0;
            while (value6 < swensenMazeAreas.length) {
                QuestArea questArea = swensenMazeAreas[value6];
                if (questArea.getAreaId() != objectId2) {
                    ((ArrayList)value2).add(value6);
                }
                ++value6;
            }
            value6 = GameUtil.randomInt(((ArrayList)value2).size());
            QuestArea questArea = swensenMazeAreas[(Integer)((ArrayList)value2).get(value6)];
            ((Player)objectId).moveTo(new Position(questArea.getTargetPosition().getX() + value42, questArea.getTargetPosition().getY() + value5, 0));
            objectId2 = value5;
            value6 = value42;
            World.getTaskScheduler().schedule(new SwensenMazeRandomObjectExitStepTask(this, 1, objectId2, (Player)objectId, value6));
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 4187 && value2 == 2667 && value32 == 3694) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(14)) != 0 && (value42 & GameUtil.bitFlag(15)) == 0) {
                if (player.hasRestrictedCombatEquipment()) {
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You can't bring combat equipment here!");
                    return true;
                }
                AttackStyleDefinition.startDelayedObjectMove(player, new Position(2671, 10098, 2));
                Player player3 = player;
                player3.packetSender.sendGameMessage("Explore this battleground and find your foe...");
                player.activeRecurringEffectId = 0;
                return true;
            }
        }
        if (objectId == 4178 && value2 == 2629 && value32 == 3660) {
            if (player.getInventoryManager().containsItemAmount(3727, 1)) {
                return false;
            }
            Player player4 = player;
            player4.packetSender.sendGameMessage("You search the cupboard...");
            player4 = player;
            player4.packetSender.sendGameMessage("You find a bucket with a number five painted on it.");
            player.getInventoryManager().addOrDropItem(new ItemStack(3727, 1));
            return true;
        }
        if (objectId == 4168 && value2 == 2638 && value32 == 3662) {
            if (player.getInventoryManager().containsItemAmount(3732, 1)) {
                return false;
            }
            Player player5 = player;
            player5.packetSender.sendGameMessage("You search the chest...");
            player5 = player;
            player5.packetSender.sendGameMessage("You find a jug with a number three painted on it.");
            player.getInventoryManager().addOrDropItem(new ItemStack(3732, 1));
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleMovementStep(Player player, int value2) {
        if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(24)) != 0 || value2 < 2) {
            return false;
        }
        if (player.activeRecurringEffectId == 1290) {
            return true;
        }
        value2 = 0;
        while (value2 < peerHouseGuardianAreas.length) {
            if (peerHouseGuardianAreas[value2].containsExclusive(player.getPosition())) {
                PeerTheSeerHouseGuardianTask peerTheSeerHouseGuardianTask = new PeerTheSeerHouseGuardianTask(this, 10, player);
                World.getTaskScheduler().schedule(peerTheSeerHouseGuardianTask);
                player.activeRecurringEffectId = 1290;
                return true;
            }
            ++value2;
        }
        return false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        Npc npc;
        if (entity2.isNpc() && entity.isPlayer()) {
            Player player = (Player)entity;
            npc = (Npc)entity2;
            if (npc.getNpcId() == 1290) {
                CombatManager.stopCombat(npc);
                CombatManager.stopCombat(player);
                DialogueManager.continueDialogue(player, 1290, 100, 0);
                return true;
            }
            if (npc.getNpcId() == 1291) {
                CombatManager.stopCombat(npc);
                CombatManager.stopCombat(player);
                DialogueManager.continueDialogue(player, 1291, 100, 0);
                return true;
            }
            if (npc.getNpcId() == 1292) {
                CombatManager.stopCombat(npc);
                CombatManager.stopCombat(player);
                DialogueManager.continueDialogue(player, 1292, 100, 0);
                return true;
            }
            if (npc.getNpcId() == 1293) {
                if ((player.getQuestState(this.getQuestId()) & GameUtil.bitFlag(15)) == 0) {
                    player.addQuestState(this.getQuestId(), GameUtil.bitFlag(15));
                    entity = player;
                    ((Player)entity).packetSender.sendGameMessage("Congratulations! You have passed the warrior's trial!");
                }
                npc.setDead(true);
                CombatManager.handleDeath(npc);
                return true;
            }
            if (npc.getNpcId() == 1279) {
                entity = player;
                ((Player)entity).packetSender.sendStillGraphicToNearbyPlayers(86, npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane(), 0);
                CombatManager.stopCombat(npc);
                CombatManager.stopCombat(player);
                CombatManager.finishDeath(npc, player, false);
                if (player.getInventoryManager().containsItemAmount(3696, 1)) {
                    entity = player;
                    ((Player)entity).packetSender.sendGameMessage("You absorb the Draugen's essence into your talisman.");
                    player.getInventoryManager().removeItem(new ItemStack(3696, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(3697, 1));
                }
                return true;
            }
        }
        if (entity2.isPlayer() && entity.isNpc()) {
            Player player = (Player)entity2;
            npc = (Npc)entity;
            if (npc.getNpcId() == 1293) {
                CombatManager.stopCombat(npc);
                CombatManager.stopCombat(player);
                entity = player;
                ((Player)entity).packetSender.sendGameMessage("Oh dear you are...");
                player.setCurrentHitpoints(player.getMaxHitpoints());
                player.moveTo(new Position(2667, 3692, 1));
                if ((player.getQuestState(this.getQuestId()) & GameUtil.bitFlag(15)) == 0) {
                    player.addQuestState(this.getQuestId(), GameUtil.bitFlag(15));
                    DialogueManager.continueDialogue(player, 1289, 100, 0);
                    entity = player;
                    ((Player)entity).packetSender.sendGameMessage("Congratulations! You have passed the warrior's trial!");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleButtonClick(Player player, int buttonId, int value6) {
        if (buttonId == 13888 || buttonId == 13889) {
            int value2 = player.fremennikDoorRiddleFirstLetterIndex;
            value2 = buttonId == 13889 ? (value2 == this.doorRiddleLetters.length - 1 ? 0 : ++value2) : (value2 == 0 ? this.doorRiddleLetters.length - 1 : --value2);
            player.fremennikDoorRiddleFirstLetterIndex = value2;
            Player player2 = player;
            player2.packetSender.sendInterfaceText(this.doorRiddleLetters[value2], 13884);
            return true;
        }
        if (buttonId == 13890 || buttonId == 13891) {
            int value3 = player.fremennikDoorRiddleSecondLetterIndex;
            value3 = buttonId == 13891 ? (value3 == this.doorRiddleLetters.length - 1 ? 0 : ++value3) : (value3 == 0 ? this.doorRiddleLetters.length - 1 : --value3);
            player.fremennikDoorRiddleSecondLetterIndex = value3;
            Player player3 = player;
            player3.packetSender.sendInterfaceText(this.doorRiddleLetters[value3], 13885);
            return true;
        }
        if (buttonId == 13892 || buttonId == 13893) {
            int value4 = player.fremennikDoorRiddleThirdLetterIndex;
            value4 = buttonId == 13893 ? (value4 == this.doorRiddleLetters.length - 1 ? 0 : ++value4) : (value4 == 0 ? this.doorRiddleLetters.length - 1 : --value4);
            player.fremennikDoorRiddleThirdLetterIndex = value4;
            Player player4 = player;
            player4.packetSender.sendInterfaceText(this.doorRiddleLetters[value4], 13886);
            return true;
        }
        if (buttonId == 13894 || buttonId == 13895) {
            int value5 = player.fremennikDoorRiddleFourthLetterIndex;
            value5 = buttonId == 13895 ? (value5 == this.doorRiddleLetters.length - 1 ? 0 : ++value5) : (value5 == 0 ? this.doorRiddleLetters.length - 1 : --value5);
            player.fremennikDoorRiddleFourthLetterIndex = value5;
            Player player5 = player;
            player5.packetSender.sendInterfaceText(this.doorRiddleLetters[value5], 13887);
            return true;
        }
        if (buttonId == 13898) {
            ArrayList arrayList = new ArrayList(this.doorRiddleAnswers);
            Collections.shuffle(arrayList, new Random(player.questRandomSeed));
            String text = (String)arrayList.get(0);
            String text2 = String.valueOf(this.doorRiddleLetters[player.fremennikDoorRiddleFirstLetterIndex]) + this.doorRiddleLetters[player.fremennikDoorRiddleSecondLetterIndex] + this.doorRiddleLetters[player.fremennikDoorRiddleThirdLetterIndex] + this.doorRiddleLetters[player.fremennikDoorRiddleFourthLetterIndex];
            if (text.equals(text2)) {
                Player player6 = player;
                player6.packetSender.closeInterfaces();
                player6 = player;
                player6.packetSender.sendGameMessage("You have solved the riddle!");
                if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(21)) == 0 && value6 >= 2) {
                    int questId = this.getQuestId();
                    player.questProgressFlags[questId] = player.questProgressFlags[questId] + GameUtil.bitFlag(21);
                }
            } else {
                Player player7 = player;
                player7.packetSender.sendGameMessage("Your answer is not correct!");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value4, int value32) {
        if (itemId == 3214) {
            if (value4 == 3690 && this.lyrePerformanceArea.contains(player.getPosition())) {
                player.getUpdateState().setFacePosition(new Position(player.getPosition().getX(), player.getPosition().getY() - 1));
                Player player2 = player;
                player2.packetSender.sendGameMessage("You withdraw your lyre.");
                player.setActionLocked(true);
                int value2 = GameUtil.randomInt(2);
                value4 = GameUtil.randomInt(2);
                value32 = GameUtil.randomInt(4);
                LyrePerformanceStartTask lyrePerformanceStartTask = new LyrePerformanceStartTask(this, 2, value2, player, value32, value4);
                World.getTaskScheduler().schedule(lyrePerformanceStartTask);
                return true;
            }
            if (value4 == 3696) {
                if (GameplayHelper.hasActiveTemporaryNpc(player, 1279)) {
                    return false;
                }
                if (player.temporaryActionValue != 1279) {
                    player.temporaryActionValue = 1279;
                    player.sharedActionValue = GameUtil.randomInt(draugenTalismanTargetPositions.length);
                } else {
                    if (GameUtil.isWithinDistance(player.getPosition(), draugenTalismanTargetPositions[player.sharedActionValue], 1)) {
                        Player player3 = player;
                        player3.packetSender.sendGameMessage("The Draugen is here! Beware!");
                        if (!GameplayHelper.hasActiveTemporaryNpc(player, 1279)) {
                            Npc npc = new Npc(1279);
                            GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, npc, true, false);
                        }
                        return true;
                    }
                    if (GameUtil.randomInt(100) == 0) {
                        player.sharedActionValue = GameUtil.randomInt(draugenTalismanTargetPositions.length);
                    }
                }
                value4 = draugenTalismanTargetPositions[player.sharedActionValue].getX();
                value32 = draugenTalismanTargetPositions[player.sharedActionValue].getY();
                itemId = player.getPosition().getX() - value4;
                value4 = player.getPosition().getY() - value32;
                String text = "";
                if (value4 != 0) {
                    text = String.valueOf(text) + (value4 < 0 ? "north" : "south");
                }
                if (value4 != 0 && itemId != 0) {
                    text = String.valueOf(text) + "-";
                }
                if (itemId != 0) {
                    text = String.valueOf(text) + (itemId < 0 ? "east" : "west");
                }
                if (itemId != 0 || value4 != 0) {
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("The talisman guides you " + text + ".");
                }
                return true;
            }
            if (value4 == 3697) {
                Player player5 = player;
                player5.packetSender.sendGameMessage("You have already fought against the Draugen.");
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        if (value32 < 2) {
            return false;
        }
        if (itemId == 3735 && value2 == 3737 || value2 == 3735 && itemId == 3737) {
            player.packetSender.sendGameMessage("You screw the lid on tightly.");
            player.getInventoryManager().replaceItem(new ItemStack(3735, 1), new ItemStack(3740, 1));
            player.getInventoryManager().removeItem(new ItemStack(3737, 1));
            return true;
        }
        if (itemId == 3744 && value2 == 3746 || value2 == 3744 && itemId == 3746) {
            player.packetSender.sendGameMessage("You coat the wooden coin with the sticky red goop.");
            player.getInventoryManager().replaceItem(new ItemStack(3744, 1), new ItemStack(3743, 1));
            player.getInventoryManager().removeItem(new ItemStack(3746, 1));
            if ((player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(22)) == 0) {
                int questId = this.getQuestId();
                player.questProgressFlags[questId] = player.questProgressFlags[questId] + GameUtil.bitFlag(22);
            }
            return true;
        }
        if (itemId == 3729 && value2 == 3727 || value2 == 3729 && itemId == 3727) {
            player.packetSender.sendGameMessage("You empty the jug into the bucket.");
            player.getInventoryManager().replaceItem(new ItemStack(3729, 1), new ItemStack(3732, 1));
            player.getInventoryManager().replaceItem(new ItemStack(3727, 1), new ItemStack(3724, 1));
            return true;
        }
        if (itemId == 3729 && value2 == 3724 || value2 == 3729 && itemId == 3724) {
            player.packetSender.sendGameMessage("You fill the bucket to the brim.");
            player.getInventoryManager().replaceItem(new ItemStack(3729, 1), new ItemStack(3731, 1));
            player.getInventoryManager().replaceItem(new ItemStack(3724, 1), new ItemStack(3722, 1));
            return true;
        }
        if (itemId == 3731 && value2 == 3727 || value2 == 3731 && itemId == 3727) {
            player.packetSender.sendGameMessage("You empty the jug into the bucket.");
            player.getInventoryManager().replaceItem(new ItemStack(3731, 1), new ItemStack(3732, 1));
            player.getInventoryManager().replaceItem(new ItemStack(3727, 1), new ItemStack(3726, 1));
            return true;
        }
        if (itemId == 3729 && value2 == 3726 || value2 == 3729 && itemId == 3726) {
            player.packetSender.sendGameMessage("You empty the jug into the bucket.");
            player.getInventoryManager().replaceItem(new ItemStack(3729, 1), new ItemStack(3732, 1));
            player.getInventoryManager().replaceItem(new ItemStack(3726, 1), new ItemStack(3723, 1));
            return true;
        }
        if (itemId == 590 && value2 == 3713 || value2 == 590 && itemId == 3713) {
            player.packetSender.sendGameMessage("You light the string of the strange object. It starts to hiss slightly.");
            player.getInventoryManager().replaceItem(new ItemStack(3713, 1), new ItemStack(3714, 1));
            return true;
        }
        if (itemId == 946 && value2 == 3692 || value2 == 946 && itemId == 3692) {
            player.packetSender.sendGameMessage("You craft an unstrung lyre out of the branch.");
            player.getUpdateState().setAnimation(1248);
            player.getInventoryManager().replaceItem(new ItemStack(3692, 1), new ItemStack(3688, 1));
            return true;
        }
        if (itemId == 3801 && value2 == 3712 || value2 == 3801 && itemId == 3712) {
            player.packetSender.sendGameMessage("You empty the keg and refill it with low alcohol beer.");
            player.getInventoryManager().removeItem(new ItemStack(3801, 1));
            player.getInventoryManager().removeItem(new ItemStack(3712, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(3711, 1));
            if (this.longhallDistractionArea.contains(player.getPosition())) {
                Npc[] npcArray = Npc.findActiveInArea(this.longhallDistractionArea);
                value2 = npcArray.length;
                int index = 0;
                while (index < value2) {
                    Npc npc = npcArray[index];
                    npc.getUpdateState().setForcedText("What was THAT?");
                    ++index;
                }
            }
            return true;
        }
        if (itemId == 3694 && value2 == 3688 || value2 == 3694 && itemId == 3688) {
            player.packetSender.sendGameMessage("You attach the golden strings to the lyre.");
            player.getInventoryManager().removeItem(new ItemStack(3688, 1));
            player.getInventoryManager().removeItem(new ItemStack(3694, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(3689, 1));
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnNpc(Player player, int npcId, int value2, int value32) {
        if (value32 < 2) {
            return false;
        }
        if (!((value32 & GameUtil.bitFlag(3)) != 0 || player.ownsItem(3713) || npcId != 1287 || value2 != 1917 && value2 != 3803)) {
            player.getInventoryManager().removeItem(new ItemStack(value2, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(3713, 1));
            DialogueManager.continueDialogue(player, 1287, 100, 0);
            return true;
        }
        return false;
    }

    private boolean hasPreparedStoneSoup(Player player) {
        return (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(4)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(5)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(6)) != 0 && (player.questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(7)) != 0;
    }

    private static boolean hasAnyMerchantTrialItem(Player player) {
        int value = 3698;
        while (value < 3711) {
            if (player.ownsItem(value)) {
                return true;
            }
            ++value;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player npcId, int npcId2, int value2, int value32, int value42) {
        if (npcId2 == 1294) {
            if (value42 == 0) {
                if (!FremennikTrialsQuest.hasRequiredSkillLevels((Player)npcId)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).pendingGameMode = 0;
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Greetings outerlander!", 591);
                    return true;
                }
                if (value2 == 2) {
                    if (((Player)npcId).pendingGameMode == 0) {
                        ((Player)npcId).getDialogueManager().showFourOptions("What is this place?", "Why will no-one talk to me?", "Do you have any quests?", "Nice hat!");
                    }
                    if (((Player)npcId).pendingGameMode == 1) {
                        ((Player)npcId).getDialogueManager().showFiveOptions("Do you have any quests?", "Why will no-one talk to me?", "Why do you call me outerlander?", "I seek an army to destroy Asgarnia!", "Who are the Fremennik?");
                    }
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        if (((Player)npcId).pendingGameMode == 0) {
                            ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What is this place?", 591);
                            ((Player)npcId).getDialogueManager().setNextDialogueStep(4);
                        }
                        if (((Player)npcId).pendingGameMode == 1) {
                            ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Do you have any quests?", 591);
                            ((Player)npcId).getDialogueManager().setNextDialogueStep(7);
                        }
                        return true;
                    }
                    if (value32 == 3 && ((Player)npcId).pendingGameMode == 0) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Do you have any quests?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("This place? Why, this is Rellekka! Homeland of all", "Fremennik! I do not recognise your face outerlander;", "Where do you come from?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("That's not important...", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).pendingGameMode = 1;
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hmmm... I will not press the issue then outerlander.", "How may my tribe and I help you?", 591);
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Quests, you say outerlander? Well, I would not call it a", "quest as such, but if you are brave of heart and strong", "of body, perhaps...", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("No, you would not be interested. Forget I said", "anything, outerlander.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes, I am interested.", "No, I'm not interested.");
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Actually, I would be very interested to hear what you", "have to offer.", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You would? These are unusual sentiments to hear from", "an outerlander! My suggestion was going to be that if", "you crave adventure and battle,", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("and your heart sings for glory, then perhaps you would", "be interested in joining our clan, and becoming a", "Fremennik yourself?", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What would that involve exactly?", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, there are two ways to become a member of our", "clan and call yourself a Fremennik: be born a", "Fremennik, or be voted in by our council of elders.", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("Well, I think I've missed the first way, but how can I", "get the council of elders to let me join your", "clan?", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, that I cannot answer myself. You will need to", "speak to each of them and see what they require of you", "as proof of your dedication.", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("There are twelve council members around this village;", "you will need to gain a majority vote of at least seven", "councillors in your favour.", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("So what you say? Give me the word, and I will tell all", "of my tribe of your intentions, be they yea or nay.", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("I want to become a Fremennik!", "I don't want to become a Fremennik.");
                    return true;
                }
                if (value2 == 20) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I think I would enjoy the challenge of becoming an", "honorary Fremennik. Where and how do I start?", 591);
                        this.startQuest((Player)npcId);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(21);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (value42 == 2) {
                if (value2 == 21) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("As I say outerlander, you must find and speak to the", "twelve members of the council of elders, and see what", "tasks they might set you.", 591);
                    return true;
                }
                if (value2 == 22) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("If you can gain the support of seven of the twelve, then", "you will be accepted as one of us without question.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(11)) != 0) {
                if (value2 <= 2 && !((Player)npcId).getInventoryManager().containsItemAmount(3701, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I got Sigli's hunting map for you.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3701, 1), new ItemStack(3708, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Excellent work outerlander! And so quickly, too! Here,", "you may take my financial report promising reduced", "sales taxes on all goods.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(10)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(11)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "guarantee of a reduction on sales taxes, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("A reduction on sales taxes? Why, I am the only one in", "the Fremennik who may authorise such a thing. What", "does an outerlander want with that?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Actually, it's not for me. I need to get it as part of my", "trials.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Hmmm. Interesting. Your trials seem to be very", "different to those I took as a young lad. Well, I am not", "adverse in principle to giving a slight tax break to our", "shops.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("There will of course be a shortfall in the tribe's income,", "that will need to be made up for elsewhere, however.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("How about this. For many years Sigli has been the only", "one in tribe who knows the locations of the best", "hunting grounds where game is easiest to catch.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("If you can persuade him to let the entire tribe know", "these hunting grounds, then we can increase", "productivity within the tribe, and any shortfall caused", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("by lowering sales taxes will be covered. I think this is a", "more than fair arrangement to make, don't you?", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yeah, that sounds very fair.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Speak to Sigli then, and you may have my promise to", "reduce our sales taxes. And best of luck with the rest", "of your trials.", 591);
                    int questId = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId] = ((Player)npcId).questProgressFlags[questId] + GameUtil.bitFlag(11);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (this.hasAllCouncilVotes((Player)npcId)) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Greetings again outerlander! How goes your attempts", "to gain votes with the council of elders?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I have seven members of the council prepared to vote", "in my favour now!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I know outerlander, for I have been closely monitoring", "your progress so far!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Then let us put the formality aside, and let me", "personally welcome you into the Fremennik! May you", "bring us honour!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ArrayList gender = new ArrayList(((Player)npcId).getGender() == 0 ? this.maleFremennikNamePool : this.femaleFremennikNamePool);
                    Collections.shuffle(gender, new Random(((Player)npcId).questRandomSeed));
                    String fremennikName = (String)gender.get(0);
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("From this day onward, you are outerlander no more!", "In honour of your acceptance into the Fremennik You", "gain a new name to be known as; You will now be", "called " + fremennikName, 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    this.awardCompletionRewards((Player)npcId);
                    return true;
                }
            }
        }
        if (npcId2 == 1286) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(18)) != 0) {
                if (value2 <= 3 && !((Player)npcId).getInventoryManager().containsItemAmount(3707, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hey. I got your cocktail for you.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("...It is true! The legendary cocktail! I have waited for", "this day ever since I first started drinking!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3707, 1), new ItemStack(3706, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Here outerlander, you may take my token. I will", "happily give up my place at the longhalls table of", "champions just for a taste of this exquisite beverage!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("It's just a drink...", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("No, it is an artform. A drink such as this should be", "appreciated, and admired. It is like a fine painting, or a", "tasteful sculpture.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("If what I hear is true, then all other drinks become like", "unpalatable water in comparison to this!", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I guess you're happy with the trade then!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(17)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(18)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "token to allow a seat at the champions table, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("As a matter of fact, I do. I have one right here. I", "earnt my place here at the longhall for surviving over", "5000 battles and raiding parties.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Due to my contribution to the tribe, I am now", "permitted to spend my days here in the longhall", "listening to the epic tales of the bard, and drinking beer.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Cool. That sounds pretty sweet! So I guess you don't", "want to give it away?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I think it sounds better than it actually is outerlander.", "I miss my glory days of combat on the battlefield. And", "to tell you the truth, the beer here isn't great, and the", "bards' music is lousy.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I would happily give up my token if it were not for the", "one thing that keeps me here. Our barkeep is one of", "the best in the world, and has worked in taverns across", "the land.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("When she was younger, she experimented a lot with her", "drinks, and invented a cocktail so alcoholic and tasty", "that it has become something of a legend to all who", "enjoy a drink.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Unfortunately, she decided that cocktails were not a", "suitable drink for Fremennik warriors, and vowed to", "never again make it.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I have been here every day since she returned, hoping", "that someday she might change her mind and I might", "try this legendary cocktail for myself. Alas, it has never", "come to pass...", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("If you can persuade her to make me her legendary", "cocktail, I will be happy to never let another drop of", "alcohol pass my lips, and will give you my champions", "token.", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("That's all?", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("That's all.", 591);
                    int questId2 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId2] = ((Player)npcId).questProgressFlags[questId2] + GameUtil.bitFlag(18);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(2)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there!", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hello outerlander. I overheard your conversation with", "Brundt just now. You wish to become a member of the", "Fremennik?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("That's right! Why, are you on the council?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Do not let my drink-soused appearance fool you, I", "earnt my place on the council many years past. I am", "always glad to see new blood enter our tribe, and will", "happily vote for you.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Great!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("...Providing you can pass a little test for me. As a", "Fremennik, you will need to show cunning, stamina,", "fortitude, and an iron constitution. I know of only one", "way to test all of these.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("And what's that?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Why, a drinking contest!", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("The task is simple enough! You versus me, a stiff drink", "each, last man standing wins the trial. So what say you?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 11) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("A drinking contest? Easy. Set them up, and I'll knock", "them back.", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("When you are ready to begin, go and pick up a keg", "from that table over there, and come back here.", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("We start when you have your keg of beer with you,", "and finish when one of us can drink no more and", "yields.", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(2));
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(2)) != 0 && (value42 & GameUtil.bitFlag(3)) == 0) {
                if (value2 == 1 && (((Player)npcId).getInventoryManager().containsItemAmount(3801, 1) || ((Player)npcId).getInventoryManager().containsItemAmount(3711, 1))) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Ah, I see you have your keg of beer. Are we ready to", "drink against each other?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes, let's start this drinking contest!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("As you wish outerlander; I will drink first, then you will", "drink.", 591);
                    return true;
                }
                if (value2 == 5 && (((Player)npcId).getInventoryManager().containsItemAmount(3801, 1) || ((Player)npcId).getInventoryManager().containsItemAmount(3711, 1))) {
                    ((Player)npcId).getPacketSender().closeInterfaces();
                    ((Player)npcId).setActionLocked(true);
                    Npc npc = Npc.findByDefinitionId(1286);
                    npc.getUpdateState().setAnimation(1327);
                    if (((Player)npcId).getInventoryManager().containsItemAmount(3711, 1)) {
                        ((Player)npcId).pendingGameMode = 3711;
                    }
                    ((Player)npcId).getPacketSender().sendGameMessage("The Fremennik drinks his tankard first. He staggers a little bit.");
                    int questId3 = this.getQuestId();
                    World.getTaskScheduler().schedule(new ManniDrinkingContestPlayerDrinkTask(this, 6, (Player)npcId, questId3));
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I guessh I win then ouddaladder! (hic) Niche try", "anyway!", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Come back if'n you fanshy a rematch! (hic) Jusht let me", "have a coffee firsht...", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(3)) != 0) {
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Wassha? Guh? You drank that whole keg! But it dinna", "affect you at all! I conshede! You can probably", "outdrink me!", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I jusht can't (hic) believe it! Thatsh shome mighty fine", "drinking legs you got! Anyone who can drink like", "THAT getsh my vote atta consh.. counsh... gets my", "vote!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 820) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(3)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Howdy! You seem like someone with discerning taste!", "Howsabout you try my brand new range of alcohol?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Didn't you used to sell poison?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("That I did, indeed! Peter Potter's Patented", "Multipurpose poison! A miracle of modern apothecarys!", "My exclusive concoction has been tested on...", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Uh, yeah, I've already heard the sales pitch.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Sorry stranger, old habits die hard I guess.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So you don't sell poison any more?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, I would, but I ran out of stock. Business wasn't", "helped with that stuff that happened up at the Sinclair", "Mansion much either, I'll be honest.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("So, being the man of enterprise that I am I decided to", "branch out a little bit!", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Into alcohol?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Absolutely! The basic premise between alcohol and poison", "is pretty much the same, after all! The difference is that", "my alcohol has a unique property others do not!", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("And what is that?", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("The salesman takes a deep breath.");
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ever been too drunk to find your own home? Ever", "wished that you could party away all night long, and", "still wake up fresh as a daisy the next morning?", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Thanks to the miracles of modern magic we have come", "up with just the solution you need! Peter Potter's", "Patented Party Potions!", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("It looks like beer! It tastes just like beer! It smells", "just like beer! But... it's not beer!", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Our mages have mused for many moments to bring", "you this miracle of modern magic! It has all the great", "tastes you'd expect, but contains absolutely no alcohol!", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("That's right! You can drink Peter Potters Patented", "Party Potion as much as you want, and suffer", "absolutely no ill effects whatsoever!", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("The clean fresh taste you know you can trust, from", "the people who brought you; Peter Potters Patented", "multipurpose poison, Peter Potters peculiar paint packs", 591);
                    return true;
                }
                if (value2 == 20) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("and Peter Potters paralysing panic pins. Available now", "from all good stockists! Ask your local bartender now,", "and experience the taste revolution of the century!", 591);
                    return true;
                }
                if (value2 == 21) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("He seems to have finished for the time being.");
                    return true;
                }
                if (value2 == 22) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So... when you say 'all good stockists'...", 591);
                    return true;
                }
                if (value2 == 23) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes?", 591);
                    return true;
                }
                if (value2 == 24) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("How many inns actually sell this stuff?", 591);
                    return true;
                }
                if (value2 == 25) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well... nobody has actually bought any yet. Everyone I", "try and sell it to always asks me what exactly the point", "of beer that has absolutely no effect on you is.", 591);
                    return true;
                }
                if (value2 == 26) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So what is the point?", 591);
                    return true;
                }
                if (value2 == 27) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Well... Um... Er... Hmmm. You, er, don't get drunk.", 591);
                    return true;
                }
                if (value2 == 28) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I see...", 591);
                    return true;
                }
                if (value2 == 29) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Aw man... You don't want any now do you? I've really", "tried to push this product, but I just don't think the", "world is ready for beer that doesn't get you drunk.", 591);
                    return true;
                }
                if (value2 == 30) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I'm a man ahead of my time I tell you! It's not that", "my products are bad, it's that they're too good for the", "market!", 591);
                    return true;
                }
                if (value2 == 31) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Actually, I would like some. How much do you want for", "it?", 591);
                    return true;
                }
                if (value2 == 32) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Y-you would??? Um, okay! I knew I still had the old", "salesmans skills going on!", 591);
                    return true;
                }
                if (value2 == 33) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I'll sell you a keg of it for only 250 gold pieces! So", "what do you say?", 591);
                    return true;
                }
                if (value2 == 34) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 35) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes please!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(36);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 36) {
                    if (((Player)npcId).getInventoryManager().containsItemAmount(995, 250)) {
                        ((Player)npcId).getInventoryManager().removeItem(new ItemStack(995, 250));
                        ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3712, 1));
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId2 == 1287) {
            if (value2 == 100) {
                ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ta very much, like. That'll hit the spot nicely. Here,", "you can have this. I picked it up as a souvenir on me", "last hols.", 591);
                return true;
            }
            if (value2 == 101) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What is it?", 591);
                return true;
            }
            if (value2 == 102) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I dunno rightly, but if you use a tinderbox on it, it", "don't half make a loud noise!", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 1269) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(9)) != 0) {
                if (value2 <= 4 && !((Player)npcId).getInventoryManager().containsItemAmount(3700, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Hello Olaf. Do you have a beautiful love song written", "for me?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("That depends outerlander... Do you have some new", "boots for me? My feet get so tired roaming the land...", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("As a matter of fact - I do!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3700, 1), new ItemStack(3699, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Oh! Superb! Those are great! They're just what I was", "looking for! Here, take this song with my compliments!", "It is one of my finest works yet!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(8)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(9)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "love ballad, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, as official Fremennik bard, it falls within my remit", "to compose all music for the tribe. I am fully versed in", "all the various types of romantic music.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Great! Can you write me one then?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well... normally I would be thrilled at the chance to", "show my skill as a poet in composing a seductively", "romantic ballad...", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("let me guess; here comes the 'but'.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("...but unfortunately I cannot concentrate fully upon", "my work recently.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Why is that then?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("It is these old worn out shoes of mine... As a bard I", "am expected to wander the lands, singing of the glorious", "battles of our warriors.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("If you can find me a pair of sturdy boots to replace", "these old worn out ones of mine, I will be happy to", "spend the time on composing you a romantic ballad.", 591);
                    int questId4 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId4] = ((Player)npcId).questProgressFlags[questId4] + GameUtil.bitFlag(9);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(4)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hello? Yes? You want something, outerlander?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Are you a member of the council?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Why, indeed I am, outerlander! My talents as an", "exemplary musician made it difficult for them not to", "accept me! Why do you wish to know this?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("Well, I ask because I am currently doing the", "Fremennik trials so as to join your clan. I need seven", "of the twelve council of elders to vote for me.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ahhh... and you wish to earn my vote? I will gladly", "accept you as a Fremennik should you be able to prove", "yourself to have a little musical ability!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So how would I do that?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Why, by playing in our longhall! All you need to do is", "impress the revellers there with a few verses of an epic", "of your own creation!", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("So what say you outerlander? Are you up for the", "challenge?", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 10) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Sure! This certainly sounds pretty easy to accomplish -", "I'll have your vote in no time!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("That is great news outerlander! We always need more", "music lovers here!", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(4));
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(4)) != 0 && (value42 & GameUtil.bitFlag(5)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So how would I go about writing this epic?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, first of all you are going to need an instrument.", "Like all true bards you are going to have to make this", "yourself.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("How do I make an instrument?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, it is a long and drawn-out process. Just east of", "this village there is an unusually musical tree that can", "be used to make very high quality instruments.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Cut a piece from it, and then carve it into a special", "shape that will allow you to string it. Using a knife as", "you would craft any other wooden object would be best", "for this.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Then what do I need to do?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Next you will need to string your lyre. There is a troll", "to the South-east who has some golden wool. I would", "not recommend using anything else to string your lyre", "with.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Anything else?", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Well, when you have crafted your lyre you will need", "the blessing of the Fossegrimen to tune your lyre to", "perfection before you even consider a public", "performance.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Who or what is Fossegrimen?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Fossegrimen is a lake spirit that lives just a little way", "South-west of this village. Make her an offering of fish,", "and you will then be ready for your performance.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Make sure you give her suitable offering however. If", "the offering is found to be unworthy, then you may", "find yourself unable to play your lyre with any skill at", "all!", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So what would be a worthy offering?", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("A raw shark, manta ray, or sea turtle should be", "sufficient as an offering.", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Okay, what else do I need to do?", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("When you have crafted your lyre and been blessed by", "Fossegrimen, then you will finally be ready to make", "your performance to the revellers at the longhall.", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Head past the bouncers and onto the stage, and then", "begin to play. If all goes well, you should find the music", "spring to your mind and sing your own epic on the", "spot.", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I will observe both you and the audience, and if you", "show enough talent, I will happily vote in your favour at", "the council of elders.", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Is that clear enough, outerlander? Would you like me", "to repeat anything?", 591);
                    return true;
                }
                if (value2 == 20) {
                    ((Player)npcId).getDialogueManager().showFourOptions("Remind me about crafting a lyre", "Remind me about Fossegrimen", "Remind me about playing on the stage", "I don't need a reminder");
                    return true;
                }
                if (value2 == 21) {
                    if (value32 == 4) {
                        ((Player)npcId).getDialogueManager().finishDialogue();
                        return false;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(20);
                    return true;
                }
                if (value2 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Wow! That was awesome! You are one of the greatest", "bards I have ever had the pleasure of watching", "performing!", 591);
                    return true;
                }
                if (value2 == 101) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("You have certainly earned my vote! I hope we can", "duet together soon!", 591);
                    return true;
                }
                if (value2 == 102) {
                    if ((((Player)npcId).getQuestState(this.getQuestId()) & GameUtil.bitFlag(5)) == 0) {
                        ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(5));
                    }
                    ((Player)npcId).getPacketSender().sendGameMessage("Congratulations! You have completed the Bard's Trial!");
                    ((Player)npcId).getPacketSender().sendGameMessage("As you finished playing you felt Fossegrimen's power leave you...");
                    ((Player)npcId).getPacketSender().sendGameMessage("You feel the musical ability from the Fossegrimen leave you...");
                    if (((Player)npcId).getInventoryManager().containsItemAmount(3690, 1)) {
                        ((Player)npcId).getInventoryManager().removeItem(new ItemStack(3690, 1));
                        ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3689, 1));
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId2 == 1270) {
            if (value42 < 2) {
                return false;
            }
            if ((((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Bah! Puny humans always try steal Lallis' golden", "apples! You go away now!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("Actually, I'm not after your golden apples. I was", "wondering if I could have some golden wool; I need it", "to string a lyre.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Ha! You not fool me human! Me am smart! Other", "trolls so jealous of how brainy I are, they kick me out", "of camp and make me live down here in cave! But me", "have last funny!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Me find golden apples on tree and me build wall to stop", "anyone who not Lalli eating lovely golden apples! Did", "me not tell you I are smart?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Yes, yes, you are incredibly clever. Now please can I", "have some golden wool?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hum, me think you not really think I are clever. Me", "think you is trying trick Lalli. Me not like you as much", "as other human. He give me present. I give him wool.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Other human?", "No, honest, you're REALLY clever.", "Can I give you a present?");
                    return true;
                }
                if (value2 == 9) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Other human? You mean someone else has been there", "and you gave them wool?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(10);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(8);
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Human call itself Askeladden! It not trick Lalli, Lalli do", "good deal with human! Stupid human get some dumb", "wool, but did not get golden apples!", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I see... okay, well, bye!", 591);
                    int questId5 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId5] = ((Player)npcId).questProgressFlags[questId5] + GameUtil.bitFlag(3);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) != 0) {
                if (!(!this.hasPreparedStoneSoup((Player)npcId) || ((Player)npcId).ownsItem(3693) || ((Player)npcId).ownsItem(3694) || ((Player)npcId).ownsItem(3689) || ((Player)npcId).ownsItem(3690) || ((Player)npcId).ownsItem(3691))) {
                    if (value2 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there.", 591);
                        return true;
                    }
                    if (value2 == 2) {
                        ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Your soup very tasty, human! But me still not want", "trade golden apples for your stone. Me think pet rock", "get jealous.", 591);
                        return true;
                    }
                    if (value2 == 3) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I. DON'T. WANT. ANY. GOLDEN APPLES. ALL.", "I. WANT. IS. A. GOLDEN. FLEECE.", 591);
                        return true;
                    }
                    if (value2 == 4) {
                        ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Gee, sorry human, all you have do is ask, me not need", "you to shout. You act like you think Lalli am stupid or", "something...", 591);
                        return true;
                    }
                    if (value2 == 5) {
                        ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3693, 1));
                        ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Here you go. Hah! Me trick you human! All you got is", "worthless golden fleece! Me got very rare soup-making", "stone!", 591);
                        return true;
                    }
                    if (value2 == 6) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Glad you're happy Lalli!", 591);
                        ((Player)npcId).getDialogueManager().finishDialogue();
                        return true;
                    }
                }
                if (value2 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("It am ready now?", 591);
                    if (this.hasPreparedStoneSoup((Player)npcId)) {
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(102);
                    }
                    return true;
                }
                if (value2 == 101) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Not just yet...", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 102) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Indeed it is. Try it and see.", 591);
                    return true;
                }
                if (value2 == 103) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hmm... YUM! That are delicious! Me never know", "human know how to make soup out of stone? It some special", "stone?", 591);
                    return true;
                }
                if (value2 == 104) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Indeed it is. But I'm willing to trade it.", 591);
                    return true;
                }
                if (value2 == 105) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Let me think about that, me like to think.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1295) {
            if (value42 == 1) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can I have another pet rock?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Sure, have as many as you like. I've plenty more!", 591);
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3695, 1));
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(19)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(20)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("I don't suppose you have any idea where I could find a", "written promise from Askeladden to stay out of the", "Longhall?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("What? I can't believe she asked you to get a written", "promise from me to stay out!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yup, she really did.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Awwwwwww.... but the longhall is just SO MUCH FUN!", "I'd live there if I could! I suppose you really need that", "promise to help become a Fremennik, huh?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yeah, I really do...", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well I'll tell you what buddy; As it's you, I'll give you", "that written promise. All I ask in return for it is a", "measly 5000 gold. What do you say?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 8) {
                    if (value32 == 1 && ((Player)npcId).getInventoryManager().containsItemAmount(995, 5000)) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("That's all you want in return? Sure thing. Here you", "go.", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(9);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 9 && ((Player)npcId).getInventoryManager().containsItemAmount(995, 5000)) {
                    ((Player)npcId).getInventoryManager().removeItem(new ItemStack(995, 5000));
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3709, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Done, and done. Let me know if you got any more", "cash burning a hole in your pocket I can relieve you", "of, buddy.", 591);
                    int questId6 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId6] = ((Player)npcId).questProgressFlags[questId6] + GameUtil.bitFlag(20);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(20)) != 0 && !FremennikTrialsQuest.hasAnyMerchantTrialItem((Player)npcId) && value2 == 1) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Here you go, Don't lose it this time.", 591);
                ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3709, 1));
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (!((((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(3)) == 0 || (value42 & GameUtil.bitFlag(5)) != 0 || ((Player)npcId).ownsItem(3695) || ((Player)npcId).ownsItem(3693) || ((Player)npcId).ownsItem(3694) || ((Player)npcId).ownsItem(3689) || ((Player)npcId).ownsItem(3690))) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Hello there. I understand you managed to get some", "golden wool from Lalli?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("HAHAHA! Yeah, that Lalli... what a maroon!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So how did you manage to get the wool?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, as you know, I am doing the same trials that you", "are as part of my test of manhood, and that troll is the", "only one who can get that wool.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You might have noticed he's kind of... messed in the", "head buddy! He's real paranoid about people stealing his", "golden apples, isn't he?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Indeed he is. So how did you manage to get some", "golden wool from him?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("It was easy buddy! I persuaded him he needed a pet to", "help him guard his apples. A pet that would never sleep!", "A pet that would never need food, or exercise!", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What pet is this then?", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("A pet ROCK!", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Man, can you believe that stupid troll traded me some", "of his golden wool for a worthless ROCK?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Buddy, I hafta say; if brains were explosives, that troll", "wouldn't have enough to blow his nose!", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Do you have any spare rocks then?", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3695, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Sure thing buddy, although I have to say, I doubt even", "that troll is stupid enough to fall for the SAME trick", "TWICE in a row! You can try anyway though!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1273) {
            if (value42 < 2) {
                return false;
            }
            if (value2 == 100) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Many thanks for the offering outerlander. Please", "accept this gift as your ability to play the lyre...", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 1278) {
            if (value2 == 100) {
                ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Yeah, you're good to go through. Olaf tells me you're", "some kind of outerlander bard here on tour. I doubt", "you're worse than Olaf is.", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (value2 == 101) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hey! You have no business in there!", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 1282) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there!", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hello outerlander.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Are you a member of the council?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("That I am outerlander; it is a position that brings my", "family and I pride.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I was wondering if I can count on your vote at the", "council of elders?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You wish to become a Fremennik? I may be persuaded", "to swing my vote to your favour, but you will first", "need to do a little task for me.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("How did I know it wouldn't be that simple for your", "vote?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Calm yourself outerlander. It is but a small task", "really... I simply require a flower.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("A flower? What's the catch?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("The catch? Well... it is not just any flower. Someone in", "this town has an extremely rare flower from a far off", "land that they picked up on their travels.", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I would like you to demonstrate your merchanting skills", "to me, by persuading them to part with it, and then give", "it to me for my vote.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well... I guess that doesn't sound too hard...", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Excellent! You will obtain this rare flower for me then?", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 15) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Okay. I don't think this will be too difficult. Any", "suggestions on where to start looking for this flower?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(16);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Ah, well outerlander, if I knew where to start looking I", "would simply do it myself!", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(6));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0) {
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("No help at ALL?", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("We are a very insular clan, so I would not expect you", "to have to leave this town to find whatever you need.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 <= 2 && !((Player)npcId).getInventoryManager().containsItemAmount(3698, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there!", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Here's that flower you wanted.", 591);
                    ((Player)npcId).getInventoryManager().removeItem(new ItemStack(3698, 1));
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(7));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(7)) != 0 && value2 == 3) {
                ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Incredible! Your merchanting skills might even match", "my own! I have no choice but to recommend you to", "the council of elders!", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 1304) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(8)) != 0) {
                if (value2 <= 2 && !((Player)npcId).getInventoryManager().containsItemAmount(3699, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("You'll be glad to know I have had a love song written", "just for you by Olaf. So can I have that flower of", "yours now?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3699, 1), new ItemStack(3698, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Oh. It's by Olaf? Hmm. Well, a deal's a deal. I just", "hope it's better than the usual rubbish he comes up with,", "or my chances are worse than ever.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(8)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "rare flower from across the sea, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Ah! Even the outerlanders have heard of my mysterious", "flower! I found it in a country far far away from here!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can I buy it from you?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I'm afraid not, outerlander. There is a woman in this", "village whose heart I seek to capture, and I think giving", "her this strange flower might be my best bet with her.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Maybe you could let me have the flower and do", "something else to impress her?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hmm... that is not a totally stupid idea outerlander. I", "know she is a lover of music, and a romantic ballad", "might be just the thing with which to woo her.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Unfortunately I don't have a musical bone in my entire", "body, so someone else will have to write it for me.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("So if I can find someone to write you a romantic", "ballad, you will give me your flower?", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("That sounds like a fair deal to me, outerlander.", 591);
                    int questId7 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId7] = ((Player)npcId).questProgressFlags[questId7] + GameUtil.bitFlag(8);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1301) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(10)) != 0) {
                if (value2 <= 2 && !((Player)npcId).getInventoryManager().containsItemAmount(3708, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerFourLineDialogue("Hello. Can I have those boots now? Here is a written", "statement from Brundt outlining future tax burdens", "upon Fremennik merchants and shopkeepers for the", "year.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3708, 1), new ItemStack(3700, 1));
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Certainly! Let me have a look at what he has written", "here, just give me a moment...", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Yes, that all appears in order. Tell Olaf to come to me", "next time for shoes!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(9)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(10)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find", "some custom sturdy boots, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, I don't usually have many shoes in stock here in", "my little clothes shop... I will be able to make you up a", "pair if you are really desperate though?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("They're not for me... I need them for Olaf.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Oh, that foolish bard... Why didn't he just ask me to", "make him some? It is his stupid pride, I believe!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I will tell you what I will do outerlander; I know that", "you must have the ear of the chieftain for him to", "consider you as worthy of becoming a Fremennik by", "trial.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I will make you a pair of sturdy boots for Olaf if you", "will persuade him to reduce the sales tax placed upon all", "Fremennik shopkeepers. It does nothing but hurt my", "business now.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Okay, I will see what I can do.", 591);
                    int questId8 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId8] = ((Player)npcId).questProgressFlags[questId8] + GameUtil.bitFlag(10);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1281) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(12)) != 0) {
                if (value2 <= 3 && !((Player)npcId).getInventoryManager().containsItemAmount(3702, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Greetings outerlander.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Here. I have your bowstring. Give me your map to the", "hunting grounds.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3702, 1), new ItemStack(3701, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well met, outerlander. I see some hunting potential", "within you. Here, take my map, I was getting too", "dependent on it for my skill anyway.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(11)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(12)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Greetings outerlander.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "map to unspoiled hunting grounds, do you?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, of course I do. I wouldn't be much of a", "huntsman if I didn't know where to find my prey now,", "would I outerlander?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("No, I guess not. So can I have it?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Directions to my hunting grounds could mean the end", "of my livelihood. The only way I would be prepared to", "give them up would be...", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What? Power? Money? Women? Wine?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("...a new string for my hunting bow. Not just any", "bowstring; I need a custom bowstring, balanced for my", "bow precisely to keep my hunt competitive.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Only in this way would I allow the knowledge of my", "hunting grounds to be passed on to strangers.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So where would I get that?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I have no idea. But then again, I'm happy with my old", "bowstring and being the only person who knows where", "my hunting ground is.", 591);
                    int questId9 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId9] = ((Player)npcId).questProgressFlags[questId9] + GameUtil.bitFlag(12);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(8)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("What do you want outerlander?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Are you a member of the council?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("The Fremennik council of elders? I am pleased to say", "that I am. My value as a huntsman is recognised by", "my position there.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I was wondering if I could persuade you to vouch for", "me as a member of your clan?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You? ... well... I am not totally against the idea", "outerlander. If you can demonstrate some hunting skills", "then perhaps I may offer my vote.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("How can I prove my hunting skills to you? I can go", "kill, like, a hundred chickens for you right now!", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Chickens? You think that would impress me?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Cows then? I can kill cows until, er, the cows come", "home.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("No. The prey I have in mind for you to prove your", "worth to me is something far more dangerous. Far", "more difficult. Far more deadly.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Not... Giant Rats?!?!", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I suspect you are mocking me outerlander. You will", "need to prove your skill as a hunter to me by tracking", "and defeating... The Draugen.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("What's a Draugen?", "Forget it.");
                    return true;
                }
                if (value2 == 13) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("What's a Draugen? Some kind of cheap barbarian rip-", "off of a dragon?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(14);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hmmm. No, the words are slightly similar I suppose,", "but they are very different creatures.", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("The Draugen is an evil ghost from Fremennik", "mythology, that devours the souls of those brave", "warriors who meet their ends at sea.", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("It stalks the coastlines, invisible to all. It brings us bad", "fortunes, and curses our journeys across the seas. It is", "also unkillable by normal means.", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("...Let me get this straight; You want me to hunt an", "unkillable, invincible, and invisible enemy? How am I", "supposed to do that?", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well outerlander, should you accept my challenge I will", "show you a special hunter's trick that will help you. Do", "you accept the challenge?", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 20) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Well, I need every vote I can get in the council of", "elders, but this certainly sounds impossible to do...", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(21);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 21) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Not at all outerlander. The Draugen is indeed", "impossible to kill, but that is not the same as being", "impossible to fight against.", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(8));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(8)) != 0 && (value42 & GameUtil.bitFlag(9)) == 0) {
                if (value2 == 1) {
                    if (!((Player)npcId).ownsItem(3696) && !((Player)npcId).ownsItem(3697)) {
                        ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3696, 1));
                        ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Don't lose it this time!", 591);
                        ((Player)npcId).getDialogueManager().finishDialogue();
                        return true;
                    }
                    if (((Player)npcId).ownsItem(3697)) {
                        ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I saw the entire hunt. Let me take that talisman from", "you; I would be honoured to speak out for you to our", "council of elders after such a hunt, outerlander.", 591);
                        return true;
                    }
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Thanks!", 591);
                    return true;
                }
                if (value2 == 3) {
                    if (((Player)npcId).ownsItem(3697)) {
                        ((Player)npcId).getInventoryManager().removeItem(new ItemStack(3697, 1));
                        ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(9));
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 22) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Every time he takes a Fremennik life, he gains in", "power, so to keep it from becoming too powerful we", "hunters hunt it and steal its life force.", 591);
                    return true;
                }
                if (value2 == 23) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(3696, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("We do this with a special talisman. Here, take it; it will", "let you track the Draugen while it's invisible, and when", "you defeat it will absorb its essence.", 591);
                    return true;
                }
                if (value2 == 24) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I want you to track the Draugen, defeat it, and store", "its essence in that talisman for me. If you can do this", "important task for my clan, I will vote for you.", 591);
                    return true;
                }
                if (value2 == 25) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Take care of the talisman, and see me when you have", "completed this task.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1303) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(13)) != 0) {
                if (value2 <= 2 && !((Player)npcId).getInventoryManager().containsItemAmount(3703, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Hi there. I got your fish, so can I have that bowstring", "for Sigli now?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3703, 1), new ItemStack(3702, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ohh... That's a nice fish. Very pleased. Here. Take the", "bowstring. You fulfilled agreement. only fair I do same.", "Good work outerlander.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Thanks!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(12)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(13)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "finely balanced custom bowstring, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Aye, I have a few in stock. What would an outerlander", "be wanting with equipment like that?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("It's for Sigli. It needs to be weighted precisely to suit", "his hunting bow.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("For Sigli eh? Well, I made his bow in the first place, so", "I'll be able to select the right string for you... just one", "small problem.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What's that?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("This string you'll be wanting... Very rare. Take a lot of", "time to recreate. Not sure you have the cash for it.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Then maybe you'll accept something else...?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Heh. Good thinking outerlander. Well, it's true, there is", "more to life than just making money. Making weapons", "is good money, but it's not why I do it.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I'll tell you what. I heard a rumour that one of the", "fishermen down by the docks caught some weird looking", "fish as they were fishing the other day.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("From what I hear this fish is unique. Nobody's ever", "seen its like before. This intrigues me. I'd like to have it", "for myself. Make a good trophy.", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("You get me that fish, I give you the bowstring. What", "do you say? We got a deal?", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Sounds good to me.", 591);
                    int questId10 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId10] = ((Player)npcId).questProgressFlags[questId10] + GameUtil.bitFlag(13);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1302) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(14)) != 0) {
                if (value2 <= 2 && !((Player)npcId).getInventoryManager().containsItemAmount(3704, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Here. I got you your map.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3704, 1), new ItemStack(3703, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Great work outerlander! With this, I can finally catch", "enough fish to make an honest living from it! Here, have", "the stupid rare fish.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(13)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(14)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find", "an exotic and extremely rare fish, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Ah, so even outerlanders have heard of my amazing", "catch the other day!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You have it? Can I trade you something for it?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("As exotic looking as it is, it is bad eating. I will happily", "trade it if you can find me the secret map of the best", "fishing spots that the navigator has hidden away.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Is that all?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Indeed it is, outerlander. The only reason I sit out", "here in the cold all day long is so I don't have to pay", "his outrageous prices.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("By getting me his copy of that map, I will finally be self", "sufficient. I might even make a profit!", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I'll see what I can do.", 591);
                    int questId11 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId11] = ((Player)npcId).questProgressFlags[questId11] + GameUtil.bitFlag(14);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1283) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(15)) != 0) {
                if (value2 <= 6 && !((Player)npcId).getInventoryManager().containsItemAmount(3705, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I would like your map of fishing spots.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I have already told you outerlander; I will not", "exchange it for anything other than a divination on the", "weather from our seer himself!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What, like this one I have here?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("W-what...? I don't believe it! How did you...? I suppose", "it doesn't matter, you have my gratitude outerlander!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("With this forecast I will be able to plan a safe course", "for our next raiding expedition!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3705, 1), new ItemStack(3704, 1));
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Here, outerlander; you may take my map of local", "fishing patterns with my gratitude!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(14)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(15)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Greetings outerlander.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "map of deep sea fishing spots do you?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hmmm? Why of course! As the navigator for the", "Fremennik I keep all of our maps secure right here.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Great! Can I have it?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Have it? Just like that? I think not outerlander. This", "map shows all of the prime fishing locations nearby.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("It is very valuable to our clan. I am afraid I can not", "just give it away.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Perhaps I can trade you something for it?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("A trade? For a map of the best fishing spots in a", "hundred leagues? I will trade it for no less than a", "weather forecast from our Seer.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("As a navigator, the weather is extremely important for", "plotting the best course. Unfortunately the Seer is", "always too busy to help me with a forecast.", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Where could I get a weather forecast from then?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I just told you: from the Seer. You will need to", "persuade him to take the time to make a forecast", "somehow.", 591);
                    int questId12 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId12] = ((Player)npcId).questProgressFlags[questId12] + GameUtil.bitFlag(15);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(10)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello!", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("I am trying to become a member of the Fremennik", "clan! The Chieftain told me that I may be able to gain", "your vote at the council of elders?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You wish to stop being an outerlander? I can", "understand that! I have no reason why I would prevent", "you becoming a Fremennik...", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("...but you must first pass a little test for me to prove", "you are worthy.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What kind of test?", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, I serve our clan as a navigator. The seas can be", "a fearful place when you know not where you are", "heading.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Should something happen to me, all members of our", "tribe have some basic sense of direction so that they", "may always return safely home.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("If you are able to demonstrate to me that you too have", "a good sense of direction then I will recommend you to", "the rest of the council of elders immediately.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well, how would I go about showing that?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ah, a simple task! Below this building I have constructed", "a maze; should you be able to walk from one side to the", "other that will be proof to me.", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("You wish to try my challenge?", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 13) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("A maze? Is that all? Sure, it sounds simple enough.", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(14);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I will warn you outerlander, this maze was designed by", "myself, and is of the most fiendish complexity!", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(10));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(10)) != 0 && (value42 & GameUtil.bitFlag(11)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello!", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Man, your maze is pretty tough!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hahahaha it is the most complex route I have ever", "devised! I am truly a genius at navigation! The world", "will remember my name!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Can't I do something else for your vote at the council", "of elders?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("No, you cannot. It is my maze, or nothing.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Oh really? Watch and learn...", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Outerlander! You have finished my maze! I am", "genuinely impressed!", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(11));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(11)) != 0) {
                if (value2 == 101) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("So does that mean I can rely on your vote at the", "council of elders to allow me into your village?", 591);
                    return true;
                }
                if (value2 == 102) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Of course outerlander! I am nothing if not a man of", "my word!", 591);
                    return true;
                }
                if (value2 == 103) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Thanks!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1288) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(16)) != 0) {
                if (value2 <= 4 && !((Player)npcId).getInventoryManager().containsItemAmount(3710, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can I have a weather forecast now please?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I have already told you outerlander; You may have a", "reading from me when I have a signed contract from a", "warrior guaranteeing my proctection.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yeah, I know; I have one right here from Thorvald.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3710, 1), new ItemStack(3705, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You have not only persuaded one of the Fremennik to", "act as a servant to me, but you have enlisted the aid of", "mighty Thorvald himself???", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You may take this forecast with my blessing", "outerlander. You have offered me the greatest security", "I can imagine.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(15)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(16)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "weather forecast from the Fremennik Seer do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Er.... Yes, because I AM the Fremennik Seer.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can I have a weather forecast then please?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You require a divination of the weather? This is a", "simple matter for me, but I will require something in", "return from you for this small service.", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I knew you were going to say that...", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Do not fret, outerlander; it is fairly simple matter. I", "require a bodyguard for protection. Find someone", "willing to offer me this service.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("That's all?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("That is all.", 591);
                    int questId13 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId13] = ((Player)npcId).questProgressFlags[questId13] + GameUtil.bitFlag(16);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(12)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hello outerlander. What do you want?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Hello. I'm looking for members of the council of elders", "to vote for me to become a Fremennik.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Are you now? Well that is interesting. Usually", "outerlanders do not concern themselves with our ways", "like that.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I am one of the members of the council of elders, and", "should you be able to prove to me that you have", "something to offer my clan", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I will vote in your favour at the next meeting.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("How can I prove that to you?", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Well, I have but a simple test. This building behind me", "is my house. Inside I have constructed a puzzle.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("As a Seer to the clan, I value intelligence very highly,", "so you may think of it as an intelligence test of sorts.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("An intelligence test? I thought barbarians were stupid!", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("That is the opinion that outerlanders usually hold of my", "people, it is true. But that is because people often", "confuse knowledge with wisdom.", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("My puzzle tests not what you know, but what you can", "work out. All members of our clan have been tested", "when they took their trials.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So what exactly does this puzzle consist of, then?", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, firstly you must enter my house with no items,", "weapons or armour. Then it is a simple matter of", "entering through one door and leaving by the other.", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I can't take anything in there with me?", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("That is correct outerlander. Everything you need to", "complete this puzzle you will find inside the building.", "Nothing more.", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("So what say you outerlander? You think you have the", "wit to earn yourself my vote?", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 18) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Yes, I accept your challenge. I have one small question,", "however...", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(19);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes, outerlander?", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(12));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(12)) != 0 && (value42 & GameUtil.bitFlag(13)) == 0) {
                if (value2 == 20) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Well... you say I can bring nothing with me when I", "enter your house...", 591);
                    return true;
                }
                if (value2 == 21) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes, outerlander?", 591);
                    return true;
                }
                if (value2 == 22) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well.....", 591);
                    return true;
                }
                if (value2 == 23) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes, outerlander?", 591);
                    return true;
                }
                if (value2 == 24) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Where is the nearest bank?", 591);
                    return true;
                }
                if (value2 == 25) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ah, I see your problem outerlander. The nearest bank", "to here is the place known to outerlanders as the Seers", "Village. It is some way South.", 591);
                    return true;
                }
                if (value2 == 26) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I do however have an alternative, should you wish to", "take it.", 591);
                    return true;
                }
                if (value2 == 27) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("And what is that?", 591);
                    return true;
                }
                if (value2 == 28) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I can store all the weapons, armour and items that you", "have upon you directly into your bank account.", 591);
                    return true;
                }
                if (value2 == 29) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("This will tax what little magic I possess however, so you", "will have to manually travel to the bank to withdraw", "them again.", 591);
                    return true;
                }
                if (value2 == 30) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("What say you outerlander? you wish me to do this for", "you?", 591);
                    return true;
                }
                if (value2 == 31) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 32) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes, thank you!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(33);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 33) {
                    if (((Player)npcId).depositInventoryAndEquipment()) {
                        ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("The task is done. I wish you luck with your test,", "outerlander.", 591);
                    } else {
                        ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I'm not able to! I wish you luck with your test,", "outerlander.", 591);
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Incredible! To have solved my puzzle so quickly! I have", "no choice but to vote in your favour!", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(13));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(13)) != 0) {
                if (value2 == 101) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So you will vote for me at the council?", 591);
                    return true;
                }
                if (value2 == 102) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Absolutely, outerlander. Your wisdom in passing my test", "marks you as worthy in my eyes.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value2 == 1) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("You wish me to bank your items for", "you?", 591);
                return true;
            }
            if (value2 == 2) {
                ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                return true;
            }
            if (value2 == 3) {
                if (value32 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes, thank you!", 591);
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                ((Player)npcId).getDialogueManager().finishDialogue();
                return false;
            }
            if (value2 == 4) {
                if (((Player)npcId).depositInventoryAndEquipment()) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("The task is done.", 591);
                } else {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I'm not able to!", 591);
                }
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 1289) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(17)) != 0) {
                if (value2 <= 6 && !((Player)npcId).getInventoryManager().containsItemAmount(3706, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I would like your contract to offer your services as a", "bodyguard.", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Oh you would, would you outerlander? I have already", "told you, I will not demean myself with such a baby", "sitting job until I can sit in the Longhall with pride.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("It's a good thing I have the Champions' Token right", "here then, isn't it?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Ah... well this is a different matter. With that token I", "can claim my rightful place as a champion in the Long", "hall!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Here outerlander, I can suffer the indignity of playing", "babysitter if it means that I can then revel with my", "warrior equals in the Long Hall afterwards!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3706, 1), new ItemStack(3710, 1));
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Here outerlander, take this contract; I will fulfill it to", "my utmost.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(16)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(17)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find a", "brave and powerful warrior to act as a bodyguard?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Know you not who I am outerlander? There are none", "more brave or powerful than me amongst all the", "Fremennik!", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("However... The role of bodyguard is below me, as a", "noble warrior. You might as well ask me to babysit the", "children!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Is there no way you would do this for me?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("There is but one way outerlander. Since I was steeled", "in battle, I have dreamt of earning my place at the", "Champions Table in the Long Hall.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("It is a tradition amongst us that the bravest and", "strongest are honoured with a table of champions to", "drink and feast all that they can in our Long Hall.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Unfortunately, there are only a fixed number of places", "available at the table, and these places were all filled", "many moons ago by others.", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Although my worthiness is undeniable, the only way I", "may take my place is if one of those already there die,", "or give up their place to me voluntarily.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("So you want me to go kill one of them off for you?", "Make it look like an accident?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("WHAT? No, no, not at all! I am shocked you would", "suggest such a thing!", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("If you can persuade one of the Revellers to give up", "their Champions' Token to you so that I might take", "their place, you may have my contract as a bodyguard.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Okay, I'll see what I can do.", 591);
                    int questId14 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId14] = ((Player)npcId).questProgressFlags[questId14] + GameUtil.bitFlag(17);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(14)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello!", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hello yourself, outerlander. What brings you to dare", "speak to a mighty Fremennik warrior such as myself?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Erm... are you a member of the council?", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("The Fremennik council of elders? Why, of course I", "am. I am recognised as one the clans mightiest", "warriors. What is it to you outerlander?", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Well... I was wondering if you could vote for me to", "become a Fremennik.", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("An outerlander wishes to become a Fremennik!?!? Ha!", "That is priceless!", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, let us say that I am not totally against this", "concept. As a warrior, I appreciate the value of brave", "and powerful warriors to our clan,", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("and even though you may be an outerlander, I will not", "hold this against you if you can prove yourself to be", "fierce of heart in a combat situation to me.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("So how can I prove that? You want me to fight? Come", "on then, bring it on! Right here, right now, buddy!", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hahahahaha! You certainly show some spirit for an", "outerlander!", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("But spirit does not always make a good warrior. It", "takes both skill and spirit to be so. I have a test that I", "give all Fremenniks on their path to be a member of the", "clan.", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("My test will challenge both your combat prowess and", "your bravery equally. Should you pass it you will earn", "my vote at the council, and more importantly my", "respect for you as a warrior.", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("So what say you, outerlander? Are you prepared for", "the battle?", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("Yes", "No");
                    return true;
                }
                if (value2 == 15) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Am I prepared? I'll show you what combat's all about,", "you big sissy barbarian type guy!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(16);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hahahahaha! I'm beginning to like you already,", "outerlander!", 591);
                    ((Player)npcId).addQuestState(this.getQuestId(), GameUtil.bitFlag(14));
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(14)) != 0 && (value42 & GameUtil.bitFlag(15)) == 0) {
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Then allow me to present you with my challenge; This", "ladder here will take you to a place of combat. I have", "placed a special warrior down there to challenge you.", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Battle him to death, and you will pass my challenge.", "If at any point you wish to leave combat, simply climb", "back up the ladder, to leave that place.", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("If you leave you will of course fail the test. You may", "retry my test in the future if you fail, but you must", "stay down there until the death if you wish for my vote", "at the council.", 591);
                    return true;
                }
                if (value2 == 20) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You must defeat him three times to prove that you are", "worthy. The fourth time that you fight him will be to", "the death, so do not show cowardice.", 591);
                    return true;
                }
                if (value2 == 21) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Is that all? It will be easy!", 591);
                    return true;
                }
                if (value2 == 22) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("No, there is one more important rule;", 591);
                    return true;
                }
                if (value2 == 23) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("You may not enter the battleground with any armour", "or weaponry of any kind.", 591);
                    return true;
                }
                if (value2 == 24) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("If you need to place your equipment into your bank", "account, I recommend that you speak to the seer, who", "knows a spell that will do that for you.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(15)) != 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("So can I count on your vote at the council of elders", "now Thorvald?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Absolutely! I watched the entire battle, and was", "extremely impressed with your bravery in combat!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hahaha! Well fought outerlander! Now come down from", "there, you have passed my trial with flying colours!", 591);
                    return true;
                }
                if (value2 == 101) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("But... I don't understand... I did not manage to beat", "Koschei...", 591);
                    return true;
                }
                if (value2 == 102) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I did not say you had to, outerlander! All I asked was", "that you fought to the death! And you did! The death", "was your own!", 591);
                    return true;
                }
                if (value2 == 103) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("I was not interested in how strong you are! I was", "interested in how BRAVE you are! You fought a", "superior enemy to your very last breath - THAT is", "bravery.", 591);
                    return true;
                }
                if (value2 == 104) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I would be honoured to represent you to the council as", "worthy of being a Fremennik after watching that superb", "battle!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1300) {
            if (value42 < 2) {
                return false;
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(19)) != 0) {
                if (value2 <= 3 && !((Player)npcId).getInventoryManager().containsItemAmount(3709, 1)) {
                    return false;
                }
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Hi! Can I please have one of your legendary cocktails", "now?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("What?!?! I can't believe you... Let me look at that...", "Askeladden would NEVER... Gosh. It looks legitimate.", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getInventoryManager().replaceItem(new ItemStack(3709, 1), new ItemStack(3707, 1));
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Here you go, on the house! You have made my life SO", "much easier! Knowing that little monster won't be", "bugging me in here all the time anymore!", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("That little weasel will have to abide by this written", "promise that Askeladden can never ever enter the", "Longhall again! He can't get round this one!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Uh... yeah... yeah, you probably won't see someone", "called Askeladden coming in here...", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if ((value42 & GameUtil.bitFlag(6)) != 0 && (value42 & GameUtil.bitFlag(7)) == 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(18)) != 0 && (((Player)npcId).questProgressFlags[this.getQuestId()] & GameUtil.bitFlag(19)) == 0) {
                if (value2 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't suppose you have any idea where I could find", "the longhall barkeeps' legendary cocktail, do you?", 591);
                    return true;
                }
                if (value2 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("How did you hear about that?!?!?", 591);
                    return true;
                }
                if (value2 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I didn't think anybody knew about that... Well, it is", "true that in my younger years as a barkeep, I", "wandered the lands trying various alcoholic delicacies.", 591);
                    return true;
                }
                if (value2 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Did you ever realise just how many different types of", "alcohol there are here in RuneScape? Lots!", 591);
                    return true;
                }
                if (value2 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Well, anyway, I used a fusion of various drinks from", "all around the world to create the greatest cocktail ever", "made!", 591);
                    return true;
                }
                if (value2 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Of course, when my wanderlust was gone, and I", "returned back to Rellekka to serve as barkeep here, I", "gave all that up.", 591);
                    return true;
                }
                if (value2 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("But you still remember how to make it, right?", 591);
                    return true;
                }
                if (value2 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Of course.", 591);
                    return true;
                }
                if (value2 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("And you have all the ingredients here? I don't need to", "go chasing round the world for obscure ingredients to", "make it?", 591);
                    return true;
                }
                if (value2 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("No, I have them all here. Why?", 591);
                    return true;
                }
                if (value2 == 11) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can you make me your legendary cocktail then?", 591);
                    return true;
                }
                if (value2 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I would rather not; it is a reminder of a life I left", "behind when I came back.", 591);
                    return true;
                }
                if (value2 == 13) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Any way I could change your mind?", 591);
                    return true;
                }
                if (value2 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You need this to become a Fremennik, right? Well, you", "seem okay for an outerlander, it would be a shame to", "see you fail. You know Askeladden?", 591);
                    return true;
                }
                if (value2 == 15) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("That kid outside? Sure.", 591);
                    return true;
                }
                if (value2 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("He is nothing but a pest. He keeps sneaking in and", "stealing beer. I shudder to think what he will be like", "when he has passed his trial of manhood,", 591);
                    return true;
                }
                if (value2 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("and is allowed in here legitimately. If you can get him", "to sign a contract promising that he will NEVER", "EVER EVER darken my doorway here again, you", "get the drink.", 591);
                    return true;
                }
                if (value2 == 18) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Any idea how I can get him to do that?", 591);
                    return true;
                }
                if (value2 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Knowing that little horror, he'll probably be willing to in", "exchange for some cash. You should go ask him", "yourself though.", 591);
                    int questId15 = this.getQuestId();
                    ((Player)npcId).questProgressFlags[questId15] = ((Player)npcId).questProgressFlags[questId15] + GameUtil.bitFlag(19);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 1290) {
            if (value2 == 100) {
                ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("It seems you have some idea of combat after all,", "outerlander! I will not hold back so much this time!", 591);
                Npc npc = ((Player)npcId).getOwnedNpc();
                if (npc != null && npc.getNpcId() == 1290) {
                    npc.transformToNpcId(1291, 999999999);
                    npc.setCurrentHitpoints(npc.getMaxHitpoints());
                }
                return true;
            }
            if (value2 == 101) {
                Npc npc = ((Player)npcId).getOwnedNpc();
                if (npc != null && npc.getNpcId() == 1291) {
                    npc.getUpdateState().setForcedText("Outerlander... you made a mistake coming down here!");
                    CombatManager.startCombat(npc, (Entity)npcId);
                }
                return true;
            }
        }
        if (npcId2 == 1291) {
            if (value2 == 100) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Impressive start... But now we fight for real!", 591);
                Npc npc = ((Player)npcId).getOwnedNpc();
                if (npc != null && npc.getNpcId() == 1291) {
                    npc.transformToNpcId(1292, 999999999);
                    npc.setCurrentHitpoints(npc.getMaxHitpoints());
                }
                return true;
            }
            if (value2 == 101) {
                Npc npc = ((Player)npcId).getOwnedNpc();
                if (npc != null && npc.getNpcId() == 1292) {
                    npc.getUpdateState().setForcedText("What kind of person calls themself " + ((Player)npcId).getUsername() + "?");
                    CombatManager.startCombat(npc, (Entity)npcId);
                }
                return true;
            }
        }
        if (npcId2 == 1292) {
            if (value2 == 100) {
                ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("You show some skill at combat... I will hold back no", "longer! This time you lose your prayer however, and", "fight like a warrior!", 591);
                ((Player)npcId).getSkillManager().getCurrentLevels()[5] = 0;
                ((Player)npcId).getSkillManager().refreshSkill(5);
                Npc npc = ((Player)npcId).getOwnedNpc();
                if (npc != null && npc.getNpcId() == 1292) {
                    npc.transformToNpcId(1293, 999999999);
                    npc.setCurrentHitpoints(npc.getMaxHitpoints());
                }
                return true;
            }
            if (value2 == 101) {
                Npc npc = ((Player)npcId).getOwnedNpc();
                if (npc != null && npc.getNpcId() == 1293) {
                    npc.getUpdateState().setForcedText("Prepare to face my power, outerlander!");
                    CombatManager.startCombat(npc, (Entity)npcId);
                }
                return true;
            }
        }
        return false;
    }
}

