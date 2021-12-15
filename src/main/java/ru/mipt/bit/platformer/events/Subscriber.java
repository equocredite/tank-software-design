package ru.mipt.bit.platformer.events;

public interface Subscriber {
    void notify(EventType eventType, Object context);
}
