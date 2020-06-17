package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GameDynamoDBRepository implements GameRepository {
    @Override
    public Game save(String userId, Game game) {
        return game;
    }

    @Override
    public Game get(String userId, String gameId) {
        return null;
    }

    @Override
    public Game update(String userId, Game game) {
        return game;
    }
}