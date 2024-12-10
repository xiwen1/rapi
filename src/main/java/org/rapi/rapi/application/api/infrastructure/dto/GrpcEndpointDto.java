package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("grpc_endpoint")
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
