package ru.mipt.bit.platformer.physics;

import ru.mipt.bit.platformer.events.EventType;
import ru.mipt.bit.platformer.events.Publisher;
import ru.mipt.bit.platformer.events.Subscriber;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.*;

/**
 * entity
 */

public class Level implements Publisher {
    private Tank player;
    private final List<Tank> bots = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Bullet> bulletsToRemove = new ArrayList<>();

    private final int height;
    private final int width;

    private final Map<EventType, Set<Subscriber>> subscribersByEventType = new HashMap<>();
    private final CollisionManager collisionManager;

    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        this.collisionManager = new CollisionManager(this);
    }

    public void tick(float deltaTime) {
        if (player != null) {
            player.tick(deltaTime);
        }
        for (var bot : bots) {
            bot.tick(deltaTime);
        }
        for (var bullet : bullets) {
            bullet.tick(deltaTime);
        }
        removeBullets();
    }

    public Tank getPlayer() {
        return player;
    }

    public void setPlayer(Tank player) {
        this.player = player;
        notifySubscribers(EventType.ADD_TANK, player);
    }

    public List<Tank> getBots() {
        return bots;
    }

    public void addBot(Tank bot) {
        bots.add(bot);
        notifySubscribers(EventType.ADD_TANK, bot);
    }

    public void addBots(Collection<Tank> bots) {
        for (var bot : bots) {
            addBot(bot);
        }
    }

    public List<Tank> getTanks() {
        if (player == null) {
            return bots;
        }
        List<Tank> tanks = new ArrayList<>(bots);
        tanks.add(player);
        return tanks;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        notifySubscribers(EventType.ADD_OBSTACLE, obstacle);
    }

    public void addObstacles(Collection<Obstacle> obstacles) {
        for (var obstacle : obstacles) {
            addObstacle(obstacle);
        }
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void removeTank(Tank tank) {
        if (player == tank) {
            player = null;
        } else {
            bots.remove(tank);
        }
        notifySubscribers(EventType.REMOVE_TANK, tank);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        notifySubscribers(EventType.ADD_BULLET, bullet);
    }

    public void removeBullet(Bullet bullet) {
        bulletsToRemove.add(bullet);
        notifySubscribers(EventType.REMOVE_BULLET, bullet);
    }

    private void removeBullets() {
        bullets.removeAll(bulletsToRemove);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    @Override
    public void subscribe(Subscriber subscriber, EventType eventType) {
        subscribersByEventType.computeIfAbsent(eventType, k -> new HashSet<>()).add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber, EventType eventType) {
        subscribersByEventType.get(eventType).remove(subscriber);
    }

    private void notifySubscribers(EventType eventType, Object context) {
        var subscribers = subscribersByEventType.get(eventType);
        if (subscribers != null) {
            for (var subscriber : subscribers) {
                subscriber.notify(eventType, context);
            }
        }
    }
}
