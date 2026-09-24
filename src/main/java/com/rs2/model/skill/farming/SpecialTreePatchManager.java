package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialTreeClearingTask;
import com.rs2.model.skill.farming.SpecialTreeCompostTask;
import com.rs2.model.skill.farming.SpecialTreeCureTask;
import com.rs2.model.skill.farming.SpecialTreeDefinition;
import com.rs2.model.skill.farming.SpecialTreeGrowthDefinition;
import com.rs2.model.skill.farming.SpecialTreeHarvestTask;
import com.rs2.model.skill.farming.SpecialTreeInspectTask;
import com.rs2.model.skill.farming.SpecialTreePatch;
import com.rs2.model.skill.farming.SpecialTreePlantingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class SpecialTreePatchManager {
    private Player player;
    public int[] growthStages = new int[4];
    public int[] treeIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};
    public boolean[] calquatRegrowthFlags = new boolean[4];

    public SpecialTreePatchManager(Player player) {
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
            value5 = SpecialTreeDefinition.forSaplingId(value3);
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
                    value = value5 == null ? -1 : (SpecialTreePatchManager.getConfigStageForPatchState(value2, (SpecialTreeDefinition)((Object)value5), value4) == 3 ? ((SpecialTreeDefinition)((Object)value5)).getHealthCheckConfigStage() : SpecialTreePatchManager.getConfigStageForPatchState(value2, (SpecialTreeDefinition)((Object)value5), value4));
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value6 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(507, value6);
    }

    private static int getConfigStageForPatchState(int state, SpecialTreeDefinition specialTreeDefinition, int value22) {
        value22 = specialTreeDefinition.getConfigStartStage() + specialTreeDefinition.getConfigStageOffset(value22 - 4);
        switch (state) {
            case 0: {
                return value22;
            }
            case 1: {
                return value22 + specialTreeDefinition.getDiseasedConfigOffset();
            }
            case 2: {
                return value22 + specialTreeDefinition.getDeadConfigOffset();
            }
            case 3: {
                return specialTreeDefinition.getHealthCheckConfigStage();
            }
        }
        return -1;
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.treeIds.length) {
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
                    SpecialTreeDefinition specialTreeDefinition = SpecialTreeDefinition.forSaplingId(this.treeIds[index]);
                    if (specialTreeDefinition != null && !this.shouldStopGrowthCycle(index)) {
                        if (specialTreeDefinition == SpecialTreeDefinition.CALQUAT && this.calquatRegrowthFlags[index] && this.growthStages[index] < 18) {
                            int value3 = index;
                            this.growthStages[value3] = this.growthStages[value3] + 1;
                        } else {
                            int value4 = this.growthStages[index] - 4;
                            int growthCycleTicks = FarmingPatchUtils.getGrowthCycleTarget(this.player, this.lastUpdateTicks[index], specialTreeDefinition.getGrowthCycleTicks(), value4);
                            if ((growthCycleTicks -= value4) > 0) {
                                value4 = 0;
                                while (value4 < growthCycleTicks) {
                                    if (this.growthStages[index] == 4) {
                                        int value5 = index;
                                        this.growthStages[value5] = this.growthStages[value5] + 1;
                                    } else {
                                        int value6 = index;
                                        SpecialTreePatchManager specialTreePatchManager = this;
                                        if (specialTreePatchManager.patchStates[value6] == 1 && GameUtil.randomInt(2) == 0) {
                                            specialTreePatchManager.patchStates[value6] = 2;
                                        }
                                        if (specialTreePatchManager.patchStates[value6] != 1 && specialTreePatchManager.patchStates[value6] != 2) {
                                            SpecialTreeDefinition specialTreeDefinition2;
                                            if (specialTreePatchManager.patchStates[value6] == 5 && specialTreePatchManager.growthStages[value6] != 2) {
                                                specialTreePatchManager.patchStates[value6] = 0;
                                            }
                                            if (specialTreePatchManager.patchStates[value6] == 0 && specialTreePatchManager.growthStages[value6] >= 4 && !specialTreePatchManager.calquatRegrowthFlags[value6] && (specialTreeDefinition2 = SpecialTreeDefinition.forSaplingId(specialTreePatchManager.treeIds[value6])) != null) {
                                                double diseaseChance = specialTreePatchManager.diseaseChanceMultipliers[value6] * specialTreeDefinition2.getDiseaseChance();
                                                double value7 = diseaseChance * 100.0;
                                                int value8 = (int)value7;
                                                if (GameUtil.randomInclusive(100) < value8 && ServerSettings.diseasingEnabled) {
                                                    specialTreePatchManager.patchStates[value6] = 1;
                                                }
                                            }
                                        }
                                        if (this.patchStates[index] == 2) break;
                                        if (this.patchStates[index] != 1) {
                                            int value9 = index;
                                            this.growthStages[value9] = this.growthStages[value9] + 1;
                                        }
                                        if (this.shouldStopGrowthCycle(index)) break;
                                        if (this.growthStages[index] >= specialTreeDefinition.getGrowthCycleCount() + 4) {
                                            this.growthStages[index] = specialTreeDefinition.getGrowthCycleCount() + 4;
                                            this.patchStates[index] = 3;
                                            break;
                                        }
                                    }
                                    ++value4;
                                }
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
        SpecialTreeDefinition specialTreeDefinition = SpecialTreeDefinition.forSaplingId(this.treeIds[value2]);
        if (specialTreeDefinition == null) {
            return;
        }
        long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[value2];
        int growthCycleTicks = (int)(elapsedMinutes / (long)specialTreeDefinition.getGrowthCycleTicks());
        this.growthStages[value2] = Math.min(growthCycleTicks, specialTreeDefinition.getGrowthCycleCount()) + 4;
        this.refreshConfig();
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        SpecialTreePatch specialTreePatch = SpecialTreePatch.forPosition(new Position(value5, value22));
        if (specialTreePatch == null || value32 != 5341 && value32 != 952) {
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
        if (this.growthStages[specialTreePatch.getIndex()] == 3) {
            return true;
        }
        if (this.growthStages[specialTreePatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new SpecialTreeClearingTask(this, value32, specialTreePatch), value4);
        return true;
    }

    public final boolean plantSapling(int value5, int value22, int value32) {
        Object value4 = SpecialTreePatch.forPosition(new Position(value5, value22));
        SpecialTreeDefinition specialTreeDefinition = SpecialTreeDefinition.forSaplingId(value32);
        if (value4 == null || specialTreeDefinition == null || ((SpecialTreePatch)((Object)value4)).getObjectId() != value32) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if ((this.growthStages[0] > 3 || this.growthStages[2] > 3 || this.growthStages[3] > 3) && ((SpecialTreePatch)((Object)value4)).getIndex() != 1) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("You already have a spirit tree planted somewhere else.");
            return true;
        }
        if (this.growthStages[((SpecialTreePatch)((Object)value4)).getIndex()] != 3) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("You can't plant a sapling here.");
            return true;
        }
        if (specialTreeDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + specialTreeDefinition.getRequiredLevel() + " to plant this sapling.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5325)) {
            this.player.getDialogueManager().showOneLineStatement("You need a trowel to plant the sapling here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2272);
        this.growthStages[((SpecialTreePatch)((Object)value4)).getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new SpecialTreePlantingTask(this, (SpecialTreePatch)((Object)value4), value32, specialTreeDefinition), 3);
        return true;
    }

    public final boolean handleSpecialTreeObject(int objectId, int value4) {
        Object value2 = SpecialTreePatch.forPosition(new Position(objectId, value4));
        if (value2 == null) {
            return false;
        }
        SpecialTreeDefinition specialTreeDefinition = SpecialTreeDefinition.forSaplingId(this.treeIds[((SpecialTreePatch)value2).getIndex()]);
        if (specialTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (specialTreeDefinition == SpecialTreeDefinition.SPIRIT_TREE && this.patchStates[((SpecialTreePatch)value2).getIndex()] != 3) {
            value2 = this;
            DialogueManager.startDialogue(((SpecialTreePatchManager)value2).player, 3636);
            return true;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        if (specialTreeDefinition == SpecialTreeDefinition.CALQUAT && this.player.getPlayerRights() < 2) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This feature is currently disabled.");
            return true;
        }
        this.player.getUpdateState().setAnimation(832);
        this.player.setActionLocked(true);
        int value3 = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new SpecialTreeHarvestTask(this, value3, (SpecialTreePatch)((Object)value2), specialTreeDefinition));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        SpecialTreePatch specialTreePatch = SpecialTreePatch.forPosition(new Position(value4, value22));
        if (specialTreePatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[specialTreePatch.getIndex()] != 3 || this.patchStates[specialTreePatch.getIndex()] == 5) {
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
        CycleEventHandler.getInstance().schedule(this.player, new SpecialTreeCompostTask(this, specialTreePatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        SpecialTreePatch specialTreePatch = SpecialTreePatch.forPosition(new Position(value3, value22));
        if (specialTreePatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        SpecialTreeGrowthDefinition specialTreeGrowthDefinition = SpecialTreeGrowthDefinition.forTreeId(this.treeIds[specialTreePatch.getIndex()]);
        Object index = SpecialTreeDefinition.forSaplingId(this.treeIds[specialTreePatch.getIndex()]);
        if (this.patchStates[specialTreePatch.getIndex()] == 1) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[specialTreePatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[specialTreePatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant has fully grown. You can check it's health", "to gain some farming experiences.");
            return true;
        }
        if (this.growthStages[specialTreePatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[specialTreePatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[specialTreePatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[specialTreePatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is one of the special patches. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (specialTreeGrowthDefinition != null && index != null) {
            index = this.player;
            ((Player)index).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new SpecialTreeInspectTask(this, specialTreePatch, specialTreeGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value3) {
        Object value2 = SpecialTreePatch.forPosition(new Position(skillId, value3));
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
        Object value3 = SpecialTreePatch.forPosition(new Position(value4, value22));
        if (value3 == null) {
            return false;
        }
        SpecialTreeDefinition specialTreeDefinition = SpecialTreeDefinition.forSaplingId(this.treeIds[((SpecialTreePatch)value3).getIndex()]);
        if (specialTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((SpecialTreePatch)value3).getIndex()] != 2) {
            value3 = this.player;
            ((Player)value3).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("special1", ((SpecialTreePatch)value3).getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            Object value = SpecialTreeDefinition.forSaplingId(this.treeIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value2 = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(((SpecialTreeDefinition)value).getGrowthCycleTicks() * value2);
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
        Object value4 = SpecialTreePatch.forPosition(new Position(value5, value22));
        if (value4 == null || value32 != 6036) {
            return false;
        }
        SpecialTreeDefinition specialTreeDefinition = SpecialTreeDefinition.forSaplingId(this.treeIds[((SpecialTreePatch)value4).getIndex()]);
        if (specialTreeDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[((SpecialTreePatch)value4).getIndex()] != 1) {
            value4 = this.player;
            ((Player)value4).packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.player.setActionLocked(true);
        this.patchStates[((SpecialTreePatch)value4).getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new SpecialTreeCureTask(this), 7);
        return true;
    }

    private void resetPatch(int value2) {
        this.treeIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
        this.calquatRegrowthFlags[value2] = false;
    }

    static Player getPlayer(SpecialTreePatchManager specialTreePatchManager) {
        return specialTreePatchManager.player;
    }

    static void resetPatch(SpecialTreePatchManager specialTreePatchManager, int value2) {
        specialTreePatchManager.resetPatch(value2);
    }
}

