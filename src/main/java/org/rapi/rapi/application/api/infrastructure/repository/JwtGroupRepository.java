package org.rapi.rapi.application.api.infrastructure.repository;

import org.rapi.rapi.application.api.infrastructure.dto.JwtGroupDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JwtGroupRepository extends MongoRepository<JwtGroupDto, String> {

}
