package org.rapi.rapi.usecase;

import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.CreateCrudGroupCommand;
import org.rapi.rapi.application.api.service.command.SetStructureForCrudGroupCommand;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.discussion.service.command.CreateDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateCrudGroupUseCase {

    private final CreateCrudGroupCommand createCrudGroupCommand;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final DomainIdMappingService domainIdMappingService;
    private final SetStructureForCrudGroupCommand setStructureForCrudGroupCommand;
    private final TrackSubjectCommand trackSubjectCommand;
    private final CreateDiscussionCommand createDiscussionCommand;

    public CreateCrudGroupUseCase(CreateCrudGroupCommand createCrudGroupCommand,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        DomainIdMappingService domainIdMappingService,
        SetStructureForCrudGroupCommand setStructureForCrudGroupCommand,
        TrackSubjectCommand trackSubjectCommand, CreateDiscussionCommand createDiscussionCommand) {
        this.createCrudGroupCommand = createCrudGroupCommand;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.domainIdMappingService = domainIdMappingService;
        this.setStructureForCrudGroupCommand = setStructureForCrudGroupCommand;
        this.trackSubjectCommand = trackSubjectCommand;
        this.createDiscussionCommand = createDiscussionCommand;
    }

    public GroupId createCrudGroup(StructureId structureId, ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var group = createCrudGroupCommand.createCrudGroup(inventoryId);
        var generatedEndpoints = setStructureForCrudGroupCommand.setStructureForCrudGroup(
            group.getId(), structureId, inventoryId);
        generatedEndpoints.forEach(e -> {
            var subject = trackSubjectCommand.trackSubject(collectionId);
            domainIdMappingService.saveMapping(e, subject.getId());
            var discussion = createDiscussionCommand.createDiscussion();
            domainIdMappingService.saveMapping(e, discussion.getId());
        });
        return group.getId();
    }
}
