package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HopsClearingTask;
import com.rs2.model.skill.farming.HopsCompostTask;
import com.rs2.model.skill.farming.HopsCureTask;
import com.rs2.model.skill.farming.HopsDefinition;
import com.rs2.model.skill.farming.HopsGrowthDefinition;
import com.rs2.model.skill.farming.HopsHarvestTask;
import com.rs2.model.skill.farming.HopsInspectTask;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPlantingTask;
import com.rs2.model.skill.farming.HopsWateringTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class HopsPatchManager {
    private Player player;
    public int[] harvestAmounts = new int[4];
    public int[] growthStages = new int[4];
    public int[] cropIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};
    private boolean[] fullyGrownFlags = new boolean[4];
    public boolean[] protectionFlags = new boolean[4];

    public HopsPatchManager(Player player) {
        this.player = player;
    }

    public final void refreshConfig() {
        int[] integerValues = new int[this.growthStages.length];
        int index = 0;
        while (index < this.growthStages.length) {
            int value;
            int value2 = this.patchStates[index];
            int value3 = this.cropIds[index];
            int value4 = this.growthStages[index];
            Object value5 = this;
            value5 = HopsDefinition.forSeedId(value3);
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
                    value4 -= 4;
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
                        case 3: {
                            value6 = 3;
                            break;
                        }
                        default: {
                            value6 = -1;
                        }
                    }
                    value = (value6 << 6) + ((HopsDefinition)((Object)value5)).getConfigStartStage() + value4;
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value7 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(506, value7);
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.cropIds.length) {
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
                Object value4 = HopsDefinition.forSeedId(this.cropIds[index]);
                if (value4 == null || this.shouldStopGrowthCycle(index)) break processGrowthControlExit1;
                int growthCycleTicks = (int)(value / (long)((HopsDefinition)value4).getGrowthCycleTicks());
                int value5 = this.growthStages[index] - 4;
                if ((growthCycleTicks -= value5) <= 0) break processGrowthControlExit1;
                value5 = 0;
                while (value5 < growthCycleTicks) {
                    processGrowthControlExit3: {
                        processGrowthControlExit4: {
                            HopsDefinition hopsDefinition;
                            int value6;
                            processGrowthControlExit5: {
                                processGrowthControlExit6: {
                                    processGrowthControlExit7: {
                                        if (this.growthStages[index] != 4) break processGrowthControlExit7;
                                        if (this.patchStates[index] == 1) {
                                            this.patchStates[index] = 0;
                                        }
                                        int value7 = index;
                                        this.growthStages[value7] = this.growthStages[value7] + 1;
                                        break processGrowthControlExit3;
                                    }
                                    value6 = index;
                                    value4 = this;
                                    if (((HopsPatchManager)value4).patchStates[value6] != 2) break processGrowthControlExit5;
                                    if (!((HopsPatchManager)value4).protectionFlags[value6]) break processGrowthControlExit6;
                                    ((HopsPatchManager)value4).patchStates[value6] = 0;
                                    hopsDefinition = HopsDefinition.forSeedId(((HopsPatchManager)value4).cropIds[value6]);
                                    if (hopsDefinition == null) break processGrowthControlExit4;
                                    int value8 = value6;
                                    ((HopsPatchManager)value4).lastUpdateTicks[value8] = ((HopsPatchManager)value4).lastUpdateTicks[value8] + (long)hopsDefinition.getGrowthCycleTicks();
                                    break processGrowthControlExit5;
                                }
                                if (GameUtil.randomInt(2) == 0) {
                                    ((HopsPatchManager)value4).patchStates[value6] = 3;
                                }
                            }
                            if (((HopsPatchManager)value4).patchStates[value6] != 2 && ((HopsPatchManager)value4).patchStates[value6] != 3) {
                                if (((HopsPatchManager)value4).patchStates[value6] == 5 && ((HopsPatchManager)value4).growthStages[value6] != 3) {
                                    ((HopsPatchManager)value4).patchStates[value6] = 0;
                                }
                                if (!(((HopsPatchManager)value4).patchStates[value6] != 0 && ((HopsPatchManager)value4).patchStates[value6] != 1 || ((HopsPatchManager)value4).growthStages[value6] < 4 || ((HopsPatchManager)value4).fullyGrownFlags[value6] || (hopsDefinition = HopsDefinition.forSeedId(((HopsPatchManager)value4).cropIds[value6])) == null)) {
                                    double diseaseChance = ((HopsPatchManager)value4).diseaseChanceMultipliers[value6] * hopsDefinition.getDiseaseChance();
                                    double value9 = diseaseChance * 100.0;
                                    int value10 = (int)value9;
                                    if (((HopsPatchManager)value4).patchStates[value6] == 1) {
                                        ((HopsPatchManager)value4).patchStates[value6] = 0;
                                    } else if (GameUtil.randomInclusive(100) <= value10 && ServerSettings.diseasingEnabled) {
                                        ((HopsPatchManager)value4).patchStates[value6] = 2;
                                    }
                                }
                            }
                        }
                        if (this.patchStates[index] == 3) break processGrowthControlExit1;
                        if (this.patchStates[index] != 2) {
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
        if (this.patchStates[value4] == 3) return true;
        int value2 = value4;
        HopsPatchManager hopsPatchManager = this;
        HopsDefinition hopsDefinition = HopsDefinition.forSeedId(hopsPatchManager.cropIds[value2]);
        if (hopsDefinition == null) return false;
        int value3 = hopsPatchManager.growthStages[value2] - 4;
        if (hopsDefinition.getConfigStartStage() + value3 != hopsDefinition.getConfigEndStage()) return false;
        hopsPatchManager.fullyGrownFlags[value2] = true;
        return true;
    }

    public final boolean waterPatch(int value4, int value22, int value32) {
        HopsPatch hopsPatch = HopsPatch.forPosition(new Position(value4, value22));
        if (hopsPatch == null) {
            return false;
        }
        Object index = HopsDefinition.forSeedId(this.cropIds[hopsPatch.getIndex()]);
        if (index == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[hopsPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[hopsPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[hopsPatch.getIndex()] == 1 || this.growthStages[hopsPatch.getIndex()] <= 1 || this.growthStages[hopsPatch.getIndex()] == ((HopsDefinition)index).getGrowthStageCount() + 4) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("This patch doesn't need watering.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(value32 == 5333 ? value32 - 2 : value32 - 1));
        index = this.player;
        ((Player)index).packetSender.sendGameMessage("You water the patch.");
        this.player.getUpdateState().setAnimation(2293);
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new HopsWateringTask(this, hopsPatch), 5);
        return true;
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        HopsPatch hopsPatch = HopsPatch.forPosition(new Position(value5, value22));
        if (hopsPatch == null || value32 != 5341 && value32 != 952) {
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
        if (this.growthStages[hopsPatch.getIndex()] == 3) {
            return true;
        }
        if (this.growthStages[hopsPatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new HopsClearingTask(this, value32, hopsPatch), value4);
        return true;
    }

    public final boolean plantSeed(int value4, int value22, int value32) {
        HopsPatch hopsPatch = HopsPatch.forPosition(new Position(value4, value22));
        HopsDefinition hopsDefinition = HopsDefinition.forSeedId(value32);
        if (hopsPatch == null || hopsDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[hopsPatch.getIndex()] != 3) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't plant a seed here.");
            return false;
        }
        if (hopsDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + hopsDefinition.getRequiredLevel() + " to plant this seed.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5343)) {
            this.player.getDialogueManager().showOneLineStatement("You need a seed dibber to plant seed here.");
            return true;
        }
        if (this.player.getInventoryManager().getItemAmount(hopsDefinition.getSeedId()) < hopsDefinition.getSeedAmount()) {
            this.player.getDialogueManager().showOneLineStatement("You need atleast " + hopsDefinition.getSeedAmount() + " seeds to plant here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2291);
        Player player = this.player;
        player.packetSender.sendSoundEffect(1321, 1, 0);
        this.growthStages[hopsPatch.getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32, hopsDefinition.getSeedAmount()));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new HopsPlantingTask(this, hopsPatch, value32, hopsDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int value5, int value22) {
        Object value3 = HopsPatch.forPosition(new Position(value5, value22));
        if (value3 == null) {
            return false;
        }
        HopsDefinition hopsDefinition = HopsDefinition.forSeedId(this.cropIds[((HopsPatch)value3).getIndex()]);
        if (hopsDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(952)) {
            this.player.getDialogueManager().showOneLineStatement("You need a spade to harvest here.");
            return true;
        }
        int value4 = this.player.nextActionSequence();
        this.player.getUpdateState().setAnimation(830);
        this.player.setActiveCycleEvent(new HopsHarvestTask(this, value4, hopsDefinition, (HopsPatch)((Object)value3)));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        HopsPatch hopsPatch = HopsPatch.forPosition(new Position(value4, value22));
        if (hopsPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[hopsPatch.getIndex()] != 3 || this.patchStates[hopsPatch.getIndex()] == 5) {
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
        CycleEventHandler.getInstance().schedule(this.player, new HopsCompostTask(this, hopsPatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        HopsPatch hopsPatch = HopsPatch.forPosition(new Position(value3, value22));
        if (hopsPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        HopsGrowthDefinition hopsGrowthDefinition = HopsGrowthDefinition.forCropId(this.cropIds[hopsPatch.getIndex()]);
        Object index = HopsDefinition.forSeedId(this.cropIds[hopsPatch.getIndex()]);
        if (this.patchStates[hopsPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[hopsPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.growthStages[hopsPatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is a hops patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[hopsPatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[hopsPatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is a hops patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[hopsPatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is a hops patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is a hops patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (hopsGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new HopsInspectTask(this, hopsPatch, hopsGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = HopsPatch.forPosition(new Position(skillId, value3));
        if (value2 == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(2);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value4, int value22) {
        Object value3 = HopsPatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        HopsDefinition hopsDefinition = HopsDefinition.forSeedId(this.cropIds[((HopsPatch)value3).getIndex()]);
        if (hopsDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((HopsPatch)value3).getIndex()] != 3) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("hops", ((HopsPatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = HopsDefinition.forSeedId(this.cropIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((HopsDefinition)value).getGrowthCycleTicks() * value2);
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

    public final boolean curePatch(int value5, int value22, int value32) {
        Object value4 = HopsPatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 6036) {
            return false;
        }
        HopsDefinition hopsDefinition = HopsDefinition.forSeedId(this.cropIds[((HopsPatch)value4).getIndex()]);
        if (hopsDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((HopsPatch)value4).getIndex()] != 2) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.player.setActionLocked(true);
        this.patchStates[((HopsPatch)value4).getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new HopsCureTask(this), 7);
        return true;
    }

    private void resetPatch(int value2) {
        this.cropIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
        this.harvestAmounts[value2] = 3;
        this.fullyGrownFlags[value2] = false;
        this.protectionFlags[value2] = false;
    }

    static Player getPlayer(HopsPatchManager hopsPatchManager) {
        return hopsPatchManager.player;
    }

    static void resetPatch(HopsPatchManager hopsPatchManager, int value2) {
        hopsPatchManager.resetPatch(value2);
    }
}
