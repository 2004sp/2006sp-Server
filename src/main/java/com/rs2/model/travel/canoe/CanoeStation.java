package com.rs2.model.travel.canoe;

import com.rs2.model.Position;

public enum CanoeStation {
    LUMBRIDGE("Lumbridge", new Position(3244, 3237), 18255, new int[]{18234, 18271}),
    CHAMPIONS_GUILD("Champions' Guild", new Position(3202, 3344), 18256, new int[]{18236, 18268}),
    BARBARIAN_VILLAGE("Barbarian Village", new Position(3113, 3411), 18257, new int[]{18238, 18265}),
    EDGEVILLE("Edgeville", new Position(3134, 3510), 18262, new int[]{18261, 18259}),
    WILDERNESS("Wilderness", new Position(3145, 3798), 18258, new int[]{18254, -1});

    int buttonId;
    int[] destinationInterfaceIds;
    String displayName;
    Position destination;

    private CanoeStation(String displayName, Position position, int buttonId, int[] destinationInterfaceIds) {
        this.displayName = displayName;
        this.destination = position;
        this.buttonId = buttonId;
        this.destinationInterfaceIds = destinationInterfaceIds;
    }
}

