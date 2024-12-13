package org.rapi.rapi.presentation.converter;

import io.vavr.control.Option;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.infrastructure.mapping.HttpMethodConverter;
import org.rapi.rapi.application.api.infrastructure.mapping.HttpStatusCodeConverter;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.presentation.dto.RestfulEndpointDetailDto;
import org.springframework.stereotype.Service;

@Service
public class RestfulEndpointDetailConverter {

    private final PresentationSchemaConverter presentationSchemaConverter;
    private final DetailRouteConverter detailRouteConverter;
    private final HttpMethodConverter httpMethodConverter;
    private final HttpStatusCodeConverter httpStatusCodeConverter;
    private final UuidConverter uuidConverter;

    public RestfulEndpointDetailConverter(PresentationSchemaConverter presentationSchemaConverter,
        DetailRouteConverter detailRouteConverter, HttpMethodConverter httpMethodConverter,
        HttpStatusCodeConverter httpStatusCodeConverter, UuidConverter uuidConverter) {
        this.presentationSchemaConverter = presentationSchemaConverter;
        this.detailRouteConverter = detailRouteConverter;
        this.httpMethodConverter = httpMethodConverter;
        this.httpStatusCodeConverter = httpStatusCodeConverter;
        this.uuidConverter = uuidConverter;
    }

    public RestfulEndpointDetailDto toRestfulEndpointDetail(RestfulEndpoint endpoint,
        StateId stateId) {
        var route = detailRouteConverter.toRoutePath(endpoint.getRoute());
        var httpMethod = httpMethodConverter.toString(endpoint.getMethod());
        var request = endpoint.getRequest().map(presentationSchemaConverter::toSchemaDto)
            .getOrNull();
        var response = endpoint.getResponses().map(r -> {
            var statusCode = httpStatusCodeConverter.toInteger(r.statusCode());
            return new RestfulEndpointDetailDto.Response(statusCode, r.description(),
                presentationSchemaConverter.toSchemaDto(r.schema()));
        });

        var headers = endpoint.getHeader().map(presentationSchemaConverter::toSchemaDto)
            .getOrNull();
        var query = endpoint.getQuery().map(presentationSchemaConverter::toSchemaDto)
            .getOrNull();
        return new RestfulEndpointDetailDto(uuidConverter.toString(endpoint.getId().id()),
            endpoint.getTitle(), httpMethod, route, request,
            response.toJavaList(), endpoint.getDescription(), uuidConverter.toString(stateId.id()),
            headers, query);
    }

    public RestfulEndpoint fromRestfulEndpointDto(
        RestfulEndpointDetailDto restfulEndpointDetailDto) {
        var route = detailRouteConverter.toRoute(restfulEndpointDetailDto.getRoutePath());
        var method = httpMethodConverter.fromString(restfulEndpointDetailDto.getHttpMethod());
        var request = Option.of(restfulEndpointDetailDto.getRequest())
            .map(presentationSchemaConverter::fromSchemaDto);
        var responses = io.vavr.collection.List.ofAll(restfulEndpointDetailDto.getResponse())
            .map(r -> {
                var statusCode = httpStatusCodeConverter.fromInteger(r.getStatusCode());
                return new Response(statusCode, r.getDescription(),
                    presentationSchemaConverter.fromSchemaDto(r.getSchema()));
            });
        var headers = Option.of(restfulEndpointDetailDto.getHeaders())
            .map(h -> (ObjectSchema) presentationSchemaConverter.fromSchemaDto(h));
        var query = Option.of(restfulEndpointDetailDto.getQuery())
            .map(q -> (ObjectSchema) presentationSchemaConverter.fromSchemaDto(q));
        return RestfulEndpoint.fromRaw(
            new EndpointId(uuidConverter.fromString(restfulEndpointDetailDto.getId())),
            restfulEndpointDetailDto.getName(), restfulEndpointDetailDto.getDescription(),
            request, headers, query, method, route, responses);
    }


}
