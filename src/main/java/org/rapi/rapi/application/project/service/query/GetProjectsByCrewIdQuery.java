package org.rapi.rapi.application.project.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetProjectsByCrewIdQuery {

    private final ProjectPersistence projectPersistence;

    public GetProjectsByCrewIdQuery(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public List<Project> getProjectsByUserId(CrewId crewId) {
        return projectPersistence.findAllByCrewId(crewId);
    }

}
