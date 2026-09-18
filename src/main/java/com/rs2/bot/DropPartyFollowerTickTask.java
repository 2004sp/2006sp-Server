package com.rs2.bot;

import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;

public final class DropPartyFollowerTickTask
extends TickTask {
    private final Player follower;
    private final Player leader;

    public DropPartyFollowerTickTask(int value2, Player player, Player player2) {
        super(3);
        this.follower = player;
        this.leader = player2;
    }

    @Override
    public final void execute() {
        if (this.follower.isDead() || !this.follower.isRegistered() || !this.follower.dropPartyFollower || this.follower.botTaskState.equals("wait for new task")) {
            this.stop();
            return;
        }
        if (!this.follower.dropPartySentToAssignedDrop && this.follower.dropPartyAssignedDropPosition != null) {
            EntityTargetMovement.clearMovementTarget(this.follower);
            PathFinder.getInstance();
            PathFinder.findPath(this.follower, this.follower.dropPartyAssignedDropPosition.getX(), this.follower.dropPartyAssignedDropPosition.getY(), true, 0, 0);
            this.follower.dropPartySentToAssignedDrop = true;
            if (!this.follower.getMovementQueue().isRunning()) {
                this.follower.getMovementQueue().setRunning(true);
            }
        }
        if (this.leader.botTaskState.equals("wait for new task") && DropPartyBotManager.pendingDropPartyGroundItems.size() == 0) {
            DropPartyBotManager.finishDropPartyParticipant(this.follower);
            this.stop();
            return;
        }
        if (!(GameUtil.randomInt(3) != 0 || this.leader.botTaskState.equals("do task") || this.leader.botTaskState.equals("wait for new task") || this.follower.dropPartySentToAssignedDrop)) {
            this.follower.queuePublicChatMessage(this.follower.botPublicChatMessage, this.follower.botPublicChatColor, this.follower.botPublicChatEffect);
        }
    }
}

