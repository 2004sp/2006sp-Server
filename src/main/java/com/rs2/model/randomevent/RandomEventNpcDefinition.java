package com.rs2.model.randomevent;

public enum RandomEventNpcDefinition {
    DRUNKEN_DWARF(956, 2429, new String[]{"'Ere, matey, 'ave some 'o the good stuff.", "Dun ignore your matey!", "I hates you 1!", "Aww comeon, talk to ikle me 1!"}),
    GENIE(409, 0, new String[]{"Greetings 1!", "I need to talk to you 1.", "1 please speak with me.", "1 I'm in a hurry, please talk to me!"}),
    RICK_TURPENTINE(2476, 2476, new String[]{"Hello 1!", "I need to talk to you 1.", "1 please speak with me.", "1 I'm in a hurry, please talk to me!"}),
    DR_JEKYLL(2540, 2541, new String[]{"Hey 1!", "I need to talk to you 1.", "1 please speak with me.", "1 I'm in a hurry, please talk to me!"});

    int npcId;
    int ignorePenaltyNpcId;
    String[] reminderLines;

    private RandomEventNpcDefinition(int npcId, int ignorePenaltyNpcId, String[] reminderLines) {
        this.npcId = npcId;
        this.ignorePenaltyNpcId = ignorePenaltyNpcId;
        this.reminderLines = reminderLines;
    }
}

