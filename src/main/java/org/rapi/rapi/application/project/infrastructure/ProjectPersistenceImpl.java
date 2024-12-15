package org.rapi.rapi.application.project.infrastructure;

import io.vavr.collection.List;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.infrastructure.mapping.ProjectMappingService;
import org.rapi.rapi.application.project.infrastructure.repository.ProjectRepository;
import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class ProjectPersistenceImpl implements ProjectPersistence {

    private final ProjectRepository projectRepository;
    private final ProjectMappingService projectMappingService;

    public ProjectPersistenceImpl(ProjectRepository projectRepository,
        ProjectMappingService projectMappingService) {
        this.projectRepository = projectRepository;
        this.projectMappingService = projectMappingService;
    }

    @Override
    public void save(Project project) {
        var projectDto = projectMappingService.toProjectDto(project);
        projectRepository.save(projectDto);
    }

    @Override
    public Project findById(ProjectId projectId) {
        return projectMappingService.fromProjectDto(
            projectRepository.findById(projectId.id().toString()).orElseThrow());
    }

    @Override
    public void delete(ProjectId projectId) {
        projectRepository.deleteById(projectId.id().toString());
    }

    @Override
    public List<Project> findAllByCrewId(CrewId crewId) {
        var allProjects = projectRepository.findByCrewIdInParticipants(crewId.id().toString());
        return List.ofAll(allProjects).map(projectMappingService::fromProjectDto);
    }

    @Override
    public List<Project> findAllByInvitation(CrewId crewId) {
        var allProjects = projectRepository.findByCrewIdInInvitations(crewId.id().toString());
        return List.ofAll(allProjects).map(projectMappingService::fromProjectDto);
    }
}
