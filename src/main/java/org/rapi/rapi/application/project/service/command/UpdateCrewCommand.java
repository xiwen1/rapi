package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.springframework.stereotype.Service;

@Service
public class UpdateCrewCommand {

    private final CrewPersistence crewPersistence;

    public UpdateCrewCommand(CrewPersistence crewPersistence) {
        this.crewPersistence = crewPersistence;
    }

    public void updateCrew(CrewId crewId, String email) {
        // preparing
        var crew = crewPersistence.findById(crewId);

        // operation
        crew.updateEmail(email);

        // persistence
        crewPersistence.save(crew);
    }
}
