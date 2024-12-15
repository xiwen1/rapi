package org.rapi.rapi.application.project.service.query;

import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetCrewByEmailQuery {

    private final CrewPersistence crewPersistence;

    public GetCrewByEmailQuery(CrewPersistence crewPersistence) {
        this.crewPersistence = crewPersistence;
    }

    public Crew getCrewByEmail(String email) {
        return crewPersistence.findByEmail(email);
    }
}
