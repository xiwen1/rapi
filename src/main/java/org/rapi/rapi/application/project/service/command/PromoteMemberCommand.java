package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.service.ProjectPersistence;
import org.springframework.stereotype.Service;

@Service
public class PromoteMemberCommand {

    private final ProjectPersistence projectPersistence;

    public PromoteMemberCommand(ProjectPersistence projectPersistence) {
        this.projectPersistence = projectPersistence;
    }

    public void promoteMember(ProjectId projectId, Member member) {
        // preparing
        var project = projectPersistence.findById(projectId);

        // operation
        project.promoteMember(member);

        // persisting
        projectPersistence.save(project);
    }
}
