package org.rapi.rapi.usecase;

import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.CreateJwtGroupCommand;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.discussion.service.command.CreateDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateJwtGroupUseCase {

    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final CreateJwtGroupCommand createJwtGroupCommand;
    private final DomainIdMappingService domainIdMappingService;
    private final TrackSubjectCommand trackSubjectCommand;
    private final CreateDiscussionCommand createDiscussionCommand;

    public CreateJwtGroupUseCase(
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        CreateJwtGroupCommand createJwtGroupCommand,
        DomainIdMappingService domainIdMappingService, TrackSubjectCommand trackSubjectCommand,
        CreateDiscussionCommand createDiscussionCommand) {
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.createJwtGroupCommand = createJwtGroupCommand;
        this.domainIdMappingService = domainIdMappingService;
        this.trackSubjectCommand = trackSubjectCommand;
        this.createDiscussionCommand = createDiscussionCommand;
    }

    public GroupId createJwtGroup(UserId userId, ProjectId projectId) {
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(userId, projectId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var group = createJwtGroupCommand.createJwtGroup(inventoryId);
        var endpoints = group.getGeneratedEndpoints();
        endpoints.forEach(e -> {
            var subject = trackSubjectCommand.trackSubject(collectionId);
            domainIdMappingService.saveMapping(e, subject.getId());
            var discussion = createDiscussionCommand.createDiscussion();
            domainIdMappingService.saveMapping(e, discussion.getId());
        });
        return group.getId();
    }
}
