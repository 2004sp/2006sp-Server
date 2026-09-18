package com.rs2.model.skill.mining;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.SkillRandomEventNpc;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.mining.MiningManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import com.rs2.util.TextUtil;

public final class MiningTask
extends CycleEvent {
    private MiningManager manager;
    private final int actionSequence;
    private final int rockObjectId;
    private final int x;
    private final int y;
    private final GatheringToolDefinition gatheringTool;
    private final int mineChanceLow;
    private final int mineChanceHigh;
    private final int oreItemId;
    private final int baseExperience;
    private final double depletionChance;
    private final int respawnTicks;

    public MiningTask(MiningManager miningManager, int actionSequence, int rockObjectId, int x, int y, GatheringToolDefinition gatheringToolDefinition, int mineChanceLow, int mineChanceHigh, int oreItemId, int baseExperience, double depletionChance, int respawnTicks) {
        this.manager = miningManager;
        this.actionSequence = actionSequence;
        this.rockObjectId = rockObjectId;
        this.x = x;
        this.y = y;
        this.gatheringTool = gatheringToolDefinition;
        this.mineChanceLow = mineChanceLow;
        this.mineChanceHigh = mineChanceHigh;
        this.oreItemId = oreItemId;
        this.baseExperience = baseExperience;
        this.depletionChance = depletionChance;
        this.respawnTicks = respawnTicks;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int value;
        boolean enabled;
        executeControlExit1: {
            executeControlExit2: {
                if (!MiningManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence)) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("mining stop invalid-sequence player=" + GameplayTrace.describe(MiningManager.getPlayer(this.manager)) + " seq=" + this.actionSequence + " rockObjectId=" + this.rockObjectId + " x=" + this.x + " y=" + this.y);
                    }
                    if (MiningManager.getPlayer((MiningManager)this.manager).botEnabled) {
                        MiningManager.getPlayer((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.getPlayer(this.manager));
                    }
                    cycleEventContainer.stop();
                    return;
                }
                enabled = false;
                value = 0;
                if (MiningManager.getRestoredRockObjectId(this.rockObjectId) != -1) break executeControlExit2;
                int randomEventRockObjectId = MiningManager.getRandomEventRockObjectId(this.rockObjectId, new Position(this.x, this.y, MiningManager.getPlayer(this.manager).getPosition().getPlane()));
                ObjectManager.getInstance();
                DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(this.x, this.y, MiningManager.getPlayer(this.manager).getPosition().getPlane());
                if (dynamicObject == null || randomEventRockObjectId == -1 || dynamicObject.getWorldObject().getObjectId() != randomEventRockObjectId) break executeControlExit1;
            }
            value = 1;
        }
        if (value != 0) {
            if (MiningManager.getPlayer((MiningManager)this.manager).gatheringHazardCounter >= 2) {
                ItemCombinationHandler.breakGatheringTool(MiningManager.getPlayer(this.manager), 14);
                Player player = MiningManager.getPlayer(this.manager);
                player.packetSender.sendStillGraphicToNearbyPlayers(157, this.x, this.y, 0, 1);
                player = MiningManager.getPlayer(this.manager);
                player.packetSender.sendGameMessage("Your pickaxe has been broken by the rock!");
                MiningManager.getPlayer(this.manager).applyDirectHit(GameUtil.randomInclusive(MiningManager.getPlayer(this.manager).getMaxHitpoints() / 20) + MiningManager.getPlayer(this.manager).getMaxHitpoints() / 20 + 1, HitType.NORMAL);
                MiningManager.getPlayer(this.manager).getUpdateState().setAnimation(-1);
                player = MiningManager.getPlayer(this.manager);
                player.packetSender.sendSoundEffect(42, 1, 0);
                if (MiningManager.getPlayer((MiningManager)this.manager).botEnabled) {
                    MiningManager.getPlayer((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.getPlayer(this.manager));
                }
                cycleEventContainer.stop();
                return;
            }
            MiningManager.getPlayer(this.manager).getUpdateState().setAnimation(this.gatheringTool.getGatherAnimationId());
            ++MiningManager.getPlayer((MiningManager)this.manager).gatheringHazardCounter;
            return;
        }
        ObjectManager.getInstance();
        DynamicObject dynamicObject2 = ObjectManager.findDynamicObjectAt(this.x, this.y, MiningManager.getPlayer(this.manager).getPosition().getPlane());
        if (dynamicObject2 != null && dynamicObject2.getWorldObject().getObjectId() != this.rockObjectId) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("mining stop depleted-before-roll player=" + GameplayTrace.describe(MiningManager.getPlayer(this.manager)) + " seq=" + this.actionSequence + " rockObjectId=" + this.rockObjectId + " dynamicId=" + dynamicObject2.getWorldObject().getObjectId() + " x=" + this.x + " y=" + this.y);
            }
            if (MiningManager.getPlayer(this.manager).getQuestState(0) != 1) {
                MiningManager.getPlayer(this.manager).getDialogueManager().showOneLineStatement("There is no more ore in this rock.");
                MiningManager.getPlayer(this.manager).setInteractionTargetId(0);
            }
            Player player2 = MiningManager.getPlayer(this.manager);
            player2.packetSender.sendGameMessage("There is no more ore in this rock.");
            player2 = MiningManager.getPlayer(this.manager);
            player2.packetSender.sendSoundEffect(429, 1, 0);
            if (MiningManager.getPlayer((MiningManager)this.manager).botEnabled) {
                MiningManager.getPlayer(this.manager).interactWithBotObjectTargets(MiningManager.getPlayer((MiningManager)this.manager).botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        if (SkillActionHelper.shouldTriggerRandomEvent(MiningManager.getPlayer(this.manager)) && !MiningManager.getPlayer((MiningManager)this.manager).botEnabled && !MiningManager.getPlayer(this.manager).isInTutorialIsland()) {
            GameplayHelper.spawnSkillRandomEventNpc(MiningManager.getPlayer(this.manager), SkillRandomEventNpc.ROCK_GOLEM);
        }
        MiningManager.getPlayer(this.manager).getUpdateState().setAnimation(this.gatheringTool.getGatherAnimationId());
        int value2 = 256;
        if (MiningManager.getPlayer(this.manager).hasChargedAmuletOfGloryEquipped()) {
            value2 = 86;
        }
        if (this.rockObjectId != 2111 && GameUtil.randomInt(value2) == 0 && MiningManager.getPlayer(this.manager).getQuestState(0) == 1) {
            String[] stringValues = new String[]{"10/4681", "1/1024", "1/2048", "1/4096", "1/16384"};
            String[] stringValues2 = new String[]{"1/157", "1/344", "1/688", "1/1376", "1/5504"};
            int[] integerValues = new int[]{65000, 1623, 1621, 1619, 1617};
            int player3 = GameUtil.rollFractionWeightIndex(MiningManager.getPlayer(this.manager).hasChargedAmuletOfGloryEquipped() ? stringValues2 : stringValues);
            int value3 = integerValues[player3];
            if (value3 != 65000) {
                ItemStack itemStack = new ItemStack(value3, 1);
                MiningManager.getPlayer(this.manager).getInventoryManager().addItem(itemStack);
                String definition = itemStack.getDefinition().getName().replace("Uncut ", "");
                Player player = MiningManager.getPlayer(this.manager);
                player.packetSender.sendGameMessage("You just found " + TextUtil.prependIndefiniteArticle(definition) + "!");
                if (!MiningManager.getPlayer(this.manager).getInventoryManager().canAddItem(itemStack)) {
                    if (MiningManager.getPlayer((MiningManager)this.manager).botEnabled) {
                        MiningManager.getPlayer((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.getPlayer(this.manager));
                    }
                    cycleEventContainer.stop();
                    return;
                }
            }
            return;
        }
        value = this.mineChanceLow;
        int value4 = this.mineChanceHigh;
        if (this.rockObjectId == 2111 && MiningManager.getPlayer(this.manager).hasChargedAmuletOfGloryEquipped()) {
            value = 84;
            value4 = 210;
        }
        if (GameUtil.rollLevelScaledChance(value, value4, MiningManager.getPlayer(this.manager).getSkillManager().getCurrentLevels()[14])) {
            String text;
            if (this.rockObjectId == 2111) {
                String[] stringValues3 = new String[]{"1000/2133", "1000/4267", "1000/8533", "100/1422", "10/256", "10/256", "1/32"};
                int[] integerValues2 = new int[]{1625, 1627, 1629, 1623, 1621, 1619, 1617};
                value = GameUtil.rollFractionWeightIndex(stringValues3);
                value2 = integerValues2[value];
            } else {
                value2 = MiningManager.rollMinedItemId(this.rockObjectId, this.oreItemId);
            }
            if (value2 == 444) {
                value = value2;
                if (this.manager.perfectGoldOreArea.containsExclusive(MiningManager.getPlayer(this.manager).getPosition())) {
                    value = 446;
                }
                MiningManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(value, 1));
            } else {
                MiningManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(value2, 1));
            }
            if (GameplayTrace.enabled()) {
                ItemService.getInstance();
                GameplayTrace.log("mining success player=" + GameplayTrace.describe(MiningManager.getPlayer(this.manager)) + " seq=" + this.actionSequence + " rockObjectId=" + this.rockObjectId + " minedItemId=" + value2 + " minedItem=" + ItemService.getItemName(value2) + " xp=" + MiningManager.getExperienceForMinedItem(value2, this.baseExperience) + " x=" + this.x + " y=" + this.y);
            }
            Object player4 = MiningManager.getPlayer(this.manager);
            PacketSender packetSender = ((Player)player4).packetSender;
            StringBuilder stringBuilder = new StringBuilder("You manage to mine some ");
            if (this.rockObjectId == 2111) {
                text = "gem";
            } else if (this.rockObjectId == 10946) {
                text = "sandstone";
            } else if (this.rockObjectId == 10947) {
                text = "granite";
            } else {
                ItemService.getInstance();
                text = String.valueOf(ItemService.getItemName(this.oreItemId).toLowerCase()) + ".";
            }
            packetSender.sendGameMessage(stringBuilder.append(text).toString());
            if (MiningManager.getPlayer(this.manager).getQuestState(0) != 1) {
                String text2;
                DialogueManager dialogueManager = MiningManager.getPlayer(this.manager).getDialogueManager();
                StringBuilder stringBuilder2 = new StringBuilder("You manage to mine some ");
                if (this.rockObjectId == 2111) {
                    text2 = "gem";
                } else if (this.rockObjectId == 10946) {
                    text2 = "sandstone";
                } else if (this.rockObjectId == 10947) {
                    text2 = "granite";
                } else {
                    ItemService.getInstance();
                    text2 = String.valueOf(ItemService.getItemName(this.oreItemId).toLowerCase()) + ".";
                }
                dialogueManager.showOneLineStatement(stringBuilder2.append(text2).toString());
                MiningManager.getPlayer(this.manager).getDialogueManager().finishDialogue();
                MiningManager.getPlayer(this.manager).setInteractionTargetId(0);
                if (MiningManager.getPlayer(this.manager).getQuestState(0) == 33 && this.rockObjectId == 3043) {
                    MiningManager.getPlayer(this.manager).advanceTutorialStage();
                } else if (MiningManager.getPlayer(this.manager).getQuestState(0) == 34 && this.rockObjectId == 3042) {
                    MiningManager.getPlayer(this.manager).advanceTutorialStage();
                }
                MiningManager.getPlayer(this.manager).getQuestManager().refreshQuestJournal();
            }
            MiningManager.getPlayer(this.manager).getSkillManager().addExperience(14, MiningManager.getExperienceForMinedItem(value2, this.baseExperience));
            MiningManager.getPlayer(this.manager);
            Player.rollActionReward();
            if (!ServerSettings.wcStyleMiningEnabled || ServerSettings.wcStyleMiningEnabled && GameUtil.rollChance(this.depletionChance)) {
                try {
                    int objectOrientation = SkillActionHelper.getObjectOrientation(this.rockObjectId, this.x, this.y, MiningManager.getPlayer(this.manager).getPosition().getPlane());
                    value = SkillActionHelper.getObjectType(this.rockObjectId, this.x, this.y, MiningManager.getPlayer(this.manager).getPosition().getPlane());
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("mining depleted player=" + GameplayTrace.describe(MiningManager.getPlayer(this.manager)) + " seq=" + this.actionSequence + " rockObjectId=" + this.rockObjectId + " depletedObjectId=" + MiningManager.getDepletedRockObjectId(this.rockObjectId) + " x=" + this.x + " y=" + this.y);
                    }
                    new DynamicObject(MiningManager.getDepletedRockObjectId(this.rockObjectId), this.x, this.y, MiningManager.getPlayer(this.manager).getPosition().getPlane(), value == 22 ? MiningManager.rotateDepletedRockOrientation(objectOrientation) : objectOrientation, value == 11 ? 11 : 10, this.rockObjectId, this.respawnTicks);
                }
                catch (Exception exception) {
                    player4 = exception;
                    exception.printStackTrace();
                }
                if (MiningManager.getPlayer((MiningManager)this.manager).botEnabled) {
                    MiningManager.getPlayer(this.manager).interactWithBotObjectTargets(MiningManager.getPlayer((MiningManager)this.manager).botInteractionTargetIds);
                }
                cycleEventContainer.stop();
                enabled = true;
            }
        }
        if (!enabled && !MiningManager.getPlayer(this.manager).getInventoryManager().canAddItem(new ItemStack(MiningManager.rollMinedItemId(this.rockObjectId, this.oreItemId), 1))) {
            if (MiningManager.getPlayer((MiningManager)this.manager).botEnabled) {
                MiningManager.getPlayer((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.getPlayer(this.manager));
            }
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        MiningManager.getPlayer(this.manager).getUpdateState().setAnimation(-1);
    }
}
