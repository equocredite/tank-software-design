package ru.mipt.bit.platformer.model.states;

public interface TankState {
    void makeProgress(float deltaTime);

    void shoot();

    void takeDamage();
}
