package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.DissolveCrudGroupCommand;
import org.rapi.rapi.application.api.service.query.GetInventoryByIdQuery;
import org.rapi.rapi.application.discussion.service.command.DeleteDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.DeleteSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class DissolveCrudGroupUseCase {

    private final DomainIdMappingService domainIdMappingService;
    private final DissolveCrudGroupCommand dissolveCrudGroupCommand;
    private final DeleteSubjectCommand deleteSubjectCommand;
    private final DeleteDiscussionCommand deleteDiscussionCommand;
    private final GetInventoryByIdQuery getInventoryByIdQuery;

    public DissolveCrudGroupUseCase(DomainIdMappingService domainIdMappingService,
        DissolveCrudGroupCommand dissolveCrudGroupCommand,
        DeleteSubjectCommand deleteSubjectCommand, DeleteDiscussionCommand deleteDiscussionCommand,
        GetInventoryByIdQuery getInventoryByIdQuery) {
        this.domainIdMappingService = domainIdMappingService;
        this.dissolveCrudGroupCommand = dissolveCrudGroupCommand;
        this.deleteSubjectCommand = deleteSubjectCommand;
        this.deleteDiscussionCommand = deleteDiscussionCommand;
        this.getInventoryByIdQuery = getInventoryByIdQuery;
    }

    public void dissolveCrudGroup(ProjectId projectId, GroupId groupId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        dissolveCrudGroupCommand.dissolveCrudGroup(groupId, inventoryId);
    }
}
