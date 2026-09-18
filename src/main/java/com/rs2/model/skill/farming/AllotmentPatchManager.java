package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.AllotmentClearingTask;
import com.rs2.model.skill.farming.AllotmentCompostTask;
import com.rs2.model.skill.farming.AllotmentCropDefinition;
import com.rs2.model.skill.farming.AllotmentCureTask;
import com.rs2.model.skill.farming.AllotmentGrowthDefinition;
import com.rs2.model.skill.farming.AllotmentHarvestTask;
import com.rs2.model.skill.farming.AllotmentInspectTask;
import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPlantingTask;
import com.rs2.model.skill.farming.AllotmentWateringTask;
import com.rs2.model.skill.farming.CropStorageDefinition;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public class AllotmentPatchManager {
    private Player player;
    public int[] harvestAmounts = new int[8];
    public int[] growthStages = new int[8];
    public int[] cropIds = new int[8];
    public int[] patchStates = new int[8];
    public long[] lastUpdateTicks = new long[8];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    public boolean[] protectionFlags = new boolean[8];
    private boolean[] fullyGrownFlags = new boolean[8];

    public AllotmentPatchManager(Player player) {
        this.player = player;
    }

    public final void refreshConfig() {
        Object value;
        int[] integerValues = new int[this.growthStages.length];
        int index = 0;
        while (index < this.growthStages.length) {
            int value2;
            int value3 = this.patchStates[index];
            int value4 = this.cropIds[index];
            int value5 = this.growthStages[index];
            value = this;
            value = AllotmentCropDefinition.forSeedId(value4);
            switch (value5) {
                case 0: {
                    value2 = 0;
                    break;
                }
                case 1: {
                    value2 = 1;
                    break;
                }
                case 2: {
                    value2 = 2;
                    break;
                }
                case 3: {
                    value2 = 3;
                    break;
                }
                default: {
                    int value6;
                    if (value == null) {
                        value2 = -1;
                        break;
                    }
                    value5 -= 4;
                    value4 = value3;
                    switch (value4) {
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
                    value2 = (value6 << 6) + ((AllotmentCropDefinition)((Object)value)).getConfigStartStage() + value5;
                }
            }
            integerValues[index] = value2;
            ++index;
        }
        index = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        value = this.player;
        ((Player)value).packetSender.sendConfig(504, index);
        index = integerValues[4] << 16 | integerValues[5] << 8 << 16 | integerValues[6] | integerValues[7] << 8;
        value = this.player;
        ((Player)value).packetSender.sendConfig(505, index);
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.cropIds.length) {
            processGrowthControlExit1: {
                Object value;
                int value2;
                long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[index];
                if (elapsedMinutes < 5L) break processGrowthControlExit1;
                if (this.growthStages[index] > 0 && this.growthStages[index] <= 3) {
                    int value3 = (int)(elapsedMinutes / 5L);
                    value2 = 0;
                    while (value2 < value3) {
                        if (this.growthStages[index] == 0) break;
                        int value4 = index;
                        this.growthStages[value4] = this.growthStages[value4] - 1;
                        this.lastUpdateTicks[index] = Server.getElapsedMinutes();
                        ++value2;
                    }
                }
                if ((value = AllotmentCropDefinition.forSeedId(this.cropIds[index])) == null || this.shouldStopGrowthCycle(index)) break processGrowthControlExit1;
                value2 = (int)(elapsedMinutes / (long)((AllotmentCropDefinition)value).getGrowthCycleTicks());
                int value5 = this.growthStages[index] - 4;
                if ((value5 = value2 - value5) <= 0) break processGrowthControlExit1;
                int index2 = 0;
                while (index2 < value5) {
                    processGrowthControlExit2: {
                        processGrowthControlExit3: {
                            AllotmentCropDefinition allotmentCropDefinition;
                            processGrowthControlExit4: {
                                processGrowthControlExit5: {
                                    processGrowthControlExit6: {
                                        if (this.growthStages[index] != 4) break processGrowthControlExit6;
                                        if (this.patchStates[index] == 1) {
                                            this.patchStates[index] = 0;
                                        }
                                        int value6 = index;
                                        this.growthStages[value6] = this.growthStages[value6] + 1;
                                        break processGrowthControlExit2;
                                    }
                                    value2 = index;
                                    value = this;
                                    if (((AllotmentPatchManager)value).patchStates[value2] != 2) break processGrowthControlExit4;
                                    if (!((AllotmentPatchManager)value).protectionFlags[value2]) break processGrowthControlExit5;
                                    ((AllotmentPatchManager)value).patchStates[value2] = 0;
                                    allotmentCropDefinition = AllotmentCropDefinition.forSeedId(((AllotmentPatchManager)value).cropIds[value2]);
                                    if (allotmentCropDefinition == null) break processGrowthControlExit3;
                                    int value7 = value2;
                                    ((AllotmentPatchManager)value).lastUpdateTicks[value7] = ((AllotmentPatchManager)value).lastUpdateTicks[value7] + (long)allotmentCropDefinition.getGrowthCycleTicks();
                                    break processGrowthControlExit4;
                                }
                                if (GameUtil.randomInt(2) == 0) {
                                    ((AllotmentPatchManager)value).patchStates[value2] = 3;
                                }
                            }
                            if (((AllotmentPatchManager)value).patchStates[value2] != 2 && ((AllotmentPatchManager)value).patchStates[value2] != 3) {
                                if (((AllotmentPatchManager)value).patchStates[value2] == 5 && ((AllotmentPatchManager)value).growthStages[value2] != 3) {
                                    ((AllotmentPatchManager)value).patchStates[value2] = 0;
                                }
                                if (!(((AllotmentPatchManager)value).patchStates[value2] != 0 && ((AllotmentPatchManager)value).patchStates[value2] != 1 || ((AllotmentPatchManager)value).growthStages[value2] < 4 || ((AllotmentPatchManager)value).fullyGrownFlags[value2] || (allotmentCropDefinition = AllotmentCropDefinition.forSeedId(((AllotmentPatchManager)value).cropIds[value2])) == null || (allotmentCropDefinition = AllotmentCropDefinition.forSeedId(((AllotmentPatchManager)value).cropIds[value2])) == null)) {
                                    double diseaseChance = ((AllotmentPatchManager)value).diseaseChanceMultipliers[value2] * allotmentCropDefinition.getDiseaseChance();
                                    double value8 = diseaseChance * 100.0;
                                    int value9 = (int)value8;
                                    int index3 = 0;
                                    if (((AllotmentPatchManager)value).patchStates[value2] == 1) {
                                        ((AllotmentPatchManager)value).patchStates[value2] = 0;
                                    } else if (!((AllotmentPatchManager)value).protectionFlags[value2] && GameUtil.randomInclusive(100) <= value9) {
                                        switch (value2) {
                                            case 0: 
                                            case 1: {
                                                index3 = 3;
                                                break;
                                            }
                                            case 2: 
                                            case 3: {
                                                index3 = 2;
                                                break;
                                            }
                                            case 4: 
                                            case 5: {
                                                index3 = 1;
                                                break;
                                            }
                                            case 6: 
                                            case 7: {
                                                index3 = 0;
                                            }
                                        }
                                        if (((AllotmentPatchManager)value).player.getFlowerPatchManager().cropIds[index3] < 33 || ((AllotmentPatchManager)value).player.getFlowerPatchManager().cropIds[index3] > 36 || allotmentCropDefinition.getProtectionCropId() != 6059) {
                                            if (((AllotmentPatchManager)value).player.getFlowerPatchManager().patchStates[index3] != 3 && ((AllotmentPatchManager)value).player.getFlowerPatchManager().fullyGrownFlags[index3] && ((AllotmentPatchManager)value).player.getFlowerPatchManager().cropIds[index3] == allotmentCropDefinition.getProtectionCropId()) {
                                                ((AllotmentPatchManager)value).player.getFlowerPatchManager().patchStates[index3] = 3;
                                                ((AllotmentPatchManager)value).player.getFlowerPatchManager().refreshConfig();
                                            } else if (ServerSettings.diseasingEnabled) {
                                                ((AllotmentPatchManager)value).patchStates[value2] = 2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (this.patchStates[index] == 3) break;
                        if (this.patchStates[index] != 2) {
                            int value10 = index;
                            this.growthStages[value10] = this.growthStages[value10] + 1;
                        }
                        if (this.shouldStopGrowthCycle(index)) break;
                    }
                    ++index2;
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
        AllotmentPatchManager allotmentPatchManager = this;
        AllotmentCropDefinition allotmentCropDefinition = AllotmentCropDefinition.forSeedId(allotmentPatchManager.cropIds[value2]);
        if (allotmentCropDefinition == null) return false;
        int value3 = allotmentPatchManager.growthStages[value2] - 4;
        if (allotmentCropDefinition.getConfigStartStage() + value3 != allotmentCropDefinition.getConfigEndStage()) return false;
        allotmentPatchManager.fullyGrownFlags[value2] = true;
        return true;
    }

    public final boolean waterPatch(int value4, int value22, int value32) {
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(new Position(value4, value22));
        if (allotmentPatch == null) {
            return false;
        }
        Object index = AllotmentCropDefinition.forSeedId(this.cropIds[allotmentPatch.getIndex()]);
        if (index == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[allotmentPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[allotmentPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[allotmentPatch.getIndex()] == 1 || this.growthStages[allotmentPatch.getIndex()] <= 1 || this.growthStages[allotmentPatch.getIndex()] == ((AllotmentCropDefinition)index).getGrowthStageCount() + 4) {
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
        CycleEventHandler.getInstance().schedule(this.player, new AllotmentWateringTask(this, allotmentPatch), 5);
        return true;
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(new Position(value5, value22));
        if (allotmentPatch == null || value32 != 5341 && value32 != 952) {
            return false;
        }
        if (this.growthStages[allotmentPatch.getIndex()] == 3) {
            return true;
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
        if (this.growthStages[allotmentPatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new AllotmentClearingTask(this, value32, allotmentPatch), value4);
        return true;
    }

    public final boolean plantSeed(int value4, int value22, int value32) {
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(new Position(value4, value22));
        AllotmentCropDefinition allotmentCropDefinition = AllotmentCropDefinition.forSeedId(value32);
        if (allotmentPatch == null || allotmentCropDefinition == null) {
            return false;
        }
        if (this.growthStages[allotmentPatch.getIndex()] != 3) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't plant a seed here.");
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (allotmentCropDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + allotmentCropDefinition.getRequiredLevel() + " to plant this seed.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5343)) {
            this.player.getDialogueManager().showOneLineStatement("You need a seed dibber to plant seed here.");
            return true;
        }
        if (this.player.getInventoryManager().getItemAmount(allotmentCropDefinition.getSeedId()) < allotmentCropDefinition.getSeedAmount()) {
            this.player.getDialogueManager().showOneLineStatement("You need atleast " + allotmentCropDefinition.getSeedAmount() + " seeds to plant here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2291);
        Player player = this.player;
        player.packetSender.sendSoundEffect(1321, 1, 0);
        this.growthStages[allotmentPatch.getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32, allotmentCropDefinition.getSeedAmount()));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new AllotmentPlantingTask(this, allotmentPatch, value32, allotmentCropDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int value5, int value22) {
        Object value3 = AllotmentPatch.forPosition(new Position(value5, value22));
        if (value3 == null) {
            return false;
        }
        AllotmentCropDefinition allotmentCropDefinition = AllotmentCropDefinition.forSeedId(this.cropIds[((AllotmentPatch)value3).getIndex()]);
        if (allotmentCropDefinition == null) {
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
        this.player.setActiveCycleEvent(new AllotmentHarvestTask(this, value4, allotmentCropDefinition, (AllotmentPatch)((Object)value3)));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(new Position(value4, value22));
        if (allotmentPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[allotmentPatch.getIndex()] != 3 || this.patchStates[allotmentPatch.getIndex()] == 5) {
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
        CycleEventHandler.getInstance().schedule(this.player, new AllotmentCompostTask(this, allotmentPatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        AllotmentPatch allotmentPatch = AllotmentPatch.forPosition(new Position(value3, value22));
        if (allotmentPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        AllotmentGrowthDefinition allotmentGrowthDefinition = AllotmentGrowthDefinition.forCropId(this.cropIds[allotmentPatch.getIndex()]);
        Object index = AllotmentCropDefinition.forSeedId(this.cropIds[allotmentPatch.getIndex()]);
        if (this.patchStates[allotmentPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[allotmentPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.growthStages[allotmentPatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is an allotment patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[allotmentPatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[allotmentPatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is an allotment patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[allotmentPatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is an allotment patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is an allotment patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (allotmentGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new AllotmentInspectTask(this, allotmentPatch, allotmentGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = AllotmentPatch.forPosition(new Position(skillId, value3));
        if (value2 == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(1);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value4, int value22) {
        Object value3 = AllotmentPatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        AllotmentCropDefinition allotmentCropDefinition = AllotmentCropDefinition.forSeedId(this.cropIds[((AllotmentPatch)value3).getIndex()]);
        if (allotmentCropDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((AllotmentPatch)value3).getIndex()] != 3) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("allotment", ((AllotmentPatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = AllotmentCropDefinition.forSeedId(this.cropIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((AllotmentCropDefinition)value).getGrowthCycleTicks() * value2);
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
        Object value4 = AllotmentPatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 6036) {
            return false;
        }
        AllotmentCropDefinition allotmentCropDefinition = AllotmentCropDefinition.forSeedId(this.cropIds[((AllotmentPatch)value4).getIndex()]);
        if (allotmentCropDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((AllotmentPatch)value4).getIndex()] != 2) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.player.setActionLocked(true);
        this.patchStates[((AllotmentPatch)value4).getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new AllotmentCureTask(this), 7);
        return true;
    }

    private void resetPatch(int value2) {
        this.cropIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
        this.harvestAmounts[value2] = 3;
        this.protectionFlags[value2] = false;
        this.fullyGrownFlags[value2] = false;
    }

    static Player getPlayer(AllotmentPatchManager allotmentPatchManager) {
        return allotmentPatchManager.player;
    }

    static void resetPatch(AllotmentPatchManager allotmentPatchManager, int value2) {
        allotmentPatchManager.resetPatch(value2);
    }

    public static boolean emptyCropStorageContainer(Player player, int value5) {
        CropStorageDefinition cropStorageDefinition;
        CropStorageDefinition cropStorageDefinition2;
        Object value2;
        int value3;
        emptyCropStorageContainerControlExit1: {
            if (value5 == -1 || new ItemStack(value5, 1).getDefinition().isNote()) {
                return false;
            }
            int value4 = value5;
            CropStorageDefinition[] cropStorageDefinitionArray = CropStorageDefinition.values();
            int length = cropStorageDefinitionArray.length;
            value3 = 0;
            while (value3 < length) {
                value2 = cropStorageDefinitionArray[value3];
                int baseContainerItemId = ((CropStorageDefinition)value2).getBaseContainerItemId();
                int baseContainerItemId2 = ((CropStorageDefinition)value2).getBaseContainerItemId() + (CropStorageDefinition.isSack((CropStorageDefinition)value2) ? 18 : 8);
                if (value4 >= baseContainerItemId && value4 <= baseContainerItemId2) {
                    cropStorageDefinition2 = (CropStorageDefinition)value2;
                    break emptyCropStorageContainerControlExit1;
                }
                ++value3;
            }
            cropStorageDefinition2 = cropStorageDefinition = null;
        }
        if (cropStorageDefinition2 != null) {
            value2 = new ItemStack(cropStorageDefinition2.getProduceItemId(), 1);
            value3 = 0;
            if (value5 == cropStorageDefinition2.getBaseContainerItemId()) {
                value3 = 1;
            }
            if (player.getInventoryManager().canAddItem((ItemStack)value2)) {
                player.getInventoryManager().addItem((ItemStack)value2);
                player.getInventoryManager().removeItem(new ItemStack(value5, 1));
                if (value3 != 0) {
                    player.getInventoryManager().addItem(new ItemStack(cropStorageDefinition2.isSack() ? 5418 : 5376, 1));
                } else {
                    player.getInventoryManager().addItem(new ItemStack(value5 - 2, 1));
                }
                return true;
            }
        }
        return false;
    }
}
