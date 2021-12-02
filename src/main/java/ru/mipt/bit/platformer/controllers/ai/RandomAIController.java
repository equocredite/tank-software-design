package ru.mipt.bit.platformer.controllers.ai;

import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.controllers.commands.Command;
import ru.mipt.bit.platformer.controllers.commands.MoveCommand;
import ru.mipt.bit.platformer.controllers.commands.ShootCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * driven port
 */

public class RandomAIController implements Controller {
    private final List<Tank> bots;

    public RandomAIController(List<Tank> bots) {
        this.bots = bots;
    }

    @Override
    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        for (var bot : bots) {
            commands.add(generateRandomCommand(bot));
        }
        return commands;
    }

    private static Command generateRandomCommand(Tank tank) {
        var directions = Direction.values();
        int commandId = ThreadLocalRandom.current().nextInt(directions.length + 1);
        if (commandId < directions.length) {
            return new MoveCommand(tank, directions[commandId]);
        }
        return new ShootCommand(tank);
    }
}
