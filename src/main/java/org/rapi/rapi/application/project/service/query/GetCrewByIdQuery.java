package org.rapi.rapi.application.project.service.query;

import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetCrewByIdQuery {

    private final CrewPersistence crewPersistence;

    public GetCrewByIdQuery(CrewPersistence crewPersistence) {
        this.crewPersistence = crewPersistence;
    }

    public Crew getCrewById(CrewId id) {
        return crewPersistence.findById(id);
    }
}
