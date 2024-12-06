package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;

public class CreateRestfulEndpointFromEntityBehavior {

    private final EndpointPersistence endpointPersistence;

    public CreateRestfulEndpointFromEntityBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public RestfulEndpoint createRestfulEndpointFromEntity(EndpointId endpointId,
        RestfulEndpoint endpoint) {
        var newEndpoint = RestfulEndpoint.create(endpointId, endpoint);
        endpointPersistence.saveRestful(newEndpoint);
        return newEndpoint;
    }
}
