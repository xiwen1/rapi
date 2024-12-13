package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.SetStructureForCrudGroupCommand;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.discussion.service.command.CreateDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class SetStructureForCrudGroupUseCase {


    private final SetStructureForCrudGroupCommand setStructureForCrudGroupCommand;
    private final DomainIdMappingService domainIdMappingService;
    private final TrackSubjectCommand trackSubjectCommand;
    private final CreateDiscussionCommand createDiscussionCommand;

    public SetStructureForCrudGroupUseCase(
        SetStructureForCrudGroupCommand setStructureForCrudGroupCommand,
        DomainIdMappingService domainIdMappingService, TrackSubjectCommand trackSubjectCommand,
        CreateDiscussionCommand createDiscussionCommand) {
        this.setStructureForCrudGroupCommand = setStructureForCrudGroupCommand;
        this.domainIdMappingService = domainIdMappingService;
        this.trackSubjectCommand = trackSubjectCommand;
        this.createDiscussionCommand = createDiscussionCommand;
    }

    public void setStructureForCrudGroup(GroupId groupId, StructureId structureId,
        ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var generatedEndpoints = setStructureForCrudGroupCommand.setStructureForCrudGroup(groupId,
            structureId, inventoryId);
        generatedEndpoints.forEach(e -> {
            var subject = trackSubjectCommand.trackSubject(collectionId);
            domainIdMappingService.saveMapping(e, subject.getId());
            var discussion = createDiscussionCommand.createDiscussion();
            domainIdMappingService.saveMapping(e, discussion.getId());
        });

    }
}
