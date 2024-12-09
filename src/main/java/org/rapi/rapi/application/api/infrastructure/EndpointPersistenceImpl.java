package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.infrastructure.dto.GrpcEndpointDto;
import org.rapi.rapi.application.api.infrastructure.dto.RestfulEndpointDto;
import org.rapi.rapi.application.api.infrastructure.repository.GrpcEndpointRepository;
import org.rapi.rapi.application.api.infrastructure.repository.RestfulEndpointRepository;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.springframework.stereotype.Service;

@Service
public class EndpointPersistenceImpl implements EndpointPersistence {

    private final RestfulEndpointRepository restfulEndpointRepository;
    private final GrpcEndpointRepository grpcEndpointRepository;

    public EndpointPersistenceImpl(RestfulEndpointRepository restfulEndpointRepository,
        GrpcEndpointRepository grpcEndpointRepository) {
        this.restfulEndpointRepository = restfulEndpointRepository;
        this.grpcEndpointRepository = grpcEndpointRepository;
    }

    @Override
    public void saveGrpc(GrpcEndpoint grpcEndpoint) {
        grpcEndpointRepository.save(GrpcEndpointDto.fromDomain(grpcEndpoint));
    }

    @Override
    public void saveRestful(RestfulEndpoint restfulEndpoint) {
        restfulEndpointRepository.save(RestfulEndpointDto.fromDomain(restfulEndpoint));
    }

    @Override
    public void deleteRestful(EndpointId endpointId) {
        restfulEndpointRepository.deleteById(endpointId.id().toString());
    }

    @Override
    public void deleteGrpc(EndpointId endpointId) {
        grpcEndpointRepository.deleteById(endpointId.id().toString());
    }

    @Override
    public RestfulEndpoint findRestfulById(EndpointId endpointId) {
        return restfulEndpointRepository.findById(endpointId.id().toString()).orElseThrow()
            .toDomain();
    }

    @Override
    public GrpcEndpoint findGrpcById(EndpointId endpointId) {
        return grpcEndpointRepository.findById(endpointId.id().toString()).orElseThrow().toDomain();
    }

    public void deleteAll() {
        restfulEndpointRepository.deleteAll();
        grpcEndpointRepository.deleteAll();
    }
}
