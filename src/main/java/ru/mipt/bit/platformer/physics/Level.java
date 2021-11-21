package ru.mipt.bit.platformer.physics;

import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class Level {
    private final Tank player;
    private final List<Tank> bots;
    private final List<Obstacle> obstacles;

    private final int height;
    private final int width;

    public Level(Tank player, List<Tank> bots, List<Obstacle> obstacles, int height, int width) {
        this.player = player;
        this.bots = bots;
        this.obstacles = obstacles;
        this.height = height;
        this.width = width;
    }

    public void tick(float deltaTime) {
        player.makeProgress(deltaTime);
        for (var bot : bots) {
            bot.makeProgress(deltaTime);
        }
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
