package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/**
 * entity
 */

public class Obstacle {
    private final GridPoint2 coordinates;
    private final float rotation;

    public Obstacle(GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates;
        this.rotation = rotation;
    }

    public Obstacle(GridPoint2 coordinates) {
        this(coordinates, 0f);
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
