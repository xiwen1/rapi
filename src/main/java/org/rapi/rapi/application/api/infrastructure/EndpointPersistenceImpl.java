package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.infrastructure.mapping.ApiMappingService;
import org.rapi.rapi.application.api.infrastructure.repository.GrpcEndpointRepository;
import org.rapi.rapi.application.api.infrastructure.repository.RestfulEndpointRepository;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.springframework.stereotype.Service;

@Service
public class EndpointPersistenceImpl implements EndpointPersistence {

    private final RestfulEndpointRepository restfulEndpointRepository;
    private final GrpcEndpointRepository grpcEndpointRepository;
    private final ApiMappingService apiMappingService;

    public EndpointPersistenceImpl(RestfulEndpointRepository restfulEndpointRepository,
        GrpcEndpointRepository grpcEndpointRepository, ApiMappingService apiMappingService) {
        this.restfulEndpointRepository = restfulEndpointRepository;
        this.grpcEndpointRepository = grpcEndpointRepository;
        this.apiMappingService = apiMappingService;
    }

    @Override
    public void saveGrpc(GrpcEndpoint grpcEndpoint) {
        grpcEndpointRepository.save(apiMappingService.toGrpcEndpointDto(grpcEndpoint));
    }

    @Override
    public void saveRestful(RestfulEndpoint restfulEndpoint) {
        restfulEndpointRepository.save(apiMappingService.toRestfulEndpointDto(restfulEndpoint));
    }

    @Override
    public void deleteRestful(EndpointId endpointId) {
        restfulEndpointRepository.deleteById(apiMappingService.toStringId(endpointId.id()));
    }

    @Override
    public void deleteGrpc(EndpointId endpointId) {
        grpcEndpointRepository.deleteById(apiMappingService.toStringId(endpointId.id()));
    }

    @Override
    public RestfulEndpoint findRestfulById(EndpointId endpointId) {
        return apiMappingService.fromRestfulEndpointDto(
            restfulEndpointRepository.findById(endpointId.id().toString()).orElseThrow());
    }

    @Override
    public GrpcEndpoint findGrpcById(EndpointId endpointId) {
        return apiMappingService.fromGrpcEndpointDto(
            grpcEndpointRepository.findById(endpointId.id().toString()).orElseThrow());
    }

    public void deleteAll() {
        restfulEndpointRepository.deleteAll();
        grpcEndpointRepository.deleteAll();
    }
}
