package org.rapi.rapi.application.project.infrastructure.dto;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("crew")
public class CrewDto {

    @Id
    private String id;
    private String email;
}
