package com.rs2.model.clue;

import com.rs2.model.Position;
import java.util.HashMap;
import java.util.Map;

public enum MapClue {
    CLUE_ITEM_2713(2713, 6994, new Position(3166, 3360), false, 1),
    CLUE_ITEM_2716(2716, 9275, new Position(3109, 3153), false, 1),
    CLUE_ITEM_2719(2719, 9108, new Position(2612, 3482), false, 1),
    CLUE_ITEM_3516(3516, 7271, new Position(3043, 3399), false, 1),
    CLUE_ITEM_3518(3518, 7045, new Position(3290, 3373), false, 1),
    CLUE_ITEM_7236(7236, 17537, new Position(2970, 3414), false, 1),
    CLUE_ITEM_2827(2827, 9720, new Position(2565, 3248), true, 2),
    CLUE_ITEM_2829(2829, 9196, new Position(2658, 3488), true, 2),
    CLUE_ITEM_7286(7286, 17907, new Position(2579, 3597), false, 2),
    CLUE_ITEM_7288(7288, 17888, new Position(2454, 3230), false, 2),
    CLUE_ITEM_7290(7290, 17774, new Position(3434, 3265), false, 2),
    CLUE_ITEM_3596(3596, 9632, new Position(2650, 3230), false, 2),
    CLUE_ITEM_3602(3602, 17687, new Position(2535, 3865), false, 2),
    CLUE_ITEM_3598(3598, 9839, new Position(2924, 3209), false, 2),
    CLUE_ITEM_3599(3599, 4305, new Position(2906, 3294), false, 2),
    CLUE_ITEM_7292(7292, 18055, new Position(2666, 3562), false, 2),
    CLUE_ITEM_3601(3601, 7113, new Position(3092, 3226), false, 2),
    CLUE_ITEM_2722(2722, 7221, new Position(3309, 3503), true, 3),
    CLUE_ITEM_3520(3520, 9454, new Position(2459, 3179), true, 3),
    CLUE_ITEM_3522(3522, 9507, new Position(3026, 3628), true, 3),
    CLUE_ITEM_3524(3524, 9043, new Position(2616, 3077), false, 3),
    CLUE_ITEM_7239(7239, 17620, new Position(3021, 3912), false, 3),
    CLUE_ITEM_7241(7241, 17634, new Position(2722, 3338), false, 3);

    private int clueItemId;
    private int interfaceId;
    private Position position;
    private boolean objectSearchClue;
    private int level;
    private static Map cluesByPosition;
    private static Map cluesByItemId;

    static {
        cluesByPosition = new HashMap();
        cluesByItemId = new HashMap();
        MapClue[] mapClueArray = MapClue.values();
        int length = mapClueArray.length;
        int index = 0;
        while (index < length) {
            MapClue mapClue = mapClueArray[index];
            cluesByPosition.put(mapClue.position, mapClue);
            cluesByItemId.put(mapClue.clueItemId, mapClue);
            ++index;
        }
    }

    public static MapClue forPosition(Position position) {
        int index = 0;
        while (index < MapClue.values().length) {
            MapClue mapClue = MapClue.values()[index];
            if (mapClue.position.equals(position)) {
                return MapClue.values()[index];
            }
            ++index;
        }
        return null;
    }

    public static MapClue forClueItemId(int itemId) {
        return (MapClue)((Object)cluesByItemId.get(itemId));
    }

    private MapClue(int clueItemId, int interfaceId, Position position, boolean objectSearchClue, int level) {
        this.clueItemId = clueItemId;
        this.interfaceId = interfaceId;
        this.position = position;
        this.objectSearchClue = objectSearchClue;
        this.level = level;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final int getInterfaceId() {
        return this.interfaceId;
    }

    public final boolean isObjectSearchClue() {
        return this.objectSearchClue;
    }

    public final int getLevel() {
        return this.level;
    }
}

