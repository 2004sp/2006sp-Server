package com.rs2.model.clue;

import com.rs2.model.Position;
import com.rs2.model.clue.CoordinateClueHandler;
import java.util.HashMap;
import java.util.Map;

public enum CoordinateClue {
    CLUE_ITEM_2801(2801, 0, 5, 1, 13, "south", "east", 2),
    CLUE_ITEM_2803(2803, 0, 13, 13, 58, "south", "east", 2),
    CLUE_ITEM_2805(2805, 0, 18, 9, 28, "south", "east", 2),
    CLUE_ITEM_2807(2807, 0, 20, 23, 15, "south", "east", 2),
    CLUE_ITEM_2809(2809, 0, 30, 24, 16, "north", "east", 2),
    CLUE_ITEM_2811(2811, 0, 31, 17, 43, "south", "east", 2),
    CLUE_ITEM_2813(2813, 2, 48, 22, 30, "north", "east", 2),
    CLUE_ITEM_2815(2815, 2, 50, 6, 20, "north", "east", 2),
    CLUE_ITEM_2817(2817, 3, 35, 13, 35, "south", "east", 2),
    CLUE_ITEM_2819(2819, 4, 0, 12, 46, "south", "east", 2),
    CLUE_ITEM_2821(2821, 4, 13, 12, 45, "north", "east", 2),
    CLUE_ITEM_2823(2823, 5, 20, 4, 28, "south", "east", 2),
    CLUE_ITEM_2825(2825, 5, 43, 23, 5, "north", "east", 2),
    CLUE_ITEM_3582(3582, 6, 31, 1, 46, "north", "west", 2),
    CLUE_ITEM_3584(3584, 7, 5, 30, 56, "north", "east", 2),
    CLUE_ITEM_3586(3586, 7, 33, 15, 0, "north", "east", 2),
    CLUE_ITEM_3588(3588, 8, 33, 1, 39, "north", "west", 2),
    CLUE_ITEM_3590(3590, 9, 48, 17, 39, "north", "east", 2),
    CLUE_ITEM_3592(3592, 11, 3, 31, 20, "north", "east", 2),
    CLUE_ITEM_3594(3594, 11, 5, 0, 45, "north", "west", 2),
    CLUE_ITEM_7305(7305, 11, 41, 14, 58, "north", "east", 2),
    CLUE_ITEM_7307(7307, 14, 54, 9, 13, "north", "east", 2),
    CLUE_ITEM_2723(2723, 3, 45, 22, 45, "south", "east", 3),
    CLUE_ITEM_2725(2725, 6, 0, 21, 48, "south", "east", 3),
    CLUE_ITEM_2727(2727, 6, 11, 15, 7, "south", "east", 3),
    CLUE_ITEM_2729(2729, 8, 3, 31, 16, "north", "east", 3),
    CLUE_ITEM_2731(2731, 13, 46, 21, 1, "north", "east", 3),
    CLUE_ITEM_2733(2733, 16, 7, 22, 45, "north", "east", 3),
    CLUE_ITEM_2735(2735, 16, 35, 27, 1, "north", "east", 3),
    CLUE_ITEM_2737(2737, 16, 43, 19, 13, "north", "east", 3),
    CLUE_ITEM_2739(2739, 16, 43, 26, 56, "north", "east", 3),
    CLUE_ITEM_2741(2741, 17, 50, 8, 30, "north", "east", 3),
    CLUE_ITEM_2743(2743, 18, 22, 16, 33, "north", "east", 3),
    CLUE_ITEM_2745(2745, 19, 43, 25, 7, "north", "east", 3),
    CLUE_ITEM_2747(2747, 20, 5, 21, 52, "north", "east", 3),
    CLUE_ITEM_3526(3526, 21, 24, 17, 54, "north", "east", 3),
    CLUE_ITEM_3528(3528, 22, 35, 19, 18, "north", "east", 3),
    CLUE_ITEM_3530(3530, 22, 45, 26, 33, "north", "east", 3),
    CLUE_ITEM_3532(3532, 24, 24, 26, 24, "north", "east", 3),
    CLUE_ITEM_3534(3534, 24, 58, 18, 43, "north", "east", 3),
    CLUE_ITEM_3536(3536, 25, 3, 17, 5, "north", "east", 3),
    CLUE_ITEM_3538(3538, 25, 3, 23, 24, "north", "east", 3);

    private int clueItemId;
    private int latitudeDegrees;
    private int latitudeMinutes;
    private int longitudeDegrees;
    private int longitudeMinutes;
    private String latitudeDirection;
    private String longitudeDirection;
    private int level;
    private Position position;
    private static Map cluesByItemId;
    private static Map cluesByPosition;

    static {
        cluesByItemId = new HashMap();
        cluesByPosition = new HashMap();
        CoordinateClue[] coordinateClueArray = CoordinateClue.values();
        int length = coordinateClueArray.length;
        int index = 0;
        while (index < length) {
            CoordinateClue coordinateClue = coordinateClueArray[index];
            coordinateClueArray[index].position = CoordinateClueHandler.resolvePosition(coordinateClue.latitudeDegrees, coordinateClue.latitudeMinutes, coordinateClue.longitudeDegrees, coordinateClue.longitudeMinutes, coordinateClue.latitudeDirection, coordinateClue.longitudeDirection);
            cluesByItemId.put(coordinateClue.clueItemId, coordinateClue);
            cluesByPosition.put(coordinateClue.position, coordinateClue);
            ++index;
        }
    }

    public static CoordinateClue forPosition(Position position) {
        int index = 0;
        while (index < CoordinateClue.values().length) {
            CoordinateClue coordinateClue = CoordinateClue.values()[index];
            if (coordinateClue.position.equals(position)) {
                return CoordinateClue.values()[index];
            }
            ++index;
        }
        return null;
    }

    public static CoordinateClue forClueItemId(int itemId) {
        return (CoordinateClue)((Object)cluesByItemId.get(itemId));
    }

    private CoordinateClue(int clueItemId, int latitudeDegrees, int latitudeMinutes, int longitudeDegrees, int longitudeMinutes, String latitudeDirection, String longitudeDirection, int level) {
        this.clueItemId = clueItemId;
        this.latitudeDegrees = latitudeDegrees;
        this.latitudeMinutes = latitudeMinutes;
        this.longitudeDegrees = longitudeDegrees;
        this.longitudeMinutes = longitudeMinutes;
        this.latitudeDirection = latitudeDirection;
        this.longitudeDirection = longitudeDirection;
        this.level = level;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final int getLatitudeDegrees() {
        return this.latitudeDegrees;
    }

    public final int getLatitudeMinutes() {
        return this.latitudeMinutes;
    }

    public final int getLongitudeDegrees() {
        return this.longitudeDegrees;
    }

    public final int getLongitudeMinutes() {
        return this.longitudeMinutes;
    }

    public final String getLatitudeDirection() {
        return this.latitudeDirection;
    }

    public final String getLongitudeDirection() {
        return this.longitudeDirection;
    }

    public final int getLevel() {
        return this.level;
    }
}

