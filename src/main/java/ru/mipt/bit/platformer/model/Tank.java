package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.states.LightTankState;
import ru.mipt.bit.platformer.model.states.TankState;
import ru.mipt.bit.platformer.physics.Level;

public class Tank implements LivingGameObject {
    private TankState state;

    private final float baseMovementSpeed;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private float movementProgress = 1f;

    private final Level level;

    public Tank(GridPoint2 coordinates, float baseMovementSpeed, Direction direction, Level level) {
        this.state = new LightTankState(this, level);
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.baseMovementSpeed = baseMovementSpeed;
        this.direction = direction;
        this.level = level;
    }

    public void setState(TankState state) {
        this.state = state;
    }

    public Tank(GridPoint2 coordinates, float baseMovementSpeed, Level level) {
        this(coordinates, baseMovementSpeed, Direction.RIGHT, level);
    }

    public float getBaseMovementSpeed() {
        return baseMovementSpeed;
    }

    @Override
    public int getHealth() {
        return state.getHealth();
    }

    public void takeDamage() {
        state.takeDamage();
    }

    public void shoot() {
        state.shoot();
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

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
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
        state.makeProgress(deltaTime);
        tryFinishMovement();
    }
}
