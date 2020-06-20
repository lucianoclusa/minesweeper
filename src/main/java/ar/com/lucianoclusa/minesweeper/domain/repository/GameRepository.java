package ar.com.lucianoclusa.minesweeper.domain.repository;

import ar.com.lucianoclusa.minesweeper.domain.Game;

public interface GameRepository {
    Game save(Game game);
    Game get(String gameId);
    Game update(Game game);
}
