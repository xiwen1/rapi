package org.rapi.rapi.application.project.infrastructure.dto;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("project")
public class ProjectDto {

    @Id
    private String id;
    private String owner;
    private String title;
    private List<ParticipantDto> participants;
    private List<String> invitedCrews;

}
