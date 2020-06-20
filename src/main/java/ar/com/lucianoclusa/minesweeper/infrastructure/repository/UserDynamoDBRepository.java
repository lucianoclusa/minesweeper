package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import ar.com.lucianoclusa.minesweeper.infrastructure.entity.UserEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
interface UserDynamoDBRepository extends CrudRepository<UserEntity, String> {
    UserEntity findFirstByUserName(String userNAme);
}
