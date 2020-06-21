package ar.com.lucianoclusa.minesweeper.domain.service;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.User;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import ar.com.lucianoclusa.minesweeper.application.model.MoveRequest;
import ar.com.lucianoclusa.minesweeper.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private GameRepository gameRepository;
    private UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game createGame(String userName, Game game) {
        User user = userRepository.getByUserName(userName);
        if (user == null) {
            user = userRepository.save(new User(userName));
        }

        Game newGame = gameRepository.save(game);

        addGameToUser(user, newGame);

        return newGame;
    }

    private void addGameToUser(User user, Game newGame) {
        List<String> userGames = user.getUserGameIds();
        if (userGames == null) {
            userGames = new ArrayList<>();
        }
        userGames.add(newGame.getId());
        user.setUserGameIds(userGames);
        userRepository.update(user);
    }

    public Game makeMove(MoveRequest moveRequest, String gameId) {
        Game game = gameRepository.get(gameId);
        if (!gameBelongsToUser(gameId, moveRequest.getUserName())) {
            throw new UserNotValidForGameException();
        }
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
        return gameRepository.update(game);
    }

    private boolean gameBelongsToUser(String gameId, String userId) {
        User user = userRepository.getByUserName(userId);
        if (user!=null && user.getUserGameIds() != null) {
            return user.getUserGameIds().contains(gameId);
        }
        return false;
    }

    public Game getGame(String gameId) {
        return gameRepository.get(gameId);
    }
}