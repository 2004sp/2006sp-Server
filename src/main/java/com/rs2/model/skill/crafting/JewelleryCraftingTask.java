package com.rs2.model.skill.crafting;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.JewelleryCraftingHandler;
import com.rs2.model.skill.crafting.JewelleryDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class JewelleryCraftingTask
extends CycleEvent {
    private int materialItemId;
    private int remainingActions;
    private int jewelleryType;
    private final Player player;
    private final int actionSequence;

    public JewelleryCraftingTask(int materialItemId, int remainingActions, int jewelleryType, Player player, int actionSequence) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.materialItemId = materialItemId;
        this.remainingActions = remainingActions;
        this.jewelleryType = jewelleryType;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions-- <= 0) {
            cycleEventContainer.stop();
            return;
        }
        if (JewelleryCraftingHandler.forMaterialItemId(this.materialItemId) == null || !this.player.getInventoryManager().getContainer().containsItem(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0]) || !this.player.getInventoryManager().getContainer().containsItem(this.player.getSelectedSkillItemId())) {
            cycleEventContainer.stop();
            return;
        }
        cycleEventContainer.setTickDelay(4);
        this.player.getUpdateState().setAnimation(899);
        Player player = this.player;
        player.packetSender.sendSoundEffect(469, 1, 0);
        switch (this.jewelleryType) {
            case 0: {
                if (this.player.getSkillManager().getCurrentLevels()[12] < JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[3]) {
                    this.player.getDialogueManager().showOneLineStatement("You need a crafting level of " + JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[3] + " to craft this.");
                    cycleEventContainer.stop();
                    return;
                }
                if (JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0] != this.player.getSelectedSkillItemId()) {
                    this.player.getInventoryManager().removeItem(new ItemStack(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0], 1));
                }
                this.player.getInventoryManager().removeItem(new ItemStack(this.player.getSelectedSkillItemId(), 1));
                int recipeData = JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[5];
                if (this.player.getSelectedSkillItemId() == 2365 && recipeData == 1641) {
                    recipeData = 773;
                }
                this.player.getInventoryManager().addItem(new ItemStack(recipeData, 1));
                player = this.player;
                player.packetSender.sendGameMessage("You craft a ring.");
                this.player.getSkillManager().addExperience(12, JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[4]);
                return;
            }
            case 1: {
                if (this.player.getSkillManager().getCurrentLevels()[12] < JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[6]) {
                    this.player.getDialogueManager().showOneLineStatement("You need a Crafting level of " + JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[6] + " to craft this.");
                    cycleEventContainer.stop();
                    return;
                }
                if (JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0] != this.player.getSelectedSkillItemId()) {
                    this.player.getInventoryManager().removeItem(new ItemStack(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0], 1));
                }
                this.player.getInventoryManager().removeItem(new ItemStack(this.player.getSelectedSkillItemId(), 1));
                int recipeData2 = JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[8];
                if (this.player.getSelectedSkillItemId() == 2365 && recipeData2 == 1660) {
                    recipeData2 = 774;
                }
                this.player.getInventoryManager().addItem(new ItemStack(recipeData2, 1));
                player = this.player;
                player.packetSender.sendGameMessage("You craft a necklace.");
                this.player.getSkillManager().addExperience(12, JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[7]);
                return;
            }
            case 2: {
                if (this.player.getSkillManager().getCurrentLevels()[12] < JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[9]) {
                    this.player.getDialogueManager().showOneLineStatement("You need a Crafting level of " + JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[9] + " to craft this.");
                    cycleEventContainer.stop();
                    return;
                }
                if (JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0] != this.player.getSelectedSkillItemId()) {
                    this.player.getInventoryManager().removeItem(new ItemStack(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[0], 1));
                }
                this.player.getInventoryManager().removeItem(new ItemStack(this.player.getSelectedSkillItemId(), 1));
                if (this.player.getInventoryManager().getContainer().containsItem(1759)) {
                    this.player.getInventoryManager().removeItem(new ItemStack(1759, 1));
                    this.player.getInventoryManager().addItem(new ItemStack(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[12], 1));
                    player = this.player;
                    player.packetSender.sendGameMessage("You craft an amulet and attach a string to it.");
                } else {
                    this.player.getInventoryManager().addItem(new ItemStack(JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[11], 1));
                    player = this.player;
                    player.packetSender.sendGameMessage("You craft an amulet.");
                }
                this.player.getSkillManager().addExperience(12, JewelleryDefinition.getRecipeData(JewelleryCraftingHandler.forMaterialItemId(this.materialItemId))[10]);
                return;
            }
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

