package org.rapi.rapi.application.project.service;

import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.ProjectId;

public class InviteParticipantService {

    private final CrewPersistence crewPersistence;
    private final ProjectPersistence projectPersistence;

    public InviteParticipantService(CrewPersistence crewPersistence,
        ProjectPersistence projectPersistence) {
        this.crewPersistence = crewPersistence;
        this.projectPersistence = projectPersistence;
    }

    public void inviteParticipant(ProjectId projectId, String email) {
        // preparing
        var crew = crewPersistence.findByEmail(email);
        var project = projectPersistence.findById(projectId);

        // operation
        project.inviteCrew(crew.getId());

        // persisting
        projectPersistence.save(project);
    }
}
