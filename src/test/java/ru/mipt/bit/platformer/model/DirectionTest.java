package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DirectionTest {
    @Test
    public void testCalcDestination() {
        float eps = 1e-6f;
        GridPoint2 from = new GridPoint2(5, 6);
        var dests = List.of(
                new GridPoint2(6, 6),
                new GridPoint2(5, 7),
                new GridPoint2(4, 6),
                new GridPoint2(5, 5));
        assertEquals(dests.get(0), Direction.RIGHT.calcDestinationCoordinatesFrom(from));
        assertEquals(dests.get(1), Direction.UP.calcDestinationCoordinatesFrom(from));
        assertEquals(dests.get(2), Direction.LEFT.calcDestinationCoordinatesFrom(from));
        assertEquals(dests.get(3), Direction.DOWN.calcDestinationCoordinatesFrom(from));
    }
}
