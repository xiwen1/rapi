package org.rapi.rapi.application.project.service;

import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Member;

public class JoinProjectService {

    private final ProjectPersistence projectPersistence;

    public JoinProjectService(ProjectPersistence projectPersistence) {
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
