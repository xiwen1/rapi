package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class InviteParticipantCommand {

    private final CrewPersistence crewPersistence;
    private final ProjectPersistence projectPersistence;

    public InviteParticipantCommand(CrewPersistence crewPersistence,
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
