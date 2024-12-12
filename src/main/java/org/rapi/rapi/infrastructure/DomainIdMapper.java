package org.rapi.rapi.infrastructure;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("domain_id_mapper")
@Getter
@Setter
public class DomainIdMapper {

    @Id
    private String id;

    private Map<String, String> endpointToSubjectMap;
    private Map<String, String> subjectToEndpointMap;
    private Map<String, String> endpointToDiscussionMap;
    private Map<String, String> discussionToEndpointMap;

    private Map<String, String> projectToInventoryMap;
    private Map<String, String> inventoryToProjectMap;

    private Map<String, String> userToAuthorMap;
    private Map<String, String> authorToUserMap;

    private Map<String, String> userToCrewMap;
    private Map<String, String> crewToUserMap;

    private Map<String, String> projectToCollectionMap;
    private Map<String, String> collectionToProjectMap;

    public DomainIdMapper(String id) {
        this.id = id;
    }


}
