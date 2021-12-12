package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.LivingGameObject;

public class HealthBarWrapperGraphics implements LivingGameObjectGraphics {
    private final LivingGameObjectGraphics wrappee;
    private final ToggleListener toggleListener;
    private final ShapeRenderer shapeRenderer;
    private final Rectangle rectangle;
    private final float healthBarWidth;
    private final float baseHealthBarHeight;

    public HealthBarWrapperGraphics(LivingGameObjectGraphics wrappee, ToggleListener toggleListener,
                                    ShapeRenderer shapeRenderer, Rectangle rectangle, float healthBarWidth,
                                    float baseHealthBarHeight) {
        this.wrappee = wrappee;
        this.toggleListener = toggleListener;
        this.shapeRenderer = shapeRenderer;
        this.rectangle = rectangle;
        this.healthBarWidth = healthBarWidth;
        this.baseHealthBarHeight = baseHealthBarHeight;
    }

    @Override
    public void draw(Batch batch) {
        wrappee.draw(batch);
    }

    @Override
    public void drawShape(ShapeRenderer shapeRenderer) {
        if (toggleListener.isEnabled()) {
            shapeRenderer.setColor(Color.RED);
            var healthFractionRemaining = 1.0f * getDrawnObject().getHealth() / getDrawnObject().getMaxHealth();
            shapeRenderer.rect(rectangle.x + healthBarWidth, rectangle.y + baseHealthBarHeight,
                    50f * healthFractionRemaining, 10);
        }
    }

    @Override
    public void move() {
        wrappee.move();
    }

    @Override
    public LivingGameObject getDrawnObject() {
        return wrappee.getDrawnObject();
    }

    @Override
    public void dispose() {
        wrappee.dispose();
    }
}
