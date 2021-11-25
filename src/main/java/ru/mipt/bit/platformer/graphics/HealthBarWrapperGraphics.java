package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.LivingGameObject;

public class HealthBarWrapperGraphics implements LivingGameObjectGraphics {
    private final LivingGameObjectGraphics wrappee;
    private final ToggleListener toggleListener;

    public HealthBarWrapperGraphics(LivingGameObjectGraphics wrappee, ToggleListener toggleListener) {
        this.wrappee = wrappee;
        this.toggleListener = toggleListener;
    }

    @Override
    public void draw(Batch batch) {
        wrappee.draw(batch);
        if (toggleListener.isEnabled()) {
            int health = getDrawnObject().getHealth();
            // ...
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
