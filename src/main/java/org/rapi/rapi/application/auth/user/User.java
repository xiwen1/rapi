package org.rapi.rapi.application.auth.user;

import org.rapi.rapi.sharedkernel.Entity;

public class User implements Entity<UserId> {
    private UserId id;
    @Override
    public UserId getId() {
        return id;
    }

    private User(UserId id) {
        this.id = id;
    }

    public static User create(UserId id) {
        return new User(id);
    }

    public static User create() {
        return new User(UserId.create());
    }
}
