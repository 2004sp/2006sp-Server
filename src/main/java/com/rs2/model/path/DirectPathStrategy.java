package com.rs2.model.path;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.path.PathResult;
import com.rs2.model.path.PathStep;
import com.rs2.model.path.PathStrategy;
import com.rs2.util.GameUtil;

public final class DirectPathStrategy
implements PathStrategy {
    @Override
    public final PathResult buildPath(Entity entity, Position position, boolean enabled2) {
        PathResult pathResult = new PathResult();
        Position currentPosition = entity.getPosition().copy();
        Position previousPosition = currentPosition.copy();
        int x = position.getX() - currentPosition.getX();
        int y = position.getY() - currentPosition.getY();
        int index = 0;
        int index2 = 0;
        int index3 = 0;
        int index4 = 0;
        if (x < 0) {
            index = -1;
        } else if (x > 0) {
            index = 1;
        }
        if (y < 0) {
            index2 = -1;
        } else if (y > 0) {
            index2 = 1;
        }
        if (x < 0) {
            index3 = -1;
        } else if (x > 0) {
            index3 = 1;
        }
        int value = Math.abs(x);
        int value2 = Math.abs(y);
        if (value <= value2) {
            value = Math.abs(y);
            value2 = Math.abs(x);
            if (y < 0) {
                index4 = -1;
            } else if (y > 0) {
                index4 = 1;
            }
            index3 = 0;
        }
        y = value >> 1;
        x = 0;
        while (x <= value) {
            y += value2;
            if (!previousPosition.equals(currentPosition) && enabled2 && !GameUtil.hasClearPath(currentPosition, previousPosition, true)) {
                return pathResult;
            }
            previousPosition = currentPosition.copy();
            pathResult.getSteps().add(new PathStep(currentPosition.getX(), currentPosition.getY()));
            if (y >= value) {
                y -= value;
                currentPosition.setX(currentPosition.getX() + index);
                currentPosition.setY(currentPosition.getY() + index2);
            } else {
                currentPosition.setX(currentPosition.getX() + index3);
                currentPosition.setY(currentPosition.getY() + index4);
            }
            ++x;
        }
        return pathResult;
    }

}

