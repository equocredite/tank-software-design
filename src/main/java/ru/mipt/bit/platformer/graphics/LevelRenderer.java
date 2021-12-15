package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LevelRenderer implements Subscriber, Disposable {
    private final Batch batch;
    private final TiledMap map;
    private final MapRenderer mapRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer tileLayer;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final Texture tankTexture;
    private final Texture bulletTexture;

    private final List<Graphics> graphics = new ArrayList<>();

    private final ObstacleGraphics obstacleGraphics;

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

        obstacleGraphics = new ObstacleGraphics(obstacleTexture, tileLayer);
        graphics.add(obstacleGraphics);

        this.toggleListener = toggleListener;
    }

    public void render() {
        clearScreen();
        graphics.forEach(Graphics::move);
        mapRenderer.render();
        batch.begin();
        graphics.forEach(graphics -> graphics.draw(batch));
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        graphics.forEach(graphics -> graphics.drawShape(shapeRenderer));
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        graphics.forEach(Graphics::dispose);
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
        var region = new TextureRegion(tankTexture);
        var rectangle = createBoundingRectangle(region);
        graphics.add(new HealthBarWrapperGraphics(new TankGraphics(region, rectangle, tank, tileMovement),
                toggleListener, shapeRenderer, rectangle, tankTexture.getWidth() * 0.25f,
                tankTexture.getHeight() * 0.75f));
    }

    private void removeTank(Tank tank) {
        graphics.removeIf(graphics -> graphics.getDrawnObject() == tank);
    }

    private void addObstacle(Obstacle obstacle) {
        obstacleGraphics.addObstacle(obstacle);
    }

    private void addBullet(Bullet bullet) {
        graphics.add(new BulletGraphics(bulletTexture, bullet, tileMovement, tileLayer));
    }

    private void removeBullet(Bullet bullet) {
        graphics.removeIf(graphics -> graphics.getDrawnObject() == bullet);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
