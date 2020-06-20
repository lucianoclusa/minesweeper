package ar.com.lucianoclusa.minesweeper.domain;

import java.util.List;

public class User {
    private String userId;
    private String userName;
    private List<String> userGameIds;

    public User(String id, String userName) {
        this.userId = id;
        this.userName = userName;
    }

    public User(String id, String userName, List<String> userGameIds) {
        this.userId = id;
        this.userName = userName;
        this.userGameIds = userGameIds;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getUserGameIds() {
        return userGameIds;
    }

    public void setUserGameIds(List<String> userGameIds) {
        this.userGameIds = userGameIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
