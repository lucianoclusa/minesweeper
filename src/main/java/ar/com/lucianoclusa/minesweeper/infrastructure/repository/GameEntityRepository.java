package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import ar.com.lucianoclusa.minesweeper.infrastructure.entity.GameEntity;
import org.springframework.stereotype.Repository;

@Repository
class GameEntityRepository implements GameRepository {

    private final GameEntityDynamoDBRepository gameEntityDynamoDBREpository;

    public GameEntityRepository(GameEntityDynamoDBRepository gameEntityDynamoDBREpository) {
        this.gameEntityDynamoDBREpository = gameEntityDynamoDBREpository;
    }

    @Override
    public Game save(String userId, Game game) {
        GameEntity gameEntity = new GameEntity(game, userId);
        GameEntity savedGameEntity = gameEntityDynamoDBREpository.save(gameEntity);
        return savedGameEntity.toGame();
    }

    @Override
    public Game get(String userId, String gameId) {
        return gameEntityDynamoDBREpository.findById(gameId).map(GameEntity::toGame).orElse(null);
    }

    @Override
    public Game update(String userId, Game game) {
        return this.save(userId, game);
    }
}