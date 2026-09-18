package com.rs2.model.item.action;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;

public final class BarrowsRepairHandler {
    private static int[] repairNpcIds = new int[]{519};
    private int repairedItemId;
    private int baseDegradedItemId;
    private static BarrowsRepairHandler[][] repairDefinitions = new BarrowsRepairHandler[][]{{new BarrowsRepairHandler(4708, 4856), new BarrowsRepairHandler(4710, 4862), new BarrowsRepairHandler(4712, 4868), new BarrowsRepairHandler(4714, 4874)}, {new BarrowsRepairHandler(4716, 4880), new BarrowsRepairHandler(4718, 4886), new BarrowsRepairHandler(4720, 4892), new BarrowsRepairHandler(4722, 4898)}, {new BarrowsRepairHandler(4724, 4904), new BarrowsRepairHandler(4726, 4910), new BarrowsRepairHandler(4728, 4916), new BarrowsRepairHandler(4730, 4922)}, {new BarrowsRepairHandler(4732, 4928), new BarrowsRepairHandler(4734, 4934), new BarrowsRepairHandler(4736, 4940), new BarrowsRepairHandler(4738, 4946)}, {new BarrowsRepairHandler(4745, 4952), new BarrowsRepairHandler(4747, 4958), new BarrowsRepairHandler(4749, 4964), new BarrowsRepairHandler(4751, 4970)}, {new BarrowsRepairHandler(4753, 4976), new BarrowsRepairHandler(4755, 4982), new BarrowsRepairHandler(4757, 4988), new BarrowsRepairHandler(4759, 4994)}};

    public static boolean handleItemOnNpc(Player player, int npcId, ItemStack itemStack) {
        int initialValue = -1;
        int[] integerValues = repairNpcIds;
        int length = repairNpcIds.length;
        int index = 0;
        while (index < length) {
            int value = integerValues[index];
            if (npcId == value) {
                initialValue = value;
                break;
            }
            ++index;
        }
        if (initialValue != -1 && BarrowsRepairHandler.calculateRepairCost(itemStack) != -1) {
            player.pendingDialogueItem = itemStack;
            player.sharedActionValue = initialValue;
            DialogueManager.startDialogue(player, 10089);
            return true;
        }
        return false;
    }

    public static boolean repairItem(Player player, ItemStack itemStack) {
        int repairCost = BarrowsRepairHandler.calculateRepairCost(itemStack);
        if (!player.getInventoryManager().containsItemStack(new ItemStack(995, repairCost)) && repairCost > 0) {
            player.packetSender.sendGameMessage("You don't have enough coins to fix that.");
            return false;
        }
        player.getInventoryManager().removeItem(new ItemStack(995, repairCost));
        player.getInventoryManager().removeItemFromSlot(itemStack, player.getSelectedItemSlot());
        InventoryManager inventoryManager = player.getInventoryManager();
        BarrowsRepairHandler repairDefinition = BarrowsRepairHandler.forItem(itemStack);
        inventoryManager.addItem(new ItemStack(repairDefinition == null ? -1 : repairDefinition.repairedItemId, 1));
        return true;
    }

    private BarrowsRepairHandler(int repairedItemId, int baseDegradedItemId) {
        this.repairedItemId = repairedItemId;
        this.baseDegradedItemId = baseDegradedItemId;
    }

    public final int getFullyDegradedItemId() {
        return this.baseDegradedItemId + 4;
    }

    public static int calculateRepairCost(ItemStack itemStack) {
        int definition = itemStack.getDefinition().getEquipmentSlot();
        BarrowsRepairHandler barrowsRepairHandler = BarrowsRepairHandler.forItem(itemStack);
        if (barrowsRepairHandler == null) {
            return -1;
        }
        int id = itemStack.getId() - barrowsRepairHandler.baseDegradedItemId;
        double metadata = itemStack.getMetadata() < 0 ? 0 : 4500 - itemStack.getMetadata();
        double value = 1.0;
        double value2 = metadata / 4500.0;
        if (definition == 0) {
            value = 15000.0;
        } else if (definition == 3) {
            value = 25000.0;
        } else if (definition == 4) {
            value = 22500.0;
        } else if (definition == 7) {
            value = 20000.0;
        }
        return (int)(value * (double)id + value * value2);
    }

    public static BarrowsRepairHandler forItem(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        int id = itemStack.getId();
        int definition = itemStack.getDefinition().getEquipmentSlot();
        if (definition == 0) {
            return BarrowsRepairHandler.findByDegradedItemIdAndPartIndex(id, 0);
        }
        if (definition == 3) {
            return BarrowsRepairHandler.findByDegradedItemIdAndPartIndex(id, 1);
        }
        if (definition == 4) {
            return BarrowsRepairHandler.findByDegradedItemIdAndPartIndex(id, 2);
        }
        if (definition == 7) {
            return BarrowsRepairHandler.findByDegradedItemIdAndPartIndex(id, 3);
        }
        return null;
    }

    private static BarrowsRepairHandler findByDegradedItemIdAndPartIndex(int itemId, int value2) {
        int index = 0;
        while (index < repairDefinitions.length) {
            BarrowsRepairHandler barrowsRepairHandler = repairDefinitions[index][value2];
            if (itemId >= barrowsRepairHandler.baseDegradedItemId) {
                barrowsRepairHandler = repairDefinitions[index][value2];
                if (itemId <= barrowsRepairHandler.baseDegradedItemId + 4) {
                    return repairDefinitions[index][value2];
                }
            }
            ++index;
        }
        return null;
    }
}

