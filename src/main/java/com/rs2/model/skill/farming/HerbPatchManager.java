package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HerbClearingTask;
import com.rs2.model.skill.farming.HerbCompostTask;
import com.rs2.model.skill.farming.HerbCureTask;
import com.rs2.model.skill.farming.HerbDefinition;
import com.rs2.model.skill.farming.HerbGrowthDefinition;
import com.rs2.model.skill.farming.HerbHarvestTask;
import com.rs2.model.skill.farming.HerbInspectTask;
import com.rs2.model.skill.farming.HerbPatch;
import com.rs2.model.skill.farming.HerbPlantingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class HerbPatchManager {
    private Player player;
    public int[] harvestAmounts = new int[4];
    public int[] growthStages = new int[4];
    public int[] cropIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};

    public HerbPatchManager(Player player) {
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
            HerbPatchManager herbPatchManager = this;
            HerbDefinition herbDefinition = HerbDefinition.forSeedId(value4);
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
                    int value6;
                    if (herbDefinition == null) {
                        value = -1;
                        break;
                    }
                    if (herbPatchManager.cropIds[value2] == 6311) {
                        if (value3 == 1) {
                            value = herbPatchManager.growthStages[value2] + 193;
                            break;
                        }
                        if (value3 == 2) {
                            value = herbPatchManager.growthStages[value2] + 196;
                            break;
                        }
                    }
                    if (value3 == 1) {
                        value = value5 + 123;
                        break;
                    }
                    if (value3 == 2) {
                        value = value5 + 165;
                        break;
                    }
                    int value7 = value5 - 4;
                    value5 = value3;
                    switch (value5) {
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
                    value = (value6 << 6) + herbDefinition.getConfigStartStage() + value7;
                }
            }
            integerValues[index] = value;
            ++index;
        }
        int value8 = (integerValues[0] << 16) + (integerValues[1] << 8 << 16) + integerValues[2] + (integerValues[3] << 8);
        Player player = this.player;
        player.packetSender.sendConfig(515, value8);
    }

    public final void processGrowth() {
        int index = 0;
        while (index < this.cropIds.length) {
            long elapsedMinutes = Server.getElapsedMinutes() - this.lastUpdateTicks[index];
            if (elapsedMinutes >= 5L) {
                Object value;
                int value2;
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
                if ((value = HerbDefinition.forSeedId(this.cropIds[index])) != null && !this.shouldStopGrowthCycle(index)) {
                    value2 = (int)(elapsedMinutes / (long)((HerbDefinition)((Object)value)).getGrowthCycleTicks());
                    int value5 = this.growthStages[index] - 4;
                    if ((value5 = value2 - value5) > 0) {
                        int index2 = 0;
                        while (index2 < value5) {
                            if (this.growthStages[index] == 4) {
                                int value6 = index;
                                this.growthStages[value6] = this.growthStages[value6] + 1;
                            } else {
                                value2 = index;
                                value = this;
                                if (((HerbPatchManager)value).patchStates[value2] == 1 && GameUtil.randomInt(2) == 0) {
                                    ((HerbPatchManager)value).patchStates[value2] = 2;
                                }
                                if (((HerbPatchManager)value).patchStates[value2] != 1 && ((HerbPatchManager)value).patchStates[value2] != 2) {
                                    HerbDefinition herbDefinition;
                                    if (((HerbPatchManager)value).patchStates[value2] == 4 && ((HerbPatchManager)value).growthStages[value2] != 3) {
                                        ((HerbPatchManager)value).patchStates[value2] = 0;
                                    }
                                    boolean enabled = ((HerbPatchManager)value).hasReachedFinalConfigStage(value2);
                                    if (((HerbPatchManager)value).patchStates[value2] == 0 && ((HerbPatchManager)value).growthStages[value2] >= 4 && ((HerbPatchManager)value).growthStages[value2] <= 7 && !enabled && (herbDefinition = HerbDefinition.forSeedId(((HerbPatchManager)value).cropIds[value2])) != null) {
                                        double diseaseChance = ((HerbPatchManager)value).diseaseChanceMultipliers[value2] * herbDefinition.getDiseaseChance();
                                        double value7 = diseaseChance * 100.0;
                                        int value8 = (int)value7;
                                        if (GameUtil.randomInclusive(100) <= value8 && ServerSettings.diseasingEnabled) {
                                            ((HerbPatchManager)value).patchStates[value2] = 1;
                                        }
                                    }
                                }
                                if (this.patchStates[index] == 2) break;
                                if (this.patchStates[index] != 1) {
                                    int value9 = index;
                                    this.growthStages[value9] = this.growthStages[value9] + 1;
                                }
                                if (this.shouldStopGrowthCycle(index)) break;
                            }
                            ++index2;
                        }
                    }
                }
            }
            ++index;
        }
        this.refreshConfig();
    }

    private boolean shouldStopGrowthCycle(int value2) {
        return this.lastUpdateTicks[value2] == 0L || this.patchStates[value2] == 2 || this.hasReachedFinalConfigStage(value2);
    }

    private boolean hasReachedFinalConfigStage(int value3) {
        int value2 = this.growthStages[value3] - 4;
        HerbDefinition herbDefinition = HerbDefinition.forSeedId(this.cropIds[value3]);
        if (herbDefinition.getConfigEndStage() == herbDefinition.getConfigStartStage() + value2) {
            this.patchStates[value3] = 0;
            return true;
        }
        return false;
    }

    public final boolean clearPatch(int value5, int value22, int value32) {
        int value4;
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value5, value22));
        if (herbPatch == null || value32 != 5341 && value32 != 952) {
            return false;
        }
        if (this.growthStages[herbPatch.getIndex()] == 3) {
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
        if (this.growthStages[herbPatch.getIndex()] <= 3) {
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
        CycleEventHandler.getInstance().schedule(this.player, new HerbClearingTask(this, value32, herbPatch), value4);
        return true;
    }

    public final boolean plantSeed(int value4, int value22, int value32) {
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value4, value22));
        HerbDefinition herbDefinition = HerbDefinition.forSeedId(value32);
        if (herbPatch == null || herbDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[herbPatch.getIndex()] != 3) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't plant a seed here.");
            return false;
        }
        if (herbDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + herbDefinition.getRequiredLevel() + " to plant this seed.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5343)) {
            this.player.getDialogueManager().showOneLineStatement("You need a seed dibber to plant seed here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2291);
        Player player = this.player;
        player.packetSender.sendSoundEffect(1321, 1, 0);
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new HerbPlantingTask(this, herbPatch, value32, herbDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int value4, int value22) {
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value4, value22));
        if (herbPatch == null) {
            return false;
        }
        HerbDefinition herbDefinition = HerbDefinition.forSeedId(this.cropIds[herbPatch.getIndex()]);
        if (herbDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5329)) {
            this.player.getDialogueManager().showOneLineStatement("You need secateurs to harvest here.");
            return true;
        }
        int value3 = this.player.nextActionSequence();
        this.player.getUpdateState().setAnimation(2282);
        this.player.setActiveCycleEvent(new HerbHarvestTask(this, value3, herbDefinition, herbPatch));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 3);
        return true;
    }

    public final boolean compostPatch(int value4, int value22, int value32) {
        if (value32 != 6032 && value32 != 6034) {
            return false;
        }
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value4, value22));
        if (herbPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[herbPatch.getIndex()] != 3 || this.patchStates[herbPatch.getIndex()] == 4) {
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
        CycleEventHandler.getInstance().schedule(this.player, new HerbCompostTask(this, herbPatch, value32), 7);
        return true;
    }

    public final boolean inspectPatch(int value3, int value22) {
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value3, value22));
        if (herbPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        HerbGrowthDefinition herbGrowthDefinition = HerbGrowthDefinition.forCropId(this.cropIds[herbPatch.getIndex()]);
        HerbDefinition herbDefinition = HerbDefinition.forSeedId(this.cropIds[herbPatch.getIndex()]);
        if (this.patchStates[herbPatch.getIndex()] == 1) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[herbPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.growthStages[herbPatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is an herb patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[herbPatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[herbPatch.getIndex()] == 0.22) {
                this.player.getDialogueManager().showTwoLineStatement("This is an herb patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[herbPatch.getIndex()] == 0.52) {
                this.player.getDialogueManager().showTwoLineStatement("This is an herb patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is an herb patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (herbGrowthDefinition != null && herbDefinition != null) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new HerbInspectTask(this, herbPatch, herbGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int skillId, int value2) {
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(skillId, value2));
        if (herbPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(7);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int value3, int value22) {
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value3, value22));
        if (herbPatch == null) {
            return false;
        }
        HerbDefinition herbDefinition = HerbDefinition.forSeedId(this.cropIds[herbPatch.getIndex()]);
        if (herbDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[herbPatch.getIndex()] != 2) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("herb", herbPatch.getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean enabled2) {
        if (enabled2) {
            HerbDefinition herbDefinition = HerbDefinition.forSeedId(this.cropIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int value = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(herbDefinition.getGrowthCycleTicks() * value);
            Player player = this.player;
            player.packetSender.sendGameMessage("You succesfully resurrected the crop.");
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

    public final boolean curePatch(int value4, int value22, int value32) {
        HerbPatch herbPatch = HerbPatch.forPosition(new Position(value4, value22));
        if (herbPatch == null || value32 != 6036) {
            return false;
        }
        HerbDefinition herbDefinition = HerbDefinition.forSeedId(this.cropIds[herbPatch.getIndex()]);
        if (herbDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[herbPatch.getIndex()] != 1) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value32));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new HerbCureTask(this, herbPatch), 7);
        return true;
    }

    private void resetPatch(int value2) {
        this.cropIds[value2] = 0;
        this.patchStates[value2] = 0;
        this.diseaseChanceMultipliers[value2] = 1.0;
        this.harvestAmounts[value2] = 3;
    }

    static Player getPlayer(HerbPatchManager herbPatchManager) {
        return herbPatchManager.player;
    }

    static void resetPatch(HerbPatchManager herbPatchManager, int value2) {
        herbPatchManager.resetPatch(value2);
    }
}
