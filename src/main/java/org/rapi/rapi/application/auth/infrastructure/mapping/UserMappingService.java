package org.rapi.rapi.application.auth.infrastructure.mapping;

import java.util.UUID;
import org.rapi.rapi.application.auth.infrastructure.dto.UserDto;
import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.application.auth.user.UserId;
import org.springframework.stereotype.Service;

@Service
public class UserMappingService {


    public UserDto toUserDto(User user) {
        var dto = new UserDto();
        dto.setId(user.getId().id().toString());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public User fromUserDto(UserDto userDto) {
        return User.fromRaw(new UserId(UUID.fromString(userDto.getId())), userDto.getUsername(),
            userDto.getPassword());
    }

}
