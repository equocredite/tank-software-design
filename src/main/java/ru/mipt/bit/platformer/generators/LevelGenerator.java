package ru.mipt.bit.platformer.generators;

import ru.mipt.bit.platformer.physics.Level;

public interface LevelGenerator {
    Level generateLevel() throws MapGenerationException;
}
