package com.rs2.model.gameplay;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.CaveLightManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class SwampGasExplosionTask
extends TickTask {
    private final Player player;

    public SwampGasExplosionTask(int value2, Player player) {
        super(7);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        Object litCaveLightSource = this.player.findLitCaveLightSource();
        if (CaveLightManager.isInSwampGasArea(this.player) && litCaveLightSource != null) {
            ItemStack itemStack = (ItemStack)litCaveLightSource;
            litCaveLightSource = this.player;
            ((Entity)litCaveLightSource).applyDirectHit(12, HitType.NORMAL);
            Object value = litCaveLightSource;
            ((Player)value).packetSender.sendGameMessage("The swamp gas explodes!");
            value = new GraphicEffect(157, 100);
            ((Entity)litCaveLightSource).getUpdateState().setGraphic(((GraphicEffect)value).getId(), ((GraphicEffect)value).getPackedDelay());
            GameplayHelper.extinguishCaveLightSource((Player)litCaveLightSource, itemStack.getId(), false);
            return;
        }
        this.stop();
    }
}

