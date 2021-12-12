package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class BulletGraphics implements Graphics {
    private final TextureRegion region;
    private final Rectangle rectangle;
    private final Bullet bullet;
    private final TileMovement tileMovement;

    public BulletGraphics(Texture texture, Bullet bullet, TileMovement tileMovement, TiledMapTileLayer tileLayer) {
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        this.bullet = bullet;
        this.tileMovement = tileMovement;

        moveRectangleAtTileCenter(tileLayer, rectangle, bullet.getCoordinates());
    }

    public void move() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, bullet.getCoordinates(),
                bullet.getDestinationCoordinates(), bullet.getMovementProgress());
    }

    @Override
    public void dispose() {
        region.getTexture().dispose();
    }

    public Bullet getDrawnObject() {
        return bullet;
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, 0f);
    }

    @Override
    public void drawShape(ShapeRenderer shapeRenderer) {
    }
}
