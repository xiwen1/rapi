package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("crud_group")
public class CrudGroupDto {

    @Id
    private String id;
    private String source;
    private String createEndpointId;
    private String listEndpointId;
    private String updateEndpointId;
    private String deleteEndpointId;
}
