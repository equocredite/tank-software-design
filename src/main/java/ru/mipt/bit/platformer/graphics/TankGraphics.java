package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

/**
 * use-case
 */

public class TankGraphics implements LivingGameObjectGraphics {
    private final TextureRegion region;
    private final Rectangle rectangle;
    private final Tank tank;
    private final TileMovement tileMovement;

    public TankGraphics(Texture texture, Tank tank, TileMovement tileMovement) {
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        this.tank = tank;
        this.tileMovement = tileMovement;
    }

    @Override
    public void dispose() {
        region.getTexture().dispose();
    }

    @Override
    public void move() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, tank.getCoordinates(),
                tank.getDestinationCoordinates(), tank.getMovementProgress());
    }

    @Override
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, tank.getRotation());
    }

    @Override
    public Tank getDrawnObject() {
        return tank;
    }
}
