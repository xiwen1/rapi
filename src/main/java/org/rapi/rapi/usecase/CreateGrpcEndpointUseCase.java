package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.service.command.CreateGrpcEndpointCommand;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.discussion.service.command.CreateDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateGrpcEndpointUseCase {

    private final DomainIdMappingService domainIdMappingService;
    private final CreateGrpcEndpointCommand createGrpcEndpointCommand;
    private final TrackSubjectCommand trackSubjectCommand;
    private final CreateDiscussionCommand createDiscussionCommand;

    public CreateGrpcEndpointUseCase(DomainIdMappingService domainIdMappingService,
        CreateGrpcEndpointCommand createGrpcEndpointCommand,
        TrackSubjectCommand trackSubjectCommand, CreateDiscussionCommand createDiscussionCommand) {
        this.domainIdMappingService = domainIdMappingService;
        this.createGrpcEndpointCommand = createGrpcEndpointCommand;
        this.trackSubjectCommand = trackSubjectCommand;
        this.createDiscussionCommand = createDiscussionCommand;
    }

    public EndpointId createGrpcEndpoint(String name, String description, String service,
        boolean isParamStream, boolean isResultStream, ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var endpoint = createGrpcEndpointCommand.createGrpcEndpoint(name, description, service,
            isParamStream, isResultStream, ObjectSchema.create(), ObjectSchema.create(),
            inventoryId);
        var subject = trackSubjectCommand.trackSubject(collectionId);
        var discussion = createDiscussionCommand.createDiscussion();
        domainIdMappingService.saveMapping(endpoint.getId(), subject.getId());
        domainIdMappingService.saveMapping(endpoint.getId(), discussion.getId());
        return endpoint.getId();
    }

}
