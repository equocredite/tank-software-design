package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.CollisionManager;
import ru.mipt.bit.platformer.physics.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomLevelGenerator implements LevelGenerator {
    private final int height;
    private final int width;
    private final int nObstacles;
    private final int nBots;

    private final Set<GridPoint2> occupiedPositions = new HashSet<>();
    private final CollisionManager collisionManager;

    public RandomLevelGenerator(int height, int width, int nObstacles, int nBots) {
        if (height * width < nObstacles + nBots + 1) {
            throw new IllegalArgumentException("too many objects");
        }
        this.height = height;
        this.width = width;
        this.nObstacles = nObstacles;
        this.nBots = nBots;
        this.collisionManager = new CollisionManager(height, width);
    }

    @Override
    public Level generateLevel() {
        var player = generateTank();
        collisionManager.addTank(player);

        var bots = generateNObjects(this::generateTank, nBots);
        collisionManager.addTanks(bots);

        var obstacles = generateNObjects(this::generateObstacle, nObstacles);
        collisionManager.addObstacles(obstacles);

        return new Level(player, bots, obstacles, height, width);
    }

    private <T> List<T> generateNObjects(Supplier<? extends T> supplier, int n) {
        return Stream.generate(supplier)
                .limit(n)
                .collect(Collectors.toList());
    }

    private Tank generateTank() {
        return new Tank(generateRandomEmptyPosition(),0.4f, collisionManager);
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
