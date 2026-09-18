package com.rs2.model.clue;

import java.util.HashMap;
import java.util.Map;

public enum AnagramClue {
    CLUE_ITEM_2845(2845, 1070, "A Bas", "", 2),
    CLUE_ITEM_2847(2847, 962, "Aha Jar", "", 2),
    CLUE_ITEM_2848(2848, 696, "Arc O Line", "Challenge", 2),
    CLUE_ITEM_2849(2849, 746, "Are Col", "Challenge", 2),
    CLUE_ITEM_2851(2851, 171, "Bail Trims", "", 2),
    CLUE_ITEM_2853(2853, 1294, "Dt Run B", "Challenge", 2),
    CLUE_ITEM_2855(2855, 28, "Eek Zero Op", "Challenge", 2),
    CLUE_ITEM_2856(2856, 550, "El Ow", "", 2),
    CLUE_ITEM_2857(2857, 469, "Goblin Kern", "", 2),
    CLUE_ITEM_2858(2858, 2520, "Got A Boy", "Challenge", 2),
    CLUE_ITEM_2858_ENTRY_0(2858, 2521, "Got A Boy", "Challenge", 2),
    CLUE_ITEM_3604(3604, 379, "Halt Us", "", 2),
    CLUE_ITEM_3605(3605, 1011, "Icy fe", "", 2),
    CLUE_ITEM_3607(3607, 648, "Lark In Dog", "Challenge", 2),
    CLUE_ITEM_3609(3609, 676, "Me If", "", 2),
    CLUE_ITEM_3610(3610, 714, "Nod Med", "Challenge", 2),
    CLUE_ITEM_3611(3611, 278, "Ok Co", "Challenge", 2),
    CLUE_ITEM_3612(3612, 659, "Peaty Pert", "", 2),
    CLUE_ITEM_3613(3613, 543, "R Ak Mi", "Challenge", 2),
    CLUE_ITEM_3566(3566, 471, "By Look", "Challenge", 3),
    CLUE_ITEM_3568(3568, 2802, "C On Game Hoc", "Challenge", 3),
    CLUE_ITEM_3570(3570, 437, "O Birdz A Zany En Pc", "Challenge", 3);

    private int clueItemId;
    private int npcId;
    private String anagramText;
    private String followupType;
    private int level;
    private static Map cluesByNpcId;
    private static Map cluesByItemId;

    static {
        cluesByNpcId = new HashMap();
        cluesByItemId = new HashMap();
        AnagramClue[] anagramClueArray = AnagramClue.values();
        int length = anagramClueArray.length;
        int index = 0;
        while (index < length) {
            AnagramClue anagramClue = anagramClueArray[index];
            cluesByNpcId.put(anagramClue.npcId, anagramClue);
            cluesByItemId.put(anagramClue.clueItemId, anagramClue);
            ++index;
        }
    }

    public static AnagramClue forNpcId(int npcId) {
        return (AnagramClue)((Object)cluesByNpcId.get(npcId));
    }

    public static AnagramClue forClueItemId(int itemId) {
        return (AnagramClue)((Object)cluesByItemId.get(itemId));
    }

    private AnagramClue(int clueItemId, int npcId, String anagramText, String followupType, int level) {
        this.clueItemId = clueItemId;
        this.npcId = npcId;
        this.anagramText = anagramText;
        this.followupType = followupType;
        this.level = level;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final String getAnagramText() {
        return this.anagramText;
    }

    public final String getFollowupType() {
        return this.followupType;
    }

    public final int getLevel() {
        return this.level;
    }
}

