package com.rs2.model.travel;

import com.rs2.model.Position;

public enum ShipRoute {
    PORT_SARIM_TO_ENTRANA("Entrana", new Position(2834, 3335), new Position(2834, 3332, 1), 15, 0, 420),
    ENTRANA_TO_PORT_SARIM("Port Sarim", new Position(3048, 3234), new Position(3048, 3231, 1), 15, 0, 420),
    PORT_SARIM_TO_CRANDOR(null, new Position(2852, 3235), null, 15, 0, 420),
    CRANDOR_TO_PORT_SARIM(null, new Position(2834, 3335), null, 14, 0, 350),
    PORT_SARIM_TO_KARAMJA("Karamja", new Position(2956, 3146), new Position(2956, 3143, 1), 10, 30, 250),
    KARAMJA_TO_PORT_SARIM("Port Sarim", new Position(3029, 3217), new Position(3032, 3217, 1), 10, 30, 250),
    ARDOUGNE_TO_BRIMHAVEN("Brimhaven", new Position(2772, 3234), new Position(2775, 3234, 1), 4, 30, 100),
    BRIMHAVEN_TO_ARDOUGNE("Ardougne", new Position(2683, 3271), new Position(2683, 3268, 1), 4, 30, 100),
    UNUSED1(null, null, null, 0, 0, 0),
    UNUSED2(null, null, null, 0, 0, 0),
    PORT_KHAZARD_TO_SHIP_YARD(null, new Position(2998, 3043), null, 24, 0, 600),
    SHIP_YARD_TO_PORT_KHAZARD(null, new Position(2676, 3170), null, 24, 0, 600),
    CAIRN_ISLAND_TO_SHIP_YARD(null, new Position(2998, 3043), null, 17, 0, 425),
    PORT_SARIM_TO_PEST_CONTROL(null, new Position(2659, 2676), null, 12, 0, 300),
    PEST_CONTROL_TO_PORT_SARIM(null, new Position(3041, 3202), null, 12, 0, 300),
    FELDIP_HILLS_TO_CAIRN_ISLAND(null, new Position(2763, 2956, 1), null, 10, 0, 250),
    RELLEKKA_TO_WATERBIRTH("Waterbirth Island", new Position(2550, 3759, 0), null, 10, 1000, 250),
    WATERBIRTH_TO_RELLEKKA("Rellekka", new Position(2620, 3686, 0), null, 10, 0, 250),
    RELLEKKA_TO_MISCELLANIA("Miscellania", new Position(2581, 3845, 0), null, 10, 0, 250),
    MISCELLANIA_TO_RELLEKKA("Rellekka", new Position(2629, 3693, 0), null, 10, 0, 250);

    String arrivalName;
    int fareCoins;
    Position destinationPosition;
    Position interfaceDestinationPosition;
    int arrivalDelayTicks;
    int jingleDelayTicks;

    private ShipRoute(String arrivalName, Position position, Position position2, int arrivalDelayTicks, int fareCoins, int jingleDelayTicks) {
        this.arrivalName = arrivalName;
        this.destinationPosition = position;
        this.interfaceDestinationPosition = position2;
        this.arrivalDelayTicks = arrivalDelayTicks;
        this.fareCoins = fareCoins;
        this.jingleDelayTicks = jingleDelayTicks;
    }
}

