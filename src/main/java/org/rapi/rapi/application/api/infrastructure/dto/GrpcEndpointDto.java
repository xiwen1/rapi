package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("grpc_endpoint")
@Getter
@Setter
public class GrpcEndpointDto {

    @Id
    private String id;
    private String title;
    private String description;
    private String service;
    private boolean isParamStream;
    private boolean isResultStream;
    private String param;
    private String result;
}
