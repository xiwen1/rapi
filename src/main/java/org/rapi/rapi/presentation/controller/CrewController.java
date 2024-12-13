package org.rapi.rapi.presentation.controller;


import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.auth.service.query.GetUserByNameQuery;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.service.command.DemoteAdminCommand;
import org.rapi.rapi.application.project.service.command.JoinProjectCommand;
import org.rapi.rapi.application.project.service.command.PromoteMemberCommand;
import org.rapi.rapi.application.project.service.query.GetCrewByEmailQuery;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.rapi.rapi.usecase.InviteCrewUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/{project_id}/crew")
public class CrewController {

    private final GetUserByNameQuery getUserByNameQuery;
    private final GetCrewByEmailQuery getCrewByEmailQuery;
    private final InviteCrewUseCase inviteCrewUseCase;
    private final UuidConverter uuidConverter;
    private final GetCurrentUserService getCurrentUserService;
    private final JoinProjectCommand joinProjectCommand;
    private final DomainIdMappingService domainIdMappingService;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final PromoteMemberCommand promoteMemberCommand;
    private final DemoteAdminCommand demoteAdminCommand;

    public CrewController(GetUserByNameQuery getUserByNameQuery,
        GetCrewByEmailQuery getCrewByEmailQuery, InviteCrewUseCase inviteCrewUseCase,
        UuidConverter uuidConverter, GetCurrentUserService getCurrentUserService,
        JoinProjectCommand joinProjectCommand, DomainIdMappingService domainIdMappingService,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        PromoteMemberCommand promoteMemberCommand, DemoteAdminCommand demoteAdminCommand) {
        this.getUserByNameQuery = getUserByNameQuery;
        this.getCrewByEmailQuery = getCrewByEmailQuery;
        this.inviteCrewUseCase = inviteCrewUseCase;
        this.uuidConverter = uuidConverter;
        this.getCurrentUserService = getCurrentUserService;
        this.joinProjectCommand = joinProjectCommand;
        this.domainIdMappingService = domainIdMappingService;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.promoteMemberCommand = promoteMemberCommand;
        this.demoteAdminCommand = demoteAdminCommand;
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> inviteCrew(@PathVariable("project_id") String projectIdString,
        @RequestBody InviteCrewRequest request) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        inviteCrewUseCase.inviteCrew(request.email(), projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<Void> joinProject(@PathVariable("project_id") String projectIdString) {
        var user = getCurrentUserService.getUser();
        var crewId = domainIdMappingService.getCrewId(user.getId());
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        joinProjectCommand.joinProject(projectId, crewId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{crew_id}")
    public ResponseEntity<Void> changeRole(@PathVariable("project_id") String projectIdString,
        @PathVariable("crew_id") String crewIdString, @RequestBody ChangeRoleRequest request) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var crewId = new CrewId(uuidConverter.fromString(crewIdString));
        var user = getCurrentUserService.getUser();
        if(!authorizeUserAccessInProjectService.authorizeOwnerInProject(user.getId(), projectId)) {
            return ResponseEntity.status(403).build();
        }
        switch (request.action()) {
            case "promote" -> {
                promoteMemberCommand.promoteMember(projectId, new Member(crewId));
            }
            case "demote" -> {
                demoteAdminCommand.demoteAdmin(projectId, new Admin(crewId));
            }
            default -> {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    public record ChangeRoleRequest(String action) {

    }

    public record InviteCrewRequest(String email) {

    }

}
