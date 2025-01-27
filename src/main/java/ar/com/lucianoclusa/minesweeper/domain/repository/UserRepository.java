package ar.com.lucianoclusa.minesweeper.domain.repository;

import ar.com.lucianoclusa.minesweeper.domain.User;

public interface UserRepository {
    User save(User user);
    User getByUserName(String userId);
    void update(User user);
    boolean exists(String userId);
}
