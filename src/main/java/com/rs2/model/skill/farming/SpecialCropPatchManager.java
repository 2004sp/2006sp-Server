package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialCropClearingTask;
import com.rs2.model.skill.farming.SpecialCropCompostTask;
import com.rs2.model.skill.farming.SpecialCropCureTask;
import com.rs2.model.skill.farming.SpecialCropDefinition;
import com.rs2.model.skill.farming.SpecialCropGrowthDefinition;
import com.rs2.model.skill.farming.SpecialCropHarvestTask;
import com.rs2.model.skill.farming.SpecialCropInspectTask;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPlantingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class SpecialCropPatchManager {
    private Player player;
    public int[] growthStages = new int[4];
    public int[] cropIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};

    public SpecialCropPatchManager(Player player) {
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
            value5 = SpecialCropDefinition.forSeedId(value3);
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
                    value = value5 == null ? -1 : (SpecialCropPatchManager.getConfigStageForPatchState(value2, (SpecialCropDefinition)((Object)value5), value4) == 3 ? ((SpecialCropDefinition)((Object)value5)).getHealthCheckConfigStage() : SpecialCropPatchManager.getConfigStageForPatchState(value2, (SpecialCropDefinition)((Object)value5), value4));
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value6 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(512, value6);
    }

    private static int getConfigStageForPatchState(int state, SpecialCropDefinition specialCropDefinition, int value22) {
        value22 -= 4;
        value22 = specialCropDefinition.getConfigStartStage() + value22;
        switch (state) {
            case 0: {
                return value22;
            }
            case 1: {
                return value22 + specialCropDefinition.getDiseasedConfigOffset();
            }
            case 2: {
                return value22 + specialCropDefinition.getDeadConfigOffset();
            }
            case 3: {
                return specialCropDefinition.getHealthCheckConfigStage();
            }
        }
        return -1;
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.cropIds.length) {
            long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[index];
            if (elapsedMinutes >= 5L) {
                if (this.growthStages[index] > 0 && this.growthStages[index] <= 3) {
                    int value = (int)(elapsedMinutes / 5L);
                    int index2 = 0;
                    while (index2 < value) {
                        if (this.growthStages[index] != 0) {
                            int value2 = index;
                            this.growthStages[value2] = this.growthStages[value2] - 1;
                            this.lastUpdateTicks[index] = Server.getElapsedMinutes();
                            ++index2;
                            continue;
                        }
                        break;
                    }
                } else {
                    SpecialCropDefinition specialCropDefinition = SpecialCropDefinition.forSeedId(this.cropIds[index]);
                    if (specialCropDefinition != null && !this.shouldStopGrowthCycle(index)) {
                        int growthCycleTicks = (int)(elapsedMinutes / (long)specialCropDefinition.getGrowthCycleTicks());
                        int value3 = this.growthStages[index] - 4;
                        if ((growthCycleTicks -= value3) > 0) {
                            value3 = 0;
                            while (value3 < growthCycleTicks) {
                                if (this.growthStages[index] == 4) {
                                    int value4 = index;
                                    this.growthStages[value4] = this.growthStages[value4] + 1;
                                } else {
                                    int value5 = index;
                                    SpecialCropPatchManager specialCropPatchManager = this;
                                    if (specialCropPatchManager.patchStates[value5] == 1 && GameUtil.randomInt(2) == 0) {
                                        specialCropPatchManager.patchStates[value5] = 2;
                                    }
                                    if (specialCropPatchManager.patchStates[value5] != 1 && specialCropPatchManager.patchStates[value5] != 2) {
                                        SpecialCropDefinition specialCropDefinition2;
                                        if (specialCropPatchManager.patchStates[value5] == 5 && specialCropPatchManager.growthStages[value5] != 2) {
                                            specialCropPatchManager.patchStates[value5] = 0;
                                        }
                                        if (specialCropPatchManager.patchStates[value5] == 0 && specialCropPatchManager.growthStages[value5] >= 4 && (specialCropDefinition2 = SpecialCropDefinition.forSeedId(specialCropPatchManager.cropIds[value5])) != null) {
                                            double diseaseChance = specialCropPatchManager.diseaseChanceMultipliers[value5] * specialCropDefinition2.getDiseaseChance();
                                            double value6 = diseaseChance * 100.0;
                                            int value7 = (int)value6;
                                            if (GameUtil.randomInclusive(100) <= value7 && ServerSettings.diseasingEnabled) {
                                                specialCropPatchManager.patchStates[value5] = 1;
                                            }
                                        }
                                    }
                                    if (this.patchStates[index] == 2) break;
                                    if (this.patchStates[index] != 1) {
                                        int value8 = index;
                                        this.growthStages[value8] = this.growthStages[value8] + 1;
                                    }
                                    if (this.shouldStopGrowthCycle(index)) break;
                                    if (this.growthStages[index] <= specialCropDefinition.getGrowthStageCount() + (specialCropDefinition == SpecialCropDefinition.BELLADONNA ? 3 : -2) && this.growthStages[index] == specialCropDefinition.getGrowthStageCount() - 2 && specialCropDefinition.getHealthCheckConfigStage() != -1) {
                                        this.growthStages[index] = specialCropDefinition.getGrowthStageCount() + 4;
                                        this.patchStates[index] = 3;
                                        break;
                                    }
                                }
                                ++value3;
                            }
                        }
                    }
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
        SpecialCropDefinition specialCropDefinition = SpecialCropDefinition.forSeedId(this.cropIds[value2]);
        if (specialCropDefinition == null) {
            return;
        }
        long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[value2];
        int growthCycleTicks = (int)(elapsedMinutes / (long)specialCropDefinition.getGrowthCycleTicks());
        this.growthStages[value2] = growthCycleTicks + 4;
        this.refreshConfig();
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        SpecialCropPatch specialCropPatch = SpecialCropPatch.forPosition(new Position(value5, value22));
        if (specialCropPatch == null || value32 != 5341 && value32 != 952) {
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
        if (this.growthStages[specialCropPatch.getIndex()] == 3) {
            return true;
        }
        if (this.growthStages[specialCropPatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new SpecialCropClearingTask(this, value32, specialCropPatch), value4);
        return true;
    }

    public final boolean plantSeed(int value4, int value22, int value32) {
        SpecialCropPatch specialCropPatch = SpecialCropPatch.forPosition(new Position(value4, value22));
        SpecialCropDefinition specialCropDefinition = SpecialCropDefinition.forSeedId(value32);
        if (specialCropPatch == null || specialCropDefinition == null || specialCropPatch.getObjectId() != value32) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[specialCropPatch.getIndex()] != 3) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't plant a seed here.");
            return false;
        }
        if (specialCropDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + specialCropDefinition.getRequiredLevel() + " to plant this seed.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5343)) {
            this.player.getDialogueManager().showOneLineStatement("You need a seed dibber to plant seed here.");
            return true;
        }
        if (this.player.getInventoryManager().getItemAmount(specialCropDefinition.getSeedId()) < specialCropDefinition.getSeedAmount()) {
            this.player.getDialogueManager().showOneLineStatement("You need atleast " + specialCropDefinition.getSeedAmount() + " seeds to plant here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2291);
        Player player = this.player;
        player.packetSender.sendSoundEffect(1321, 1, 0);
        this.growthStages[specialCropPatch.getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32, specialCropDefinition.getSeedAmount()));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new SpecialCropPlantingTask(this, specialCropPatch, value32, specialCropDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int value5, int value22) {
        Object value3 = SpecialCropPatch.forPosition(new Position(value5, value22));
        if (value3 == null) {
            return false;
        }
        SpecialCropDefinition specialCropDefinition = SpecialCropDefinition.forSeedId(this.cropIds[((SpecialCropPatch)value3).getIndex()]);
        if (specialCropDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        this.player.setActionLocked(true);
        this.player.getUpdateState().setAnimation(832);
        int value4 = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new SpecialCropHarvestTask(this, value4, (SpecialCropPatch)((Object)value3), specialCropDefinition));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        SpecialCropPatch specialCropPatch = SpecialCropPatch.forPosition(new Position(value4, value22));
        if (specialCropPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[specialCropPatch.getIndex()] != 3 || this.patchStates[specialCropPatch.getIndex()] == 5) {
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
        CycleEventHandler.getInstance().schedule(this.player, new SpecialCropCompostTask(this, specialCropPatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        SpecialCropPatch specialCropPatch = SpecialCropPatch.forPosition(new Position(value3, value22));
        if (specialCropPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        SpecialCropGrowthDefinition specialCropGrowthDefinition = SpecialCropGrowthDefinition.forCropId(this.cropIds[specialCropPatch.getIndex()]);
        Object index = SpecialCropDefinition.forSeedId(this.cropIds[specialCropPatch.getIndex()]);
        if (this.patchStates[specialCropPatch.getIndex()] == 1) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[specialCropPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[specialCropPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant has fully grown. You can check it's health", "to gain some farming experiences.");
            return true;
        }
        if (this.growthStages[specialCropPatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[specialCropPatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[specialCropPatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[specialCropPatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (specialCropGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new SpecialCropInspectTask(this, specialCropPatch, specialCropGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = SpecialCropPatch.forPosition(new Position(skillId, value3));
        if (value2 == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(8);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value4, int value22) {
        Object value3 = SpecialCropPatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        SpecialCropDefinition specialCropDefinition = SpecialCropDefinition.forSeedId(this.cropIds[((SpecialCropPatch)value3).getIndex()]);
        if (specialCropDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((SpecialCropPatch)value3).getIndex()] != 2) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("special2", ((SpecialCropPatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = SpecialCropDefinition.forSeedId(this.cropIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((SpecialCropDefinition)value).getGrowthCycleTicks() * value2);
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
        Object value4 = SpecialCropPatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 6036) {
            return false;
        }
        SpecialCropDefinition specialCropDefinition = SpecialCropDefinition.forSeedId(this.cropIds[((SpecialCropPatch)value4).getIndex()]);
        if (specialCropDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((SpecialCropPatch)value4).getIndex()] != 1) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.player.setActionLocked(true);
        this.patchStates[((SpecialCropPatch)value4).getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new SpecialCropCureTask(this), 7);
        return true;
    }

    private void resetPatch(int value2) {
        this.cropIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
    }

    static Player getPlayer(SpecialCropPatchManager specialCropPatchManager) {
        return specialCropPatchManager.player;
    }

    static void resetPatch(SpecialCropPatchManager specialCropPatchManager, int value2) {
        specialCropPatchManager.resetPatch(value2);
    }
}
