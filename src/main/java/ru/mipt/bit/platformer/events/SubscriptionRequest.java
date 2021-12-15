package ru.mipt.bit.platformer.events;

public class SubscriptionRequest {
    private final EventType eventType;
    private final Subscriber subscriber;

    public SubscriptionRequest(EventType eventType, Subscriber subscriber) {
        this.eventType = eventType;
        this.subscriber = subscriber;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
}
