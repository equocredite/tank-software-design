package ru.mipt.bit.platformer.controllers.commands;

/**
 * use-case
 */

public enum NoopCommand implements Command {
    INSTANCE;

    @Override
    public void execute() {}
}
