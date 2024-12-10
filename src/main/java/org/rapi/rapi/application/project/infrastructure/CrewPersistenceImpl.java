package org.rapi.rapi.application.project.infrastructure;

import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.infrastructure.mapping.ProjectMappingService;
import org.rapi.rapi.application.project.infrastructure.repository.CrewRepository;
import org.rapi.rapi.application.project.service.CrewPersistence;

public class CrewPersistenceImpl implements CrewPersistence {

    private final CrewRepository crewRepository;
    private final ProjectMappingService projectMappingService;

    public CrewPersistenceImpl(CrewRepository crewRepository,
        ProjectMappingService projectMappingService) {
        this.crewRepository = crewRepository;
        this.projectMappingService = projectMappingService;
    }

    @Override
    public void save(Crew crew) {
        crewRepository.save(projectMappingService.toCrewDto(crew));
    }

    @Override
    public Crew findById(CrewId crewId) {
        return projectMappingService.fromCrewDto(
            crewRepository.findById(crewId.id().toString()).orElseThrow());
    }

    @Override
    public Crew findByEmail(String email) {
        return projectMappingService.fromCrewDto(
            crewRepository.findFirstByEmail(email).orElseThrow());
    }

    @Override
    public void delete(CrewId crewId) {
        crewRepository.deleteById(crewId.id().toString());
    }
}
