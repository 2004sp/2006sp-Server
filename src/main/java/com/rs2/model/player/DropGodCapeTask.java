package com.rs2.model.player;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DropGodCapeTask
extends TickTask {
    private final Player player;
    private final int capeAnimationId;

    public DropGodCapeTask(Player player, int value3, Player player2, int capeAnimationId) {
        super(4);
        this.player = player2;
        this.capeAnimationId = capeAnimationId;
    }

    @Override
    public final void execute() {
        int position = this.player.getPosition().getX();
        int position2 = this.player.getPosition().getY() + 1;
        int index = 0;
        if (this.capeAnimationId == 2873) {
            index = 2412;
        } else if (this.capeAnimationId == 2874) {
            index = 2414;
        } else if (this.capeAnimationId == 2875) {
            index = 2413;
        }
        GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(index, 1), this.player, new Position(position, position2, 0)));
        Player player = this.player;
        player.packetSender.sendStillGraphicToNearbyPlayers(86, position, position2, 0, 0);
        this.player.setActionLocked(false);
        DialogueManager.continueContextDialogue(1, this.player, this.capeAnimationId, 10, 0, this.player.getPosition().getX(), this.player.getPosition().getY());
        this.stop();
    }
}

