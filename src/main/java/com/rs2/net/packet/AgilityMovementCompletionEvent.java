package com.rs2.net.packet;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

public final class AgilityMovementCompletionEvent
extends CycleEvent {
    private boolean delayElapsed = false;
    private PacketSender packetSender;
    private final String completionMessage;
    private final double experience;
    private final boolean restoreRunning;
    private final boolean clearForcedMovementFlag;

    public AgilityMovementCompletionEvent(PacketSender packetSender, String completionMessage, double experience, boolean restoreRunning, boolean clearForcedMovementFlag) {
        this.packetSender = packetSender;
        this.completionMessage = completionMessage;
        this.experience = experience;
        this.restoreRunning = restoreRunning;
        this.clearForcedMovementFlag = clearForcedMovementFlag;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.delayElapsed) {
            PacketSender.getPlayer(this.packetSender).setActionLocked(false);
            cycleEventContainer.stop();
        }
        this.delayElapsed = true;
    }

    @Override
    public final void onStop() {
        PacketSender.getPlayer(this.packetSender).setWalkAnimationOverride(-1);
        PacketSender.getPlayer(this.packetSender).setAppearanceUpdateRequired(true);
        Player player = PacketSender.getPlayer(this.packetSender);
        player.packetSender.sendGameMessage(this.completionMessage);
        PacketSender.getPlayer(this.packetSender).getSkillManager().addExperience(16, this.experience);
        if (this.restoreRunning) {
            PacketSender.getPlayer(this.packetSender).getMovementQueue().setRunning(true);
        }
        if (this.clearForcedMovementFlag) {
            PacketSender.getPlayer((PacketSender)this.packetSender).forcedMovementActive = false;
        }
        if (PacketSender.getPlayer((PacketSender)this.packetSender).botEnabled) {
            PacketSender.getPlayer((PacketSender)this.packetSender).botRouteActionPending = false;
        }
    }
}

