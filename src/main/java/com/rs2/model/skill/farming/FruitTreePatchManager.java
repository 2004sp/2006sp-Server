package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.farming.FruitTreeClearingTask;
import com.rs2.model.skill.farming.FruitTreeCompostTask;
import com.rs2.model.skill.farming.FruitTreeCuttingTask;
import com.rs2.model.skill.farming.FruitTreeDefinition;
import com.rs2.model.skill.farming.FruitTreeGrowthDefinition;
import com.rs2.model.skill.farming.FruitTreeHarvestTask;
import com.rs2.model.skill.farming.FruitTreeInspectTask;
import com.rs2.model.skill.farming.FruitTreePatch;
import com.rs2.model.skill.farming.FruitTreePlantingTask;
import com.rs2.model.skill.farming.FruitTreePruneTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class FruitTreePatchManager {
    private Player player;
    public int[] growthStages = new int[4];
    public int[] treeIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};
    public boolean[] protectionFlags = new boolean[4];

    public FruitTreePatchManager(Player player) {
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
            value5 = FruitTreeDefinition.forSaplingId(value3);
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
                    value = value5 == null ? -1 : (value2 == 6 ? ((FruitTreeDefinition)((Object)value5)).getStumpConfigStage() : (FruitTreePatchManager.getConfigStageForPatchState(value2, (FruitTreeDefinition)((Object)value5), value4) == 3 ? ((FruitTreeDefinition)((Object)value5)).getHealthCheckConfigStage() : FruitTreePatchManager.getConfigStageForPatchState(value2, (FruitTreeDefinition)((Object)value5), value4)));
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value6 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(503, value6);
    }

    private static int getConfigStageForPatchState(int state, FruitTreeDefinition fruitTreeDefinition, int value22) {
        value22 -= 4;
        value22 = fruitTreeDefinition.getConfigStartStage() + value22;
        switch (state) {
            case 0: {
                return value22;
            }
            case 1: {
                return value22 + fruitTreeDefinition.getDiseasedConfigOffset();
            }
            case 2: {
                return value22 + fruitTreeDefinition.getDeadConfigOffset();
            }
            case 3: {
                return fruitTreeDefinition.getHealthCheckConfigStage();
            }
        }
        return -1;
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
                FruitTreeDefinition fruitTreeDefinition = FruitTreeDefinition.forSaplingId(this.treeIds[index]);
                if (fruitTreeDefinition == null || this.shouldStopGrowthCycle(index)) break processGrowthControlExit1;
                int growthCycleTicks = (int)(value / (long)fruitTreeDefinition.getGrowthCycleTicks());
                int value4 = this.growthStages[index] - 4;
                if ((growthCycleTicks -= value4) <= 0) break processGrowthControlExit1;
                value4 = 0;
                while (value4 < growthCycleTicks) {
                    processGrowthControlExit3: {
                        processGrowthControlExit4: {
                            FruitTreeDefinition fruitTreeDefinition2;
                            FruitTreePatchManager fruitTreePatchManager;
                            int value5;
                            processGrowthControlExit5: {
                                processGrowthControlExit6: {
                                    processGrowthControlExit7: {
                                        if (this.growthStages[index] != 4) break processGrowthControlExit7;
                                        int value6 = index;
                                        this.growthStages[value6] = this.growthStages[value6] + 1;
                                        break processGrowthControlExit3;
                                    }
                                    value5 = index;
                                    fruitTreePatchManager = this;
                                    if (fruitTreePatchManager.patchStates[value5] != 1) break processGrowthControlExit5;
                                    if (!fruitTreePatchManager.protectionFlags[value5]) break processGrowthControlExit6;
                                    fruitTreePatchManager.patchStates[value5] = 0;
                                    fruitTreeDefinition2 = FruitTreeDefinition.forSaplingId(fruitTreePatchManager.treeIds[value5]);
                                    if (fruitTreeDefinition2 == null) break processGrowthControlExit4;
                                    int value7 = value5;
                                    fruitTreePatchManager.lastUpdateTicks[value7] = fruitTreePatchManager.lastUpdateTicks[value7] + (long)fruitTreeDefinition2.getGrowthCycleTicks();
                                    break processGrowthControlExit5;
                                }
                                if (GameUtil.randomInt(2) == 0) {
                                    fruitTreePatchManager.patchStates[value5] = 2;
                                }
                            }
                            if (fruitTreePatchManager.patchStates[value5] != 1 && fruitTreePatchManager.patchStates[value5] != 2) {
                                if (fruitTreePatchManager.patchStates[value5] == 5 && fruitTreePatchManager.growthStages[value5] != 2) {
                                    fruitTreePatchManager.patchStates[value5] = 0;
                                }
                                if (fruitTreePatchManager.patchStates[value5] == 0 && fruitTreePatchManager.growthStages[value5] >= 4 && (fruitTreeDefinition2 = FruitTreeDefinition.forSaplingId(fruitTreePatchManager.treeIds[value5])) != null) {
                                    double diseaseChance = fruitTreePatchManager.diseaseChanceMultipliers[value5] * fruitTreeDefinition2.getDiseaseChance();
                                    double value8 = diseaseChance * 100.0;
                                    int value9 = (int)value8;
                                    if (GameUtil.randomInclusive(100) <= value9 && ServerSettings.diseasingEnabled) {
                                        fruitTreePatchManager.patchStates[value5] = 1;
                                    }
                                }
                            }
                        }
                        if (this.patchStates[index] == 2) break processGrowthControlExit1;
                        if (this.patchStates[index] != 1) {
                            int value10 = index;
                            this.growthStages[value10] = this.growthStages[value10] + 1;
                        }
                        if (this.shouldStopGrowthCycle(index)) break processGrowthControlExit1;
                        if (this.growthStages[index] + fruitTreeDefinition.getConfigStartStage() == fruitTreeDefinition.getMatureConfigStage() + 3) {
                            this.growthStages[index] = fruitTreeDefinition.getGrowthStageCount() + 7;
                            this.patchStates[index] = 3;
                            break;
                        }
                    }
                    ++value4;
                }
            }
            ++index;
        }
        this.refreshConfig();
    }

    private boolean shouldStopGrowthCycle(int value2) {
        return this.lastUpdateTicks[value2] == 0L || this.patchStates[value2] == 2 || this.patchStates[value2] == 3;
    }

    public final void recalculateRegrowthStage(int value2) {
        FruitTreeDefinition fruitTreeDefinition = FruitTreeDefinition.forSaplingId(this.treeIds[value2]);
        if (fruitTreeDefinition == null) {
            return;
        }
        long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[value2];
        int growthCycleTicks = (int)(elapsedMinutes / (long)fruitTreeDefinition.getGrowthCycleTicks());
        this.growthStages[value2] = growthCycleTicks + 4;
        this.refreshConfig();
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        FruitTreePatch fruitTreePatch = FruitTreePatch.forPosition(new Position(value5, value22));
        if (fruitTreePatch == null || value32 != 5341 && value32 != 952) {
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
        if (this.growthStages[fruitTreePatch.getIndex()] == 3) {
            return true;
        }
        if (this.growthStages[fruitTreePatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new FruitTreeClearingTask(this, value32, fruitTreePatch), value4);
        return true;
    }

    public final boolean plantSapling(int value5, int value22, int value32) {
        Object value4 = FruitTreePatch.forPosition(new Position(value5, value22));
        FruitTreeDefinition fruitTreeDefinition = FruitTreeDefinition.forSaplingId(value32);
        if (value4 == null || fruitTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[((FruitTreePatch)((Object)value4)).getIndex()] != 3) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("You can't plant a sapling here.");
            return true;
        }
        if (fruitTreeDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + fruitTreeDefinition.getRequiredLevel() + " to plant this sapling.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5325)) {
            this.player.getDialogueManager().showOneLineStatement("You need a trowel to plant the sapling here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2272);
        this.growthStages[((FruitTreePatch)((Object)value4)).getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new FruitTreePlantingTask(this, (FruitTreePatch)((Object)value4), value32, fruitTreeDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int value4, int value22) {
        FruitTreePatch fruitTreePatch = FruitTreePatch.forPosition(new Position(value4, value22));
        if (fruitTreePatch == null) {
            return false;
        }
        Enum enum_ = FruitTreeDefinition.forSaplingId(this.treeIds[fruitTreePatch.getIndex()]);
        if (enum_ == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[fruitTreePatch.getIndex()] + ((FruitTreeDefinition)enum_).getConfigStartStage() == ((FruitTreeDefinition)enum_).getMatureConfigStage() + 4) {
            boolean enabled;
            int value3 = value22;
            value22 = value4;
            FruitTreePatchManager fruitTreePatchManager = this;
            enum_ = FruitTreePatch.forPosition(new Position(value22, value3));
            if (enum_ == null) {
                enabled = false;
            } else {
                Object index = FruitTreeDefinition.forSaplingId(fruitTreePatchManager.treeIds[((FruitTreePatch)enum_).getIndex()]);
                if (index == null) {
                    enabled = false;
                } else if (fruitTreePatchManager.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                    index = fruitTreePatchManager.player;
                    ((Player)index).packetSender.sendGameMessage("Not enough space in your inventory.");
                    enabled = true;
                } else if (ItemCombinationHandler.findUsableGatheringTool(fruitTreePatchManager.player, 8) == null) {
                    index = fruitTreePatchManager.player;
                    ((Player)index).packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
                    enabled = true;
                } else {
                    index = fruitTreePatchManager.player;
                    ((Player)index).packetSender.sendGameMessage("You swing your axe at the tree.");
                    int usableGatheringTool = ItemCombinationHandler.findUsableGatheringTool(fruitTreePatchManager.player, 8).getGatherAnimationId();
                    fruitTreePatchManager.player.getUpdateState().setAnimation(usableGatheringTool);
                    fruitTreePatchManager.player.pendingFarmingPatchPosition = new Position(value22, value3);
                    value22 = fruitTreePatchManager.player.nextActionSequence();
                    fruitTreePatchManager.player.setActiveCycleEvent(new FruitTreeCuttingTask(fruitTreePatchManager, value22, (FruitTreePatch)enum_));
                    CycleEventHandler.getInstance().schedule(fruitTreePatchManager.player, fruitTreePatchManager.player.getActiveCycleEvent(), 5);
                    enabled = true;
                }
            }
            return true;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        this.player.getUpdateState().setAnimation(832);
        value4 = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new FruitTreeHarvestTask(this, value4, fruitTreePatch, (FruitTreeDefinition)enum_));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        FruitTreePatch fruitTreePatch = FruitTreePatch.forPosition(new Position(value4, value22));
        if (fruitTreePatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[fruitTreePatch.getIndex()] != 3 || this.patchStates[fruitTreePatch.getIndex()] == 5) {
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
        CycleEventHandler.getInstance().schedule(this.player, new FruitTreeCompostTask(this, fruitTreePatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        FruitTreePatch fruitTreePatch = FruitTreePatch.forPosition(new Position(value3, value22));
        if (fruitTreePatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        FruitTreeGrowthDefinition fruitTreeGrowthDefinition = FruitTreeGrowthDefinition.forTreeId(this.treeIds[fruitTreePatch.getIndex()]);
        Object index = FruitTreeDefinition.forSaplingId(this.treeIds[fruitTreePatch.getIndex()]);
        if (this.patchStates[fruitTreePatch.getIndex()] == 1) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[fruitTreePatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[fruitTreePatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant has fully grown. You can check it's health", "to gain some farming experiences.");
            return true;
        }
        if (this.patchStates[fruitTreePatch.getIndex()] == 6) {
            this.player.getDialogueManager().showTwoLineStatement("This is a fruit tree stump, to remove it, use a ", "spade on it to clear the patch");
            return true;
        }
        if (this.growthStages[fruitTreePatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is a fruit tree patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[fruitTreePatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[fruitTreePatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is a fruit tree patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[fruitTreePatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is a fruit tree patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is a fruit tree patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (fruitTreeGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new FruitTreeInspectTask(this, fruitTreePatch, fruitTreeGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = FruitTreePatch.forPosition(new Position(skillId, value3));
        if (value2 == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(4);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value4, int value22) {
        Object value3 = FruitTreePatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        FruitTreeDefinition fruitTreeDefinition = FruitTreeDefinition.forSaplingId(this.treeIds[((FruitTreePatch)value3).getIndex()]);
        if (fruitTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((FruitTreePatch)value3).getIndex()] != 2) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("fruittree", ((FruitTreePatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = FruitTreeDefinition.forSaplingId(this.treeIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((FruitTreeDefinition)value).getGrowthCycleTicks() * value2);
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
        Object value4 = FruitTreePatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 5329 && value32 != 7409) {
            return false;
        }
        FruitTreeDefinition fruitTreeDefinition = FruitTreeDefinition.forSaplingId(this.treeIds[((FruitTreePatch)value4).getIndex()]);
        if (fruitTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((FruitTreePatch)value4).getIndex()] != 1) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This area doesn't need to be pruned.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2275);
        this.player.setActionLocked(true);
        this.patchStates[((FruitTreePatch)value4).getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new FruitTreePruneTask(this), 15);
        return true;
    }

    private void resetPatch(int value2) {
        this.treeIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
        this.protectionFlags[value2] = false;
    }

    static Player getPlayer(FruitTreePatchManager fruitTreePatchManager) {
        return fruitTreePatchManager.player;
    }

    static void resetPatch(FruitTreePatchManager fruitTreePatchManager, int value2) {
        fruitTreePatchManager.resetPatch(value2);
    }
}
