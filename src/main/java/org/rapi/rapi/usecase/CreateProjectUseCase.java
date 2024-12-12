package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.command.CreateInventoryCommand;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.service.command.CreateProjectCommand;
import org.rapi.rapi.application.state.service.command.CreateCollectionCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectUseCase {

    private final DomainIdMappingService domainIdMappingService;
    private final CreateProjectCommand createProjectCommand;
    private final CreateCollectionCommand createCollectionCommand;
    private final CreateInventoryCommand createInventoryCommand;

    public CreateProjectUseCase(DomainIdMappingService domainIdMappingService,
        CreateProjectCommand createProjectCommand, CreateCollectionCommand createCollectionCommand,
        CreateInventoryCommand createInventoryCommand) {
        this.domainIdMappingService = domainIdMappingService;
        this.createProjectCommand = createProjectCommand;
        this.createCollectionCommand = createCollectionCommand;
        this.createInventoryCommand = createInventoryCommand;
    }

    public ProjectId createProject(UserId userId, String projectName) {
        var crewId = domainIdMappingService.getCrewId(userId);
        var project = createProjectCommand.createProject(projectName, new Admin(crewId));
        var collection = createCollectionCommand.createCollection();
        var inventory = createInventoryCommand.createInventory();
        domainIdMappingService.saveMapping(project.getId(), inventory.getId());
        domainIdMappingService.saveMapping(project.getId(), collection.getId());
        return project.getId();
    }
}
