package org.rapi.rapi.application.auth.user;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class User implements Entity<UserId> {

    private final UserId id;
    private final String password;
    private String username;

    private User(UserId id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User fromRaw(UserId id, String username, String password) {
        return new User(id, username, password);
    }

    public static User create(String username, String password) {
        return new User(UserId.create(), username, password);
    }

    public void resetPassword(String username) {
        this.username = username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}
