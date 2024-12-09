package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.service.ProjectPersistence;

public class CreateProjectService {
    private final ProjectPersistence projectPersistence;

    public CreateProjectService(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public Project createProject(String name, Admin admin) {
        // operation
        var project = Project.create(name, admin);

        // persistence
        projectPersistence.save(project);

        return project;
    }
}
