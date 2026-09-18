package com.rs2.model.skill;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolComparator;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationRecipe;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;
import java.util.Collections;

public class ItemCombinationHandler {
    private Player player;
    private RectangularArea caveLightShortcutArea = new RectangularArea(2638, 9736, 2655, 9745, 0);

    public ItemCombinationHandler(Player player) {
        this.player = player;
    }

    public final boolean handleItemCombination(ItemStack firstItem, ItemStack secondItem) {
        ItemCombinationRecipe recipe = ItemCombinationRecipe.forItemIds(firstItem.getId(), secondItem.getId());
        if (recipe == null) {
            return false;
        }
        int[] skillRequirement = ItemCombinationRecipe.getSkillRequirement(recipe);
        if (skillRequirement != null && this.player.getSkillManager().getCurrentLevels()[skillRequirement[0]] < skillRequirement[1]) {
            this.player.packetSender.sendGameMessage("Your " + SkillManager.SKILL_NAMES[skillRequirement[0]].toLowerCase() + " level is not high enough to do this.");
            return true;
        }
        ItemStack[] requiredItems = ItemCombinationRecipe.getRequiredItems(recipe);
        if (requiredItems != null) {
            int index = 0;
            while (index < requiredItems.length) {
                ItemStack itemStack = requiredItems[index];
                if (this.player.getInventoryManager().getItemAmount(itemStack.getId()) < itemStack.getAmount()) {
                    return true;
                }
                ++index;
            }
            index = 0;
            while (index < requiredItems.length) {
                this.player.getInventoryManager().removeItem(requiredItems[index]);
                ++index;
            }
        }
        if (ItemCombinationRecipe.getMessage(recipe) != null) {
            this.player.packetSender.sendGameMessage(ItemCombinationRecipe.getMessage(recipe));
        }
        ItemStack[] productItems = ItemCombinationRecipe.getProductItems(recipe);
        if (productItems != null) {
            if (productItems.length == 2 && requiredItems != null && requiredItems.length == 2) {
                this.player.getInventoryManager().addItem(productItems[0]);
                this.player.getInventoryManager().addItem(productItems[1]);
            } else {
                int index2 = 0;
                while (index2 < productItems.length) {
                    this.player.getInventoryManager().addItem(productItems[index2]);
                    ++index2;
                }
                if (this.player.getActiveCaveLightLevel() > 0 && this.caveLightShortcutArea.containsExclusive(this.player.getPosition())) {
                    this.player.packetSender.sendGameMessage("The light lets you see further into the room.");
                    this.player.moveTo(new Position(this.player.getPosition().getX(), this.player.getPosition().getY() + 23, 0));
                }
            }
        }
        if (ItemCombinationRecipe.getAnimationId(recipe) > 0) {
            this.player.getUpdateState().setAnimation(ItemCombinationRecipe.getAnimationId(recipe));
        }
        if (skillRequirement != null) {
            this.player.getSkillManager().addExperience(skillRequirement[0], ItemCombinationRecipe.getExperience(recipe));
        }
        return true;
    }

