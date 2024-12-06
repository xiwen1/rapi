package org.rapi.rapi.application.project.project;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Project implements Entity<ProjectId> {

    private final ProjectId id;
    private final Admin owner;
    private final String title;
    private List<Participant> participants;
    private List<CrewId> invitedCrews;

    private Project(ProjectId id, String title, Admin owner, List<Participant> participants,
        List<CrewId> invitedCrews) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.participants = participants;
        this.invitedCrews = invitedCrews;
    }


    public static Project create(ProjectId id, String title, Admin owner,
        List<Participant> participants, List<CrewId> invitationList) {
        return new Project(id, title, owner, participants, invitationList);
    }

    public static Project create(String title, Admin owner) {
        return new Project(ProjectId.create(), title, owner, List.of(owner), List.empty());
    }

    public void inviteCrew(CrewId crewId) {
        if (this.invitedCrews.contains(crewId) || this.participants.map(Participant::getCrew)
            .contains(crewId)) {
            throw new IllegalArgumentException("Crew already invited or already a participant");
        }
        this.invitedCrews = this.invitedCrews.append(crewId);
    }

    public void addParticipantViaInvitation(Participant participant) {
        if (this.invitedCrews.contains(participant.getCrew())) {
            this.invitedCrews = this.invitedCrews.remove(participant.getCrew());
        } else {
            throw new IllegalArgumentException("Participant not invited");
        }
        if (this.participants.contains(participant)) {
            throw new IllegalArgumentException("Participant already exists");
        }
        this.invitedCrews = this.invitedCrews.remove(participant.getCrew());
        this.participants = this.participants.append(participant);
    }

    public void promoteMember(Member member) {
        if (!this.participants.contains(member)) {
            throw new IllegalArgumentException("Participant not in project");
        }
        this.participants = this.participants.remove(member).append(new Admin(member.getCrew()));
    }

    public void demoteAdmin(Admin admin) {
        if (!this.participants.contains(admin) || this.owner.equals(admin)) {
            throw new IllegalArgumentException("Participant not in project or is owner");
        }
        this.participants = this.participants.remove(admin).append(new Member(admin.getCrew()));
    }

    public void removeMember(Member member) {
        if (!this.participants.contains(member)) {
            throw new IllegalArgumentException("Participant not in project");
        }
        this.participants = this.participants.remove(member);
    }

}
