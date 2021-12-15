package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.LivingGameObject;

public interface LivingGameObjectGraphics extends Graphics {
    LivingGameObject getDrawnObject();
}
