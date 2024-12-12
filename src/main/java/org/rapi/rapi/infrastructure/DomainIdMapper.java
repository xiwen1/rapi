package org.rapi.rapi.infrastructure;

import java.util.HashMap;
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


    private Map<String, String> endpointToSubjectMap = new HashMap<>();
    private Map<String, String> subjectToEndpointMap = new HashMap<>();
    private Map<String, String> endpointToDiscussionMap = new HashMap<>();
    private Map<String, String> discussionToEndpointMap = new HashMap<>();

    private Map<String, String> projectToInventoryMap = new HashMap<>();
    private Map<String, String> inventoryToProjectMap = new HashMap<>();

    private Map<String, String> userToAuthorMap = new HashMap<>();
    private Map<String, String> authorToUserMap = new HashMap<>();

    private Map<String, String> userToCrewMap = new HashMap<>();
    private Map<String, String> crewToUserMap = new HashMap<>();

    private Map<String, String> projectToCollectionMap = new HashMap<>();
    private Map<String, String> collectionToProjectMap = new HashMap<>();

    public DomainIdMapper(String id) {
        this.id = id;

    }

    public DomainIdMapper() {
    }
}
