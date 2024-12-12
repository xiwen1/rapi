package org.rapi.rapi.application.project.service.query;

import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetProjectByIdQuery {

    private final ProjectPersistence projectPersistence;

    public GetProjectByIdQuery(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public Project getProjectById(ProjectId id) {
        return projectPersistence.findById(id);
    }
}
