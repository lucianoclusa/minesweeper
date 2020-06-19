package ar.com.lucianoclusa.minesweeper.domain.service;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import ar.com.lucianoclusa.minesweeper.application.model.MoveRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String userId, Game game) {
        return gameRepository.save(userId, game);
    }

    public Game makeMove(MoveRequest moveRequest, String userId, String gameId) {
        Game game = gameRepository.get(userId, gameId);
        switch (moveRequest.getMovementType()) {
            case OPEN:
                game.openSlot(moveRequest.getRow(), moveRequest.getColumn());
                break;
            case QUESTION:
                game.questionSlot(moveRequest.getRow(), moveRequest.getColumn());
                break;
            case FLAG:
                game.flagSlot(moveRequest.getRow(), moveRequest.getColumn());
                break;
            case CLEAN:
                game.cleanSlot(moveRequest.getRow(), moveRequest.getColumn());
                break;
        }
        return gameRepository.update(userId, game);
    }

    public Game getGame(String userId, String gameId) {
        return gameRepository.get(userId, gameId);
    }
}