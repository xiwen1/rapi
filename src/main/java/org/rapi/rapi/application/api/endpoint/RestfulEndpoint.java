package org.rapi.rapi.application.api.endpoint;

import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpMethod;

import java.util.List;

@Getter
public class RestfulEndpoint extends Endpoint {
    private HttpMethod method;
    private Route route;
    private List<Response> responses;

    private RestfulEndpoint(EndpointId id, String title, String description, Schema request, HttpMethod method, Route route, List<Response> responses) {
        super(id, title, description, request);
        this.method = method;
        this.route = route;
        this.responses = responses;
    }

    public static RestfulEndpoint create(EndpointId id, String title, String description, Schema request, HttpMethod method, Route route, List<Response> responses) {
        return new RestfulEndpoint(id, title, description, request, method, route, responses);
    }

    public static RestfulEndpoint create(String title, String description, Schema request, HttpMethod method, Route route, List<Response> responses) {
        return new RestfulEndpoint(EndpointId.create(), title, description, request, method, route, responses);
    }
}
