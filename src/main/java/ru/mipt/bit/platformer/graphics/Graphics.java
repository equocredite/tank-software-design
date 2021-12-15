package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public interface Graphics extends Disposable {
    void move();

    void draw(Batch batch);

    void drawShape(ShapeRenderer shapeRenderer);

    Object getDrawnObject();
}
