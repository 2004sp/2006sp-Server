package com.rs2;

import com.rs2.Server;
import com.rs2.model.World;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import com.rs2.model.task.TickTask;

public final class MinuteMaintenanceTickTask
extends TickTask {
    public MinuteMaintenanceTickTask(Server server, int intervalTicks) {
        super(intervalTicks);
    }

    @Override
    public final void execute() {
        Server.setElapsedMinutes(Server.getElapsedMinutes() + 1L);
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Object value = playerArray[index];
            if (value != null) {
                int value2;
                ((Player)value).getCompostBinManager().processRotting();
                ((Player)value).getAllotmentPatchManager().processGrowth();
                ((Player)value).getFlowerPatchManager().processGrowth();
                ((Player)value).getHerbPatchManager().processGrowth();
                ((Player)value).getHopsPatchManager().processGrowth();
                ((Player)value).getBushPatchManager().processGrowth();
                ((Player)value).getTreePatchManager().processGrowth();
                ((Player)value).getFruitTreePatchManager().processGrowth();
                ((Player)value).getSpecialTreePatchManager().processGrowth();
                ((Player)value).getSpecialCropPatchManager().processGrowth();
                Player player = (Player)value;
                value = ItemService.getInstance();
                int index2 = 0;
                while (index2 < 28) {
                    ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(index2);
                    if (itemStack != null && itemStack.getMetadata() >= 0 && itemStack.getDefinition().getEquipmentSlot() == -1 && !ItemService.isEssencePouch(itemStack.getId())) {
                        itemStack.setMetadata(itemStack.getMetadata() - 1);
                        if (itemStack.getMetadata() == 0) {
                            int[] integerValues = FarmingPatchUtils.wateredSeedlingItemIds;
                            value2 = 0;
                            while (value2 < 14) {
                                int value3 = integerValues[value2];
                                if (value3 == itemStack.getId()) {
                                    player.getPlantPotHandler().finishInventorySeedlingGrowth(itemStack.getId());
                                }
                                ++value2;
                            }
                            if (itemStack.getId() == 1995) {
                                player.getWineFermentationHandler().finishInventoryWineFermentation(index2);
                            }
                        }
                    }
                    ++index2;
                }
                index2 = 0;
                while (index2 < player.getBankContainer().getTabCount()) {
                    int index3 = 0;
                    while (index3 < 288) {
                        ItemStack itemStack = player.getBankContainer().getItemAtTabSlot(index3, index2);
                        if (itemStack != null && itemStack.getMetadata() >= 0 && itemStack.getDefinition().getEquipmentSlot() == -1 && !ItemService.isEssencePouch(itemStack.getId())) {
                            itemStack.setMetadata(itemStack.getMetadata() - 1);
                            if (itemStack.getMetadata() == 0) {
                                int[] integerValues2 = FarmingPatchUtils.wateredSeedlingItemIds;
                                int index4 = 0;
                                while (index4 < 14) {
                                    value2 = integerValues2[index4];
                                    if (value2 == itemStack.getId()) {
                                        player.getPlantPotHandler().finishBankSeedlingGrowth(itemStack.getId(), index3, index2);
                                    }
                                    ++index4;
                                }
                                if (itemStack.getId() == 1995) {
                                    player.getWineFermentationHandler().finishBankWineFermentation(index3);
                                }
                            }
                        }
                        ++index3;
                    }
                    ++index2;
                }
            }
            ++index;
        }
    }
}

