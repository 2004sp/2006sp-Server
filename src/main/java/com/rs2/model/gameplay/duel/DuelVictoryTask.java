package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelArenaLocationManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class DuelVictoryTask
extends CycleEvent {
    private final Player winner;
    private final String loserUsername;
    private final String loserCombatLevel;
    private final ItemStack[] rewardItems;

    public DuelVictoryTask(Player player, String loserUsername, String loserCombatLevel, ItemStack[] itemStackArray) {
        this.winner = player;
        this.loserUsername = loserUsername;
        this.loserCombatLevel = loserCombatLevel;
        this.rewardItems = itemStackArray;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.winner != null) {
            this.winner.getDuelArenaLocationManager();
            this.winner.moveTo(DuelArenaLocationManager.randomExitPosition());
            Object value = this.winner;
            ((Player)value).packetSender.sendInterfaceText(this.loserUsername, 6840);
            value = this.winner;
            ((Player)value).packetSender.sendInterfaceText(this.loserCombatLevel, 6839);
            value = this.winner;
            ((Player)value).packetSender.sendItemContainer(6822, this.rewardItems);
            value = this.winner;
            ((Player)value).packetSender.sendEntityHintIcon(10, -1);
            ItemStack[] itemStackArray = this.rewardItems;
            int length = this.rewardItems.length;
            int index = 0;
            while (index < length) {
                value = itemStackArray[index];
                this.winner.getInventoryManager().addOrDropItem((ItemStack)value);
                ++index;
            }
            this.winner.resetCombatState();
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        if (this.winner != null) {
            this.winner.setActionLocked(false);
            Player player = this.winner;
            player.packetSender.showInterface(6733);
            player = this.winner;
            player.packetSender.sendMusicJingle(221, 200);
        }
    }
}

