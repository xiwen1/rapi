package org.rapi.rapi.application.api.service.query;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetRestfulEndpointByIdQuery {

    private final EndpointPersistence endpointPersistence;

    public GetRestfulEndpointByIdQuery(EndpointPersistence endpointPersistence) {
        this.endpointPersistence = endpointPersistence;
    }

    public RestfulEndpoint getRestfulEndpointById(EndpointId id) {
        return endpointPersistence.findRestfulById(id);
    }
}
