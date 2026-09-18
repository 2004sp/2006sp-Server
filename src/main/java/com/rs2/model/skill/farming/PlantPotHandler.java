package com.rs2.model.skill.farming;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.skill.farming.BushPatch;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.skill.farming.FruitTreePatch;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.skill.farming.HerbPatch;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.skill.farming.SaplingDefinition;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.skill.farming.SpecialTreePatch;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.skill.farming.TreePatch;
import com.rs2.model.skill.farming.TreePatchManager;

public final class PlantPotHandler {
    private Player player;

    public PlantPotHandler(Player player) {
        this.player = player;
    }

    public final void finishInventorySeedlingGrowth(int value2) {
        SaplingDefinition saplingDefinition = SaplingDefinition.forWateredSeedlingId(value2);
        if (saplingDefinition == null) {
            return;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value2));
        this.player.getInventoryManager().addItem(new ItemStack(saplingDefinition.getSaplingId()));
    }

    public final void finishBankSeedlingGrowth(int value4, int value22, int value32) {
        SaplingDefinition saplingDefinition = SaplingDefinition.forWateredSeedlingId(value4);
        if (saplingDefinition == null) {
            return;
        }
        this.player.getBankContainer().removeFromTab(new ItemStack(value4), value22, value32);
        this.player.getBankContainer().addToTab(new ItemStack(saplingDefinition.getSaplingId()), value32);
    }

    public final boolean waterSeedling(int value3, int value22) {
        SaplingDefinition saplingDefinition = SaplingDefinition.forSeedlingId(value3);
        if (saplingDefinition == null) {
            saplingDefinition = SaplingDefinition.forSeedlingId(value22);
        }
        if (saplingDefinition == null || !new ItemStack(value3).getDefinition().getName().toLowerCase().contains("watering") && !new ItemStack(value22).getDefinition().getName().toLowerCase().contains("watering")) {
            return false;
        }
        if (value3 >= 5333 && value3 <= 5340 && this.player.getInventoryManager().removeItem(new ItemStack(value3))) {
            this.player.getInventoryManager().addItem(new ItemStack(value3 == 5333 ? value3 - 2 : value3 - 1));
        }
        if (value22 >= 5333 && value22 <= 5340 && this.player.getInventoryManager().removeItem(new ItemStack(value22))) {
            this.player.getInventoryManager().addItem(new ItemStack(value3 == 5333 ? value3 - 2 : value3 - 1));
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You water the " + new ItemStack(saplingDefinition.getSeedId()).getDefinition().getName().toLowerCase() + ".");
        this.player.getInventoryManager().removeItem(new ItemStack(saplingDefinition.getSeedlingId()));
        this.player.getInventoryManager().addItem(new ItemStack(saplingDefinition.getWateredSeedlingId()));
        return true;
    }

    public final boolean plantSeedInPot(int value5, int value22, int value32, int value42) {
        SaplingDefinition saplingDefinition = SaplingDefinition.forSeedId(value5);
        if (saplingDefinition == null) {
            saplingDefinition = SaplingDefinition.forSeedlingId(value22);
        }
        if (saplingDefinition == null || value5 != 5354 && value22 != 5354) {
            return false;
        }
        if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(saplingDefinition.getSeedId()), value5 == 5354 ? value32 : value42)) {
            this.player.getInventoryManager().setItemInSlot(new ItemStack(saplingDefinition.getSeedlingId()), value5 == 5354 ? value32 : value42);
        } else if (this.player.getInventoryManager().removeItem(new ItemStack(saplingDefinition.getSeedId()))) {
            this.player.getInventoryManager().addItem(new ItemStack(saplingDefinition.getSeedlingId()));
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You sow some maple tree seeds in the plantpots.");
        player = this.player;
        player.packetSender.sendGameMessage("They need watering before they will grow.");
        return true;
    }

    public final boolean fillPlantPotWithSoil(int value14, int value22, int value32) {
        if (value14 != 5350) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        int value4 = value32;
        int value5 = value22;
        Object allotmentPatchManager = this.player.getAllotmentPatchManager();
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(new Position(value5, value4));
        if (!(allotmentPatch != null && ((AllotmentPatchManager)allotmentPatchManager).growthStages[allotmentPatch.getIndex()] == 3)) {
            value4 = value32;
            int value6 = value22;
            allotmentPatchManager = this.player.getBushPatchManager();
            BushPatch bushPatch = BushPatch.forPosition(new Position(value6, value4));
            if (!(bushPatch != null && ((BushPatchManager)allotmentPatchManager).growthStages[bushPatch.getIndex()] == 3)) {
                value4 = value32;
                int value7 = value22;
                allotmentPatchManager = this.player.getFlowerPatchManager();
                FlowerPatch flowerPatch = FlowerPatch.forPosition(new Position(value7, value4));
                if (!(flowerPatch != null && ((FlowerPatchManager)allotmentPatchManager).growthStages[flowerPatch.getIndex()] == 3)) {
                    value4 = value32;
                    int value8 = value22;
                    allotmentPatchManager = this.player.getFruitTreePatchManager();
                    FruitTreePatch fruitTreePatch = FruitTreePatch.forPosition(new Position(value8, value4));
                    if (!(fruitTreePatch != null && ((FruitTreePatchManager)allotmentPatchManager).growthStages[fruitTreePatch.getIndex()] == 3)) {
                        value4 = value32;
                        int value9 = value22;
                        allotmentPatchManager = this.player.getHerbPatchManager();
                        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value9, value4));
                        if (!(herbPatch != null && ((HerbPatchManager)allotmentPatchManager).growthStages[herbPatch.getIndex()] == 3)) {
                            value4 = value32;
                            int value10 = value22;
                            allotmentPatchManager = this.player.getHopsPatchManager();
                            HopsPatch hopsPatch = HopsPatch.forPosition(new Position(value10, value4));
                            if (!(hopsPatch != null && ((HopsPatchManager)allotmentPatchManager).growthStages[hopsPatch.getIndex()] == 3)) {
                                value4 = value32;
                                int value11 = value22;
                                allotmentPatchManager = this.player.getTreePatchManager();
                                TreePatch treePatch = TreePatch.forPosition(new Position(value11, value4));
                                if (!(treePatch != null && ((TreePatchManager)allotmentPatchManager).growthStages[treePatch.getIndex()] == 3)) {
                                    value4 = value32;
                                    int value12 = value22;
                                    allotmentPatchManager = this.player.getSpecialTreePatchManager();
                                    SpecialTreePatch specialTreePatch = SpecialTreePatch.forPosition(new Position(value12, value4));
                                    if (!(specialTreePatch != null && ((SpecialTreePatchManager)allotmentPatchManager).growthStages[specialTreePatch.getIndex()] == 3)) {
                                        value4 = value32;
                                        int value13 = value22;
                                        allotmentPatchManager = this.player.getSpecialCropPatchManager();
                                        SpecialCropPatch specialCropPatch = SpecialCropPatch.forPosition(new Position(value13, value4));
                                        if (!(specialCropPatch != null && ((SpecialCropPatchManager)allotmentPatchManager).growthStages[specialCropPatch.getIndex()] == 3)) {
                                            Player player = this.player;
                                            player.packetSender.sendGameMessage("You can only fill your pot on raked patches.");
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5325)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You need a gardening trowel to fill this pot with soil.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value14));
        this.player.getUpdateState().setAnimation(2287);
        Player player = this.player;
        player.packetSender.sendGameMessage("You fill the empty plant pot with soil.");
        this.player.getInventoryManager().addItem(new ItemStack(5354));
        return true;
    }
}

