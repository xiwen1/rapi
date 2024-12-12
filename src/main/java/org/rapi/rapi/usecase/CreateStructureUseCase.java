package org.rapi.rapi.usecase;

import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.command.CreateStructureCommand;
import org.rapi.rapi.application.api.service.command.UpdateStructureCommand;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.springframework.stereotype.Service;

@Service
public class CreateStructureUseCase {

    private final DomainIdMappingService domainIdMappingService;
    private final CreateStructureCommand createStructureCommand;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final UpdateStructureCommand updateStructureCommand;

    public CreateStructureUseCase(DomainIdMappingService domainIdMappingService,
        CreateStructureCommand createStructureCommand,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        UpdateStructureCommand updateStructureCommand) {
        this.domainIdMappingService = domainIdMappingService;
        this.createStructureCommand = createStructureCommand;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.updateStructureCommand = updateStructureCommand;
    }

    public StructureId createStructure(String name, ProjectId projectId, UserId userId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(userId, projectId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }
        var structure = createStructureCommand.createStructureInInventory(inventoryId);
        structure.rename(name);
        updateStructureCommand.updateStructure(structure, inventoryId);
        return structure.getId();
    }
}
