package org.rapi.rapi.application.auth.infrastructure.repository;

import java.util.Optional;
import org.rapi.rapi.application.auth.infrastructure.dto.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDto, String> {

    Optional<UserDto> findByUsername(String username);
}
