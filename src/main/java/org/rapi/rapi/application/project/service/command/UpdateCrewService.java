package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.service.CrewPersistence;

public class UpdateCrewService {
    private final CrewPersistence crewPersistence;

    public UpdateCrewService(CrewPersistence crewPersistence) {
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
