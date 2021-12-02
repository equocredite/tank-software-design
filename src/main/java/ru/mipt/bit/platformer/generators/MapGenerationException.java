package ru.mipt.bit.platformer.generators;

/**
 * use-case
 */

public class MapGenerationException extends Exception {
    public MapGenerationException(String message) {
        super(message);
    }

    public MapGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
