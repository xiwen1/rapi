package org.rapi.rapi.application.api.infrastructure.repository;

import org.rapi.rapi.application.api.infrastructure.dto.RestfulEndpointDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface RestfulEndpointRepository extends MongoRepository<RestfulEndpointDto, String> {

}
