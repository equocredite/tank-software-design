package ru.mipt.bit.platformer.model.states;

import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.Level;

import static com.badlogic.gdx.math.MathUtils.clamp;

/**
 * use-case
 */

public class MediumTankState implements TankState {
    private static final int HEALTH = 2;

    private final Tank tank;
    private final Level level;

    public MediumTankState(Tank tank, Level level) {
        this.tank = tank;
        this.level = level;
    }

    @Override
    public void makeProgress(float deltaTime) {
        tank.setMovementProgress(clamp(tank.getMovementProgress() +
                deltaTime / tank.getBaseMovementSpeed() / 2f, 0f, 1f));
    }

    @Override
    public void shoot() {
        level.addBullet(new Bullet(tank.getCoordinates(), tank.getDirection(), level));
    }

    public void takeDamage() {
        tank.setState(new HeavyTankState(tank, level));
    }

    @Override
    public int getHealth() {
        return HEALTH;
    }
}
