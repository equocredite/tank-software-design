package ru.mipt.bit.platformer.generators;

public class MapGenerationException extends Exception {
    public MapGenerationException(String message) {
        super(message);
    }

    public MapGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
