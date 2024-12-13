package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.service.command.DeleteGrpcEndpointCommand;
import org.rapi.rapi.application.discussion.service.command.DeleteDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.DeleteSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class DeleteGrpcEndpointUseCase {


    private final DomainIdMappingService domainIdMappingService;
    private final DeleteGrpcEndpointCommand deleteGrpcEndpointCommand;
    private final DeleteSubjectCommand deleteSubjectCommand;
    private final DeleteDiscussionCommand deleteDiscussionCommand;

    public DeleteGrpcEndpointUseCase(DomainIdMappingService domainIdMappingService,
        DeleteGrpcEndpointCommand deleteGrpcEndpointCommand,
        DeleteSubjectCommand deleteSubjectCommand,
        DeleteDiscussionCommand deleteDiscussionCommand) {
        this.domainIdMappingService = domainIdMappingService;
        this.deleteGrpcEndpointCommand = deleteGrpcEndpointCommand;
        this.deleteSubjectCommand = deleteSubjectCommand;
        this.deleteDiscussionCommand = deleteDiscussionCommand;
    }

    public void deleteGrpcEndpoint(EndpointId endpointId, ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        deleteGrpcEndpointCommand.deleteGrpcEndpoint(inventoryId, endpointId);
        var subjectId = domainIdMappingService.getSubjectId(endpointId);
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        deleteSubjectCommand.deleteSubject(subjectId, collectionId);
        deleteDiscussionCommand.deleteDiscussion(discussionId);
        domainIdMappingService.deleteMapping(endpointId);
    }
}
