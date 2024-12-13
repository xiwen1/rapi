package org.rapi.rapi.usecase;

import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.command.InviteParticipantCommand;
import org.rapi.rapi.application.project.service.query.GetCrewByEmailQuery;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.springframework.stereotype.Service;

@Service
public class InviteCrewUseCase {

    private final GetCrewByEmailQuery getCrewByEmailQuery;
    private final GetProjectByIdQuery getProjectByIdQuery;
    private final InviteParticipantCommand inviteParticipantCommand;

    public InviteCrewUseCase(GetCrewByEmailQuery getCrewByEmailQuery,
        GetProjectByIdQuery getProjectByIdQuery,
        InviteParticipantCommand inviteParticipantCommand) {
        this.getCrewByEmailQuery = getCrewByEmailQuery;
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.inviteParticipantCommand = inviteParticipantCommand;
    }

    public void inviteCrew(String email, ProjectId projectId) {
        inviteParticipantCommand.inviteParticipant(projectId, email);
    }
}
