package ru.mipt.bit.platformer.controllers.commands;

public enum NoopCommand implements Command {
    INSTANCE;

    @Override
    public void execute() {}
}
