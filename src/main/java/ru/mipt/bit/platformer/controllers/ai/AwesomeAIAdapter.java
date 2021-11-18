package ru.mipt.bit.platformer.controllers.ai;

import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.state.immovable.Obstacle;

import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.controllers.commands.Command;
import ru.mipt.bit.platformer.controllers.commands.MoveCommand;
import ru.mipt.bit.platformer.controllers.commands.NoopCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.physics.Level;

import java.util.List;
import java.util.stream.Collectors;

public class AwesomeAIAdapter implements Controller {
    private final AI ai;
    private final Level level;

    public AwesomeAIAdapter(AI ai, Level level) {
        this.ai = ai;
        this.level = level;
    }

    private static Orientation directionToOrientation(Direction direction) {
        switch (direction) {
            case RIGHT: return Orientation.E;
            case UP: return Orientation.N;
            case LEFT: return Orientation.W;
            case DOWN: return Orientation.S;
            default: return null;
        }
    }

    private static Player tankToPlayer(Tank tank) {
        return Player.builder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(directionToOrientation(tank.getDirection()))
                .build();
    }

    private static Bot tankToBot(Tank tank) {
        return Bot.builder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(directionToOrientation(tank.getDirection()))
                .build();
    }

    private static Obstacle obstacleToObstacle(ru.mipt.bit.platformer.model.Obstacle obstacle) {
        return new Obstacle(obstacle.getCoordinates().x, obstacle.getCoordinates().y);
    }

    private GameState buildGameState() {
        var player = tankToPlayer(level.getPlayer());
        var bots = level.getBots().stream()
                .map(AwesomeAIAdapter::tankToBot)
                .collect(Collectors.toList());
        var obstacles = level.getObstacles().stream()
                .map(AwesomeAIAdapter::obstacleToObstacle)
                .collect(Collectors.toList());
        return GameState.builder()
                .obstacles(obstacles)
                .bots(bots)
                .player(player)
                .levelWidth(level.getWidth())
                .levelHeight(level.getHeight())
                .build();
    }

    private static Direction directionFromAction(Action action) {
        switch (action) {
            case MoveEast: return Direction.RIGHT;
            case MoveNorth: return Direction.UP;
            case MoveWest: return Direction.LEFT;
            case MoveSouth: return Direction.DOWN;
            default: return null;
        }
    }

    private static Command commandFromRecommendation(Recommendation recommendation) {
        Tank tank = (Tank) recommendation.getActor().getSource();
        Direction direction = directionFromAction(recommendation.getAction());
        if (direction == null) {
            return NoopCommand.INSTANCE;
        }
        return new MoveCommand(tank, direction);
    }

    @Override
    public List<Command> getCommands() {
        var gameState = buildGameState();
        return ai.recommend(gameState).stream()
                .map(AwesomeAIAdapter::commandFromRecommendation)
                .collect(Collectors.toList());
    }
}
