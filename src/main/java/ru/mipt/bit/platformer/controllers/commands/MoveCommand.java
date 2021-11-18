package ru.mipt.bit.platformer.controllers.commands;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommand implements Command {
    private final Tank tank;
    private final Direction direction;

    public MoveCommand(Tank tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        if (tank != null && direction != null) {
            tank.tryRotateAndStartMovement(direction);
        }
    }
}
