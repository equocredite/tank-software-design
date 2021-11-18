package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.controllers.commands.Command;
import ru.mipt.bit.platformer.controllers.commands.MoveCommand;
import ru.mipt.bit.platformer.controllers.commands.NoopCommand;
import ru.mipt.bit.platformer.input.KeyboardListener;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class PlayerKeyboardController implements Controller {
    private final Tank player;

    public PlayerKeyboardController(Tank player) {
        this.player = player;
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
        throw new IllegalArgumentException();
    }
}
