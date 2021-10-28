package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.physics.CollisionManager;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class Tank {
    private final float movementSpeed;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private float movementProgress = 1f;

    private final CollisionManager collisionManager;

    public Tank(GridPoint2 coordinates, float movementSpeed, CollisionManager collisionManager, Direction direction) {
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.movementSpeed = movementSpeed;
        this.collisionManager = collisionManager;
        this.direction = direction;
    }

    public Tank(GridPoint2 coordinates, float movementSpeed, CollisionManager collisionManager) {
        this(coordinates, movementSpeed, collisionManager, Direction.RIGHT);
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
        if (collisionManager.canMove(this, newDestination)) {
            destinationCoordinates = newDestination;
            movementProgress = 0f;
        }
    }

    public void makeProgress(float deltaTime) {
        movementProgress = clamp(movementProgress + deltaTime / movementSpeed, 0f, 1f);
        tryFinishMovement();
    }
}
