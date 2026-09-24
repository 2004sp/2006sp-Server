package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FlowerClearingTask;
import com.rs2.model.skill.farming.FlowerCompostTask;
import com.rs2.model.skill.farming.FlowerCureTask;
import com.rs2.model.skill.farming.FlowerDefinition;
import com.rs2.model.skill.farming.FlowerGrowthDefinition;
import com.rs2.model.skill.farming.FlowerHarvestTask;
import com.rs2.model.skill.farming.FlowerInspectTask;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPlantingTask;
import com.rs2.model.skill.farming.FlowerWateringTask;
import com.rs2.model.skill.farming.ScarecrowPlantingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class FlowerPatchManager {
    private Player player;
    public int[] growthStages = new int[4];
    public int[] cropIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};
    public boolean[] fullyGrownFlags = new boolean[4];

    public FlowerPatchManager(Player player) {
        this.player = player;
    }

    public final void refreshConfig() {
        int[] integerValues = new int[this.growthStages.length];
        int index = 0;
        while (index < this.growthStages.length) {
            int value;
            int value2 = index;
            int value3 = this.patchStates[index];
            int value4 = this.cropIds[index];
            int value5 = this.growthStages[index];
            Object value6 = this;
            if (((FlowerPatchManager)value6).cropIds[value2] >= 33 && ((FlowerPatchManager)value6).cropIds[value2] <= 36 && ((FlowerPatchManager)value6).growthStages[value2] > 3) {
                value = 0 + ((FlowerPatchManager)value6).cropIds[value2];
            } else {
                value6 = FlowerDefinition.forSeedId(value4);
                switch (value5) {
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
                        int value7;
                        if (value6 == null) {
                            value = -1;
                            break;
                        }
                        value5 -= 4;
                        value4 = value3;
                        switch (value4) {
                            case 0: {
                                value7 = 0;
                                break;
                            }
                            case 1: {
                                value7 = 1;
                                break;
                            }
                            case 2: {
                                value7 = 2;
                                break;
                            }
                            case 3: {
                                value7 = 3;
                                break;
                            }
                            default: {
                                value7 = -1;
                            }
                        }
                        value = (value7 << 6) + ((FlowerDefinition)((Object)value6)).getConfigStartStage() + value5;
                    }
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value8 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(508, value8);
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.cropIds.length) {
            long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[index];
            if (elapsedMinutes >= 5L) {
                int value;
                if (this.growthStages[index] > 0 && this.growthStages[index] <= 3) {
                    int value2 = (int)(elapsedMinutes / 5L);
                    value = 0;
                    while (value < value2) {
                        if (this.growthStages[index] == 0) break;
                        int value3 = index;
                        this.growthStages[value3] = this.growthStages[value3] - 1;
                        this.lastUpdateTicks[index] = Server.getElapsedMinutes();
                        ++value;
                    }
                }
                if (this.cropIds[index] > 33 && this.cropIds[index] <= 36) {
                    int value4 = index;
                    this.cropIds[value4] = this.cropIds[value4] - 1;
                } else {
                    Object value5 = FlowerDefinition.forSeedId(this.cropIds[index]);
                    if (value5 != null && this.cropIds[index] != 33 && !this.shouldStopGrowthCycle(index)) {
                        int value6 = this.growthStages[index] - 4;
                        value = FarmingPatchUtils.getGrowthCycleTarget(this.player, this.lastUpdateTicks[index], ((FlowerDefinition)value5).getGrowthCycleTicks(), value6);
                        if ((value6 = value - value6) > 0) {
                            int index2 = 0;
                            while (index2 < value6) {
                                if (this.growthStages[index] == 4) {
                                    if (this.patchStates[index] == 1) {
                                        this.patchStates[index] = 0;
                                    }
                                    int value7 = index;
                                    this.growthStages[value7] = this.growthStages[value7] + 1;
                                } else {
                                    value = index;
                                    value5 = this;
                                    if (((FlowerPatchManager)value5).patchStates[value] == 2 && GameUtil.randomInt(2) == 0) {
                                        ((FlowerPatchManager)value5).patchStates[value] = 3;
                                    }
                                    if (((FlowerPatchManager)value5).patchStates[value] != 2 && ((FlowerPatchManager)value5).patchStates[value] != 3) {
                                        FlowerDefinition flowerDefinition;
                                        if (((FlowerPatchManager)value5).patchStates[value] == 5 && ((FlowerPatchManager)value5).growthStages[value] != 3) {
                                            ((FlowerPatchManager)value5).patchStates[value] = 0;
                                        }
                                        if (!(((FlowerPatchManager)value5).patchStates[value] != 0 && ((FlowerPatchManager)value5).patchStates[value] != 1 || ((FlowerPatchManager)value5).growthStages[value] < 4 || ((FlowerPatchManager)value5).fullyGrownFlags[value] || (flowerDefinition = FlowerDefinition.forSeedId(((FlowerPatchManager)value5).cropIds[value])) == null)) {
                                            double diseaseChance = ((FlowerPatchManager)value5).diseaseChanceMultipliers[value] * flowerDefinition.getDiseaseChance();
                                            double value8 = diseaseChance * 100.0;
                                            int value9 = (int)value8;
                                            if (((FlowerPatchManager)value5).patchStates[value] == 1) {
                                                ((FlowerPatchManager)value5).patchStates[value] = 0;
                                            } else if (GameUtil.randomInclusive(100) <= value9 && ServerSettings.diseasingEnabled) {
                                                ((FlowerPatchManager)value5).patchStates[value] = 2;
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
                    }
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
        FlowerPatchManager flowerPatchManager = this;
        FlowerDefinition flowerDefinition = FlowerDefinition.forSeedId(flowerPatchManager.cropIds[value2]);
        if (flowerDefinition == null) return false;
        int value3 = flowerPatchManager.growthStages[value2] - 4;
        if (flowerDefinition.getConfigEndStage() != flowerDefinition.getConfigStartStage() + value3) return false;
        flowerPatchManager.fullyGrownFlags[value2] = true;
        return true;
    }

    public final boolean waterPatch(int value4, int value22, int value32) {
        FlowerPatch flowerPatch = FlowerPatch.forPosition(new Position(value4, value22));
        if (flowerPatch == null) {
            return false;
        }
        Object index = FlowerDefinition.forSeedId(this.cropIds[flowerPatch.getIndex()]);
        if (index == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[flowerPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[flowerPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[flowerPatch.getIndex()] == 1 || this.growthStages[flowerPatch.getIndex()] <= 1 || this.growthStages[flowerPatch.getIndex()] == ((FlowerDefinition)index).getGrowthStageCount() + 4) {
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
        CycleEventHandler.getInstance().schedule(this.player, new FlowerWateringTask(this, flowerPatch), 5);
        return true;
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        FlowerPatch flowerPatch = FlowerPatch.forPosition(new Position(value5, value22));
        if (flowerPatch == null || value32 != 5341 && value32 != 952) {
            return false;
        }
        if (this.growthStages[flowerPatch.getIndex()] == 3) {
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
        if (this.growthStages[flowerPatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new FlowerClearingTask(this, value32, flowerPatch), value4);
        return true;
    }

    public final boolean plantSeed(int value4, int value22, int value32) {
        FlowerPatch flowerPatch = FlowerPatch.forPosition(new Position(value4, value22));
        FlowerDefinition flowerDefinition = FlowerDefinition.forSeedId(value32);
        if (flowerPatch == null || flowerDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[flowerPatch.getIndex()] != 3) {
            this.player.getDialogueManager().showOneLineStatement("You can't plant a seed here.");
            return false;
        }
        if (flowerDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + flowerDefinition.getRequiredLevel() + " to plant this seed.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5343)) {
            this.player.getDialogueManager().showOneLineStatement("You need a seed dibber to plant seed here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2291);
        Player player = this.player;
        player.packetSender.sendSoundEffect(1321, 1, 0);
        this.growthStages[flowerPatch.getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new FlowerPlantingTask(this, flowerPatch, value32, flowerDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int value5, int value22) {
        Object value3 = FlowerPatch.forPosition(new Position(value5, value22));
        if (value3 == null) {
            return false;
        }
        FlowerDefinition flowerDefinition = FlowerDefinition.forSeedId(this.cropIds[((FlowerPatch)value3).getIndex()]);
        if (flowerDefinition == null) {
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
        CycleEventHandler.getInstance().schedule(this.player, new FlowerHarvestTask(this, value4, (FlowerPatch)((Object)value3), flowerDefinition), 2);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        FlowerPatch flowerPatch = FlowerPatch.forPosition(new Position(value4, value22));
        if (flowerPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[flowerPatch.getIndex()] != 3 || this.patchStates[flowerPatch.getIndex()] == 5) {
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
        CycleEventHandler.getInstance().schedule(this.player, new FlowerCompostTask(this, flowerPatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        FlowerPatch flowerPatch = FlowerPatch.forPosition(new Position(value3, value22));
        if (flowerPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        FlowerGrowthDefinition flowerGrowthDefinition = FlowerGrowthDefinition.forCropId(this.cropIds[flowerPatch.getIndex()]);
        Object index = FlowerDefinition.forSeedId(this.cropIds[flowerPatch.getIndex()]);
        if (this.patchStates[flowerPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[flowerPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.growthStages[flowerPatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is an flower patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[flowerPatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[flowerPatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is an flower patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[flowerPatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is an flower patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is an flower patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (flowerGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new FlowerInspectTask(this, flowerPatch, flowerGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = FlowerPatch.forPosition(new Position(skillId, value3));
        if (value2 == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(6);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value4, int value22) {
        Object value3 = FlowerPatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        FlowerDefinition flowerDefinition = FlowerDefinition.forSeedId(this.cropIds[((FlowerPatch)value3).getIndex()]);
        if (flowerDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((FlowerPatch)value3).getIndex()] != 3) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("flower", ((FlowerPatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = FlowerDefinition.forSeedId(this.cropIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((FlowerDefinition)value).getGrowthCycleTicks() * value2);
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
        Object value4 = FlowerPatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 6036) {
            return false;
        }
        FlowerDefinition flowerDefinition = FlowerDefinition.forSeedId(this.cropIds[((FlowerPatch)value4).getIndex()]);
        if (flowerDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((FlowerPatch)value4).getIndex()] != 2) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.patchStates[((FlowerPatch)value4).getIndex()] = 0;
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new FlowerCureTask(this), 7);
        return true;
    }

    public final boolean plantScarecrow(int value5, int value22, int value32) {
        Object value4 = FlowerPatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 6059) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[((FlowerPatch)value4).getIndex()] != 3) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("You need to clear the patch before planting a scarecrow");
            return false;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(6059));
        this.player.getUpdateState().setAnimation(832);
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new ScarecrowPlantingTask(this, (FlowerPatch)((Object)value4)), 2);
        return true;
    }

    private void resetPatch(int value2) {
        this.cropIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
    }

    static Player getPlayer(FlowerPatchManager flowerPatchManager) {
        return flowerPatchManager.player;
    }

    static void resetPatch(FlowerPatchManager flowerPatchManager, int value2) {
        flowerPatchManager.resetPatch(value2);
    }
}

