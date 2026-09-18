package com.rs2.model.quest.impl;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.DragonSlayerShipHoleRepairTask;
import com.rs2.model.travel.ShipRoute;
import com.rs2.model.travel.TravelManager;
import com.rs2.util.GameUtil;

public final class DragonSlayerQuest
extends QuestScript {
    private final String[] wormbrainCombatTaunts = new String[]{"Feel the wrath of my feet!", "Let me drink my tea in peace!", "By the Power of Custard!", "Leave me alone, I need to feed my pet rock!"};

    public DragonSlayerQuest(int value2) {
        super(5);
        super.setQuestPointReward(2);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value2) {
        if (value2 == 0) {
            return new String[]{"I can start this quest by speaking to the Guild master in", "the Champions' Guild, south-west of Varrock.", "I will need to be able to defeat a level 83 dragon.", String.valueOf(player.getQuestPoints() >= 32 ? "@str@" : "") + "To enter the Champions' Guild I need 32 Quest Points."};
        }
        if (value2 == 2) {
            return new String[]{"The Guildmaster told me to go speak with Oziach, who", "can be found by the cliffs west of Edgeville."};
        }
        if (value2 == 3) {
            return new String[]{"Oziach told me to slay the dragon of Crandor. I should", "go and ask some tips from the Guildmaster."};
        }
        if (value2 >= 4 && value2 < 260) {
            return new String[]{"I should now find the following things:", "- Map to Crandor", "- Ship and Captain", "- Way to protect from dragonbreath"};
        }
        if (value2 == 260) {
            return new String[]{"I should go back to the ship, when I'm ready to go", "to Crandor to defeat the dragon."};
        }
        if (value2 == 261) {
            return new String[]{"I should now find and kill the dragon."};
        }
        if (value2 == 1) {
            return new String[]{"Quest Completed!", "", "You were awarded:", "2 Quest Points", "Ability to wear rune platebody", "18,650 Strength XP", "18,650 Defence XP"};
        }
        return null;
    }

    @Override
    public final void awardCompletionRewards(Player player) {
        super.markQuestComplete(player);
        super.showQuestCompleteInterface(player);
        Player player2 = player;
        player2.packetSender.sendInterfaceText("2 Quest Points", 12150);
        player2 = player;
        player2.packetSender.sendInterfaceText("Ability to wear rune platebody", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("18,650 Strength XP", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("18,650 Defence XP", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(2, 18650.0);
        player.getSkillManager().addQuestExperience(1, 18650.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 1127);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleItemOnObject(Player player, int objectId, int value4, int value32) {
        int value2 = value32 - 4;
        if (value32 < 4) {
            return false;
        }
        if (objectId == 1907 && value4 == 2586) {
            if ((value2 & 0x10) != 0) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("You already have Wizard's Mind Bomb there.");
                return true;
            }
            player.getInventoryManager().replaceItem(new ItemStack(1907, 1), new ItemStack(1919, 1));
            Player player3 = player;
            player3.packetSender.sendGameMessage("You pour the Wizard's Mind Bomb into the opening in the door.");
            player.addQuestState(this.getQuestId(), 16);
            DragonSlayerQuest.sendMelzarDoorOpenMessageIfReady(player, player.getQuestState(this.getQuestId()));
            return true;
        }
        if (objectId == 1791 && value4 == 2586) {
            if ((value2 & 0x20) != 0) {
                Player player4 = player;
                player4.packetSender.sendGameMessage("You already have Unfired bowl there.");
                return true;
            }
            player.getInventoryManager().removeItem(new ItemStack(1791, 1));
            Player player5 = player;
            player5.packetSender.sendGameMessage("You put the unfired bowl into the opening in the door.");
            player.addQuestState(this.getQuestId(), 32);
            DragonSlayerQuest.sendMelzarDoorOpenMessageIfReady(player, player.getQuestState(this.getQuestId()));
            return true;
        }
        if (objectId == 950 && value4 == 2586) {
            if ((value2 & 0x40) != 0) {
                Player player6 = player;
                player6.packetSender.sendGameMessage("You already have Silk there.");
                return true;
            }
            player.getInventoryManager().removeItem(new ItemStack(950, 1));
            Player player7 = player;
            player7.packetSender.sendGameMessage("You put the silk into the opening in the door.");
            player.addQuestState(this.getQuestId(), 64);
            DragonSlayerQuest.sendMelzarDoorOpenMessageIfReady(player, player.getQuestState(this.getQuestId()));
            return true;
        }
        if (objectId == 301 && value4 == 2586) {
            if ((value2 & 0x80) != 0) {
                Player player8 = player;
                player8.packetSender.sendGameMessage("You already have Lobster pot there.");
                return true;
            }
            player.getInventoryManager().removeItem(new ItemStack(301, 1));
            Player player9 = player;
            player9.packetSender.sendGameMessage("You put the lobster pot into the opening in the door.");
            player.addQuestState(this.getQuestId(), 128);
            DragonSlayerQuest.sendMelzarDoorOpenMessageIfReady(player, player.getQuestState(this.getQuestId()));
            return true;
        }
        return false;
    }

    private static void sendMelzarDoorOpenMessageIfReady(Player player, int value2) {
        if (((value2 -= 4) & 0x10) != 0 && (value2 & 0x20) != 0 && (value2 & 0x40) != 0 && (value2 & 0x80) != 0) {
            player.packetSender.sendGameMessage("The door opens...");
        }
    }

    @Override
    public final boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value22 == 2604 && value52 == 2935 && value62 == 9657 && value8 == 1 && !player.ownsItem(1535) && !player.ownsItem(1538)) {
            if (value32 == 1) {
                player.getDialogueManager().showItemMessage("You find a map piece in the chest.", new ItemStack(1535, 1));
                return true;
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1535, 1));
                ObjectManager.getInstance().removeDynamicObjectAt(2935, 9657, 0, 0);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(2603, 2935, 9657, 0, 2, 10, 2603, 999999999), true);
                return false;
            }
        }
        if (value22 == 2587 && value52 == 3057 && value62 == 9841 && value8 == 1 && !player.ownsItem(1537) && !player.ownsItem(1538)) {
            if (value32 == 1) {
                player.getDialogueManager().showOneLineStatement("As you open the chest, you notice an inscription on the lid:");
                return true;
            }
            if (value32 == 2) {
                player.getDialogueManager().showThreeLineStatement("Here I rest the map to my beloved home. To whoever finds it, I beg", "of you, let it be. I was honour-bound not to destroy the map piece,", "but I have used all my magical skill to keep it from being recovered.");
                return true;
            }
            if (value32 == 3) {
                player.getDialogueManager().showFourLineStatement("This map leads to the lair of the beast that destroyed my home,", "devoured my family, and burned to a cinder all that I love. But", "revenge would not benefit me now, and to disturb this beast is to risk", "bringing its wrath down upon another land.");
                return true;
            }
            if (value32 == 4) {
                player.getDialogueManager().showThreeLineStatement("I cannot stop you from taking this map piece now, but think on this:", "if you can slay the Dragon of Crandor, you are a greater hero than", "my land ever produced. There is no shame in backing out now.");
                return true;
            }
            if (value32 == 5) {
                ObjectManager.getInstance().removeDynamicObjectAt(3057, 9841, 0, 0);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(2588, 3057, 9841, 0, 3, 10, 2587, 999999999), true);
                return false;
            }
        }
        if (value22 == 2588 && value52 == 3057 && value62 == 9841 && value8 == 1 && !player.ownsItem(1537) && !player.ownsItem(1538)) {
            if (value32 == 1) {
                player.getDialogueManager().showItemMessage("You find a map piece in the chest.", new ItemStack(1537, 1));
                return true;
            }
            if (value32 == 2) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1537, 1));
                ObjectManager.getInstance().removeDynamicObjectAt(3057, 9841, 0, 0);
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(2587, 3057, 9841, 0, 3, 10, 2587, 999999999), true);
                return false;
            }
        }
        return false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value5, int value32, int value42) {
        int value2 = value42 - 4;
        int questId = this.getQuestId();
        if (objectId == 2589 && value5 == 3047 && value32 == 9639 && player.getInventoryManager().containsItem(2347) && player.getInventoryManager().containsItem(960) && player.getInventoryManager().containsItemAmount(1539, 30)) {
            player.setActionLocked(true);
            player.getUpdateState().setAnimation(898);
            DragonSlayerShipHoleRepairTask dragonSlayerShipHoleRepairTask = new DragonSlayerShipHoleRepairTask(this, 2, value2, player, questId);
            World.getTaskScheduler().schedule(dragonSlayerShipHoleRepairTask);
            return true;
        }
        if (objectId == 2607 && value5 == 2847 && value32 == 9636 || objectId == 2608 && value5 == 2847 && value32 == 9637) {
            if (value42 == 261) {
                Player player2 = player;
                player2.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2847 ? 1 : -1, 0, true);
                player2 = player;
                player2.packetSender.openWestShiftedDoubleDoorPair(2607, 2608, 2847, 9636, 2847, 9637, 0);
            }
            return true;
        }
        if (objectId == 2606 && value5 == 2836 && value32 == 9600) {
            if (value42 == 261 || value42 == 1) {
                Player player3 = player;
                player3.packetSender.openSingleDoor(objectId, value5, value32, 0);
                player3 = player;
                player3.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9600 ? 1 : -1, true);
            } else {
                Player player4 = player;
                player4.packetSender.sendGameMessage("It is locked.");
            }
            return true;
        }
        if (objectId == 2593 && value5 == 3047 && value32 == 3205 && ((value2 & 1) != 0 && value42 > 4 || value42 > 259)) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You board the ship.");
            player.moveTo(new Position(3047, 3207, 1));
            return true;
        }
        if (objectId == 2594 && value5 == 3047 && value32 == 3206 && ((value2 & 1) != 0 && value42 > 4 || value42 > 259)) {
            Player player6 = player;
            player6.packetSender.sendGameMessage("You disembark the ship.");
            player.moveTo(new Position(3047, 3204, 0));
            return true;
        }
        if (objectId == 2590 && value5 == 3049 && value32 == 3208) {
            objectId = 1;
            if ((value2 & 8) != 0) {
                objectId = 2;
            }
            if (value42 == 260) {
                objectId = 3;
            }
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(3048, 9640, objectId));
            return true;
        }
        if (objectId == 2592 && (value5 == 3049 || value5 == 3048) && value32 == 9640) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(3048, 3208, 1));
            return true;
        }
        if (objectId == 9563 && value5 == 3012 && value32 == 3189) {
            Player player7 = player;
            player7.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2586 && value5 == 3051 && value32 == 9840) {
            if (value2 > 0 && (value2 & 0x10) != 0 && (value2 & 0x20) != 0 && (value2 & 0x40) != 0 && (value2 & 0x80) != 0) {
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 3051 ? 1 : -1, 0, true);
                player8 = player;
                player8.packetSender.openSingleDoor(2586, 3051, 9840, 0);
            } else {
                Player player9 = player;
                player9.packetSender.sendGameMessage("You can't see any way to open the door.");
            }
            return true;
        }
        if (objectId == 2603 && value5 == 2935 && value32 == 9657) {
            ObjectManager.getInstance().removeDynamicObjectAt(2935, 9657, 0, 0);
            ObjectManager.getInstance().addDynamicObject(new DynamicObject(2604, 2935, 9657, 0, 2, 10, 2603, 999999999), true);
            Player player10 = player;
            player10.packetSender.sendGameMessage("You open the chest.");
            return true;
        }
        if (objectId == 2595 && value5 == 2941 && value32 == 3248) {
            if (player.getInventoryManager().containsItem(1542)) {
                Player player11 = player;
                player11.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2941 ? 1 : -1, 0, true);
                player11 = player;
                player11.packetSender.openSingleDoor(2595, 2941, 3248, 0);
                player11 = player;
                player11.packetSender.sendGameMessage("You use the key and the door opens.");
                return true;
            }
            Player player12 = player;
            player12.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2596 && value5 == 2926) {
            if (player.getInventoryManager().containsItem(1543)) {
                player.getInventoryManager().removeItem(new ItemStack(1543, 1));
                Player player13 = player;
                player13.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2926 ? 1 : -1, 0, true);
                player13 = player;
                player13.packetSender.openSingleDoor(2596, 2926, value32, 0);
                player13 = player;
                player13.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player14 = player;
            player14.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2597 && value5 == 2931) {
            if (player.getInventoryManager().containsItem(1544)) {
                player.getInventoryManager().removeItem(new ItemStack(1544, 1));
                Player player15 = player;
                player15.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2931 ? 1 : -1, 0, true);
                player15 = player;
                player15.packetSender.openSingleDoor(2597, 2931, value32, 1);
                player15 = player;
                player15.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player16 = player;
            player16.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2598 && value32 == 3249) {
            if (player.getInventoryManager().containsItem(1545)) {
                player.getInventoryManager().removeItem(new ItemStack(1545, 1));
                Player player17 = player;
                player17.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 3250 ? 1 : -1, true);
                player17 = player;
                player17.packetSender.openSingleDoor(2598, value5, 3249, 2);
                player17 = player;
                player17.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player18 = player;
            player18.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2598 && value5 == 2936 && value32 == 3256) {
            if (player.getInventoryManager().containsItem(1545)) {
                player.getInventoryManager().removeItem(new ItemStack(1545, 1));
                Player player19 = player;
                player19.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2936 ? 1 : -1, 0, true);
                player19 = player;
                player19.packetSender.openSingleDoor(2598, 2936, 3256, 2);
                player19 = player;
                player19.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player20 = player;
            player20.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2599 && value5 == 2931 && value32 == 9643) {
            if (player.getInventoryManager().containsItem(1546)) {
                player.getInventoryManager().removeItem(new ItemStack(1546, 1));
                Player player21 = player;
                player21.packetSender.queueRelativeMovementStep(player.getPosition().getX() < 2931 ? 1 : -1, player.getPosition().getY() < 9643 ? 1 : 0, true);
                player21 = player;
                player21.packetSender.openSingleDoor(2599, 2931, 9643, 0);
                player21 = player;
                player21.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player22 = player;
            player22.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2600 && value5 == 2929 && value32 == 9652) {
            if (player.getInventoryManager().containsItem(1547)) {
                player.getInventoryManager().removeItem(new ItemStack(1547, 1));
                Player player23 = player;
                player23.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9652 ? 1 : -1, true);
                player23 = player;
                player23.packetSender.openSingleDoor(2600, 2929, 9652, 0);
                player23 = player;
                player23.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player24 = player;
            player24.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2601 && value5 == 2936 && value32 == 9655) {
            if (player.getInventoryManager().containsItem(1548)) {
                player.getInventoryManager().removeItem(new ItemStack(1548, 1));
                Player player25 = player;
                player25.packetSender.queueRelativeMovementStep(0, player.getPosition().getY() < 9656 ? 1 : -1, true);
                player25 = player;
                player25.packetSender.openSingleDoor(2601, 2936, 9655, 0);
                player25 = player;
                player25.packetSender.sendGameMessage("The key disintegrates as it unlocks the door.");
                return true;
            }
            Player player26 = player;
            player26.packetSender.sendGameMessage("It is locked.");
            return true;
        }
        if (objectId == 2602 && value5 == 2938 && value32 == 3252) {
            if (player.getPosition().getX() > 2937) {
                Player player27 = player;
                player27.packetSender.queueRelativeMovementStep(-1, 0, true);
                player27 = player;
                player27.packetSender.openSingleDoor(2602, 2938, 3252, 0);
            } else {
                Player player28 = player;
                player28.packetSender.sendGameMessage("It won't open.");
            }
            return true;
        }
        if (objectId == 2602 && value5 == 2924 && value32 == 9654) {
            if (player.getPosition().getX() > 2923) {
                Player player29 = player;
                player29.packetSender.queueRelativeMovementStep(-1, 0, true);
                player29 = player;
                player29.packetSender.openSingleDoor(2602, 2924, 9654, 0);
            } else {
                Player player30 = player;
                player30.packetSender.sendGameMessage("It won't open.");
            }
            return true;
        }
        if (objectId == 2602 && value5 == 2927 && value32 == 9649) {
            if (player.getPosition().getX() > 2926) {
                Player player31 = player;
                player31.packetSender.queueRelativeMovementStep(-1, 0, true);
                player31 = player;
                player31.packetSender.openSingleDoor(2602, 2927, 9649, 0);
            } else {
                Player player32 = player;
                player32.packetSender.sendGameMessage("It won't open.");
            }
            return true;
        }
        if (objectId == 2602 && value5 == 2931 && value32 == 9640) {
            if (player.getPosition().getX() > 2930) {
                Player player33 = player;
                player33.packetSender.queueRelativeMovementStep(-1, 0, true);
                player33 = player;
                player33.packetSender.openSingleDoor(2602, 2931, 9640, 0);
            } else {
                Player player34 = player;
                player34.packetSender.sendGameMessage("It won't open.");
            }
            return true;
        }
        if (objectId == 1530 && value5 == 2935 && value32 == 3256) {
            if (player.getPosition().getY() < 3257) {
                Player player35 = player;
                player35.packetSender.queueRelativeMovementStep(0, 1, true);
                player35 = player;
                player35.packetSender.openSingleDoor(1530, 2935, 3256, 1);
            } else {
                Player player36 = player;
                player36.packetSender.sendGameMessage("It won't open.");
            }
            return true;
        }
        if (objectId == 2605 && value5 == 2932 && value32 == 3240) {
            AttackStyleDefinition.startDelayedObjectMove(player, new Position(2933, 9640, 0));
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214) {
            if (value2 == 1535) {
                player.getDialogueManager().showItemMessage("This is Melzar's piece of the map.", new ItemStack(1535, 1));
                return true;
            }
            if (value2 == 1537) {
                player.getDialogueManager().showItemMessage("This is Thalzar's piece of the map.", new ItemStack(1537, 1));
                return true;
            }
            if (value2 == 1536) {
                player.getDialogueManager().showItemMessage("This is Lozar's piece of the map.", new ItemStack(1536, 1));
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        Player player2 = player;
        if (player2.getInventoryManager().containsItem(1535) && player2.getInventoryManager().containsItem(1536) && player2.getInventoryManager().containsItem(1537) && DragonSlayerQuest.isCrandorMapPiece(itemId) && DragonSlayerQuest.isCrandorMapPiece(value2)) {
            player.getInventoryManager().removeItem(new ItemStack(1535, 1));
            player.getInventoryManager().removeItem(new ItemStack(1536, 1));
            player.getInventoryManager().removeItem(new ItemStack(1537, 1));
            player.getInventoryManager().addItem(new ItemStack(1538, 1));
            player.getDialogueManager().showTwoItemMessage("You put the three pieces together and assemble a map", "that shows the route through the reefs to Crandor.", new ItemStack(-1, 1), new ItemStack(1538, 1));
            player.getDialogueManager().finishDialogue();
            return true;
        }
        return false;
    }

    private static boolean isCrandorMapPiece(int value2) {
        return value2 == 1535 || value2 == 1536 || value2 == 1537;
    }

    @Override
    public final boolean handleNpcKill(Player player, int npcId, int value2) {
        if (npcId == 742 && value2 == 261) {
            player.moveTo(new Position(2846, 9636, 0));
            this.awardCompletionRewards(player);
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleCombatDeath(Entity entity3, Entity entity, int value2) {
        if (entity.isNpc() && ((Entity)entity3).isPlayer()) {
            entity3 = (Player)entity3;
            if (((Npc)(entity = (Npc)entity)).getNpcId() == 745) {
                CombatManager.stopCombat(entity);
                ((Npc)entity).setCurrentHitpoints(((Npc)entity).getMaxHitpoints());
                entity.getUpdateState().setForcedText("Ow!");
                Entity entity2 = entity3;
                ((Player)entity2).packetSender.sendGameMessage("Wormbrain drops a map piece on the floor.");
                GroundItem groundItem = new GroundItem(new ItemStack(1536, 1), (Entity)entity3, entity.getPosition());
                GroundItemManager.getInstance().spawn(groundItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public final int getQuestDamageOverride(Entity entity, Entity entity2, int value2) {
        if (entity.isNpc() && ((Npc)(entity = (Npc)entity)).getNpcId() == 753) {
            entity.getUpdateState().setForcedText(this.wormbrainCombatTaunts[GameUtil.randomInclusive(this.wormbrainCombatTaunts.length - 1)]);
        }
        return -1;
    }

    @Override
    public final boolean canAttackNpc(Player player, int npcId, int value2) {
        if (npcId == 745) {
            if (value2 >= 4 && value2 < 260) {
                npcId = 0;
                while (npcId < player.getVisibleGroundItems().size()) {
                    GroundItem groundItem = (GroundItem)player.getVisibleGroundItems().get(npcId);
                    if (groundItem.getItem().getId() == 1536) {
                        player.packetSender.sendGameMessage("You have just beaten Wormbrain up. Give the poor goblin a break.");
                        return false;
                    }
                    ++npcId;
                }
                if (player.ownsItem(1536) || player.ownsItem(1538)) {
                    player.packetSender.sendGameMessage("You already have the map piece from Wormbrain.");
                    return false;
                }
                return true;
            }
            if (value2 <= 3) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean handleNpcDialogue(Player player, int npcId, int value5, int value32, int value42) {
        int value2 = value42 - 4;
        if (npcId == 198 && (value42 == 0 || value42 == 2 || value42 == 3 || value42 == 4 || value42 > 4 && value42 < 260)) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Greetings!", 591);
                if (value42 == 3) {
                    player.getDialogueManager().setNextDialogueStep(14);
                }
                if (value42 >= 4) {
                    player.getDialogueManager().setNextDialogueStep(14);
                }
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showTwoOptions("What is this place?", "Can I have a quest?");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What is this place?", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can I have a quest?", 591);
                    if (value42 == 0) {
                        player.getDialogueManager().setNextDialogueStep(4);
                    }
                    if (value42 == 2) {
                        player.getDialogueManager().setNextDialogueStep(10);
                    }
                    return true;
                }
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcOneLineDialogue("Aha!", 591);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showNpcThreeLineDialogue("Yes! A mighty and perilous quest fit only for the most", "powerful champions! And, at the end of it, you will earn", "the right to wear the legendary rune platebody!", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showPlayerOneLineDialogue("So, what is this quest?", 591);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcThreeLineDialogue("You'll have to speak to Oziach, the maker of rune", "armour. He sets the quests that champions must", "complete in order to wear it.", 591);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showNpcTwoLineDialogue("Oziach lives in a hut, by the cliffs to the west of", "Edgeville. He can be a little...odd...sometimes, though.", 591);
                this.startQuest(player);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 9) {
                player.getDialogueManager().showNpcThreeLineDialogue("This is the Champions' Guild. Only adventurers who", "have proved themselves worthy by gaining influence", "from quests are allowed in here.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 10) {
                player.getDialogueManager().showNpcTwoLineDialogue("You're already on a quest for me, If I recall correctly.", "Have you talked to Oziach yet?", 591);
                return true;
            }
            if (value5 == 11) {
                player.getDialogueManager().showPlayerOneLineDialogue("No, not yet.", 591);
                return true;
            }
            if (value5 == 12) {
                player.getDialogueManager().showNpcThreeLineDialogue("Well, he's the only one who can grant you the right to", "wear rune platemail. He lives in a hut, by the cliffs west", "of Edgeville.", 591);
                return true;
            }
            if (value5 == 13) {
                player.getDialogueManager().showPlayerOneLineDialogue("Okay, I'll go and talk to him.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 14) {
                player.getDialogueManager().showTwoOptions("What is this place?", "I talked to Oziach...");
                return true;
            }
            if (value5 == 15) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What is this place?", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I talked to Oziach and he gave me a quest.", 591);
                    player.getDialogueManager().setNextDialogueStep(16);
                    return true;
                }
            }
            if (value5 == 16) {
                player.getDialogueManager().showNpcOneLineDialogue("Oh? What did he tell you to do?", 591);
                return true;
            }
            if (value5 == 17) {
                player.getDialogueManager().showPlayerOneLineDialogue("Defeat the dragon of Crandor.", 591);
                return true;
            }
            if (value5 == 18) {
                player.getDialogueManager().showNpcOneLineDialogue("The dragon of Crandor?", 591);
                return true;
            }
            if (value5 == 19) {
                player.getDialogueManager().showPlayerOneLineDialogue("Um, yes...", 591);
                return true;
            }
            if (value5 == 20) {
                player.getDialogueManager().showNpcOneLineDialogue("Goodness, he hasn't given you an easy job, has he?", 591);
                return true;
            }
            if (value5 == 21) {
                player.getDialogueManager().showPlayerOneLineDialogue("What's so special about this dragon?", 591);
                return true;
            }
            if (value5 == 22) {
                player.getDialogueManager().showNpcFourLineDialogue("Thirty years ago, Crandor was thriving community", "with a great tradition of mages and adventurers. Many", "Crandorians even earned the right to be part of the", "Champions' Guild!", 591);
                return true;
            }
            if (value5 == 23) {
                player.getDialogueManager().showNpcThreeLineDialogue("One of their adventurers went too far, however. He", "descended into the volcano in the centre of Crandor", "and woke the dragon Elvarg.", 591);
                return true;
            }
            if (value5 == 24) {
                player.getDialogueManager().showNpcThreeLineDialogue("He must have fought valiantly against the dragon", "because they say that, to this day, she has a scar down", "her side,", 591);
                return true;
            }
            if (value5 == 25) {
                player.getDialogueManager().showNpcTwoLineDialogue("but the dragon still won the fight. She emerged and laid", "waste to the whole of Crandor with her fire breath!", 591);
                return true;
            }
            if (value5 == 26) {
                player.getDialogueManager().showNpcFourLineDialogue("Some refugees managed to escape in fishing boats.", "They landed on the coast, north of Rimmington, and", "set up camp but the dragon followed them and burned", "the camp to the ground.", 591);
                return true;
            }
            if (value5 == 27) {
                player.getDialogueManager().showNpcThreeLineDialogue("Out of all the people of Crandor there were only three", "survivors: a trio of wizards who used magic to escape.", "Their names were Thalzar, Lozar and Melzar.", 591);
                return true;
            }
            if (value5 == 28) {
                player.getDialogueManager().showNpcFourLineDialogue("If you're serious about taking on Elvarg, first you'll", "need to get to Crandor. The island is surrounded by", "dangerous reefs, so you'll need a ship capable of getting", "through them and a map to show you the way.", 591);
                return true;
            }
            if (value5 == 29) {
                player.pendingGameMode = 0;
                player.getDialogueManager().showNpcTwoLineDialogue("When you reach Crandor, you'll also need some kind of", "protection against the dragon's breath.", 591);
                return true;
            }
            if (value5 == 30) {
                if (player.pendingGameMode == 0) {
                    player.getDialogueManager().showFiveOptions("How can I find the route to Crandor?", "Where can I find the right ship?", "How can I protect myself from the dragon's breath?", "What's so special about this dragon?", "Okay, I'll get going!");
                }
                if (player.pendingGameMode == 1) {
                    player.getDialogueManager().showFiveOptions("Where is Thalzar's map piece?", "Where is Lozar's map piece?", "Where can I find the right ship?", "How can I protect myself from the dragon's breath?", "Okay, I'll get going!");
                }
                if (player.pendingGameMode == 2) {
                    player.getDialogueManager().showFiveOptions("Where is Melzar's map piece?", "Where is Thalzar's map piece?", "Where can I find the right ship?", "How can I protect myself from the dragon's breath?", "Okay, I'll get going!");
                }
                if (player.pendingGameMode == 4) {
                    player.getDialogueManager().showThreeOptions("How can I find the route to Crandor?", "How can I protect myself from the dragon's breath?", "Okay, I'll get going!");
                }
                if (player.pendingGameMode == 8) {
                    player.getDialogueManager().showThreeOptions("How can I find the route to Crandor?", "Where can I find the right ship?", "Okay, I'll get going!");
                }
                if (player.pendingGameMode == 16) {
                    player.getDialogueManager().showFiveOptions("Where is Melzar's map piece?", "Where is Lozar's map piece?", "Where can I find the right ship?", "How can I protect myself from the dragon's breath?", "Okay, I'll get going!");
                }
                return true;
            }
            if (value5 == 31) {
                if (value32 > 0 && value32 < 5 && value42 == 3) {
                    player.setQuestState(this.getQuestId(), 4);
                }
                if (value32 == 1) {
                    if (player.pendingGameMode == 0 || player.pendingGameMode == 4 || player.pendingGameMode == 8) {
                        player.getDialogueManager().showPlayerOneLineDialogue("How can I find the route to Crandor?", 591);
                        player.getDialogueManager().setNextDialogueStep(32);
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where is Thalzar's map piece?", 591);
                        player.getDialogueManager().setNextDialogueStep(55);
                    }
                    if (player.pendingGameMode == 2 || player.pendingGameMode == 16) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where is Melzar's map piece?", 591);
                        player.getDialogueManager().setNextDialogueStep(35);
                    }
                    return true;
                }
                if (value32 == 2) {
                    if (player.pendingGameMode == 0 || player.pendingGameMode == 8) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where can I find the right ship?", 591);
                        player.getDialogueManager().setNextDialogueStep(45);
                    }
                    if (player.pendingGameMode == 1 || player.pendingGameMode == 16) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where is Lozar's map piece?", 591);
                        player.getDialogueManager().setNextDialogueStep(41);
                    }
                    if (player.pendingGameMode == 2) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where is Thalzar's map piece?", 591);
                        player.getDialogueManager().setNextDialogueStep(55);
                    }
                    if (player.pendingGameMode == 4) {
                        player.getDialogueManager().showPlayerOneLineDialogue("How can I protect myself from the dragon's breath?", 591);
                        player.getDialogueManager().setNextDialogueStep(50);
                    }
                    return true;
                }
                if (value32 == 3) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("How can I protect myself from the dragon's breath?", 591);
                        player.getDialogueManager().setNextDialogueStep(50);
                    }
                    if (player.pendingGameMode != 0 && player.pendingGameMode != 4 && player.pendingGameMode != 8) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Where can I find the right ship?", 591);
                        player.getDialogueManager().setNextDialogueStep(45);
                    }
                    if (player.pendingGameMode == 4 || player.pendingGameMode == 8) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Okay, I'll get going!", 591);
                        player.getDialogueManager().finishDialogue();
                    }
                    return true;
                }
                if (value32 == 4) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().finishDialogue();
                        return false;
                    }
                    if (player.pendingGameMode != 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("How can I protect myself from the dragon's breath?", 591);
                        player.getDialogueManager().setNextDialogueStep(50);
                    }
                }
                if (value32 == 5) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Okay, I'll get going!", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
            if (value5 == 32) {
                player.getDialogueManager().showNpcFourLineDialogue("Only one map exists that shows the route through the", "reefs of Crandor. That map was split into three pieces", "by Melzar, Thalzar and Lozar, the wizards who escaped", "from the dragon. Each of them took one piece.", 591);
                return true;
            }
            if (value5 == 33) {
                player.getDialogueManager().showThreeOptions("Where is Melzar's map piece?", "Where is Thalzar's map piece?", "Where is Lozar's map piece?");
                return true;
            }
            if (value5 == 34) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Where is Melzar's map piece?", 591);
                    player.getDialogueManager().setNextDialogueStep(35);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Where is Thalzar's map piece?", 591);
                    player.getDialogueManager().setNextDialogueStep(55);
                    return true;
                }
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Where is Lozar's map piece?", 591);
                    player.getDialogueManager().setNextDialogueStep(41);
                    return true;
                }
            }
            if (value5 == 35) {
                player.pendingGameMode = 1;
                player.getDialogueManager().showNpcThreeLineDialogue("Melzar built a castle on the site of the Crandorian", "refugee camp, north of Rimmington. He's locked himself", "in there and no one's seen him for years.", 591);
                return true;
            }
            if (value5 == 36) {
                player.getDialogueManager().showNpcFourLineDialogue("The inside of his castle is like a maze, and is populated", "by undead monsters. Maybe, if you could get all the", "way through the maze, you could find his piece of the", "map.", 591);
                return true;
            }
            if (value5 == 37) {
                player.getDialogueManager().showNpcTwoLineDialogue("Adventurers sometimes go in there to prove themselves,", "so I can give you this key to Melzar's Maze.", 591);
                return true;
            }
            if (value5 == 38) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1542, 1));
                player.getDialogueManager().showItemMessage("The Guild master hands you a key.", new ItemStack(1542, 1));
                player.getDialogueManager().setNextDialogueStep(30);
                return true;
            }
            if (value5 == 41) {
                player.pendingGameMode = 2;
                player.getDialogueManager().showNpcTwoLineDialogue("A few weeks ago, I'd have told you to speak to Lozar", "herself, in her house across the river from Lumbridge.", 591);
                return true;
            }
            if (value5 == 42) {
                player.getDialogueManager().showNpcThreeLineDialogue("Unfortunately, goblin raiders killed her and stole", "everything. One of the goblins from the Goblin Village", "probably has the map piece now.", 591);
                player.getDialogueManager().setNextDialogueStep(30);
                return true;
            }
            if (value5 == 45) {
                player.pendingGameMode = 4;
                player.getDialogueManager().showNpcThreeLineDialogue("Even if you find the right route, only a ship made to", "the old crandorian design would be able to navigate", "through the reefs to the island.", 591);
                return true;
            }
            if (value5 == 46) {
                player.getDialogueManager().showNpcTwoLineDialogue("If there's still one in existence, it's probably in Port", "Sarim.", 591);
                return true;
            }
            if (value5 == 47) {
                player.getDialogueManager().showNpcThreeLineDialogue("Then, of course, you'll need to find a captain willing to", "sail to Crandor, and I'm not sure where you'd find one", "of them!", 591);
                player.getDialogueManager().setNextDialogueStep(30);
                return true;
            }
            if (value5 == 50) {
                player.pendingGameMode = 8;
                player.getDialogueManager().showNpcThreeLineDialogue("That part shouldn't be too difficult, actually. I believe", "the Duke of Lumbridge has a special shield in his", "armoury that is enchanted against dragon's breath.", 591);
                player.getDialogueManager().setNextDialogueStep(30);
                return true;
            }
            if (value5 == 53) {
                player.getDialogueManager().showTwoOptions("What is this place?", "About my quest to kill the dragon...");
                return true;
            }
            if (value5 == 54) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("What is this place?", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("About my quest to kill the dragon...", 591);
                    player.getDialogueManager().setNextDialogueStep(28);
                    return true;
                }
            }
            if (value5 == 55) {
                player.pendingGameMode = 16;
                player.getDialogueManager().showNpcThreeLineDialogue("Thalzar was the most paranoid of the three wizards. He", "hid his map piece and took the secret of its location to", "his grave.", 591);
                return true;
            }
            if (value5 == 56) {
                player.getDialogueManager().showNpcThreeLineDialogue("I don't think you'd be able to find out where it is by", "ordinary means. You'll need to talk to the Oracle on", "Ice Mountain.", 591);
                player.getDialogueManager().setNextDialogueStep(30);
                return true;
            }
        }
        if (npcId == 747 && value42 == 2) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Aye, 'tis a fair day my friend.", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showThreeOptions("Can you sell me a rune platebody?", "I'm not your friend.", "Yes, it's a very nice day.");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can you sell me a rune platebody?", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes, it's a very nice day.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("Aye, may the gods walk by yer side. Now leave me", "alone.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showNpcOneLineDialogue("So, how does thee know I 'ave some?", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showTwoOptions("The Guild master of the Champions' Guild told me.", "I am a master detective.");
                return true;
            }
            if (value5 == 7) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("The Guild master of the Champions' Guild told me.", 591);
                    player.getDialogueManager().setNextDialogueStep(8);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(6);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showNpcThreeLineDialogue("Yes, I suppose he would, wouldn't he? He's always", "sending you fancy-pants 'heroes' up to bother me.", "Telling me I'll give them a quest or sommat like that.", 591);
                return true;
            }
            if (value5 == 9) {
                player.getDialogueManager().showNpcTwoLineDialogue("Well, I'm not going to let just anyone wear my rune", "platemail. It's only for heroes. So, leave me alone.", 591);
                return true;
            }
            if (value5 == 10) {
                player.getDialogueManager().showTwoOptions("I thought you were going to give me a quest.", "That's a pity, I'm not a hero.");
                return true;
            }
            if (value5 == 11) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I thought you were going to give me a quest.", 591);
                    player.getDialogueManager().setNextDialogueStep(12);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(10);
                return true;
            }
            if (value5 == 12) {
                player.getDialogueManager().showNpcOneLineDialogue("*Sigh*", 591);
                return true;
            }
            if (value5 == 13) {
                player.getDialogueManager().showNpcTwoLineDialogue("All right, I'll give ye a quest. I'll let ye wear my rune", "platemail if ye...", 591);
                return true;
            }
            if (value5 == 14) {
                player.getDialogueManager().showNpcOneLineDialogue("Slay the dragon of Crandor!", 591);
                return true;
            }
            if (value5 == 15) {
                player.getDialogueManager().showTwoOptions("A dragon, that sounds like fun.", "I may be a champion, but I don't think I'm up to dragon-killing yet.");
                return true;
            }
            if (value5 == 16) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("A dragon, that sounds like fun!", 591);
                    player.getDialogueManager().setNextDialogueStep(17);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(15);
                return true;
            }
            if (value5 == 17) {
                player.getDialogueManager().showNpcTwoLineDialogue("Hah, yes, you are a typical reckless adventurer, aren't", "you? Now go kill the dragon and get out of my face.", 591);
                return true;
            }
            if (value5 == 18) {
                player.getDialogueManager().showPlayerOneLineDialogue("But how can I defeat the dragon?", 591);
                return true;
            }
            if (value5 == 19) {
                player.getDialogueManager().showNpcThreeLineDialogue("Go talk to the Guild master in the Champions' Guild. He'll", "help ye out if yer so keen on doing a quest. I'm not", "going to be handholding any adventurers.", 591);
                player.setQuestState(this.getQuestId(), 3);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 746 && value42 == 4) {
            if (value5 == 1) {
                player.getDialogueManager().showTwoOptions("I seek a piece of the map to the island of Crandor.", "Can you impart your wise knowledge to me, O Oracle?");
                return true;
            }
            if (value5 == 2) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I seek a piece of the map to the island of Crandor.", 591);
                    player.getDialogueManager().setNextDialogueStep(3);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can you impart your wise knowledge to me, O Oracle?", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
            }
            if (value5 == 3) {
                player.getDialogueManager().showNpcFourLineDialogue("The map's behind a door below,", "but entering is rather tough.", "This is what you need to know:", "You must use the following stuff.", 591);
                return true;
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcFourLineDialogue("First, a drink used by a mage.", "Next, some worm string, changed to sheet.", "Then, a small crustacean cage.", "Last, a bowl that's not seen heat.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("The God Wars are over...as long as the thing they", "were fighting over remains hidden.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 744 && value42 >= 4 && (value2 & 1) == 0 && value42 < 259) {
            if (value5 == 1) {
                player.pendingGameMode = 0;
                player.getDialogueManager().showNpcFourLineDialogue("So, are you interested in buying a ship? Now, I'll be", "straight with you: she's not quite seaworthy right now,", "but give her a bit of work and she'll be the nippiest ship", "this side of Port Khazard.", 591);
                return true;
            }
            if (value5 == 2) {
                if (player.pendingGameMode == 0) {
                    player.getDialogueManager().showFiveOptions("Do you know when she will be seaworthy?", "Would you take me to Crandor when she's ready?", "Why is she damaged?", "I'd like to buy her.", "Ah well, never mind.");
                }
                if (player.pendingGameMode == 1) {
                    player.getDialogueManager().showFourOptions("Would you take me to Crandor when she's ready?", "Why is she damaged?", "I'd like to buy her.", "Ah well, never mind.");
                }
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    if (player.pendingGameMode == 0) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Do you know when she will be seaworthy?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                    }
                    if (player.pendingGameMode == 1) {
                        player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                        player.getDialogueManager().setNextDialogueStep(2);
                    }
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value32 == 3 && player.pendingGameMode == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'd like to buy her.", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
                if (value32 == 4 && player.pendingGameMode == 0) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'd like to buy her.", 591);
                    player.getDialogueManager().setNextDialogueStep(5);
                    return true;
                }
            }
            if (value5 == 4) {
                player.pendingGameMode = 1;
                player.getDialogueManager().showNpcTwoLineDialogue("No, not really. Port Sarim's shipbuilders aren't very", "efficient so it could be quite a while.", 591);
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showNpcTwoLineDialogue("Of course! I'm sure the work needed to do on it", "wouldn't be too expensive.", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showNpcThreeLineDialogue("How does 2,000 gold sound? I'll even throw in my", "cabin boy, Jenkins, for free! He'll swab the decks and", "splice the mainsails for you!", 591);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showTwoOptions("Yep, sounds good.", "I'm not paying that much for a broken boat!");
                return true;
            }
            if (value5 == 8) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yep, sounds good.", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(7);
                    return true;
                }
            }
            if (value5 == 9 && player.getInventoryManager().containsItemAmount(995, 2000)) {
                player.getInventoryManager().removeItem(new ItemStack(995, 2000));
                player.addQuestState(this.getQuestId(), 1);
                player.getDialogueManager().showNpcOneLineDialogue("Okey dokey, she's all yours!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 741 && value42 >= 4 && !player.ownsItem(1540)) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Greetings. Welcome to my castle.", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showThreeOptions("I seek a shield that will protect me from dragonbreath.", "Have you any quests for me?", "Where can I find money?");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I seek a shield that will protect me from dragonbreath.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("A knight going on a dragon quest, hmm? What dragon", "do you intend to slay?", 591);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showTwoOptions("Elvarg, the dragon of Crandor island!", "Oh, no dragon in particular");
                return true;
            }
            if (value5 == 6) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Elvarg, the dragon of Crandor island!", 591);
                    player.getDialogueManager().setNextDialogueStep(7);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(5);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcOneLineDialogue("Elvarg? Are you sure?", 591);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showTwoOptionsWithTitle("Well, are you sure?", "Yes", "No");
                return true;
            }
            if (value5 == 9) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Yes!", 591);
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value5 == 10) {
                player.getDialogueManager().showNpcOneLineDialogue("Well, you're a braver man than I!", 591);
                return true;
            }
            if (value5 == 11) {
                player.getDialogueManager().showPlayerOneLineDialogue("Why is everyone so scared of this dragon?", 591);
                return true;
            }
            if (value5 == 12) {
                player.getDialogueManager().showNpcThreeLineDialogue("Back in my father's day, Crandor was an important", "city-state. Politically, it was as important as Falador or", "Varrock and its ships traded with every port.", 591);
                return true;
            }
            if (value5 == 13) {
                player.getDialogueManager().showNpcThreeLineDialogue("But, one day when I was little, all contact was lost. The", "trading ships and the diplomatic envoys just stopped", "coming.", 591);
                return true;
            }
            if (value5 == 14) {
                player.getDialogueManager().showNpcFourLineDialogue("I remember my father being very scared. He posted", "lookouts on the roof to warn if the dragon was", "approaching. All the city rulers were worried that", "Elvarg would devastate the whole continent.", 591);
                return true;
            }
            if (value5 == 15) {
                player.getDialogueManager().showTwoOptions("I'd better leave that dragon alone.", "So, are you going to give me the shield or not?");
                return true;
            }
            if (value5 == 16) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("So, are you going to give me the shield or not?", 591);
                    player.getDialogueManager().setNextDialogueStep(17);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value5 == 17) {
                player.getDialogueManager().showNpcTwoLineDialogue("If you really think you're up to it then perhaps you", "are the one who can kill this dragon.", 591);
                return true;
            }
            if (value5 == 18) {
                player.getInventoryManager().addOrDropItem(new ItemStack(1540, 1));
                player.getDialogueManager().showItemMessage("The Duke hands you a heavy orange shield.", new ItemStack(1540, 1));
                player.getDialogueManager().setNextDialogueStep(19);
                return true;
            }
            if (value5 == 19) {
                player.getDialogueManager().showNpcOneLineDialogue("Take care out there. If you kill it...", 591);
                return true;
            }
            if (value5 == 20) {
                player.getDialogueManager().showNpcTwoLineDialogue("If you kill it, for Saradomin's sake make sure it's really", "dead!", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 918) {
            if (value42 == 259) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcThreeLineDialogue("Why, hello there, lad. Me friends call me Ned. I was a", "man of the sea, but it's past me now. Could I be", "making or selling you some rope?", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showThreeOptions("You're a sailor? Could you take me to Crandor?", "Yes, I would like some rope.", "No thanks Ned, I don't need any.");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerTwoLineDialogue("You're a sailor? Could you take me to the island of", "Crandor?", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                    player.getDialogueManager().setNextDialogueStep(2);
                    return true;
                }
                if (value5 == 4) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Well, I was a sailor. I've not been able to get work at", "sea these days, though. They say I'm too old.", 591);
                    return true;
                }
                if (value5 == 5) {
                    player.getDialogueManager().showNpcOneLineDialogue("Sorry, where was it you said you wanted to go?", 591);
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showPlayerOneLineDialogue("To the island of Crandor.", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showNpcOneLineDialogue("Crandor?", 591);
                    return true;
                }
                if (value5 == 8) {
                    player.getDialogueManager().showNpcTwoLineDialogue("But... It would be a chance to sail a ship once more.", "I'd sail anywhere if it was a chance to sail again.", 591);
                    return true;
                }
                if (value5 == 9) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Then again, no captain in his right mind would sail to", "that island...", 591);
                    return true;
                }
                if (value5 == 10) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ah, you only live once! I'll do it!", 591);
                    return true;
                }
                if (value5 == 11) {
                    player.getDialogueManager().showNpcOneLineDialogue("So, where's your ship?", 591);
                    return true;
                }
                if (value5 == 12) {
                    player.getDialogueManager().showPlayerOneLineDialogue("It's the Lady Lumbridge, in Port Sarim.", 591);
                    return true;
                }
                if (value5 == 13) {
                    player.getDialogueManager().showNpcTwoLineDialogue("That old pile of junk? Last I heard, she wasn't", "seaworthy.", 591);
                    return true;
                }
                if (value5 == 14) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I fixed her up!", 591);
                    return true;
                }
                if (value5 == 15) {
                    player.getDialogueManager().showNpcOneLineDialogue("You did? Excellent!", 591);
                    return true;
                }
                if (value5 == 16) {
                    player.getDialogueManager().showNpcOneLineDialogue("Just show me the map and we can get ready to go!", 591);
                    return true;
                }
                if (value5 == 17) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Here you go.", 591);
                    return true;
                }
                if (value5 == 18 && player.getInventoryManager().containsItem(1538)) {
                    player.getInventoryManager().removeItem(new ItemStack(1538, 1));
                    player.setQuestState(this.getQuestId(), 260);
                    player.getDialogueManager().showItemMessage("You hand the map to Ned.", new ItemStack(1538, 1));
                    return true;
                }
            }
            if (value42 == 260 && value5 == 19) {
                player.getDialogueManager().showNpcOneLineDialogue("Excellent! I'll meet you at the ship, then.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 754) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Ahoy! What d'ye think of yer ship then?", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showThreeOptions("I'm ready to go back to shore.", "I'd like to inspect her some more.", "Can you sail this ship to Crandor?");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I'd like to inspect her some more.", 591);
                    player.getDialogueManager().setNextDialogueStep(9);
                    return true;
                }
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Can you sail this ship to Crandor?", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("Not me, sir! I'm just an 'umble cabin boy, You'll need", "a proper cap'n.", 591);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showPlayerOneLineDialogue("Where can I find a captain?", 591);
                return true;
            }
            if (value5 == 6) {
                player.getDialogueManager().showNpcThreeLineDialogue("The cap'n s round 'ere seem to be a mite scared of", "Crandor. I ask 'em why and they just say it was afore", "my time,", 591);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcThreeLineDialogue("but there is one cap'n I reckon might 'elp. I 'eard", "there's a retired 'un who lives in Draynor Village who's", "so desperate to sail again 'e'd take any job.", 591);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showNpcTwoLineDialogue("I can't remember 'is name, but 'e lives in Draynor", "Village an' makes rope.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
            if (value5 == 9) {
                player.getDialogueManager().showNpcOneLineDialogue("Aye.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 745 && value42 >= 4 && (!player.ownsItem(1536) && !player.ownsItem(1538) && value5 < 11 || value5 == 11)) {
            if (value5 == 1) {
                player.getDialogueManager().showNpcOneLineDialogue("Whut you want?", 591);
                return true;
            }
            if (value5 == 2) {
                player.getDialogueManager().showThreeOptions("I believe you've got a piece of map that I need.", "What are you in for?", "Sorry, thought this was a zoo.");
                return true;
            }
            if (value5 == 3) {
                if (value32 == 1) {
                    player.getDialogueManager().showPlayerOneLineDialogue("I believe you've got a piece of map that I need.", 591);
                    player.getDialogueManager().setNextDialogueStep(4);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(2);
                return true;
            }
            if (value5 == 4) {
                player.getDialogueManager().showNpcTwoLineDialogue("So? Why should I be giving it to you? What you do", "for Wormbrain?", 591);
                return true;
            }
            if (value5 == 5) {
                player.getDialogueManager().showFourOptions("I'm not going to do anything for you. Forget it.", "I'll let you live. I could just kill you.", "I suppose I could pay you for the map piece...", "Where did you get the map piece from?");
                return true;
            }
            if (value5 == 6) {
                if (value32 == 3) {
                    player.getDialogueManager().showPlayerTwoLineDialogue("I suppose I could pay you for the map piece. Say, 500", "coins?", 591);
                    player.getDialogueManager().setNextDialogueStep(7);
                    return true;
                }
                player.getDialogueManager().showOneLineStatement("This option is currently missing...");
                player.getDialogueManager().setNextDialogueStep(5);
                return true;
            }
            if (value5 == 7) {
                player.getDialogueManager().showNpcOneLineDialogue("Me not stoopid, it worth at least 10,000 coins!", 591);
                return true;
            }
            if (value5 == 8) {
                player.getDialogueManager().showTwoOptions("You must be joking! Forget it.", "Alright then, 10,000 it is.");
                return true;
            }
            if (value5 == 9) {
                if (value32 == 2) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Alright then, 10,000 coins it is.", 591);
                    player.getDialogueManager().setNextDialogueStep(10);
                    return true;
                }
                player.getDialogueManager().finishDialogue();
                return false;
            }
            if (value5 == 10 && player.getInventoryManager().containsItemAmount(995, 10000)) {
                player.getInventoryManager().removeItem(new ItemStack(995, 10000));
                player.getInventoryManager().addOrDropItem(new ItemStack(1536, 1));
                player.getDialogueManager().showItemMessage("You buy the map piece from Wormbrain.", new ItemStack(1536, 1));
                return true;
            }
            if (value5 == 11) {
                player.getDialogueManager().showNpcTwoLineDialogue("Fank you very much! Now me can bribe da guards,", "hehehe.", 591);
                player.getDialogueManager().finishDialogue();
                return true;
            }
        }
        if (npcId == 743) {
            if (value42 == 260) {
                if (value5 == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue("Ah! There you are! Ready to go?", 591);
                    return true;
                }
                if (value5 == 2) {
                    player.getDialogueManager().showTwoOptions("Yep, lets go!", "No, I'm not quite ready yet...");
                    return true;
                }
                if (value5 == 3) {
                    if (value32 == 1) {
                        player.getDialogueManager().showPlayerOneLineDialogue("Yep, lets go!", 591);
                        player.getDialogueManager().setNextDialogueStep(4);
                        return true;
                    }
                    player.getDialogueManager().finishDialogue();
                    return false;
                }
                if (value5 == 4) {
                    TravelManager.handleShipRoute(player, ShipRoute.PORT_SARIM_TO_CRANDOR);
                    player.setQuestState(this.getQuestId(), 261);
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You feel the ship begin to move...");
                    player2 = player;
                    player2.packetSender.sendGameMessage("The ship is sailing across the ocean...");
                    player.getDialogueManager().markDialogueInactive();
                    return false;
                }
            }
            if (value42 == 261) {
                if (value5 == 5) {
                    player.getDialogueManager().showOneLineStatement("You hear a loud crack as the ship comes to a juddering halt.");
                    return true;
                }
                if (value5 == 6) {
                    player.getDialogueManager().showNpcTwoLineDialogue("Um.... I think we're there. I probably should have paid", "closer attention.", 591);
                    return true;
                }
                if (value5 == 7) {
                    player.getDialogueManager().showPlayerOneLineDialogue("Gee... you think?", 591);
                    player.getDialogueManager().finishDialogue();
                    return true;
                }
            }
        }
        return false;
    }
}
