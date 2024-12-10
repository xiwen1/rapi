package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.springframework.stereotype.Service;

@Service
public class UpdateGrpcEndpointCommand {

    private final EndpointPersistence endpointPersistence;

    public UpdateGrpcEndpointCommand(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public void updateGrpcEndpoint(GrpcEndpoint grpcEndpoint) {
        endpointPersistence.saveGrpc(grpcEndpoint);
    }
}
