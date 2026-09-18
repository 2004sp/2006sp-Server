package com.rs2.model.quest;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.quest.QuestHook;
import com.rs2.model.quest.event.ChristmasDropEventHook;
import com.rs2.model.quest.event.EasterEggDropEventHook;
import com.rs2.model.quest.event.HalloweenMaskDropEventHook;
import com.rs2.model.quest.event.NoopQuestEventHook;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;

public final class QuestEventRegistry {
    public static int eventHookCount = 0;
    private static int SERVER_MAINTENANCE_EVENT_TYPE = 0;
    private static int HALLOWEEN_EVENT_TYPE = 1;
    private static int CHRISTMAS_EVENT_TYPE = 2;
    private static int EASTER_EVENT_TYPE = 3;
    private static QuestHook[] eventHooks = new QuestHook[]{new NoopQuestEventHook(-1), new ServerMaintenanceEventHook(-1, 0), new HalloweenMaskDropEventHook(-1, HALLOWEEN_EVENT_TYPE), new ChristmasDropEventHook(-1, CHRISTMAS_EVENT_TYPE), new EasterEggDropEventHook(-1, EASTER_EVENT_TYPE)};

    public static QuestHook getEventHook(int value2) {
        int index = 0;
        while (index < eventHooks.length) {
            int questId = eventHooks[index].getQuestId();
            if (value2 == questId && eventHooks[index].isEnabled()) {
                return eventHooks[index];
            }
            ++index;
        }
        return eventHooks[0];
    }

    public static void initializeEventHooks() {
        eventHookCount = eventHooks.length;
        int index = 0;
        while (index < eventHooks.length) {
            if (eventHooks[index].getEventType() == 0) {
                eventHooks[index].initialize();
                eventHooks[index].setEnabled(true);
            }
            if (eventHooks[index].getEventType() == HALLOWEEN_EVENT_TYPE && Server.halloweenEventActive && ServerSettings.holidayItemDropsEnabled) {
                eventHooks[index].initialize();
                eventHooks[index].setEnabled(true);
            }
            if (eventHooks[index].getEventType() == CHRISTMAS_EVENT_TYPE && Server.christmasEventActive && ServerSettings.holidayItemDropsEnabled) {
                eventHooks[index].initialize();
                eventHooks[index].setEnabled(true);
            }
            if (eventHooks[index].getEventType() == EASTER_EVENT_TYPE && Server.easterEventActive && ServerSettings.holidayItemDropsEnabled) {
                eventHooks[index].initialize();
                eventHooks[index].setEnabled(true);
            }
            ++index;
        }
    }
}

