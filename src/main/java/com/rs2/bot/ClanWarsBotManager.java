package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.ClanWarsBotCombatTickTask;
import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.bot.combat.BotCombatLoadoutTables;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.objects.functions.DoorHandler;
import com.rs2.model.objects.functions.DoubleDoorHandler;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import com.rs2.util.path.WalkingCollisionMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ClanWarsBotManager {
    public static boolean clanWarsEventActive = false;
    public static boolean clanWarsRetreatActive = false;
    public static int clanWarsBaseCombatLevel = 35;
    public static int clanWarsSupplyMultiplier = 5;
    private static int clanWarsTeamOneCapeId = -1;
    private static int clanWarsTeamTwoCapeId = -1;
    public static String clanWarsTeamOneTag = "";
    public static String clanWarsTeamTwoTag = "";
    private static List clanWarsTeamTagPool = Arrays.asList("RoT", "LiT");
    private static RectangularArea[] clanWarsTeamSpawnAreas = new RectangularArea[]{new RectangularArea(3198, 3885, 3208, 3895), new RectangularArea(3302, 3891, 3312, 3901)};
    public static Position clanWarsRallyPosition = new Position(3255, 3893, 0);
    public static ArrayList<Player> clanWarsTeamOneBots = new ArrayList<Player>();
    public static ArrayList<Player> clanWarsTeamTwoBots = new ArrayList<Player>();

    private static void prepareClanWarsCombatant(Player player) {
        int value;
        int value2;
        int value3;
        int value4;
        Player player2;
        GameplayHelper.resetBotTaskState(player);
        if (ServerSettings.wildyBotsUseNewGeneration) {
            player2 = player;
            GameplayHelper.resetBotSkillsToBase(player2);
            value4 = ServerSettings.wildyBotsBaseCombatLevel - ServerSettings.wildyBotsCombatLevelSpread;
            value3 = value4 + GameUtil.randomInt((ServerSettings.wildyBotsCombatLevelSpread << 1) + 1);
            if (value3 < 3) {
                value3 = 3;
            }
            if (value3 > SkillManager.maxCombatLevel) {
                value3 = SkillManager.maxCombatLevel;
            }
            while (player2.getCombatLevel() < value3) {
                int[] integerValues = new int[6];
                integerValues[1] = 2;
                integerValues[2] = 1;
                integerValues[3] = 4;
                integerValues[4] = 6;
                integerValues[5] = 5;
                int[] integerValues2 = integerValues;
                value2 = integerValues[GameUtil.randomInt(6)];
                player2.getSkillManager();
                value = SkillManager.getLevelForExperience(player2.getSkillManager().getExperience()[value2]);
                BotCombatHelper.setBotSkillLevel(player2, value2, value + 1);
                player2.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player2);
                int[] skillManager = player2.getSkillManager().getCurrentLevels();
                player2.getSkillManager();
                skillManager[3] = SkillManager.getLevelForExperience(player2.getSkillManager().getExperience()[3]);
            }
            player2.getSkillManager().refreshAllSkills();
            BotCombatLoadoutManager.selectCombatStyleFromStats(player2, false);
        } else {
            player2 = player;
            GameplayHelper.resetBotSkillsToBase(player2);
            value4 = clanWarsBaseCombatLevel + GameUtil.randomInt(6);
            value3 = value4 / 5 << 1;
            if (value3 == 0) {
                value3 = 2;
            }
            BotCombatHelper.setBotSkillLevel(player2, 0, value4 - value4 / 5 + GameUtil.randomInt(value3));
            value3 = value4 / 5 << 1;
            if (value3 == 0) {
                value3 = 2;
            }
            BotCombatHelper.setBotSkillLevel(player2, 2, value4 - value4 / 5 + GameUtil.randomInt(value3));
            value3 = value4 / 5 << 1;
            if (value3 == 0) {
                value3 = 2;
            }
            BotCombatHelper.setBotSkillLevel(player2, 1, value4 - value4 / 5 + GameUtil.randomInt(value3));
            value3 = value4 / 5 << 1;
            if (value3 == 0) {
                value3 = 2;
            }
            BotCombatHelper.setBotSkillLevel(player2, 4, value4 - value4 / 5 + GameUtil.randomInt(value3));
            value3 = value4 / 5 << 1;
            if (value3 == 0) {
                value3 = 2;
            }
            BotCombatHelper.setBotSkillLevel(player2, 6, value4 - value4 / 5 + GameUtil.randomInt(value3));
            value3 = value4 / 5 << 1;
            if (value3 == 0) {
                value3 = 2;
            }
            BotCombatHelper.setBotSkillLevel(player2, 5, value4 - (value4 / 5 << 1) + GameUtil.randomInt(value3));
            player2.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player2);
            int[] skillManager2 = player2.getSkillManager().getCurrentLevels();
            player2.getSkillManager();
            skillManager2[3] = SkillManager.getLevelForExperience(player2.getSkillManager().getExperience()[3]);
            player2.getSkillManager().refreshAllSkills();
            BotCombatLoadoutManager.selectCombatStyleFromStats(player2, false);
        }
        player2 = player;
        BotCombatLoadoutManager.prepareCombatLoadout(player2, true);
        if (player2.clanWarsTeamId == 1) {
            player2.getEquipmentManager().getContainer().setItem(1, new ItemStack(clanWarsTeamOneCapeId));
        } else {
            player2.getEquipmentManager().getContainer().setItem(1, new ItemStack(clanWarsTeamTwoCapeId));
        }
        player2 = player;
        RectangularArea rectangularArea2 = clanWarsTeamOneBots.contains(player2) ? clanWarsTeamSpawnAreas[0] : clanWarsTeamSpawnAreas[1];
        int index = 0;
        value2 = 0;
        value = 1;
        while (value != 0) {
            RectangularArea rectangularArea = rectangularArea2;
            int minX = rectangularArea.getMinX();
            index = rectangularArea.getMinY();
            value2 = rectangularArea.getMaxX();
            int maxY = rectangularArea.getMaxY();
            value2 -= minX;
            maxY -= index;
            Position position = new Position(minX += GameUtil.randomInt(value2), maxY = index + GameUtil.randomInt(maxY));
            index = position.getX();
            value = WalkingCollisionMap.getTileFlags(index, value2 = position.getY(), 0) != 0 ? 1 : 0;
        }
        player.moveTo(new Position(index, value2, 0));
        Player player3 = player;
        World.getTaskScheduler().schedule(new ClanWarsBotCombatTickTask(3, player3));
    }

    public static boolean tryOpenNearbyDoor(Player player) {
        Position playerPosition = player.getPosition();
        int plane = playerPosition.getPlane();

        for (int radius = 1; radius <= 2; ++radius) {
            for (int offsetX = -radius; offsetX <= radius; ++offsetX) {
                for (int offsetY = -radius; offsetY <= radius; ++offsetY) {
                    if (Math.max(Math.abs(offsetX), Math.abs(offsetY)) != radius) {
                        continue;
                    }

                    int objectX = playerPosition.getX() + offsetX;
                    int objectY = playerPosition.getY() + offsetY;
                    LoadedWorldObject door = WorldObjectLookup.findObjectByNameAt("door", objectX, objectY, plane);
                    if (door == null) {
                        door = WorldObjectLookup.findObjectByNameAt("gate", objectX, objectY, plane);
                    }
                    if (door == null) {
                        continue;
                    }

                    int objectId = door.getWorldObject().getObjectId();
                    if (DoubleDoorHandler.handleDoubleDoor(objectId, objectX, objectY, plane)) {
                        player.packetSender.sendSoundEffect(318, 1, 0);
                        return true;
                    }
                    if (DoorHandler.handleDoor(player, objectId, objectX, objectY, plane)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void processClanWarsBotEvent() {
        boolean enabled;
        if (!clanWarsRetreatActive) {
            enabled = false;
            for (Player player : clanWarsTeamOneBots) {
                int distance = GameUtil.getDistance(player.getPosition(), clanWarsRallyPosition);
                if ((!player.isInWilderness() || player.botCombatEscapeActive) && distance >= 20) continue;
                enabled = true;
                break;
            }
            boolean enabled2 = false;
            for (Object value : clanWarsTeamTwoBots) {
                int distance2 = GameUtil.getDistance(((Entity)value).getPosition(), clanWarsRallyPosition);
                if ((!((Entity)value).isInWilderness() || ((Player)value).botCombatEscapeActive) && distance2 >= 20) continue;
                enabled2 = true;
                break;
            }
            if (!enabled || !enabled2) {
                if (enabled) {
                    for (Object value2 : clanWarsTeamOneBots) {
                        if (!((Entity)value2).isInWilderness()) continue;
                        ((Player)value2).queuePublicChatMessage("#" + clanWarsTeamOneTag);
                        BotCombatEscapeHandler.tryStartBotCombatEscape((Player)value2);
                    }
                } else {
                    for (Object value3 : clanWarsTeamTwoBots) {
                        if (!((Entity)value3).isInWilderness()) continue;
                        ((Player)value3).queuePublicChatMessage("#" + clanWarsTeamTwoTag);
                        BotCombatEscapeHandler.tryStartBotCombatEscape((Player)value3);
                    }
                }
                clanWarsRetreatActive = true;
            }
        }
        enabled = true;
        for (Player player : clanWarsTeamOneBots) {
            if (!player.isInWilderness()) continue;
            enabled = false;
            break;
        }
        if (enabled) {
            for (Player player : clanWarsTeamTwoBots) {
                if (!player.isInWilderness()) continue;
                enabled = false;
                break;
            }
        }
        if (enabled) {
            clanWarsEventActive = false;
            clanWarsRetreatActive = false;
        }
    }

    public static void hideClanWarsBot(Player player) {
        player.moveTo(new Position(9999, 9999, 0));
    }

    public static Player findClanWarsOpponent(Player player) {
        Player player2;
        ArrayList<Player> arrayList = new ArrayList<Player>();
        int value = clanWarsTeamOneBots.contains(player) ? clanWarsTeamTwoCapeId : clanWarsTeamOneCapeId;
        Player[] playerArray = World.players;
        int length = World.players.length;
        int index = 0;
        while (index < length) {
            player2 = playerArray[index];
            if (player2 != null && player2 != player && player2.getPosition().isWithinViewport(player.getPosition())) {
                int value2;
                if (PathReachability.isReachable(player, player2.getPosition().getX(), player2.getPosition().getY(), true, 0, 0) && (value2 = player2.getEquipmentManager().getItemIdAtSlot(1)) == value) {
                    arrayList.add(player2);
                }
            }
            ++index;
        }
        if (arrayList.size() > 0) {
            Collections.shuffle(arrayList);
            player2 = (Player)arrayList.get(0);
            return player2;
        }
        return null;
    }

    public static void startClanWarsCombatants() {
        for (Player player : clanWarsTeamOneBots) {
            ClanWarsBotManager.prepareClanWarsCombatant(player);
        }
        for (Player player : clanWarsTeamTwoBots) {
            ClanWarsBotManager.prepareClanWarsCombatant(player);
        }
    }

    public static void chooseClanWarsTeamCapes() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (!BotCombatHelper.isFreeToPlayWorld() && ItemDefinition.isDefined(4413)) {
            int[] integerValues = BotCombatLoadoutTables.castleWarsCapeIds;
            int index = 0;
            while (index < 55) {
                int value = integerValues[index];
                arrayList.add(value);
                ++index;
            }
        } else {
            int[] integerValues2 = BotCombatLoadoutTables.capeIds;
            int index2 = 0;
            while (index2 < 7) {
                int value2 = integerValues2[index2];
                arrayList.add(value2);
                ++index2;
            }
        }
        Collections.shuffle(arrayList);
        clanWarsTeamOneCapeId = (Integer)arrayList.get(0);
        clanWarsTeamTwoCapeId = (Integer)arrayList.get(1);
    }

    public static void chooseClanWarsTeamTags() {
        Collections.shuffle(clanWarsTeamTagPool);
        clanWarsTeamOneTag = (String)clanWarsTeamTagPool.get(0);
        clanWarsTeamTwoTag = (String)clanWarsTeamTagPool.get(1);
    }
}

