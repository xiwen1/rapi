package org.rapi.rapi.application.discussion.infrastructure.repository;

import org.rapi.rapi.application.discussion.infrastructure.dto.AuthorDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<AuthorDto, String> {

}
