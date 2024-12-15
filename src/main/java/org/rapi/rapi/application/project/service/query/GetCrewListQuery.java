package org.rapi.rapi.application.project.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetCrewListQuery {

    private final ProjectPersistence projectPersistence;
    private final CrewPersistence crewPersistence;

    public GetCrewListQuery(ProjectPersistence projectPersistence,
        CrewPersistence crewPersistence) {
        this.projectPersistence = projectPersistence;
        this.crewPersistence = crewPersistence;
    }

    public List<Crew> getCrewList(ProjectId projectId) {
        return projectPersistence.findById(projectId).getParticipants().map(Participant::getCrew)
            .map(crewPersistence::findById);
    }
}
