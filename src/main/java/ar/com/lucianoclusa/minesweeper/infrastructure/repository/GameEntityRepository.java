package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import ar.com.lucianoclusa.minesweeper.infrastructure.entity.GameEntity;
import org.springframework.stereotype.Repository;

@Repository
class GameEntityRepository implements GameRepository {

    private final GameDynamoDBRepository gameEntityDynamoDBREpository;

    public GameEntityRepository(GameDynamoDBRepository gameEntityDynamoDBREpository) {
        this.gameEntityDynamoDBREpository = gameEntityDynamoDBREpository;
    }

    @Override
    public Game save(Game game) {
        GameEntity gameEntity = new GameEntity(game);
        GameEntity savedGameEntity = gameEntityDynamoDBREpository.save(gameEntity);
        return savedGameEntity.toGame();
    }

    @Override
    public Game get(String gameId) {
        return gameEntityDynamoDBREpository.findById(gameId).map(GameEntity::toGame).orElse(null);
    }

    @Override
    public Game update(Game game) {
        return this.save(game);
    }
}