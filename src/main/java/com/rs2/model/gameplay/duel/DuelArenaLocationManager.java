package com.rs2.model.gameplay.duel;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.gameplay.PositionRange;
import com.rs2.model.player.Player;
import com.rs2.util.path.WalkingCollisionMap;

public final class DuelArenaLocationManager {
    private static PositionRange[] obstacleArenaStartAreas = new PositionRange[]{new PositionRange(new Position(3367, 3246, 0), new Position(3385, 3256, 0)), new PositionRange(new Position(3336, 3227, 0), new Position(3355, 3237, 0)), new PositionRange(new Position(3367, 3208, 0), new Position(3386, 3218, 0))};
    private static PositionRange[] standardArenaStartAreas = new PositionRange[]{new PositionRange(new Position(3337, 3245, 0), new Position(3355, 3256, 0)), new PositionRange(new Position(3366, 3227, 0), new Position(3386, 3237, 0)), new PositionRange(new Position(3337, 3207, 0), new Position(3354, 3218, 0))};

    public DuelArenaLocationManager(Player player) {
    }

    public static Position findAdjacentOpenPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        Position position2 = new Position(x, y + 1);
        Position position3 = new Position(x, y - 1);
        Position position4 = new Position(x - 1, y);
        Position position5 = new Position(x + 1, y);
        if (WalkingCollisionMap.getTileFlags(position2.getX(), position2.getY(), 0) == 0) {
            return position2;
        }
        if (WalkingCollisionMap.getTileFlags(position3.getX(), position3.getY(), 0) == 0) {
            return position3;
        }
        if (WalkingCollisionMap.getTileFlags(position4.getX(), position4.getY(), 0) == 0) {
            return position4;
        }
        return position5;
    }

    public static Position randomExitPosition() {
        return GameplayHelper.randomUnblockedPositionInRange(new PositionRange(new Position(3356, 3269, 0), new Position(3379, 3280)));
    }

    public final Position randomStartPosition(boolean enabled2, int value2) {
        return GameplayHelper.randomUnblockedPositionInRange((enabled2 ? obstacleArenaStartAreas : standardArenaStartAreas)[value2]);
    }
}

