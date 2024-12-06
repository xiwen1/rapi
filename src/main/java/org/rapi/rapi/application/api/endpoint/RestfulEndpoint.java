package org.rapi.rapi.application.api.endpoint;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpMethod;

@Getter
public class RestfulEndpoint extends Endpoint {

    private final HttpMethod method;
    private final Route route;
    private final List<Response> responses;
    private final Option<Schema> request;
    private final Option<ObjectSchema> header;
    private final Option<ObjectSchema> query;

    private RestfulEndpoint(EndpointId id, String title, String description, Option<Schema> request,
                            HttpMethod method,
                            Route route, List<Response> responses, Option<ObjectSchema> header,
                            Option<ObjectSchema> query) {
        super(id, title, description);
        this.method = method;
        this.route = route;
        this.responses = responses;
        this.request = request;
        this.header = header;
        this.query = query;
    }

    public static RestfulEndpoint create(EndpointId id, String title, String description,
                                         Option<Schema> request, Option<ObjectSchema> header, Option<ObjectSchema> query,
                                         HttpMethod method, Route route, List<Response> responses) {
        return new RestfulEndpoint(id, title, description, request, method, route, responses,
                header, query);
    }

    public static RestfulEndpoint create(String title, String description, Option<Schema> request,
                                         HttpMethod method,
                                         Route route, List<Response> responses) {
        return new RestfulEndpoint(EndpointId.create(), title, description, request, method, route,
                responses, Option.none(), Option.none());
    }

    public static RestfulEndpoint create(String title, String description, HttpMethod method,
                                         Route route, List<Response> responses) {
        return new RestfulEndpoint(EndpointId.create(), title, description, Option.none(), method,
                route, responses, Option.none(), Option.none());
    }

    public static RestfulEndpoint create(String title, String description, Schema request,
                                         HttpMethod method,
                                         Route route, List<Response> responses) {
        return new RestfulEndpoint(EndpointId.create(), title, description, Option.some(request),
                method,
                route, responses, Option.none(), Option.none());
    }

    public static RestfulEndpoint create(EndpointId id, RestfulEndpoint copy) {
        return new RestfulEndpoint(id, copy.getTitle(), copy.getDescription(), copy.getRequest(),
                copy.getMethod(),
                copy.getRoute(), copy.getResponses(), copy.getHeader(), copy.getQuery());
    }
}
