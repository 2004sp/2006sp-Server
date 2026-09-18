package com.rs2.bot;

import com.rs2.bot.BotPlayer;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DropPartyBotJoinTask
extends TickTask {
    private final BotTaskDefinition dropPartyTask;

    public DropPartyBotJoinTask(int value2, BotTaskDefinition botTaskDefinition) {
        super(1);
        this.dropPartyTask = botTaskDefinition;
    }

    @Override
    public final void execute() {
        if (BotTaskDefinition.dropPartyBotJoinIndex == 0) {
            Player player = (Player)BotPlayer.dropPartyBots.get(0);
            DropPartyBotManager.dropPartyParticipants.add(player);
            player.botTaskReturnToBankRequested = false;
            player.botEnabled = true;
            this.dropPartyTask.assignedBotPlayers.add(player);
            BotTaskDefinition botTaskDefinition = this.dropPartyTask;
            Player player2 = player;
            player.currentBotTask = botTaskDefinition;
            player.dropPartyLeader = true;
            player.dropPartyPretaskComplete = false;
            player.dropPartyPretaskLoopCount = 0;
            player.dropPartyFollower = false;
            player.dropPartySentToAssignedDrop = false;
            this.dropPartyTask.startTask(player);
            player.botTaskStartTimeMillis = System.currentTimeMillis();
        } else {
            Player player = (Player)BotPlayer.dropPartyBots.get(BotTaskDefinition.dropPartyBotJoinIndex);
            Player player3 = (Player)BotPlayer.dropPartyBots.get(BotTaskDefinition.dropPartyBotJoinIndex - 1);
            DropPartyBotManager.dropPartyParticipants.add(player);
            BotTaskDefinition botTaskDefinition = this.dropPartyTask;
            Player player4 = player;
            player.currentBotTask = botTaskDefinition;
            player.dropPartyFollower = true;
            player.dropPartySentToAssignedDrop = false;
            player.dropPartyLeader = false;
            player.dropPartyPretaskComplete = false;
            player.dropPartyPretaskLoopCount = 0;
            player.dropPartyAssignedDropPosition = null;
            this.dropPartyTask.startTask(player);
            player.getUpdateState().setFaceEntity(player3.getEncodedIndex());
            player.setAttackRange(1);
            player.setMovementTarget(player3);
        }
        if (BotTaskDefinition.dropPartyBotJoinIndex == BotPlayer.dropPartyBots.size() - 1 || BotTaskDefinition.dropPartyBotJoinIndex == DropPartyBotManager.targetDropPartySize - 1) {
            this.stop();
            return;
        }
        ++BotTaskDefinition.dropPartyBotJoinIndex;
    }
}

