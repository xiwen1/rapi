package org.rapi.rapi.application.discussion.infrastructure.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("author")
public class AuthorDto {

    @Id
    private String id;

}
