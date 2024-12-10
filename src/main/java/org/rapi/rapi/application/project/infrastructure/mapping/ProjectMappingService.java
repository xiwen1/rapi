package org.rapi.rapi.application.project.infrastructure.mapping;

import io.vavr.collection.List;
import java.util.UUID;
import org.rapi.rapi.application.project.crew.Crew;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.infrastructure.dto.CrewDto;
import org.rapi.rapi.application.project.infrastructure.dto.ParticipantDto;
import org.rapi.rapi.application.project.infrastructure.dto.ProjectDto;
import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.springframework.stereotype.Service;

@Service
public class ProjectMappingService {

    private ParticipantDto toParticipantDto(Participant participant) {
        var dto = new ParticipantDto();
        dto.setCrewId(participant.getCrew().id().toString());
        if (participant instanceof Admin) {
            dto.setRole("admin");
        } else if (participant instanceof Member) {
            dto.setRole("member");
        } else {
            throw new IllegalArgumentException("Unknown participant type");
        }
        return dto;
    }

    private Participant fromParticipantDto(ParticipantDto participantDto) {
        return switch (participantDto.getRole()) {
            case "admin" -> new Admin(new CrewId(UUID.fromString(participantDto.getCrewId())));
            case "member" -> new Member(new CrewId(UUID.fromString(participantDto.getCrewId())));
            default -> throw new IllegalArgumentException("Unknown participant type");
        };
    }

    public ProjectDto toProjectDto(Project project) {
        var dto = new ProjectDto();
        dto.setId(project.getId().id().toString());
        dto.setOwner(project.getOwner().getCrew().id().toString());
        dto.setParticipants(project.getParticipants().map(this::toParticipantDto).toJavaList());
        dto.setTitle(project.getTitle());
        dto.setInvitedCrews(
            project.getInvitedCrews().map(CrewId::id).map(UUID::toString).toJavaList());
        return dto;
    }

    public Project fromProjectDto(ProjectDto projectDto) {

        var owner = new Admin(new CrewId(UUID.fromString(projectDto.getOwner())));

        var participants = projectDto.getParticipants().stream().map(this::fromParticipantDto)
            .toList();
        var invitedCrews = projectDto.getInvitedCrews().stream().map(UUID::fromString)
            .map(CrewId::new).toList();
        return Project.fromRaw(new ProjectId(UUID.fromString(projectDto.getId())),
            projectDto.getTitle(), owner,
            List.ofAll(participants), List.ofAll(invitedCrews));
    }

    public CrewDto toCrewDto(Crew crew) {
        var dto = new CrewDto();
        dto.setId(crew.getId().toString());
        dto.setEmail(crew.getEmail());
        return dto;
    }

    public Crew fromCrewDto(CrewDto crewDto) {
        return Crew.fromRaw(new CrewId(UUID.fromString(crewDto.getId())), crewDto.getEmail());
    }
}
