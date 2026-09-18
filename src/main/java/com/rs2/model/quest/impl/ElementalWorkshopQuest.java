package com.rs2.model.quest.impl;

import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.ElementalShieldSmithingTask;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.model.task.TickTask;

public final class ElementalWorkshopQuest
extends QuestScript {
    private int elementalShieldBookLastPageIndex = 1;

    public ElementalWorkshopQuest(int value2) {
        super(32);
        super.setQuestPointReward(1);
    }

    @Override
    public final String[] buildQuestJournal(Player player, int value3) {
        int value2 = value3 - 3;
        if (value3 == 0) {
            value3 = player.getSkillManager().getBaseLevel(12);
            value2 = player.getSkillManager().getBaseLevel(14);
            int skillManager = player.getSkillManager().getBaseLevel(13);
            String[] stringValues = new String[]{"I can start this quest by reading a", "book found in Seers village.", "", "Minimum requirements:", String.valueOf(value2 >= 20 ? "@str@" : "") + "Level 20 Mining", String.valueOf(skillManager >= 20 ? "@str@" : "") + "Level 20 Smithing", String.valueOf(value3 >= 20 ? "@str@" : "") + "Level 20 Crafting"};
            return stringValues;
        }
        if (value3 == 2) {
            String[] stringValues2 = new String[]{"I should now search for the hidden workshop located", "in the village of the Seers."};
            return stringValues2;
        }
        if (value3 >= 3 && value3 < 66) {
            String[] stringValues3 = new String[]{"I should do the following things now:", String.valueOf((value2 & 4) != 0 ? "@str@" : "") + "Get the waterwheel running", String.valueOf((value2 & 0x10) != 0 ? "@str@" : "") + "Start the bellows", String.valueOf((value2 & 0x20) != 0 ? "@str@" : "") + "Warm up the furnace"};
            return stringValues3;
        }
        if (value3 == 66) {
            String[] stringValues4 = new String[]{"I have fixed everything, and should now be able to make", "the elemental shield."};
            return stringValues4;
        }
        if (value3 == 1) {
            String[] stringValues5 = new String[]{"Quest Completed!", "", "You were awarded:", "1 Quest Point", "5000 Crafting  XP", "5000 Smithing XP", "The ability to make elemental shields."};
            return stringValues5;
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
        player2.packetSender.sendInterfaceText("5000 Crafting  XP", 12151);
        player2 = player;
        player2.packetSender.sendInterfaceText("5000 Smithing XP", 12152);
        player2 = player;
        player2.packetSender.sendInterfaceText("The ability to make elemental shields.", 12153);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12154);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 12155);
        player.getSkillManager().addQuestExperience(12, 5000.0);
        player.getSkillManager().addQuestExperience(13, 5000.0);
        player2 = player;
        player2.packetSender.sendInterfaceModel(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 250, 2890);
        player2 = player;
        player2.packetSender.showInterface(InterfaceDefinition.interfaceCount <= 12140 ? 1689 : 12140);
        player2 = player;
        player.deferLevelUpInterfaces = false;
    }

    @Override
    public final boolean handleFirstObjectAction(Player player, int objectId, int value5, int value32, int value42) {
        int value2 = value42 - 3;
        if (objectId == 3403) {
            if (player.ownedNpc != null && !player.ownedNpc.isDead() && player.ownedNpc.getNpcId() == 1023) {
                return true;
            }
            GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(player, 14);
            if (gatheringToolDefinition == null) {
                Player player2 = player;
                player2.packetSender.sendGameMessage("You do not have a pickaxe that you can use.");
                return true;
            }
            if (!SkillActionHelper.checkSkillRequirement(player, 14, 20, "mine here")) {
                return true;
            }
            new DynamicObject(ServerSettings.placeholderObjectId, value5, value32, player.getPosition().getPlane(), 0, 10, objectId, 100);
            Npc npc = new Npc(1023);
            GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, npc, true, false);
            npc.getUpdateState().setAnimation(1038);
            npc.getUpdateState().setForcedText("Grr... Ge'roff us!");
            return true;
        }
        if (objectId == 3389 && value5 == 2716 && value32 == 3481 && !player.ownsItem(2886)) {
            player.getInventoryManager().addOrDropItem(new ItemStack(2886, 1));
            player.getDialogueManager().showItemMessage("You find a book titled 'The Elemental Shield'.", new ItemStack(2886, 1));
            return true;
        }
        if (objectId == 3410 && value5 == 2734 && value32 == 9882) {
            if (value42 == 1) {
                return false;
            }
            if ((value2 & 8) != 0) {
                Player player3 = player;
                player3.packetSender.sendGameMessage("You have already fixed the bellows.");
                return true;
            }
            if (!SkillActionHelper.checkSkillRequirement(player, 12, 20, "fix the bellows")) {
                return true;
            }
            if (player.getInventoryManager().containsItemAmount(1741, 1) && player.getInventoryManager().containsItemAmount(1733, 1) && player.getInventoryManager().containsItemAmount(1734, 1)) {
                Player player4 = player;
                player4.packetSender.sendGameMessage("You stitch the leather over the hole in the bellows.");
                player.getInventoryManager().removeItem(new ItemStack(1741, 1));
                player.getInventoryManager().removeItem(new ItemStack(1734, 1));
                player.addQuestState(this.getQuestId(), 8);
                return true;
            }
        }
        if (objectId == 3397 && value5 == 2724 && value32 == 9894) {
            if (!player.ownsItem(2888)) {
                player.getInventoryManager().addOrDropItem(new ItemStack(2888, 1));
                Player player5 = player;
                player5.packetSender.sendGameMessage("You find a stone bowl.");
            } else {
                Player player6 = player;
                player6.packetSender.sendGameMessage("It's empty.");
            }
            return true;
        }
        if (objectId == 3390 && value5 == 2710 && value32 == 3495 || objectId == 3391 && value5 == 2709 && value32 == 3495) {
            if (player.getPosition().getY() < 3496) {
                if (player.getInventoryManager().containsItemAmount(2887, 1)) {
                    Player player7 = player;
                    player7.packetSender.queueRelativeMovementStep(0, 1, true);
                    player7 = player;
                    player7.packetSender.openNorthShiftedDoubleDoorPair(3391, 3390, 2709, 3495, 2710, 3495, 0);
                    player7 = player;
                    player7.packetSender.sendGameMessage("You use the battered key to open the doors.");
                    return true;
                }
            } else {
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(0, -1, true);
                player8 = player;
                player8.packetSender.openNorthShiftedDoubleDoorPair(3391, 3390, 2709, 3495, 2710, 3495, 0);
                return true;
            }
        }
        if (objectId == 3415 && value5 == 2710 && value32 == 3497) {
            player.moveTo(new Position(2716, 9888, 0));
            if (value42 == 2) {
                player.getDialogueManager().showPlayerTwoLineDialogue("Now to explore this area thoroughly, to find what", "forgotten secrets it contains.", 591);
                player.getDialogueManager().finishDialogue();
                player.setQuestState(this.getQuestId(), 3);
            }
            return true;
        }
        if (objectId == 3416 && value5 == 2714 && value32 == 9887) {
            player.moveTo(new Position(2709, 3498, 0));
            return true;
        }
        if (objectId == 3404 && value5 == 2726 && value32 == 9908) {
            if (value42 == 1) {
                return false;
            }
            if ((value2 & 1) == 0) {
                Player player9 = player;
                player9.packetSender.sendGameMessage("You turn the handle.");
                player.addQuestState(this.getQuestId(), 1);
            } else {
                Player player10 = player;
                player10.packetSender.sendGameMessage("You have already turned this handle.");
            }
            return true;
        }
        if (objectId == 3405 && value5 == 2713 && value32 == 9908) {
            if (value42 == 1) {
                return false;
            }
            if ((value2 & 1) == 0) {
                Player player11 = player;
                player11.packetSender.sendGameMessage("It doesn't seem to work quite yet.");
            }
            if ((value2 & 2) == 0 && (value2 & 1) != 0) {
                Player player12 = player;
                player12.packetSender.sendGameMessage("You turn the handle.");
                player.addQuestState(this.getQuestId(), 2);
            }
            if ((value2 & 2) != 0) {
                Player player13 = player;
                player13.packetSender.sendGameMessage("You have already turned this handle.");
            }
            return true;
        }
        if (objectId == 3406 && value5 == 2722 && value32 == 9906) {
            if (value42 == 1) {
                return false;
            }
            if ((value2 & 1) == 0 || (value2 & 2) == 0) {
                Player player14 = player;
                player14.packetSender.sendGameMessage("It doesn't seem to work quite yet.");
            }
            if ((value2 & 4) == 0 && (value2 & 1) != 0 && (value2 & 2) != 0) {
                Player player15 = player;
                player15.packetSender.sendGameMessage("You pull the lever.");
                player15 = player;
                player15.packetSender.sendGameMessage("You hear the sound of a water wheel starting up.");
                player.addQuestState(this.getQuestId(), 4);
            }
            if ((value2 & 4) != 0) {
                Player player16 = player;
                player16.packetSender.sendGameMessage("You have already fixed the waterwheel.");
            }
            return true;
        }
        if (objectId == 3409 && value5 == 2734 && value32 == 9887) {
            if (value42 == 1) {
                return false;
            }
            if ((value2 & 8) == 0) {
                Player player17 = player;
                player17.packetSender.sendGameMessage("You should fix the bellows first before pulling the lever.");
            }
            if ((value2 & 0x10) == 0 && (value2 & 8) != 0) {
                Player player18 = player;
                player18.packetSender.sendGameMessage("You pull the lever.");
                player18 = player;
                player18.packetSender.sendGameMessage("The bellows pump air down the pipe.");
                player.addQuestState(this.getQuestId(), 16);
            }
            if ((value2 & 0x10) != 0) {
                Player player19 = player;
                player19.packetSender.sendGameMessage("You have already pulled the lever.");
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        if (itemId == 2886 && value2 == 946 || itemId == 946 && value2 == 2886) {
            if (value32 != 0) {
                if (!player.ownsItem(2887)) {
                    player.getInventoryManager().addItem(new ItemStack(2887, 1));
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You make a small cut in the spine of the book.");
                    player2 = player;
                    player2.packetSender.sendGameMessage("Inside you find a small, old, battered key.");
                    return true;
                }
            } else {
                Player player3 = player;
                player3.packetSender.sendGameMessage("You don't want to damage the book.");
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleItemOnObject(Player objectId, int objectId2, int value4, int value32) {
        int value2 = value32 - 3;
        if (objectId2 == 2888 && value4 == 3414) {
            ((Player)objectId).getInventoryManager().replaceItem(new ItemStack(2888, 1), new ItemStack(2889, 1));
            Player player = objectId;
            player.packetSender.sendGameMessage("You fill the bowl with hot lava.");
            return true;
        }
        if (objectId2 == 2889 && value4 == 3413) {
            if (value32 == 1) {
                return false;
            }
            if ((value2 & 0x20) != 0) {
                Player player = objectId;
                player.packetSender.sendGameMessage("You have already added lava to the furnace.");
                return true;
            }
            ((Player)objectId).getInventoryManager().replaceItem(new ItemStack(2889, 1), new ItemStack(2888, 1));
            Player player = objectId;
            player.packetSender.sendGameMessage("You empty the lava into the furnace.");
            ((Player)objectId).addQuestState(this.getQuestId(), 32);
            return true;
        }
        if (objectId2 == 2892 && value4 == 3413 && (value32 == 66 || value32 == 1)) {
            SmeltingHandler.handleOreOnFurnace((Player)objectId, 2892);
            return true;
        }
        if (objectId2 == 2893 && value4 == 3402 && (value32 == 66 || value32 == 1) && ((Player)objectId).getInventoryManager().containsItemAmount(2347, 1)) {
            if (value32 != 1) {
                if (((Player)objectId).getInventoryManager().containsItemAmount(2886, 1)) {
                    Player player = objectId;
                    player.packetSender.sendGameMessage("Following the instructions in the book you make an elemental shield.");
                } else {
                    return true;
                }
            }
            Player player = objectId;
            player.packetSender.sendSoundEffect(468, 1, 0);
            ((Entity)objectId).getUpdateState().setAnimation(898);
            World.getTaskScheduler().schedule(new ElementalShieldSmithingTask(this, 3, (Player)objectId, value32));
            return true;
        }
        return false;
    }

    private void showElementalShieldBookPage(Player player, int value4) {
        String[] stringValues;
        Player player2;
        Player player3 = player2 = player;
        player2.packetSender.sendInterfaceText("", 14165);
        player3 = player2;
        player3.packetSender.sendInterfaceText("", 14166);
        int value2 = 843;
        while (value2 <= 864) {
            player3 = player2;
            player3.packetSender.sendInterfaceText("", value2);
            ++value2;
        }
        int value3 = value4;
        if (value3 == 0) {
            String[] stringValues2;
            stringValues = stringValues2 = new String[]{"Book of the elemental shield", "", "", "Within the pages of this", "book you will find the", "secret to working the", "very elements themselves.", "Early in the fifth age, a", "new ore was discovered.", "This ore has a unique", "property of absorbing,", "transforming or focusing", "elemental energy. A", "workshop was erected", "close by to work this new", "material. The workshop", "was set up for artisans", "and inventors to be able", "to come and create", "devices made from the", "unique ore, found only in", "the village of the Seers."};
        } else if (value3 == 1) {
            String[] stringValues3 = new String[]{"Book of the elemental shield", "", "", "After some time of", "successful industry the", "true power of this ore", "became apparent, as", "greater and more", "powerful weapons were", "created. Realising the", "threat this posed, the magi", "of the time closed down", "the workshop and bound", "it under lock and key,", "also trying to destroy all", "knowledge of", "manufacturing processes.", "Yet this book remains and", "you may still find a way", "to enter the workshop", "within this leather bound", "volume."};
            stringValues = stringValues3;
        } else {
            stringValues = null;
        }
        String[] stringValues4 = stringValues;
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues4[0], 903);
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues4[1], 14165);
        player3 = player;
        player3.packetSender.sendInterfaceText((String)stringValues4[2], 14166);
        value3 = 3;
        while (value3 < stringValues4.length) {
            player3 = player;
            player3.packetSender.sendInterfaceText((String)stringValues4[value3], value3 + 843 - 3);
            ++value3;
        }
        player3 = player;
        player3.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == 0 ? 1 : 0, 840);
        player3 = player;
        player3.packetSender.setInterfaceHiddenFlag(player.activeBookPageIndex == this.elementalShieldBookLastPageIndex ? 1 : 0, 842);
    }

    @Override
    public final boolean handleButtonClick(Player player, int buttonId, int value2) {
        if (player.activeBookItemId == 2886) {
            if (buttonId == 841 && player.activeBookPageIndex < this.elementalShieldBookLastPageIndex) {
                ++player.activeBookPageIndex;
                this.showElementalShieldBookPage(player, player.activeBookPageIndex);
                return true;
            }
            if (buttonId == 839 && player.activeBookPageIndex > 0) {
                --player.activeBookPageIndex;
                this.showElementalShieldBookPage(player, player.activeBookPageIndex);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        if (itemId == 3214 && value2 == 2886) {
            this.showElementalShieldBookPage(player, 0);
            player.activeBookItemId = value2;
            player.activeBookPageIndex = 0;
            Player player2 = player;
            player2.packetSender.showInterface(837);
            if (value32 == 0) {
                player2 = player;
                player2.packetSender.sendGameMessage("The book has two parts: an introduction and an instruction section.");
                player2 = player;
                player2.packetSender.sendGameMessage("You flip the book open to the introduction and start reading.");
                this.startQuest(player);
            }
            return true;
        }
        return false;
    }
}

