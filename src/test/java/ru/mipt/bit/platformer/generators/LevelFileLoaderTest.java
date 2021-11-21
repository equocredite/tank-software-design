package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Obstacle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class LevelFileLoaderTest {
    @Test
    public void testGenerate() throws MapGenerationException {
        var generator = new LevelFileLoader(8, 10, "/gameObjectMap");
        var level = generator.generateLevel();

        assertEquals(new GridPoint2(5, 1), level.getPlayer().getCoordinates());

        List<GridPoint2> actualTreePositions = level.getObstacles().stream()
                .map(Obstacle::getCoordinates)
                .collect(Collectors.toList());
        assertEquals(7, actualTreePositions.size());

        List<GridPoint2> expectedTreePositions = List.of(
                new GridPoint2(0, 2),
                new GridPoint2(3, 5),
                new GridPoint2(6, 2),
                new GridPoint2(6, 5),
                new GridPoint2(8, 4),
                new GridPoint2(9, 2),
                new GridPoint2(9, 3)
        );
        actualTreePositions.sort(Comparator.comparing((GridPoint2 p) -> p.x).thenComparing((GridPoint2 p) -> p.y));

        assertEquals(expectedTreePositions, actualTreePositions);
    }
}
