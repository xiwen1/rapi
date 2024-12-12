package org.rapi.rapi.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainIdMapperRepository extends MongoRepository<DomainIdMapper, String> {

}
