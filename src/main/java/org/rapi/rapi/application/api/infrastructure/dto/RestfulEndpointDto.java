package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestfulEndpointDto {

    private String id;
    private String title;
    private String description;
    private String method;
    private RouteDto route;
    private List<ResponseDto> responses;
    private SchemaDto request;
    private SchemaDto header;
    private SchemaDto query;
}
