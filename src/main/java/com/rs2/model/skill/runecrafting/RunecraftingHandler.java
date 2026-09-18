package com.rs2.model.skill.runecrafting;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.runecrafting.RuneDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingAltarDefinition;
import com.rs2.util.GameUtil;

public final class RunecraftingHandler {
    public static int PURE_ESSENCE_ITEM_ID = 7936;
    private static int[] SCRYING_ORB_NPC_IDS = new int[]{171, 300, 462, 553, 844};

    public static void craftRunesAtAltar(Player player, RuneDefinition runeDefinition) {
        if (!ServerSettings.runecraftingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (player.getQuestState(14) != 1) {
            QuestDefinition questDefinition = QuestDefinition.forId(14);
            String name = questDefinition.getName();
            player.packetSender.sendGameMessage("You need to complete " + name + " to do this.");
            return;
        }
        if (!SkillActionHelper.checkSkillRequirement(player, 20, runeDefinition.getRequiredLevel(), "craft this rune")) {
            return;
        }
        int inventoryManager = player.getInventoryManager().getItemAmount(1436);
        int inventoryManager2 = player.getInventoryManager().getItemAmount(PURE_ESSENCE_ITEM_ID);
        double multipleRunesLevelInterval = runeDefinition.getMultipleRunesLevelInterval() < 0 ? 0 : player.getSkillManager().getBaseLevel(20) / runeDefinition.getMultipleRunesLevelInterval();
        int value = (int)Math.floor(multipleRunesLevelInterval) + 1;
        if (runeDefinition.getRuneItemId() >= 560) {
            if (inventoryManager2 <= 0) {
                if (PURE_ESSENCE_ITEM_ID != 1436) {
                    player.packetSender.sendGameMessage("You need pure essence to make these kinds of runes.");
                } else {
                    player.packetSender.sendGameMessage("You need rune essence to make runes.");
                }
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return;
            }
            player.getInventoryManager().removeItem(new ItemStack(PURE_ESSENCE_ITEM_ID, inventoryManager2));
            player.getInventoryManager().addItem(new ItemStack(runeDefinition.getRuneItemId(), inventoryManager2 * value));
            player.getSkillManager().addExperience(20, runeDefinition.getExperience() * (double)inventoryManager2);
        } else {
            if (inventoryManager2 <= 0 && inventoryManager <= 0) {
                if (PURE_ESSENCE_ITEM_ID != 1436) {
                    player.packetSender.sendGameMessage("You need rune or pure essence to make these kinds of runes.");
                } else {
                    player.packetSender.sendGameMessage("You need rune essence to make runes.");
                }
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return;
            }
            int index = 0;
            while (index < 28) {
                player.getInventoryManager().removeItem(new ItemStack(1436, inventoryManager));
                if (PURE_ESSENCE_ITEM_ID != 1436) {
                    player.getInventoryManager().removeItem(new ItemStack(PURE_ESSENCE_ITEM_ID, inventoryManager2));
                }
                ++index;
            }
            player.getInventoryManager().addItem(new ItemStack(runeDefinition.getRuneItemId(), inventoryManager * value));
            player.getSkillManager().addExperience(20, runeDefinition.getExperience() * (double)inventoryManager);
            if (PURE_ESSENCE_ITEM_ID != 1436) {
                player.getInventoryManager().addItem(new ItemStack(runeDefinition.getRuneItemId(), inventoryManager2 * value));
                player.getSkillManager().addExperience(20, runeDefinition.getExperience() * (double)inventoryManager2);
            }
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
        }
        player.packetSender.sendSoundEffect(481, 1, 0);
        player.getUpdateState().setAnimation(791);
        player.getUpdateState().setGraphicHeight100(186);
    }

    public static void recordScryingOrbTeleport(Player player) {
        int value;
        int value2;
        int value3;
        recordScryingOrbTeleportControlExit1: {
            value3 = player.getAbyssMageNpcId();
            int index = 0;
            while (index < SCRYING_ORB_NPC_IDS.length) {
                value2 = SCRYING_ORB_NPC_IDS[index];
                if (value3 == value2) {
                    value = index;
                    break recordScryingOrbTeleportControlExit1;
                }
                ++index;
            }
            value = -1;
        }
        value3 = 1 + value;
        ItemStack itemStack = player.getInventoryManager().getContainer().findFlatItem(5519);
        value2 = itemStack.getMetadata();
        if ((value2 & GameUtil.bitFlag(value3)) == 0) {
            itemStack.setMetadata(value2 += GameUtil.bitFlag(value3));
        }
        Player player2 = player;
        value2 = 0;
        int metadata = itemStack.getMetadata();
        int index2 = 0;
        while (index2 < SCRYING_ORB_NPC_IDS.length) {
            int value4 = index2 + 1;
            if ((metadata & GameUtil.bitFlag(value4)) != 0) {
                ++value2;
            }
            ++index2;
        }
        if (value2 >= 3) {
            player2.getInventoryManager().removeItem(new ItemStack(5519));
            player2.getInventoryManager().addItem(new ItemStack(5518));
            Player player3 = player2;
            player3.packetSender.sendGameMessage("Your scrying orb has absorbed enough teleport information.");
        }
    }

    public static void startAbyssMageTeleport(Player player, Npc npc) {
        if (player.getQuestState(14) != 1) {
            QuestDefinition questDefinition = QuestDefinition.forId(14);
            String name = questDefinition.getName();
            player.packetSender.sendGameMessage("You need to complete " + name + " to do this.");
            return;
        }
        player.setAbyssMageNpcId(npc.getNpcId());
        npc.startAbyssMageTeleport(player, 2911, 4832, 0, "Senventior disthine molenko!");
    }

    public static boolean locateTalismanDirection(Player player, int direction) {
        RunecraftingAltarDefinition altarDefinition = null;
        for (RunecraftingAltarDefinition candidate : RunecraftingAltarDefinition.values()) {
            if (direction == candidate.talismanItemId) {
                altarDefinition = candidate;
                break;
            }
        }
        if (altarDefinition == null) {
            return false;
        }
        int y = altarDefinition.ruinsPosition.getY();
        int x = altarDefinition.ruinsPosition.getX();
        String text = "";
        String text2 = "";
        if (player.getPosition().getX() >= x) {
            text = "west";
        }
        if (player.getPosition().getY() > y) {
            text2 = "South";
        }
        if (player.getPosition().getX() < x) {
            text = "east";
        }
        if (player.getPosition().getY() <= y) {
            text2 = "North";
        }
        player.packetSender.sendGameMessage("You feel a slight pull towards " + text2 + "-" + text + "...");
        return true;
    }

}

