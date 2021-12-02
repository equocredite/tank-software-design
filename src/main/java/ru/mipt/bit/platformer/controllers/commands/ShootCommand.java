package ru.mipt.bit.platformer.controllers.commands;

import ru.mipt.bit.platformer.model.Tank;

/**
 * use-case
 */

public class ShootCommand implements Command {
    private final Tank tank;

    public ShootCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        tank.shoot();
    }
}
