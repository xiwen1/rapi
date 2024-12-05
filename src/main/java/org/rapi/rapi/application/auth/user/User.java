package org.rapi.rapi.application.auth.user;

import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class User implements Entity<UserId> {
    private UserId id;
    @Setter
    private String username;
    @Setter
    private String password;

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
