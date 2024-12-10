package org.rapi.rapi.application.discussion.infrastructure.repository;

import org.rapi.rapi.application.discussion.infrastructure.dto.DiscussionDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscussionRepository extends MongoRepository<DiscussionDto, String> {

}
