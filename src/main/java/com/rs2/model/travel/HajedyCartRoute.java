package com.rs2.model.travel;

import com.rs2.model.Position;

public enum HajedyCartRoute {
    BRIMHAVEN_TO_SHILO(new Position(2836, 2956), 3, 0),
    SHILO_TO_BRIMHAVEN(new Position(2780, 3211), 3, 0);

    int fareCoins;
    Position destination;

    private HajedyCartRoute(Position destination, int fareCoins, int unused) {
        this.destination = destination;
        this.fareCoins = fareCoins;
    }
}

