package com.rs2.model.clue;

import com.rs2.bot.route.BotWorldRouteChoice;
import com.rs2.cache.CacheArchiveEntry;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.cache.CacheFile;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.clue.CoordinateClueHandler;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.drop.NpcDropManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class TreasureTrailManager {
    public static final int[] DEFAULT_SLIDER_PUZZLE_PIECES = new int[]{3952, 3954, 3956, 3958, 3960, 3962, 3964, 3966, 3968, 3970, 3972, 3974, 3976, 3978, 3980, 3982, 3984, 3986, 3988, 3990, 3992, 3994, 3996, 3998, -1};
    public static final int[] SLIDER_PUZZLE_ONE_PIECES = new int[]{2749, 2750, 2751, 2752, 2753, 2754, 2755, 2756, 2757, 2758, 2759, 2760, 2761, 2762, 2763, 2764, 2765, 2766, 2767, 2768, 2769, 2770, 2771, 2772, -1};
    public static final int[] SLIDER_PUZZLE_TWO_PIECES = new int[]{3619, 3620, 3621, 3622, 3623, 3624, 3625, 3626, 3627, 3628, 3629, 3630, 3631, 3632, 3633, 3634, 3635, 3636, 3637, 3638, 3639, 3640, 3641, 3642, -1};
    public static final int[] SLIDER_PUZZLE_THREE_PIECES = new int[]{3643, 3644, 3645, 3646, 3647, 3648, 3649, 3650, 3651, 3652, 3653, 3654, 3655, 3656, 3657, 3658, 3659, 3660, 3661, 3662, 3663, 3664, 3665, 3666, -1};
    private static int[] tierOneCommonRewardItems = new int[]{554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 374, 380, 362, 1379, 1381, 1383, 1385, 1387, 1065, 1099, 1135, 1097, 1169, 841, 843, 845, 847, 849};
    private static int[] tierTwoCommonRewardItems = new int[]{1367, 1217, 1179, 1151, 1107, 1077, 1269, 1089, 1125, 1165, 1195, 1283, 1297, 1313, 1327, 1341, 1367, 1426, 334, 330, 851, 853, 855, 857, 859, 4821, 1765};
    private static int[] tierThreeCommonRewardItems = new int[]{1430, 1371, 1345, 1331, 1317, 1301, 1287, 1271, 1211, 1199, 1073, 1161, 1183, 1091, 1111, 1123, 1145, 1199, 1681, 4823};
    private static int[] tierFourCommonRewardItems = new int[]{1432, 1373, 1347, 1333, 1319, 1303, 1289, 1275, 1213, 1079, 1093, 1113, 1127, 1147, 1163, 1185, 1201, 4824, 386, 2491, 2497, 2503};
    private static int[] tierOneUniqueRewardItems = new int[]{2583, 2585, 2587, 2589, 2591, 2593, 2595, 2597, 3472, 3473, 2579, 2633, 2635, 2637, 2631, 7362, 7364, 7366, 7368, 7386, 7388, 7390, 7392, 7394, 7396, 7329, 7330, 7331, 7332, 7338, 7344, 7350, 7356, 3827, 3831, 3835, 3827, 3831, 3835, 3827, 3831, 3835};
    private static int[] tierTwoUniqueRewardItems;
    private static int[] tierThreeUniqueRewardItems;
    private static int[] tierFourUniqueRewardItems;

    static {
        tierTwoUniqueRewardItems = new int[]{7329, 7330, 7331, 7319, 7321, 7323, 7325, 7327, 7370, 7372, 7378, 7380, 2645, 2647, 2649, 2579, 2577, 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 7334, 7340, 7346, 7352, 7358, 3828, 3832, 3836, 3829, 3833, 3837, 3829, 3833, 3837, 3829, 3833, 3837};
        tierThreeUniqueRewardItems = new int[]{3480, 2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669, 2671, 2673, 2675, 2581, 2651, 7398, 7399, 7400, 7329, 7330, 7331, 7374, 7376, 7382, 7384, 2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 7336, 7342, 7348, 7354, 7360, 3830, 3834, 3838, 3830, 3834, 3838, 3830, 3834, 3838, 2639, 2640, 2643, 65000};
        tierFourUniqueRewardItems = new int[]{3481, 3483, 3485, 3486, 3488, 2437, 2441, 2443, 2445, 2453, 3017};
    }

    public static void filterRewardItemPools() {
        int[] integerValues;
        int[] integerValues2;
        int[] integerValues3;
        int[] integerValues4;
        int[] integerValues5;
        int[] integerValues6;
        int[] integerValues7;
        int[] integerValues8;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int[] integerValues9 = integerValues8 = (int[])tierOneUniqueRewardItems.clone();
        int length = integerValues8.length;
        int index = 0;
        while (index < length) {
            int value = integerValues9[index];
            if (ItemDefinition.isDefined(value)) {
                arrayList.add(value);
            }
            ++index;
        }
        tierOneUniqueRewardItems = new int[arrayList.size()];
        int index2 = 0;
        while (index2 < arrayList.size()) {
            TreasureTrailManager.tierOneUniqueRewardItems[index2] = (Integer)arrayList.get(index2);
            ++index2;
        }
        arrayList.clear();
        integerValues9 = integerValues7 = (int[])tierTwoUniqueRewardItems.clone();
        length = integerValues7.length;
        index = 0;
        while (index < length) {
            int value2 = integerValues9[index];
            if (ItemDefinition.isDefined(value2)) {
                arrayList.add(value2);
            }
            ++index;
        }
        tierTwoUniqueRewardItems = new int[arrayList.size()];
        int index3 = 0;
        while (index3 < arrayList.size()) {
            TreasureTrailManager.tierTwoUniqueRewardItems[index3] = (Integer)arrayList.get(index3);
            ++index3;
        }
        arrayList.clear();
        integerValues9 = integerValues6 = (int[])tierThreeUniqueRewardItems.clone();
        length = integerValues6.length;
        index = 0;
        while (index < length) {
            int value3 = integerValues9[index];
            if (ItemDefinition.isDefined(value3)) {
                arrayList.add(value3);
            }
            ++index;
        }
        tierThreeUniqueRewardItems = new int[arrayList.size()];
        int index4 = 0;
        while (index4 < arrayList.size()) {
            TreasureTrailManager.tierThreeUniqueRewardItems[index4] = (Integer)arrayList.get(index4);
            ++index4;
        }
        arrayList.clear();
        integerValues9 = integerValues5 = (int[])tierFourUniqueRewardItems.clone();
        length = integerValues5.length;
        index = 0;
        while (index < length) {
            int value4 = integerValues9[index];
            if (ItemDefinition.isDefined(value4)) {
                arrayList.add(value4);
            }
            ++index;
        }
        tierFourUniqueRewardItems = new int[arrayList.size()];
        int index5 = 0;
        while (index5 < arrayList.size()) {
            TreasureTrailManager.tierFourUniqueRewardItems[index5] = (Integer)arrayList.get(index5);
            ++index5;
        }
        arrayList.clear();
        integerValues9 = integerValues4 = (int[])tierOneCommonRewardItems.clone();
        length = integerValues4.length;
        index = 0;
        while (index < length) {
            int value5 = integerValues9[index];
            if (ItemDefinition.isDefined(value5)) {
                arrayList.add(value5);
            }
            ++index;
        }
        tierOneCommonRewardItems = new int[arrayList.size()];
        int index6 = 0;
        while (index6 < arrayList.size()) {
            TreasureTrailManager.tierOneCommonRewardItems[index6] = (Integer)arrayList.get(index6);
            ++index6;
        }
        arrayList.clear();
        integerValues9 = integerValues3 = (int[])tierTwoCommonRewardItems.clone();
        length = integerValues3.length;
        index = 0;
        while (index < length) {
            int value6 = integerValues9[index];
            if (ItemDefinition.isDefined(value6)) {
                arrayList.add(value6);
            }
            ++index;
        }
        tierTwoCommonRewardItems = new int[arrayList.size()];
        int index7 = 0;
        while (index7 < arrayList.size()) {
            TreasureTrailManager.tierTwoCommonRewardItems[index7] = (Integer)arrayList.get(index7);
            ++index7;
        }
        arrayList.clear();
        integerValues9 = integerValues2 = (int[])tierThreeCommonRewardItems.clone();
        length = integerValues2.length;
        index = 0;
        while (index < length) {
            int value7 = integerValues9[index];
            if (ItemDefinition.isDefined(value7)) {
                arrayList.add(value7);
            }
            ++index;
        }
        tierThreeCommonRewardItems = new int[arrayList.size()];
        int index8 = 0;
        while (index8 < arrayList.size()) {
            TreasureTrailManager.tierThreeCommonRewardItems[index8] = (Integer)arrayList.get(index8);
            ++index8;
        }
        arrayList.clear();
        integerValues9 = integerValues = (int[])tierFourCommonRewardItems.clone();
        length = integerValues.length;
        index = 0;
        while (index < length) {
            int value8 = integerValues9[index];
            if (ItemDefinition.isDefined(value8)) {
                arrayList.add(value8);
            }
            ++index;
        }
        tierFourCommonRewardItems = new int[arrayList.size()];
        int index9 = 0;
        while (index9 < arrayList.size()) {
            TreasureTrailManager.tierFourCommonRewardItems[index9] = (Integer)arrayList.get(index9);
            ++index9;
        }
        arrayList.clear();
    }

    public static void clearClueInterfaceText(Player player) {
        int value = 6968;
        while (value <= 6975) {
            Player player2 = player;
            player2.packetSender.sendInterfaceText("", value);
            ++value;
        }
    }

    public static void advanceOrCompleteTrail(Player player, int value2, String text3, boolean enabled2, String text22) {
        Npc npc = World.getNpcs()[player.getInteractionTargetIndex()];
        player.getDialogueManager().setDialogueNpcId(npc != null ? npc.getNpcId() : 0);
        player.sliderPuzzlePieces = new ItemStack[25];
        switch (value2) {
            case 1: {
                if (player.treasureTrailStepCount > 0 && GameUtil.randomInclusive(6) == 0 || player.treasureTrailStepCount >= 3) {
                    if (enabled2) {
                        player.getDialogueManager().setDialogueId(10009);
                        player.getDialogueManager().setNextDialogueStep(1);
                        player.getDialogueManager().showNpcOneLineDialogue(text22, 588);
                        return;
                    }
                    TreasureTrailManager.completeTreasureTrail(player, value2);
                    return;
                }
                player.getDialogueManager().showItemIdMessage(text3, 2701);
                player.getDialogueManager().finishDialogue();
                TreasureTrailManager.awardNextClueScroll(player, value2);
                return;
            }
            case 2: {
                if (player.treasureTrailStepCount >= 2 && GameUtil.randomInclusive(6) == 0 || player.treasureTrailStepCount >= 4) {
                    if (enabled2) {
                        player.getDialogueManager().setDialogueId(10009);
                        player.getDialogueManager().setNextDialogueStep(1);
                        player.getDialogueManager().showNpcOneLineDialogue(text22, 588);
                        return;
                    }
                    TreasureTrailManager.completeTreasureTrail(player, value2);
                    return;
                }
                player.getDialogueManager().showItemIdMessage(text3, 2701);
                player.getDialogueManager().finishDialogue();
                TreasureTrailManager.awardNextClueScroll(player, value2);
                return;
            }
            case 3: {
                if (player.treasureTrailStepCount >= 3 && GameUtil.randomInclusive(6) == 0 || player.treasureTrailStepCount >= 5) {
                    if (enabled2) {
                        player.getDialogueManager().setDialogueId(10009);
                        player.getDialogueManager().setNextDialogueStep(1);
                        player.getDialogueManager().showNpcOneLineDialogue(text22, 588);
                        return;
                    }
                    TreasureTrailManager.completeTreasureTrail(player, value2);
                    return;
                }
                player.getDialogueManager().showItemIdMessage(text3, 2701);
                player.getDialogueManager().finishDialogue();
                TreasureTrailManager.awardNextClueScroll(player, value2);
            }
        }
    }

    private static void awardNextClueScroll(Player player, int value2) {
        ++player.treasureTrailStepCount;
        int initialValue = 1;
        if (player.clueRequiredItems != null) {
            if (!player.getInventoryManager().containsItemStack(player.clueRequiredItems[0])) {
                initialValue = 0;
            }
            if (initialValue != 0) {
                initialValue = 0;
                while (initialValue < player.clueRequiredItems.length) {
                    player.getInventoryManager().removeItem(player.clueRequiredItems[initialValue]);
                    ++initialValue;
                }
                player.clueRequiredItems = null;
            } else {
                return;
            }
        }
        player.getInventoryManager().addOrDropItem(new ItemStack(TreasureTrailManager.randomClueItemForLevel(value2), 1));
        String text = player.treasureTrailStepCount > 1 ? "steps" : "step";
        Player player2 = player;
        player2.packetSender.sendGameMessage("You have completed " + player.treasureTrailStepCount + " " + text + " so far.");
    }

    public static void completeTreasureTrail(Player player, int value2) {
        ++player.treasureTrailStepCount;
        int initialValue = 1;
        if (player.clueRequiredItems != null) {
            if (!player.getInventoryManager().containsItemStack(player.clueRequiredItems[0])) {
                initialValue = 0;
            }
            if (initialValue != 0) {
                initialValue = 0;
                while (initialValue < player.clueRequiredItems.length) {
                    player.getInventoryManager().removeItem(player.clueRequiredItems[initialValue]);
                    ++initialValue;
                }
                player.clueRequiredItems = null;
            } else {
                return;
            }
        }
        ItemStack[] itemStackArray = null;
        switch (value2) {
            case 1: {
                itemStackArray = TreasureTrailManager.rollRewardItems(player, 1);
                ++player.easyCluesCompleted;
                break;
            }
            case 2: {
                itemStackArray = TreasureTrailManager.rollRewardItems(player, 2);
                ++player.mediumCluesCompleted;
                break;
            }
            case 3: {
                itemStackArray = TreasureTrailManager.rollRewardItems(player, 3);
                ++player.hardCluesCompleted;
            }
        }
        int[] rewardItemIds = new int[itemStackArray.length];
        int[] integerValues = new int[itemStackArray.length];
        int index = 0;
        int index2 = 0;
        while (index2 < itemStackArray.length) {
            rewardItemIds[index2] = itemStackArray[index2].getId();
            integerValues[index2] = itemStackArray[index2].getAmount();
            player.getInventoryManager().addOrDropItem(new ItemStack(rewardItemIds[index2], integerValues[index2]));
            int guidePrice = GrandExchangeManager.getGuidePrice(rewardItemIds[index2]);
            index += guidePrice * integerValues[index2];
            ++index2;
        }
        Player player2 = player;
        player2.packetSender.sendGameMessage("Well done, you've completed the Treasure Trail! (" + player.treasureTrailStepCount + " " + (player.treasureTrailStepCount < 2 ? "step" : "steps") + ")");
        Player player3 = player;
        player3.packetSender.sendGameMessage("Your treasure is worth around " + GameUtil.formatNumber(index) + " coins!");
        player.treasureTrailStepCount = 0;
        player.sliderPuzzlePieces = new ItemStack[25];
        Player player4 = player;
        player4.packetSender.sendItemContainer(6963, itemStackArray);
        Player player5 = player;
        player5.packetSender.showInterface(6960);
        Player player6 = player;
        player6.packetSender.sendMusicJingle(237, 256);
    }

    private static ItemStack[] rollRewardItems(Player player, int itemId) {
        int value;
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        int index = 0;
        if (itemId == 1) {
            index = 2;
        } else if (itemId == 2) {
            index = 3;
        } else if (itemId == 3) {
            index = 4;
        }
        index += GameUtil.randomInt(3);
        int index2 = 0;
        while (index2 < index) {
            value = itemId;
            ItemStack[] itemStackArray3 = null;
            if (value == 1) {
                itemStackArray3 = NpcDropManager.rollDrops((Entity)player, 6420, false);
            } else if (value == 2) {
                itemStackArray3 = NpcDropManager.rollDrops((Entity)player, 6421, false);
            } else if (value == 3) {
                itemStackArray3 = NpcDropManager.rollDrops((Entity)player, 6422, false);
            }
            if (itemStackArray3 != null) {
                value = 0;
                while (value < itemStackArray3.length) {
                    arrayList.add(itemStackArray3[value]);
                    ++value;
                }
            }
            ++index2;
        }
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        int index3 = 0;
        while (index3 < arrayList.size()) {
            value = ((ItemStack)arrayList.get(index3)).getId();
            int amount = ((ItemStack)arrayList.get(index3)).getAmount();
            if (!hashMap.containsKey(value)) {
                hashMap.put(value, amount);
            } else {
                int integer = (Integer)hashMap.get(value);
                hashMap.put(value, integer + amount);
            }
            ++index3;
        }
        ArrayList<ItemStack> arrayList2 = new ArrayList<ItemStack>();
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            int key = (Integer)entry.getKey();
            int value2 = (Integer)entry.getValue();
            arrayList2.add(new ItemStack(key, value2));
        }
        ArrayList<ItemStack> arrayList3 = new ArrayList<ItemStack>();
        int index4 = 0;
        while (index4 < arrayList2.size()) {
            int id = ((ItemStack)arrayList2.get(index4)).getId();
            int amount2 = ((ItemStack)arrayList2.get(index4)).getAmount();
            ItemDefinition itemDefinition = ((ItemStack)arrayList2.get(index4)).getDefinition();
            if (itemDefinition.isStackable() || amount2 == 1) {
                arrayList3.add((ItemStack)arrayList2.get(index4));
            } else {
                int index5 = 0;
                while (index5 < amount2) {
                    arrayList3.add(new ItemStack(id, 1));
                    ++index5;
                }
            }
            ++index4;
        }
        ItemStack[] itemStackArray = new ItemStack[arrayList3.size()];
        index4 = 0;
        while (index4 < arrayList3.size()) {
            itemStackArray[index4] = (ItemStack)arrayList3.get(index4);
            ++index4;
        }
        return itemStackArray;
    }

    public static int randomClueItemForLevel(int itemId) {
        ArrayList<Integer> candidateItems = new ArrayList<Integer>();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        switch (itemId) {
            case 1: {
                candidateItems.add(CacheArchiveEntry.randomMapClueItemForLevel(1));
                candidateItems.add(CacheFile.randomSearchClueItemForLevel(1));
                candidateItems.add(CacheDefinitionIndex.randomNpcClueItemForLevel(1));
                candidateItems.add(CacheArchiveEntry.randomMapClueItemForLevel(1));
                candidateItems.add(CacheFile.randomSearchClueItemForLevel(1));
                candidateItems.add(CacheDefinitionIndex.randomNpcClueItemForLevel(1));
                candidateItems.add(BotWorldRouteChoice.randomCrypticDigClueItemForLevel(1));
                break;
            }
            case 2: {
                candidateItems.add(GameplayHelper.randomAnagramClueItemForLevel(2));
                candidateItems.add(CacheArchiveEntry.randomMapClueItemForLevel(2));
                candidateItems.add(CacheFile.randomSearchClueItemForLevel(2));
                candidateItems.add(CacheDefinitionIndex.randomNpcClueItemForLevel(2));
                candidateItems.add(CoordinateClueHandler.randomClueItemForLevel(2));
                break;
            }
            case 3: {
                candidateItems.add(GameplayHelper.randomAnagramClueItemForLevel(3));
                candidateItems.add(CacheArchiveEntry.randomMapClueItemForLevel(3));
                candidateItems.add(CacheFile.randomSearchClueItemForLevel(3));
                candidateItems.add(CacheDefinitionIndex.randomNpcClueItemForLevel(3));
                candidateItems.add(BotWorldRouteChoice.randomCrypticDigClueItemForLevel(3));
                candidateItems.add(CoordinateClueHandler.randomClueItemForLevel(3));
                break;
            }
            default: {
                return -1;
            }
        }
        for (Integer clueItemId : candidateItems) {
            itemId = clueItemId;
            if (!ItemDefinition.isDefined(itemId)) continue;
            arrayList.add(itemId);
        }
        if (arrayList.isEmpty()) {
            return -1;
        }
        return (Integer)arrayList.get(GameUtil.randomInclusive(arrayList.size() - 1));
    }

    public static boolean handleRewardContainerItem(Player player, int itemId) {
        switch (itemId) {
            case 2717: {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                TreasureTrailManager.completeTreasureTrail(player, 1);
                return true;
            }
            case 2714: {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                TreasureTrailManager.completeTreasureTrail(player, 2);
                return true;
            }
            case 2715: {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                TreasureTrailManager.completeTreasureTrail(player, 3);
                return true;
            }
            case 2724: {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                TreasureTrailManager.advanceOrCompleteTrail(player, 1, "You've found another clue!", false, "Here is your reward!");
                return true;
            }
            case 2726: {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                TreasureTrailManager.advanceOrCompleteTrail(player, 2, "You've found another clue!", false, "Here is your reward!");
                return true;
            }
            case 2728: {
                player.getInventoryManager().removeItem(new ItemStack(itemId, 1));
                TreasureTrailManager.advanceOrCompleteTrail(player, 3, "You've found another clue!", false, "Here is your reward!");
                return true;
            }
        }
        return false;
    }

    public static void spawnClueWizard(Player player) {
        String text;
        int value = player.isInWilderness() ? 1007 : 1264;
        String text2 = text = player.isInWilderness() ? "Die, human!" : "For Saradomin!";
        if (!GameplayHelper.hasActiveTemporaryNpc(player, value)) {
            Npc npc = new Npc(value);
            GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, npc, true, true);
            npc.getUpdateState().setForcedText(text);
        }
    }

    public static void recordClueWizardKill(Player player, Npc npc) {
        if (npc.getNpcId() == 1007 || npc.getNpcId() == 1264) {
            player.killedClueAttacker = true;
        }
    }

}
