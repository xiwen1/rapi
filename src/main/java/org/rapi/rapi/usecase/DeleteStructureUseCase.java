package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.command.DeleteRestfulEndpointCommand;
import org.rapi.rapi.application.api.service.command.DeleteStructureCommand;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.discussion.service.command.DeleteDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.rapi.rapi.application.state.service.command.DeleteSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class DeleteStructureUseCase {

    private final GetProjectByIdQuery getProjectByIdQuery;
    private final DomainIdMappingService domainIdMappingService;
    private final DeleteStructureCommand deleteStructureCommand;
    private final DeleteRestfulEndpointCommand deleteRestfulEndpointCommand;
    private final DeleteSubjectCommand deleteSubjectCommand;
    private final DeleteDiscussionCommand deleteDiscussionCommand;

    public DeleteStructureUseCase(GetProjectByIdQuery getProjectByIdQuery,
        DomainIdMappingService domainIdMappingService,
        DeleteStructureCommand deleteStructureCommand,
        DeleteRestfulEndpointCommand deleteRestfulEndpointCommand,
        DeleteSubjectCommand deleteSubjectCommand,
        DeleteDiscussionCommand deleteDiscussionCommand) {
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.domainIdMappingService = domainIdMappingService;
        this.deleteStructureCommand = deleteStructureCommand;
        this.deleteRestfulEndpointCommand = deleteRestfulEndpointCommand;
        this.deleteSubjectCommand = deleteSubjectCommand;
        this.deleteDiscussionCommand = deleteDiscussionCommand;
    }

    public void deleteStructure(ProjectId projectId, UserId userId, StructureId structureId) {
        var project = getProjectByIdQuery.getProjectById(projectId);
        var crewId = domainIdMappingService.getCrewId(userId);
        if (!project.getParticipants().map(Participant::getCrew).contains(crewId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        // Delete the structure
        var endpointsDeleted = deleteStructureCommand.deleteStructure(inventoryId, structureId);
        var subjectIds = endpointsDeleted.map(domainIdMappingService::getSubjectId);
        var discussionIds = endpointsDeleted.map(domainIdMappingService::getDiscussionId);
        subjectIds.forEach(s -> deleteSubjectCommand.deleteSubject(s, collectionId));
        discussionIds.forEach(deleteDiscussionCommand::deleteDiscussion);
        endpointsDeleted.forEach(domainIdMappingService::deleteMapping);
    }
}
