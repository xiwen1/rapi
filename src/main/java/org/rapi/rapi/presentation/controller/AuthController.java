package org.rapi.rapi.presentation.controller;

import org.rapi.rapi.usecase.LoginUseCase;
import org.rapi.rapi.usecase.RegisterUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(LoginRequest loginRequest) {
        return new LoginResponse(
            loginUseCase.login(loginRequest.username(), loginRequest.password()));
    }

    @PostMapping("/register")
    public void register(RegisterRequest registerRequest) {
        registerUseCase.register(registerRequest.username(), registerRequest.password(),
            registerRequest.email());
    }

    public record LoginRequest(String username, String password) {

    }

    public record LoginResponse(String token) {

    }

    public record RegisterRequest(String username, String password, String email) {

    }


}


