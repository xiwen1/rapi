package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.EndpointId;

public class DeleteRestfulEndpointBehavior {

    private final EndpointPersistence endpointPersistence;

    public DeleteRestfulEndpointBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void deleteRestfulEndpoint(EndpointId endpointId) {
        endpointPersistence.deleteRestful(endpointId);
    }
}
