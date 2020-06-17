package ar.com.lucianoclusa.minesweeper.domain.repository;

import ar.com.lucianoclusa.minesweeper.domain.Game;

public interface GameRepository {
    Game save(String userId, Game game);
    Game get(String userId, String gameId);
    Game update(String userId, Game game);
}
