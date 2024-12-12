package org.rapi.rapi.application.auth.service.query;

import org.rapi.rapi.application.auth.service.UserPersistence;
import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.application.auth.user.UserId;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdQuery {

    private final UserPersistence userPersistence;

    public GetUserByIdQuery(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User getUserById(UserId id) {
        return userPersistence.findById(id);
    }
}
