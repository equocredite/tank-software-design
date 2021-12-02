package ru.mipt.bit.platformer.generators;

import ru.mipt.bit.platformer.events.SubscriptionRequest;
import ru.mipt.bit.platformer.physics.Level;

import java.util.Collections;
import java.util.List;

/**
 * entity
 */

public interface LevelGenerator {
    Level generateLevel(List<SubscriptionRequest> initialSubscriptionRequests) throws MapGenerationException;

    default Level generateLevel() throws MapGenerationException {
        return generateLevel(Collections.emptyList());
    }
}
