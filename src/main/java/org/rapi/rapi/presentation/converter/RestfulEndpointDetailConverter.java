package org.rapi.rapi.presentation.converter;

import io.vavr.control.Option;
import java.util.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.endpoint.route.SchemaFragment;
import org.rapi.rapi.application.api.infrastructure.mapping.HttpMethodConverter;
import org.rapi.rapi.application.api.infrastructure.mapping.HttpStatusCodeConverter;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.presentation.dto.RestfulEndpointDetailDto;
import org.rapi.rapi.presentation.dto.RestfulEndpointDetailDto.RoutePath;
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


    @Service
    public class DetailRouteConverter {

        public List<RestfulEndpointDetailDto.RoutePath> toRoutePath(Route route) {
            var routePaths = route.fragments().map(f -> {
                switch (f) {
                    case ConstantFragment constantFragment -> {
                        var constant = new RoutePath.Constant();
                        constant.setConstant(constantFragment.constant());
                        var ret = new RoutePath();
                        ret.setConstant(constant);
                        return ret;
                    }
                    case SchemaFragment schemaFragment -> {
                        var namedSchema = new RoutePath.NamedSchema();
                        namedSchema.setName(schemaFragment.name());
                        namedSchema.setSchema(
                            presentationSchemaConverter.toSchemaDto(schemaFragment.schema()));
                        var ret = new RoutePath();
                        ret.setNamedSchema(namedSchema);
                        return ret;
                    }
                    default -> throw new IllegalArgumentException("Invalid fragment type");
                }
            });
            return routePaths.toJavaList();
        }

        public Route toRoute(List<RestfulEndpointDetailDto.RoutePath> routePath) {
            return new Route(io.vavr.collection.List.ofAll(routePath).map(r -> {
                if (r.getConstant() != null) {
                    return new ConstantFragment(r.getConstant().getConstant());
                } else {
                    return new SchemaFragment(r.getNamedSchema().getName(),
                        presentationSchemaConverter.fromSchemaDto(r.getNamedSchema().getSchema()));
                }
            }));
        }
    }
}
