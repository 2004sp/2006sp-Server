package com.rs2.model.gameplay.barrows;

import com.rs2.util.RectangularArea;

public final class BarrowsTunnelRoom {
    int roomId;
    int[] connectedRoomIds;
    int[] doorBitIndexes;
    RectangularArea roomBounds;

    BarrowsTunnelRoom(int roomId, int[] connectedRoomIds, int[] doorBitIndexes, RectangularArea rectangularArea) {
        this.roomId = roomId;
        this.connectedRoomIds = connectedRoomIds;
        this.doorBitIndexes = doorBitIndexes;
        this.roomBounds = rectangularArea;
    }
}

