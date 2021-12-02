package ru.mipt.bit.platformer.physics;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Tank;

/**
 * entity
 */

public class CollisionManager {
    private final Level level;

    public CollisionManager(Level level) {
        this.level = level;
    }

    public boolean isValidPosition(GridPoint2 point) {
        return point.x >= 0 && point.x < level.getWidth() && point.y >= 0 && point.y < level.getHeight();
    }

    public boolean collidesWithObstacles(GridPoint2 dest) {
        for (var obstacle : level.getObstacles()) {
            if (obstacle.getCoordinates().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    public Tank findCollidingTank(GridPoint2 point) {
        for (var tank : level.getTanks()) {
            if (tank.getCoordinates().equals(point)) {
                return tank;
            }
        }
        return null;
    }

    private boolean collidesWithOtherTanks(Tank tank, GridPoint2 dest) {
        for (var otherTank : level.getTanks()) {
            if (tank == otherTank) {
                continue;
            }
            if (otherTank.getCoordinates().equals(dest) || otherTank.getDestinationCoordinates().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    public boolean canMove(Tank tank, GridPoint2 dest) {
        return isValidPosition(dest) && !collidesWithObstacles(dest) && !collidesWithOtherTanks(tank, dest);
    }
}