    public static boolean isGatheringToolItemId(int itemId) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[index];
            if (gatheringToolDefinition.getToolItemId() == itemId) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static GatheringToolDefinition findUsableGatheringTool(Player player, int value2) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[index];
            if (gatheringToolDefinition.getSkillId() == value2 && player.getSkillManager().getCurrentLevels()[value2] >= gatheringToolDefinition.getRequiredLevel() && (player.getEquipmentManager().getItemIdAtSlot(3) == gatheringToolDefinition.getToolItemId() || player.getInventoryManager().containsItem(gatheringToolDefinition.getToolItemId()))) {
                return gatheringToolDefinition;
            }
            ++index;
        }
        return null;
    }

    public static GatheringToolDefinition findOwnedGatheringTool(Player player, int value2) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[index];
            if (gatheringToolDefinition.getSkillId() == value2 && player.getSkillManager().getCurrentLevels()[value2] >= gatheringToolDefinition.getRequiredLevel() && player.ownsItem(gatheringToolDefinition.getToolItemId())) {
                return gatheringToolDefinition;
            }
            ++index;
        }
        return null;
    }

    public static ArrayList getGatheringToolsForSkill(int skillId) {
        ArrayList<GatheringToolDefinition> arrayList = new ArrayList<GatheringToolDefinition>();
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[index];
            if (gatheringToolDefinition.getSkillId() == skillId) {
                arrayList.add(gatheringToolDefinition);
            }
            ++index;
        }
        Collections.sort(arrayList, new GatheringToolComparator(skillId));
        return arrayList;
    }

    public static GatheringToolDefinition getOwnedOrFallbackGatheringTool(Player player, int value2) {
        GatheringToolDefinition ownedTool = ItemCombinationHandler.findOwnedGatheringTool(player, value2);
        if (ownedTool != null) {
            return ownedTool;
        }
        ArrayList tools = ItemCombinationHandler.getGatheringToolsForSkill(value2);
        return (GatheringToolDefinition)tools.get(0);
    }

    public static GatheringToolDefinition forBrokenToolItemId(int itemId) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[index];
            if (gatheringToolDefinition.getBrokenToolItemId() == itemId) {
                return gatheringToolDefinition;
            }
            ++index;
        }
        return null;
    }

    public static boolean handleToolHeadAttachment(Player player, int value3, int value22) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            handleToolHeadAttachmentControlExit1: {
                GatheringToolDefinition gatheringToolDefinition;
                handleToolHeadAttachmentControlExit2: {
                    gatheringToolDefinition = gatheringToolDefinitionArray[index];
                    if (gatheringToolDefinition.getToolHeadItemId() != value3 && gatheringToolDefinition.getToolHeadItemId() != value22) break handleToolHeadAttachmentControlExit1;
                    GatheringToolDefinition gatheringToolDefinition2 = gatheringToolDefinition;
                    if (value3 == 0) break handleToolHeadAttachmentControlExit2;
                    gatheringToolDefinition2 = gatheringToolDefinition;
                    if (value22 != 0) break handleToolHeadAttachmentControlExit1;
                }
                player.getInventoryManager().removeItem(new ItemStack(value3, 1));
                player.getInventoryManager().removeItem(new ItemStack(value22, 1));
                player.getInventoryManager().addItem(new ItemStack(gatheringToolDefinition.getToolItemId(), 1));
                return true;
            }
            ++index;
        }
        return false;
    }

    public static void breakGatheringTool(Player player, int value2) {
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(player, value2);
        if (player.getEquipmentManager().getItemIdAtSlot(3) == gatheringToolDefinition.getToolItemId()) {
            player.getEquipmentManager().replaceSlotItem(gatheringToolDefinition.getBrokenToolItemId(), 3);
            return;
        }
        if (player.getInventoryManager().containsItemAmount(gatheringToolDefinition.getToolItemId(), 1)) {
            player.getInventoryManager().removeItem(new ItemStack(gatheringToolDefinition.getToolItemId(), 1));
            player.getInventoryManager().addItem(new ItemStack(gatheringToolDefinition.getBrokenToolItemId(), 1));
        }
    }

    public static boolean repairBrokenGatheringTool(Player player, int value2) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int length = gatheringToolDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[index];
            if (gatheringToolDefinition.getBrokenToolItemId() == value2) {
                if (!player.getInventoryManager().containsItemStack(new ItemStack(995, gatheringToolDefinition.getRepairCostCoins()))) {
                    player.packetSender.sendGameMessage("You don't have enough coins to fix that.");
                    return false;
                }
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                player.getInventoryManager().removeItem(new ItemStack(995, gatheringToolDefinition.getRepairCostCoins()));
                player.getInventoryManager().addItem(new ItemStack(gatheringToolDefinition.getToolItemId(), 1));
                return true;
            }
            ++index;
        }
        return false;
    }
}

