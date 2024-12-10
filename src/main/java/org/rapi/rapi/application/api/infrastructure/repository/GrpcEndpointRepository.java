package org.rapi.rapi.application.api.infrastructure.repository;

import org.rapi.rapi.application.api.infrastructure.dto.GrpcEndpointDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrpcEndpointRepository extends MongoRepository<GrpcEndpointDto, String> {

}
