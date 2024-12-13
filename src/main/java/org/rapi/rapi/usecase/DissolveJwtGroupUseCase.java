package org.rapi.rapi.usecase;

import io.vavr.collection.List;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.DissolveJwtGroupCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.springframework.stereotype.Service;

@Service
public class DissolveJwtGroupUseCase {

    private final DomainIdMappingService domainIdMappingService;
    private final SetEndpointsForJwtGroupUseCase setEndpointsForJwtGroupUseCase;
    private final DissolveJwtGroupCommand dissolveJwtGroupCommand;

    public DissolveJwtGroupUseCase(DomainIdMappingService domainIdMappingService,
        SetEndpointsForJwtGroupUseCase setEndpointsForJwtGroupUseCase,
        DissolveJwtGroupCommand dissolveJwtGroupCommand) {
        this.domainIdMappingService = domainIdMappingService;
        this.setEndpointsForJwtGroupUseCase = setEndpointsForJwtGroupUseCase;
        this.dissolveJwtGroupCommand = dissolveJwtGroupCommand;
    }

    public void dissolveJwtGroup(ProjectId projectId, GroupId groupId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        setEndpointsForJwtGroupUseCase.setEndpointsForJwtGroup(List.empty(), groupId, projectId);
        dissolveJwtGroupCommand.dissolveJwtGroup(groupId, inventoryId);
    }
}
