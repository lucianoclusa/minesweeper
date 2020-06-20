package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import ar.com.lucianoclusa.minesweeper.infrastructure.entity.GameEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
interface GameDynamoDBRepository extends CrudRepository<GameEntity, String> {}
