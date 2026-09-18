package com.rs2.model.quest.event;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.event.FrozenBotReloginTask;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;
import java.util.Iterator;

public final class FrozenBotRelogScanTask
extends TickTask {
    public FrozenBotRelogScanTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(100);
    }

    @Override
    public final void execute() {
        Object value;
        Object value2 = new ArrayList();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Integer> botModes = new ArrayList<Integer>();
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            value = playerArray[index];
            if (value != null && ((Player)value).isBot && (((Player)value).botMode == 4 || ((Player)value).botMode == 0) && ((Player)value).hasBotStalled()) {
                ((ArrayList)value2).add(value);
                arrayList.add(((Player)value).getUsername());
                botModes.add(((Player)value).botMode);
                System.out.println("Detected possibly frozen bot: " + ((Player)value).getUsername() + " at: " + ((Entity)value).getPosition() + ", trying to fix by relogging.");
            }
            ++index;
        }
        if (((ArrayList)value2).size() > 0) {
            Iterator iterator = ((ArrayList)value2).iterator();
            while (iterator.hasNext()) {
                value2 = value = (Player)iterator.next();
                ((Player)value).packetSender.sendLogout();
                ((Player)value).disconnect();
            }
            World.getTaskScheduler().schedule(new FrozenBotReloginTask(this, 30, arrayList, botModes));
        }
    }
}

