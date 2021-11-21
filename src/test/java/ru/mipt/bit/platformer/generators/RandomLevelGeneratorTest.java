package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mipt.bit.platformer.model.Obstacle;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RandomLevelGeneratorTest {
    @ParameterizedTest
    @CsvSource({
            "10, 8, 10, 1",
            "8, 5, 3, 1",
            "15, 10, 0, 1",
            "15, 10, 20, 2"
    })
    public void testGenerate(int height, int width, int nTrees, int nBots) {
        Function<GridPoint2, Boolean> checkPositionCorrectness = (GridPoint2 point) -> {
            return 0 <= point.x && point.x < width && 0 <= point.y && point.y < height;
        };

        var generator = new RandomLevelGenerator(height, width, nTrees, nBots);
        var level = generator.generateLevel();

        assertTrue(checkPositionCorrectness.apply(level.getPlayer().getCoordinates()));

        var obstaclePositions = level.getObstacles().stream()
                .map(Obstacle::getCoordinates)
                .collect(Collectors.toList());
        assertEquals(nTrees, obstaclePositions.size());
        assertTrue(obstaclePositions.stream().allMatch(checkPositionCorrectness::apply));
    }
}
