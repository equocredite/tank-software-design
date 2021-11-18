package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.controllers.commands.Command;

import java.util.List;

public interface Controller {
    List<Command> getCommands();
}
