package org.rapi.rapi.application.api.infrastructure.repository;

import org.rapi.rapi.application.api.infrastructure.dto.StructureDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StructureRepository extends MongoRepository<StructureDto, String> {

}
