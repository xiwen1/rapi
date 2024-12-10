package org.rapi.rapi.application.state.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionRepository extends MongoRepository<CollectionDto, String> {

}
