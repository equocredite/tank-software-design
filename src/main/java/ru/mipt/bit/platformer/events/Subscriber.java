package ru.mipt.bit.platformer.events;

/**
 * entity
 */

public interface Subscriber {
    void notify(EventType eventType, Object context);
}
