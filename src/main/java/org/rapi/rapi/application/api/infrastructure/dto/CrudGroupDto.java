package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrudGroupDto {

    private String id;
    private String source;
    private String createEndpointId;
    private String listEndpointId;
    private String updateEndpointId;
    private String deleteEndpointId;
}
