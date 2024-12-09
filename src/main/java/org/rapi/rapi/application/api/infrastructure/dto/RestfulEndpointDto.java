package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.application.api.infrastructure.dto.route.RouteDto;
import org.rapi.rapi.application.api.infrastructure.dto.schema.ObjectSchemaDto;
import org.rapi.rapi.application.api.infrastructure.dto.schema.SchemaDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("restful_endpoints")
public class RestfulEndpointDto {

    @Id
    private String id;
    private String title;
    private String description;
    private String method;
    private RouteDto route;
    private List<ResponseDto> responses;
    private SchemaDto request;
    private ObjectSchemaDto header;
    private ObjectSchemaDto query;
}
