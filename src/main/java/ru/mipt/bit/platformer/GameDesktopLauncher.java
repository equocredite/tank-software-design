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
import ru.mipt.bit.platformer.ai.RandomAI;
import ru.mipt.bit.platformer.controllers.BotController;
import ru.mipt.bit.platformer.controllers.PlayerKeyboardController;
import ru.mipt.bit.platformer.graphics.LevelRenderer;
import ru.mipt.bit.platformer.graphics.ObstacleGraphics;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.CollisionManager;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.loaders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private final GameObjectMapLoader gameObjectMapLoader;

    private LevelRenderer levelRenderer;
    private PlayerKeyboardController playerController;
    private BotController botController;

    private CollisionManager collisionManager;

    private Tank player;
    private List<Tank> bots;
    private List<Obstacle> obstacles;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        //
        var gameDesktopLauncher = new GameDesktopLauncher(new GameObjectMapFileLoader(8, 10, "/gameObjectMap"));
        new Lwjgl3Application(gameDesktopLauncher, config);
    }

    public GameDesktopLauncher(GameObjectMapLoader gameObjectMapLoader) {
        this.gameObjectMapLoader = gameObjectMapLoader;
    }

    private void createPlayer() {
        player = new Tank(gameObjectMapLoader.getPlayerPosition(),0.4f, collisionManager);
    }

    private void createBots() {
        bots = gameObjectMapLoader.getBotPositions().stream()
                .map(position -> new Tank(position,0.4f, collisionManager))
                .collect(Collectors.toList());
    }

    private void createObstacles() {
        obstacles = gameObjectMapLoader.getTreePositions().stream()
                .map(position -> new Obstacle(position, 0))
                .collect(Collectors.toList());
    }

    @Override
    public void create() {
        TiledMap map = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer tileLayer = getSingleLayer(map);

        try {
            gameObjectMapLoader.loadMap();
        } catch (MapLoadingException e) {
            e.printStackTrace();
        }

        collisionManager = new CollisionManager(tileLayer.getHeight(), tileLayer.getWidth());

        createPlayer();
        createBots();
        createObstacles();

        collisionManager.setObstacles(obstacles);

        playerController = new PlayerKeyboardController(player);
        botController = new BotController(new RandomAI(bots), bots);

        var tileMovement = new TileMovement(tileLayer, Interpolation.smooth);

        List<TankGraphics> tankGraphics = new ArrayList<>();
        tankGraphics.add(new TankGraphics(new Texture("images/tank_blue.png"), player, tileMovement));
        for (var bot : bots) {
            tankGraphics.add(new TankGraphics(new Texture("images/tank_blue.png"), bot, tileMovement));
        }
        ObstacleGraphics obstacleGraphics = new ObstacleGraphics(new Texture("images/greenTree.png"),
                obstacles, tileLayer);
        levelRenderer = new LevelRenderer(map, tankGraphics, obstacleGraphics);

        List<Tank> allTanks = new ArrayList<>();
        allTanks.add(player);
        allTanks.addAll(bots);
        collisionManager.setTanks(allTanks);
    }

    @Override
    public void render() {
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        playerController.process(deltaTime);
        botController.process(deltaTime);
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
