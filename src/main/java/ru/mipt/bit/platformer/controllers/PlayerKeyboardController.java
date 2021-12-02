package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.controllers.commands.*;
import ru.mipt.bit.platformer.graphics.ToggleListener;
import ru.mipt.bit.platformer.input.KeyboardListener;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

/**
 * use-case / input port
 */

public class PlayerKeyboardController implements Controller {
    private final Tank player;
    private final ToggleListener toggleListener;

    public PlayerKeyboardController(Tank player, ToggleListener toggleListener) {
        this.player = player;
        this.toggleListener = toggleListener;
    }

    @Override
    public List<Command> getCommands() {
        Integer key = KeyboardListener.getPressedKey();
        Command command = buildCommandFromKey(key);
        return List.of(command);
    }

    private Command buildCommandFromKey(Integer key) {
        if (key == null) {
            return NoopCommand.INSTANCE;
        }
        if (key == Input.Keys.RIGHT || key == Input.Keys.D) {
            return new MoveCommand(player, Direction.RIGHT);
        }
        if (key == Input.Keys.UP || key == Input.Keys.W) {
            return new MoveCommand(player, Direction.UP);
        }
        if (key == Input.Keys.LEFT || key == Input.Keys.A) {
            return new MoveCommand(player, Direction.LEFT);
        }
        if (key == Input.Keys.DOWN || key == Input.Keys.S) {
            return new MoveCommand(player, Direction.DOWN);
        }
        if (key == Input.Keys.SPACE) {
            return new ShootCommand(player);
        }
        if (key == Input.Keys.L) {
            return new ToggleCommand(toggleListener);
        }
        throw new IllegalArgumentException();
    }
}
