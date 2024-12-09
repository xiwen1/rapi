package org.rapi.rapi.application.api.infrastructure.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("crud_group")
@Getter
@Setter
public class CrudGroupDto {

    @Id
    private String id;
    private String createEndpointId;
    private String listEndpointId;
    private String updateEndpointId;
    private String deleteEndpointId;
}
