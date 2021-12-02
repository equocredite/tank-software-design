package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.LivingGameObject;

/**
 * use-case
 */

public interface LivingGameObjectGraphics extends Disposable {
    void draw(Batch batch);

    void move();

    LivingGameObject getDrawnObject();
}
