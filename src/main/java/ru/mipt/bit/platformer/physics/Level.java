package ru.mipt.bit.platformer.physics;

import ru.mipt.bit.platformer.loaders.GameObjectMapLoader;
import ru.mipt.bit.platformer.loaders.MapLoadingException;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;
import java.util.stream.Collectors;

public class Level {
    private Tank player;
    private List<Tank> bots;
    private List<Obstacle> obstacles;

    private final int height;
    private final int width;

    private final GameObjectMapLoader gameObjectMapLoader;
    private final CollisionManager collisionManager;

    public Level(GameObjectMapLoader gameObjectMapLoader, int height, int width) {
        this.height = height;
        this.width = width;
        this.gameObjectMapLoader = gameObjectMapLoader;
        this.collisionManager = new CollisionManager(height, width);

        try {
            gameObjectMapLoader.loadMap();
        } catch (MapLoadingException e) {
            e.printStackTrace();
        }

        createGameObjects();
    }

    public void tick(float deltaTime) {
        player.makeProgress(deltaTime);
        for (var bot : bots) {
            bot.makeProgress(deltaTime);
        }
    }

    private void createGameObjects() {
        createPlayer();
        createBots();
        createObstacles();
    }

    private void createPlayer() {
        player = new Tank(gameObjectMapLoader.getPlayerPosition(),0.4f, collisionManager);
        collisionManager.addTank(player);
    }

    private void createBots() {
        bots = gameObjectMapLoader.getBotPositions().stream()
                .map(position -> new Tank(position,0.4f, collisionManager))
                .collect(Collectors.toList());
        collisionManager.addTanks(bots);
    }

    private void createObstacles() {
        obstacles = gameObjectMapLoader.getTreePositions().stream()
                .map(position -> new Obstacle(position, 0))
                .collect(Collectors.toList());
        collisionManager.addObstacles(obstacles);
    }

    public Tank getPlayer() {
        return player;
    }

    public List<Tank> getBots() {
        return bots;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
