package com.rs2.model.randomevent;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventNpcDefinition;
import com.rs2.model.randomevent.RandomEventNpcReminderEvent;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class RandomEventManager {
    private static int[][] JEKYLL_HERB_POTION_PAIRS = new int[][]{{249, 113}, {251, 2446}, {253, 2428}, {255, 2430}, {257, 3008}, {2998, 2432}, {259, 3032}, {261, 2440}, {263, 3016}, {3000, 2440}, {265, 3024}, {2481, 2442}, {267, 3040}};

    public static ItemStack selectJekyllRequestedHerb() {
        return new ItemStack(JEKYLL_HERB_POTION_PAIRS[GameUtil.randomExclusive(13)][0]);
    }

    public static ItemStack getJekyllPotionRewardForHerb(int value2) {
        if (value2 == 1) {
            return new ItemStack(117);
        }
        int[][] integerValues = JEKYLL_HERB_POTION_PAIRS;
        int index = 0;
        while (index < 13) {
            int[] integerValues2 = integerValues[index];
            if (integerValues2[0] == value2) {
                return new ItemStack(integerValues2[1]);
            }
            ++index;
        }
        return null;
    }

    public static boolean isRandomEventCombatExcludedNpcId(int npcId) {
        RandomEventNpcDefinition[] randomEventNpcDefinitionArray = RandomEventNpcDefinition.values();
        int length = randomEventNpcDefinitionArray.length;
        int index = 0;
        while (index < length) {
            RandomEventNpcDefinition randomEventNpcDefinition = randomEventNpcDefinitionArray[index];
            RandomEventNpcDefinition randomEventNpcDefinition2 = randomEventNpcDefinition;
            randomEventNpcDefinition2 = randomEventNpcDefinition;
            if (randomEventNpcDefinition.ignorePenaltyNpcId == npcId) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static void spawnRandomEventNpc(Player player, RandomEventNpcDefinition randomEventNpcDefinition) {
        if (player.ownedNpc != null) {
            return;
        }
        Npc npc = new Npc(randomEventNpcDefinition.npcId);
        player.setActiveRandomEventNpc(npc);
        GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, npc, false, false);
        player.packetSender.sendStillGraphic(86, npc.getPosition(), 0x640000);
        player.packetSender.sendSoundEffect(300, 1, 0);
        int ignorePenaltyNpcId = randomEventNpcDefinition.ignorePenaltyNpcId;
        String[] reminderLines = randomEventNpcDefinition.reminderLines;
        CycleEventHandler.getInstance().schedule(npc, new RandomEventNpcReminderEvent(player, npc, reminderLines, ignorePenaltyNpcId), 1);
    }

    public static boolean handleLampButtonClick(Player player, int buttonId) {
        switch (buttonId) {
            case 2812: {
                Player player2 = player;
                player2.packetSender.sendConfig(261, 1);
                player.setSelectedLampSkill(0);
                return true;
            }
            case 2813: {
                Player player3 = player;
                player3.packetSender.sendConfig(261, 2);
                player.setSelectedLampSkill(2);
                return true;
            }
            case 2814: {
                Player player4 = player;
                player4.packetSender.sendConfig(261, 3);
                player.setSelectedLampSkill(4);
                return true;
            }
            case 2815: {
                Player player5 = player;
                player5.packetSender.sendConfig(261, 4);
                player.setSelectedLampSkill(6);
                return true;
            }
            case 2816: {
                Player player6 = player;
                player6.packetSender.sendConfig(261, 5);
                player.setSelectedLampSkill(1);
                return true;
            }
            case 2817: {
                Player player7 = player;
                player7.packetSender.sendConfig(261, 6);
                player.setSelectedLampSkill(3);
                return true;
            }
            case 2818: {
                Player player8 = player;
                player8.packetSender.sendConfig(261, 7);
                player.setSelectedLampSkill(5);
                return true;
            }
            case 2819: {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                } else if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                } else {
                    Player player9 = player;
                    player9.packetSender.sendConfig(261, 8);
                    player.setSelectedLampSkill(16);
                }
                return true;
            }
            case 2820: {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                } else if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                } else {
                    Player player10 = player;
                    player10.packetSender.sendConfig(261, 9);
                    player.setSelectedLampSkill(15);
                }
                return true;
            }
            case 2821: {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                } else if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                } else {
                    Player player11 = player;
                    player11.packetSender.sendConfig(261, 10);
                    player.setSelectedLampSkill(17);
                }
                return true;
            }
            case 2822: {
                Player player12 = player;
                player12.packetSender.sendConfig(261, 11);
                player.setSelectedLampSkill(12);
                return true;
            }
            case 2823: {
                Player player13 = player;
                player13.packetSender.sendConfig(261, 12);
                player.setSelectedLampSkill(20);
                return true;
            }
            case 2824: {
                Player player14 = player;
                player14.packetSender.sendConfig(261, 13);
                player.setSelectedLampSkill(14);
                return true;
            }
            case 2825: {
                Player player15 = player;
                player15.packetSender.sendConfig(261, 14);
                player.setSelectedLampSkill(13);
                return true;
            }
            case 2826: {
                Player player16 = player;
                player16.packetSender.sendConfig(261, 15);
                player.setSelectedLampSkill(10);
                return true;
            }
            case 2827: {
                Player player17 = player;
                player17.packetSender.sendConfig(261, 16);
                player.setSelectedLampSkill(7);
                return true;
            }
            case 2828: {
                Player player18 = player;
                player18.packetSender.sendConfig(261, 17);
                player.setSelectedLampSkill(11);
                return true;
            }
            case 2829: {
                Player player19 = player;
                player19.packetSender.sendConfig(261, 18);
                player.setSelectedLampSkill(8);
                return true;
            }
            case 2830: {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                } else if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                } else {
                    Player player20 = player;
                    player20.packetSender.sendConfig(261, 19);
                    player.setSelectedLampSkill(9);
                }
                return true;
            }
            case 12034: {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                } else if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                } else {
                    Player player21 = player;
                    player21.packetSender.sendConfig(261, 20);
                    player.setSelectedLampSkill(18);
                }
                return true;
            }
            case 13914: {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                } else if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                } else {
                    Player player22 = player;
                    player22.packetSender.sendConfig(261, 21);
                    player.setSelectedLampSkill(19);
                }
                return true;
            }
            case 2831: {
                Player player23 = player;
                player23.packetSender.closeInterfaces();
                if (player.getSelectedLampSkill() == -1) {
                    player23 = player;
                    player23.packetSender.sendGameMessage("You need to select a skill that you wish to level.");
                } else if (player.getInventoryManager().removeItem(new ItemStack(2528))) {
                    int skillManager = player.getSkillManager().getBaseLevel(player.getSelectedLampSkill()) * 10;
                    int skillManager2 = player.getSkillManager().calculateExperienceGain(player.getSelectedLampSkill(), skillManager);
                    player.getSkillManager().addExperience(player.getSelectedLampSkill(), skillManager);
                    Player player24 = player;
                    player24.packetSender.sendGameMessage("Congratulations, " + GameUtil.formatNumber((long)skillManager2) + " xp was added to your " + SkillManager.SKILL_NAMES[player.getSelectedLampSkill()] + " skill.");
                    player24 = player;
                    player24.packetSender.sendSoundEffect(430, 1, 0);
                    player.setSelectedLampSkill(-1);
                    player24 = player;
                    player24.packetSender.sendConfig(261, 0);
                }
                return true;
            }
        }
        return false;
    }
}

