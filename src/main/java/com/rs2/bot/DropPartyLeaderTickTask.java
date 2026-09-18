package com.rs2.bot;

import com.rs2.bot.DropPartyBotManager;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;

public final class DropPartyLeaderTickTask
extends TickTask {
    private final Player leader;

    public DropPartyLeaderTickTask(int value2, Player player) {
        super(3);
        this.leader = player;
    }

    @Override
    public final void execute() {
        if (this.leader.isDead() || !this.leader.isRegistered() || !this.leader.dropPartyLeader || this.leader.botTaskState.equals("wait for new task")) {
            this.stop();
            return;
        }
        if (this.leader.botTaskState.equals("do task")) {
            Object value;
            Object value2;
            if (this.getIntervalTicks() != 8) {
                this.leader.queuePublicChatMessage("Dropping now!");
                this.setIntervalTicks(8);
                for (Object participantObject : DropPartyBotManager.dropPartyParticipants) {
                    if (participantObject == this.leader) continue;
                    ((Player)participantObject).botTaskState = "do task";
                    EntityTargetMovement.clearMovementTarget((Entity)participantObject);
                    ((Entity)participantObject).getMovementQueue().clear();
                    value = this.leader.currentBotTask.getRandomTaskAreaPosition();
                    PathFinder.getInstance();
                    PathFinder.findPath((Player)participantObject, ((Position)value).getX(), ((Position)value).getY(), true, 0, 0);
                }
            }
            if (GameUtil.randomInt(2) == 0) {
                value2 = null;
                ItemStack[] itemStackArray = this.leader.getInventoryManager().getContainer().getItems();
                int length = itemStackArray.length;
                int index = 0;
                while (index < length) {
                    ItemStack itemStack = itemStackArray[index];
                    if (itemStack != null) {
                        value2 = itemStack;
                        break;
                    }
                    ++index;
                }
                if (value2 != null) {
                    BotCombatHelper.dropInventoryItem(this.leader, (ItemStack)value2);
                } else {
                    DropPartyBotManager.finishLeaderDrops(this.leader);
                    this.stop();
                    return;
                }
            }
            value2 = this.leader.currentBotTask.getRandomTaskAreaPosition();
            PathFinder.getInstance();
            PathFinder.findPath(this.leader, ((Position)value2).getX(), ((Position)value2).getY(), true, 0, 0);
            int value3 = GameUtil.randomInt(DropPartyBotManager.dropPartyParticipants.size());
            value = (Player)DropPartyBotManager.dropPartyParticipants.get(value3);
            if (!((Player)value).dropPartySentToAssignedDrop && value != this.leader) {
                ((Player)value).botTaskState = "do task";
                ((Player)value).dropPartyAssignedDropPosition = this.leader.getPosition();
            }
            return;
        }
        if (GameUtil.randomInt(3) == 0) {
            this.leader.queuePublicChatMessage(this.leader.botPublicChatMessage, this.leader.botPublicChatColor, this.leader.botPublicChatEffect);
        }
    }
}

