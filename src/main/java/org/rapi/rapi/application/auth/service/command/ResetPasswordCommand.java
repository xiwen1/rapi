package org.rapi.rapi.application.auth.service.command;

import org.rapi.rapi.application.auth.service.UserPersistence;
import org.rapi.rapi.application.auth.user.UserId;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordCommand {

    private final UserPersistence userPersistence;

    public ResetPasswordCommand(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public void resetPassword(UserId userId, String newPassword) {
        // prepare
        var user = userPersistence.findById(userId);

        // operate
        user.resetPassword(newPassword);

        // persist
        userPersistence.save(user);
    }
}
