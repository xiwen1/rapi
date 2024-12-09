package org.rapi.rapi.application.api.infrastructure.dto;

import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@Document("restful_endpoints")
public class RestfulEndpointDto {

    @Id
    private String _id;
    private String title;
    private String description;
    private HttpMethod method;
    private RouteDto route;
    private List<Response> responses;
    private Schema request;
    private ObjectSchema header;
    private ObjectSchema query;

    @PersistenceCreator
    public RestfulEndpointDto(String _id, String title, String description, HttpMethod method,
        RouteDto route, List<Response> responses, Schema request, ObjectSchema header,
        ObjectSchema query) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.method = method;
        this.route = route;
        this.responses = responses;
        this.request = request;
        this.header = header;
        this.query = query;
    }

    public static RestfulEndpointDto fromDomain(RestfulEndpoint domain) {
        return new RestfulEndpointDto(
            domain.getId().id().toString(),
            domain.getTitle(),
            domain.getDescription(),
            domain.getMethod(),
            RouteDto.fromDomain(domain.getRoute()),
            domain.getResponses(),
            domain.getRequest().getOrNull(),
            domain.getHeader().getOrNull(),
            domain.getQuery().getOrNull()
        );
    }

    public RestfulEndpoint toDomain() {
        return RestfulEndpoint.fromRaw(
            new EndpointId(UUID.fromString(get_id())),
            getTitle(),
            getDescription(),
            getRequest() == null ? Option.none() : Option.some(getRequest()),
            getHeader() == null ? Option.none() : Option.some(getHeader()),
            getQuery() == null ? Option.none() : Option.some(getQuery()),
            getMethod(),
            getRoute().toDomain(),
            getResponses()
        );
    }

}
