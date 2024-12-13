package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.service.command.DeleteRestfulEndpointCommand;
import org.rapi.rapi.application.discussion.service.command.DeleteDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.DeleteSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class DeleteRestfulEndpointUseCase {

    private final DomainIdMappingService domainIdMappingService;
    private final DeleteRestfulEndpointCommand deleteRestfulEndpointCommand;
    private final DeleteSubjectCommand deleteSubjectCommand;
    private final DeleteDiscussionCommand deleteDiscussionCommand;

    public DeleteRestfulEndpointUseCase(DomainIdMappingService domainIdMappingService,
        DeleteRestfulEndpointCommand deleteRestfulEndpointCommand,
        DeleteSubjectCommand deleteSubjectCommand,
        DeleteDiscussionCommand deleteDiscussionCommand) {
        this.domainIdMappingService = domainIdMappingService;
        this.deleteRestfulEndpointCommand = deleteRestfulEndpointCommand;
        this.deleteSubjectCommand = deleteSubjectCommand;
        this.deleteDiscussionCommand = deleteDiscussionCommand;
    }

    public void deleteRestfulEndpoint(EndpointId endpointId, ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        deleteRestfulEndpointCommand.deleteRestfulEndpoint(inventoryId, endpointId);
        var subjectId = domainIdMappingService.getSubjectId(endpointId);
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        deleteSubjectCommand.deleteSubject(subjectId, collectionId);
        deleteDiscussionCommand.deleteDiscussion(discussionId);
        domainIdMappingService.deleteMapping(endpointId);
    }
}
