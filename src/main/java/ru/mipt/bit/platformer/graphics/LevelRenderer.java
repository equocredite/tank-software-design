package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.events.EventType;
import ru.mipt.bit.platformer.events.Subscriber;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LevelRenderer implements Subscriber, Disposable {
    private final Batch batch;
    private final TiledMap map;
    private final MapRenderer mapRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer tileLayer;

    private final Texture tankTexture;
    private final Texture bulletTexture;

    private final List<TankGraphics> tankGraphics = new ArrayList<>();
    private final ObstacleGraphics obstacleGraphics;
    private final List<BulletGraphics> bulletGraphics = new ArrayList<>();

    private final ToggleListener toggleListener;

    public LevelRenderer(TiledMap map, TileMovement tileMovement, TiledMapTileLayer tileLayer, Texture tankTexture,
                         Texture obstacleTexture, Texture bulletTexture, ToggleListener toggleListener) {
        this.batch = new SpriteBatch();
        this.map = map;
        this.mapRenderer = createSingleLayerMapRenderer(map, batch);
        this.tileMovement = tileMovement;
        this.tileLayer = tileLayer;

        this.tankTexture = tankTexture;
        this.bulletTexture = bulletTexture;

        this.obstacleGraphics = new ObstacleGraphics(obstacleTexture, tileLayer);

        this.toggleListener = toggleListener;
    }

    public void render() {
        clearScreen();
        for (var graphics : tankGraphics) {
            graphics.move();
        }
        for (var graphics : bulletGraphics) {
            graphics.move();
        }
        mapRenderer.render();
        batch.begin();
        for (var graphics : tankGraphics) {
            graphics.draw(batch);
        }
        for (var graphics : bulletGraphics) {
            graphics.draw(batch);
        }
        obstacleGraphics.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        for (var graphics : tankGraphics) {
            graphics.dispose();
        }
        for (var graphics : bulletGraphics) {
            graphics.dispose();
        }
        obstacleGraphics.dispose();
    }

    @Override
    public void notify(EventType eventType, Object context) {
        switch (eventType) {
            case ADD_TANK:
                addTank((Tank) context);
                break;
            case REMOVE_TANK:
                removeTank((Tank) context);
                break;
            case ADD_OBSTACLE:
                addObstacle((Obstacle) context);
                break;
            case ADD_BULLET:
                addBullet((Bullet) context);
                break;
            case REMOVE_BULLET:
                removeBullet((Bullet) context);
        }
    }

    private void addTank(Tank tank) {
        tankGraphics.add(new TankGraphics(tankTexture, tank, tileMovement));
    }

    private void removeTank(Tank tank) {
        tankGraphics.removeIf(graphics -> graphics.getDrawnObject() == tank);
    }

    private void addObstacle(Obstacle obstacle) {
        obstacleGraphics.addObstacle(obstacle);
    }

    private void addBullet(Bullet bullet) {
        bulletGraphics.add(new BulletGraphics(bulletTexture, bullet, tileMovement, tileLayer));
    }

    private void removeBullet(Bullet bullet) {
        bulletGraphics.removeIf(graphics -> graphics.getDrawnObject() == bullet);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
