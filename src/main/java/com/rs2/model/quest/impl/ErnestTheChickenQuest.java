package com.rs2.model.quest.impl;

import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.CompostHeapKeyFindTask;
import com.rs2.model.quest.impl.ErnestSecretDoorReturnTask;
import com.rs2.model.quest.impl.PoisonedFishFoodPiranhaTask;
import com.rs2.model.quest.impl.PouletmorphMachineStartTask;
import com.rs2.util.GameUtil;

public final class ErnestTheChickenQuest
extends QuestScript {
    public ErnestTheChickenQuest(int value2) {
        super(6);
        super.setQuestPointReward(4);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to Veronica who is", "outside Draynor Manor", "", "There aren't any requirements for this quest"};
        }
        if (value2 == 2) {
            return new String[]{"I should go into Draynor Manor and look for Ernest"};
        }
        if (value2 == 3 || value2 == 4) {
            return new String[]{"I should bring these items to Professor Oddenstein:", "Pressure gauge", "Rubber tube", "Oil can"};
        }
        if (value2 == 5) {
            return new String[]{"I should talk to Professor Oddenstein to", "finish this quest."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "4 Quest Points", "300 Coins"};
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
        player2.packetSender.sendInterfaceText("300 Coins", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getInventoryManager().addOrDropItem(new ItemStack(995, 300));
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 314);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    private static boolean hasAllMachineParts(Player player) {
        return player.getInventoryManager().containsItemAmount(271, 1) && player.getInventoryManager().containsItemAmount(276, 1) && player.getInventoryManager().containsItemAmount(277, 1);
    }

    public static void refreshBasementLeverDoorConfig(Player player) {
        if (player.configStates[33] == 4) {
            player.configStates[668] = 4;
        }
        if (player.configStates[33] == 6) {
            player.configStates[668] = 132;
        }
        if (player.configStates[33] == 14) {
            player.configStates[668] = 0;
        }
        if (player.configStates[33] == 30) {
            player.configStates[668] = 256;
        }
        if (player.configStates[33] == 22) {
            player.configStates[668] = 396;
        }
        if (player.configStates[33] == 20) {
            player.configStates[668] = 332;
        }
        if (player.configStates[33] == 16) {
            player.configStates[668] = 328;
        }
        if (player.configStates[33] == 80) {
            player.configStates[668] = 34;
        }
        if (player.configStates[33] == 48) {
            player.configStates[668] = 0;
        }
        if (player.configStates[33] == 112) {
            player.configStates[668] = 3;
        }
        if (player.configStates[33] == 96) {
            player.configStates[668] = 1;
        }
        if (player.configStates[33] == 120) {
            player.configStates[668] = 19;
        }
        if (player.configStates[33] == 104) {
            player.configStates[668] = 1;
        }
        if (player.configStates[33] == 56) {
            player.configStates[668] = 64;
        }
        if (player.configStates[33] == 88) {
            player.configStates[668] = 306;
        }
        if (player.configStates[33] == 0) {
            player.configStates[668] = 0;
        }
        Player player2 = player;
        player2.packetSender.sendConfig(668, player.configStates[668]);
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        if (player.getQuestState(this.getQuestId()) >= 3 && objectId == 274 && value2 == 153 && player.getInventoryManager().containsItem(274)) {
            PoisonedFishFoodPiranhaTask poisonedFishFoodPiranhaTask = new PoisonedFishFoodPiranhaTask(this, 1, player);
            Player player2 = player;
            player2.packetSender.sendGameMessage("You pour the poisoned fish food into the fountain.");
            player.getInventoryManager().removeItem(new ItemStack(274, 1));
            if (player.getQuestState(this.getQuestId()) == 3) {
                player.setQuestState(this.getQuestId(), 4);
                World.getTaskScheduler().schedule(poisonedFishFoodPiranhaTask);
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 153 && value52 == 3087 && value62 == 3334 && value8 == 1 && value72 == 4 && !player.ownsItem(271)) {
            if (value32 == 1) {
                player.getDialogueManager().showPlayerOneLineDialogue("There seems to be a pressure gauge in here...", 591);
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showPlayerOneLineDialogue("... and a lot of dead fish.", 591);
                return true;
            }
            if (value32 == 3) {
                player.getDialogueManager().showPlayerOneLineDialogue("You get the pressure gauge from the fountain.", 591);
                player.getInventoryManager().addOrDropItem(new ItemStack(271, 1));
                Player player2 = player;
                player2.packetSender.closeInterfaces();
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        int initialValue = 1;
        if (ServerSettings.cacheVersion >= 362) {
            initialValue = 2;
        }
        if (objectId == 134 && value2 == 3108 && value32 == 3353 || objectId == 135 && value2 == 3109 && value32 == 3353) {
            if (player.getPosition().getY() < 3354) {
                Player player2 = player;
                player2.packetSender.queueRelativeMovementStep(0, 1, true);
                player2 = player;
                player2.packetSender.openNorthShiftedDoubleDoorPair(134, 135, 3108, 3353, 3109, 3353, 0);
                player2 = player;
                player2.packetSender.sendGameMessage("The doors slam shut behind you.");
                return true;
            }
            Player player3 = player;
            player3.packetSender.sendGameMessage("The doors won't open.");
            return true;
        }
        if (objectId == 155 && value2 == 3097 && value32 == 3358 || objectId == 156 && value2 == 3097 && value32 == 3359) {
            if (player.getPosition().getX() >= 3098) {
                Player player4 = player;
                player4.packetSender.queueRelativeMovementStep(-2, 0, true);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(ServerSettings.placeholderObjectId, 3097, 3358, 0, 0, 10, 155, 2), true);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(ServerSettings.placeholderObjectId, 3097, 3359, 0, 0, 10, 156, 2), true);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(155, 3097, 3357, 0, 0, 10, 11474, 2), true);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(156, 3097, 3360, 0, 0, 10, 11512, 2), true);
                player4 = player;
                player4.packetSender.sendGameMessage("You've found a secret door!");
                player4 = player;
                player4.packetSender.sendSoundEffect(318, 1, 0);
                return true;
            }
            Player player5 = player;
            player5.packetSender.sendGameMessage("It won't open.");
            return true;
        }
        if (objectId == 160 && value2 == 3096 && value32 == 3357) {
            ErnestSecretDoorReturnTask ernestSecretDoorReturnTask = new ErnestSecretDoorReturnTask(this, 3, player);
            Player player6 = player;
            player6.packetSender.queueRelativeMovementStep(0, 1, true);
            player6 = player;
            player6.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player6 = player;
            player6.packetSender.sendGameMessage("The lever opens the secret door!");
            World.getTaskScheduler().schedule(ernestSecretDoorReturnTask);
            return true;
        }
        if (objectId == 136 && value2 == 3123 && value32 == 3361) {
            Player player7 = player;
            player7.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3361 ? 1 : -1, true);
            player7 = player;
            player7.packetSender.openSingleDoor(136, 3123, 3361, 0);
            return true;
        }
        if (objectId == 131 && value2 == 3107 && value32 == 3367) {
            if (player.getInventoryManager().containsItem(275)) {
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3108 ? 1 : -1, 0, true);
                player8 = player;
                player8.packetSender.openSingleDoor(131, 3107, 3367, 0);
                player8 = player;
                player8.packetSender.sendGameMessage("You unlock the door.");
                return true;
            }
            Player player9 = player;
            player9.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 152 && value2 == 3084 && value32 == 3360 && value42 >= 3 && !player.ownsItem(275)) {
            if (player.getInventoryManager().containsItem(952)) {
                CompostHeapKeyFindTask compostHeapKeyFindTask = new CompostHeapKeyFindTask(this, 2, player);
                player.getUpdateState().setAnimation(830);
                Player player10 = player;
                player10.packetSender.sendSoundEffect(232, 1, 0);
                player10 = player;
                player10.packetSender.sendGameMessage("You dig through the compost...");
                World.getTaskScheduler().schedule(compostHeapKeyFindTask);
                return true;
            }
            Player player11 = player;
            player11.packetSender.sendGameMessage("I need a spade to do that.");
            return true;
        }
        if (objectId == 133 && value2 == 3092 && value32 == 3362) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(3117, 9753, 0));
            player.configStates[33] = 0;
            Player player12 = player;
            player12.packetSender.sendConfig(33, 0);
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 132 && value2 == 3117 && value32 == 9754) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(3092, 3361, 0));
            return true;
        }
        if (objectId == 146 && value2 == 3108 && value32 == 9745) {
            Player player13;
            if ((player.configStates[33] & 2) != 0) {
                player.configStates[33] = player.configStates[33] - 2;
                player13 = player;
                player13.packetSender.sendGameMessage("You pull lever A up.");
            } else {
                player.configStates[33] = player.configStates[33] + 2;
                player13 = player;
                player13.packetSender.sendGameMessage("You pull lever A down.");
            }
            player13 = player;
            player13.packetSender.sendConfig(33, player.configStates[33]);
            player13 = player;
            player13.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player13 = player;
            player13.packetSender.sendGameMessage("You hear a clunk.");
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 147 && value2 == 3118 && value32 == 9752) {
            Player player14;
            if ((player.configStates[33] & 4) != 0) {
                player.configStates[33] = player.configStates[33] - 4;
                player14 = player;
                player14.packetSender.sendGameMessage("You pull lever B up.");
            } else {
                player.configStates[33] = player.configStates[33] + 4;
                player14 = player;
                player14.packetSender.sendGameMessage("You pull lever B down.");
            }
            player14 = player;
            player14.packetSender.sendConfig(33, player.configStates[33]);
            player14 = player;
            player14.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player14 = player;
            player14.packetSender.sendGameMessage("You hear a clunk.");
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 148 && value2 == 3112 && value32 == 9760) {
            Player player15;
            if ((player.configStates[33] & 8) != 0) {
                player.configStates[33] = player.configStates[33] - 8;
                player15 = player;
                player15.packetSender.sendGameMessage("You pull lever C up.");
            } else {
                player.configStates[33] = player.configStates[33] + 8;
                player15 = player;
                player15.packetSender.sendGameMessage("You pull lever C down.");
            }
            player15 = player;
            player15.packetSender.sendConfig(33, player.configStates[33]);
            player15 = player;
            player15.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player15 = player;
            player15.packetSender.sendGameMessage("You hear a clunk.");
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 149 && value2 == 3108 && value32 == 9767) {
            Player player16;
            if ((player.configStates[33] & 0x10) != 0) {
                player.configStates[33] = player.configStates[33] - 16;
                player16 = player;
                player16.packetSender.sendGameMessage("You pull lever D up.");
            } else {
                player.configStates[33] = player.configStates[33] + 16;
                player16 = player;
                player16.packetSender.sendGameMessage("You pull lever D down.");
            }
            player16 = player;
            player16.packetSender.sendConfig(33, player.configStates[33]);
            player16 = player;
            player16.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player16 = player;
            player16.packetSender.sendGameMessage("You hear a clunk.");
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 150 && value2 == 3097 && value32 == 9767) {
            Player player17;
            if ((player.configStates[33] & 0x20) != 0) {
                player.configStates[33] = player.configStates[33] - 32;
                player17 = player;
                player17.packetSender.sendGameMessage("You pull lever E up.");
            } else {
                player.configStates[33] = player.configStates[33] + 32;
                player17 = player;
                player17.packetSender.sendGameMessage("You pull lever E down.");
            }
            player17 = player;
            player17.packetSender.sendConfig(33, player.configStates[33]);
            player17 = player;
            player17.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player17 = player;
            player17.packetSender.sendGameMessage("You hear a clunk.");
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 151 && value2 == 3096 && value32 == 9765) {
            Player player18;
            if ((player.configStates[33] & 0x40) != 0) {
                player.configStates[33] = player.configStates[33] - 64;
                player18 = player;
                player18.packetSender.sendGameMessage("You pull lever F up.");
            } else {
                player.configStates[33] = player.configStates[33] + 64;
                player18 = player;
                player18.packetSender.sendGameMessage("You pull lever F down.");
            }
            player18 = player;
            player18.packetSender.sendConfig(33, player.configStates[33]);
            player18 = player;
            player18.packetSender.sendSoundEffect(319, 1, 0);
            player.getUpdateState().setAnimation(835);
            player18 = player;
            player18.packetSender.sendGameMessage("You hear a clunk.");
            ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);
            return true;
        }
        if (objectId == 144 && value2 == 3108 && value32 == 9758) {
            objectId = 0;
            value2 = 0;
            if ((player.configStates[33] & 2) != 0) {
                objectId = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value2 = 1;
            }
            if (objectId != 0 && value2 != 0) {
                Player player19 = player;
                player19.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() <= 9757 ? initialValue : -initialValue, true);
                player19 = player;
                player19.packetSender.openSingleDoor(144, 3108, 9758, 0);
            } else {
                Player player20 = player;
                player20.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 139 && value2 == 3105 && value32 == 9760) {
            objectId = 0;
            value2 = 0;
            if ((player.configStates[33] & 2) != 0) {
                objectId = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value2 = 1;
            }
            if (objectId != 0 && value2 != 0) {
                Player player21 = player;
                player21.packetSender.queueRelativeMovementStep(player.getPosition().getX() <= 3104 ? initialValue : -initialValue, 0, true);
                player21 = player;
                player21.packetSender.openSingleDoor(139, 3105, 9760, 0);
            } else {
                Player player22 = player;
                player22.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 145 && value2 == 3102 && value32 == 9758) {
            objectId = 0;
            if ((player.configStates[33] & 0x10) != 0) {
                objectId = 1;
            }
            if (objectId != 0) {
                Player player23 = player;
                player23.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() <= 9757 ? initialValue : -initialValue, true);
                player23 = player;
                player23.packetSender.openSingleDoor(145, 3102, 9758, 0);
            } else {
                Player player24 = player;
                player24.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 140 && value2 == 3100 && value32 == 9760) {
            objectId = 0;
            if ((player.configStates[33] & 0x10) != 0) {
                objectId = 1;
            }
            if (objectId != 0) {
                Player player25 = player;
                player25.packetSender.queueRelativeMovementStep(player.getPosition().getX() <= 3099 ? initialValue : -initialValue, 0, true);
                player25 = player;
                player25.packetSender.openSingleDoor(140, 3100, 9760, 0);
            } else {
                Player player26 = player;
                player26.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 143 && value2 == 3097 && value32 == 9763) {
            value32 = 0;
            value42 = 0;
            boolean enabled = false;
            objectId = 0;
            value2 = 0;
            boolean enabled2 = false;
            if ((player.configStates[33] & 2) != 0) {
                value32 = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value42 = 1;
            }
            if ((player.configStates[33] & 0x10) != 0) {
                enabled = true;
            }
            if ((player.configStates[33] & 0x40) != 0) {
                objectId = 1;
            }
            if ((player.configStates[33] & 0x20) != 0) {
                value2 = 1;
            }
            if ((player.configStates[33] & 8) != 0) {
                enabled2 = true;
            }
            if (value32 == 0 && value42 == 0 && enabled && value2 == 0 && objectId == 0 && !enabled2) {
                Player player27 = player;
                player27.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() <= 9762 ? initialValue : -initialValue, true);
                player27 = player;
                player27.packetSender.openSingleDoor(143, 3097, 9763, 0);
            } else {
                Player player28 = player;
                player28.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 138 && value2 == 3100 && value32 == 9765) {
            value32 = 0;
            value42 = 0;
            boolean enabled3 = false;
            objectId = 0;
            if ((player.configStates[33] & 2) != 0) {
                value32 = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value42 = 1;
            }
            if ((player.configStates[33] & 0x10) != 0) {
                enabled3 = true;
            }
            if ((player.configStates[33] & 0x40) != 0) {
                objectId = 1;
            }
            if (value32 == 0 && value42 == 0 && enabled3 && objectId != 0) {
                Player player29 = player;
                player29.packetSender.queueRelativeMovementStep(player.getPosition().getX() <= 3099 ? initialValue : -initialValue, 0, true);
                player29 = player;
                player29.packetSender.openSingleDoor(138, 3100, 9765, 0);
            } else {
                Player player30 = player;
                player30.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 137 && value2 == 3105 && value32 == 9765) {
            objectId = 0;
            value2 = 0;
            value32 = 0;
            value42 = 0;
            boolean enabled4 = false;
            if ((player.configStates[33] & 2) != 0) {
                objectId = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value2 = 1;
            }
            if ((player.configStates[33] & 0x10) != 0) {
                value32 = 1;
            }
            if ((player.configStates[33] & 0x40) != 0) {
                value42 = 1;
            }
            if ((player.configStates[33] & 0x20) != 0) {
                enabled4 = true;
            }
            if (objectId == 0 && value2 == 0 && value32 != 0 && value42 != 0 && enabled4) {
                Player player31 = player;
                player31.packetSender.queueRelativeMovementStep(player.getPosition().getX() <= 3104 ? initialValue : -initialValue, 0, true);
                player31 = player;
                player31.packetSender.openSingleDoor(137, 3105, 9765, 0);
            } else {
                Player player32 = player;
                player32.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 142 && value2 == 3102 && value32 == 9763) {
            value32 = 0;
            value42 = 0;
            boolean enabled5 = false;
            objectId = 0;
            value2 = 0;
            if ((player.configStates[33] & 2) != 0) {
                value32 = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value42 = 1;
            }
            if ((player.configStates[33] & 0x10) != 0) {
                enabled5 = true;
            }
            if ((player.configStates[33] & 0x40) != 0) {
                objectId = 1;
            }
            if ((player.configStates[33] & 0x20) != 0) {
                value2 = 1;
            }
            if (value32 == 0 && value42 == 0 && enabled5 && value2 == 0 && objectId != 0) {
                Player player33 = player;
                player33.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() <= 9762 ? initialValue : -initialValue, true);
                player33 = player;
                player33.packetSender.openSingleDoor(142, 3102, 9763, 0);
            } else {
                Player player34 = player;
                player34.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        if (objectId == 141 && value2 == 3100 && value32 == 9755) {
            value32 = 0;
            value42 = 0;
            boolean enabled6 = false;
            objectId = 0;
            value2 = 0;
            boolean enabled7 = false;
            if ((player.configStates[33] & 2) != 0) {
                value32 = 1;
            }
            if ((player.configStates[33] & 4) != 0) {
                value42 = 1;
            }
            if ((player.configStates[33] & 0x10) != 0) {
                enabled6 = true;
            }
            if ((player.configStates[33] & 0x40) != 0) {
                objectId = 1;
            }
            if ((player.configStates[33] & 0x20) != 0) {
                value2 = 1;
            }
            if ((player.configStates[33] & 8) != 0) {
                enabled7 = true;
            }
            if (value32 == 0 && value42 == 0 && enabled6 && value2 == 0 && objectId != 0 && enabled7) {
                Player player35 = player;
                player35.packetSender.queueRelativeMovementStep(player.getPosition().getX() <= 3099 ? initialValue : -initialValue, 0, true);
                player35 = player;
                player35.packetSender.openSingleDoor(141, 3100, 9755, 0);
            } else {
                Player player36 = player;
                player36.packetSender.sendGameMessage("The door is locked.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value6, int value32, int value42) {
        if (npcId == 285) {
            if (value42 == 0) {
                if (value6 == 1) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Can you please help me? I'm in a terrible spot of", "trouble.", 591);
                    return true;
                }
                if (value6 == 2) {
                    player.getDialogueManager().showTwoOptions("Aha, sounds like a quest. I'll help.", "No, I'm looking for something to kill.");
                    return true;
                }
                if (value6 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Aha, sounds like a quest. I'll help.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        this.startQuest(player);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
            }
            if (value42 == 2) {
                if (value6 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Yes yes, I suppose it is a quest. My fiance Ernest and", "I came upon this house.", 591);
                    return true;
                }
                if (value6 == 5) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Seeing as we were a little lost Ernest decided to go in", "and ask for directions.", 591);
                    return true;
                }
                if (value6 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("That was an hour ago. That house looks spooky, can", "you go and see if you can find him for me?", 591);
                    return true;
                }
                if (value6 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ok, I'll see what I can do.", 591);
                    return true;
                }
                if (value6 == 8) {
                    player.getDialogueManager().showNpcOneLineDialogue("Thank you, thank you. I'm very grateful.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        if (npcId == 286) {
            if (value42 == 2) {
                if (value6 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Be careful in here, there's lots of dangerous equipment.", 591);
                    return true;
                }
                if (value6 == 2) {
                    player.getDialogueManager().showThreeOptions("I'm looking for a guy called Ernest.", "What does this machine do?", "Is this your house?");
                    return true;
                }
                if (value6 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("I'm looking for a guy called Ernest.", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value6 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Ah Ernest, top notch bloke. He's helping me with my", "experiments.", 591);
                    return true;
                }
                if (value6 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So you know where he is then?", 591);
                    return true;
                }
                if (value6 == 6) {
                    player.getDialogueManager().showNpcOneLineDialogue("He's that chicken over there.", 591);
                    return true;
                }
                if (value6 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Ernest is a chicken..? Are you sure..?", 591);
                    return true;
                }
                if (value6 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Oh, he isn't normally a chicken, or at least he wasn't", "until he helped me test my pouletmorph machine.", 591);
                    return true;
                }
                if (value6 == 9) {
                    player.getDialogueManager().showNpcThreeLineDialogue("It was originally going to be called a transmutation", "machine. But after testing pouletmorph seems more", "appropriate.", 591);
                    return true;
                }
                if (value6 == 10) {
                    player.getDialogueManager().showTwoOptions("I'm glad Veronica didn't actually get engaged to a chicken.", "Change him back this instant!");
                    return true;
                }
                if (value6 == 11) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("I'm glad Veronica didn't actually get engaged to a", "chicken.", 591);
                        player.getDialogueManager().setNextDialogueStep(12);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                if (value6 == 12) {
                    player.getDialogueManager().showNpcOneLineDialogue("Who's Veronica?", 591);
                    return true;
                }
                if (value6 == 13) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("Ernest's fiancee. She probably doesn't want to marry a", "chicken.", 591);
                    return true;
                }
                if (value6 == 14) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ooh I dunno. She could have free eggs for breakfast.", 591);
                    return true;
                }
                if (value6 == 15) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I think you'd better change him back.", 591);
                    return true;
                }
                if (value6 == 16) {
                    player.getDialogueManager().showNpcOneLineDialogue("Umm... It's not so easy...", 591);
                    return true;
                }
                if (value6 == 17) {
                    player.getDialogueManager().showNpcTwoLineDialogue("My machine is broken, and the house gremlins have", "run off with some vital bits.", 591);
                    return true;
                }
                if (value6 == 18) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Well I can look for them.", 591);
                    return true;
                }
                if (value6 == 19) {
                    player.getDialogueManager().showNpcThreeLineDialogue("That would be a help. They'll be somewhere in the", "manor house or its grounds, the gremlins never get", "further than entrance gate.", 591);
                    return true;
                }
                if (value6 == 20) {
                    player.getDialogueManager().showNpcThreeLineDialogue("I'm missing the pressure gauge and a rubber tube.", "They've also taken my oil can, which I'm going to need", "to get this thing started again.", 591);
                    player.setQuestState(this.getQuestId(), 3);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 3 || value42 == 4) {
                if (value6 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Have you found anything yet?", 591);
                    if (ErnestTheChickenQuest.hasAllMachineParts(player)) {
                        player.getDialogueManager().setNextDialogueStep(2);
                    } else {
                        player.getDialogueManager().setNextDialogueStep(5);
                    }
                    return true;
                }
                if (value6 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'm still looking.", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
                if (value6 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I have everything!", 591);
                    return true;
                }
                if (value6 == 3) {
                    player.getDialogueManager().showNpcOneLineDialogue("Give 'em here then.", 591);
                    return true;
                }
                if (value6 == 4) {
                    Object value2 = player;
                    ((Player)value2).packetSender.closeInterfaces();
                    value2 = player;
                    ((Player)value2).packetSender.sendGameMessage("You give a rubber tube, a pressure gauge,");
                    value2 = player;
                    ((Player)value2).packetSender.sendGameMessage("and a can of oil to the professor.");
                    Npc npc = Npc.findByDefinitionId(286);
                    npc.getMovementQueue().addStep(new Position(npc.getPosition().getX() < 3108 ? 3107 : 3111, 3367, 2));
                    Position position = npc.getPosition();
                    Position position2 = new Position(npc.getPosition().getX() < 3108 ? 3107 : 3111, 3367, 2);
                    int distance = GameUtil.getDistance(position, position2);
                    double value5 = 100.0 + (double)distance * 45.0;
                    value5 = Math.ceil(value5 * 12.0 / 600.0);
                    if (distance > 1) {
                        value5 += 1.0;
                    }
                    distance = 0 + (int)value5;
                    PouletmorphMachineStartTask pouletmorphMachineStartTask = new PouletmorphMachineStartTask(this, distance, player);
                    World.getTaskScheduler().schedule(pouletmorphMachineStartTask);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value42 == 5) {
                this.awardCompletionRewards(player);
            }
        }
        if (npcId == 287) {
            if (value6 == 100 && ErnestTheChickenQuest.hasAllMachineParts(player)) {
                player.setQuestState(this.getQuestId(), 5);
                player.getInventoryManager().removeItem(new ItemStack(271, 1));
                player.getInventoryManager().removeItem(new ItemStack(276, 1));
                player.getInventoryManager().removeItem(new ItemStack(277, 1));
                player.getDialogueManager().showNpcTwoLineDialogue("Thank you sir. It was dreadfully irritating being a", "chicken. How can I ever thank you?", 591);
                return true;
            }
            if (value42 == 5) {
                if (value6 == 101) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Well a cash reward is always nice...", 591);
                    return true;
                }
                if (value6 == 102) {
                    player.getDialogueManager().showNpcOneLineDialogue("Of course, of course.", 591);
                    return true;
                }
                if (value6 == 103) {
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("Ernest hands you 300 coins.");
                    this.awardCompletionRewards(player);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}
