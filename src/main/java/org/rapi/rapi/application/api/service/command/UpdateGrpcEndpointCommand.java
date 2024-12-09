package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.service.EndpointPersistence;

public class UpdateGrpcEndpointCommand {

    private final EndpointPersistence endpointPersistence;

    public UpdateGrpcEndpointCommand(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void updateGrpcEndpoint(GrpcEndpoint grpcEndpoint) {
        endpointPersistence.saveGrpc(grpcEndpoint);
    }
}
