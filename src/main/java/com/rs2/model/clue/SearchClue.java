package com.rs2.model.clue;

import com.rs2.model.Position;
import java.util.HashMap;
import java.util.Map;

public enum SearchClue {
    CLUE_ITEM_2691(new String[]{"A crate found in the", "tower of a church is", "your next location."}, 2691, new Position(2612, 3307, 2), -1, 832, 1),
    CLUE_ITEM_2692(new String[]{"Search a bookcase in the Wizards", "tower."}, 2692, new Position(3113, 3158, 0), -1, 832, 1),
    CLUE_ITEM_2693(new String[]{"Search chests found in the", "upstairs of shops in Port Sarim."}, 2693, new Position(3016, 3205, 1), 378, 832, 1),
    CLUE_ITEM_2694(new String[]{"Search for a crate in a ", "building in Hemenster"}, 2694, new Position(2636, 3453, 0), -1, 832, 1),
    CLUE_ITEM_2695(new String[]{"Search for a crate in Varrock", "Castle."}, 2695, new Position(3224, 3492, 0), -1, 832, 1),
    CLUE_ITEM_2696(new String[]{"Search for a crate on the ", "ground floor of a house in", "Seers' Village"}, 2696, new Position(2699, 3470, 0), -1, 832, 1),
    CLUE_ITEM_2697(new String[]{"Search the boxes in a shop ", "in Taverley"}, 2697, new Position(2886, 3449, 0), -1, 832, 1),
    CLUE_ITEM_2698(new String[]{"Search the boxes in one of ", "the tents in Al Kharid"}, 2698, new Position(3308, 3206, 0), -1, 832, 1),
    CLUE_ITEM_2699(new String[]{"Search the boxes in the ", "Goblin house near Lumbridge"}, 2699, new Position(3245, 3245, 0), -1, 832, 1),
    CLUE_ITEM_2700(new String[]{"Search the boxes in the ", "house near the South ", "entrance of Varrock"}, 2700, new Position(3203, 3384, 0), -1, 832, 1),
    CLUE_ITEM_2701(new String[]{"Search the boxes just ", "outside the Armour shop in ", "East Ardougne"}, 2701, new Position(2654, 3299, 0), -1, 832, 1),
    CLUE_ITEM_2702(new String[]{"Search the chest in the Duke ", "of Lumbridge's bedroom"}, 2702, new Position(3209, 3218, 1), 378, 832, 1),
    CLUE_ITEM_2703(new String[]{"Search the chest in the left-", "hand tower of Camelot castle"}, 2703, new Position(2748, 3495, 2), 378, 832, 1),
    CLUE_ITEM_2704(new String[]{"Search the chests in the", "Dwarven Mine"}, 2704, new Position(3000, 9798, 0), 378, 832, 1),
    CLUE_ITEM_2705(new String[]{"Search the chests upstairs in", "Al Kharid palace."}, 2705, new Position(3301, 3169, 1), 378, 832, 1),
    CLUE_ITEM_2706(new String[]{"Search the Coffin in Edgeville."}, 2706, new Position(3090, 3476, 0), 0, 832, 1),
    CLUE_ITEM_2707(new String[]{"Search the crate in the left-", "hand tower of Lumbridge", "castle"}, 2707, new Position(3228, 3212, 1), -1, 832, 1),
    CLUE_ITEM_2708(new String[]{"Search the crate near a cart ", "in Port Khazard."}, 2708, new Position(2660, 3149, 0), -1, 832, 1),
    CLUE_ITEM_2709(new String[]{"Search the crates in a house ", "in Yanille that has a piano"}, 2709, new Position(2598, 3105, 0), -1, 832, 1),
    CLUE_ITEM_2710(new String[]{"Search the crates in Canifis"}, 2710, new Position(3509, 3497, 0), -1, 832, 1),
    CLUE_ITEM_2711(new String[]{"Search the crates in Draynor ", "Manor"}, 2711, new Position(3106, 3369, 2), -1, 832, 1),
    CLUE_ITEM_2712(new String[]{"Search the crates in East", "Ardougne's general store."}, 2712, new Position(2615, 3291, 0), -1, 832, 1),
    CLUE_ITEM_3490(new String[]{"Search the crates of", "Falador's general store"}, 3490, new Position(2955, 3390, 0), -1, 832, 1),
    CLUE_ITEM_3491(new String[]{"Search the crates in Horvik's ", "armoury"}, 3491, new Position(3228, 3433, 0), -1, 832, 1),
    CLUE_ITEM_3492(new String[]{"Search the crates in", "Barbarian Village", "helmet shop"}, 3492, new Position(3073, 3430, 0), -1, 832, 1),
    CLUE_ITEM_3493(new String[]{"Search the crates in the ", "guard house of the northern ", "gate of East Ardougne."}, 3493, new Position(2645, 3338, 0), -1, 832, 1),
    CLUE_ITEM_3494(new String[]{"Search the crates in the ", "most north-western house in Al Kharid"}, 3494, new Position(3289, 3202, 0), -1, 832, 1),
    CLUE_ITEM_3495(new String[]{"Search the crates in the Port ", "Sarim fishing shop"}, 3495, new Position(3012, 3222, 0), -1, 832, 1),
    CLUE_ITEM_3496(new String[]{"Search the crates in the shed ", "just north of east Ardougne"}, 3496, new Position(2617, 3347, 0), -1, 832, 1),
    CLUE_ITEM_3497(new String[]{"Search the crates near a cart ", "in Varrock."}, 3497, new Position(3226, 3452, 0), -1, 832, 1),
    CLUE_ITEM_3498(new String[]{"Search the drawers above", "Varrock's shops."}, 3498, new Position(3206, 3419, 1), 351, 832, 1),
    CLUE_ITEM_3499(new String[]{"Search the drawers in a ", "house in Draynor Village."}, 3499, new Position(3097, 3277, 0), 351, 832, 1),
    CLUE_ITEM_3500(new String[]{"Search the drawers in", "Falador's chainmail shop"}, 3500, new Position(2969, 3311, 0), 349, 832, 1),
    CLUE_ITEM_3501(new String[]{"Search the drawers in one of", "Gertrude's bedrooms"}, 3501, new Position(3156, 3406, 0), 349, 832, 1),
    CLUE_ITEM_3502(new String[]{"Search the drawers in the ", "upstairs of a house in ", "Catherby."}, 3502, new Position(2809, 3451, 1), 351, 832, 1),
    CLUE_ITEM_3503(new String[]{"Search the drawers of houses ", "in Burthorpe"}, 3503, new Position(2929, 3570, 0), 349, 832, 1),
    CLUE_ITEM_3504(new String[]{"Search the drawers upstairs ", "in Falador's shield shop."}, 3504, new Position(2971, 3386, 1), 349, 832, 1),
    CLUE_ITEM_3505(new String[]{"Search the drawers in the ", "upstairs of the bank to the ", "east of Varrock"}, 3505, new Position(3250, 3420, 1), 349, 832, 1),
    CLUE_ITEM_3506(new String[]{"Search the tents in the ", "imperial guard camp in", "Burthorpe for some boxes"}, 3506, new Position(2885, 3540, 0), -1, 832, 1),
    CLUE_ITEM_3507(new String[]{"Search through chests found ", "in the upstairs of houses in ", "eastern Falador."}, 3507, new Position(3041, 3364, 1), 378, 832, 1),
    CLUE_ITEM_3508(new String[]{"Search through some drawers", "in the upstairs of a", "house in Rimmington."}, 3508, new Position(2970, 3214, 1), 353, 832, 1),
    CLUE_ITEM_3509(new String[]{"Search through some drawers ", "found in Taverley's houses."}, 3509, new Position(2894, 3418, 0), 351, 832, 1),
    CLUE_ITEM_3510(new String[]{"Search upstairs in the ", "houses of Seers' Village for ", "some drawers."}, 3510, new Position(2716, 3471, 1), 348, 832, 1),
    CLUE_ITEM_3614(new String[]{"A town with a different sort of", "night-life is your destination. ", "Search for some crates in one", "of the houses."}, 3614, new Position(3498, 3507, 0), -1, 832, 2),
    CLUE_ITEM_3615(new String[]{"Go to the village being", "attacked by trolls, search the", "drawers in one of the houses."}, 3615, new Position(2921, 3577, 0), 351, 832, 2),
    CLUE_ITEM_3616(new String[]{"Go to this ", "building to be ", "illuminated, and ", "search the ", "drawers while ", "you're there."}, 3616, new Position(2512, 3641, 1), 351, 832, 2),
    CLUE_ITEM_3617(new String[]{"In a town where everyone has", "perfect vision, seek some", "locked drawers in a house that", "sits opposite a workshop."}, 3617, new Position(2709, 3478, 0), 351, 832, 2),
    CLUE_ITEM_3618(new String[]{"In a town where the ", "guards are armed with ", "maces, search the ", "upstairs room of the ", "Public House."}, 3618, new Position(2574, 3326, 1), 349, 832, 2),
    CLUE_ITEM_7274(new String[]{"In a town where thieves ", "steal from stalls, search ", "for some drawers ", "upstairs of a house near ", "the bank."}, 7274, new Position(2611, 3324, 1), 349, 832, 2),
    CLUE_ITEM_7276(new String[]{"In a town where wizards are", "known to gather, search", "upstairs in a large", "house to the north."}, 7276, new Position(2593, 3108, 1), 378, 832, 2),
    CLUE_ITEM_7278(new String[]{"In a village made of ", "bamboo look for some ", "crates under one of the ", "houses."}, 7278, new Position(2800, 3074, 0), 0, 832, 2),
    CLUE_ITEM_7280(new String[]{"Probably filled ", "with wizards' ", "socks"}, 7280, new Position(3116, 9562, 0), 351, 832, 2),
    CLUE_ITEM_7282(new String[]{"Search the upstairs drawers ", "of a house in a village were ", "pirates are known to have a ", "good time."}, 7282, new Position(2809, 3165, 1), 349, 832, 2),
    CLUE_ITEM_7284(new String[]{"The dead, red dragon watches", "over this chest.", "He must really dig the view."}, 7284, new Position(3353, 3332, 0), 378, 832, 2),
    CLUE_ITEM_7296(new String[]{"This crate holds a better ", "reward than a broken ", "arrow"}, 7296, new Position(2671, 3437, 0), 0, 832, 2),
    CLUE_ITEM_7298(new String[]{"This crate is mine, all ", "mine, even if it is in the ", "middle of the desert."}, 7298, new Position(3289, 3022, 0), 0, 832, 2),
    CLUE_ITEM_7300(new String[]{"You'll need to look for ", "a town with a central ", "fountain. Look for a locked", "chest in the town's chapel."}, 7300, new Position(3256, 3487, 0), 378, 832, 2),
    CLUE_ITEM_3572(new String[]{"Four blades I have, yet draw no", "blood;", "Still I turn my prey to powder.", "If you are brave, come search my", "roof;", "It is there my blades are louder."}, 3572, new Position(3166, 3309, 2), 0, 832, 3),
    CLUE_ITEM_3573(new String[]{"A great view: watch", "the rapidly drying", "hides get splashed.", "Check the box you are sitting on."}, 3573, new Position(2523, 3493, 1), 0, 832, 3),
    CLUE_ITEM_3574(new String[]{"His head might be hollow, but", "the crates nearby are filled with", "surprises."}, 3574, new Position(3478, 3091, 0), 0, 832, 3),
    CLUE_ITEM_3575(new String[]{"If you look closely enough,", "it seems that the archers have lost", "more than their needles."}, 3575, new Position(2671, 3415, 0), 0, 832, 3),
    CLUE_ITEM_3577(new String[]{"It seems to have reached the ", "end of the line, and it's still", "empty."}, 3577, new Position(3041, 9820, 0), 0, 832, 3),
    CLUE_ITEM_3579(new String[]{"My home is grey, and made of", "stone;", "A castle with a search for a meal.", "Hidden in some drawers I am,", "Across from a wooden wheel."}, 3579, new Position(3213, 3216, 1), 5619, 832, 3),
    CLUE_ITEM_3580(new String[]{"Probably filled with books", "on magic."}, 3580, new Position(3096, 9571, 0), 0, 832, 3),
    CLUE_ITEM_7243(new String[]{"Read 'How to breed scorpions'", "By O.W. Thathurt."}, 7243, new Position(2702, 3409, 1), 0, 832, 3),
    CLUE_ITEM_7245(new String[]{"The cheapest water for miles around,", "but they react badly to", "religious icons."}, 7245, new Position(3178, 2987, 0), 0, 832, 3),
    CLUE_ITEM_7247(new String[]{"This village has a ", "problem with cartloads ", "of the undead. Try ", "checking the bookcase ", "to find the answer."}, 7247, new Position(2833, 2991, 0), 0, 832, 3),
    CLUE_ITEM_7248(new String[]{"When no weapons are at hand,", "then is the time to reflect.", "In Saradomin's name,", "redemption draws closer..."}, 7248, new Position(2818, 3351, 0), 351, 832, 3),
    CLUE_ITEM_7249(new String[]{"You have all of the elements", "available to solve this clue.", "Fortunately you do not have to", "go so far as to stand in a", "draft."}, 7249, new Position(2723, 9891, 0), 0, 832, 3),
    CLUE_ITEM_7250(new String[]{"Try not to let yourself be ", "dazzled when you ", "search these drawers."}, 7250, new Position(2561, 3323, 0), 351, 832, 3);

