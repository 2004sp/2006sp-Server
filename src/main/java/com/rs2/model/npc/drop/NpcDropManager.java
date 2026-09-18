package com.rs2.model.npc.drop;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Random;

public final class NpcDropManager {
    private static Random random = new Random();
    private static int[] HERB_DROP_ITEM_IDS = new int[]{199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 2485, 3049, 3051};
    private static int[] SEED_DROP_ITEM_IDS = new int[]{5291, 5292, 5293, 5294, 5295, 5297, 5298, 5299, 5301, 5303, 5304, 5302, 5296, 5300};
    private static int[] GEM_DROP_ITEM_IDS = new int[]{1623, 1621, 1619, 1617};
    private static int[] HERB_DROP_WEIGHTS = new int[]{4, 4, 4, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 1};
    private static int[] SEED_DROP_WEIGHTS = new int[]{4, 4, 4, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 1};
    private static int[] GEM_DROP_WEIGHTS = new int[]{4, 3, 2, 1};
    private static ArrayList<Integer> weightedVirtualDropChoices = new ArrayList<Integer>();

    private static int resolveVirtualDropItemId(Entity entity, int itemId) {
        Player player = null;
        if (entity.isPlayer()) {
            player = (Player)entity;
        }
        Random random = new Random();
        int index = 0;
        if (!entity.isPlayer() || player.ownsClueScroll()) {
            index = 1;
        }
        weightedVirtualDropChoices.clear();
        int value = itemId - 65000;
        switch (value) {
            case 0: {
                return -1;
            }
            case 1: {
                if (index != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(1);
            }
            case 2: {
                if (index != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(2);
            }
            case 3: {
                if (index != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(3);
            }
            case 4: {
                if (index != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(3);
            }
            case 5: {
                value = 0;
                while (value < HERB_DROP_ITEM_IDS.length) {
                    itemId = HERB_DROP_WEIGHTS[value];
                    index = 0;
                    while (index < itemId) {
                        weightedVirtualDropChoices.add(HERB_DROP_ITEM_IDS[value]);
                        ++index;
                    }
                    ++value;
                }
                return weightedVirtualDropChoices.get(random.nextInt(weightedVirtualDropChoices.size()));
            }
            case 6: {
                value = 0;
                while (value < GEM_DROP_ITEM_IDS.length) {
                    itemId = GEM_DROP_WEIGHTS[value];
                    index = 0;
                    while (index < itemId) {
                        if (!ServerSettings.freeToPlayWorld && (player == null || player.isMember()) || !ItemDefinition.forId(GEM_DROP_ITEM_IDS[value]).isMembersOnly()) {
                            weightedVirtualDropChoices.add(GEM_DROP_ITEM_IDS[value]);
                        }
                        ++index;
                    }
                    ++value;
                }
                return weightedVirtualDropChoices.get(random.nextInt(weightedVirtualDropChoices.size()));
            }
            case 7: {
                value = 0;
                while (value < SEED_DROP_ITEM_IDS.length) {
                    itemId = SEED_DROP_WEIGHTS[value];
                    index = 0;
                    while (index < itemId) {
                        weightedVirtualDropChoices.add(SEED_DROP_ITEM_IDS[value]);
                        ++index;
                    }
                    ++value;
                }
                return weightedVirtualDropChoices.get(random.nextInt(weightedVirtualDropChoices.size()));
            }
        }
        return -1;
    }

    private static boolean isVirtualDropId(int value2) {
        return value2 >= 65000;
    }

    private static int randomInclusive(int value3, int value22) {
        value3 = random.nextInt(value22 + 1 - value3) + value3;
        return value3;
    }

    private static NpcDropTable getDropTableForNpcId(int npcId) {
        NpcDefinition npcDefinition = NpcDefinition.forId(npcId);
        int dropTableNpcIdOverride = npcDefinition.getDropTableNpcIdOverride();
        npcId = dropTableNpcIdOverride == -1 ? npcId : dropTableNpcIdOverride;
        return NpcDropTable.forNpcId(npcId);
    }

    public static ItemStack[] rollDrops(Entity entity, int value2, boolean enabled2) {
        NpcDefinition npcDefinition = NpcDefinition.forId(value2);
        ItemStack[] guaranteedDrops = null;
        NpcDropEntry[] guaranteedEntries = NpcDropManager.getDropTableForNpcId(value2).getGuaranteedDrops(entity);
        if (guaranteedEntries != null && guaranteedEntries.length > 0) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
            int index2 = 0;
            while (index2 < guaranteedEntries.length) {
                NpcDropEntry npcDropEntry = guaranteedEntries[index2];
                int itemId = npcDropEntry.getItemId();
                int initialValue = 1;
                if (npcDropEntry.getFixedAmount() > 0) {
                    initialValue = npcDropEntry.getFixedAmount();
                }
                if (npcDropEntry.getMinAmount() > 0) {
                    initialValue = NpcDropManager.randomInclusive(npcDropEntry.getMinAmount(), npcDropEntry.getMaxAmount());
                }
                if (npcDropEntry.getAmountOptions().length > 0 && npcDropEntry.getItemId() > 0) {
                    int amountOptions = random.nextInt(npcDropEntry.getAmountOptions().length);
                    initialValue = npcDropEntry.getAmountOptions()[amountOptions];
                }
                if (npcDropEntry.getMinAmounts().length > 0 && npcDropEntry.getItemId() > 0) {
                    int amountOptions2 = random.nextInt(npcDropEntry.getAmountOptions().length);
                    initialValue = NpcDropManager.randomInclusive(npcDropEntry.getMinAmounts()[amountOptions2], npcDropEntry.getMaxAmounts()[amountOptions2]);
                }
                if (NpcDropManager.isVirtualDropId(itemId)) {
                    if (NpcDropManager.isVirtualDropTableId(itemId)) {
                        ItemStack[] itemStackArray4 = NpcDropManager.resolveVirtualDropTable(entity, itemId, initialValue, npcDefinition.getCombatLevel());
                        if (itemStackArray4 != null) {
                            int index3 = 0;
                            while (index3 < itemStackArray4.length) {
                                arrayList.add(NpcDropManager.createItemStack(itemStackArray4[index3].getId(), itemStackArray4[index3].getAmount()));
                                ++index3;
                            }
                        }
                    } else {
                        itemId = NpcDropManager.resolveVirtualDropItemId(entity, itemId);
                    }
                }
                if (itemId == 5509) {
                    itemId = GameplayHelper.selectNextAvailableEssencePouch(entity, itemId);
                }
                if (ItemDefinition.isDefined(itemId)) {
                    arrayList.add(NpcDropManager.createItemStack(itemId, initialValue));
                }
                ++index2;
            }
            guaranteedDrops = arrayList.toArray(new ItemStack[arrayList.size()]);
        }
        ItemStack[] weightedDrops = NpcDropManager.rollWeightedDrops(entity, value2, true, 1, npcDefinition.getCombatLevel());
        ItemStack[] independentDrops = NpcDropManager.rollIndependentDrops(entity, value2, true, 1, npcDefinition.getCombatLevel());
        int guaranteedCount = guaranteedDrops == null ? 0 : guaranteedDrops.length;
        int weightedCount = weightedDrops == null ? 0 : weightedDrops.length;
        int independentCount = independentDrops == null ? 0 : independentDrops.length;
        ItemStack[] itemStackArray = new ItemStack[guaranteedCount + weightedCount + independentCount];
        int index = 0;
        index = NpcDropManager.copyDrops(guaranteedDrops, itemStackArray, index);
        index = NpcDropManager.copyDrops(weightedDrops, itemStackArray, index);
        NpcDropManager.copyDrops(independentDrops, itemStackArray, index);
        return itemStackArray;
    }

    private static int copyDrops(ItemStack[] source, ItemStack[] destination, int offset) {
        if (source == null) {
            return offset;
        }
        int index = 0;
        while (index < source.length) {
            destination[offset++] = source[index];
            ++index;
        }
        return offset;
    }

    private static ItemStack[] rollIndependentDrops(Entity entity, int value5, boolean enabled2, int value22, int value32) {
        NpcDropEntry[] npcDropEntryArray = NpcDropManager.getDropTableForNpcId(value5).getIndependentDrops(entity);
        if (npcDropEntryArray == null) {
            return null;
        }
        if (npcDropEntryArray.length == 0) {
            return null;
        }
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        int index = 0;
        while (index < npcDropEntryArray.length) {
            double chanceNumerator = npcDropEntryArray[index].getChanceNumerator();
            double chanceDenominator = npcDropEntryArray[index].getChanceDenominator();
            double value4 = chanceNumerator / chanceDenominator;
            if (ServerSettings.customDropRatesEnabled) {
                value4 = NpcDropManager.applyCustomDropRate(chanceNumerator, chanceDenominator);
            }
            if (GameUtil.rollChance(value4)) {
                ItemStack[] itemStackArray = NpcDropManager.rollDropEntrySelection(entity, value5, true, 1, value32, true, index);
                int index2 = 0;
                while (itemStackArray != null && index2 < itemStackArray.length) {
                    arrayList.add(itemStackArray[index2]);
                    ++index2;
                }
            }
            ++index;
        }
        return arrayList.toArray(new ItemStack[arrayList.size()]);
    }

    private static ItemStack[] rollWeightedDrops(Entity entity, int value4, boolean enabled2, int value22, int value32) {
        return NpcDropManager.rollDropEntrySelection(entity, value4, true, value22, value32, false, -1);
    }

    private static ItemStack[] rollDropEntrySelection(Entity entity, int value7, boolean enabled5, int value22, int value32, boolean enabled22, int value42) {
        NpcDropTable npcDropTable = NpcDropManager.getDropTableForNpcId(value7);
        NpcDropEntry[] npcDropEntryArray = enabled22 ? npcDropTable.getIndependentDrops(entity) : npcDropTable.getWeightedDrops(entity);
        if (npcDropEntryArray == null) {
            return null;
        }
        if (npcDropEntryArray.length == 0) {
            return null;
        }
        NpcDropEntry[] npcDropEntryArray2 = npcDropEntryArray;
        boolean enabled3 = false;
        if (!enabled22) {
            if (ServerSettings.rareDropChanceDivisor > 0 || ServerSettings.customDropRatesEnabled) {
                enabled3 = true;
            }
            if (enabled3 && ServerSettings.rareDropChanceDivisor > 0) {
                if (enabled5) {
                    NpcDropManager.assignMissingWeightedChances(npcDropEntryArray2);
                }
                ArrayList<NpcDropEntry> commonDrops = new ArrayList<NpcDropEntry>();
                ArrayList<NpcDropEntry> rareDrops = new ArrayList<NpcDropEntry>();
                int index = 0;
                while (index < npcDropEntryArray.length) {
                    NpcDropEntry npcDropEntry = npcDropEntryArray[index];
                    npcDropEntry.getItemId();
                    double chanceNumerator = npcDropEntry.getChanceNumerator();
                    double chanceDenominator = npcDropEntry.getChanceDenominator();
                    double value5 = chanceNumerator / chanceDenominator;
                    double value6 = 1.0 / (double)ServerSettings.rareDropChanceDivisor;
                    if (value5 <= value6) {
                        rareDrops.add(npcDropEntry);
                    } else {
                        commonDrops.add(npcDropEntry);
                    }
                    ++index;
                }
                ArrayList<NpcDropEntry> selectedDrops = rareDrops.size() == 0 ? commonDrops : rareDrops;
                npcDropEntryArray2 = selectedDrops.toArray(new NpcDropEntry[selectedDrops.size()]);
            }
            if ((value7 == 6391 || value7 == 6392 || value7 == 6393) && entity.isPlayer()) {
                Player player = (Player)entity;
                boolean enabled4;
                if (player.getEquipmentManager().getItemIdAtSlot(12) == 2572) {
                    player.ringOfWealthShinePending = true;
                    enabled4 = true;
                } else {
                    enabled4 = false;
                }
                if (enabled4) {
                    npcDropEntryArray2 = NpcDropManager.removeNoDropEntries(npcDropEntryArray);
                    enabled3 = true;
                }
            }
        }
        int noDrop = 0;
        ItemStack[] itemStackArray = new ItemStack[1];
        int selectedIndex;
        if (enabled5) {
            if (enabled22) {
                selectedIndex = value42;
            } else {
                NpcDropManager.assignMissingWeightedChances(npcDropEntryArray2);
                selectedIndex = NpcDropManager.selectDropIndex(npcDropEntryArray2, enabled3);
            }
            if (selectedIndex == -1) {
                noDrop = 1;
                selectedIndex = 0;
            }
        } else {
            selectedIndex = random.nextInt(npcDropEntryArray2.length);
        }
        NpcDropEntry selectedEntry = npcDropEntryArray2[selectedIndex];
        int itemId;
        int index2 = 0;
        if (selectedEntry.getItemIds().length > 0) {
            index2 = random.nextInt(selectedEntry.getItemIds().length);
            itemId = selectedEntry.getItemIds()[index2];
        } else {
            itemId = selectedEntry.getItemId();
        }
        index2 = NpcDropManager.rollEntryAmountForInitialVirtualCheck(selectedEntry);
        if (noDrop != 0) {
            itemId = 65000;
        }
        if (NpcDropManager.isVirtualDropId(itemId)) {
            if (NpcDropManager.isVirtualDropTableId(itemId)) {
                ItemStack[] nestedDrops = NpcDropManager.resolveVirtualDropTable(entity, itemId, index2 * value22, value32);
                if (nestedDrops != null) {
                    itemStackArray = new ItemStack[nestedDrops.length];
                    int index3 = 0;
                    while (index3 < nestedDrops.length) {
                        itemStackArray[index3] = NpcDropManager.createItemStack(nestedDrops[index3].getId(), nestedDrops[index3].getAmount());
                        ++index3;
                    }
                }
            } else {
                itemId = NpcDropManager.resolveVirtualDropItemId(entity, itemId);
            }
        }
        if (itemId == 5509) {
            itemId = GameplayHelper.selectNextAvailableEssencePouch(entity, itemId);
        }
        int amount = NpcDropManager.rollEntryAmount(selectedEntry);
        if (selectedEntry.getItemIds().length > 1) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
            int index4 = 0;
            while (index4 < selectedEntry.getItemIds().length) {
                itemId = selectedEntry.getItemIds()[index4];
                amount = selectedEntry.getMinAmounts().length > 1 ? NpcDropManager.randomInclusive(selectedEntry.getMinAmounts()[index4], selectedEntry.getMaxAmounts()[index4]) : selectedEntry.getAmountOptions()[index4];
                if (NpcDropManager.isVirtualDropId(itemId)) {
                    if (NpcDropManager.isVirtualDropTableId(itemId)) {
                        ItemStack[] nestedDrops = NpcDropManager.resolveVirtualDropTable(entity, itemId, amount * value22, value32);
                        int index5 = 0;
                        while (nestedDrops != null && index5 < nestedDrops.length) {
                            arrayList.add(nestedDrops[index5]);
                            ++index5;
                        }
                    } else {
                        itemId = NpcDropManager.resolveVirtualDropItemId(entity, itemId);
                    }
                }
                if (itemId == 5509) {
                    itemId = GameplayHelper.selectNextAvailableEssencePouch(entity, itemId);
                }
                if (ItemDefinition.isDefined(itemId)) {
                    arrayList.add(NpcDropManager.createItemStack(itemId, amount * value22));
                }
                ++index4;
            }
            itemStackArray = arrayList.toArray(new ItemStack[arrayList.size()]);
        } else if (ItemDefinition.isDefined(itemId)) {
            itemStackArray[0] = NpcDropManager.createItemStack(itemId, amount * value22);
        }
        return itemStackArray;
    }

    private static int rollEntryAmountForInitialVirtualCheck(NpcDropEntry npcDropEntry) {
        int value = NpcDropManager.rollEntryAmount(npcDropEntry);
        if (npcDropEntry.getItemIds().length > 1 && npcDropEntry.getMinAmounts().length <= 1) {
            value = npcDropEntry.getAmountOptions()[0];
        }
        if (npcDropEntry.getMinAmounts().length > 1) {
            value = NpcDropManager.randomInclusive(npcDropEntry.getMinAmounts()[0], npcDropEntry.getMaxAmounts()[0]);
        }
        return value;
    }

    private static int rollEntryAmount(NpcDropEntry npcDropEntry) {
        int index = 0;
        if (npcDropEntry.getFixedAmount() > 0) {
            index = npcDropEntry.getFixedAmount();
        }
        if (npcDropEntry.getMinAmount() > 0) {
            index = NpcDropManager.randomInclusive(npcDropEntry.getMinAmount(), npcDropEntry.getMaxAmount());
        }
        if (npcDropEntry.getAmountOptions().length > 0 && npcDropEntry.getItemId() > 0) {
            int amountOptions = random.nextInt(npcDropEntry.getAmountOptions().length);
            index = npcDropEntry.getAmountOptions()[amountOptions];
        }
        return index;
    }

    private static NpcDropEntry[] removeNoDropEntries(NpcDropEntry[] npcDropEntryArray) {
        int value;
        ArrayList<NpcDropEntry> arrayList = new ArrayList<NpcDropEntry>();
        int index = 0;
        while (index < npcDropEntryArray.length) {
            value = npcDropEntryArray[index].getItemId();
            if (value != 65000) {
                arrayList.add(npcDropEntryArray[index]);
            }
            ++index;
        }
        NpcDropEntry[] npcDropEntryArray2 = new NpcDropEntry[arrayList.size()];
        value = 0;
        while (value < arrayList.size()) {
            arrayList.get(value).getItemId();
            npcDropEntryArray2[value] = arrayList.get(value);
            ++value;
        }
        return npcDropEntryArray2;
    }

    private static boolean isVirtualDropTableId(int value2) {
        return value2 >= 65008;
    }

    private static ItemStack[] resolveVirtualDropTable(Entity entity, int value4, int value22, int value32) {
        ItemStack[] itemStackArray = null;
        if (value4 == 65008) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6391, true, value22, value32);
        } else if (value4 == 65009) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6392, true, value22, value32);
        } else if (value4 == 65010) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6393, true, value22, value32);
        } else if (value4 == 65011) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6394, true, value22, value32);
        } else if (value4 == 65012) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6395, true, value22, value32);
        } else if (value4 == 65013) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6396, true, value22, value32);
        } else if (value4 == 65014) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6397, true, value22, value32);
        } else if (value4 == 65015) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6398, true, value22, value32);
        } else if (value4 == 65016) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6399, true, value22, value32);
        } else if (value4 == 65017) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6400, true, value22, value32);
        } else if (value4 == 65018) {
            value4 = 1 + value32 * 10;
            if ((value4 = GameUtil.randomInt(value4)) < 485) {
                itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6401, true, value22, value32);
            } else if (value4 >= 485 && value4 < 728) {
                itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6414, true, value22, value32);
            } else if (value4 >= 728 && value4 < 850) {
                itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6415, true, value22, value32);
            } else if (value4 >= 850 && value4 < 947) {
                itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6416, true, value22, value32);
            } else if (value4 >= 947 && value4 < 995) {
                itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6417, true, value22, value32);
            } else if (value4 >= 995) {
                itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6418, true, value22, value32);
            }
        } else if (value4 == 65019) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6402, true, value22, value32);
        } else if (value4 == 65020) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6403, true, value22, value32);
        } else if (value4 == 65021) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6404, true, value22, value32);
        } else if (value4 == 65022) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6405, true, value22, value32);
        } else if (value4 == 65023) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6406, true, value22, value32);
        } else if (value4 == 65024) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6407, true, value22, value32);
        } else if (value4 == 65025) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6408, true, value22, value32);
        } else if (value4 == 65026) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6409, true, value22, value32);
        } else if (value4 == 65027) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6410, true, value22, value32);
        } else if (value4 == 65028) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6411, true, value22, value32);
        } else if (value4 == 65029) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6412, true, value22, value32);
        } else if (value4 == 65030) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6413, true, value22, value32);
        } else if (value4 == 65036) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6419, true, value22, value32);
        } else if (value4 == 65040) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6423, true, value22, value32);
        } else if (value4 == 65041) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6424, true, value22, value32);
        } else if (value4 == 65042) {
            itemStackArray = NpcDropManager.rollWeightedDrops(entity, 6425, true, value22, value32);
        }
        return itemStackArray;
    }

    private static void assignMissingWeightedChances(NpcDropEntry[] npcDropEntryArray) {
        int length = npcDropEntryArray.length;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        double value = 0.0;
        int index = 0;
        while (index < length) {
            if (npcDropEntryArray[index].getChanceType() == 1) {
                arrayList.add(index);
            } else {
                double chanceNumerator = npcDropEntryArray[index].getChanceNumerator();
                double chanceDenominator = npcDropEntryArray[index].getChanceDenominator();
                double value2 = chanceNumerator / chanceDenominator;
                value += value2;
            }
            ++index;
        }
        if (arrayList.size() > 0) {
            double value3;
            double value4 = value3 = (1.0 - value) / (double)arrayList.size();
            double value5 = Math.floor(value3);
            double value6 = value4 - value5;
            long value7 = NpcDropManager.greatestCommonDivisor(Math.round(value6 * 1.0E9), 1000000000L);
            long value8 = Math.round(value6 * 1.0E9) / value7;
            long value9 = 1000000000L / value7;
            String text = String.valueOf((long)(value5 * (double)value9) + value8) + "/" + value9;
            String[] stringValues = text.split("/");
            int index2 = 0;
            while (index2 < arrayList.size()) {
                int value10 = arrayList.get(index2);
                npcDropEntryArray[value10].setChanceNumerator(Integer.parseInt(stringValues[0]));
                npcDropEntryArray[value10].setChanceDenominator(Integer.parseInt(stringValues[1]));
                ++index2;
            }
        }
    }

    private static long greatestCommonDivisor(long value4, long value22) {
        while (value4 != 0L) {
            if (value22 == 0L) {
                return value4;
            }
            if (value4 < value22) {
                value22 %= value4;
                continue;
            }
            long value3 = value22;
            value22 = value4 % value22;
            value4 = value3;
        }
        return value22;
    }

    private static double applyCustomDropRate(double value8, double value22) {
        double value3 = value8 / value22;
        double value4 = value3 >= 0.043478260869565216 ? value8 * ServerSettings.commonDropRateMultiplier : (value3 >= 0.011111111111111112 ? value8 * ServerSettings.uncommonDropRateMultiplier : (value3 >= 0.0012300123001230013 ? value8 * ServerSettings.rareDropRateMultiplier : value8 * ServerSettings.veryRareDropRateMultiplier));
        double value5 = value4 / value22;
        if (ServerSettings.dropRateCap > 0) {
            double value6 = (double)ServerSettings.dropRateCap;
            double value7 = 1.0 / value6;
            if (value5 < value7) {
                value5 = value7;
            }
        }
        return value5;
    }

    private static int selectDropIndex(NpcDropEntry[] npcDropEntryArray, boolean index) {
        double value = 1.0;
        if (index) {
            value = 0.0;
        }
        int length = npcDropEntryArray.length;
        int index2 = 0;
        while (index2 < length) {
            double chanceNumerator = npcDropEntryArray[index2].getChanceNumerator();
            double chanceDenominator = npcDropEntryArray[index2].getChanceDenominator();
            double value2 = chanceNumerator / chanceDenominator;
            if (ServerSettings.customDropRatesEnabled) {
                value2 = NpcDropManager.applyCustomDropRate(chanceNumerator, chanceDenominator);
            }
            if (index) {
                value += value2;
            }
            ++index2;
        }
        double value3 = Math.random();
        double value4 = value3 * value;
        double value5 = 0.0;
        int index3 = 0;
        while (index3 < length) {
            double chanceNumerator2 = npcDropEntryArray[index3].getChanceNumerator();
            double chanceDenominator2 = npcDropEntryArray[index3].getChanceDenominator();
            double value6 = chanceNumerator2 / chanceDenominator2;
            if (ServerSettings.customDropRatesEnabled) {
                value6 = NpcDropManager.applyCustomDropRate(chanceNumerator2, chanceDenominator2);
            }
            value5 += value6;
            if (value5 >= value4) {
                return index3;
            }
            ++index3;
        }
        return -1;
    }

    private static ItemStack createItemStack(int itemId, int value2) {
        return new ItemStack(itemId, value2);
    }
}
