package com.rs2.model.skill.firemaking;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.firemaking.FiremakingHandler;
import com.rs2.model.skill.firemaking.FiremakingLog;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;

public final class FiremakingTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final GroundItem groundItem;
    private final int logItemId;
    private final FiremakingLog firemakingLog;
    private final int fireX;
    private final int fireY;
    private final int plane;

    public FiremakingTask(FiremakingHandler firemakingHandler, Player player, int actionSequence, GroundItem groundItem, int logItemId, FiremakingLog firemakingLog, int fireX, int fireY, int plane) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.groundItem = groundItem;
        this.logItemId = logItemId;
        this.firemakingLog = firemakingLog;
        this.fireX = fireX;
        this.fireY = fireY;
        this.plane = plane;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        executeControlExit1: {
            executeControlExit2: {
                if (!this.player.isCurrentActionSequence(this.actionSequence)) break executeControlExit2;
                if (this.groundItem == null) break executeControlExit1;
                GroundItemManager.getInstance();
                if (GroundItemManager.isVisible(this.player, this.groundItem)) break executeControlExit1;
            }
            cycleEventContainer.stop();
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("firemaking stopped-invalid-state player=" + GameplayTrace.describe(this.player) + " logItemId=" + this.logItemId + " x=" + this.fireX + " y=" + this.fireY + " plane=" + this.plane);
            }
            return;
        }
        boolean skillManager = GameUtil.rollLevelScaledChance(64, 512, this.player.getSkillManager().getCurrentLevels()[11]);
        if (this.logItemId == 7404 || this.logItemId == 7405 || this.logItemId == 7406) {
            skillManager = true;
        }
        if (skillManager) {
            Player player;
            if (this.player.canStepToOffset(-1, 0)) {
                player = this.player;
                player.packetSender.queueRelativeMovementStep(-1, 0, false);
            } else {
                player = this.player;
                player.packetSender.queueRelativeMovementStep(1, 0, false);
            }
            player = null;
            if (this.player.gameMode != 0) {
                player = this.player;
            }
            new DynamicObject(this.firemakingLog.getFireObjectId(), this.fireX, this.fireY, this.plane, -1, 10, ServerSettings.placeholderObjectId, GameUtil.randomBetweenInclusive(100, 200), player);
            player = this.player;
            player.packetSender.sendGameMessage("The fire catches and the logs begin to burn.");
            player = this.player;
            player.packetSender.sendSoundEffect(374, 1, 0);
            if (this.player.getQuestState(0) == 9) {
                this.player.advanceTutorialStage();
            }
            if (this.player.getQuestState(0) != 1) {
                this.player.getQuestManager().refreshQuestJournal();
            }
            if (this.groundItem != null) {
                GroundItemManager.getInstance().removeForPickup(this.groundItem, this.player);
            }
            this.player.getUpdateState().setFacePosition(new Position(this.fireX, this.fireY));
            this.player.getSkillManager().addExperience(11, this.firemakingLog.getExperience());
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("firemaking success player=" + GameplayTrace.describe(this.player) + " logItemId=" + this.logItemId + " fireObjectId=" + this.firemakingLog.getFireObjectId() + " x=" + this.fireX + " y=" + this.fireY + " plane=" + this.plane + " xp=" + this.firemakingLog.getExperience());
            }
            cycleEventContainer.stop();
            return;
        }
        this.player.getUpdateState().setAnimation(733);
        Player player = this.player;
        player.packetSender.sendSoundEffect(375, 1, 0);
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

