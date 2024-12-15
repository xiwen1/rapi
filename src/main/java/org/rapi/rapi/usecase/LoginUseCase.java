package org.rapi.rapi.usecase;

import org.rapi.rapi.application.auth.service.query.GetUserByNameQuery;
import org.rapi.rapi.exception.InvalidCredentialsException;
import org.rapi.rapi.presentation.configuration.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final JwtUtil jwtUtil;
    private final GetUserByNameQuery getUserByNameQuery;

    public LoginUseCase(JwtUtil jwtUtil, GetUserByNameQuery getUserByNameQuery) {
        this.jwtUtil = jwtUtil;
        this.getUserByNameQuery = getUserByNameQuery;
    }

    public String login(String username, String password) {
        var user = getUserByNameQuery.getUserByName(username);
        if (user.getPassword().equals(password)) {
            return jwtUtil.generateToken(user);
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }
}
