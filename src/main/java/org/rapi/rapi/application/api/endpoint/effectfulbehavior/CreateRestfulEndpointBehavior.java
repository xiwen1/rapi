package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpMethod;

public class CreateRestfulEndpointBehavior {

    private final EndpointPersistence endpointPersistence;

    public CreateRestfulEndpointBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public RestfulEndpoint createRestfulEndpoint(String title, String description,
        Option<Schema> request,
        HttpMethod method,
        Route route, List<Response> responses) {
        var restfulEndpoint = RestfulEndpoint.create(title, description, request, method, route,
            responses);
        endpointPersistence.saveRestful(restfulEndpoint);
        return restfulEndpoint;
    }
}
