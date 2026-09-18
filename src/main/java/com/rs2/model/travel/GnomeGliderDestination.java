package com.rs2.model.travel;

import com.rs2.model.Position;

public enum GnomeGliderDestination {
    SINDARPOS(826, new Position(2848, 3497), 0),
    TA_QUIR_PRIW(825, new Position(2465, 3501, 3), 1),
    LEMANTO_ANDRA(827, new Position(3321, 3427), 2),
    KAR_HEWO(828, new Position(3278, 3212), 3),
    GANDIUS(824, new Position(2971, 2969), 4),
    LEMANTOLLY_UNDRI(12342, new Position(2544, 2970), 5);

    int buttonId;
    Position destination;
    int routeIndex;

    private GnomeGliderDestination(int buttonId, Position position, int routeIndex) {
        this.buttonId = buttonId;
        this.destination = position;
        this.routeIndex = routeIndex;
    }
}

