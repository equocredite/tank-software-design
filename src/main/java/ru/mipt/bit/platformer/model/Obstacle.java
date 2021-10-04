package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle {
    private final GridPoint2 coordinates;
    private final float rotation;

    public Obstacle(GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
