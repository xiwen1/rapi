package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.structure.schema.Schema;

public class CreateGrpcEndpointBehavior {

    private final EndpointPersistence endpointPersistence;

    public CreateGrpcEndpointBehavior(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public GrpcEndpoint createGrpcEndpoint(String title, String description,
        String service,
        boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        var grpcEndpoint = GrpcEndpoint.create(title, description, service, isParamStream,
            isResultStream, param, result);
        endpointPersistence.saveGrpc(grpcEndpoint);
        return grpcEndpoint;
    }
}
