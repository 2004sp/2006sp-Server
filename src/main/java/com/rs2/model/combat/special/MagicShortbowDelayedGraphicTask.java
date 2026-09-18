package com.rs2.model.combat.special;

import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.special.MagicShortbowSpecialAttack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class MagicShortbowDelayedGraphicTask
extends TickTask {
    private final Player player;

    public MagicShortbowDelayedGraphicTask(MagicShortbowSpecialAttack magicShortbowSpecialAttack, int value2, Player player) {
        super(1);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.getUpdateState().setGraphic(new GraphicEffect(256, 90).withDelay15(15));
        this.stop();
    }
}

