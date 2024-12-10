package org.rapi.rapi.application.auth.infrastructure.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("user")
public class UserDto {

    @Id
    private String id;
    private String username;
    private String password;
}
