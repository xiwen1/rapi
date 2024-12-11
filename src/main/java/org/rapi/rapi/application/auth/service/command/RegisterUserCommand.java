package org.rapi.rapi.application.auth.service.command;

import org.rapi.rapi.application.auth.service.UserPersistence;
import org.rapi.rapi.application.auth.user.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserCommand {

    private final UserPersistence userPersistence;

    public RegisterUserCommand(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User createUser(String username, String password) {
        // operation
        var user = User.create(username, password);

        // persistence
        userPersistence.register(user);

        return user;
    }
}