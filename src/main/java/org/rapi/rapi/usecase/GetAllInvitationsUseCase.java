package org.rapi.rapi.usecase;

import io.vavr.collection.List;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetAllInvitationsUseCase {

    private final ProjectPersistence projectPersistence;
    private final DomainIdMappingService domainIdMappingService;

    public GetAllInvitationsUseCase(ProjectPersistence projectPersistence,
        DomainIdMappingService domainIdMappingService) {
        this.projectPersistence = projectPersistence;
        this.domainIdMappingService = domainIdMappingService;
    }

    public List<Project> getAllInvitations(UserId userId) {
        var crewId = domainIdMappingService.getCrewId(userId);
        return projectPersistence.findAllByInvitation(crewId);
    }
}
