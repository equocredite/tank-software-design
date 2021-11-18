package ru.mipt.bit.platformer.physics;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollisionManager {
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();
    private final int mapHeight;
    private final int mapWidth;

    public CollisionManager(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void addTanks(Collection<Tank> tanks) {
        this.tanks.addAll(tanks);
    }

    public void addObstacles(Collection<Obstacle> obstacles) {
        this.obstacles.addAll(obstacles);
    }

    private boolean isLegalMapPosition(GridPoint2 point) {
        return point.x >= 0 && point.x < mapWidth && point.y >= 0 && point.y < mapHeight;
    }

    private boolean collidesWithObstacles(GridPoint2 dest) {
        for (var obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesWithOtherTanks(Tank tank, GridPoint2 dest) {
        for (var otherTank : tanks) {
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
        return isLegalMapPosition(dest) && !collidesWithObstacles(dest) && !collidesWithOtherTanks(tank, dest);
    }
}
