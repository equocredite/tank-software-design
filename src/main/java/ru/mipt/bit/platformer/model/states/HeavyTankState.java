package ru.mipt.bit.platformer.model.states;

import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.Level;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class HeavyTankState implements TankState {
    private final Tank tank;
    private final Level level;

    public HeavyTankState(Tank tank, Level level) {
        this.tank = tank;
        this.level = level;
    }

    @Override
    public void makeProgress(float deltaTime) {
        tank.setMovementProgress(clamp(tank.getMovementProgress() +
                deltaTime / tank.getBaseMovementSpeed() / 3f, 0f, 1f));
    }

    @Override
    public void shoot() {
        // noop
    }

    public void takeDamage() {
        level.removeTank(tank);
    }
}
