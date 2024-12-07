package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.EndpointId;

public class DeleteGrpcEndpointBehavior {

    private final EndpointPersistence endpointPersistence;

    public DeleteGrpcEndpointBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void deleteGrpcEndpoint(EndpointId endpointId) {
        endpointPersistence.deleteGrpc(endpointId);
    }
}
