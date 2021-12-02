package ru.mipt.bit.platformer.events;

import java.util.Collection;

/**
 * entity
 */

public interface Publisher {
    void subscribe(Subscriber subscriber, EventType eventType);

    default void subscribeAll(Collection<SubscriptionRequest> requests) {
        for (var request : requests) {
            subscribe(request.getSubscriber(), request.getEventType());
        }
    }

    void unsubscribe(Subscriber subscriber, EventType eventType);
}
