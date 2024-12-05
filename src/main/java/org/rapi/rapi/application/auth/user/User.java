package org.rapi.rapi.application.auth.user;

import org.rapi.rapi.sharedkernel.Entity;

public class User implements Entity<UserId> {
    private UserId id;
    private String username;

    @Override
    public UserId getId() {
        return id;
    }

    private User(UserId id, String username) {
        this.id = id;
        this.username = username;
    }

    public static User create(UserId id, String username) {
        return new User(id, username);
    }

    public static User create(String username) {
        return new User(UserId.create(), username);
    }
}
