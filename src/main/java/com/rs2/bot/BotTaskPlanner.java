package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatLoadoutTables;
import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.AmmunitionDefinition;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.cooking.CookableFoodDefinition;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.model.skill.smithing.SmithingBarDefinition;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class BotTaskPlanner {
    private static int[][] skillTargetLevelMilestones;

    static {
        int[][] nArrayArray = new int[21][];
        int[] integerValues = new int[8];
        integerValues[1] = -1;
        integerValues[2] = -1;
        integerValues[3] = -1;
        integerValues[4] = -1;
        integerValues[5] = -1;
        integerValues[6] = -1;
        integerValues[7] = -1;
        nArrayArray[0] = integerValues;
        nArrayArray[1] = new int[]{1, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[2] = new int[]{2, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[3] = new int[]{3, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[4] = new int[]{4, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[5] = new int[]{5, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[6] = new int[]{6, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[7] = new int[]{7, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[8] = new int[]{8, 15, 30, 45, 60, 75, -1, -1};
        nArrayArray[9] = new int[]{9, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[10] = new int[]{10, 20, 30, 40, 50, 60, 70, 80};
        nArrayArray[11] = new int[]{11, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[12] = new int[]{12, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[13] = new int[]{13, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[14] = new int[]{14, 20, 35, 45, 60, 75, 90, -1};
        nArrayArray[15] = new int[]{15, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[16] = new int[]{16, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[17] = new int[]{17, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[18] = new int[]{18, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[19] = new int[]{19, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[20] = new int[]{20, -1, -1, -1, -1, -1, -1, -1};
        skillTargetLevelMilestones = nArrayArray;
    }

    public static void startInitialProgressiveBotTask(Player player) {
        if (player.currentBotTask == null && player.totalPlaytimeMillis == 0L) {
            int value;
            Object value2 = new ArrayList<BotTaskDefinition>();
            ((ArrayList)value2).addAll(BotTaskDefinition.miningTasks);
            ((ArrayList)value2).addAll(BotTaskDefinition.fishingTasks);
            ((ArrayList)value2).addAll(BotTaskDefinition.woodcuttingTasks);
            ((ArrayList)value2).add((BotTaskDefinition)BotTaskDefinition.runecraftingTasks.get(0));
            BotTaskDefinition botTaskDefinition = GameplayHelper.selectAvailableBotTask(player, (ArrayList)value2, false);
            if (botTaskDefinition == null) {
                player.botEnabled = false;
                return;
            }
            value2 = player;
            player.currentBotTask = botTaskDefinition;
            BotTaskPlanner.configureCurrentBotTaskGoals(player);
            BotTaskPlanner.prepareDeferredUpgradePurchase(player);
            player.botTaskReturnToBankRequested = false;
            player.botEnabled = true;
            player.currentBotTask.startTask(player);
            player.botTaskStartTimeMillis = System.currentTimeMillis();
            player.botTaskDurationMinutes = value = 30 + GameUtil.randomInt(60);
            player.botTaskSavedElapsedMillis = 0L;
            if (player.currentBotTask.usesEscapeMonitor) {
                player.currentBotTask.startEscapeMonitor(player);
            }
        }
    }

    public static BotTaskDefinition selectShopPurchaseTask(Player player, int value12, int value22) {
        BotTaskDefinition botTaskDefinition = null;
        int value3 = 3000;
        int initialValue = -1;
        for (Object botTaskDefinitionObject : BotTaskDefinition.shopTasks) {
            BotTaskDefinition botTaskDefinition2 = (BotTaskDefinition)botTaskDefinitionObject;
            double value4;
            double value5;
            int value6;
            int value7;
            int value8;
            if (botTaskDefinition2.membersOnly && ServerSettings.freeToPlayWorld || botTaskDefinition2.membersOnly && !player.isMember() || (value8 = GameUtil.getDistance(player.getPosition(), botTaskDefinition2.getStartPosition())) > 3000 || (value7 = botTaskDefinition2.getShopId()) == -1) continue;
            ShopDefinition shopDefinition = (ShopDefinition)ShopManager.getShopDefinitions().get(value7);
            ItemDefinition itemDefinition = new ItemStack(value12).getDefinition();
            initialValue = itemDefinition.getShopValue();
            int buyPricePercent = shopDefinition.getBuyPricePercent();
            double priceChangeRate = (double)(buyPricePercent + (value6 = (int)((double)buyPricePercent + (value5 = (value4 = shopDefinition.getPriceChangeRate()) * (double)(value22 - 1))))) / 2.0;
            double value9 = priceChangeRate / 100.0;
            double value10 = (double)initialValue * value9;
            double value11 = value10 * (double)value22 * 1.2;
            if (!player.ownsItemAmount(995, initialValue = (int)value11)) continue;
            buyPricePercent = 0;
            if (shopDefinition.getOriginalStock().findFlatItem(value12) != null) {
                buyPricePercent = shopDefinition.getOriginalStock().findFlatItem(value12).getAmount();
            }
            if (buyPricePercent <= 0 || value8 >= value3) continue;
            botTaskDefinition = botTaskDefinition2;
            value3 = value8;
        }
        if (botTaskDefinition != null && initialValue != -1) {
            player.botTaskItemId = value12;
            player.botShopItemAmount = value22;
            player.botShopBuyMode = 1;
            player.botTaskRequiredItems = new ItemStack[]{new ItemStack(995, initialValue)};
        } else {
            player.botTaskItemId = -1;
            player.botShopItemAmount = -1;
            player.botShopBuyMode = -1;
        }
        return botTaskDefinition;
    }

    public static void resetBotTaskGoals(Player player) {
        player.botSkillTargetSkillId = -1;
        player.botSkillTargetLevel = -1;
        player.botReservedGoalByte1 = -1;
        player.botReservedGoalByte2 = -1;
        player.botReservedGoalByte3 = -1;
        player.botReservedGoalByte4 = -1;
        player.botCompletionItemId = -1;
        player.botCompletionItemAmount = -1;
        player.botSecondaryCompletionItemId = -1;
        player.botReservedGoalInt2 = -1;
        player.botReservedGoalInt3 = -1;
        player.botReservedGoalInt4 = -1;
    }

    public static void configureCurrentBotTaskGoals(Player player) {
        boolean enabled = false;
        Player player2 = player;
        BotTaskDefinition botTaskDefinition = player2.currentBotTask;
        BotTaskPlanner.resetBotTaskGoals(player2);
        if (!BotTaskDefinition.shopTasks.contains(botTaskDefinition)) {
            player2.botTaskRequiredItems = null;
            Player player3 = player2;
            ArrayList arrayList = player3.currentBotTask.getRequiredItems(player3);
            player3.botTaskRequiredItems = new ItemStack[arrayList.size()];
            int index = 0;
            Iterator iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                ItemStack itemStack;
                player3.botTaskRequiredItems[index] = itemStack = (ItemStack)iterator.next();
                ++index;
            }
            boolean enabled2 = false;
            player3 = player2;
            if (BotTaskDefinition.miningTasks.contains(player3.currentBotTask)) {
                player3.botSkillTargetSkillId = 14;
            } else if (BotTaskDefinition.fishingTasks.contains(player3.currentBotTask)) {
                player3.botSkillTargetSkillId = 10;
            } else if (BotTaskDefinition.woodcuttingTasks.contains(player3.currentBotTask)) {
                player3.botSkillTargetSkillId = 8;
            }
            if (player3.botSkillTargetSkillId != -1) {
                index = -1;
                int initialValue = 1;
                while (initialValue < skillTargetLevelMilestones[player3.botSkillTargetSkillId].length) {
                    int value = skillTargetLevelMilestones[player3.botSkillTargetSkillId][initialValue];
                    if (value == -1) break;
                    if (player3.getSkillManager().getCurrentLevels()[player3.botSkillTargetSkillId] < value) {
                        index = value;
                        break;
                    }
                    ++initialValue;
                }
                if (index == -1) {
                    int[] skillManager = player3.getSkillManager().getCurrentLevels();
                    int value2 = player3.botSkillTargetSkillId;
                    int value3 = skillManager[value2] + 5;
                    skillManager[value2] = value3;
                    index = value3;
                }
                player3.botSkillTargetLevel = index;
                if (!enabled2) {
                    player3.queuePublicChatMessage("Im going to train " + SkillManager.SKILL_NAMES[player3.botSkillTargetSkillId] + ".");
                }
            }
            if (BotTaskDefinition.runecraftingTasks.get(0) == player3.currentBotTask) {
                player3.botCompletionItemId = 1436;
                player3.botSecondaryCompletionItemId = 7936;
                player3.botCompletionItemAmount = 1000;
                if (!enabled2) {
                    player3.queuePublicChatMessage("Im going to mine ess.");
                }
            } else if (BotTaskDefinition.smeltingTasks.contains(player3.currentBotTask)) {
                if (!enabled2) {
                    player3.queuePublicChatMessage("Im going to make some bars.");
                }
            } else if (BotTaskDefinition.sheepShearingTasks.contains(player3.currentBotTask)) {
                player3.botCompletionItemId = 1737;
                player3.botCompletionItemAmount = 100;
                if (!enabled2) {
                    player3.queuePublicChatMessage("Im going to get some wool.");
                }
            } else if (BotTaskDefinition.smithingTasks.contains(player3.currentBotTask)) {
                if (!enabled2) {
                    player3.queuePublicChatMessage("Im going to smith some items.");
                }
            } else if (BotTaskDefinition.cookingTasks.contains(player3.currentBotTask)) {
                if (!enabled2) {
                    player3.queuePublicChatMessage("Im going to cook some food.");
                }
            } else if (BotTaskDefinition.runecraftingTasks.contains(player3.currentBotTask) && !enabled2) {
                player3.queuePublicChatMessage("Im going to do some runecrafting.");
            }
        }
        if (botTaskDefinition.combatTask) {
            BotTaskPlanner.prepareBotCombatLoadoutLists(player2, false);
        }
        if (BotTaskDefinition.cookingTasks.contains(botTaskDefinition)) {
            BotTaskPlanner.prepareCookingTaskRequirements(player2);
            return;
        }
        if (BotTaskDefinition.spinningTasks.contains(botTaskDefinition)) {
            BotTaskPlanner.prepareSpinningTaskRequirements(player2);
            return;
        }
        if (BotTaskDefinition.smeltingTasks.contains(botTaskDefinition)) {
            BotTaskPlanner.hasSmeltingTaskMaterial(player2);
            return;
        }
        if (BotTaskDefinition.smithingTasks.contains(botTaskDefinition)) {
            BotTaskPlanner.prepareSmithingTaskRequirements(player2);
            return;
        }
        if (BotTaskDefinition.tanningTasks.contains(botTaskDefinition)) {
            BotTaskPlanner.prepareTanningTaskRequirements(player2, false);
            return;
        }
        if (BotTaskDefinition.leatherCraftingTasks.contains(botTaskDefinition)) {
            BotTaskPlanner.prepareLeatherCraftingTaskRequirements(player2);
        }
    }

    public static void populateBotShopSellItemIds(Player player) {
        int value;
        player.botShopSellItemIds.clear();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Object value2 = BotCombatLoadoutTables.post306BowIds;
        int index = 0;
        while (index < 4) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.swordIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.longswordIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.scimitarIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.battleaxeIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.warhammerIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.maceIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.daggerIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.twoHandedSwordIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.basicRangedHeadIds;
        index = 0;
        while (index < 2) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.basicRangedBodyIds;
        index = 0;
        while (index < 4) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.basicRangedLegIds;
        index = 0;
        while (index < 3) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.basicRangedVambraceIds;
        index = 0;
        while (index < 2) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.mediumHelmetIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.fullHelmetIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.chainbodyIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.platebodyIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.platelegIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.plateskirtIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.squareShieldIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        value2 = BotCombatLoadoutTables.kiteshieldIds;
        index = 0;
        while (index < 7) {
            value = ((int[])value2)[index];
            arrayList.add(new Integer(value));
            ++index;
        }
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(1381);
        arrayList2.add(1383);
        arrayList2.add(1385);
        arrayList2.add(1387);
        BotTaskPlanner.prepareBotCombatLoadoutLists(player, true);
        index = -1;
        int initialValue = -1;
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findOwnedGatheringTool(player, 14);
        value2 = (Object)gatheringToolDefinition;
        if (gatheringToolDefinition != null) {
            index = ((GatheringToolDefinition)((Object)value2)).getToolItemId();
        }
        if ((value2 = ItemCombinationHandler.findOwnedGatheringTool(player, 8)) != null) {
            initialValue = ((GatheringToolDefinition)((Object)value2)).getToolItemId();
        }
        ItemStack[] itemStackArray = player.getBankContainer().getItems();
        int length = itemStackArray.length;
        int index2 = 0;
        while (index2 < length) {
            value2 = itemStackArray[index2];
            if (value2 != null) {
                if (player.botShopSellItemIds.size() > 20) break;
                if (ItemCombinationHandler.isGatheringToolItemId(((ItemStack)value2).getId()) && ((ItemStack)value2).getId() != index && ((ItemStack)value2).getId() != initialValue) {
                    player.botShopSellItemIds.add(((ItemStack)value2).getId());
                } else {
                    ItemDefinition itemDefinition = ((ItemStack)value2).getDefinition();
                    if (itemDefinition.getName().toLowerCase().endsWith("logs") && !itemDefinition.getName().toLowerCase().equals("logs")) {
                        player.botShopSellItemIds.add(((ItemStack)value2).getId());
                    } else if (!(!arrayList.contains(((ItemStack)value2).getId()) || player.botMeleeLoadoutItemIds.contains(((ItemStack)value2).getId()) || player.botMagicLoadoutItemIds.contains(((ItemStack)value2).getId()) || player.botRangedLoadoutItemIds.contains(((ItemStack)value2).getId()) || arrayList2.contains(((ItemStack)value2).getId()))) {
                        player.botShopSellItemIds.add(((ItemStack)value2).getId());
                    }
                }
            }
            ++index2;
        }
    }

    private static boolean tryDeferForUpgradePurchase(Player player, int value3, int[] integerValues2, int value22, boolean enabled2) {
        return BotTaskPlanner.tryDeferForUpgradePurchase(player, value3, integerValues2, value22, enabled2, false);
    }

    private static boolean tryDeferForUpgradePurchase(Player player, int currentLevel, int[] itemIds, int skillId, boolean requireUpgrade, boolean shuffle) {
        if (shuffle) {
            ArrayList<Integer> shuffledIds = new ArrayList<Integer>();
            int index = itemIds.length - 1;
            while (index >= 0) {
                shuffledIds.add(itemIds[index]);
                --index;
            }
            Collections.shuffle(shuffledIds);
            itemIds = new int[shuffledIds.size()];
            index = 0;
            while (index < shuffledIds.size()) {
                itemIds[index] = (Integer)shuffledIds.get(index);
                ++index;
            }
        }
        int index = itemIds.length - 1;
        while (index >= 0) {
            int itemId = itemIds[index];
            if (player.getEquipmentManager().canEquipItem(itemId)) {
                ItemDefinition itemDefinition = new ItemStack(itemId, 1).getDefinition();
                boolean shouldDefer = false;
                if (!requireUpgrade || currentLevel < itemDefinition.getRequiredLevel(skillId)) {
                    shouldDefer = true;
                }
                if (shouldDefer) {
                    BotTaskDefinition botTaskDefinition = BotTaskPlanner.selectShopPurchaseTask(player, itemId, 1);
                    if (botTaskDefinition != null) {
                        BotTaskPlanner.resetBotTaskGoals(player);
                        player.deferredBotTask = player.currentBotTask;
                        player.currentBotTask = botTaskDefinition;
                        return true;
                    }
                } else if (currentLevel >= itemDefinition.getRequiredLevel(skillId) && requireUpgrade) {
                    return false;
                }
            }
            --index;
        }
        return false;
    }

    public static void prepareDeferredUpgradePurchase(Player player) {
        prepareDeferredUpgradePurchaseControlExit1: {
            if (!player.currentBotTask.combatTask) {
                player.botCombatLoadoutSlotCursor = -1;
            }
            if (BotTaskDefinition.miningTasks.contains(player.currentBotTask) || BotTaskDefinition.woodcuttingTasks.contains(player.currentBotTask) || BotTaskDefinition.runecraftingTasks.get(0) == player.currentBotTask) {
                int skillId = 14;
                if (BotTaskDefinition.woodcuttingTasks.contains(player.currentBotTask)) {
                    skillId = 8;
                }
                GatheringToolDefinition ownedTool = ItemCombinationHandler.findOwnedGatheringTool(player, skillId);
                ArrayList arrayList = ItemCombinationHandler.getGatheringToolsForSkill(skillId);
                int index = arrayList.size() - 1;
                while (index >= 0) {
                    GatheringToolDefinition toolDefinition = (GatheringToolDefinition)arrayList.get(index);
                    boolean shouldBuy = false;
                    if (ownedTool == null) {
                        shouldBuy = true;
                    } else if (skillId == 14) {
                        if (toolDefinition.getToolSpeed() < ownedTool.getToolSpeed()) {
                            shouldBuy = true;
                        }
                    } else if (toolDefinition.getToolSpeed() > ownedTool.getToolSpeed()) {
                        shouldBuy = true;
                    }
                    if (shouldBuy) {
                        BotTaskDefinition purchaseTask = BotTaskPlanner.selectShopPurchaseTask(player, toolDefinition.getToolItemId(), 1);
                        if (purchaseTask != null) {
                            BotTaskPlanner.resetBotTaskGoals(player);
                            player.deferredBotTask = player.currentBotTask;
                            player.currentBotTask = purchaseTask;
                            return;
                        }
                    }
                    --index;
                }
                return;
            }
            if (player.currentBotTask.combatTask) {
                int[] integerValues = new int[8];
                integerValues[0] = 3;
                integerValues[1] = 5;
                integerValues[3] = 7;
                integerValues[4] = 4;
                integerValues[5] = 1;
                integerValues[6] = 9;
                integerValues[7] = 10;
                int[] integerValues2 = integerValues;
                if (player.botCombatStyle == 0) {
                    int value = player.botCombatLoadoutSlotCursor + 1;
                    while (value < 8) {
                        Object value2;
                        ItemDefinition itemDefinition = null;
                        player.botCombatLoadoutSlotCursor = value;
                        int value3 = integerValues2[value];
                        Iterator iterator = player.botMeleeLoadoutItemIds.iterator();
                        while (iterator.hasNext()) {
                            int integer = (Integer)iterator.next();
                            value2 = new ItemStack(integer, 1);
                            ItemDefinition itemDefinition2 = ((ItemStack)value2).getDefinition();
                            if (itemDefinition2.getEquipmentSlot() != value3) continue;
                            itemDefinition = itemDefinition2;
                            break;
                        }
                        if (value3 == 3) {
                            int index2 = 0;
                            if (itemDefinition != null) {
                                index2 = itemDefinition.getRequiredLevel(0);
                            }
                            value2 = null;
                            int value4 = GameUtil.randomInt(4);
                            if (value4 == 0) {
                                value2 = BotCombatLoadoutTables.swordIds;
                            } else if (value4 == 1) {
                                value2 = BotCombatLoadoutTables.longswordIds;
                            } else if (value4 == 2) {
                                value2 = BotCombatLoadoutTables.scimitarIds;
                            } else if (value4 == 3) {
                                value2 = BotCombatLoadoutTables.battleaxeIds;
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index2, (int[])value2, 0, itemDefinition != null)) {
                                return;
                            }
                        } else if (value3 == 5) {
                            int index3 = 0;
                            if (itemDefinition != null) {
                                index3 = itemDefinition.getRequiredLevel(1);
                            }
                            value2 = null;
                            int value5 = GameUtil.randomInt(2);
                            if (value5 == 0) {
                                value2 = BotCombatLoadoutTables.squareShieldIds;
                            } else if (value5 == 1) {
                                value2 = BotCombatLoadoutTables.kiteshieldIds;
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index3, (int[])value2, 1, itemDefinition != null)) {
                                return;
                            }
                        } else if (value3 == 0) {
                            int index4 = 0;
                            if (itemDefinition != null) {
                                index4 = itemDefinition.getRequiredLevel(1);
                            }
                            value2 = null;
                            int value6 = GameUtil.randomInt(2);
                            if (value6 == 0) {
                                value2 = BotCombatLoadoutTables.mediumHelmetIds;
                            } else if (value6 == 1) {
                                value2 = BotCombatLoadoutTables.fullHelmetIds;
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index4, (int[])value2, 1, itemDefinition != null)) {
                                return;
                            }
                        } else if (value3 == 7) {
                            int index5 = 0;
                            if (itemDefinition != null) {
                                index5 = itemDefinition.getRequiredLevel(1);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index5, (int[])(value2 = player.getGender() == 0 ? (Object)BotCombatLoadoutTables.platelegIds : (Object)BotCombatLoadoutTables.plateskirtIds), 1, itemDefinition != null)) {
                                return;
                            }
                        } else if (value3 == 4) {
                            int index6 = 0;
                            if (itemDefinition != null) {
                                index6 = itemDefinition.getRequiredLevel(1);
                            }
                            value2 = null;
                            int value7 = GameUtil.randomInt(2);
                            if (value7 == 0) {
                                value2 = BotCombatLoadoutTables.chainbodyIds;
                            } else if (value7 == 1) {
                                value2 = BotCombatLoadoutTables.platebodyIds;
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index6, (int[])value2, 1, itemDefinition != null)) {
                                return;
                            }
                        } else if (value3 == 1) {
                            int index7 = 0;
                            if (itemDefinition != null) {
                                index7 = itemDefinition.getRequiredLevel(1);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index7, (int[])(value2 = (Object)BotCombatLoadoutTables.capeIds), 1, itemDefinition != null, true)) {
                                return;
                            }
                        } else if (value3 == 9) {
                            int index8 = 0;
                            if (itemDefinition != null) {
                                index8 = itemDefinition.getRequiredLevel(1);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index8, (int[])(value2 = (Object)new int[]{1059}), 1, itemDefinition != null)) {
                                return;
                            }
                        } else if (value3 == 10) {
                            int index9 = 0;
                            if (itemDefinition != null) {
                                index9 = itemDefinition.getRequiredLevel(1);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index9, (int[])(value2 = (Object)new int[]{1061}), 1, itemDefinition != null)) break prepareDeferredUpgradePurchaseControlExit1;
                        }
                        ++value;
                    }
                    return;
                }
                if (player.botCombatStyle == 1) {
                    int value8 = player.botCombatLoadoutSlotCursor + 1;
                    while (value8 < 8) {
                        Object value9;
                        ItemDefinition itemDefinition = null;
                        player.botCombatLoadoutSlotCursor = value8;
                        int value10 = integerValues2[value8];
                        Iterator iterator = player.botRangedLoadoutItemIds.iterator();
                        while (iterator.hasNext()) {
                            int integer2 = (Integer)iterator.next();
                            ItemStack itemStack = new ItemStack(integer2, 1);
                            value9 = itemStack;
                            ItemDefinition itemDefinition3 = itemStack.getDefinition();
                            if (itemDefinition3.getEquipmentSlot() != value10) continue;
                            itemDefinition = itemDefinition3;
                            break;
                        }
                        if (value10 == 3) {
                            int index10 = 0;
                            if (itemDefinition != null) {
                                index10 = itemDefinition.getRequiredLevel(4);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index10, BotCombatLoadoutTables.post306BowIds, 4, itemDefinition != null)) {
                                return;
                            }
                        } else if (value10 == 9) {
                            int index11 = 0;
                            if (itemDefinition != null) {
                                index11 = itemDefinition.getRequiredLevel(4);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index11, BotCombatLoadoutTables.basicRangedVambraceIds, 4, itemDefinition != null)) {
                                return;
                            }
                        } else if (value10 == 0) {
                            int index12 = 0;
                            if (itemDefinition != null) {
                                index12 = itemDefinition.getRequiredLevel(4);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index12, BotCombatLoadoutTables.basicRangedHeadIds, 4, itemDefinition != null)) {
                                return;
                            }
                        } else if (value10 == 7) {
                            int index13 = 0;
                            if (itemDefinition != null) {
                                index13 = itemDefinition.getRequiredLevel(4);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index13, BotCombatLoadoutTables.basicRangedLegIds, 4, itemDefinition != null)) {
                                return;
                            }
                        } else if (value10 == 4) {
                            int index14 = 0;
                            if (itemDefinition != null) {
                                index14 = itemDefinition.getRequiredLevel(4);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index14, BotCombatLoadoutTables.basicRangedBodyIds, 4, itemDefinition != null)) {
                                return;
                            }
                        } else if (value10 == 1) {
                            int index15 = 0;
                            if (itemDefinition != null) {
                                index15 = itemDefinition.getRequiredLevel(1);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index15, BotCombatLoadoutTables.capeIds, 1, itemDefinition != null, true)) break prepareDeferredUpgradePurchaseControlExit1;
                        }
                        ++value8;
                    }
                    return;
                }
                if (player.botCombatStyle == 2) {
                    int value11 = player.botCombatLoadoutSlotCursor + 1;
                    while (value11 < 8) {
                        Object value12;
                        ItemDefinition itemDefinition = null;
                        player.botCombatLoadoutSlotCursor = value11;
                        int value13 = integerValues2[value11];
                        Iterator iterator = player.botMagicLoadoutItemIds.iterator();
                        while (iterator.hasNext()) {
                            int integer3 = (Integer)iterator.next();
                            value12 = new ItemStack(integer3, 1);
                            ItemDefinition itemDefinition4 = ((ItemStack)value12).getDefinition();
                            if (itemDefinition4.getEquipmentSlot() != value13) continue;
                            itemDefinition = itemDefinition4;
                            break;
                        }
                        if (value13 == 0) {
                            int index16 = 0;
                            if (itemDefinition != null) {
                                index16 = itemDefinition.getRequiredLevel(6);
                            }
                            if (BotTaskPlanner.tryDeferForUpgradePurchase(player, index16, (int[])(value12 = (Object)new int[]{579, 1017}), 6, itemDefinition != null, true)) break;
                        }
                        ++value11;
                    }
                }
            }
        }
    }

    public static boolean prepareCookingTaskRequirements(Player player) {
        ArrayList<ItemStack> availableFood = new ArrayList<ItemStack>();
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null) {
                ItemDefinition itemDefinition = itemStack.getDefinition();
                CookableFoodDefinition foodDefinition = CookableFoodDefinition.forRawItemId(itemStack.getId());
                if (itemDefinition.getName().toLowerCase().startsWith("raw ") && foodDefinition != null && player.getSkillManager().getCurrentLevels()[7] >= foodDefinition.getRequiredLevel()) {
                    availableFood.add(itemStack);
                }
            }
            ++index;
        }
        itemStackArray = player.getBankContainer().getItems();
        length = itemStackArray.length;
        index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null) {
                ItemDefinition itemDefinition = itemStack.getDefinition();
                CookableFoodDefinition foodDefinition = CookableFoodDefinition.forRawItemId(itemStack.getId());
                if (itemDefinition.getName().toLowerCase().startsWith("raw ") && foodDefinition != null && player.getSkillManager().getCurrentLevels()[7] >= foodDefinition.getRequiredLevel()) {
                    availableFood.add(itemStack);
                }
            }
            ++index;
        }
        if (availableFood.size() == 0) {
            return false;
        }
        player.botTaskItemId = ((ItemStack)availableFood.get(0)).getId();
        player.botTaskRequiredItems = new ItemStack[]{new ItemStack(player.botTaskItemId, 28)};
        return true;
    }

    public static boolean prepareSpinningTaskRequirements(Player player) {
        ArrayList<ItemStack> availableItems = new ArrayList<ItemStack>();
        if (player.ownsItemAmount(1737, 28)) {
            availableItems.add(new ItemStack(1737, 28));
        }
        if (player.getSkillManager().getCurrentLevels()[12] >= 10 && player.ownsItemAmount(1779, 28)) {
            availableItems.add(new ItemStack(1779, 28));
        }
        if (player.getSkillManager().getCurrentLevels()[12] >= 19 && player.ownsItemAmount(6051, 28)) {
            availableItems.add(new ItemStack(6051, 28));
        }
        if (availableItems.size() == 0) {
            return false;
        }
        player.botTaskItemId = ((ItemStack)availableItems.get(0)).getId();
        player.botTaskRequiredItems = new ItemStack[]{new ItemStack(player.botTaskItemId, 28)};
        return true;
    }

    public static boolean prepareTanningTaskRequirements(Player player, boolean enabled2) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (player.ownsItemAmount(1739, 100) && player.ownsItemAmount(995, 500)) {
            arrayList.add(new ItemStack(1739, 100));
        }
        if (!ServerSettings.freeToPlayWorld && player.isMember()) {
            if (player.getSkillManager().getCurrentLevels()[12] >= 57 && player.ownsItemAmount(1753, 100) && player.ownsItemAmount(995, 2500)) {
                arrayList.add(new ItemStack(1753, 100));
            }
            if (player.getSkillManager().getCurrentLevels()[12] >= 66 && player.ownsItemAmount(1751, 100) && player.ownsItemAmount(995, 2500)) {
                arrayList.add(new ItemStack(1751, 100));
            }
            if (player.getSkillManager().getCurrentLevels()[12] >= 73 && player.ownsItemAmount(1749, 100) && player.ownsItemAmount(995, 2500)) {
                arrayList.add(new ItemStack(1749, 100));
            }
            if (player.getSkillManager().getCurrentLevels()[12] >= 79 && player.ownsItemAmount(1747, 100) && player.ownsItemAmount(995, 2500)) {
                arrayList.add(new ItemStack(1747, 100));
            }
        }
        if (arrayList.size() == 0) {
            return false;
        }
        if (!enabled2) {
            int value = 550;
            player.botTaskItemId = ((ItemStack)arrayList.get(0)).getId();
            if (player.botTaskItemId == 1739) {
                value = 100;
            }
            ItemStack[] itemStackArray2 = new ItemStack[]{new ItemStack(995, value), new ItemStack(player.botTaskItemId, 27)};
            player.botTaskRequiredItems = itemStackArray2;
        }
        return true;
    }

    public static boolean prepareLeatherCraftingTaskRequirements(Player player) {
        ArrayList<ItemStack> availableItems = new ArrayList<ItemStack>();
        if (player.ownsItemAmount(1741, 100)) {
            availableItems.add(new ItemStack(1741, 100));
        }
        if (player.getSkillManager().getCurrentLevels()[12] >= 28 && player.ownsItemAmount(1743, 100)) {
            availableItems.add(new ItemStack(1743, 100));
        }
        if (!ServerSettings.freeToPlayWorld && player.isMember()) {
            if (player.getSkillManager().getCurrentLevels()[12] >= 57 && player.ownsItemAmount(1745, 100)) {
                availableItems.add(new ItemStack(1745, 100));
            }
            if (player.getSkillManager().getCurrentLevels()[12] >= 66 && player.ownsItemAmount(2505, 100)) {
                availableItems.add(new ItemStack(2505, 100));
            }
            if (player.getSkillManager().getCurrentLevels()[12] >= 73 && player.ownsItemAmount(2507, 100)) {
                availableItems.add(new ItemStack(2507, 100));
            }
            if (player.getSkillManager().getCurrentLevels()[12] >= 79 && player.ownsItemAmount(2509, 100)) {
                availableItems.add(new ItemStack(2509, 100));
            }
        }
        if (availableItems.size() == 0) {
            return false;
        }
        player.botTaskItemId = ((ItemStack)availableItems.get(0)).getId();
        player.botTaskRequiredItems = new ItemStack[]{new ItemStack(1733, 1), new ItemStack(1734, 26), new ItemStack(player.botTaskItemId, 26)};
        return true;
    }

    public static boolean hasSmeltingTaskMaterial(Player player) {
        return SmeltingHandler.selectBestBotSmeltingBarItemId(player) != -1;
    }

    public static boolean prepareSmithingTaskRequirements(Player player) {
        int initialValue = -1;
        int index = 0;
        while (index < SmithingBarDefinition.VALUES.length) {
            SmithingBarDefinition smithingBarDefinition = SmithingBarDefinition.VALUES[index];
            int requiredLevel = smithingBarDefinition.getRequiredLevel();
            if (player.getSkillManager().getCurrentLevels()[13] >= requiredLevel && player.ownsItem(smithingBarDefinition.getBarItemId())) {
                player.botTaskItemId = initialValue = smithingBarDefinition.getBarItemId();
                ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(2347, 1), new ItemStack(initialValue, 27)};
                player.botTaskRequiredItems = itemStackArray;
                break;
            }
            ++index;
        }
        return initialValue != -1;
    }

    public static void selectMeleeTrainingFightMode(Player player) {
        if (player.botCombatStyle != 0) {
            return;
        }
        Player player2 = player;
        int skillManager = player2.getSkillManager().getCurrentLevels()[0] <= player2.getSkillManager().getCurrentLevels()[1] && player2.getSkillManager().getCurrentLevels()[0] <= player2.getSkillManager().getCurrentLevels()[2] ? 0 : (player2.getSkillManager().getCurrentLevels()[2] < player2.getSkillManager().getCurrentLevels()[0] && player2.getSkillManager().getCurrentLevels()[2] <= player2.getSkillManager().getCurrentLevels()[1] ? 2 : 1);
        WeaponProfile weaponProfile = WeaponProfile.forItem(player.getEquipmentManager().getContainer().getItemAt(3));
        AttackStyleDefinition[] attackStyleDefinitionArray = weaponProfile.getInterfaceDefinition().getAttackStyles();
        int initialValue = -1;
        int value = 10;
        boolean enabled = false;
        int index = 0;
        while (index < attackStyleDefinitionArray.length) {
            if (enabled) break;
            int[] xpMode = attackStyleDefinitionArray[index].getXpMode().getSkillIds();
            int index2 = 0;
            while (index2 < xpMode.length) {
                if (enabled) break;
                if (xpMode[index2] == skillManager && xpMode.length == 1) {
                    initialValue = index;
                    value = xpMode.length;
                    enabled = true;
                } else if (xpMode[index2] == skillManager && xpMode.length < value) {
                    value = xpMode.length;
                    initialValue = index;
                }
                ++index2;
            }
            ++index;
        }
        if (initialValue == -1) {
            initialValue = 0;
        }
        player.setFightMode(initialValue);
    }

    private static void prepareBotCombatLoadoutLists(Player player, boolean enabled5) {
        int loadoutCategoryIndex = 0;
        int value;
        int value2;
        Object value3;
        ItemStack itemStack;
        player.botMeleeLoadoutItemIds.clear();
        player.botMagicLoadoutItemIds.clear();
        player.botRangedLoadoutItemIds.clear();
        player.botCombatLoadoutItemIds.clear();
        Object value4 = new ArrayList<ItemStack>();
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        ArrayList<ItemStack> arrayList2 = new ArrayList<ItemStack>();
        ItemStack[] itemStackArray = player.getEquipmentManager().getContainer().getItems();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            itemStack = itemStackArray[index];
            if (itemStack != null && !ItemCombinationHandler.isGatheringToolItemId(itemStack.getId()) && ((ItemDefinition)(value3 = itemStack.getDefinition())).getEquipmentSlot() != -1 && player.getEquipmentManager().canEquipItem(itemStack.getId())) {
                if (((ItemDefinition)value3).getEquipmentSlot() == 13 && ((ItemDefinition)value3).getName().toLowerCase().endsWith("arrow")) {
                    if (itemStack.getAmount() >= 200) {
                        arrayList.add(itemStack);
                    }
                } else {
                    if (((ItemDefinition)value3).getEquipmentSlot() == 3 && ((ItemDefinition)value3).getName().toLowerCase().endsWith("bow")) {
                        arrayList2.add(itemStack);
                    }
                    ((ArrayList)value4).add(itemStack);
                }
            }
            ++index;
        }
        ItemStack[] itemStackArray2 = player.getInventoryManager().getContainer().getItems();
        length = itemStackArray2.length;
        index = 0;
        while (index < length) {
            itemStack = itemStackArray2[index];
            if (itemStack != null && !ItemCombinationHandler.isGatheringToolItemId(itemStack.getId()) && ((ItemDefinition)(value3 = itemStack.getDefinition())).getEquipmentSlot() != -1 && player.getEquipmentManager().canEquipItem(itemStack.getId())) {
                if (((ItemDefinition)value3).getEquipmentSlot() == 13 && ((ItemDefinition)value3).getName().toLowerCase().endsWith("arrow")) {
                    if (itemStack.getAmount() >= 200) {
                        arrayList.add(itemStack);
                    }
                } else {
                    if (((ItemDefinition)value3).getEquipmentSlot() == 3 && ((ItemDefinition)value3).getName().toLowerCase().endsWith("bow")) {
                        arrayList2.add(itemStack);
                    }
                    ((ArrayList)value4).add(itemStack);
                }
            }
            ++index;
        }
        ItemStack[] itemStackArray3 = player.getBankContainer().getItems();
        length = itemStackArray3.length;
        index = 0;
        while (index < length) {
            itemStack = itemStackArray3[index];
            if (itemStack != null && !ItemCombinationHandler.isGatheringToolItemId(itemStack.getId()) && ((ItemDefinition)(value3 = itemStack.getDefinition())).getEquipmentSlot() != -1 && player.getEquipmentManager().canEquipItem(itemStack.getId())) {
                if (((ItemDefinition)value3).getEquipmentSlot() == 13 && ((ItemDefinition)value3).getName().toLowerCase().endsWith("arrow")) {
                    if (itemStack.getAmount() >= 200) {
                        arrayList.add(itemStack);
                    }
                } else {
                    if (((ItemDefinition)value3).getEquipmentSlot() == 3 && ((ItemDefinition)value3).getName().toLowerCase().endsWith("bow")) {
                        arrayList2.add(itemStack);
                    }
                    ((ArrayList)value4).add(itemStack);
                }
            }
            ++index;
        }
        boolean enabled2 = false;
        index = 0;
        length = -1;
        int[] integerValues = new int[4];
        int[] integerValues2 = integerValues;
        integerValues[0] = 1381;
        integerValues2[1] = 1383;
        integerValues2[2] = 1385;
        integerValues2[3] = 1387;
        value3 = new ArrayList();
        if (!enabled5) {
            int index2 = 0;
            BotTaskDefinition botTaskDefinition;
            BotTaskDefinition botTaskDefinition2;
            if (arrayList2.size() > 0 && arrayList.size() > 0) {
                prepareBotCombatLoadoutListsControlLoop1: for (ItemStack itemStack2 : arrayList2) {
                    if (enabled2) break;
                    for (ItemStack itemStack3 : arrayList) {
                        if (enabled2) continue prepareBotCombatLoadoutListsControlLoop1;
                        if (!AmmunitionDefinition.isCompatible(player, itemStack2, itemStack3)) continue;
                        enabled2 = true;
                    }
                }
            }
            if (!enabled2) {
                BotTaskDefinition botTaskDefinition3;
                if (arrayList2.size() == 0) {
                    Object value5 = BotCombatLoadoutTables.post306BowIds;
                    if (BotTaskPlanner.tryDeferForUpgradePurchase(player, 0, (int[])value5, 4, false)) {
                        return;
                    }
                } else if (arrayList.size() == 0 && (botTaskDefinition3 = BotTaskPlanner.selectShopPurchaseTask(player, 882, 400)) != null) {
                    BotTaskPlanner.resetBotTaskGoals(player);
                    value4 = player.currentBotTask;
                    Player player2 = player;
                    player.deferredBotTask = (BotTaskDefinition)value4;
                    value4 = botTaskDefinition3;
                    player2 = player;
                    player.currentBotTask = (BotTaskDefinition)value4;
                    return;
                }
            }
            if (!player.ownsItemAmount(556, 250) && !player.ownsItem(1438) && !player.ownsItem(5527) && player.ownsItemAmount(995, 375) && (botTaskDefinition2 = BotTaskPlanner.selectShopPurchaseTask(player, 556, 250)) != null) {
                BotTaskPlanner.resetBotTaskGoals(player);
                value4 = player.currentBotTask;
                Player player3 = player;
                player.deferredBotTask = (BotTaskDefinition)value4;
                value4 = botTaskDefinition2;
                player3 = player;
                player.currentBotTask = (BotTaskDefinition)value4;
                return;
            }
            if (!player.ownsItemAmount(558, 250) && player.ownsItemAmount(995, 375) && (botTaskDefinition = BotTaskPlanner.selectShopPurchaseTask(player, 558, 250)) != null) {
                BotTaskPlanner.resetBotTaskGoals(player);
                value4 = player.currentBotTask;
                Player player4 = player;
                player.deferredBotTask = (BotTaskDefinition)value4;
                value4 = botTaskDefinition;
                player4 = player;
                player.currentBotTask = (BotTaskDefinition)value4;
                return;
            }
            int[][] integerValues3 = new int[4][2];
            int index3 = 0;
            Object value6 = BotCombatLoadoutTables.elementalStrikeSpells;
            int index4 = 0;
            while (index4 < 4) {
                SpellDefinition spellDefinition = ((SpellDefinition[])value6)[index4];
                ((ArrayList)value3).add(spellDefinition);
                ++index4;
            }
            boolean enabled3 = false;
            if (player.getSkillManager().getCurrentLevels()[6] >= 13) {
                index2 = 2;
            }
            index4 = index2;
            while (index4 < ((ArrayList)value3).size()) {
                SpellDefinition spellDefinition = (SpellDefinition)((Object)((ArrayList)value3).get(index4));
                if (spellDefinition.getRequiredLevel() > player.getSkillManager().getCurrentLevels()[6]) break;
                integerValues3[index4][0] = 99999;
                integerValues3[index4][1] = 99999;
                ItemStack[] itemStackArray4 = spellDefinition.getRuneCosts();
                value2 = itemStackArray4.length;
                value = 0;
                while (value < value2) {
                    int value7;
                    value6 = itemStackArray4[value];
                    if (!player.ownsItem(((ItemStack)value6).getId())) {
                        integerValues3[index4][0] = 0;
                    } else {
                        value7 = player.getOwnedItemAmount(((ItemStack)value6).getId()) / ((ItemStack)value6).getAmount();
                        if (value7 < integerValues3[index4][0]) {
                            integerValues3[index4][0] = value7;
                        }
                    }
                    if ((index4 == 0 && ((ItemStack)value6).getId() != 556 || index4 == 1 && ((ItemStack)value6).getId() != 555 || index4 == 2 && ((ItemStack)value6).getId() != 557 || index4 == 3 && ((ItemStack)value6).getId() != 554) && (value7 = player.getOwnedItemAmount(((ItemStack)value6).getId()) / ((ItemStack)value6).getAmount()) < integerValues3[index4][1]) {
                        integerValues3[index4][1] = value7;
                    }
                    ++value;
                }
                ++index4;
            }
            index4 = index2;
            while (index4 < ((ArrayList)value3).size()) {
                if (player.ownsItem(integerValues2[index4]) || player.ownsItemAmount(995, 2000)) {
                    if (integerValues3[index4][1] > index3) {
                        index3 = integerValues3[index4][1];
                        length = index4;
                    }
                } else if (integerValues3[index4][0] > index3) {
                    index3 = integerValues3[index4][0];
                    length = index4;
                }
                ++index4;
            }
            if (length != -1) {
                BotTaskDefinition botTaskDefinition4;
                if (!player.ownsItem(integerValues2[length]) && (botTaskDefinition4 = BotTaskPlanner.selectShopPurchaseTask(player, integerValues2[length], 1)) != null) {
                    BotTaskPlanner.resetBotTaskGoals(player);
                    value4 = player.currentBotTask;
                    Player player5 = player;
                    player.deferredBotTask = (BotTaskDefinition)value4;
                    value4 = botTaskDefinition4;
                    player5 = player;
                    player.currentBotTask = (BotTaskDefinition)value4;
                    return;
                }
                if (index3 < 250) {
                    SpellDefinition spellDefinition = (SpellDefinition)((Object)((ArrayList)value3).get(length));
                    ItemStack[] itemStackArray5 = spellDefinition.getRuneCosts();
                    value = itemStackArray5.length;
                    int index5 = 0;
                    while (index5 < value) {
                        BotTaskDefinition botTaskDefinition5;
                        int value8;
                        ItemStack itemStack4 = itemStackArray5[index5];
                        if (!(player.ownsItem(integerValues2[length]) && (length == 0 && itemStack4.getId() == 556 || length == 1 && itemStack4.getId() == 555 || length == 2 && itemStack4.getId() == 557 || length == 3 && itemStack4.getId() == 554) || (value8 = player.getOwnedItemAmount(itemStack4.getId()) / itemStack4.getAmount()) >= 250 || (botTaskDefinition5 = BotTaskPlanner.selectShopPurchaseTask(player, itemStack4.getId(), itemStack4.getAmount() * 250)) == null)) {
                            BotTaskPlanner.resetBotTaskGoals(player);
                            value4 = player.currentBotTask;
                            Player player6 = player;
                            player.deferredBotTask = (BotTaskDefinition)value4;
                            value4 = botTaskDefinition5;
                            player6 = player;
                            player.currentBotTask = (BotTaskDefinition)value4;
                            return;
                        }
                        ++index5;
                    }
                }
                if (index3 >= 250) {
                    index = 1;
                }
            }
        }
        int[][] integerValues4 = new int[3][14];
        int[][] integerValues5 = new int[3][14];
        Object value9 = ((ArrayList)value4).iterator();
        while (((Iterator)value9).hasNext()) {
            ItemStack itemStack5 = (ItemStack)((Iterator)value9).next();
            ItemDefinition itemDefinition = itemStack5.getDefinition();
            int equipmentSlot = itemDefinition.getEquipmentSlot();
            value = 0;
            while (value < 3) {
                prepareBotCombatLoadoutListsControlExit1: {
                    prepareBotCombatLoadoutListsControlExit2: {
                        prepareBotCombatLoadoutListsControlExit3: {
                            prepareBotCombatLoadoutListsControlExit4: {
                                prepareBotCombatLoadoutListsControlExit5: {
                                    prepareBotCombatLoadoutListsControlExit6: {
                                        prepareBotCombatLoadoutListsControlExit7: {
                                            value2 = 0;
                                            if (value != 0) break prepareBotCombatLoadoutListsControlExit6;
                                            if (equipmentSlot != 3) break prepareBotCombatLoadoutListsControlExit7;
                                            if (itemDefinition.isTwoHanded()) break prepareBotCombatLoadoutListsControlExit1;
                                            value2 = 0 + itemDefinition.getBonus(0);
                                            value2 += itemDefinition.getBonus(1);
                                            value2 += itemDefinition.getBonus(2);
                                            value2 += itemDefinition.getBonus(10);
                                            break prepareBotCombatLoadoutListsControlExit2;
                                        }
                                        value2 = 0 + itemDefinition.getBonus(5);
                                        value2 += itemDefinition.getBonus(6);
                                        value2 += itemDefinition.getBonus(7);
                                        break prepareBotCombatLoadoutListsControlExit2;
                                    }
                                    if (value != 1) break prepareBotCombatLoadoutListsControlExit4;
                                    if (itemDefinition.getBonus(3) < 0) break prepareBotCombatLoadoutListsControlExit1;
                                    if (equipmentSlot != 3) break prepareBotCombatLoadoutListsControlExit5;
                                    if (!itemDefinition.getName().toLowerCase().contains("staff")) break prepareBotCombatLoadoutListsControlExit1;
                                    value2 = itemDefinition.getBonus(3);
                                    if (length != -1 && itemStack5.getId() == integerValues2[length]) {
                                        value2 = 5000;
                                    }
                                    break prepareBotCombatLoadoutListsControlExit2;
                                }
                                value2 = 0 + itemDefinition.getBonus(3) * 3;
                                value2 += itemDefinition.getBonus(5);
                                value2 += itemDefinition.getBonus(6);
                                value2 += itemDefinition.getBonus(7);
                                break prepareBotCombatLoadoutListsControlExit2;
                            }
                            if (value != 2) break prepareBotCombatLoadoutListsControlExit2;
                            if (itemDefinition.getBonus(4) < 0 || equipmentSlot == 5 || equipmentSlot == 13) break prepareBotCombatLoadoutListsControlExit1;
                            if (equipmentSlot != 3) break prepareBotCombatLoadoutListsControlExit3;
                            if (!itemDefinition.getName().toLowerCase().endsWith("bow")) break prepareBotCombatLoadoutListsControlExit1;
                            value2 = itemDefinition.getBonus(4);
                            break prepareBotCombatLoadoutListsControlExit2;
                        }
                        value2 = 0 + itemDefinition.getBonus(4) * 3;
                        value2 += itemDefinition.getBonus(5);
                        value2 += itemDefinition.getBonus(6);
                        value2 += itemDefinition.getBonus(7);
                    }
                    if (integerValues5[value][equipmentSlot] < value2) {
                        integerValues4[value][equipmentSlot] = itemStack5.getId();
                        integerValues5[value][equipmentSlot] = value2;
                    }
                }
                ++value;
            }
        }
        boolean enabled4 = false;
        value9 = new ArrayList<Integer>();
        ((ArrayList)value9).add(0);
        if (enabled2) {
            ((ArrayList)value9).add(1);
        }
        if (index != 0) {
            ((ArrayList)value9).add(2);
        }
        if (player.botCombatLoadoutSlotCursor == -1) {
            player.botCombatStyle = (Integer)((ArrayList)value9).get(GameUtil.randomInt(((ArrayList)value9).size()));
            if (player.currentBotTask.getForcedCombatStyle() != -1) {
                if (player.currentBotTask.getForcedCombatStyle() == 0) {
                    player.botCombatStyle = 0;
                } else if (player.currentBotTask.getForcedCombatStyle() == 1) {
                    if (!enabled2) {
                        player.botTaskReturnToBankRequested = true;
                        player.currentBotTask.startWalkToBank(player);
                        return;
                    }
                    player.botCombatStyle = 1;
                } else if (player.currentBotTask.getForcedCombatStyle() == 2) {
                    if (index == 0) {
                        player.botTaskReturnToBankRequested = true;
                        player.currentBotTask.startWalkToBank(player);
                        return;
                    }
                    player.botCombatStyle = 2;
                }
            }
        }
        player.botElementalSpellIndex = -1;
        if (player.botCombatStyle == 1) {
            int value10 = 10000;
            int initialValue = -1;
            value = integerValues4[2][3];
            ItemStack itemStack6 = new ItemStack(value);
            for (ItemStack itemStack7 : arrayList) {
                if (!AmmunitionDefinition.isCompatible(player, itemStack6, itemStack7) || itemStack7.getDefinition().getValue() >= value10) continue;
                value10 = itemStack7.getDefinition().getValue();
                initialValue = itemStack7.getId();
            }
            player.addBotTaskRequiredItem(initialValue, 200);
        }
        if (player.botCombatStyle == 2 && length != -1) {
            SpellDefinition spellDefinition = (SpellDefinition)((Object)((ArrayList)value3).get(length));
            ItemStack[] itemStackArray6 = spellDefinition.getRuneCosts();
            int length2 = itemStackArray6.length;
            value = 0;
            while (value < length2) {
                ItemStack itemStack8 = itemStackArray6[value];
                if (!(player.ownsItem(integerValues2[length]) && (length == 0 && itemStack8.getId() == 556 || length == 1 && itemStack8.getId() == 555 || length == 2 && itemStack8.getId() == 557 || length == 3 && itemStack8.getId() == 554))) {
                    player.addBotTaskRequiredItem(itemStack8.getId(), itemStack8.getAmount() * 200);
                }
                ++value;
            }
            player.botElementalSpellIndex = length;
        }
        if (player.botCombatStyle == 2 && length == -1) {
            player.botCombatStyle = 0;
        }
        int selectedLoadoutIndex = 0;
        if (player.botCombatStyle == 2) {
            selectedLoadoutIndex = 1;
        } else if (player.botCombatStyle == 1) {
            selectedLoadoutIndex = 2;
        }
        if (!enabled5) {
            int selectedSlot = 0;
            while (selectedSlot < integerValues4[selectedLoadoutIndex].length) {
                int value11 = integerValues4[selectedLoadoutIndex][selectedSlot];
                if (value11 > 0) {
                    player.botCombatLoadoutItemIds.add(value11);
                }
                ++selectedSlot;
            }
        }
        while (loadoutCategoryIndex < 3) {
            int index6 = 0;
            while (index6 < integerValues4[loadoutCategoryIndex].length) {
                value = integerValues4[loadoutCategoryIndex][index6];
                if (value > 0) {
                    if (loadoutCategoryIndex == 0) {
                        player.botMeleeLoadoutItemIds.add(value);
                    } else if (loadoutCategoryIndex == 1) {
                        player.botMagicLoadoutItemIds.add(value);
                    } else if (loadoutCategoryIndex == 2) {
                        player.botRangedLoadoutItemIds.add(value);
                    }
                }
                ++index6;
            }
            ++loadoutCategoryIndex;
        }
    }
}
