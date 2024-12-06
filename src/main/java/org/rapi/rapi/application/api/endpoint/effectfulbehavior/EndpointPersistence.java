package org.rapi.rapi.application.api.endpoint.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.Endpoint;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;

public interface EndpointPersistence {

    void saveGrpc(GrpcEndpoint grpcEndpoint);

    void saveRestful(RestfulEndpoint restfulEndpoint);

    void deleteRestful(EndpointId endpointId);

    void deleteGrpc(EndpointId endpointId);

    Endpoint findById(EndpointId endpointId);

}
