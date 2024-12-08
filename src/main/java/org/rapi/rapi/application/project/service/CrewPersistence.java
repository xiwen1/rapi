package org.rapi.rapi.application.project.service;

import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.crew.CrewId;

public interface CrewPersistence {
    void save(Crew crew);
    Crew findById(CrewId crewId);
    Crew findByEmail(String email);

    void delete(CrewId crewId);

}
