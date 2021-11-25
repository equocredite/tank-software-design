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
import ru.mipt.bit.platformer.events.EventType;
import ru.mipt.bit.platformer.events.SubscriptionRequest;
import ru.mipt.bit.platformer.graphics.LevelRenderer;
import ru.mipt.bit.platformer.graphics.ToggleListener;
import ru.mipt.bit.platformer.physics.Level;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.generators.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        var tileMovement = new TileMovement(tileLayer, Interpolation.smooth);

        var tankTexture = new Texture("images/tank_blue.png");
        var obstacleTexture = new Texture("images/greenTree.png");
        var bulletTexture = new Texture("images/bullet.png");

        var toggleListener = new ToggleListener();

        levelRenderer = new LevelRenderer(map, tileMovement, tileLayer, tankTexture, obstacleTexture, bulletTexture,
                toggleListener);

        var initialSubscriptionRequests = Arrays.stream(EventType.values())
                .map(eventType -> new SubscriptionRequest(eventType, levelRenderer))
                .collect(Collectors.toList());

        try {
            level = levelGenerator.generateLevel(initialSubscriptionRequests);
        } catch (MapGenerationException e) {
            e.printStackTrace();
        }

        var playerController = new PlayerKeyboardController(level.getPlayer(), toggleListener);
        var botController = new RandomAIController(level.getBots());
        commandExecutor = new CommandExecutor(List.of(
                playerController, botController
        ));
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
