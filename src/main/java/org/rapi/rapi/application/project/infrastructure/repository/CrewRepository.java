package org.rapi.rapi.application.project.infrastructure.repository;

import java.util.Optional;
import org.rapi.rapi.application.project.infrastructure.dto.CrewDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrewRepository extends MongoRepository<CrewDto, String> {

    Optional<CrewDto> findFirstByEmail(String email);
}
