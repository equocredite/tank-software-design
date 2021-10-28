package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.physics.CollisionManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Tank player;
    private static final List<Obstacle> OBSTACLES = List.of(new Obstacle(new GridPoint2(6, 6)));
    private static final CollisionManager COLLISION_MANAGER = new CollisionManager(10, 10);

    static {
        COLLISION_MANAGER.setObstacles(OBSTACLES);
    }

    @BeforeEach
    private void setUp() {
        player = new Tank(new GridPoint2(5, 6),0.4f, COLLISION_MANAGER, Direction.UP);
    }

    @Test
    public void testValidMovement() {
        List<GridPoint2> path = List.of(
                new GridPoint2(5, 7),
                new GridPoint2(4, 7),
                new GridPoint2(4, 6),
                new GridPoint2(5, 6));
        List<Direction> directions = List.of(Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT);
        for (int i = 0; i < path.size(); ++i) {
            var destination = path.get(i);
            var direction = directions.get(i);
            player.tryRotateAndStartMovement(direction);
            player.makeProgress(1f);
            assertEquals(player.getCoordinates(), path.get(i));
        }
    }

    @Test
    public void testCollision() {
        player.tryRotateAndStartMovement(Direction.RIGHT);
        assertFalse(player.isMoving());
        assertEquals(0f, player.getRotation());
    }
}
