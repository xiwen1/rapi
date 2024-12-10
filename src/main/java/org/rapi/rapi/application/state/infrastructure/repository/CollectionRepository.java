package org.rapi.rapi.application.state.infrastructure.repository;

import org.rapi.rapi.application.state.infrastructure.dto.CollectionDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionRepository extends MongoRepository<CollectionDto, String> {

}
