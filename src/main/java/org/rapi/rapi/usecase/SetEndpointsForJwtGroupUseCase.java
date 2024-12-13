package org.rapi.rapi.usecase;

import io.vavr.collection.List;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.command.SetEndpointsForJwtGroupCommand;
import org.rapi.rapi.application.discussion.service.command.CreateDiscussionCommand;
import org.rapi.rapi.application.discussion.service.command.DeleteDiscussionCommand;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.service.command.DeleteSubjectCommand;
import org.rapi.rapi.application.state.service.command.TrackSubjectCommand;
import org.springframework.stereotype.Service;

@Service
public class SetEndpointsForJwtGroupUseCase {

    private final SetEndpointsForJwtGroupCommand setEndpointsForJwtGroupCommand;
    private final DomainIdMappingService domainIdMappingService;
    private final TrackSubjectCommand trackSubjectCommand;
    private final CreateDiscussionCommand createDiscussionCommand;
    private final DeleteSubjectCommand deleteSubjectCommand;
    private final DeleteDiscussionCommand deleteDiscussionCommand;

    public SetEndpointsForJwtGroupUseCase(
        SetEndpointsForJwtGroupCommand setEndpointsForJwtGroupCommand,
        DomainIdMappingService domainIdMappingService, TrackSubjectCommand trackSubjectCommand,
        CreateDiscussionCommand createDiscussionCommand, DeleteSubjectCommand deleteSubjectCommand,
        DeleteDiscussionCommand deleteDiscussionCommand) {
        this.setEndpointsForJwtGroupCommand = setEndpointsForJwtGroupCommand;
        this.domainIdMappingService = domainIdMappingService;
        this.trackSubjectCommand = trackSubjectCommand;
        this.createDiscussionCommand = createDiscussionCommand;
        this.deleteSubjectCommand = deleteSubjectCommand;
        this.deleteDiscussionCommand = deleteDiscussionCommand;
    }

    public void setEndpointsForJwtGroup(List<EndpointId> endpoints, GroupId groupId,
        ProjectId projectId) {
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var result = setEndpointsForJwtGroupCommand.setEndpointsForJwtGroup(endpoints, groupId,
            inventoryId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        result.removedFromInventory().forEach(tuple -> {
            var subjectId = domainIdMappingService.getSubjectId(tuple._1);
            var discussionId = domainIdMappingService.getDiscussionId(tuple._1);
            domainIdMappingService.deleteMapping(tuple._1);
            domainIdMappingService.saveMapping(tuple._2, subjectId);
            domainIdMappingService.saveMapping(tuple._2, discussionId);

        });
        result.addedToInventory().forEach(tuple -> {
            var subjectId = domainIdMappingService.getSubjectId(tuple._2);
            var discussionId = domainIdMappingService.getDiscussionId(tuple._2);
            domainIdMappingService.deleteMapping(tuple._2);
            domainIdMappingService.saveMapping(tuple._1, subjectId);
            domainIdMappingService.saveMapping(tuple._1, discussionId);

        });
        result.getFromCrud().forEach(tuple -> {
            var subject = trackSubjectCommand.trackSubject(collectionId);
            domainIdMappingService.saveMapping(tuple._2, subject.getId());
            var discussion = createDiscussionCommand.createDiscussion();
            domainIdMappingService.saveMapping(tuple._2, discussion.getId());
        });
        result.backToCrud().forEach(tuple -> {
            var subjectId = domainIdMappingService.getSubjectId(tuple._2);
            var discussionId = domainIdMappingService.getDiscussionId(tuple._2);
            deleteSubjectCommand.deleteSubject(subjectId, collectionId);
            deleteDiscussionCommand.deleteDiscussion(discussionId);
            domainIdMappingService.deleteMapping(tuple._2);
        });
    }
}
