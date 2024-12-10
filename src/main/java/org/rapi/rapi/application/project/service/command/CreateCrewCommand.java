package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateCrewCommand {

    private final CrewPersistence crewPersistence;

    public CreateCrewCommand(CrewPersistence crewPersistence) {
        this.crewPersistence = crewPersistence;
    }

    public Crew createCrew(String email) {
        // operation
        var crew = Crew.create(email);

        // persistence
        crewPersistence.save(crew);

        return crew;
    }
}
