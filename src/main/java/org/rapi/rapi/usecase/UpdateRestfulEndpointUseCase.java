package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.service.command.UpdateRestfulEndpointCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.command.SwitchStateCommand;
import org.springframework.stereotype.Service;

@Service
public class UpdateRestfulEndpointUseCase {

    private final UpdateRestfulEndpointCommand updateRestfulEndpointCommand;
    private final SwitchStateCommand switchStateCommand;
    private final DomainIdMappingService domainIdMappingService;

    public UpdateRestfulEndpointUseCase(UpdateRestfulEndpointCommand updateRestfulEndpointCommand,
        SwitchStateCommand switchStateCommand, DomainIdMappingService domainIdMappingService) {
        this.updateRestfulEndpointCommand = updateRestfulEndpointCommand;
        this.switchStateCommand = switchStateCommand;
        this.domainIdMappingService = domainIdMappingService;
    }

    public void updateRestfulEndpoint(RestfulEndpoint endpoint, StateId stateId,
        ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var subjectId = domainIdMappingService.getSubjectId(endpoint.getId());
        updateRestfulEndpointCommand.updateRestfulEndpoint(endpoint, inventoryId);
        switchStateCommand.switchState(collectionId, stateId, subjectId);
    }
}
