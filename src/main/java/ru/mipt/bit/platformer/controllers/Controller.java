package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.controllers.commands.Command;

import java.util.List;

/**
 * entity
 */

public interface Controller {
    List<Command> getCommands();
}
