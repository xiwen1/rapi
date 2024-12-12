package org.rapi.rapi.usecase;

import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.command.UpdateStructureCommand;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.springframework.stereotype.Service;

@Service
public class UpdateStructureUseCase {

    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final UpdateStructureCommand updateStructureCommand;
    private final DomainIdMappingService domainIdMappingService;

    public UpdateStructureUseCase(
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        UpdateStructureCommand updateStructureCommand,
        DomainIdMappingService domainIdMappingService) {
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.updateStructureCommand = updateStructureCommand;
        this.domainIdMappingService = domainIdMappingService;
    }

    public void updateStructure(ProjectId projectId, UserId userId,
        Structure structure) {
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(userId, projectId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        updateStructureCommand.updateStructure(structure, inventoryId);
    }
}
