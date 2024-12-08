package org.rapi.rapi.application.auth.service;

import org.rapi.rapi.application.auth.user.UserId;

public class ResetPasswordService {
    private final UserPersistence userPersistence;

    public ResetPasswordService(UserPersistence userPersistence) {
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
