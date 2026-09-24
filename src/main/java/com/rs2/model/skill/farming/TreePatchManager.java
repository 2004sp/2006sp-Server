package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.farming.FarmedTreeCuttingTask;
import com.rs2.model.skill.farming.FarmedTreeDefinition;
import com.rs2.model.skill.farming.FarmedTreeGrowthDefinition;
import com.rs2.model.skill.farming.TreeClearingTask;
import com.rs2.model.skill.farming.TreeCompostTask;
import com.rs2.model.skill.farming.TreeHealthCheckTask;
import com.rs2.model.skill.farming.TreeInspectTask;
import com.rs2.model.skill.farming.TreePatch;
import com.rs2.model.skill.farming.TreePlantingTask;
import com.rs2.model.skill.farming.TreePruneTask;
import com.rs2.model.skill.farming.TreeStumpRegrowthTask;
import com.rs2.model.skill.woodcutting.TreeDefinition;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class TreePatchManager {
    private Player player;
    public int[] growthStages = new int[4];
    public int[] treeIds = new int[4];
    public int[] patchData = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};
    private boolean[] fullyGrownFlags = new boolean[4];
    public boolean[] protectionFlags = new boolean[4];

    public TreePatchManager(Player player) {
        this.player = player;
    }

    public final void refreshConfig() {
        int[] integerValues = new int[this.growthStages.length];
        int index = 0;
        while (index < this.growthStages.length) {
            int value;
            int value2 = this.patchStates[index];
            int value3 = this.treeIds[index];
            int value4 = this.growthStages[index];
            Object value5 = this;
            value5 = FarmedTreeDefinition.forSaplingId(value3);
            switch (value4) {
                case 0: {
                    value = 0;
                    break;
                }
                case 1: {
                    value = 1;
                    break;
                }
                case 2: {
                    value = 2;
                    break;
                }
                case 3: {
                    value = 3;
                    break;
                }
                default: {
                    int value6;
                    if (value5 == null) {
                        value = -1;
                        break;
                    }
                    if (value2 == 6) {
                        value = ((FarmedTreeDefinition)((Object)value5)).getCheckedTreeConfigStage();
                        break;
                    }
                    if (value2 == 7) {
                        value = ((FarmedTreeDefinition)((Object)value5)).getStumpConfigStage();
                        break;
                    }
                    value4 = ((FarmedTreeDefinition)((Object)value5)).getConfigStageOffset(value4 - 4);
                    value3 = value2;
                    switch (value3) {
                        case 0: {
                            value6 = 0;
                            break;
                        }
                        case 1: {
                            value6 = 1;
                            break;
                        }
                        case 2: {
                            value6 = 2;
                            break;
                        }
                        default: {
                            value6 = -1;
                        }
                    }
                    value = (value6 << 6) + ((FarmedTreeDefinition)((Object)value5)).getConfigStartStage() + value4;
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value7 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(502, value7);
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.treeIds.length) {
            processGrowthControlExit1: {
                long value;
                processGrowthControlExit2: {
                    value = Server.getElapsedMinutes() - this.lastUpdateTicks[index];
                    if (value < 5L) break processGrowthControlExit1;
                    if (this.growthStages[index] <= 0 || this.growthStages[index] > 3) break processGrowthControlExit2;
                    int value2 = (int)(value / 5L);
                    int index2 = 0;
                    while (index2 < value2) {
                        if (this.growthStages[index] != 0) {
                            int value3 = index;
                            this.growthStages[value3] = this.growthStages[value3] - 1;
                            this.lastUpdateTicks[index] = Server.getElapsedMinutes();
                            ++index2;
                            continue;
                        }
                        break processGrowthControlExit1;
                    }
                    break processGrowthControlExit1;
                }
                Object value4 = FarmedTreeDefinition.forSaplingId(this.treeIds[index]);
                if (value4 == null || this.shouldStopGrowthCycle(index)) break processGrowthControlExit1;
                int value5 = this.growthStages[index] - 4;
                int growthCycleTicks = FarmingPatchUtils.getGrowthCycleTarget(this.player, this.lastUpdateTicks[index], ((FarmedTreeDefinition)value4).getGrowthCycleTicks(), value5);
                if ((growthCycleTicks -= value5) <= 0) break processGrowthControlExit1;
                value5 = 0;
                while (value5 < growthCycleTicks) {
                    processGrowthControlExit3: {
                        processGrowthControlExit4: {
                            FarmedTreeDefinition farmedTreeDefinition;
                            int value6;
                            processGrowthControlExit5: {
                                processGrowthControlExit6: {
                                    processGrowthControlExit7: {
                                        if (this.growthStages[index] != 4) break processGrowthControlExit7;
                                        int value7 = index;
                                        this.growthStages[value7] = this.growthStages[value7] + 1;
                                        break processGrowthControlExit3;
                                    }
                                    value6 = index;
                                    value4 = this;
                                    if (((TreePatchManager)value4).patchStates[value6] != 1) break processGrowthControlExit5;
                                    if (!((TreePatchManager)value4).protectionFlags[value6]) break processGrowthControlExit6;
                                    ((TreePatchManager)value4).patchStates[value6] = 0;
                                    farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(((TreePatchManager)value4).treeIds[value6]);
                                    if (farmedTreeDefinition == null) break processGrowthControlExit4;
                                    int value8 = value6;
                                    ((TreePatchManager)value4).lastUpdateTicks[value8] = ((TreePatchManager)value4).lastUpdateTicks[value8] + (long)farmedTreeDefinition.getGrowthCycleTicks();
                                    break processGrowthControlExit5;
                                }
                                if (GameUtil.randomInt(2) == 0) {
                                    ((TreePatchManager)value4).patchStates[value6] = 2;
                                }
                            }
                            if (((TreePatchManager)value4).patchStates[value6] != 1 && ((TreePatchManager)value4).patchStates[value6] != 2) {
                                if (((TreePatchManager)value4).patchStates[value6] == 5 && ((TreePatchManager)value4).growthStages[value6] != 3) {
                                    ((TreePatchManager)value4).patchStates[value6] = 0;
                                }
                                if (((TreePatchManager)value4).patchStates[value6] == 0 && ((TreePatchManager)value4).growthStages[value6] >= 4 && !((TreePatchManager)value4).fullyGrownFlags[value6] && (farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(((TreePatchManager)value4).treeIds[value6])) != null) {
                                    double diseaseChance = ((TreePatchManager)value4).diseaseChanceMultipliers[value6] * farmedTreeDefinition.getDiseaseChance();
                                    double value9 = diseaseChance * 100.0;
                                    int value10 = (int)value9;
                                    if (GameUtil.randomInclusive(100) <= value10 && ServerSettings.diseasingEnabled) {
                                        ((TreePatchManager)value4).patchStates[value6] = 1;
                                    }
                                }
                            }
                        }
                        if (this.patchStates[index] == 2) break processGrowthControlExit1;
                        if (this.patchStates[index] != 1) {
                            int value11 = index;
                            this.growthStages[value11] = this.growthStages[value11] + 1;
                        }
                        if (this.shouldStopGrowthCycle(index)) break processGrowthControlExit1;
                    }
                    ++value5;
                }
            }
            ++index;
        }
        this.refreshConfig();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean shouldStopGrowthCycle(int value4) {
        if (this.lastUpdateTicks[value4] == 0L) return true;
        if (this.patchStates[value4] == 2) return true;
        int value2 = value4;
        TreePatchManager treePatchManager = this;
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(treePatchManager.treeIds[value2]);
        if (farmedTreeDefinition == null) return false;
        int value3 = treePatchManager.growthStages[value2] - 4;
        if (value3 < farmedTreeDefinition.getGrowthCycleCount()) return false;
        treePatchManager.fullyGrownFlags[value2] = true;
        return true;
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        TreePatch treePatch = TreePatch.forPosition(new Position(value5, value22));
        if (treePatch == null || value32 != 5341 && value32 != 952) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!this.player.isMember()) {
            this.player.packetSender.sendGameMessage("You need a members account to access members content.");
            return true;
        }
        if (ServerSettings.freeToPlayWorld) {
            this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return true;
        }
        if (this.growthStages[treePatch.getIndex()] == 3) {
            return true;
        }
        if (this.growthStages[treePatch.getIndex()] <= 3) {
            if (!this.player.getInventoryManager().getContainer().containsItem(5341)) {
                this.player.getDialogueManager().showOneLineStatement("You need a rake to clear this path.");
                return true;
            }
            value32 = 2273;
            value22 = 1323;
            value4 = 5;
        } else {
            if (!this.player.getInventoryManager().getContainer().containsItem(952)) {
                this.player.getDialogueManager().showOneLineStatement("You need a spade to clear this path.");
                return true;
            }
            value32 = 830;
            value22 = 232;
            value4 = 3;
        }
        this.player.setActionLocked(true);
        Player player = this.player;
        player.packetSender.sendSoundEffect(value22, 1, 0);
        this.player.getUpdateState().setAnimation(value32);
        CycleEventHandler.getInstance().schedule(this.player, new TreeClearingTask(this, value32, treePatch), value4);
        return true;
    }

    public final boolean plantSapling(int value5, int value22, int value32) {
        Object value4 = TreePatch.forPosition(new Position(value5, value22));
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(value32);
        if (value4 == null || farmedTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[((TreePatch)((Object)value4)).getIndex()] != 3) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("You can't plant a sapling here.");
            return true;
        }
        if (farmedTreeDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + farmedTreeDefinition.getRequiredLevel() + " to plant this sapling.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5325)) {
            this.player.getDialogueManager().showOneLineStatement("You need a trowel to plant the sapling here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2272);
        this.growthStages[((TreePatch)((Object)value4)).getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new TreePlantingTask(this, (TreePatch)((Object)value4), value32, farmedTreeDefinition), 3);
        return true;
    }

    public final boolean checkHealth(int value5, int value22) {
        Object value3 = TreePatch.forPosition(new Position(value5, value22));
        if (value3 == null) {
            return false;
        }
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(this.treeIds[((TreePatch)value3).getIndex()]);
        if (farmedTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((TreePatch)value3).getIndex()] != 0) {
            return false;
        }
        this.player.getUpdateState().setAnimation(832);
        int value4 = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new TreeHealthCheckTask(this, value4, farmedTreeDefinition, (TreePatch)((Object)value3)));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final void scheduleStumpRegrowth(int value2) {
        CycleEventHandler.getInstance().schedule(this.player, new TreeStumpRegrowthTask(this, value2), 500);
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        TreePatch treePatch = TreePatch.forPosition(new Position(value4, value22));
        if (treePatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[treePatch.getIndex()] != 3 || this.patchStates[treePatch.getIndex()] == 5) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This patch doesn't need compost.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(1925));
        Player player = this.player;
        player.packetSender.sendGameMessage("You pour some " + (value32 == 6034 ? "super" : "") + "compost on the patch.");
        this.player.getUpdateState().setAnimation(2283);
        this.player.getSkillManager().addExperience(19, value32 == 6034 ? 26.0 : 18.0);
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new TreeCompostTask(this, treePatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        TreePatch treePatch = TreePatch.forPosition(new Position(value3, value22));
        if (treePatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        FarmedTreeGrowthDefinition farmedTreeGrowthDefinition = FarmedTreeGrowthDefinition.forTreeId(this.treeIds[treePatch.getIndex()]);
        Object index = FarmedTreeDefinition.forSaplingId(this.treeIds[treePatch.getIndex()]);
        if (this.patchStates[treePatch.getIndex()] == 1) {
            this.player.getDialogueManager().showTwoLineStatement("This tree is diseased. Use secateurs to prune the area, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[treePatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This tree is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[treePatch.getIndex()] == 7) {
            this.player.getDialogueManager().showTwoLineStatement("This is a tree stump, to remove it, use a spade on it", "to recieve some roots and clear the patch.");
            return true;
        }
        if (this.growthStages[treePatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is a tree patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[treePatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[treePatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is a tree patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[treePatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is a tree patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is a tree patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (farmedTreeGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new TreeInspectTask(this, treePatch, farmedTreeGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = TreePatch.forPosition(new Position(skillId, value3));
        if (value2 == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(3);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value4, int value22) {
        Object value3 = TreePatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(this.treeIds[((TreePatch)value3).getIndex()]);
        if (farmedTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((TreePatch)value3).getIndex()] != 2) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("tree", ((TreePatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = FarmedTreeDefinition.forSaplingId(this.treeIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((FarmedTreeDefinition)value).getGrowthCycleTicks() * value2);
            value = this.player;
            ((Player)value).packetSender.sendGameMessage("You succesfully resurrected the crop.");
        } else {
            this.resetPatch(this.player.pendingCropResurrectionPatchIndex);
            this.growthStages[this.player.pendingCropResurrectionPatchIndex] = 3;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes();
            Player player = this.player;
            player.packetSender.sendGameMessage("You failed to resurrect the crop.");
        }
        this.refreshConfig();
        return true;
    }

    public final boolean prunePatch(int value5, int value22, int value32) {
        Object value4 = TreePatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 5329 && value32 != 7409) {
            return false;
        }
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(this.treeIds[((TreePatch)value4).getIndex()]);
        if (farmedTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((TreePatch)value4).getIndex()] != 1) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This area doesn't need to be pruned.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2275);
        this.player.setActionLocked(true);
        this.patchStates[((TreePatch)value4).getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new TreePruneTask(this), 15);
        return true;
    }

    public final void resetPatch(int value2) {
        this.treeIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
        this.patchData[value2] = 0;
        this.fullyGrownFlags[value2] = false;
        this.protectionFlags[value2] = false;
    }

    public final boolean startCuttingTree(int value4, int value22) {
        TreePatch treePatch = TreePatch.forPosition(new Position(value4, value22));
        if (treePatch == null) {
            return false;
        }
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(this.treeIds[treePatch.getIndex()]);
        if (farmedTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(this.player, 8);
        if (gatheringToolDefinition == null) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
            return true;
        }
        int treeObjectId = farmedTreeDefinition.getTreeObjectId();
        TreeDefinition treeDefinition = TreeDefinition.forObjectId(treeObjectId);
        if (treeDefinition == null) {
            return true;
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You swing your axe at the tree.");
        int gatherAnimationId = gatheringToolDefinition.getGatherAnimationId();
        gatheringToolDefinition.getToolSpeed();
        treeDefinition.getRequiredLevel();
        this.player.getUpdateState().setAnimation(gatherAnimationId);
        this.player.pendingFarmingPatchPosition = new Position(value4, value22);
        int value3 = this.player.nextActionSequence();
        this.player.temporaryActionValue = 0;
        this.player.sharedActionValue = 0;
        this.player.setActiveCycleEvent(new FarmedTreeCuttingTask(this, value3, gatherAnimationId, treeDefinition, gatheringToolDefinition, treeObjectId, treePatch, value4, value22));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 1);
        return true;
    }

    public final boolean canContinueCuttingTree(int value4, int value22) {
        Object value3 = TreePatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        FarmedTreeDefinition farmedTreeDefinition = FarmedTreeDefinition.forSaplingId(this.treeIds[((TreePatch)value3).getIndex()]);
        if (farmedTreeDefinition == null) {
            return false;
        }
        int treeObjectId = farmedTreeDefinition.getTreeObjectId();
        if (!this.fullyGrownFlags[((TreePatch)value3).getIndex()]) {
            return false;
        }
        if (ItemCombinationHandler.findUsableGatheringTool(this.player, 8) == null) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
            return false;
        }
        if (!this.player.getInventoryManager().canAddItem(new ItemStack(1511))) {
            return false;
        }
        return SkillActionHelper.checkSkillRequirement(this.player, 8, TreeDefinition.forObjectId(treeObjectId).getRequiredLevel(), "chop this tree");
    }

    static Player getPlayer(TreePatchManager treePatchManager) {
        return treePatchManager.player;
    }
}

