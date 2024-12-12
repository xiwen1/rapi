package org.rapi.rapi.application.project.infrastructure.repository;

import java.util.List;
import org.rapi.rapi.application.project.infrastructure.dto.ProjectDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProjectRepository extends MongoRepository<ProjectDto, String> {

    @Query("{ 'participants.crewId': ?0 }")
    List<ProjectDto> findByCrewIdInParticipants(String crewId);
}
