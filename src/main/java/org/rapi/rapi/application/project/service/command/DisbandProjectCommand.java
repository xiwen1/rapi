package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class DisbandProjectCommand {

    private final ProjectPersistence projectPersistence;

    public DisbandProjectCommand(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public void disbandProject(ProjectId projectId) {
        projectPersistence.delete(projectId);
    }
}
