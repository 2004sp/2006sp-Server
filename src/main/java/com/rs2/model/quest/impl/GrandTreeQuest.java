package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestHook;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.GloughChestScrollFindTask;
import com.rs2.model.quest.impl.GloughDemonSpawnTask;
import com.rs2.model.quest.impl.GloughGuardArrestStartTask;
import com.rs2.model.quest.impl.GrandTreeGloughCaveEncounterTask;
import com.rs2.model.quest.impl.GrandTreeGuardPassageReportTask;
import com.rs2.model.quest.impl.GrandTreeGuardPrisonEscortTask;
import com.rs2.model.quest.impl.KingNarnodePrisonReleaseTask;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class GrandTreeQuest
extends QuestScript {
    private List daconiaRootSearchPositions = Arrays.asList(new Position(2481, 9904, 0), new Position(2465, 9891, 0), new Position(2467, 9872, 0), new Position(2457, 9881, 0), new Position(2455, 9874, 0), new Position(2439, 9881, 0), new Position(2444, 9893, 0), new Position(2468, 9890, 0));
    private Position twig789PlacementPosition = new Position(2485, 3467, 2);
    private Position twig790PlacementPosition = new Position(2486, 3467, 2);
    private Position twig791PlacementPosition = new Position(2487, 3467, 2);
    private Position twig792PlacementPosition = new Position(2488, 3467, 2);
    private int gloughJournalLastPageIndex = 3;
    private int translationBookLastPageIndex = 4;
    private RectangularArea kingNarnodeIntroArea = new RectangularArea(2461, 3491, 2470, 3500, 0);
    private RectangularArea grandTreeUndergroundArea = new RectangularArea(2426, 9851, 2498, 9921, 0);

    public GrandTreeQuest(int value2) {
        super(46);
        super.setQuestPointReward(5);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            int skillManager = player.getSkillManager().getBaseLevel(16);
            String[] stringValues = new String[]{"I can start the quest at the Grand Tree in the Gnome", "Stronghold by speaking to King Narnode Shareen.", "", "I must have:", String.valueOf(skillManager >= 25 ? "@str@" : "") + "Level 25 Agility.", "High enough combat to defeat a level 172 demon."};
            return stringValues;
        }
        if (value2 == 2) {
            return new String[]{"I should follow King Narnode Shareen below the", "Grand Tree."};
        }
        if (value2 == 3) {
            return new String[]{"I should go show the bark sample to Hazelmere who", "lives on an island east of Yanille."};
        }
        if (value2 == 4) {
            return new String[]{"I should go tell King Narnode Shareen what Hazelmere", "told me."};
        }
        if (value2 == 5) {
            return new String[]{"I should go tell Glough what is happening. He lives", "in a tree house just in front of the Grand Tree."};
        }
        if (value2 == 6) {
            return new String[]{"I should now go back to King Narnode Shareen."};
        }
        if (value2 == 7) {
            return new String[]{"I should now go talk to the prisoner in the top", "level of the Grand Tree."};
        }
        if (value2 == 8) {
            if (!player.ownsItem(785)) {
                return new String[]{"I should search Glough's home to find out what he's", "up to."};
            }
            return new String[]{"I should speak with Glough."};
        }
        if (value2 == 9) {
            return new String[]{"I should talk with Charlie."};
        }
        if (value2 == 10 || value2 == 11) {
            if (!player.ownsItem(787)) {
                return new String[]{"I should go check the Karamja Shipyard east of Shilo", "Village. I might need a password: Ka-Lu-Min"};
            }
            return new String[]{"I should go show King what I found from the Shipyard", "and possibly ask Charlie for more information."};
        }
        if (value2 == 12) {
            if (player.ownsItem(794)) {
                return new String[]{"I should go show the invasion plans to King."};
            }
            if (!player.ownsItem(788)) {
                return new String[]{"I should go look for a key for Glough's chest.", "Charlie told me Anita should have the key.", "Anita lives west of the toad swamp."};
            }
            return new String[]{"I should go search the chest in Glough's house."};
        }
        if (value2 == 13) {
            return new String[]{"The King gave me some weird twigs found from", "Glough's house."};
        }
        if (value2 == 14) {
            return new String[]{"I should go down the secret trap door in Glough's house."};
        }
        if (value2 == 15) {
            return new String[]{"I should go tell the King what has happened."};
        }
        if (value2 == 16) {
            if (!player.ownsItem(793)) {
                return new String[]{"I should search for Daconia rock from the roots of", "the Grand Tree."};
            }
            return new String[]{"I should go give the Daconia rock to King Narnode Shareen."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "5 Quest Points", "7900 Agility XP", "18,400 Attack XP", "2150 Magic XP"};
        }
        return null;
    }

    private static boolean hasRequiredAgilityLevel(Player player) {
        return player.getSkillManager().getBaseLevel(16) >= 25;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("5 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("7900 Agility XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("18,400 Attack XP", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("2150 Magic XP", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(16, 7900.0);
        player.getSkillManager().addQuestExperience(0, 18400.0);
        player.getSkillManager().addQuestExperience(6, 2150.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 793);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleNpcDeathDrop(Player player, int npcId, Position position, int value2) {
        if (npcId == 674 && value2 == 11 && !player.ownsItem(787)) {
            GroundItem groundItem = new GroundItem(new ItemStack(787, 1), player, position);
            GroundItemManager.getInstance().spawn(groundItem);
            player.packetSender.sendGameMessage("The foreman drops a piece of paper as he dies.");
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value5, int value32, int value42) {
        if (objectId == 2446 && value5 == 2463 && value32 == 3497) {
            if (player.getQuestState(this.getQuestId()) == 0) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("It is locked.");
            } else {
                AttackStyleDefinition.startDelayedObjectMove(player, new Position(2464, 9897, 0));
            }
            return true;
        }
        if (objectId == 1985 && value42 == 16) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You search the root but don't find anything.");
            player.getUpdateState().setAnimation(827);
            return true;
        }
        if (objectId == 1986 && value42 == 16) {
            ArrayList arrayList = new ArrayList(this.daconiaRootSearchPositions);
            Collections.shuffle(arrayList, new Random(player.questRandomSeed));
            Position position = (Position)arrayList.get(0);
            if (value5 == position.getX() && value32 == position.getY() && !player.ownsItem(793)) {
                player.getDialogueManager().showItemMessage("You've found a Daconia rock!", new ItemStack(793, 1));
                player.getInventoryManager().addOrDropItem(new ItemStack(793, 1));
            } else {
                Player player4 = player;
                player4.packetSender.sendGameMessage("You search the root but don't find anything.");
            }
            player.getUpdateState().setAnimation(827);
            return true;
        }
        if (objectId == 2447 && value5 == 2484 && value32 == 3464) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(2486, 3465, 2));
            return true;
        }
        if (objectId == 2448 && value5 == 2485 && value32 == 3464) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(2484, 3463, 1));
            return true;
        }
        if (objectId == 2444 && value5 == 2487 && value32 == 3464 && value42 == 14) {
            player.setActionLocked(true);
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(2491, 9864, 0));
            GrandTreeGloughCaveEncounterTask grandTreeGloughCaveEncounterTask = new GrandTreeGloughCaveEncounterTask(this, 2, player);
            World.getTaskScheduler().schedule(grandTreeGloughCaveEncounterTask);
            return true;
        }
        if (objectId == 2434 && value5 == 2476 && value32 == 3465) {
            ObjectManager.getInstance().removeDynamicObjectAt(2476, 3465, 1, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2435, 2476, 3465, 1, 1, 10, 2434, 999999999), true);
            return true;
        }
        if (objectId == 2435 && value5 == 2476 && value32 == 3465 && value42 == 8 && !player.ownsItem(785)) {
            player.getDialogueManager().showItemMessage("You've found Glough's Journal!", new ItemStack(785, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(785, 1));
            return true;
        }
        if (objectId == 2451 && value5 == 2466 && value32 == 9904) {
            if (value42 == 1) {
                Player player4 = player;
                player4.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9905 ? 2 : -2, true);
            } else {
                Player player5 = player;
                Object value2 = player5;
                value2 = this;
                player5.packetSender.sendGameMessage("You need to complete: " + QuestDefinition.forId(((QuestHook)value2).getQuestId()).getName() + " to go through.");
            }
            return true;
        }
        if (objectId == 2439 && value5 == 2945 && value32 == 3042 || objectId == 2438 && value5 == 2945 && value32 == 3041) {
            if (value42 != 10 && value42 != 11) {
                if (player.getQuestState(62) == 3 || player.getQuestState(62) == 4) {
                    Player player6 = player;
                    player6.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2945 ? 1 : -1, 0, true);
                    player6 = player;
                    player6.packetSender.openDoubleDoorPair(2439, 2945, 3042, 2438, 2945, 3041);
                } else if (player.getQuestState(62) == 2 && player.getInventoryManager().containsItem(4004)) {
                    Npc npc = Npc.findByDefinitionId(675);
                    npc.queueScriptedPath(new Position[]{new Position(2944, 3040, 0)});
                    DialogueManager.continueDialogue(player, 675, 2, 0);
                } else {
                    Npc npc = Npc.findByDefinitionId(675);
                    npc.queueScriptedPath(new Position[]{new Position(2944, 3040, 0)});
                    DialogueManager.continueDialogue(player, 675, 100, 0);
                }
            } else if (player.getPosition().getX() < 2945 && value42 == 10) {
                Npc npc = Npc.findByDefinitionId(675);
                npc.queueScriptedPath(new Position[]{new Position(2944, 3040, 0)});
                DialogueManager.continueDialogue(player, 675, 2, 0);
            } else {
                Player player7 = player;
                player7.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2945 ? 1 : -1, 0, true);
                player7 = player;
                player7.packetSender.openDoubleDoorPair(2439, 2945, 3042, 2438, 2945, 3041);
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 2435 && value2 == 2476 && value32 == 3465 && value42 == 8 && !player.ownsItem(785)) {
            player.getDialogueManager().showItemMessage("You've found Glough's Journal!", new ItemStack(785, 1));
            player.getInventoryManager().addOrDropItem(new ItemStack(785, 1));
            return true;
        }
        return false;
    }

    private static boolean isTwigItem(int itemId) {
        return itemId == 789 || itemId == 790 || itemId == 791 || itemId == 792;
    }

    private boolean areAllTwigsPlaced(Player player) {
        GroundItemManager.getInstance();
        if (GroundItemManager.findVisibleItem(player, 789, this.twig789PlacementPosition) != null) {
            GroundItemManager.getInstance();
            if (GroundItemManager.findVisibleItem(player, 790, this.twig790PlacementPosition) != null) {
                GroundItemManager.getInstance();
                if (GroundItemManager.findVisibleItem(player, 791, this.twig791PlacementPosition) != null) {
                    GroundItemManager.getInstance();
                    if (GroundItemManager.findVisibleItem(player, 792, this.twig792PlacementPosition) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (GrandTreeQuest.isTwigItem(objectId) && value2 == 2440) {
            GroundItemManager.getInstance();
            if (GroundItemManager.findVisibleItemAt(player, this.twig789PlacementPosition) == null) {
                player.getInventoryManager().removeItem(new ItemStack(objectId, 1));
                GroundItem groundItem = new GroundItem(new ItemStack(objectId, 1), player, this.twig789PlacementPosition);
                GroundItemManager.getInstance().spawn(groundItem);
                if (this.areAllTwigsPlaced(player)) {
                    player.getDialogueManager().showOneLineStatement("You can hear the grinding of an ancient pulley system.");
                    player.setQuestState(this.getQuestId(), 14);
                }
            } else {
                player.packetSender.sendGameMessage("Theres already something on the table.");
            }
            return true;
        }
        if (GrandTreeQuest.isTwigItem(objectId) && value2 == 2441) {
            GroundItemManager.getInstance();
            if (GroundItemManager.findVisibleItemAt(player, this.twig790PlacementPosition) == null) {
                player.getInventoryManager().removeItem(new ItemStack(objectId, 1));
                GroundItem groundItem = new GroundItem(new ItemStack(objectId, 1), player, this.twig790PlacementPosition);
                GroundItemManager.getInstance().spawn(groundItem);
                if (this.areAllTwigsPlaced(player)) {
                    player.getDialogueManager().showOneLineStatement("You can hear the grinding of an ancient pulley system.");
                    player.setQuestState(this.getQuestId(), 14);
                }
            } else {
                player.packetSender.sendGameMessage("Theres already something on the table.");
            }
            return true;
        }
        if (GrandTreeQuest.isTwigItem(objectId) && value2 == 2442) {
            GroundItemManager.getInstance();
            if (GroundItemManager.findVisibleItemAt(player, this.twig791PlacementPosition) == null) {
                player.getInventoryManager().removeItem(new ItemStack(objectId, 1));
                GroundItem groundItem = new GroundItem(new ItemStack(objectId, 1), player, this.twig791PlacementPosition);
                GroundItemManager.getInstance().spawn(groundItem);
                if (this.areAllTwigsPlaced(player)) {
                    player.getDialogueManager().showOneLineStatement("You can hear the grinding of an ancient pulley system.");
                    player.setQuestState(this.getQuestId(), 14);
                }
            } else {
                player.packetSender.sendGameMessage("Theres already something on the table.");
            }
            return true;
        }
        if (GrandTreeQuest.isTwigItem(objectId) && value2 == 2443) {
            GroundItemManager.getInstance();
            if (GroundItemManager.findVisibleItemAt(player, this.twig792PlacementPosition) == null) {
                player.getInventoryManager().removeItem(new ItemStack(objectId, 1));
                GroundItem groundItem = new GroundItem(new ItemStack(objectId, 1), player, this.twig792PlacementPosition);
                GroundItemManager.getInstance().spawn(groundItem);
                if (this.areAllTwigsPlaced(player)) {
                    player.getDialogueManager().showOneLineStatement("You can hear the grinding of an ancient pulley system.");
                    player.setQuestState(this.getQuestId(), 14);
                }
            } else {
                player.packetSender.sendGameMessage("Theres already something on the table.");
            }
            return true;
        }
        if (objectId == 788 && value2 == 2436 && player.getInventoryManager().containsItem(788) && value32 == 12 && !player.ownsItem(794)) {
            GloughChestScrollFindTask gloughChestScrollFindTask = new GloughChestScrollFindTask(this, 2, player);
            player.setActionLocked(true);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2437, 2482, 3462, 1, 0, 10, 2436, 2), true);
            World.getTaskScheduler().schedule(gloughChestScrollFindTask);
            return true;
        }
        return false;
    }

    private static void clearBookInterfaceText(Player player) {
        Player player2 = player;
        player2.packetSender.sendInterfaceText("", 14165);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 14166);
        int value = 843;
        while (value <= 864) {
            player2 = player;
            player2.packetSender.sendInterfaceText("", value);
            ++value;
        }
    }

    private void showGloughJournalPage(Player player, int value3) {
        String[] stringValues;
        GrandTreeQuest.clearBookInterfaceText(player);
        stringValues = value3 == 0 ? (stringValues = new String[]{"Journal", "", "", "@red@The migration failed!", "", "After spending half a", "century hiding", "underground you would", "think that the great", "migration would have", "improved life on", "RuneScape for tree", "gnomes. However, rather", "than the great liberation", "promised to us by King", "Healthorg at the end of", "the last age, we have been", "forced to live in hiding,", "up trees or in the gnome", "maze, laughed at and", "mocked by man. Living in", "constant fear of human", "aggression, we are in a", "no better situation now", "than when we lived in the"}) : (value3 == 1 ? (stringValues = new String[]{"Journal", "", "", "caves! Change must come", "soon!", "", "@red@They must be stopped!", "", "Today I heard of three", "more gnomes slain by", "Khazard's human troops", "for fun, I can't control", "my anger! Humanity", "seems to have acquired a", "level of arrogance", "comparable to that of", "Zamorak, killing and", "pillaging at will! We are", "small and at heart not", "warriors but something", "must be done! We will", "pick up arms and go", "forth into the human", "world! We will defend", "ourselves and we will"}) : (value3 == 2 ? (stringValues = new String[]{"Journal", "", "", "pursue justice for all", "gnomes who fell at the", "hands of humans!", "", "@red@Gaining support.", "", "Some of the local gnomes", "seem strangely deluded", "about humans, many", "actually believe that", "humans are not all", "naturally evil but instead", "vary from person to", "person. This sort of talk", "could be the end for the", "tree gnomes and I must", "continue to convince my", "fellow gnome folk the cold", "truth about these human", "creatures! How they will", "not stop until all gnome", "life is destroyed! Unless"}) : (value3 == 3 ? (stringValues = new String[]{"Journal", "", "", "we can destroy them", "first!"}) : null)));
        Player player2 = player;
        player2.packetSender.sendInterfaceText((String)stringValues[0], 903);
        player2 = player;
        player2.packetSender.sendInterfaceText((String)stringValues[1], 14165);
        player2 = player;
        player2.packetSender.sendInterfaceText(stringValues[2], 14166);
        int value2 = 3;
        while (value2 < stringValues.length) {
            player2 = player;
            player2.packetSender.sendInterfaceText(stringValues[value2], value2 + 843 - 3);
            ++value2;
        }
        player2 = player;
        player2.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == 0 ? 1 : 0, 840);
        player2 = player;
        player2.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == this.gloughJournalLastPageIndex ? 1 : 0, 842);
    }

    private void showTranslationBookPage(Player player, int value3) {
        String[] stringValues;
        GrandTreeQuest.clearBookInterfaceText(player);
        stringValues = value3 == 0 ? (stringValues = new String[]{"Gnome-English Translation", "", "", "Gnome-English", "Translation", "", "written by Anita", "", "This text contains the", "ancient Gnome words I", "have managed to translate", "thus far.", "", "", "-A-", "arpos: rocks", "ando: gate", "andra: city", "ataris: cow", "", "-C-", "cef: threat", "cheray: lazy", "Cinqo: King", "cretor: bucket"}) : (value3 == 1 ? (stringValues = new String[]{"Gnome-English Translation", "", "", "-E-", "eis: me", "es: a", "et: and", "eto: will", "", "-G-", "gandius: jungle", "Gal: All", "gentis: leaf", "gutus: banana", "gomondo: branch", "", "-H-", "har: old", "harij: harpoon", "hewo: grass", "", "-I-", "ip: you", "imindus: quest", "irno: translate"}) : (value3 == 2 ? (stringValues = new String[]{"Gnome-English Translation", "", "", "", "-K-", "kar: no", "kai: boat", "ko: sail", "", "-L-", "lauf: eye", "laquinay: common sense", "lemanto: man", "lemantolly: stupid man", "lovos: gave", "", "-M-", "meso: came", "meris: kill", "mina: time(s)", "mos: coin", "mis: I", "mond: seal", "", "-P-"}) : (value3 == 3 ? (stringValues = new String[]{"Gnome-English Translation", "", "", "por: long", "prit: with", "priw: tree", "pro: to", "", "-Q-", "Qui: guard", "Quir: guardian", "", "-R-", "rentos: agility", "", "-S-", "sarko: Begone", "sind: big", "", "-T-", "ta: the", "tuzo: open", "", "-U-", "undri: lands"}) : (value3 == 4 ? (stringValues = new String[]{"Gnome-English Translation", "", "", "umesco: Soul"}) : null))));
        Player player2 = player;
        player2.packetSender.sendInterfaceText((String)stringValues[0], 903);
        player2 = player;
        player2.packetSender.sendInterfaceText(stringValues[1], 14165);
        player2 = player;
        player2.packetSender.sendInterfaceText(stringValues[2], 14166);
        int value2 = 3;
        while (value2 < stringValues.length) {
            player2 = player;
            player2.packetSender.sendInterfaceText(stringValues[value2], value2 + 843 - 3);
            ++value2;
        }
        player2 = player;
        player2.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == 0 ? 1 : 0, 840);
        player2 = player;
        player2.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == this.translationBookLastPageIndex ? 1 : 0, 842);
    }

    @Override
    public final boolean handleButtonClick(Player player, int buttonId, int value2) {
        if (player.activeBookItemId == 785) {
            if (buttonId == 841 && player.activeBookPageIndex < this.gloughJournalLastPageIndex) {
                ++player.activeBookPageIndex;
                this.showGloughJournalPage(player, player.activeBookPageIndex);
                return true;
            }
            if (buttonId == 839 && player.activeBookPageIndex > 0) {
                --player.activeBookPageIndex;
                this.showGloughJournalPage(player, player.activeBookPageIndex);
                return true;
            }
        }
        if (player.activeBookItemId == 784) {
            if (buttonId == 841 && player.activeBookPageIndex < this.translationBookLastPageIndex) {
                ++player.activeBookPageIndex;
                this.showTranslationBookPage(player, player.activeBookPageIndex);
                return true;
            }
            if (buttonId == 839 && player.activeBookPageIndex > 0) {
                --player.activeBookPageIndex;
                this.showTranslationBookPage(player, player.activeBookPageIndex);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214) {
            if (value2 == 786) {
                Player player2 = player;
                player.packetSender.sendInterfaceText("", 1139);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1140);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1141);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1142);
                player2 = player;
                player2.packetSender.sendInterfaceText("@yel@Es lemanto meso pro eis prit ta Cinqo mond.", 1143);
                player2 = player;
                player2.packetSender.sendInterfaceText("@yel@Mi lovos ta lemanto Daconia arpos", 1144);
                player2 = player;
                player2.packetSender.sendInterfaceText("@yel@et Daconia arpos eto meriz ta priw!", 1145);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1146);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1147);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1148);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1149);
                player2 = player;
                player2.packetSender.sendInterfaceText("", 1150);
                player2 = player;
                player2.packetSender.showInterface(1136);
                return true;
            }
            if (value2 == 785) {
                this.showGloughJournalPage(player, 0);
                player.activeBookItemId = value2;
                player.activeBookPageIndex = 0;
                Player player3 = player;
                player3.packetSender.showInterface(837);
                return true;
            }
            if (value2 == 787) {
                Player player4 = player;
                player.packetSender.sendInterfaceText("", 1139);
                player4 = player;
                player4.packetSender.sendInterfaceText("", 1140);
                player4 = player;
                player4.packetSender.sendInterfaceText("@red@Karamja Shipyard", 1141);
                player4 = player;
                player4.packetSender.sendInterfaceText("", 1142);
                player4 = player;
                player4.packetSender.sendInterfaceText("@gre@Order", 1143);
                player4 = player;
                player4.packetSender.sendInterfaceText("", 1144);
                player4 = player;
                player4.packetSender.sendInterfaceText("@yel@Order for 30 Karamja battleships", 1145);
                player4 = player;
                player4.packetSender.sendInterfaceText("@yel@Lumber required: 2000", 1146);
                player4 = player;
                player4.packetSender.sendInterfaceText("@yel@Troop capacity: 300", 1147);
                player4 = player;
                player4.packetSender.sendInterfaceText("", 1148);
                player4 = player;
                player4.packetSender.sendInterfaceText("", 1149);
                player4 = player;
                player4.packetSender.sendInterfaceText("", 1150);
                player4 = player;
                player4.packetSender.showInterface(1136);
                return true;
            }
            if (value2 == 794) {
                Player player5 = player;
                player.packetSender.sendInterfaceText("@red@Invasion", 1139);
                player5 = player;
                player5.packetSender.sendInterfaceText("", 1140);
                player5 = player;
                player5.packetSender.sendInterfaceText("@yel@Troops board three fleets of battleships at Karamja.", 1141);
                player5 = player;
                player5.packetSender.sendInterfaceText("@gre@Fleet 1", 1142);
                player5 = player;
                player5.packetSender.sendInterfaceText("@yel@Attacks Misthalin from the south.", 1143);
                player5 = player;
                player5.packetSender.sendInterfaceText("@gre@Fleet 2", 1144);
                player5 = player;
                player5.packetSender.sendInterfaceText("@yel@Groups at Crandor and attacks Asgarnia from the west.", 1145);
                player5 = player;
                player5.packetSender.sendInterfaceText("@gre@Fleet 3", 1146);
                player5 = player;
                player5.packetSender.sendInterfaceText("@yel@Sails north to attack Kandarin from south, reinforced", 1147);
                player5 = player;
                player5.packetSender.sendInterfaceText("@yel@by gnome foot soldiers leaving gnome stronghold.", 1148);
                player5 = player;
                player5.packetSender.sendInterfaceText("", 1149);
                player5 = player;
                player5.packetSender.sendInterfaceText("@red@Take no prisoners!", 1150);
                player5 = player;
                player5.packetSender.showInterface(1136);
                return true;
            }
            if (value2 == 784) {
                this.showTranslationBookPage(player, 0);
                player.activeBookItemId = value2;
                player.activeBookPageIndex = 0;
                Player player6 = player;
                player6.packetSender.showInterface(837);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 677) {
            player.setQuestState(this.getQuestId(), 15);
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player npcId, int npcId2, int value5, int value32, int value42) {
        if (npcId2 == 670 && this.kingNarnodeIntroArea.containsExclusiveOnPlane(((Entity)npcId).getPosition())) {
            if (value42 == 0) {
                if (!GrandTreeQuest.hasRequiredAgilityLevel((Player)npcId)) {
                    return false;
                }
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Welcome Traveller. I am King Narnode. It's nice to", "see an outsider.", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hi! It seems to be a busy settlement.", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("For now.", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("You seem worried, what's up?", "I'll be off now.");
                    return true;
                }
                if (value5 == 5) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You seem worried, what's up?", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(6);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Traveller, Can I speak to you in strictest confidence?", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Of course sire.", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Not here, follow me.", 591);
                    return true;
                }
                if (value5 == 9) {
                    Npc npc;
                    if (((Entity)npcId).getInteractionTarget() != null && ((Entity)npcId).getInteractionTarget().isNpc() && (npc = (Npc)((Entity)npcId).getInteractionTarget()).getNpcId() == 670) {
                        npc.getMovementQueue().addStep(new Position(2464, 3498, 0));
                        npc.getUpdateState().setForcedText("Down here.");
                    }
                    this.startQuest((Player)npcId);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (!(value42 != 3 || value5 != 1 || ((Player)npcId).ownsItem(783) && ((Player)npcId).ownsItem(784))) {
                if (!((Player)npcId).ownsItem(783)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(783, 1));
                }
                if (!((Player)npcId).ownsItem(784)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(784, 1));
                }
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Don't lose them this time!", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 4) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello again, your highness.", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hello Traveller, did you speak to Hazelmere?", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes! I managed to find him.", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Do you understand what he said?", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("I think so!", "No, I need to go back.");
                    return true;
                }
                if (value5 == 6) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I think so!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(7);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("So what did he say?", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showFiveOptions("King Narnode must be stopped, he is a madman!", "Praise be to the great Zamorak!", "Do you have any bread? I do like bread.", "The time has come to attack!", "None of the above.");
                    return true;
                }
                if (value5 == 9) {
                    if (value32 == 0) {
                        value32 = 5;
                    }
                    if (value32 == 5) {
                        ((Player)npcId).getDialogueManager().showFiveOptions("The tree is fine, you have nothing to fear.", "You must come and see me!", "The tree needs watering as there has been drought.", "Grave danger lies ahead, only the bravest will linger.", "None of the above.");
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                if (value5 == 10) {
                    if (value32 == 0) {
                        value32 = 5;
                    }
                    if (value32 == 5) {
                        ((Player)npcId).getDialogueManager().showFiveOptions("Time is of the essence! We must move quickly.", "A man came to me with the King's seal.", "There is no need for haste, just send a runner.", "You must act now or we will all die!", "None of the above.");
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value5 == 11) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("A man came to me with the King's seal.", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                if (value5 == 12) {
                    ((Player)npcId).getDialogueManager().showFiveOptions("I gave the man Daconia rocks.", "You must use force!", "Use a bucket of milk from a scared cow.", "Take this banana to him, he will understand.", "None of the above.");
                    return true;
                }
                if (value5 == 13) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I gave the man Daconia rocks.", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(12);
                    return true;
                }
                if (value5 == 14) {
                    ((Player)npcId).getDialogueManager().showFourOptions("All with be fine on the third night.", "You must wait till the second night.", "Nothing will help us now!", "And Daconia rocks will kill the tree!");
                    return true;
                }
                if (value5 == 15) {
                    if (value32 == 4) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("And Daconia rocks will kill the tree!", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(14);
                    return true;
                }
                if (value5 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Of course! I should've known Someone must've forged", "my royal seal. Hazelmere thought I sent him for the", "Daconia stones!", 591);
                    return true;
                }
                if (value5 == 17) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What are Daconia stones?", 591);
                    return true;
                }
                if (value5 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hazelmere created the Daconia stones. They are a", "safety measure, in case the tree grew out of control.", "They're the only thing that can kill the tree.", 591);
                    return true;
                }
                if (value5 == 19) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("This is terrible! The stones must be recovered!", 591);
                    return true;
                }
                if (value5 == 20) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can I help?", 591);
                    return true;
                }
                if (value5 == 21) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("First I must warn the tree guardians. Please, could", "you tell the chief tree guardian Glough. He lives in a", "tree house just in front of the Grand Tree.", 591);
                    return true;
                }
                if (value5 == 22) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("If he's not there he will be at his girlfriend Anita's place.", "Meet me back here once you've told him.", 591);
                    return true;
                }
                if (value5 == 23) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("OK! I'll be back soon.", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 5);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 6) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Hello, your highness. Have you any news on the", "Daconia stones?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("It's OK Traveller, thanks to Glough! He found a", "human sneaking around! He had three Daconia rocks", "on him!", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Wow! That was quick!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Yes Glough really knows what he's doing. The human", "has been detained until we know who else is involved.", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Maybe Glough was right, maybe humans are invading!", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I doubt it, can I speak to the prisoner?", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Certainly. He's on the top level of the tree. Be careful,", "it's a long way down!", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 7);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 11) {
                if (value5 == 1 && ((Player)npcId).getInventoryManager().containsItem(787)) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("King Narnode, I need to talk!", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Traveller, what are you doing here? The stronghold has", "been put on full alert! It's not safe for you here!", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Your highness, I believe Glough is killing the trees in", "order to make a mass fleet of warships!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("That's an absurd accusation!", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("His hatred for humanity is stronger than you know!", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("That's enough Traveller, you sound as paranoid as him!", "Traveller please leave! It's bad enough having one", "human locked up.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 12) {
                if (value5 == 1 && ((Player)npcId).getInventoryManager().containsItem(794)) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hi, your highness, did you think about what I said?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Look, if you're right about Glough I would have him", "arrested but there's no reason for me to think he's", "lying.", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Look, I found this at Glough's home!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showItemMessage("You give the King the invasion plans.", new ItemStack(794, 1));
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("If these are to be believed then this is terrible!", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("But it's not proof, any one could have made these.", "Traveller, I understand your concern, I had guards", "search Glough's house but they found nothing", "suspicious, just these odd twigs.", 591);
                    return true;
                }
                if (value5 == 7) {
                    if (((Player)npcId).getInventoryManager().getContainer().getFreeSlots() < 4) {
                        ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Hmm. Looks like you can't carry anymore. Put 4", "things down and come back to me.", 591);
                        ((Player)npcId).getDialogueManager().finishDialogue();
                    } else {
                        ((Player)npcId).getDialogueManager().showItemMessage("The King has given you some twigs lashed together.", new ItemStack(789, 1));
                    }
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("On the other hand, if Glough's right about the humans", "we will need an army of gnomes to protect ourselves.", "So I've decided to allow Glough to raise a mighty", "gnome army.", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("The Grand Tree's still slowly dying, if it is human", "sabotage we must respond!", 591);
                    return true;
                }
                if (value5 == 10) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(792, 1));
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(790, 1));
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(791, 1));
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(789, 1));
                    ((Player)npcId).setQuestState(this.getQuestId(), 13);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
            if (!(value42 != 13 || value5 != 1 || ((Player)npcId).ownsItem(792) && ((Player)npcId).ownsItem(790) && ((Player)npcId).ownsItem(791) && ((Player)npcId).ownsItem(789))) {
                if (!((Player)npcId).ownsItem(792)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(792, 1));
                }
                if (!((Player)npcId).ownsItem(790)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(790, 1));
                }
                if (!((Player)npcId).ownsItem(791)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(791, 1));
                }
                if (!((Player)npcId).ownsItem(789)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(789, 1));
                }
                ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I should probably be more careful and not lose", "these this time.", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId2 == 670 && this.grandTreeUndergroundArea.containsExclusive(((Entity)npcId).getPosition()) || npcId2 == 2781 && value5 > 1) {
            if (!(value42 != 3 || value5 != 1 || ((Player)npcId).ownsItem(783) && ((Player)npcId).ownsItem(784))) {
                if (!((Player)npcId).ownsItem(783)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(783, 1));
                }
                if (!((Player)npcId).ownsItem(784)) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(784, 1));
                }
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Don't lose them this time!", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 2) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("So what is this place?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("These, my friend, are the foundations of the stronghold.", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("They look like roots to me.", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Not just any roots Traveller! These were created by", "gnome mages eons ago, since then they have grown to", "become a mighty stronghold!", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Impressive. What exactly is the problem?", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("In the last two months our tree guardians have", "reported continuing deterioration of the Grand Tree's", "health. I've never seen this before! It could be the end", "for us all!", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You mean the tree is ill?", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("In effect yes. Would you be willing to help us discover", "what is happening to the tree?", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showTwoOptions("I'm sorry, I don't want to get involved.", "I'd be happy to help!");
                    return true;
                }
                if (value5 == 10) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I'd be happy to help!", 591);
                        ((Player)npcId).getDialogueManager().setNextDialogueStep(11);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Thank Guthix for your arrival!", 591);
                    return true;
                }
                if (value5 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("The first task is to find out what's killing the tree.", 591);
                    return true;
                }
                if (value5 == 13) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Do you have an idea?", 591);
                    return true;
                }
                if (value5 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("My top tree guardian, Glough, believes it's human", "sabotage. I'm not so sure! The only way to know for", "sure is to talk to Hazelmere.", 591);
                    return true;
                }
                if (value5 == 15) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Who's Hazelmere?", 591);
                    return true;
                }
                if (value5 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Hazelmere is one of the mages that created the Grand", "Tree! He is the only one that has survived from that", "time. Take this bark sample to him, he will be able to", "help!", 591);
                    return true;
                }
                if (value5 == 17) {
                    ((Player)npcId).getDialogueManager().showItemMessage("The king has given you a sample of bark.", new ItemStack(783, 1));
                    return true;
                }
                if (value5 == 18) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("The mage only talks in the old tongue, you'll need this.", 591);
                    return true;
                }
                if (value5 == 19) {
                    ((Player)npcId).getDialogueManager().showItemMessage("The king has given you a translation book.", new ItemStack(784, 1));
                    return true;
                }
                if (value5 == 20) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What is it?", 591);
                    return true;
                }
                if (value5 == 21) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("It's a translation book, you'll need it to translate what", "Hazelmere says. Do this carefully! His words are our", "only hope! You'll find his dwellings high upon a towering", "hill, on an island east of Yanille.", 591);
                    return true;
                }
                if (value5 == 22) {
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(783, 1));
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(784, 1));
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I'll show you the way back up.", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 3);
                    return true;
                }
            }
            if (value42 == 3 && value5 == 23) {
                Npc npc;
                if (((Entity)npcId).getInteractionTarget() != null && ((Entity)npcId).getInteractionTarget().isNpc() && (npc = (Npc)((Entity)npcId).getInteractionTarget()).getNpcId() == 670) {
                    npc.getMovementQueue().addStep(new Position(2464, 9896, 0));
                    npc.getUpdateState().setForcedText("Up here.");
                }
                ((Player)npcId).getDialogueManager().finishDialogue();
                return false;
            }
            if (value42 == 15) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Traveller you're wounded! What happened?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("It's Glough! He set a demon on me!", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("What?! Glough?! With a demon?!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showPlayerThreeLineDialogue("Glough has a store of Daconia rocks further up the", "passage! He's been accessing the roots from a secret", "passage at his home.", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Never! Not Glough! He's a good gnome at heart!", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Guard!", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(2781);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Sire!", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Go and check out that passage!", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).setActionLocked(true);
                    ((Player)npcId).getPacketSender().showInterface(8677);
                    GrandTreeGuardPassageReportTask grandTreeGuardPassageReportTask = new GrandTreeGuardPassageReportTask(this, 5, (Player)npcId);
                    World.getTaskScheduler().schedule(grandTreeGuardPassageReportTask);
                    return true;
                }
                if (value5 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("We found Glough hiding under a horde of Daconia", "rocks!", 591);
                    return true;
                }
                if (value5 == 101) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("That's what I've been trying to tell you! Glough's been", "fooling you!", 591);
                    return true;
                }
                if (value5 == 102) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I..I don't know what to say! How could I have been so", "blind?!", 591);
                    return true;
                }
                if (value5 == 103) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Guard! Call off the military training!", 591);
                    return true;
                }
                if (value5 == 104) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("The humans are not attacking!", 591);
                    return true;
                }
                if (value5 == 105) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(2781);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes sir!", 591);
                    return true;
                }
                if (value5 == 106) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("You have my full apologies Traveller!", 591);
                    return true;
                }
                if (value5 == 107) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("And my gratitude!", 591);
                    return true;
                }
                if (value5 == 108) {
                    ((Player)npcId).getDialogueManager().setDialogueNpcId(670);
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("A reward will have to wait though, the tree is still dying!", "The guards are clearing Glough's rock supply now but", "there must be more Daconia hidden somewhere in the", "roots! Help us search, we have little time!", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 16);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 16) {
                if (value5 == 1 && ((Player)npcId).getInventoryManager().containsItem(793)) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Traveller, have you managed to find the Daconia?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Is this it?", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Yes! Excellent, well done!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showItemMessage("You give the King the Daconia rock.", new ItemStack(793, 1));
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("It's incredible, the tree's health is improving already! I", "don't know what to say, we owe you so much!", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("To think Glough had me fooled all along!", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("All that matters now is that humans and gnomes can", "live together in peace!", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I'll drink to that!", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("From now on I vow to make this stronghold a", "welcoming place for all! I'll grant you access to all our", "facilities.", 591);
                    return true;
                }
                if (value5 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Thanks!", 591);
                    return true;
                }
                if (value5 == 11) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I think!", 591);
                    return true;
                }
                if (value5 == 12) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("It should make your stay here easier. You can use the", "spirit tree to transport yourself, as well as the gnome", "glider. I also give you access to our mine.", 591);
                    return true;
                }
                if (value5 == 13) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Mine?", 591);
                    return true;
                }
                if (value5 == 14) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Very few know of the secret mine under the Grand", "Tree. If you push on the roots just to my north they", "will separate and let you pass.", 591);
                    return true;
                }
                if (value5 == 15) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Strange!", 591);
                    return true;
                }
                if (value5 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("That's magic trees for you!", 591);
                    return true;
                }
                if (value5 == 17) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("All the best Traveller and thanks again!", 591);
                    return true;
                }
                if (value5 == 18) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You too, your highness!", 591);
                    return true;
                }
                if (value5 == 19 && ((Player)npcId).getInventoryManager().containsItem(793)) {
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    ((Player)npcId).getInventoryManager().removeItem(new ItemStack(793, 1));
                    this.awardCompletionRewards((Player)npcId);
                    return true;
                }
            }
        }
        if (npcId2 == 670) {
            if (value42 == 9) {
                if (value5 == 100) {
                    ((Player)npcId).setActionLocked(false);
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Traveller please accept my apologies! Glough had no", "right to arrest you! I just think he's scared of humans", "Let me get you out of there.", 591);
                    return true;
                }
                if (value5 == 101) {
                    Npc npc;
                    if (((Entity)npcId).getInteractionTarget() != null && ((Entity)npcId).getInteractionTarget().isNpc() && (npc = (Npc)((Entity)npcId).getInteractionTarget()).getNpcId() == 670) {
                        npc.queueScriptedPath(new Position[]{new Position(2465, 3495, 3)});
                    }
                    ((Player)npcId).getPacketSender().openSingleDoor(3367, 2465, 3496, ((Entity)npcId).getPosition().getPlane());
                    ((Player)npcId).getPacketSender().queueRelativeMovementStep(1, 0, true);
                    ((Player)npcId).setQuestState(this.getQuestId(), 10);
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I don't think you can trust Glough, your highness. He", "seems to have an unnatural hatred for humans.", 591);
                    return true;
                }
            }
            if (value42 == 10) {
                if (value5 == 102) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I know he can be a bit extreme at times. But he's the", "best tree guardian I have, he has made the gnomes", "paranoid about humans though.", 591);
                    return true;
                }
                if (value5 == 103) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("I'm afraid Glough has placed guards on the front gate", "to stop you escaping! Let my glider pilot fly you away", "until things calm down around here.", 591);
                    return true;
                }
                if (value5 == 104) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Well, OK.", 591);
                    return true;
                }
                if (value5 == 105) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I'm sorry again Traveller!", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 669) {
            if (value42 == 4 && value5 == 1 && !((Player)npcId).ownsItem(786)) {
                ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(786, 1));
                ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I should probably be more careful and not lose", "the scroll this time.", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 3 && ((Player)npcId).getInventoryManager().containsItem(783)) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("The mage starts to speak but all you hear is:");
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Blah. Blah, blah, blah, blah...blah!", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showItemMessage("You give the bark sample to Hazelmere.", new ItemStack(783, 1));
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("The mage carefully examines the sample.");
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Blah, blah...Daconia...blah, blah.", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Can you write this down and I'll try and translate it?", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Blah, blah?", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showTwoLineStatement("You make a writing motion. The mage scribbles something down on a", "scroll.");
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showItemMessage("Hazelmere has given you the scroll.", new ItemStack(786, 1));
                    ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(786, 1));
                    ((Player)npcId).setQuestState(this.getQuestId(), 4);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId2 == 671) {
            if (value42 == 5) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("The gnome is munching on a worm hole.");
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Can I help human? Can't you see I'm eating?!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showOneLineStatement("The gnome continues to eat.");
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("The King asked me to inform you that the Daconia", "rocks have been taken!", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Surely not!", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Apparently a human took them from Hazelmere.", "Hazelmere believed him; he had the King's seal!", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I should've known! The humans are going to invade!", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Never!", 591);
                    return true;
                }
                if (value5 == 10) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("Your type can't be trusted! I'll take care of this! Go", "back to the King.", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 6);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 8 && ((Player)npcId).ownsItem(785)) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Glough! I don't know what you're up to but I know", "you paid Charlie to get those rocks!", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("You're fool human! You have no idea what's going on.", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("I know the Grand Tree's dying! And I think you're", "part of the reason.", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("How dare you accuse me! I'm the head tree guardian!", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Guards! Guards!", 591);
                    ((Player)npcId).setActionLocked(true);
                    Npc npc = new Npc(2781);
                    GameplayHelper.replaceOwnedRoamingNpcAtPosition((Player)npcId, npc, 2477, 3463, 1, -1, false, false);
                    npc.setMovementTarget((Entity)npcId);
                    World.getTaskScheduler().schedule(new GloughGuardArrestStartTask(this, 3, (Player)npcId, npc));
                    return true;
                }
            }
            if (value42 == 14) {
                if (value5 == 100) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("You really are becoming a headache! Well, at least now", "you can die knowing you were right, it will save me", "having to hunt you down like all the other human filth", "of RuneScape!", 591);
                    return true;
                }
                if (value5 == 101) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You're crazy Glough!", 591);
                    return true;
                }
                if (value5 == 102) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Bah! Well, soon you'll see, the gnome's are ready to", "fight, in three weeks this tree will be dead wood, in ten", "weeks it will be 30 battleships! Finally we will rid the", "world of the disease called humanity!", 591);
                    return true;
                }
                if (value5 == 103) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("What makes you think I'll let you get away with it?", 591);
                    return true;
                }
                if (value5 == 104) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Fool...meet my little friend!", 591);
                    return true;
                }
                if (value5 == 105) {
                    Object value2;
                    ((Player)npcId).setActionLocked(true);
                    if (((Entity)npcId).getInteractionTarget() != null && ((Entity)npcId).getInteractionTarget().isNpc() && ((Npc)(value2 = (Npc)((Entity)npcId).getInteractionTarget())).getNpcId() == 671) {
                        ((Npc)value2).queueScriptedPath(new Position[]{new Position(2484, 9864, 0)});
                    }
                    World.getTaskScheduler().schedule(new GloughDemonSpawnTask(this, 3, (Player)npcId));
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return false;
                }
            }
        }
        if (npcId2 == 2781 && value42 == 8 && value5 == 100) {
            ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Come with me!", 591);
            int questId = this.getQuestId();
            World.getTaskScheduler().schedule(new GrandTreeGuardPrisonEscortTask(this, 3, (Player)npcId, questId));
            return true;
        }
        if (npcId2 == 673) {
            if (value42 == 9) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("So they got you as well?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("It's Glough! He's trying to cover something up.", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I shouldn't tell you this adventurer.", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("But if you want to get to the bottom of this you should", "go and talk to the Karamja Shipyard foreman.", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Why?", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcFourLineDialogue("Glough sent me to Karamja to meet him. I delivered a", "large amount of gold. For what? I don't know. He may", "be able to tell you what Glough's up to. That's if you", "can get out of here. You'll find him in the", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Karamja Shipyard, east of Shilo village. Be careful! If he", "discovers you're not working for Glough there'll be", "trouble! The sea men use the passwords Ka-Lu-Min.", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Thanks Charlie!", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    ((Player)npcId).setActionLocked(true);
                    Npc npc = new Npc(670);
                    GameplayHelper.replaceOwnedRoamingNpcAtPosition((Player)npcId, npc, 2467, 3496, 3, -1, false, false);
                    npc.queueScriptedPath(new Position[]{new Position(2465, 3496, 3)});
                    World.getTaskScheduler().schedule(new KingNarnodePrisonReleaseTask(this, 3, (Player)npcId, npc));
                    return false;
                }
            }
            if (value42 == 7) {
                if (value5 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Tell me. Why would you want to kill the Grand Tree?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("What do you mean?!", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerTwoLineDialogue("Don't tell me, you just happened to be caught carrying", "Daconia rocks!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("All I know is that I did what I was asked.", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I don't understand.", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Glough paid me to go to this gnome on a hill. I gave", "the gnome a seal and he gave me some rocks to give to", "Glough.", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I've been doing it for weeks, this time though Glough", "locked me up here! I just don't understand it.", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Sounds like Glough is hiding something!", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcTwoLineDialogue("I don't know what he's up to. If you want to find out", "you'd better search his home.", 591);
                    return true;
                }
                if (value5 == 10) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("OK. Thanks Charlie.", 591);
                    return true;
                }
                if (value5 == 11) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Good luck!", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 8);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 11) {
                if (value5 == 1 && ((Player)npcId).ownsItem(787)) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("How are you doing Charlie?", 591);
                    return true;
                }
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I've been better.", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Glough has some plan to rule RuneScape!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I wouldn't put it past him, the Gnome's crazy!", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I need some proof to convince the King.", 591);
                    return true;
                }
                if (value5 == 6) {
                    ((Player)npcId).getDialogueManager().showNpcThreeLineDialogue("Hmm...you could be in luck! Before Glough had me", "locked up I heard him mention that he'd left his chest", "keys at his girlfriend's.", 591);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Where does she live?", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Just west of the toad swamp.", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("OK, I'll see what I can find.", 591);
                    ((Player)npcId).setQuestState(this.getQuestId(), 12);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        int regionId = GameUtil.getRegionId(((Entity)npcId).getPosition().getX(), ((Entity)npcId).getPosition().getY());
        if ((npcId2 == 3811 || npcId2 == 170 && regionId == 9782) && value42 == 10) {
            if (value5 == 1) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hi, the King said that you need to leave?", 591);
                return true;
            }
            if (value5 == 2) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Apparently humans are invading!", 591);
                return true;
            }
            if (value5 == 3) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I find that hard to believe! I have lots of human friends.", 591);
                return true;
            }
            if (value5 == 4) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I don't understand it either!", 591);
                return true;
            }
            if (value5 == 5) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("So where to?", 591);
                return true;
            }
            if (value5 == 6) {
                ((Player)npcId).getDialogueManager().showTwoOptions("Take me to Karamja please!", "Not anywhere for now!");
                return true;
            }
            if (value5 == 7) {
                if (value32 == 1) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Take me to Karamja please!", 591);
                    return true;
                }
                ((Player)npcId).getDialogueManager().finishDialogue();
                return false;
            }
            if (value5 == 8) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("OK! You're the boss! Hold on tight, it'll be a rough ride!", 591);
                return true;
            }
            if (value5 == 9) {
                ((Player)npcId).moveTo(new Position(2917, 3057, 0));
                ((Player)npcId).getDialogueManager().finishDialogue();
                return false;
            }
        }
        if (npcId2 == 675) {
            if (value5 == 100) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hey! You can't go in there!", 591);
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 10) {
                if (value5 == 2) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hey you! What are you up to?", 591);
                    return true;
                }
                if (value5 == 3) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I'm trying to open the gate!", 591);
                    return true;
                }
                if (value5 == 4) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("I can see that! Why?", 591);
                    return true;
                }
                if (value5 == 5) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("I'm from the Ministry of Health and Safety.", "Glough sent me.", "I'm just looking around.");
                    return true;
                }
                if (value5 == 6) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Glough sent me.", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value5 == 7) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Hmm...really? What for?", 591);
                    return true;
                }
                if (value5 == 8) {
                    ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("You're wasting my time! Take me to your superior!", 591);
                    return true;
                }
                if (value5 == 9) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("OK. Password.", 591);
                    return true;
                }
                if (value5 == 10) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Ka.", "Ko.", "Ke.");
                    return true;
                }
                if (value5 == 11) {
                    if (value32 == 1) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Ka.", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                if (value5 == 12) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Lo.", "Lu.", "Le.");
                    return true;
                }
                if (value5 == 13) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Lu.", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(12);
                    return true;
                }
                if (value5 == 14) {
                    ((Player)npcId).getDialogueManager().showThreeOptions("Mon.", "Min.", "Men.");
                    return true;
                }
                if (value5 == 15) {
                    if (value32 == 2) {
                        ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Min.", 591);
                        return true;
                    }
                    ((Player)npcId).getDialogueManager().showOneLineStatement("This option is currently missing...");
                    ((Player)npcId).getDialogueManager().setNextDialogueStep(14);
                    return true;
                }
                if (value5 == 16) {
                    ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Sorry to have kept you.", 591);
                    ((Player)npcId).getDialogueManager().finishDialogue();
                    ((Player)npcId).setQuestState(this.getQuestId(), 11);
                    return true;
                }
            }
        }
        if (npcId2 == 672 && value42 == 12 && !((Player)npcId).ownsItem(788) && !((Player)npcId).ownsItem(794)) {
            if (value5 == 1) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Hello there.", 591);
                return true;
            }
            if (value5 == 2) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Oh hello, I've seen you with the King.", 591);
                return true;
            }
            if (value5 == 3) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Yes, I'm helping him with a problem.", 591);
                return true;
            }
            if (value5 == 4) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("You must know my boy friend Glough then?", 591);
                return true;
            }
            if (value5 == 5) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("Indeed!", 591);
                return true;
            }
            if (value5 == 6) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Could you do me a favour?", 591);
                return true;
            }
            if (value5 == 7) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("I suppose so.", 591);
                return true;
            }
            if (value5 == 8) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Please give this key to Glough, he left it here last night.", 591);
                return true;
            }
            if (value5 == 9) {
                ((Player)npcId).getDialogueManager().showItemMessage("Anita gives you a key.", new ItemStack(788, 1));
                return true;
            }
            if (value5 == 10) {
                ((Player)npcId).getDialogueManager().showNpcOneLineDialogue("Thanks a lot.", 591);
                return true;
            }
            if (value5 == 11) {
                ((Player)npcId).getDialogueManager().showPlayerOneLineDialogue("No...thank you!", 591);
                ((Player)npcId).getInventoryManager().addOrDropItem(new ItemStack(788, 1));
                ((Player)npcId).getDialogueManager().finishDialogue();
                return true;
            }
        }
        return false;
    }
}

