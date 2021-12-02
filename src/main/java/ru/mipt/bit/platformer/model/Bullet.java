package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.physics.Level;

import static com.badlogic.gdx.math.MathUtils.clamp;

/**
 * entity
 */

public class Bullet {
    private final int damage = 1;
    private final float movementSpeed = 0.2f;
    private float movementProgress = 1f;

    private GridPoint2 coordinates;
    private final Direction direction;
    private final Level level;

    public Bullet(GridPoint2 source, Direction direction, Level level) {
        this.coordinates = direction.calcDestinationCoordinatesFrom(source);
        this.direction = direction;
        this.level = level;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return direction.calcDestinationCoordinatesFrom(coordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void tick(float deltaTime) {
        if (tryCollide()) {
            level.removeBullet(this);
            return;
        }

        movementProgress = clamp(movementProgress + deltaTime / movementSpeed, 0f, 1f);
        if (movementProgress == 1f) {
            coordinates = getDestinationCoordinates();
            movementProgress = 0f;
        }
    }

    private boolean tryCollide() {
        var collisionManager = level.getCollisionManager();
        if (collisionManager.collidesWithObstacles(coordinates)) {
            return true;
        }
        var tank = collisionManager.findCollidingTank(coordinates);
        if (tank != null) {
            tank.takeDamage();
            return true;
        }
        return false;
    }
}
