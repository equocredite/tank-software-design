package ru.mipt.bit.platformer.controllers;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private final List<Controller> controllers;

    public CommandExecutor(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public CommandExecutor() {
        this(new ArrayList<>());
    }

    public void addController(Controller controller) {
        controllers.add(controller);
    }

    public void executeCommands() {
        for (var controller : controllers) {
            for (var command : controller.getCommands()) {
                command.execute();
            }
        }
    }
}
