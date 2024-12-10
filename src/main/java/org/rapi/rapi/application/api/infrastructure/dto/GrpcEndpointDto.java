package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrpcEndpointDto {

    private String id;
    private String title;
    private String description;
    private String service;
    private boolean isParamStream;
    private boolean isResultStream;
    private SchemaDto param;
    private SchemaDto result;
}
