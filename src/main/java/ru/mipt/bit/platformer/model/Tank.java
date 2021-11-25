package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.physics.Level;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class Tank {
    private int health = 2;

    private final float movementSpeed;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private float movementProgress = 1f;

    private final Level level;

    public Tank(GridPoint2 coordinates, float movementSpeed, Direction direction, Level level) {
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.level = level;
    }

    public Tank(GridPoint2 coordinates, float movementSpeed, Level level) {
        this(coordinates, movementSpeed, Direction.RIGHT, level);
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            level.removeTank(this);
        }
    }

    public void shoot() {
        level.addBullet(new Bullet(coordinates, direction, level));
    }

    public float getRotation() {
        return direction.getAngle();
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    private void tryFinishMovement() {
        if (!isMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void tryRotateAndStartMovement(Direction newDirection) {
        if (isMoving()) {
            return;
        }
        direction = newDirection;
        var newDestination = direction.calcDestinationCoordinatesFrom(coordinates);
        if (level.getCollisionManager().canMove(this, newDestination)) {
            destinationCoordinates = newDestination;
            movementProgress = 0f;
        }
    }

    public void tick(float deltaTime) {
        movementProgress = clamp(movementProgress + deltaTime / movementSpeed, 0f, 1f);
        tryFinishMovement();
    }
}
