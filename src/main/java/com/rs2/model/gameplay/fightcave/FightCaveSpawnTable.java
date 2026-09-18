package com.rs2.model.gameplay.fightcave;

import com.rs2.model.Position;
import com.rs2.model.gameplay.PositionRange;
import java.util.HashMap;

public final class FightCaveSpawnTable {
    private static PositionRange northWestSpawnArea = new PositionRange(new Position(2378, 5103, 0), new Position(2382, 5108, 0));
    private static PositionRange centerSpawnArea = new PositionRange(new Position(2396, 5085, 0), new Position(2402, 5090, 0));
    private static PositionRange eastSpawnArea = new PositionRange(new Position(2415, 5079, 0), new Position(2420, 5084, 0));
    private static PositionRange southSpawnArea = new PositionRange(new Position(2396, 5069, 0), new Position(2403, 5074, 0));
    private static PositionRange southWestSpawnArea = new PositionRange(new Position(2377, 5067, 0), new Position(2383, 5072, 0));
    public static final PositionRange[] spawnAreaRotation;

    static {
        new Position(2381, 5105, 0);
        new Position(2398, 5087, 0);
        new Position(2418, 5081, 0);
        new Position(2400, 5074, 0);
        new Position(2381, 5070, 0);
        new HashMap();
        spawnAreaRotation = new PositionRange[]{eastSpawnArea, southWestSpawnArea, centerSpawnArea, northWestSpawnArea, southWestSpawnArea, eastSpawnArea, southSpawnArea, northWestSpawnArea, centerSpawnArea, eastSpawnArea, southWestSpawnArea, southSpawnArea, northWestSpawnArea, centerSpawnArea, southSpawnArea};
    }
}

