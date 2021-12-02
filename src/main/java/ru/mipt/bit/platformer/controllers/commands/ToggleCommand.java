package ru.mipt.bit.platformer.controllers.commands;

import ru.mipt.bit.platformer.graphics.ToggleListener;

/**
 * use-case
 */

public class ToggleCommand implements Command {
    private final ToggleListener listener;

    public ToggleCommand(ToggleListener listener) {
        this.listener = listener;
    }

    public void execute() {
        listener.toggle();
    }
}
