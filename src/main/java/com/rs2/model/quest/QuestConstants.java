package com.rs2.model.quest;

import com.rs2.model.npc.NpcDefinition;

public final class QuestConstants {
    public static final int[] COMBAT_TAB_INTERFACE;
    public static final int[] STATS_TAB_INTERFACE;
    public static final int[] QUEST_TAB_INTERFACE;
    public static final int[] INVENTORY_TAB_INTERFACE;
    public static final int[] EQUIPMENT_TAB_INTERFACE;
    public static final int[] PRAYER_TAB_INTERFACE;
    public static final int[] MAGIC_TAB_INTERFACE;
    public static final int[] FRIENDS_TAB_INTERFACE;
    public static final int[] IGNORE_TAB_INTERFACE;
    public static final int[] LOGOUT_TAB_INTERFACE;
    public static final int[] OPTIONS_TAB_INTERFACE;
    public static final int[] EMOTES_TAB_INTERFACE;
    public static final int[] MUSIC_TAB_INTERFACE;
    public static final int RELDO_NPC_ID;

    static {
        int[] integerValues = new int[2];
        integerValues[1] = 2423;
        COMBAT_TAB_INTERFACE = integerValues;
        STATS_TAB_INTERFACE = new int[]{1, 3917};
        QUEST_TAB_INTERFACE = new int[]{2, 638};
        INVENTORY_TAB_INTERFACE = new int[]{3, 3213};
        EQUIPMENT_TAB_INTERFACE = new int[]{4, 1644};
        PRAYER_TAB_INTERFACE = new int[]{5, 5608};
        MAGIC_TAB_INTERFACE = new int[]{6, 1151};
        FRIENDS_TAB_INTERFACE = new int[]{8, 5065};
        IGNORE_TAB_INTERFACE = new int[]{9, 5715};
        LOGOUT_TAB_INTERFACE = new int[]{10, 2449};
        OPTIONS_TAB_INTERFACE = new int[]{11, 904};
        EMOTES_TAB_INTERFACE = new int[]{12, 147};
        MUSIC_TAB_INTERFACE = new int[]{13, 962};
        RELDO_NPC_ID = !NpcDefinition.isDefined(2661) ? 647 : 2661;
    }
}
