package org.rapi.rapi.application.auth.service;

import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.application.auth.user.UserId;

public interface UserPersistence {

    void save(User user);

    User findById(UserId userId);

    User findByUsername(String username);

    void delete(UserId userId);

    void register(User user);

}
