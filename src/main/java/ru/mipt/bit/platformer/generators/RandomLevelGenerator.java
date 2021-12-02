package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.events.SubscriptionRequest;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * use-case
 */

public class RandomLevelGenerator implements LevelGenerator {
    private final int height;
    private final int width;
    private final int nObstacles;
    private final int nBots;

    private final Level level;

    private final Set<GridPoint2> occupiedPositions = new HashSet<>();
    public RandomLevelGenerator(int height, int width, int nObstacles, int nBots) {
        if (height * width < nObstacles + nBots + 1) {
            throw new IllegalArgumentException("too many objects");
        }
        this.height = height;
        this.width = width;
        this.nObstacles = nObstacles;
        this.nBots = nBots;
        this.level = new Level(height, width);
    }

    @Override
    public Level generateLevel(List<SubscriptionRequest> initialSubscriptionRequests) {
        level.subscribeAll(initialSubscriptionRequests);
        level.setPlayer(generateTank());
        level.addBots(generateNObjects(this::generateTank, nBots));
        level.addObstacles(generateNObjects(this::generateObstacle, nObstacles));
        return level;
    }

    private <T> List<T> generateNObjects(Supplier<? extends T> supplier, int n) {
        return Stream.generate(supplier)
                .limit(n)
                .collect(Collectors.toList());
    }

    private Tank generateTank() {
        return new Tank(generateRandomEmptyPosition(),0.4f, level);
    }

    private Obstacle generateObstacle() {
        return new Obstacle(generateRandomEmptyPosition());
    }

    private boolean isOccupied(GridPoint2 point) {
        return occupiedPositions.contains(point);
    }

    private GridPoint2 generateRandomEmptyPosition() {
        var point = new GridPoint2();
        do {
            point.x = ThreadLocalRandom.current().nextInt(width);
            point.y = ThreadLocalRandom.current().nextInt(height);
        } while (isOccupied(point));
        occupiedPositions.add(point);
        return point;
    }
}
