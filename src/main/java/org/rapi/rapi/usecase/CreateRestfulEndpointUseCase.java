package org.rapi.rapi.usecase;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.service.command.CreateRestfulEndpointCommand;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.discussion.service.command.CreateDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class CreateRestfulEndpointUseCase {

    private final CreateRestfulEndpointCommand createRestfulEndpointCommand;
    private final DomainIdMappingService domainIdMappingService;
    private final TrackSubjectCommand trackSubjectCommand;
    private final CreateDiscussionCommand createDiscussionCommand;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;

    public CreateRestfulEndpointUseCase(
        CreateRestfulEndpointCommand createRestfulEndpointCommand,
        DomainIdMappingService domainIdMappingService, TrackSubjectCommand trackSubjectCommand,
        CreateDiscussionCommand createDiscussionCommand,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService) {
        this.createRestfulEndpointCommand = createRestfulEndpointCommand;
        this.domainIdMappingService = domainIdMappingService;
        this.trackSubjectCommand = trackSubjectCommand;
        this.createDiscussionCommand = createDiscussionCommand;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
    }

    public EndpointId createRestfulEndpoint(String title, String description, HttpMethod method,
        ProjectId projectId, UserId userId) {
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(userId, projectId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }

        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var endpoint = createRestfulEndpointCommand.createRestfulEndpoint(title, description,
            Option.none(), method,
            Route.create(List.of()), List.of(), inventoryId);
        var subject = trackSubjectCommand.trackSubject(collectionId);
        var discussion = createDiscussionCommand.createDiscussion();
        domainIdMappingService.saveMapping(endpoint.getId(), subject.getId());
        domainIdMappingService.saveMapping(endpoint.getId(), discussion.getId());
        return endpoint.getId();
    }
}
