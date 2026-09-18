package com.rs2.model.c;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class SheepShearingTask
extends CycleEvent {
    private final Player player;
    private final Npc sheep;

    public SheepShearingTask(Player player, Npc npc) {
        this.player = player;
        this.sheep = npc;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!GameUtil.rollChance(0.75)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("The sheep manages to get away!");
            int spawnMinPosition = this.sheep.getSpawnMinPosition().getX();
            int spawnMinPosition2 = this.sheep.getSpawnMinPosition().getY();
            int spawnMaxPosition = this.sheep.getSpawnMaxPosition().getX() - this.sheep.getSpawnMinPosition().getX();
            int spawnMaxPosition2 = this.sheep.getSpawnMaxPosition().getY() - this.sheep.getSpawnMinPosition().getY();
            spawnMaxPosition = GameUtil.getRandom().nextInt(spawnMaxPosition);
            spawnMaxPosition2 = GameUtil.getRandom().nextInt(spawnMaxPosition2);
            Position position = new Position(spawnMinPosition + spawnMaxPosition, spawnMinPosition2 + spawnMaxPosition2, this.sheep.getPosition().getPlane());
            this.sheep.queuePathTo(position, true);
            if (this.player.botEnabled) {
                this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        this.player.getInventoryManager().addItem(new ItemStack(1737));
        this.sheep.getUpdateState().setForcedText("Baa!");
        Player player = this.player;
        player.packetSender.sendSoundEffect(1314, 1, 0);
        player = this.player;
        player.packetSender.sendGameMessage("You manage to shear the sheep.");
        this.sheep.transformToNpcId(42, 10);
        if (this.player.botEnabled) {
            this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

