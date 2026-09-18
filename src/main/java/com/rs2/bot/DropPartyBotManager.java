package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.bot.DropPartyCompletionTask;
import com.rs2.bot.DropPartyFollowerTickTask;
import com.rs2.bot.DropPartyGroundItemPickupTask;
import com.rs2.bot.DropPartyLeaderCleanupTask;
import com.rs2.bot.DropPartyLeaderTickTask;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class DropPartyBotManager {
    private static int leaderDropItemCount = 10;
    public static boolean dropPartyActive = false;
    public static int dropPartyChanceDivisor = 48;
    private static int valuableDropMinValue = 500;
    public static ArrayList pendingDropPartyGroundItems = new ArrayList();
    public static ArrayList dropPartyParticipants = new ArrayList();
    public static int targetDropPartySize = 0;
    public static int baseDropPartySize = 4;
    public static int dropPartyPretaskLoopLimit = 2;
    private static ArrayList dropPartyRewardPool = new ArrayList();
    private static ArrayList valuableDropPartyRewardPool = new ArrayList();

    public static void notifyDropPartyRewardDropped(GroundItem groundItem) {
        int initialValue = 1;
        while (initialValue < dropPartyParticipants.size()) {
            Object player = (Player)dropPartyParticipants.get(initialValue);
            int value = 2 + GameUtil.randomInt(3);
            int distance = GameUtil.getDistance(((Entity)player).getPosition(), groundItem.getPosition());
            int value2 = 1 + GameUtil.randomInt(5);
            int guidePrice = GrandExchangeManager.getGuidePrice(groundItem.getItem().getId());
            if (guidePrice >= 500) {
                value2 = 20;
            }
            if (distance == 0 && GameUtil.randomInt(3) == 0) {
                BotCombatHelper.pickupVisibleGroundItem((Player)player, groundItem.getItem().getId(), groundItem.getPosition());
            } else if (distance < value2) {
                World.getTaskScheduler().schedule(new DropPartyGroundItemPickupTask(value, (Player)player, groundItem));
            }
            ++initialValue;
        }
    }

    public static void finishLeaderDrops(Player player) {
        ((Player)player).queuePublicChatMessage("All dropped, good luck to all.");
        ((Player)player).botTaskState = "wait for new task";
        World.getTaskScheduler().schedule(new DropPartyLeaderCleanupTask(10, (Player)player));
    }

    public static void finishDropPartyParticipant(Player player) {
        ((Player)player).botTaskState = "wait for new task";
        World.getTaskScheduler().schedule(new DropPartyCompletionTask(10, (Player)player));
    }

    public static void startDropPartyTick(Player player) {
        player.botPublicChatMessage = "Follow for Drop party!";
        player.botPublicChatColor = GameUtil.randomInt(12);
        int[] integerValues = new int[3];
        integerValues[1] = 1;
        integerValues[2] = 3;
        int[] integerValues2 = integerValues;
        int value = GameUtil.randomInt(3);
        player.botPublicChatEffect = integerValues2[value];
        if (player.dropPartyLeader) {
            World.getTaskScheduler().schedule(new DropPartyLeaderTickTask(3, player));
            return;
        }
        Player player2 = (Player)dropPartyParticipants.get(0);
        World.getTaskScheduler().schedule(new DropPartyFollowerTickTask(3, player, player2));
    }

    public static void prepareDropPartyCombatLoadout(Player player) {
        int value;
        GameplayHelper.resetBotSkillsToBase(player);
        int value2 = 80 + GameUtil.randomInt(15);
        if (player.dropPartyFollower) {
            value2 = 1 + GameUtil.randomInt(60);
        }
        if ((value = value2 / 5 << 1) == 0) {
            value = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, value2 - value2 / 5 + GameUtil.randomInt(value));
        value = value2 / 5 << 1;
        if (value == 0) {
            value = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, value2 - value2 / 5 + GameUtil.randomInt(value));
        value = value2 / 5 << 1;
        if (value == 0) {
            value = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, value2 - value2 / 5 + GameUtil.randomInt(value));
        value = value2 / 5 << 1;
        if (value == 0) {
            value = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, value2 - value2 / 5 + GameUtil.randomInt(value));
        value = value2 / 5 << 1;
        if (value == 0) {
            value = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, value2 - value2 / 5 + GameUtil.randomInt(value));
        value = value2 / 5 << 1;
        if (value == 0) {
            value = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, value2 - (value2 / 5 << 1) + GameUtil.randomInt(value));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
        BotCombatLoadoutManager.selectCombatStyleFromStats(player, true);
    }

    public static void initializeDropPartyRewardPools() {
        Object value = BotTradeAdvertManager.tradeAdvertOfferPool;
        Iterator iterator = ((ArrayList)value).iterator();
        while (iterator.hasNext()) {
            value = (GameplayHelper)iterator.next();
            if (value == null || ((GameplayHelper)value).getTradeAdvertItemId() >= 7955) continue;
            int guidePrice = GrandExchangeManager.getGuidePrice(((GameplayHelper)value).getTradeAdvertItemId());
            if (guidePrice >= valuableDropMinValue) {
                valuableDropPartyRewardPool.add(value);
            }
            dropPartyRewardPool.add(value);
        }
    }

    public static void prepareDropPartyInventory(Player player) {
        int value;
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.dropPartyLeader) {
            int value2;
            value = 0;
            ArrayList<GameplayHelper> arrayList = new ArrayList<GameplayHelper>();
            for (Object value3 : dropPartyRewardPool) {
                arrayList.add((GameplayHelper)value3);
            }
            ArrayList<GameplayHelper> arrayList2 = new ArrayList<GameplayHelper>();
            for (Object value4 : valuableDropPartyRewardPool) {
                arrayList2.add((GameplayHelper)value4);
            }
            ArrayList<GameplayHelper> arrayList3 = new ArrayList<GameplayHelper>();
            ArrayList<GameplayHelper> arrayList4 = arrayList;
            int index = 0;
            while (index < leaderDropItemCount) {
                int value5;
                if (index == leaderDropItemCount - 1 && value < valuableDropMinValue) {
                    arrayList4 = arrayList2;
                }
                int value6 = GameUtil.randomInt(arrayList4.size());
                GameplayHelper gameplayHelper2 = (GameplayHelper)arrayList4.get(value6);
                arrayList3.add(gameplayHelper2);
                arrayList.remove(gameplayHelper2);
                if (arrayList2.contains(gameplayHelper2)) {
                    arrayList2.remove(gameplayHelper2);
                }
                if ((value5 = GrandExchangeManager.getGuidePrice(gameplayHelper2.getTradeAdvertItemId())) > value) {
                    value = value5;
                    player.botAdvertItemId = gameplayHelper2.getTradeAdvertItemId();
                }
                ++index;
            }
            value = value2 = value;
            value2 = baseDropPartySize;
            if (value >= 10000 && value < 250000) {
                value2 = 6;
            }
            if (value >= 25000 && value < 100000) {
                value2 = 8;
            }
            if (value >= 100000 && value < 200000) {
                value2 = 10;
            }
            if (value >= 200000 && value < 400000) {
                value2 = 12;
            }
            if (value >= 400000 && value < 600000) {
                value2 = 14;
            }
            if (value >= 600000 && value < 800000) {
                value2 = 16;
            }
            if (value >= 800000 && value < 1000000) {
                value2 = 18;
            }
            if (value >= 1000000) {
                value2 = 20;
            }
            if (value2 > ServerSettings.otherBotCount) {
                value2 = ServerSettings.otherBotCount;
            }
            targetDropPartySize = value2;
            Collections.shuffle(arrayList3);
            Iterator<GameplayHelper> iterator = arrayList3.iterator();
            while (iterator.hasNext()) {
                GameplayHelper gameplayHelper = iterator.next();
                player.getInventoryManager().addItem(new ItemStack(gameplayHelper.getTradeAdvertItemId(), 1));
            }
            ItemDefinition itemDefinition = ItemDefinition.forId(player.botAdvertItemId);
            player.botPublicChatMessage = "Follow for Drop party! Best drop: " + itemDefinition.getDisplayName();
        }
        Player player2 = player;
        if (player2.botCombatStyle == 0) {
            BotCombatLoadoutManager.prepareMeleeLoadout(player2);
            BotCombatLoadoutManager.equipGlovesAndBoots(player2);
        } else if (player2.botCombatStyle == 2) {
            BotCombatLoadoutManager.prepareMagicLoadout(player2);
        } else if (player2.botCombatStyle == 1) {
            BotCombatLoadoutManager.prepareRangedLoadout(player2);
            int skillManager = player2.getSkillManager().getCurrentLevels()[4] >= 40 ? 1731 : (GameUtil.randomInt(3) == 0 ? 1478 : 1729);
            if (!BotCombatHelper.isFreeToPlayWorld() && player2.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
                skillManager = 1712;
            }
            player2.getEquipmentManager().getContainer().setItem(2, new ItemStack(skillManager));
        }
        BotCombatLoadoutManager.equipRandomCape(player2);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

}

