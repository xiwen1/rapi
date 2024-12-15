package org.rapi.rapi.application.auth.service.query;

import org.rapi.rapi.application.auth.service.UserPersistence;
import org.rapi.rapi.application.auth.user.User;
import org.springframework.stereotype.Service;

@Service
public class GetUserByNameQuery {

    private final UserPersistence userPersistence;

    public GetUserByNameQuery(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User getUserByName(String username) {
        return userPersistence.findByUsername(username);
    }
}