    private String[] clueTextLines;
    private int clueItemId;
    private Position position;
    private int replacementObjectId;
    private int animationId;
    private int level;
    private static Map cluesByPosition;
    private static Map cluesByItemId;

    static {
        cluesByPosition = new HashMap();
        cluesByItemId = new HashMap();
        SearchClue[] searchClueArray = SearchClue.values();
        int length = searchClueArray.length;
        int index = 0;
        while (index < length) {
            SearchClue searchClue = searchClueArray[index];
            cluesByPosition.put(searchClue.position, searchClue);
            cluesByItemId.put(searchClue.clueItemId, searchClue);
            ++index;
        }
    }

    public static SearchClue forPosition(Position position) {
        int index = 0;
        while (index < SearchClue.values().length) {
            SearchClue searchClue = SearchClue.values()[index];
            if (searchClue.position.equals(position)) {
                return SearchClue.values()[index];
            }
            ++index;
        }
        return null;
    }

    public static SearchClue forClueItemId(int itemId) {
        return (SearchClue)((Object)cluesByItemId.get(itemId));
    }

    private SearchClue(String[] clueTextLines, int clueItemId, Position position, int replacementObjectId, int value32, int level) {
        this.clueTextLines = clueTextLines;
        this.clueItemId = clueItemId;
        this.position = position;
        this.replacementObjectId = replacementObjectId;
        this.animationId = 832;
        this.level = level;
    }

    public final String[] getClueTextLines() {
        return this.clueTextLines;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final int getReplacementObjectId() {
        return this.replacementObjectId;
    }

    public final int getAnimationId() {
        return this.animationId;
    }

    public final int getLevel() {
        return this.level;
    }
}

