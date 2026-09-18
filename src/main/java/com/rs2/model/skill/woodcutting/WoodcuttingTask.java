package com.rs2.model.skill.woodcutting;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.SkillRandomEventNpc;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.woodcutting.TreeDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;

public final class WoodcuttingTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final TreeDefinition treeDefinition;
    private final int x;
    private final int y;
    private final GatheringToolDefinition gatheringTool;
    private final int treeObjectId;

    public WoodcuttingTask(Player player, int actionSequence, TreeDefinition treeDefinition, int x, int y, GatheringToolDefinition gatheringToolDefinition, int treeObjectId) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.treeDefinition = treeDefinition;
        this.x = x;
        this.y = y;
        this.gatheringTool = gatheringToolDefinition;
        this.treeObjectId = treeObjectId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int value;
        boolean enabled;
        Object value2;
        executeControlExit1: {
            if (!this.player.isCurrentActionSequence(this.actionSequence)) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("woodcutting stop invalid-sequence player=" + GameplayTrace.describe(this.player) + " seq=" + this.actionSequence + " tree=" + this.treeDefinition + " objectId=" + this.treeObjectId + " x=" + this.x + " y=" + this.y);
                }
                cycleEventContainer.stop();
                return;
            }
            value2 = new Position(this.x, this.y, this.player.getPosition().getPlane());
            TreeDefinition treeDefinition = this.treeDefinition;
            if (treeDefinition.getEntNpcIds() == null) {
                enabled = false;
            } else {
                int[] entNpcIds = treeDefinition.getEntNpcIds();
                int length = entNpcIds.length;
                int index = 0;
                while (index < length) {
                    int value3 = entNpcIds[index];
                    Npc entNpc = Npc.findByDefinitionIdAtPosition(value3, (Position)value2);
                    if (entNpc != null) {
                        enabled = true;
                        break executeControlExit1;
                    }
                    ++index;
                }
                enabled = false;
            }
        }
        if (enabled) {
            if (this.player.gatheringHazardCounter >= 2) {
                ItemCombinationHandler.breakGatheringTool(this.player, 8);
                value2 = this.player;
                ((Player)value2).packetSender.sendGameMessage("Your axe has been broken by the tree ent!");
                value2 = this.player;
                ((Player)value2).packetSender.sendSoundEffect(343, 1, 0);
                this.player.getUpdateState().setAnimation(-1);
                if (this.player.botEnabled) {
                    this.player.currentBotTask.startWalkToBank(this.player);
                }
                cycleEventContainer.stop();
                return;
            }
            this.player.getUpdateState().setAnimation(this.gatheringTool.getGatherAnimationId(), 0);
            ++this.player.gatheringHazardCounter;
            return;
        }
        ObjectManager.getInstance();
        Object dynamicObjectAt = ObjectManager.findDynamicObjectAt(this.x, this.y, this.player.getPosition().getPlane());
        if (dynamicObjectAt != null && ((DynamicObject)dynamicObjectAt).getWorldObject().getObjectId() != this.treeObjectId) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("woodcutting stop depleted-before-roll player=" + GameplayTrace.describe(this.player) + " seq=" + this.actionSequence + " tree=" + this.treeDefinition + " objectId=" + this.treeObjectId + " dynamicId=" + ((DynamicObject)dynamicObjectAt).getWorldObject().getObjectId() + " x=" + this.x + " y=" + this.y);
            }
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("The tree has run out of logs.");
            cycleEventContainer.stop();
            if (this.player.botEnabled) {
                if (this.treeDefinition == TreeDefinition.VINES) {
                    this.player.botRouteActionPending = false;
                    return;
                }
                this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
            }
            return;
        }
        dynamicObjectAt = new ItemStack(this.treeDefinition.getLogItemId(), 1);
        if (((ItemStack)dynamicObjectAt).getId() > 0 && this.player.getInventoryManager().getContainer().getFirstFreeSlot() == -1) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("woodcutting stop full-inventory player=" + GameplayTrace.describe(this.player) + " seq=" + this.actionSequence + " tree=" + this.treeDefinition + " logItemId=" + ((ItemStack)dynamicObjectAt).getId());
            }
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("Your inventory is too full to hold any more " + ((ItemStack)dynamicObjectAt).getDefinition().getName().toLowerCase() + ".");
            value2 = this.player;
            ((Player)value2).packetSender.sendSoundEffect(1878, 1, 0);
            cycleEventContainer.stop();
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return;
        }
        if (SkillActionHelper.shouldTriggerRandomEvent(this.player) && !this.player.botEnabled && !this.player.isInTutorialIsland()) {
            GameplayHelper.spawnSkillRandomEventNpc(this.player, SkillRandomEventNpc.TREE_SPIRIT);
        }
        if (this.player.isMember() && !ServerSettings.freeToPlayWorld && GameUtil.randomInt(256) == 0 && !this.player.botEnabled && !this.player.isInTutorialIsland() && ItemDefinition.isDefined(value = 5070 + GameUtil.randomInclusive(4))) {
            GroundItem groundItem = new GroundItem(new ItemStack(value), this.player);
            GroundItemManager.getInstance().spawn(groundItem);
        }
        if (GameUtil.rollLevelScaledChance(this.treeDefinition.getCutChanceLow(), this.treeDefinition.getCutChanceHigh(), this.player.getSkillManager().getCurrentLevels()[8], this.gatheringTool.getToolSpeed())) {
            this.player.getSkillManager().addExperience(8, this.treeDefinition.getExperience());
            if (((ItemStack)dynamicObjectAt).getId() > 0) {
                this.player.getInventoryManager().addItem((ItemStack)dynamicObjectAt);
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("woodcutting success player=" + GameplayTrace.describe(this.player) + " seq=" + this.actionSequence + " tree=" + this.treeDefinition + " logItemId=" + ((ItemStack)dynamicObjectAt).getId() + " logName=" + ((ItemStack)dynamicObjectAt).getDefinition().getName() + " xp=" + this.treeDefinition.getExperience() + " x=" + this.x + " y=" + this.y);
                }
                WoodcuttingTask woodcuttingTask = this;
                woodcuttingTask.player.rollActionReward();
                if (this.player.getQuestState(0) != 1) {
                    this.player.getDialogueManager().showItemMessage("You get some logs.", new ItemStack(1511));
                    this.player.getDialogueManager().finishDialogue();
                    if (this.player.getQuestState(0) == 8) {
                        value2 = this.player;
                        ((Player)value2).packetSender.sendEntityHintIcon(1, -1);
                        this.player.advanceTutorialStage();
                    }
                    this.player.getQuestManager().refreshQuestJournal();
                    this.player.setInteractionTargetId(0);
                } else if (this.treeDefinition != TreeDefinition.DRAMEN_TREE && this.treeDefinition != TreeDefinition.STRANGE_MUSICAL_TREE) {
                    value2 = this.player;
                    ((Player)value2).packetSender.sendGameMessage("You get some " + ((ItemStack)dynamicObjectAt).getDefinition().getName().toLowerCase() + ".");
                } else if (this.treeDefinition == TreeDefinition.DRAMEN_TREE) {
                    value2 = this.player;
                    ((Player)value2).packetSender.sendGameMessage("You cut a branch from the Dramen tree.");
                } else if (this.treeDefinition == TreeDefinition.STRANGE_MUSICAL_TREE) {
                    value2 = this.player;
                    ((Player)value2).packetSender.sendGameMessage("You cut a branch from the strangely musical tree.");
                }
            }
            if (this.treeDefinition != TreeDefinition.DRAMEN_TREE && this.treeDefinition != TreeDefinition.STRANGE_MUSICAL_TREE && GameUtil.rollChance(TreeDefinition.getDepletionChance(this.treeDefinition))) {
                if (this.treeDefinition != TreeDefinition.VINES) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("woodcutting depleted player=" + GameplayTrace.describe(this.player) + " seq=" + this.actionSequence + " tree=" + this.treeDefinition + " stumpId=" + this.treeDefinition.getStumpObjectId() + " objectId=" + this.treeObjectId + " x=" + this.x + " y=" + this.y);
                    }
                    value2 = this.player;
                    ((Player)value2).packetSender.sendGameMessage("The tree has run out of logs.");
                    value2 = this.player;
                    ((Player)value2).packetSender.sendSoundEffect(1312, 1, 0);
                }
                int objectOrientation = SkillActionHelper.getObjectOrientation(this.treeObjectId, this.x, this.y, this.player.getPosition().getPlane());
                int respawnTicksLow = GameUtil.randomBetweenInclusive(this.treeDefinition.getRespawnTicksLow(), this.treeDefinition.getRespawnTicksHigh());
                new DynamicObject(this.treeDefinition.getStumpObjectId(), this.x, this.y, this.player.getPosition().getPlane(), objectOrientation, 10, this.treeObjectId, respawnTicksLow, this.treeDefinition != TreeDefinition.VINES);
                cycleEventContainer.stop();
                if (this.treeDefinition == TreeDefinition.VINES) {
                    int value4 = this.y;
                    objectOrientation = this.x;
                    dynamicObjectAt = this.player;
                    if (objectOrientation > ((Entity)dynamicObjectAt).getPosition().getX() && value4 == ((Entity)dynamicObjectAt).getPosition().getY()) {
                        Object value5 = dynamicObjectAt;
                        ((Player)value5).packetSender.queueRelativeMovementStep(2, 0, true);
                    } else if (objectOrientation < ((Entity)dynamicObjectAt).getPosition().getX() && value4 == ((Entity)dynamicObjectAt).getPosition().getY()) {
                        Object value6 = dynamicObjectAt;
                        ((Player)value6).packetSender.queueRelativeMovementStep(-2, 0, true);
                    } else if (objectOrientation == ((Entity)dynamicObjectAt).getPosition().getX() && value4 < ((Entity)dynamicObjectAt).getPosition().getY()) {
                        Object value7 = dynamicObjectAt;
                        ((Player)value7).packetSender.queueRelativeMovementStep(0, -2, true);
                    } else {
                        Object value8 = dynamicObjectAt;
                        ((Player)value8).packetSender.queueRelativeMovementStep(0, 2, true);
                    }
                    if (this.player.botEnabled) {
                        this.player.botRouteActionPending = false;
                        return;
                    }
                } else if (this.player.botEnabled) {
                    this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
                }
                return;
            }
        }
        if (((ItemStack)dynamicObjectAt).getId() > 0 && this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("woodcutting stop no-space-after-roll player=" + GameplayTrace.describe(this.player) + " seq=" + this.actionSequence + " tree=" + this.treeDefinition + " logItemId=" + ((ItemStack)dynamicObjectAt).getId());
            }
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("Not enough space in your inventory.");
            value2 = this.player;
            ((Player)value2).packetSender.sendSoundEffect(1878, 1, 0);
            this.player.getUpdateState().setAnimation(-1);
            cycleEventContainer.stop();
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return;
        }
        this.player.getUpdateState().setAnimation(this.gatheringTool.getGatherAnimationId(), 0);
    }

    @Override
    public final void onStop() {
        this.player.getMovementQueue().clear();
        this.player.getUpdateState().setAnimation(-1, 0);
    }
}
