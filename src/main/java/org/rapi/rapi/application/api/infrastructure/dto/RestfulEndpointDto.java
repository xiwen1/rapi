package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("restful_endpoint")
public class RestfulEndpointDto {

    @Id
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
