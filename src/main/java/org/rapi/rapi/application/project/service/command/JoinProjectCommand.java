package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class JoinProjectCommand {

    private final ProjectPersistence projectPersistence;

    public JoinProjectCommand(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public void joinProject(ProjectId projectId, CrewId crewId) {
        // preparing
        var project = projectPersistence.findById(projectId);

        // operation
        project.addParticipantViaInvitation(new Member(crewId));

        // persisting
        projectPersistence.save(project);
    }
}
