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
    private ProjectId id;
    private String title;
    private Admin owner;
    private List<Participant> participants;
    private List<CrewId> invitationList;

    @Override
    public ProjectId getId() {
        return id;
    }

    private Project(ProjectId id, String title, Admin owner, List<Participant> participants, List<CrewId> invitationList) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.participants = participants;
        this.invitationList = invitationList;
    }

    public static Project create(ProjectId id, String title, Admin owner, List<Participant> participants, List<CrewId> invitationList) {
        return new Project(id, title, owner, participants, invitationList);
    }

    public static Project create(String title, Admin owner) {
        return new Project(ProjectId.create(), title, owner, List.empty(), List.empty());
    }

    public void inviteCrew(CrewId crewId) {
        if (this.invitationList.contains(crewId) || this.participants.map(Participant::getId).contains(crewId)) {
            throw new IllegalArgumentException("Crew already invited or already a participant");
        }
        this.invitationList = this.invitationList.append(crewId);
    }

    public void addParticipant(Participant participant) {
        if (this.invitationList.contains(participant.getId())) {
            this.invitationList = this.invitationList.remove(participant.getId());
        } else {
            throw new IllegalArgumentException("Participant not invited");
        }
        if (this.participants.contains(participant)) {
            throw new IllegalArgumentException("Participant already exists");
        }
        this.participants = this.participants.append(participant);
    }

    public void promoteMember(Member member) {
        if (!this.participants.contains(member)) {
            throw new IllegalArgumentException("Participant not in project");
        }
        this.participants = this.participants.remove(member).append(new Admin(member.getId()));
    }

    public void demoteAdmin(Admin admin) {
        if (!this.participants.contains(admin)) {
            throw new IllegalArgumentException("Participant not in project");
        }
        this.participants = this.participants.remove(admin).append(new Member(admin.getId()));
    }

}
