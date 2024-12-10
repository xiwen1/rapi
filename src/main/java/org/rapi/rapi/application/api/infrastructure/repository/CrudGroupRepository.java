package org.rapi.rapi.application.api.infrastructure.repository;

import org.rapi.rapi.application.api.infrastructure.dto.CrudGroupDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrudGroupRepository extends MongoRepository<CrudGroupDto, String> {

}
