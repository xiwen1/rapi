package org.rapi.rapi.presentation;

import org.rapi.rapi.application.auth.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetCurrentUserService {

    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
