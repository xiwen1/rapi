package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.service.ProjectPersistence;

public class DemoteAdminService {

    private final ProjectPersistence projectPersistence;

    public DemoteAdminService(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public void demoteAdmin(ProjectId projectId, Admin admin) {
        // operation
        var project = projectPersistence.findById(projectId);
        project.demoteAdmin(admin);

        // persistence
        projectPersistence.save(project);

    }
}
