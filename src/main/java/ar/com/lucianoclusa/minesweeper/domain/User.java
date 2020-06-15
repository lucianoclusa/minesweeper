package ar.com.lucianoclusa.minesweeper.domain;

public class User {

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    private String id;
    private final String name;

    public static User findUserById(String userId) {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
