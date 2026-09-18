package com.rs2.model.clue;

import com.rs2.model.Position;
import java.util.HashMap;
import java.util.Map;

public enum CrypticDigClue {
    CLUE_ITEM_2677(new String[]{"Dig near some giant mushrooms,", "behind the Grand Tree."}, 2677, new Position(2458, 3505), 1),
    CLUE_ITEM_2773(new String[]{"46 is my number, and fire is my ", "blood. I have created over a ", "thousand blades. Unknown to life, ", "nor known to death."}, 2773, new Position(3170, 3885), 3),
    CLUE_ITEM_2774(new String[]{"Aggie I see, Lonely and southern I", "feel.", "I am neither inside nor outside the", "house,", "yet no house would be complete without", "me. The treasure lies beneath me!"}, 2774, new Position(3085, 3255), 3),
    CLUE_ITEM_2776(new String[]{"Come to the @or2@evil ledge,", "@bla@Yew know yew want to.", "Try not to get stung."}, 2776, new Position(3089, 3468), 3),
    CLUE_ITEM_2778(new String[]{"Covered in shadows, the centre", "of the circle is where you will", "find the answer."}, 2778, new Position(3488, 3289), 3),
    CLUE_ITEM_2780(new String[]{"I am a token of the greatest love.", "I have no beginning or end. My", "eye is red, I can fit like a glove. Go", "to the place where it's money they", "lend,", "And dig by the gate to be my friend."}, 2780, new Position(3191, 9825), 3),
    CLUE_ITEM_2782(new String[]{"I lie lonely and forgotten", "in mid wilderness,", "where the dead rise from their beds.", "Feel free to quarrel and wind me up,", "and dig while you shoot their heads."}, 2782, new Position(3174, 3663), 3),
    CLUE_ITEM_2783(new String[]{"The beasts to my east", "snap claws and tails.", "The rest to my west", "can slide and eat fish.", "The force to my north", "will jump and they'll wail.", "Come dig by my fire", "and make a wish."}, 2783, new Position(2599, 3268), 3);

    private String[] clueTextLines;
    private int clueItemId;
    private Position position;
    private int level;
    private static Map cluesByItemId;
    private static Map cluesByPosition;

    static {
        cluesByItemId = new HashMap();
        cluesByPosition = new HashMap();
        CrypticDigClue[] crypticDigClueArray = CrypticDigClue.values();
        int length = crypticDigClueArray.length;
        int index = 0;
        while (index < length) {
            CrypticDigClue crypticDigClue = crypticDigClueArray[index];
            cluesByItemId.put(crypticDigClue.clueItemId, crypticDigClue);
            cluesByPosition.put(crypticDigClue.position, crypticDigClue);
            ++index;
        }
    }

    public static CrypticDigClue forPosition(Position position) {
        int index = 0;
        while (index < CrypticDigClue.values().length) {
            CrypticDigClue crypticDigClue = CrypticDigClue.values()[index];
            if (crypticDigClue.position.equals(position)) {
                return CrypticDigClue.values()[index];
            }
            ++index;
        }
        return null;
    }

    public static CrypticDigClue forClueItemId(int itemId) {
        return (CrypticDigClue)((Object)cluesByItemId.get(itemId));
    }

    private CrypticDigClue(String[] clueTextLines, int clueItemId, Position position, int level) {
        this.clueTextLines = clueTextLines;
        this.clueItemId = clueItemId;
        this.position = position;
        this.level = level;
    }

    public final String[] getClueTextLines() {
        return this.clueTextLines;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final int getLevel() {
        return this.level;
    }
}

