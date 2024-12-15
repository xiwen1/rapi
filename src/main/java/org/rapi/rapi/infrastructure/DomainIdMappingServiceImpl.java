package org.rapi.rapi.infrastructure;

import java.util.UUID;
import org.rapi.rapi.application.DomainIdMappingService;
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
public class DomainIdMappingServiceImpl implements DomainIdMappingService {

    private final static String ID = "domain_id_mapper";

    private final DomainIdMapperRepository repository;

    public DomainIdMappingServiceImpl(DomainIdMapperRepository repository) {
        this.repository = repository;
    }

    private DomainIdMapper getMapper() {
        var domainIdMapper = repository.findById(ID);
        if (domainIdMapper.isEmpty()) {
            var mapper = new DomainIdMapper(ID);
            repository.save(mapper);
            return mapper;
        }
        return domainIdMapper.get();
    }

    private void saveMapper(DomainIdMapper mapper) {
        repository.save(mapper);
    }

    @Override
    public DiscussionId getDiscussionId(EndpointId endpointId) {
        if (getMapper().getEndpointToDiscussionMap().containsKey(endpointId.id().toString())) {
            return new DiscussionId(UUID.fromString(
                getMapper().getEndpointToDiscussionMap().get(endpointId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No discussion id found for endpoint id: " + endpointId.id());
    }

    @Override
    public EndpointId getEndpointId(DiscussionId discussionId) {
        if (getMapper().getDiscussionToEndpointMap().containsKey(discussionId.id().toString())) {
            return new EndpointId(UUID.fromString(
                getMapper().getDiscussionToEndpointMap().get(discussionId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No endpoint id found for discussion id: " + discussionId.id());
    }

    @Override
    public SubjectId getSubjectId(EndpointId endpointId) {
        if (getMapper().getEndpointToSubjectMap().containsKey(endpointId.id().toString())) {
            return new SubjectId(UUID.fromString(
                getMapper().getEndpointToSubjectMap().get(endpointId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No subject id found for endpoint id: " + endpointId.id());
    }

    @Override
    public EndpointId getEndpointId(SubjectId subjectId) {
        if (getMapper().getSubjectToEndpointMap().containsKey(subjectId.id().toString())) {
            return new EndpointId(UUID.fromString(
                getMapper().getSubjectToEndpointMap().get(subjectId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No endpoint id found for subject id: " + subjectId.id());
    }

    @Override
    public InventoryId getInventoryId(ProjectId projectId) {
        if (getMapper().getProjectToInventoryMap().containsKey(projectId.id().toString())) {
            return new InventoryId(UUID.fromString(
                getMapper().getProjectToInventoryMap().get(projectId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No inventory id found for project id: " + projectId.id());
    }

    @Override
    public ProjectId getProjectId(InventoryId inventoryId) {
        if (getMapper().getInventoryToProjectMap().containsKey(inventoryId.id().toString())) {
            return new ProjectId(UUID.fromString(
                getMapper().getInventoryToProjectMap().get(inventoryId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No project id found for inventory id: " + inventoryId.id());
    }

    @Override
    public AuthorId getAuthorId(UserId userId) {
        if (getMapper().getUserToAuthorMap().containsKey(userId.id().toString())) {
            return new AuthorId(UUID.fromString(
                getMapper().getUserToAuthorMap().get(userId.id().toString())));
        }
        throw new IllegalArgumentException("No author id found for user id: " + userId.id());
    }

    @Override
    public UserId getUserId(AuthorId authorId) {
        if (getMapper().getAuthorToUserMap().containsKey(authorId.id().toString())) {
            return new UserId(UUID.fromString(
                getMapper().getAuthorToUserMap().get(authorId.id().toString())));
        }
        throw new IllegalArgumentException("No user id found for author id: " + authorId.id());
    }

    @Override
    public CrewId getCrewId(UserId userId) {
        if (getMapper().getUserToCrewMap().containsKey(userId.id().toString())) {
            return new CrewId(UUID.fromString(
                getMapper().getUserToCrewMap().get(userId.id().toString())));
        }
        throw new IllegalArgumentException("No crew id found for user id: " + userId.id());
    }

    @Override
    public UserId getUserId(CrewId crewId) {
        if (getMapper().getCrewToUserMap().containsKey(crewId.id().toString())) {
            return new UserId(UUID.fromString(
                getMapper().getCrewToUserMap().get(crewId.id().toString())));
        }
        throw new IllegalArgumentException("No user id found for crew id: " + crewId.id());
    }

    @Override
    public CollectionId getCollectionId(ProjectId projectId) {
        if (getMapper().getProjectToCollectionMap().containsKey(projectId.id().toString())) {
            return new CollectionId(UUID.fromString(
                getMapper().getProjectToCollectionMap().get(projectId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No collection id found for project id: " + projectId.id());
    }

    @Override
    public ProjectId getProjectId(CollectionId collectionId) {
        if (getMapper().getCollectionToProjectMap().containsKey(collectionId.id().toString())) {
            return new ProjectId(UUID.fromString(
                getMapper().getCollectionToProjectMap().get(collectionId.id().toString())));
        }
        throw new IllegalArgumentException(
            "No project id found for collection id: " + collectionId.id());
    }

    @Override
    public void saveMapping(EndpointId endpointId, DiscussionId discussionId) {
        var mapper = getMapper();
        mapper.getEndpointToDiscussionMap()
            .put(endpointId.id().toString(), discussionId.id().toString());
        mapper.getDiscussionToEndpointMap()
            .put(discussionId.id().toString(), endpointId.id().toString());
        saveMapper(mapper);
    }

    @Override
    public void saveMapping(EndpointId endpointId, SubjectId subjectId) {
        var mapper = getMapper();
        mapper.getEndpointToSubjectMap().put(endpointId.id().toString(), subjectId.id().toString());
        mapper.getSubjectToEndpointMap().put(subjectId.id().toString(), endpointId.id().toString());
        saveMapper(mapper);
    }

    @Override
    public void saveMapping(ProjectId projectId, InventoryId inventoryId) {
        var mapper = getMapper();
        mapper.getProjectToInventoryMap()
            .put(projectId.id().toString(), inventoryId.id().toString());
        mapper.getInventoryToProjectMap()
            .put(inventoryId.id().toString(), projectId.id().toString());
        saveMapper(mapper);
    }

    @Override
    public void saveMapping(ProjectId projectId, CollectionId collectionId) {
        var mapper = getMapper();
        mapper.getProjectToCollectionMap()
            .put(projectId.id().toString(), collectionId.id().toString());
        mapper.getCollectionToProjectMap()
            .put(collectionId.id().toString(), projectId.id().toString());
        saveMapper(mapper);
    }

    @Override
    public void saveMapping(UserId userId, AuthorId authorId) {
        var mapper = getMapper();
        mapper.getUserToAuthorMap().put(userId.id().toString(), authorId.id().toString());
        mapper.getAuthorToUserMap().put(authorId.id().toString(), userId.id().toString());
        saveMapper(mapper);
    }

    @Override
    public void saveMapping(UserId userId, CrewId crewId) {
        var mapper = getMapper();
        mapper.getUserToCrewMap().put(userId.id().toString(), crewId.id().toString());
        mapper.getCrewToUserMap().put(crewId.id().toString(), userId.id().toString());
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(EndpointId endpointId) {
        var mapper = getMapper();
        if (mapper.getEndpointToDiscussionMap().containsKey(endpointId.id().toString())) {
            var discussionId = new DiscussionId(UUID.fromString(
                mapper.getEndpointToDiscussionMap().get(endpointId.id().toString())));
            mapper.getEndpointToDiscussionMap().remove(endpointId.id().toString());
            mapper.getDiscussionToEndpointMap().remove(discussionId.id().toString());
        }
        if (mapper.getEndpointToSubjectMap().containsKey(endpointId.id().toString())) {
            var subjectId = new SubjectId(UUID.fromString(
                mapper.getEndpointToSubjectMap().get(endpointId.id().toString())));
            mapper.getEndpointToSubjectMap().remove(endpointId.id().toString());
            mapper.getSubjectToEndpointMap().remove(subjectId.id().toString());
        }
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(ProjectId projectId) {
        var mapper = getMapper();
        if (mapper.getProjectToInventoryMap().containsKey(projectId.id().toString())) {
            var inventoryId = new InventoryId(UUID.fromString(
                mapper.getProjectToInventoryMap().get(projectId.id().toString())));
            mapper.getProjectToInventoryMap().remove(projectId.id().toString());
            mapper.getInventoryToProjectMap().remove(inventoryId.id().toString());
        }
        if (mapper.getProjectToCollectionMap().containsKey(projectId.id().toString())) {
            var collectionId = new CollectionId(UUID.fromString(
                mapper.getProjectToCollectionMap().get(projectId.id().toString())));
            mapper.getProjectToCollectionMap().remove(projectId.id().toString());
            mapper.getCollectionToProjectMap().remove(collectionId.id().toString());
        }
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(UserId userId) {
        var mapper = getMapper();
        if (mapper.getUserToAuthorMap().containsKey(userId.id().toString())) {
            var authorId = new AuthorId(UUID.fromString(
                mapper.getUserToAuthorMap().get(userId.id().toString())));
            mapper.getUserToAuthorMap().remove(userId.id().toString());
            mapper.getAuthorToUserMap().remove(authorId.id().toString());
        }
        if (mapper.getUserToCrewMap().containsKey(userId.id().toString())) {
            var crewId = new CrewId(UUID.fromString(
                mapper.getUserToCrewMap().get(userId.id().toString())));
            mapper.getUserToCrewMap().remove(userId.id().toString());
            mapper.getCrewToUserMap().remove(crewId.id().toString());
        }
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(DiscussionId discussionId) {
        var mapper = getMapper();
        if (mapper.getDiscussionToEndpointMap().containsKey(discussionId.id().toString())) {
            var endpointId = new EndpointId(UUID.fromString(
                mapper.getDiscussionToEndpointMap().get(discussionId.id().toString())));
            mapper.getDiscussionToEndpointMap().remove(discussionId.id().toString());
            mapper.getEndpointToDiscussionMap().remove(endpointId.id().toString());
        }
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(SubjectId subjectId) {
        var mapper = getMapper();
        if (mapper.getSubjectToEndpointMap().containsKey(subjectId.id().toString())) {
            var endpointId = new EndpointId(UUID.fromString(
                mapper.getSubjectToEndpointMap().get(subjectId.id().toString())));
            mapper.getSubjectToEndpointMap().remove(subjectId.id().toString());
            mapper.getEndpointToSubjectMap().remove(endpointId.id().toString());
        }
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(InventoryId inventoryId) {
        var mapper = getMapper();
        if (mapper.getInventoryToProjectMap().containsKey(inventoryId.id().toString())) {
            var projectId = new ProjectId(UUID.fromString(
                mapper.getInventoryToProjectMap().get(inventoryId.id().toString())));
            mapper.getInventoryToProjectMap().remove(inventoryId.id().toString());
            mapper.getProjectToInventoryMap().remove(projectId.id().toString());
        }
        saveMapper(mapper);
    }

    @Override
    public void deleteMapping(CollectionId collectionId) {
        var mapper = getMapper();
        if (mapper.getCollectionToProjectMap().containsKey(collectionId.id().toString())) {
            var projectId = new ProjectId(UUID.fromString(
                mapper.getCollectionToProjectMap().get(collectionId.id().toString())));
            mapper.getCollectionToProjectMap().remove(collectionId.id().toString());
            mapper.getProjectToCollectionMap().remove(projectId.id().toString());
        }
        saveMapper(mapper);
    }
}
