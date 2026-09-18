package com.rs2.model.quest.impl;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestConstants;
import com.rs2.model.quest.QuestScript;
import com.rs2.util.GameUtil;

public final class TutorialQuest
extends QuestScript {
    public TutorialQuest(int value2) {
        super(0);
    }

    @Override
    public final boolean refreshQuestJournalStatus(Player player, int value2) {
        if (value2 == 1) {
            return false;
        }
        player.advanceTutorialStage();
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        if (objectId == 3014 && value2 == 3098 && value32 == 3107) {
            if (value42 == 5) {
                Player player2 = player;
                player2.packetSender.queueRelativeMovementStep(1, 0, true);
                player2 = player;
                player2.packetSender.openSingleDoor(3014, 3098, 3107, player.getPosition().getPlane());
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3016 && value2 == 3089 && value32 == 3091 || objectId == 3015 && value2 == 3089 && value32 == 3092) {
            if (value42 == 15) {
                Player player3 = player;
                player3.packetSender.queueRelativeMovementStep(-1, 0, true);
                player3 = player;
                player3.packetSender.openDoubleDoorPair(3015, 3089, 3092, 3016, 3089, 3091);
                player3 = player;
                player3.packetSender.sendSoundEffect(318, 1, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3017 && value2 == 3079 && value32 == 3084) {
            if (value42 == 16) {
                Player player4 = player;
                player4.packetSender.queueRelativeMovementStep(-1, 0, true);
                player4 = player;
                player4.packetSender.openSingleDoor(3017, 3079, 3084, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3018 && value2 == 3072 && value32 == 3090) {
            if (value42 >= 21) {
                Player player5 = player;
                player5.packetSender.queueRelativeMovementStep(-1, 0, true);
                player5 = player;
                player5.packetSender.openSingleDoor(3018, 3072, 3090, 0);
                if (value42 == 21) {
                    player5 = player;
                    player5.packetSender.sendEntityHintIcon(1, -1);
                    player.advanceTutorialStage();
                }
                return true;
            }
            return true;
        }
        if (objectId == 3019 && value2 == 3086 && value32 == 3126) {
            if (value42 == 24) {
                Player player6 = player;
                player6.packetSender.queueRelativeMovementStep(0, -1, true);
                player6 = player;
                player6.packetSender.openSingleDoor(3019, 3086, 3126, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3029 && value2 == 3088 && value32 == 3119) {
            if (value42 == 28) {
                AttackStyleDefinition.startDelayedObjectMove(player, new Position(3088, 9520, 0));
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3021 && value2 == 3094 && value32 == 9502 || objectId == 3020 && value2 == 3094 && value32 == 9503) {
            if (value42 == 39) {
                Player player7 = player;
                player7.packetSender.queueRelativeMovementStep(1, 0, true);
                player7 = player;
                player7.packetSender.openDoubleDoorPair(3020, 3094, 9503, 3021, 3094, 9502);
                player7 = player;
                player7.packetSender.sendSoundEffect(318, 1, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3022 && value2 == 3111 && value32 == 9518 || objectId == 3023 && value2 == 3111 && value32 == 9519) {
            if (value42 >= 46 && value42 <= 50) {
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3111 ? 1 : -1, 0, true);
                player8 = player;
                player8.packetSender.openWestShiftedDoubleDoorPair(3022, 3023, 3111, 9518, 3111, 9519, 0);
                if (value42 == 46) {
                    player.advanceTutorialStage();
                }
                return true;
            }
            return true;
        }
        if (objectId == 3030 && value2 == 3111 && value32 == 9526) {
            if (value42 == 50) {
                AttackStyleDefinition.startDelayedObjectMove(player, new Position(3111, 3125, 0));
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3024 && value2 == 3125 && value32 == 3124) {
            if (value42 == 52) {
                Player player9 = player;
                player9.packetSender.queueRelativeMovementStep(1, 0, true);
                player9 = player;
                player9.packetSender.openSingleDoor(3024, 3125, 3124, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3025 && value2 == 3130 && value32 == 3124) {
            if (value42 == 54) {
                Player player10 = player;
                player10.packetSender.queueRelativeMovementStep(1, 0, true);
                player10 = player;
                player10.packetSender.openSingleDoor(3025, 3130, 3124, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        if (objectId == 3026 && value2 == 3122 && value32 == 3102) {
            if (value42 == 61) {
                Player player11 = player;
                player11.packetSender.queueRelativeMovementStep(0, -1, true);
                player11 = player;
                player11.packetSender.openSingleDoor(3026, 3122, 3102, 0);
                player.advanceTutorialStage();
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public final void refreshQuestJournal(Player itemStackArray, int value3) {
        Object value2 = itemStackArray;
        itemStackArray.packetSender.setSidebarInterface(QuestConstants.LOGOUT_TAB_INTERFACE[0], QuestConstants.LOGOUT_TAB_INTERFACE[1]);
        if (value3 < 6) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 0);
        }
        value2 = itemStackArray;
        itemStackArray.packetSender.setInterfaceHiddenFlag(1, 12224);
        value2 = itemStackArray;
        itemStackArray.packetSender.setInterfaceHiddenFlag(1, 12225);
        value2 = itemStackArray;
        itemStackArray.packetSender.setInterfaceHiddenFlag(1, 12226);
        value2 = itemStackArray;
        itemStackArray.packetSender.setInterfaceHiddenFlag(1, 12227);
        value2 = itemStackArray;
        itemStackArray.packetSender.setInterfaceHiddenFlag(0, 12161);
        value2 = itemStackArray;
        itemStackArray.packetSender.sendInterfaceText("% Done", 12224);
        if (ServerSettings.cacheVersion > 289) {
            value2 = itemStackArray;
            itemStackArray.packetSender.showWalkableInterface(8680);
        }
        if (value3 >= 3) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.OPTIONS_TAB_INTERFACE[0], QuestConstants.OPTIONS_TAB_INTERFACE[1]);
        }
        if (value3 >= 7) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.INVENTORY_TAB_INTERFACE[0], QuestConstants.INVENTORY_TAB_INTERFACE[1]);
        }
        if (value3 >= 10) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.STATS_TAB_INTERFACE[0], QuestConstants.STATS_TAB_INTERFACE[1]);
        }
        if (value3 >= 20) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.MUSIC_TAB_INTERFACE[0], QuestConstants.MUSIC_TAB_INTERFACE[1]);
        }
        if (value3 >= 22) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.EMOTES_TAB_INTERFACE[0], QuestConstants.EMOTES_TAB_INTERFACE[1]);
        }
        if (value3 >= 26) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.QUEST_TAB_INTERFACE[0], QuestConstants.QUEST_TAB_INTERFACE[1]);
        }
        if (value3 >= 41) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.EQUIPMENT_TAB_INTERFACE[0], QuestConstants.EQUIPMENT_TAB_INTERFACE[1]);
        }
        if (value3 >= 45) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.COMBAT_TAB_INTERFACE[0], QuestConstants.COMBAT_TAB_INTERFACE[1]);
            itemStackArray.getEquipmentManager().refreshWeaponInterface();
        }
        if (value3 >= 56) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.PRAYER_TAB_INTERFACE[0], QuestConstants.PRAYER_TAB_INTERFACE[1]);
        }
        if (value3 >= 58) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.FRIENDS_TAB_INTERFACE[0], QuestConstants.FRIENDS_TAB_INTERFACE[1]);
        }
        if (value3 >= 59) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.IGNORE_TAB_INTERFACE[0], QuestConstants.IGNORE_TAB_INTERFACE[1]);
        }
        if (value3 >= 63) {
            value2 = itemStackArray;
            itemStackArray.packetSender.setSidebarInterface(QuestConstants.MAGIC_TAB_INTERFACE[0], QuestConstants.MAGIC_TAB_INTERFACE[1]);
        }
        if (value3 >= 6 && value3 < 12) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 2);
        }
        if (value3 >= 12 && value3 < 16) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 3);
        }
        if (value3 >= 16 && value3 < 20) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 4);
        }
        if (value3 >= 20 && value3 < 22) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 5);
        }
        if (value3 >= 22 && value3 < 25) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 6);
        }
        if (value3 >= 25 && value3 < 29) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 7);
        }
        if (value3 >= 29 && value3 < 35) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 8);
        }
        if (value3 >= 35 && value3 < 40) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 9);
        }
        if (value3 >= 40 && value3 < 46) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 10);
        }
        if (value3 >= 46 && value3 < 49) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 11);
        }
        if (value3 >= 49 && value3 < 51) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 12);
        }
        if (value3 >= 51 && value3 < 53) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 13);
        }
        if (value3 >= 53 && value3 < 55) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 14);
        }
        if (value3 >= 55 && value3 < 60) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 15);
        }
        if (value3 >= 60 && value3 < 62) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 16);
        }
        if (value3 >= 62 && value3 < 64) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 17);
        }
        if (value3 >= 64) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendConfig(406, 20);
        }
        if (value3 == 0) {
            value2 = itemStackArray;
            itemStackArray.packetSender.showInterface(3559);
            if (!itemStackArray.ownsItem(995)) {
                itemStackArray.getBankContainer().addToTab(new ItemStack(995, 25), 0);
            }
        }
        if (value3 == 0 || value3 == 2) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(945).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Getting Started", "To start the tutorial use your left mouse-button to click on the", "'Guide' in this room. He is indicated by a flashing", "yellow arrow above his head. If you can't see him, use your", "keyboard's arrow keys to rotate the view.", true);
        }
        if (value3 == 3) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.OPTIONS_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Player controls", "Please click on the flashing spanner icon found at the bottom", "right of your screen. This will display your player controls.", "", true);
        }
        if (value3 == 4) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(945).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Player controls", "On the side panel, you can now see a variety of options from", "changing the brightness of the screen and the volume of", "music, to selecting whether your player should accept help", "from other players. Don't worry about these too much for now.", true);
        }
        if (value3 == 5) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3098, 3107, 130, 3);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Interacting with scenery", "You can interact with many items of the scenery by simply clicking", "on them. Right clicking will also give more options. Feel free to", "try it with the things in this room, then click on the door", "indicated with the yellow arrow to go through.", true);
        }
        if (value3 == 6) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(943).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Moving around", "Follow the path to find the next instructor. Clicking on the", "ground will walk you to that point. Talk to the Survival Expert by", "the pond to continue the tutorial. Remember you can rotate", "the view by pressing the arrow keys.", true);
        }
        if (value3 == 7) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.INVENTORY_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Viewing the items that you were given.", "Click on the flashing backpack icon to the right hand side of", "the main window to view your inventory. Your inventory is a list", "of everything you have in your backpack.", "", true);
        }
        if (value3 == 8) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3100, 3095, 170, 3);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Cut down a tree", "You can click on the backpack icon at any time to view the", "items that you currently have in your inventory. You will see", "that you now have an axe in your inventory. Use this to get", "some logs by clicking on one of the trees in the area.", true);
        }
        if (value3 == 9) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Making a fire", "Well done! You managed to cut some logs from the tree! Next,", "use the tinderbox in your inventory to light the logs.", "First click on the tinderbox to 'use' it.", "Then click on the logs in your inventory to light them.", true);
        }
        if (value3 == 10) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.STATS_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "You gained some experience.", "Click on the flashing bar graph icon near the inventory button", "to see your skill stats.", "", true);
        }
        if (value3 == 11) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(943).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Your skill stats.", "Here you will see how good your skills are. As you move your", "mouse over any of the icons in this panel, the small yellow", "popup box will show you the exact amount of experience you", "have and how much you need. Speak to Brynna to continue. ", true);
        }
        if (value3 == 12) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3101, 3092, 70, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Catch some Shrimp.", "Click on the sparkling fishing spot, indicated by the flashing", "arrow. Remember, you can check your inventory by clicking the", "backpack icon.", "", true);
        }
        if (value3 == 13) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Cooking your shrimp.", "Now you have caught some shrimp, let's cook it. First light a", "fire: chop down a tree and then use the tinderbox on the logs.", "If you've lost your axe or tinderbox Brynna will give you", "another.", true);
        }
        if (value3 == 14) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Burning your shrimp.", "You have just burnt your first shrimp. This is normal. As you", "get more experience in Cooking, you will burn stuff less often.", "Let's try cooking without burning it this time. First catch some", "more shrimp, then use them on a fire.", true);
        }
        if (value3 == 15) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3089, 3091, 120, 4);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Well done, you've just cooked your first RuneScape meal.", "If you'd like a recap on anything you've learnt so far, speak to", "the Survival Expert. You can now move on to the next", "instructor. Click on the gate shown and follow the path.", "Remember, you can move the camera with the arrow keys.", true);
        }
        if (value3 == 16) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3079, 3084, 130, 3);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Find your next instructor.", "Follow the path until you get to the door with the yellow arrow", "above it. Click on the door to open it. Notice the mini-map in", "the top right; this shows a top down view of the area around", "you. This can also be used for navigation.", true);
        }
        if (value3 == 17) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(942).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Find your next instructor.", "Talk to the chef indicated. He will teach you the more advanced", "aspects of Cooking such as combining ingredients. He will also", "teach you about your music player menu as well.", "", true);
        }
        if (value3 == 18) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Making dough.", "This is the base for many of the meals. To make dough we must", "mix flour and water. First, right click the bucket of water and", "select use, then left click on the pot of flour.", "", true);
        }
        if (value3 == 19) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3076, 3081, 100, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Cooking dough.", "Now you have made dough, you can cook it. To cook the dough,", "use it with the range shown by the arrow. If you lose your", "dough, talk to Lev - he will give you more ingredients.", "", true);
        }
        if (value3 == 20) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.MUSIC_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Cooking dough.", "Well done! Your first loaf of bread. As you gain experience in", "Cooking, you will be able to make other things like pies, cakes", "and even kebabs. Now you've got the hang of cooking, let's", "move on. Click on the flashing icon in the bottom right.", true);
        }
        if (value3 == 21) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3073, 3090, 130, 3);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("The music player.", "From this interface you can control the music that is played.", "As you explore the world, more of the tunes will become", "unlocked. Once you've examined this menu use the next door", "to continue. If you need a recap talk to the Master Chef.", true);
        }
        if (value3 == 22) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.EMOTES_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("It's only a short distance to the next guide", "", "Why not try running there. Start by opening the player", "controls, that's the flashing icon of a running man.", "", true);
        }
        if (value3 == 23) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Running.", "In this menu you will see many options from waving to walking.", "At the top of the panel there are two buttons. One is walk the", "other one is run. Click the run button.", "", true);
        }
        if (value3 == 24) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3086, 3126, 130, 5);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Run to the next guide.", "Now that you have the run turned on follow the path, until you", "come to the end. You may notice that your energy left goes", "down. If this reaches zero you'll stop running. Click on the door", "to pass through it.", true);
        }
        if (value3 == 25) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(949).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Talk with the Quest Guide.", "", "He will tell you all about quests.", "", true);
        }
        if (value3 == 26) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.QUEST_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Open the Quest Journal.", "", "Click on the flashing icon next to your inventory.", "", true);
        }
        if (value3 == 27) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(949).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Your Quest Journal.", "", "This is your Quest Journal, a list of all the quests in the game.", "Talk to the Quest Guide again for an explanation.", "", true);
        }
        if (value3 == 28) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3088, 3119, 100, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Moving on.", "It's time to enter some caves. Click on the ladder to go down to", "the next area.", "", true);
        }
        if (value3 == 29) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(948).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Mining and Smithing.", "Next let's get you a weapon, or more to the point, you can", "make your first weapon yourself. Don't panic, the Mining", "Instructor will help you. Talk to him and he'll tell you all about it.", "", true);
        }
        if (value3 == 30) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3076, 9504, 70, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Prospecting.", "To prospect a mineable rock, just right click it and select the", "'prospect rock' option. This will tell you the type of ore you can", "mine from it. Try it now on one of the rocks indicated.", "", true);
        }
        if (value3 == 31) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3086, 9501, 70, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("It's tin.", "", "So now you know there's tin in the grey rocks, try prospecting", "the brown ones next.", "", true);
        }
        if (value3 == 32) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(948).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("It's copper.", "Talk to the Mining Instructor to find out about these types of", "ore and how you can mine them. He'll even give you the", "required tools.", "", true);
        }
        if (value3 == 33) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3076, 9504, 70, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Mining.", "It's quite simple really. All you need to do is right click on the", "rock and select 'mine'. You can only mine when you have a", "pickaxe. So give it a try: first mine one tin ore.", "", true);
        }
        if (value3 == 34) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3086, 9501, 70, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Mining.", "Now you have some tin ore you just need some copper ore,", "then you'll have all you need to create a bronze bar. As you", "did before right click on the copper rock and select 'mine'.", "", true);
        }
        if (value3 == 35) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3079, 9496, 120, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Smelting.", "You should now have both some copper and tin ore. So let's", "smelt them to make a bronze bar. To do this, right click on", "either tin or copper ore and select use then left click on the", "furnace. Try it now.", true);
        }
        if (value3 == 36) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(948).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("You've made a bronze bar!", "", "Speak to the Mining Instructor and he'll show you how to make", "it into a weapon.", "", true);
        }
        if (value3 == 37) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3083, 9499, 70, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Smithing a dagger.", "To smith you'll need a hammer - like the one you were given by", "Dezzick - access to an anvil like the one with the arrow over it", "and enough metal bars to make what you are trying to smith.", "To start the process, use the bar on one of the anvils.", true);
        }
        if (value3 == 38) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Smithing a dagger.", "Now you have the Smithing menu open, you will see a list of all", "the things you can make. Only the dagger can be made at your", "skill level; this is shown by the white text under it. You'll need", "to select the dagger to continue.", true);
        }
        if (value3 == 39) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3094, 9502, 120, 4);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("You've finished in this area.", "So let's move on. Go through the gates shown by the arrow.", "Remember, you may need to move the camera to see your", "surroundings. Speak to the guide for a recap at any time.", "", true);
        }
        if (value3 == 40) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(944).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Combat.", "", "In this area you will find out about combat with swords and", "bows. Speak to the guide and he will tell you all about it.", "", true);
        }
        if (value3 == 41) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.EQUIPMENT_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Wielding weapons.", "", "You now have access to a new interface. Click on the flashing", "icon of a man, the one to the right of your backpack icon.", "", true);
        }
        if (value3 == 42) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("This is your worn inventory.", "From here you can see what items you have equipped. Let's", "get one of those slots filled, go back to your inventory", "and right click your dagger, select wield from the menu.", "", true);
        }
        if (value3 == 43) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(944).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("You're now holding your dagger.", "Clothes, armour, weapons and many other items are equipped", "like this. You can unequip items by clicking on the item in the", "worn inventory. Speak to the Combat Instructor to continue.", "", true);
        }
        if (value3 == 44) {
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Unequipping items.", "In your worn inventory panel, right click on the dagger and", "select remove option from the drop down list. After you've", "unequipped the dagger, wield the sword and shield. As you", "pass the mouse over an item you will see it's name.", true);
        }
        if (value3 == 45) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.COMBAT_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Combat Interface.", "", "Click on the flashing crossed swords icon to see the combat", "interface.", "", true);
        }
        if (value3 == 46) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3110, 9518, 120, 4);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("This is your combat interface.", "From this interface you can select the type of attack your", "character will use. Different monsters have different", "weaknesses. Now you have the tools needed for battle why", "not slay some rats. Click on the gate indicated to continue.", true);
        }
        if (value3 == 47) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(950).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Attacking.", "", "To attack the rat, right click it and select the attack option. You", "will then walk over to it and start hitting it.", "", true);
        }
        if (value3 == 48) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(944).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Well done, you've made your first kill!", "", "Pass through the gate and talk to the Combat Instructor; he", "will give you your next task.", "", true);
        }
        if (value3 == 49) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(950).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Rat ranging.", "Now you have a bow and some arrows. Before you can use", "them you'll need to equip them. Once equipped with the", "ranging gear, try killing another rat. Remember: to attack, right", "click on the monster and select attack.", true);
        }
        if (value3 == 50) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3111, 9526, 100, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Moving on.", "You have completed the tasks here. To move on, click on the", "ladder shown. If you need to go over any of what you learnt", "here, just talk to the Combat Instructor and he'll tell you what", "he can.", true);
        }
        if (value3 == 51) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3122, 3124, 100, 2);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Banking.", "Follow the path and you will come to the front of the building.", "This is the Bank of Runescape, where you can store all your", "most valued items. To open your bank box just right click on an", "open booth indicated and select 'use'.", true);
        }
        if (value3 == 52) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3125, 3124, 130, 3);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("This is your bank box.", "You can store stuff here for safekeeping. If you die, anything", "in your bank will be saved. To deposit something, right click it", "and select 'store'. Once you've had a good look, close the", "window and move on through the door indicated.", true);
        }
        if (value3 == 53) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(947).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Financial advice.", "", "The guide here will tell you all about making cash. Just click on", "him to hear what he's got to say.", "", true);
        }
        if (value3 == 54) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3130, 3124, 130, 3);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "", "Continue through the next door.", "", "", true);
        }
        if (value3 == 55) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(954).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Prayer.", "Follow the path to the chapel and enter it.", "Once inside talk to the monk. He'll tell you all about the Prayer", "skill.", "", true);
        }
        if (value3 == 56) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.PRAYER_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Your Prayer menu.", "", "Click on the flashing icon to open the Prayer menu.", "", "", true);
        }
        if (value3 == 57) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(954).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Your Prayer menu.", "", "Talk with Brother Brace and he'll tell you about prayers.", "", true);
        }
        if (value3 == 58) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.FRIENDS_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Friends list.", "You should now see another new icon. Click on the flashing", "smiling face to open your friends list.", "", true);
        }
        if (value3 == 59) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.IGNORE_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("This is your friends list.", "", "This will be explained by Brother Brace shortly, but first click", "on the other flashing face to the right of your screen.", "", true);
        }
        if (value3 == 60) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(954).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("This is your ignore list.", "The two lists - friends and ignore - can be very helpful for", "keeping track of when your friends are online or for blocking", "messages from people you simply don't like. Speak with", "Brother Brace and he will tell you more.", true);
        }
        if (value3 == 61) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendPositionHintIcon(3122, 3102, 130, 6);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "Your final instructor!", "You're almost finished on tutorial island. Pass through the", "door to find the path leading to your final instructor.", "", true);
        }
        if (value3 == 62) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(946).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Your final instructor!", "Just follow the path to the Wizard's house, where you will be", "shown how to cast spells. Just talk with the mage indicated to", "find out more.", "", true);
        }
        if (value3 == 63) {
            value2 = itemStackArray;
            itemStackArray.packetSender.flashSidebarIcon(QuestConstants.MAGIC_TAB_INTERFACE[0]);
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Open up your final menu.", "", "Open up the Magic menu by clicking on the flashing icon next", "to the Prayer button you just learned about.", "", true);
        }
        if (value3 == 64) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(946).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("", "This is where all of your magic spells are.", "Talk to Terrova to learn more.", "", "", true);
        }
        if (value3 == 65) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(951).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Cast Wind Strike at a chicken.", "Now you have runes you should see the Wind Strike icon at the", "top left corner of the Magic interface - first in from the", "left. Walk over to the caged chickens, click the Wind Strike icon", "and then select one of the chickens to cast it on.", true);
        }
        if (value3 == 66) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(951).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("Cast Wind Strike on a chicken.", "That's it, you cast a spell! Sadly it didn't have any effect this time, but", "the more you practice, the better you'll get. Repeat this process until", "you successfully cast the spell. Click the Wind Strike icon again and", "then select one of the chickens.", true);
        }
        if (value3 == 67) {
            value2 = itemStackArray;
            itemStackArray.packetSender.sendEntityHintIcon(1, Npc.findByDefinitionId(946).getIndex());
            itemStackArray.getDialogueManager().showTutorialInstructionOverlay("You have almost completed the tutorial!", "", "All you need to do now is move on to the mainland. Just speak", "with Terrova and he'll teleport you to Lumbridge Castle.", "", true);
        }
        if (value3 == 68) {
            value2 = itemStackArray;
            itemStackArray.packetSender.closeInterface(-1);
            value2 = itemStackArray;
            itemStackArray.packetSender.closeInterfaces();
            value2 = itemStackArray;
            itemStackArray.packetSender.showWalkableInterface(-1);
            itemStackArray.getDialogueManager().showFiveLineStatement("Welcome to Lumbridge! To get more help, simply click on the", "Lumbridge Guide and he will give you some tips.", "He can be found by looking for the question mark icon on", "your minimap. If you find that you are lost any time, look for", "other players, they might help you to make your way back.");
            Player player2 = itemStackArray;
            itemStackArray.getInventoryManager().getContainer().clear();
            player2.getEquipmentManager().getContainer().clear();
            player2.getBankContainer().clear();
            player2.getInventoryManager().refresh();
            player2.getEquipmentManager().refresh();
            player2.setAppearanceUpdateRequired(true);
            player2.getBankContainer().addToTab(new ItemStack(995, 25), 0);
            value2 = new ItemStack[]{new ItemStack(1351), new ItemStack(590), new ItemStack(303), new ItemStack(315), new ItemStack(1925), new ItemStack(1931), new ItemStack(2309), new ItemStack(1265), new ItemStack(1205), new ItemStack(1277), new ItemStack(1171), new ItemStack(841), new ItemStack(882, 25), new ItemStack(556, 25), new ItemStack(558, 15), new ItemStack(555, 6), new ItemStack(557, 4), new ItemStack(559, 2)};
            ItemStack[] itemStackArray2 = (ItemStack[])value2;
            int index = 0;
            while (index < 18) {
                value2 = itemStackArray2[index];
                player2.getInventoryManager().addItem((ItemStack)value2);
                ++index;
            }
            itemStackArray.setQuestState(0, 1);
            if (itemStackArray.loginRestrictionExempt) {
                ItemStack itemStack = new ItemStack(7956, 1);
                itemStackArray.getInventoryManager().addItem(itemStack);
                GameUtil.addTrackedRareItemAmount(itemStack);
                value2 = itemStackArray;
                itemStackArray.packetSender.sendGameMessage("You received a reward for participating the test week.");
            }
        }
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        if (npcId == 945) {
            if (value42 == 2) {
                if (!ServerSettings.tutorialSkipPromptEnabled && value2 < 3) {
                    value2 = 3;
                }
                if (value2 == 23 && value32 == 2) {
                    value2 = 20;
                    value32 = 0;
                }
                if (value2 == 20) {
                    player.getDialogueManager().showFourOptionsWithTitle("Choose your gamemode", "Normal", "<img=3>Ironman", "<img=4>Ultimate ironman", "<img=5>Hardcore ironman");
                    player.getDialogueManager().setNextDialogueStep(21);
                    return true;
                }
                if (value2 == 21) {
                    if (value32 == 1) {
                        player.getDialogueManager().showOneLineStatement("In normal mode you just play the game normally.");
                        player.pendingGameMode = 0;
                    }
                    if (value32 == 2) {
                        player.getDialogueManager().showOneLineStatement("In ironman mode you cannot interact with other players.");
                        player.pendingGameMode = 1;
                    }
                    if (value32 == 3) {
                        player.getDialogueManager().showTwoLineStatement("In ultimate ironman mode you cannot interact with other players,", "and also cannot use banks.");
                        player.pendingGameMode = 2;
                    }
                    if (value32 == 4) {
                        player.getDialogueManager().showTwoLineStatement("In hardcore ironman mode you cannot interact with other players,", "and only have 1 life.");
                        player.pendingGameMode = 3;
                    }
                    player.getDialogueManager().setNextDialogueStep(22);
                    return true;
                }
                if (value2 == 22) {
                    player.getDialogueManager().showTwoOptionsWithTitle("Choose this gamemode?", "Yes", "No");
                    return true;
                }
                if (value2 == 23 && value32 == 1) {
                    player.gameMode = player.pendingGameMode;
                    String text = "normal";
                    if (player.gameMode == 1) {
                        text = "<img=3>ironman";
                    } else if (player.gameMode == 2) {
                        text = "<img=4>ultimate ironman";
                    } else if (player.gameMode == 3) {
                        text = "<img=5>hardcore ironman";
                    }
                    player.getDialogueManager().showOneLineStatement("Your gamemode has been set to: " + text);
                    player.packetSender.sendAccountStatus();
                    player.pendingGameMode = 255;
                    if (ServerSettings.tutorialSkipPromptEnabled) {
                        player.getDialogueManager().setNextDialogueStep(1);
                    } else {
                        player.getDialogueManager().setNextDialogueStep(3);
                    }
                    return true;
                }
                if (value2 == 1) {
                    player.getDialogueManager().showTwoOptionsWithTitle("Would you like to skip tutorial?", "Yes.", "No.");
                    return true;
                }
                if (value2 == 2) {
                    if (value32 == 1) {
                        Player player2 = player;
                        player2.packetSender.sendEntityHintIcon(1, -1);
                        player.moveTo(new Position(3233, 3229, 0));
                        player.getDialogueManager().resetDialogueState();
                        player.getDialogueManager().finishDialogue();
                        player.completeTutorial();
                        return true;
                    }
                    value2 = 3;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Greetings! I see you are a new arrival to this land. My", "job is to welcome all new visitors. So welcome!", 588);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You have already learned the first thing needed to", "succeed in this world talking to other people!", 588);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("You will find many inhabitants of this world have useful", "things to say to you. By clicking on them with your", "mouse you can talk to them.", 588);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcFourLineDialogue("I would also suggest reading through some of the", "supporting information on the website. There you can", "find the Knowledge Base, which contains all the", "additional information you're ever likely to need. It also", 588);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("contains maps and helpful tips to help you on your", "journey.", 588);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("You will notice a flashing icon of a spanner, please click", "on this to continue the tutorial.", 588);
                    Player player3 = player;
                    player3.packetSender.sendEntityHintIcon(1, -1);
                    player.advanceTutorialStage();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 4) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("I'm glad you're making progress!", 588);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("To continue the tutorial go through that door over", "there and speak to your first instructor!", 588);
                    player.advanceTutorialStage();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 943) {
            if (value42 == 6) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcFourLineDialogue("Hello there, newcomer. My name is Brynna. My job is", "to teach you a few survival tips and tricks. First off", "we're going to start with the most basic survival skill of", "all: making a fire.", 588);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoItemMessage("The Survival Guide gives you a @dbl@tinderbox @bla@and a @dbl@bronze", "@dbl@axe@bla@!", new ItemStack(590, 1), new ItemStack(1351, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(1351, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(590, 1));
                    player.advanceTutorialStage();
                    Player player4 = player;
                    player4.packetSender.sendEntityHintIcon(1, -1);
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (!(value42 < 7 || value2 != 1 || player.ownsItem(1351) && player.ownsItem(590))) {
                player.getDialogueManager().showTwoItemMessage("The Survival Guide gives you a @dbl@tinderbox @bla@and a @dbl@bronze", "@dbl@axe@bla@!", new ItemStack(590, 1), new ItemStack(1351, 1));
                player.setInteractionTargetId(0);
                player.getInventoryManager().addOrDropItem(new ItemStack(1351, 1));
                player.getInventoryManager().addOrDropItem(new ItemStack(590, 1));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 11) {
                if (value2 == 1) {
                    Player player5 = player;
                    player5.packetSender.sendConfig(406, 3);
                    player.getDialogueManager().showNpcThreeLineDialogue("Well done! Next we need to get some food in our", "bellies. We'll need something to cook. There are shrimp", "in the pond there, so let's catch and cook some.", 588);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showItemMessage("The Survival Guide gives you a @dbl@net@bla@!", new ItemStack(303, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(303, 1));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 12 && value2 == 1 && !player.ownsItem(303)) {
                player.getDialogueManager().showItemMessage("The Survival Guide gives you a @dbl@net@bla@!", new ItemStack(303, 1));
                player.setInteractionTargetId(0);
                player.getInventoryManager().addOrDropItem(new ItemStack(303, 1));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 942) {
            if (value42 == 17) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Ah! Welcome, newcomer. I am the Master Chef, Lev. It", "is here I will teach you how to cook food truly fit for a", "king.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I already know how to cook. Brynna taught me just", "now.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Hahahahahaha! You call THAT cooking? Some shrimp", "on an open log fire? Oh, no, no, no. I am going to", "teach you the fine art of cooking bread.", 607);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("And no fine meal is complete without good music, so", "we'll cover that while you're here too.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showTwoItemMessage("The Cooking Guide gives you a @dbl@bucket of water @bla@and a", "@dbl@pot of flour@bla@!", new ItemStack(1929, 1), new ItemStack(1933, 1));
                    player.setInteractionTargetId(0);
                    Player player6 = player;
                    player6.packetSender.sendEntityHintIcon(1, -1);
                    player.getInventoryManager().addOrDropItem(new ItemStack(1933, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(1929, 1));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (!(value42 < 18 || value42 >= 20 || value2 != 1 || player.ownsItem(2307) || player.ownsItem(1933) && player.ownsItem(1929))) {
                player.getDialogueManager().showTwoItemMessage("The Cooking Guide gives you a @dbl@bucket of water @bla@and a", "@dbl@pot of flour@bla@!", new ItemStack(1929, 1), new ItemStack(1933, 1));
                player.setInteractionTargetId(0);
                player.getInventoryManager().addOrDropItem(new ItemStack(1933, 1));
                player.getInventoryManager().addOrDropItem(new ItemStack(1929, 1));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 949) {
            if (value42 == 25) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah. Welcome, adventurer. I'm here to tell you all about", "quests. Let's start by opening the quest side panel.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.advanceTutorialStage();
                    Player player7 = player;
                    player7.packetSender.sendEntityHintIcon(1, -1);
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    player7 = player;
                    player7.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 == 27) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Now you have the journal open I'll tell you a bit about", "it. At the moment all quests are shown in red, which", "means you have not started them yet.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcFourLineDialogue("When you start a quest it will change colour to yellow,", "and to green when you've finished. This is so you can", "easily see what's complete, what's started, and what's left", "to begin.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("The start of quests are easy to find. Look out for the", "star icons on the minimap, just like the one you should", "see marking my house.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcFourLineDialogue("The quests themselves can vary greatly from collecting", "beads to hunting down dragons. Generally quests are", "started by talking to a non-player character like me,", "and will involve a series of tasks.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcFourLineDialogue("There's not a lot more I can tell you about questing.", "You have to experience the thrill of it yourself to fully", "understand. You may find some adventure in the caves", "under my house.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    Player player8 = player;
                    player8.packetSender.closeInterfaces();
                    return true;
                }
            }
        }
        if (npcId == 948) {
            if (value42 == 29) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcFourLineDialogue("Hi there. You must be new around here. So what do I", "call you? Newcomer' seems so impersonal, and if we're", "going to be working together, I'd rather call you by", "name.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("You can call me " + player.getUsername() + ".", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ok then, " + player.getUsername() + ". My name is Dezzick and I'm a", "miner by trade. Let's prospect some of those rocks.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    Player player9 = player;
                    player9.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 == 32) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I prospected both types of rock! One set contains tin", "and the other has copper ore inside.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Absolutely right, " + player.getUsername() + ". These two ore types can", "be smelted together to make bronze.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("So now you know what ore is in the rocks over there,", "why don't you have a go at mining some tin and", "copper? Here, you'll need this to start with.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showItemMessage("Dezzick gives you a @dbl@bronze pickaxe@bla@!", new ItemStack(1265, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(1265, 1));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 33 && value2 == 1 && !player.ownsItem(1265)) {
                player.getDialogueManager().showItemMessage("Dezzick gives you a @dbl@bronze pickaxe@bla@!", new ItemStack(1265, 1));
                player.setInteractionTargetId(0);
                player.getInventoryManager().addOrDropItem(new ItemStack(1265, 1));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 36) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("How do I make a weapon out of this?", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Okay, I'll show you how to make a dagger out of it.", "You'll be needing this...", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showItemMessage("Dezzick gives you a @dbl@hammer@bla@!", new ItemStack(2347, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(2347, 1));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 37 && value2 == 1 && !player.ownsItem(2347)) {
                player.getDialogueManager().showItemMessage("Dezzick gives you a @dbl@hammer@bla@!", new ItemStack(2347, 1));
                player.setInteractionTargetId(0);
                player.getInventoryManager().addOrDropItem(new ItemStack(2347, 1));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 944) {
            if (value42 == 40) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hi! My name's " + player.getUsername() + ".", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Do I look like I care? To me you're just another", "newcomer who thinks they're ready to fight.", 595);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("I am Vannaka, the greatest swordsman alive.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcOneLineDialogue("Let's get started by teaching you to wield a weapon.", 591);
                    return true;
                }
                if (value2 == 5) {
                    Player player10 = player;
                    player10.packetSender.sendEntityHintIcon(1, -1);
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    player10 = player;
                    player10.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 == 43) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Very good, but that little butter knife isn't going to", "protect you much. Here, take these.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoItemMessage("The Combat Instructor gives you a @dbl@bronze sword @bla@and a", "@dbl@wooden shield@bla@!", new ItemStack(1277, 1), new ItemStack(1171, 1));
                    player.setInteractionTargetId(0);
                    Player player11 = player;
                    player11.packetSender.sendEntityHintIcon(1, -1);
                    player.getInventoryManager().addOrDropItem(new ItemStack(1171, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(1277, 1));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (!(value42 < 44 || value2 != 1 || player.ownsItem(1171) && player.ownsItem(1277))) {
                player.getDialogueManager().showTwoItemMessage("The Combat Instructor gives you a @dbl@bronze sword @bla@and a", "@dbl@wooden shield@bla@!", new ItemStack(1277, 1), new ItemStack(1171, 1));
                player.setInteractionTargetId(0);
                Player player12 = player;
                player12.packetSender.sendEntityHintIcon(1, -1);
                player.getInventoryManager().addOrDropItem(new ItemStack(1171, 1));
                player.getInventoryManager().addOrDropItem(new ItemStack(1277, 1));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 48) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I did it! I killed a giant rat!", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I saw, " + player.getUsername() + ". You seem better at this than I", "thought. Now that you have grasped basic swordplay,", "let's move on.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcFourLineDialogue("Let's try some ranged attacking, with this you can kill", "foes from a distance. Also, foes unable to reach you are", "as good as dead. You'll be able to attack the rats", "without entering the pit.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showTwoItemMessage("The Combat Instructor gives you some @dbl@bronze arrows @bla@and", "a @dbl@shortbow@bla@!", new ItemStack(882, 50), new ItemStack(841, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(841, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(882, 50));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 >= 49) {
                if (value2 == 1 && !player.ownsItem(841) && !player.ownsItem(882)) {
                    player.getDialogueManager().showTwoItemMessage("The Combat Instructor gives you some @dbl@bronze arrows @bla@and", "a @dbl@shortbow@bla@!", new ItemStack(882, 50), new ItemStack(841, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(841, 1));
                    player.getInventoryManager().addOrDropItem(new ItemStack(882, 50));
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1 && player.ownsItem(841) && !player.ownsItem(882)) {
                    player.getDialogueManager().showItemMessage("The Combat Instructor gives you some @dbl@bronze arrows@bla@!", new ItemStack(882, 50));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(882, 50));
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value2 == 1 && !player.ownsItem(841) && player.ownsItem(882)) {
                    player.getDialogueManager().showItemMessage("The Combat Instructor gives you a @dbl@shortbow@bla@!", new ItemStack(841, 1));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(841, 1));
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 947 && value42 == 53) {
            if (value2 == 1) {
                player.getDialogueManager().showPlayerOneLineDialogue("Hello. Who are you?", 591);
                return true;
            }
            if (value2 == 2) {
                player.getDialogueManager().showNpcTwoLineDialogue("I'm the Financial Advisor. I'm here to tell people how to", "make money.", 591);
                return true;
            }
            if (value2 == 3) {
                player.getDialogueManager().showPlayerOneLineDialogue("Okay. How can I make money then?", 591);
                return true;
            }
            if (value2 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("How you can make money? Quite.", 591);
                return true;
            }
            if (value2 == 5) {
                player.getDialogueManager().showNpcThreeLineDialogue("Well, there are three basic ways of making money here:", "combat, quests and trading. I will talk you through each", "of them very quickly.", 591);
                return true;
            }
            if (value2 == 6) {
                player.getDialogueManager().showNpcThreeLineDialogue("Let's start with combat as it is probably still fresh in", "your mind. Many enemies, both human and monster,", "will drop items when they die.", 591);
                return true;
            }
            if (value2 == 7) {
                player.getDialogueManager().showNpcThreeLineDialogue("Now, the next way to earn money quickly is by quests.", "Many people on RuneScape have things they need", "doing, which they will reward you for.", 591);
                return true;
            }
            if (value2 == 8) {
                player.getDialogueManager().showNpcThreeLineDialogue("By getting a high level in skills such as Cooking, Mining,", "Smithing or Fishing, you can create or catch your own", "items and sell them for pure profit.", 591);
                return true;
            }
            if (value2 == 9) {
                player.getDialogueManager().showNpcTwoLineDialogue("Well, that about covers it. Come back if you'd like to go", "over this again.", 591);
                return true;
            }
            if (value2 == 10) {
                player.advanceTutorialStage();
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                Player player13 = player;
                player13.packetSender.closeInterfaces();
                return true;
            }
        }
        if (npcId == 954) {
            if (value42 == 55) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Good day, brother, my name's " + player.getUsername() + ".", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Hello, " + player.getUsername() + ". I'm Brother Brace. I'm here to tell", "you all about Prayer.", 591);
                    return true;
                }
                if (value2 == 3) {
                    Player player14 = player;
                    player14.packetSender.sendEntityHintIcon(1, -1);
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    player14 = player;
                    player14.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 == 57) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("This is your Prayer list. Prayers can help a lot in", "combat. Click on the prayer you wish to use to activate", "it, and click it again to deactivate it.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Active prayers will drain your Prayer Points, which", "you can recharge by finding an altar or other holy spot", "and praying there.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcThreeLineDialogue("As you noticed, most enemies will drop bones when", "defeated. Burying bones, by clicking them in your", "inventory, will gain you Prayer experience.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("I'm also the community officer 'round here, so it's my", "job to tell you about your friends and ignore list.", 591);
                    return true;
                }
                if (value2 == 5) {
                    Player player15 = player;
                    player15.packetSender.sendEntityHintIcon(1, -1);
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    player15 = player;
                    player15.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 == 60) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcFourLineDialogue("Good. Now you have both menus open I'll tell you a", "little about each. You can add people to either list by", "clicking the add button then typing their name into the", "box that appears.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcFourLineDialogue("You remove people from the lists in the same way. If", "you add someone to your ignore list they will not be", "able to talk to you or send any form of message to", "you.", 591);
                    return true;
                }
                if (value2 == 3) {
                    player.getDialogueManager().showNpcFourLineDialogue("Your friends list shows the online status of your", "friends. Friends in red are offline, friends in green are", "online and on the same server and friends in yellow", "are online, but on a different server.", 591);
                    return true;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Are there rules on in-game behaviour?", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Yes, you should read the rules of conduct on the", "website to make sure you do nothing to get yourself", "banned.", 591);
                    return true;
                }
                if (value2 == 6) {
                    player.getDialogueManager().showNpcThreeLineDialogue("But in general, always try to be courteous to other", "players - remember the people in the game are real", "people with real feelings.", 591);
                    return true;
                }
                if (value2 == 7) {
                    player.getDialogueManager().showNpcTwoLineDialogue("If you go 'round being abusive or causing trouble your", "character could end up being the one in trouble.", 591);
                    return true;
                }
                if (value2 == 8) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Okay, thanks. I'll bear that in mind.", 591);
                    return true;
                }
                if (value2 == 9) {
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    Player player16 = player;
                    player16.packetSender.closeInterfaces();
                    return true;
                }
            }
        }
        if (npcId == 946) {
            if (value42 == 62) {
                if (value2 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Hello.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Good day, newcomer. My name is Terrova. I'm here", "to tell you about Magic. Let's start by opening your", "spell list.", 591);
                    return true;
                }
                if (value2 == 3) {
                    Player player17 = player;
                    player17.packetSender.sendEntityHintIcon(1, -1);
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    player17 = player;
                    player17.packetSender.closeInterfaces();
                    return true;
                }
            }
            if (value42 == 64) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Good. This is a list of your spells. Currently you can", "only cast one offensive spell called Wind Strike. Let's", "try it out on one of those chickens.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoItemMessage("Terrova gives you five @dbl@air runes @bla@and five @dbl@mind runes@bla@!", "", new ItemStack(556, 5), new ItemStack(558, 5));
                    player.setInteractionTargetId(0);
                    player.getInventoryManager().addOrDropItem(new ItemStack(556, 5));
                    player.getInventoryManager().addOrDropItem(new ItemStack(558, 5));
                    player.advanceTutorialStage();
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (!(value42 < 65 || value42 >= 67 || value2 != 1 || player.ownsItem(556) && player.ownsItem(558))) {
                player.getDialogueManager().showTwoItemMessage("Terrova gives you five @dbl@air runes @bla@and five @dbl@mind runes@bla@!", "", new ItemStack(556, 5), new ItemStack(558, 5));
                player.setInteractionTargetId(0);
                player.getInventoryManager().addOrDropItem(new ItemStack(556, 5));
                player.getInventoryManager().addOrDropItem(new ItemStack(558, 5));
                player.getDialogueManager().resetDialogueState();
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value42 == 67) {
                if (value2 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well you're all finished here now. I'll give you a", "reasonable number of runes when you leave.", 591);
                    return true;
                }
                if (value2 == 2) {
                    player.getDialogueManager().showTwoOptionsWithTitle("Do you want to go to the mainland?", "Yes.", "No.");
                    return true;
                }
                if (value2 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showNpcFourLineDialogue("When you get to the mainland you will find yourself in", "the town of Lumbridge. If you want some ideas on", "where to go next, talk to my friend the Lumbridge", "Guide. You can't miss him; he's holding a big staff with", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value2 == 4) {
                    player.getDialogueManager().showNpcFourLineDialogue("a question mark on the end. He also has a white beard", "and carries a rucksack full of scrolls. There are also", "many tutors willing to teach you about the many skills", "you could learn.", 591);
                    return true;
                }
                if (value2 == 5) {
                    player.getDialogueManager().showNpcThreeLineDialogue("If all else fails, visit the RuneScape website for a whole", "chestload of information on quests, skills and minigames", "as well as a very good starter's guide.", 591);
                    return true;
                }
                if (value2 == 6) {
                    Player player18 = player;
                    player18.packetSender.sendEntityHintIcon(1, -1);
                    player.moveTo(new Position(3233, 3229, 0));
                    player.getDialogueManager().resetDialogueState();
                    player.getDialogueManager().finishDialogue();
                    player.advanceTutorialStage();
                    return true;
                }
            }
        }
        return false;
    }
}

