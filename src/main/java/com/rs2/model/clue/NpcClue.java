package com.rs2.model.clue;

import java.util.HashMap;
import java.util.Map;

public enum NpcClue {
    CLUE_ITEM_2678(new String[]{"One of the sailors in Port Sarim ", " is your next destination."}, 2678, 376, "", 1),
    CLUE_ITEM_2679(new String[]{"Someone watching the fights ", "in the duel arena is your next ", "destination"}, 2679, 969, "", 1),
    CLUE_ITEM_2680(new String[]{"Speak to Arhein in Catherby"}, 2680, 563, "", 1),
    CLUE_ITEM_2681(new String[]{"Speak to Doric who lives ", "north of Falador"}, 2681, 284, "", 1),
    CLUE_ITEM_2682(new String[]{"Speak to Ellis in Al Kharid."}, 2682, 2824, "", 1),
    CLUE_ITEM_2683(new String[]{"Speak to Gaius in Taverley."}, 2683, 586, "", 1),
    CLUE_ITEM_2684(new String[]{"Speak to Jatix in Taverley."}, 2684, 587, "", 1),
    CLUE_ITEM_2685(new String[]{"Speak to Ned in Draynor "}, 2685, 918, "", 1),
    CLUE_ITEM_2686(new String[]{"Speak to Sir Kay in Camelot", "Castle"}, 2686, 241, "", 1),
    CLUE_ITEM_2687(new String[]{"Speak to the bartender of the", "Blue Moon Inn in Varrock"}, 2687, 733, "", 1),
    CLUE_ITEM_2688(new String[]{"Speak to the staff of Sinclair ", "Mansion"}, 2688, 809, "", 1),
    CLUE_ITEM_2689(new String[]{"Talk to the bartender of the Rusty ", "Anchor in Port Sarim."}, 2689, 734, "", 1),
    CLUE_ITEM_2690(new String[]{"Talk to the Squire in the White ", "Knights' castle in Falador."}, 2690, 606, "", 1),
    CLUE_ITEM_2831(new String[]{"Speak to a referee."}, 2831, 635, "Challenge", 2),
    CLUE_ITEM_2833(new String[]{"Speak to Donovan the Family ", "Handyman"}, 2833, 806, "", 2),
    CLUE_ITEM_2835(new String[]{"Speak to Hajedy"}, 2835, 510, "", 2),
    CLUE_ITEM_2837(new String[]{"Speak to Hazelmere"}, 2837, 669, "Challenge", 2),
    CLUE_ITEM_2839(new String[]{"Speak to Kangai Mau"}, 2839, 846, "", 2),
    CLUE_ITEM_2841(new String[]{"Speak to Roavar"}, 2841, 1042, "", 2),
    CLUE_ITEM_2843(new String[]{"Speak to Ulizius"}, 2843, 1054, "", 2),
    CLUE_ITEM_2785(new String[]{"'A bag belt only?' he asked ", "his balding brothers"}, 2785, 801, "", 3),
    CLUE_ITEM_2786(new String[]{"Citric Cellar", ""}, 2786, 603, "Puzzle", 3),
    CLUE_ITEM_2788(new String[]{"Generally ", "speaking, his ", "nose was very ", "bent"}, 2788, 296, "Puzzle", 3),
    CLUE_ITEM_2790(new String[]{"Identify the back of this ", "over-acting brother. ", "(He's a long way from ", "home.)"}, 2790, 1008, "Puzzle", 3),
    CLUE_ITEM_2792(new String[]{"If a man carried my ", "burden, he would break ", "his back. I am not rich, ", "but leave silver in my ", "track. Speak to the ", "keeper of my trail."}, 2792, 558, "", 3),
    CLUE_ITEM_2793(new String[]{"My name is like a tree, ", "yet it is spelt with a \"g\"", "come see the fur, which ", "is right near me"}, 2793, 783, "Puzzle", 3),
    CLUE_ITEM_2794(new String[]{"Often sought out by scholars of", "histories past, find me where", "words of wisdom speak volumes."}, 2794, 618, "Puzzle", 3),
    CLUE_ITEM_2796(new String[]{"'Small Shoe.' Often found with ", "rod on mushroom."}, 2796, 162, "Puzzle", 3),
    CLUE_ITEM_2797(new String[]{"Snah? I feel all confused, like ", "one of those cakes."}, 2797, 0, "", 3),
    CLUE_ITEM_2799(new String[]{"Surprising? I bet he is..."}, 2799, 883, "", 3),
    CLUE_ITEM_3564(new String[]{"Surviving."}, 3564, 605, "Puzzle", 3);

    private String[] clueTextLines;
    private int clueItemId;
    private int npcId;
    private String followupType;
    private int level;
    private static Map cluesByNpcId;
    private static Map cluesByItemId;

    static {
        cluesByNpcId = new HashMap();
        cluesByItemId = new HashMap();
        NpcClue[] npcClueArray = NpcClue.values();
        int length = npcClueArray.length;
        int index = 0;
        while (index < length) {
            NpcClue npcClue = npcClueArray[index];
            cluesByNpcId.put(npcClue.npcId, npcClue);
            cluesByItemId.put(npcClue.clueItemId, npcClue);
            ++index;
        }
    }

    public static NpcClue forNpcId(int npcId) {
        return (NpcClue)((Object)cluesByNpcId.get(npcId));
    }

    public static NpcClue forClueItemId(int itemId) {
        return (NpcClue)((Object)cluesByItemId.get(itemId));
    }

    private NpcClue(String[] clueTextLines, int clueItemId, int npcId, String followupType, int level) {
        this.clueTextLines = clueTextLines;
        this.clueItemId = clueItemId;
        this.npcId = npcId;
        this.followupType = followupType;
        this.level = level;
    }

    public final String[] getClueTextLines() {
        return this.clueTextLines;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final String getFollowupType() {
        return this.followupType;
    }

    public final int getLevel() {
        return this.level;
    }
}

