package ru.mipt.bit.platformer.model.states;

/**
 * entity
 */

public interface TankState {
    void makeProgress(float deltaTime);

    void shoot();

    void takeDamage();

    int getHealth();
}
