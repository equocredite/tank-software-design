package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.CollisionManager;
import ru.mipt.bit.platformer.physics.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LevelFileLoader implements LevelGenerator {
    private static final char PLAYER_SYMBOL = 'P';
    private static final char BOT_SYMBOL = 'B';
    private static final char OBSTACLE_SYMBOL = 'T';

    private final int height;
    private final int width;
    private final String fileName;
    private final CollisionManager collisionManager;

    public LevelFileLoader(int height, int width, String fileName) {
        this.height = height;
        this.width = width;
        this.fileName = fileName;
        this.collisionManager = new CollisionManager(height, width);
    }

    @Override
    public Level generateLevel() throws MapGenerationException {
        Tank player = null;
        List<Tank> bots = new ArrayList<>();
        List<Obstacle> obstacles = new ArrayList<>();

        List<String> lines;
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new MapGenerationException("error reading map file", e);
        }

        if (height != lines.size()) {
            throw new MapGenerationException("incorrect map height");
        }

        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);

            if (width != line.length()) {
                throw new MapGenerationException("incorrect map width");
            }

            final int y = lines.size() - i - 1;

            for (int x = 0; x < line.length(); ++x) {
                if (line.charAt(x) == PLAYER_SYMBOL) {
                    if (player != null) {
                        throw new MapGenerationException("multiple positions for player");
                    }
                    player = generateTank(new GridPoint2(x, y));
                    collisionManager.addTank(player);
                }
                if (line.charAt(x) == BOT_SYMBOL) {
                    var bot = generateTank(new GridPoint2(x, y));
                    collisionManager.addTank(bot);
                    bots.add(bot);
                }
                if (line.charAt(x) == OBSTACLE_SYMBOL) {
                    var obstacle = new Obstacle(new GridPoint2(x, y));
                    collisionManager.addObstacle(obstacle);
                    obstacles.add(obstacle);
                }
            }
        }

        return new Level(player, bots, obstacles, height, width);
    }

    private Tank generateTank(GridPoint2 position) {
        return new Tank(position, 0.4f, collisionManager);
    }
}
