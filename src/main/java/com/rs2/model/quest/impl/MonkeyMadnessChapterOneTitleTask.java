package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class MonkeyMadnessChapterOneTitleTask
extends TickTask {
    private final Player player;

    public MonkeyMadnessChapterOneTitleTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        Player player;
        this.player.setActionLocked(false);
        Player player2 = player = this.player;
        player.packetSender.sendInterfaceText("", 3026);
        player2 = player;
        player2.packetSender.sendInterfaceText("@cya@Monkey Madness: Chapter 1", 3027);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3028);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3029);
        player2 = player;
        player2.packetSender.sendInterfaceText("@cya@In which our hero finds himself drawn back into Glough's web", 3030);
        player2 = player;
        player2.packetSender.sendInterfaceText("@cya@of deception and deceit.", 3031);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3032);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3033);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3034);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3035);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3036);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3037);
        player2 = player;
        player2.packetSender.showInterface(3023);
        this.stop();
    }
}
