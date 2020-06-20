package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import ar.com.lucianoclusa.minesweeper.domain.User;
import ar.com.lucianoclusa.minesweeper.infrastructure.entity.UserEntity;
import ar.com.lucianoclusa.minesweeper.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
class UserEntityRepository implements UserRepository {

    private final UserDynamoDBRepository userDynamoDBRepository;

    public UserEntityRepository(UserDynamoDBRepository userDynamoDBRepository) {
        this.userDynamoDBRepository = userDynamoDBRepository;
    }

    @Override
    public User save(User user) {
        UserEntity gameEntity = new UserEntity(user);
        UserEntity savedUserEntity = userDynamoDBRepository.save(gameEntity);
        return savedUserEntity.toUser();
    }

    @Override
    public User getByUserName(String userId) {
        UserEntity fetchedUser = userDynamoDBRepository.findFirstByUserName(userId);
        if (fetchedUser != null) {
            return fetchedUser.toUser();
        } else {
            return null;
        }
    }

    @Override
    public User update(User user) {
        return this.save(user);
    }

    @Override
    public boolean exists(String userId) {
        return userDynamoDBRepository.existsById(userId);
    }
}