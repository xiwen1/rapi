package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;

public class UpdateRestfulEndpointBehavior {

    private final EndpointPersistence endpointPersistence;

    public UpdateRestfulEndpointBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void updateRestfulEndpoint(RestfulEndpoint restfulEndpoint) {
        endpointPersistence.saveRestful(restfulEndpoint);
    }

}
