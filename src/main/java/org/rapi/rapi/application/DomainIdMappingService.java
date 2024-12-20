package org.rapi.rapi.application;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.SubjectId;
import org.springframework.stereotype.Service;

@Service
public interface DomainIdMappingService {

    DiscussionId getDiscussionId(EndpointId endpointId);

    EndpointId getEndpointId(DiscussionId discussionId);

    SubjectId getSubjectId(EndpointId endpointId);

    EndpointId getEndpointId(SubjectId subjectId);

    InventoryId getInventoryId(ProjectId projectId);

    ProjectId getProjectId(InventoryId inventoryId);

    AuthorId getAuthorId(UserId userId);

    UserId getUserId(AuthorId authorId);

    CrewId getCrewId(UserId userId);

    UserId getUserId(CrewId crewId);

    CollectionId getCollectionId(ProjectId projectId);

    ProjectId getProjectId(CollectionId collectionId);

    void saveMapping(EndpointId endpointId, DiscussionId discussionId);

    void saveMapping(EndpointId endpointId, SubjectId subjectId);

    void saveMapping(ProjectId projectId, InventoryId inventoryId);

    void saveMapping(ProjectId projectId, CollectionId collectionId);

    void saveMapping(UserId userId, AuthorId authorId);

    void saveMapping(UserId userId, CrewId crewId);

    void deleteMapping(EndpointId endpointId);

    void deleteMapping(ProjectId projectId);

    void deleteMapping(UserId userId);

    void deleteMapping(DiscussionId discussionId);

    void deleteMapping(SubjectId subjectId);

    void deleteMapping(InventoryId inventoryId);

    void deleteMapping(CollectionId collectionId);


}
