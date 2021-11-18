package ru.mipt.bit.platformer.controllers.ai;

import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.controllers.commands.Command;
import ru.mipt.bit.platformer.controllers.commands.MoveCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomAIController implements Controller {
    private final List<Tank> bots;

    public RandomAIController(List<Tank> bots) {
        this.bots = bots;
    }

    @Override
    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        for (var bot : bots) {
            commands.add(new MoveCommand(bot, randomDirection()));
        }
        return commands;
    }

    public static Direction randomDirection(Random rng) {
        var values = Direction.values();
        return values[rng.nextInt(values.length)];
    }

    public static Direction randomDirection() {
        return randomDirection(ThreadLocalRandom.current());
    }
}
