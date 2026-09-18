package com.rs2.model.item.consumable;

import com.rs2.model.item.consumable.FoodHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameplayTrace;

public final class FoodHealMessageEvent
extends CycleEvent {
    private FoodHandler foodHandler;

    public FoodHealMessageEvent(FoodHandler foodHandler) {
        this.foodHandler = foodHandler;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int currentHp = this.foodHandler.player.getSkillManager().getCurrentLevels()[3];
        int baseHp = this.foodHandler.player.getSkillManager().getBaseLevel(3);
        if (currentHp < baseHp) {
            Player player = this.foodHandler.player;
            player.packetSender.sendGameMessage("It heals some health.");
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("food heal-message sent player=" + GameplayTrace.describe(this.foodHandler.player) + " currentHp=" + currentHp + " baseHp=" + baseHp);
            }
        } else if (GameplayTrace.enabled()) {
            GameplayTrace.log("food heal-message skipped player=" + GameplayTrace.describe(this.foodHandler.player) + " currentHp=" + currentHp + " baseHp=" + baseHp);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

