package org.rapi.rapi.presentation.controller;

import java.util.List;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.rapi.rapi.usecase.GetAllInvitationsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

    private final GetCurrentUserService getCurrentUserService;
    private final GetAllInvitationsUseCase getAllInvitationsUseCase;
    private final UuidConverter uuidConverter;

    public InvitationController(GetCurrentUserService getCurrentUserService,
        GetAllInvitationsUseCase getAllInvitationsUseCase, UuidConverter uuidConverter) {
        this.getCurrentUserService = getCurrentUserService;
        this.getAllInvitationsUseCase = getAllInvitationsUseCase;
        this.uuidConverter = uuidConverter;
    }

    @GetMapping("")
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        var user = getCurrentUserService.getUser();
        var allProjects = getAllInvitationsUseCase.getAllInvitations(user.getId());
        return ResponseEntity.ok(allProjects.map(
                project -> new Invitation(uuidConverter.toString(project.getId().id()),
                    project.getTitle()))
            .toJavaList());
    }

    public record Invitation(String projectId, String projectName) {

    }
}
