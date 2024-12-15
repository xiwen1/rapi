package org.rapi.rapi.presentation.controller;

import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.usecase.GetUserProfileUseCase;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final GetUserProfileUseCase getUserProfileUseCase;

    public UserProfileController(GetUserProfileUseCase getUserProfileUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase;
    }

    @GetMapping("")
    public GetProfileResponse getProfile() {
        var authToken = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authToken.getPrincipal();
        var userProfile = getUserProfileUseCase.getUserProfile(user.getId());
        return new GetProfileResponse(userProfile.username(), userProfile.email(),
            userProfile.id());
    }

    public record GetProfileResponse(String username, String email, String id) {

    }


}
