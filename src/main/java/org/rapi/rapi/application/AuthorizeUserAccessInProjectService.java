package org.rapi.rapi.application;

import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeUserAccessInProjectService {

    private final GetProjectByIdQuery getProjectByIdQuery;
    private final DomainIdMappingService domainIdMappingService;

    public AuthorizeUserAccessInProjectService(GetProjectByIdQuery getProjectByIdQuery,
        DomainIdMappingService domainIdMappingService) {
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.domainIdMappingService = domainIdMappingService;
    }

    public boolean authorizeUserAccessInProject(UserId userId, ProjectId projectId) {
        var project = getProjectByIdQuery.getProjectById(projectId);
        var crewId = domainIdMappingService.getCrewId(userId);
        return project.getParticipants().map(Participant::getCrew).contains(crewId);
    }

    public boolean authorizeOwnerInProject(UserId userId, ProjectId projectId) {
        var project = getProjectByIdQuery.getProjectById(projectId);
        var crewId = domainIdMappingService.getCrewId(userId);
        return project.getOwner().getCrew().equals(crewId);
    }
}
