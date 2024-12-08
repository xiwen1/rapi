package org.rapi.rapi.application.api.service;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;

public class UpdateGrpcEndpointService {

    private final EndpointPersistence endpointPersistence;

    public UpdateGrpcEndpointService(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void updateGrpcEndpoint(GrpcEndpoint grpcEndpoint) {
        endpointPersistence.saveGrpc(grpcEndpoint);
    }
}
