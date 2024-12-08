package org.rapi.rapi.application.project.service.command;

import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.service.ProjectPersistence;

public class PromoteMemberService {
    private final ProjectPersistence projectPersistence;

    public PromoteMemberService(ProjectPersistence projectPersistence) {
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
