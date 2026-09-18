package com.rs2.model.quest.event;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.model.quest.event.FrozenBotRelogScanTask;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

public final class FrozenBotReloginTask
extends TickTask {
    private final ArrayList reloginNames;
    private final ArrayList botModes;

    public FrozenBotReloginTask(FrozenBotRelogScanTask frozenBotRelogScanTask, int value2, ArrayList arrayList, ArrayList botModes) {
        super(30);
        this.reloginNames = arrayList;
        this.botModes = botModes;
    }

    @Override
    public final void execute() {
        for (int index = 0; index < this.reloginNames.size(); ++index) {
            String text = (String)this.reloginNames.get(index);
            int botMode = ((Integer)this.botModes.get(index)).intValue();
            BotPlayer.createNamedBot(text, "zxcvbn", botMode);
        }
        this.stop();
    }
}
