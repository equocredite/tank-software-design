package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.controllers.CommandExecutor;
import ru.mipt.bit.platformer.controllers.ai.RandomAIController;
import ru.mipt.bit.platformer.controllers.PlayerKeyboardController;
import ru.mipt.bit.platformer.graphics.LevelRenderer;
import ru.mipt.bit.platformer.graphics.ObstacleGraphics;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.physics.Level;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.generators.*;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private final LevelGenerator levelGenerator;

    private CommandExecutor commandExecutor;
    private Level level;
    private LevelRenderer levelRenderer;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        var gameDesktopLauncher = new GameDesktopLauncher(new LevelFileLoader(8, 10, "/gameObjectMap"));
        new Lwjgl3Application(gameDesktopLauncher, config);
    }

    public GameDesktopLauncher(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
    }

    @Override
    public void create() {
        TiledMap map = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer tileLayer = getSingleLayer(map);

        try {
            level = levelGenerator.generateLevel();
        } catch (MapGenerationException e) {
            e.printStackTrace();
        }

        var playerController = new PlayerKeyboardController(level.getPlayer());
        var botController = new RandomAIController(level.getBots());
        commandExecutor = new CommandExecutor(List.of(
                playerController, botController
        ));

        var tileMovement = new TileMovement(tileLayer, Interpolation.smooth);

        List<TankGraphics> tankGraphics = new ArrayList<>();
        tankGraphics.add(new TankGraphics(new Texture("images/tank_blue.png"), level.getPlayer(), tileMovement));
        for (var bot : level.getBots()) {
            tankGraphics.add(new TankGraphics(new Texture("images/tank_blue.png"), bot, tileMovement));
        }
        ObstacleGraphics obstacleGraphics = new ObstacleGraphics(new Texture("images/greenTree.png"),
                level.getObstacles(), tileLayer);
        levelRenderer = new LevelRenderer(map, tankGraphics, obstacleGraphics);
    }

    @Override
    public void render() {
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        commandExecutor.executeCommands();
        level.tick(deltaTime);
        levelRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        levelRenderer.dispose();
    }
}
