package org.rapi.rapi.application.api.service.query;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetGrpcEndpointByIdQuery {

    private final EndpointPersistence endpointPersistence;

    public GetGrpcEndpointByIdQuery(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public GrpcEndpoint getGrpcEndpointById(EndpointId endpointId) {
        return endpointPersistence.findGrpcById(endpointId);
    }
}
