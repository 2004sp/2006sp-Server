package com.rs2.model.randomevent;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class RandomEventNpcReminderEvent
extends CycleEvent {
    private int reminderTicksRemaining = 120;
    private int interactionGraceTicks = -1;
    private String playerDisplayName;
    private final Npc npc;
    private final String[] reminderLines;
    private final int ignorePenaltyNpcId;
    private final Player player;

    public RandomEventNpcReminderEvent(Player player, Npc npc, String[] reminderLines, int ignorePenaltyNpcId) {
        this.player = player;
        this.npc = npc;
        this.reminderLines = reminderLines;
        this.ignorePenaltyNpcId = ignorePenaltyNpcId;
        this.playerDisplayName = GameUtil.formatDisplayName(player.getUsername());
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.npc.getInteractionTarget() != null && this.npc.getInteractionTarget() == this.npc.getOwnerPlayer() && this.interactionGraceTicks < 0) {
            this.interactionGraceTicks = 240;
        }
        if (this.interactionGraceTicks > 0) {
            --this.interactionGraceTicks;
            return;
        }
        if (this.reminderTicksRemaining > 0 && this.interactionGraceTicks < 0) {
            switch (this.reminderTicksRemaining) {
                case 120: {
                    this.npc.getUpdateState().setForcedTextAndMarkUpdated(this.reminderLines[0].replaceAll("1", this.playerDisplayName));
                    break;
                }
                case 90: {
                    this.npc.getUpdateState().setForcedTextAndMarkUpdated(this.reminderLines[1].replaceAll("1", this.playerDisplayName));
                    break;
                }
                case 60: {
                    this.npc.getUpdateState().setForcedTextAndMarkUpdated(this.reminderLines[2].replaceAll("1", this.playerDisplayName));
                    break;
                }
                case 30: {
                    this.npc.getUpdateState().setForcedTextAndMarkUpdated(this.reminderLines[3].replaceAll("1", this.playerDisplayName));
                    break;
                }
                case 2: {
                    if (this.npc.getNpcId() != 409) break;
                    this.npc.getUpdateState().setAnimation(863);
                }
            }
            --this.reminderTicksRemaining;
            return;
        }
        GameplayHelper.unregisterTemporaryNpc(this.npc);
        if (this.npc.getOwnerPlayer() != null && this.interactionGraceTicks != 0) {
            if (this.ignorePenaltyNpcId == 2541) {
                this.player.getSingleCombatTimer().setDelayTicks(0);
                this.player.getSingleCombatTimer().reset();
                GameplayHelper.spawnOwnedNpcAdjacentToPlayer(this.player, new Npc(this.ignorePenaltyNpcId + GameplayHelper.getRandomEventCombatLevelOffset(this.player.getCombatLevel())), true, false);
            } else if (this.ignorePenaltyNpcId > 0) {
                this.player.getSingleCombatTimer().setDelayTicks(0);
                this.player.getSingleCombatTimer().reset();
                GameplayHelper.spawnOwnedNpcAdjacentToPlayer(this.player, new Npc(this.ignorePenaltyNpcId), true, false);
            } else {
                Player player = this.player;
                player.packetSender.sendStillGraphic(86, this.npc.getPosition(), 0x640000);
                player = this.player;
                player.packetSender.sendSoundEffect(300, 1, 0);
            }
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

