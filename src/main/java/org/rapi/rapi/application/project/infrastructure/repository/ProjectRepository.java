package org.rapi.rapi.application.project.infrastructure.repository;

import org.rapi.rapi.application.project.infrastructure.dto.ProjectDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<ProjectDto, String> {

}
