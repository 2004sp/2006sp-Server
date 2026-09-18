package com.rs2.util;

import com.rs2.Server;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.WeightedChanceEntry;
import com.rs2.util.WeightedChanceEntryThresholdComparator;
import com.rs2.util.path.ProjectileCollisionMap;
import com.rs2.util.path.WalkingCollisionMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public final class GameUtil {
    private static Random random;

    static {
        char[] characterValues = new char[]{' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\u00a3', '$', '%', '\"', '[', ']'};
        random = new Random();
    }

    public static int getRegionId(int value3, int value22) {
        value3 >>= 6;
        value3 = (value22 >>= 6) + (value3 << 8);
        return value3;
    }

    public static Position getRegionBasePosition(int value3) {
        int value2 = value3 >> 8 << 6;
        value3 = (value3 & 0xFF) << 6;
        return new Position(value2, value3);
    }

    public static int bitFlag(int value2) {
        value2 = (int)Math.pow(2.0, value2);
        return value2;
    }

    public static int randomBetweenInclusive(int value3, int value22) {
        value22 -= value3;
        value22 = GameUtil.randomInt(value22 + 1);
        return value3 + value22;
    }

    public static int clampRunWeightForEnergyDrain(int value4, int value22, int value32) {
        if (value32 < 0) {
            return 0;
        }
        if (value32 > 64) {
            return 64;
        }
        return value32;
    }

    public static int rollLevelScaledChanceIndex(int[] index, int[] value2, int[] integerValues32, int index2) {
        ArrayList entries = new ArrayList();
        ArrayList sortedEntries = new ArrayList();
        int index3 = 0;
        while (index3 < index.length) {
            WeightedChanceEntry entry = new WeightedChanceEntry(index[index3], value2[index3], integerValues32[index3]);
            sortedEntries.add(entry);
            entries.add(entry);
            ++index3;
        }
        Collections.sort(sortedEntries, new WeightedChanceEntryThresholdComparator());
        double[] sortedProbabilities = GameUtil.calculateLevelScaledProbabilities(sortedEntries, index2);
        double[] probabilities = new double[sortedProbabilities.length];
        int index4 = 0;
        while (index4 < sortedProbabilities.length) {
            probabilities[entries.indexOf(sortedEntries.get(index4))] = sortedProbabilities[index4];
            ++index4;
        }
        index4 = GameUtil.rollProbabilityIndex(probabilities);
        return index4;
    }

    public static boolean rollLevelScaledChance(int level, int value22, int value32) {
        return GameUtil.rollLevelScaledChance(level, value22, value32, 1.0);
    }

    public static boolean rollLevelScaledChance(int level, int value22, int value32, double level2) {
        double value;
        double value4 = level;
        double value5 = value22;
        level = (int)(value4 *= level2);
        value22 = (int)(value5 *= level2);
        double levelScaledChance = GameUtil.calculateLevelScaledChance(level, value22, value32);
        return levelScaledChance >= (value = Math.random());
    }

    public static boolean rollChance(double value3) {
        double value2 = Math.random();
        return value3 >= value2;
    }

    private static double calculateLevelScaledChance(int level, int value22, int value32) {
        double value = Math.floor((double)(level * (99 - value32)) / 98.0) + Math.floor((double)(value22 * (value32 - 1)) / 98.0) + 1.0;
        return Math.min(Math.max(value / 256.0, 0.0), 1.0);
    }

    private static double[] calculateLevelScaledProbabilities(ArrayList arrayList, int level) {
        double[] probabilities = new double[arrayList.size()];
        int index = 0;
        while (index < arrayList.size()) {
            WeightedChanceEntry entry = (WeightedChanceEntry)arrayList.get(index);
            if (level < entry.requiredLevel) {
                probabilities[index] = 0.0;
            } else {
                double probability = 1.0;
                int index2 = 0;
                while (index2 < arrayList.size()) {
                    WeightedChanceEntry weightedChanceEntry = (WeightedChanceEntry)arrayList.get(index2);
                    if (index2 == index) {
                        probability *= GameUtil.calculateLevelScaledChance(weightedChanceEntry.lowChance, weightedChanceEntry.highChance, level);
                        probabilities[index] = probability;
                        break;
                    }
                    if (level >= weightedChanceEntry.requiredLevel) {
                        probability *= 1.0 - GameUtil.calculateLevelScaledChance(weightedChanceEntry.lowChance, weightedChanceEntry.highChance, level);
                    }
                    ++index2;
                }
                if (index2 >= arrayList.size()) {
                    throw new IllegalStateException("Index out of bounds");
                }
            }
            ++index;
        }
        return probabilities;
    }

    public static int rollProbabilityIndex(double[] index) {
        double value;
        double value2 = value = Math.random();
        double value3 = 0.0;
        int index2 = 0;
        while (index2 < index.length) {
            double value4;
            value3 += index[index2];
            if (value3 >= value2) {
                return index2;
            }
            ++index2;
        }
        return -1;
    }

    public static int rollFractionWeightIndex(String[] index) {
        int[] integerValues = new int[index.length];
        int[] integerValues2 = new int[index.length];
        int index2 = 0;
        while (index2 < index.length) {
            String[] stringValues = index[index2].split("/");
            integerValues[index2] = Integer.parseInt(stringValues[0]);
            integerValues2[index2] = Integer.parseInt(stringValues[1]);
            ++index2;
        }
        double value = 0.0;
        int index3 = 0;
        while (index3 < integerValues.length) {
            double value2 = integerValues[index3];
            double value3 = integerValues2[index3];
            double value4 = value2 / value3;
            value += value4;
            ++index3;
        }
        double value5 = Math.random();
        double value6 = value5 * value;
        double value7 = 0.0;
        int index4 = 0;
        while (index4 < integerValues.length) {
            double value8;
            double value9 = integerValues[index4];
            double value10 = integerValues2[index4];
            double value11 = value9 / value10;
            value7 += value11;
            if (value7 >= value6) {
                return index4;
            }
            ++index4;
        }
        return -1;
    }

    public static void addTrackedRareItemAmount(ItemStack itemStack) {
        int index = 0;
        for (Object trackedRareItemObject : Server.trackedRareItems) {
            ItemStack itemStack2 = (ItemStack)trackedRareItemObject;
            if (itemStack.getId() == itemStack2.getId()) {
                int amount = itemStack2.getAmount() + itemStack.getAmount();
                itemStack.setAmount(amount);
                Server.trackedRareItems.set(index, itemStack);
                return;
            }
            ++index;
        }
    }

    public static String formatNumber(long value2) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);
        return numberFormat.format(value2);
    }

    public static String formatCompactAmountHighThreshold(int amount) {
        int value = Math.abs(amount);
        String text = "";
        if (value >= 100000 && value < 10000000) {
            text = "K";
            amount /= 1000;
        }
        if (value >= 10000000) {
            text = "M";
            amount /= 1000000;
        }
        return String.valueOf(amount) + text;
    }

    public static String formatCompactAmount(int amount) {
        int value = Math.abs(amount);
        String text = "";
        if (value >= 1000 && value < 10000000) {
            text = "K";
            amount /= 1000;
        }
        if (value >= 10000000) {
            text = "M";
            amount /= 1000000;
        }
        return String.valueOf(amount) + text;
    }

    public static String formatCompactAmountDetailed(int amount) {
        int value = Math.abs(amount);
        String text = "";
        int initialValue = 1;
        if (value >= 1000 && value < 1000000) {
            text = "K";
            amount /= 1000;
            initialValue = 1000;
        }
        if (value >= 1000000) {
            text = "M";
            amount /= 1000000;
            initialValue = 1000000;
        }
        String text2 = "";
        if ((value -= amount * initialValue) != 0) {
            text2 = String.valueOf(value);
        }
        if (!text2.equals("")) {
            String text3 = String.valueOf(amount) + "," + value;
            text3 = text3.indexOf(",") < 0 ? text3 : text3.replaceAll("0*$", "").replaceAll("\\,$", "");
            return String.valueOf(text3) + text;
        }
        return String.valueOf(amount) + text;
    }

    public static String capitalizeLowercaseFirst(String text2) {
        if ((text2 = text2.toLowerCase()).length() <= 1) {
            return text2.toUpperCase();
        }
        text2 = String.valueOf(text2.substring(0, 1).toUpperCase()) + text2.substring(1);
        return text2;
    }

    public static String capitalizeWords(String text2) {
        int index = 0;
        while (index < text2.length()) {
            if (index == 0) {
                text2 = String.format("%s%s", Character.valueOf(Character.toUpperCase(text2.charAt(0))), text2.substring(1));
            }
            if (!Character.isLetterOrDigit(text2.charAt(index)) && index + 1 < text2.length()) {
                text2 = String.format("%s%s%s", text2.subSequence(0, index + 1), Character.valueOf(Character.toUpperCase(text2.charAt(index + 1))), text2.substring(index + 2));
            }
            ++index;
        }
        return text2;
    }

    public static String formatDisplayName(String text2) {
        text2 = GameUtil.capitalizeWords(text2);
        text2.replace("_", " ");
        return text2;
    }

    public static int randomExclusive(int value2) {
        value2 = (int)(Math.random() * (double)value2);
        if (value2 < 0) {
            return 0;
        }
        return value2;
    }

    public static int randomInclusive(int value2) {
        value2 = (int)(Math.random() * (double)(value2 + 1));
        if (value2 < 0) {
            return 0;
        }
        return value2;
    }

    public static int randomInt(int value2) {
        value2 = random.nextInt(value2);
        return value2;
    }

    public static int randomOneToInclusive(int value3, int value22) {
        value3 = value22 - 1;
        value3 = 1 + GameUtil.randomInt(value3 + 1);
        return value3;
    }

    public static int rollPriceFluctuationPercent(int value3) {
        value3 = random.nextInt(66);
        int index = 0;
        int value2 = 11;
        while (value3 >= 0) {
            value3 -= value2;
            ++index;
            --value2;
        }
        return index - 1;
    }

    public static int[] shuffleIntArray(int[] integerValues2) {
        Random random = new Random();
        int value = integerValues2.length - 1;
        while (value > 0) {
            int value2 = random.nextInt(value + 1);
            int value3 = integerValues2[value2];
            integerValues2[value2] = integerValues2[value];
            integerValues2[value] = value3;
            --value;
        }
        return integerValues2;
    }

    public static String formatNumber(int value2) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);
        return numberFormat.format(value2);
    }

    public static boolean hasClearPath(Position position, Position position2, boolean enabled2) {
        return GameUtil.hasClearPath(position.getX(), position.getY(), position2.getX(), position2.getY(), position.getPlane(), enabled2);
    }

    public static boolean isNpcLastStepFacingPlayer(Player player, Npc npc) {
        Position position;
        if (player == null) {
            return false;
        }
        if (npc == null) {
            return false;
        }
        Position position2 = player.getPosition();
        if (position2.isWithinDistance(position = npc.getPosition(), 6) && (player.getPosition().getX() > npc.getPosition().getX() && npc.getLastStepFacingDirection() == 4 || player.getPosition().getX() <= npc.getPosition().getX() && npc.getLastStepFacingDirection() == 5 || player.getPosition().getY() > npc.getPosition().getY() && npc.getLastStepFacingDirection() == 2 || player.getPosition().getY() <= npc.getPosition().getY() && npc.getLastStepFacingDirection() == 0)) {
            return GameUtil.hasClearPath(position2.getX(), position2.getY(), position.getX(), position.getY(), position2.getPlane(), false);
        }
        return false;
    }

    public static boolean isNpcWaypointFacingPlayer(Player player, Npc npc) {
        Position position;
        Position position2 = player.getPosition();
        if (position2.isWithinDistance(position = npc.getPosition(), 6) && (player.getPosition().getX() > npc.getPosition().getX() && npc.getWaypointFacingDirection() == 4 || player.getPosition().getX() <= npc.getPosition().getX() && npc.getWaypointFacingDirection() == 5 || player.getPosition().getY() > npc.getPosition().getY() && npc.getWaypointFacingDirection() == 2 || player.getPosition().getY() <= npc.getPosition().getY() && npc.getWaypointFacingDirection() == 0)) {
            return GameUtil.hasClearPath(position2.getX(), position2.getY(), position.getX(), position.getY(), position2.getPlane(), false);
        }
        return false;
    }

    private static boolean hasClearPath(int value10, int value22, int value32, int value42, int value52, boolean enabled2) {
        if (enabled2) {
            return WalkingCollisionMap.canTravelBetween(value10, value22, value32, value42, value52, 1, 1);
        }
        int value6 = value10;
        value10 = value32 - value6;
        value22 = value42 - value22;
        int value7 = Math.max(Math.abs(value10), Math.abs(value22));
        int index = 0;
        while (index < value7) {
            int value8 = value32 - value10;
            int value9 = value42 - value22;
            int index2 = 0;
            while (index2 <= 0) {
                int index3 = 0;
                while (index3 <= 0) {
                    if (value10 < 0 && value22 < 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2 - 1, value9 + index3 - 1, value52) & 0x128010E) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2 - 1, value9 + index3, value52) & 0x1280108) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2, value9 + index3 - 1, value52) & 0x1280102) != 0 : (value10 > 0 && value22 > 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2 + 1, value9 + index3 + 1, value52) & 0x12801E0) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2 + 1, value9 + index3, value52) & 0x1280180) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2, value9 + index3 + 1, value52) & 0x1280120) != 0 : (value10 < 0 && value22 > 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2 - 1, value9 + index3 + 1, value52) & 0x1280138) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2 - 1, value9 + index3, value52) & 0x1280108) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2, value9 + index3 + 1, value52) & 0x1280120) != 0 : (value10 > 0 && value22 < 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2 + 1, value9 + index3 - 1, value52) & 0x1280183) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2 + 1, value9 + index3, value52) & 0x1280180) != 0 || (ProjectileCollisionMap.getTileFlags(value8 + index2, value9 + index3 - 1, value52) & 0x1280102) != 0 : (value10 > 0 && value22 == 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2 + 1, value9 + index3, value52) & 0x1280180) != 0 : (value10 < 0 && value22 == 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2 - 1, value9 + index3, value52) & 0x1280108) != 0 : (value10 == 0 && value22 > 0 ? (ProjectileCollisionMap.getTileFlags(value8 + index2, value9 + index3 + 1, value52) & 0x1280120) != 0 : value10 == 0 && value22 < 0 && (ProjectileCollisionMap.getTileFlags(value8 + index2, value9 + index3 - 1, value52) & 0x1280102) != 0))))))) {
                        return false;
                    }
                    ++index3;
                }
                ++index2;
            }
            if (value10 < 0) {
                ++value10;
            } else if (value10 > 0) {
                --value10;
            }
            if (value22 < 0) {
                ++value22;
            } else if (value22 > 0) {
                --value22;
            }
            ++index;
        }
        return true;
    }

    public static final boolean isWithinDistance(Position position, Position position2, int distance) {
        if (position == null || position2 == null) {
            return false;
        }
        return GameUtil.isWithinDistance(position.getX(), position.getY(), position2.getX(), position2.getY(), distance) && position.getPlane() == position2.getPlane();
    }

    public static final boolean isWithinDistance(int distance, int value22, int value32, int value42, int value52) {
        distance -= value32;
        return (distance = (int)Math.sqrt(Math.pow(distance, 2.0) + Math.pow(value22 -= value42, 2.0))) <= value52;
    }

    public static Position findReachableInteractionPosition(int value8, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (value52 <= 0 && value62 <= 0) {
            if (value32 == value8 && value42 == value22) {
                return new Position(value8, value22, value72);
            }
            return null;
        }
        if (value52 == 1 && value62 == 1) {
            if (GameUtil.isWithinDistance(value32, value42, value8, value22, 1)) {
                return new Position(value8, value22, value72);
            }
            return null;
        }
        value52 = value8 + value52 - 1;
        value62 = value22 + value62 - 1;
        Position position = new Position(value32, value42, value72);
        while (value8 <= value52) {
            value42 = value22;
            while (value42 <= value62) {
                Position position2 = new Position(value8, value42, value72);
                if (GameUtil.isWithinDistance(position2, position, 1) && position2.isOrthogonallyAlignedWith(position)) {
                    return position2;
                }
                ++value42;
            }
            ++value8;
        }
        return null;
    }

    public static Position getDelta(Position position, Position position2) {
        return new Position(position2.getX() - position.getX(), position2.getY() - position.getY());
    }

    public static int getDistance(Position position, Position position2) {
        int x = position2.getX() - position.getX();
        int y = position2.getY() - position.getY();
        return (int)Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
    }

    public static int getDirectionForDelta(int direction, int value22) {
        if (direction < 0) {
            if (value22 < 0) {
                return 5;
            }
            if (value22 > 0) {
                return 0;
            }
            return 3;
        }
        if (direction > 0) {
            if (value22 < 0) {
                return 7;
            }
            if (value22 > 0) {
                return 2;
            }
            return 4;
        }
        if (value22 < 0) {
            return 6;
        }
        if (value22 > 0) {
            return 1;
        }
        return -1;
    }

    public static String formatNumber(double value2) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);
        return numberFormat.format(value2);
    }

    public static Random getRandom() {
        return random;
    }

    public static String getOrdinalWord(int value2) {
        if (value2 == 1) {
            return "first";
        }
        if (value2 == 2) {
            return "second";
        }
        if (value2 == 3) {
            return "third";
        }
        if (value2 == 4) {
            return "fourth";
        }
        return "first";
    }

    public static int getDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        int value = calendar.get(1);
        int value2 = calendar.get(2);
        int[] integerValues = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (value % 4 == 0 && value % 100 != 0 || value % 400 == 0) {
            integerValues[1] = 29;
        }
        int value3 = 0 + calendar.get(5);
        value = 0;
        while (value < 12) {
            if (value < value2) {
                value3 += integerValues[value];
            }
            ++value;
        }
        return value3;
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1);
    }

    public static long secondsToTicks(long ticks) {
        return (long)Math.ceil((double)ticks * 1000.0 / 600.0);
    }

    public static int minutesToMillis(int value2) {
        return value2 * 60 * 1000;
    }
}

