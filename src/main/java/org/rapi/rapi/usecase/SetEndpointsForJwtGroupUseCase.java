package org.rapi.rapi.usecase;

import io.vavr.collection.List;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.SetEndpointsForJwtGroupCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class SetEndpointsForJwtGroupUseCase {

    private final SetEndpointsForJwtGroupCommand setEndpointsForJwtGroupCommand;
    private final DomainIdMappingService domainIdMappingService;
    private final TrackSubjectCommand trackSubjectCommand;

    public SetEndpointsForJwtGroupUseCase(
        SetEndpointsForJwtGroupCommand setEndpointsForJwtGroupCommand,
        DomainIdMappingService domainIdMappingService, TrackSubjectCommand trackSubjectCommand) {
        this.setEndpointsForJwtGroupCommand = setEndpointsForJwtGroupCommand;
        this.domainIdMappingService = domainIdMappingService;
        this.trackSubjectCommand = trackSubjectCommand;
    }

    public void setEndpointsForJwtGroup(List<EndpointId> endpoints, GroupId groupId,
        ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var result = setEndpointsForJwtGroupCommand.setEndpointsForJwtGroup(endpoints, groupId,
            inventoryId);
        result.removedFromInventory().forEach(tuple -> {
            var subjectId = domainIdMappingService.getSubjectId(tuple._1);
            domainIdMappingService.saveMapping(tuple._2, subjectId);
            domainIdMappingService.deleteMapping(tuple._1);
            var discussionId = domainIdMappingService.getDiscussionId(tuple._1);
            domainIdMappingService.saveMapping(tuple._2, discussionId);
            domainIdMappingService.deleteMapping(tuple._1);
        });
        result.addedToInventory().forEach(tuple -> {
            var subjectId = domainIdMappingService.getSubjectId(tuple._2);
            domainIdMappingService.saveMapping(tuple._1, subjectId);
            domainIdMappingService.deleteMapping(tuple._2);
            var discussionId = domainIdMappingService.getDiscussionId(tuple._2);
            domainIdMappingService.saveMapping(tuple._1, discussionId);
            domainIdMappingService.deleteMapping(tuple._2);
        });
    }
}
