package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;

public class UpdateGrpcEndpointBehavior {

    private final EndpointPersistence endpointPersistence;

    public UpdateGrpcEndpointBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void updateGrpcEndpoint(GrpcEndpoint grpcEndpoint) {
        endpointPersistence.saveGrpc(grpcEndpoint);
    }
}
