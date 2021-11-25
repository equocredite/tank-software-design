package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Obstacle;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class ObstacleGraphics implements Disposable {
    private final TiledMapTileLayer tileLayer;
    private final TextureRegion region;
    private final List<Rectangle> rectangles = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    public ObstacleGraphics(Texture texture, TiledMapTileLayer tileLayer) {
        this.tileLayer = tileLayer;
        this.region = new TextureRegion(texture);
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        Rectangle rectangle = createBoundingRectangle(region);
        rectangles.add(rectangle);
        moveRectangleAtTileCenter(tileLayer, rectangle, obstacle.getCoordinates());
    }

    @Override
    public void dispose() {
        region.getTexture().dispose();
    }

    public void draw(Batch batch) {
        for (int i = 0; i < obstacles.size(); ++i) {
            drawTextureRegionUnscaled(batch, region, rectangles.get(i), obstacles.get(i).getRotation());
        }
    }
}
