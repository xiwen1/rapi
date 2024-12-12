package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.command.DeleteStructureCommand;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.springframework.stereotype.Service;

@Service
public class DeleteStructureUseCase {

    private final GetProjectByIdQuery getProjectByIdQuery;
    private final DomainIdMappingService domainIdMappingService;
    private final DeleteStructureCommand deleteStructureCommand;

    public DeleteStructureUseCase(GetProjectByIdQuery getProjectByIdQuery,
        DomainIdMappingService domainIdMappingService,
        DeleteStructureCommand deleteStructureCommand) {
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.domainIdMappingService = domainIdMappingService;
        this.deleteStructureCommand = deleteStructureCommand;
    }

    public void deleteStructure(ProjectId projectId, UserId userId, StructureId structureId) {
        var project = getProjectByIdQuery.getProjectById(projectId);
        var crewId = domainIdMappingService.getCrewId(userId);
        if (!project.getParticipants().map(Participant::getCrew).contains(crewId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        // Delete the structure
        deleteStructureCommand.deleteStructure(inventoryId, structureId);
    }
}
