package org.rapi.rapi.application.auth.service.command;

import org.rapi.rapi.application.auth.service.UserPersistence;
import org.rapi.rapi.application.auth.user.User;

public class CreateUserService {
    private final UserPersistence userPersistence;

    public CreateUserService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User createUser(String username, String password) {
        // operation
        var user = User.create(username, password);

        // persistence
        userPersistence.save(user);

        return user;
    }
}